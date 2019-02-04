/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.Util;
import com.tirion.common.set.SimpleSmartSet;
import com.tirion.common.set.SmartSet;
import com.tirion.common.type.Type;
import com.tirion.db.catalog.model.function.Function;
import com.tirion.db.sql.ast.AbstractNode;
import com.tirion.db.sql.ast.Node;

/**
 * For now only field references & stars can be passed to functions. No
 * support to pass contants.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class FunctionCall extends AbstractNode implements AliasAware, FieldRefsAware, OwnerAware {
	
	private static final long serialVersionUID = 8359907860865231679L;
	
	private static final Set<Kind> ALLOWED_TYPES = new HashSet<Kind>();
	static {
		ALLOWED_TYPES.add(Kind.FIELD_REF);
//		ALLOWED_TYPES.add(Kind.CONSTANT);
		ALLOWED_TYPES.add(Kind.STAR);	
	}

	/**
	 * Fields derived via grouping functions will have this owner.
	 */
	public static final String FUNCTION_CALL_OWNER = "_<internal>_";
	
	@JsonProperty
	private String name;
	@JsonProperty
	private String alias;
	@JsonProperty
	private final List<Node> parameters;
	
	@Enriched
	@JsonProperty
	private Type returnType;
	
	@Enriched
	@JsonProperty
	private List<Type> parameterTypes;
	
	@Enriched
	@JsonProperty
	private Function function;
	
	public FunctionCall() {
		super();
		parameters = new ArrayList<Node>();
	}
	
	public FunctionCall(String name) {
		this();
		this.name = name.toLowerCase();
	}
	
	@Override
	public boolean hasOwner() {
		return true;
	}

	@Override
	public String getOwner() {
		return FUNCTION_CALL_OWNER;
	}

	public List<Type> getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(List<Type> parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public Function getFunction() {
		return function;
	}
	
	@Override
	public SmartSet<FieldRef> getRequiredFieldRefs() {
		SmartSet<FieldRef> set = new SimpleSmartSet<FieldRef>();
		for(Node param : parameters) {
			if(param.getKind() == Kind.FIELD_REF) {
				set.add((FieldRef)param);
			}
		}
		return set;
	}

	public void append(Node parameter) {
		if(!ALLOWED_TYPES.contains(parameter.getKind())) {
			throw new IllegalArgumentException("Function parameter in SELECT clause can not contain node of type '" + parameter.getKind()+ "'");
		}
		parameters.add(parameter);
		((AbstractNode)parameter).setParent(this);
	}	
	
	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}

	public int getParameterCount() {
		return parameters.size();
	}
	
	public Node getParameter(int index) {
		return getParameters().get(index);
	}
	
	public List<FieldRef> getFieldRefParams() {
		List<FieldRef> list = new ArrayList<FieldRef>();
		for(Node param : getParameters()) {
			if(param.getKind() == Kind.FIELD_REF) {
				list.add((FieldRef)param);
			}
		}
		return list;
	}
	
	public List<Node> getParameters() {
		return parameters;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}
	
	@Override
	public boolean hasAlias() {
		return alias != null && !alias.isEmpty();
	}

	@Override
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias.toLowerCase();
	}

	@Override
	public Kind getKind() {
		return Kind.FUNCTION_CALL;
	}

	@Override
	public String toSql() {
		String paramsString = "";
		for(Node parameter : parameters) {
			paramsString += parameter.toSql() + ",";
		}
		paramsString = Util.trimCommasDots(paramsString);
		return getOwner() + "." + getName() + "(" + paramsString + ")" + (hasAlias() ? " AS " + getAlias() : "");
	}
}
