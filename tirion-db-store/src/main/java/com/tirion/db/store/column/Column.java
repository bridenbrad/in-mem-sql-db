/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.column;

import com.tirion.common.SizeAware;
import com.tirion.common.extractor.ArrayExtractor;
import com.tirion.common.type.Type;
import com.tirion.db.store.appender.Appendable;
import com.tirion.db.store.builder.tokenizer.Tokenizer;
import com.tirion.db.store.column.token.TokenMap;
import com.tirion.db.store.index.Index;
import com.tirion.db.store.index.Index.IndexKind;
import com.tirion.db.store.page.finder.PageFinder;
import com.tirion.db.store.page.source.PageSource;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Column extends Appendable<ColumnData>, SizeAware {
	
	boolean hasIndex(IndexKind indexKind);
	
	Index getIndex(IndexKind indexKind);
	
	boolean isTokenized();
	
	Type getTokenType();
	
	TokenMap<Object, Object> getTokenMap();
	
	String getName();
	
	/**
	 * Native type, not a declared type.
	 */
	Type getType();
	
	PageSource getPageSource();
	
	PageFinder getPageFinder();

	int getPageCount();
	
	long getRowCount();
	
	void truncate();
	
	/**
	 * Internal helper method. Do not use!
	 */
	Tokenizer getTokenizer();
	
	/**
	 * Internal helper method. Do not use! Either token
	 * or value array extractor, depending if column is
	 * tokenized or not.
	 */
	ArrayExtractor getArrayExtractor();
}
