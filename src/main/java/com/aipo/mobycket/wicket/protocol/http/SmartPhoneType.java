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
public enum SmartPhoneType {

  /**
   * Windows Mobile 端末
   */
  WM {

    @Override
    public String getDummyUserAgent() {
      return null;
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.TOUCH.get();
    }
  },

  /**
   * iPhone 端末
   */
  IPHONE {

    @Override
    public String getDummyUserAgent() {
      return null;
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.TOUCH.get();
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
      return MultiDeviceDirname.TOUCH.get();
    }
  };

  public abstract String getDummyUserAgent();

  public abstract String getDefaultDirname();
}
