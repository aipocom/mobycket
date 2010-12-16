/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2010 Aimluck,Inc.
 * http://aipostyle.com/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.aipo.mobycket.wicket.protocol.http;

import org.apache.wicket.settings.IResourceSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

import com.aipo.mobycket.wicket.markup.MultiDeviceMarkupCache;
import com.aipo.mobycket.wicket.util.resource.locator.MultiDeviceResourceStreamLocator;

/**
 * 
 */
public abstract class WebApplication extends
    org.apache.wicket.protocol.http.WebApplication {

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
  }
}
