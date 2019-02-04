/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.tuple;

import java.util.ArrayList;
import java.util.List;

import com.tirion.common.ListAware;

/**
 * List of locations within the tuple.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Locations implements ListAware<Location> {
	
	private final List<Location> locations;

	public Locations() {
		super();
		locations = new ArrayList<Location>();
	}

	@Override
	public void append(Location location) {
		locations.add(location);
	}
	
	@Override
	public void append(List<Location> locations) {
		for(Location location : locations) {
			append(location);
		}
	}
	
	@Override
	public boolean isEmpty() {
		return locations.isEmpty();
	}

	@Override
	public Location get(int index) {
		return locations.get(index);
	}

	@Override
	public boolean has(int index) {
		return index < locations.size();
	}

	/**
	 * Number of distinct locations.
	 */
	@Override
	public int getCount() {
		return locations.size();
	}
	
	@Override
	public List<Location> get() {
		return locations;
	}
}
