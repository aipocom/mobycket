/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
 */

package com.aipo.mobycket.wicket.protocol.http;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class WicketFilter extends org.apache.wicket.protocol.http.WicketFilter {

  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory
    .getLogger(WicketFilter.class.getName());

  private FilterConfig filterConfig;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
    super.init(filterConfig);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    try {
      WebContextLocator.set(new WebContext(
        (HttpServletRequest) request,
        (HttpServletResponse) response,
        filterConfig.getServletContext()));
      super.doFilter(request, response, chain);
    } finally {
      WebContextLocator.set(null);
    }
  }

  protected WebContext createWebContext(HttpServletRequest request,
      HttpServletResponse response, ServletContext servletContext) {
    return new WebContext(request, response, servletContext);
  }
}
