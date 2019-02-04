/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.merger;

import java.util.Arrays;
import java.util.TreeSet;

import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.EmptyRowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.ListBackedRowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleRowIdMerger implements RowIdMerger {

	@Override
	public RowIdSource mergeAnd(RowIdSource... rowIdSources) {
		if(rowIdSources == null || rowIdSources.length == 0) {
			return EmptyRowIdSource.INSTANCE;
		}
		if(rowIdSources.length == 1) {
			return rowIdSources[0];
		}
		TreeSet<Long> result = new TreeSet<Long>();
		result.addAll(rowIdSources[0].asList());
		for (int i = 1; i < rowIdSources.length; i++) {	
			RowIdSource source = rowIdSources[i];
			if(source.isEmpty()) {
				continue;
			}
			result.retainAll(source.asList());
		}
		return asListBackedRowIdSource(result);
	}

	@Override
	public RowIdSource mergeOr(RowIdSource... rowIdSources) {
		if(rowIdSources == null || rowIdSources.length == 0) {
			return new EmptyRowIdSource();
		}
		TreeSet<Long> result = new TreeSet<Long>();
		for(RowIdSource rowIdSource : rowIdSources) {
			if(rowIdSource.isEmpty()) {
				continue;
			}
			result.addAll(rowIdSource.asList());
		}
		return asListBackedRowIdSource(result);
	}
	
	private RowIdSource asListBackedRowIdSource(TreeSet<Long> set) {
		if(set.isEmpty()) {
			return EmptyRowIdSource.INSTANCE;
		}
		return new ListBackedRowIdSource(Arrays.asList(set.toArray(new Long[]{})));
	}
}
