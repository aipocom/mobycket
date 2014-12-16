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
