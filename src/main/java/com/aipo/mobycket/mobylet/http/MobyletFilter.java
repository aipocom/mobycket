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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    String path = ((HttpServletRequest) request).getServletPath();

    if (excludeFromFilter(path)) {
      chain.doFilter(request, response);
    } else {
      super.doFilter(request, response, chain);
    }

  }

  /**
   * 静的ファイルはフィルター除外
   *
   * @param path
   * @return
   */
  private boolean excludeFromFilter(String path) {
    try {
      String fileExtension = "";

      List<String> excludeFileExtension =
        Arrays
          .asList(
            ".htc",
            ".ico",
            ".js",
            ".png",
            ".gif",
            ".svg",
            ".jpg",
            ".css",
            ".eot",
            ".ttf",
            ".woff",
            ".woff2",
            ".oft");

      if (path != null && path.contains(".")) {
        fileExtension = path.substring(path.lastIndexOf("."));
      }

      return excludeFileExtension.contains(fileExtension);
    } catch (Exception e) {
      // ignore
    }
    return false;
  }
}
