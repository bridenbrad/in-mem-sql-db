/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.allocator.persistent.file;

import com.tirion.common.Lifecycle;
import com.tirion.db.store.allocator.Allocator;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface StorageFile extends Lifecycle, Allocator {
	
	String getFileName();
}
