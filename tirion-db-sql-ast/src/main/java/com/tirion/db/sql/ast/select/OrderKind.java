/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public enum OrderKind {
	
	ASC,
	DESC,;
	
	public static OrderKind parseFromString(String value) {
		for(OrderKind kind : values()) {
			if(kind.name().equalsIgnoreCase(value)) {
				return kind;
			}
		}
		throw new IllegalArgumentException("Unable to build order kind from '" + value + "'");
	}
}