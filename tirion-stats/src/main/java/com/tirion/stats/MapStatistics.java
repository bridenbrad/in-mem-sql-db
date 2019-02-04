/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.stats;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class MapStatistics implements Statistics {

	private static final Logger log = LoggerFactory.getLogger(MapStatistics.class);
	
	private final Map<String, Statistics> map = new HashMap<String, Statistics>();

	public MapStatistics(Kind kind) {
		super();
	}

	public Map<String, Statistics> getMap() {
		return map;
	}
	
	@Override
	public Kind getKind() {
		// TODO Auto-generated method stub
		return null;
	}



	public void add(String key, Statistics value) {
		Statistics old = map.put(key, value);
		if(old != null) {
			log.warn("Duplicate key " + key + " in map statistics, probably minor bug");
		}
	}
}
