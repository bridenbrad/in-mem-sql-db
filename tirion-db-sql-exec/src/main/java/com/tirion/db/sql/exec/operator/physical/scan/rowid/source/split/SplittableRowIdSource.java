/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.source.split;

import java.util.List;

import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.BatchRowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.SizeAwareRowIdSource;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface SplittableRowIdSource extends SizeAwareRowIdSource {
	
	/**
	 * May return more or less splits that splits count hint.
	 */
	List<BatchRowIdSource> splitBatch(int splitsCountHint);
}
