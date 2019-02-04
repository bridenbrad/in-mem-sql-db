/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.source;

import com.tirion.db.store.page.Page;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ProxyPageSource implements PageSource {

	private final int startPageId; // inclusive
	private final int endPageId; // exclusive
	private final PageSource underying;
	
	private Page page;
	
	public ProxyPageSource(int startPageId, int endPageId, PageSource underying) {
		super();
		this.startPageId = startPageId;
		this.endPageId = endPageId;
		this.underying = underying;
	}

	@Override
	public boolean hasNext() {
		if(!underying.hasNext()) {
			return false;
		}
		page = underying.next();
		if(startPageId <= page.getId() && page.getId() < endPageId) {
			return true;
		}
		return false;
	}

	@Override
	public Page next() {
		return page;
	}
}
