/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.query;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.exec.operator.physical.PhysicalOperator;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Query {

	@JsonProperty
	private final QueryId queryId;
	@JsonProperty
	private final long parseDuration;
	@JsonProperty
	private final long planDuraction;
	@JsonProperty
	private final long execDuration;
	@JsonProperty
	private final long tupleCount;
	@JsonProperty
	private final String sql;
	@JsonProperty
	private final Node ast;
	@JsonProperty
	private final PhysicalOperator operator;
	
	public Query(QueryId queryId, long parseDuration, long planDuraction,
			long execDuration, long tupleCount, String sql, Node ast,
			PhysicalOperator operator) {
		super();
		this.queryId = queryId;
		this.parseDuration = parseDuration;
		this.planDuraction = planDuraction;
		this.execDuration = execDuration;
		this.tupleCount = tupleCount;
		this.sql = sql;
		this.ast = ast;
		this.operator = operator;
	}



	public String getNiceName() {
		return "query-plan-" + queryId.getNiceName();
	}
	
	public QueryId getQueryId() {
		return queryId;
	}

	public long getParseDuration() {
		return parseDuration;
	}

	public long getPlanDuraction() {
		return planDuraction;
	}

	public long getExecDuration() {
		return execDuration;
	}

	public long getTupleCount() {
		return tupleCount;
	}

	public String getSql() {
		return sql;
	}

	public Node getAst() {
		return ast;
	}

	public PhysicalOperator getOperator() {
		return operator;
	}
}
