/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
 */

package com.aipo.mobycket.wicket.protocol.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link WebContext} ロケータです。
 * 
 * @author nbeppu
 * 
 */
public final class WebContextLocator {

  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory
    .getLogger(WebContextLocator.class.getName());

  private static ThreadLocal<WebContext> webContexts =
    new ThreadLocal<WebContext>();

  /**
   * {@link WebContext} を取得します。
   * 
   * @return {@link WebContext}
   */
  public static WebContext get() {
    return webContexts.get();
  }

  /**
   * {@link WebContext} を設定します。
   * 
   * @param webContext
   */
  public static void set(WebContext webContext) {
    webContexts.set(webContext);
  }

  private WebContextLocator() {
  }
}
