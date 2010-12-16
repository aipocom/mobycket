/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2010 Aimluck,Inc.
 * http://aipostyle.com/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
