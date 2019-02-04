/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.common;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.ast.AbstractNode;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Star extends AbstractNode {

	private static final long serialVersionUID = -8860132762813947462L;
	
	// null in count(*), not null in 'SELECT a.*'
	@JsonProperty
	private String owner;

	public boolean hasOwner() {
		return owner != null;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public Kind getKind() {
		return Kind.STAR;
	}

	@Override
	public String toSql() {
		return (hasOwner() ? getOwner() + "." : "") + "*";
	}
}
