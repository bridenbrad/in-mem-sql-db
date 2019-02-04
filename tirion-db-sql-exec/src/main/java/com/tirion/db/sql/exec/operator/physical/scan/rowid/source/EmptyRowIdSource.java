/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.source;

import java.util.Collections;
import java.util.List;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class EmptyRowIdSource implements RowIdSource {
	
	public static final RowIdSource INSTANCE = new EmptyRowIdSource();

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public long next() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Long> asList() {
		return Collections.emptyList();
	}
}
