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
package com.aipo.mobycket.wicket.markup.html;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import com.aipo.mobycket.wicket.protocol.http.WebContext;
import com.aipo.mobycket.wicket.protocol.http.WebContextLocator;

/**
 * 
 */
public abstract class BaseWebPage extends WebPage {

  public BaseWebPage() {
    this(null);
  }

  public BaseWebPage(IModel<?> model) {
    super(model);
  }

  @Override
  protected void onBeforeRender() {

    WebContext webContext = WebContextLocator.get();

    // contentType
    Label contentType = new Label("contentType", "");
    contentType.add(new SimpleAttributeModifier("content", new StringBuilder(
      webContext.getContentType()).append("; charset=").append(
      webContext.getContentCharsetName())));
    addOrReplace(contentType);

    super.onBeforeRender();
  }

}
