/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.projector.extractor;

import java.util.List;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * Able to get value from column at given row ID
 * and inject it into proper index within tuple.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface ValueExtractor {

	/**
	 * Extract value from column at given row ID and
	 * inject it into the tuple.
	 */
	void exec(long rowId, Tuple tuple);
	
	void exec(List<Long> rowIds, List<Tuple> tuples);
}
