/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.common;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.type.Type;

/**
 * Field reference may not have owner only in case it is GROUP
 * BY clause derived field.<p>
 * 
 * Implements hashCode & equals via name & owner fields only.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class FieldRef extends Value implements AliasAware, OwnerAware {

	private static final long serialVersionUID = -8278582785360335396L;
	
	@JsonProperty
	private String name;
	@JsonProperty
	private String alias;
	@JsonProperty
	private String owner; // table or event that owns it
	
	@Enriched
	@JsonProperty
	private Type type; // will be deduced
	
	public FieldRef() {
		super();
	}

	public FieldRef(String name) {
		this.name = name.toLowerCase();
	}

	@Override
	public boolean hasAlias() {
		return alias != null;
	}
	
	@Override
	public Type getType() {
		if(type == null) {
			throw new IllegalStateException();
		}
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean hasOwner() {
		return owner != null;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		if(owner != null) {			
			this.owner = owner.toLowerCase();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	@Override
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		if(alias != null) {			
			this.alias = alias.toLowerCase();
		}
	}
	
	/**
	 * True if names & owners are same.
	 */
	public boolean areSame(FieldRef field) {
		if(!field.getName().equalsIgnoreCase(getName())) {
			return false;
		}
		if(!hasOwner() && !field.hasOwner()) {
			return true;
		}
		if(!hasOwner() || !field.hasOwner()) {
			return false;
		}
		return getOwner().equalsIgnoreCase(field.getOwner());
	}

	@Override
	public Kind getKind() {
		return Kind.FIELD_REF;
	}

	@Override
	public String toString() {
		return "FieldReference [owner=" + owner + ", name=" + name + ", alias="
				+ alias + ", type=" + type + "]";
	}
	
	@Override
	public String toSql() {
		return (hasOwner() ? getOwner() + "." : "") 
				+ getName() 
				+ (hasAlias() ? " AS " + getAlias() : "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldRef other = (FieldRef) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}
}
