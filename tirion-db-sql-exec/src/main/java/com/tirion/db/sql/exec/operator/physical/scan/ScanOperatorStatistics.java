/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.stats.Statistics;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ScanOperatorStatistics implements Statistics {

	@JsonProperty
	private long operationExecDuration = -1;
	@JsonProperty
	private int rowIdCount = 0; // -1 means full table scan

	@Override
	public Kind getKind() {
		return Kind.SCAN_OPERATOR;
	}

	public long getOperationExecDuration() {
		return operationExecDuration;
	}

	public void setOperationExecDuration(long operationExecDuration) {
		this.operationExecDuration = operationExecDuration;
	}

	public int getRowIdCount() {
		return rowIdCount;
	}

	public void setRowIdCount(int rowIdCount) {
		this.rowIdCount = rowIdCount;
	}
}
