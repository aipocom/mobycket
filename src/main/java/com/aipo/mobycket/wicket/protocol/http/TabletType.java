/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
 */

package com.aipo.mobycket.wicket.protocol.http;

import com.aipo.mobycket.wicket.util.resource.locator.MultiDeviceDirname;

/**
 * 
 */
public enum TabletType {

  /**
   * iPhone 端末
   */
  IPAD {

    @Override
    public String getDummyUserAgent() {
      return null;
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.TABLET.get();
    }
  },

  /**
   * Android 端末
   */
  ANDROID {

    @Override
    public String getDummyUserAgent() {
      return null;
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.TABLET.get();
    }
  };

  public abstract String getDummyUserAgent();

  public abstract String getDefaultDirname();
}
