/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
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
