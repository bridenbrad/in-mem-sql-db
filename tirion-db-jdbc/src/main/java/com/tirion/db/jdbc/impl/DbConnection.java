/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.jdbc.impl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.jdbc.AbstractConnection;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DbConnection extends AbstractConnection {
	
	private final String host;
	private final int port;
	
//	private RmiServer jdbcServer;
	
	public DbConnection(String host, int port) {
		this.host = host;
		this.port = port;
		init();
	}
	
	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
//		return new DbDatabaseMetaData(jdbcS	erver, this);
		throw new NotYetImplementedException();
	}

	@Override
	public String getCatalog() throws SQLException {
		return "default";
	}

//	public RmiServer getJdbcServer() {
//		return jdbcServer;
//	}

	@Override
	public Statement createStatement() throws SQLException {
		return new DbStatement(this);
	}

	@Override
	public synchronized boolean isClosed() throws SQLException {
//		return jdbcServer == null;
		throw new NotYetImplementedException();
	}
	
	@Override
	public synchronized void close() throws SQLException {
//		jdbcServer = null;
	}
	
//	private void init() {
//		PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
//	    Properties properties = new Properties();
//	    properties.setProperty("host", host);
//	    properties.setProperty("port", String.valueOf(port));
//	    configurer.setProperties(properties);
//	    
//	    context = new ClassPathXmlApplicationContext(xmlFile);
////	    context = new FileSystemXmlApplicationContext();
//	    context.addBeanFactoryPostProcessor(configurer);
//	    context.setConfigLocation(xmlFile);
//	    context.refresh();
//	    jdbcServer = (JdbcServer)context.getBean("jdbcServer");
//	}
	
	private void init() {
		try {
			Registry registry = LocateRegistry.getRegistry(host, port);
//			jdbcServer = (RmiServer) registry.lookup("tirion-jdbc-server");
		} catch (Exception e) {
			throw new RuntimeException("Unable to connect to '" + host + ":" + port + "'", e);
		}
	}
}
