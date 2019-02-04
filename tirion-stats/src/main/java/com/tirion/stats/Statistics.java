/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.stats;

/**
 * All times should be in millis for consistency reasons.
 * Other layers should do conversion to whatever time unit
 * they find appropriate.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Statistics {

	public static enum Kind {
		FILE_TEXT_READER,
		FILES_TEXT_READER,
		
		GROUP_OPERATOR,
		GROUPER_STATISTICS,
		
		HASH_JOIN_OPERATOR,
		NESTED_LOOP_OPERATOR,
		FILTER_OPERATOR,
		SCAN_OPERATOR,
	}
	
	Kind getKind();
}
