parser grammar ElasticsearchParser;

options {
	tokenVocab = ElasticsearchLexer;
}

sql: (
		deleteOperation
		| updateOperation
	) SEMI? EOF;

//OPERATIONS
deleteOperation: DELETE FROM tableRef whereClause?;

updateOperation:
	UPDATE tableRef SET ID EQ identity (COMMA ID EQ identity)*  whereClause?;


nameClause:
	LPAREN nameClause RPAREN														# lrName
	| DISTINCT fieldName = nameClause												# distinctName
	| left = nameClause op = (STAR | DIVIDE | MOD | PLUS | MINUS) right = nameClause	# binaryName
	| functionName = ID params = collection									# functionName
	| highlighter = HIGHLIGHTER? field = ID									# fieldName
;

identity: ID | number = ( INT | FLOAT ) | str = STRING | list = identityList;

expression:
	LPAREN expression RPAREN															# lrExpr
	| leftExpr = expression operator = (MUL | DIV | MOD) rightExpr = expression				# binary
	| leftExpr = expression operator = (PLUS | MINUS) rightExpr = expression				# binary
	| leftExpr = expression operator = (LSH | RSH | USH) rightExpr = expression				# binary
	| leftExpr = expression operator = (LT | LTE | GT | GTE) rightExpr = expression			# binary
	| leftExpr = expression operator = (EQ | NE | AEQ | NAEQ | TEQ | NTEQ| MPPEQ| NMPPEQ) rightExpr = expression				# binary
	| leftExpr = expression operator = AND rightExpr = expression				# binary
	| leftExpr = expression operator = OR rightExpr = expression					# binary
	| expr = nameClause BETWEEN left = identity AND right = identity							# betweenAnd
	| rangeClause #betweenAnd
	| leftExpr = expression operator = XOR rightExpr = expression							# binary
	| leftExpr = expression operator = BWOR rightExpr = expression							# binary
	| <assoc = right> expr = expression COND leftExpr = expression COLON rightExpr = expression	# conditional
	| inClause																			# in
	| nameClause																				# nameExpr
	| identity																			# primitive
	| isClause																			# binary
	| nestedClause																		# nested
	| likeClause																		# binar
	| fullTextClause																	# fullText
	| notClause 															            # binary
;

rangeClause:
    field = nameClause RANGED IN (LPAREN|LBRACKET) left = rangeItemClause COMMA right = rangeItemClause (RPAREN|RBRACKET)
;

rangeItemClause:
    STRING|INT|FLOAT
;

collection: LPAREN identity? ( COMMA identity)* RPAREN;

identityList: LBRACKET identity (COMMA identity)* RBRACKET;

likeClause: field = nameClause not = NOT? funName=(FUZZY|PREFIX|WILDCARD)* LIKE pattern = STRING;

notClause:
    NOT expression
;

isClause: field = nameClause IS not = NOT? NULL;

inClause: left = nameClause NOT? IN right = inRightOperandList;

inRightOperandList:
	inRightOperand
	| LPAREN inRightOperand (COMMA inRightOperand)* RPAREN;

inRightOperand:
	identity # constLiteral
	| left = inRightOperand op = (
		STAR
		| DIV
		| MOD
		| PLUS
		| MINUS
	) right = inRightOperand # arithmeticLiteral;

tableRef: indexName = ID ( AS alias = ID)?;

fullTextClause: queryStringClause|multiMatchClause;

queryStringClause: QUERY BY STRING;

multiMatchClause:
	LPAREN nameClause (COMMA nameClause)*  RPAREN AEQ value = STRING
;

nestedClause:
	LBRACKET nestedPath = ID COMMA query = expression RBRACKET;

whereClause: WHERE expression;




