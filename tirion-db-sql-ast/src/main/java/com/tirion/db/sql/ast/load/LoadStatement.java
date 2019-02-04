/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.load;

import java.util.HashSet;
import java.util.Set;

import com.tirion.db.sql.ast.AbstractNode;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class LoadStatement extends AbstractNode {
	
	private static final long serialVersionUID = -5700695142326653476L;
	
	private String tableName;
	private String fileNames;
	
//	private String separator;
//	private String workerName;
//	
//	private String dateFormat;
//	private String timeFormat;
//	private String timestampFormat;
	
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * Tokenizes underlying string by using ; separator.
	 */
	public Set<String> getFileNamesAsSet() {
		Set<String> fileNames = new HashSet<String>();
		for(String fileName : getFileNames().split(";")) {
			fileNames.add(fileName);
		}
		return fileNames;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}

	@Override
	public Kind getKind() {
		return Kind.LOAD_STATEMENT;
	}

	@Override
	public String toSql() {
		return "LOAD TABLE " + getTableName() + " FROM FILE \"" + getFileNames() + "\";";
	}
}
