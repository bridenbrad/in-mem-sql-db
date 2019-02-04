/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.set;

import java.util.HashSet;
import java.util.Set;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.sql.exec.operator.physical.PhysicalOperator;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.store.column.Column;
import com.tirion.pool.Pool;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DynamicStringSetSource<T> implements SetSource<T> {

	private final PhysicalOperator operator;
	private Set<Object> set;
	
	private final Pool pool;
	private final Column column;
	
	public DynamicStringSetSource(PhysicalOperator operator, Pool pool, Column column) {
		this.operator = operator;
		this.pool = pool;
		this.column = column;
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized Set<T> getSet() {
		if(set == null) {
			execute();
		}
		return (Set<T>) set;
	}
	
	private void execute() {
		Set<Object> strings = new HashSet<Object>();
		operator.init();
		while(true) {
			Tuple tuple = operator.next();
			if(tuple == null) {
				break;
			}
			strings.add(tuple.get(0));
		}
		operator.shutdown();
		
		Set<Object> tokens = new HashSet<Object>(strings.size());
		if(pool != null) {
			for(Object value : strings) {
				tokens.add(pool.getToken((String)value));
			}			
		} else {
			for(Object value : strings) {
				tokens.add(column.getTokenMap().getTokenFor(value, false));
			}
		}
		this.set = tokens;
		throw new NotYetImplementedException("this is broken");
	}
}
