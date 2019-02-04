/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.column;

import com.tirion.common.sequence.Sequence;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.token.ProxyTokenMap;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleColumnData implements ColumnData {

	private final Sequence[] sequences;
	private final DataStats[] stats;
	private final ProxyTokenMap<Object, Object> tokenMap;
	
	public SimpleColumnData(Sequence[] sequences, DataStats[] stats, ProxyTokenMap<Object, Object> tokenMap) {
		super();
		this.sequences = sequences;
		this.stats = stats;
		this.tokenMap = tokenMap;
	}
	
	@Override
	public DataStats[] getStats() {
		return stats;
	}

	@Override
	public Sequence[] getSequences() {
		return sequences;
	}

	@Override
	public ProxyTokenMap<Object, Object> getTokenMap() {
		return tokenMap;
	}
}
