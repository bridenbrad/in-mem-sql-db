/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.source;

import java.util.Iterator;
import java.util.List;

/**
 * Thin wrapper around Java's {@link List}.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ListBackedRowIdSource implements RowIdSource {

	private final List<Long> rowIds;
	private final Iterator<Long> it;
	
	public ListBackedRowIdSource(List<Long> rowIds) {
		this.rowIds = rowIds;
		this.it = rowIds.iterator();
	}

	@Override
	public boolean isEmpty() {
		return rowIds.isEmpty();
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}	

	@Override
	public long next() {
		return it.next();
	}

	@Override
	public List<Long> asList() {
		return rowIds;
	}
}
