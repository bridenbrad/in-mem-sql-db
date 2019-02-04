/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.slice;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractSlice implements Slice {

	private final int slice;
	private final int rowCount;
	
	public AbstractSlice(int slice, int rowCount) {
		super();
		this.slice = slice;
		this.rowCount = rowCount;
	}

	@Override
	public final int getId() {
		return slice;
	}

	@Override
	public final int getRowCount() {
		return rowCount;
	}
}
