/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page;

import java.util.LinkedList;
import java.util.List;

import com.tirion.common.ListAware;

/**
 * List of {@link Page} objects.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Pages implements ListAware<Page> {

	private final List<Page> pages;

	public Pages() {
		super();
		pages = new LinkedList<Page>();
	}

	@Override
	public void append(Page page) {
		pages.add(page);
	}
	
	@Override
	public void append(List<Page> values) {
		pages.addAll(values);
	}

	@Override
	public List<Page> get() {
		return pages;
	}
	
	@Override
	public int getCount() {
		return pages.size();
	}

	@Override
	public boolean isEmpty() {
		return pages.isEmpty();
	}

	@Override
	public Page get(int index) {
		return pages.get(index);
	}
	
	@Override
	public boolean has(int index) {
		return index < pages.size();
	}
}
