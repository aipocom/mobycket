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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.IAjaxLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationBehavior;
import org.apache.wicket.markup.html.navigation.paging.IPageable;

/**
 * 
 */
public class AjaxCursorPagingNavigationBehavior extends
		AjaxPagingNavigationBehavior {

	private static final long serialVersionUID = -9013146737659024442L;

	private final IAjaxLink owner;

	/**
	 * @param owner
	 * @param pageable
	 * @param event
	 */
	public AjaxCursorPagingNavigationBehavior(IAjaxLink owner,
			IPageable pageable, String event) {
		super(owner, pageable, event);
		this.owner = owner;
	}

	@Override
	protected void onEvent(AjaxRequestTarget target) {
		// handle the event
		owner.onClick(target);

		// find the PagingNavigator parent of this link
		AjaxCursorPagingNavigator navigator = ((Component) owner)
				.findParent(AjaxCursorPagingNavigator.class);

		// if this is embedded inside a navigator
		if (navigator != null) {
			// tell the PagingNavigator to update the IPageable
			navigator.onAjaxEvent(target);
		}
	}

}
