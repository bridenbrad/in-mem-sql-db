/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.column.token;

/**
 * This is used during loading, where we build proxy map of
 * new key/value pairs that were detected in new data set.
 * If loading is committed, this map is then merged into 
 * proxied map.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface ProxyTokenMap<TokenType, ValueType> extends TokenMap<TokenType, ValueType> {

	/**
	 * Merge back into main token map we are proxying.
	 */
	void merge();
}
