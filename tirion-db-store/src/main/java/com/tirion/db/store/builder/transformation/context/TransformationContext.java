/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.context;

import java.util.List;

import com.tirion.db.store.builder.transformation.slice.SequenceSlice;
import com.tirion.db.store.builder.transformation.slice.Slice;
import com.tirion.db.store.column.token.ProxyTokenMap;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface TransformationContext {

	List<SequenceSlice> getSlices();
	
	ProxyTokenMap<Object, Object>[] getTokenMapProxies();

	void onSlice(Slice slice);
}
