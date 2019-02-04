/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine;

import java.io.File;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.file.FileUtil;
import com.tirion.db.catalog.Catalog;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.common.Config;
import com.tirion.db.common.Config.Key;
import com.tirion.db.engine.client.reqres.req.GenerateDsvFileRequest;
import com.tirion.db.engine.client.reqres.req.TextRequest;
import com.tirion.db.engine.client.reqres.req.TupleListenerAwareTextRequest;
import com.tirion.db.engine.client.reqres.res.BatchSelectResponse;
import com.tirion.db.engine.client.reqres.res.Response;
import com.tirion.db.engine.client.reqres.res.Response.Status;
import com.tirion.db.engine.client.reqres.res.SimpleResponse;
import com.tirion.db.engine.client.reqres.res.SingleValueResponse;
import com.tirion.db.engine.query.Query;
import com.tirion.db.engine.query.QueryId;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.create.CreateStatement;
import com.tirion.db.sql.ast.execute.ExecuteStatement;
import com.tirion.db.sql.ast.get.GetStatement;
import com.tirion.db.sql.ast.load.LoadStatement;
import com.tirion.db.sql.ast.select.SelectStatement;
import com.tirion.db.sql.ast.set.SetStatement;
import com.tirion.db.sql.exec.operator.physical.PhysicalOperator;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.sql.exec.tuple.sink.BufferingTupleListener;
import com.tirion.db.sql.exec.tuple.sink.TupleListener;
import com.tirion.db.sql.parser.Parser;
import com.tirion.db.sql.plan.Planner;
import com.tirion.db.store.Store;
import com.tirion.executor.job.Job;

/**
 * Single JVM engine. Pseudo cluster.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class PseudoEngine extends AbstractLeaderEngine {
	
	private static final Logger log = LoggerFactory.getLogger(PseudoEngine.class);
	
	private Catalog catalog;
	private Parser parser;
	private Planner planner;
	private Store store;
	
	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}
	
	@Override
	public Set<String> getTableNames() {
		return catalog.getTableNames();
	}
	
	@Override
	protected Response executeSet(TextRequest request) {
		SetStatement set = (SetStatement) parser.parse(request.getText());
		request.getSession().getConfig().set(Config.Key.parse(set.getName()), set.getValue());
		return new SimpleResponse(request);
	}
	
	@Override
	protected Response executeGet(TextRequest request) {
		GetStatement get = (GetStatement) parser.parse(request.getText());
		Object value = request.getSession().getConfig().get(Key.parse(get.getName()));
		return new SingleValueResponse(request, value);
	}
	
	@Override
	protected Response executeSelect(TextRequest request) {
		if(request instanceof TupleListenerAwareTextRequest) {
			TupleListenerAwareTextRequest tupleListenerAwareRequest = (TupleListenerAwareTextRequest) request;
			executeSelectInThreadSafeMode(request, tupleListenerAwareRequest.getListener());
			return new SimpleResponse(request);
		} else {
			BufferingTupleListener listener = new BufferingTupleListener();
			Entity entity = executeSelectInThreadSafeMode(request, listener);
			return new BatchSelectResponse(request, Status.OK, entity, listener.getArrays());
		}
	}
	
	private Entity executeSelectInThreadSafeMode(TextRequest request, TupleListener listener) {
		long start = System.currentTimeMillis();
		SelectStatement select = parse(request.getText(), SelectStatement.class);
		long parseDuration = System.currentTimeMillis() - start;
		
//		List<Scope> scopes = getRuntime().store().getScopes(select.getTableNames());
//		Tx tx = getRuntime().txManager().newTx(Kind.WRITE);
//		tx.register(scopes);	
//		TxJobContext ctx = new SimpleJobContext(request.getId(), tx, getRuntime());
		
		start = System.currentTimeMillis();
		PhysicalOperator root = planner.plan(null, select);
		long planDuration = System.currentTimeMillis() - start;
		
		start = System.currentTimeMillis();
		long tupleCount = executePhysicalOperator(root, listener);
		long execDuration = System.currentTimeMillis() - start;
		
		QueryId queryId = new QueryId(request.getSession().getId(), request.getId());
		getQueryListener().onQuery(new Query(queryId, parseDuration, planDuration, execDuration, tupleCount, request.getText(), select, root));
		
		return select.getOutput();
	}
	
	private long executePhysicalOperator(PhysicalOperator root, TupleListener listener) {
		root.init();
		try {
			long tupleCount = 0;
			while(true) {
				Tuple tuple = root.next();
				if(tuple == null) {
					break;
				}
				++tupleCount;
				listener.onTuple(tuple);
			}
			return tupleCount;
		} finally {
			root.shutdown();
		}
	}

	@Override
	protected Response executeLoad(TextRequest request) {
		LoadStatement load = parse(request.getText(), LoadStatement.class);
		String tableName = load.getTableName();
		
//		JobContext ctx = new SimpleJobContext(request.getId(), getRuntime());
		
//		Tx tx = getRuntime().txManager().newTx(Kind.WRITE);
//		tx.register(getRuntime().store().getScope(tableName));
		
//		tx.begin();
		try {
//			List<Arrays> arrays = getRuntime().fileLoader().loadDsvFiles(getRuntime().catalog().getEntity(tableName), 
//					FileUtil.asFiles(load.getFileNamesAsSet()), 
//					request.getSession().getConfig());
//			getRuntime().tableLoader().load(tableName, arrays);				
//			tx.commit();
			return new SimpleResponse(request);
		} catch(Throwable e) {
//			tx.rollback();
			return new SimpleResponse(request, Status.ERROR, "Exception during loading to table " + tableName, e);
		}
	}
	
	@Override
	protected Response executeExecute(TextRequest request) {
		ExecuteStatement execute = (ExecuteStatement) parser.parse(request.getText());
		String text = FileUtil.readTextFileFully(new File(execute.getFileName()));
		String[] statements = text.split(";");
		for (int i = 0; i < statements.length; i++) {
			String statement = statements[i].trim();
			if(statement.isEmpty()) {
				continue;
			}
			statement = statement + ";";
			Response response = execute(new TextRequest(request.getSession(), statement));
			if(response.getStatus() != Status.OK) {
				return response;
			}
		}
		return new SimpleResponse(request);
	}
	
	@Override
	protected Response executeCreate(TextRequest request) {
		CreateStatement create = (CreateStatement) parser.parse(request.getText());
		Entity entity = create.getEntity();
		log.warn("Creating table '" + entity.getName() + "' in lockless mode");
		catalog.register(entity);
		store.createTable(entity);
		return new SimpleResponse(request);
	}
	
	@Override
	protected Response executeGenerateDsvFile(GenerateDsvFileRequest request) {
		final int rowCount = request.getRowCount();
		final String tableName = request.getTableName();
		try {
			log.info("Preparing to generate " + rowCount + " rows to DSV file for table " + tableName);

			Job job = null;//new SimpleJob(request.getId(), getRuntime());
			
			GenericDsvGenerator generator = new GenericDsvGenerator(job, catalog.getEntity(tableName), rowCount);
			generator.call();
			
			log.info("Done generating " + rowCount + " rows to DSV file for table " + tableName);
			return new SimpleResponse(request);
		} catch (Exception e) {
			String message = "Exception while generating " + rowCount + " rows to DSV file for table " + tableName;
			log.error(message, e);
			return new SimpleResponse(request, Status.ERROR, message, e);
		}
	}

	@Override
	protected synchronized Response executeDrop(TextRequest request) {
		throw new NotYetImplementedException();
//		getRuntime().store().dropTable(tableName);
//		getRuntime().catalog().unregister(tableName);	
	}
	
	@Override
	protected synchronized Response executeInsert(TextRequest request) {
		throw new NotYetImplementedException();
//		InsertStatement insert = (InsertStatement) parser.parse(((TextRequest)request).getText());
//		String tableName = insert.getTableName();
//		locker.writeLock(tableName);
//		try {
////			execute(new TruncateTableCommand(tableName));
//			return null;
//		} finally {
//			locker.writeUnlock(tableName);
//		}
	}

	@Override
	protected synchronized Response executeDelete(TextRequest request) {
		throw new NotYetImplementedException();
//		DeleteStatement delete = (DeleteStatement) parser.parse(((TextRequest)request).getText());
//		String tableName = delete.getTableName();
//		locker.writeLock(tableName);
//		try {
////			execute(null);
//			return null;
//		} finally {
//			locker.writeUnlock(tableName);
//		}
	}

	@Override
	protected synchronized Response executeTruncate(TextRequest request) {
		throw new NotYetImplementedException();
//		TruncateStatement truncate = (TruncateStatement) parser.parse(((TextRequest)request).getText());
//		String tableName = truncate.getTableName();
//		locker.writeLock(tableName);
//		try {
//		getRuntime().store().getTable(tableName).truncate();/
//			execute(new TruncateTableCommand(tableName));
//			return new IncrementalTextResponse(Status.OK);
//		} finally {
//			locker.writeUnlock(tableName);
//		}
	}
	
	private <T> T parse(String text, Class<T> clazz) {
		Node node = parser.parse(text);
		return clazz.cast(node);
	}
	
//	private Response loadTrade(LoadTradeRequest request) {
//		int pageCount = request.getPageCount();
//		log.info("Preparding to load " + pageCount + " pages into trade table");
//		Entity entity = runtime.catalog().getEntity(Trade.NAME);
//		TradeSource source = new CardinalityAwareTradeSource(Constants.ROWS_PER_PAGE * pageCount, entity);
//		for (int i = 0; i < pageCount; i++) {
//			List<Trade> trades = new ArrayList<Trade>(Constants.ROWS_PER_PAGE);
//			for (int j = 0; j < Constants.ROWS_PER_PAGE; j++) {
//				Trade trade = source.next();
//				if(trade == null) {
//					throw new NullPointerException();
//				}
//				trades.add(trade);
//			}
//			Transposer<Trade> transposer = new TradeTransposer(entity);
//			List<Arrays> arrays = transposer.transpose(trades);
//			runtime.tableLoader().load(Trade.NAME, arrays);
//		}
//		return new BatchSelectResponse(Status.OK);
//	}
//	
//	private Response loadSpotTrade(LoadSpotTradeRequest request) {
//		int pageCount = request.getPageCount();
//		log.info("Preparding to load " + pageCount + " pages into spot_trade table");
//		Entity entity = runtime.catalog().getEntity(SpotTrade.NAME);
//		SpotTradeSource source = new CardinalityAwareSpotTradeSource(Constants.ROWS_PER_PAGE * pageCount, entity);
//		for (int i = 0; i < pageCount; i++) {
//			List<SpotTrade> trades = new ArrayList<SpotTrade>(Constants.ROWS_PER_PAGE);
//			for (int j = 0; j < Constants.ROWS_PER_PAGE; j++) {
//				SpotTrade trade = source.next();
//				if(trade == null) {
//					throw new NullPointerException();
//				}
//				trades.add(trade);
//			}
//			Transposer<SpotTrade> transposer = new SpotTradeTransposer(entity, runtime.pool());
//			List<Arrays> arrays = transposer.transpose(trades);
//			runtime.tableLoader().load(SpotTrade.NAME, arrays);
//		}
//		return new BatchSelectResponse(request, Status.OK);
//	}
//	
//	private Response generateRiskDsv(GenerateRiskDsv request) {
//		try {
//			int pageCount = request.getPageCount();
//			log.info("Preparding to generate " + pageCount + " pages to DSV files");
//			Entity entity = runtime.catalog().getEntity(Risk.NAME);
//			
//			RiskDsvGenerator gen = new RiskDsvGenerator(pageCount, entity);
//			gen.call();
//			return new BatchSelectResponse(Status.OK);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
}
