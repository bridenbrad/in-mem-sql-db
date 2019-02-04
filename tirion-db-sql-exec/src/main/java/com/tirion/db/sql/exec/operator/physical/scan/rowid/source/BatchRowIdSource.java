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
 * Should we used when we want to do projections in parallel
 * from multiple threads.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface BatchRowIdSource extends RowIdSource {

	List<Long> nextBatch();
}
