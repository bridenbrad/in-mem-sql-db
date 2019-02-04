/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.parser.transformer;

import org.antlr.runtime.tree.CommonTree;

import com.tirion.common.Util;
import com.tirion.db.common.JoinType;
import com.tirion.db.sql.SqlGrammarParser;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.common.EntityRef;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FunctionCall;
import com.tirion.db.sql.ast.common.Star;
import com.tirion.db.sql.ast.common.constant.Constant;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.ast.select.FromClause;
import com.tirion.db.sql.ast.select.GroupByClause;
import com.tirion.db.sql.ast.select.HavingClause;
import com.tirion.db.sql.ast.select.Join;
import com.tirion.db.sql.ast.select.JoinClause;
import com.tirion.db.sql.ast.select.LimitClause;
import com.tirion.db.sql.ast.select.OrderByClause;
import com.tirion.db.sql.ast.select.OrderKind;
import com.tirion.db.sql.ast.select.SelectClause;
import com.tirion.db.sql.ast.select.SelectStatement;
import com.tirion.db.sql.ast.select.SmartSelectStatement;
import com.tirion.db.sql.ast.select.WhereClause;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SelectTransformer extends AbstractTransformer {

	private Transformer boolTransformer = new BooleanExpressionTransformer();
	
	@Override
	public Node transform(CommonTree root) {
		assertType(root, SqlGrammarParser.SELECT_STATEMENT);
		
		SmartSelectStatement stmt = new SmartSelectStatement();
		
		stmt.setSelectClause(transformSelectClause((CommonTree)root.getChild(0)));
		stmt.setFromClause(transformFromClause((CommonTree)root.getChild(1)));
		
		int index = 2;		
		JoinClause joinClause = new JoinClause();
		while(true) {
			CommonTree node = (CommonTree)root.getChild(index);
			if(node == null || node.getType() != SqlGrammarParser.JOIN) {
				break;
			}
			joinClause.append(transformatJoin(node));
			++index;
		}
		if(joinClause.joinCount() > 0) {
			stmt.setJoinClause(joinClause);
		}
		
		CommonTree 
		node = (CommonTree)root.getChild(index);
		if(node != null && node.getType() == SqlGrammarParser.WHERE) {
			stmt.setWhereClause(transformWhereClause(node));
			++index;
		}
		node = (CommonTree)root.getChild(index);
		if(node != null && node.getType() == SqlGrammarParser.GROUP) {
			stmt.setGroupByClause(transformGroupByClause(node));
			++index;
		}
		node = (CommonTree)root.getChild(index);
		if(node != null && node.getType() == SqlGrammarParser.HAVING) {
			stmt.setHavingClause(transformHavingClause(node));
			++index;
		}
		node = (CommonTree)root.getChild(index);
		if(node != null && node.getType() == SqlGrammarParser.ORDER) {
			stmt.setOrderByClause(transformOrderByClause(node));
			++index;
		}
		node = (CommonTree)root.getChild(index);
		if(node != null && node.getType() == SqlGrammarParser.LIMIT) {
			stmt.setLimitClause(transformLimitClause(node));
			++index;
		}
		
		return stmt;
	}
	
	private SelectClause transformSelectClause(CommonTree root) {
		assertType(root, SqlGrammarParser.SELECT);
		
		SelectClause selectClause = new SelectClause();
		
		for (int i = 0; i < root.getChildCount(); i++) {
			CommonTree node = (CommonTree)root.getChild(i);
			if(node.getType() == SqlGrammarParser.STAR) {
				Star star = new Star();
				star.setOwner(((CommonTree)node.getChild(0)).getText());
				selectClause.append(star);
			} else if(node.getType() == SqlGrammarParser.NAME) {
				FieldRef fieldRef = new FieldRef();
				fieldRef.setName(node.getText());
				
				String owner = null;
				String alias = null;
				if(node.getChildCount() == 2) {			
					owner = ((CommonTree)node.getChild(0)).getText();
					alias = ((CommonTree)node.getChild(1).getChild(0)).getText();
				} else if(node.getChildCount() == 1) {
					CommonTree child = (CommonTree)node.getChild(0);
					if(child.getType() == SqlGrammarParser.AS) {
						alias = child.getChild(0).getText();
					} else {
						owner = child.getText();
					}
				}
				fieldRef.setOwner(owner);
				fieldRef.setAlias(alias);;
				selectClause.append(fieldRef);
			} else if(node.getType() == SqlGrammarParser.FUNCTION_CALL) {
				FunctionCall functionCall = transformFunctionCall(node);
				selectClause.append(functionCall);
			} else {
				throw new IllegalArgumentException("Unexpected token '" + super.tokenIdToString(node.getType()) + "'");
			}
			
		}	
		return selectClause;
	}
	
	private FunctionCall transformFunctionCall(CommonTree node) {
		assertType(node, SqlGrammarParser.FUNCTION_CALL);
		FunctionCall functionCall = new FunctionCall();
		functionCall.setName(((CommonTree)node.getChild(0)).getText());
		
		int lastIndex = node.getChildCount() - 1;
		if(node.getChild(lastIndex).getType() == SqlGrammarParser.AS) {
			functionCall.setAlias(((CommonTree)node.getChild(lastIndex).getChild(0)).getText());
			--lastIndex;
		}
		for (int j = 1; j <= lastIndex; j++) {
			CommonTree child = (CommonTree) node.getChild(j);
			switch (child.getType()) {
				case SqlGrammarParser.STAR:
					functionCall.append(new Star());
					break;
				case SqlGrammarParser.NAME:
					FieldRef fieldRef = new FieldRef();
					fieldRef.setName(child.getText());
					if(child.getChildCount() > 0) {
						fieldRef.setOwner(((CommonTree)child.getChild(0)).getText());
					}
					functionCall.append(fieldRef);
					break;
				case SqlGrammarParser.INT: // pass thru
				case SqlGrammarParser.FLOAT:
					Constant constant = new Constant();
					constant.setValue(((CommonTree)child).getText());
					functionCall.append(constant);
					break;
				case SqlGrammarParser.STRING:
					constant = new Constant();
					constant.setValue(Util.trimQuotes(((CommonTree)child).getText()));
					functionCall.append(constant);
					break;
				default:
					throw new IllegalArgumentException("Unexpected token '" + tokenIdToString(child.getType()) + "'");
			}
		}
		return functionCall;
	}

	private FromClause transformFromClause(CommonTree root) {
		assertType(root, SqlGrammarParser.FROM);
		
		String alias = null;
		if(root.getChildCount() > 1) {
			alias = ((CommonTree) root.getChild(1).getChild(0)).getText();
		}
		
		FromClause from = new FromClause();
		from.setAlias(alias);
		
		CommonTree child = (CommonTree) root.getChild(0);		
		if(child.getType() == SqlGrammarParser.NAME) {
			EntityRef entityRef = new EntityRef();
			entityRef.setName(child.getText());
			from.setTable(entityRef);
		} else if(child.getType() == SqlGrammarParser.SELECT_STATEMENT) {
			SelectStatement selectStatement = (SelectStatement) transform(child);
			from.setSubSelect(selectStatement);
		} else {
			throw new IllegalArgumentException("Unexpected token '" + tokenIdToString(child.getType()) + "'");
		}
		return from;
	}
	
	private Join transformatJoin(CommonTree root) {
		assertType(root, SqlGrammarParser.JOIN);
		
		Join join = new Join();
		JoinType joinType = JoinType.fromString(((CommonTree) root.getChild(0)).getText());
		join.setJoinType(joinType);
		
		int onIndex = 2;
		String alias = null;
		CommonTree child = (CommonTree) root.getChild(2);
		if(child.getType() == SqlGrammarParser.AS) {
			alias = ((CommonTree)child.getChild(0)).getText();
			++onIndex;
		}
		join.setAlias(alias);
		
		child = (CommonTree) root.getChild(1);
		if(child.getType() == SqlGrammarParser.NAME) {
			EntityRef entityRef = new EntityRef();
			entityRef.setName(child.getText());
			join.setTable(entityRef);
		} else if(child.getType() == SqlGrammarParser.SELECT_STATEMENT) {
			join.setSubSelect((SelectStatement)transform(child));
		} else {
			throw new IllegalArgumentException("Unexpected token '" + tokenIdToString(child.getType()) + "'");
		}
		join.setCondition((BoolExpression)boolTransformer.transform((CommonTree) root.getChild(onIndex).getChild(0)));
		return join;
	}
	
	private WhereClause transformWhereClause(CommonTree root) {
		assertType(root, SqlGrammarParser.WHERE);
		WhereClause whereClause = new WhereClause();
		whereClause.setExpression((BoolExpression)boolTransformer.transform((CommonTree)root.getChild(0)));
		return whereClause;
	}
	
	private GroupByClause transformGroupByClause(CommonTree root) {
		assertType(root, SqlGrammarParser.GROUP);
		
		GroupByClause groupBy = new GroupByClause();
		for (int i = 0; i < root.getChildCount(); i++) {
			String fieldName = ((CommonTree)root.getChild(i)).getText();
			String owner = null;
			if(root.getChild(i).getChildCount() > 0) {
				owner = root.getChild(i).getChild(0).getText();
			}
			
			FieldRef fieldRef = new FieldRef();
			fieldRef.setName(fieldName);
			fieldRef.setOwner(owner);
			groupBy.append(fieldRef);
		}
		return groupBy;
	}
	
	private HavingClause transformHavingClause(CommonTree root) {
		assertType(root, SqlGrammarParser.HAVING);
		HavingClause havingClause = new HavingClause();
		havingClause.setExpression((BoolExpression)boolTransformer.transform((CommonTree)root.getChild(0)));
		return havingClause;
	}
	
	private OrderByClause transformOrderByClause(CommonTree root) {
		assertType(root, SqlGrammarParser.ORDER);
		
		OrderByClause orderBy = new OrderByClause();
		for (int i = 0; i < root.getChildCount(); i++) {
			CommonTree child = (CommonTree)root.getChild(i);
			String fieldName = child.getText();
			
			OrderKind orderKind = OrderKind.ASC;
			String owner = null;
			if(child.getChildCount() == 2) {
				owner = ((CommonTree)child.getChild(0)).getText();
				orderKind = OrderKind.parseFromString(((CommonTree)child.getChild(1)).getText());				
			} else if(child.getChildCount() == 1) {
				CommonTree childChild = (CommonTree)child.getChild(0);
				if(childChild.getType() == SqlGrammarParser.NAME) {
					owner = childChild.getText();
				} else {
					orderKind = OrderKind.parseFromString(childChild.getText());
				}
			}
			FieldRef fieldRef = new FieldRef();
			fieldRef.setName(fieldName);
			fieldRef.setOwner(owner);
			
			orderBy.append(fieldRef, orderKind);
		}
		return orderBy;
	}
	
	private LimitClause transformLimitClause(CommonTree root) {
		assertType(root, SqlGrammarParser.LIMIT);
		LimitClause limitClause = new LimitClause();
		String value = ((CommonTree)root.getChild(0)).getText();
		limitClause.setValue(Integer.parseInt(value));
		return limitClause;
	}
}
