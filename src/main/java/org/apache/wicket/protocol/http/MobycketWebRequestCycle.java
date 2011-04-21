/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://aipostyle.com/
 */

package org.apache.wicket.protocol.http;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.AbortException;
import org.apache.wicket.IRedirectListener;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Page;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.BrowserInfoPage;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.ClientInfo;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.settings.IRequestCycleSettings;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see org.apache.wicket.protocol.http.WebRequestCycle
 */
public class MobycketWebRequestCycle extends RequestCycle {

  /** Logging object */
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory
    .getLogger(WebRequestCycle.class);

  private static final MetaDataKey<Boolean> BROWSER_WAS_POLLED_KEY =
    new MetaDataKey<Boolean>() {
      private static final long serialVersionUID = 1L;
    };

  public MobycketWebRequestCycle(final WebApplication application,
      final WebRequest request, final Response response) {
    super(application, request, response);
  }

  @Override
  public IRequestCycleProcessor getProcessor() {
    return ((WebApplication) getApplication()).getRequestCycleProcessor();
  }

  @Override
  protected void onExceptionLoop(RuntimeException e) {
    super.onExceptionLoop(e);
    try {
      getWebResponse().getHttpServletResponse().sendError(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
        null);
    } catch (IOException e1) {
      // ignore
    }
  }

  public WebRequest getWebRequest() {
    return (WebRequest) request;
  }

  public WebResponse getWebResponse() {
    return (WebResponse) response;
  }

  public WebSession getWebSession() {
    return (WebSession) getSession();
  }

  @Override
  public final void redirectTo(final Page page) {
    String redirectUrl = null;

    // Check if use serverside response for client side redirects
    IRequestCycleSettings settings = application.getRequestCycleSettings();
    if ((settings.getRenderStrategy() == IRequestCycleSettings.REDIRECT_TO_BUFFER)
      && (application instanceof WebApplication)
      && !(getWebRequest().isAjax())) {
      // remember the current response
      final WebResponse currentResponse = getWebResponse();
      try {
        if (getWebRequest() instanceof ServletWebRequest) {
          // Get the redirect url and set it in the ServletWebRequest
          // so that it can be used for relative url calculation.
          ((ServletWebRequest) getWebRequest()).setWicketRedirectUrl(Strings
            .replaceAll(
              page.urlFor(IRedirectListener.INTERFACE).toString(),
              "../",
              "")
            .toString());
        }
        // create the redirect response.
        final BufferedHttpServletResponse servletResponse =
          new BufferedHttpServletResponse(currentResponse
            .getHttpServletResponse());
        final WebResponse redirectResponse = new WebResponse(servletResponse) {
          @Override
          public CharSequence encodeURL(CharSequence url) {
            return currentResponse.encodeURL(url);
          }

          @Override
          public void sendRedirect(String url) throws IOException {
            String reqUrl =
              ((WebRequest) RequestCycle.get().getRequest())
                .getHttpServletRequest()
                .getRequestURI();
            String absUrl = RequestUtils.toAbsolutePath(reqUrl, url);
            getHttpServletResponse().sendRedirect(absUrl);
          }
        };
        redirectResponse.setCharacterEncoding(currentResponse
          .getCharacterEncoding());

        // redirect the response to the buffer
        setResponse(redirectResponse);

        // render the page into the buffer
        page.renderPage();

        // re-assign the original response
        setResponse(currentResponse);

        final String responseRedirect = servletResponse.getRedirectUrl();
        if (responseRedirect != null) {
          // if the redirectResponse has another redirect url set
          // then the rendering of this page caused a redirect to
          // something else.
          // set this redirect then.
          redirectUrl = responseRedirect;
        } else if (servletResponse.getContentLength() > 0) {
          // call filter() so that any filters can process the
          // response
          servletResponse.filter(currentResponse);

          // Set the final character encoding before calling close
          servletResponse.setCharacterEncoding(currentResponse
            .getCharacterEncoding());
          // close it so that the response is fixed and encoded from
          // here on.
          servletResponse.close();

          if (getWebRequest() instanceof ServletWebRequest) {
            // Get the redirect url and set it in the ServletWebRequest
            // so that it can be used for relative url calculation.
            ((ServletWebRequest) getWebRequest()).setWicketRedirectUrl(null);
          }

          redirectUrl = page.urlFor(IRedirectListener.INTERFACE).toString();
          String stripped =
            Strings.stripJSessionId(Strings
              .replaceAll(redirectUrl, "../", "")
              .toString());
          int index = stripped.indexOf("?");
          String bufferId = stripped.substring(index + 1);

          String sessionId =
            getApplication().getSessionStore().getSessionId(request, true);
          ((WebApplication) application).addBufferedResponse(
            sessionId,
            bufferId,
            servletResponse);
        }
      } catch (RuntimeException ex) {
        // re-assign the original response
        setResponse(currentResponse);

        /*
         * check if the raised exception wraps an abort exception. if so, it is
         * probably wise to unwrap and rethrow the abort exception
         */
        Throwable cause = ex;
        while (cause != null) {
          if (cause instanceof AbortException) {
            throw ((AbortException) cause);
          }
          cause = cause.getCause();
        }

        if (!(ex instanceof PageExpiredException)) {
          logRuntimeException(ex);
        }

        IRequestCycleProcessor processor = getProcessor();
        processor.respond(ex, this);
        return;
      }
    } else {
      redirectUrl = page.urlFor(IRedirectListener.INTERFACE).toString();

      // Redirect page can touch its models already (via for example the
      // constructors)
      // this can be removed i guess because this page will be detached in
      // the page target
      // page.internalDetach();
    }

    if (redirectUrl == null) {
      redirectUrl = page.urlFor(IRedirectListener.INTERFACE).toString();
    }

    // Always touch the page again so that a redirect listener makes a page
    // stateful and adds it to the pagemap
    getSession().touch(page);

    // Redirect to the url for the page
    response.redirect(redirectUrl);
  }

  /**
   * @see org.apache.wicket.RequestCycle#newClientInfo()
   */
  @Override
  protected ClientInfo newClientInfo() {
    if (getApplication()
      .getRequestCycleSettings()
      .getGatherExtendedBrowserInfo()) {
      Session session = getSession();
      if (session.getMetaData(BROWSER_WAS_POLLED_KEY) == null) {
        // we haven't done the redirect yet; record that we will be
        // doing that now and redirect
        session.setMetaData(BROWSER_WAS_POLLED_KEY, Boolean.TRUE);
        String url = "/" + getRequest().getURL();
        throw new RestartResponseException(newBrowserInfoPage(url));
      }
      // if we get here, the redirect already has been done; clear
      // the meta data entry; we don't need it any longer is the client
      // info object will be cached too
      session.setMetaData(BROWSER_WAS_POLLED_KEY, (Boolean) null);
    }
    return new MobycketWebClientInfo(this);
  }

  /**
   * Override this method if you want to use a custom page for gathering the
   * client's browser information.<br/>
   * The easiest way is just to extend {@link BrowserInfoPage} and provide your
   * own markup file
   * 
   * @param url
   *          the url to redirect to when the browser info is handled
   * @return the {@link WebPage} which should be used while gathering browser
   *         info
   */
  protected WebPage newBrowserInfoPage(String url) {
    return new BrowserInfoPage(url);
  }

  /**
   * If it's an ajax request we always redirect.
   * 
   * @see org.apache.wicket.RequestCycle#isRedirect()
   */
  @Override
  public final boolean isRedirect() {
    if (getWebRequest().isAjax()) {
      return true;
    } else {
      return super.isRedirect();
    }
  }

  /**
       * 
       */
  void unset() {
    set(null);
  }

}
