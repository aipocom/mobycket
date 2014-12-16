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
