/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.create;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.sql.ast.AbstractNode;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class CreateStatement extends AbstractNode {

	private static final long serialVersionUID = -6971642306094798368L;

	@JsonProperty
	private Entity entity;
	
	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public Kind getKind() {
		return Kind.CREATE_STATEMENT;
	}

	@Override
	public String toSql() {
		String columnList = "";
		for(Field field : entity.getFields()) {
			columnList += field.getName() + " " + field.getDeclaredType().toString();
			columnList += " " + (field.getOptions().isNullable() ? "NULL" : "NOT NULL");
			columnList += ",\n";
		}
		return "CREATE TABLE " + entity.getName() + "( "
				+ columnList + ") AS " + entity.getType().toString();
	}
}
