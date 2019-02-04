/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.tuple.allocator;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class OffHeapAllocator implements Allocator {

	@Override
	public Tuple allocate() {
		throw new NotYetImplementedException();
	}

}
