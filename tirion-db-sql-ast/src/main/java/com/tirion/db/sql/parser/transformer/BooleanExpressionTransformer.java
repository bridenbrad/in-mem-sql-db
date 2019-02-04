/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.parser.transformer;

import java.util.HashSet;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.sql.SqlGrammarParser;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.constant.Constant;
import com.tirion.db.sql.ast.common.constant.ConstantRange;
import com.tirion.db.sql.ast.common.constant.ConstantSet;
import com.tirion.db.sql.ast.expression.bool.And;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.ast.expression.bool.Or;
import com.tirion.db.sql.ast.expression.compare.BetweenCompare;
import com.tirion.db.sql.ast.expression.compare.EqCompare;
import com.tirion.db.sql.ast.expression.compare.GtCompare;
import com.tirion.db.sql.ast.expression.compare.GtEqCompare;
import com.tirion.db.sql.ast.expression.compare.InCompare;
import com.tirion.db.sql.ast.expression.compare.LtCompare;
import com.tirion.db.sql.ast.expression.compare.LtEqCompare;
import com.tirion.db.sql.ast.expression.compare.NeqCompare;
import com.tirion.db.sql.ast.expression.compare.NullCompare;
import com.tirion.db.sql.ast.expression.compare.NullCompare.IsNotNullCompare;
import com.tirion.db.sql.ast.expression.compare.NullCompare.IsNullCompare;
import com.tirion.db.sql.ast.select.SelectStatement;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class BooleanExpressionTransformer extends AbstractTransformer {
	
	private final Set<Integer> types = new HashSet<Integer>();

	public BooleanExpressionTransformer() {
		super();
		types.add(SqlGrammarParser.FLOAT);
		types.add(SqlGrammarParser.INT);
		types.add(SqlGrammarParser.STRING);
	}

	@Override
	public Node transform(CommonTree root) {
		return transformInternal(root);
	}

	private BoolExpression transformInternal(CommonTree root) {
		switch (root.getType()) {
			case SqlGrammarParser.AND:
				{
					And and = new And();
					for (int i = 0; i < root.getChildCount(); i++) {
						and.append(transformInternal((CommonTree)root.getChild(i)));
					}
					return and;
				}
			case SqlGrammarParser.OR:
				{
					Or or = new Or();
					for (int i = 0; i < root.getChildCount(); i++) {
						or.append(transformInternal((CommonTree)root.getChild(i)));
					}
					return or;
				}
			case SqlGrammarParser.BETWEEN:
				{
					BetweenCompare between = new BetweenCompare();
					
					FieldRef left = new FieldRef();
					left.setName(((CommonTree)root.getChild(0)).getText());
					if(((CommonTree)root.getChild(0)).getChildCount() > 0) {
						left.setOwner((((CommonTree)root.getChild(0).getChild(0)).getText()));
					}
					
					ConstantRange range = new ConstantRange();
					range.setLow(getConstant((CommonTree)root.getChild(1)));
//					range.setLow(((CommonTree)root.getChild(1)).getText());
					range.setHigh(getConstant((CommonTree)root.getChild(2)));
//					range.setHigh(((CommonTree)root.getChild(2)).getText());
					
					between.setFieldRef(left);
					between.setRange(range);
					
					return between;
				}
			case SqlGrammarParser.IN:
				{
					InCompare in = new InCompare();
					
					FieldRef left = new FieldRef();
					left.setName(((CommonTree)root.getChild(0)).getText());
					if(((CommonTree)root.getChild(0)).getChildCount() > 0) {
						left.setOwner((((CommonTree)root.getChild(0).getChild(0)).getText()));
					}
					in.setFieldRef(left);
					
					in.setHasNot(false);
					int index = 1;
					if(root.getChild(index).getType() == SqlGrammarParser.NOT) {
						in.setHasNot(true);
						++index;
					}
					
					if(root.getChild(index).getType() == SqlGrammarParser.SELECT_STATEMENT) {
						SelectStatement select = (SelectStatement) new SelectTransformer().transform((CommonTree)root.getChild(index));
						in.setSelect(select);
					} else {						
						ConstantSet set = new ConstantSet();
						for (int i = index; i < root.getChildCount(); i++) {
//							set.appendStr(((CommonTree)root.getChild(i)).getText());
							set.appendStr(getConstant((CommonTree)root.getChild(i)));
						}						
						in.setSet(set);
					}
					
					return in;
				}
			case SqlGrammarParser.EQ:
				{
					EqCompare compare = new EqCompare();
					
					FieldRef left = new FieldRef();
					left.setName(((CommonTree)root.getChild(0)).getText());
					if(((CommonTree)root.getChild(0)).getChildCount() > 0) {
						left.setOwner((((CommonTree)root.getChild(0).getChild(0)).getText()));
					}
					compare.setFieldRef(left);
					
					CommonTree node = (CommonTree)root.getChild(1);
					if(types.contains(node.getType())) { // constant
						Constant constant = new Constant();
//						constant.setValue(node.getText());
						constant.setValue(getConstant(node));
						compare.setRight(constant);						
					} else {
						FieldRef right = new FieldRef(node.getText());
						if(node.getChildCount() > 0) {
							right.setOwner(node.getChild(0).getText());
						}
						compare.setRight(right);	
					}

					return compare;
				}
			case SqlGrammarParser.IS:
				{
					EqCompare compare = new EqCompare();
					FieldRef left = new FieldRef();
					left.setName(((CommonTree)root.getChild(0)).getText());
					if(((CommonTree)root.getChild(0)).getChildCount() > 0) {
						left.setOwner((((CommonTree)root.getChild(0).getChild(0)).getText()));
					}
					compare.setFieldRef(left);
					
					CommonTree node = (CommonTree)root.getChild(1);
					Constant constant = new Constant();
					constant.setValue(node.getText());
					
					compare.setRight(constant);	
					
					return compare;
				}
			case SqlGrammarParser.NEQ:
				{
					NeqCompare compare = new NeqCompare();
					
					FieldRef left = new FieldRef();
					left.setName(((CommonTree)root.getChild(0)).getText());
					if(((CommonTree)root.getChild(0)).getChildCount() > 0) {
						left.setOwner((((CommonTree)root.getChild(0).getChild(0)).getText()));
					}
					compare.setFieldRef(left);
					
					CommonTree node = (CommonTree)root.getChild(1);
					if(types.contains(node.getType())) { // constant
						Constant constant = new Constant();
//						constant.setValue(node.getText());
						constant.setValue(getConstant(node));
						compare.setRight(constant);						
					} else {
						FieldRef right = new FieldRef(node.getText());
						if(node.getChildCount() > 0) {
							right.setOwner(node.getChild(0).getText());
						}
						compare.setRight(right);	
					}
					
					return compare;
				}
			case SqlGrammarParser.LT:
				{
					LtCompare compare = new LtCompare();
					
					FieldRef left = new FieldRef();
					left.setName(((CommonTree)root.getChild(0)).getText());
					if(((CommonTree)root.getChild(0)).getChildCount() > 0) {
						left.setOwner((((CommonTree)root.getChild(0).getChild(0)).getText()));
					}
					compare.setFieldRef(left);
					
					CommonTree node = (CommonTree)root.getChild(1);
					if(types.contains(node.getType())) { // constant
						Constant constant = new Constant();
//						constant.setValue(node.getText());
						constant.setValue(getConstant(node));
						compare.setRight(constant);						
					} else {
						FieldRef right = new FieldRef(node.getText());
						if(node.getChildCount() > 0) {
							right.setOwner(node.getChild(0).getText());
						}
						compare.setRight(right);	
					}
					
					return compare;
				}
			case SqlGrammarParser.LTEQ:
				{
					LtEqCompare compare = new LtEqCompare();
					
					FieldRef left = new FieldRef();
					left.setName(((CommonTree)root.getChild(0)).getText());
					if(((CommonTree)root.getChild(0)).getChildCount() > 0) {
						left.setOwner((((CommonTree)root.getChild(0).getChild(0)).getText()));
					}
					compare.setFieldRef(left);
					
					CommonTree node = (CommonTree)root.getChild(1);
					if(types.contains(node.getType())) { // constant
						Constant constant = new Constant();
//						constant.setValue(node.getText());
						constant.setValue(getConstant(node));
						compare.setRight(constant);						
					} else {
						FieldRef right = new FieldRef(node.getText());
						if(node.getChildCount() > 0) {
							right.setOwner(node.getChild(0).getText());
						}
						compare.setRight(right);	
					}
					
					return compare;
				}
			case SqlGrammarParser.GT:
				{
					GtCompare compare = new GtCompare();
					
					FieldRef left = new FieldRef();
					left.setName(((CommonTree)root.getChild(0)).getText());
					if(((CommonTree)root.getChild(0)).getChildCount() > 0) {
						left.setOwner((((CommonTree)root.getChild(0).getChild(0)).getText()));
					}
					compare.setFieldRef(left);
					
					CommonTree node = (CommonTree)root.getChild(1);
					if(types.contains(node.getType())) { // constant
						Constant constant = new Constant();
//						constant.setValue(node.getText());
						constant.setValue(getConstant(node));
						compare.setRight(constant);						
					} else {
						FieldRef right = new FieldRef(node.getText());
						if(node.getChildCount() > 0) {
							right.setOwner(node.getChild(0).getText());
						}
						compare.setRight(right);	
					}
					
					return compare;
				}
			case SqlGrammarParser.GTEQ:
				{
					GtEqCompare compare = new GtEqCompare();
					
					FieldRef left = new FieldRef();
					left.setName(((CommonTree)root.getChild(0)).getText());
					if(((CommonTree)root.getChild(0)).getChildCount() > 0) {
						left.setOwner((((CommonTree)root.getChild(0).getChild(0)).getText()));
					}
					compare.setFieldRef(left);
					
					CommonTree node = (CommonTree)root.getChild(1);
					if(types.contains(node.getType())) { // constant
						Constant constant = new Constant();
//						constant.setValue(node.getText());
						constant.setValue(getConstant(node));
						compare.setRight(constant);						
					} else {
						FieldRef right = new FieldRef(node.getText());
						if(node.getChildCount() > 0) {
							right.setOwner(node.getChild(0).getText());
						}
						compare.setRight(right);	
					}
					
					return compare;
				}
			case SqlGrammarParser.NULL:
				{
					NullCompare compare = new IsNullCompare();
					if(root.getChildCount() > 1) {
						compare = new IsNotNullCompare();
					}
					FieldRef left = new FieldRef();
					left.setName(((CommonTree)root.getChild(0)).getText());
					if(((CommonTree)root.getChild(0)).getChildCount() > 0) {
						left.setOwner((((CommonTree)root.getChild(0).getChild(0)).getText()));
					}
					compare.setFieldRef(left);
					return compare;
				}
			case SqlGrammarParser.EXISTS:
				{
					throw new NotYetImplementedException("EXISTS clause is temporary disabled");
//					ExistsCompare exists = new ExistsCompare();
					
//					exists.setHasNot(false);
//					int index = 0;
//					if(root.getChild(index).getType() == SqlGrammarParser.NOT) {
//						exists.setHasNot(true);
//						++index;
//					}
//					SelectStatement select = (SelectStatement) new SelectTransformer().transform((CommonTree)root.getChild(index));
//					exists.setSelect(select);
//					return exists;
				}
			default:
				throw new IllegalArgumentException("Unexpected token '" + tokenIdToString(root.getType()) + "'");
		}
	}
	
	private String getConstant(CommonTree node) {
		String text = node.getText();
		if(node.getChildCount() > 0) {
			// number with explicit + or -
			text = node.getChild(0) + text;
		}
		return text;
	}
}
