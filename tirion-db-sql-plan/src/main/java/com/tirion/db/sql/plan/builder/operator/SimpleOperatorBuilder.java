/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.sql.plan.builder.operator;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import com.tirion.common.Pair;
//import com.tirion.common.type.Type;
//import com.tirion.compiler.Result;
//import com.tirion.compiler.source.Source;
//import com.tirion.db.catalog.model.Entity;
//import com.tirion.db.catalog.model.Field;
//import com.tirion.db.sql.ast.Fields;
//import com.tirion.db.sql.ast.Node;
//import com.tirion.db.sql.ast.Node.Kind;
//import com.tirion.db.sql.ast.SimpleFields;
//import com.tirion.db.sql.ast.common.EntityRef;
//import com.tirion.db.sql.ast.common.FieldRef;
//import com.tirion.db.sql.ast.common.FunctionCall;
//import com.tirion.db.sql.ast.common.Relation;
//import com.tirion.db.sql.ast.expression.bool.BoolExpression;
//import com.tirion.db.sql.ast.expression.compare.InCompare;
//import com.tirion.db.sql.ast.select.HavingClause;
//import com.tirion.db.sql.ast.select.Join;
//import com.tirion.db.sql.ast.select.OrderByClause;
//import com.tirion.db.sql.ast.select.OrderKind;
//import com.tirion.db.sql.ast.select.SelectStatement;
//import com.tirion.db.sql.ast.select.WhereClause;
//import com.tirion.db.sql.exec.operator.physical.AbstractPhysicalOperator;
//import com.tirion.db.sql.exec.operator.physical.DualSourcePhysicalOperator;
//import com.tirion.db.sql.exec.operator.physical.PhysicalOperator;
//import com.tirion.db.sql.exec.operator.physical.SingleSourcePhysicalOperator;
//import com.tirion.db.sql.exec.operator.physical.common.key.KeyBuilder;
//import com.tirion.db.sql.exec.operator.physical.common.key.NullAwareKeyBuilder;
//import com.tirion.db.sql.exec.operator.physical.filter.FilterOperator;
//import com.tirion.db.sql.exec.operator.physical.filter.TupleFilter;
//import com.tirion.db.sql.exec.operator.physical.filter.TuplesFilter;
//import com.tirion.db.sql.exec.operator.physical.groupby.GroupByOperator;
//import com.tirion.db.sql.exec.operator.physical.groupby.hash.HashGroupByOperator;
//import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.AbstractFunctor;
//import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.Functors;
//import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.FunctorsFactory;
//import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.SimpleFunctorsFactory;
//import com.tirion.db.sql.exec.operator.physical.groupby.hash.grouper.Grouper;
//import com.tirion.db.sql.exec.operator.physical.groupby.hash.grouper.ParallelGrouper;
//import com.tirion.db.sql.exec.operator.physical.groupby.hash.grouper.SimpleGrouper;
//import com.tirion.db.sql.exec.operator.physical.join.HashJoinOperator;
//import com.tirion.db.sql.exec.operator.physical.join.NestedLoopJoinOperator;
//import com.tirion.db.sql.exec.operator.physical.join.merger.SimpleTupleMerger;
//import com.tirion.db.sql.exec.operator.physical.limit.LimitOperator;
//import com.tirion.db.sql.exec.operator.physical.orderby.InMemoryOrderByOperator;
//import com.tirion.db.sql.exec.operator.physical.orderby.LimitAwareInMemoryOrderByOperator;
//import com.tirion.db.sql.exec.operator.physical.orderby.OrderByOperator;
//import com.tirion.db.sql.exec.operator.physical.root.RootOperator;
//import com.tirion.db.sql.exec.operator.physical.root.converter.AbstractConverter;
//import com.tirion.db.sql.exec.operator.physical.root.converter.Converter;
//import com.tirion.db.sql.exec.operator.physical.scan.ScanOperator;
//import com.tirion.db.sql.exec.operator.physical.scan.SimpleScanOperator;
//import com.tirion.db.sql.exec.operator.physical.scan.exec.MainOperation;
//import com.tirion.db.sql.exec.operator.physical.scan.projector.SmartProjector;
//import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.ValueExtractor;
//import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.ValueExtractors;
//import com.tirion.db.sql.exec.tuple.Location;
//import com.tirion.db.sql.exec.tuple.comparator.TupleComparator;
//import com.tirion.db.sql.plan.builder.operation.OperationBuilder;
//import com.tirion.db.sql.plan.builder.operation.SimpleOperationBuilder;
//import com.tirion.db.sql.plan.generator.FilterGenerator;
//import com.tirion.db.sql.plan.generator.SimpleFilterGenerator;
//import com.tirion.db.store.page.finder.PageFinder;
//import com.tirion.db.store.table.Table;
//import com.tirion.db.tx.Tx;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class SimpleOperatorBuilder implements OperatorBuilder {
//	
//	private final TxJobContext jobContext;
//	private final AtomicInteger nodeIdCounter;
//	private final OperationBuilder operationBuilder;
//
//	public SimpleOperatorBuilder(TxJobContext jobContext) {
//		this.jobContext = jobContext;
//		operationBuilder = new SimpleOperationBuilder(jobContext);
//		nodeIdCounter = new AtomicInteger();
//	}
//	
//	/**
//	 * Should be called once per top-level query.
//	 */
//	@Override
//	public PhysicalOperator build(SelectStatement select) {
//		try {
//			RootOperator root = buildRootOperator(select);
//			root.setSource(buildInternal(select));
//			root.setConverters(buildRootConverters(select.getOutput()));
//			return root;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	private RootOperator buildRootOperator(SelectStatement select) throws Exception {
//		RootOperator root = new RootOperator();
//		injectCommon(root);
//		if(select.getInput() != null) {
//			List<Location> locations = new ArrayList<Location>();
//			Fields fields = select.getInput();
//			for(FieldRef fieldRef : select.getSelectClause().getRequiredFieldRefs().get()) {
//				Field field = fields.getField(fieldRef);
//				locations.add(new Location(field.getIndex(), field.getDeclaredType()));
//			}
//			root.setLocations(locations.toArray(new Location[]{}));
//		}
//		return root;
//	}
//	
//	private Converter[] buildRootConverters(Entity entity) {
//		List<Location> dateLocations = new ArrayList<Location>();
//		List<Location> timeLocations = new ArrayList<Location>();
//		List<Location> timestampLocations = new ArrayList<Location>();
//		int index = 0;
//		for(Field field : entity.getFields()) {
//			final Type type = field.getDeclaredType();
//			switch (type) {
//				case DATE:
//					dateLocations.add(new Location(index, Type.DATE));
//					break;
//				case TIME:
//					timeLocations.add(new Location(index, Type.TIME));
//					break;
//				case TIMESTAMP:
//					timestampLocations.add(new Location(index, Type.TIMESTAMP));
//					break;
//				default:
//					break;
//			}
//			++index;
//		}
//		List<Converter> converters = new ArrayList<Converter>();
//		if(!dateLocations.isEmpty()) {			
//			converters.add(new AbstractConverter.DateConverter(dateLocations.toArray(new Location[]{})));
//		}
//		if(!timeLocations.isEmpty()) {			
//			converters.add(new AbstractConverter.TimeConverter(timeLocations.toArray(new Location[]{})));
//		}
//		if(!timestampLocations.isEmpty()) {			
//			converters.add(new AbstractConverter.TimestampConverter(timestampLocations.toArray(new Location[]{})));
//		}
//		
//		if(converters.isEmpty()) {
//			return null;
//		} else {			
//			return converters.toArray(new Converter[]{});
//		}
//		
//	}
//	
//	/**
//	 * Should be recursively called for subselects.
//	 */
//	private PhysicalOperator buildInternal(SelectStatement select) throws Exception {	
//		if(select.hasWhereClause()) {
//			for(InCompare in : select.getWhereClause().getInSubSelects()) {
//				in.setOperator(buildInternal(in.getSelect()));
//			}
//		}
//		if(select.hasJoinClause()) {
//			return buildWithJoins(select);
//		} else {
//			return buildWithoutJoins(select);
//		}
//	}
//	
//	private PhysicalOperator buildWithJoins(SelectStatement select) throws Exception {
//		final boolean hasWhere = select.hasWhereClause();
//		final WhereClause where = select.getWhereClause();
//		final boolean pushable = (hasWhere ? where.filtersArePushable() : false);
//				
//		Fields entity = new SimpleFields(select.getFromClause().getEntity());
//		PhysicalOperator left = buildScanOperatorFor(
//				select.getFromClause().getRelation(), 
//				hasWhere && pushable ? where.getPushableFiltersFor(select.getFromClause().getAlias()) : null, 
//				select.getProjectionsFor(select.getFromClause().getAlias()));	
//		
//		PhysicalOperator joinMaster = null; // root of join subtree that will be linked to other nodes
//		for(Join join : select.getJoinClause().getJoins()) {
//			PhysicalOperator right = buildScanOperatorFor(
//					join.getRelation(), 
//					hasWhere && pushable && join.isInner() ? where.getPushableFiltersFor(join.getAlias()) : null, 
//					select.getProjectionsFor(join.getAlias()));
//			DualSourcePhysicalOperator joined = null;
//			if(join.isHashCandidate()) {
//				joined = buildHashJoinOperator(join);
//			} else {
//				joined = buildNesterLoopJoinOperator(join);
//			}
//			injectCommon(joined);
//			joined.setLeftSource(left);
//			joined.setRightSource(right);
//			left = joined;
//			entity = entity.merge(join.getRightInput());
//			if(hasWhere && pushable && !join.isInner()) { // post JOIN filter for right relation
//				FilterOperator filter = buildFilterOperator(entity, where.getPushableFiltersFor(join.getAlias()));
//				filter.setSource(left);
//				left = filter;
//			}
//		}
//		joinMaster = left;
//		if(hasWhere && !pushable) { // one filter for entire WHERE clause expression
//			FilterOperator filter = buildFilterOperator(entity, where.getExpression());
//			filter.setSource(joinMaster);
//			joinMaster = filter;
//		}
//		SingleSourcePhysicalOperator orderByLimitCombo = buildLimitOrderByCombinedOperator(select);
//		FilterOperator having = buildHavingOperator(select);
//		GroupByOperator groupBy = buildGroupByOperator(select);
//		return wireOperators(orderByLimitCombo, having, groupBy, joinMaster);
//	}
//	
//	private PhysicalOperator buildWithoutJoins(SelectStatement select) throws Exception {	
//		SingleSourcePhysicalOperator orderByLimitCombo = buildLimitOrderByCombinedOperator(select);
//		FilterOperator having = buildHavingOperator(select);
//		GroupByOperator groupBy = buildGroupByOperator(select);
//		PhysicalOperator scan = buildScanOperatorFor(
//				select.getFromClause().getRelation(), 
//				select.hasWhereClause() ? select.getWhereClause().getExpression() : null, 
//				select.getProjectionsFor(select.getFromClause().getAlias()));
//		
//		return wireOperators(orderByLimitCombo, having, groupBy, scan);
//	}
//	
//	private PhysicalOperator wireOperators(
//			SingleSourcePhysicalOperator orderByLimitCombo, 
//			SingleSourcePhysicalOperator having, 
//			SingleSourcePhysicalOperator groupBy, 
//			PhysicalOperator leaf) {
//		PhysicalOperator current = leaf;
//		if(groupBy != null) {
//			groupBy.setSource(current);
//			current = groupBy;
//		}
//		if(having != null) {
//			having.setSource(current);
//			current = having;
//		}
//		if(orderByLimitCombo != null) {
//			orderByLimitCombo.setSource(current);
//			current = orderByLimitCombo;
//		}
//		return current;
//	}
//	
//	private DualSourcePhysicalOperator buildHashJoinOperator(Join join) {
//		HashJoinOperator operator = new HashJoinOperator();
//		injectCommon(operator);
//		operator.setJoinType(join.getJoinType());
//		operator.setLeftKeyBuilder( buildKeyBuilder(join.getLeftInput(),  join.getLeftJoinConditionFieldRefs()));
//		operator.setRightKeyBuilder(buildKeyBuilder(join.getRightInput(), join.getRightJoinConditionFieldRefs()));
//		operator.setTupleMerger(new SimpleTupleMerger());
//		return operator;
//	}
//	
//	@SuppressWarnings("unchecked")
//	private DualSourcePhysicalOperator buildNesterLoopJoinOperator(Join join) throws Exception {
//		FilterGenerator builder = new SimpleFilterGenerator(true);
//		Source source = builder.generateTuplesFilter(join.getLeftInput(), join.getRightInput(), join.getCondition());
//		Result result = job.getRuntime().compiler().compile(source);
//		if(result.getStatus() != Result.Status.OK) {
//			throw new RuntimeException("Exception while compiling Java code '" + result.getMessages() + "'");
//		}
//		Class<TuplesFilter> clazz = (Class<TuplesFilter>) result.getClazz();
//		
//		NestedLoopJoinOperator operator = new NestedLoopJoinOperator();
//		injectCommon(operator);
//		operator.setJoinType(join.getJoinType());
//		operator.setFilter(clazz.newInstance());
//		operator.setTupleMerger(new SimpleTupleMerger());
//		
//		return operator;
//	}
//	
//	/**
//	 * Builds {@link TupleFilter} backed {@link FilterOperator}.
//	 */
//	@SuppressWarnings("unchecked")
//	private FilterOperator buildFilterOperator(Fields entity, BoolExpression expression) throws Exception {
//		FilterGenerator generator = new SimpleFilterGenerator(false);
//		Source source = generator.generateTupleFilter(entity, expression);
//		Result result = jobContext.getRuntime().compiler().compile(source);
//		if(result.getStatus() != Result.Status.OK) {
//			throw new RuntimeException("Exception while compiling Java code '" + result.getMessages() + "'");
//		}
//		Class<TupleFilter> clazz = (Class<TupleFilter>) result.getClazz();
//		FilterOperator operator = new FilterOperator();
//		injectCommon(operator);
//		operator.setFilter(clazz.newInstance());
//		return operator;
//	}
//	
//	private FilterOperator buildHavingOperator(SelectStatement select) throws Exception {
//		if(!select.hasHavingClause()) {
//			return null;
//		}
//		HavingClause having = select.getHavingClause();
//		return buildFilterOperator(having.getInput(), having.getExpression());
//	}
//	
//	private SingleSourcePhysicalOperator buildLimitOrderByCombinedOperator(SelectStatement select) {
//		if(!select.hasOrderByClause()) {
//			return buildLimitOperator(select);
//		}
//		OrderByOperator orderByOperator = null;
//		if (select.hasLimitClause()) {
//			orderByOperator = new LimitAwareInMemoryOrderByOperator();
//			((LimitAwareInMemoryOrderByOperator) orderByOperator).setLimit(select.getLimitClause().getValue());
//		} else {
//			orderByOperator = new InMemoryOrderByOperator();
//		}
//		injectCommon(orderByOperator);
//		orderByOperator.setComparator(buildTupleComparator(select));
//		return orderByOperator;
//	}
//	
//	private TupleComparator buildTupleComparator(SelectStatement select) {
//		OrderByClause order = select.getOrderByClause();
//		TupleComparator comparator = new TupleComparator();
//		for (Pair<FieldRef, OrderKind> pair : order.getSmartFields()) {
//			Field field = order.getInput().getField(pair.getFirst().getName());
//			comparator.append(TupleComparator.newOrderer(
//					field.getNativeTypeHack(), 
//					field.getIndex(),
//					pair.getSecond() == OrderKind.ASC)
//			);
//		}
//		return comparator;
//	}
//	
//	private SingleSourcePhysicalOperator buildLimitOperator(SelectStatement select) {
//		if(!select.hasLimitClause()) {
//			return null;
//		}
//		LimitOperator limit = new LimitOperator();
//		injectCommon(limit);
//		limit.setMaxCount(select.getLimitClause().getValue());
//		return limit;
//	}
//	
//	private GroupByOperator buildGroupByOperator(SelectStatement select) {
//		if(!select.hasGroupByClause()) {
//			return null;
//		}
//		HashGroupByOperator groupBy = new HashGroupByOperator();
//		injectCommon(groupBy);
//		
//		KeyBuilder keyBuilder = buildKeyBuilder(select.getGroupByClause().getInput(), select.getGroupByClause().getFields());
//		Functors functors = buildFunctors(select);
//		
//		final int groupByBuckets = jobContext.getRuntime().configuration().getGroupByOperatorParallelBuckets();
//		final int outputFieldCount = keyBuilder.getPartCount() + functors.getCount();
//		final FunctorsFactory factory = new SimpleFunctorsFactory(functors);
//		Grouper grouper = null;
//		if(groupByBuckets == 1) {
//			grouper = new SimpleGrouper(outputFieldCount, factory);
//		} else {
//			grouper = new ParallelGrouper(
//					groupByBuckets, 
//					outputFieldCount,
//					factory,
//					jobContext);
//		}
//		groupBy.setGrouper(grouper);
//		groupBy.setKeyBuilder(keyBuilder);
//		
//		return groupBy;
//	}
//	
//	private Functors buildFunctors(SelectStatement select) {
//		Fields input = select.getGroupByClause().getInput();
//		Fields output = select.getGroupByClause().getOutput();
//		Functors functors = new Functors();
//		for (Node column : select.getSelectClause().getColumns()) {
//			if (column.getKind() == Kind.FUNCTION_CALL) {
//				FunctionCall functionCall = (FunctionCall) column;
//				Node param = functionCall.getParameters().get(0); // single parameter functions only for now
//				AbstractFunctor functor = null;
//				if(param.getKind() == Kind.FIELD_REF) {
//					FieldRef fieldRef = (FieldRef) param;
//					functor = (AbstractFunctor) functionCall.getFunction().buildFunctor(
//							functionCall.getParameterTypes(), 
//							input.getField(fieldRef).getIndex(), 
//							output.getField(functionCall.getAlias()).getIndex());
//				} else {
//					functor = (AbstractFunctor) functionCall.getFunction().buildFunctor(
//							functionCall.getParameterTypes(), 
//							Integer.MIN_VALUE, 
//							output.getField(functionCall.getAlias()).getIndex());
//				}
//				functors.append(functor);
//			}
//		}
//		return functors;
//	}
//	
//	private KeyBuilder buildKeyBuilder(Fields entity, Collection<FieldRef> fieldRefs) {
//		List<Location> locations = new ArrayList<Location>();
//		for(FieldRef fieldRef : fieldRefs) {
//			Field field = entity.getField(fieldRef.getOwner(), fieldRef.getName());
//			locations.add(new Location(field.getIndex(), field.getNativeTypeHack()));
//		}
//		return new NullAwareKeyBuilder(locations.toArray(new Location[]{}));
//	}
//	
//	/**
//	 * Builds scan operator for given relation. If relation is sub-select, root will be {@link FilterOperator}. 
//	 * Otherwise it will be {@link ScanOperator}.
//	 */
//	private PhysicalOperator buildScanOperatorFor(Relation relation, BoolExpression expression, List<Field> projections) throws Exception {
//		if(relation.isSelect()) {
//			// TODO push down of filters into sub-select
//			SelectStatement select = (SelectStatement) relation;
//			PhysicalOperator operator = buildInternal(select);
//			if(expression != null) {
//				FilterOperator filter = buildFilterOperator(new SimpleFields(select.getOutput()), expression);	
//				filter.setSource(operator);
//				operator = filter;
//			}
//			return operator;
//		} else { // physical table
//			String tableName = ((EntityRef) relation).getName();
//			
//			SmartProjector projector = new SmartProjector();
//			projector.setValueExtractors(buildValueExtractors(tableName, projections));
//			
//			MainOperation operation = null;
//			if(expression != null) {
//				operation = operationBuilder.build(tableName, expression);
//			}
//			
//			SimpleScanOperator scan = new SimpleScanOperator();
//			injectCommon(scan);
//			scan.setOperation(operation);
//			scan.setProjector(projector);
//			scan.setTableName(tableName);
//			scan.setPageSource(jobContext.getRuntime().store().getTable(tableName).getColumns()[0].getPageSource()); // TODO hackish
//			return scan;
//		}
//	}
//	
//	/**
//	 * @param fields to project
//	 */
//	private ValueExtractors buildValueExtractors(String tableName, List<Field> fields) {
//		int index = 0;
//		ValueExtractor[] extractors = new ValueExtractor[fields.size()];
//		Table table = jobContext.getRuntime().store().getTable(tableName);
//		for (int i = 0; i < fields.size(); i++) {
//			final Field field = fields.get(i);
//			PageFinder pageFinder = table.getColumn(field.getName()).getPageFinder();
//			extractors[i] = ValueExtractors.newExtractor(field.getDeclaredType(), index, pageFinder, jobContext.getRuntime());
//			++index;
//		}
//		return new ValueExtractors(extractors);
//	}
//
//	/**
//	 * Injects node ID, {@link Runtime} & {@link Tx}.
//	 */
//	private void injectCommon(AbstractPhysicalOperator operator) {
//		operator.setId(nextNodeId());
//		operator.setJobContext(jobContext);
//	}
//	
//	/**
//	 * Returns next free node id.
//	 */
//	private int nextNodeId() {
//		return nodeIdCounter.getAndIncrement();
//	}
//}
