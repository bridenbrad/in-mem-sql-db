/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan;

import com.tirion.db.sql.exec.operator.physical.AbstractPhysicalOperator;
import com.tirion.db.store.page.source.PageSource;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class ScanOperator extends AbstractPhysicalOperator {

	protected PageSource pageSource;
	
	public final void setPageSource(PageSource pageSource) {
		this.pageSource = pageSource;
	}

	@Override
	public final Kind getKind() {
		return Kind.SCAN;
	}
}
