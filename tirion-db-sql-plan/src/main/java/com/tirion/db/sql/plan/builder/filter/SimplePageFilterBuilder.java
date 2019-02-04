/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.sql.plan.builder.filter;
//
//import com.tirion.common.NotYetImplementedException;
//import com.tirion.common.type.Type;
//import com.tirion.db.sql.ast.Node.Kind;
//import com.tirion.db.sql.ast.common.FieldRef;
//import com.tirion.db.sql.ast.common.constant.Constant;
//import com.tirion.db.sql.ast.common.constant.ConstantRange;
//import com.tirion.db.sql.ast.expression.compare.BetweenCompare;
//import com.tirion.db.sql.ast.expression.compare.Compare;
//import com.tirion.db.sql.ast.expression.compare.InCompare;
//import com.tirion.db.sql.ast.expression.compare.SimpleCompare;
//import com.tirion.db.sql.exec.operator.physical.scan.filter.page.AbstractPageFilter;
//import com.tirion.db.sql.exec.operator.physical.scan.filter.page.BooleanPageFilter;
//import com.tirion.db.sql.exec.operator.physical.scan.filter.page.BytePageFilter;
//import com.tirion.db.sql.exec.operator.physical.scan.filter.page.DoublePageFilter;
//import com.tirion.db.sql.exec.operator.physical.scan.filter.page.FloatPageFilter;
//import com.tirion.db.sql.exec.operator.physical.scan.filter.page.IntegerPageFilter;
//import com.tirion.db.sql.exec.operator.physical.scan.filter.page.LongPageFilter;
//import com.tirion.db.sql.exec.operator.physical.scan.filter.page.NullPageFilter;
//import com.tirion.db.sql.exec.operator.physical.scan.filter.page.PageFilter;
//import com.tirion.db.sql.exec.operator.physical.scan.filter.page.ShortPageFilter;
//import com.tirion.db.sql.exec.operator.physical.scan.set.DynamicSetSource;
//import com.tirion.db.sql.exec.operator.physical.scan.set.StaticSetSource;
//import com.tirion.db.store.column.Column;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class SimplePageFilterBuilder implements PageFilterBuilder {
//
//	private final TxJobContext jobContext;
//
//	public SimplePageFilterBuilder(TxJobContext jobContext) {
//		this.jobContext = jobContext;
//	}
//
//	@Override
//	public PageFilter build(String tableName, Compare compare) {
//		FieldRef fieldRef = compare.getFieldRef();
//		Column column = jobContext.getRuntime().store().getTable(tableName).getColumn(fieldRef.getName());
//		AbstractPageFilter filter = buildInternal(compare, column);
//		filter.setRuntime(jobContext.getRuntime());
//		filter.setColumn(column);
//		filter.setMaxPageId(jobContext.getTx().getScope(tableName).getMaxPageId());
//		return filter;
//	}
//	
//	private AbstractPageFilter buildInternal(Compare compare, Column column) {
//		if(compare.getKind() == Kind.IS_NOT_NULL_OPERATOR) {
//			return new NullPageFilter(false);
//		}
//		if(compare.getKind() == Kind.IS_NULL_OPERATOR) {
//			return new NullPageFilter(true);
//		}
//		final Type type = compare.getFieldRef().getType();
//		switch(type) {
//			case BOOLEAN:
//				return buildBoolPageFilter((SimpleCompare)compare);
//			case BYTE:
//				return buildBytePageFilter(compare);
//			case SHORT:
//				return buildShortPageFilter(compare);
//			case INT:
//				return buildIntegerPageFilter(compare);
//			case LONG:
//				return buildLongPageFilter(compare);
//			case FLOAT:
//				return buildFloatPageFilter(compare);
//			case DOUBLE:
//				return buildDoublePageFilter(compare);
//			case VARCHAR:
//				return buildLongPageFilter(compare);
//			case DATE:
//				return buildShortPageFilter(compare);
//			case TIMESTAMP:
//				return buildLongPageFilter(compare);
//			case TIME:
//				return buildIntegerPageFilter(compare);
//			default:
//				throw new NotYetImplementedException(type.toString());
//		}
//	}
//	
////	private AbstractPageFilter buildStringPageFilter(Compare compare, Column column) {
////		if(compare instanceof SimpleCompare) {
////			Util.assertTrue(compare.getKind() == Kind.EQ_OPERATOR || compare.getKind() == Kind.NEQ_OPERATOR);
////			SimpleCompare simpleCompare = (SimpleCompare) compare;
////			final String value = ((Constant)simpleCompare.getRight()).getValue(String.class);
////			if(compare.getKind() == Kind.EQ_OPERATOR) {					
////				return new StringPageFilter.StringEqPageFilter(value);
////			} else {
////				return new StringPageFilter.StringNeqPageFilter(value);
////			}
////			
////		} else {
////			InCompare in = (InCompare) compare;			
////			if(in.isConstant()) {
////				return new StringPageFilter.StringInPageFilter(new StaticSetSource<String>(in.getSet().getValues(String.class)));						
////			} else {
////				return new StringPageFilter.StringInPageFilter(new DynamicStringSetSource<String>(
////						in.getOperator(), null, column));
////			}
////		}
////	}
//
//	private AbstractPageFilter buildBoolPageFilter(SimpleCompare compare) {
//		Constant constant = (Constant) compare.getRight();
//		boolean value = constant.getValue(Boolean.class);
//		if(compare.getKind() == Kind.NEQ_OPERATOR) {
//			value = !value;
//		}
//		return new BooleanPageFilter(value);
//	}
//	
//	private AbstractPageFilter buildBytePageFilter(Compare compare) {
//		if(compare instanceof SimpleCompare) {
//			SimpleCompare simpleCompare = (SimpleCompare) compare;
//			Constant constant = (Constant) simpleCompare.getRight();
//			switch (compare.getKind()) {
//				case EQ_OPERATOR:
//					return new BytePageFilter.ByteEqPageFilter((Byte)constant.getValue());
//				case NEQ_OPERATOR:
//					return new BytePageFilter.ByteNeqPageFilter((Byte)constant.getValue());	
//				case LT_OPERATOR:
//					return new BytePageFilter.ByteLtPageFilter((Byte)constant.getValue());
//				case LTEQ_OPERATOR:
//					return new BytePageFilter.ByteLtEqPageFilter((Byte)constant.getValue());
//				case GT_OPERATOR:
//					return new BytePageFilter.ByteGtPageFilter((Byte)constant.getValue());
//				case GTEQ_OPERATOR:
//					return new BytePageFilter.ByteGtEqPageFilter((Byte)constant.getValue());
//				default:
//					throw new NotYetImplementedException(compare.getKind().toString());
//			}
//		} else if(compare instanceof BetweenCompare) {
//			ConstantRange range = ((BetweenCompare) compare).getRange();
//			return new BytePageFilter.ByteBetweenPageFilter((Byte)range.getLow(), (Byte)range.getHigh());
//		} else if(compare instanceof InCompare) {
//			InCompare in = (InCompare) compare;
//			if(in.isConstant()) {
//				return new BytePageFilter.ByteInPageFilter(new StaticSetSource<Byte>(in.getSet().getValues(Byte.class)));						
//			} else {
//				return new BytePageFilter.ByteInPageFilter(new DynamicSetSource<Byte>(in.getOperator()));
//			}
//		} else {
//			throw new IllegalArgumentException(compare.getClass().getName());
//		}
//	}
//	
//	private AbstractPageFilter buildShortPageFilter(Compare compare) {
//		if(compare instanceof SimpleCompare) {
//			SimpleCompare simpleCompare = (SimpleCompare) compare;
//			Constant constant = (Constant) simpleCompare.getRight();
//			switch (compare.getKind()) {
//				case EQ_OPERATOR:
//					return new ShortPageFilter.ShortEqPageFilter((Short)constant.getValue());
//				case NEQ_OPERATOR:
//					return new ShortPageFilter.ShortNeqPageFilter((Short)constant.getValue());	
//				case LT_OPERATOR:
//					return new ShortPageFilter.ShortLtPageFilter((Short)constant.getValue());
//				case LTEQ_OPERATOR:
//					return new ShortPageFilter.ShortLtEqPageFilter((Short)constant.getValue());
//				case GT_OPERATOR:
//					return new ShortPageFilter.ShortGtPageFilter((Short)constant.getValue());
//				case GTEQ_OPERATOR:
//					return new ShortPageFilter.ShortGtEqPageFilter((Short)constant.getValue());
//				default:
//					throw new NotYetImplementedException(compare.getKind().toString());
//			}
//		} else if(compare instanceof BetweenCompare) {
//			ConstantRange range = ((BetweenCompare) compare).getRange();
//			return new ShortPageFilter.ShortBetweenPageFilter((Short)range.getLow(), (Short)range.getHigh());
//		} else if(compare instanceof InCompare) {
//			InCompare in = (InCompare) compare;
//			if(in.isConstant()) {
//				return new ShortPageFilter.ShortInPageFilter(new StaticSetSource<Short>(in.getSet().getValues(Short.class)));						
//			} else {
//				return new ShortPageFilter.ShortInPageFilter(new DynamicSetSource<Short>(in.getOperator()));
//			}		
//		} else {
//			throw new IllegalArgumentException(compare.getClass().getName());
//		}
//	}
//	
//	private AbstractPageFilter buildIntegerPageFilter(Compare compare) {
//		if(compare instanceof SimpleCompare) {
//			SimpleCompare simpleCompare = (SimpleCompare) compare;
//			Constant constant = (Constant) simpleCompare.getRight();
//			switch (compare.getKind()) {
//				case EQ_OPERATOR:
//					return new IntegerPageFilter.IntegerEqPageFilter((Integer)constant.getValue());
//				case NEQ_OPERATOR:
//					return new IntegerPageFilter.IntegerNeqPageFilter((Integer)constant.getValue());	
//				case LT_OPERATOR:
//					return new IntegerPageFilter.IntegerLtPageFilter((Integer)constant.getValue());
//				case LTEQ_OPERATOR:
//					return new IntegerPageFilter.IntegerLtEqPageFilter((Integer)constant.getValue());
//				case GT_OPERATOR:
//					return new IntegerPageFilter.IntegerGtPageFilter((Integer)constant.getValue());
//				case GTEQ_OPERATOR:
//					return new IntegerPageFilter.IntegerGtEqPageFilter((Integer)constant.getValue());
//				default:
//					throw new NotYetImplementedException(compare.getKind().toString());
//			}
//		} else if(compare instanceof BetweenCompare) {
//			ConstantRange range = ((BetweenCompare) compare).getRange();
//			return new IntegerPageFilter.IntegerBetweenPageFilter((Integer)range.getLow(), (Integer)range.getHigh());
//		} else if(compare instanceof InCompare) {
//			InCompare in = (InCompare) compare;
//			if(in.isConstant()) {
//				return new IntegerPageFilter.IntegerInPageFilter(new StaticSetSource<Integer>(in.getSet().getValues(Integer.class)));						
//			} else {
//				return new IntegerPageFilter.IntegerInPageFilter(new DynamicSetSource<Integer>(in.getOperator()));
//			}		
//		} else {
//			throw new IllegalArgumentException(compare.getClass().getName());
//		}
//	}
//	
//	private AbstractPageFilter buildLongPageFilter(Compare compare) {
//		if(compare instanceof SimpleCompare) {
//			SimpleCompare simpleCompare = (SimpleCompare) compare;
//			Constant constant = (Constant) simpleCompare.getRight();
//			switch (compare.getKind()) {
//				case EQ_OPERATOR:
//					return new LongPageFilter.LongEqPageFilter((Long)constant.getValue());
//				case NEQ_OPERATOR:
//					return new LongPageFilter.LongNeqPageFilter((Long)constant.getValue());	
//				case LT_OPERATOR:
//					return new LongPageFilter.LongLtPageFilter((Long)constant.getValue());
//				case LTEQ_OPERATOR:
//					return new LongPageFilter.LongLtEqPageFilter((Long)constant.getValue());
//				case GT_OPERATOR:
//					return new LongPageFilter.LongGtPageFilter((Long)constant.getValue());
//				case GTEQ_OPERATOR:
//					return new LongPageFilter.LongGtEqPageFilter((Long)constant.getValue());
//				default:
//					throw new NotYetImplementedException(compare.getKind().toString());
//			}
//		} else if(compare instanceof BetweenCompare) {
//			ConstantRange range = ((BetweenCompare) compare).getRange();
//			return new LongPageFilter.LongBetweenPageFilter((Long)range.getLow(), (Long)range.getHigh());
//		} else if(compare instanceof InCompare) {
//			InCompare in = (InCompare) compare;
//			if(in.isConstant()) {
//				return new LongPageFilter.LongInPageFilter(new StaticSetSource<Long>(in.getSet().getValues(Long.class)));						
//			} else {
//				return new LongPageFilter.LongInPageFilter(new DynamicSetSource<Long>(in.getOperator()));
//			}		
//		} else {
//			throw new IllegalArgumentException(compare.getClass().getName());
//		}
//	}
//	
//	private AbstractPageFilter buildFloatPageFilter(Compare compare) {
//		if(compare instanceof SimpleCompare) {
//			SimpleCompare simpleCompare = (SimpleCompare) compare;
//			Constant constant = (Constant) simpleCompare.getRight();
//			switch (compare.getKind()) {
//				case EQ_OPERATOR:
//					return new FloatPageFilter.FloatEqPageFilter((Float)constant.getValue());
//				case NEQ_OPERATOR:
//					return new FloatPageFilter.FloatNeqPageFilter((Float)constant.getValue());	
//				case LT_OPERATOR:
//					return new FloatPageFilter.FloatLtPageFilter((Float)constant.getValue());
//				case LTEQ_OPERATOR:
//					return new FloatPageFilter.FloatLtEqPageFilter((Float)constant.getValue());
//				case GT_OPERATOR:
//					return new FloatPageFilter.FloatGtPageFilter((Float)constant.getValue());
//				case GTEQ_OPERATOR:
//					return new FloatPageFilter.FloatGtEqPageFilter((Float)constant.getValue());
//				default:
//					throw new NotYetImplementedException(compare.getKind().toString());
//			}
//		} else if(compare instanceof BetweenCompare) {
//			ConstantRange range = ((BetweenCompare) compare).getRange();
//			return new FloatPageFilter.FloatBetweenPageFilter((Float)range.getLow(), (Float)range.getHigh());
//		} else if(compare instanceof InCompare) {
//			InCompare in = (InCompare) compare;
//			if(in.isConstant()) {
//				return new FloatPageFilter.FloatInPageFilter(new StaticSetSource<Float>(in.getSet().getValues(Float.class)));						
//			} else {
//				return new FloatPageFilter.FloatInPageFilter(new DynamicSetSource<Float>(in.getOperator()));
//			}	
//		} else {
//			throw new IllegalArgumentException(compare.getClass().getName());
//		}
//	}
//	
//	private AbstractPageFilter buildDoublePageFilter(Compare compare) {
//		if(compare instanceof SimpleCompare) {
//			SimpleCompare simpleCompare = (SimpleCompare) compare;
//			Constant constant = (Constant) simpleCompare.getRight();
//			switch (compare.getKind()) {
//				case EQ_OPERATOR:
//					return new DoublePageFilter.DoubleEqPageFilter((Double)constant.getValue());
//				case NEQ_OPERATOR:
//					return new DoublePageFilter.DoubleNeqPageFilter((Double)constant.getValue());	
//				case LT_OPERATOR:
//					return new DoublePageFilter.DoubleLtPageFilter((Double)constant.getValue());
//				case LTEQ_OPERATOR:
//					return new DoublePageFilter.DoubleLtEqPageFilter((Double)constant.getValue());
//				case GT_OPERATOR:
//					return new DoublePageFilter.DoubleGtPageFilter((Double)constant.getValue());
//				case GTEQ_OPERATOR:
//					return new DoublePageFilter.DoubleGtEqPageFilter((Double)constant.getValue());
//				default:
//					throw new NotYetImplementedException(compare.getKind().toString());
//			}
//		} else if(compare instanceof BetweenCompare) {
//			ConstantRange range = ((BetweenCompare) compare).getRange();
//			return new DoublePageFilter.DoubleBetweenPageFilter((Double)range.getLow(), (Double)range.getHigh());
//		} else if(compare instanceof InCompare) {
//			InCompare in = (InCompare) compare;
//			if(in.isConstant()) {
//				return new DoublePageFilter.DoubleInPageFilter(new StaticSetSource<Double>(in.getSet().getValues(Double.class)));						
//			} else {
//				return new DoublePageFilter.DoubleInPageFilter(new DynamicSetSource<Double>(in.getOperator()));
//			}		
//		} else {
//			throw new IllegalArgumentException(compare.getClass().getName());
//		}
//	}
//}
