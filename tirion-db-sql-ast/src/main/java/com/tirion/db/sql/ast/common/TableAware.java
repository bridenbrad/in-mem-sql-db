/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.common;

import java.util.SortedSet;

/**
 * List of used physical tables.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface TableAware {

	/**
	 * Returns list of all physical tables touched
	 * by query.
	 */
	SortedSet<String> getTableNames();
}
