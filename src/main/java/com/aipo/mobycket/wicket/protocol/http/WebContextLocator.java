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
