/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
 */

package com.aipo.mobycket.wicket.markup;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.DefaultMarkupCacheKeyProvider;

import com.aipo.mobycket.wicket.protocol.http.WebContext;
import com.aipo.mobycket.wicket.protocol.http.WebContextLocator;

/**
 * 
 */
public class MultiDeviceMarkupCacheKeyProvider extends
    DefaultMarkupCacheKeyProvider {

  /**
   * @param container
   * @param containerClass
   * @return
   */
  @Override
  public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
    String key = super.getCacheKey(container, containerClass);
    WebContext webContext = WebContextLocator.get();
    String dirname = webContext.getDirname();
    return new StringBuilder(key).append("_").append(dirname).toString();
  }
}
