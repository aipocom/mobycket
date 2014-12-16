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

import com.aipo.mobycket.wicket.util.resource.locator.MultiDeviceDirname;

/**
 * キャリア
 * 
 */
public enum Carrier {

  /**
   * PC 端末
   */
  OTHER {

    @Override
    public String getDummyUserAgent() {
      return "Mozilla/*.* (****)";
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.PC.get();
    }
  },

  /**
   * DoCoMo 端末
   */
  DOCOMO {

    @Override
    public String getDummyUserAgent() {
      return "DoCoMo/*.* ****(****) (****)";
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.MOBILE.get();
    }
  },

  /**
   * AU 端末
   */
  AU {

    @Override
    public String getDummyUserAgent() {
      return "KDDI-**** UP.Browser/*.* (GUI) ****";
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.MOBILE.get();
    }
  },

  /**
   * Softbank 端末
   */
  SOFTBANK {

    @Override
    public String getDummyUserAgent() {
      return "SoftBank/*.*/****/****";
    }

    @Override
    public String getDefaultDirname() {
      return MultiDeviceDirname.MOBILE.get();
    }
  };

  public abstract String getDummyUserAgent();

  public abstract String getDefaultDirname();
}
