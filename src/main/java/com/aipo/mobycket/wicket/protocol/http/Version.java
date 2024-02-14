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

/**
 * ブラウザバージョン
 * 
 */
public class Version {

  public static boolean isAndroid2(String userAgent) {
    String[] pattern = new String[] { "Android 2" };
    for (String string : pattern) {
      boolean contains = userAgent.contains(string);
      if (contains) {
        return true;
      }
    }
    return false;
  }

  public static boolean isAndroid4(String userAgent) {
    String[] pattern = new String[] { "Android 4", "Android-4" };
    for (String string : pattern) {
      boolean contains = userAgent.contains(string);
      if (contains) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param userAgent
   * @return
   */
  public static int getVersion(String userAgent) {
    if (isAndroid2(userAgent)) {
      return 2;
    }
    if (isAndroid4(userAgent)) {
      return 4;
    }
    return 0;
  }
}
