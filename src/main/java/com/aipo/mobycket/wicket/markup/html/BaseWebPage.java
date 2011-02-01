/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
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
