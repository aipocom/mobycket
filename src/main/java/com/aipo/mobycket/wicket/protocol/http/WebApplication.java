/*
 * Copyright 2004-2014 Aimluck,Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aipo.mobycket.wicket.protocol.http;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.settings.IResourceSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

import com.aipo.mobycket.wicket.markup.MultiDeviceMarkupCache;
import com.aipo.mobycket.wicket.util.resource.locator.MultiDeviceResourceStreamLocator;

/**
 * 
 */
public abstract class WebApplication extends AuthenticatedWebApplication {

  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory
    .getLogger(WebApplication.class.getName());

  public static final String DEFAULT_HTML_FOLDER = "WEB-INF";

  @Override
  protected void init() {
    super.init();
    getMarkupSettings().setStripWicketTags(true);
    getMarkupSettings().setMarkupCache(new MultiDeviceMarkupCache(this));
    String rootPackageNage = getHomePage().getPackage().getName();

    new AnnotatedMountScanner().scanPackage(rootPackageNage).mount(this);
    IResourceSettings resourceSettings = getResourceSettings();
    resourceSettings.addResourceFolder(DEFAULT_HTML_FOLDER);
    resourceSettings
      .setResourceStreamLocator(new MultiDeviceResourceStreamLocator(
        rootPackageNage.replace('.', '/')));
    // getRequestCycleSettings().setGatherExtendedBrowserInfo(true);

  }

  @Override
  protected WebResponse newWebResponse(HttpServletResponse servletResponse) {
    return new WebResponse(servletResponse) {
      @Override
      public void sendRedirect(String url) throws IOException {
        if (url.startsWith("http")) {
          getHttpServletResponse().sendRedirect(url);
          return;
        }
        HttpServletRequest httpServletRequest =
          ((WebRequest) RequestCycle.get().getRequest())
            .getHttpServletRequest();
        String reqUrl = httpServletRequest.getRequestURI();
        String absUrl = RequestUtils.toAbsolutePath(reqUrl, url);
        getHttpServletResponse().sendRedirect(getRequestBaseUrl() + absUrl);
      }
    };
  }

  protected String getRequestBaseUrl() {
    HttpServletRequest request =
      ((WebRequest) RequestCycle.get().getRequest()).getHttpServletRequest();
    String scheme = request.getScheme();
    int port = request.getServerPort();
    String serverName = request.getServerName();
    StringBuilder b = new StringBuilder(scheme);
    b.append("://").append(serverName);
    if (!("http".equals(scheme) && port == 80)
      && !("https".equals(scheme) && port == 443)) {
      b.append(":").append(port);
    }

    return b.toString();

  }
}
