/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.builder.operation;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.sql.ast.common.constant.Constant;
import com.tirion.db.sql.ast.expression.bool.And;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.ast.expression.bool.Or;
import com.tirion.db.sql.ast.expression.compare.BetweenCompare;
import com.tirion.db.sql.ast.expression.compare.Compare;
import com.tirion.db.sql.ast.expression.compare.InCompare;
import com.tirion.db.sql.ast.expression.compare.SimpleCompare;
import com.tirion.db.sql.exec.operator.physical.scan.exec.AndOperation;
import com.tirion.db.sql.exec.operator.physical.scan.exec.IndexedOperation;
import com.tirion.db.sql.exec.operator.physical.scan.exec.MainOperation;
import com.tirion.db.sql.exec.operator.physical.scan.exec.Operation;
import com.tirion.db.sql.exec.operator.physical.scan.exec.OrOperation;
import com.tirion.db.sql.plan.builder.filter.PageFilterBuilder;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.index.Index.IndexKind;
import com.tirion.db.store.table.Table;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleOperationBuilder implements OperationBuilder {

	private final Job job;
	private final PageFilterBuilder pageFilterBuilder;
	
	public SimpleOperationBuilder(Job job) {
//		this.job = job;
//		pageFilterBuilder = new SimplePageFilterBuilder(job);
		throw new NotYetImplementedException();
	}

	@Override
	public MainOperation build(String tableName, BoolExpression expression) {
		throw new NotYetImplementedException();
//		try {
//			Table table = job.getRuntime().store().getTable(tableName);
//			Operation operation = buildInternalComposite(table, expression);
//			return new MainOperation(job, operation);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
	}
	private Operation buildInternalComposite(Table table, BoolExpression expression) {
		switch (expression.getKind()) {
			case AND_EXPRESSION:
				AndOperation and = new AndOperation(job);
				for(BoolExpression expr : ((And)expression).getChildren()) {
					and.append(buildInternalComposite(table, expr));
				}
				return and;
			case OR_EXPRESSION:
				OrOperation or = new OrOperation(job);
				for(BoolExpression expr : ((Or)expression).getChildren()) {
					or.append(buildInternalComposite(table, expr));
				}
				return or;
			default:
				return buildInternalCompare(table, (Compare)expression);
		}
	}

	private Operation buildInternalCompare(Table table, Compare compare) {
		Column column = table.getColumn(compare.getFieldRef().getName());
		if(column.hasIndex(IndexKind.BM)) {
			IndexedOperation operation = new IndexedOperation(job, column);
//			operation.setOperator(compare.getKind());
			switch (compare.getKind()) {
				case BETWEEN_OPERATOR:
					BetweenCompare between = (BetweenCompare) compare;
					operation.setLow(between.getRange().getLow());
					operation.setHigh(between.getRange().getHigh());
					break;
				case IN_OPERATOR:
					InCompare in = (InCompare) compare;
					operation.setSet(in.getSet().getSortedSet());
					break;
				default:
					SimpleCompare simpleCompare = (SimpleCompare) compare;
//					operation.setOperator(simpleCompare.getKind());
					Constant constant = (Constant) simpleCompare.getRight();
					operation.setValue(constant.getValue());
					break;
			}
			return operation;
		} else {
//			NonIndexedOperation operation = new NonIndexedOperation(job, column);
//			operation.setPageFilter((AbstractPageFilter)pageFilterBuilder.build(table.getName(), compare));
//			return operation;
			throw new NotYetImplementedException();
		}
	}
}
