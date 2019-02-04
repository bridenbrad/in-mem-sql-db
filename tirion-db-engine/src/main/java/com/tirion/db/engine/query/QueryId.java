/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.query;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class QueryId {

	@JsonProperty
	private final long sessionId;
	@JsonProperty
	private final long requestId;
	
	public QueryId(long sessionId, long requestId) {
		this.sessionId = sessionId;
		this.requestId = requestId;
	}
	
	public String getNiceName() {
		return sessionId + "-" + requestId;
	}

	public long getSessionId() {
		return sessionId;
	}

	public long getRequestId() {
		return requestId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (sessionId ^ (sessionId >>> 32));
		result = prime * result + (int) (requestId ^ (requestId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryId other = (QueryId) obj;
		if (sessionId != other.sessionId)
			return false;
		if (requestId != other.requestId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QueryId [sessionId=" + sessionId + ", requestId=" + requestId
				+ "]";
	}
}
