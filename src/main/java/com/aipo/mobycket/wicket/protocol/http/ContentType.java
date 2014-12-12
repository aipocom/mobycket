/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
 */

package com.aipo.mobycket.wicket.protocol.http;

/**
 * 
 */
public enum ContentType {

  XHTML {
    @Override
    public String get() {
      return "application/xhtml+xml";
    }
  },

  HTML {
    @Override
    public String get() {
      return "text/html";
    }
  },

  JSON {
    @Override
    public String get() {
      return "application/json";
    }
  };

  public abstract String get();
}
