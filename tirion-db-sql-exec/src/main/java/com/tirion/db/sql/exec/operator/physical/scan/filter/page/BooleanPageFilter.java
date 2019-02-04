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
//public final class BooleanPageFilter extends AbstractPageFilter {
//	
//	private final boolean value;
//	
//	/**
//	 * If value is true, then filter will match all true
//	 * values.
//	 */
//	public BooleanPageFilter(boolean value) {
//		super();
//		this.value = value;
//	}
//
//	@Override
//	protected void processFilteredPage(Page page, List<Long> rowIds) {
//		if(page != null && !rowIds.isEmpty()) {
//			List<Object> values = page.getValues(rowIds);
//			for (int i = 0; i < values.size(); i++) {
//				final Object value = values.get(i);
//				if(value != null && value.equals(this.value)) {
//					getRowIdSink().onRowId(rowIds.get(i));
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
//			getRowIdSink().before(page);
//			Bitmap bitmap = (Bitmap) page.getUnderlying();
//			int index = 0;
//			for (long rowId = page.getStartRowId(); rowId < page.getEndRowId(); rowId++) {
//				if(value && bitmap.isSet(index++)) {
//					getRowIdSink().onRowId(rowId);
//				}
//			}
//			getRowIdSink().after(page);
//		}
//	}
//}
