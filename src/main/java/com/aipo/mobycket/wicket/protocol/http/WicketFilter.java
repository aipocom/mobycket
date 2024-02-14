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
