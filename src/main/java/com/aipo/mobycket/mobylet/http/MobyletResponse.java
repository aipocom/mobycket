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
