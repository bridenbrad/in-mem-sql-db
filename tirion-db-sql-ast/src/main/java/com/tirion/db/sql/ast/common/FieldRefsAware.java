/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.common;

import com.tirion.common.set.SmartSet;

/**
 * List of {@link FieldRef}s that given node needs, such as those
 * from JOIN condition. Must not include derived fields, such as
 * those derived by GROUP BY's functions.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface FieldRefsAware {

	/**
	 * List of {@link FieldRef}s needed by this object.
	 */
	SmartSet<FieldRef> getRequiredFieldRefs();
}
