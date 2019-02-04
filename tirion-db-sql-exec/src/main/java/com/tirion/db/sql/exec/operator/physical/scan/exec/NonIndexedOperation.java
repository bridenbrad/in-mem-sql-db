/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
///**
// * Copyright © 2013, drekthar
// * All rights reserved.
// *
// * No portion of this file may be reproduced in any form, or by any means, without the prior written
// * consent of the author.
// */
//package com.tirion.db.sql.exec.operator.physical.scan.exec;
//
//import com.tirion.db.sql.exec.operator.physical.scan.filter.page.AbstractPageFilter;
//import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;
//import com.tirion.db.store.column.Column;
//import com.tirion.executor.job.Job;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class NonIndexedOperation extends BasicOperation {
//	
//	private AbstractPageFilter pageFilter;
//	
//	public NonIndexedOperation(Job job, Column column) {
//		super(job, column);
//	}
//
//	public void setPageFilter(AbstractPageFilter pageFilter) {
//		this.pageFilter = pageFilter;
//	}
//	
//	public RowIdSource execute(RowIdSource filter) {
//		pageFilter.setColumn(getColumn());
//		if(filter != null) {
//			pageFilter.setFilter(filter);
//		}
//		pageFilter.apply();
//		RowIdSource rowIdSource = pageFilter.getRowIdSource();
//		if(rowIdSource == null || rowIdSource.isEmpty()) {
//			return null;
//		}
//		return rowIdSource;
//	}
//}
