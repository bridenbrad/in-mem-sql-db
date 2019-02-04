/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.tracking;

/**
 * Wrapper around object whose access count & last 
 * access times can be tracked.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Trackable<T> {

	T get();
	
	long getAccessCount();
	
	long getLastAccessTime();
}
