/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.table;

 import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.store.appender.Appender;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.column.ColumnData;
import com.tirion.db.store.column.InMemoryColumn;
import com.tirion.db.store.table.data.TableData;
import com.tirion.common.runtime.Runtime;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class InMemoryTable implements Table {
	
	private static final Logger log = LoggerFactory.getLogger(InMemoryTable.class);
	
	private final Entity entity;
	@JsonProperty
	private final Column[] columns;
	
	public InMemoryTable(Entity entity, Runtime runtime) {
		this.entity = entity;
		columns = new Column[entity.fieldCount()];
		for (int i = 0; i < columns.length; i++) {
			Field field = entity.getField(i);
			columns[i] = new InMemoryColumn(field, runtime);
		}
	}
	
	@Override
	public long sizeInBytes() {
		long columnsSize = 0;
		for(Column column : columns) {
			columnsSize += column.sizeInBytes();
		}
		return 8 * columns.length + 2 * 8 + columnsSize;
	}

	@Override
	public String getName() {
		return entity.getName();
	}

	@Override
	public void truncate() {
		for(Column column : columns) {
			column.truncate();
		}
	}
	
	@Override
	public Column getColumn(String name) {
		for(Column column : columns) {
			if(column.getName().equalsIgnoreCase(name)) {
				return column;
			}
		}
		throw new IllegalArgumentException("Unable to find column '" + name + "' within table '" + getName() + "'");
	}
	
	@Override
	public Column[] getColumns() {
		return columns;
	}

	@Override
	public long getRowCount() {
		return columns[0].getRowCount();
	}

	@Override
	public int getPageCount() {
		return columns[0].getPageCount();
	}

	@Override
	public Appender<TableData> getAppender() {
		return new InternalInMemoryTableAppender();
	}
	
	private final class InternalInMemoryTableAppender implements Appender<TableData> {

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
		public void append(TableData tableData) {
			List<Appender<ColumnData>> columnAppenders = new ArrayList<Appender<ColumnData>>();
			for(Column column : columns) {
				columnAppenders.add(column.getAppender());
			}
			
			beginAll(columnAppenders);
			try {
				for (int i = 0; i < columns.length; i++) {
					ColumnData columnData = tableData.build(i);
					columnAppenders.get(i).append(columnData);
				}
				commitAll(columnAppenders);
			} catch(Throwable t) {
				log.error("Exception while appending column data to table '" + getName() + "'", t);
				rollbackAll(columnAppenders);
			}
		}		
		
		private void beginAll(List<Appender<ColumnData>> columnAppenders) {
			for(Appender<ColumnData> appender : columnAppenders) {
				appender.begin();
			}
		}
		
		private void commitAll(List<Appender<ColumnData>> columnAppenders) {
			for(Appender<ColumnData> appender : columnAppenders) {
				appender.commit();
			}
		}
		
		private void rollbackAll(List<Appender<ColumnData>> columnAppenders) {
			for(Appender<ColumnData> appender : columnAppenders) {
				appender.rollback();
			}
		}
	}
}
