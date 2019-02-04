/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.source;

import com.tirion.db.store.page.Page;

/**
 * Used to iterate over page's columns in sequential manner
 * from first to last one.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface PageSource {

	boolean hasNext();
	
	/**
	 * Returns null when done.
	 */
	Page next();
}
