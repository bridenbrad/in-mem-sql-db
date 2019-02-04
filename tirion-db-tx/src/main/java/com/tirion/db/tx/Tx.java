/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.tx;

import java.util.List;

import com.tirion.common.tx.TxAware;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Tx extends TxAware {
	
	public static enum Kind {
		READ,
		WRITE,
	}
	
	long getId();
	
	Kind getKind();
	
	List<Scope> getScopes();

	Scope getScope(String tableName);
	
	void register(Scope scope);
	
	void register(List<Scope> scopes);
}
