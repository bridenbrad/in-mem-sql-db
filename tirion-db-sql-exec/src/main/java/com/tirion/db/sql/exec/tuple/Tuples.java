/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.tuple;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.tirion.common.ListAware;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Tuples implements ListAware<Tuple>, Serializable {
	
	private static final long serialVersionUID = 3924887352604582681L;
	
	private final List<Tuple> tuples;
	
	public Tuples() {
		this(new LinkedList<Tuple>());
	}
	
	public Tuples(List<Tuple> tuples) {
		this.tuples = tuples;
	}

	@Override
	public void append(Tuple tuple) {
		tuples.add(tuple);
	}

	@Override
	public void append(List<Tuple> tuples) {
		tuples.addAll(tuples);
	}

	@Override
	public int getCount() {
		return tuples.size();
	}

	@Override
	public boolean isEmpty() {
		return tuples.isEmpty();
	}

	@Override
	public List<Tuple> get() {
		return tuples;
	}

	@Override
	public Tuple get(int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean has(int index) {
		throw new UnsupportedOperationException();
	}
}
