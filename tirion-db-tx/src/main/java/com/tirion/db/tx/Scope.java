/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.tx;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Scope {

	@JsonProperty
	private final String tableName;
	@JsonProperty
	private final int maxPageId;
	
	/**
	 * @param maxPageId exclusive
	 */
	public Scope(String tableName, int maxPageId) {
		this.maxPageId = maxPageId;
		this.tableName = tableName;
	}

	/**
	 * Exclusive.
	 */
	public int getMaxPageId() {
		return maxPageId;
	}

	public String getTableName() {
		return tableName;
	}

	@Override
	public String toString() {
		return "Scope[tableName=" + tableName + ",maxPageId=" + maxPageId + "]";
	}
}
