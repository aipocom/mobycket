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
package com.aipo.mobycket.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 */
public final class DigestUtil {

  public static String passwordEncrypt(String input, String seed) {
    return sha512(new StringBuilder(input).append(md5(seed)).toString());
  }

  /**
   * 
   * @param input
   * @return
   * @throws NullPointerException
   */
  public static String sha512(String input) throws NullPointerException {
    if (input == null) {
      throw new NullPointerException("The input parameter must not be null.");
    }
    return encrypt(input, "SHA-512");
  }

  /**
   * 
   * @param input
   * @return
   * @throws NullPointerException
   */
  public static String sha1(String input) throws NullPointerException {
    if (input == null) {
      throw new NullPointerException("The input parameter must not be null.");
    }
    return encrypt(input, "SHA-1");
  }

  /**
   * 
   * @param input
   * @return
   * @throws NullPointerException
   */
  public static String md5(String input) throws NullPointerException {
    if (input == null) {
      throw new NullPointerException("The input parameter must not be null.");
    }
    return encrypt(input, "MD5");
  }

  /**
   * 
   * @param input
   * @param type
   * @return
   * @throws NullPointerException
   */
  public static String encrypt(String input, String type)
      throws NullPointerException {
    if (input == null) {
      throw new NullPointerException("The input parameter must not be null.");
    }
    if (type == null) {
      throw new NullPointerException("The type parameter must not be null.");
    }

    try {
      MessageDigest md = MessageDigest.getInstance(type);
      md.update(input.getBytes("MS932"));
      return toHexString(md.digest());
    } catch (NoSuchAlgorithmException e) {
      // ignore
    } catch (UnsupportedEncodingException e) {
      // ignore
    }
    return null;
  }

  /**
   * 
   * @param b
   * @return
   */
  private static String toHexString(byte[] b) {
    StringBuilder hexString = new StringBuilder();
    String plainText = null;
    for (int i = 0; i < b.length; i++) {
      plainText = Integer.toHexString(0xFF & b[i]);
      if (plainText.length() < 2) {
        plainText = "0" + plainText;
      }
      hexString.append(plainText);
    }
    return hexString.toString();
  }

  private DigestUtil() {
  }
}
