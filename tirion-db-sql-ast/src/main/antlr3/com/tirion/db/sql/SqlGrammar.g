// @author Veljko Zivkovic, Copyright (C) All Rights Reserved
grammar SqlGrammar;

options { 
	output=AST; 
}

tokens {
	FUNCTION_CALL;
	
	SELECT_STATEMENT;
	CREATE_STATEMENT;
	LOAD_STATEMENT;
	DROP_STATEMENT;
	SET_STATEMENT;
	GET_STATEMENT;
	EXECUTE_STATEMENT;
	SHOW_STATEMENT;
	DESCRIBE_STATEMENT;
	DELETE_STATEMENT;
	INSERT_STATEMENT;
	TRUNCATE_STATEMENT;
}

@lexer::header {
	package com.tirion.db.sql;
}

@parser::header {
   	 package com.tirion.db.sql;
}

@lexer::members {
    public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
		throw new java.lang.RuntimeException("Exception in lexer/parser", e);
    }
}

@parser::members {
    public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
		throw new java.lang.RuntimeException("Exception in lexer/parser", e);
    }
}

statement__
	:	(selectStatement__ | createTableStatement__ | loadTableStatement__ | 
		dropStatement__ | setStatement__ | getStatement__ | 
		executeFileStatement__ | showTablesStatement__ | describeTableStatement__ |
		deleteStatement__ | insertStatement__ | truncateStatement__) SEMI_COLON!;

truncateStatement__
	:	TRUNCATE TABLE NAME -> ^(TRUNCATE_STATEMENT NAME);

deleteStatement__
	:	DELETE FROM TABLE? NAME whereClause__ -> ^(DELETE_STATEMENT NAME whereClause__);
	
insertStatement__
	:	INSERT INTO TABLE? NAME selectStatement__ -> ^(INSERT_STATEMENT NAME selectStatement__);

showTablesStatement__
	:	SHOW TABLES -> ^(SHOW_STATEMENT);
	
describeTableStatement__
	:	DESCRIBE TABLE NAME -> ^(DESCRIBE_STATEMENT NAME);
	
executeFileStatement__
	:	EXECUTE FILE STRING -> ^(EXECUTE_STATEMENT STRING);	

setStatement__: 	SET STRING EQ STRING -> ^(SET_STATEMENT STRING STRING);	

getStatement__
	:	GET STRING -> ^(GET_STATEMENT STRING);

dropStatement__
	:	DROP TABLE NAME -> ^(DROP_STATEMENT NAME);	
	
//dropPartition__
//	:	PARTITION! INT FROM!;

loadTableStatement__
	:	LOAD TABLE NAME FROM FILE STRING
			-> ^(LOAD_STATEMENT NAME STRING);
	
//onWorker__:	ON! WORKER^ STRING;
	
//loadPartitions__
//	:	PARTITION^ BY! NAME (COMMA! NAME)*;

createTableStatement__
	:	CREATE TABLE NAME LPAREN createColumnList__ RPAREN tableType__? 
			-> ^(CREATE_STATEMENT NAME createColumnList__ tableType__?);

tableType__
	:	AS^ (LARGE | SMALL);

createColumnList__
	:	createColumn__ (COMMA! createColumn__)*;
	
createColumn__
	:	NAME^ COLUMN_TYPE columnNullability__? columnOptions__? ;
	
columnOptions__
	:	STRING;
	
columnNullability__
	:	NOT? NULL^;

selectStatement__
	:	selectClause__  
		fromClause__ 
		joinClause__?
		whereClause__? 
		groupByClause__? 
		havingClause__?
		orderByClause__? 
		limitClause__? -> ^(SELECT_STATEMENT selectClause__  
					  fromClause__ 
					  joinClause__?
					  whereClause__? 
					 groupByClause__? 
					 havingClause__?
					 orderByClause__? 
					 limitClause__?);

selectClause__
	:	SELECT^ columnRef__ (COMMA! columnRef__)*;

columnRef__
	:	NAME DOT! STAR^  | prefixedName__^ alias__? | functionCall__^ alias__?;
	
functionCall__
	:	NAME LPAREN functionParameters__ RPAREN -> ^(FUNCTION_CALL NAME functionParameters__);
	
functionParameters__
	:	functionParameter__ (COMMA! functionParameter__)*;	
functionParameter__
	:	prefixedName__ | constantValue__ | STAR ;
	
// ========== window clause
/*
windowClause__
	:	functionCall__ OVER LPAREN windowPartitionClause__? windowOrderClause__? windowFrameClause__ RPAREN;
	
windowPartitionClause__
	:	PARTITION BY NAME (COMMA NAME)*;
windowOrderClause__
	:	ORDER BY NAME (COMMA NAME)*;
windowFrameClause__
	:	(ROWS | RANGE) BETWEEN startFrameSpec__ AND endFrameSpec__;
	
startFrameSpec__
	:	range__ PRECEDING | CURRENT ROW;
endFrameSpec__
	:	range__ FOLLOWING | CURRENT ROW;

range__	:	INT | UNBOUNDED;
*/
// ======================	

fromClause__
	:	FROM^ (NAME alias__? | subSelectStatement__ alias__)  ;

subSelectStatement__
	:	LPAREN! selectStatement__ RPAREN!;

whereClause__
	:	WHERE^ boolExpr__;	
	
				
//  +++++++++++++++++++++++++++++++++++++++
boolExpr__
	:	boolAndExpr__;

boolAndExpr__
	:	boolOrExpr__ (AND^ boolOrExpr__)*;	
	
boolOrExpr__
	:	boolNotExpr__ (OR^ boolNotExpr__)*;

boolNotExpr__ : /*NOT^ boolTerm__ |*/ boolTerm__; // TODO support for NOT

boolTerm__
	:	boolComp__ | aritExpression__  | LPAREN! boolAndExpr__ RPAREN! ;
	
boolComp__
	:	prefixedName__ IS^ boolLiteral__;

boolLiteral__
	:	FALSE | TRUE;
	
aritExpression__
	:	betweenExpression__ | compareExpression__ |  inExpression__ | nullExpression__ | existsExpression__;
	
existsExpression__
	:	NOT? EXISTS^ LPAREN! selectStatement__ RPAREN!;

betweenExpression__
	:	prefixedName__ BETWEEN^ constantValue__ AND! constantValue__;

compareExpression__
	:	prefixedName__ op__^ (constantValue__ | prefixedName__);

inExpression__
	:	prefixedName__ NOT? IN^ LPAREN! inValueExpression__ RPAREN!;
	
inValueExpression__
	:	constantList__ | selectStatement__;

constantList__
	:	constantValue__ (COMMA! constantValue__)*;
	
op__	:	EQ | NEQ | LT | LTEQ | GT | GTEQ;

nullExpression__
	:	prefixedName__ IS! NOT? NULL^;


// +++++++++++++++++++++++++++++++++++++++++

joinClause__
	:	joinDefinition__ (joinDefinition__)*;

joinDefinition__
	:	(INNER | LEFT) JOIN^ joinTable__  joinCondition__;
	
joinTable__
	:	(NAME alias__? | subSelectStatement__ alias__) ;
	
joinCondition__
	:	ON^ boolExpr__;

groupByClause__
	:	GROUP^ BY! prefixedColumnList__ ;
orderByClause__
	:	ORDER^ BY! orderByColumns__;

orderByColumns__
	:	orderByColumn__ (COMMA! orderByColumn__)*;
	
orderByColumn__
	:	prefixedName__^ (ASC | DESC)?;

havingClause__
	:	HAVING^ boolExpr__;	

limitClause__
	:	LIMIT^ INT ;
	
prefixedColumnList__
	: prefixedName__ (COMMA! prefixedName__)*;

prefixedName__
	:	(NAME DOT!)? NAME^;
	
// used in GET & SET statements
namespaceName__:(NAME DOT!)* NAME;

constantValue__
	:	signedNumber__ | STRING;

signedNumber__
	: unaryOperator__? number__^;
	
number__:	INT | FLOAT;

alias__	:	AS^ NAME;

unaryOperator__
	:	PLUS | MINUS;

TRUNCATE:	'TRUNCATE';
DELETE	:	'DELETE';
INSERT	:	'INSERT';
INTO	:	'INTO';
EXECUTE	:	'EXECUTE';
CREATE	:	'CREATE';
SELECT	:	'SELECT';
AS	:	'AS';
FROM	:	'FROM';
WHERE	:	'WHERE';
GROUP	:	'GROUP';
ORDER	:	'ORDER';
BY	:	'BY';
AND	:	'AND';
OR	:	'OR';
NOT	:	'NOT';
LIMIT	:	'LIMIT';
IN	:	'IN';
IS	:	'IS';
TRUE	:	'TRUE';
FALSE	:	'FALSE';
TABLE	:	'TABLE';
LOAD	:	'LOAD';
FILE	:	'FILE';
DELIMITED
	:	'DELIMITED';
DROP	:	'DROP';
PARTITION
	:	'PARTITION';
ROW	:	'ROW';
ROWS	:	'ROWS';
RANGE	:	'RANGE';
BETWEEN	:	'BETWEEN';
PRECEDING
	:	'PRECEDING';
FOLLOWING
	:	'FOLLOWING';
UNBOUNDED
	:	'UNBOUNDED';
CURRENT	:	'CURRENT';
ROLLUP	:	'ROLLUP';
CUBE	:	'CUBE';
HAVING	:	'HAVING';
OVER	:	'OVER';

INNER	:	'INNER';
LEFT	:	'LEFT';
JOIN	:	'JOIN';
ON	:	'ON';
NULL	:	'NULL';
SET	:	'SET';
GET	:	'GET';
SHOW	:	'SHOW';
TABLES	:	'TABLES';
DESCRIBE:	'DESCRIBE';
SMALL	:	'SMALL';
LARGE	:	'LARGE';
EXISTS	:	'EXISTS';
WORKER	:	'WORKER';

COLUMN_TYPE
	:	BOOL_TYPE | BYTE_TYPE | SHORT_TYPE | INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE | VARCHAR_TYPE | TIMESTAMP | DATE | TIME;
fragment BOOL_TYPE
	:	'BOOLEAN';
fragment BYTE_TYPE
	:	'BYTE';
fragment SHORT_TYPE
	:	'SHORT';
fragment INT_TYPE	:	'INT';
fragment LONG_TYPE
	:	'LONG';
fragment FLOAT_TYPE
	:	'FLOAT';
fragment DOUBLE_TYPE
	:	'DOUBLE';
fragment VARCHAR_TYPE
	:	'VARCHAR';
	
fragment TIMESTAMP
	:	'TIMESTAMP';
fragment DATE
	:	'DATE';
fragment TIME
	:   'TIME';

ASC	:	'ASC';
DESC	:	'DESC';
STAR	:	'*';
PLUS	:	'+';
MINUS	:	'-';
COLON	:	':';
DOT	:	'.';
SEMI_COLON
	:	';';
COMMA	:	',';
LPAREN:	'(';
RPAREN:	')';

EQ	:	'=';
NEQ	:	'!=';
LT	:	'<';
LTEQ	:	'<=';
GT	:	'>';
GTEQ	:	'>=';


NAME  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

INT :	'0'..'9'+
    ;

FLOAT
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '.' ('0'..'9')+ EXPONENT?
    |   ('0'..'9')+ EXPONENT
    ;

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

STRING
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
   ;


CHAR:  '\'' ( ESC_SEQ | ~('\''|'\\') ) '\'';

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;
