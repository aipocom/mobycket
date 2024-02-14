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
package com.aipo.mobycket.wicket.markup.html.calendar;

import org.apache.wicket.Component;
import org.apache.wicket.Response;
import org.apache.wicket.util.string.Strings;

/**
 *
 */
public class DatePicker extends
    org.apache.wicket.extensions.yui.calendar.DatePicker {

  private static final long serialVersionUID = -4335499704867623972L;

  @Override
  public void onRendered(Component component) {
    Response response = component.getResponse();
    response.write("\n<span class=\"yui-skin-lucoz\">&nbsp;<span style=\"");
    if (renderOnLoad()) {
      response.write("display:block;");
    } else {
      response.write("display:none;");
      response.write("position:absolute;");
    }
    response.write("z-index: 99999;\" id=\"");
    response.write(getEscapedComponentMarkupId());
    response.write("Dp\"></span><img style=\"");
    response.write(getIconStyle());
    response.write("\" id=\"");
    response.write(getIconId());
    response.write("\" src=\"");
    CharSequence iconUrl = getIconUrl();
    response.write(Strings.escapeMarkup(iconUrl != null
      ? iconUrl.toString()
      : ""));
    response.write("\" alt=\"");
    CharSequence alt = getIconAltText();
    response.write(Strings.escapeMarkup((alt != null) ? alt.toString() : ""));
    response.write("\" title=\"");
    CharSequence title = getIconTitle();
    response.write(Strings
      .escapeMarkup((title != null) ? title.toString() : ""));
    response.write("\"/>");

    if (renderOnLoad()) {
      response.write("<br style=\"clear:left;\"/>");
    }
    response.write("</span>");
  }
}
