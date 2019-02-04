/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.functor;

import java.util.ArrayList;
import java.util.List;

import com.tirion.common.Lifecycle;
import com.tirion.common.ListAware;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.sql.exec.tuple.Tuples;
import com.tirion.db.sql.exec.tuple.sink.TupleListener;

/**
 * Just a tin wrapper around list of {@link Functor}s.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Functors implements Lifecycle, TupleListener, ListAware<Functor>{

	private final List<Functor> functors;
	
	public Functors() {
		functors = new ArrayList<Functor>();
	}
	
	public Functors(int count) {
		functors = new ArrayList<Functor>(count);
	}

	@Override
	public void append(Functor functor) {
		functors.add(functor);
	}

	@Override
	public void append(List<Functor> functors) {
		for(Functor functor : functors) {
			append(functor);
		}
	}

	@Override
	public void init() {
		for(Functor functor : functors) {
			functor.init();
		}
	}

	@Override
	public void shutdown() {
		for(Functor functor : functors) {
			functor.shutdown();
		}
	}

	@Override
	public void onTuple(Tuple tuple) {
		for(Functor functor : functors) {
			functor.onTuple(tuple);
		}
	}
	
	@Override
	public void onTuples(Tuples tuples) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int getCount() {
		return functors.size();
	}

	@Override
	public boolean isEmpty() {
		return functors.isEmpty();
	}

	@Override
	public List<Functor> get() {
		return functors;
	}

	@Override
	public Functor get(int index) {
		return functors.get(index);
	}

	@Override
	public boolean has(int index) {
		return index < functors.size();
	}

	public void writeStateTo(Tuple tuple) {
		for(Functor functor : functors) {
			functor.writeStateTo(tuple);
		}
	}
	
	public Functors cloneMe() {
		Functors result = new Functors(getCount());
		for(Functor functor : functors) {
			result.append(functor.cloneMe());
		}
		return result;
	}
}
