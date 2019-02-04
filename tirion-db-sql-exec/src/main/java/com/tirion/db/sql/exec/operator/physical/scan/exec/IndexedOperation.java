/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.exec;

import java.util.SortedSet;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.bitmap.Bitmaps;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.index.Index.IndexKind;
import com.tirion.db.store.index.bm.BmIndex;
import com.tirion.executor.job.Job;

/**
 * Index lookup.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class IndexedOperation extends BasicOperation {

//	@JsonProperty
//	private Kind operator;
	
	// these fields are mutually exclusive
	@JsonProperty
	private Object value;
	@JsonProperty
	private Object low, high;
	@JsonProperty
	private SortedSet<Object> set;
	
	public IndexedOperation(Job job, Column column) {
		super(job, column);
	}

//	public void setOperator(Kind operator) {
//		this.operator = operator;
//	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setLow(Object low) {
		this.low = low;
	}

	public void setHigh(Object high) {
		this.high = high;
	}

	public void setSet(SortedSet<Object> set) {
		this.set = set;
	}
	
	@SuppressWarnings("unchecked")
	public Bitmaps execute() {
		BmIndex<Object> index = (BmIndex<Object>) getColumn().getIndex(IndexKind.BM);
		Bitmaps result = null;
//		switch (operator) {
//			case EQ_OPERATOR:
//				result = index.getEq(value, getJob());
//				break;
//			case NEQ_OPERATOR:
//				result = index.getNeq(value, getJob());
//				break;
//			case LT_OPERATOR:
//				result = index.getLt(value, getJob());
//				break;
//			case LTEQ_OPERATOR:
//				result = index.getLtEq(value, getJob());
//				break;
//			case GT_OPERATOR:
//				result = index.getGt(value, getJob());
//				break;
//			case GTEQ_OPERATOR:
//				result = index.getGtEq(value, getJob());
//				break;
//			case BETWEEN_OPERATOR:
//				result = index.getBetween(low, high, getJob());
//				break;
//			case IN_OPERATOR:
//				result = index.getIn(set, getJob());
//				break;
//			default:
//				throw new IllegalArgumentException("Illegal operator '" + operator + "'");
//		}
		return result;
	}
}
