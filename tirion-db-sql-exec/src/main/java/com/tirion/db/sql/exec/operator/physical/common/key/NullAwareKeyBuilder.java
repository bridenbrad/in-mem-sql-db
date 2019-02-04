/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.common.key;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.sql.exec.operator.physical.common.key.part.AbstractPartFactory;
import com.tirion.db.sql.exec.operator.physical.common.key.part.Part;
import com.tirion.db.sql.exec.operator.physical.common.key.part.PartFactory;
import com.tirion.db.sql.exec.tuple.EmptyTuple;
import com.tirion.db.sql.exec.tuple.Location;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class NullAwareKeyBuilder implements KeyBuilder {

	@JsonProperty
	private final Location[] locations;
	private final PartFactory[] factories;
	
	public NullAwareKeyBuilder(Location[] locations) {
		this.locations = locations;
		this.factories = buildPartFactories(locations);
	}
	
	@Override
	public int getPartCount() {
		return locations.length;
	}

	@Override
	public Key build(Tuple tuple) {
		if(tuple == EmptyTuple.EOS_TUPLE) {
			return EmptyKey.EMPTY_KEY;
		}
		Part[] parts = new Part[locations.length];
		for (int i = 0; i < locations.length; i++) {
			parts[i] = factories[i].newPart(locations[i], tuple);
		}
		return new CompositeKey(parts);
	}

	@Override
	public List<Key> buildRollup(Tuple tuple) {
		throw new NotYetImplementedException();
	}

	@Override
	public List<Key> buildCube(Tuple tuple) {
		throw new NotYetImplementedException();
	}
	
	private PartFactory[] buildPartFactories(Location[] locations) {
		PartFactory[] factories = new PartFactory[locations.length];
		for (int i = 0; i < locations.length; i++) {			
			switch (locations[i].getType()) {
				case BOOLEAN:
					factories[i] = new AbstractPartFactory.BooleanPartFactory();
					break;
				case BYTE:
					factories[i] = new AbstractPartFactory.BytePartFactory();
					break;
				case DATE:
				case SHORT:
					factories[i] = new AbstractPartFactory.ShortPartFactory();
					break;
				case TIME:
				case INT:
					factories[i] = new AbstractPartFactory.IntegerPartFactory();
					break;
				case VARCHAR:
					factories[i] = new AbstractPartFactory.StringPartFactory();
					break;
				case TIMESTAMP:
				case LONG:
					factories[i] = new AbstractPartFactory.LongPartFactory();
					break;
				case FLOAT:
					factories[i] = new AbstractPartFactory.FloatPartFactory();
					break;
				case DOUBLE:
					factories[i] = new AbstractPartFactory.DoublePartFactory();
					break;
				default:
					throw new NotYetImplementedException(locations[i].getType().toString());
			}
		}
		return factories;
	}
}
