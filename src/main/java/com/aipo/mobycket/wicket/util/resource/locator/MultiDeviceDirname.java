/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
 */

package com.aipo.mobycket.wicket.util.resource.locator;

/**
 * 
 */
public enum MultiDeviceDirname {

  PC {
    @Override
    public String get() {
      return this.toString().toLowerCase();
    }
  },

  MOBILE {
    @Override
    public String get() {
      return this.toString().toLowerCase();
    }
  },

  TOUCH {
    @Override
    public String get() {
      return this.toString().toLowerCase();
    }
  },

  TABLET {
    @Override
    public String get() {
      return this.toString().toLowerCase();
    }
  };

  public abstract String get();
}
