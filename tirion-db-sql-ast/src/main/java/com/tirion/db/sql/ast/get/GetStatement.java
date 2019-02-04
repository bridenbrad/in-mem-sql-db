/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.get;

import com.tirion.db.sql.ast.AbstractNode;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class GetStatement extends AbstractNode {

	private static final long serialVersionUID = 3134520620376912725L;
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Kind getKind() {
		return Kind.GET_STATEMENT;
	}

	@Override
	public String toSql() {
		return "GET " + name + ";";
	}
}
