/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.root;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.exec.operator.physical.SingleSourcePhysicalOperator;
import com.tirion.db.sql.exec.operator.physical.root.converter.Converter;
import com.tirion.db.sql.exec.tuple.Location;
import com.tirion.db.sql.exec.tuple.OnHeapTuple;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * Root of every query. Will remove any unneeded columns. Easier to
 * centralize column removal here.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class RootOperator extends SingleSourcePhysicalOperator {

	private Converter[] converters;
	@JsonProperty
	private Location[] locations;
	
	public void setConverters(Converter[] converters) {
		this.converters = converters;
	}

	public void setLocations(Location[] locations) {
		this.locations = locations;
	}

	@Override
	public Kind getKind() {
		return Kind.ROOT;
	}

	@Override
	protected Tuple nextInternal() {
		Tuple tuple = nextFromSource();
		if(tuple == null) {
			setDone();
			return null;
		}
		if(locations != null) {			
			Object[] result = new Object[locations.length];
			for (int i = 0; i < locations.length; i++) {
				result[i] = tuple.getUnderlying()[locations[i].getIndex()];
			}
			tuple = new OnHeapTuple(result);
		}
		if(converters != null) {
			for (int i = 0; i < converters.length; i++) {
				converters[i].convert(tuple);
			}
		}
		return tuple;
	}
}
