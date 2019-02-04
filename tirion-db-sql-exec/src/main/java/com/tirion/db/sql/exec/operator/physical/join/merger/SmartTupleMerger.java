/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.join.merger;

import com.tirion.db.sql.exec.tuple.Location;
import com.tirion.db.sql.exec.tuple.Locations;
import com.tirion.db.sql.exec.tuple.OnHeapTuple;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * Will remove fields which are not needed.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SmartTupleMerger implements TupleMerger {

	private final Locations leftLocations;
	private final Locations rightLocations;
	
	public SmartTupleMerger(Locations leftLocations, Locations rightLocations) {
		this.leftLocations = leftLocations;
		this.rightLocations = rightLocations;
	}

	@Override
	public Tuple merge(Tuple left, Tuple right) {
		Object[] result = new Object[leftLocations.getCount() + rightLocations.getCount()];
		int index = 0;
		for(Location location : leftLocations.get()) {
			result[index++] = left.get(location.getIndex());
		}
		if(right != null) {		
			for(Location location : rightLocations.get()) {
				result[index++] = right.get(location.getIndex());
			}
		}
		return new OnHeapTuple(result);
	}
}
