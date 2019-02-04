/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.shell;

import java.net.InetSocketAddress;
import java.text.DecimalFormat;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.db.engine.LeaderEngine;
import com.tirion.db.engine.client.Session;
import com.tirion.db.engine.client.reqres.req.GenerateDsvFileRequest;
import com.tirion.db.engine.client.reqres.req.Request;
import com.tirion.db.engine.client.reqres.req.TupleListenerAwareTextRequest;
import com.tirion.db.engine.client.reqres.res.Response;
import com.tirion.db.engine.client.reqres.res.SingleValueResponse;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.sql.exec.tuple.sink.AbstractTupleListener;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ShellServerHandler extends SimpleChannelUpstreamHandler {

    private static final Logger log = LoggerFactory.getLogger(ShellServerHandler.class);
    
    private static final String NEW_LINE = "\r\n";
    
    private final DecimalFormat format = new DecimalFormat("#.##");
    
    private final Runtime runtime;
  
    private Session session;
    private LeaderEngine engine;
   
	public ShellServerHandler(Runtime runtime) {
		super();
		this.runtime = runtime;
	}

	@Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent && log.isTraceEnabled()) {
            log.trace("Received upstream event " + e.toString());
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    	Channel channel = e.getChannel();
    	String name = "shell-client://" + getClientHostPort(channel);
    	
    	session = engine.connect(name);
    	
        channel.write("welcome to tirion-db shell" + NEW_LINE);
        channel.write("tirion-db: ");
        channel.setAttachment(session);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
    	final Channel channel = e.getChannel();
    	
        String request = (String) e.getMessage();
        request = request.trim();
        if(request.isEmpty()) {
        
        } else if(!request.endsWith(";")) {
        	channel.write("Statement must end with ;");
        } else if(request.equals("EXIT;") || request.equals("QUIT;")) {
        	channel.close();
        } else if(request.equals("HELP;")) {
        	channel.write(getHelp());        	
        } else {   
        	try {
				long start = System.currentTimeMillis();
				processBackendCommand(request, channel);
				long end = System.currentTimeMillis();
				float duration = (float)(end-start) / (float)1000;
				channel.write("OK, done in " + format.format(duration) + " seconds" + NEW_LINE);
			} catch (Exception ex) {
				log.error("Exception while processing request '" + request + "'", ex);
				channel.write(ex.getMessage() + NEW_LINE);
			}
        }
        e.getChannel().write("tirion-db: ");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        log.error("Shell server received unexpected exception from client " + getClientHostPort(e.getChannel()), e.getCause());
        Session session = (Session) ctx.getChannel().getAttachment();
        session.close();
        e.getChannel().close();
    }
    
    private String getClientHostPort(Channel channel) {
    	InetSocketAddress address = (InetSocketAddress)channel.getRemoteAddress();
    	return address.getHostName() + ":" + address.getPort();
    }
    
    private String getHelp() {
    	return "TODO" + NEW_LINE;
    }
    
	private void processBackendCommand(String line, Channel channel) throws Exception {
		Request request = null;
		if(line.startsWith("GENERATE")) { // GENERATE X 1
			int first = line.indexOf(' ');
			int last = line.lastIndexOf(' ');
			String tableName = line.substring(first + 1, last).trim();
			int rowCount = Integer.parseInt(line.substring(last + 1, line.length() - 1));
			request = new GenerateDsvFileRequest(session, rowCount, tableName);
		}
		else {				
			request = new TupleListenerAwareTextRequest(session, line, new ShellPrintingTupleListener(channel));
		}
		Response response = engine.execute(request);
		if(response.getStatus() != Response.Status.OK) {
			channel.write("Error while processing request: " + response.getMessage());
			if(response.getException() != null) {
				log.error("Exception while processing request for session " + session.getId(), response.getException());
			}
		} else if(response instanceof SingleValueResponse) {
			channel.write("\"" + ((SingleValueResponse)response).getValue() + "\"" + NEW_LINE);
		}
	}
	
	private final class ShellPrintingTupleListener extends AbstractTupleListener {

		private final Channel channel;
	
		public ShellPrintingTupleListener(Channel channel) {
			super();
			this.channel = channel;
		}
		
		@Override
		public void onTuple(Tuple tuple) {
			if(tuple != null) {				
				for(Object value : tuple.getUnderlying()) {
					channel.write(value + ",");
				}
				channel.write("\n");
			}
		}
	}
}
