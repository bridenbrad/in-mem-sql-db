/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Thin wrapper around Java's {@link List}.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleRowIdSource implements PeekableRowIdSource  {

	private Long current;
	private final Collection<Long> rowIds;
	private final Iterator<Long> it;
	
	public SimpleRowIdSource(Collection<Long> rowIds) {
		this.rowIds = rowIds;
		this.it = rowIds.iterator();
	}
	
	@Override
	public List<Long> asList() {
		return new ArrayList<Long>(rowIds);
	}

	public Collection<Long> getRowIds() {
		return rowIds;
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
	public long peek() {
		if(current != null) {
			return current;
		}
		current = it.next();
		return current;
	}

	@Override
	public long next() {
		if(current != null) {
			long result = current;
			current = null;
			return result;
		}
		return it.next();
	}
}
