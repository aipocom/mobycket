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

package com.aipo.mobycket.wicket.markup.html.navigation.paging;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class AjaxCursorPagingNavigator extends CursorPagingNavigator {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(AjaxCursorPagingNavigator.class);

	private static final long serialVersionUID = 2167911171813577721L;
	private final IPageable pageable;

	public AjaxCursorPagingNavigator(final String id, final IPageable pageable) {
		super(id, pageable);
		this.pageable = pageable;
		setOutputMarkupId(true);
	}

	@Override
	protected Link<?> newPagingNavigationIncrementLink(String id,
			IPageable pageable, int increment) {
		return new AjaxCursorPagingNavigationIncrementLink(id, pageable,
				increment);
	}

	protected void onAjaxEvent(AjaxRequestTarget target) {
		// update the container (parent) of the pageable, this assumes that
		// the pageable is a component, and that it is a child of a web
		// markup container.

		Component container = ((Component) pageable);
		// no need for a nullcheck as there is bound to be a non-repeater
		// somewhere higher in the hierarchy
		while (container instanceof AbstractRepeater) {
			container = container.getParent();
		}
		target.addComponent(container);

		// in case the navigator is not contained by the container, we have
		// to add it to the response
		if (((MarkupContainer) container).contains(this, true) == false) {
			target.addComponent(this);
		}
	}
}
