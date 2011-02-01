/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
 */

package com.aipo.mobycket.wicket.protocol.http;

import org.apache.wicket.authentication.AuthenticatedWebApplication;
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

}
