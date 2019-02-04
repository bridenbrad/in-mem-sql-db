/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.table.data;

import java.util.List;

import com.tirion.common.sequence.Sequence;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.builder.transformation.slice.SequenceSlice;
import com.tirion.db.store.builder.transformation.slice.StatsSequenceSlice;
import com.tirion.db.store.column.ColumnData;
import com.tirion.db.store.column.SimpleColumnData;
import com.tirion.db.store.column.token.ProxyTokenMap;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleTableData implements TableData {

	private final List<SequenceSlice> slices;
	private final ProxyTokenMap<Object, Object>[] proxyTokenMaps;
		
	public SimpleTableData(List<SequenceSlice> slices, ProxyTokenMap<Object, Object>[] proxyTokenMaps) {
		super();
		this.slices = slices;
		this.proxyTokenMaps = proxyTokenMaps;
	}

	@Override
	public List<SequenceSlice> getSlices() {
		return slices;
	}

	@Override
	public ProxyTokenMap<Object, Object>[] getTokenMaps() {
		return proxyTokenMaps;
	}

	@Override
	public ColumnData build(int columnIndex) {
		Sequence[] sequences = new Sequence[slices.size()];
		for (int i = 0; i < slices.size(); i++) {
			sequences[i] = slices.get(i).getSequences()[columnIndex];
		}
		DataStats[] stats = new DataStats[slices.size()];
		for (int i = 0; i < slices.size(); i++) {
			SequenceSlice slice = slices.get(i);
			if(slice instanceof StatsSequenceSlice) {				
				stats[i] = ((StatsSequenceSlice)slice).getStats()[columnIndex];
			}
		}
		return new SimpleColumnData(sequences, stats, proxyTokenMaps[columnIndex]);
	}
}
