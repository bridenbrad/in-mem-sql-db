/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.source;

import java.util.ArrayList;
import java.util.List;

import com.tirion.db.store.page.Page;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimplePageSource implements PageSource {

	private final List<Page> pages;
	private int index;
	
	public SimplePageSource() {
		super();
		pages = new ArrayList<Page>();
		index = 0;
	}

	public void append(Page page) {
		pages.add(page);
	}
	
	@Override
	public boolean hasNext() {
		return index < pages.size();
	}

	@Override
	public Page next() {
		if(index >= pages.size()) {
			return null;
		}
		return pages.get(index++);
	}
}
