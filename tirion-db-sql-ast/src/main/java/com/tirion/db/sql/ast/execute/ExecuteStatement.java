/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.execute;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.ast.AbstractNode;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ExecuteStatement extends AbstractNode {

	private static final long serialVersionUID = -8694267337264664934L;

	@JsonProperty
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public Kind getKind() {
		return Kind.EXECUTE_STATEMENT;
	}

	@Override
	public String toSql() {
		return "EXECUTE FILE \"" + getFileName() + "\"";
	}
}
