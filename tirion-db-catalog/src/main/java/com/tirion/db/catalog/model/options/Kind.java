/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model.options;

public enum Kind {

	 TOKENIZE("tokenize"),
	 BM_INDEX("bmindex"),
	 MM_STATS("mm_stats"),
	 RANGE_STATS("range_stats"),
	 COMPRESS("compress"),
	 OFFHEAP("offheap"),
	 DISTINCT("distinct"),
	 ;
	 
	private final String name;

	private Kind(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static Kind parseFrom(String str) {
		for(Kind kind : values()) {
			if(kind.getName().equalsIgnoreCase(str)) {
				return kind;
			}
		}
		throw new IllegalArgumentException("Unable to parse option kind from string '" + str + "'");
	}
}
