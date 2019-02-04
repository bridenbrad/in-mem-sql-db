/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.common.key.part;

import com.tirion.db.sql.exec.tuple.Location;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractPartFactory implements PartFactory {

	public static final class BooleanPartFactory extends AbstractPartFactory {
		@Override
		public Part newPart(Location location, Tuple tuple) {
			final int index = location.getIndex();
			if(tuple.isNull(index)) {
				return new BooleanPart(false, true);
			} else {				
				return new BooleanPart(tuple.getBoolean(index), false);
			}
		}
	}
	
	public static final class BytePartFactory extends AbstractPartFactory {
		@Override
		public Part newPart(Location location, Tuple tuple) {
			final int index = location.getIndex();
			if(tuple.isNull(index)) {
				return new BytePart((byte)0, true);
			} else {				
				return new BytePart(tuple.getByte(index), false);
			}
		}
	}

	public static final class ShortPartFactory extends AbstractPartFactory {
		@Override
		public Part newPart(Location location, Tuple tuple) {
			final int index = location.getIndex();
			if(tuple.isNull(index)) {
				return new ShortPart((short)0, true);
			} else {				
				return new ShortPart(tuple.getShort(index), false);
			}
		}
	}
	
	public static final class IntegerPartFactory extends AbstractPartFactory {
		@Override
		public Part newPart(Location location, Tuple tuple) {
			final int index = location.getIndex();
			if(tuple.isNull(index)) {
				return new IntegerPart(0, true);
			} else {				
				return new IntegerPart(tuple.getInteger(index), false);
			}
		}
	}
	
	public static final class LongPartFactory extends AbstractPartFactory {
		@Override
		public Part newPart(Location location, Tuple tuple) {
			final int index = location.getIndex();
			if(tuple.isNull(index)) {
				return new LongPart(0L, true);
			} else {				
				return new LongPart(tuple.getLong(index), false);
			}
		}
	}
	
	public static final class FloatPartFactory extends AbstractPartFactory {
		@Override
		public Part newPart(Location location, Tuple tuple) {
			final int index = location.getIndex();
			if(tuple.isNull(index)) {
				return new FloatPart(0F, true);
			} else {				
				return new FloatPart(tuple.getFloat(index), false);
			}
		}
	}
	
	public static final class DoublePartFactory extends AbstractPartFactory {
		@Override
		public Part newPart(Location location, Tuple tuple) {
			final int index = location.getIndex();
			if(tuple.isNull(index)) {
				return new DoublePart(0D, true);
			} else {				
				return new DoublePart(tuple.getDouble(index), false);
			}
		}
	}
	
	public static final class StringPartFactory extends AbstractPartFactory {
		@Override
		public Part newPart(Location location, Tuple tuple) {
			final int index = location.getIndex();
			if(tuple.isNull(index)) {
				return new StringPart(null);
			} else {				
				return new StringPart(tuple.getString(index));
			}
		}
	}
}
