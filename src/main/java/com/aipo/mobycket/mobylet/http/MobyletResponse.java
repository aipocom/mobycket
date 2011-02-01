/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://aipostyle.com/
 */

package com.aipo.mobycket.mobylet.http;

import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.dialect.MobyletDialect;

import com.aipo.mobycket.wicket.protocol.http.Carrier;

/**
 * 
 */
public class MobyletResponse extends org.mobylet.core.http.MobyletResponse {

  private final MobyletDialect dialect;

  /**
   * @param response
   * @param dialect
   */
  public MobyletResponse(HttpServletResponse response, MobyletDialect dialect) {
    super(response, dialect);
    this.dialect = dialect;
  }

  @Override
  public String encodeURL(String url) {
    if (dialect.getCarrier().equals(Carrier.DOCOMO)) {
      return super.encodeURL(url);
    } else {
      String encodeURL = super.encodeURL(url);
      if (encodeURL.indexOf(';') >= 0) {
        return REG_JSESSIONID.matcher(encodeURL).replaceFirst("");
      } else {
        return encodeURL;
      }
    }
  }

  @Override
  public String encodeRedirectURL(String url) {
    if (dialect.getCarrier().equals(Carrier.DOCOMO)) {
      return super.encodeRedirectURL(url);
    } else {
      String encodeURL = super.encodeRedirectURL(url);
      if (encodeURL.indexOf(';') >= 0) {
        return REG_JSESSIONID.matcher(encodeURL).replaceFirst("");
      } else {
        return encodeURL;
      }
    }
  }
}
