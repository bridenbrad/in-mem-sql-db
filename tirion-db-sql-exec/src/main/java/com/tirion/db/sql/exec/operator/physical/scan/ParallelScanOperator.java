/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.exec.operator.physical.scan.exec.MainOperation;
import com.tirion.db.sql.exec.operator.physical.scan.projector.ParallelProjector;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.FullTableScanRowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.split.SplittableRowIdSource;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ParallelScanOperator extends ScanOperator {

	@JsonProperty
	private String tableName;
	private MainOperation operation;
	private ParallelProjector projector;
	
	private SplittableRowIdSource rowIdSource;
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setOperation(MainOperation operation) {
		this.operation = operation;
	}

	public void setProjector(ParallelProjector projector) {
		this.projector = projector;
	}

	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
		rowIdSource = null;
	}

	@Override
	protected Tuple nextInternal() {
		if(operation != null) {
			if(!isOperationDone()) {
				executeOperation();
				projector.setJob(null);
				projector.setSource(rowIdSource);
			}			
		} else {
//			rowIdSource = new FullTableScanRowIdSource(pageSource, getTx().getScope(tableName).getMaxPageId());
		}
		Tuple tuple = projector.next();
		if(tuple == null) {
			setDone();
		}
		return tuple;
	}
	
	private void executeOperation() {
		rowIdSource = operation.execute();
	}

	private boolean isOperationDone() {
		return rowIdSource != null;
	}
}
