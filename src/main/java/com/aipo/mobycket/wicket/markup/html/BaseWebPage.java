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

    /*-
    // title
    String titleStr = getPageTitle();
    addOrReplace(new Label("title", titleStr == null ? "" : titleStr));

    // description
    String decriptionStr = getDescription();
    Label description = new Label("description", "");
    description.add(new SimpleAttributeModifier(
      "content",
      decriptionStr == null ? "" : decriptionStr));
    addOrReplace(description);

    // keywords
    String keywordsStr = getKeywords();
    Label keywords = new Label("keywords", "");
    keywords.add(new SimpleAttributeModifier("content", keywordsStr == null
      ? ""
      : keywordsStr));
    addOrReplace(keywords);

    super.onBeforeRender();
     */
    super.onBeforeRender();
  }

  /*-
  protected abstract String getPageTitle();

  protected abstract String getDescription();

  protected abstract String getKeywords();
   */

}
