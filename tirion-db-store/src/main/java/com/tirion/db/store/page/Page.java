/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page;

import java.nio.ByteBuffer;
import java.util.List;

import com.tirion.common.SizeAware;
import com.tirion.common.bitmap.Bitmap;
import com.tirion.common.bitmap.NullBitmapAware;
import com.tirion.common.type.Type;
import com.tirion.db.store.column.Column;

/**
 * Column may have both compressed and uncompressed
 * pages. We add that flexibility for now just so that
 * we have choice to change things in the future. Choice
 * is always good. 
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Page extends SizeAware, NullBitmapAware, StatsAware {
	
	public static enum Kind {
		ON_HEAP_NON_TOKENIZED_COMPRESSED,
		ON_HEAP_NON_TOKENIZED_UNCOMPRESSED,
		ON_HEAP_TOKENIZED_COMPRESSED,
		ON_HEAP_TOKENIZED_UNCOMPRESSED,
		
		OFF_HEAP_NON_TOKENIZED_COMPRESSED,
		OFF_HEAP_NON_TOKENIZED_UNCOMPRESSED,
		OFF_HEAP_TOKENIZED_COMPRESSED,
		OFF_HEAP_TOKENIZED_UNCOMPRESSED,
		
		BOOLEAN,
		SINGLE_VALUE,
	}
	
	Kind getKind();

	/**
	 * Native type, not a declared type.
	 */
	Type getType();
	
	/**
	 * Unique per column, monolitically increasing. Pages
	 * of different columns of same table that are at the
	 * same logical level must have same ID. This ID is
	 * used for atomicity/tx purposes.
	 */
	int getId();
	
	/**
	 * Inclusive. This is just logical indicator,
	 * do not use it for inspection of arrays.
	 */
	long getStartRowId();
	
	/**
	 * Exclusive. This is just logical indicator,
	 * do not use it for inspection of arrays.
	 */
	long getEndRowId();
	
	/**
	 * Diff between end row ID and start row ID i.e.
	 * number of entries.
	 */
	int getCount();
	
	Column getOwner();
	
	/**
	 * Returns decompressed data. <p> Returns native java array 
	 * in case page is on heap. Returns {@link ByteBuffer} in case 
	 * page is off heap and was not compressed. Returns native java array in case
	 * page is off heap and is compressed. Returns {@link Bitmap} 
	 * in case page is boolean page. Will not detokenize underlying.
	 */
	Object getUnderlying();
	
	/**
	 * If true, it will detokenize underlying. Guaranteed
	 * to return native java array.
	 */
	Object getUnderlying(boolean detokenize);
	
	/**
	 * Returns true if given row ID belongs to
	 * this page.
	 */
	boolean belongs(long rowId);
	
	/**
	 * Will return null if value is null. Returns actual
	 * value, not mapped token in case column is tokenized.
	 */
	Object getValue(long rowId);
	
	/**
	 * Will return nulls for all null values. Should
	 * use array backed list.
	 */
	List<Object> getValues(List<Long> rowIds);
	
	boolean isNull(long rowId);
}
