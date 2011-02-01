/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
 */

package com.aipo.mobycket.wicket.protocol.http;

import com.aipo.mobycket.wicket.util.resource.locator.MultiDeviceDirname;

/**
 * キャリア
 * 
 */
public enum Carrier {

  /**
   * PC 端末
   */
  OTHER {

    @Override
    public String getDummyUserAgent() {
      return "Mozilla/*.* (****)";
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.PC.get();
    }
  },

  /**
   * DoCoMo 端末
   */
  DOCOMO {

    @Override
    public String getDummyUserAgent() {
      return "DoCoMo/*.* ****(****) (****)";
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.MOBILE.get();
    }
  },

  /**
   * AU 端末
   */
  AU {

    @Override
    public String getDummyUserAgent() {
      return "KDDI-**** UP.Browser/*.* (GUI) ****";
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.MOBILE.get();
    }
  },

  /**
   * Softbank 端末
   */
  SOFTBANK {

    @Override
    public String getDummyUserAgent() {
      return "SoftBank/*.*/****/****";
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.MOBILE.get();
    }
  };

  public abstract String getDummyUserAgent();

  public abstract String getDefaultDirname();
}
