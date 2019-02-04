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
public final class EntityRef extends AbstractNode implements Relation {

	private static final long serialVersionUID = -1697874792955928353L;

	@JsonProperty
	private String name;
	
	@Override
	public boolean isSelect() {
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	@Override
	public Kind getKind() {
		return Kind.ENTITY_REF;
	}

	@Override
	public String toSql() {
		return getName();
	}
}
