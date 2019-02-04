/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.tx;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractTx implements Tx {
	
	@JsonProperty
	private final long id;
	@JsonProperty
	private final List<Scope> scopes;
	@JsonProperty
	private final Kind kind;
	
	private final Runtime runtime;
	
	public AbstractTx(long id, Kind kind, Runtime runtime) {
		this.id = id;
		scopes = new ArrayList<Scope>();
		this.runtime = runtime;
		this.kind = kind;
	}
	
	protected final Runtime getRuntime() {
		return runtime;
	}
	
	@Override
	public final Kind getKind() {
		return kind;
	}

	@Override
	public final long getId() {
		return id;
	}

	@Override
	public final void register(Scope scope) {
		scopes.add(scope);
	}
	
	@Override
	public final void register(List<Scope> scopes) {
		for(Scope scope : scopes) {
			register(scope);
		}
	}

	@Override
	public final List<Scope> getScopes() {
		return scopes;
	}

	@Override
	public final Scope getScope(String tableName) {
		for(Scope scope : scopes) {
			if(scope.getTableName().equalsIgnoreCase(tableName)) {
				return scope;
			}
		}
		throw new IllegalArgumentException("No tx scope for table '" + tableName + "'");
	}
	
	@Override
	public final void begin() {
		beginInternal();
	}

	@Override
	public final void commit() {
		commitInternal();
	}

	@Override
	public final void rollback() {
		rollbackInternal();
	}

	protected abstract void beginInternal();
	protected abstract void commitInternal();
	protected abstract void rollbackInternal();
}
