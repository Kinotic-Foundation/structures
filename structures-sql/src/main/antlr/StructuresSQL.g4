grammar StructuresSQL;

migrations
    : statement+ EOF
    ;

statement
    : createTableStatement
    | createComponentTemplateStatement
    | createIndexTemplateStatement
    | alterTableStatement
    | reindexStatement
    | updateStatement
    | deleteStatement
    | comment
    ;

createTableStatement
    : CREATE TABLE ID LPAREN columnDefinition (COMMA columnDefinition)* RPAREN SEMICOLON
    ;

createComponentTemplateStatement
    : CREATE COMPONENT TEMPLATE ID LPAREN componentDefinition (COMMA componentDefinition)* RPAREN SEMICOLON
    ;

createIndexTemplateStatement
    : CREATE INDEX TEMPLATE ID FOR STRING USING STRING (WITH LPAREN componentDefinition (COMMA componentDefinition)* RPAREN)? SEMICOLON
    ;

componentDefinition
    : NUMBER_OF_SHARDS EQUALS INTEGER_LITERAL
    | NUMBER_OF_REPLICAS EQUALS INTEGER_LITERAL
    | columnDefinition
    ;

alterTableStatement
    : ALTER TABLE ID ADD COLUMN ID type SEMICOLON
    ;

reindexStatement
    : REINDEX ID INTO ID reindexOptions? SEMICOLON
    ;

reindexOptions
    : WITH LPAREN reindexOption (COMMA reindexOption)* RPAREN
    ;

reindexOption
    : CONFLICTS EQUALS (ABORT | PROCEED)
    | MAX_DOCS EQUALS INTEGER_LITERAL
    | SLICES EQUALS (AUTO | INTEGER_LITERAL)
    | SIZE EQUALS INTEGER_LITERAL
    | SOURCE_FIELDS EQUALS STRING
    | QUERY EQUALS STRING
    | SCRIPT EQUALS STRING
    ;

updateStatement
    : UPDATE ID SET assignment (COMMA assignment)* WHERE whereClause SEMICOLON
    ;

deleteStatement
    : DELETE FROM ID WHERE whereClause SEMICOLON
    ;

assignment
    : ID EQUALS expression
    ;

expression
    : PARAMETER
    | STRING
    | INTEGER_LITERAL
    | BOOLEAN_LITERAL
    | ID operator expression  // e.g., age + 1, status == 'active'
    | LPAREN expression RPAREN
    ;

operator
    : PLUS
    | MINUS
    | MULTIPLY
    | DIVIDE
    | EQUALS  // For expressions like status == 'active'
    ;

whereClause
    : condition
    | LPAREN whereClause RPAREN
    | whereClause AND whereClause
    | whereClause OR whereClause
    ;

condition
    : ID comparisonOperator (PARAMETER | STRING | INTEGER_LITERAL | BOOLEAN_LITERAL)
    ;

comparisonOperator
    : EQUALS
    | NOT_EQUALS
    | LESS_THAN
    | GREATER_THAN
    | LESS_THAN_EQUALS
    | GREATER_THAN_EQUALS
    ;

columnDefinition
    : ID type
    ;

type
    : TEXT
    | KEYWORD
    | INTEGER
    | BOOLEAN
    | DATE
    ;

comment
    : COMMENT
    ;

// Tokens
CREATE: 'CREATE';
TABLE: 'TABLE';
COMPONENT: 'COMPONENT';
INDEX: 'INDEX';
TEMPLATE: 'TEMPLATE';
FOR: 'FOR';
USING: 'USING';
ALTER: 'ALTER';
ADD: 'ADD';
COLUMN: 'COLUMN';
REINDEX: 'REINDEX';
INTO: 'INTO';
WITH: 'WITH';
CONFLICTS: 'CONFLICTS';
ABORT: 'ABORT';
PROCEED: 'PROCEED';
MAX_DOCS: 'MAX_DOCS';
SLICES: 'SLICES';
AUTO: 'AUTO';
SIZE: 'SIZE';
SOURCE_FIELDS: 'SOURCE_FIELDS';
QUERY: 'QUERY';
SCRIPT: 'SCRIPT';
NUMBER_OF_SHARDS: 'NUMBER_OF_SHARDS';
NUMBER_OF_REPLICAS: 'NUMBER_OF_REPLICAS';
UPDATE: 'UPDATE';
DELETE: 'DELETE';
FROM: 'FROM';
SET: 'SET';
WHERE: 'WHERE';
AND: 'AND';
OR: 'OR';
TEXT: 'TEXT';
KEYWORD: 'KEYWORD';
INTEGER: 'INTEGER';
BOOLEAN: 'BOOLEAN';
DATE: 'DATE';
LPAREN: '(';
RPAREN: ')';
COMMA: ',';
SEMICOLON: ';';
EQUALS: '==';
NOT_EQUALS: '!=';
LESS_THAN: '<';
GREATER_THAN: '>';
LESS_THAN_EQUALS: '<=';
GREATER_THAN_EQUALS: '>=';
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
PARAMETER: '?';
STRING: '\'' ~[']* '\'';
ID: [a-zA-Z_][a-zA-Z_0-9]*;
INTEGER_LITERAL: [0-9]+;
BOOLEAN_LITERAL: 'true' | 'false';
COMMENT: '--' ~[\r\n]* -> skip;
WS: [ \t\r\n]+ -> skip;