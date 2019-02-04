/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model;

/**
 * Large or small.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public enum EntityType {

	LARGE,
	SMALL,;
	
	public static EntityType parseFromString(String value) {
		for(EntityType entityType : values()) {
			if(entityType.name().equalsIgnoreCase(value)) {
				return entityType;
			}
		}
		throw new IllegalArgumentException("Unable to build entity type from '" + value + "'");
	}
}
