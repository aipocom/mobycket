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
