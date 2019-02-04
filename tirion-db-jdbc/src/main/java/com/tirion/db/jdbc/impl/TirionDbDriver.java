/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.jdbc.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.common.Pair;
import com.tirion.db.jdbc.AbstractDriver;

/**
 * jdbc:tirion-db://host:port/default
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TirionDbDriver extends AbstractDriver {
	
	static {
		try {
			DriverManager.registerDriver(new TirionDbDriver());
			System.err.println("Registered tirion-db driver with driver manager");
		} catch (SQLException e) {
			throw new RuntimeException("Unable to register tirion-db driver");
		}
	}
	
	private static final Logger log = LoggerFactory.getLogger(TirionDbDriver.class);
	
	public static final String URL_PREFIX = "jdbc:tirion-db://";
	
	public static final String DEFAULT_URL = URL_PREFIX + "localhost:3333/default";

	@Override
	public boolean acceptsURL(String url) throws SQLException {
//		if(url.startsWith(URL_PREFIX)) {
//			return true;
//		}
//		return false;
		return true;
	}
	
	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		Pair<String, Integer> pair = getHostPort(url);
		return new DbConnection(pair.getFirst(), pair.getSecond());
	}
	
	private Pair<String, Integer> getHostPort(String url) {
		if(!url.startsWith(URL_PREFIX)) {
			throw new IllegalArgumentException("Illegal start of URL");
		}
		url = url.substring(URL_PREFIX.length());
		int index = url.indexOf(':');
		String host = url.substring(0, index);
		int port = Integer.parseInt(url.substring(index+1, url.lastIndexOf('/')));
		log.debug("Using following host:port => " + host + ":" + port);
		return new Pair<String, Integer>(host, port);
	}
}
