/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.source;

import java.util.List;

/**
 * Row IDs have to be in increasing order. First row has ID 0.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface RowIdSource {

	/**
	 * True if this source has no row IDs.
	 */
	boolean isEmpty();
	
	boolean hasNext();
	
	long next();
	
	List<Long> asList();
}
