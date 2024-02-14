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

import org.apache.wicket.Application;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.MarkupCache;

/**
 * 
 */
public class MultiDeviceMarkupCache extends MarkupCache {

  /** The markup cache key provider used by MarkupCache */
  private IMarkupCacheKeyProvider markupCacheKeyProvider;

  /**
   * @param application
   */
  public MultiDeviceMarkupCache(Application application) {
    super(application);
  }

  @Override
  public IMarkupCacheKeyProvider getMarkupCacheKeyProvider(
      final MarkupContainer container) {
    if (container instanceof IMarkupCacheKeyProvider) {
      return (IMarkupCacheKeyProvider) container;
    }

    if (markupCacheKeyProvider == null) {
      markupCacheKeyProvider = new MultiDeviceMarkupCacheKeyProvider();
    }
    return markupCacheKeyProvider;
  }
}
