/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
 */

package com.aipo.mobycket.wicket.util.resource.locator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aipo.mobycket.wicket.protocol.http.WebContext;
import com.aipo.mobycket.wicket.protocol.http.WebContextLocator;

public class MultiDeviceResourceStreamLocator extends ResourceStreamLocator {

  private static final Logger logger = LoggerFactory
    .getLogger(MultiDeviceResourceStreamLocator.class);

  public static final String DEFAULT_PC_DIR = MultiDeviceDirname.PC.get();

  private final Map<String, String> pagesPath;

  public MultiDeviceResourceStreamLocator(String pagesPath) {
    this.pagesPath = new HashMap<String, String>(4);
    // wicket base
    this.pagesPath.put("org/apache/wicket/markup/html", "templates/wicket/");
    // wicket ajex
    this.pagesPath.put(
      "org/apache/wicket/ajax/markup/html",
      "templates/wicketajax/");
    // mobycket
    this.pagesPath.put(
      "com/aipo/mobycket/wicket/markup/html",
      "templates/mobycket/");
    // application
    this.pagesPath.put(pagesPath, "html/");
  }

  @Override
  public IResourceStream locate(Class<?> clazz, String path) {
    String dirname = getDeviceDirname();
    Iterator<Entry<String, String>> iterator = pagesPath.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> next = iterator.next();
      String nextPath = next.getKey();
      String nextDir = next.getValue();
      if (path.indexOf(nextPath, 0) != -1) {
        String shortPath = path.substring(nextPath.length() + 1);

        if (!DEFAULT_PC_DIR.equals(dirname)) {
          String devicePath =
            new StringBuilder(nextDir).append(dirname).append("/").append(
              shortPath).toString();
          IResourceStream located = super.locate(clazz, devicePath);
          if (located != null) {
            return located;
          }
        }

        String devicePath =
          new StringBuilder(nextDir).append(DEFAULT_PC_DIR).append("/").append(
            shortPath).toString();
        IResourceStream located = super.locate(clazz, devicePath);
        if (located != null) {
          return located;
        }
      }
    }

    return super.locate(clazz, path);
  }

  protected String getDeviceDirname() {
    WebContext webContext = WebContextLocator.get();
    if (webContext == null) {
      logger.info("webContext is not initialized.");
      return DEFAULT_PC_DIR;
    }
    return webContext.getDirname();
  }
}
