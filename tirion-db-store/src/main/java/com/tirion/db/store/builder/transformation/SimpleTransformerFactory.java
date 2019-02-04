/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation;

import java.util.ArrayList;
import java.util.List;

import com.tirion.common.runtime.Runtime;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.common.Config;
import com.tirion.db.store.Store;
import com.tirion.db.store.allocator.Allocator;
import com.tirion.db.store.builder.tokenizer.TokenizationFactory;
import com.tirion.db.store.builder.tokenizer.Tokenizer;
import com.tirion.db.store.builder.transformation.context.SimpleTransformationContext;
import com.tirion.db.store.builder.transformation.context.TransformationContext;
import com.tirion.db.store.builder.transformation.transformer.BufferTransformer;
import com.tirion.db.store.builder.transformation.transformer.CompressingTransformer;
import com.tirion.db.store.builder.transformation.transformer.ParsingTransformer;
import com.tirion.db.store.builder.transformation.transformer.SplittingTransformer;
import com.tirion.db.store.builder.transformation.transformer.StatsTransformer;
import com.tirion.db.store.builder.transformation.transformer.TokenizingTransformer;
import com.tirion.db.store.builder.transformation.transformer.Transformer;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.column.token.ProxyTokenMap;
import com.tirion.db.store.table.Table;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleTransformerFactory implements TransformerFactory {
	
	private final Allocator allocator;
	private final Store store;
	private final Runtime runtime;
	
	public SimpleTransformerFactory(Allocator allocator, Store store, Runtime runtime) {
		super();
		this.allocator = allocator;
		this.store = store;
		this.runtime = runtime;
	}

	@Override
	public TransformationContext newContext(Entity entity, Config config, Job job) {
		final ProxyTokenMap<Object, Object>[] tokenMaps = buildTokenMapProxies(entity);
		
		List<Transformer> transformers = new ArrayList<Transformer>();
		
		{
			SplittingTransformer transformer = new SplittingTransformer(entity, config, job, runtime);
			transformers.add(transformer);
		}
		{
			ParsingTransformer transformer = new ParsingTransformer(entity, config, job, runtime);
			transformers.add(transformer);
		}
		{
			StatsTransformer transformer = new StatsTransformer(entity, job, runtime);
			transformers.add(transformer);
		}
		{
			TokenizingTransformer transformer = new TokenizingTransformer(entity, buildTokenizers(entity, tokenMaps), job, runtime);
			transformers.add(transformer);
		}
		{
			CompressingTransformer transformer = new CompressingTransformer(entity, job, runtime);
			transformers.add(transformer);
		}
		{
			BufferTransformer transformer = new BufferTransformer(entity, allocator, runtime, job);
			transformers.add(transformer);
		}
		return new SimpleTransformationContext(tokenMaps, transformers.toArray(new Transformer[]{}));
	}
	
	private Tokenizer[] buildTokenizers(Entity entity, ProxyTokenMap<Object, Object>[] tokenMaps) {
		Table table = store.getTable(entity.getName());
		Tokenizer[] result = new Tokenizer[entity.fieldCount()];
		for (int i = 0; i < entity.fieldCount(); i++) {
			Field field = entity.getField(i);
			if(field.getOptions().isTokenized()) {
				Column column = table.getColumn(field.getName());
				result[i] = TokenizationFactory.newTokenizer(column.getTokenType(), column.getType(), tokenMaps[i]);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private ProxyTokenMap<Object, Object>[] buildTokenMapProxies(Entity entity) {
		ProxyTokenMap<Object, Object>[] result = new ProxyTokenMap[entity.fieldCount()];
		Table table = store.getTable(entity.getName());
		for (int i = 0; i < entity.fieldCount(); i++) {
			Field field = entity.getField(i);
			if(!field.getOptions().isTokenized()) {
				continue;
			}
			Column column = table.getColumn(field.getName());
			result[i] = column.getTokenMap().newProxy();
		}
		return result;
	}
}
