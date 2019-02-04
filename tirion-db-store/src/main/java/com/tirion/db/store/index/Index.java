/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.index;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Index {

	public static enum IndexKind {
		BM,
		HI,
		TR,
	}
	
	IndexKind getKind();
}
