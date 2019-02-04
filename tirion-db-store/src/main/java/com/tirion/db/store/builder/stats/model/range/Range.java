/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats.model.range;

import com.tirion.common.SizeAware;
import com.tirion.common.type.Type;

/**
 * Range is all values that are greater than lowest
 * value and lower than greatest value. This way we
 * can handle float values.<p>
 * 
 * Range ends are exclusive.
 */
public interface Range extends SizeAware {
	
	Type getType();
}
