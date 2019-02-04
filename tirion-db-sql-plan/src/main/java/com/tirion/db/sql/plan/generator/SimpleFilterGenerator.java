/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.generator;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.type.Type;
import com.tirion.common.uuid.UuidUtil;
import com.tirion.compiler.source.Source;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.sql.ast.Fields;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.constant.Constant;
import com.tirion.db.sql.ast.common.constant.ConstantRange;
import com.tirion.db.sql.ast.expression.bool.And;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.ast.expression.bool.Or;
import com.tirion.db.sql.ast.expression.compare.BetweenCompare;
import com.tirion.db.sql.ast.expression.compare.Compare;
import com.tirion.db.sql.ast.expression.compare.InCompare;
import com.tirion.db.sql.ast.expression.compare.NullCompare;
import com.tirion.db.sql.ast.expression.compare.NullCompare.IsNotNullCompare;
import com.tirion.db.sql.ast.expression.compare.SimpleCompare;
import com.tirion.db.sql.exec.operator.physical.filter.TupleFilter;
import com.tirion.db.sql.exec.operator.physical.filter.TuplesFilter;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleFilterGenerator implements FilterGenerator {
	
	private static final String TUPLE_TEMPLATE_NAME  = "templates/tuple-filter-template.vm";
	private static final String TUPLES_TEMPLATE_NAME = "templates/tuples-filter-template.vm";

	/**
	 * Name of Velocity template.
	 */
	private final String templateName;
	
	/**
	 * Expression of matches(Tuple) method.
	 */
	private final StringBuilder matchesExpression = new StringBuilder();
	
	/**
	 * Initialization of {@link Set}s.
	 */
	private final StringBuilder constructorExpression = new StringBuilder();
	
	/**
	 * Declaration of sets.
	 */
	private final StringBuilder fieldDeclarations = new StringBuilder(); 
	
	/**
	 * Variable name counter.
	 */
	private int counter = 0;
	
	/**
	 * True if {@link TuplesFilter}, false if {@link TupleFilter}.
	 */
	public SimpleFilterGenerator(boolean isTuples) {
		this.templateName = isTuples ? TUPLES_TEMPLATE_NAME : TUPLE_TEMPLATE_NAME;
	}
	
	@Override
	public Source generateTupleFilter(Fields virtEntity, BoolExpression expression) {
		try {
			onExpression(virtEntity, null, expression);
			return generateSourceCode();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Source generateTuplesFilter(Fields leftVirtEntity, Fields rightVirtEntity, BoolExpression expression) {
		try {
			onExpression(leftVirtEntity, rightVirtEntity, expression);
			return generateSourceCode();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Source generateSourceCode() throws Exception {
		VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();
        Template template = engine.getTemplate(templateName);
        
		String className = UuidUtil.nextJavaIdentifier("TupleFilter");		
		VelocityContext ctx = new VelocityContext();
		ctx.put("constructorExpression", constructorExpression);
		ctx.put("fieldDeclarations", fieldDeclarations);
		ctx.put("matchesExpression", matchesExpression);
		ctx.put("name", className);
		
		Writer writer = new StringWriter();
		template.merge(ctx, writer);
		writer.close();
		return new Source(className, writer.toString());
	}
	
	private void onExpression(Fields leftVirtEntity, Fields rightVirtEntity, BoolExpression expression) {
		if(expression instanceof And) {
			List<BoolExpression> children = ((And) expression).getChildren();
			append("(");
			for (int i = 0; i < children.size(); i++) {
				BoolExpression child = children.get(i);
				onExpression(leftVirtEntity, rightVirtEntity, child);
				if(i < children.size() - 1) {
					append(" && ");
				}
			}
			append(")");
		} else if(expression instanceof Or) {
			List<BoolExpression> children = ((Or) expression).getChildren();
			append("(");
			for (int i = 0; i < children.size(); i++) {
				BoolExpression child = children.get(i);
				onExpression(leftVirtEntity, rightVirtEntity, child);
				if(i < children.size() - 1) {
					append(" || ");
				}
			}
			append(")");
		} else {
			processCompare(leftVirtEntity, rightVirtEntity, (Compare) expression);
		}
	}
	
	private void processCompare(Fields leftVirtEntity, Fields rightVirtEntity, Compare compare) {
		if(compare instanceof InCompare) {
			InCompare inCompare = (InCompare) compare;
			Fields virtEntity = getOwningTuple(leftVirtEntity, rightVirtEntity, inCompare.getFieldRef());
			processInCompare(virtEntity, virtEntity == leftVirtEntity ? "left" : "right", inCompare);
		} else if(compare instanceof BetweenCompare) {
			BetweenCompare betweenCompare = (BetweenCompare) compare;
			Fields virtEntity = getOwningTuple(leftVirtEntity, rightVirtEntity, betweenCompare.getFieldRef());
			processBetweenCompare(virtEntity, virtEntity == leftVirtEntity ? "left" : "right", betweenCompare);
		} else if(compare instanceof NullCompare) {
			NullCompare nullCompare = (NullCompare) compare;
			Fields virtEntity = getOwningTuple(leftVirtEntity, rightVirtEntity, nullCompare.getFieldRef());
			processNullCompare(virtEntity, virtEntity == leftVirtEntity ? "left" : "right", nullCompare);
		} else {
			processSimpleCompare(leftVirtEntity, rightVirtEntity, (SimpleCompare)compare);
		}
	}
	
	private void processSimpleCompare(Fields leftFields, Fields rightFields, SimpleCompare compare) {
		{			
			FieldRef fieldRef = compare.getFieldRef();
			Fields fields = getOwningTuple(leftFields, rightFields, fieldRef);
			Field field = fields.getField(fieldRef);
			String tupleName = fields == leftFields ? "left" : "right";
			append(tupleName + ".get" + field.getNativeType().getLargeTypeName() + "(" + field.getIndex() + ")");		
		}
		switch (compare.getKind()) {
			case EQ_OPERATOR:
				append(" == ");
				break;
			case NEQ_OPERATOR:
				append(" != ");
				break;
			case LT_OPERATOR:
				append(" < ");
				break;
			case LTEQ_OPERATOR:
				append(" <= ");
				break;
			case GT_OPERATOR:
				append(" > ");
				break;
			case GTEQ_OPERATOR:
				append(" >= ");
				break;
			default:
				throw new NotYetImplementedException(compare.getKind().name());
		}
		{
			if(compare.getRight() instanceof Constant) {
				Constant constant = (Constant) compare.getRight();
				append("((" + constant.getType().getSmallTypeName() + ") " + getJavaConstant(constant.getType(), constant.getValue()) + ")");
			} else {
				FieldRef fieldRef = (FieldRef) compare.getRight();
				Fields virtEntity = getOwningTuple(leftFields, rightFields, fieldRef);
				Field field = virtEntity.getField(fieldRef);
				String tupleName = virtEntity == leftFields ? "left" : "right";
				append(tupleName + ".get" + field.getDeclaredType().getLargeTypeName() + "(" + field.getIndex() + ")");
			}
		}
	}	
	
	private void processInCompare(Fields virtEntity, String tupleName, InCompare compare) {
		String setName = nextVariableName("set");
		generateInCompareFieldsDeclarations(setName, virtEntity.getField(compare.getFieldRef().getName()).getNativeType());
		generateInCompareConstructorExpression(setName, compare);
		generateInCompareMatchesCode(tupleName, setName, compare, virtEntity);
	}
	
	private void generateInCompareFieldsDeclarations(String setName, Type type) {
		fieldDeclarations.append("private Set<" + type.getLargeTypeName() + "> " + setName + ";\n");
	}

	private void generateInCompareConstructorExpression(String setName, InCompare compare) {
		constructorExpression.append(setName + " = new HashSet<" + compare.getFieldRef().getType().getLargeTypeName() + ">();\n");
		for(Object value : compare.getSet().getSortedSet()) {			
			constructorExpression.append(setName + ".add(" + getJavaConstant(compare.getSet().getType(), value) + ");");
		}
	}
	
	private void generateInCompareMatchesCode(String tupleName, String setName, InCompare compare, Fields virtEntity) {
		Field field = virtEntity.getField(compare.getFieldRef());
		append("(");
		append(getNullCheckSafety(tupleName, field));
		append(" && ");
		if(compare.hasNot()) {
			append("!");
		}
		append(setName + ".contains(" + tupleName + ".get" + field.getNativeType().getLargeTypeName() + "(" + field.getIndex() + "));");
		append(")");
	}
	
	private void processBetweenCompare(Fields virtEntity, String tupleVarName, BetweenCompare compare) {
		Field field = virtEntity.getField(compare.getFieldRef());
		ConstantRange range = compare.getRange();
		append("(");
		append(getNullCheckSafety(tupleVarName, field));
		append(" && (");
		append(getJavaConstant(range.getType(), range.getLow()) + " <= " + getValueExtract(tupleVarName, field));
		append(" && ");
		append(getValueExtract(tupleVarName, field) + " <= " + getJavaConstant(range.getType(), range.getHigh()));
		append(")");
		append(")");
	}
	
	private void processNullCompare(Fields virtEntity, String tupleVarName, NullCompare compare) {
		if(compare instanceof IsNotNullCompare) {
			append("!");
		}		
		append(tupleVarName + ".isNull(" + virtEntity.getField(compare.getFieldRef()).getIndex() + ")");
	}
	
	private Fields getOwningTuple(Fields leftVirtEntity, Fields rightVirtEntity, FieldRef fieldRef) {
		if(leftVirtEntity == null) {
			return rightVirtEntity;
		}
		if(rightVirtEntity == null) {
			return leftVirtEntity;
		}
		if(leftVirtEntity.fieldExists(fieldRef) && rightVirtEntity.fieldExists(fieldRef)) {
			throw new IllegalStateException("Same field exists in both left & right virtual entity '" + fieldRef.toString() + "'");
		}
		if(leftVirtEntity.fieldExists(fieldRef)) {
			return leftVirtEntity;
		}
		if(rightVirtEntity.fieldExists(fieldRef)) {
			return rightVirtEntity;
		}
		throw new IllegalStateException("Unable to find field '" + fieldRef.toString() + "'");
	}
	
	private String nextVariableName(String prefix) {
		return prefix + "_" + counter++;
	}
	
	private String getNullCheckSafety(String tupleVarName, Field field) {
		return "!" + tupleVarName + ".isNull(" + field.getIndex() + ")";
	}
	
	private String getValueExtract(String tupleVarName, Field field) {
		return tupleVarName + ".get" + field.getNativeType().getLargeTypeName() + "(" + field.getIndex() + ")";
	}
	
	private String getJavaConstant(Type type, Object value) {
		switch (type) {
			case VARCHAR:
				return "\"" + value + "\"";
			case LONG:
				return value + "L";
			case DOUBLE:
				return value + "D";
			case FLOAT:
				return value + "F";
			default:
				return value.toString();
		}
	}
	
	private void append(String value) {
		matchesExpression.append(value);
	}
}
