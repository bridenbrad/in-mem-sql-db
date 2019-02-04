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
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
//import com.tirion.common.TableType;
//
//@Entity
//@Table(name = "table_info", schema = "tirion")
//public class TableInfoOld {
//
//	@Id
//	@SequenceGenerator(name="seq", sequenceName="tirion.tableIdSeq")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
//	private int tableId;
//	
//	private String name;
//	
//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name = "tableId", nullable = false)
//	private List<ColumnInfoOld> columns;
//	
//	@Enumerated(EnumType.STRING)
//	private TableType tableType;
//
//	public int getTableId() {
//		return tableId;
//	}
//
//	public void setTableId(int tableId) {
//		this.tableId = tableId;
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
//	public List<ColumnInfoOld> getColumns() {
//		return columns;
//	}
//
//	public void setColumns(List<ColumnInfoOld> columns) {
//		this.columns = columns;
//	}
//
//	public TableType getTableType() {
//		return tableType;
//	}
//
//	public void setTableType(TableType tableType) {
//		this.tableType = tableType;
//	}
//}
