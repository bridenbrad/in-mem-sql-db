/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.join;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.common.JoinType;
import com.tirion.db.sql.exec.operator.physical.DualSourcePhysicalOperator;
import com.tirion.db.sql.exec.operator.physical.join.merger.TupleMerger;

/**
 * We support only LEFT and INNER join.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class JoinOperator extends DualSourcePhysicalOperator {

	@JsonProperty
	private JoinType joinType;
	private TupleMerger tupleMerger;
	
	protected final JoinType getJoinType() {
		return joinType;
	}

	public final void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}
	
	protected final TupleMerger getTupleMerger() {
		return tupleMerger;
	}

	public final void setTupleMerger(TupleMerger tupleMerger) {
		this.tupleMerger = tupleMerger;
	}

	@Override
	public final Kind getKind() {
		return Kind.JOIN;
	}
	
	
}
