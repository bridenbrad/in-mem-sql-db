/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.shell;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.tirion.common.thread.NameAwareThreadFactory;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TelnetShell implements Shell {

	private ServerBootstrap bootstrap;

	private int port;
	private Runtime runtime;
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setRuntime(Runtime runtime) {
		this.runtime = runtime;
	}

	@Override
	public void init() {
		bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(new NameAwareThreadFactory("tirion-db-rpc-boss")),
                        Executors.newCachedThreadPool(new NameAwareThreadFactory("tirion-db-rpc-work"))));
		bootstrap.setPipelineFactory(new TelnetShellPipelineFactory(runtime));
		bootstrap.bind(new InetSocketAddress(port));
	}

	@Override
	public void shutdown() {
		bootstrap.shutdown();
		bootstrap.releaseExternalResources();
	}
}
