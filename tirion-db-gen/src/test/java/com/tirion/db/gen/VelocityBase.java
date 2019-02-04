/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.gen;

import java.io.FileWriter;
import java.io.Writer;

import junit.framework.TestCase;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class VelocityBase extends TestCase {

	private Template template;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Velocity.init();
		template = Velocity.getTemplate(getTemplateFileName());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	protected final void generate(String outputFile, VelocityContext ctx) {
		try {
			Writer writer = new FileWriter(outputFile);
			template.merge(ctx, writer);
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected final void generate(Type type, String outputFileName) throws Exception {
		VelocityContext ctx = new VelocityContext();
		ctx.put("typeName", type.getTypeName());
		ctx.put("smallTypeName", type.getSmallTypeName());
		ctx.put("largeTypeName", type.getLargeTypeName());
		ctx.put("capsTypeName", type.name());
		ctx.put("typeSize", type.getWidth());
		generate(outputFileName, ctx);
	}

	protected abstract String getTemplateFileName();
}
