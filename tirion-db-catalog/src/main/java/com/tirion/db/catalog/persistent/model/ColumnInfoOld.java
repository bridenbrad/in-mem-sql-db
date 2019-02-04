/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.persistent.model;
//package com.tirion.db.catalog.persistent.model;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
//import com.tirion.common.type.Type;
//
//@Entity
//@Table(name = "column_info", schema = "tirion")
//public class ColumnInfoOld {
//
//	@Id
//	@SequenceGenerator(name="seq", sequenceName="tirion.columnIdSeq")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
//	private int columnId;
//	
//	private String name;
//	
//	private int position;
//	
//	@Enumerated(EnumType.STRING)
//	private Type type;
//	
//	private boolean nullable;
//	
//	@ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="tableId", updatable=false, insertable=false)
//	private TableInfoOld tableInfoOld;
//
//	public int getColumnId() {
//		return columnId;
//	}
//
//	public void setColumnId(int columnId) {
//		this.columnId = columnId;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public int getPosition() {
//		return position;
//	}
//
//	public void setPosition(int position) {
//		this.position = position;
//	}
//
//	public Type getType() {
//		return type;
//	}
//
//	public void setType(Type type) {
//		this.type = type;
//	}
//
//	public boolean isNullable() {
//		return nullable;
//	}
//
//	public void setNullable(boolean nullable) {
//		this.nullable = nullable;
//	}
//
//	public TableInfoOld getTableInfo() {
//		return tableInfoOld;
//	}
//
//	public void setTableInfo(TableInfoOld tableInfoOld) {
//		this.tableInfoOld = tableInfoOld;
//	}
//}
