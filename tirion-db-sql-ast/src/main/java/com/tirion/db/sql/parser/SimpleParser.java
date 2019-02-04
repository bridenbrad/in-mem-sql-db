/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.parser;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.tirion.common.metrics.Metrics;
import com.tirion.db.sql.SqlGrammarLexer;
import com.tirion.db.sql.SqlGrammarParser;
import com.tirion.db.sql.SqlGrammarParser.statement___return;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.parser.transformer.CreateTableTransformer;
import com.tirion.db.sql.parser.transformer.DeleteTransformer;
import com.tirion.db.sql.parser.transformer.DescribeTransformer;
import com.tirion.db.sql.parser.transformer.DropTransformer;
import com.tirion.db.sql.parser.transformer.ExecuteTransformer;
import com.tirion.db.sql.parser.transformer.GetTransformer;
import com.tirion.db.sql.parser.transformer.InsertTransformer;
import com.tirion.db.sql.parser.transformer.LoadTransformer;
import com.tirion.db.sql.parser.transformer.SelectTransformer;
import com.tirion.db.sql.parser.transformer.SetTransformer;
import com.tirion.db.sql.parser.transformer.ShowTransformer;
import com.tirion.db.sql.parser.transformer.Transformer;
import com.tirion.db.sql.parser.transformer.TruncateTransformer;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleParser implements Parser {

	private Transformer getTransformer = new GetTransformer();
	private Transformer setTransformer = new SetTransformer();
	private Transformer loadTransformer = new LoadTransformer();
	private Transformer dropTransformer = new DropTransformer();
	private Transformer executeTransformer = new ExecuteTransformer();
	private Transformer createTransformer = new CreateTableTransformer();
	private Transformer selectTransformer = new SelectTransformer();
	private Transformer deleteTransformer = new DeleteTransformer();
	private Transformer insertTransformer = new InsertTransformer();
	private Transformer showTransformer = new ShowTransformer();
	private Transformer describeTransformer = new DescribeTransformer();
	private Transformer truncateTransformer = new TruncateTransformer();
	
	private Histogram parsingTime;
	
	@Override
	public void init() {
		parsingTime = Metrics.get().histogram(MetricRegistry.name("parser", "time"));
	}

	@Override
	public void shutdown() {
	}

	@Override
	public Node parse(String statement) {
		long start = System.currentTimeMillis();
		Node node = build(buildAntlrAst(statement));
		long duration = System.currentTimeMillis() - start;
		parsingTime.update(duration);
		return node;
	}
	
	@Override
	public List<Node> parse(String[] statements) {
		List<Node> nodes = new ArrayList<Node>();
		for(String statement : statements) {
			nodes.add(parse(statement));
		}
		return nodes;
	}

	private Node build(statement___return ast) {
		CommonTree root = (CommonTree) ast.getTree();
		switch (root.getType()) {
			case SqlGrammarParser.SELECT_STATEMENT:
				return selectTransformer.transform(root);
			case SqlGrammarParser.CREATE_STATEMENT:
				return createTransformer.transform(root);
			case SqlGrammarParser.LOAD_STATEMENT:
				return loadTransformer.transform(root);
			case SqlGrammarParser.DROP_STATEMENT:
				return dropTransformer.transform(root);
			case SqlGrammarParser.SET_STATEMENT:
				return setTransformer.transform(root);
			case SqlGrammarParser.GET_STATEMENT:
				return getTransformer.transform(root);
			case SqlGrammarParser.EXECUTE_STATEMENT:
				return executeTransformer.transform(root);
			case SqlGrammarParser.SHOW_STATEMENT:
				return showTransformer.transform(root);
			case SqlGrammarParser.DESCRIBE_STATEMENT:
				return describeTransformer.transform(root);
			case SqlGrammarParser.DELETE_STATEMENT:
				return deleteTransformer.transform(root);
			case SqlGrammarParser.INSERT_STATEMENT:
				return insertTransformer.transform(root);
			case SqlGrammarParser.TRUNCATE_STATEMENT:
				return truncateTransformer.transform(root);
			default:
				throw new IllegalArgumentException("Unexpected ANTLR node type '" 
						+ SqlGrammarParser.tokenNames[root.getType()] + "'");
		}
	}
	
	private statement___return buildAntlrAst(String statement) {
		try {
			CharStream stream = new ANTLRStringStream(statement);
			SqlGrammarLexer lexer = new SqlGrammarLexer(stream);
			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			SqlGrammarParser parser = new SqlGrammarParser(tokenStream);
			return parser.statement__();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
	}
}
