/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.finder;

import com.tirion.common.OwnerAware;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.Page;

/**
 * Used to find mapping from row ID to page which
 * manages that row ID. Used for random access to 
 * pages, such as during value projection or during
 * parametrized search (i.e. brute force search over
 * only certain rows).
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface PageFinder extends OwnerAware<Column> {
	
	Page findPage(long rowId);
}
