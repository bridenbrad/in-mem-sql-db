/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.column;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.extractor.ArrayExtractor;
import com.tirion.common.extractor.ExtractorFactory;
import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.Sequence;
import com.tirion.common.sequence.array.Array;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.common.type.Type;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.common.Constants;
import com.tirion.db.store.appender.Appender;
import com.tirion.db.store.builder.page.PageBuilder;
import com.tirion.db.store.builder.page.SimplePageBuilder;
import com.tirion.db.store.builder.tokenizer.TokenizationFactory;
import com.tirion.db.store.builder.tokenizer.Tokenizer;
import com.tirion.db.store.column.token.TokenMap;
import com.tirion.db.store.index.Index;
import com.tirion.db.store.index.Index.IndexKind;
import com.tirion.db.store.index.bm.BmIndex;
import com.tirion.db.store.page.Page;
import com.tirion.db.store.page.finder.PageFinder;
import com.tirion.db.store.page.header.AbstractHeaderSource;
import com.tirion.db.store.page.header.Header;
import com.tirion.db.store.page.header.HeaderSource;
import com.tirion.db.store.page.header.SimpleHeader;
import com.tirion.db.store.page.source.PageSource;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class InMemoryColumn extends AbstractColumn {
		
	private final Runtime runtime;
	@JsonProperty
	private final TokenMap<Object, Object> tokenMap;
	private final Tokenizer tokenizer;
	private final BmIndex<Object> index;
	@JsonProperty
	private final List<Page> pages;
	private final ArrayExtractor arrayExtractor;
	private final HeaderSource headerSource;
	
	public InMemoryColumn(Field field, Runtime runtime) {
		super(field.getName(), field.getNativeType());
		this.runtime = runtime;
		headerSource = new InternalHeaderSource();
		pages = new CopyOnWriteArrayList<Page>();
		if(TokenizationFactory.shouldTokenize(field)) {
			Type tokenType = TokenizationFactory.tokenTypeFor(field.getOptions().getDistinct(), getType());
			tokenMap = TokenizationFactory.newTokenMap(tokenType, this, runtime);			
			tokenizer = new InternalTokenizer(TokenizationFactory.newTokenizer(tokenType, getType(), tokenMap));
		} else {
			tokenMap = null;
			tokenizer = null;
		}
		if(field.getOptions().hasBmIndex()) {
			index = null;//BmIndexFactory.newBmIndex(getType(), runtime);
		} else {
			index = null;
		}
		if(getType() != Type.BOOLEAN) {			
			if(isTokenized()) {
				arrayExtractor = ExtractorFactory.newArrayExtractor(getTokenType());			
			} else {
				arrayExtractor = ExtractorFactory.newArrayExtractor(getType());
			}
		} else {
			arrayExtractor = null;
		}
	}
	
	@Override
	public final ArrayExtractor getArrayExtractor() {
		return arrayExtractor;
	}

	@Override
	public long sizeInBytes() {
		long pagesSize = 0;
		for(Page page : pages) {
			pagesSize += page.sizeInBytes();
		}
		pagesSize += pages.size() * 8;
		return super.getAbstractSize() + 5 * 8 + (tokenMap != null ? tokenMap.sizeInBytes() : 0) 
				+ (index != null ? index.sizeInBytes() : 0) + pagesSize;
	}

	@Override
	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	@Override
	public Type getTokenType() {
		return tokenMap.getTokenType();
	}

	@Override
	public boolean hasIndex(IndexKind indexKind) {
		return indexKind == IndexKind.BM && index != null;
	}

	@Override
	public Index getIndex(IndexKind indexKind) {
		if(indexKind != IndexKind.BM) {
			throw new IllegalArgumentException("Unknown index kind '" + indexKind + "'");
		}
		if(index == null) {
			throw new IllegalArgumentException("Underlying index is null");
		}
		return index;
	}

	@Override
	public boolean isTokenized() {
		return tokenMap != null;
	}

	@Override
	public TokenMap<Object, Object> getTokenMap() {
		return tokenMap;
	}

	@Override
	public PageSource getPageSource() {
		return new InternalPageSource();
	}

	@Override
	public PageFinder getPageFinder() {
		return new InternalPageFinder();
	}

	@Override
	public int getPageCount() {
		return pages.size();
	}

	@Override
	public long getRowCount() {
		if(pages.isEmpty()) {
			return 0;
		}
		return pages.get(pages.size() - 1).getEndRowId();
	}

	@Override
	public void truncate() {
		throw new NotYetImplementedException();
	}

	@Override
	public Appender<ColumnData> getAppender() {
		return new InternalInMemoryColumnAppender();
	}
	
	private final class InternalHeaderSource extends AbstractHeaderSource {
		
		/**
		 * TODO FIXME WARNING make this atomic; if page loading fails then
		 * there will be holes in page IDs, and behavior is undefined...
		 */
		private final AtomicInteger pageIdCounter = new AtomicInteger(0);

		@Override
		protected Header nextInternal(int count) {
			int pageId = pageIdCounter.getAndIncrement();
			long startRowId = pageId * Constants.ROWS_PER_PAGE;
			long actualEndRowID = startRowId + count;
			return new SimpleHeader(pageId, startRowId, actualEndRowID);
		}
	}
	
	private final class InternalInMemoryColumnAppender implements Appender<ColumnData> {

		@Override
		public void begin() {
		}

		@Override
		public void rollback() {
		}

		@Override
		public void commit() {
		}

		@Override
		public void append(ColumnData columnData) {
			try {
				PageBuilder pageBuilder = new SimplePageBuilder(false, InMemoryColumn.this, InMemoryColumn.this.runtime, InMemoryColumn.this.headerSource);
				if(isTokenized()) {
					columnData.getTokenMap().merge();
				}
				List<Page> pages = new LinkedList<Page>();
				Sequence[] sequences = columnData.getSequences();
				for (int i = 0; i < sequences.length; i++) {					
					pages.add(pageBuilder.build(sequences[i], columnData.getStats()[i]));
				}
				InMemoryColumn.this.pages.addAll(pages);
			} catch (Exception e) {
				throw new RuntimeException("Exception while appending to column '" + getName() + "'", e);
			}
		}
	}
	
	private final class InternalPageFinder implements PageFinder {
		
		@Override
		public Column getOwner() {
			return InMemoryColumn.this;
		}

		@Override
		public Page findPage(long rowId) {
			return pages.get((int)(rowId / Constants.ROWS_PER_PAGE));
		}
	}
	
	private final class InternalPageSource implements PageSource {

		private final Iterator<Page> it;
		
		public InternalPageSource() {
			it = pages.iterator();
		}
		
		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public Page next() {
			if(it.hasNext()) {
				return it.next();
			}
			return null;
		}
	}
	
	// proxy, prevents tokenization operations...
	private final class InternalTokenizer implements Tokenizer {

		private final Tokenizer underlying;
		
		public InternalTokenizer(Tokenizer underlying) {
			this.underlying = underlying;
		}

		@Override
		public Array tokenize(Array array) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Array detokenize(Array array) {
			return underlying.detokenize(array);
		}

		@Override
		public Array detokenize(Buffer buffer) {
			return underlying.detokenize(buffer);
		}
	}
}