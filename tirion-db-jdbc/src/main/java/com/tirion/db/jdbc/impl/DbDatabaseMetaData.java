/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.jdbc.impl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.tirion.db.jdbc.AbstractResultSet;
import com.tirion.db.jdbc.AbstractResultSetMetaData;

/**
 * Catalog name is default and schema name is admin.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DbDatabaseMetaData implements DatabaseMetaData {
	
//	private final RmiServer server;
	private Connection connection;
	
//	public DbDatabaseMetaData(RmiServer server, Connection connection) {
//		this.server = server;
//		this.connection = connection;
//	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean allProceduresAreCallable() throws SQLException {
		return false;
	}

	@Override
	public boolean allTablesAreSelectable() throws SQLException {
		return true;
	}

	@Override
	public String getURL() throws SQLException {
		return TirionDbDriver.DEFAULT_URL;
	}

	@Override
	public String getUserName() throws SQLException {
		return "admin";
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return false;
	}

	@Override
	public boolean nullsAreSortedHigh() throws SQLException {
		return true;
	}

	@Override
	public boolean nullsAreSortedLow() throws SQLException {
		return !nullsAreSortedHigh();
	}

	@Override
	public boolean nullsAreSortedAtStart() throws SQLException {
		return true;
	}

	@Override
	public boolean nullsAreSortedAtEnd() throws SQLException {
		return false;
	}

	@Override
	public String getDatabaseProductName() throws SQLException {
		return "tirion-db";
	}

	@Override
	public String getDatabaseProductVersion() throws SQLException {
		return "1.0";
	}

	@Override
	public String getDriverName() throws SQLException {
		return "tirion-db-jdbc-driver";
	}

	@Override
	public String getDriverVersion() throws SQLException {
		return "1,0";
	}

	@Override
	public int getDriverMajorVersion() {
		return 1;
	}

	@Override
	public int getDriverMinorVersion() {
		return 0;
	}

	@Override
	public boolean usesLocalFiles() throws SQLException {
		return true;
	}

	@Override
	public boolean usesLocalFilePerTable() throws SQLException {
		return true;
	}

	@Override
	public boolean supportsMixedCaseIdentifiers() throws SQLException {
		return false;
	}

	@Override
	public boolean storesUpperCaseIdentifiers() throws SQLException {
		return false;
	}

	@Override
	public boolean storesLowerCaseIdentifiers() throws SQLException {
		return true;
	}

	@Override
	public boolean storesMixedCaseIdentifiers() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
		return false;
	}

	@Override
	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		return false;
	}

	@Override
	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		return true;
	}

	@Override
	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		return false;
	}

	@Override
	public String getIdentifierQuoteString() throws SQLException {
		return " ";
	}

	@Override
	public String getSQLKeywords() throws SQLException {
		return "";
	}

	@Override
	public String getNumericFunctions() throws SQLException {
		return "";
	}

	@Override
	public String getStringFunctions() throws SQLException {
		return "";
	}

	@Override
	public String getSystemFunctions() throws SQLException {
		return "";
	}

	@Override
	public String getTimeDateFunctions() throws SQLException {
		return "";
	}

	@Override
	public String getSearchStringEscape() throws SQLException {
		return "";
	}

	@Override
	public String getExtraNameCharacters() throws SQLException {
		return "";
	}

	@Override
	public boolean supportsAlterTableWithAddColumn() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsColumnAliasing() throws SQLException {
		return true;
	}

	@Override
	public boolean nullPlusNonNullIsNull() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsConvert() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsConvert(int fromType, int toType)
			throws SQLException {
		return false;
	}

	@Override
	public boolean supportsTableCorrelationNames() throws SQLException {
		return true;
	}

	@Override
	public boolean supportsDifferentTableCorrelationNames() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsExpressionsInOrderBy() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsOrderByUnrelated() throws SQLException {
		return true;
	}

	@Override
	public boolean supportsGroupBy() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsGroupByUnrelated() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsGroupByBeyondSelect() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsLikeEscapeClause() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsMultipleResultSets() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsMultipleTransactions() throws SQLException {
		return true;
	}

	@Override
	public boolean supportsNonNullableColumns() throws SQLException {
		return true;
	}

	@Override
	public boolean supportsMinimumSQLGrammar() throws SQLException {
		return true;
	}

	@Override
	public boolean supportsCoreSQLGrammar() throws SQLException {
		return true;
	}

	@Override
	public boolean supportsExtendedSQLGrammar() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsANSI92EntryLevelSQL() throws SQLException {
		return true;
	}

	@Override
	public boolean supportsANSI92IntermediateSQL() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsANSI92FullSQL() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsIntegrityEnhancementFacility() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsOuterJoins() throws SQLException {
		return true;
	}

	@Override
	public boolean supportsFullOuterJoins() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsLimitedOuterJoins() throws SQLException {
		return true;
	}

	@Override
	public String getSchemaTerm() throws SQLException {
		return "schema";
	}

	@Override
	public String getProcedureTerm() throws SQLException {
		return "procedure";
	}

	@Override
	public String getCatalogTerm() throws SQLException {
		return "catalog";
	}

	@Override
	public boolean isCatalogAtStart() throws SQLException {
		return false;
	}

	@Override
	public String getCatalogSeparator() throws SQLException {
		return ".";
	}

	@Override
	public boolean supportsSchemasInDataManipulation() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsSchemasInProcedureCalls() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsSchemasInIndexDefinitions() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsCatalogsInDataManipulation() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsPositionedDelete() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsPositionedUpdate() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsSelectForUpdate() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsStoredProcedures() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsSubqueriesInComparisons() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsSubqueriesInExists() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsSubqueriesInIns() throws SQLException {
		return true;
	}

	@Override
	public boolean supportsSubqueriesInQuantifieds() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsCorrelatedSubqueries() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsUnion() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsUnionAll() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
		return false;
	}

	@Override
	public int getMaxBinaryLiteralLength() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxCharLiteralLength() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxColumnNameLength() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxColumnsInGroupBy() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxColumnsInIndex() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxColumnsInOrderBy() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxColumnsInSelect() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxColumnsInTable() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxConnections() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxCursorNameLength() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxIndexLength() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxSchemaNameLength() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxProcedureNameLength() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxCatalogNameLength() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxRowSize() throws SQLException {
		return 0;
	}

	@Override
	public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
		return false;
	}

	@Override
	public int getMaxStatementLength() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxStatements() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxTableNameLength() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxTablesInSelect() throws SQLException {
		return 0;
	}

	@Override
	public int getMaxUserNameLength() throws SQLException {
		return 64;
	}

	@Override
	public int getDefaultTransactionIsolation() throws SQLException {
		return Connection.TRANSACTION_NONE;
	}

	@Override
	public boolean supportsTransactions() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsTransactionIsolationLevel(int level)
			throws SQLException {
		return false;
	}

	@Override
	public boolean supportsDataDefinitionAndDataManipulationTransactions()
			throws SQLException {
		return false;
	}

	@Override
	public boolean supportsDataManipulationTransactionsOnly()
			throws SQLException {
		return false;
	}

	@Override
	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		return false;
	}

	@Override
	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		return true;
	}

	@Override
	public ResultSet getProcedures(String catalog, String schemaPattern,
			String procedureNamePattern) throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getProcedureColumns(String catalog, String schemaPattern,
			String procedureNamePattern, String columnNamePattern)
			throws SQLException {
		return new DbEmptyResultSet();
	}
	
	private Set<String> getTableNames() {
//		try {
////			return server.getTableNames();
//		} catch (RemoteException e) {
//			throw new RuntimeException(e);
//		}
		return null;
	}

	@Override
	public ResultSet getTables(String catalog, String schemaPattern,
			String tableNamePattern, String[] types) throws SQLException {
		final List<String> tableNames = new ArrayList<String>(getTableNames());
		final AtomicInteger counter = new AtomicInteger();
		final AtomicInteger current = new AtomicInteger();
		return new AbstractResultSet() {
			
			@Override
			public ResultSetMetaData getMetaData() throws SQLException {
				return new AbstractResultSetMetaData() {
					@Override
					public String getColumnTypeName(int column) throws SQLException {
						return "STRING";
					}
					
					@Override
					public int getColumnType(int column) throws SQLException {
						return Types.VARCHAR;
					}
					
					@Override
					public String getColumnName(int column) throws SQLException {
						switch (column) {
							case 1:
								return "TABLE_TYPE";
							case 2:
								return "TABLE_CAT";
							case 3:
								return "TABLE_SCHEM";
							case 4:
								return "TABLE_NAME";
							default:
								throw new IllegalArgumentException(String.valueOf(column));
						}
					}
					
					
					@Override
					public String getColumnLabel(int column) throws SQLException {
						return getColumnName(column);
					}
					
					@Override
					public int getColumnCount() throws SQLException {
						return 4;
					}
				};
			}
			
			@Override
			public boolean next() throws SQLException {
				current.set(counter.getAndIncrement());
				return current.get() < 4;
			}
			
			@Override
			public void close() throws SQLException {
			}

			@Override
			public String getString(int columnIndex) throws SQLException {
				switch (columnIndex) {
					case 1:
						return "TABLE";
					case 2:
						return "default";
					case 3:
						return "admin";
					case 4:
						return tableNames.get(current.get());
					default:
						throw new IllegalArgumentException(String.valueOf(columnIndex));
				}
			}

			@Override
			public String getString(String columnLabel) throws SQLException {
				if(columnLabel.equalsIgnoreCase("TABLE_TYPE")) {
					return "TABLE";
				} else if(columnLabel.equalsIgnoreCase("TABLE_CAT")) {
					return "default";
				} else if(columnLabel.equalsIgnoreCase("TABLE_SCHEM")) {
					return "admin";
				} else if(columnLabel.equalsIgnoreCase("TABLE_NAME")) {
					return tableNames.get(current.get());
				}
				return super.getString(columnLabel);
			}
		};
	}

	@Override
	public ResultSet getSchemas() throws SQLException {
		return new SingleRowResultSet() {

			@Override
			public String getString(int columnIndex) throws SQLException {
				if(columnIndex == 1) {
					return "admin";
				} else if(columnIndex == 2) {
					return "default";
				} else {
					throw new IllegalArgumentException(String.valueOf(columnIndex));
				}
			}

			@Override
			public String getString(String columnLabel) throws SQLException {
				columnLabel = columnLabel.toUpperCase();
				if("TABLE_SCHEM".equals(columnLabel)) {
					return "admin";
				} else if("TABLE_CATALOG".equals(columnLabel)) {
					return "default";
				} else {
					throw new IllegalArgumentException(columnLabel);
				}
			}

			@Override
			public ResultSetMetaData getMetaData() throws SQLException {
				return new AbstractResultSetMetaData() {
					
					@Override
					public String getColumnTypeName(int column) throws SQLException {
						return "STRING";
					}
					
					@Override
					public int getColumnType(int column) throws SQLException {
						return Types.VARCHAR;
					}
					
					@Override
					public String getColumnName(int column) throws SQLException {
						if(column == 1) {
							return "TABLE_SCHEM";
						} else {							
							return "TABLE_CATALOG";
						}
					}
					
					@Override
					public String getColumnLabel(int column) throws SQLException {
						return getColumnName(column);
					}
					
					@Override
					public int getColumnCount() throws SQLException {
						return 2;
					}
				};
			}
		};
	}

	@Override
	public ResultSet getCatalogs() throws SQLException {
		return new SingleRowResultSet() {
			@Override
			public String getString(int columnIndex) throws SQLException {
				return "default";
			}

			@Override
			public String getString(String columnLabel) throws SQLException {
				return "default";
			}

			@Override
			public ResultSetMetaData getMetaData() throws SQLException {
				return new AbstractResultSetMetaData() {
					
					@Override
					public String getColumnTypeName(int column) throws SQLException {
						return "STRING";
					}
					
					@Override
					public int getColumnType(int column) throws SQLException {
						return Types.VARCHAR;
					}
					
					@Override
					public String getColumnName(int column) throws SQLException {
						return "TABLE_CAT";
					}
					
					@Override
					public String getColumnLabel(int column) throws SQLException {
						return "TABLE_CAT";
					}
					
					@Override
					public int getColumnCount() throws SQLException {
						return 1;
					}
				};
			}
		};
	}

	@Override
	public ResultSet getTableTypes() throws SQLException {
		return new SingleRowResultSet() {
			@Override
			public String getString(int columnIndex) throws SQLException {
				return "TABLE";
			}

			@Override
			public String getString(String columnLabel) throws SQLException {
				return "TABLE";
			}

			@Override
			public ResultSetMetaData getMetaData() throws SQLException {
				return new AbstractResultSetMetaData() {
					
					@Override
					public String getColumnTypeName(int column) throws SQLException {
						return "STRING";
					}
					
					@Override
					public int getColumnType(int column) throws SQLException {
						return Types.VARCHAR;
					}
					
					@Override
					public String getColumnName(int column) throws SQLException {
						return "TABLE_CAT";
					}
					
					@Override
					public String getColumnLabel(int column) throws SQLException {
						return "TABLE_CAT";
					}
					
					@Override
					public int getColumnCount() throws SQLException {
						return 1;
					}
				};
			}
		};
	}

	@Override
	public ResultSet getColumns(String catalog, String schemaPattern,
			String tableNamePattern, String columnNamePattern)
			throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getColumnPrivileges(String catalog, String schema,
			String table, String columnNamePattern) throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getTablePrivileges(String catalog, String schemaPattern,
			String tableNamePattern) throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getBestRowIdentifier(String catalog, String schema,
			String table, int scope, boolean nullable) throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getVersionColumns(String catalog, String schema,
			String table) throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getPrimaryKeys(String catalog, String schema, String table)
			throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getImportedKeys(String catalog, String schema, String table)
			throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getExportedKeys(String catalog, String schema, String table)
			throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getCrossReference(String parentCatalog,
			String parentSchema, String parentTable, String foreignCatalog,
			String foreignSchema, String foreignTable) throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getTypeInfo() throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getIndexInfo(String catalog, String schema, String table,
			boolean unique, boolean approximate) throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public boolean supportsResultSetType(int type) throws SQLException {
		return false;
	}

	@Override
	public boolean supportsResultSetConcurrency(int type, int concurrency)
			throws SQLException {
		return false;
	}

	@Override
	public boolean ownUpdatesAreVisible(int type) throws SQLException {
		return false;
	}

	@Override
	public boolean ownDeletesAreVisible(int type) throws SQLException {
		return false;
	}

	@Override
	public boolean ownInsertsAreVisible(int type) throws SQLException {
		return false;
	}

	@Override
	public boolean othersUpdatesAreVisible(int type) throws SQLException {
		return false;
	}

	@Override
	public boolean othersDeletesAreVisible(int type) throws SQLException {
		return false;
	}

	@Override
	public boolean othersInsertsAreVisible(int type) throws SQLException {
		return false;
	}

	@Override
	public boolean updatesAreDetected(int type) throws SQLException {
		return false;
	}

	@Override
	public boolean deletesAreDetected(int type) throws SQLException {
		return false;
	}

	@Override
	public boolean insertsAreDetected(int type) throws SQLException {
		return false;
	}

	@Override
	public boolean supportsBatchUpdates() throws SQLException {
		return false;
	}

	@Override
	public ResultSet getUDTs(String catalog, String schemaPattern,
			String typeNamePattern, int[] types) throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return connection;
	}

	@Override
	public boolean supportsSavepoints() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsNamedParameters() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsMultipleOpenResults() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsGetGeneratedKeys() throws SQLException {
		return false;
	}

	@Override
	public ResultSet getSuperTypes(String catalog, String schemaPattern,
			String typeNamePattern) throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getSuperTables(String catalog, String schemaPattern,
			String tableNamePattern) throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public ResultSet getAttributes(String catalog, String schemaPattern,
			String typeNamePattern, String attributeNamePattern)
			throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public boolean supportsResultSetHoldability(int holdability)
			throws SQLException {
		return false;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return 0;
	}

	@Override
	public int getDatabaseMajorVersion() throws SQLException {
		return 1;
	}

	@Override
	public int getDatabaseMinorVersion() throws SQLException {
		return 0;
	}

	@Override
	public int getJDBCMajorVersion() throws SQLException {
		return 1;
	}

	@Override
	public int getJDBCMinorVersion() throws SQLException {
		return 0;
	}

	@Override
	public int getSQLStateType() throws SQLException {
		return 0;
	}

	@Override
	public boolean locatorsUpdateCopy() throws SQLException {
		return false;
	}

	@Override
	public boolean supportsStatementPooling() throws SQLException {
		return false;
	}

	@Override
	public RowIdLifetime getRowIdLifetime() throws SQLException {
		return null;
	}

	@Override
	public ResultSet getSchemas(String catalog, String schemaPattern)
			throws SQLException {
		return new DbEmptyResultSet();
	}

	@Override
	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		return false;
	}

	@Override
	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		return false;
	}

	@Override
	public ResultSet getClientInfoProperties() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSet getFunctions(String catalog, String schemaPattern,
			String functionNamePattern) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSet getFunctionColumns(String catalog, String schemaPattern,
			String functionNamePattern, String columnNamePattern)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSet getPseudoColumns(String catalog, String schemaPattern,
			String tableNamePattern, String columnNamePattern)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean generatedKeyAlwaysReturned() throws SQLException {
		return false;
	}
}
