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
