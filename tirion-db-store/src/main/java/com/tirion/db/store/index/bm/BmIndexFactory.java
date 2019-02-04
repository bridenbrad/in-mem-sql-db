/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.index.bm;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class BmIndexFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BmIndex<Object> newBmIndex(Type type, Runtime runtime) {
		throw new NotYetImplementedException();
//		switch (type) {
//			case BYTE:
////				return (BmIndex)new OnHeapBmIndex<Byte>(runtime);
//				return (BmIndex)new OffHeapBmIndex<Byte>(runtime);
//			case DATE:
//			case SHORT:
////				return (BmIndex)new OnHeapBmIndex<Short>(runtime);
//				return (BmIndex)new OffHeapBmIndex<Short>(runtime);
//			case TIME:
//			case INT:
////				return (BmIndex)new OnHeapBmIndex<Integer>(runtime);
//				return (BmIndex)new OffHeapBmIndex<Integer>(runtime);
//			case TIMESTAMP:
//			case LONG:
////				return (BmIndex)new OnHeapBmIndex<Long>(runtime);
//				return (BmIndex)new OffHeapBmIndex<Long>(runtime);
//			case FLOAT:
////				return (BmIndex)new OnHeapBmIndex<Float>(runtime);
//				return (BmIndex)new OffHeapBmIndex<Float>(runtime);
//			case DOUBLE:
////				return (BmIndex)new OnHeapBmIndex<Double>(runtime);
//				return (BmIndex)new OffHeapBmIndex<Double>(runtime);
////			case STRING:
////				return (BmIndex)new OnHeapBmIndex<String>(runtime);
////				return (BmIndex)new OffHeapBmIndex<Long>(runtime);				
//			default:
//				throw new NotYetImplementedException(type.toString());
//		}
	}
	
	private BmIndexFactory() {
	}
}
