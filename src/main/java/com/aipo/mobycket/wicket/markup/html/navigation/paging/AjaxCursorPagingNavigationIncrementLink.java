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

package com.aipo.mobycket.wicket.markup.html.navigation.paging;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationIncrementLink;
import org.apache.wicket.markup.html.navigation.paging.IPageable;

/**
 * 
 */
public class AjaxCursorPagingNavigationIncrementLink extends
		AjaxPagingNavigationIncrementLink {

	private static final long serialVersionUID = -3323545955823389646L;

	/**
	 * @param id
	 * @param pageable
	 * @param increment
	 */
	public AjaxCursorPagingNavigationIncrementLink(String id,
			IPageable pageable, int increment) {
		super(id, pageable, increment);
		add(new AjaxCursorPagingNavigationBehavior(this, pageable, "onclick"));

		setOutputMarkupId(true);

	}

}
