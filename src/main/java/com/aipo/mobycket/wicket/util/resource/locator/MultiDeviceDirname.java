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
package com.aipo.mobycket.wicket.util.resource.locator;

/**
 * 
 */
public enum MultiDeviceDirname {

  PC {
    @Override
    public String get() {
      return this.toString().toLowerCase();
    }
  },

  MOBILE {
    @Override
    public String get() {
      return this.toString().toLowerCase();
    }
  },

  TOUCH {
    @Override
    public String get() {
      return this.toString().toLowerCase();
    }
  },

  TABLET {
    @Override
    public String get() {
      return this.toString().toLowerCase();
    }
  };

  public abstract String get();
}
