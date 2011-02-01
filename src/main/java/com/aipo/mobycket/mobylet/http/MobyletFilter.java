/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://aipostyle.com/
 */

package com.aipo.mobycket.mobylet.http;

import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.dialect.MobyletDialect;

/**
 * 
 */
public class MobyletFilter extends org.mobylet.core.http.MobyletFilter {

  @Override
  protected MobyletResponse wrapResponse(HttpServletResponse response,
      MobyletDialect dialect) {
    return new MobyletResponse(response, dialect);
  }
}
