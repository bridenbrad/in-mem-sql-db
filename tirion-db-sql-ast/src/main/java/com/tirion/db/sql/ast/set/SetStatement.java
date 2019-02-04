/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.set;

import com.tirion.db.sql.ast.AbstractNode;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SetStatement extends AbstractNode {

	private static final long serialVersionUID = 8044195357216231118L;
	
	private String name;
	private String value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public Kind getKind() {
		return Kind.SET_STATEMENT;
	}

	@Override
	public String toSql() {
		return "SET " + name + "=" + value;
	}
}
