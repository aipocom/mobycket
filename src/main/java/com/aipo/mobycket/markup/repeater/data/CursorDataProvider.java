/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
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

package com.aipo.mobycket.markup.repeater.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public abstract class CursorDataProvider<T> implements ICursorDataProvider<T> {

  private static final long serialVersionUID = 153252588794405919L;

  private final Map<Integer, String> cursors = new HashMap<Integer, String>();

  public CursorDataProvider() {
    cursors.put(0, null);
  }

  @Override
  public String getNextToken(int first) {
    return cursors.get(Integer.valueOf(first));
  }

  /**
   * @param token
   */
  @Override
  public void setNextToken(int first, String nextToken) {
    cursors.put(first + 1, nextToken);
  }
}
