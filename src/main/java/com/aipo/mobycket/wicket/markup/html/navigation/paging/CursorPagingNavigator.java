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
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationIncrementLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class CursorPagingNavigator extends Panel {

	private static final long serialVersionUID = 5543166798199841199L;

	public static final String NAVIGATION_ID = "navigation";
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(CursorPagingNavigator.class);

	private final IPageable pageable;

	public CursorPagingNavigator(final String id, final IPageable pageable) {
		super(id);
		this.pageable = pageable;
	}

	protected AbstractLink newPagingNavigationIncrementLink(String id,
			IPageable pageable, int increment) {
		return new PagingNavigationIncrementLink<Void>(id, pageable, increment);
	}

	public final IPageable getPageable() {
		return pageable;
	}

	@Override
	protected void onBeforeRender() {
		if (get("prev") == null) {
			// Add additional page links
			// add(newPagingNavigationLink("first", pageable, 0).add(
			// new TitleAppender("PagingNavigator.first")));
			add(newPagingNavigationIncrementLink("prev", pageable, -1).add(
					new TitleAppender("CursorPagingNavigator.previous")));
			add(newPagingNavigationIncrementLink("next", pageable, 1).add(
					new TitleAppender("CursorPagingNavigator.next")));
			// add(newPagingNavigationLink("last", pageable, -1).add(
			// new TitleAppender("PagingNavigator.last")));
		}
		super.onBeforeRender();
	}

	private final class TitleAppender extends AbstractBehavior {
		private static final long serialVersionUID = 1L;

		private final String resourceKey;

		/**
		 * Constructor
		 * 
		 * @param resourceKey
		 *            resource key of the message
		 */
		public TitleAppender(String resourceKey) {
			this.resourceKey = resourceKey;
		}

		/** {@inheritDoc} */
		@Override
		public void onComponentTag(Component component, ComponentTag tag) {
			tag.put("title", CursorPagingNavigator.this.getString(resourceKey));
		}
	}
}
