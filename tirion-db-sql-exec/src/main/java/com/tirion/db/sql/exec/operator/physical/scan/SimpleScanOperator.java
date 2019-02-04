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
import com.tirion.db.sql.exec.operator.physical.scan.projector.AbstractProjector;
import com.tirion.db.sql.exec.operator.physical.scan.projector.Projector;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.FullTableScanRowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.stats.Statistics;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleScanOperator extends ScanOperator {

	@JsonProperty
	private String tableName;
	private MainOperation operation;
	private AbstractProjector projector;	
	private RowIdSource rowIdSource;
	
	private ScanOperatorStatistics stats = new ScanOperatorStatistics();

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setOperation(MainOperation operation) {
		this.operation = operation;
	}

	public void setProjector(Projector projector) {
		this.projector = (AbstractProjector) projector;
	}
	
	@Override
	@JsonProperty
	public Statistics getStatistics() {
		return stats;
	}

	@Override
	public void init() {
		projector.init();
	}

	@Override
	public void shutdown() {
		projector.shutdown();
		rowIdSource = null;
	}

	@Override
	protected Tuple nextInternal() {
		try {
			if(!isOperationDone()) {
				executeOperation();
			}
			Tuple tuple = projector.next();
			if(tuple == null) {
				setDone();
			}
			return tuple;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void executeOperation() {
		final long startTime = System.currentTimeMillis();
		if(operation != null) { // not a full table scan
			rowIdSource = operation.execute();
//			stats.setRowIdCount(rowIdSource.size());
		} else {
//			rowIdSource = new FullTableScanRowIdSource(pageSource, getTx().getScope(tableName).getMaxPageId());
			stats.setRowIdCount(-1);
		}
//		projector.setRowIdSource(rowIdSource);
		stats.setOperationExecDuration(System.currentTimeMillis() - startTime);;
	}
	
	private boolean isOperationDone() {
		return rowIdSource != null;
	}
}
