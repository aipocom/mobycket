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
