/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.parser;

import com.tirion.common.sequence.array.Array;

/**
 * Parsers produce {@link Array}s whose null bitmaps are always
 * backed by boolean array (assuming at least one null value
 * is present). Should be stateless.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Parser {

	/**
	 * Start row is inclusive, end row is exclusive.
	 */
	Array parse(String[][] matrix, int startRow, int endRow, int columnIndex);
}
