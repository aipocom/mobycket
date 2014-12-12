/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
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
