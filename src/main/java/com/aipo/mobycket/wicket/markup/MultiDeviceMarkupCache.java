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
