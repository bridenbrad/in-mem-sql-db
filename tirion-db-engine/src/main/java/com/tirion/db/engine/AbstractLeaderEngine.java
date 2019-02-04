/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.engine.client.CloseAwareSession;
import com.tirion.db.engine.client.Session;
import com.tirion.db.engine.client.SessionCloseListener;
import com.tirion.db.engine.client.reqres.req.GenerateDsvFileRequest;
import com.tirion.db.engine.client.reqres.req.Request;
import com.tirion.db.engine.client.reqres.req.TextRequest;
import com.tirion.db.engine.client.reqres.res.Response;
import com.tirion.db.engine.client.reqres.res.Response.Status;
import com.tirion.db.engine.client.reqres.res.SimpleResponse;
import com.tirion.db.engine.query.JsonLoggingQueryListener;
import com.tirion.db.engine.query.QueryListener;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractLeaderEngine implements LeaderEngine {

	private static final Logger log = LoggerFactory.getLogger(AbstractLeaderEngine.class);
	
	// key is session ID
	private final Map<Long, Session> sessions = new ConcurrentHashMap<Long, Session>();
	private final AtomicLong sessionIdSource = new AtomicLong(0);
	
	private QueryListener queryListener;
	
	private Runtime runtime;
	
	public final void setRuntime(Runtime runtime) {
		this.runtime = runtime;
	}
	
	public final void setWorkDir(String workDir) {
		queryListener = new JsonLoggingQueryListener(workDir + File.separator + "query-plans");
	}
	
	protected final Runtime getRuntime() {
		return runtime;
	}
	
	protected final QueryListener getQueryListener() {
		return queryListener;
	}

	@Override
	public final synchronized Session connect(String name) {
		long newSessionId = sessionIdSource.incrementAndGet();
		log.info("Allocated session ID " + newSessionId + " to client with name '" + name + "'");
		CloseAwareSession session = new CloseAwareSession(newSessionId); 
		session.register(new InternalSessionCloseListener());
		Session old = sessions.put(newSessionId, session);
		if(old != null) {
			throw new IllegalStateException("Bug in code. Duplicate session ID " + newSessionId 
					+ " encounted while create session for client with name '" + name + "'");
		}
		log.info("Default configuration for session ID " + newSessionId + " is " + session.getConfig().toString());
		return session;
	}
	
	@Override
	public final Response execute(Request request) {
		if(log.isInfoEnabled()) {			
			log.info("Received request " + request.getId() + " for session " + request.getSession().getId());
		}
		Response response = executeInternal(request);
		if(log.isInfoEnabled()) {			
			log.info("Done processing request " + request.getId() + " for session " + request.getSession().getId());
		}
		return response;
	}
	
	private Response executeInternal(Request request) {
		try {
			if(request instanceof GenerateDsvFileRequest) {
				return executeGenerateDsvFile((GenerateDsvFileRequest)request);
			}
			if(request instanceof TextRequest) {	
				TextRequest textRequest = (TextRequest) request;
				if(log.isInfoEnabled()) {
					log.info("Preparing to execute '" + textRequest.toString() + "'");
				}
				final String sql = textRequest.getText();
				if(sql.startsWith("SELECT")) {
					return executeSelect(textRequest);
				} else if(sql.startsWith("CREATE")) {
					return executeCreate(textRequest);
				} else if(sql.startsWith("DROP")) {
					return executeDrop(textRequest);
				} else if(sql.startsWith("LOAD")) {
					return executeLoad(textRequest);
				} else if(sql.startsWith("INSERT")) {
					return executeInsert(textRequest);
				} else if(sql.startsWith("DELETE")) {
					return executeDelete(textRequest);
				} else if(sql.startsWith("TRUNCATE")) {
					return executeTruncate(textRequest);
				} else if(sql.startsWith("EXECUTE")) {
					return executeExecute(textRequest);
				} else if(sql.startsWith("SET")) {
					return executeSet(textRequest);
				} else if(sql.startsWith("GET")) {
					return executeGet(textRequest);
				} else {
					throw new IllegalArgumentException("Unknown statement '" + sql + "'");
				}
			} else {
				throw new NotYetImplementedException();
			}
		} catch (Throwable e) {
			log.error("Exception while processing request " + request.getId() + " for session " + request.getSession().getId(),  e);
			return new SimpleResponse(request, Status.ERROR, null, e);
		}
	}
	
	private final class InternalSessionCloseListener implements SessionCloseListener {

		@Override
		public void onClose(Session session) {
			sessions.remove(session.getId());
		}

	}
	
	protected abstract Response executeSet(TextRequest request);
	
	protected abstract Response executeGet(TextRequest request);

	protected abstract Response executeSelect(TextRequest request);

	protected abstract Response executeLoad(TextRequest request);
	
	protected abstract Response executeExecute(TextRequest request);
	
	protected abstract Response executeCreate(TextRequest request);
	
	protected abstract Response executeGenerateDsvFile(GenerateDsvFileRequest request);
	
	protected abstract Response executeDrop(TextRequest request);
	
	protected abstract Response executeInsert(TextRequest request);

	protected abstract Response executeDelete(TextRequest request);

	protected abstract Response executeTruncate(TextRequest request);
}
