/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats.model.minmax;

import com.tirion.common.SizeAware;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface MinMax extends SizeAware {

	/**
	 * In case min is equal to max.
	 */
	boolean isSingleton();
}

