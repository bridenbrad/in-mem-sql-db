/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.page;

import com.tirion.common.type.Type; 
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.header.HeaderSource;
import com.tirion.common.runtime.Runtime;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractPageBuilder implements PageBuilder {

	private final boolean isOnHeap;
	private final Column column;
	private final Runtime runtime;
	private final HeaderSource headerSource;
	
	public AbstractPageBuilder(boolean isOnHeap, Column column, Runtime runtime, HeaderSource headerSource) {
		super();
		this.isOnHeap = isOnHeap;
		this.column = column;
		this.runtime = runtime;
		this.headerSource = headerSource;
	}
	
	protected final boolean isTokenized() {
		return column.isTokenized();
	}

	protected final Type getType() {
		return column.getType();
	}
	
	protected final boolean isOnHeap() {
		return isOnHeap;
	}

	protected final Column getOwner() {
		return column;
	}
	
	protected final Runtime getRuntime() {
		return runtime;
	}
	
	protected final HeaderSource getHeaderSource() {
		return headerSource;
	}
}
