/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
///**
// * Copyright © 2013, drekthar
// * All rights reserved.
// *
// * No portion of this file may be reproduced in any form, or by any means, without the prior written
// * consent of the author.
// */
//package com.tirion.db.sql.exec.operator.physical.scan.filter.page;
//
//import java.util.List;
//
//import com.tirion.common.bitmap.Bitmap;
//import com.tirion.db.store.page.Page;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class NullPageFilter extends AbstractPageFilter {
//	
//	private final boolean isNull;
//
//	/**
//	 * If isNull is true, then filter will match all IS NULL
//	 * values.
//	 */
//	public NullPageFilter(boolean isNull) {
//		super();
//		this.isNull = isNull;
//	}
//	
//	@Override
//	protected void processFilteredPage(Page page, List<Long> rowIds) {
//		if(page != null && !rowIds.isEmpty()) {
//			List<Object> values = page.getValues(rowIds);
//			for (int i = 0; i < values.size(); i++) {
//				final Object value = values.get(i);
//				if(isNull) {
//					if(value == null) {						
//						getRowIdSink().onRowId(rowIds.get(i));
//					}
//				} else {
//					if(value != null) {
//						getRowIdSink().onRowId(rowIds.get(i));
//					}
//				}
//			}
//		}
//	}
//
//	@Override
//	protected void executeWithoutRowIdFilter() {
//		while(true) {			
//			Page page = getPageSource().next();
//			if(page == null) {
//				break;
//			}
//			if(isNull && !page.hasNullBitmap()) {				
//				continue;				
//			}
//			processBitmap(page, page.getNullBitmap());
//		}	
//	}
//	
//	private void processBitmap(Page page, Bitmap bitmap) {
//		if((bitmap.isFull() && isNull) || (bitmap.isEmpty() && !isNull)) {
//			getRowIdSink().onRowIdRange(page.getStartRowId(), page.getEndRowId());
//		} else {			
//			for (int i = 0; i < page.getCount(); i++) {
//				if(isNull && bitmap.isSet(i)) {
//					getRowIdSink().onRowId(page.getStartRowId() + i);
//				} else if(!isNull && !bitmap.isSet(i)) {
//					getRowIdSink().onRowId(page.getStartRowId() + i);
//				}
//			}
//		}
//	}
//}
