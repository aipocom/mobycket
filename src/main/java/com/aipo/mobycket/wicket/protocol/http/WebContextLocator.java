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
