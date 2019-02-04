/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.header;

import com.tirion.db.common.Constants;

public abstract class AbstractHeaderSource implements HeaderSource {

	@Override
	public final Header next(int count) {
		if(count > Constants.ROWS_PER_PAGE) {
			throw new IllegalArgumentException("Row count must be less than " 
					+ Constants.ROWS_PER_PAGE + ". Encounted " + count);
		}
		return nextInternal(count);
	}

	protected abstract Header nextInternal(int count);
}
