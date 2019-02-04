/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.context;

import java.util.ArrayList;
import java.util.List;

import com.tirion.db.store.builder.transformation.slice.SequenceSlice;
import com.tirion.db.store.builder.transformation.slice.Slice;
import com.tirion.db.store.builder.transformation.transformer.Transformer;
import com.tirion.db.store.column.token.ProxyTokenMap;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleTransformationContext implements TransformationContext {

	private final List<SequenceSlice> slices;
	private final ProxyTokenMap<Object, Object>[] tokenMaps;
	private final Transformer[] transformers;

	public SimpleTransformationContext(ProxyTokenMap<Object, Object>[] tokenMaps, Transformer[] transformers) {
		super();
		this.tokenMaps = tokenMaps;
		this.transformers = transformers;
		slices = new ArrayList<SequenceSlice>();
	}

	@Override
	public ProxyTokenMap<Object, Object>[] getTokenMapProxies() {
		return tokenMaps;
	}
	
	@Override
	public List<SequenceSlice> getSlices() {
		return slices;
	}

	@Override
	public void onSlice(Slice slice) {
		for (int i = 0; i < transformers.length; i++) {
			slice = transformers[i].transform(slice);
		}
		slices.add((SequenceSlice)slice);
	}
}
