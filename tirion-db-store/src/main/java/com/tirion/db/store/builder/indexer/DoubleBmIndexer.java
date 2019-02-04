/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.indexer;

import java.util.Map;

import com.tirion.common.bitmap.Bitmaps;
import com.tirion.common.sequence.array.Array;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DoubleBmIndexer extends AbstractBmIndexer<Double> {

	public DoubleBmIndexer(Map<Double, Bitmaps> map) {
		super(map);
	}

	@Override
	public void build(int slice, Array array) {
		double[] arr = (double[]) array.getUnderlying();
		for (int i = 0; i < arr.length; i++) {
			super.registerNewValue(arr[i], slice, i);
		}
	}
}



