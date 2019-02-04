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

import com.tirion.db.sql.exec.operator.physical.PhysicalOperator;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DynamicSetSource<T> implements SetSource<T> {

	private final PhysicalOperator operator;
	private Set<Object> set;
	
	public DynamicSetSource(PhysicalOperator operator) {
		this.operator = operator;
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
		Set<Object> set = new HashSet<Object>();
		operator.init();
		while(true) {
			Tuple tuple = operator.next();
			if(tuple == null) {
				break;
			}
			set.add(tuple.get(0));
		}
		operator.shutdown();		
		this.set = set;
	}
}
