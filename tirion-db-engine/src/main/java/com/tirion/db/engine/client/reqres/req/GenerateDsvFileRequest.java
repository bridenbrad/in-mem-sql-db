/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client.reqres.req;

import com.tirion.db.engine.client.Session;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class GenerateDsvFileRequest extends AbstractRequest {

	private static final long serialVersionUID = 8462662492856251046L;
	
	private final int rowCount;
	private final String tableName;
	
	public GenerateDsvFileRequest(Session session, int rowCount,
			String tableName) {
		super(session);
		this.rowCount = rowCount;
		this.tableName = tableName;
	}

	public int getRowCount() {
		return rowCount;
	}

	public String getTableName() {
		return tableName;
	}
}
