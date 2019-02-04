/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.slice;

/**
 * Input data is divided in slices. Each slice has same number
 * of rows as there are rows per page. First slice has number
 * 0, second has 1 etc.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Slice {
	
	int getId();
	
	int getRowCount();
}
