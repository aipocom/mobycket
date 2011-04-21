/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://aipostyle.com/
 */

package org.apache.wicket.protocol.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.request.ClientInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see org.apache.wicket.protocol.http.WebClientInfo
 */
public class MobycketWebClientInfo extends ClientInfo {

  private static final long serialVersionUID = 1L;

  /** log. */
  private static final Logger log = LoggerFactory
    .getLogger(WebClientInfo.class);

  /**
   * The user agent string from the User-Agent header, app. Theoretically, this
   * might differ from {@link ClientProperties#isJavaEnabled()} property, which
   * is not set until an actual reply from a browser (e.g. using
   * {@link org.apache.wicket.markup.html.pages.BrowserInfoPage} is set.
   */
  private final String userAgent;

  /** Client properties object. */
  private final ClientProperties properties = new ClientProperties();

  /**
   * Construct.
   * 
   * @param requestCycle
   *          the request cycle
   */
  public MobycketWebClientInfo(MobycketWebRequestCycle requestCycle) {
    super();
    HttpServletRequest httpServletRequest =
      requestCycle.getWebRequest().getHttpServletRequest();
    userAgent = httpServletRequest.getHeader("User-Agent");
    properties.setRemoteAddress(getRemoteAddr(requestCycle));
    init();
  }

  /**
   * Construct.
   * 
   * @param requestCycle
   *          the request cycle
   * @param userAgent
   *          the user agent
   */
  public MobycketWebClientInfo(MobycketWebRequestCycle requestCycle,
      String userAgent) {
    super();
    this.userAgent = userAgent;
    requestCycle.getWebRequest().getHttpServletRequest();
    properties.setRemoteAddress(getRemoteAddr(requestCycle));
    init();
  }

  /**
   * Gets the client properties object.
   * 
   * @return the client properties object
   */
  public final ClientProperties getProperties() {
    return properties;
  }

  /**
   * Gets the user agent string.
   * 
   * @return the user agent string
   */
  public final String getUserAgent() {
    return userAgent;
  }

  /**
   * When using ProxyPass, requestCycle().getHttpServletRequest().
   * getRemoteAddr() returns the IP of the machine forwarding the request. In
   * order to maintain the clients ip address, the server places it in the <a
   * href="http://httpd.apache.org/docs/2.2/mod/mod_proxy.html#x-headers">X-
   * Forwarded-For</a> Header.
   * 
   * @author Ryan Gravener (rgravener)
   * 
   * @param requestCycle
   *          the request cycle
   * @return remoteAddr IP address of the client, using the X-Forwarded-For
   *         header and defaulting to: getHttpServletRequest().getRemoteAddr()
   * 
   */
  protected String getRemoteAddr(MobycketWebRequestCycle requestCycle) {
    HttpServletRequest httpServletReq =
      requestCycle.getWebRequest().getHttpServletRequest();
    String remoteAddr = httpServletReq.getHeader("X-Forwarded-For");
    if (remoteAddr == null) {
      remoteAddr = httpServletReq.getRemoteAddr();
    } else {
      if (remoteAddr.indexOf(",") != -1) {
        // sometimes the header is of form client ip,proxy 1 ip,proxy 2
        // ip,...,proxy n ip,
        // we just want the client
        remoteAddr = remoteAddr.split(",")[0].trim();
      }
    }
    return remoteAddr;
  }

  /**
   * Initialize the client properties object
   */
  private final void init() {
    String userAgent =
      (getUserAgent() != null) ? getUserAgent().toLowerCase() : "";

    boolean browserChrome = userAgent.indexOf("chrome") != -1;
    boolean browserOpera = userAgent.indexOf("opera") != -1;
    boolean browserKonqueror = userAgent.indexOf("konqueror") != -1;

    // Note deceptive user agent fields:
    // - Konqueror and Chrome UA fields contain "like Gecko"
    // - Opera UA field typically contains "MSIE"
    // - Chrome UA field contains "Safari"
    boolean deceptiveUserAgent =
      browserOpera || browserKonqueror || browserChrome;

    boolean browserSafari =
      !deceptiveUserAgent && userAgent.indexOf("safari") != -1;

    // -Safari UA fields contain "like Gecko"
    deceptiveUserAgent = deceptiveUserAgent || browserSafari;

    boolean browserMozilla =
      !deceptiveUserAgent && userAgent.indexOf("gecko") != -1;
    boolean browserFireFox = userAgent.indexOf("firefox") != -1;
    boolean browserInternetExplorer =
      !deceptiveUserAgent && userAgent.indexOf("msie") != -1;

    int majorVersion = -1, minorVersion = -1;

    // Store browser information.
    if (browserOpera) {
      properties.setBrowserOpera(true);
    } else if (browserKonqueror) {
      properties.setBrowserKonqueror(true);
    } else if (browserSafari) {
      properties.setBrowserSafari(true);
    } else if (browserChrome) {
      properties.setBrowserChrome(true);
    } else if (browserMozilla) {
      properties.setBrowserMozilla(true);
      if (browserFireFox) {
        properties.setBrowserMozillaFirefox(true);
      }
    } else if (browserInternetExplorer) {
      properties.setBrowserInternetExplorer(true);
      Matcher matcher = Pattern.compile("msie (\\d+)").matcher(userAgent);
      if (matcher.find()) {
        majorVersion = Integer.parseInt(matcher.group(1));
      }
    }

    if (majorVersion != -1) {
      properties.setBrowserVersionMajor(majorVersion);
    }

    if (minorVersion != -1) {
      properties.setBrowserVersionMinor(minorVersion);
    }

    // Set quirk flags.
    if (browserInternetExplorer) {
      properties.setProprietaryIECssExpressionsSupported(true);
      properties.setQuirkCssPositioningOneSideOnly(true);
      properties.setQuirkIERepaint(true);
      properties.setQuirkIESelectZIndex(true);
      properties.setQuirkIETextareaNewlineObliteration(true);
      properties.setQuirkIESelectPercentWidth(true);
      properties.setQuirkIESelectListDomUpdate(true);
      properties.setQuirkIETablePercentWidthScrollbarError(true);
      properties.setQuirkCssBackgroundAttachmentUseFixed(true);
      properties.setQuirkCssBorderCollapseInside(true);
      properties.setQuirkCssBorderCollapseFor0Padding(true);
      if (majorVersion < 7) {
        properties.setProprietaryIEPngAlphaFilterRequired(true);
      }
    }
    if (browserMozilla) {
      properties.setQuirkMozillaTextInputRepaint(true);
      properties.setQuirkMozillaPerformanceLargeDomRemove(true);
    }

    if (log.isDebugEnabled()) {
      log.debug("determined user agent: " + properties);
    }
  }
}