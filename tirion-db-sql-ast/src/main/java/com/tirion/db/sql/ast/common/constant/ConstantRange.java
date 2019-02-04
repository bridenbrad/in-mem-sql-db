/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.common.constant;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.ast.common.Enriched;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ConstantRange extends BaseConstant {

	private static final long serialVersionUID = 6060240694521013504L;
	
	@Enriched // string will be replaced with value of correct type
	@JsonProperty
	private Object low;
	
	@Enriched // string will be replaced with value of correct type
	@JsonProperty
	private Object high;

	public Object getLow() {
		return low;
	}

	public void setLow(Object low) {
		this.low = low;
	}
	
	public Object getHigh() {
		return high;
	}

	public void setHigh(Object high) {
		this.high = high;
	}

	@Override
	public Kind getKind() {
		return Kind.CONSTANT_RANGE;
	}

	@Override
	public String toSql() {
		return low + " AND " + high;
	}
}
