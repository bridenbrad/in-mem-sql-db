/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.common;

/**
 * LEFT or INNER.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public enum JoinType {
	
	LEFT,
	INNER,;
	
	public static JoinType fromString(String str) {
		for(JoinType joinType : values()) {
			if(joinType.name().equalsIgnoreCase(str)) {
				return joinType;
			}
		}
		throw new IllegalArgumentException("Unable to build join type from '" + str + "'");
	}
}