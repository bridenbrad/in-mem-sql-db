/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.root.converter;

import java.sql.Timestamp;

import com.tirion.common.date.DateUtil;
import com.tirion.db.sql.exec.tuple.Location;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractConverter implements Converter {

	protected final Location[] locations;
	
	public AbstractConverter(Location[] locations) {
		this.locations = locations;
	}

	public static final class DateConverter extends AbstractConverter {
		
		public DateConverter(Location[] locations) {
			super(locations);
		}

		@Override
		public void convert(Tuple tuple) {
			for (int i = 0; i < locations.length; i++) {
				final int index = locations[i].getIndex();
				if(!tuple.isNull(index)) {
					short value = tuple.getShort(index);
					java.sql.Date date = DateUtil.getSqlDateFromDateNumber(value);
					tuple.putDate(index, date);
				}
			}
		}
	}
	
	public static final class TimestampConverter extends AbstractConverter {
		
		public TimestampConverter(Location[] locations) {
			super(locations);
		}

		@Override
		public void convert(Tuple tuple) {
			for (int i = 0; i < locations.length; i++) {
				final int index = locations[i].getIndex();
				if(!tuple.isNull(index)) {
					long value = tuple.getLong(index);
					tuple.putTimestamp(index, new Timestamp(value));
				}
			}
		}
	}
	
	public static final class TimeConverter extends AbstractConverter {
		
		public TimeConverter(Location[] locations) {
			super(locations);
		}

		@Override
		public void convert(Tuple tuple) {
			for (int i = 0; i < locations.length; i++) {
				final int index = locations[i].getIndex();
				if(!tuple.isNull(index)) {
					int value = tuple.getInteger(index);
					tuple.putTime(index, DateUtil.getSqlTimeFromTimeNumber(value));
				}
			}
		}
	}
}
