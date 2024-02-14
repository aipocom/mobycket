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
package com.aipo.mobycket.wicket.markup.html.form;

import org.apache.wicket.Component;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.form.Form;

/**
 * 
 */
public abstract class IndicatingAjaxButton extends AjaxButton implements
    IAjaxIndicatorAware {

  private static final long serialVersionUID = -2238212593688080513L;

  private AjaxIndicatorAppender appender;

  private final String indicaterUrl;

  private boolean isIndicatingCenter = false;

  private String indicatingMessage;

  public IndicatingAjaxButton(String id, String indicaterUrl) {
    super(id);
    add(appender = getWicketAjaxIndicatorAppender());
    this.indicaterUrl = indicaterUrl;
  }

  public IndicatingAjaxButton(String id, Form<?> form, String indicaterUrl) {
    super(id, form);
    add(appender = getWicketAjaxIndicatorAppender());
    this.indicaterUrl = indicaterUrl;
  }

  public IndicatingAjaxButton setIndicatingCenter(boolean isIndicatingCenter) {
    this.isIndicatingCenter = isIndicatingCenter;
    return this;
  }

  public IndicatingAjaxButton setIndicatingMessage(String indicatingMessage) {
    this.indicatingMessage = indicatingMessage;
    return this;
  }

  public AjaxIndicatorAppender getWicketAjaxIndicatorAppender() {
    return new AjaxIndicatorAppender() {

      private static final long serialVersionUID = 2238212593688080513L;

      @Override
      public void onRendered(Component component) {
        final Response r = component.getResponse();

        r.write("<span style=\"display:none;");
        if (isIndicatingCenter) {
          r.write("text-align:center;");
        }
        r.write("\" class=\"");
        r.write(getSpanClass());
        r.write("\" ");
        r.write("id=\"");
        r.write(getMarkupId());
        r.write("\">");
        if (isIndicatingCenter) {
          r.write("<br/>");
        }
        r.write("<img src=\"");
        r.write(getIndicatorUrl());
        r.write("\" alt=\"\" align=\"absmiddle\"/>");
        if (indicatingMessage != null) {
          r.write(indicatingMessage);
        }
        r.write("</span>");
      }

      @Override
      protected CharSequence getIndicatorUrl() {
        return indicaterUrl;
      }
    };
  }

  @Override
  public String getAjaxIndicatorMarkupId() {
    return appender.getMarkupId();
  }

}
