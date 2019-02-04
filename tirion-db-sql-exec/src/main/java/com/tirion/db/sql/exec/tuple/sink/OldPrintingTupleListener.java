/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.sql.exec.tuple.sink;
//
//import java.io.IOException;
//import java.io.Writer;
//import java.util.List;
//
//import com.tirion.common.NotYetImplementedException;
//import com.tirion.common.date.DateUtil;
//import com.tirion.db.catalog.model.Entity;
//import com.tirion.db.catalog.model.Field;
//import com.tirion.db.sql.exec.tuple.Tuple;
//import com.tirion.db.sql.exec.tuple.Tuples;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class OldPrintingTupleListener extends EntityAwareTupleListener{
//
//	private final Writer writer;
//
//	public OldPrintingTupleListener(Entity entity, Writer writer) {
//		super(entity);
//		this.writer = writer;
//	}
//
//	@Override
//	public void onTuples(Tuples tuples) {
//		for(Tuple tuple : tuples.get()) {
//			onTuple(tuple);
//		}
//	}
//
//	@Override
//	public void onTuple(Tuple tuple) {
//		List<Field> fields = getEntityInfo().getFields();
//		for (int i = 0; i < fields.size(); i++) {
//			final Field field = getEntityInfo().getField(i);
//			final int index = field.getIndex();
//			switch (field.getDeclaredType()) {
//				case BOOLEAN:
//					boolean bool = tuple.getBoolean(index); 
//					write(String.valueOf(bool));
//					break;
//				case BYTE:
//					byte byteValue = tuple.getByte(index); 
//					write(String.valueOf(byteValue));
//					break;
//				case SHORT:
//					short shortValue = tuple.getShort(index); 
//					write(String.valueOf(shortValue));
//					break;
//				case INT:
//					int intValue = tuple.getInteger(index); 
//					write(String.valueOf(intValue));
//					break;
//				case LONG:
//					long longValue = tuple.getLong(index); 
//					write(String.valueOf(longValue));
//					break;
//				case FLOAT:
//					float floatValue = tuple.getFloat(index); 
//					write(String.valueOf(floatValue));
//					break;
//				case DOUBLE:
//					double doubleValue = tuple.getDouble(index); 
//					write(String.valueOf(doubleValue));
//					break;
//				case DATE:
//					String date = DateUtil.dateNumberToString(tuple.getShort(index));
//					write(date);
//					break;
//				case TIMESTAMP:
//					String timestamp = DateUtil.timestampToString(tuple.getLong(index));
//					write(timestamp);
//					break;
//				case VARCHAR:
//					String str = tuple.getString(index);
//					write(str);
//					break;
//				case TIME:
//					String time = DateUtil.timeToString(tuple.getInteger(index));
//					write(time);
//					break;
//				default:
//					throw new NotYetImplementedException(field.getDeclaredType().name());
//			}
//			write(",");
//		}
//		write("\n");
//	}
//	
//	private void write(String value) {
//		try {
//			writer.write(value);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
//}
