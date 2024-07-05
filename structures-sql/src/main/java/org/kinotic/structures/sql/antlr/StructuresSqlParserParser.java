// Generated from /Users/navid/workspace/git/continuum/structures/structures-sql/src/main/resources/antlr/StructuresSqlParser.g4 by ANTLR 4.13.1
package org.kinotic.structures.sql.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class StructuresSqlParserParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, ABSENT=1, ADD=2, ADMIN=3, AFTER=4, ALL=5, ALTER=6, 
		ANALYZE=7, AND=8, ANY=9, ARRAY=10, AS=11, ASC=12, AT=13, AUTHORIZATION=14, 
		BEGIN=15, BERNOULLI=16, BETWEEN=17, BOTH=18, BY=19, CALL=20, CALLED=21, 
		CASCADE=22, CASE=23, CAST=24, CATALOG=25, CATALOGS=26, COLUMN=27, COLUMNS=28, 
		COMMENT=29, COMMIT=30, COMMITTED=31, CONDITIONAL=32, CONSTRAINT=33, COUNT=34, 
		COPARTITION=35, CREATE=36, CROSS=37, CUBE=38, CURRENT=39, CURRENT_CATALOG=40, 
		CURRENT_DATE=41, CURRENT_PATH=42, CURRENT_ROLE=43, CURRENT_SCHEMA=44, 
		CURRENT_TIME=45, CURRENT_TIMESTAMP=46, CURRENT_USER=47, DATA=48, DATE=49, 
		DAY=50, DEALLOCATE=51, DECLARE=52, DEFAULT=53, DEFINE=54, DEFINER=55, 
		DELETE=56, DENY=57, DESC=58, DESCRIBE=59, DESCRIPTOR=60, DETERMINISTIC=61, 
		DISTINCT=62, DISTRIBUTED=63, DO=64, DOUBLE=65, DROP=66, ELSE=67, EMPTY=68, 
		ELSEIF=69, ENCODING=70, END=71, ERROR=72, ESCAPE=73, EXCEPT=74, EXCLUDING=75, 
		EXECUTE=76, EXISTS=77, EXPLAIN=78, EXTRACT=79, FALSE=80, FETCH=81, FILTER=82, 
		FINAL=83, FIRST=84, FOLLOWING=85, FOR=86, FORMAT=87, FROM=88, FULL=89, 
		FUNCTION=90, FUNCTIONS=91, GRACE=92, GRANT=93, GRANTED=94, GRANTS=95, 
		GRAPHVIZ=96, GROUP=97, GROUPING=98, GROUPS=99, HAVING=100, HOUR=101, IF=102, 
		IGNORE=103, IMMEDIATE=104, IN=105, INCLUDING=106, INITIAL=107, INNER=108, 
		INPUT=109, INSERT=110, INTERSECT=111, INTERVAL=112, INTO=113, INVOKER=114, 
		IO=115, IS=116, ISOLATION=117, ITERATE=118, JOIN=119, JSON=120, JSON_ARRAY=121, 
		JSON_EXISTS=122, JSON_OBJECT=123, JSON_QUERY=124, JSON_TABLE=125, JSON_VALUE=126, 
		KEEP=127, KEY=128, KEYS=129, LANGUAGE=130, LAST=131, LATERAL=132, LEADING=133, 
		LEAVE=134, LEFT=135, LEVEL=136, LIKE=137, LIMIT=138, LISTAGG=139, LOCAL=140, 
		LOCALTIME=141, LOCALTIMESTAMP=142, LOGICAL=143, LOOP=144, MAP=145, MATCH=146, 
		MATCHED=147, MATCHES=148, MATCH_RECOGNIZE=149, MATERIALIZED=150, MEASURES=151, 
		MERGE=152, MINUTE=153, MONTH=154, NATURAL=155, NESTED=156, NEXT=157, NFC=158, 
		NFD=159, NFKC=160, NFKD=161, NO=162, NONE=163, NORMALIZE=164, NOT=165, 
		NULL=166, NULLIF=167, NULLS=168, OBJECT=169, OF=170, OFFSET=171, OMIT=172, 
		ON=173, ONE=174, ONLY=175, OPTION=176, OR=177, ORDER=178, ORDINALITY=179, 
		OUTER=180, OUTPUT=181, OVER=182, OVERFLOW=183, PARTITION=184, PARTITIONS=185, 
		PASSING=186, PAST=187, PATH=188, PATTERN=189, PER=190, PERIOD=191, PERMUTE=192, 
		PLAN=193, POSITION=194, PRECEDING=195, PRECISION=196, PREPARE=197, PRIVILEGES=198, 
		PROPERTIES=199, PRUNE=200, QUOTES=201, RANGE=202, READ=203, RECURSIVE=204, 
		REFRESH=205, RENAME=206, REPEAT=207, REPEATABLE=208, REPLACE=209, RESET=210, 
		RESPECT=211, RESTRICT=212, RETURN=213, RETURNING=214, RETURNS=215, REVOKE=216, 
		RIGHT=217, ROLE=218, ROLES=219, ROLLBACK=220, ROLLUP=221, ROW=222, ROWS=223, 
		RUNNING=224, SCALAR=225, SCHEMA=226, SCHEMAS=227, SECOND=228, SECURITY=229, 
		SEEK=230, SELECT=231, SERIALIZABLE=232, SESSION=233, SET=234, SETS=235, 
		SHOW=236, SOME=237, START=238, STATS=239, SUBSET=240, SUBSTRING=241, SYSTEM=242, 
		TABLE=243, TABLES=244, TABLESAMPLE=245, TEXT=246, TEXT_STRING=247, THEN=248, 
		TIES=249, TIME=250, TIMESTAMP=251, TO=252, TRAILING=253, TRANSACTION=254, 
		TRIM=255, TRUE=256, TRUNCATE=257, TRY_CAST=258, TYPE=259, UESCAPE=260, 
		UNBOUNDED=261, UNCOMMITTED=262, UNCONDITIONAL=263, UNION=264, UNIQUE=265, 
		UNKNOWN=266, UNMATCHED=267, UNNEST=268, UNTIL=269, UPDATE=270, USE=271, 
		USER=272, USING=273, UTF16=274, UTF32=275, UTF8=276, VALIDATE=277, VALUE=278, 
		VALUES=279, VERBOSE=280, VERSION=281, VIEW=282, WHEN=283, WHERE=284, WHILE=285, 
		WINDOW=286, WITH=287, WITHIN=288, WITHOUT=289, WORK=290, WRAPPER=291, 
		WRITE=292, YEAR=293, ZONE=294, EQ=295, NEQ=296, LT=297, LTE=298, GT=299, 
		GTE=300, PLUS=301, MINUS=302, ASTERISK=303, SLASH=304, PERCENT=305, CONCAT=306, 
		QUESTION_MARK=307, SEMICOLON=308, STRING=309, UNICODE_STRING=310, BINARY_LITERAL=311, 
		INTEGER_VALUE=312, DECIMAL_VALUE=313, DOUBLE_VALUE=314, IDENTIFIER=315, 
		DIGIT_IDENTIFIER=316, QUOTED_IDENTIFIER=317, BACKQUOTED_IDENTIFIER=318, 
		SIMPLE_COMMENT=319, BRACKETED_COMMENT=320, WS=321, UNRECOGNIZED=322, DELIMITER=323;
	public static final int
		RULE_singleStatement = 0, RULE_standaloneExpression = 1, RULE_standalonePathSpecification = 2, 
		RULE_standaloneType = 3, RULE_standaloneRowPattern = 4, RULE_standaloneFunctionSpecification = 5, 
		RULE_statement = 6, RULE_rootQuery = 7, RULE_withFunction = 8, RULE_query = 9, 
		RULE_with = 10, RULE_tableElement = 11, RULE_columnDefinition = 12, RULE_likeClause = 13, 
		RULE_properties = 14, RULE_propertyAssignments = 15, RULE_property = 16, 
		RULE_propertyValue = 17, RULE_queryNoWith = 18, RULE_limitRowCount = 19, 
		RULE_rowCount = 20, RULE_queryTerm = 21, RULE_queryPrimary = 22, RULE_sortItem = 23, 
		RULE_querySpecification = 24, RULE_groupBy = 25, RULE_groupingElement = 26, 
		RULE_groupingSet = 27, RULE_windowDefinition = 28, RULE_windowSpecification = 29, 
		RULE_namedQuery = 30, RULE_setQuantifier = 31, RULE_selectItem = 32, RULE_relation = 33, 
		RULE_joinType = 34, RULE_joinCriteria = 35, RULE_sampledRelation = 36, 
		RULE_sampleType = 37, RULE_trimsSpecification = 38, RULE_listAggOverflowBehavior = 39, 
		RULE_listaggCountIndication = 40, RULE_patternRecognition = 41, RULE_measureDefinition = 42, 
		RULE_rowsPerMatch = 43, RULE_emptyMatchHandling = 44, RULE_skipTo = 45, 
		RULE_subsetDefinition = 46, RULE_variableDefinition = 47, RULE_aliasedRelation = 48, 
		RULE_columnAliases = 49, RULE_relationPrimary = 50, RULE_jsonTableColumn = 51, 
		RULE_jsonTableSpecificPlan = 52, RULE_jsonTablePathName = 53, RULE_planPrimary = 54, 
		RULE_jsonTableDefaultPlan = 55, RULE_tableFunctionCall = 56, RULE_tableFunctionArgument = 57, 
		RULE_tableArgument = 58, RULE_tableArgumentRelation = 59, RULE_descriptorArgument = 60, 
		RULE_descriptorField = 61, RULE_copartitionTables = 62, RULE_expression = 63, 
		RULE_booleanExpression = 64, RULE_predicate = 65, RULE_valueExpression = 66, 
		RULE_primaryExpression = 67, RULE_jsonPathInvocation = 68, RULE_jsonValueExpression = 69, 
		RULE_jsonRepresentation = 70, RULE_jsonArgument = 71, RULE_jsonExistsErrorBehavior = 72, 
		RULE_jsonValueBehavior = 73, RULE_jsonQueryWrapperBehavior = 74, RULE_jsonQueryBehavior = 75, 
		RULE_jsonObjectMember = 76, RULE_processingMode = 77, RULE_nullTreatment = 78, 
		RULE_string = 79, RULE_timeZoneSpecifier = 80, RULE_comparisonOperator = 81, 
		RULE_comparisonQuantifier = 82, RULE_booleanValue = 83, RULE_interval = 84, 
		RULE_intervalField = 85, RULE_normalForm = 86, RULE_type = 87, RULE_rowField = 88, 
		RULE_typeParameter = 89, RULE_whenClause = 90, RULE_filter = 91, RULE_mergeCase = 92, 
		RULE_over = 93, RULE_windowFrame = 94, RULE_frameExtent = 95, RULE_frameBound = 96, 
		RULE_rowPattern = 97, RULE_patternPrimary = 98, RULE_patternQuantifier = 99, 
		RULE_updateAssignment = 100, RULE_explainOption = 101, RULE_transactionMode = 102, 
		RULE_levelOfIsolation = 103, RULE_callArgument = 104, RULE_pathElement = 105, 
		RULE_pathSpecification = 106, RULE_functionSpecification = 107, RULE_functionDeclaration = 108, 
		RULE_parameterDeclaration = 109, RULE_returnsClause = 110, RULE_routineCharacteristic = 111, 
		RULE_controlStatement = 112, RULE_caseStatementWhenClause = 113, RULE_elseIfClause = 114, 
		RULE_elseClause = 115, RULE_variableDeclaration = 116, RULE_sqlStatementList = 117, 
		RULE_privilege = 118, RULE_entityKind = 119, RULE_grantObject = 120, RULE_qualifiedName = 121, 
		RULE_queryPeriod = 122, RULE_rangeType = 123, RULE_grantor = 124, RULE_principal = 125, 
		RULE_roles = 126, RULE_privilegeOrRole = 127, RULE_identifier = 128, RULE_number = 129, 
		RULE_authorizationUser = 130, RULE_nonReserved = 131;
	private static String[] makeRuleNames() {
		return new String[] {
			"singleStatement", "standaloneExpression", "standalonePathSpecification", 
			"standaloneType", "standaloneRowPattern", "standaloneFunctionSpecification", 
			"statement", "rootQuery", "withFunction", "query", "with", "tableElement", 
			"columnDefinition", "likeClause", "properties", "propertyAssignments", 
			"property", "propertyValue", "queryNoWith", "limitRowCount", "rowCount", 
			"queryTerm", "queryPrimary", "sortItem", "querySpecification", "groupBy", 
			"groupingElement", "groupingSet", "windowDefinition", "windowSpecification", 
			"namedQuery", "setQuantifier", "selectItem", "relation", "joinType", 
			"joinCriteria", "sampledRelation", "sampleType", "trimsSpecification", 
			"listAggOverflowBehavior", "listaggCountIndication", "patternRecognition", 
			"measureDefinition", "rowsPerMatch", "emptyMatchHandling", "skipTo", 
			"subsetDefinition", "variableDefinition", "aliasedRelation", "columnAliases", 
			"relationPrimary", "jsonTableColumn", "jsonTableSpecificPlan", "jsonTablePathName", 
			"planPrimary", "jsonTableDefaultPlan", "tableFunctionCall", "tableFunctionArgument", 
			"tableArgument", "tableArgumentRelation", "descriptorArgument", "descriptorField", 
			"copartitionTables", "expression", "booleanExpression", "predicate", 
			"valueExpression", "primaryExpression", "jsonPathInvocation", "jsonValueExpression", 
			"jsonRepresentation", "jsonArgument", "jsonExistsErrorBehavior", "jsonValueBehavior", 
			"jsonQueryWrapperBehavior", "jsonQueryBehavior", "jsonObjectMember", 
			"processingMode", "nullTreatment", "string", "timeZoneSpecifier", "comparisonOperator", 
			"comparisonQuantifier", "booleanValue", "interval", "intervalField", 
			"normalForm", "type", "rowField", "typeParameter", "whenClause", "filter", 
			"mergeCase", "over", "windowFrame", "frameExtent", "frameBound", "rowPattern", 
			"patternPrimary", "patternQuantifier", "updateAssignment", "explainOption", 
			"transactionMode", "levelOfIsolation", "callArgument", "pathElement", 
			"pathSpecification", "functionSpecification", "functionDeclaration", 
			"parameterDeclaration", "returnsClause", "routineCharacteristic", "controlStatement", 
			"caseStatementWhenClause", "elseIfClause", "elseClause", "variableDeclaration", 
			"sqlStatementList", "privilege", "entityKind", "grantObject", "qualifiedName", 
			"queryPeriod", "rangeType", "grantor", "principal", "roles", "privilegeOrRole", 
			"identifier", "number", "authorizationUser", "nonReserved"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'ABSENT'", "'ADD'", "'ADMIN'", "'AFTER'", "'ALL'", "'ALTER'", 
			"'ANALYZE'", "'AND'", "'ANY'", "'ARRAY'", "'AS'", "'ASC'", "'AT'", "'AUTHORIZATION'", 
			"'BEGIN'", "'BERNOULLI'", "'BETWEEN'", "'BOTH'", "'BY'", "'CALL'", "'CALLED'", 
			"'CASCADE'", "'CASE'", "'CAST'", "'CATALOG'", "'CATALOGS'", "'COLUMN'", 
			"'COLUMNS'", "'COMMENT'", "'COMMIT'", "'COMMITTED'", "'CONDITIONAL'", 
			"'CONSTRAINT'", "'COUNT'", "'COPARTITION'", "'CREATE'", "'CROSS'", "'CUBE'", 
			"'CURRENT'", "'CURRENT_CATALOG'", "'CURRENT_DATE'", "'CURRENT_PATH'", 
			"'CURRENT_ROLE'", "'CURRENT_SCHEMA'", "'CURRENT_TIME'", "'CURRENT_TIMESTAMP'", 
			"'CURRENT_USER'", "'DATA'", "'DATE'", "'DAY'", "'DEALLOCATE'", "'DECLARE'", 
			"'DEFAULT'", "'DEFINE'", "'DEFINER'", "'DELETE'", "'DENY'", "'DESC'", 
			"'DESCRIBE'", "'DESCRIPTOR'", "'DETERMINISTIC'", "'DISTINCT'", "'DISTRIBUTED'", 
			"'DO'", "'DOUBLE'", "'DROP'", "'ELSE'", "'EMPTY'", "'ELSEIF'", "'ENCODING'", 
			"'END'", "'ERROR'", "'ESCAPE'", "'EXCEPT'", "'EXCLUDING'", "'EXECUTE'", 
			"'EXISTS'", "'EXPLAIN'", "'EXTRACT'", "'FALSE'", "'FETCH'", "'FILTER'", 
			"'FINAL'", "'FIRST'", "'FOLLOWING'", "'FOR'", "'FORMAT'", "'FROM'", "'FULL'", 
			"'FUNCTION'", "'FUNCTIONS'", "'GRACE'", "'GRANT'", "'GRANTED'", "'GRANTS'", 
			"'GRAPHVIZ'", "'GROUP'", "'GROUPING'", "'GROUPS'", "'HAVING'", "'HOUR'", 
			"'IF'", "'IGNORE'", "'IMMEDIATE'", "'IN'", "'INCLUDING'", "'INITIAL'", 
			"'INNER'", "'INPUT'", "'INSERT'", "'INTERSECT'", "'INTERVAL'", "'INTO'", 
			"'INVOKER'", "'IO'", "'IS'", "'ISOLATION'", "'ITERATE'", "'JOIN'", "'JSON'", 
			"'JSON_ARRAY'", "'JSON_EXISTS'", "'JSON_OBJECT'", "'JSON_QUERY'", "'JSON_TABLE'", 
			"'JSON_VALUE'", "'KEEP'", "'KEY'", "'KEYS'", "'LANGUAGE'", "'LAST'", 
			"'LATERAL'", "'LEADING'", "'LEAVE'", "'LEFT'", "'LEVEL'", "'LIKE'", "'LIMIT'", 
			"'LISTAGG'", "'LOCAL'", "'LOCALTIME'", "'LOCALTIMESTAMP'", "'LOGICAL'", 
			"'LOOP'", "'MAP'", "'MATCH'", "'MATCHED'", "'MATCHES'", "'MATCH_RECOGNIZE'", 
			"'MATERIALIZED'", "'MEASURES'", "'MERGE'", "'MINUTE'", "'MONTH'", "'NATURAL'", 
			"'NESTED'", "'NEXT'", "'NFC'", "'NFD'", "'NFKC'", "'NFKD'", "'NO'", "'NONE'", 
			"'NORMALIZE'", "'NOT'", "'NULL'", "'NULLIF'", "'NULLS'", "'OBJECT'", 
			"'OF'", "'OFFSET'", "'OMIT'", "'ON'", "'ONE'", "'ONLY'", "'OPTION'", 
			"'OR'", "'ORDER'", "'ORDINALITY'", "'OUTER'", "'OUTPUT'", "'OVER'", "'OVERFLOW'", 
			"'PARTITION'", "'PARTITIONS'", "'PASSING'", "'PAST'", "'PATH'", "'PATTERN'", 
			"'PER'", "'PERIOD'", "'PERMUTE'", "'PLAN'", "'POSITION'", "'PRECEDING'", 
			"'PRECISION'", "'PREPARE'", "'PRIVILEGES'", "'PROPERTIES'", "'PRUNE'", 
			"'QUOTES'", "'RANGE'", "'READ'", "'RECURSIVE'", "'REFRESH'", "'RENAME'", 
			"'REPEAT'", "'REPEATABLE'", "'REPLACE'", "'RESET'", "'RESPECT'", "'RESTRICT'", 
			"'RETURN'", "'RETURNING'", "'RETURNS'", "'REVOKE'", "'RIGHT'", "'ROLE'", 
			"'ROLES'", "'ROLLBACK'", "'ROLLUP'", "'ROW'", "'ROWS'", "'RUNNING'", 
			"'SCALAR'", "'SCHEMA'", "'SCHEMAS'", "'SECOND'", "'SECURITY'", "'SEEK'", 
			"'SELECT'", "'SERIALIZABLE'", "'SESSION'", "'SET'", "'SETS'", "'SHOW'", 
			"'SOME'", "'START'", "'STATS'", "'SUBSET'", "'SUBSTRING'", "'SYSTEM'", 
			"'TABLE'", "'TABLES'", "'TABLESAMPLE'", "'TEXT'", "'STRING'", "'THEN'", 
			"'TIES'", "'TIME'", "'TIMESTAMP'", "'TO'", "'TRAILING'", "'TRANSACTION'", 
			"'TRIM'", "'TRUE'", "'TRUNCATE'", "'TRY_CAST'", "'TYPE'", "'UESCAPE'", 
			"'UNBOUNDED'", "'UNCOMMITTED'", "'UNCONDITIONAL'", "'UNION'", "'UNIQUE'", 
			"'UNKNOWN'", "'UNMATCHED'", "'UNNEST'", "'UNTIL'", "'UPDATE'", "'USE'", 
			"'USER'", "'USING'", "'UTF16'", "'UTF32'", "'UTF8'", "'VALIDATE'", "'VALUE'", 
			"'VALUES'", "'VERBOSE'", "'VERSION'", "'VIEW'", "'WHEN'", "'WHERE'", 
			"'WHILE'", "'WINDOW'", "'WITH'", "'WITHIN'", "'WITHOUT'", "'WORK'", "'WRAPPER'", 
			"'WRITE'", "'YEAR'", "'ZONE'", "'='", null, null, "'<='", null, "'>='", 
			"'+'", "'-'", "'*'", "'/'", "'%'", "'||'", "'?'", "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "CALL", "CALLED", "CASCADE", 
			"CASE", "CAST", "CATALOG", "CATALOGS", "COLUMN", "COLUMNS", "COMMENT", 
			"COMMIT", "COMMITTED", "CONDITIONAL", "CONSTRAINT", "COUNT", "COPARTITION", 
			"CREATE", "CROSS", "CUBE", "CURRENT", "CURRENT_CATALOG", "CURRENT_DATE", 
			"CURRENT_PATH", "CURRENT_ROLE", "CURRENT_SCHEMA", "CURRENT_TIME", "CURRENT_TIMESTAMP", 
			"CURRENT_USER", "DATA", "DATE", "DAY", "DEALLOCATE", "DECLARE", "DEFAULT", 
			"DEFINE", "DEFINER", "DELETE", "DENY", "DESC", "DESCRIBE", "DESCRIPTOR", 
			"DETERMINISTIC", "DISTINCT", "DISTRIBUTED", "DO", "DOUBLE", "DROP", "ELSE", 
			"EMPTY", "ELSEIF", "ENCODING", "END", "ERROR", "ESCAPE", "EXCEPT", "EXCLUDING", 
			"EXECUTE", "EXISTS", "EXPLAIN", "EXTRACT", "FALSE", "FETCH", "FILTER", 
			"FINAL", "FIRST", "FOLLOWING", "FOR", "FORMAT", "FROM", "FULL", "FUNCTION", 
			"FUNCTIONS", "GRACE", "GRANT", "GRANTED", "GRANTS", "GRAPHVIZ", "GROUP", 
			"GROUPING", "GROUPS", "HAVING", "HOUR", "IF", "IGNORE", "IMMEDIATE", 
			"IN", "INCLUDING", "INITIAL", "INNER", "INPUT", "INSERT", "INTERSECT", 
			"INTERVAL", "INTO", "INVOKER", "IO", "IS", "ISOLATION", "ITERATE", "JOIN", 
			"JSON", "JSON_ARRAY", "JSON_EXISTS", "JSON_OBJECT", "JSON_QUERY", "JSON_TABLE", 
			"JSON_VALUE", "KEEP", "KEY", "KEYS", "LANGUAGE", "LAST", "LATERAL", "LEADING", 
			"LEAVE", "LEFT", "LEVEL", "LIKE", "LIMIT", "LISTAGG", "LOCAL", "LOCALTIME", 
			"LOCALTIMESTAMP", "LOGICAL", "LOOP", "MAP", "MATCH", "MATCHED", "MATCHES", 
			"MATCH_RECOGNIZE", "MATERIALIZED", "MEASURES", "MERGE", "MINUTE", "MONTH", 
			"NATURAL", "NESTED", "NEXT", "NFC", "NFD", "NFKC", "NFKD", "NO", "NONE", 
			"NORMALIZE", "NOT", "NULL", "NULLIF", "NULLS", "OBJECT", "OF", "OFFSET", 
			"OMIT", "ON", "ONE", "ONLY", "OPTION", "OR", "ORDER", "ORDINALITY", "OUTER", 
			"OUTPUT", "OVER", "OVERFLOW", "PARTITION", "PARTITIONS", "PASSING", "PAST", 
			"PATH", "PATTERN", "PER", "PERIOD", "PERMUTE", "PLAN", "POSITION", "PRECEDING", 
			"PRECISION", "PREPARE", "PRIVILEGES", "PROPERTIES", "PRUNE", "QUOTES", 
			"RANGE", "READ", "RECURSIVE", "REFRESH", "RENAME", "REPEAT", "REPEATABLE", 
			"REPLACE", "RESET", "RESPECT", "RESTRICT", "RETURN", "RETURNING", "RETURNS", 
			"REVOKE", "RIGHT", "ROLE", "ROLES", "ROLLBACK", "ROLLUP", "ROW", "ROWS", 
			"RUNNING", "SCALAR", "SCHEMA", "SCHEMAS", "SECOND", "SECURITY", "SEEK", 
			"SELECT", "SERIALIZABLE", "SESSION", "SET", "SETS", "SHOW", "SOME", "START", 
			"STATS", "SUBSET", "SUBSTRING", "SYSTEM", "TABLE", "TABLES", "TABLESAMPLE", 
			"TEXT", "TEXT_STRING", "THEN", "TIES", "TIME", "TIMESTAMP", "TO", "TRAILING", 
			"TRANSACTION", "TRIM", "TRUE", "TRUNCATE", "TRY_CAST", "TYPE", "UESCAPE", 
			"UNBOUNDED", "UNCOMMITTED", "UNCONDITIONAL", "UNION", "UNIQUE", "UNKNOWN", 
			"UNMATCHED", "UNNEST", "UNTIL", "UPDATE", "USE", "USER", "USING", "UTF16", 
			"UTF32", "UTF8", "VALIDATE", "VALUE", "VALUES", "VERBOSE", "VERSION", 
			"VIEW", "WHEN", "WHERE", "WHILE", "WINDOW", "WITH", "WITHIN", "WITHOUT", 
			"WORK", "WRAPPER", "WRITE", "YEAR", "ZONE", "EQ", "NEQ", "LT", "LTE", 
			"GT", "GTE", "PLUS", "MINUS", "ASTERISK", "SLASH", "PERCENT", "CONCAT", 
			"QUESTION_MARK", "SEMICOLON", "STRING", "UNICODE_STRING", "BINARY_LITERAL", 
			"INTEGER_VALUE", "DECIMAL_VALUE", "DOUBLE_VALUE", "IDENTIFIER", "DIGIT_IDENTIFIER", 
			"QUOTED_IDENTIFIER", "BACKQUOTED_IDENTIFIER", "SIMPLE_COMMENT", "BRACKETED_COMMENT", 
			"WS", "UNRECOGNIZED", "DELIMITER"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "StructuresSqlParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public StructuresSqlParserParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SingleStatementContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode EOF() { return getToken(StructuresSqlParserParser.EOF, 0); }
		public SingleStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSingleStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSingleStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSingleStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleStatementContext singleStatement() throws RecognitionException {
		SingleStatementContext _localctx = new SingleStatementContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_singleStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			statement();
			setState(265);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StandaloneExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(StructuresSqlParserParser.EOF, 0); }
		public StandaloneExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_standaloneExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterStandaloneExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitStandaloneExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitStandaloneExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StandaloneExpressionContext standaloneExpression() throws RecognitionException {
		StandaloneExpressionContext _localctx = new StandaloneExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_standaloneExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			expression();
			setState(268);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StandalonePathSpecificationContext extends ParserRuleContext {
		public PathSpecificationContext pathSpecification() {
			return getRuleContext(PathSpecificationContext.class,0);
		}
		public TerminalNode EOF() { return getToken(StructuresSqlParserParser.EOF, 0); }
		public StandalonePathSpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_standalonePathSpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterStandalonePathSpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitStandalonePathSpecification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitStandalonePathSpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StandalonePathSpecificationContext standalonePathSpecification() throws RecognitionException {
		StandalonePathSpecificationContext _localctx = new StandalonePathSpecificationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_standalonePathSpecification);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			pathSpecification();
			setState(271);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StandaloneTypeContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode EOF() { return getToken(StructuresSqlParserParser.EOF, 0); }
		public StandaloneTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_standaloneType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterStandaloneType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitStandaloneType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitStandaloneType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StandaloneTypeContext standaloneType() throws RecognitionException {
		StandaloneTypeContext _localctx = new StandaloneTypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_standaloneType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			type(0);
			setState(274);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StandaloneRowPatternContext extends ParserRuleContext {
		public RowPatternContext rowPattern() {
			return getRuleContext(RowPatternContext.class,0);
		}
		public TerminalNode EOF() { return getToken(StructuresSqlParserParser.EOF, 0); }
		public StandaloneRowPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_standaloneRowPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterStandaloneRowPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitStandaloneRowPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitStandaloneRowPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StandaloneRowPatternContext standaloneRowPattern() throws RecognitionException {
		StandaloneRowPatternContext _localctx = new StandaloneRowPatternContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_standaloneRowPattern);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			rowPattern(0);
			setState(277);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StandaloneFunctionSpecificationContext extends ParserRuleContext {
		public FunctionSpecificationContext functionSpecification() {
			return getRuleContext(FunctionSpecificationContext.class,0);
		}
		public TerminalNode EOF() { return getToken(StructuresSqlParserParser.EOF, 0); }
		public StandaloneFunctionSpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_standaloneFunctionSpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterStandaloneFunctionSpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitStandaloneFunctionSpecification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitStandaloneFunctionSpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StandaloneFunctionSpecificationContext standaloneFunctionSpecification() throws RecognitionException {
		StandaloneFunctionSpecificationContext _localctx = new StandaloneFunctionSpecificationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_standaloneFunctionSpecification);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(279);
			functionSpecification();
			setState(280);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StatementDefaultContext extends StatementContext {
		public RootQueryContext rootQuery() {
			return getRuleContext(RootQueryContext.class,0);
		}
		public StatementDefaultContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterStatementDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitStatementDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitStatementDefault(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UseContext extends StatementContext {
		public IdentifierContext schema;
		public IdentifierContext catalog;
		public TerminalNode USE() { return getToken(StructuresSqlParserParser.USE, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public UseContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterUse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitUse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitUse(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UpdateContext extends StatementContext {
		public BooleanExpressionContext where;
		public TerminalNode UPDATE() { return getToken(StructuresSqlParserParser.UPDATE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode SET() { return getToken(StructuresSqlParserParser.SET, 0); }
		public List<UpdateAssignmentContext> updateAssignment() {
			return getRuleContexts(UpdateAssignmentContext.class);
		}
		public UpdateAssignmentContext updateAssignment(int i) {
			return getRuleContext(UpdateAssignmentContext.class,i);
		}
		public TerminalNode WHERE() { return getToken(StructuresSqlParserParser.WHERE, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public UpdateContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterUpdate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitUpdate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitUpdate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_statement);
		int _la;
		try {
			setState(305);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				_localctx = new StatementDefaultContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(282);
				rootQuery();
				}
				break;
			case 2:
				_localctx = new UseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(283);
				match(USE);
				setState(284);
				((UseContext)_localctx).schema = identifier();
				}
				break;
			case 3:
				_localctx = new UseContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(285);
				match(USE);
				setState(286);
				((UseContext)_localctx).catalog = identifier();
				setState(287);
				match(T__0);
				setState(288);
				((UseContext)_localctx).schema = identifier();
				}
				break;
			case 4:
				_localctx = new UpdateContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(290);
				match(UPDATE);
				setState(291);
				qualifiedName();
				setState(292);
				match(SET);
				setState(293);
				updateAssignment();
				setState(298);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(294);
					match(T__1);
					setState(295);
					updateAssignment();
					}
					}
					setState(300);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(303);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WHERE) {
					{
					setState(301);
					match(WHERE);
					setState(302);
					((UpdateContext)_localctx).where = booleanExpression(0);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RootQueryContext extends ParserRuleContext {
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public WithFunctionContext withFunction() {
			return getRuleContext(WithFunctionContext.class,0);
		}
		public RootQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rootQuery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRootQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRootQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRootQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RootQueryContext rootQuery() throws RecognitionException {
		RootQueryContext _localctx = new RootQueryContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_rootQuery);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(307);
				withFunction();
				}
				break;
			}
			setState(310);
			query();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WithFunctionContext extends ParserRuleContext {
		public TerminalNode WITH() { return getToken(StructuresSqlParserParser.WITH, 0); }
		public List<FunctionSpecificationContext> functionSpecification() {
			return getRuleContexts(FunctionSpecificationContext.class);
		}
		public FunctionSpecificationContext functionSpecification(int i) {
			return getRuleContext(FunctionSpecificationContext.class,i);
		}
		public WithFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterWithFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitWithFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitWithFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WithFunctionContext withFunction() throws RecognitionException {
		WithFunctionContext _localctx = new WithFunctionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_withFunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			match(WITH);
			setState(313);
			functionSpecification();
			setState(318);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(314);
				match(T__1);
				setState(315);
				functionSpecification();
				}
				}
				setState(320);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueryContext extends ParserRuleContext {
		public QueryNoWithContext queryNoWith() {
			return getRuleContext(QueryNoWithContext.class,0);
		}
		public WithContext with() {
			return getRuleContext(WithContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(321);
				with();
				}
			}

			setState(324);
			queryNoWith();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WithContext extends ParserRuleContext {
		public TerminalNode WITH() { return getToken(StructuresSqlParserParser.WITH, 0); }
		public List<NamedQueryContext> namedQuery() {
			return getRuleContexts(NamedQueryContext.class);
		}
		public NamedQueryContext namedQuery(int i) {
			return getRuleContext(NamedQueryContext.class,i);
		}
		public TerminalNode RECURSIVE() { return getToken(StructuresSqlParserParser.RECURSIVE, 0); }
		public WithContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_with; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterWith(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitWith(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitWith(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WithContext with() throws RecognitionException {
		WithContext _localctx = new WithContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_with);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326);
			match(WITH);
			setState(328);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==RECURSIVE) {
				{
				setState(327);
				match(RECURSIVE);
				}
			}

			setState(330);
			namedQuery();
			setState(335);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(331);
				match(T__1);
				setState(332);
				namedQuery();
				}
				}
				setState(337);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableElementContext extends ParserRuleContext {
		public ColumnDefinitionContext columnDefinition() {
			return getRuleContext(ColumnDefinitionContext.class,0);
		}
		public LikeClauseContext likeClause() {
			return getRuleContext(LikeClauseContext.class,0);
		}
		public TableElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTableElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTableElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTableElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableElementContext tableElement() throws RecognitionException {
		TableElementContext _localctx = new TableElementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_tableElement);
		try {
			setState(340);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case T__3:
			case T__4:
			case T__6:
			case T__8:
			case T__9:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__17:
			case CALL:
			case CALLED:
			case CASCADE:
			case CATALOG:
			case CATALOGS:
			case COLUMN:
			case COLUMNS:
			case COMMENT:
			case COMMIT:
			case COMMITTED:
			case CONDITIONAL:
			case COUNT:
			case COPARTITION:
			case CURRENT:
			case DATA:
			case DATE:
			case DAY:
			case DECLARE:
			case DEFAULT:
			case DEFINE:
			case DEFINER:
			case DENY:
			case DESC:
			case DESCRIPTOR:
			case DETERMINISTIC:
			case DISTRIBUTED:
			case DO:
			case DOUBLE:
			case EMPTY:
			case ELSEIF:
			case ENCODING:
			case ERROR:
			case EXCLUDING:
			case EXPLAIN:
			case FETCH:
			case FILTER:
			case FINAL:
			case FIRST:
			case FOLLOWING:
			case FORMAT:
			case FUNCTION:
			case FUNCTIONS:
			case GRACE:
			case GRANT:
			case GRANTED:
			case GRANTS:
			case GRAPHVIZ:
			case GROUPS:
			case HOUR:
			case IF:
			case IGNORE:
			case IMMEDIATE:
			case INCLUDING:
			case INITIAL:
			case INPUT:
			case INTERVAL:
			case INVOKER:
			case IO:
			case ISOLATION:
			case ITERATE:
			case JSON:
			case KEEP:
			case KEY:
			case KEYS:
			case LANGUAGE:
			case LAST:
			case LATERAL:
			case LEADING:
			case LEAVE:
			case LEVEL:
			case LIMIT:
			case LOCAL:
			case LOGICAL:
			case LOOP:
			case MAP:
			case MATCH:
			case MATCHED:
			case MATCHES:
			case MATCH_RECOGNIZE:
			case MATERIALIZED:
			case MEASURES:
			case MERGE:
			case MINUTE:
			case MONTH:
			case NESTED:
			case NEXT:
			case NFC:
			case NFD:
			case NFKC:
			case NFKD:
			case NO:
			case NONE:
			case NULLIF:
			case NULLS:
			case OBJECT:
			case OF:
			case OFFSET:
			case OMIT:
			case ONE:
			case ONLY:
			case OPTION:
			case ORDINALITY:
			case OUTPUT:
			case OVER:
			case OVERFLOW:
			case PARTITION:
			case PARTITIONS:
			case PASSING:
			case PAST:
			case PATH:
			case PATTERN:
			case PER:
			case PERIOD:
			case PERMUTE:
			case PLAN:
			case POSITION:
			case PRECEDING:
			case PRECISION:
			case PRIVILEGES:
			case PROPERTIES:
			case PRUNE:
			case QUOTES:
			case RANGE:
			case READ:
			case REFRESH:
			case RENAME:
			case REPEAT:
			case REPEATABLE:
			case REPLACE:
			case RESET:
			case RESPECT:
			case RESTRICT:
			case RETURN:
			case RETURNING:
			case RETURNS:
			case REVOKE:
			case ROLE:
			case ROLES:
			case ROLLBACK:
			case ROW:
			case ROWS:
			case RUNNING:
			case SCALAR:
			case SCHEMA:
			case SCHEMAS:
			case SECOND:
			case SECURITY:
			case SEEK:
			case SERIALIZABLE:
			case SESSION:
			case SET:
			case SETS:
			case SHOW:
			case SOME:
			case START:
			case STATS:
			case SUBSET:
			case SUBSTRING:
			case SYSTEM:
			case TABLES:
			case TABLESAMPLE:
			case TEXT:
			case TEXT_STRING:
			case TIES:
			case TIME:
			case TIMESTAMP:
			case TO:
			case TRAILING:
			case TRANSACTION:
			case TRUNCATE:
			case TRY_CAST:
			case TYPE:
			case UNBOUNDED:
			case UNCOMMITTED:
			case UNCONDITIONAL:
			case UNIQUE:
			case UNKNOWN:
			case UNMATCHED:
			case UNTIL:
			case UPDATE:
			case USE:
			case USER:
			case UTF16:
			case UTF32:
			case UTF8:
			case VALIDATE:
			case VALUE:
			case VERBOSE:
			case VERSION:
			case VIEW:
			case WHILE:
			case WINDOW:
			case WITHIN:
			case WITHOUT:
			case WORK:
			case WRAPPER:
			case WRITE:
			case YEAR:
			case ZONE:
			case IDENTIFIER:
			case DIGIT_IDENTIFIER:
			case QUOTED_IDENTIFIER:
			case BACKQUOTED_IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(338);
				columnDefinition();
				}
				break;
			case LIKE:
				enterOuterAlt(_localctx, 2);
				{
				setState(339);
				likeClause();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnDefinitionContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode NOT() { return getToken(StructuresSqlParserParser.NOT, 0); }
		public TerminalNode NULL() { return getToken(StructuresSqlParserParser.NULL, 0); }
		public TerminalNode COMMENT() { return getToken(StructuresSqlParserParser.COMMENT, 0); }
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode WITH() { return getToken(StructuresSqlParserParser.WITH, 0); }
		public PropertiesContext properties() {
			return getRuleContext(PropertiesContext.class,0);
		}
		public ColumnDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterColumnDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitColumnDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitColumnDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnDefinitionContext columnDefinition() throws RecognitionException {
		ColumnDefinitionContext _localctx = new ColumnDefinitionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_columnDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(342);
			qualifiedName();
			setState(343);
			type(0);
			setState(346);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(344);
				match(NOT);
				setState(345);
				match(NULL);
				}
			}

			setState(350);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMENT) {
				{
				setState(348);
				match(COMMENT);
				setState(349);
				string();
				}
			}

			setState(354);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(352);
				match(WITH);
				setState(353);
				properties();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LikeClauseContext extends ParserRuleContext {
		public Token optionType;
		public TerminalNode LIKE() { return getToken(StructuresSqlParserParser.LIKE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode PROPERTIES() { return getToken(StructuresSqlParserParser.PROPERTIES, 0); }
		public TerminalNode INCLUDING() { return getToken(StructuresSqlParserParser.INCLUDING, 0); }
		public TerminalNode EXCLUDING() { return getToken(StructuresSqlParserParser.EXCLUDING, 0); }
		public LikeClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_likeClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLikeClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLikeClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLikeClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LikeClauseContext likeClause() throws RecognitionException {
		LikeClauseContext _localctx = new LikeClauseContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_likeClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(356);
			match(LIKE);
			setState(357);
			qualifiedName();
			setState(360);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXCLUDING || _la==INCLUDING) {
				{
				setState(358);
				((LikeClauseContext)_localctx).optionType = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EXCLUDING || _la==INCLUDING) ) {
					((LikeClauseContext)_localctx).optionType = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(359);
				match(PROPERTIES);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropertiesContext extends ParserRuleContext {
		public PropertyAssignmentsContext propertyAssignments() {
			return getRuleContext(PropertyAssignmentsContext.class,0);
		}
		public PropertiesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_properties; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterProperties(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitProperties(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitProperties(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertiesContext properties() throws RecognitionException {
		PropertiesContext _localctx = new PropertiesContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_properties);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			match(T__2);
			setState(363);
			propertyAssignments();
			setState(364);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropertyAssignmentsContext extends ParserRuleContext {
		public List<PropertyContext> property() {
			return getRuleContexts(PropertyContext.class);
		}
		public PropertyContext property(int i) {
			return getRuleContext(PropertyContext.class,i);
		}
		public PropertyAssignmentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyAssignments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPropertyAssignments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPropertyAssignments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPropertyAssignments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyAssignmentsContext propertyAssignments() throws RecognitionException {
		PropertyAssignmentsContext _localctx = new PropertyAssignmentsContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_propertyAssignments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(366);
			property();
			setState(371);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(367);
				match(T__1);
				setState(368);
				property();
				}
				}
				setState(373);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropertyContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode EQ() { return getToken(StructuresSqlParserParser.EQ, 0); }
		public PropertyValueContext propertyValue() {
			return getRuleContext(PropertyValueContext.class,0);
		}
		public PropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_property; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitProperty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitProperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyContext property() throws RecognitionException {
		PropertyContext _localctx = new PropertyContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_property);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(374);
			identifier();
			setState(375);
			match(EQ);
			setState(376);
			propertyValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropertyValueContext extends ParserRuleContext {
		public PropertyValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyValue; }
	 
		public PropertyValueContext() { }
		public void copyFrom(PropertyValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DefaultPropertyValueContext extends PropertyValueContext {
		public TerminalNode DEFAULT() { return getToken(StructuresSqlParserParser.DEFAULT, 0); }
		public DefaultPropertyValueContext(PropertyValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterDefaultPropertyValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitDefaultPropertyValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitDefaultPropertyValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NonDefaultPropertyValueContext extends PropertyValueContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NonDefaultPropertyValueContext(PropertyValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterNonDefaultPropertyValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitNonDefaultPropertyValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitNonDefaultPropertyValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyValueContext propertyValue() throws RecognitionException {
		PropertyValueContext _localctx = new PropertyValueContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_propertyValue);
		try {
			setState(380);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new DefaultPropertyValueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(378);
				match(DEFAULT);
				}
				break;
			case 2:
				_localctx = new NonDefaultPropertyValueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(379);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueryNoWithContext extends ParserRuleContext {
		public RowCountContext offset;
		public LimitRowCountContext limit;
		public RowCountContext fetchFirst;
		public QueryTermContext queryTerm() {
			return getRuleContext(QueryTermContext.class,0);
		}
		public TerminalNode ORDER() { return getToken(StructuresSqlParserParser.ORDER, 0); }
		public TerminalNode BY() { return getToken(StructuresSqlParserParser.BY, 0); }
		public List<SortItemContext> sortItem() {
			return getRuleContexts(SortItemContext.class);
		}
		public SortItemContext sortItem(int i) {
			return getRuleContext(SortItemContext.class,i);
		}
		public TerminalNode OFFSET() { return getToken(StructuresSqlParserParser.OFFSET, 0); }
		public List<RowCountContext> rowCount() {
			return getRuleContexts(RowCountContext.class);
		}
		public RowCountContext rowCount(int i) {
			return getRuleContext(RowCountContext.class,i);
		}
		public TerminalNode LIMIT() { return getToken(StructuresSqlParserParser.LIMIT, 0); }
		public TerminalNode FETCH() { return getToken(StructuresSqlParserParser.FETCH, 0); }
		public LimitRowCountContext limitRowCount() {
			return getRuleContext(LimitRowCountContext.class,0);
		}
		public TerminalNode FIRST() { return getToken(StructuresSqlParserParser.FIRST, 0); }
		public TerminalNode NEXT() { return getToken(StructuresSqlParserParser.NEXT, 0); }
		public List<TerminalNode> ROW() { return getTokens(StructuresSqlParserParser.ROW); }
		public TerminalNode ROW(int i) {
			return getToken(StructuresSqlParserParser.ROW, i);
		}
		public List<TerminalNode> ROWS() { return getTokens(StructuresSqlParserParser.ROWS); }
		public TerminalNode ROWS(int i) {
			return getToken(StructuresSqlParserParser.ROWS, i);
		}
		public TerminalNode ONLY() { return getToken(StructuresSqlParserParser.ONLY, 0); }
		public TerminalNode WITH() { return getToken(StructuresSqlParserParser.WITH, 0); }
		public TerminalNode TIES() { return getToken(StructuresSqlParserParser.TIES, 0); }
		public QueryNoWithContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryNoWith; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQueryNoWith(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQueryNoWith(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQueryNoWith(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryNoWithContext queryNoWith() throws RecognitionException {
		QueryNoWithContext _localctx = new QueryNoWithContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_queryNoWith);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			queryTerm(0);
			setState(393);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ORDER) {
				{
				setState(383);
				match(ORDER);
				setState(384);
				match(T__18);
				setState(385);
				sortItem();
				setState(390);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(386);
					match(T__1);
					setState(387);
					sortItem();
					}
					}
					setState(392);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(400);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OFFSET) {
				{
				setState(395);
				match(OFFSET);
				setState(396);
				((QueryNoWithContext)_localctx).offset = rowCount();
				setState(398);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ROW || _la==ROWS) {
					{
					setState(397);
					_la = _input.LA(1);
					if ( !(_la==ROW || _la==ROWS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				}
			}

			setState(415);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LIMIT:
				{
				{
				setState(402);
				match(LIMIT);
				setState(403);
				((QueryNoWithContext)_localctx).limit = limitRowCount();
				}
				}
				break;
			case FETCH:
				{
				{
				setState(404);
				match(FETCH);
				setState(405);
				_la = _input.LA(1);
				if ( !(_la==FIRST || _la==NEXT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(407);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==QUESTION_MARK || _la==INTEGER_VALUE) {
					{
					setState(406);
					((QueryNoWithContext)_localctx).fetchFirst = rowCount();
					}
				}

				setState(409);
				_la = _input.LA(1);
				if ( !(_la==ROW || _la==ROWS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(413);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ONLY:
					{
					setState(410);
					match(ONLY);
					}
					break;
				case WITH:
					{
					setState(411);
					match(WITH);
					setState(412);
					match(TIES);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				}
				break;
			case EOF:
			case T__3:
				break;
			default:
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LimitRowCountContext extends ParserRuleContext {
		public TerminalNode ALL() { return getToken(StructuresSqlParserParser.ALL, 0); }
		public RowCountContext rowCount() {
			return getRuleContext(RowCountContext.class,0);
		}
		public LimitRowCountContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_limitRowCount; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLimitRowCount(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLimitRowCount(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLimitRowCount(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LimitRowCountContext limitRowCount() throws RecognitionException {
		LimitRowCountContext _localctx = new LimitRowCountContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_limitRowCount);
		try {
			setState(419);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(417);
				match(T__4);
				}
				break;
			case QUESTION_MARK:
			case INTEGER_VALUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(418);
				rowCount();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RowCountContext extends ParserRuleContext {
		public TerminalNode INTEGER_VALUE() { return getToken(StructuresSqlParserParser.INTEGER_VALUE, 0); }
		public TerminalNode QUESTION_MARK() { return getToken(StructuresSqlParserParser.QUESTION_MARK, 0); }
		public RowCountContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rowCount; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRowCount(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRowCount(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRowCount(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RowCountContext rowCount() throws RecognitionException {
		RowCountContext _localctx = new RowCountContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_rowCount);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(421);
			_la = _input.LA(1);
			if ( !(_la==QUESTION_MARK || _la==INTEGER_VALUE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueryTermContext extends ParserRuleContext {
		public QueryTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryTerm; }
	 
		public QueryTermContext() { }
		public void copyFrom(QueryTermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class QueryTermDefaultContext extends QueryTermContext {
		public QueryPrimaryContext queryPrimary() {
			return getRuleContext(QueryPrimaryContext.class,0);
		}
		public QueryTermDefaultContext(QueryTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQueryTermDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQueryTermDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQueryTermDefault(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetOperationContext extends QueryTermContext {
		public QueryTermContext left;
		public Token operator;
		public QueryTermContext right;
		public List<QueryTermContext> queryTerm() {
			return getRuleContexts(QueryTermContext.class);
		}
		public QueryTermContext queryTerm(int i) {
			return getRuleContext(QueryTermContext.class,i);
		}
		public TerminalNode INTERSECT() { return getToken(StructuresSqlParserParser.INTERSECT, 0); }
		public SetQuantifierContext setQuantifier() {
			return getRuleContext(SetQuantifierContext.class,0);
		}
		public TerminalNode UNION() { return getToken(StructuresSqlParserParser.UNION, 0); }
		public TerminalNode EXCEPT() { return getToken(StructuresSqlParserParser.EXCEPT, 0); }
		public SetOperationContext(QueryTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSetOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSetOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSetOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryTermContext queryTerm() throws RecognitionException {
		return queryTerm(0);
	}

	private QueryTermContext queryTerm(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		QueryTermContext _localctx = new QueryTermContext(_ctx, _parentState);
		QueryTermContext _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_queryTerm, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new QueryTermDefaultContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(424);
			queryPrimary();
			}
			_ctx.stop = _input.LT(-1);
			setState(440);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(438);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
					case 1:
						{
						_localctx = new SetOperationContext(new QueryTermContext(_parentctx, _parentState));
						((SetOperationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_queryTerm);
						setState(426);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(427);
						((SetOperationContext)_localctx).operator = match(INTERSECT);
						setState(429);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==T__4 || _la==DISTINCT) {
							{
							setState(428);
							setQuantifier();
							}
						}

						setState(431);
						((SetOperationContext)_localctx).right = queryTerm(3);
						}
						break;
					case 2:
						{
						_localctx = new SetOperationContext(new QueryTermContext(_parentctx, _parentState));
						((SetOperationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_queryTerm);
						setState(432);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(433);
						((SetOperationContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EXCEPT || _la==UNION) ) {
							((SetOperationContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(435);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==T__4 || _la==DISTINCT) {
							{
							setState(434);
							setQuantifier();
							}
						}

						setState(437);
						((SetOperationContext)_localctx).right = queryTerm(2);
						}
						break;
					}
					} 
				}
				setState(442);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueryPrimaryContext extends ParserRuleContext {
		public QueryPrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryPrimary; }
	 
		public QueryPrimaryContext() { }
		public void copyFrom(QueryPrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubqueryContext extends QueryPrimaryContext {
		public QueryNoWithContext queryNoWith() {
			return getRuleContext(QueryNoWithContext.class,0);
		}
		public SubqueryContext(QueryPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSubquery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSubquery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSubquery(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class QueryPrimaryDefaultContext extends QueryPrimaryContext {
		public QuerySpecificationContext querySpecification() {
			return getRuleContext(QuerySpecificationContext.class,0);
		}
		public QueryPrimaryDefaultContext(QueryPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQueryPrimaryDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQueryPrimaryDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQueryPrimaryDefault(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TableContext extends QueryPrimaryContext {
		public TerminalNode TABLE() { return getToken(StructuresSqlParserParser.TABLE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TableContext(QueryPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTable(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InlineTableContext extends QueryPrimaryContext {
		public TerminalNode VALUES() { return getToken(StructuresSqlParserParser.VALUES, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public InlineTableContext(QueryPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterInlineTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitInlineTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitInlineTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryPrimaryContext queryPrimary() throws RecognitionException {
		QueryPrimaryContext _localctx = new QueryPrimaryContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_queryPrimary);
		try {
			int _alt;
			setState(459);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SELECT:
				_localctx = new QueryPrimaryDefaultContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(443);
				querySpecification();
				}
				break;
			case TABLE:
				_localctx = new TableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(444);
				match(TABLE);
				setState(445);
				qualifiedName();
				}
				break;
			case VALUES:
				_localctx = new InlineTableContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(446);
				match(VALUES);
				setState(447);
				expression();
				setState(452);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(448);
						match(T__1);
						setState(449);
						expression();
						}
						} 
					}
					setState(454);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
				}
				}
				break;
			case T__2:
				_localctx = new SubqueryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(455);
				match(T__2);
				setState(456);
				queryNoWith();
				setState(457);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SortItemContext extends ParserRuleContext {
		public Token ordering;
		public Token nullOrdering;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode NULLS() { return getToken(StructuresSqlParserParser.NULLS, 0); }
		public TerminalNode ASC() { return getToken(StructuresSqlParserParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(StructuresSqlParserParser.DESC, 0); }
		public TerminalNode FIRST() { return getToken(StructuresSqlParserParser.FIRST, 0); }
		public TerminalNode LAST() { return getToken(StructuresSqlParserParser.LAST, 0); }
		public SortItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sortItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSortItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSortItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSortItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SortItemContext sortItem() throws RecognitionException {
		SortItemContext _localctx = new SortItemContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_sortItem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(461);
			expression();
			setState(463);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11 || _la==DESC) {
				{
				setState(462);
				((SortItemContext)_localctx).ordering = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__11 || _la==DESC) ) {
					((SortItemContext)_localctx).ordering = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(467);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NULLS) {
				{
				setState(465);
				match(NULLS);
				setState(466);
				((SortItemContext)_localctx).nullOrdering = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==FIRST || _la==LAST) ) {
					((SortItemContext)_localctx).nullOrdering = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QuerySpecificationContext extends ParserRuleContext {
		public BooleanExpressionContext where;
		public BooleanExpressionContext having;
		public TerminalNode SELECT() { return getToken(StructuresSqlParserParser.SELECT, 0); }
		public List<SelectItemContext> selectItem() {
			return getRuleContexts(SelectItemContext.class);
		}
		public SelectItemContext selectItem(int i) {
			return getRuleContext(SelectItemContext.class,i);
		}
		public SetQuantifierContext setQuantifier() {
			return getRuleContext(SetQuantifierContext.class,0);
		}
		public TerminalNode FROM() { return getToken(StructuresSqlParserParser.FROM, 0); }
		public List<RelationContext> relation() {
			return getRuleContexts(RelationContext.class);
		}
		public RelationContext relation(int i) {
			return getRuleContext(RelationContext.class,i);
		}
		public TerminalNode WHERE() { return getToken(StructuresSqlParserParser.WHERE, 0); }
		public TerminalNode GROUP() { return getToken(StructuresSqlParserParser.GROUP, 0); }
		public TerminalNode BY() { return getToken(StructuresSqlParserParser.BY, 0); }
		public GroupByContext groupBy() {
			return getRuleContext(GroupByContext.class,0);
		}
		public TerminalNode HAVING() { return getToken(StructuresSqlParserParser.HAVING, 0); }
		public TerminalNode WINDOW() { return getToken(StructuresSqlParserParser.WINDOW, 0); }
		public List<WindowDefinitionContext> windowDefinition() {
			return getRuleContexts(WindowDefinitionContext.class);
		}
		public WindowDefinitionContext windowDefinition(int i) {
			return getRuleContext(WindowDefinitionContext.class,i);
		}
		public List<BooleanExpressionContext> booleanExpression() {
			return getRuleContexts(BooleanExpressionContext.class);
		}
		public BooleanExpressionContext booleanExpression(int i) {
			return getRuleContext(BooleanExpressionContext.class,i);
		}
		public QuerySpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_querySpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQuerySpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQuerySpecification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQuerySpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuerySpecificationContext querySpecification() throws RecognitionException {
		QuerySpecificationContext _localctx = new QuerySpecificationContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_querySpecification);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(469);
			match(SELECT);
			setState(471);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(470);
				setQuantifier();
				}
				break;
			}
			setState(473);
			selectItem();
			setState(478);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(474);
					match(T__1);
					setState(475);
					selectItem();
					}
					} 
				}
				setState(480);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			}
			setState(490);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				{
				setState(481);
				match(FROM);
				setState(482);
				relation(0);
				setState(487);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(483);
						match(T__1);
						setState(484);
						relation(0);
						}
						} 
					}
					setState(489);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				}
				}
				break;
			}
			setState(494);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				{
				setState(492);
				match(WHERE);
				setState(493);
				((QuerySpecificationContext)_localctx).where = booleanExpression(0);
				}
				break;
			}
			setState(499);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(496);
				match(GROUP);
				setState(497);
				match(T__18);
				setState(498);
				groupBy();
				}
				break;
			}
			setState(503);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				{
				setState(501);
				match(HAVING);
				setState(502);
				((QuerySpecificationContext)_localctx).having = booleanExpression(0);
				}
				break;
			}
			setState(514);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				{
				setState(505);
				match(WINDOW);
				setState(506);
				windowDefinition();
				setState(511);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(507);
						match(T__1);
						setState(508);
						windowDefinition();
						}
						} 
					}
					setState(513);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GroupByContext extends ParserRuleContext {
		public List<GroupingElementContext> groupingElement() {
			return getRuleContexts(GroupingElementContext.class);
		}
		public GroupingElementContext groupingElement(int i) {
			return getRuleContext(GroupingElementContext.class,i);
		}
		public SetQuantifierContext setQuantifier() {
			return getRuleContext(SetQuantifierContext.class,0);
		}
		public GroupByContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupBy; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterGroupBy(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitGroupBy(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitGroupBy(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupByContext groupBy() throws RecognitionException {
		GroupByContext _localctx = new GroupByContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_groupBy);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(517);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				{
				setState(516);
				setQuantifier();
				}
				break;
			}
			setState(519);
			groupingElement();
			setState(524);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(520);
					match(T__1);
					setState(521);
					groupingElement();
					}
					} 
				}
				setState(526);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GroupingElementContext extends ParserRuleContext {
		public GroupingElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupingElement; }
	 
		public GroupingElementContext() { }
		public void copyFrom(GroupingElementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MultipleGroupingSetsContext extends GroupingElementContext {
		public TerminalNode GROUPING() { return getToken(StructuresSqlParserParser.GROUPING, 0); }
		public TerminalNode SETS() { return getToken(StructuresSqlParserParser.SETS, 0); }
		public List<GroupingSetContext> groupingSet() {
			return getRuleContexts(GroupingSetContext.class);
		}
		public GroupingSetContext groupingSet(int i) {
			return getRuleContext(GroupingSetContext.class,i);
		}
		public MultipleGroupingSetsContext(GroupingElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterMultipleGroupingSets(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitMultipleGroupingSets(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitMultipleGroupingSets(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SingleGroupingSetContext extends GroupingElementContext {
		public GroupingSetContext groupingSet() {
			return getRuleContext(GroupingSetContext.class,0);
		}
		public SingleGroupingSetContext(GroupingElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSingleGroupingSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSingleGroupingSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSingleGroupingSet(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CubeContext extends GroupingElementContext {
		public TerminalNode CUBE() { return getToken(StructuresSqlParserParser.CUBE, 0); }
		public List<GroupingSetContext> groupingSet() {
			return getRuleContexts(GroupingSetContext.class);
		}
		public GroupingSetContext groupingSet(int i) {
			return getRuleContext(GroupingSetContext.class,i);
		}
		public CubeContext(GroupingElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCube(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCube(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCube(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RollupContext extends GroupingElementContext {
		public TerminalNode ROLLUP() { return getToken(StructuresSqlParserParser.ROLLUP, 0); }
		public List<GroupingSetContext> groupingSet() {
			return getRuleContexts(GroupingSetContext.class);
		}
		public GroupingSetContext groupingSet(int i) {
			return getRuleContext(GroupingSetContext.class,i);
		}
		public RollupContext(GroupingElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRollup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRollup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRollup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupingElementContext groupingElement() throws RecognitionException {
		GroupingElementContext _localctx = new GroupingElementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_groupingElement);
		int _la;
		try {
			setState(567);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				_localctx = new SingleGroupingSetContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(527);
				groupingSet();
				}
				break;
			case 2:
				_localctx = new RollupContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(528);
				match(ROLLUP);
				setState(529);
				match(T__2);
				setState(538);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
				case 1:
					{
					setState(530);
					groupingSet();
					setState(535);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(531);
						match(T__1);
						setState(532);
						groupingSet();
						}
						}
						setState(537);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					break;
				}
				setState(540);
				match(T__3);
				}
				break;
			case 3:
				_localctx = new CubeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(541);
				match(CUBE);
				setState(542);
				match(T__2);
				setState(551);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
				case 1:
					{
					setState(543);
					groupingSet();
					setState(548);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(544);
						match(T__1);
						setState(545);
						groupingSet();
						}
						}
						setState(550);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					break;
				}
				setState(553);
				match(T__3);
				}
				break;
			case 4:
				_localctx = new MultipleGroupingSetsContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(554);
				match(GROUPING);
				setState(555);
				match(SETS);
				setState(556);
				match(T__2);
				setState(557);
				groupingSet();
				setState(562);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(558);
					match(T__1);
					setState(559);
					groupingSet();
					}
					}
					setState(564);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(565);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GroupingSetContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public GroupingSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupingSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterGroupingSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitGroupingSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitGroupingSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupingSetContext groupingSet() throws RecognitionException {
		GroupingSetContext _localctx = new GroupingSetContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_groupingSet);
		int _la;
		try {
			setState(582);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(569);
				match(T__2);
				setState(578);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
				case 1:
					{
					setState(570);
					expression();
					setState(575);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(571);
						match(T__1);
						setState(572);
						expression();
						}
						}
						setState(577);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					break;
				}
				setState(580);
				match(T__3);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(581);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WindowDefinitionContext extends ParserRuleContext {
		public IdentifierContext name;
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public WindowSpecificationContext windowSpecification() {
			return getRuleContext(WindowSpecificationContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public WindowDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_windowDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterWindowDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitWindowDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitWindowDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WindowDefinitionContext windowDefinition() throws RecognitionException {
		WindowDefinitionContext _localctx = new WindowDefinitionContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_windowDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(584);
			((WindowDefinitionContext)_localctx).name = identifier();
			setState(585);
			match(T__10);
			setState(586);
			match(T__2);
			setState(587);
			windowSpecification();
			setState(588);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WindowSpecificationContext extends ParserRuleContext {
		public IdentifierContext existingWindowName;
		public ExpressionContext expression;
		public List<ExpressionContext> partition = new ArrayList<ExpressionContext>();
		public TerminalNode PARTITION() { return getToken(StructuresSqlParserParser.PARTITION, 0); }
		public List<TerminalNode> BY() { return getTokens(StructuresSqlParserParser.BY); }
		public TerminalNode BY(int i) {
			return getToken(StructuresSqlParserParser.BY, i);
		}
		public TerminalNode ORDER() { return getToken(StructuresSqlParserParser.ORDER, 0); }
		public List<SortItemContext> sortItem() {
			return getRuleContexts(SortItemContext.class);
		}
		public SortItemContext sortItem(int i) {
			return getRuleContext(SortItemContext.class,i);
		}
		public WindowFrameContext windowFrame() {
			return getRuleContext(WindowFrameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public WindowSpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_windowSpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterWindowSpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitWindowSpecification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitWindowSpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WindowSpecificationContext windowSpecification() throws RecognitionException {
		WindowSpecificationContext _localctx = new WindowSpecificationContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_windowSpecification);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(591);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				{
				setState(590);
				((WindowSpecificationContext)_localctx).existingWindowName = identifier();
				}
				break;
			}
			setState(603);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PARTITION) {
				{
				setState(593);
				match(PARTITION);
				setState(594);
				match(T__18);
				setState(595);
				((WindowSpecificationContext)_localctx).expression = expression();
				((WindowSpecificationContext)_localctx).partition.add(((WindowSpecificationContext)_localctx).expression);
				setState(600);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(596);
					match(T__1);
					setState(597);
					((WindowSpecificationContext)_localctx).expression = expression();
					((WindowSpecificationContext)_localctx).partition.add(((WindowSpecificationContext)_localctx).expression);
					}
					}
					setState(602);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(615);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ORDER) {
				{
				setState(605);
				match(ORDER);
				setState(606);
				match(T__18);
				setState(607);
				sortItem();
				setState(612);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(608);
					match(T__1);
					setState(609);
					sortItem();
					}
					}
					setState(614);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(618);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==GROUPS || _la==MEASURES || _la==RANGE || _la==ROWS) {
				{
				setState(617);
				windowFrame();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NamedQueryContext extends ParserRuleContext {
		public IdentifierContext name;
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ColumnAliasesContext columnAliases() {
			return getRuleContext(ColumnAliasesContext.class,0);
		}
		public NamedQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedQuery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterNamedQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitNamedQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitNamedQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedQueryContext namedQuery() throws RecognitionException {
		NamedQueryContext _localctx = new NamedQueryContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_namedQuery);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(620);
			((NamedQueryContext)_localctx).name = identifier();
			setState(622);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(621);
				columnAliases();
				}
			}

			setState(624);
			match(T__10);
			setState(625);
			match(T__2);
			setState(626);
			query();
			setState(627);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SetQuantifierContext extends ParserRuleContext {
		public TerminalNode DISTINCT() { return getToken(StructuresSqlParserParser.DISTINCT, 0); }
		public TerminalNode ALL() { return getToken(StructuresSqlParserParser.ALL, 0); }
		public SetQuantifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setQuantifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSetQuantifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSetQuantifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSetQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetQuantifierContext setQuantifier() throws RecognitionException {
		SetQuantifierContext _localctx = new SetQuantifierContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_setQuantifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(629);
			_la = _input.LA(1);
			if ( !(_la==T__4 || _la==DISTINCT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectItemContext extends ParserRuleContext {
		public SelectItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectItem; }
	 
		public SelectItemContext() { }
		public void copyFrom(SelectItemContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SelectAllContext extends SelectItemContext {
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public TerminalNode ASTERISK() { return getToken(StructuresSqlParserParser.ASTERISK, 0); }
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public ColumnAliasesContext columnAliases() {
			return getRuleContext(ColumnAliasesContext.class,0);
		}
		public SelectAllContext(SelectItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSelectAll(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSelectAll(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSelectAll(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SelectSingleContext extends SelectItemContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public SelectSingleContext(SelectItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSelectSingle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSelectSingle(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSelectSingle(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectItemContext selectItem() throws RecognitionException {
		SelectItemContext _localctx = new SelectItemContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_selectItem);
		int _la;
		try {
			setState(646);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
			case 1:
				_localctx = new SelectSingleContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(631);
				expression();
				setState(636);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
				case 1:
					{
					setState(633);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__10) {
						{
						setState(632);
						match(T__10);
						}
					}

					setState(635);
					identifier();
					}
					break;
				}
				}
				break;
			case 2:
				_localctx = new SelectAllContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(638);
				primaryExpression(0);
				setState(639);
				match(T__0);
				setState(640);
				match(ASTERISK);
				setState(643);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
				case 1:
					{
					setState(641);
					match(T__10);
					setState(642);
					columnAliases();
					}
					break;
				}
				}
				break;
			case 3:
				_localctx = new SelectAllContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(645);
				match(ASTERISK);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RelationContext extends ParserRuleContext {
		public RelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relation; }
	 
		public RelationContext() { }
		public void copyFrom(RelationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RelationDefaultContext extends RelationContext {
		public SampledRelationContext sampledRelation() {
			return getRuleContext(SampledRelationContext.class,0);
		}
		public RelationDefaultContext(RelationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRelationDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRelationDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRelationDefault(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JoinRelationContext extends RelationContext {
		public RelationContext left;
		public SampledRelationContext right;
		public RelationContext rightRelation;
		public List<RelationContext> relation() {
			return getRuleContexts(RelationContext.class);
		}
		public RelationContext relation(int i) {
			return getRuleContext(RelationContext.class,i);
		}
		public TerminalNode CROSS() { return getToken(StructuresSqlParserParser.CROSS, 0); }
		public TerminalNode JOIN() { return getToken(StructuresSqlParserParser.JOIN, 0); }
		public JoinTypeContext joinType() {
			return getRuleContext(JoinTypeContext.class,0);
		}
		public JoinCriteriaContext joinCriteria() {
			return getRuleContext(JoinCriteriaContext.class,0);
		}
		public TerminalNode NATURAL() { return getToken(StructuresSqlParserParser.NATURAL, 0); }
		public SampledRelationContext sampledRelation() {
			return getRuleContext(SampledRelationContext.class,0);
		}
		public JoinRelationContext(RelationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJoinRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJoinRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJoinRelation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationContext relation() throws RecognitionException {
		return relation(0);
	}

	private RelationContext relation(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RelationContext _localctx = new RelationContext(_ctx, _parentState);
		RelationContext _prevctx = _localctx;
		int _startState = 66;
		enterRecursionRule(_localctx, 66, RULE_relation, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new RelationDefaultContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(649);
			sampledRelation();
			}
			_ctx.stop = _input.LT(-1);
			setState(669);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new JoinRelationContext(new RelationContext(_parentctx, _parentState));
					((JoinRelationContext)_localctx).left = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_relation);
					setState(651);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(665);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case CROSS:
						{
						setState(652);
						match(CROSS);
						setState(653);
						match(JOIN);
						setState(654);
						((JoinRelationContext)_localctx).right = sampledRelation();
						}
						break;
					case FULL:
					case INNER:
					case JOIN:
					case LEFT:
					case RIGHT:
						{
						setState(655);
						joinType();
						setState(656);
						match(JOIN);
						setState(657);
						((JoinRelationContext)_localctx).rightRelation = relation(0);
						setState(658);
						joinCriteria();
						}
						break;
					case NATURAL:
						{
						setState(660);
						match(NATURAL);
						setState(661);
						joinType();
						setState(662);
						match(JOIN);
						setState(663);
						((JoinRelationContext)_localctx).right = sampledRelation();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					} 
				}
				setState(671);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JoinTypeContext extends ParserRuleContext {
		public TerminalNode INNER() { return getToken(StructuresSqlParserParser.INNER, 0); }
		public TerminalNode LEFT() { return getToken(StructuresSqlParserParser.LEFT, 0); }
		public TerminalNode OUTER() { return getToken(StructuresSqlParserParser.OUTER, 0); }
		public TerminalNode RIGHT() { return getToken(StructuresSqlParserParser.RIGHT, 0); }
		public TerminalNode FULL() { return getToken(StructuresSqlParserParser.FULL, 0); }
		public JoinTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joinType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJoinType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJoinType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJoinType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JoinTypeContext joinType() throws RecognitionException {
		JoinTypeContext _localctx = new JoinTypeContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_joinType);
		int _la;
		try {
			setState(687);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INNER:
			case JOIN:
				enterOuterAlt(_localctx, 1);
				{
				setState(673);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INNER) {
					{
					setState(672);
					match(INNER);
					}
				}

				}
				break;
			case LEFT:
				enterOuterAlt(_localctx, 2);
				{
				setState(675);
				match(LEFT);
				setState(677);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(676);
					match(OUTER);
					}
				}

				}
				break;
			case RIGHT:
				enterOuterAlt(_localctx, 3);
				{
				setState(679);
				match(RIGHT);
				setState(681);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(680);
					match(OUTER);
					}
				}

				}
				break;
			case FULL:
				enterOuterAlt(_localctx, 4);
				{
				setState(683);
				match(FULL);
				setState(685);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(684);
					match(OUTER);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JoinCriteriaContext extends ParserRuleContext {
		public TerminalNode ON() { return getToken(StructuresSqlParserParser.ON, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode USING() { return getToken(StructuresSqlParserParser.USING, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public JoinCriteriaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joinCriteria; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJoinCriteria(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJoinCriteria(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJoinCriteria(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JoinCriteriaContext joinCriteria() throws RecognitionException {
		JoinCriteriaContext _localctx = new JoinCriteriaContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_joinCriteria);
		int _la;
		try {
			setState(703);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ON:
				enterOuterAlt(_localctx, 1);
				{
				setState(689);
				match(ON);
				setState(690);
				booleanExpression(0);
				}
				break;
			case USING:
				enterOuterAlt(_localctx, 2);
				{
				setState(691);
				match(USING);
				setState(692);
				match(T__2);
				setState(693);
				identifier();
				setState(698);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(694);
					match(T__1);
					setState(695);
					identifier();
					}
					}
					setState(700);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(701);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SampledRelationContext extends ParserRuleContext {
		public ExpressionContext percentage;
		public PatternRecognitionContext patternRecognition() {
			return getRuleContext(PatternRecognitionContext.class,0);
		}
		public TerminalNode TABLESAMPLE() { return getToken(StructuresSqlParserParser.TABLESAMPLE, 0); }
		public SampleTypeContext sampleType() {
			return getRuleContext(SampleTypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SampledRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sampledRelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSampledRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSampledRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSampledRelation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SampledRelationContext sampledRelation() throws RecognitionException {
		SampledRelationContext _localctx = new SampledRelationContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_sampledRelation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(705);
			patternRecognition();
			setState(712);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
			case 1:
				{
				setState(706);
				match(TABLESAMPLE);
				setState(707);
				sampleType();
				setState(708);
				match(T__2);
				setState(709);
				((SampledRelationContext)_localctx).percentage = expression();
				setState(710);
				match(T__3);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SampleTypeContext extends ParserRuleContext {
		public TerminalNode BERNOULLI() { return getToken(StructuresSqlParserParser.BERNOULLI, 0); }
		public TerminalNode SYSTEM() { return getToken(StructuresSqlParserParser.SYSTEM, 0); }
		public SampleTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sampleType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSampleType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSampleType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSampleType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SampleTypeContext sampleType() throws RecognitionException {
		SampleTypeContext _localctx = new SampleTypeContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_sampleType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(714);
			_la = _input.LA(1);
			if ( !(_la==T__15 || _la==SYSTEM) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TrimsSpecificationContext extends ParserRuleContext {
		public TerminalNode LEADING() { return getToken(StructuresSqlParserParser.LEADING, 0); }
		public TerminalNode TRAILING() { return getToken(StructuresSqlParserParser.TRAILING, 0); }
		public TerminalNode BOTH() { return getToken(StructuresSqlParserParser.BOTH, 0); }
		public TrimsSpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trimsSpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTrimsSpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTrimsSpecification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTrimsSpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TrimsSpecificationContext trimsSpecification() throws RecognitionException {
		TrimsSpecificationContext _localctx = new TrimsSpecificationContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_trimsSpecification);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(716);
			_la = _input.LA(1);
			if ( !(_la==T__17 || _la==LEADING || _la==TRAILING) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListAggOverflowBehaviorContext extends ParserRuleContext {
		public TerminalNode ERROR() { return getToken(StructuresSqlParserParser.ERROR, 0); }
		public TerminalNode TRUNCATE() { return getToken(StructuresSqlParserParser.TRUNCATE, 0); }
		public ListaggCountIndicationContext listaggCountIndication() {
			return getRuleContext(ListaggCountIndicationContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public ListAggOverflowBehaviorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listAggOverflowBehavior; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterListAggOverflowBehavior(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitListAggOverflowBehavior(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitListAggOverflowBehavior(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListAggOverflowBehaviorContext listAggOverflowBehavior() throws RecognitionException {
		ListAggOverflowBehaviorContext _localctx = new ListAggOverflowBehaviorContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_listAggOverflowBehavior);
		int _la;
		try {
			setState(724);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ERROR:
				enterOuterAlt(_localctx, 1);
				{
				setState(718);
				match(ERROR);
				}
				break;
			case TRUNCATE:
				enterOuterAlt(_localctx, 2);
				{
				setState(719);
				match(TRUNCATE);
				setState(721);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STRING || _la==UNICODE_STRING) {
					{
					setState(720);
					string();
					}
				}

				setState(723);
				listaggCountIndication();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListaggCountIndicationContext extends ParserRuleContext {
		public TerminalNode WITH() { return getToken(StructuresSqlParserParser.WITH, 0); }
		public TerminalNode COUNT() { return getToken(StructuresSqlParserParser.COUNT, 0); }
		public TerminalNode WITHOUT() { return getToken(StructuresSqlParserParser.WITHOUT, 0); }
		public ListaggCountIndicationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listaggCountIndication; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterListaggCountIndication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitListaggCountIndication(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitListaggCountIndication(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListaggCountIndicationContext listaggCountIndication() throws RecognitionException {
		ListaggCountIndicationContext _localctx = new ListaggCountIndicationContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_listaggCountIndication);
		try {
			setState(730);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WITH:
				enterOuterAlt(_localctx, 1);
				{
				setState(726);
				match(WITH);
				setState(727);
				match(COUNT);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 2);
				{
				setState(728);
				match(WITHOUT);
				setState(729);
				match(COUNT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PatternRecognitionContext extends ParserRuleContext {
		public ExpressionContext expression;
		public List<ExpressionContext> partition = new ArrayList<ExpressionContext>();
		public AliasedRelationContext aliasedRelation() {
			return getRuleContext(AliasedRelationContext.class,0);
		}
		public TerminalNode MATCH_RECOGNIZE() { return getToken(StructuresSqlParserParser.MATCH_RECOGNIZE, 0); }
		public TerminalNode PATTERN() { return getToken(StructuresSqlParserParser.PATTERN, 0); }
		public RowPatternContext rowPattern() {
			return getRuleContext(RowPatternContext.class,0);
		}
		public TerminalNode DEFINE() { return getToken(StructuresSqlParserParser.DEFINE, 0); }
		public List<VariableDefinitionContext> variableDefinition() {
			return getRuleContexts(VariableDefinitionContext.class);
		}
		public VariableDefinitionContext variableDefinition(int i) {
			return getRuleContext(VariableDefinitionContext.class,i);
		}
		public TerminalNode PARTITION() { return getToken(StructuresSqlParserParser.PARTITION, 0); }
		public List<TerminalNode> BY() { return getTokens(StructuresSqlParserParser.BY); }
		public TerminalNode BY(int i) {
			return getToken(StructuresSqlParserParser.BY, i);
		}
		public TerminalNode ORDER() { return getToken(StructuresSqlParserParser.ORDER, 0); }
		public List<SortItemContext> sortItem() {
			return getRuleContexts(SortItemContext.class);
		}
		public SortItemContext sortItem(int i) {
			return getRuleContext(SortItemContext.class,i);
		}
		public TerminalNode MEASURES() { return getToken(StructuresSqlParserParser.MEASURES, 0); }
		public List<MeasureDefinitionContext> measureDefinition() {
			return getRuleContexts(MeasureDefinitionContext.class);
		}
		public MeasureDefinitionContext measureDefinition(int i) {
			return getRuleContext(MeasureDefinitionContext.class,i);
		}
		public RowsPerMatchContext rowsPerMatch() {
			return getRuleContext(RowsPerMatchContext.class,0);
		}
		public TerminalNode AFTER() { return getToken(StructuresSqlParserParser.AFTER, 0); }
		public TerminalNode MATCH() { return getToken(StructuresSqlParserParser.MATCH, 0); }
		public SkipToContext skipTo() {
			return getRuleContext(SkipToContext.class,0);
		}
		public TerminalNode SUBSET() { return getToken(StructuresSqlParserParser.SUBSET, 0); }
		public List<SubsetDefinitionContext> subsetDefinition() {
			return getRuleContexts(SubsetDefinitionContext.class);
		}
		public SubsetDefinitionContext subsetDefinition(int i) {
			return getRuleContext(SubsetDefinitionContext.class,i);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode INITIAL() { return getToken(StructuresSqlParserParser.INITIAL, 0); }
		public TerminalNode SEEK() { return getToken(StructuresSqlParserParser.SEEK, 0); }
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public ColumnAliasesContext columnAliases() {
			return getRuleContext(ColumnAliasesContext.class,0);
		}
		public PatternRecognitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_patternRecognition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPatternRecognition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPatternRecognition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPatternRecognition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PatternRecognitionContext patternRecognition() throws RecognitionException {
		PatternRecognitionContext _localctx = new PatternRecognitionContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_patternRecognition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(732);
			aliasedRelation();
			setState(815);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,90,_ctx) ) {
			case 1:
				{
				setState(733);
				match(MATCH_RECOGNIZE);
				setState(734);
				match(T__2);
				setState(745);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PARTITION) {
					{
					setState(735);
					match(PARTITION);
					setState(736);
					match(T__18);
					setState(737);
					((PatternRecognitionContext)_localctx).expression = expression();
					((PatternRecognitionContext)_localctx).partition.add(((PatternRecognitionContext)_localctx).expression);
					setState(742);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(738);
						match(T__1);
						setState(739);
						((PatternRecognitionContext)_localctx).expression = expression();
						((PatternRecognitionContext)_localctx).partition.add(((PatternRecognitionContext)_localctx).expression);
						}
						}
						setState(744);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(757);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ORDER) {
					{
					setState(747);
					match(ORDER);
					setState(748);
					match(T__18);
					setState(749);
					sortItem();
					setState(754);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(750);
						match(T__1);
						setState(751);
						sortItem();
						}
						}
						setState(756);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(768);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MEASURES) {
					{
					setState(759);
					match(MEASURES);
					setState(760);
					measureDefinition();
					setState(765);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(761);
						match(T__1);
						setState(762);
						measureDefinition();
						}
						}
						setState(767);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(771);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__4 || _la==ONE) {
					{
					setState(770);
					rowsPerMatch();
					}
				}

				setState(776);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(773);
					match(T__3);
					setState(774);
					match(MATCH);
					setState(775);
					skipTo();
					}
				}

				setState(779);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INITIAL || _la==SEEK) {
					{
					setState(778);
					_la = _input.LA(1);
					if ( !(_la==INITIAL || _la==SEEK) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(781);
				match(PATTERN);
				setState(782);
				match(T__2);
				setState(783);
				rowPattern(0);
				setState(784);
				match(T__3);
				setState(794);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SUBSET) {
					{
					setState(785);
					match(SUBSET);
					setState(786);
					subsetDefinition();
					setState(791);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(787);
						match(T__1);
						setState(788);
						subsetDefinition();
						}
						}
						setState(793);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(796);
				match(DEFINE);
				setState(797);
				variableDefinition();
				setState(802);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(798);
					match(T__1);
					setState(799);
					variableDefinition();
					}
					}
					setState(804);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(805);
				match(T__3);
				setState(813);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,89,_ctx) ) {
				case 1:
					{
					setState(807);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__10) {
						{
						setState(806);
						match(T__10);
						}
					}

					setState(809);
					identifier();
					setState(811);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,88,_ctx) ) {
					case 1:
						{
						setState(810);
						columnAliases();
						}
						break;
					}
					}
					break;
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MeasureDefinitionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public MeasureDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_measureDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterMeasureDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitMeasureDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitMeasureDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MeasureDefinitionContext measureDefinition() throws RecognitionException {
		MeasureDefinitionContext _localctx = new MeasureDefinitionContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_measureDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(817);
			expression();
			setState(818);
			match(T__10);
			setState(819);
			identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RowsPerMatchContext extends ParserRuleContext {
		public TerminalNode ONE() { return getToken(StructuresSqlParserParser.ONE, 0); }
		public TerminalNode ROW() { return getToken(StructuresSqlParserParser.ROW, 0); }
		public TerminalNode PER() { return getToken(StructuresSqlParserParser.PER, 0); }
		public TerminalNode MATCH() { return getToken(StructuresSqlParserParser.MATCH, 0); }
		public TerminalNode ALL() { return getToken(StructuresSqlParserParser.ALL, 0); }
		public TerminalNode ROWS() { return getToken(StructuresSqlParserParser.ROWS, 0); }
		public EmptyMatchHandlingContext emptyMatchHandling() {
			return getRuleContext(EmptyMatchHandlingContext.class,0);
		}
		public RowsPerMatchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rowsPerMatch; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRowsPerMatch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRowsPerMatch(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRowsPerMatch(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RowsPerMatchContext rowsPerMatch() throws RecognitionException {
		RowsPerMatchContext _localctx = new RowsPerMatchContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_rowsPerMatch);
		int _la;
		try {
			setState(832);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ONE:
				enterOuterAlt(_localctx, 1);
				{
				setState(821);
				match(ONE);
				setState(822);
				match(ROW);
				setState(823);
				match(PER);
				setState(824);
				match(MATCH);
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				setState(825);
				match(T__4);
				setState(826);
				match(ROWS);
				setState(827);
				match(PER);
				setState(828);
				match(MATCH);
				setState(830);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OMIT || _la==SHOW || _la==WITH) {
					{
					setState(829);
					emptyMatchHandling();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EmptyMatchHandlingContext extends ParserRuleContext {
		public TerminalNode SHOW() { return getToken(StructuresSqlParserParser.SHOW, 0); }
		public TerminalNode EMPTY() { return getToken(StructuresSqlParserParser.EMPTY, 0); }
		public TerminalNode MATCHES() { return getToken(StructuresSqlParserParser.MATCHES, 0); }
		public TerminalNode OMIT() { return getToken(StructuresSqlParserParser.OMIT, 0); }
		public TerminalNode WITH() { return getToken(StructuresSqlParserParser.WITH, 0); }
		public TerminalNode UNMATCHED() { return getToken(StructuresSqlParserParser.UNMATCHED, 0); }
		public TerminalNode ROWS() { return getToken(StructuresSqlParserParser.ROWS, 0); }
		public EmptyMatchHandlingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyMatchHandling; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterEmptyMatchHandling(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitEmptyMatchHandling(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitEmptyMatchHandling(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EmptyMatchHandlingContext emptyMatchHandling() throws RecognitionException {
		EmptyMatchHandlingContext _localctx = new EmptyMatchHandlingContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_emptyMatchHandling);
		try {
			setState(843);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SHOW:
				enterOuterAlt(_localctx, 1);
				{
				setState(834);
				match(SHOW);
				setState(835);
				match(EMPTY);
				setState(836);
				match(MATCHES);
				}
				break;
			case OMIT:
				enterOuterAlt(_localctx, 2);
				{
				setState(837);
				match(OMIT);
				setState(838);
				match(EMPTY);
				setState(839);
				match(MATCHES);
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 3);
				{
				setState(840);
				match(WITH);
				setState(841);
				match(UNMATCHED);
				setState(842);
				match(ROWS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SkipToContext extends ParserRuleContext {
		public TerminalNode TO() { return getToken(StructuresSqlParserParser.TO, 0); }
		public TerminalNode NEXT() { return getToken(StructuresSqlParserParser.NEXT, 0); }
		public TerminalNode ROW() { return getToken(StructuresSqlParserParser.ROW, 0); }
		public TerminalNode PAST() { return getToken(StructuresSqlParserParser.PAST, 0); }
		public TerminalNode LAST() { return getToken(StructuresSqlParserParser.LAST, 0); }
		public TerminalNode FIRST() { return getToken(StructuresSqlParserParser.FIRST, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public SkipToContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_skipTo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSkipTo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSkipTo(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSkipTo(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SkipToContext skipTo() throws RecognitionException {
		SkipToContext _localctx = new SkipToContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_skipTo);
		try {
			setState(864);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,94,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(845);
				match(T__4);
				setState(846);
				match(TO);
				setState(847);
				match(NEXT);
				setState(848);
				match(ROW);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(849);
				match(T__4);
				setState(850);
				match(PAST);
				setState(851);
				match(LAST);
				setState(852);
				match(ROW);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(853);
				match(T__4);
				setState(854);
				match(TO);
				setState(855);
				match(FIRST);
				setState(856);
				identifier();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(857);
				match(T__4);
				setState(858);
				match(TO);
				setState(859);
				match(LAST);
				setState(860);
				identifier();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(861);
				match(T__4);
				setState(862);
				match(TO);
				setState(863);
				identifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubsetDefinitionContext extends ParserRuleContext {
		public IdentifierContext name;
		public IdentifierContext identifier;
		public List<IdentifierContext> union = new ArrayList<IdentifierContext>();
		public TerminalNode EQ() { return getToken(StructuresSqlParserParser.EQ, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public SubsetDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subsetDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSubsetDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSubsetDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSubsetDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubsetDefinitionContext subsetDefinition() throws RecognitionException {
		SubsetDefinitionContext _localctx = new SubsetDefinitionContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_subsetDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(866);
			((SubsetDefinitionContext)_localctx).name = identifier();
			setState(867);
			match(EQ);
			setState(868);
			match(T__2);
			setState(869);
			((SubsetDefinitionContext)_localctx).identifier = identifier();
			((SubsetDefinitionContext)_localctx).union.add(((SubsetDefinitionContext)_localctx).identifier);
			setState(874);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(870);
				match(T__1);
				setState(871);
				((SubsetDefinitionContext)_localctx).identifier = identifier();
				((SubsetDefinitionContext)_localctx).union.add(((SubsetDefinitionContext)_localctx).identifier);
				}
				}
				setState(876);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(877);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableDefinitionContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterVariableDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitVariableDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitVariableDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDefinitionContext variableDefinition() throws RecognitionException {
		VariableDefinitionContext _localctx = new VariableDefinitionContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_variableDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(879);
			identifier();
			setState(880);
			match(T__10);
			setState(881);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AliasedRelationContext extends ParserRuleContext {
		public RelationPrimaryContext relationPrimary() {
			return getRuleContext(RelationPrimaryContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public ColumnAliasesContext columnAliases() {
			return getRuleContext(ColumnAliasesContext.class,0);
		}
		public AliasedRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aliasedRelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterAliasedRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitAliasedRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitAliasedRelation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AliasedRelationContext aliasedRelation() throws RecognitionException {
		AliasedRelationContext _localctx = new AliasedRelationContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_aliasedRelation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(883);
			relationPrimary();
			setState(891);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,98,_ctx) ) {
			case 1:
				{
				setState(885);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(884);
					match(T__10);
					}
				}

				setState(887);
				identifier();
				setState(889);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
				case 1:
					{
					setState(888);
					columnAliases();
					}
					break;
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnAliasesContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public ColumnAliasesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnAliases; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterColumnAliases(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitColumnAliases(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitColumnAliases(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnAliasesContext columnAliases() throws RecognitionException {
		ColumnAliasesContext _localctx = new ColumnAliasesContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_columnAliases);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(893);
			match(T__2);
			setState(894);
			identifier();
			setState(899);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(895);
				match(T__1);
				setState(896);
				identifier();
				}
				}
				setState(901);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(902);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RelationPrimaryContext extends ParserRuleContext {
		public RelationPrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationPrimary; }
	 
		public RelationPrimaryContext() { }
		public void copyFrom(RelationPrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubqueryRelationContext extends RelationPrimaryContext {
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public SubqueryRelationContext(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSubqueryRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSubqueryRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSubqueryRelation(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JsonTableContext extends RelationPrimaryContext {
		public TerminalNode JSON_TABLE() { return getToken(StructuresSqlParserParser.JSON_TABLE, 0); }
		public JsonPathInvocationContext jsonPathInvocation() {
			return getRuleContext(JsonPathInvocationContext.class,0);
		}
		public TerminalNode COLUMNS() { return getToken(StructuresSqlParserParser.COLUMNS, 0); }
		public List<JsonTableColumnContext> jsonTableColumn() {
			return getRuleContexts(JsonTableColumnContext.class);
		}
		public JsonTableColumnContext jsonTableColumn(int i) {
			return getRuleContext(JsonTableColumnContext.class,i);
		}
		public TerminalNode PLAN() { return getToken(StructuresSqlParserParser.PLAN, 0); }
		public JsonTableSpecificPlanContext jsonTableSpecificPlan() {
			return getRuleContext(JsonTableSpecificPlanContext.class,0);
		}
		public TerminalNode DEFAULT() { return getToken(StructuresSqlParserParser.DEFAULT, 0); }
		public JsonTableDefaultPlanContext jsonTableDefaultPlan() {
			return getRuleContext(JsonTableDefaultPlanContext.class,0);
		}
		public TerminalNode ON() { return getToken(StructuresSqlParserParser.ON, 0); }
		public List<TerminalNode> ERROR() { return getTokens(StructuresSqlParserParser.ERROR); }
		public TerminalNode ERROR(int i) {
			return getToken(StructuresSqlParserParser.ERROR, i);
		}
		public TerminalNode EMPTY() { return getToken(StructuresSqlParserParser.EMPTY, 0); }
		public JsonTableContext(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonTable(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenthesizedRelationContext extends RelationPrimaryContext {
		public RelationContext relation() {
			return getRuleContext(RelationContext.class,0);
		}
		public ParenthesizedRelationContext(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterParenthesizedRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitParenthesizedRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitParenthesizedRelation(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnnestContext extends RelationPrimaryContext {
		public TerminalNode UNNEST() { return getToken(StructuresSqlParserParser.UNNEST, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode WITH() { return getToken(StructuresSqlParserParser.WITH, 0); }
		public TerminalNode ORDINALITY() { return getToken(StructuresSqlParserParser.ORDINALITY, 0); }
		public UnnestContext(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterUnnest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitUnnest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitUnnest(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TableFunctionInvocationContext extends RelationPrimaryContext {
		public TerminalNode TABLE() { return getToken(StructuresSqlParserParser.TABLE, 0); }
		public TableFunctionCallContext tableFunctionCall() {
			return getRuleContext(TableFunctionCallContext.class,0);
		}
		public TableFunctionInvocationContext(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTableFunctionInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTableFunctionInvocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTableFunctionInvocation(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LateralContext extends RelationPrimaryContext {
		public TerminalNode LATERAL() { return getToken(StructuresSqlParserParser.LATERAL, 0); }
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public LateralContext(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLateral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLateral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLateral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TableNameContext extends RelationPrimaryContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public QueryPeriodContext queryPeriod() {
			return getRuleContext(QueryPeriodContext.class,0);
		}
		public TableNameContext(RelationPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTableName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTableName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTableName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationPrimaryContext relationPrimary() throws RecognitionException {
		RelationPrimaryContext _localctx = new RelationPrimaryContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_relationPrimary);
		int _la;
		try {
			setState(975);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,106,_ctx) ) {
			case 1:
				_localctx = new TableNameContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(904);
				qualifiedName();
				setState(906);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,100,_ctx) ) {
				case 1:
					{
					setState(905);
					queryPeriod();
					}
					break;
				}
				}
				break;
			case 2:
				_localctx = new SubqueryRelationContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(908);
				match(T__2);
				setState(909);
				query();
				setState(910);
				match(T__3);
				}
				break;
			case 3:
				_localctx = new UnnestContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(912);
				match(UNNEST);
				setState(913);
				match(T__2);
				setState(914);
				expression();
				setState(919);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(915);
					match(T__1);
					setState(916);
					expression();
					}
					}
					setState(921);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(922);
				match(T__3);
				setState(925);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,102,_ctx) ) {
				case 1:
					{
					setState(923);
					match(WITH);
					setState(924);
					match(ORDINALITY);
					}
					break;
				}
				}
				break;
			case 4:
				_localctx = new LateralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(927);
				match(LATERAL);
				setState(928);
				match(T__2);
				setState(929);
				query();
				setState(930);
				match(T__3);
				}
				break;
			case 5:
				_localctx = new TableFunctionInvocationContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(932);
				match(TABLE);
				setState(933);
				match(T__2);
				setState(934);
				tableFunctionCall();
				setState(935);
				match(T__3);
				}
				break;
			case 6:
				_localctx = new ParenthesizedRelationContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(937);
				match(T__2);
				setState(938);
				relation(0);
				setState(939);
				match(T__3);
				}
				break;
			case 7:
				_localctx = new JsonTableContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(941);
				match(JSON_TABLE);
				setState(942);
				match(T__2);
				setState(943);
				jsonPathInvocation();
				setState(944);
				match(COLUMNS);
				setState(945);
				match(T__2);
				setState(946);
				jsonTableColumn();
				setState(951);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(947);
					match(T__1);
					setState(948);
					jsonTableColumn();
					}
					}
					setState(953);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(954);
				match(T__3);
				setState(966);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,104,_ctx) ) {
				case 1:
					{
					setState(955);
					match(PLAN);
					setState(956);
					match(T__2);
					setState(957);
					jsonTableSpecificPlan();
					setState(958);
					match(T__3);
					}
					break;
				case 2:
					{
					setState(960);
					match(PLAN);
					setState(961);
					match(DEFAULT);
					setState(962);
					match(T__2);
					setState(963);
					jsonTableDefaultPlan();
					setState(964);
					match(T__3);
					}
					break;
				}
				setState(971);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EMPTY || _la==ERROR) {
					{
					setState(968);
					_la = _input.LA(1);
					if ( !(_la==EMPTY || _la==ERROR) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(969);
					match(ON);
					setState(970);
					match(ERROR);
					}
				}

				setState(973);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonTableColumnContext extends ParserRuleContext {
		public JsonTableColumnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonTableColumn; }
	 
		public JsonTableColumnContext() { }
		public void copyFrom(JsonTableColumnContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class QueryColumnContext extends JsonTableColumnContext {
		public JsonQueryBehaviorContext emptyBehavior;
		public JsonQueryBehaviorContext errorBehavior;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode FORMAT() { return getToken(StructuresSqlParserParser.FORMAT, 0); }
		public JsonRepresentationContext jsonRepresentation() {
			return getRuleContext(JsonRepresentationContext.class,0);
		}
		public TerminalNode PATH() { return getToken(StructuresSqlParserParser.PATH, 0); }
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public JsonQueryWrapperBehaviorContext jsonQueryWrapperBehavior() {
			return getRuleContext(JsonQueryWrapperBehaviorContext.class,0);
		}
		public TerminalNode WRAPPER() { return getToken(StructuresSqlParserParser.WRAPPER, 0); }
		public TerminalNode QUOTES() { return getToken(StructuresSqlParserParser.QUOTES, 0); }
		public List<TerminalNode> ON() { return getTokens(StructuresSqlParserParser.ON); }
		public TerminalNode ON(int i) {
			return getToken(StructuresSqlParserParser.ON, i);
		}
		public TerminalNode EMPTY() { return getToken(StructuresSqlParserParser.EMPTY, 0); }
		public TerminalNode ERROR() { return getToken(StructuresSqlParserParser.ERROR, 0); }
		public TerminalNode KEEP() { return getToken(StructuresSqlParserParser.KEEP, 0); }
		public TerminalNode OMIT() { return getToken(StructuresSqlParserParser.OMIT, 0); }
		public List<JsonQueryBehaviorContext> jsonQueryBehavior() {
			return getRuleContexts(JsonQueryBehaviorContext.class);
		}
		public JsonQueryBehaviorContext jsonQueryBehavior(int i) {
			return getRuleContext(JsonQueryBehaviorContext.class,i);
		}
		public TerminalNode SCALAR() { return getToken(StructuresSqlParserParser.SCALAR, 0); }
		public TerminalNode TEXT_STRING() { return getToken(StructuresSqlParserParser.TEXT_STRING, 0); }
		public QueryColumnContext(JsonTableColumnContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQueryColumn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQueryColumn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQueryColumn(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NestedColumnsContext extends JsonTableColumnContext {
		public TerminalNode NESTED() { return getToken(StructuresSqlParserParser.NESTED, 0); }
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode COLUMNS() { return getToken(StructuresSqlParserParser.COLUMNS, 0); }
		public List<JsonTableColumnContext> jsonTableColumn() {
			return getRuleContexts(JsonTableColumnContext.class);
		}
		public JsonTableColumnContext jsonTableColumn(int i) {
			return getRuleContext(JsonTableColumnContext.class,i);
		}
		public TerminalNode PATH() { return getToken(StructuresSqlParserParser.PATH, 0); }
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public NestedColumnsContext(JsonTableColumnContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterNestedColumns(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitNestedColumns(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitNestedColumns(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ValueColumnContext extends JsonTableColumnContext {
		public JsonValueBehaviorContext emptyBehavior;
		public JsonValueBehaviorContext errorBehavior;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode PATH() { return getToken(StructuresSqlParserParser.PATH, 0); }
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public List<TerminalNode> ON() { return getTokens(StructuresSqlParserParser.ON); }
		public TerminalNode ON(int i) {
			return getToken(StructuresSqlParserParser.ON, i);
		}
		public TerminalNode EMPTY() { return getToken(StructuresSqlParserParser.EMPTY, 0); }
		public TerminalNode ERROR() { return getToken(StructuresSqlParserParser.ERROR, 0); }
		public List<JsonValueBehaviorContext> jsonValueBehavior() {
			return getRuleContexts(JsonValueBehaviorContext.class);
		}
		public JsonValueBehaviorContext jsonValueBehavior(int i) {
			return getRuleContext(JsonValueBehaviorContext.class,i);
		}
		public ValueColumnContext(JsonTableColumnContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterValueColumn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitValueColumn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitValueColumn(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrdinalityColumnContext extends JsonTableColumnContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode FOR() { return getToken(StructuresSqlParserParser.FOR, 0); }
		public TerminalNode ORDINALITY() { return getToken(StructuresSqlParserParser.ORDINALITY, 0); }
		public OrdinalityColumnContext(JsonTableColumnContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterOrdinalityColumn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitOrdinalityColumn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitOrdinalityColumn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonTableColumnContext jsonTableColumn() throws RecognitionException {
		JsonTableColumnContext _localctx = new JsonTableColumnContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_jsonTableColumn);
		int _la;
		try {
			setState(1054);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,119,_ctx) ) {
			case 1:
				_localctx = new OrdinalityColumnContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(977);
				identifier();
				setState(978);
				match(FOR);
				setState(979);
				match(ORDINALITY);
				}
				break;
			case 2:
				_localctx = new ValueColumnContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(981);
				identifier();
				setState(982);
				type(0);
				setState(985);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PATH) {
					{
					setState(983);
					match(PATH);
					setState(984);
					string();
					}
				}

				setState(991);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,108,_ctx) ) {
				case 1:
					{
					setState(987);
					((ValueColumnContext)_localctx).emptyBehavior = jsonValueBehavior();
					setState(988);
					match(ON);
					setState(989);
					match(EMPTY);
					}
					break;
				}
				setState(997);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DEFAULT || _la==ERROR || _la==NULL) {
					{
					setState(993);
					((ValueColumnContext)_localctx).errorBehavior = jsonValueBehavior();
					setState(994);
					match(ON);
					setState(995);
					match(ERROR);
					}
				}

				}
				break;
			case 3:
				_localctx = new QueryColumnContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(999);
				identifier();
				setState(1000);
				type(0);
				setState(1001);
				match(FORMAT);
				setState(1002);
				jsonRepresentation();
				setState(1005);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PATH) {
					{
					setState(1003);
					match(PATH);
					setState(1004);
					string();
					}
				}

				setState(1010);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WITH || _la==WITHOUT) {
					{
					setState(1007);
					jsonQueryWrapperBehavior();
					setState(1008);
					match(WRAPPER);
					}
				}

				setState(1019);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==KEEP || _la==OMIT) {
					{
					setState(1012);
					_la = _input.LA(1);
					if ( !(_la==KEEP || _la==OMIT) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(1013);
					match(QUOTES);
					setState(1017);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==ON) {
						{
						setState(1014);
						match(ON);
						setState(1015);
						match(SCALAR);
						setState(1016);
						match(TEXT_STRING);
						}
					}

					}
				}

				setState(1025);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,114,_ctx) ) {
				case 1:
					{
					setState(1021);
					((QueryColumnContext)_localctx).emptyBehavior = jsonQueryBehavior();
					setState(1022);
					match(ON);
					setState(1023);
					match(EMPTY);
					}
					break;
				}
				setState(1031);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EMPTY || _la==ERROR || _la==NULL) {
					{
					setState(1027);
					((QueryColumnContext)_localctx).errorBehavior = jsonQueryBehavior();
					setState(1028);
					match(ON);
					setState(1029);
					match(ERROR);
					}
				}

				}
				break;
			case 4:
				_localctx = new NestedColumnsContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1033);
				match(NESTED);
				setState(1035);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PATH) {
					{
					setState(1034);
					match(PATH);
					}
				}

				setState(1037);
				string();
				setState(1040);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(1038);
					match(T__10);
					setState(1039);
					identifier();
					}
				}

				setState(1042);
				match(COLUMNS);
				setState(1043);
				match(T__2);
				setState(1044);
				jsonTableColumn();
				setState(1049);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(1045);
					match(T__1);
					setState(1046);
					jsonTableColumn();
					}
					}
					setState(1051);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1052);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonTableSpecificPlanContext extends ParserRuleContext {
		public JsonTableSpecificPlanContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonTableSpecificPlan; }
	 
		public JsonTableSpecificPlanContext() { }
		public void copyFrom(JsonTableSpecificPlanContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CrossPlanContext extends JsonTableSpecificPlanContext {
		public List<PlanPrimaryContext> planPrimary() {
			return getRuleContexts(PlanPrimaryContext.class);
		}
		public PlanPrimaryContext planPrimary(int i) {
			return getRuleContext(PlanPrimaryContext.class,i);
		}
		public List<TerminalNode> CROSS() { return getTokens(StructuresSqlParserParser.CROSS); }
		public TerminalNode CROSS(int i) {
			return getToken(StructuresSqlParserParser.CROSS, i);
		}
		public CrossPlanContext(JsonTableSpecificPlanContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCrossPlan(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCrossPlan(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCrossPlan(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JoinPlanContext extends JsonTableSpecificPlanContext {
		public JsonTablePathNameContext jsonTablePathName() {
			return getRuleContext(JsonTablePathNameContext.class,0);
		}
		public PlanPrimaryContext planPrimary() {
			return getRuleContext(PlanPrimaryContext.class,0);
		}
		public TerminalNode OUTER() { return getToken(StructuresSqlParserParser.OUTER, 0); }
		public TerminalNode INNER() { return getToken(StructuresSqlParserParser.INNER, 0); }
		public JoinPlanContext(JsonTableSpecificPlanContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJoinPlan(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJoinPlan(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJoinPlan(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LeafPlanContext extends JsonTableSpecificPlanContext {
		public JsonTablePathNameContext jsonTablePathName() {
			return getRuleContext(JsonTablePathNameContext.class,0);
		}
		public LeafPlanContext(JsonTableSpecificPlanContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLeafPlan(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLeafPlan(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLeafPlan(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnionPlanContext extends JsonTableSpecificPlanContext {
		public List<PlanPrimaryContext> planPrimary() {
			return getRuleContexts(PlanPrimaryContext.class);
		}
		public PlanPrimaryContext planPrimary(int i) {
			return getRuleContext(PlanPrimaryContext.class,i);
		}
		public List<TerminalNode> UNION() { return getTokens(StructuresSqlParserParser.UNION); }
		public TerminalNode UNION(int i) {
			return getToken(StructuresSqlParserParser.UNION, i);
		}
		public UnionPlanContext(JsonTableSpecificPlanContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterUnionPlan(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitUnionPlan(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitUnionPlan(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonTableSpecificPlanContext jsonTableSpecificPlan() throws RecognitionException {
		JsonTableSpecificPlanContext _localctx = new JsonTableSpecificPlanContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_jsonTableSpecificPlan);
		int _la;
		try {
			setState(1081);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,122,_ctx) ) {
			case 1:
				_localctx = new LeafPlanContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1056);
				jsonTablePathName();
				}
				break;
			case 2:
				_localctx = new JoinPlanContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1057);
				jsonTablePathName();
				setState(1058);
				_la = _input.LA(1);
				if ( !(_la==INNER || _la==OUTER) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1059);
				planPrimary();
				}
				break;
			case 3:
				_localctx = new UnionPlanContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1061);
				planPrimary();
				setState(1062);
				match(UNION);
				setState(1063);
				planPrimary();
				setState(1068);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==UNION) {
					{
					{
					setState(1064);
					match(UNION);
					setState(1065);
					planPrimary();
					}
					}
					setState(1070);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 4:
				_localctx = new CrossPlanContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1071);
				planPrimary();
				setState(1072);
				match(CROSS);
				setState(1073);
				planPrimary();
				setState(1078);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==CROSS) {
					{
					{
					setState(1074);
					match(CROSS);
					setState(1075);
					planPrimary();
					}
					}
					setState(1080);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonTablePathNameContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public JsonTablePathNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonTablePathName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonTablePathName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonTablePathName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonTablePathName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonTablePathNameContext jsonTablePathName() throws RecognitionException {
		JsonTablePathNameContext _localctx = new JsonTablePathNameContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_jsonTablePathName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1083);
			identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PlanPrimaryContext extends ParserRuleContext {
		public JsonTablePathNameContext jsonTablePathName() {
			return getRuleContext(JsonTablePathNameContext.class,0);
		}
		public JsonTableSpecificPlanContext jsonTableSpecificPlan() {
			return getRuleContext(JsonTableSpecificPlanContext.class,0);
		}
		public PlanPrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_planPrimary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPlanPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPlanPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPlanPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PlanPrimaryContext planPrimary() throws RecognitionException {
		PlanPrimaryContext _localctx = new PlanPrimaryContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_planPrimary);
		try {
			setState(1090);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,123,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1085);
				jsonTablePathName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1086);
				match(T__2);
				setState(1087);
				jsonTableSpecificPlan();
				setState(1088);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonTableDefaultPlanContext extends ParserRuleContext {
		public TerminalNode OUTER() { return getToken(StructuresSqlParserParser.OUTER, 0); }
		public TerminalNode INNER() { return getToken(StructuresSqlParserParser.INNER, 0); }
		public TerminalNode UNION() { return getToken(StructuresSqlParserParser.UNION, 0); }
		public TerminalNode CROSS() { return getToken(StructuresSqlParserParser.CROSS, 0); }
		public JsonTableDefaultPlanContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonTableDefaultPlan; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonTableDefaultPlan(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonTableDefaultPlan(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonTableDefaultPlan(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonTableDefaultPlanContext jsonTableDefaultPlan() throws RecognitionException {
		JsonTableDefaultPlanContext _localctx = new JsonTableDefaultPlanContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_jsonTableDefaultPlan);
		int _la;
		try {
			setState(1102);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INNER:
			case OUTER:
				enterOuterAlt(_localctx, 1);
				{
				setState(1092);
				_la = _input.LA(1);
				if ( !(_la==INNER || _la==OUTER) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1095);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(1093);
					match(T__1);
					setState(1094);
					_la = _input.LA(1);
					if ( !(_la==CROSS || _la==UNION) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				}
				break;
			case CROSS:
			case UNION:
				enterOuterAlt(_localctx, 2);
				{
				setState(1097);
				_la = _input.LA(1);
				if ( !(_la==CROSS || _la==UNION) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1100);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(1098);
					match(T__1);
					setState(1099);
					_la = _input.LA(1);
					if ( !(_la==INNER || _la==OUTER) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableFunctionCallContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public List<TableFunctionArgumentContext> tableFunctionArgument() {
			return getRuleContexts(TableFunctionArgumentContext.class);
		}
		public TableFunctionArgumentContext tableFunctionArgument(int i) {
			return getRuleContext(TableFunctionArgumentContext.class,i);
		}
		public TerminalNode COPARTITION() { return getToken(StructuresSqlParserParser.COPARTITION, 0); }
		public List<CopartitionTablesContext> copartitionTables() {
			return getRuleContexts(CopartitionTablesContext.class);
		}
		public CopartitionTablesContext copartitionTables(int i) {
			return getRuleContext(CopartitionTablesContext.class,i);
		}
		public TableFunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableFunctionCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTableFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTableFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTableFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableFunctionCallContext tableFunctionCall() throws RecognitionException {
		TableFunctionCallContext _localctx = new TableFunctionCallContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_tableFunctionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1104);
			qualifiedName();
			setState(1105);
			match(T__2);
			setState(1114);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,128,_ctx) ) {
			case 1:
				{
				setState(1106);
				tableFunctionArgument();
				setState(1111);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(1107);
					match(T__1);
					setState(1108);
					tableFunctionArgument();
					}
					}
					setState(1113);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
			setState(1125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COPARTITION) {
				{
				setState(1116);
				match(COPARTITION);
				setState(1117);
				copartitionTables();
				setState(1122);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(1118);
					match(T__1);
					setState(1119);
					copartitionTables();
					}
					}
					setState(1124);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1127);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableFunctionArgumentContext extends ParserRuleContext {
		public TableArgumentContext tableArgument() {
			return getRuleContext(TableArgumentContext.class,0);
		}
		public DescriptorArgumentContext descriptorArgument() {
			return getRuleContext(DescriptorArgumentContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TableFunctionArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableFunctionArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTableFunctionArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTableFunctionArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTableFunctionArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableFunctionArgumentContext tableFunctionArgument() throws RecognitionException {
		TableFunctionArgumentContext _localctx = new TableFunctionArgumentContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_tableFunctionArgument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1132);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,131,_ctx) ) {
			case 1:
				{
				setState(1129);
				identifier();
				setState(1130);
				match(T__5);
				}
				break;
			}
			setState(1137);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,132,_ctx) ) {
			case 1:
				{
				setState(1134);
				tableArgument();
				}
				break;
			case 2:
				{
				setState(1135);
				descriptorArgument();
				}
				break;
			case 3:
				{
				setState(1136);
				expression();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableArgumentContext extends ParserRuleContext {
		public TableArgumentRelationContext tableArgumentRelation() {
			return getRuleContext(TableArgumentRelationContext.class,0);
		}
		public TerminalNode PARTITION() { return getToken(StructuresSqlParserParser.PARTITION, 0); }
		public List<TerminalNode> BY() { return getTokens(StructuresSqlParserParser.BY); }
		public TerminalNode BY(int i) {
			return getToken(StructuresSqlParserParser.BY, i);
		}
		public TerminalNode PRUNE() { return getToken(StructuresSqlParserParser.PRUNE, 0); }
		public TerminalNode WHEN() { return getToken(StructuresSqlParserParser.WHEN, 0); }
		public TerminalNode EMPTY() { return getToken(StructuresSqlParserParser.EMPTY, 0); }
		public TerminalNode KEEP() { return getToken(StructuresSqlParserParser.KEEP, 0); }
		public TerminalNode ORDER() { return getToken(StructuresSqlParserParser.ORDER, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<SortItemContext> sortItem() {
			return getRuleContexts(SortItemContext.class);
		}
		public SortItemContext sortItem(int i) {
			return getRuleContext(SortItemContext.class,i);
		}
		public TableArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTableArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTableArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTableArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableArgumentContext tableArgument() throws RecognitionException {
		TableArgumentContext _localctx = new TableArgumentContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_tableArgument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1139);
			tableArgumentRelation();
			setState(1157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PARTITION) {
				{
				setState(1140);
				match(PARTITION);
				setState(1141);
				match(T__18);
				setState(1155);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,135,_ctx) ) {
				case 1:
					{
					setState(1142);
					match(T__2);
					setState(1151);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,134,_ctx) ) {
					case 1:
						{
						setState(1143);
						expression();
						setState(1148);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==T__1) {
							{
							{
							setState(1144);
							match(T__1);
							setState(1145);
							expression();
							}
							}
							setState(1150);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						}
						break;
					}
					setState(1153);
					match(T__3);
					}
					break;
				case 2:
					{
					setState(1154);
					expression();
					}
					break;
				}
				}
			}

			setState(1165);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRUNE:
				{
				setState(1159);
				match(PRUNE);
				setState(1160);
				match(WHEN);
				setState(1161);
				match(EMPTY);
				}
				break;
			case KEEP:
				{
				setState(1162);
				match(KEEP);
				setState(1163);
				match(WHEN);
				setState(1164);
				match(EMPTY);
				}
				break;
			case T__1:
			case T__3:
			case COPARTITION:
			case ORDER:
				break;
			default:
				break;
			}
			setState(1183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ORDER) {
				{
				setState(1167);
				match(ORDER);
				setState(1168);
				match(T__18);
				setState(1181);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,139,_ctx) ) {
				case 1:
					{
					setState(1169);
					match(T__2);
					setState(1170);
					sortItem();
					setState(1175);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(1171);
						match(T__1);
						setState(1172);
						sortItem();
						}
						}
						setState(1177);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(1178);
					match(T__3);
					}
					break;
				case 2:
					{
					setState(1180);
					sortItem();
					}
					break;
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableArgumentRelationContext extends ParserRuleContext {
		public TableArgumentRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableArgumentRelation; }
	 
		public TableArgumentRelationContext() { }
		public void copyFrom(TableArgumentRelationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TableArgumentQueryContext extends TableArgumentRelationContext {
		public TerminalNode TABLE() { return getToken(StructuresSqlParserParser.TABLE, 0); }
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public ColumnAliasesContext columnAliases() {
			return getRuleContext(ColumnAliasesContext.class,0);
		}
		public TableArgumentQueryContext(TableArgumentRelationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTableArgumentQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTableArgumentQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTableArgumentQuery(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TableArgumentTableContext extends TableArgumentRelationContext {
		public TerminalNode TABLE() { return getToken(StructuresSqlParserParser.TABLE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public ColumnAliasesContext columnAliases() {
			return getRuleContext(ColumnAliasesContext.class,0);
		}
		public TableArgumentTableContext(TableArgumentRelationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTableArgumentTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTableArgumentTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTableArgumentTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableArgumentRelationContext tableArgumentRelation() throws RecognitionException {
		TableArgumentRelationContext _localctx = new TableArgumentRelationContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_tableArgumentRelation);
		int _la;
		try {
			setState(1211);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,147,_ctx) ) {
			case 1:
				_localctx = new TableArgumentTableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1185);
				match(TABLE);
				setState(1186);
				match(T__2);
				setState(1187);
				qualifiedName();
				setState(1188);
				match(T__3);
				setState(1196);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,143,_ctx) ) {
				case 1:
					{
					setState(1190);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__10) {
						{
						setState(1189);
						match(T__10);
						}
					}

					setState(1192);
					identifier();
					setState(1194);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__2) {
						{
						setState(1193);
						columnAliases();
						}
					}

					}
					break;
				}
				}
				break;
			case 2:
				_localctx = new TableArgumentQueryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1198);
				match(TABLE);
				setState(1199);
				match(T__2);
				setState(1200);
				query();
				setState(1201);
				match(T__3);
				setState(1209);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,146,_ctx) ) {
				case 1:
					{
					setState(1203);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__10) {
						{
						setState(1202);
						match(T__10);
						}
					}

					setState(1205);
					identifier();
					setState(1207);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__2) {
						{
						setState(1206);
						columnAliases();
						}
					}

					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DescriptorArgumentContext extends ParserRuleContext {
		public TerminalNode DESCRIPTOR() { return getToken(StructuresSqlParserParser.DESCRIPTOR, 0); }
		public List<DescriptorFieldContext> descriptorField() {
			return getRuleContexts(DescriptorFieldContext.class);
		}
		public DescriptorFieldContext descriptorField(int i) {
			return getRuleContext(DescriptorFieldContext.class,i);
		}
		public TerminalNode CAST() { return getToken(StructuresSqlParserParser.CAST, 0); }
		public TerminalNode NULL() { return getToken(StructuresSqlParserParser.NULL, 0); }
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public DescriptorArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_descriptorArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterDescriptorArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitDescriptorArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitDescriptorArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DescriptorArgumentContext descriptorArgument() throws RecognitionException {
		DescriptorArgumentContext _localctx = new DescriptorArgumentContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_descriptorArgument);
		int _la;
		try {
			setState(1231);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DESCRIPTOR:
				enterOuterAlt(_localctx, 1);
				{
				setState(1213);
				match(DESCRIPTOR);
				setState(1214);
				match(T__2);
				setState(1215);
				descriptorField();
				setState(1220);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(1216);
					match(T__1);
					setState(1217);
					descriptorField();
					}
					}
					setState(1222);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1223);
				match(T__3);
				}
				break;
			case CAST:
				enterOuterAlt(_localctx, 2);
				{
				setState(1225);
				match(CAST);
				setState(1226);
				match(T__2);
				setState(1227);
				match(NULL);
				setState(1228);
				match(T__10);
				setState(1229);
				match(DESCRIPTOR);
				setState(1230);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DescriptorFieldContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public DescriptorFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_descriptorField; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterDescriptorField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitDescriptorField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitDescriptorField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DescriptorFieldContext descriptorField() throws RecognitionException {
		DescriptorFieldContext _localctx = new DescriptorFieldContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_descriptorField);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1233);
			identifier();
			setState(1235);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,150,_ctx) ) {
			case 1:
				{
				setState(1234);
				type(0);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CopartitionTablesContext extends ParserRuleContext {
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public CopartitionTablesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_copartitionTables; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCopartitionTables(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCopartitionTables(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCopartitionTables(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CopartitionTablesContext copartitionTables() throws RecognitionException {
		CopartitionTablesContext _localctx = new CopartitionTablesContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_copartitionTables);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1237);
			match(T__2);
			setState(1238);
			qualifiedName();
			setState(1239);
			match(T__1);
			setState(1240);
			qualifiedName();
			setState(1245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(1241);
				match(T__1);
				setState(1242);
				qualifiedName();
				}
				}
				setState(1247);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1248);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1250);
			booleanExpression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BooleanExpressionContext extends ParserRuleContext {
		public BooleanExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExpression; }
	 
		public BooleanExpressionContext() { }
		public void copyFrom(BooleanExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LogicalNotContext extends BooleanExpressionContext {
		public TerminalNode NOT() { return getToken(StructuresSqlParserParser.NOT, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public LogicalNotContext(BooleanExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLogicalNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLogicalNot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLogicalNot(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PredicatedContext extends BooleanExpressionContext {
		public ValueExpressionContext valueExpression;
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public PredicatedContext(BooleanExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPredicated(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPredicated(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPredicated(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrContext extends BooleanExpressionContext {
		public List<BooleanExpressionContext> booleanExpression() {
			return getRuleContexts(BooleanExpressionContext.class);
		}
		public BooleanExpressionContext booleanExpression(int i) {
			return getRuleContext(BooleanExpressionContext.class,i);
		}
		public TerminalNode OR() { return getToken(StructuresSqlParserParser.OR, 0); }
		public OrContext(BooleanExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitOr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndContext extends BooleanExpressionContext {
		public List<BooleanExpressionContext> booleanExpression() {
			return getRuleContexts(BooleanExpressionContext.class);
		}
		public BooleanExpressionContext booleanExpression(int i) {
			return getRuleContext(BooleanExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(StructuresSqlParserParser.AND, 0); }
		public AndContext(BooleanExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanExpressionContext booleanExpression() throws RecognitionException {
		return booleanExpression(0);
	}

	private BooleanExpressionContext booleanExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BooleanExpressionContext _localctx = new BooleanExpressionContext(_ctx, _parentState);
		BooleanExpressionContext _prevctx = _localctx;
		int _startState = 128;
		enterRecursionRule(_localctx, 128, RULE_booleanExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1259);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case T__3:
			case T__4:
			case T__6:
			case T__8:
			case T__9:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__17:
			case CALL:
			case CALLED:
			case CASCADE:
			case CASE:
			case CAST:
			case CATALOG:
			case CATALOGS:
			case COLUMN:
			case COLUMNS:
			case COMMENT:
			case COMMIT:
			case COMMITTED:
			case CONDITIONAL:
			case COUNT:
			case COPARTITION:
			case CURRENT:
			case CURRENT_CATALOG:
			case CURRENT_DATE:
			case CURRENT_PATH:
			case CURRENT_SCHEMA:
			case CURRENT_TIME:
			case CURRENT_TIMESTAMP:
			case CURRENT_USER:
			case DATA:
			case DATE:
			case DAY:
			case DECLARE:
			case DEFAULT:
			case DEFINE:
			case DEFINER:
			case DENY:
			case DESC:
			case DESCRIPTOR:
			case DETERMINISTIC:
			case DISTRIBUTED:
			case DO:
			case DOUBLE:
			case EMPTY:
			case ELSEIF:
			case ENCODING:
			case ERROR:
			case EXCLUDING:
			case EXISTS:
			case EXPLAIN:
			case EXTRACT:
			case FALSE:
			case FETCH:
			case FILTER:
			case FINAL:
			case FIRST:
			case FOLLOWING:
			case FORMAT:
			case FUNCTION:
			case FUNCTIONS:
			case GRACE:
			case GRANT:
			case GRANTED:
			case GRANTS:
			case GRAPHVIZ:
			case GROUPING:
			case GROUPS:
			case HOUR:
			case IF:
			case IGNORE:
			case IMMEDIATE:
			case INCLUDING:
			case INITIAL:
			case INPUT:
			case INTERVAL:
			case INVOKER:
			case IO:
			case ISOLATION:
			case ITERATE:
			case JSON:
			case JSON_ARRAY:
			case JSON_EXISTS:
			case JSON_OBJECT:
			case JSON_QUERY:
			case JSON_VALUE:
			case KEEP:
			case KEY:
			case KEYS:
			case LANGUAGE:
			case LAST:
			case LATERAL:
			case LEADING:
			case LEAVE:
			case LEVEL:
			case LIMIT:
			case LISTAGG:
			case LOCAL:
			case LOCALTIME:
			case LOCALTIMESTAMP:
			case LOGICAL:
			case LOOP:
			case MAP:
			case MATCH:
			case MATCHED:
			case MATCHES:
			case MATCH_RECOGNIZE:
			case MATERIALIZED:
			case MEASURES:
			case MERGE:
			case MINUTE:
			case MONTH:
			case NESTED:
			case NEXT:
			case NFC:
			case NFD:
			case NFKC:
			case NFKD:
			case NO:
			case NONE:
			case NORMALIZE:
			case NULL:
			case NULLIF:
			case NULLS:
			case OBJECT:
			case OF:
			case OFFSET:
			case OMIT:
			case ONE:
			case ONLY:
			case OPTION:
			case ORDINALITY:
			case OUTPUT:
			case OVER:
			case OVERFLOW:
			case PARTITION:
			case PARTITIONS:
			case PASSING:
			case PAST:
			case PATH:
			case PATTERN:
			case PER:
			case PERIOD:
			case PERMUTE:
			case PLAN:
			case POSITION:
			case PRECEDING:
			case PRECISION:
			case PRIVILEGES:
			case PROPERTIES:
			case PRUNE:
			case QUOTES:
			case RANGE:
			case READ:
			case REFRESH:
			case RENAME:
			case REPEAT:
			case REPEATABLE:
			case REPLACE:
			case RESET:
			case RESPECT:
			case RESTRICT:
			case RETURN:
			case RETURNING:
			case RETURNS:
			case REVOKE:
			case ROLE:
			case ROLES:
			case ROLLBACK:
			case ROW:
			case ROWS:
			case RUNNING:
			case SCALAR:
			case SCHEMA:
			case SCHEMAS:
			case SECOND:
			case SECURITY:
			case SEEK:
			case SERIALIZABLE:
			case SESSION:
			case SET:
			case SETS:
			case SHOW:
			case SOME:
			case START:
			case STATS:
			case SUBSET:
			case SUBSTRING:
			case SYSTEM:
			case TABLES:
			case TABLESAMPLE:
			case TEXT:
			case TEXT_STRING:
			case TIES:
			case TIME:
			case TIMESTAMP:
			case TO:
			case TRAILING:
			case TRANSACTION:
			case TRIM:
			case TRUE:
			case TRUNCATE:
			case TRY_CAST:
			case TYPE:
			case UNBOUNDED:
			case UNCOMMITTED:
			case UNCONDITIONAL:
			case UNIQUE:
			case UNKNOWN:
			case UNMATCHED:
			case UNTIL:
			case UPDATE:
			case USE:
			case USER:
			case UTF16:
			case UTF32:
			case UTF8:
			case VALIDATE:
			case VALUE:
			case VERBOSE:
			case VERSION:
			case VIEW:
			case WHILE:
			case WINDOW:
			case WITHIN:
			case WITHOUT:
			case WORK:
			case WRAPPER:
			case WRITE:
			case YEAR:
			case ZONE:
			case PLUS:
			case MINUS:
			case QUESTION_MARK:
			case STRING:
			case UNICODE_STRING:
			case BINARY_LITERAL:
			case INTEGER_VALUE:
			case DECIMAL_VALUE:
			case DOUBLE_VALUE:
			case IDENTIFIER:
			case DIGIT_IDENTIFIER:
			case QUOTED_IDENTIFIER:
			case BACKQUOTED_IDENTIFIER:
				{
				_localctx = new PredicatedContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1253);
				((PredicatedContext)_localctx).valueExpression = valueExpression(0);
				setState(1255);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,152,_ctx) ) {
				case 1:
					{
					setState(1254);
					predicate(((PredicatedContext)_localctx).valueExpression);
					}
					break;
				}
				}
				break;
			case NOT:
				{
				_localctx = new LogicalNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1257);
				match(NOT);
				setState(1258);
				booleanExpression(3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(1269);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,155,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1267);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,154,_ctx) ) {
					case 1:
						{
						_localctx = new AndContext(new BooleanExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_booleanExpression);
						setState(1261);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(1262);
						match(T__7);
						setState(1263);
						booleanExpression(3);
						}
						break;
					case 2:
						{
						_localctx = new OrContext(new BooleanExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_booleanExpression);
						setState(1264);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(1265);
						match(OR);
						setState(1266);
						booleanExpression(2);
						}
						break;
					}
					} 
				}
				setState(1271);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,155,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PredicateContext extends ParserRuleContext {
		public ParserRuleContext value;
		public PredicateContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public PredicateContext(ParserRuleContext parent, int invokingState, ParserRuleContext value) {
			super(parent, invokingState);
			this.value = value;
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
	 
		public PredicateContext() { }
		public void copyFrom(PredicateContext ctx) {
			super.copyFrom(ctx);
			this.value = ctx.value;
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonContext extends PredicateContext {
		public ValueExpressionContext right;
		public ComparisonOperatorContext comparisonOperator() {
			return getRuleContext(ComparisonOperatorContext.class,0);
		}
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public ComparisonContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitComparison(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LikeContext extends PredicateContext {
		public ValueExpressionContext pattern;
		public ValueExpressionContext escape;
		public TerminalNode LIKE() { return getToken(StructuresSqlParserParser.LIKE, 0); }
		public List<ValueExpressionContext> valueExpression() {
			return getRuleContexts(ValueExpressionContext.class);
		}
		public ValueExpressionContext valueExpression(int i) {
			return getRuleContext(ValueExpressionContext.class,i);
		}
		public TerminalNode NOT() { return getToken(StructuresSqlParserParser.NOT, 0); }
		public TerminalNode ESCAPE() { return getToken(StructuresSqlParserParser.ESCAPE, 0); }
		public LikeContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLike(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLike(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLike(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InSubqueryContext extends PredicateContext {
		public TerminalNode IN() { return getToken(StructuresSqlParserParser.IN, 0); }
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public TerminalNode NOT() { return getToken(StructuresSqlParserParser.NOT, 0); }
		public InSubqueryContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterInSubquery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitInSubquery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitInSubquery(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DistinctFromContext extends PredicateContext {
		public ValueExpressionContext right;
		public TerminalNode IS() { return getToken(StructuresSqlParserParser.IS, 0); }
		public TerminalNode DISTINCT() { return getToken(StructuresSqlParserParser.DISTINCT, 0); }
		public TerminalNode FROM() { return getToken(StructuresSqlParserParser.FROM, 0); }
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public TerminalNode NOT() { return getToken(StructuresSqlParserParser.NOT, 0); }
		public DistinctFromContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterDistinctFrom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitDistinctFrom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitDistinctFrom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InListContext extends PredicateContext {
		public TerminalNode IN() { return getToken(StructuresSqlParserParser.IN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode NOT() { return getToken(StructuresSqlParserParser.NOT, 0); }
		public InListContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterInList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitInList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitInList(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NullPredicateContext extends PredicateContext {
		public TerminalNode IS() { return getToken(StructuresSqlParserParser.IS, 0); }
		public TerminalNode NULL() { return getToken(StructuresSqlParserParser.NULL, 0); }
		public TerminalNode NOT() { return getToken(StructuresSqlParserParser.NOT, 0); }
		public NullPredicateContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterNullPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitNullPredicate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitNullPredicate(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BetweenContext extends PredicateContext {
		public ValueExpressionContext lower;
		public ValueExpressionContext upper;
		public TerminalNode BETWEEN() { return getToken(StructuresSqlParserParser.BETWEEN, 0); }
		public TerminalNode AND() { return getToken(StructuresSqlParserParser.AND, 0); }
		public List<ValueExpressionContext> valueExpression() {
			return getRuleContexts(ValueExpressionContext.class);
		}
		public ValueExpressionContext valueExpression(int i) {
			return getRuleContext(ValueExpressionContext.class,i);
		}
		public TerminalNode NOT() { return getToken(StructuresSqlParserParser.NOT, 0); }
		public BetweenContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterBetween(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitBetween(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitBetween(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class QuantifiedComparisonContext extends PredicateContext {
		public ComparisonOperatorContext comparisonOperator() {
			return getRuleContext(ComparisonOperatorContext.class,0);
		}
		public ComparisonQuantifierContext comparisonQuantifier() {
			return getRuleContext(ComparisonQuantifierContext.class,0);
		}
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public QuantifiedComparisonContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQuantifiedComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQuantifiedComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQuantifiedComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredicateContext predicate(ParserRuleContext value) throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState(), value);
		enterRule(_localctx, 130, RULE_predicate);
		int _la;
		try {
			setState(1333);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,164,_ctx) ) {
			case 1:
				_localctx = new ComparisonContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1272);
				comparisonOperator();
				setState(1273);
				((ComparisonContext)_localctx).right = valueExpression(0);
				}
				break;
			case 2:
				_localctx = new QuantifiedComparisonContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1275);
				comparisonOperator();
				setState(1276);
				comparisonQuantifier();
				setState(1277);
				match(T__2);
				setState(1278);
				query();
				setState(1279);
				match(T__3);
				}
				break;
			case 3:
				_localctx = new BetweenContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1282);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(1281);
					match(NOT);
					}
				}

				setState(1284);
				match(T__16);
				setState(1285);
				((BetweenContext)_localctx).lower = valueExpression(0);
				setState(1286);
				match(T__7);
				setState(1287);
				((BetweenContext)_localctx).upper = valueExpression(0);
				}
				break;
			case 4:
				_localctx = new InListContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1290);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(1289);
					match(NOT);
					}
				}

				setState(1292);
				match(IN);
				setState(1293);
				match(T__2);
				setState(1294);
				expression();
				setState(1299);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(1295);
					match(T__1);
					setState(1296);
					expression();
					}
					}
					setState(1301);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1302);
				match(T__3);
				}
				break;
			case 5:
				_localctx = new InSubqueryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1305);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(1304);
					match(NOT);
					}
				}

				setState(1307);
				match(IN);
				setState(1308);
				match(T__2);
				setState(1309);
				query();
				setState(1310);
				match(T__3);
				}
				break;
			case 6:
				_localctx = new LikeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1313);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(1312);
					match(NOT);
					}
				}

				setState(1315);
				match(LIKE);
				setState(1316);
				((LikeContext)_localctx).pattern = valueExpression(0);
				setState(1319);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,161,_ctx) ) {
				case 1:
					{
					setState(1317);
					match(ESCAPE);
					setState(1318);
					((LikeContext)_localctx).escape = valueExpression(0);
					}
					break;
				}
				}
				break;
			case 7:
				_localctx = new NullPredicateContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(1321);
				match(IS);
				setState(1323);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(1322);
					match(NOT);
					}
				}

				setState(1325);
				match(NULL);
				}
				break;
			case 8:
				_localctx = new DistinctFromContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(1326);
				match(IS);
				setState(1328);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(1327);
					match(NOT);
					}
				}

				setState(1330);
				match(DISTINCT);
				setState(1331);
				match(FROM);
				setState(1332);
				((DistinctFromContext)_localctx).right = valueExpression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueExpressionContext extends ParserRuleContext {
		public ValueExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueExpression; }
	 
		public ValueExpressionContext() { }
		public void copyFrom(ValueExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ValueExpressionDefaultContext extends ValueExpressionContext {
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public ValueExpressionDefaultContext(ValueExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterValueExpressionDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitValueExpressionDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitValueExpressionDefault(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ConcatenationContext extends ValueExpressionContext {
		public ValueExpressionContext left;
		public ValueExpressionContext right;
		public TerminalNode CONCAT() { return getToken(StructuresSqlParserParser.CONCAT, 0); }
		public List<ValueExpressionContext> valueExpression() {
			return getRuleContexts(ValueExpressionContext.class);
		}
		public ValueExpressionContext valueExpression(int i) {
			return getRuleContext(ValueExpressionContext.class,i);
		}
		public ConcatenationContext(ValueExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterConcatenation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitConcatenation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitConcatenation(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticBinaryContext extends ValueExpressionContext {
		public ValueExpressionContext left;
		public Token operator;
		public ValueExpressionContext right;
		public List<ValueExpressionContext> valueExpression() {
			return getRuleContexts(ValueExpressionContext.class);
		}
		public ValueExpressionContext valueExpression(int i) {
			return getRuleContext(ValueExpressionContext.class,i);
		}
		public TerminalNode ASTERISK() { return getToken(StructuresSqlParserParser.ASTERISK, 0); }
		public TerminalNode SLASH() { return getToken(StructuresSqlParserParser.SLASH, 0); }
		public TerminalNode PERCENT() { return getToken(StructuresSqlParserParser.PERCENT, 0); }
		public TerminalNode PLUS() { return getToken(StructuresSqlParserParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(StructuresSqlParserParser.MINUS, 0); }
		public ArithmeticBinaryContext(ValueExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterArithmeticBinary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitArithmeticBinary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitArithmeticBinary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticUnaryContext extends ValueExpressionContext {
		public Token operator;
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(StructuresSqlParserParser.MINUS, 0); }
		public TerminalNode PLUS() { return getToken(StructuresSqlParserParser.PLUS, 0); }
		public ArithmeticUnaryContext(ValueExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterArithmeticUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitArithmeticUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitArithmeticUnary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtTimeZoneContext extends ValueExpressionContext {
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public TerminalNode AT() { return getToken(StructuresSqlParserParser.AT, 0); }
		public TimeZoneSpecifierContext timeZoneSpecifier() {
			return getRuleContext(TimeZoneSpecifierContext.class,0);
		}
		public AtTimeZoneContext(ValueExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterAtTimeZone(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitAtTimeZone(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitAtTimeZone(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueExpressionContext valueExpression() throws RecognitionException {
		return valueExpression(0);
	}

	private ValueExpressionContext valueExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ValueExpressionContext _localctx = new ValueExpressionContext(_ctx, _parentState);
		ValueExpressionContext _prevctx = _localctx;
		int _startState = 132;
		enterRecursionRule(_localctx, 132, RULE_valueExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1339);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,165,_ctx) ) {
			case 1:
				{
				_localctx = new ValueExpressionDefaultContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1336);
				primaryExpression(0);
				}
				break;
			case 2:
				{
				_localctx = new ArithmeticUnaryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1337);
				((ArithmeticUnaryContext)_localctx).operator = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
					((ArithmeticUnaryContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1338);
				valueExpression(4);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1355);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,167,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1353);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,166,_ctx) ) {
					case 1:
						{
						_localctx = new ArithmeticBinaryContext(new ValueExpressionContext(_parentctx, _parentState));
						((ArithmeticBinaryContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_valueExpression);
						setState(1341);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(1342);
						((ArithmeticBinaryContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 303)) & ~0x3f) == 0 && ((1L << (_la - 303)) & 7L) != 0)) ) {
							((ArithmeticBinaryContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1343);
						((ArithmeticBinaryContext)_localctx).right = valueExpression(4);
						}
						break;
					case 2:
						{
						_localctx = new ArithmeticBinaryContext(new ValueExpressionContext(_parentctx, _parentState));
						((ArithmeticBinaryContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_valueExpression);
						setState(1344);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(1345);
						((ArithmeticBinaryContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((ArithmeticBinaryContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(1346);
						((ArithmeticBinaryContext)_localctx).right = valueExpression(3);
						}
						break;
					case 3:
						{
						_localctx = new ConcatenationContext(new ValueExpressionContext(_parentctx, _parentState));
						((ConcatenationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_valueExpression);
						setState(1347);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(1348);
						match(CONCAT);
						setState(1349);
						((ConcatenationContext)_localctx).right = valueExpression(2);
						}
						break;
					case 4:
						{
						_localctx = new AtTimeZoneContext(new ValueExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_valueExpression);
						setState(1350);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(1351);
						match(T__12);
						setState(1352);
						timeZoneSpecifier();
						}
						break;
					}
					} 
				}
				setState(1357);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,167,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryExpressionContext extends ParserRuleContext {
		public PrimaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpression; }
	 
		public PrimaryExpressionContext() { }
		public void copyFrom(PrimaryExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DereferenceContext extends PrimaryExpressionContext {
		public PrimaryExpressionContext base;
		public IdentifierContext fieldName;
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public DereferenceContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterDereference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitDereference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitDereference(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeConstructorContext extends PrimaryExpressionContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode DOUBLE() { return getToken(StructuresSqlParserParser.DOUBLE, 0); }
		public TerminalNode PRECISION() { return getToken(StructuresSqlParserParser.PRECISION, 0); }
		public TypeConstructorContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTypeConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTypeConstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTypeConstructor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JsonValueContext extends PrimaryExpressionContext {
		public JsonValueBehaviorContext emptyBehavior;
		public JsonValueBehaviorContext errorBehavior;
		public TerminalNode JSON_VALUE() { return getToken(StructuresSqlParserParser.JSON_VALUE, 0); }
		public JsonPathInvocationContext jsonPathInvocation() {
			return getRuleContext(JsonPathInvocationContext.class,0);
		}
		public TerminalNode RETURNING() { return getToken(StructuresSqlParserParser.RETURNING, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> ON() { return getTokens(StructuresSqlParserParser.ON); }
		public TerminalNode ON(int i) {
			return getToken(StructuresSqlParserParser.ON, i);
		}
		public TerminalNode EMPTY() { return getToken(StructuresSqlParserParser.EMPTY, 0); }
		public TerminalNode ERROR() { return getToken(StructuresSqlParserParser.ERROR, 0); }
		public List<JsonValueBehaviorContext> jsonValueBehavior() {
			return getRuleContexts(JsonValueBehaviorContext.class);
		}
		public JsonValueBehaviorContext jsonValueBehavior(int i) {
			return getRuleContext(JsonValueBehaviorContext.class,i);
		}
		public JsonValueContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CurrentDateContext extends PrimaryExpressionContext {
		public Token name;
		public TerminalNode CURRENT_DATE() { return getToken(StructuresSqlParserParser.CURRENT_DATE, 0); }
		public CurrentDateContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCurrentDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCurrentDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCurrentDate(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubstringContext extends PrimaryExpressionContext {
		public TerminalNode SUBSTRING() { return getToken(StructuresSqlParserParser.SUBSTRING, 0); }
		public List<ValueExpressionContext> valueExpression() {
			return getRuleContexts(ValueExpressionContext.class);
		}
		public ValueExpressionContext valueExpression(int i) {
			return getRuleContext(ValueExpressionContext.class,i);
		}
		public TerminalNode FROM() { return getToken(StructuresSqlParserParser.FROM, 0); }
		public TerminalNode FOR() { return getToken(StructuresSqlParserParser.FOR, 0); }
		public SubstringContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSubstring(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSubstring(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSubstring(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CastContext extends PrimaryExpressionContext {
		public TerminalNode CAST() { return getToken(StructuresSqlParserParser.CAST, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode TRY_CAST() { return getToken(StructuresSqlParserParser.TRY_CAST, 0); }
		public CastContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCast(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCast(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCast(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LambdaContext extends PrimaryExpressionContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LambdaContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLambda(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLambda(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLambda(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenthesizedExpressionContext extends PrimaryExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenthesizedExpressionContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterParenthesizedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitParenthesizedExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitParenthesizedExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TrimContext extends PrimaryExpressionContext {
		public ValueExpressionContext trimChar;
		public ValueExpressionContext trimSource;
		public TerminalNode TRIM() { return getToken(StructuresSqlParserParser.TRIM, 0); }
		public List<ValueExpressionContext> valueExpression() {
			return getRuleContexts(ValueExpressionContext.class);
		}
		public ValueExpressionContext valueExpression(int i) {
			return getRuleContext(ValueExpressionContext.class,i);
		}
		public TerminalNode FROM() { return getToken(StructuresSqlParserParser.FROM, 0); }
		public TrimsSpecificationContext trimsSpecification() {
			return getRuleContext(TrimsSpecificationContext.class,0);
		}
		public TrimContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTrim(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTrim(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTrim(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParameterContext extends PrimaryExpressionContext {
		public TerminalNode QUESTION_MARK() { return getToken(StructuresSqlParserParser.QUESTION_MARK, 0); }
		public ParameterContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NormalizeContext extends PrimaryExpressionContext {
		public TerminalNode NORMALIZE() { return getToken(StructuresSqlParserParser.NORMALIZE, 0); }
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public NormalFormContext normalForm() {
			return getRuleContext(NormalFormContext.class,0);
		}
		public NormalizeContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterNormalize(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitNormalize(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitNormalize(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LocalTimestampContext extends PrimaryExpressionContext {
		public Token name;
		public Token precision;
		public TerminalNode LOCALTIMESTAMP() { return getToken(StructuresSqlParserParser.LOCALTIMESTAMP, 0); }
		public TerminalNode INTEGER_VALUE() { return getToken(StructuresSqlParserParser.INTEGER_VALUE, 0); }
		public LocalTimestampContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLocalTimestamp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLocalTimestamp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLocalTimestamp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JsonObjectContext extends PrimaryExpressionContext {
		public TerminalNode JSON_OBJECT() { return getToken(StructuresSqlParserParser.JSON_OBJECT, 0); }
		public List<JsonObjectMemberContext> jsonObjectMember() {
			return getRuleContexts(JsonObjectMemberContext.class);
		}
		public JsonObjectMemberContext jsonObjectMember(int i) {
			return getRuleContext(JsonObjectMemberContext.class,i);
		}
		public TerminalNode RETURNING() { return getToken(StructuresSqlParserParser.RETURNING, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> NULL() { return getTokens(StructuresSqlParserParser.NULL); }
		public TerminalNode NULL(int i) {
			return getToken(StructuresSqlParserParser.NULL, i);
		}
		public TerminalNode ON() { return getToken(StructuresSqlParserParser.ON, 0); }
		public TerminalNode ABSENT() { return getToken(StructuresSqlParserParser.ABSENT, 0); }
		public TerminalNode WITH() { return getToken(StructuresSqlParserParser.WITH, 0); }
		public TerminalNode UNIQUE() { return getToken(StructuresSqlParserParser.UNIQUE, 0); }
		public TerminalNode WITHOUT() { return getToken(StructuresSqlParserParser.WITHOUT, 0); }
		public TerminalNode FORMAT() { return getToken(StructuresSqlParserParser.FORMAT, 0); }
		public JsonRepresentationContext jsonRepresentation() {
			return getRuleContext(JsonRepresentationContext.class,0);
		}
		public TerminalNode KEYS() { return getToken(StructuresSqlParserParser.KEYS, 0); }
		public JsonObjectContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonObject(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntervalLiteralContext extends PrimaryExpressionContext {
		public IntervalContext interval() {
			return getRuleContext(IntervalContext.class,0);
		}
		public IntervalLiteralContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterIntervalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitIntervalLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitIntervalLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumericLiteralContext extends PrimaryExpressionContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public NumericLiteralContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterNumericLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitNumericLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitNumericLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BooleanLiteralContext extends PrimaryExpressionContext {
		public BooleanValueContext booleanValue() {
			return getRuleContext(BooleanValueContext.class,0);
		}
		public BooleanLiteralContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitBooleanLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JsonArrayContext extends PrimaryExpressionContext {
		public TerminalNode JSON_ARRAY() { return getToken(StructuresSqlParserParser.JSON_ARRAY, 0); }
		public List<JsonValueExpressionContext> jsonValueExpression() {
			return getRuleContexts(JsonValueExpressionContext.class);
		}
		public JsonValueExpressionContext jsonValueExpression(int i) {
			return getRuleContext(JsonValueExpressionContext.class,i);
		}
		public TerminalNode RETURNING() { return getToken(StructuresSqlParserParser.RETURNING, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> NULL() { return getTokens(StructuresSqlParserParser.NULL); }
		public TerminalNode NULL(int i) {
			return getToken(StructuresSqlParserParser.NULL, i);
		}
		public TerminalNode ON() { return getToken(StructuresSqlParserParser.ON, 0); }
		public TerminalNode ABSENT() { return getToken(StructuresSqlParserParser.ABSENT, 0); }
		public TerminalNode FORMAT() { return getToken(StructuresSqlParserParser.FORMAT, 0); }
		public JsonRepresentationContext jsonRepresentation() {
			return getRuleContext(JsonRepresentationContext.class,0);
		}
		public JsonArrayContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonArray(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SimpleCaseContext extends PrimaryExpressionContext {
		public ExpressionContext operand;
		public ExpressionContext elseExpression;
		public TerminalNode CASE() { return getToken(StructuresSqlParserParser.CASE, 0); }
		public TerminalNode END() { return getToken(StructuresSqlParserParser.END, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<WhenClauseContext> whenClause() {
			return getRuleContexts(WhenClauseContext.class);
		}
		public WhenClauseContext whenClause(int i) {
			return getRuleContext(WhenClauseContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(StructuresSqlParserParser.ELSE, 0); }
		public SimpleCaseContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSimpleCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSimpleCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSimpleCase(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ColumnReferenceContext extends PrimaryExpressionContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ColumnReferenceContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterColumnReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitColumnReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitColumnReference(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NullLiteralContext extends PrimaryExpressionContext {
		public TerminalNode NULL() { return getToken(StructuresSqlParserParser.NULL, 0); }
		public NullLiteralContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterNullLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitNullLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitNullLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RowConstructorContext extends PrimaryExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ROW() { return getToken(StructuresSqlParserParser.ROW, 0); }
		public RowConstructorContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRowConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRowConstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRowConstructor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubscriptContext extends PrimaryExpressionContext {
		public PrimaryExpressionContext value;
		public ValueExpressionContext index;
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public SubscriptContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSubscript(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSubscript(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSubscript(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JsonExistsContext extends PrimaryExpressionContext {
		public TerminalNode JSON_EXISTS() { return getToken(StructuresSqlParserParser.JSON_EXISTS, 0); }
		public JsonPathInvocationContext jsonPathInvocation() {
			return getRuleContext(JsonPathInvocationContext.class,0);
		}
		public JsonExistsErrorBehaviorContext jsonExistsErrorBehavior() {
			return getRuleContext(JsonExistsErrorBehaviorContext.class,0);
		}
		public TerminalNode ON() { return getToken(StructuresSqlParserParser.ON, 0); }
		public TerminalNode ERROR() { return getToken(StructuresSqlParserParser.ERROR, 0); }
		public JsonExistsContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonExists(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonExists(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonExists(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CurrentPathContext extends PrimaryExpressionContext {
		public Token name;
		public TerminalNode CURRENT_PATH() { return getToken(StructuresSqlParserParser.CURRENT_PATH, 0); }
		public CurrentPathContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCurrentPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCurrentPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCurrentPath(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubqueryExpressionContext extends PrimaryExpressionContext {
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public SubqueryExpressionContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSubqueryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSubqueryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSubqueryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinaryLiteralContext extends PrimaryExpressionContext {
		public TerminalNode BINARY_LITERAL() { return getToken(StructuresSqlParserParser.BINARY_LITERAL, 0); }
		public BinaryLiteralContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterBinaryLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitBinaryLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitBinaryLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CurrentTimeContext extends PrimaryExpressionContext {
		public Token name;
		public Token precision;
		public TerminalNode CURRENT_TIME() { return getToken(StructuresSqlParserParser.CURRENT_TIME, 0); }
		public TerminalNode INTEGER_VALUE() { return getToken(StructuresSqlParserParser.INTEGER_VALUE, 0); }
		public CurrentTimeContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCurrentTime(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCurrentTime(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCurrentTime(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LocalTimeContext extends PrimaryExpressionContext {
		public Token name;
		public Token precision;
		public TerminalNode LOCALTIME() { return getToken(StructuresSqlParserParser.LOCALTIME, 0); }
		public TerminalNode INTEGER_VALUE() { return getToken(StructuresSqlParserParser.INTEGER_VALUE, 0); }
		public LocalTimeContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLocalTime(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLocalTime(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLocalTime(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CurrentUserContext extends PrimaryExpressionContext {
		public Token name;
		public TerminalNode CURRENT_USER() { return getToken(StructuresSqlParserParser.CURRENT_USER, 0); }
		public CurrentUserContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCurrentUser(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCurrentUser(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCurrentUser(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JsonQueryContext extends PrimaryExpressionContext {
		public JsonQueryBehaviorContext emptyBehavior;
		public JsonQueryBehaviorContext errorBehavior;
		public TerminalNode JSON_QUERY() { return getToken(StructuresSqlParserParser.JSON_QUERY, 0); }
		public JsonPathInvocationContext jsonPathInvocation() {
			return getRuleContext(JsonPathInvocationContext.class,0);
		}
		public TerminalNode RETURNING() { return getToken(StructuresSqlParserParser.RETURNING, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public JsonQueryWrapperBehaviorContext jsonQueryWrapperBehavior() {
			return getRuleContext(JsonQueryWrapperBehaviorContext.class,0);
		}
		public TerminalNode WRAPPER() { return getToken(StructuresSqlParserParser.WRAPPER, 0); }
		public TerminalNode QUOTES() { return getToken(StructuresSqlParserParser.QUOTES, 0); }
		public List<TerminalNode> ON() { return getTokens(StructuresSqlParserParser.ON); }
		public TerminalNode ON(int i) {
			return getToken(StructuresSqlParserParser.ON, i);
		}
		public TerminalNode EMPTY() { return getToken(StructuresSqlParserParser.EMPTY, 0); }
		public TerminalNode ERROR() { return getToken(StructuresSqlParserParser.ERROR, 0); }
		public TerminalNode KEEP() { return getToken(StructuresSqlParserParser.KEEP, 0); }
		public TerminalNode OMIT() { return getToken(StructuresSqlParserParser.OMIT, 0); }
		public List<JsonQueryBehaviorContext> jsonQueryBehavior() {
			return getRuleContexts(JsonQueryBehaviorContext.class);
		}
		public JsonQueryBehaviorContext jsonQueryBehavior(int i) {
			return getRuleContext(JsonQueryBehaviorContext.class,i);
		}
		public TerminalNode FORMAT() { return getToken(StructuresSqlParserParser.FORMAT, 0); }
		public JsonRepresentationContext jsonRepresentation() {
			return getRuleContext(JsonRepresentationContext.class,0);
		}
		public TerminalNode SCALAR() { return getToken(StructuresSqlParserParser.SCALAR, 0); }
		public TerminalNode TEXT_STRING() { return getToken(StructuresSqlParserParser.TEXT_STRING, 0); }
		public JsonQueryContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonQuery(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MeasureContext extends PrimaryExpressionContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public OverContext over() {
			return getRuleContext(OverContext.class,0);
		}
		public MeasureContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterMeasure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitMeasure(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitMeasure(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExtractContext extends PrimaryExpressionContext {
		public TerminalNode EXTRACT() { return getToken(StructuresSqlParserParser.EXTRACT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode FROM() { return getToken(StructuresSqlParserParser.FROM, 0); }
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public ExtractContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterExtract(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitExtract(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitExtract(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringLiteralContext extends PrimaryExpressionContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public StringLiteralContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayConstructorContext extends PrimaryExpressionContext {
		public TerminalNode ARRAY() { return getToken(StructuresSqlParserParser.ARRAY, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayConstructorContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterArrayConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitArrayConstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitArrayConstructor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FunctionCallContext extends PrimaryExpressionContext {
		public IdentifierContext label;
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode ASTERISK() { return getToken(StructuresSqlParserParser.ASTERISK, 0); }
		public ProcessingModeContext processingMode() {
			return getRuleContext(ProcessingModeContext.class,0);
		}
		public FilterContext filter() {
			return getRuleContext(FilterContext.class,0);
		}
		public OverContext over() {
			return getRuleContext(OverContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ORDER() { return getToken(StructuresSqlParserParser.ORDER, 0); }
		public TerminalNode BY() { return getToken(StructuresSqlParserParser.BY, 0); }
		public List<SortItemContext> sortItem() {
			return getRuleContexts(SortItemContext.class);
		}
		public SortItemContext sortItem(int i) {
			return getRuleContext(SortItemContext.class,i);
		}
		public SetQuantifierContext setQuantifier() {
			return getRuleContext(SetQuantifierContext.class,0);
		}
		public NullTreatmentContext nullTreatment() {
			return getRuleContext(NullTreatmentContext.class,0);
		}
		public FunctionCallContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CurrentTimestampContext extends PrimaryExpressionContext {
		public Token name;
		public Token precision;
		public TerminalNode CURRENT_TIMESTAMP() { return getToken(StructuresSqlParserParser.CURRENT_TIMESTAMP, 0); }
		public TerminalNode INTEGER_VALUE() { return getToken(StructuresSqlParserParser.INTEGER_VALUE, 0); }
		public CurrentTimestampContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCurrentTimestamp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCurrentTimestamp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCurrentTimestamp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CurrentSchemaContext extends PrimaryExpressionContext {
		public Token name;
		public TerminalNode CURRENT_SCHEMA() { return getToken(StructuresSqlParserParser.CURRENT_SCHEMA, 0); }
		public CurrentSchemaContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCurrentSchema(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCurrentSchema(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCurrentSchema(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExistsContext extends PrimaryExpressionContext {
		public TerminalNode EXISTS() { return getToken(StructuresSqlParserParser.EXISTS, 0); }
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public ExistsContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterExists(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitExists(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitExists(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PositionContext extends PrimaryExpressionContext {
		public TerminalNode POSITION() { return getToken(StructuresSqlParserParser.POSITION, 0); }
		public List<ValueExpressionContext> valueExpression() {
			return getRuleContexts(ValueExpressionContext.class);
		}
		public ValueExpressionContext valueExpression(int i) {
			return getRuleContext(ValueExpressionContext.class,i);
		}
		public TerminalNode IN() { return getToken(StructuresSqlParserParser.IN, 0); }
		public PositionContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPosition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPosition(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListaggContext extends PrimaryExpressionContext {
		public Token name;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode LISTAGG() { return getToken(StructuresSqlParserParser.LISTAGG, 0); }
		public TerminalNode WITHIN() { return getToken(StructuresSqlParserParser.WITHIN, 0); }
		public TerminalNode GROUP() { return getToken(StructuresSqlParserParser.GROUP, 0); }
		public TerminalNode ORDER() { return getToken(StructuresSqlParserParser.ORDER, 0); }
		public TerminalNode BY() { return getToken(StructuresSqlParserParser.BY, 0); }
		public List<SortItemContext> sortItem() {
			return getRuleContexts(SortItemContext.class);
		}
		public SortItemContext sortItem(int i) {
			return getRuleContext(SortItemContext.class,i);
		}
		public SetQuantifierContext setQuantifier() {
			return getRuleContext(SetQuantifierContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode ON() { return getToken(StructuresSqlParserParser.ON, 0); }
		public TerminalNode OVERFLOW() { return getToken(StructuresSqlParserParser.OVERFLOW, 0); }
		public ListAggOverflowBehaviorContext listAggOverflowBehavior() {
			return getRuleContext(ListAggOverflowBehaviorContext.class,0);
		}
		public FilterContext filter() {
			return getRuleContext(FilterContext.class,0);
		}
		public ListaggContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterListagg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitListagg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitListagg(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SearchedCaseContext extends PrimaryExpressionContext {
		public ExpressionContext elseExpression;
		public TerminalNode CASE() { return getToken(StructuresSqlParserParser.CASE, 0); }
		public TerminalNode END() { return getToken(StructuresSqlParserParser.END, 0); }
		public List<WhenClauseContext> whenClause() {
			return getRuleContexts(WhenClauseContext.class);
		}
		public WhenClauseContext whenClause(int i) {
			return getRuleContext(WhenClauseContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(StructuresSqlParserParser.ELSE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SearchedCaseContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSearchedCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSearchedCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSearchedCase(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CurrentCatalogContext extends PrimaryExpressionContext {
		public Token name;
		public TerminalNode CURRENT_CATALOG() { return getToken(StructuresSqlParserParser.CURRENT_CATALOG, 0); }
		public CurrentCatalogContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCurrentCatalog(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCurrentCatalog(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCurrentCatalog(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GroupingOperationContext extends PrimaryExpressionContext {
		public TerminalNode GROUPING() { return getToken(StructuresSqlParserParser.GROUPING, 0); }
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public GroupingOperationContext(PrimaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterGroupingOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitGroupingOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitGroupingOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryExpressionContext primaryExpression() throws RecognitionException {
		return primaryExpression(0);
	}

	private PrimaryExpressionContext primaryExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PrimaryExpressionContext _localctx = new PrimaryExpressionContext(_ctx, _parentState);
		PrimaryExpressionContext _prevctx = _localctx;
		int _startState = 134;
		enterRecursionRule(_localctx, 134, RULE_primaryExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1811);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,231,_ctx) ) {
			case 1:
				{
				_localctx = new NullLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1359);
				match(NULL);
				}
				break;
			case 2:
				{
				_localctx = new IntervalLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1360);
				interval();
				}
				break;
			case 3:
				{
				_localctx = new TypeConstructorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1361);
				identifier();
				setState(1362);
				string();
				}
				break;
			case 4:
				{
				_localctx = new TypeConstructorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1364);
				match(DOUBLE);
				setState(1365);
				match(PRECISION);
				setState(1366);
				string();
				}
				break;
			case 5:
				{
				_localctx = new NumericLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1367);
				number();
				}
				break;
			case 6:
				{
				_localctx = new BooleanLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1368);
				booleanValue();
				}
				break;
			case 7:
				{
				_localctx = new StringLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1369);
				string();
				}
				break;
			case 8:
				{
				_localctx = new BinaryLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1370);
				match(BINARY_LITERAL);
				}
				break;
			case 9:
				{
				_localctx = new ParameterContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1371);
				match(QUESTION_MARK);
				}
				break;
			case 10:
				{
				_localctx = new PositionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1372);
				match(POSITION);
				setState(1373);
				match(T__2);
				setState(1374);
				valueExpression(0);
				setState(1375);
				match(IN);
				setState(1376);
				valueExpression(0);
				setState(1377);
				match(T__3);
				}
				break;
			case 11:
				{
				_localctx = new RowConstructorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1379);
				match(T__2);
				setState(1380);
				expression();
				setState(1383); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(1381);
					match(T__1);
					setState(1382);
					expression();
					}
					}
					setState(1385); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__1 );
				setState(1387);
				match(T__3);
				}
				break;
			case 12:
				{
				_localctx = new RowConstructorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1389);
				match(ROW);
				setState(1390);
				match(T__2);
				setState(1391);
				expression();
				setState(1396);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(1392);
					match(T__1);
					setState(1393);
					expression();
					}
					}
					setState(1398);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1399);
				match(T__3);
				}
				break;
			case 13:
				{
				_localctx = new ListaggContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1401);
				((ListaggContext)_localctx).name = match(LISTAGG);
				setState(1402);
				match(T__2);
				setState(1404);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,170,_ctx) ) {
				case 1:
					{
					setState(1403);
					setQuantifier();
					}
					break;
				}
				setState(1406);
				expression();
				setState(1409);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(1407);
					match(T__1);
					setState(1408);
					string();
					}
				}

				setState(1414);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ON) {
					{
					setState(1411);
					match(ON);
					setState(1412);
					match(OVERFLOW);
					setState(1413);
					listAggOverflowBehavior();
					}
				}

				setState(1416);
				match(T__3);
				{
				setState(1417);
				match(WITHIN);
				setState(1418);
				match(GROUP);
				setState(1419);
				match(T__2);
				setState(1420);
				match(ORDER);
				setState(1421);
				match(T__18);
				setState(1422);
				sortItem();
				setState(1427);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(1423);
					match(T__1);
					setState(1424);
					sortItem();
					}
					}
					setState(1429);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1430);
				match(T__3);
				}
				setState(1433);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,174,_ctx) ) {
				case 1:
					{
					setState(1432);
					filter();
					}
					break;
				}
				}
				break;
			case 14:
				{
				_localctx = new FunctionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1436);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,175,_ctx) ) {
				case 1:
					{
					setState(1435);
					processingMode();
					}
					break;
				}
				setState(1438);
				qualifiedName();
				setState(1439);
				match(T__2);
				setState(1443);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -5262737029699602754L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & -9120583187364427405L) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & -6228115030305409L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 9149062092676919263L) != 0) || ((((_la - 257)) & ~0x3f) == 0 && ((1L << (_la - 257)) & 4323455915874252663L) != 0)) {
					{
					setState(1440);
					((FunctionCallContext)_localctx).label = identifier();
					setState(1441);
					match(T__0);
					}
				}

				setState(1445);
				match(ASTERISK);
				setState(1446);
				match(T__3);
				setState(1448);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,177,_ctx) ) {
				case 1:
					{
					setState(1447);
					filter();
					}
					break;
				}
				setState(1451);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,178,_ctx) ) {
				case 1:
					{
					setState(1450);
					over();
					}
					break;
				}
				}
				break;
			case 15:
				{
				_localctx = new FunctionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1454);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,179,_ctx) ) {
				case 1:
					{
					setState(1453);
					processingMode();
					}
					break;
				}
				setState(1456);
				qualifiedName();
				setState(1457);
				match(T__2);
				setState(1469);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,182,_ctx) ) {
				case 1:
					{
					setState(1459);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,180,_ctx) ) {
					case 1:
						{
						setState(1458);
						setQuantifier();
						}
						break;
					}
					setState(1461);
					expression();
					setState(1466);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(1462);
						match(T__1);
						setState(1463);
						expression();
						}
						}
						setState(1468);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					break;
				}
				setState(1481);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ORDER) {
					{
					setState(1471);
					match(ORDER);
					setState(1472);
					match(T__18);
					setState(1473);
					sortItem();
					setState(1478);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(1474);
						match(T__1);
						setState(1475);
						sortItem();
						}
						}
						setState(1480);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(1483);
				match(T__3);
				setState(1485);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,185,_ctx) ) {
				case 1:
					{
					setState(1484);
					filter();
					}
					break;
				}
				setState(1491);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,187,_ctx) ) {
				case 1:
					{
					setState(1488);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==IGNORE || _la==RESPECT) {
						{
						setState(1487);
						nullTreatment();
						}
					}

					setState(1490);
					over();
					}
					break;
				}
				}
				break;
			case 16:
				{
				_localctx = new MeasureContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1493);
				identifier();
				setState(1494);
				over();
				}
				break;
			case 17:
				{
				_localctx = new LambdaContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1496);
				identifier();
				setState(1497);
				match(T__6);
				setState(1498);
				expression();
				}
				break;
			case 18:
				{
				_localctx = new LambdaContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1500);
				match(T__2);
				setState(1509);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,189,_ctx) ) {
				case 1:
					{
					setState(1501);
					identifier();
					setState(1506);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(1502);
						match(T__1);
						setState(1503);
						identifier();
						}
						}
						setState(1508);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					break;
				}
				setState(1511);
				match(T__3);
				setState(1512);
				match(T__6);
				setState(1513);
				expression();
				}
				break;
			case 19:
				{
				_localctx = new SubqueryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1514);
				match(T__2);
				setState(1515);
				query();
				setState(1516);
				match(T__3);
				}
				break;
			case 20:
				{
				_localctx = new ExistsContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1518);
				match(EXISTS);
				setState(1519);
				match(T__2);
				setState(1520);
				query();
				setState(1521);
				match(T__3);
				}
				break;
			case 21:
				{
				_localctx = new SimpleCaseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1523);
				match(CASE);
				setState(1524);
				((SimpleCaseContext)_localctx).operand = expression();
				setState(1526); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(1525);
					whenClause();
					}
					}
					setState(1528); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==WHEN );
				setState(1532);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(1530);
					match(ELSE);
					setState(1531);
					((SimpleCaseContext)_localctx).elseExpression = expression();
					}
				}

				setState(1534);
				match(END);
				}
				break;
			case 22:
				{
				_localctx = new SearchedCaseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1536);
				match(CASE);
				setState(1538); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(1537);
					whenClause();
					}
					}
					setState(1540); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==WHEN );
				setState(1544);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(1542);
					match(ELSE);
					setState(1543);
					((SearchedCaseContext)_localctx).elseExpression = expression();
					}
				}

				setState(1546);
				match(END);
				}
				break;
			case 23:
				{
				_localctx = new CastContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1548);
				match(CAST);
				setState(1549);
				match(T__2);
				setState(1550);
				expression();
				setState(1551);
				match(T__10);
				setState(1552);
				type(0);
				setState(1553);
				match(T__3);
				}
				break;
			case 24:
				{
				_localctx = new CastContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1555);
				match(TRY_CAST);
				setState(1556);
				match(T__2);
				setState(1557);
				expression();
				setState(1558);
				match(T__10);
				setState(1559);
				type(0);
				setState(1560);
				match(T__3);
				}
				break;
			case 25:
				{
				_localctx = new ArrayConstructorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1562);
				match(T__9);
				setState(1563);
				match(T__7);
				setState(1572);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,195,_ctx) ) {
				case 1:
					{
					setState(1564);
					expression();
					setState(1569);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(1565);
						match(T__1);
						setState(1566);
						expression();
						}
						}
						setState(1571);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					break;
				}
				setState(1574);
				match(T__8);
				}
				break;
			case 26:
				{
				_localctx = new ColumnReferenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1575);
				identifier();
				}
				break;
			case 27:
				{
				_localctx = new CurrentDateContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1576);
				((CurrentDateContext)_localctx).name = match(CURRENT_DATE);
				}
				break;
			case 28:
				{
				_localctx = new CurrentTimeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1577);
				((CurrentTimeContext)_localctx).name = match(CURRENT_TIME);
				setState(1581);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,196,_ctx) ) {
				case 1:
					{
					setState(1578);
					match(T__2);
					setState(1579);
					((CurrentTimeContext)_localctx).precision = match(INTEGER_VALUE);
					setState(1580);
					match(T__3);
					}
					break;
				}
				}
				break;
			case 29:
				{
				_localctx = new CurrentTimestampContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1583);
				((CurrentTimestampContext)_localctx).name = match(CURRENT_TIMESTAMP);
				setState(1587);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,197,_ctx) ) {
				case 1:
					{
					setState(1584);
					match(T__2);
					setState(1585);
					((CurrentTimestampContext)_localctx).precision = match(INTEGER_VALUE);
					setState(1586);
					match(T__3);
					}
					break;
				}
				}
				break;
			case 30:
				{
				_localctx = new LocalTimeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1589);
				((LocalTimeContext)_localctx).name = match(LOCALTIME);
				setState(1593);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,198,_ctx) ) {
				case 1:
					{
					setState(1590);
					match(T__2);
					setState(1591);
					((LocalTimeContext)_localctx).precision = match(INTEGER_VALUE);
					setState(1592);
					match(T__3);
					}
					break;
				}
				}
				break;
			case 31:
				{
				_localctx = new LocalTimestampContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1595);
				((LocalTimestampContext)_localctx).name = match(LOCALTIMESTAMP);
				setState(1599);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,199,_ctx) ) {
				case 1:
					{
					setState(1596);
					match(T__2);
					setState(1597);
					((LocalTimestampContext)_localctx).precision = match(INTEGER_VALUE);
					setState(1598);
					match(T__3);
					}
					break;
				}
				}
				break;
			case 32:
				{
				_localctx = new CurrentUserContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1601);
				((CurrentUserContext)_localctx).name = match(CURRENT_USER);
				}
				break;
			case 33:
				{
				_localctx = new CurrentCatalogContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1602);
				((CurrentCatalogContext)_localctx).name = match(CURRENT_CATALOG);
				}
				break;
			case 34:
				{
				_localctx = new CurrentSchemaContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1603);
				((CurrentSchemaContext)_localctx).name = match(CURRENT_SCHEMA);
				}
				break;
			case 35:
				{
				_localctx = new CurrentPathContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1604);
				((CurrentPathContext)_localctx).name = match(CURRENT_PATH);
				}
				break;
			case 36:
				{
				_localctx = new TrimContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1605);
				match(TRIM);
				setState(1606);
				match(T__2);
				setState(1614);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,202,_ctx) ) {
				case 1:
					{
					setState(1608);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,200,_ctx) ) {
					case 1:
						{
						setState(1607);
						trimsSpecification();
						}
						break;
					}
					setState(1611);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -5262465450302376258L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & -2347169330619225741L) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & -6227771432895105L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & -74309944177856545L) != 0) || ((((_la - 256)) & ~0x3f) == 0 && ((1L << (_la - 256)) & 9216722737727139567L) != 0)) {
						{
						setState(1610);
						((TrimContext)_localctx).trimChar = valueExpression(0);
						}
					}

					setState(1613);
					match(FROM);
					}
					break;
				}
				setState(1616);
				((TrimContext)_localctx).trimSource = valueExpression(0);
				setState(1617);
				match(T__3);
				}
				break;
			case 37:
				{
				_localctx = new TrimContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1619);
				match(TRIM);
				setState(1620);
				match(T__2);
				setState(1621);
				((TrimContext)_localctx).trimSource = valueExpression(0);
				setState(1622);
				match(T__1);
				setState(1623);
				((TrimContext)_localctx).trimChar = valueExpression(0);
				setState(1624);
				match(T__3);
				}
				break;
			case 38:
				{
				_localctx = new SubstringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1626);
				match(SUBSTRING);
				setState(1627);
				match(T__2);
				setState(1628);
				valueExpression(0);
				setState(1629);
				match(FROM);
				setState(1630);
				valueExpression(0);
				setState(1633);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FOR) {
					{
					setState(1631);
					match(FOR);
					setState(1632);
					valueExpression(0);
					}
				}

				setState(1635);
				match(T__3);
				}
				break;
			case 39:
				{
				_localctx = new NormalizeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1637);
				match(NORMALIZE);
				setState(1638);
				match(T__2);
				setState(1639);
				valueExpression(0);
				setState(1642);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(1640);
					match(T__1);
					setState(1641);
					normalForm();
					}
				}

				setState(1644);
				match(T__3);
				}
				break;
			case 40:
				{
				_localctx = new ExtractContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1646);
				match(EXTRACT);
				setState(1647);
				match(T__2);
				setState(1648);
				identifier();
				setState(1649);
				match(FROM);
				setState(1650);
				valueExpression(0);
				setState(1651);
				match(T__3);
				}
				break;
			case 41:
				{
				_localctx = new ParenthesizedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1653);
				match(T__2);
				setState(1654);
				expression();
				setState(1655);
				match(T__3);
				}
				break;
			case 42:
				{
				_localctx = new GroupingOperationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1657);
				match(GROUPING);
				setState(1658);
				match(T__2);
				setState(1667);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,206,_ctx) ) {
				case 1:
					{
					setState(1659);
					qualifiedName();
					setState(1664);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(1660);
						match(T__1);
						setState(1661);
						qualifiedName();
						}
						}
						setState(1666);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					break;
				}
				setState(1669);
				match(T__3);
				}
				break;
			case 43:
				{
				_localctx = new JsonExistsContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1670);
				match(JSON_EXISTS);
				setState(1671);
				match(T__2);
				setState(1672);
				jsonPathInvocation();
				setState(1677);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ERROR || _la==FALSE || _la==TRUE || _la==UNKNOWN) {
					{
					setState(1673);
					jsonExistsErrorBehavior();
					setState(1674);
					match(ON);
					setState(1675);
					match(ERROR);
					}
				}

				setState(1679);
				match(T__3);
				}
				break;
			case 44:
				{
				_localctx = new JsonValueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1681);
				match(JSON_VALUE);
				setState(1682);
				match(T__2);
				setState(1683);
				jsonPathInvocation();
				setState(1686);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==RETURNING) {
					{
					setState(1684);
					match(RETURNING);
					setState(1685);
					type(0);
					}
				}

				setState(1692);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,209,_ctx) ) {
				case 1:
					{
					setState(1688);
					((JsonValueContext)_localctx).emptyBehavior = jsonValueBehavior();
					setState(1689);
					match(ON);
					setState(1690);
					match(EMPTY);
					}
					break;
				}
				setState(1698);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DEFAULT || _la==ERROR || _la==NULL) {
					{
					setState(1694);
					((JsonValueContext)_localctx).errorBehavior = jsonValueBehavior();
					setState(1695);
					match(ON);
					setState(1696);
					match(ERROR);
					}
				}

				setState(1700);
				match(T__3);
				}
				break;
			case 45:
				{
				_localctx = new JsonQueryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1702);
				match(JSON_QUERY);
				setState(1703);
				match(T__2);
				setState(1704);
				jsonPathInvocation();
				setState(1711);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==RETURNING) {
					{
					setState(1705);
					match(RETURNING);
					setState(1706);
					type(0);
					setState(1709);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==FORMAT) {
						{
						setState(1707);
						match(FORMAT);
						setState(1708);
						jsonRepresentation();
						}
					}

					}
				}

				setState(1716);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WITH || _la==WITHOUT) {
					{
					setState(1713);
					jsonQueryWrapperBehavior();
					setState(1714);
					match(WRAPPER);
					}
				}

				setState(1725);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==KEEP || _la==OMIT) {
					{
					setState(1718);
					_la = _input.LA(1);
					if ( !(_la==KEEP || _la==OMIT) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(1719);
					match(QUOTES);
					setState(1723);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==ON) {
						{
						setState(1720);
						match(ON);
						setState(1721);
						match(SCALAR);
						setState(1722);
						match(TEXT_STRING);
						}
					}

					}
				}

				setState(1731);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,216,_ctx) ) {
				case 1:
					{
					setState(1727);
					((JsonQueryContext)_localctx).emptyBehavior = jsonQueryBehavior();
					setState(1728);
					match(ON);
					setState(1729);
					match(EMPTY);
					}
					break;
				}
				setState(1737);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EMPTY || _la==ERROR || _la==NULL) {
					{
					setState(1733);
					((JsonQueryContext)_localctx).errorBehavior = jsonQueryBehavior();
					setState(1734);
					match(ON);
					setState(1735);
					match(ERROR);
					}
				}

				setState(1739);
				match(T__3);
				}
				break;
			case 46:
				{
				_localctx = new JsonObjectContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1741);
				match(JSON_OBJECT);
				setState(1742);
				match(T__2);
				setState(1771);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,223,_ctx) ) {
				case 1:
					{
					setState(1743);
					jsonObjectMember();
					setState(1748);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(1744);
						match(T__1);
						setState(1745);
						jsonObjectMember();
						}
						}
						setState(1750);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(1757);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case NULL:
						{
						setState(1751);
						match(NULL);
						setState(1752);
						match(ON);
						setState(1753);
						match(NULL);
						}
						break;
					case T__0:
						{
						setState(1754);
						match(T__0);
						setState(1755);
						match(ON);
						setState(1756);
						match(NULL);
						}
						break;
					case T__3:
					case RETURNING:
					case WITH:
					case WITHOUT:
						break;
					default:
						break;
					}
					setState(1769);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case WITH:
						{
						setState(1759);
						match(WITH);
						setState(1760);
						match(UNIQUE);
						setState(1762);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==KEYS) {
							{
							setState(1761);
							match(KEYS);
							}
						}

						}
						break;
					case WITHOUT:
						{
						setState(1764);
						match(WITHOUT);
						setState(1765);
						match(UNIQUE);
						setState(1767);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==KEYS) {
							{
							setState(1766);
							match(KEYS);
							}
						}

						}
						break;
					case T__3:
					case RETURNING:
						break;
					default:
						break;
					}
					}
					break;
				}
				setState(1779);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==RETURNING) {
					{
					setState(1773);
					match(RETURNING);
					setState(1774);
					type(0);
					setState(1777);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==FORMAT) {
						{
						setState(1775);
						match(FORMAT);
						setState(1776);
						jsonRepresentation();
						}
					}

					}
				}

				setState(1781);
				match(T__3);
				}
				break;
			case 47:
				{
				_localctx = new JsonArrayContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1782);
				match(JSON_ARRAY);
				setState(1783);
				match(T__2);
				setState(1800);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,228,_ctx) ) {
				case 1:
					{
					setState(1784);
					jsonValueExpression();
					setState(1789);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(1785);
						match(T__1);
						setState(1786);
						jsonValueExpression();
						}
						}
						setState(1791);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(1798);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case NULL:
						{
						setState(1792);
						match(NULL);
						setState(1793);
						match(ON);
						setState(1794);
						match(NULL);
						}
						break;
					case T__0:
						{
						setState(1795);
						match(T__0);
						setState(1796);
						match(ON);
						setState(1797);
						match(NULL);
						}
						break;
					case T__3:
					case RETURNING:
						break;
					default:
						break;
					}
					}
					break;
				}
				setState(1808);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==RETURNING) {
					{
					setState(1802);
					match(RETURNING);
					setState(1803);
					type(0);
					setState(1806);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==FORMAT) {
						{
						setState(1804);
						match(FORMAT);
						setState(1805);
						jsonRepresentation();
						}
					}

					}
				}

				setState(1810);
				match(T__3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1823);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,233,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1821);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,232,_ctx) ) {
					case 1:
						{
						_localctx = new SubscriptContext(new PrimaryExpressionContext(_parentctx, _parentState));
						((SubscriptContext)_localctx).value = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_primaryExpression);
						setState(1813);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(1814);
						match(T__7);
						setState(1815);
						((SubscriptContext)_localctx).index = valueExpression(0);
						setState(1816);
						match(T__8);
						}
						break;
					case 2:
						{
						_localctx = new DereferenceContext(new PrimaryExpressionContext(_parentctx, _parentState));
						((DereferenceContext)_localctx).base = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_primaryExpression);
						setState(1818);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(1819);
						match(T__0);
						setState(1820);
						((DereferenceContext)_localctx).fieldName = identifier();
						}
						break;
					}
					} 
				}
				setState(1825);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,233,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonPathInvocationContext extends ParserRuleContext {
		public StringContext path;
		public IdentifierContext pathName;
		public JsonValueExpressionContext jsonValueExpression() {
			return getRuleContext(JsonValueExpressionContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public TerminalNode PASSING() { return getToken(StructuresSqlParserParser.PASSING, 0); }
		public List<JsonArgumentContext> jsonArgument() {
			return getRuleContexts(JsonArgumentContext.class);
		}
		public JsonArgumentContext jsonArgument(int i) {
			return getRuleContext(JsonArgumentContext.class,i);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public JsonPathInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonPathInvocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonPathInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonPathInvocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonPathInvocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonPathInvocationContext jsonPathInvocation() throws RecognitionException {
		JsonPathInvocationContext _localctx = new JsonPathInvocationContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_jsonPathInvocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1826);
			jsonValueExpression();
			setState(1827);
			match(T__1);
			setState(1828);
			((JsonPathInvocationContext)_localctx).path = string();
			setState(1831);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(1829);
				match(T__10);
				setState(1830);
				((JsonPathInvocationContext)_localctx).pathName = identifier();
				}
			}

			setState(1842);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PASSING) {
				{
				setState(1833);
				match(PASSING);
				setState(1834);
				jsonArgument();
				setState(1839);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(1835);
					match(T__1);
					setState(1836);
					jsonArgument();
					}
					}
					setState(1841);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonValueExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode FORMAT() { return getToken(StructuresSqlParserParser.FORMAT, 0); }
		public JsonRepresentationContext jsonRepresentation() {
			return getRuleContext(JsonRepresentationContext.class,0);
		}
		public JsonValueExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonValueExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonValueExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonValueExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonValueExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonValueExpressionContext jsonValueExpression() throws RecognitionException {
		JsonValueExpressionContext _localctx = new JsonValueExpressionContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_jsonValueExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1844);
			expression();
			setState(1847);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FORMAT) {
				{
				setState(1845);
				match(FORMAT);
				setState(1846);
				jsonRepresentation();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonRepresentationContext extends ParserRuleContext {
		public TerminalNode JSON() { return getToken(StructuresSqlParserParser.JSON, 0); }
		public TerminalNode ENCODING() { return getToken(StructuresSqlParserParser.ENCODING, 0); }
		public TerminalNode UTF8() { return getToken(StructuresSqlParserParser.UTF8, 0); }
		public TerminalNode UTF16() { return getToken(StructuresSqlParserParser.UTF16, 0); }
		public TerminalNode UTF32() { return getToken(StructuresSqlParserParser.UTF32, 0); }
		public JsonRepresentationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonRepresentation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonRepresentation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonRepresentation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonRepresentation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonRepresentationContext jsonRepresentation() throws RecognitionException {
		JsonRepresentationContext _localctx = new JsonRepresentationContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_jsonRepresentation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1849);
			match(JSON);
			setState(1852);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ENCODING) {
				{
				setState(1850);
				match(ENCODING);
				setState(1851);
				_la = _input.LA(1);
				if ( !(((((_la - 274)) & ~0x3f) == 0 && ((1L << (_la - 274)) & 7L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonArgumentContext extends ParserRuleContext {
		public JsonValueExpressionContext jsonValueExpression() {
			return getRuleContext(JsonValueExpressionContext.class,0);
		}
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public JsonArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonArgumentContext jsonArgument() throws RecognitionException {
		JsonArgumentContext _localctx = new JsonArgumentContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_jsonArgument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1854);
			jsonValueExpression();
			setState(1855);
			match(T__10);
			setState(1856);
			identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonExistsErrorBehaviorContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(StructuresSqlParserParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(StructuresSqlParserParser.FALSE, 0); }
		public TerminalNode UNKNOWN() { return getToken(StructuresSqlParserParser.UNKNOWN, 0); }
		public TerminalNode ERROR() { return getToken(StructuresSqlParserParser.ERROR, 0); }
		public JsonExistsErrorBehaviorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonExistsErrorBehavior; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonExistsErrorBehavior(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonExistsErrorBehavior(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonExistsErrorBehavior(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonExistsErrorBehaviorContext jsonExistsErrorBehavior() throws RecognitionException {
		JsonExistsErrorBehaviorContext _localctx = new JsonExistsErrorBehaviorContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_jsonExistsErrorBehavior);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1858);
			_la = _input.LA(1);
			if ( !(_la==ERROR || _la==FALSE || _la==TRUE || _la==UNKNOWN) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonValueBehaviorContext extends ParserRuleContext {
		public TerminalNode ERROR() { return getToken(StructuresSqlParserParser.ERROR, 0); }
		public TerminalNode NULL() { return getToken(StructuresSqlParserParser.NULL, 0); }
		public TerminalNode DEFAULT() { return getToken(StructuresSqlParserParser.DEFAULT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public JsonValueBehaviorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonValueBehavior; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonValueBehavior(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonValueBehavior(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonValueBehavior(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonValueBehaviorContext jsonValueBehavior() throws RecognitionException {
		JsonValueBehaviorContext _localctx = new JsonValueBehaviorContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_jsonValueBehavior);
		try {
			setState(1864);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ERROR:
				enterOuterAlt(_localctx, 1);
				{
				setState(1860);
				match(ERROR);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 2);
				{
				setState(1861);
				match(NULL);
				}
				break;
			case DEFAULT:
				enterOuterAlt(_localctx, 3);
				{
				setState(1862);
				match(DEFAULT);
				setState(1863);
				expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonQueryWrapperBehaviorContext extends ParserRuleContext {
		public TerminalNode WITHOUT() { return getToken(StructuresSqlParserParser.WITHOUT, 0); }
		public TerminalNode ARRAY() { return getToken(StructuresSqlParserParser.ARRAY, 0); }
		public TerminalNode WITH() { return getToken(StructuresSqlParserParser.WITH, 0); }
		public TerminalNode CONDITIONAL() { return getToken(StructuresSqlParserParser.CONDITIONAL, 0); }
		public TerminalNode UNCONDITIONAL() { return getToken(StructuresSqlParserParser.UNCONDITIONAL, 0); }
		public JsonQueryWrapperBehaviorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonQueryWrapperBehavior; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonQueryWrapperBehavior(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonQueryWrapperBehavior(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonQueryWrapperBehavior(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonQueryWrapperBehaviorContext jsonQueryWrapperBehavior() throws RecognitionException {
		JsonQueryWrapperBehaviorContext _localctx = new JsonQueryWrapperBehaviorContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_jsonQueryWrapperBehavior);
		int _la;
		try {
			setState(1877);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WITHOUT:
				enterOuterAlt(_localctx, 1);
				{
				setState(1866);
				match(WITHOUT);
				setState(1868);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9) {
					{
					setState(1867);
					match(T__9);
					}
				}

				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 2);
				{
				setState(1870);
				match(WITH);
				setState(1872);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CONDITIONAL || _la==UNCONDITIONAL) {
					{
					setState(1871);
					_la = _input.LA(1);
					if ( !(_la==CONDITIONAL || _la==UNCONDITIONAL) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(1875);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9) {
					{
					setState(1874);
					match(T__9);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonQueryBehaviorContext extends ParserRuleContext {
		public TerminalNode ERROR() { return getToken(StructuresSqlParserParser.ERROR, 0); }
		public TerminalNode NULL() { return getToken(StructuresSqlParserParser.NULL, 0); }
		public TerminalNode EMPTY() { return getToken(StructuresSqlParserParser.EMPTY, 0); }
		public TerminalNode ARRAY() { return getToken(StructuresSqlParserParser.ARRAY, 0); }
		public TerminalNode OBJECT() { return getToken(StructuresSqlParserParser.OBJECT, 0); }
		public JsonQueryBehaviorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonQueryBehavior; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonQueryBehavior(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonQueryBehavior(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonQueryBehavior(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonQueryBehaviorContext jsonQueryBehavior() throws RecognitionException {
		JsonQueryBehaviorContext _localctx = new JsonQueryBehaviorContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_jsonQueryBehavior);
		try {
			setState(1885);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,244,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1879);
				match(ERROR);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1880);
				match(NULL);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1881);
				match(EMPTY);
				setState(1882);
				match(T__9);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1883);
				match(EMPTY);
				setState(1884);
				match(OBJECT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonObjectMemberContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode VALUE() { return getToken(StructuresSqlParserParser.VALUE, 0); }
		public JsonValueExpressionContext jsonValueExpression() {
			return getRuleContext(JsonValueExpressionContext.class,0);
		}
		public TerminalNode KEY() { return getToken(StructuresSqlParserParser.KEY, 0); }
		public JsonObjectMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonObjectMember; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterJsonObjectMember(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitJsonObjectMember(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitJsonObjectMember(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonObjectMemberContext jsonObjectMember() throws RecognitionException {
		JsonObjectMemberContext _localctx = new JsonObjectMemberContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_jsonObjectMember);
		try {
			setState(1898);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,246,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1888);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,245,_ctx) ) {
				case 1:
					{
					setState(1887);
					match(KEY);
					}
					break;
				}
				setState(1890);
				expression();
				setState(1891);
				match(VALUE);
				setState(1892);
				jsonValueExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1894);
				expression();
				setState(1895);
				match(T__9);
				setState(1896);
				jsonValueExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProcessingModeContext extends ParserRuleContext {
		public TerminalNode RUNNING() { return getToken(StructuresSqlParserParser.RUNNING, 0); }
		public TerminalNode FINAL() { return getToken(StructuresSqlParserParser.FINAL, 0); }
		public ProcessingModeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_processingMode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterProcessingMode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitProcessingMode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitProcessingMode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcessingModeContext processingMode() throws RecognitionException {
		ProcessingModeContext _localctx = new ProcessingModeContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_processingMode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1900);
			_la = _input.LA(1);
			if ( !(_la==FINAL || _la==RUNNING) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NullTreatmentContext extends ParserRuleContext {
		public TerminalNode IGNORE() { return getToken(StructuresSqlParserParser.IGNORE, 0); }
		public TerminalNode NULLS() { return getToken(StructuresSqlParserParser.NULLS, 0); }
		public TerminalNode RESPECT() { return getToken(StructuresSqlParserParser.RESPECT, 0); }
		public NullTreatmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nullTreatment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterNullTreatment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitNullTreatment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitNullTreatment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NullTreatmentContext nullTreatment() throws RecognitionException {
		NullTreatmentContext _localctx = new NullTreatmentContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_nullTreatment);
		try {
			setState(1906);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IGNORE:
				enterOuterAlt(_localctx, 1);
				{
				setState(1902);
				match(IGNORE);
				setState(1903);
				match(NULLS);
				}
				break;
			case RESPECT:
				enterOuterAlt(_localctx, 2);
				{
				setState(1904);
				match(RESPECT);
				setState(1905);
				match(NULLS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringContext extends ParserRuleContext {
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
	 
		public StringContext() { }
		public void copyFrom(StringContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnicodeStringLiteralContext extends StringContext {
		public TerminalNode UNICODE_STRING() { return getToken(StructuresSqlParserParser.UNICODE_STRING, 0); }
		public TerminalNode UESCAPE() { return getToken(StructuresSqlParserParser.UESCAPE, 0); }
		public TerminalNode STRING() { return getToken(StructuresSqlParserParser.STRING, 0); }
		public UnicodeStringLiteralContext(StringContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterUnicodeStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitUnicodeStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitUnicodeStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BasicStringLiteralContext extends StringContext {
		public TerminalNode STRING() { return getToken(StructuresSqlParserParser.STRING, 0); }
		public BasicStringLiteralContext(StringContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterBasicStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitBasicStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitBasicStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_string);
		try {
			setState(1914);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING:
				_localctx = new BasicStringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1908);
				match(STRING);
				}
				break;
			case UNICODE_STRING:
				_localctx = new UnicodeStringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1909);
				match(UNICODE_STRING);
				setState(1912);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,248,_ctx) ) {
				case 1:
					{
					setState(1910);
					match(UESCAPE);
					setState(1911);
					match(STRING);
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TimeZoneSpecifierContext extends ParserRuleContext {
		public TimeZoneSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timeZoneSpecifier; }
	 
		public TimeZoneSpecifierContext() { }
		public void copyFrom(TimeZoneSpecifierContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TimeZoneIntervalContext extends TimeZoneSpecifierContext {
		public TerminalNode TIME() { return getToken(StructuresSqlParserParser.TIME, 0); }
		public TerminalNode ZONE() { return getToken(StructuresSqlParserParser.ZONE, 0); }
		public IntervalContext interval() {
			return getRuleContext(IntervalContext.class,0);
		}
		public TimeZoneIntervalContext(TimeZoneSpecifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTimeZoneInterval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTimeZoneInterval(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTimeZoneInterval(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TimeZoneStringContext extends TimeZoneSpecifierContext {
		public TerminalNode TIME() { return getToken(StructuresSqlParserParser.TIME, 0); }
		public TerminalNode ZONE() { return getToken(StructuresSqlParserParser.ZONE, 0); }
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TimeZoneStringContext(TimeZoneSpecifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTimeZoneString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTimeZoneString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTimeZoneString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TimeZoneSpecifierContext timeZoneSpecifier() throws RecognitionException {
		TimeZoneSpecifierContext _localctx = new TimeZoneSpecifierContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_timeZoneSpecifier);
		try {
			setState(1922);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,250,_ctx) ) {
			case 1:
				_localctx = new TimeZoneIntervalContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1916);
				match(TIME);
				setState(1917);
				match(ZONE);
				setState(1918);
				interval();
				}
				break;
			case 2:
				_localctx = new TimeZoneStringContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1919);
				match(TIME);
				setState(1920);
				match(ZONE);
				setState(1921);
				string();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonOperatorContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(StructuresSqlParserParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(StructuresSqlParserParser.NEQ, 0); }
		public TerminalNode LT() { return getToken(StructuresSqlParserParser.LT, 0); }
		public TerminalNode LTE() { return getToken(StructuresSqlParserParser.LTE, 0); }
		public TerminalNode GT() { return getToken(StructuresSqlParserParser.GT, 0); }
		public TerminalNode GTE() { return getToken(StructuresSqlParserParser.GTE, 0); }
		public ComparisonOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterComparisonOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitComparisonOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitComparisonOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonOperatorContext comparisonOperator() throws RecognitionException {
		ComparisonOperatorContext _localctx = new ComparisonOperatorContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_comparisonOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1924);
			_la = _input.LA(1);
			if ( !(((((_la - 295)) & ~0x3f) == 0 && ((1L << (_la - 295)) & 63L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonQuantifierContext extends ParserRuleContext {
		public TerminalNode ALL() { return getToken(StructuresSqlParserParser.ALL, 0); }
		public TerminalNode SOME() { return getToken(StructuresSqlParserParser.SOME, 0); }
		public TerminalNode ANY() { return getToken(StructuresSqlParserParser.ANY, 0); }
		public ComparisonQuantifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonQuantifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterComparisonQuantifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitComparisonQuantifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitComparisonQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonQuantifierContext comparisonQuantifier() throws RecognitionException {
		ComparisonQuantifierContext _localctx = new ComparisonQuantifierContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_comparisonQuantifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1926);
			_la = _input.LA(1);
			if ( !(_la==T__4 || _la==T__8 || _la==SOME) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BooleanValueContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(StructuresSqlParserParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(StructuresSqlParserParser.FALSE, 0); }
		public BooleanValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterBooleanValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitBooleanValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitBooleanValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanValueContext booleanValue() throws RecognitionException {
		BooleanValueContext _localctx = new BooleanValueContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_booleanValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1928);
			_la = _input.LA(1);
			if ( !(_la==FALSE || _la==TRUE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IntervalContext extends ParserRuleContext {
		public Token sign;
		public IntervalFieldContext from;
		public IntervalFieldContext to;
		public TerminalNode INTERVAL() { return getToken(StructuresSqlParserParser.INTERVAL, 0); }
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public List<IntervalFieldContext> intervalField() {
			return getRuleContexts(IntervalFieldContext.class);
		}
		public IntervalFieldContext intervalField(int i) {
			return getRuleContext(IntervalFieldContext.class,i);
		}
		public TerminalNode TO() { return getToken(StructuresSqlParserParser.TO, 0); }
		public TerminalNode PLUS() { return getToken(StructuresSqlParserParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(StructuresSqlParserParser.MINUS, 0); }
		public IntervalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interval; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterInterval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitInterval(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitInterval(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntervalContext interval() throws RecognitionException {
		IntervalContext _localctx = new IntervalContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_interval);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1930);
			match(INTERVAL);
			setState(1932);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PLUS || _la==MINUS) {
				{
				setState(1931);
				((IntervalContext)_localctx).sign = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
					((IntervalContext)_localctx).sign = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(1934);
			string();
			setState(1935);
			((IntervalContext)_localctx).from = intervalField();
			setState(1938);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,252,_ctx) ) {
			case 1:
				{
				setState(1936);
				match(TO);
				setState(1937);
				((IntervalContext)_localctx).to = intervalField();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IntervalFieldContext extends ParserRuleContext {
		public TerminalNode YEAR() { return getToken(StructuresSqlParserParser.YEAR, 0); }
		public TerminalNode MONTH() { return getToken(StructuresSqlParserParser.MONTH, 0); }
		public TerminalNode DAY() { return getToken(StructuresSqlParserParser.DAY, 0); }
		public TerminalNode HOUR() { return getToken(StructuresSqlParserParser.HOUR, 0); }
		public TerminalNode MINUTE() { return getToken(StructuresSqlParserParser.MINUTE, 0); }
		public TerminalNode SECOND() { return getToken(StructuresSqlParserParser.SECOND, 0); }
		public IntervalFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intervalField; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterIntervalField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitIntervalField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitIntervalField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntervalFieldContext intervalField() throws RecognitionException {
		IntervalFieldContext _localctx = new IntervalFieldContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_intervalField);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1940);
			_la = _input.LA(1);
			if ( !(_la==DAY || ((((_la - 101)) & ~0x3f) == 0 && ((1L << (_la - 101)) & 13510798882111489L) != 0) || _la==SECOND || _la==YEAR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NormalFormContext extends ParserRuleContext {
		public TerminalNode NFD() { return getToken(StructuresSqlParserParser.NFD, 0); }
		public TerminalNode NFC() { return getToken(StructuresSqlParserParser.NFC, 0); }
		public TerminalNode NFKD() { return getToken(StructuresSqlParserParser.NFKD, 0); }
		public TerminalNode NFKC() { return getToken(StructuresSqlParserParser.NFKC, 0); }
		public NormalFormContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_normalForm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterNormalForm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitNormalForm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitNormalForm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NormalFormContext normalForm() throws RecognitionException {
		NormalFormContext _localctx = new NormalFormContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_normalForm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1942);
			_la = _input.LA(1);
			if ( !(((((_la - 158)) & ~0x3f) == 0 && ((1L << (_la - 158)) & 15L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RowTypeContext extends TypeContext {
		public TerminalNode ROW() { return getToken(StructuresSqlParserParser.ROW, 0); }
		public List<RowFieldContext> rowField() {
			return getRuleContexts(RowFieldContext.class);
		}
		public RowFieldContext rowField(int i) {
			return getRuleContext(RowFieldContext.class,i);
		}
		public RowTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRowType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRowType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRowType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntervalTypeContext extends TypeContext {
		public IntervalFieldContext from;
		public IntervalFieldContext to;
		public TerminalNode INTERVAL() { return getToken(StructuresSqlParserParser.INTERVAL, 0); }
		public List<IntervalFieldContext> intervalField() {
			return getRuleContexts(IntervalFieldContext.class);
		}
		public IntervalFieldContext intervalField(int i) {
			return getRuleContext(IntervalFieldContext.class,i);
		}
		public TerminalNode TO() { return getToken(StructuresSqlParserParser.TO, 0); }
		public IntervalTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterIntervalType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitIntervalType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitIntervalType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ARRAY() { return getToken(StructuresSqlParserParser.ARRAY, 0); }
		public TerminalNode INTEGER_VALUE() { return getToken(StructuresSqlParserParser.INTEGER_VALUE, 0); }
		public ArrayTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitArrayType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DoublePrecisionTypeContext extends TypeContext {
		public TerminalNode DOUBLE() { return getToken(StructuresSqlParserParser.DOUBLE, 0); }
		public TerminalNode PRECISION() { return getToken(StructuresSqlParserParser.PRECISION, 0); }
		public DoublePrecisionTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterDoublePrecisionType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitDoublePrecisionType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitDoublePrecisionType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LegacyArrayTypeContext extends TypeContext {
		public TerminalNode ARRAY() { return getToken(StructuresSqlParserParser.ARRAY, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public LegacyArrayTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLegacyArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLegacyArrayType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLegacyArrayType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GenericTypeContext extends TypeContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<TypeParameterContext> typeParameter() {
			return getRuleContexts(TypeParameterContext.class);
		}
		public TypeParameterContext typeParameter(int i) {
			return getRuleContext(TypeParameterContext.class,i);
		}
		public GenericTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterGenericType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitGenericType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitGenericType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DateTimeTypeContext extends TypeContext {
		public Token base;
		public TypeParameterContext precision;
		public TerminalNode TIMESTAMP() { return getToken(StructuresSqlParserParser.TIMESTAMP, 0); }
		public TerminalNode WITHOUT() { return getToken(StructuresSqlParserParser.WITHOUT, 0); }
		public List<TerminalNode> TIME() { return getTokens(StructuresSqlParserParser.TIME); }
		public TerminalNode TIME(int i) {
			return getToken(StructuresSqlParserParser.TIME, i);
		}
		public TerminalNode ZONE() { return getToken(StructuresSqlParserParser.ZONE, 0); }
		public TypeParameterContext typeParameter() {
			return getRuleContext(TypeParameterContext.class,0);
		}
		public TerminalNode WITH() { return getToken(StructuresSqlParserParser.WITH, 0); }
		public DateTimeTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterDateTimeType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitDateTimeType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitDateTimeType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LegacyMapTypeContext extends TypeContext {
		public TypeContext keyType;
		public TypeContext valueType;
		public TerminalNode MAP() { return getToken(StructuresSqlParserParser.MAP, 0); }
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public LegacyMapTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLegacyMapType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLegacyMapType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLegacyMapType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		return type(0);
	}

	private TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState);
		TypeContext _prevctx = _localctx;
		int _startState = 174;
		enterRecursionRule(_localctx, 174, RULE_type, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2035);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,263,_ctx) ) {
			case 1:
				{
				_localctx = new RowTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1945);
				match(ROW);
				setState(1946);
				match(T__2);
				setState(1947);
				rowField();
				setState(1952);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(1948);
					match(T__1);
					setState(1949);
					rowField();
					}
					}
					setState(1954);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1955);
				match(T__3);
				}
				break;
			case 2:
				{
				_localctx = new IntervalTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1957);
				match(INTERVAL);
				setState(1958);
				((IntervalTypeContext)_localctx).from = intervalField();
				setState(1961);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,254,_ctx) ) {
				case 1:
					{
					setState(1959);
					match(TO);
					setState(1960);
					((IntervalTypeContext)_localctx).to = intervalField();
					}
					break;
				}
				}
				break;
			case 3:
				{
				_localctx = new DateTimeTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1963);
				((DateTimeTypeContext)_localctx).base = match(TIMESTAMP);
				setState(1968);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,255,_ctx) ) {
				case 1:
					{
					setState(1964);
					match(T__2);
					setState(1965);
					((DateTimeTypeContext)_localctx).precision = typeParameter();
					setState(1966);
					match(T__3);
					}
					break;
				}
				setState(1973);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,256,_ctx) ) {
				case 1:
					{
					setState(1970);
					match(WITHOUT);
					setState(1971);
					match(TIME);
					setState(1972);
					match(ZONE);
					}
					break;
				}
				}
				break;
			case 4:
				{
				_localctx = new DateTimeTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1975);
				((DateTimeTypeContext)_localctx).base = match(TIMESTAMP);
				setState(1980);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(1976);
					match(T__2);
					setState(1977);
					((DateTimeTypeContext)_localctx).precision = typeParameter();
					setState(1978);
					match(T__3);
					}
				}

				setState(1982);
				match(WITH);
				setState(1983);
				match(TIME);
				setState(1984);
				match(ZONE);
				}
				break;
			case 5:
				{
				_localctx = new DateTimeTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1985);
				((DateTimeTypeContext)_localctx).base = match(TIME);
				setState(1990);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,258,_ctx) ) {
				case 1:
					{
					setState(1986);
					match(T__2);
					setState(1987);
					((DateTimeTypeContext)_localctx).precision = typeParameter();
					setState(1988);
					match(T__3);
					}
					break;
				}
				setState(1995);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,259,_ctx) ) {
				case 1:
					{
					setState(1992);
					match(WITHOUT);
					setState(1993);
					match(TIME);
					setState(1994);
					match(ZONE);
					}
					break;
				}
				}
				break;
			case 6:
				{
				_localctx = new DateTimeTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1997);
				((DateTimeTypeContext)_localctx).base = match(TIME);
				setState(2002);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(1998);
					match(T__2);
					setState(1999);
					((DateTimeTypeContext)_localctx).precision = typeParameter();
					setState(2000);
					match(T__3);
					}
				}

				setState(2004);
				match(WITH);
				setState(2005);
				match(TIME);
				setState(2006);
				match(ZONE);
				}
				break;
			case 7:
				{
				_localctx = new DoublePrecisionTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2007);
				match(DOUBLE);
				setState(2008);
				match(PRECISION);
				}
				break;
			case 8:
				{
				_localctx = new LegacyArrayTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2009);
				match(T__9);
				setState(2010);
				match(T__10);
				setState(2011);
				type(0);
				setState(2012);
				match(T__11);
				}
				break;
			case 9:
				{
				_localctx = new LegacyMapTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2014);
				match(MAP);
				setState(2015);
				match(T__10);
				setState(2016);
				((LegacyMapTypeContext)_localctx).keyType = type(0);
				setState(2017);
				match(T__1);
				setState(2018);
				((LegacyMapTypeContext)_localctx).valueType = type(0);
				setState(2019);
				match(T__11);
				}
				break;
			case 10:
				{
				_localctx = new GenericTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2021);
				identifier();
				setState(2033);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,262,_ctx) ) {
				case 1:
					{
					setState(2022);
					match(T__2);
					setState(2023);
					typeParameter();
					setState(2028);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(2024);
						match(T__1);
						setState(2025);
						typeParameter();
						}
						}
						setState(2030);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(2031);
					match(T__3);
					}
					break;
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(2046);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,265,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArrayTypeContext(new TypeContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_type);
					setState(2037);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(2038);
					match(T__9);
					setState(2042);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,264,_ctx) ) {
					case 1:
						{
						setState(2039);
						match(T__7);
						setState(2040);
						match(INTEGER_VALUE);
						setState(2041);
						match(T__8);
						}
						break;
					}
					}
					} 
				}
				setState(2048);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,265,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RowFieldContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public RowFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rowField; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRowField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRowField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRowField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RowFieldContext rowField() throws RecognitionException {
		RowFieldContext _localctx = new RowFieldContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_rowField);
		try {
			setState(2053);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,266,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2049);
				type(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2050);
				identifier();
				setState(2051);
				type(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeParameterContext extends ParserRuleContext {
		public TerminalNode INTEGER_VALUE() { return getToken(StructuresSqlParserParser.INTEGER_VALUE, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTypeParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTypeParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTypeParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParameterContext typeParameter() throws RecognitionException {
		TypeParameterContext _localctx = new TypeParameterContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_typeParameter);
		try {
			setState(2057);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER_VALUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(2055);
				match(INTEGER_VALUE);
				}
				break;
			case T__0:
			case T__1:
			case T__2:
			case T__3:
			case T__4:
			case T__6:
			case T__8:
			case T__9:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__17:
			case CALL:
			case CALLED:
			case CASCADE:
			case CATALOG:
			case CATALOGS:
			case COLUMN:
			case COLUMNS:
			case COMMENT:
			case COMMIT:
			case COMMITTED:
			case CONDITIONAL:
			case COUNT:
			case COPARTITION:
			case CURRENT:
			case DATA:
			case DATE:
			case DAY:
			case DECLARE:
			case DEFAULT:
			case DEFINE:
			case DEFINER:
			case DENY:
			case DESC:
			case DESCRIPTOR:
			case DETERMINISTIC:
			case DISTRIBUTED:
			case DO:
			case DOUBLE:
			case EMPTY:
			case ELSEIF:
			case ENCODING:
			case ERROR:
			case EXCLUDING:
			case EXPLAIN:
			case FETCH:
			case FILTER:
			case FINAL:
			case FIRST:
			case FOLLOWING:
			case FORMAT:
			case FUNCTION:
			case FUNCTIONS:
			case GRACE:
			case GRANT:
			case GRANTED:
			case GRANTS:
			case GRAPHVIZ:
			case GROUPS:
			case HOUR:
			case IF:
			case IGNORE:
			case IMMEDIATE:
			case INCLUDING:
			case INITIAL:
			case INPUT:
			case INTERVAL:
			case INVOKER:
			case IO:
			case ISOLATION:
			case ITERATE:
			case JSON:
			case KEEP:
			case KEY:
			case KEYS:
			case LANGUAGE:
			case LAST:
			case LATERAL:
			case LEADING:
			case LEAVE:
			case LEVEL:
			case LIMIT:
			case LOCAL:
			case LOGICAL:
			case LOOP:
			case MAP:
			case MATCH:
			case MATCHED:
			case MATCHES:
			case MATCH_RECOGNIZE:
			case MATERIALIZED:
			case MEASURES:
			case MERGE:
			case MINUTE:
			case MONTH:
			case NESTED:
			case NEXT:
			case NFC:
			case NFD:
			case NFKC:
			case NFKD:
			case NO:
			case NONE:
			case NULLIF:
			case NULLS:
			case OBJECT:
			case OF:
			case OFFSET:
			case OMIT:
			case ONE:
			case ONLY:
			case OPTION:
			case ORDINALITY:
			case OUTPUT:
			case OVER:
			case OVERFLOW:
			case PARTITION:
			case PARTITIONS:
			case PASSING:
			case PAST:
			case PATH:
			case PATTERN:
			case PER:
			case PERIOD:
			case PERMUTE:
			case PLAN:
			case POSITION:
			case PRECEDING:
			case PRECISION:
			case PRIVILEGES:
			case PROPERTIES:
			case PRUNE:
			case QUOTES:
			case RANGE:
			case READ:
			case REFRESH:
			case RENAME:
			case REPEAT:
			case REPEATABLE:
			case REPLACE:
			case RESET:
			case RESPECT:
			case RESTRICT:
			case RETURN:
			case RETURNING:
			case RETURNS:
			case REVOKE:
			case ROLE:
			case ROLES:
			case ROLLBACK:
			case ROW:
			case ROWS:
			case RUNNING:
			case SCALAR:
			case SCHEMA:
			case SCHEMAS:
			case SECOND:
			case SECURITY:
			case SEEK:
			case SERIALIZABLE:
			case SESSION:
			case SET:
			case SETS:
			case SHOW:
			case SOME:
			case START:
			case STATS:
			case SUBSET:
			case SUBSTRING:
			case SYSTEM:
			case TABLES:
			case TABLESAMPLE:
			case TEXT:
			case TEXT_STRING:
			case TIES:
			case TIME:
			case TIMESTAMP:
			case TO:
			case TRAILING:
			case TRANSACTION:
			case TRUNCATE:
			case TRY_CAST:
			case TYPE:
			case UNBOUNDED:
			case UNCOMMITTED:
			case UNCONDITIONAL:
			case UNIQUE:
			case UNKNOWN:
			case UNMATCHED:
			case UNTIL:
			case UPDATE:
			case USE:
			case USER:
			case UTF16:
			case UTF32:
			case UTF8:
			case VALIDATE:
			case VALUE:
			case VERBOSE:
			case VERSION:
			case VIEW:
			case WHILE:
			case WINDOW:
			case WITHIN:
			case WITHOUT:
			case WORK:
			case WRAPPER:
			case WRITE:
			case YEAR:
			case ZONE:
			case IDENTIFIER:
			case DIGIT_IDENTIFIER:
			case QUOTED_IDENTIFIER:
			case BACKQUOTED_IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(2056);
				type(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhenClauseContext extends ParserRuleContext {
		public ExpressionContext condition;
		public ExpressionContext result;
		public TerminalNode WHEN() { return getToken(StructuresSqlParserParser.WHEN, 0); }
		public TerminalNode THEN() { return getToken(StructuresSqlParserParser.THEN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public WhenClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterWhenClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitWhenClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitWhenClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenClauseContext whenClause() throws RecognitionException {
		WhenClauseContext _localctx = new WhenClauseContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_whenClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2059);
			match(WHEN);
			setState(2060);
			((WhenClauseContext)_localctx).condition = expression();
			setState(2061);
			match(THEN);
			setState(2062);
			((WhenClauseContext)_localctx).result = expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FilterContext extends ParserRuleContext {
		public TerminalNode FILTER() { return getToken(StructuresSqlParserParser.FILTER, 0); }
		public TerminalNode WHERE() { return getToken(StructuresSqlParserParser.WHERE, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public FilterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterFilter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitFilter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitFilter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FilterContext filter() throws RecognitionException {
		FilterContext _localctx = new FilterContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_filter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2064);
			match(FILTER);
			setState(2065);
			match(T__2);
			setState(2066);
			match(WHERE);
			setState(2067);
			booleanExpression(0);
			setState(2068);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MergeCaseContext extends ParserRuleContext {
		public MergeCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mergeCase; }
	 
		public MergeCaseContext() { }
		public void copyFrom(MergeCaseContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MergeInsertContext extends MergeCaseContext {
		public ExpressionContext condition;
		public IdentifierContext identifier;
		public List<IdentifierContext> targets = new ArrayList<IdentifierContext>();
		public ExpressionContext expression;
		public List<ExpressionContext> values = new ArrayList<ExpressionContext>();
		public TerminalNode WHEN() { return getToken(StructuresSqlParserParser.WHEN, 0); }
		public TerminalNode NOT() { return getToken(StructuresSqlParserParser.NOT, 0); }
		public TerminalNode MATCHED() { return getToken(StructuresSqlParserParser.MATCHED, 0); }
		public TerminalNode THEN() { return getToken(StructuresSqlParserParser.THEN, 0); }
		public TerminalNode INSERT() { return getToken(StructuresSqlParserParser.INSERT, 0); }
		public TerminalNode VALUES() { return getToken(StructuresSqlParserParser.VALUES, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(StructuresSqlParserParser.AND, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public MergeInsertContext(MergeCaseContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterMergeInsert(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitMergeInsert(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitMergeInsert(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MergeUpdateContext extends MergeCaseContext {
		public ExpressionContext condition;
		public IdentifierContext identifier;
		public List<IdentifierContext> targets = new ArrayList<IdentifierContext>();
		public ExpressionContext expression;
		public List<ExpressionContext> values = new ArrayList<ExpressionContext>();
		public TerminalNode WHEN() { return getToken(StructuresSqlParserParser.WHEN, 0); }
		public TerminalNode MATCHED() { return getToken(StructuresSqlParserParser.MATCHED, 0); }
		public TerminalNode THEN() { return getToken(StructuresSqlParserParser.THEN, 0); }
		public TerminalNode UPDATE() { return getToken(StructuresSqlParserParser.UPDATE, 0); }
		public TerminalNode SET() { return getToken(StructuresSqlParserParser.SET, 0); }
		public List<TerminalNode> EQ() { return getTokens(StructuresSqlParserParser.EQ); }
		public TerminalNode EQ(int i) {
			return getToken(StructuresSqlParserParser.EQ, i);
		}
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(StructuresSqlParserParser.AND, 0); }
		public MergeUpdateContext(MergeCaseContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterMergeUpdate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitMergeUpdate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitMergeUpdate(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MergeDeleteContext extends MergeCaseContext {
		public ExpressionContext condition;
		public TerminalNode WHEN() { return getToken(StructuresSqlParserParser.WHEN, 0); }
		public TerminalNode MATCHED() { return getToken(StructuresSqlParserParser.MATCHED, 0); }
		public TerminalNode THEN() { return getToken(StructuresSqlParserParser.THEN, 0); }
		public TerminalNode DELETE() { return getToken(StructuresSqlParserParser.DELETE, 0); }
		public TerminalNode AND() { return getToken(StructuresSqlParserParser.AND, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MergeDeleteContext(MergeCaseContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterMergeDelete(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitMergeDelete(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitMergeDelete(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MergeCaseContext mergeCase() throws RecognitionException {
		MergeCaseContext _localctx = new MergeCaseContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_mergeCase);
		int _la;
		try {
			setState(2134);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,275,_ctx) ) {
			case 1:
				_localctx = new MergeUpdateContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2070);
				match(WHEN);
				setState(2071);
				match(MATCHED);
				setState(2074);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(2072);
					match(T__7);
					setState(2073);
					((MergeUpdateContext)_localctx).condition = expression();
					}
				}

				setState(2076);
				match(THEN);
				setState(2077);
				match(UPDATE);
				setState(2078);
				match(SET);
				setState(2079);
				((MergeUpdateContext)_localctx).identifier = identifier();
				((MergeUpdateContext)_localctx).targets.add(((MergeUpdateContext)_localctx).identifier);
				setState(2080);
				match(EQ);
				setState(2081);
				((MergeUpdateContext)_localctx).expression = expression();
				((MergeUpdateContext)_localctx).values.add(((MergeUpdateContext)_localctx).expression);
				setState(2089);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(2082);
					match(T__1);
					setState(2083);
					((MergeUpdateContext)_localctx).identifier = identifier();
					((MergeUpdateContext)_localctx).targets.add(((MergeUpdateContext)_localctx).identifier);
					setState(2084);
					match(EQ);
					setState(2085);
					((MergeUpdateContext)_localctx).expression = expression();
					((MergeUpdateContext)_localctx).values.add(((MergeUpdateContext)_localctx).expression);
					}
					}
					setState(2091);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				_localctx = new MergeDeleteContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2092);
				match(WHEN);
				setState(2093);
				match(MATCHED);
				setState(2096);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(2094);
					match(T__7);
					setState(2095);
					((MergeDeleteContext)_localctx).condition = expression();
					}
				}

				setState(2098);
				match(THEN);
				setState(2099);
				match(DELETE);
				}
				break;
			case 3:
				_localctx = new MergeInsertContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2100);
				match(WHEN);
				setState(2101);
				match(NOT);
				setState(2102);
				match(MATCHED);
				setState(2105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(2103);
					match(T__7);
					setState(2104);
					((MergeInsertContext)_localctx).condition = expression();
					}
				}

				setState(2107);
				match(THEN);
				setState(2108);
				match(INSERT);
				setState(2120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(2109);
					match(T__2);
					setState(2110);
					((MergeInsertContext)_localctx).identifier = identifier();
					((MergeInsertContext)_localctx).targets.add(((MergeInsertContext)_localctx).identifier);
					setState(2115);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(2111);
						match(T__1);
						setState(2112);
						((MergeInsertContext)_localctx).identifier = identifier();
						((MergeInsertContext)_localctx).targets.add(((MergeInsertContext)_localctx).identifier);
						}
						}
						setState(2117);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(2118);
					match(T__3);
					}
				}

				setState(2122);
				match(VALUES);
				setState(2123);
				match(T__2);
				setState(2124);
				((MergeInsertContext)_localctx).expression = expression();
				((MergeInsertContext)_localctx).values.add(((MergeInsertContext)_localctx).expression);
				setState(2129);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(2125);
					match(T__1);
					setState(2126);
					((MergeInsertContext)_localctx).expression = expression();
					((MergeInsertContext)_localctx).values.add(((MergeInsertContext)_localctx).expression);
					}
					}
					setState(2131);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2132);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OverContext extends ParserRuleContext {
		public IdentifierContext windowName;
		public TerminalNode OVER() { return getToken(StructuresSqlParserParser.OVER, 0); }
		public WindowSpecificationContext windowSpecification() {
			return getRuleContext(WindowSpecificationContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public OverContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_over; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterOver(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitOver(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitOver(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OverContext over() throws RecognitionException {
		OverContext _localctx = new OverContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_over);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2136);
			match(OVER);
			setState(2142);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,276,_ctx) ) {
			case 1:
				{
				setState(2137);
				((OverContext)_localctx).windowName = identifier();
				}
				break;
			case 2:
				{
				setState(2138);
				match(T__2);
				setState(2139);
				windowSpecification();
				setState(2140);
				match(T__3);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WindowFrameContext extends ParserRuleContext {
		public FrameExtentContext frameExtent() {
			return getRuleContext(FrameExtentContext.class,0);
		}
		public TerminalNode MEASURES() { return getToken(StructuresSqlParserParser.MEASURES, 0); }
		public List<MeasureDefinitionContext> measureDefinition() {
			return getRuleContexts(MeasureDefinitionContext.class);
		}
		public MeasureDefinitionContext measureDefinition(int i) {
			return getRuleContext(MeasureDefinitionContext.class,i);
		}
		public TerminalNode AFTER() { return getToken(StructuresSqlParserParser.AFTER, 0); }
		public TerminalNode MATCH() { return getToken(StructuresSqlParserParser.MATCH, 0); }
		public SkipToContext skipTo() {
			return getRuleContext(SkipToContext.class,0);
		}
		public TerminalNode PATTERN() { return getToken(StructuresSqlParserParser.PATTERN, 0); }
		public RowPatternContext rowPattern() {
			return getRuleContext(RowPatternContext.class,0);
		}
		public TerminalNode SUBSET() { return getToken(StructuresSqlParserParser.SUBSET, 0); }
		public List<SubsetDefinitionContext> subsetDefinition() {
			return getRuleContexts(SubsetDefinitionContext.class);
		}
		public SubsetDefinitionContext subsetDefinition(int i) {
			return getRuleContext(SubsetDefinitionContext.class,i);
		}
		public TerminalNode DEFINE() { return getToken(StructuresSqlParserParser.DEFINE, 0); }
		public List<VariableDefinitionContext> variableDefinition() {
			return getRuleContexts(VariableDefinitionContext.class);
		}
		public VariableDefinitionContext variableDefinition(int i) {
			return getRuleContext(VariableDefinitionContext.class,i);
		}
		public TerminalNode INITIAL() { return getToken(StructuresSqlParserParser.INITIAL, 0); }
		public TerminalNode SEEK() { return getToken(StructuresSqlParserParser.SEEK, 0); }
		public WindowFrameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_windowFrame; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterWindowFrame(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitWindowFrame(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitWindowFrame(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WindowFrameContext windowFrame() throws RecognitionException {
		WindowFrameContext _localctx = new WindowFrameContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_windowFrame);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==MEASURES) {
				{
				setState(2144);
				match(MEASURES);
				setState(2145);
				measureDefinition();
				setState(2150);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(2146);
					match(T__1);
					setState(2147);
					measureDefinition();
					}
					}
					setState(2152);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(2155);
			frameExtent();
			setState(2159);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,279,_ctx) ) {
			case 1:
				{
				setState(2156);
				match(T__3);
				setState(2157);
				match(MATCH);
				setState(2158);
				skipTo();
				}
				break;
			}
			setState(2162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==INITIAL || _la==SEEK) {
				{
				setState(2161);
				_la = _input.LA(1);
				if ( !(_la==INITIAL || _la==SEEK) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(2169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PATTERN) {
				{
				setState(2164);
				match(PATTERN);
				setState(2165);
				match(T__2);
				setState(2166);
				rowPattern(0);
				setState(2167);
				match(T__3);
				}
			}

			setState(2180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SUBSET) {
				{
				setState(2171);
				match(SUBSET);
				setState(2172);
				subsetDefinition();
				setState(2177);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(2173);
					match(T__1);
					setState(2174);
					subsetDefinition();
					}
					}
					setState(2179);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(2191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFINE) {
				{
				setState(2182);
				match(DEFINE);
				setState(2183);
				variableDefinition();
				setState(2188);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(2184);
					match(T__1);
					setState(2185);
					variableDefinition();
					}
					}
					setState(2190);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FrameExtentContext extends ParserRuleContext {
		public Token frameType;
		public FrameBoundContext start;
		public FrameBoundContext end;
		public TerminalNode RANGE() { return getToken(StructuresSqlParserParser.RANGE, 0); }
		public List<FrameBoundContext> frameBound() {
			return getRuleContexts(FrameBoundContext.class);
		}
		public FrameBoundContext frameBound(int i) {
			return getRuleContext(FrameBoundContext.class,i);
		}
		public TerminalNode ROWS() { return getToken(StructuresSqlParserParser.ROWS, 0); }
		public TerminalNode GROUPS() { return getToken(StructuresSqlParserParser.GROUPS, 0); }
		public TerminalNode BETWEEN() { return getToken(StructuresSqlParserParser.BETWEEN, 0); }
		public TerminalNode AND() { return getToken(StructuresSqlParserParser.AND, 0); }
		public FrameExtentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_frameExtent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterFrameExtent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitFrameExtent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitFrameExtent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FrameExtentContext frameExtent() throws RecognitionException {
		FrameExtentContext _localctx = new FrameExtentContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_frameExtent);
		try {
			setState(2217);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,286,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2193);
				((FrameExtentContext)_localctx).frameType = match(RANGE);
				setState(2194);
				((FrameExtentContext)_localctx).start = frameBound();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2195);
				((FrameExtentContext)_localctx).frameType = match(ROWS);
				setState(2196);
				((FrameExtentContext)_localctx).start = frameBound();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2197);
				((FrameExtentContext)_localctx).frameType = match(GROUPS);
				setState(2198);
				((FrameExtentContext)_localctx).start = frameBound();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(2199);
				((FrameExtentContext)_localctx).frameType = match(RANGE);
				setState(2200);
				match(T__16);
				setState(2201);
				((FrameExtentContext)_localctx).start = frameBound();
				setState(2202);
				match(T__7);
				setState(2203);
				((FrameExtentContext)_localctx).end = frameBound();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(2205);
				((FrameExtentContext)_localctx).frameType = match(ROWS);
				setState(2206);
				match(T__16);
				setState(2207);
				((FrameExtentContext)_localctx).start = frameBound();
				setState(2208);
				match(T__7);
				setState(2209);
				((FrameExtentContext)_localctx).end = frameBound();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(2211);
				((FrameExtentContext)_localctx).frameType = match(GROUPS);
				setState(2212);
				match(T__16);
				setState(2213);
				((FrameExtentContext)_localctx).start = frameBound();
				setState(2214);
				match(T__7);
				setState(2215);
				((FrameExtentContext)_localctx).end = frameBound();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FrameBoundContext extends ParserRuleContext {
		public FrameBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_frameBound; }
	 
		public FrameBoundContext() { }
		public void copyFrom(FrameBoundContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoundedFrameContext extends FrameBoundContext {
		public Token boundType;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode PRECEDING() { return getToken(StructuresSqlParserParser.PRECEDING, 0); }
		public TerminalNode FOLLOWING() { return getToken(StructuresSqlParserParser.FOLLOWING, 0); }
		public BoundedFrameContext(FrameBoundContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterBoundedFrame(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitBoundedFrame(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitBoundedFrame(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnboundedFrameContext extends FrameBoundContext {
		public Token boundType;
		public TerminalNode UNBOUNDED() { return getToken(StructuresSqlParserParser.UNBOUNDED, 0); }
		public TerminalNode PRECEDING() { return getToken(StructuresSqlParserParser.PRECEDING, 0); }
		public TerminalNode FOLLOWING() { return getToken(StructuresSqlParserParser.FOLLOWING, 0); }
		public UnboundedFrameContext(FrameBoundContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterUnboundedFrame(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitUnboundedFrame(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitUnboundedFrame(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CurrentRowBoundContext extends FrameBoundContext {
		public TerminalNode CURRENT() { return getToken(StructuresSqlParserParser.CURRENT, 0); }
		public TerminalNode ROW() { return getToken(StructuresSqlParserParser.ROW, 0); }
		public CurrentRowBoundContext(FrameBoundContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCurrentRowBound(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCurrentRowBound(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCurrentRowBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FrameBoundContext frameBound() throws RecognitionException {
		FrameBoundContext _localctx = new FrameBoundContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_frameBound);
		int _la;
		try {
			setState(2228);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,287,_ctx) ) {
			case 1:
				_localctx = new UnboundedFrameContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2219);
				match(UNBOUNDED);
				setState(2220);
				((UnboundedFrameContext)_localctx).boundType = match(PRECEDING);
				}
				break;
			case 2:
				_localctx = new UnboundedFrameContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2221);
				match(UNBOUNDED);
				setState(2222);
				((UnboundedFrameContext)_localctx).boundType = match(FOLLOWING);
				}
				break;
			case 3:
				_localctx = new CurrentRowBoundContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2223);
				match(CURRENT);
				setState(2224);
				match(ROW);
				}
				break;
			case 4:
				_localctx = new BoundedFrameContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2225);
				expression();
				setState(2226);
				((BoundedFrameContext)_localctx).boundType = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==FOLLOWING || _la==PRECEDING) ) {
					((BoundedFrameContext)_localctx).boundType = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RowPatternContext extends ParserRuleContext {
		public RowPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rowPattern; }
	 
		public RowPatternContext() { }
		public void copyFrom(RowPatternContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class QuantifiedPrimaryContext extends RowPatternContext {
		public PatternPrimaryContext patternPrimary() {
			return getRuleContext(PatternPrimaryContext.class,0);
		}
		public PatternQuantifierContext patternQuantifier() {
			return getRuleContext(PatternQuantifierContext.class,0);
		}
		public QuantifiedPrimaryContext(RowPatternContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQuantifiedPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQuantifiedPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQuantifiedPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PatternConcatenationContext extends RowPatternContext {
		public List<RowPatternContext> rowPattern() {
			return getRuleContexts(RowPatternContext.class);
		}
		public RowPatternContext rowPattern(int i) {
			return getRuleContext(RowPatternContext.class,i);
		}
		public PatternConcatenationContext(RowPatternContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPatternConcatenation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPatternConcatenation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPatternConcatenation(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PatternAlternationContext extends RowPatternContext {
		public List<RowPatternContext> rowPattern() {
			return getRuleContexts(RowPatternContext.class);
		}
		public RowPatternContext rowPattern(int i) {
			return getRuleContext(RowPatternContext.class,i);
		}
		public PatternAlternationContext(RowPatternContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPatternAlternation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPatternAlternation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPatternAlternation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RowPatternContext rowPattern() throws RecognitionException {
		return rowPattern(0);
	}

	private RowPatternContext rowPattern(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RowPatternContext _localctx = new RowPatternContext(_ctx, _parentState);
		RowPatternContext _prevctx = _localctx;
		int _startState = 194;
		enterRecursionRule(_localctx, 194, RULE_rowPattern, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new QuantifiedPrimaryContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(2231);
			patternPrimary();
			setState(2233);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,288,_ctx) ) {
			case 1:
				{
				setState(2232);
				patternQuantifier();
				}
				break;
			}
			}
			_ctx.stop = _input.LT(-1);
			setState(2242);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,290,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(2240);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,289,_ctx) ) {
					case 1:
						{
						_localctx = new PatternConcatenationContext(new RowPatternContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_rowPattern);
						setState(2235);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(2236);
						rowPattern(3);
						}
						break;
					case 2:
						{
						_localctx = new PatternAlternationContext(new RowPatternContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_rowPattern);
						setState(2237);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(2238);
						match(T__12);
						setState(2239);
						rowPattern(2);
						}
						break;
					}
					} 
				}
				setState(2244);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,290,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PatternPrimaryContext extends ParserRuleContext {
		public PatternPrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_patternPrimary; }
	 
		public PatternPrimaryContext() { }
		public void copyFrom(PatternPrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PatternPermutationContext extends PatternPrimaryContext {
		public TerminalNode PERMUTE() { return getToken(StructuresSqlParserParser.PERMUTE, 0); }
		public List<RowPatternContext> rowPattern() {
			return getRuleContexts(RowPatternContext.class);
		}
		public RowPatternContext rowPattern(int i) {
			return getRuleContext(RowPatternContext.class,i);
		}
		public PatternPermutationContext(PatternPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPatternPermutation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPatternPermutation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPatternPermutation(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PartitionEndAnchorContext extends PatternPrimaryContext {
		public PartitionEndAnchorContext(PatternPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPartitionEndAnchor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPartitionEndAnchor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPartitionEndAnchor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PatternVariableContext extends PatternPrimaryContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public PatternVariableContext(PatternPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPatternVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPatternVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPatternVariable(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExcludedPatternContext extends PatternPrimaryContext {
		public RowPatternContext rowPattern() {
			return getRuleContext(RowPatternContext.class,0);
		}
		public ExcludedPatternContext(PatternPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterExcludedPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitExcludedPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitExcludedPattern(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PartitionStartAnchorContext extends PatternPrimaryContext {
		public PartitionStartAnchorContext(PatternPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPartitionStartAnchor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPartitionStartAnchor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPartitionStartAnchor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EmptyPatternContext extends PatternPrimaryContext {
		public EmptyPatternContext(PatternPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterEmptyPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitEmptyPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitEmptyPattern(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GroupedPatternContext extends PatternPrimaryContext {
		public RowPatternContext rowPattern() {
			return getRuleContext(RowPatternContext.class,0);
		}
		public GroupedPatternContext(PatternPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterGroupedPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitGroupedPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitGroupedPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PatternPrimaryContext patternPrimary() throws RecognitionException {
		PatternPrimaryContext _localctx = new PatternPrimaryContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_patternPrimary);
		int _la;
		try {
			setState(2270);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,292,_ctx) ) {
			case 1:
				_localctx = new PatternVariableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2245);
				identifier();
				}
				break;
			case 2:
				_localctx = new EmptyPatternContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2246);
				match(T__2);
				setState(2247);
				match(T__3);
				}
				break;
			case 3:
				_localctx = new PatternPermutationContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2248);
				match(PERMUTE);
				setState(2249);
				match(T__2);
				setState(2250);
				rowPattern(0);
				setState(2255);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(2251);
					match(T__1);
					setState(2252);
					rowPattern(0);
					}
					}
					setState(2257);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2258);
				match(T__3);
				}
				break;
			case 4:
				_localctx = new GroupedPatternContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2260);
				match(T__2);
				setState(2261);
				rowPattern(0);
				setState(2262);
				match(T__3);
				}
				break;
			case 5:
				_localctx = new PartitionStartAnchorContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(2264);
				match(T__13);
				}
				break;
			case 6:
				_localctx = new PartitionEndAnchorContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(2265);
				match(T__14);
				}
				break;
			case 7:
				_localctx = new ExcludedPatternContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(2266);
				match(T__15);
				setState(2267);
				rowPattern(0);
				setState(2268);
				match(T__16);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PatternQuantifierContext extends ParserRuleContext {
		public PatternQuantifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_patternQuantifier; }
	 
		public PatternQuantifierContext() { }
		public void copyFrom(PatternQuantifierContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ZeroOrMoreQuantifierContext extends PatternQuantifierContext {
		public Token reluctant;
		public TerminalNode ASTERISK() { return getToken(StructuresSqlParserParser.ASTERISK, 0); }
		public TerminalNode QUESTION_MARK() { return getToken(StructuresSqlParserParser.QUESTION_MARK, 0); }
		public ZeroOrMoreQuantifierContext(PatternQuantifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterZeroOrMoreQuantifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitZeroOrMoreQuantifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitZeroOrMoreQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OneOrMoreQuantifierContext extends PatternQuantifierContext {
		public Token reluctant;
		public TerminalNode PLUS() { return getToken(StructuresSqlParserParser.PLUS, 0); }
		public TerminalNode QUESTION_MARK() { return getToken(StructuresSqlParserParser.QUESTION_MARK, 0); }
		public OneOrMoreQuantifierContext(PatternQuantifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterOneOrMoreQuantifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitOneOrMoreQuantifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitOneOrMoreQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ZeroOrOneQuantifierContext extends PatternQuantifierContext {
		public Token reluctant;
		public List<TerminalNode> QUESTION_MARK() { return getTokens(StructuresSqlParserParser.QUESTION_MARK); }
		public TerminalNode QUESTION_MARK(int i) {
			return getToken(StructuresSqlParserParser.QUESTION_MARK, i);
		}
		public ZeroOrOneQuantifierContext(PatternQuantifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterZeroOrOneQuantifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitZeroOrOneQuantifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitZeroOrOneQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RangeQuantifierContext extends PatternQuantifierContext {
		public Token exactly;
		public Token reluctant;
		public Token atLeast;
		public Token atMost;
		public List<TerminalNode> INTEGER_VALUE() { return getTokens(StructuresSqlParserParser.INTEGER_VALUE); }
		public TerminalNode INTEGER_VALUE(int i) {
			return getToken(StructuresSqlParserParser.INTEGER_VALUE, i);
		}
		public TerminalNode QUESTION_MARK() { return getToken(StructuresSqlParserParser.QUESTION_MARK, 0); }
		public RangeQuantifierContext(PatternQuantifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRangeQuantifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRangeQuantifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRangeQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PatternQuantifierContext patternQuantifier() throws RecognitionException {
		PatternQuantifierContext _localctx = new PatternQuantifierContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_patternQuantifier);
		int _la;
		try {
			setState(2302);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,300,_ctx) ) {
			case 1:
				_localctx = new ZeroOrMoreQuantifierContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2272);
				match(ASTERISK);
				setState(2274);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,293,_ctx) ) {
				case 1:
					{
					setState(2273);
					((ZeroOrMoreQuantifierContext)_localctx).reluctant = match(QUESTION_MARK);
					}
					break;
				}
				}
				break;
			case 2:
				_localctx = new OneOrMoreQuantifierContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2276);
				match(PLUS);
				setState(2278);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,294,_ctx) ) {
				case 1:
					{
					setState(2277);
					((OneOrMoreQuantifierContext)_localctx).reluctant = match(QUESTION_MARK);
					}
					break;
				}
				}
				break;
			case 3:
				_localctx = new ZeroOrOneQuantifierContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2280);
				match(QUESTION_MARK);
				setState(2282);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,295,_ctx) ) {
				case 1:
					{
					setState(2281);
					((ZeroOrOneQuantifierContext)_localctx).reluctant = match(QUESTION_MARK);
					}
					break;
				}
				}
				break;
			case 4:
				_localctx = new RangeQuantifierContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2284);
				match(T__17);
				setState(2285);
				((RangeQuantifierContext)_localctx).exactly = match(INTEGER_VALUE);
				setState(2286);
				match(T__18);
				setState(2288);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,296,_ctx) ) {
				case 1:
					{
					setState(2287);
					((RangeQuantifierContext)_localctx).reluctant = match(QUESTION_MARK);
					}
					break;
				}
				}
				break;
			case 5:
				_localctx = new RangeQuantifierContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(2290);
				match(T__17);
				setState(2292);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INTEGER_VALUE) {
					{
					setState(2291);
					((RangeQuantifierContext)_localctx).atLeast = match(INTEGER_VALUE);
					}
				}

				setState(2294);
				match(T__1);
				setState(2296);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INTEGER_VALUE) {
					{
					setState(2295);
					((RangeQuantifierContext)_localctx).atMost = match(INTEGER_VALUE);
					}
				}

				setState(2298);
				match(T__18);
				setState(2300);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,299,_ctx) ) {
				case 1:
					{
					setState(2299);
					((RangeQuantifierContext)_localctx).reluctant = match(QUESTION_MARK);
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UpdateAssignmentContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode EQ() { return getToken(StructuresSqlParserParser.EQ, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UpdateAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updateAssignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterUpdateAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitUpdateAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitUpdateAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdateAssignmentContext updateAssignment() throws RecognitionException {
		UpdateAssignmentContext _localctx = new UpdateAssignmentContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_updateAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2304);
			identifier();
			setState(2305);
			match(EQ);
			setState(2306);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExplainOptionContext extends ParserRuleContext {
		public ExplainOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explainOption; }
	 
		public ExplainOptionContext() { }
		public void copyFrom(ExplainOptionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplainFormatContext extends ExplainOptionContext {
		public Token value;
		public TerminalNode FORMAT() { return getToken(StructuresSqlParserParser.FORMAT, 0); }
		public TerminalNode TEXT() { return getToken(StructuresSqlParserParser.TEXT, 0); }
		public TerminalNode GRAPHVIZ() { return getToken(StructuresSqlParserParser.GRAPHVIZ, 0); }
		public TerminalNode JSON() { return getToken(StructuresSqlParserParser.JSON, 0); }
		public ExplainFormatContext(ExplainOptionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterExplainFormat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitExplainFormat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitExplainFormat(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplainTypeContext extends ExplainOptionContext {
		public Token value;
		public TerminalNode TYPE() { return getToken(StructuresSqlParserParser.TYPE, 0); }
		public TerminalNode LOGICAL() { return getToken(StructuresSqlParserParser.LOGICAL, 0); }
		public TerminalNode DISTRIBUTED() { return getToken(StructuresSqlParserParser.DISTRIBUTED, 0); }
		public TerminalNode VALIDATE() { return getToken(StructuresSqlParserParser.VALIDATE, 0); }
		public TerminalNode IO() { return getToken(StructuresSqlParserParser.IO, 0); }
		public ExplainTypeContext(ExplainOptionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterExplainType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitExplainType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitExplainType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExplainOptionContext explainOption() throws RecognitionException {
		ExplainOptionContext _localctx = new ExplainOptionContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_explainOption);
		int _la;
		try {
			setState(2312);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FORMAT:
				_localctx = new ExplainFormatContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2308);
				match(FORMAT);
				setState(2309);
				((ExplainFormatContext)_localctx).value = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==GRAPHVIZ || _la==JSON || _la==TEXT) ) {
					((ExplainFormatContext)_localctx).value = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case TYPE:
				_localctx = new ExplainTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2310);
				match(TYPE);
				setState(2311);
				((ExplainTypeContext)_localctx).value = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==DISTRIBUTED || _la==IO || _la==LOGICAL || _la==VALIDATE) ) {
					((ExplainTypeContext)_localctx).value = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TransactionModeContext extends ParserRuleContext {
		public TransactionModeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_transactionMode; }
	 
		public TransactionModeContext() { }
		public void copyFrom(TransactionModeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TransactionAccessModeContext extends TransactionModeContext {
		public Token accessMode;
		public TerminalNode READ() { return getToken(StructuresSqlParserParser.READ, 0); }
		public TerminalNode ONLY() { return getToken(StructuresSqlParserParser.ONLY, 0); }
		public TerminalNode WRITE() { return getToken(StructuresSqlParserParser.WRITE, 0); }
		public TransactionAccessModeContext(TransactionModeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterTransactionAccessMode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitTransactionAccessMode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitTransactionAccessMode(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IsolationLevelContext extends TransactionModeContext {
		public TerminalNode ISOLATION() { return getToken(StructuresSqlParserParser.ISOLATION, 0); }
		public TerminalNode LEVEL() { return getToken(StructuresSqlParserParser.LEVEL, 0); }
		public LevelOfIsolationContext levelOfIsolation() {
			return getRuleContext(LevelOfIsolationContext.class,0);
		}
		public IsolationLevelContext(TransactionModeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterIsolationLevel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitIsolationLevel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitIsolationLevel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TransactionModeContext transactionMode() throws RecognitionException {
		TransactionModeContext _localctx = new TransactionModeContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_transactionMode);
		int _la;
		try {
			setState(2319);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ISOLATION:
				_localctx = new IsolationLevelContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2314);
				match(ISOLATION);
				setState(2315);
				match(LEVEL);
				setState(2316);
				levelOfIsolation();
				}
				break;
			case READ:
				_localctx = new TransactionAccessModeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2317);
				match(READ);
				setState(2318);
				((TransactionAccessModeContext)_localctx).accessMode = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ONLY || _la==WRITE) ) {
					((TransactionAccessModeContext)_localctx).accessMode = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LevelOfIsolationContext extends ParserRuleContext {
		public LevelOfIsolationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_levelOfIsolation; }
	 
		public LevelOfIsolationContext() { }
		public void copyFrom(LevelOfIsolationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReadUncommittedContext extends LevelOfIsolationContext {
		public TerminalNode READ() { return getToken(StructuresSqlParserParser.READ, 0); }
		public TerminalNode UNCOMMITTED() { return getToken(StructuresSqlParserParser.UNCOMMITTED, 0); }
		public ReadUncommittedContext(LevelOfIsolationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterReadUncommitted(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitReadUncommitted(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitReadUncommitted(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SerializableContext extends LevelOfIsolationContext {
		public TerminalNode SERIALIZABLE() { return getToken(StructuresSqlParserParser.SERIALIZABLE, 0); }
		public SerializableContext(LevelOfIsolationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSerializable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSerializable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSerializable(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReadCommittedContext extends LevelOfIsolationContext {
		public TerminalNode READ() { return getToken(StructuresSqlParserParser.READ, 0); }
		public TerminalNode COMMITTED() { return getToken(StructuresSqlParserParser.COMMITTED, 0); }
		public ReadCommittedContext(LevelOfIsolationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterReadCommitted(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitReadCommitted(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitReadCommitted(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RepeatableReadContext extends LevelOfIsolationContext {
		public TerminalNode REPEATABLE() { return getToken(StructuresSqlParserParser.REPEATABLE, 0); }
		public TerminalNode READ() { return getToken(StructuresSqlParserParser.READ, 0); }
		public RepeatableReadContext(LevelOfIsolationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRepeatableRead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRepeatableRead(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRepeatableRead(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LevelOfIsolationContext levelOfIsolation() throws RecognitionException {
		LevelOfIsolationContext _localctx = new LevelOfIsolationContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_levelOfIsolation);
		try {
			setState(2328);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,303,_ctx) ) {
			case 1:
				_localctx = new ReadUncommittedContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2321);
				match(READ);
				setState(2322);
				match(UNCOMMITTED);
				}
				break;
			case 2:
				_localctx = new ReadCommittedContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2323);
				match(READ);
				setState(2324);
				match(COMMITTED);
				}
				break;
			case 3:
				_localctx = new RepeatableReadContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2325);
				match(REPEATABLE);
				setState(2326);
				match(READ);
				}
				break;
			case 4:
				_localctx = new SerializableContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2327);
				match(SERIALIZABLE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CallArgumentContext extends ParserRuleContext {
		public CallArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callArgument; }
	 
		public CallArgumentContext() { }
		public void copyFrom(CallArgumentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PositionalArgumentContext extends CallArgumentContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PositionalArgumentContext(CallArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPositionalArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPositionalArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPositionalArgument(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NamedArgumentContext extends CallArgumentContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NamedArgumentContext(CallArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterNamedArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitNamedArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitNamedArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallArgumentContext callArgument() throws RecognitionException {
		CallArgumentContext _localctx = new CallArgumentContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_callArgument);
		try {
			setState(2335);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,304,_ctx) ) {
			case 1:
				_localctx = new PositionalArgumentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2330);
				expression();
				}
				break;
			case 2:
				_localctx = new NamedArgumentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2331);
				identifier();
				setState(2332);
				match(T__5);
				setState(2333);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PathElementContext extends ParserRuleContext {
		public PathElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathElement; }
	 
		public PathElementContext() { }
		public void copyFrom(PathElementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class QualifiedArgumentContext extends PathElementContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public QualifiedArgumentContext(PathElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQualifiedArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQualifiedArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQualifiedArgument(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnqualifiedArgumentContext extends PathElementContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public UnqualifiedArgumentContext(PathElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterUnqualifiedArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitUnqualifiedArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitUnqualifiedArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathElementContext pathElement() throws RecognitionException {
		PathElementContext _localctx = new PathElementContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_pathElement);
		try {
			setState(2342);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,305,_ctx) ) {
			case 1:
				_localctx = new QualifiedArgumentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2337);
				identifier();
				setState(2338);
				match(T__0);
				setState(2339);
				identifier();
				}
				break;
			case 2:
				_localctx = new UnqualifiedArgumentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2341);
				identifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PathSpecificationContext extends ParserRuleContext {
		public List<PathElementContext> pathElement() {
			return getRuleContexts(PathElementContext.class);
		}
		public PathElementContext pathElement(int i) {
			return getRuleContext(PathElementContext.class,i);
		}
		public PathSpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathSpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPathSpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPathSpecification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPathSpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathSpecificationContext pathSpecification() throws RecognitionException {
		PathSpecificationContext _localctx = new PathSpecificationContext(_ctx, getState());
		enterRule(_localctx, 212, RULE_pathSpecification);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2344);
			pathElement();
			setState(2349);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(2345);
				match(T__1);
				setState(2346);
				pathElement();
				}
				}
				setState(2351);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionSpecificationContext extends ParserRuleContext {
		public TerminalNode FUNCTION() { return getToken(StructuresSqlParserParser.FUNCTION, 0); }
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public ReturnsClauseContext returnsClause() {
			return getRuleContext(ReturnsClauseContext.class,0);
		}
		public ControlStatementContext controlStatement() {
			return getRuleContext(ControlStatementContext.class,0);
		}
		public List<RoutineCharacteristicContext> routineCharacteristic() {
			return getRuleContexts(RoutineCharacteristicContext.class);
		}
		public RoutineCharacteristicContext routineCharacteristic(int i) {
			return getRuleContext(RoutineCharacteristicContext.class,i);
		}
		public FunctionSpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionSpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterFunctionSpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitFunctionSpecification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitFunctionSpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionSpecificationContext functionSpecification() throws RecognitionException {
		FunctionSpecificationContext _localctx = new FunctionSpecificationContext(_ctx, getState());
		enterRule(_localctx, 214, RULE_functionSpecification);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2352);
			match(FUNCTION);
			setState(2353);
			functionDeclaration();
			setState(2354);
			returnsClause();
			setState(2358);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,307,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(2355);
					routineCharacteristic();
					}
					} 
				}
				setState(2360);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,307,_ctx);
			}
			setState(2361);
			controlStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionDeclarationContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public List<ParameterDeclarationContext> parameterDeclaration() {
			return getRuleContexts(ParameterDeclarationContext.class);
		}
		public ParameterDeclarationContext parameterDeclaration(int i) {
			return getRuleContext(ParameterDeclarationContext.class,i);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 216, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2363);
			qualifiedName();
			setState(2364);
			match(T__2);
			setState(2373);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,309,_ctx) ) {
			case 1:
				{
				setState(2365);
				parameterDeclaration();
				setState(2370);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(2366);
					match(T__1);
					setState(2367);
					parameterDeclaration();
					}
					}
					setState(2372);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
			setState(2375);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParameterDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ParameterDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterParameterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitParameterDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitParameterDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterDeclarationContext parameterDeclaration() throws RecognitionException {
		ParameterDeclarationContext _localctx = new ParameterDeclarationContext(_ctx, getState());
		enterRule(_localctx, 218, RULE_parameterDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2378);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,310,_ctx) ) {
			case 1:
				{
				setState(2377);
				identifier();
				}
				break;
			}
			setState(2380);
			type(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnsClauseContext extends ParserRuleContext {
		public TerminalNode RETURNS() { return getToken(StructuresSqlParserParser.RETURNS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ReturnsClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnsClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterReturnsClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitReturnsClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitReturnsClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnsClauseContext returnsClause() throws RecognitionException {
		ReturnsClauseContext _localctx = new ReturnsClauseContext(_ctx, getState());
		enterRule(_localctx, 220, RULE_returnsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2382);
			match(RETURNS);
			setState(2383);
			type(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RoutineCharacteristicContext extends ParserRuleContext {
		public RoutineCharacteristicContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_routineCharacteristic; }
	 
		public RoutineCharacteristicContext() { }
		public void copyFrom(RoutineCharacteristicContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReturnsNullOnNullInputCharacteristicContext extends RoutineCharacteristicContext {
		public TerminalNode RETURNS() { return getToken(StructuresSqlParserParser.RETURNS, 0); }
		public List<TerminalNode> NULL() { return getTokens(StructuresSqlParserParser.NULL); }
		public TerminalNode NULL(int i) {
			return getToken(StructuresSqlParserParser.NULL, i);
		}
		public TerminalNode ON() { return getToken(StructuresSqlParserParser.ON, 0); }
		public TerminalNode INPUT() { return getToken(StructuresSqlParserParser.INPUT, 0); }
		public ReturnsNullOnNullInputCharacteristicContext(RoutineCharacteristicContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterReturnsNullOnNullInputCharacteristic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitReturnsNullOnNullInputCharacteristic(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitReturnsNullOnNullInputCharacteristic(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SecurityCharacteristicContext extends RoutineCharacteristicContext {
		public TerminalNode SECURITY() { return getToken(StructuresSqlParserParser.SECURITY, 0); }
		public TerminalNode DEFINER() { return getToken(StructuresSqlParserParser.DEFINER, 0); }
		public TerminalNode INVOKER() { return getToken(StructuresSqlParserParser.INVOKER, 0); }
		public SecurityCharacteristicContext(RoutineCharacteristicContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSecurityCharacteristic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSecurityCharacteristic(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSecurityCharacteristic(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CalledOnNullInputCharacteristicContext extends RoutineCharacteristicContext {
		public TerminalNode CALLED() { return getToken(StructuresSqlParserParser.CALLED, 0); }
		public TerminalNode ON() { return getToken(StructuresSqlParserParser.ON, 0); }
		public TerminalNode NULL() { return getToken(StructuresSqlParserParser.NULL, 0); }
		public TerminalNode INPUT() { return getToken(StructuresSqlParserParser.INPUT, 0); }
		public CalledOnNullInputCharacteristicContext(RoutineCharacteristicContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCalledOnNullInputCharacteristic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCalledOnNullInputCharacteristic(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCalledOnNullInputCharacteristic(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CommentCharacteristicContext extends RoutineCharacteristicContext {
		public TerminalNode COMMENT() { return getToken(StructuresSqlParserParser.COMMENT, 0); }
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public CommentCharacteristicContext(RoutineCharacteristicContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCommentCharacteristic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCommentCharacteristic(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCommentCharacteristic(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LanguageCharacteristicContext extends RoutineCharacteristicContext {
		public TerminalNode LANGUAGE() { return getToken(StructuresSqlParserParser.LANGUAGE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public LanguageCharacteristicContext(RoutineCharacteristicContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLanguageCharacteristic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLanguageCharacteristic(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLanguageCharacteristic(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeterministicCharacteristicContext extends RoutineCharacteristicContext {
		public TerminalNode DETERMINISTIC() { return getToken(StructuresSqlParserParser.DETERMINISTIC, 0); }
		public TerminalNode NOT() { return getToken(StructuresSqlParserParser.NOT, 0); }
		public DeterministicCharacteristicContext(RoutineCharacteristicContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterDeterministicCharacteristic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitDeterministicCharacteristic(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitDeterministicCharacteristic(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RoutineCharacteristicContext routineCharacteristic() throws RecognitionException {
		RoutineCharacteristicContext _localctx = new RoutineCharacteristicContext(_ctx, getState());
		enterRule(_localctx, 222, RULE_routineCharacteristic);
		int _la;
		try {
			setState(2404);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LANGUAGE:
				_localctx = new LanguageCharacteristicContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2385);
				match(LANGUAGE);
				setState(2386);
				identifier();
				}
				break;
			case DETERMINISTIC:
			case NOT:
				_localctx = new DeterministicCharacteristicContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2388);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(2387);
					match(NOT);
					}
				}

				setState(2390);
				match(DETERMINISTIC);
				}
				break;
			case RETURNS:
				_localctx = new ReturnsNullOnNullInputCharacteristicContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2391);
				match(RETURNS);
				setState(2392);
				match(NULL);
				setState(2393);
				match(ON);
				setState(2394);
				match(NULL);
				setState(2395);
				match(INPUT);
				}
				break;
			case CALLED:
				_localctx = new CalledOnNullInputCharacteristicContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2396);
				match(CALLED);
				setState(2397);
				match(ON);
				setState(2398);
				match(NULL);
				setState(2399);
				match(INPUT);
				}
				break;
			case SECURITY:
				_localctx = new SecurityCharacteristicContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(2400);
				match(SECURITY);
				setState(2401);
				_la = _input.LA(1);
				if ( !(_la==DEFINER || _la==INVOKER) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case COMMENT:
				_localctx = new CommentCharacteristicContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(2402);
				match(COMMENT);
				setState(2403);
				string();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ControlStatementContext extends ParserRuleContext {
		public ControlStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_controlStatement; }
	 
		public ControlStatementContext() { }
		public void copyFrom(ControlStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatementContext extends ControlStatementContext {
		public IdentifierContext label;
		public List<TerminalNode> WHILE() { return getTokens(StructuresSqlParserParser.WHILE); }
		public TerminalNode WHILE(int i) {
			return getToken(StructuresSqlParserParser.WHILE, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DO() { return getToken(StructuresSqlParserParser.DO, 0); }
		public SqlStatementListContext sqlStatementList() {
			return getRuleContext(SqlStatementListContext.class,0);
		}
		public TerminalNode END() { return getToken(StructuresSqlParserParser.END, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public WhileStatementContext(ControlStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SimpleCaseStatementContext extends ControlStatementContext {
		public List<TerminalNode> CASE() { return getTokens(StructuresSqlParserParser.CASE); }
		public TerminalNode CASE(int i) {
			return getToken(StructuresSqlParserParser.CASE, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode END() { return getToken(StructuresSqlParserParser.END, 0); }
		public List<CaseStatementWhenClauseContext> caseStatementWhenClause() {
			return getRuleContexts(CaseStatementWhenClauseContext.class);
		}
		public CaseStatementWhenClauseContext caseStatementWhenClause(int i) {
			return getRuleContext(CaseStatementWhenClauseContext.class,i);
		}
		public ElseClauseContext elseClause() {
			return getRuleContext(ElseClauseContext.class,0);
		}
		public SimpleCaseStatementContext(ControlStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSimpleCaseStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSimpleCaseStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSimpleCaseStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RepeatStatementContext extends ControlStatementContext {
		public IdentifierContext label;
		public List<TerminalNode> REPEAT() { return getTokens(StructuresSqlParserParser.REPEAT); }
		public TerminalNode REPEAT(int i) {
			return getToken(StructuresSqlParserParser.REPEAT, i);
		}
		public SqlStatementListContext sqlStatementList() {
			return getRuleContext(SqlStatementListContext.class,0);
		}
		public TerminalNode UNTIL() { return getToken(StructuresSqlParserParser.UNTIL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode END() { return getToken(StructuresSqlParserParser.END, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public RepeatStatementContext(ControlStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRepeatStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRepeatStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRepeatStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentStatementContext extends ControlStatementContext {
		public TerminalNode SET() { return getToken(StructuresSqlParserParser.SET, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode EQ() { return getToken(StructuresSqlParserParser.EQ, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignmentStatementContext(ControlStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterAssignmentStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitAssignmentStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LeaveStatementContext extends ControlStatementContext {
		public TerminalNode LEAVE() { return getToken(StructuresSqlParserParser.LEAVE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public LeaveStatementContext(ControlStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLeaveStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLeaveStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLeaveStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompoundStatementContext extends ControlStatementContext {
		public TerminalNode BEGIN() { return getToken(StructuresSqlParserParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(StructuresSqlParserParser.END, 0); }
		public List<VariableDeclarationContext> variableDeclaration() {
			return getRuleContexts(VariableDeclarationContext.class);
		}
		public VariableDeclarationContext variableDeclaration(int i) {
			return getRuleContext(VariableDeclarationContext.class,i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(StructuresSqlParserParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(StructuresSqlParserParser.SEMICOLON, i);
		}
		public SqlStatementListContext sqlStatementList() {
			return getRuleContext(SqlStatementListContext.class,0);
		}
		public CompoundStatementContext(ControlStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCompoundStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCompoundStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCompoundStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IterateStatementContext extends ControlStatementContext {
		public TerminalNode ITERATE() { return getToken(StructuresSqlParserParser.ITERATE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public IterateStatementContext(ControlStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterIterateStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitIterateStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitIterateStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LoopStatementContext extends ControlStatementContext {
		public IdentifierContext label;
		public List<TerminalNode> LOOP() { return getTokens(StructuresSqlParserParser.LOOP); }
		public TerminalNode LOOP(int i) {
			return getToken(StructuresSqlParserParser.LOOP, i);
		}
		public SqlStatementListContext sqlStatementList() {
			return getRuleContext(SqlStatementListContext.class,0);
		}
		public TerminalNode END() { return getToken(StructuresSqlParserParser.END, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public LoopStatementContext(ControlStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterLoopStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitLoopStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitLoopStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStatementContext extends ControlStatementContext {
		public TerminalNode RETURN() { return getToken(StructuresSqlParserParser.RETURN, 0); }
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public ReturnStatementContext(ControlStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends ControlStatementContext {
		public List<TerminalNode> IF() { return getTokens(StructuresSqlParserParser.IF); }
		public TerminalNode IF(int i) {
			return getToken(StructuresSqlParserParser.IF, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode THEN() { return getToken(StructuresSqlParserParser.THEN, 0); }
		public SqlStatementListContext sqlStatementList() {
			return getRuleContext(SqlStatementListContext.class,0);
		}
		public TerminalNode END() { return getToken(StructuresSqlParserParser.END, 0); }
		public List<ElseIfClauseContext> elseIfClause() {
			return getRuleContexts(ElseIfClauseContext.class);
		}
		public ElseIfClauseContext elseIfClause(int i) {
			return getRuleContext(ElseIfClauseContext.class,i);
		}
		public ElseClauseContext elseClause() {
			return getRuleContext(ElseClauseContext.class,0);
		}
		public IfStatementContext(ControlStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SearchedCaseStatementContext extends ControlStatementContext {
		public List<TerminalNode> CASE() { return getTokens(StructuresSqlParserParser.CASE); }
		public TerminalNode CASE(int i) {
			return getToken(StructuresSqlParserParser.CASE, i);
		}
		public TerminalNode END() { return getToken(StructuresSqlParserParser.END, 0); }
		public List<CaseStatementWhenClauseContext> caseStatementWhenClause() {
			return getRuleContexts(CaseStatementWhenClauseContext.class);
		}
		public CaseStatementWhenClauseContext caseStatementWhenClause(int i) {
			return getRuleContext(CaseStatementWhenClauseContext.class,i);
		}
		public ElseClauseContext elseClause() {
			return getRuleContext(ElseClauseContext.class,0);
		}
		public SearchedCaseStatementContext(ControlStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSearchedCaseStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSearchedCaseStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSearchedCaseStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ControlStatementContext controlStatement() throws RecognitionException {
		ControlStatementContext _localctx = new ControlStatementContext(_ctx, getState());
		enterRule(_localctx, 224, RULE_controlStatement);
		int _la;
		try {
			int _alt;
			setState(2505);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,324,_ctx) ) {
			case 1:
				_localctx = new ReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2406);
				match(RETURN);
				setState(2407);
				valueExpression(0);
				}
				break;
			case 2:
				_localctx = new AssignmentStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2408);
				match(SET);
				setState(2409);
				identifier();
				setState(2410);
				match(EQ);
				setState(2411);
				expression();
				}
				break;
			case 3:
				_localctx = new SimpleCaseStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2413);
				match(CASE);
				setState(2414);
				expression();
				setState(2416); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(2415);
					caseStatementWhenClause();
					}
					}
					setState(2418); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==WHEN );
				setState(2421);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(2420);
					elseClause();
					}
				}

				setState(2423);
				match(END);
				setState(2424);
				match(CASE);
				}
				break;
			case 4:
				_localctx = new SearchedCaseStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2426);
				match(CASE);
				setState(2428); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(2427);
					caseStatementWhenClause();
					}
					}
					setState(2430); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==WHEN );
				setState(2433);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(2432);
					elseClause();
					}
				}

				setState(2435);
				match(END);
				setState(2436);
				match(CASE);
				}
				break;
			case 5:
				_localctx = new IfStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(2438);
				match(IF);
				setState(2439);
				expression();
				setState(2440);
				match(THEN);
				setState(2441);
				sqlStatementList();
				setState(2445);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ELSEIF) {
					{
					{
					setState(2442);
					elseIfClause();
					}
					}
					setState(2447);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2449);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(2448);
					elseClause();
					}
				}

				setState(2451);
				match(END);
				setState(2452);
				match(IF);
				}
				break;
			case 6:
				_localctx = new IterateStatementContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(2454);
				match(ITERATE);
				setState(2455);
				identifier();
				}
				break;
			case 7:
				_localctx = new LeaveStatementContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(2456);
				match(LEAVE);
				setState(2457);
				identifier();
				}
				break;
			case 8:
				_localctx = new CompoundStatementContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(2458);
				match(T__14);
				setState(2464);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,319,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(2459);
						variableDeclaration();
						setState(2460);
						match(SEMICOLON);
						}
						} 
					}
					setState(2466);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,319,_ctx);
				}
				setState(2468);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -5262737029691214146L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & -9120583187364427405L) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & -6228115030305409L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 9149062092676919263L) != 0) || ((((_la - 257)) & ~0x3f) == 0 && ((1L << (_la - 257)) & 4323455915874252663L) != 0)) {
					{
					setState(2467);
					sqlStatementList();
					}
				}

				setState(2470);
				match(END);
				}
				break;
			case 9:
				_localctx = new LoopStatementContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(2474);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,321,_ctx) ) {
				case 1:
					{
					setState(2471);
					((LoopStatementContext)_localctx).label = identifier();
					setState(2472);
					match(T__9);
					}
					break;
				}
				setState(2476);
				match(LOOP);
				setState(2477);
				sqlStatementList();
				setState(2478);
				match(END);
				setState(2479);
				match(LOOP);
				}
				break;
			case 10:
				_localctx = new WhileStatementContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(2484);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,322,_ctx) ) {
				case 1:
					{
					setState(2481);
					((WhileStatementContext)_localctx).label = identifier();
					setState(2482);
					match(T__9);
					}
					break;
				}
				setState(2486);
				match(WHILE);
				setState(2487);
				expression();
				setState(2488);
				match(DO);
				setState(2489);
				sqlStatementList();
				setState(2490);
				match(END);
				setState(2491);
				match(WHILE);
				}
				break;
			case 11:
				_localctx = new RepeatStatementContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(2496);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,323,_ctx) ) {
				case 1:
					{
					setState(2493);
					((RepeatStatementContext)_localctx).label = identifier();
					setState(2494);
					match(T__9);
					}
					break;
				}
				setState(2498);
				match(REPEAT);
				setState(2499);
				sqlStatementList();
				setState(2500);
				match(UNTIL);
				setState(2501);
				expression();
				setState(2502);
				match(END);
				setState(2503);
				match(REPEAT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CaseStatementWhenClauseContext extends ParserRuleContext {
		public TerminalNode WHEN() { return getToken(StructuresSqlParserParser.WHEN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode THEN() { return getToken(StructuresSqlParserParser.THEN, 0); }
		public SqlStatementListContext sqlStatementList() {
			return getRuleContext(SqlStatementListContext.class,0);
		}
		public CaseStatementWhenClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseStatementWhenClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCaseStatementWhenClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCaseStatementWhenClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCaseStatementWhenClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseStatementWhenClauseContext caseStatementWhenClause() throws RecognitionException {
		CaseStatementWhenClauseContext _localctx = new CaseStatementWhenClauseContext(_ctx, getState());
		enterRule(_localctx, 226, RULE_caseStatementWhenClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2507);
			match(WHEN);
			setState(2508);
			expression();
			setState(2509);
			match(THEN);
			setState(2510);
			sqlStatementList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElseIfClauseContext extends ParserRuleContext {
		public TerminalNode ELSEIF() { return getToken(StructuresSqlParserParser.ELSEIF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode THEN() { return getToken(StructuresSqlParserParser.THEN, 0); }
		public SqlStatementListContext sqlStatementList() {
			return getRuleContext(SqlStatementListContext.class,0);
		}
		public ElseIfClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseIfClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterElseIfClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitElseIfClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitElseIfClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseIfClauseContext elseIfClause() throws RecognitionException {
		ElseIfClauseContext _localctx = new ElseIfClauseContext(_ctx, getState());
		enterRule(_localctx, 228, RULE_elseIfClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2512);
			match(ELSEIF);
			setState(2513);
			expression();
			setState(2514);
			match(THEN);
			setState(2515);
			sqlStatementList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElseClauseContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(StructuresSqlParserParser.ELSE, 0); }
		public SqlStatementListContext sqlStatementList() {
			return getRuleContext(SqlStatementListContext.class,0);
		}
		public ElseClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterElseClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitElseClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitElseClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseClauseContext elseClause() throws RecognitionException {
		ElseClauseContext _localctx = new ElseClauseContext(_ctx, getState());
		enterRule(_localctx, 230, RULE_elseClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2517);
			match(ELSE);
			setState(2518);
			sqlStatementList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableDeclarationContext extends ParserRuleContext {
		public TerminalNode DECLARE() { return getToken(StructuresSqlParserParser.DECLARE, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode DEFAULT() { return getToken(StructuresSqlParserParser.DEFAULT, 0); }
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 232, RULE_variableDeclaration);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2520);
			match(DECLARE);
			setState(2521);
			identifier();
			setState(2526);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,325,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(2522);
					match(T__1);
					setState(2523);
					identifier();
					}
					} 
				}
				setState(2528);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,325,_ctx);
			}
			setState(2529);
			type(0);
			setState(2532);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFAULT) {
				{
				setState(2530);
				match(DEFAULT);
				setState(2531);
				valueExpression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SqlStatementListContext extends ParserRuleContext {
		public List<ControlStatementContext> controlStatement() {
			return getRuleContexts(ControlStatementContext.class);
		}
		public ControlStatementContext controlStatement(int i) {
			return getRuleContext(ControlStatementContext.class,i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(StructuresSqlParserParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(StructuresSqlParserParser.SEMICOLON, i);
		}
		public SqlStatementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sqlStatementList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSqlStatementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSqlStatementList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSqlStatementList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SqlStatementListContext sqlStatementList() throws RecognitionException {
		SqlStatementListContext _localctx = new SqlStatementListContext(_ctx, getState());
		enterRule(_localctx, 234, RULE_sqlStatementList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2537); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(2534);
					controlStatement();
					setState(2535);
					match(SEMICOLON);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(2539); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,327,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrivilegeContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(StructuresSqlParserParser.CREATE, 0); }
		public TerminalNode SELECT() { return getToken(StructuresSqlParserParser.SELECT, 0); }
		public TerminalNode DELETE() { return getToken(StructuresSqlParserParser.DELETE, 0); }
		public TerminalNode INSERT() { return getToken(StructuresSqlParserParser.INSERT, 0); }
		public TerminalNode UPDATE() { return getToken(StructuresSqlParserParser.UPDATE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public PrivilegeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_privilege; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPrivilege(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPrivilege(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPrivilege(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrivilegeContext privilege() throws RecognitionException {
		PrivilegeContext _localctx = new PrivilegeContext(_ctx, getState());
		enterRule(_localctx, 236, RULE_privilege);
		try {
			setState(2547);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,328,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2541);
				match(CREATE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2542);
				match(SELECT);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2543);
				match(DELETE);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(2544);
				match(INSERT);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(2545);
				match(UPDATE);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(2546);
				identifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EntityKindContext extends ParserRuleContext {
		public TerminalNode TABLE() { return getToken(StructuresSqlParserParser.TABLE, 0); }
		public TerminalNode SCHEMA() { return getToken(StructuresSqlParserParser.SCHEMA, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public EntityKindContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entityKind; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterEntityKind(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitEntityKind(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitEntityKind(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EntityKindContext entityKind() throws RecognitionException {
		EntityKindContext _localctx = new EntityKindContext(_ctx, getState());
		enterRule(_localctx, 238, RULE_entityKind);
		try {
			setState(2552);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,329,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2549);
				match(TABLE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2550);
				match(SCHEMA);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2551);
				identifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GrantObjectContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EntityKindContext entityKind() {
			return getRuleContext(EntityKindContext.class,0);
		}
		public GrantObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grantObject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterGrantObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitGrantObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitGrantObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrantObjectContext grantObject() throws RecognitionException {
		GrantObjectContext _localctx = new GrantObjectContext(_ctx, getState());
		enterRule(_localctx, 240, RULE_grantObject);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2555);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,330,_ctx) ) {
			case 1:
				{
				setState(2554);
				entityKind();
				}
				break;
			}
			setState(2557);
			qualifiedName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QualifiedNameContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQualifiedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQualifiedName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 242, RULE_qualifiedName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2559);
			identifier();
			setState(2564);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,331,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(2560);
					match(T__0);
					setState(2561);
					identifier();
					}
					} 
				}
				setState(2566);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,331,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueryPeriodContext extends ParserRuleContext {
		public ValueExpressionContext end;
		public TerminalNode FOR() { return getToken(StructuresSqlParserParser.FOR, 0); }
		public RangeTypeContext rangeType() {
			return getRuleContext(RangeTypeContext.class,0);
		}
		public TerminalNode AS() { return getToken(StructuresSqlParserParser.AS, 0); }
		public TerminalNode OF() { return getToken(StructuresSqlParserParser.OF, 0); }
		public ValueExpressionContext valueExpression() {
			return getRuleContext(ValueExpressionContext.class,0);
		}
		public QueryPeriodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryPeriod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQueryPeriod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQueryPeriod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQueryPeriod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryPeriodContext queryPeriod() throws RecognitionException {
		QueryPeriodContext _localctx = new QueryPeriodContext(_ctx, getState());
		enterRule(_localctx, 244, RULE_queryPeriod);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2567);
			match(FOR);
			setState(2568);
			rangeType();
			setState(2569);
			match(T__10);
			setState(2570);
			match(OF);
			setState(2571);
			((QueryPeriodContext)_localctx).end = valueExpression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RangeTypeContext extends ParserRuleContext {
		public TerminalNode TIMESTAMP() { return getToken(StructuresSqlParserParser.TIMESTAMP, 0); }
		public TerminalNode VERSION() { return getToken(StructuresSqlParserParser.VERSION, 0); }
		public RangeTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rangeType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRangeType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRangeType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRangeType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RangeTypeContext rangeType() throws RecognitionException {
		RangeTypeContext _localctx = new RangeTypeContext(_ctx, getState());
		enterRule(_localctx, 246, RULE_rangeType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2573);
			_la = _input.LA(1);
			if ( !(_la==TIMESTAMP || _la==VERSION) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GrantorContext extends ParserRuleContext {
		public GrantorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grantor; }
	 
		public GrantorContext() { }
		public void copyFrom(GrantorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CurrentUserGrantorContext extends GrantorContext {
		public TerminalNode CURRENT_USER() { return getToken(StructuresSqlParserParser.CURRENT_USER, 0); }
		public CurrentUserGrantorContext(GrantorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCurrentUserGrantor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCurrentUserGrantor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCurrentUserGrantor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SpecifiedPrincipalContext extends GrantorContext {
		public PrincipalContext principal() {
			return getRuleContext(PrincipalContext.class,0);
		}
		public SpecifiedPrincipalContext(GrantorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterSpecifiedPrincipal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitSpecifiedPrincipal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitSpecifiedPrincipal(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CurrentRoleGrantorContext extends GrantorContext {
		public TerminalNode CURRENT_ROLE() { return getToken(StructuresSqlParserParser.CURRENT_ROLE, 0); }
		public CurrentRoleGrantorContext(GrantorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterCurrentRoleGrantor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitCurrentRoleGrantor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitCurrentRoleGrantor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrantorContext grantor() throws RecognitionException {
		GrantorContext _localctx = new GrantorContext(_ctx, getState());
		enterRule(_localctx, 248, RULE_grantor);
		try {
			setState(2578);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case T__3:
			case T__4:
			case T__6:
			case T__8:
			case T__9:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__17:
			case CALL:
			case CALLED:
			case CASCADE:
			case CATALOG:
			case CATALOGS:
			case COLUMN:
			case COLUMNS:
			case COMMENT:
			case COMMIT:
			case COMMITTED:
			case CONDITIONAL:
			case COUNT:
			case COPARTITION:
			case CURRENT:
			case DATA:
			case DATE:
			case DAY:
			case DECLARE:
			case DEFAULT:
			case DEFINE:
			case DEFINER:
			case DENY:
			case DESC:
			case DESCRIPTOR:
			case DETERMINISTIC:
			case DISTRIBUTED:
			case DO:
			case DOUBLE:
			case EMPTY:
			case ELSEIF:
			case ENCODING:
			case ERROR:
			case EXCLUDING:
			case EXPLAIN:
			case FETCH:
			case FILTER:
			case FINAL:
			case FIRST:
			case FOLLOWING:
			case FORMAT:
			case FUNCTION:
			case FUNCTIONS:
			case GRACE:
			case GRANT:
			case GRANTED:
			case GRANTS:
			case GRAPHVIZ:
			case GROUPS:
			case HOUR:
			case IF:
			case IGNORE:
			case IMMEDIATE:
			case INCLUDING:
			case INITIAL:
			case INPUT:
			case INTERVAL:
			case INVOKER:
			case IO:
			case ISOLATION:
			case ITERATE:
			case JSON:
			case KEEP:
			case KEY:
			case KEYS:
			case LANGUAGE:
			case LAST:
			case LATERAL:
			case LEADING:
			case LEAVE:
			case LEVEL:
			case LIMIT:
			case LOCAL:
			case LOGICAL:
			case LOOP:
			case MAP:
			case MATCH:
			case MATCHED:
			case MATCHES:
			case MATCH_RECOGNIZE:
			case MATERIALIZED:
			case MEASURES:
			case MERGE:
			case MINUTE:
			case MONTH:
			case NESTED:
			case NEXT:
			case NFC:
			case NFD:
			case NFKC:
			case NFKD:
			case NO:
			case NONE:
			case NULLIF:
			case NULLS:
			case OBJECT:
			case OF:
			case OFFSET:
			case OMIT:
			case ONE:
			case ONLY:
			case OPTION:
			case ORDINALITY:
			case OUTPUT:
			case OVER:
			case OVERFLOW:
			case PARTITION:
			case PARTITIONS:
			case PASSING:
			case PAST:
			case PATH:
			case PATTERN:
			case PER:
			case PERIOD:
			case PERMUTE:
			case PLAN:
			case POSITION:
			case PRECEDING:
			case PRECISION:
			case PRIVILEGES:
			case PROPERTIES:
			case PRUNE:
			case QUOTES:
			case RANGE:
			case READ:
			case REFRESH:
			case RENAME:
			case REPEAT:
			case REPEATABLE:
			case REPLACE:
			case RESET:
			case RESPECT:
			case RESTRICT:
			case RETURN:
			case RETURNING:
			case RETURNS:
			case REVOKE:
			case ROLE:
			case ROLES:
			case ROLLBACK:
			case ROW:
			case ROWS:
			case RUNNING:
			case SCALAR:
			case SCHEMA:
			case SCHEMAS:
			case SECOND:
			case SECURITY:
			case SEEK:
			case SERIALIZABLE:
			case SESSION:
			case SET:
			case SETS:
			case SHOW:
			case SOME:
			case START:
			case STATS:
			case SUBSET:
			case SUBSTRING:
			case SYSTEM:
			case TABLES:
			case TABLESAMPLE:
			case TEXT:
			case TEXT_STRING:
			case TIES:
			case TIME:
			case TIMESTAMP:
			case TO:
			case TRAILING:
			case TRANSACTION:
			case TRUNCATE:
			case TRY_CAST:
			case TYPE:
			case UNBOUNDED:
			case UNCOMMITTED:
			case UNCONDITIONAL:
			case UNIQUE:
			case UNKNOWN:
			case UNMATCHED:
			case UNTIL:
			case UPDATE:
			case USE:
			case USER:
			case UTF16:
			case UTF32:
			case UTF8:
			case VALIDATE:
			case VALUE:
			case VERBOSE:
			case VERSION:
			case VIEW:
			case WHILE:
			case WINDOW:
			case WITHIN:
			case WITHOUT:
			case WORK:
			case WRAPPER:
			case WRITE:
			case YEAR:
			case ZONE:
			case IDENTIFIER:
			case DIGIT_IDENTIFIER:
			case QUOTED_IDENTIFIER:
			case BACKQUOTED_IDENTIFIER:
				_localctx = new SpecifiedPrincipalContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2575);
				principal();
				}
				break;
			case CURRENT_USER:
				_localctx = new CurrentUserGrantorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2576);
				match(CURRENT_USER);
				}
				break;
			case CURRENT_ROLE:
				_localctx = new CurrentRoleGrantorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2577);
				match(CURRENT_ROLE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrincipalContext extends ParserRuleContext {
		public PrincipalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_principal; }
	 
		public PrincipalContext() { }
		public void copyFrom(PrincipalContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnspecifiedPrincipalContext extends PrincipalContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public UnspecifiedPrincipalContext(PrincipalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterUnspecifiedPrincipal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitUnspecifiedPrincipal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitUnspecifiedPrincipal(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UserPrincipalContext extends PrincipalContext {
		public TerminalNode USER() { return getToken(StructuresSqlParserParser.USER, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public UserPrincipalContext(PrincipalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterUserPrincipal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitUserPrincipal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitUserPrincipal(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RolePrincipalContext extends PrincipalContext {
		public TerminalNode ROLE() { return getToken(StructuresSqlParserParser.ROLE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public RolePrincipalContext(PrincipalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRolePrincipal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRolePrincipal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRolePrincipal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrincipalContext principal() throws RecognitionException {
		PrincipalContext _localctx = new PrincipalContext(_ctx, getState());
		enterRule(_localctx, 250, RULE_principal);
		try {
			setState(2585);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,333,_ctx) ) {
			case 1:
				_localctx = new UnspecifiedPrincipalContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2580);
				identifier();
				}
				break;
			case 2:
				_localctx = new UserPrincipalContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2581);
				match(USER);
				setState(2582);
				identifier();
				}
				break;
			case 3:
				_localctx = new RolePrincipalContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2583);
				match(ROLE);
				setState(2584);
				identifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RolesContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public RolesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_roles; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterRoles(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitRoles(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitRoles(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RolesContext roles() throws RecognitionException {
		RolesContext _localctx = new RolesContext(_ctx, getState());
		enterRule(_localctx, 252, RULE_roles);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2587);
			identifier();
			setState(2592);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(2588);
				match(T__1);
				setState(2589);
				identifier();
				}
				}
				setState(2594);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrivilegeOrRoleContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(StructuresSqlParserParser.CREATE, 0); }
		public TerminalNode SELECT() { return getToken(StructuresSqlParserParser.SELECT, 0); }
		public TerminalNode DELETE() { return getToken(StructuresSqlParserParser.DELETE, 0); }
		public TerminalNode INSERT() { return getToken(StructuresSqlParserParser.INSERT, 0); }
		public TerminalNode UPDATE() { return getToken(StructuresSqlParserParser.UPDATE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public PrivilegeOrRoleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_privilegeOrRole; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterPrivilegeOrRole(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitPrivilegeOrRole(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitPrivilegeOrRole(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrivilegeOrRoleContext privilegeOrRole() throws RecognitionException {
		PrivilegeOrRoleContext _localctx = new PrivilegeOrRoleContext(_ctx, getState());
		enterRule(_localctx, 254, RULE_privilegeOrRole);
		try {
			setState(2601);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,335,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2595);
				match(CREATE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2596);
				match(SELECT);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2597);
				match(DELETE);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(2598);
				match(INSERT);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(2599);
				match(UPDATE);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(2600);
				identifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdentifierContext extends ParserRuleContext {
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
	 
		public IdentifierContext() { }
		public void copyFrom(IdentifierContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BackQuotedIdentifierContext extends IdentifierContext {
		public TerminalNode BACKQUOTED_IDENTIFIER() { return getToken(StructuresSqlParserParser.BACKQUOTED_IDENTIFIER, 0); }
		public BackQuotedIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterBackQuotedIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitBackQuotedIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitBackQuotedIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class QuotedIdentifierContext extends IdentifierContext {
		public TerminalNode QUOTED_IDENTIFIER() { return getToken(StructuresSqlParserParser.QUOTED_IDENTIFIER, 0); }
		public QuotedIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterQuotedIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitQuotedIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitQuotedIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DigitIdentifierContext extends IdentifierContext {
		public TerminalNode DIGIT_IDENTIFIER() { return getToken(StructuresSqlParserParser.DIGIT_IDENTIFIER, 0); }
		public DigitIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterDigitIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitDigitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitDigitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnquotedIdentifierContext extends IdentifierContext {
		public TerminalNode IDENTIFIER() { return getToken(StructuresSqlParserParser.IDENTIFIER, 0); }
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public UnquotedIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterUnquotedIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitUnquotedIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitUnquotedIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 256, RULE_identifier);
		try {
			setState(2608);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				_localctx = new UnquotedIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2603);
				match(IDENTIFIER);
				}
				break;
			case QUOTED_IDENTIFIER:
				_localctx = new QuotedIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2604);
				match(QUOTED_IDENTIFIER);
				}
				break;
			case T__0:
			case T__1:
			case T__2:
			case T__3:
			case T__4:
			case T__6:
			case T__8:
			case T__9:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__17:
			case CALL:
			case CALLED:
			case CASCADE:
			case CATALOG:
			case CATALOGS:
			case COLUMN:
			case COLUMNS:
			case COMMENT:
			case COMMIT:
			case COMMITTED:
			case CONDITIONAL:
			case COUNT:
			case COPARTITION:
			case CURRENT:
			case DATA:
			case DATE:
			case DAY:
			case DECLARE:
			case DEFAULT:
			case DEFINE:
			case DEFINER:
			case DENY:
			case DESC:
			case DESCRIPTOR:
			case DETERMINISTIC:
			case DISTRIBUTED:
			case DO:
			case DOUBLE:
			case EMPTY:
			case ELSEIF:
			case ENCODING:
			case ERROR:
			case EXCLUDING:
			case EXPLAIN:
			case FETCH:
			case FILTER:
			case FINAL:
			case FIRST:
			case FOLLOWING:
			case FORMAT:
			case FUNCTION:
			case FUNCTIONS:
			case GRACE:
			case GRANT:
			case GRANTED:
			case GRANTS:
			case GRAPHVIZ:
			case GROUPS:
			case HOUR:
			case IF:
			case IGNORE:
			case IMMEDIATE:
			case INCLUDING:
			case INITIAL:
			case INPUT:
			case INTERVAL:
			case INVOKER:
			case IO:
			case ISOLATION:
			case ITERATE:
			case JSON:
			case KEEP:
			case KEY:
			case KEYS:
			case LANGUAGE:
			case LAST:
			case LATERAL:
			case LEADING:
			case LEAVE:
			case LEVEL:
			case LIMIT:
			case LOCAL:
			case LOGICAL:
			case LOOP:
			case MAP:
			case MATCH:
			case MATCHED:
			case MATCHES:
			case MATCH_RECOGNIZE:
			case MATERIALIZED:
			case MEASURES:
			case MERGE:
			case MINUTE:
			case MONTH:
			case NESTED:
			case NEXT:
			case NFC:
			case NFD:
			case NFKC:
			case NFKD:
			case NO:
			case NONE:
			case NULLIF:
			case NULLS:
			case OBJECT:
			case OF:
			case OFFSET:
			case OMIT:
			case ONE:
			case ONLY:
			case OPTION:
			case ORDINALITY:
			case OUTPUT:
			case OVER:
			case OVERFLOW:
			case PARTITION:
			case PARTITIONS:
			case PASSING:
			case PAST:
			case PATH:
			case PATTERN:
			case PER:
			case PERIOD:
			case PERMUTE:
			case PLAN:
			case POSITION:
			case PRECEDING:
			case PRECISION:
			case PRIVILEGES:
			case PROPERTIES:
			case PRUNE:
			case QUOTES:
			case RANGE:
			case READ:
			case REFRESH:
			case RENAME:
			case REPEAT:
			case REPEATABLE:
			case REPLACE:
			case RESET:
			case RESPECT:
			case RESTRICT:
			case RETURN:
			case RETURNING:
			case RETURNS:
			case REVOKE:
			case ROLE:
			case ROLES:
			case ROLLBACK:
			case ROW:
			case ROWS:
			case RUNNING:
			case SCALAR:
			case SCHEMA:
			case SCHEMAS:
			case SECOND:
			case SECURITY:
			case SEEK:
			case SERIALIZABLE:
			case SESSION:
			case SET:
			case SETS:
			case SHOW:
			case SOME:
			case START:
			case STATS:
			case SUBSET:
			case SUBSTRING:
			case SYSTEM:
			case TABLES:
			case TABLESAMPLE:
			case TEXT:
			case TEXT_STRING:
			case TIES:
			case TIME:
			case TIMESTAMP:
			case TO:
			case TRAILING:
			case TRANSACTION:
			case TRUNCATE:
			case TRY_CAST:
			case TYPE:
			case UNBOUNDED:
			case UNCOMMITTED:
			case UNCONDITIONAL:
			case UNIQUE:
			case UNKNOWN:
			case UNMATCHED:
			case UNTIL:
			case UPDATE:
			case USE:
			case USER:
			case UTF16:
			case UTF32:
			case UTF8:
			case VALIDATE:
			case VALUE:
			case VERBOSE:
			case VERSION:
			case VIEW:
			case WHILE:
			case WINDOW:
			case WITHIN:
			case WITHOUT:
			case WORK:
			case WRAPPER:
			case WRITE:
			case YEAR:
			case ZONE:
				_localctx = new UnquotedIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2605);
				nonReserved();
				}
				break;
			case BACKQUOTED_IDENTIFIER:
				_localctx = new BackQuotedIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2606);
				match(BACKQUOTED_IDENTIFIER);
				}
				break;
			case DIGIT_IDENTIFIER:
				_localctx = new DigitIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(2607);
				match(DIGIT_IDENTIFIER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumberContext extends ParserRuleContext {
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
	 
		public NumberContext() { }
		public void copyFrom(NumberContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DecimalLiteralContext extends NumberContext {
		public TerminalNode DECIMAL_VALUE() { return getToken(StructuresSqlParserParser.DECIMAL_VALUE, 0); }
		public TerminalNode MINUS() { return getToken(StructuresSqlParserParser.MINUS, 0); }
		public DecimalLiteralContext(NumberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterDecimalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitDecimalLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitDecimalLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DoubleLiteralContext extends NumberContext {
		public TerminalNode DOUBLE_VALUE() { return getToken(StructuresSqlParserParser.DOUBLE_VALUE, 0); }
		public TerminalNode MINUS() { return getToken(StructuresSqlParserParser.MINUS, 0); }
		public DoubleLiteralContext(NumberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterDoubleLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitDoubleLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitDoubleLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntegerLiteralContext extends NumberContext {
		public TerminalNode INTEGER_VALUE() { return getToken(StructuresSqlParserParser.INTEGER_VALUE, 0); }
		public TerminalNode MINUS() { return getToken(StructuresSqlParserParser.MINUS, 0); }
		public IntegerLiteralContext(NumberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitIntegerLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitIntegerLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 258, RULE_number);
		int _la;
		try {
			setState(2622);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,340,_ctx) ) {
			case 1:
				_localctx = new DecimalLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2611);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(2610);
					match(MINUS);
					}
				}

				setState(2613);
				match(DECIMAL_VALUE);
				}
				break;
			case 2:
				_localctx = new DoubleLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2615);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(2614);
					match(MINUS);
					}
				}

				setState(2617);
				match(DOUBLE_VALUE);
				}
				break;
			case 3:
				_localctx = new IntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2619);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(2618);
					match(MINUS);
					}
				}

				setState(2621);
				match(INTEGER_VALUE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AuthorizationUserContext extends ParserRuleContext {
		public AuthorizationUserContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_authorizationUser; }
	 
		public AuthorizationUserContext() { }
		public void copyFrom(AuthorizationUserContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringUserContext extends AuthorizationUserContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public StringUserContext(AuthorizationUserContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterStringUser(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitStringUser(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitStringUser(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdentifierUserContext extends AuthorizationUserContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public IdentifierUserContext(AuthorizationUserContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterIdentifierUser(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitIdentifierUser(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitIdentifierUser(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AuthorizationUserContext authorizationUser() throws RecognitionException {
		AuthorizationUserContext _localctx = new AuthorizationUserContext(_ctx, getState());
		enterRule(_localctx, 260, RULE_authorizationUser);
		try {
			setState(2626);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case T__3:
			case T__4:
			case T__6:
			case T__8:
			case T__9:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__17:
			case CALL:
			case CALLED:
			case CASCADE:
			case CATALOG:
			case CATALOGS:
			case COLUMN:
			case COLUMNS:
			case COMMENT:
			case COMMIT:
			case COMMITTED:
			case CONDITIONAL:
			case COUNT:
			case COPARTITION:
			case CURRENT:
			case DATA:
			case DATE:
			case DAY:
			case DECLARE:
			case DEFAULT:
			case DEFINE:
			case DEFINER:
			case DENY:
			case DESC:
			case DESCRIPTOR:
			case DETERMINISTIC:
			case DISTRIBUTED:
			case DO:
			case DOUBLE:
			case EMPTY:
			case ELSEIF:
			case ENCODING:
			case ERROR:
			case EXCLUDING:
			case EXPLAIN:
			case FETCH:
			case FILTER:
			case FINAL:
			case FIRST:
			case FOLLOWING:
			case FORMAT:
			case FUNCTION:
			case FUNCTIONS:
			case GRACE:
			case GRANT:
			case GRANTED:
			case GRANTS:
			case GRAPHVIZ:
			case GROUPS:
			case HOUR:
			case IF:
			case IGNORE:
			case IMMEDIATE:
			case INCLUDING:
			case INITIAL:
			case INPUT:
			case INTERVAL:
			case INVOKER:
			case IO:
			case ISOLATION:
			case ITERATE:
			case JSON:
			case KEEP:
			case KEY:
			case KEYS:
			case LANGUAGE:
			case LAST:
			case LATERAL:
			case LEADING:
			case LEAVE:
			case LEVEL:
			case LIMIT:
			case LOCAL:
			case LOGICAL:
			case LOOP:
			case MAP:
			case MATCH:
			case MATCHED:
			case MATCHES:
			case MATCH_RECOGNIZE:
			case MATERIALIZED:
			case MEASURES:
			case MERGE:
			case MINUTE:
			case MONTH:
			case NESTED:
			case NEXT:
			case NFC:
			case NFD:
			case NFKC:
			case NFKD:
			case NO:
			case NONE:
			case NULLIF:
			case NULLS:
			case OBJECT:
			case OF:
			case OFFSET:
			case OMIT:
			case ONE:
			case ONLY:
			case OPTION:
			case ORDINALITY:
			case OUTPUT:
			case OVER:
			case OVERFLOW:
			case PARTITION:
			case PARTITIONS:
			case PASSING:
			case PAST:
			case PATH:
			case PATTERN:
			case PER:
			case PERIOD:
			case PERMUTE:
			case PLAN:
			case POSITION:
			case PRECEDING:
			case PRECISION:
			case PRIVILEGES:
			case PROPERTIES:
			case PRUNE:
			case QUOTES:
			case RANGE:
			case READ:
			case REFRESH:
			case RENAME:
			case REPEAT:
			case REPEATABLE:
			case REPLACE:
			case RESET:
			case RESPECT:
			case RESTRICT:
			case RETURN:
			case RETURNING:
			case RETURNS:
			case REVOKE:
			case ROLE:
			case ROLES:
			case ROLLBACK:
			case ROW:
			case ROWS:
			case RUNNING:
			case SCALAR:
			case SCHEMA:
			case SCHEMAS:
			case SECOND:
			case SECURITY:
			case SEEK:
			case SERIALIZABLE:
			case SESSION:
			case SET:
			case SETS:
			case SHOW:
			case SOME:
			case START:
			case STATS:
			case SUBSET:
			case SUBSTRING:
			case SYSTEM:
			case TABLES:
			case TABLESAMPLE:
			case TEXT:
			case TEXT_STRING:
			case TIES:
			case TIME:
			case TIMESTAMP:
			case TO:
			case TRAILING:
			case TRANSACTION:
			case TRUNCATE:
			case TRY_CAST:
			case TYPE:
			case UNBOUNDED:
			case UNCOMMITTED:
			case UNCONDITIONAL:
			case UNIQUE:
			case UNKNOWN:
			case UNMATCHED:
			case UNTIL:
			case UPDATE:
			case USE:
			case USER:
			case UTF16:
			case UTF32:
			case UTF8:
			case VALIDATE:
			case VALUE:
			case VERBOSE:
			case VERSION:
			case VIEW:
			case WHILE:
			case WINDOW:
			case WITHIN:
			case WITHOUT:
			case WORK:
			case WRAPPER:
			case WRITE:
			case YEAR:
			case ZONE:
			case IDENTIFIER:
			case DIGIT_IDENTIFIER:
			case QUOTED_IDENTIFIER:
			case BACKQUOTED_IDENTIFIER:
				_localctx = new IdentifierUserContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2624);
				identifier();
				}
				break;
			case STRING:
			case UNICODE_STRING:
				_localctx = new StringUserContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2625);
				string();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NonReservedContext extends ParserRuleContext {
		public TerminalNode ABSENT() { return getToken(StructuresSqlParserParser.ABSENT, 0); }
		public TerminalNode ADD() { return getToken(StructuresSqlParserParser.ADD, 0); }
		public TerminalNode ADMIN() { return getToken(StructuresSqlParserParser.ADMIN, 0); }
		public TerminalNode AFTER() { return getToken(StructuresSqlParserParser.AFTER, 0); }
		public TerminalNode ALL() { return getToken(StructuresSqlParserParser.ALL, 0); }
		public TerminalNode ANALYZE() { return getToken(StructuresSqlParserParser.ANALYZE, 0); }
		public TerminalNode ANY() { return getToken(StructuresSqlParserParser.ANY, 0); }
		public TerminalNode ARRAY() { return getToken(StructuresSqlParserParser.ARRAY, 0); }
		public TerminalNode ASC() { return getToken(StructuresSqlParserParser.ASC, 0); }
		public TerminalNode AT() { return getToken(StructuresSqlParserParser.AT, 0); }
		public TerminalNode AUTHORIZATION() { return getToken(StructuresSqlParserParser.AUTHORIZATION, 0); }
		public TerminalNode BEGIN() { return getToken(StructuresSqlParserParser.BEGIN, 0); }
		public TerminalNode BERNOULLI() { return getToken(StructuresSqlParserParser.BERNOULLI, 0); }
		public TerminalNode BOTH() { return getToken(StructuresSqlParserParser.BOTH, 0); }
		public TerminalNode CALL() { return getToken(StructuresSqlParserParser.CALL, 0); }
		public TerminalNode CALLED() { return getToken(StructuresSqlParserParser.CALLED, 0); }
		public TerminalNode CASCADE() { return getToken(StructuresSqlParserParser.CASCADE, 0); }
		public TerminalNode CATALOG() { return getToken(StructuresSqlParserParser.CATALOG, 0); }
		public TerminalNode CATALOGS() { return getToken(StructuresSqlParserParser.CATALOGS, 0); }
		public TerminalNode COLUMN() { return getToken(StructuresSqlParserParser.COLUMN, 0); }
		public TerminalNode COLUMNS() { return getToken(StructuresSqlParserParser.COLUMNS, 0); }
		public TerminalNode COMMENT() { return getToken(StructuresSqlParserParser.COMMENT, 0); }
		public TerminalNode COMMIT() { return getToken(StructuresSqlParserParser.COMMIT, 0); }
		public TerminalNode COMMITTED() { return getToken(StructuresSqlParserParser.COMMITTED, 0); }
		public TerminalNode CONDITIONAL() { return getToken(StructuresSqlParserParser.CONDITIONAL, 0); }
		public TerminalNode COPARTITION() { return getToken(StructuresSqlParserParser.COPARTITION, 0); }
		public TerminalNode COUNT() { return getToken(StructuresSqlParserParser.COUNT, 0); }
		public TerminalNode CURRENT() { return getToken(StructuresSqlParserParser.CURRENT, 0); }
		public TerminalNode DATA() { return getToken(StructuresSqlParserParser.DATA, 0); }
		public TerminalNode DATE() { return getToken(StructuresSqlParserParser.DATE, 0); }
		public TerminalNode DAY() { return getToken(StructuresSqlParserParser.DAY, 0); }
		public TerminalNode DECLARE() { return getToken(StructuresSqlParserParser.DECLARE, 0); }
		public TerminalNode DEFAULT() { return getToken(StructuresSqlParserParser.DEFAULT, 0); }
		public TerminalNode DEFINE() { return getToken(StructuresSqlParserParser.DEFINE, 0); }
		public TerminalNode DEFINER() { return getToken(StructuresSqlParserParser.DEFINER, 0); }
		public TerminalNode DENY() { return getToken(StructuresSqlParserParser.DENY, 0); }
		public TerminalNode DESC() { return getToken(StructuresSqlParserParser.DESC, 0); }
		public TerminalNode DESCRIPTOR() { return getToken(StructuresSqlParserParser.DESCRIPTOR, 0); }
		public TerminalNode DETERMINISTIC() { return getToken(StructuresSqlParserParser.DETERMINISTIC, 0); }
		public TerminalNode DISTRIBUTED() { return getToken(StructuresSqlParserParser.DISTRIBUTED, 0); }
		public TerminalNode DO() { return getToken(StructuresSqlParserParser.DO, 0); }
		public TerminalNode DOUBLE() { return getToken(StructuresSqlParserParser.DOUBLE, 0); }
		public TerminalNode ELSEIF() { return getToken(StructuresSqlParserParser.ELSEIF, 0); }
		public TerminalNode EMPTY() { return getToken(StructuresSqlParserParser.EMPTY, 0); }
		public TerminalNode ENCODING() { return getToken(StructuresSqlParserParser.ENCODING, 0); }
		public TerminalNode ERROR() { return getToken(StructuresSqlParserParser.ERROR, 0); }
		public TerminalNode EXCLUDING() { return getToken(StructuresSqlParserParser.EXCLUDING, 0); }
		public TerminalNode EXPLAIN() { return getToken(StructuresSqlParserParser.EXPLAIN, 0); }
		public TerminalNode FETCH() { return getToken(StructuresSqlParserParser.FETCH, 0); }
		public TerminalNode FILTER() { return getToken(StructuresSqlParserParser.FILTER, 0); }
		public TerminalNode FINAL() { return getToken(StructuresSqlParserParser.FINAL, 0); }
		public TerminalNode FIRST() { return getToken(StructuresSqlParserParser.FIRST, 0); }
		public TerminalNode FOLLOWING() { return getToken(StructuresSqlParserParser.FOLLOWING, 0); }
		public TerminalNode FORMAT() { return getToken(StructuresSqlParserParser.FORMAT, 0); }
		public TerminalNode FUNCTION() { return getToken(StructuresSqlParserParser.FUNCTION, 0); }
		public TerminalNode FUNCTIONS() { return getToken(StructuresSqlParserParser.FUNCTIONS, 0); }
		public TerminalNode GRACE() { return getToken(StructuresSqlParserParser.GRACE, 0); }
		public TerminalNode GRANT() { return getToken(StructuresSqlParserParser.GRANT, 0); }
		public TerminalNode GRANTED() { return getToken(StructuresSqlParserParser.GRANTED, 0); }
		public TerminalNode GRANTS() { return getToken(StructuresSqlParserParser.GRANTS, 0); }
		public TerminalNode GRAPHVIZ() { return getToken(StructuresSqlParserParser.GRAPHVIZ, 0); }
		public TerminalNode GROUPS() { return getToken(StructuresSqlParserParser.GROUPS, 0); }
		public TerminalNode HOUR() { return getToken(StructuresSqlParserParser.HOUR, 0); }
		public TerminalNode IF() { return getToken(StructuresSqlParserParser.IF, 0); }
		public TerminalNode IGNORE() { return getToken(StructuresSqlParserParser.IGNORE, 0); }
		public TerminalNode IMMEDIATE() { return getToken(StructuresSqlParserParser.IMMEDIATE, 0); }
		public TerminalNode INCLUDING() { return getToken(StructuresSqlParserParser.INCLUDING, 0); }
		public TerminalNode INITIAL() { return getToken(StructuresSqlParserParser.INITIAL, 0); }
		public TerminalNode INPUT() { return getToken(StructuresSqlParserParser.INPUT, 0); }
		public TerminalNode INTERVAL() { return getToken(StructuresSqlParserParser.INTERVAL, 0); }
		public TerminalNode INVOKER() { return getToken(StructuresSqlParserParser.INVOKER, 0); }
		public TerminalNode IO() { return getToken(StructuresSqlParserParser.IO, 0); }
		public TerminalNode ITERATE() { return getToken(StructuresSqlParserParser.ITERATE, 0); }
		public TerminalNode ISOLATION() { return getToken(StructuresSqlParserParser.ISOLATION, 0); }
		public TerminalNode JSON() { return getToken(StructuresSqlParserParser.JSON, 0); }
		public TerminalNode KEEP() { return getToken(StructuresSqlParserParser.KEEP, 0); }
		public TerminalNode KEY() { return getToken(StructuresSqlParserParser.KEY, 0); }
		public TerminalNode KEYS() { return getToken(StructuresSqlParserParser.KEYS, 0); }
		public TerminalNode LANGUAGE() { return getToken(StructuresSqlParserParser.LANGUAGE, 0); }
		public TerminalNode LAST() { return getToken(StructuresSqlParserParser.LAST, 0); }
		public TerminalNode LATERAL() { return getToken(StructuresSqlParserParser.LATERAL, 0); }
		public TerminalNode LEADING() { return getToken(StructuresSqlParserParser.LEADING, 0); }
		public TerminalNode LEAVE() { return getToken(StructuresSqlParserParser.LEAVE, 0); }
		public TerminalNode LEVEL() { return getToken(StructuresSqlParserParser.LEVEL, 0); }
		public TerminalNode LIMIT() { return getToken(StructuresSqlParserParser.LIMIT, 0); }
		public TerminalNode LOCAL() { return getToken(StructuresSqlParserParser.LOCAL, 0); }
		public TerminalNode LOGICAL() { return getToken(StructuresSqlParserParser.LOGICAL, 0); }
		public TerminalNode LOOP() { return getToken(StructuresSqlParserParser.LOOP, 0); }
		public TerminalNode MAP() { return getToken(StructuresSqlParserParser.MAP, 0); }
		public TerminalNode MATCH() { return getToken(StructuresSqlParserParser.MATCH, 0); }
		public TerminalNode MATCHED() { return getToken(StructuresSqlParserParser.MATCHED, 0); }
		public TerminalNode MATCHES() { return getToken(StructuresSqlParserParser.MATCHES, 0); }
		public TerminalNode MATCH_RECOGNIZE() { return getToken(StructuresSqlParserParser.MATCH_RECOGNIZE, 0); }
		public TerminalNode MATERIALIZED() { return getToken(StructuresSqlParserParser.MATERIALIZED, 0); }
		public TerminalNode MEASURES() { return getToken(StructuresSqlParserParser.MEASURES, 0); }
		public TerminalNode MERGE() { return getToken(StructuresSqlParserParser.MERGE, 0); }
		public TerminalNode MINUTE() { return getToken(StructuresSqlParserParser.MINUTE, 0); }
		public TerminalNode MONTH() { return getToken(StructuresSqlParserParser.MONTH, 0); }
		public TerminalNode NESTED() { return getToken(StructuresSqlParserParser.NESTED, 0); }
		public TerminalNode NEXT() { return getToken(StructuresSqlParserParser.NEXT, 0); }
		public TerminalNode NFC() { return getToken(StructuresSqlParserParser.NFC, 0); }
		public TerminalNode NFD() { return getToken(StructuresSqlParserParser.NFD, 0); }
		public TerminalNode NFKC() { return getToken(StructuresSqlParserParser.NFKC, 0); }
		public TerminalNode NFKD() { return getToken(StructuresSqlParserParser.NFKD, 0); }
		public TerminalNode NO() { return getToken(StructuresSqlParserParser.NO, 0); }
		public TerminalNode NONE() { return getToken(StructuresSqlParserParser.NONE, 0); }
		public TerminalNode NULLIF() { return getToken(StructuresSqlParserParser.NULLIF, 0); }
		public TerminalNode NULLS() { return getToken(StructuresSqlParserParser.NULLS, 0); }
		public TerminalNode OBJECT() { return getToken(StructuresSqlParserParser.OBJECT, 0); }
		public TerminalNode OF() { return getToken(StructuresSqlParserParser.OF, 0); }
		public TerminalNode OFFSET() { return getToken(StructuresSqlParserParser.OFFSET, 0); }
		public TerminalNode OMIT() { return getToken(StructuresSqlParserParser.OMIT, 0); }
		public TerminalNode ONE() { return getToken(StructuresSqlParserParser.ONE, 0); }
		public TerminalNode ONLY() { return getToken(StructuresSqlParserParser.ONLY, 0); }
		public TerminalNode OPTION() { return getToken(StructuresSqlParserParser.OPTION, 0); }
		public TerminalNode ORDINALITY() { return getToken(StructuresSqlParserParser.ORDINALITY, 0); }
		public TerminalNode OUTPUT() { return getToken(StructuresSqlParserParser.OUTPUT, 0); }
		public TerminalNode OVER() { return getToken(StructuresSqlParserParser.OVER, 0); }
		public TerminalNode OVERFLOW() { return getToken(StructuresSqlParserParser.OVERFLOW, 0); }
		public TerminalNode PARTITION() { return getToken(StructuresSqlParserParser.PARTITION, 0); }
		public TerminalNode PARTITIONS() { return getToken(StructuresSqlParserParser.PARTITIONS, 0); }
		public TerminalNode PASSING() { return getToken(StructuresSqlParserParser.PASSING, 0); }
		public TerminalNode PAST() { return getToken(StructuresSqlParserParser.PAST, 0); }
		public TerminalNode PATH() { return getToken(StructuresSqlParserParser.PATH, 0); }
		public TerminalNode PATTERN() { return getToken(StructuresSqlParserParser.PATTERN, 0); }
		public TerminalNode PER() { return getToken(StructuresSqlParserParser.PER, 0); }
		public TerminalNode PERIOD() { return getToken(StructuresSqlParserParser.PERIOD, 0); }
		public TerminalNode PERMUTE() { return getToken(StructuresSqlParserParser.PERMUTE, 0); }
		public TerminalNode PLAN() { return getToken(StructuresSqlParserParser.PLAN, 0); }
		public TerminalNode POSITION() { return getToken(StructuresSqlParserParser.POSITION, 0); }
		public TerminalNode PRECEDING() { return getToken(StructuresSqlParserParser.PRECEDING, 0); }
		public TerminalNode PRECISION() { return getToken(StructuresSqlParserParser.PRECISION, 0); }
		public TerminalNode PRIVILEGES() { return getToken(StructuresSqlParserParser.PRIVILEGES, 0); }
		public TerminalNode PROPERTIES() { return getToken(StructuresSqlParserParser.PROPERTIES, 0); }
		public TerminalNode PRUNE() { return getToken(StructuresSqlParserParser.PRUNE, 0); }
		public TerminalNode QUOTES() { return getToken(StructuresSqlParserParser.QUOTES, 0); }
		public TerminalNode RANGE() { return getToken(StructuresSqlParserParser.RANGE, 0); }
		public TerminalNode READ() { return getToken(StructuresSqlParserParser.READ, 0); }
		public TerminalNode REFRESH() { return getToken(StructuresSqlParserParser.REFRESH, 0); }
		public TerminalNode RENAME() { return getToken(StructuresSqlParserParser.RENAME, 0); }
		public TerminalNode REPEAT() { return getToken(StructuresSqlParserParser.REPEAT, 0); }
		public TerminalNode REPEATABLE() { return getToken(StructuresSqlParserParser.REPEATABLE, 0); }
		public TerminalNode REPLACE() { return getToken(StructuresSqlParserParser.REPLACE, 0); }
		public TerminalNode RESET() { return getToken(StructuresSqlParserParser.RESET, 0); }
		public TerminalNode RESPECT() { return getToken(StructuresSqlParserParser.RESPECT, 0); }
		public TerminalNode RESTRICT() { return getToken(StructuresSqlParserParser.RESTRICT, 0); }
		public TerminalNode RETURN() { return getToken(StructuresSqlParserParser.RETURN, 0); }
		public TerminalNode RETURNING() { return getToken(StructuresSqlParserParser.RETURNING, 0); }
		public TerminalNode RETURNS() { return getToken(StructuresSqlParserParser.RETURNS, 0); }
		public TerminalNode REVOKE() { return getToken(StructuresSqlParserParser.REVOKE, 0); }
		public TerminalNode ROLE() { return getToken(StructuresSqlParserParser.ROLE, 0); }
		public TerminalNode ROLES() { return getToken(StructuresSqlParserParser.ROLES, 0); }
		public TerminalNode ROLLBACK() { return getToken(StructuresSqlParserParser.ROLLBACK, 0); }
		public TerminalNode ROW() { return getToken(StructuresSqlParserParser.ROW, 0); }
		public TerminalNode ROWS() { return getToken(StructuresSqlParserParser.ROWS, 0); }
		public TerminalNode RUNNING() { return getToken(StructuresSqlParserParser.RUNNING, 0); }
		public TerminalNode SCALAR() { return getToken(StructuresSqlParserParser.SCALAR, 0); }
		public TerminalNode SCHEMA() { return getToken(StructuresSqlParserParser.SCHEMA, 0); }
		public TerminalNode SCHEMAS() { return getToken(StructuresSqlParserParser.SCHEMAS, 0); }
		public TerminalNode SECOND() { return getToken(StructuresSqlParserParser.SECOND, 0); }
		public TerminalNode SECURITY() { return getToken(StructuresSqlParserParser.SECURITY, 0); }
		public TerminalNode SEEK() { return getToken(StructuresSqlParserParser.SEEK, 0); }
		public TerminalNode SERIALIZABLE() { return getToken(StructuresSqlParserParser.SERIALIZABLE, 0); }
		public TerminalNode SESSION() { return getToken(StructuresSqlParserParser.SESSION, 0); }
		public TerminalNode SET() { return getToken(StructuresSqlParserParser.SET, 0); }
		public TerminalNode SETS() { return getToken(StructuresSqlParserParser.SETS, 0); }
		public TerminalNode SHOW() { return getToken(StructuresSqlParserParser.SHOW, 0); }
		public TerminalNode SOME() { return getToken(StructuresSqlParserParser.SOME, 0); }
		public TerminalNode START() { return getToken(StructuresSqlParserParser.START, 0); }
		public TerminalNode STATS() { return getToken(StructuresSqlParserParser.STATS, 0); }
		public TerminalNode SUBSET() { return getToken(StructuresSqlParserParser.SUBSET, 0); }
		public TerminalNode SUBSTRING() { return getToken(StructuresSqlParserParser.SUBSTRING, 0); }
		public TerminalNode SYSTEM() { return getToken(StructuresSqlParserParser.SYSTEM, 0); }
		public TerminalNode TABLES() { return getToken(StructuresSqlParserParser.TABLES, 0); }
		public TerminalNode TABLESAMPLE() { return getToken(StructuresSqlParserParser.TABLESAMPLE, 0); }
		public TerminalNode TEXT() { return getToken(StructuresSqlParserParser.TEXT, 0); }
		public TerminalNode TEXT_STRING() { return getToken(StructuresSqlParserParser.TEXT_STRING, 0); }
		public TerminalNode TIES() { return getToken(StructuresSqlParserParser.TIES, 0); }
		public TerminalNode TIME() { return getToken(StructuresSqlParserParser.TIME, 0); }
		public TerminalNode TIMESTAMP() { return getToken(StructuresSqlParserParser.TIMESTAMP, 0); }
		public TerminalNode TO() { return getToken(StructuresSqlParserParser.TO, 0); }
		public TerminalNode TRAILING() { return getToken(StructuresSqlParserParser.TRAILING, 0); }
		public TerminalNode TRANSACTION() { return getToken(StructuresSqlParserParser.TRANSACTION, 0); }
		public TerminalNode TRUNCATE() { return getToken(StructuresSqlParserParser.TRUNCATE, 0); }
		public TerminalNode TRY_CAST() { return getToken(StructuresSqlParserParser.TRY_CAST, 0); }
		public TerminalNode TYPE() { return getToken(StructuresSqlParserParser.TYPE, 0); }
		public TerminalNode UNBOUNDED() { return getToken(StructuresSqlParserParser.UNBOUNDED, 0); }
		public TerminalNode UNCOMMITTED() { return getToken(StructuresSqlParserParser.UNCOMMITTED, 0); }
		public TerminalNode UNCONDITIONAL() { return getToken(StructuresSqlParserParser.UNCONDITIONAL, 0); }
		public TerminalNode UNIQUE() { return getToken(StructuresSqlParserParser.UNIQUE, 0); }
		public TerminalNode UNKNOWN() { return getToken(StructuresSqlParserParser.UNKNOWN, 0); }
		public TerminalNode UNMATCHED() { return getToken(StructuresSqlParserParser.UNMATCHED, 0); }
		public TerminalNode UNTIL() { return getToken(StructuresSqlParserParser.UNTIL, 0); }
		public TerminalNode UPDATE() { return getToken(StructuresSqlParserParser.UPDATE, 0); }
		public TerminalNode USE() { return getToken(StructuresSqlParserParser.USE, 0); }
		public TerminalNode USER() { return getToken(StructuresSqlParserParser.USER, 0); }
		public TerminalNode UTF16() { return getToken(StructuresSqlParserParser.UTF16, 0); }
		public TerminalNode UTF32() { return getToken(StructuresSqlParserParser.UTF32, 0); }
		public TerminalNode UTF8() { return getToken(StructuresSqlParserParser.UTF8, 0); }
		public TerminalNode VALIDATE() { return getToken(StructuresSqlParserParser.VALIDATE, 0); }
		public TerminalNode VALUE() { return getToken(StructuresSqlParserParser.VALUE, 0); }
		public TerminalNode VERBOSE() { return getToken(StructuresSqlParserParser.VERBOSE, 0); }
		public TerminalNode VERSION() { return getToken(StructuresSqlParserParser.VERSION, 0); }
		public TerminalNode VIEW() { return getToken(StructuresSqlParserParser.VIEW, 0); }
		public TerminalNode WHILE() { return getToken(StructuresSqlParserParser.WHILE, 0); }
		public TerminalNode WINDOW() { return getToken(StructuresSqlParserParser.WINDOW, 0); }
		public TerminalNode WITHIN() { return getToken(StructuresSqlParserParser.WITHIN, 0); }
		public TerminalNode WITHOUT() { return getToken(StructuresSqlParserParser.WITHOUT, 0); }
		public TerminalNode WORK() { return getToken(StructuresSqlParserParser.WORK, 0); }
		public TerminalNode WRAPPER() { return getToken(StructuresSqlParserParser.WRAPPER, 0); }
		public TerminalNode WRITE() { return getToken(StructuresSqlParserParser.WRITE, 0); }
		public TerminalNode YEAR() { return getToken(StructuresSqlParserParser.YEAR, 0); }
		public TerminalNode ZONE() { return getToken(StructuresSqlParserParser.ZONE, 0); }
		public NonReservedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonReserved; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).enterNonReserved(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSqlParserListener ) ((StructuresSqlParserListener)listener).exitNonReserved(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSqlParserVisitor ) return ((StructuresSqlParserVisitor<? extends T>)visitor).visitNonReserved(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonReservedContext nonReserved() throws RecognitionException {
		NonReservedContext _localctx = new NonReservedContext(_ctx, getState());
		enterRule(_localctx, 262, RULE_nonReserved);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2628);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & -5262737029699602754L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & -9120583187364427405L) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & -6228115030305409L) != 0) || ((((_la - 192)) & ~0x3f) == 0 && ((1L << (_la - 192)) & 9149062092676919263L) != 0) || ((((_la - 257)) & ~0x3f) == 0 && ((1L << (_la - 257)) & 273598576503L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 21:
			return queryTerm_sempred((QueryTermContext)_localctx, predIndex);
		case 33:
			return relation_sempred((RelationContext)_localctx, predIndex);
		case 64:
			return booleanExpression_sempred((BooleanExpressionContext)_localctx, predIndex);
		case 66:
			return valueExpression_sempred((ValueExpressionContext)_localctx, predIndex);
		case 67:
			return primaryExpression_sempred((PrimaryExpressionContext)_localctx, predIndex);
		case 87:
			return type_sempred((TypeContext)_localctx, predIndex);
		case 97:
			return rowPattern_sempred((RowPatternContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean queryTerm_sempred(QueryTermContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean relation_sempred(RelationContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean booleanExpression_sempred(BooleanExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 2);
		case 4:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean valueExpression_sempred(ValueExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 3);
		case 6:
			return precpred(_ctx, 2);
		case 7:
			return precpred(_ctx, 1);
		case 8:
			return precpred(_ctx, 5);
		}
		return true;
	}
	private boolean primaryExpression_sempred(PrimaryExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 24);
		case 10:
			return precpred(_ctx, 22);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 11:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean rowPattern_sempred(RowPatternContext _localctx, int predIndex) {
		switch (predIndex) {
		case 12:
			return precpred(_ctx, 2);
		case 13:
			return precpred(_ctx, 1);
		}
		return true;
	}

	private static final String _serializedATNSegment0 =
		"\u0004\u0001\u0143\u0a47\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007"+
		"\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007"+
		"\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007"+
		"\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007"+
		"\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007"+
		"\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007"+
		"\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007"+
		",\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u0007"+
		"1\u00022\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u0007"+
		"6\u00027\u00077\u00028\u00078\u00029\u00079\u0002:\u0007:\u0002;\u0007"+
		";\u0002<\u0007<\u0002=\u0007=\u0002>\u0007>\u0002?\u0007?\u0002@\u0007"+
		"@\u0002A\u0007A\u0002B\u0007B\u0002C\u0007C\u0002D\u0007D\u0002E\u0007"+
		"E\u0002F\u0007F\u0002G\u0007G\u0002H\u0007H\u0002I\u0007I\u0002J\u0007"+
		"J\u0002K\u0007K\u0002L\u0007L\u0002M\u0007M\u0002N\u0007N\u0002O\u0007"+
		"O\u0002P\u0007P\u0002Q\u0007Q\u0002R\u0007R\u0002S\u0007S\u0002T\u0007"+
		"T\u0002U\u0007U\u0002V\u0007V\u0002W\u0007W\u0002X\u0007X\u0002Y\u0007"+
		"Y\u0002Z\u0007Z\u0002[\u0007[\u0002\\\u0007\\\u0002]\u0007]\u0002^\u0007"+
		"^\u0002_\u0007_\u0002`\u0007`\u0002a\u0007a\u0002b\u0007b\u0002c\u0007"+
		"c\u0002d\u0007d\u0002e\u0007e\u0002f\u0007f\u0002g\u0007g\u0002h\u0007"+
		"h\u0002i\u0007i\u0002j\u0007j\u0002k\u0007k\u0002l\u0007l\u0002m\u0007"+
		"m\u0002n\u0007n\u0002o\u0007o\u0002p\u0007p\u0002q\u0007q\u0002r\u0007"+
		"r\u0002s\u0007s\u0002t\u0007t\u0002u\u0007u\u0002v\u0007v\u0002w\u0007"+
		"w\u0002x\u0007x\u0002y\u0007y\u0002z\u0007z\u0002{\u0007{\u0002|\u0007"+
		"|\u0002}\u0007}\u0002~\u0007~\u0002\u007f\u0007\u007f\u0002\u0080\u0007"+
		"\u0080\u0002\u0081\u0007\u0081\u0002\u0082\u0007\u0082\u0002\u0083\u0007"+
		"\u0083\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0005\u0006\u0129\b\u0006\n\u0006\f\u0006"+
		"\u012c\t\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u0130\b\u0006\u0003"+
		"\u0006\u0132\b\u0006\u0001\u0007\u0003\u0007\u0135\b\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b\u013d\b\b\n\b\f\b"+
		"\u0140\t\b\u0001\t\u0003\t\u0143\b\t\u0001\t\u0001\t\u0001\n\u0001\n\u0003"+
		"\n\u0149\b\n\u0001\n\u0001\n\u0001\n\u0005\n\u014e\b\n\n\n\f\n\u0151\t"+
		"\n\u0001\u000b\u0001\u000b\u0003\u000b\u0155\b\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0003\f\u015b\b\f\u0001\f\u0001\f\u0003\f\u015f\b\f\u0001\f"+
		"\u0001\f\u0003\f\u0163\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u0169"+
		"\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0005\u000f\u0172\b\u000f\n\u000f\f\u000f\u0175\t\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011"+
		"\u0003\u0011\u017d\b\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0005\u0012\u0185\b\u0012\n\u0012\f\u0012\u0188"+
		"\t\u0012\u0003\u0012\u018a\b\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0003\u0012\u018f\b\u0012\u0003\u0012\u0191\b\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u0198\b\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u019e\b\u0012\u0003"+
		"\u0012\u01a0\b\u0012\u0001\u0013\u0001\u0013\u0003\u0013\u01a4\b\u0013"+
		"\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0003\u0015\u01ae\b\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0003\u0015\u01b4\b\u0015\u0001\u0015\u0005\u0015"+
		"\u01b7\b\u0015\n\u0015\f\u0015\u01ba\t\u0015\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0005\u0016\u01c3"+
		"\b\u0016\n\u0016\f\u0016\u01c6\t\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0003\u0016\u01cc\b\u0016\u0001\u0017\u0001\u0017\u0003\u0017"+
		"\u01d0\b\u0017\u0001\u0017\u0001\u0017\u0003\u0017\u01d4\b\u0017\u0001"+
		"\u0018\u0001\u0018\u0003\u0018\u01d8\b\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0005\u0018\u01dd\b\u0018\n\u0018\f\u0018\u01e0\t\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u01e6\b\u0018\n\u0018"+
		"\f\u0018\u01e9\t\u0018\u0003\u0018\u01eb\b\u0018\u0001\u0018\u0001\u0018"+
		"\u0003\u0018\u01ef\b\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0003\u0018"+
		"\u01f4\b\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u01f8\b\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u01fe\b\u0018\n"+
		"\u0018\f\u0018\u0201\t\u0018\u0003\u0018\u0203\b\u0018\u0001\u0019\u0003"+
		"\u0019\u0206\b\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0005\u0019\u020b"+
		"\b\u0019\n\u0019\f\u0019\u020e\t\u0019\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0005\u001a\u0216\b\u001a\n\u001a"+
		"\f\u001a\u0219\t\u001a\u0003\u001a\u021b\b\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0005\u001a\u0223\b\u001a"+
		"\n\u001a\f\u001a\u0226\t\u001a\u0003\u001a\u0228\b\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0005"+
		"\u001a\u0231\b\u001a\n\u001a\f\u001a\u0234\t\u001a\u0001\u001a\u0001\u001a"+
		"\u0003\u001a\u0238\b\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0005\u001b\u023e\b\u001b\n\u001b\f\u001b\u0241\t\u001b\u0003\u001b\u0243"+
		"\b\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u0247\b\u001b\u0001\u001c"+
		"\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001d"+
		"\u0003\u001d\u0250\b\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0005\u001d\u0257\b\u001d\n\u001d\f\u001d\u025a\t\u001d\u0003"+
		"\u001d\u025c\b\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0005\u001d\u0263\b\u001d\n\u001d\f\u001d\u0266\t\u001d\u0003\u001d"+
		"\u0268\b\u001d\u0001\u001d\u0003\u001d\u026b\b\u001d\u0001\u001e\u0001"+
		"\u001e\u0003\u001e\u026f\b\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001 \u0001 \u0003 \u027a"+
		"\b \u0001 \u0003 \u027d\b \u0001 \u0001 \u0001 \u0001 \u0001 \u0003 \u0284"+
		"\b \u0001 \u0003 \u0287\b \u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001"+
		"!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001"+
		"!\u0003!\u029a\b!\u0005!\u029c\b!\n!\f!\u029f\t!\u0001\"\u0003\"\u02a2"+
		"\b\"\u0001\"\u0001\"\u0003\"\u02a6\b\"\u0001\"\u0001\"\u0003\"\u02aa\b"+
		"\"\u0001\"\u0001\"\u0003\"\u02ae\b\"\u0003\"\u02b0\b\"\u0001#\u0001#\u0001"+
		"#\u0001#\u0001#\u0001#\u0001#\u0005#\u02b9\b#\n#\f#\u02bc\t#\u0001#\u0001"+
		"#\u0003#\u02c0\b#\u0001$\u0001$\u0001$\u0001$\u0001$\u0001$\u0001$\u0003"+
		"$\u02c9\b$\u0001%\u0001%\u0001&\u0001&\u0001\'\u0001\'\u0001\'\u0003\'"+
		"\u02d2\b\'\u0001\'\u0003\'\u02d5\b\'\u0001(\u0001(\u0001(\u0001(\u0003"+
		"(\u02db\b(\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0005"+
		")\u02e5\b)\n)\f)\u02e8\t)\u0003)\u02ea\b)\u0001)\u0001)\u0001)\u0001)"+
		"\u0001)\u0005)\u02f1\b)\n)\f)\u02f4\t)\u0003)\u02f6\b)\u0001)\u0001)\u0001"+
		")\u0001)\u0005)\u02fc\b)\n)\f)\u02ff\t)\u0003)\u0301\b)\u0001)\u0003)"+
		"\u0304\b)\u0001)\u0001)\u0001)\u0003)\u0309\b)\u0001)\u0003)\u030c\b)"+
		"\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0005)\u0316"+
		"\b)\n)\f)\u0319\t)\u0003)\u031b\b)\u0001)\u0001)\u0001)\u0001)\u0005)"+
		"\u0321\b)\n)\f)\u0324\t)\u0001)\u0001)\u0003)\u0328\b)\u0001)\u0001)\u0003"+
		")\u032c\b)\u0003)\u032e\b)\u0003)\u0330\b)\u0001*\u0001*\u0001*\u0001"+
		"*\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0003"+
		"+\u033f\b+\u0003+\u0341\b+\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0001"+
		",\u0001,\u0001,\u0003,\u034c\b,\u0001-\u0001-\u0001-\u0001-\u0001-\u0001"+
		"-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001"+
		"-\u0001-\u0001-\u0001-\u0003-\u0361\b-\u0001.\u0001.\u0001.\u0001.\u0001"+
		".\u0001.\u0005.\u0369\b.\n.\f.\u036c\t.\u0001.\u0001.\u0001/\u0001/\u0001"+
		"/\u0001/\u00010\u00010\u00030\u0376\b0\u00010\u00010\u00030\u037a\b0\u0003"+
		"0\u037c\b0\u00011\u00011\u00011\u00011\u00051\u0382\b1\n1\f1\u0385\t1"+
		"\u00011\u00011\u00012\u00012\u00032\u038b\b2\u00012\u00012\u00012\u0001"+
		"2\u00012\u00012\u00012\u00012\u00012\u00052\u0396\b2\n2\f2\u0399\t2\u0001"+
		"2\u00012\u00012\u00032\u039e\b2\u00012\u00012\u00012\u00012\u00012\u0001"+
		"2\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u0001"+
		"2\u00012\u00012\u00012\u00012\u00012\u00012\u00052\u03b6\b2\n2\f2\u03b9"+
		"\t2\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u0001"+
		"2\u00012\u00012\u00032\u03c7\b2\u00012\u00012\u00012\u00032\u03cc\b2\u0001"+
		"2\u00012\u00032\u03d0\b2\u00013\u00013\u00013\u00013\u00013\u00013\u0001"+
		"3\u00013\u00033\u03da\b3\u00013\u00013\u00013\u00013\u00033\u03e0\b3\u0001"+
		"3\u00013\u00013\u00013\u00033\u03e6\b3\u00013\u00013\u00013\u00013\u0001"+
		"3\u00013\u00033\u03ee\b3\u00013\u00013\u00013\u00033\u03f3\b3\u00013\u0001"+
		"3\u00013\u00013\u00013\u00033\u03fa\b3\u00033\u03fc\b3\u00013\u00013\u0001"+
		"3\u00013\u00033\u0402\b3\u00013\u00013\u00013\u00013\u00033\u0408\b3\u0001"+
		"3\u00013\u00033\u040c\b3\u00013\u00013\u00013\u00033\u0411\b3\u00013\u0001"+
		"3\u00013\u00013\u00013\u00053\u0418\b3\n3\f3\u041b\t3\u00013\u00013\u0003"+
		"3\u041f\b3\u00014\u00014\u00014\u00014\u00014\u00014\u00014\u00014\u0001"+
		"4\u00014\u00054\u042b\b4\n4\f4\u042e\t4\u00014\u00014\u00014\u00014\u0001"+
		"4\u00054\u0435\b4\n4\f4\u0438\t4\u00034\u043a\b4\u00015\u00015\u00016"+
		"\u00016\u00016\u00016\u00016\u00036\u0443\b6\u00017\u00017\u00017\u0003"+
		"7\u0448\b7\u00017\u00017\u00017\u00037\u044d\b7\u00037\u044f\b7\u0001"+
		"8\u00018\u00018\u00018\u00018\u00058\u0456\b8\n8\f8\u0459\t8\u00038\u045b"+
		"\b8\u00018\u00018\u00018\u00018\u00058\u0461\b8\n8\f8\u0464\t8\u00038"+
		"\u0466\b8\u00018\u00018\u00019\u00019\u00019\u00039\u046d\b9\u00019\u0001"+
		"9\u00019\u00039\u0472\b9\u0001:\u0001:\u0001:\u0001:\u0001:\u0001:\u0001"+
		":\u0005:\u047b\b:\n:\f:\u047e\t:\u0003:\u0480\b:\u0001:\u0001:\u0003:"+
		"\u0484\b:\u0003:\u0486\b:\u0001:\u0001:\u0001:\u0001:\u0001:\u0001:\u0003"+
		":\u048e\b:\u0001:\u0001:\u0001:\u0001:\u0001:\u0001:\u0005:\u0496\b:\n"+
		":\f:\u0499\t:\u0001:\u0001:\u0001:\u0003:\u049e\b:\u0003:\u04a0\b:\u0001"+
		";\u0001;\u0001;\u0001;\u0001;\u0003;\u04a7\b;\u0001;\u0001;\u0003;\u04ab"+
		"\b;\u0003;\u04ad\b;\u0001;\u0001;\u0001;\u0001;\u0001;\u0003;\u04b4\b"+
		";\u0001;\u0001;\u0003;\u04b8\b;\u0003;\u04ba\b;\u0003;\u04bc\b;\u0001"+
		"<\u0001<\u0001<\u0001<\u0001<\u0005<\u04c3\b<\n<\f<\u04c6\t<\u0001<\u0001"+
		"<\u0001<\u0001<\u0001<\u0001<\u0001<\u0001<\u0003<\u04d0\b<\u0001=\u0001"+
		"=\u0003=\u04d4\b=\u0001>\u0001>\u0001>\u0001>\u0001>\u0001>\u0005>\u04dc"+
		"\b>\n>\f>\u04df\t>\u0001>\u0001>\u0001?\u0001?\u0001@\u0001@\u0001@\u0003"+
		"@\u04e8\b@\u0001@\u0001@\u0003@\u04ec\b@\u0001@\u0001@\u0001@\u0001@\u0001"+
		"@\u0001@\u0005@\u04f4\b@\n@\f@\u04f7\t@\u0001A\u0001A\u0001A\u0001A\u0001"+
		"A\u0001A\u0001A\u0001A\u0001A\u0001A\u0003A\u0503\bA\u0001A\u0001A\u0001"+
		"A\u0001A\u0001A\u0001A\u0003A\u050b\bA\u0001A\u0001A\u0001A\u0001A\u0001"+
		"A\u0005A\u0512\bA\nA\fA\u0515\tA\u0001A\u0001A\u0001A\u0003A\u051a\bA"+
		"\u0001A\u0001A\u0001A\u0001A\u0001A\u0001A\u0003A\u0522\bA\u0001A\u0001"+
		"A\u0001A\u0001A\u0003A\u0528\bA\u0001A\u0001A\u0003A\u052c\bA\u0001A\u0001"+
		"A\u0001A\u0003A\u0531\bA\u0001A\u0001A\u0001A\u0003A\u0536\bA\u0001B\u0001"+
		"B\u0001B\u0001B\u0003B\u053c\bB\u0001B\u0001B\u0001B\u0001B\u0001B\u0001"+
		"B\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0005B\u054a\bB\nB\fB\u054d"+
		"\tB\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0004C\u0568\bC\u000bC\fC\u0569\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0005C\u0573\bC\nC\fC\u0576"+
		"\tC\u0001C\u0001C\u0001C\u0001C\u0001C\u0003C\u057d\bC\u0001C\u0001C\u0001"+
		"C\u0003C\u0582\bC\u0001C\u0001C\u0001C\u0003C\u0587\bC\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0005C\u0592\bC\nC\fC\u0595"+
		"\tC\u0001C\u0001C\u0001C\u0003C\u059a\bC\u0001C\u0003C\u059d\bC\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0003C\u05a4\bC\u0001C\u0001C\u0001C\u0003"+
		"C\u05a9\bC\u0001C\u0003C\u05ac\bC\u0001C\u0003C\u05af\bC\u0001C\u0001"+
		"C\u0001C\u0003C\u05b4\bC\u0001C\u0001C\u0001C\u0005C\u05b9\bC\nC\fC\u05bc"+
		"\tC\u0003C\u05be\bC\u0001C\u0001C\u0001C\u0001C\u0001C\u0005C\u05c5\b"+
		"C\nC\fC\u05c8\tC\u0003C\u05ca\bC\u0001C\u0001C\u0003C\u05ce\bC\u0001C"+
		"\u0003C\u05d1\bC\u0001C\u0003C\u05d4\bC\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0005C\u05e1\bC\nC\fC\u05e4"+
		"\tC\u0003C\u05e6\bC\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0004C\u05f7\bC\u000b"+
		"C\fC\u05f8\u0001C\u0001C\u0003C\u05fd\bC\u0001C\u0001C\u0001C\u0001C\u0004"+
		"C\u0603\bC\u000bC\fC\u0604\u0001C\u0001C\u0003C\u0609\bC\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0005"+
		"C\u0620\bC\nC\fC\u0623\tC\u0003C\u0625\bC\u0001C\u0001C\u0001C\u0001C"+
		"\u0001C\u0001C\u0001C\u0003C\u062e\bC\u0001C\u0001C\u0001C\u0001C\u0003"+
		"C\u0634\bC\u0001C\u0001C\u0001C\u0001C\u0003C\u063a\bC\u0001C\u0001C\u0001"+
		"C\u0001C\u0003C\u0640\bC\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0003C\u0649\bC\u0001C\u0003C\u064c\bC\u0001C\u0003C\u064f\bC\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0003C\u0662\bC\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0003C\u066b\bC\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0005C\u067f\bC\nC\fC\u0682\tC\u0003"+
		"C\u0684\bC\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0003"+
		"C\u068e\bC\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0003C\u0697"+
		"\bC\u0001C\u0001C\u0001C\u0001C\u0003C\u069d\bC\u0001C\u0001C\u0001C\u0001"+
		"C\u0003C\u06a3\bC\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0001C\u0003C\u06ae\bC\u0003C\u06b0\bC\u0001C\u0001C\u0001C\u0003C\u06b5"+
		"\bC\u0001C\u0001C\u0001C\u0001C\u0001C\u0003C\u06bc\bC\u0003C\u06be\b"+
		"C\u0001C\u0001C\u0001C\u0001C\u0003C\u06c4\bC\u0001C\u0001C\u0001C\u0001"+
		"C\u0003C\u06ca\bC\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0005"+
		"C\u06d3\bC\nC\fC\u06d6\tC\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0003"+
		"C\u06de\bC\u0001C\u0001C\u0001C\u0003C\u06e3\bC\u0001C\u0001C\u0001C\u0003"+
		"C\u06e8\bC\u0003C\u06ea\bC\u0003C\u06ec\bC\u0001C\u0001C\u0001C\u0001"+
		"C\u0003C\u06f2\bC\u0003C\u06f4\bC\u0001C\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0005C\u06fc\bC\nC\fC\u06ff\tC\u0001C\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0003C\u0707\bC\u0003C\u0709\bC\u0001C\u0001C\u0001C\u0001C\u0003C\u070f"+
		"\bC\u0003C\u0711\bC\u0001C\u0003C\u0714\bC\u0001C\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0005C\u071e\bC\nC\fC\u0721\tC\u0001D\u0001"+
		"D\u0001D\u0001D\u0001D\u0003D\u0728\bD\u0001D\u0001D\u0001D\u0001D\u0005"+
		"D\u072e\bD\nD\fD\u0731\tD\u0003D\u0733\bD\u0001E\u0001E\u0001E\u0003E"+
		"\u0738\bE\u0001F\u0001F\u0001F\u0003F\u073d\bF\u0001G\u0001G\u0001G\u0001"+
		"G\u0001H\u0001H\u0001I\u0001I\u0001I\u0001I\u0003I\u0749\bI\u0001J\u0001"+
		"J\u0003J\u074d\bJ\u0001J\u0001J\u0003J\u0751\bJ\u0001J\u0003J\u0754\b"+
		"J\u0003J\u0756\bJ\u0001K\u0001K\u0001K\u0001K\u0001K\u0001K\u0003K\u075e"+
		"\bK\u0001L\u0003L\u0761\bL\u0001L\u0001L\u0001L\u0001L\u0001L\u0001L\u0001"+
		"L\u0001L\u0003L\u076b\bL\u0001M\u0001M\u0001N\u0001N\u0001N\u0001N\u0003"+
		"N\u0773\bN\u0001O\u0001O\u0001O\u0001O\u0003O\u0779\bO\u0003O\u077b\b"+
		"O\u0001P\u0001P\u0001P\u0001P\u0001P\u0001P\u0003P\u0783\bP\u0001Q\u0001"+
		"Q\u0001R\u0001R\u0001S\u0001S\u0001T\u0001T\u0003T\u078d\bT\u0001T\u0001"+
		"T\u0001T\u0001T\u0003T\u0793\bT\u0001U\u0001U\u0001V\u0001V\u0001W\u0001"+
		"W\u0001W\u0001W\u0001W\u0001W\u0005W\u079f\bW\nW\fW\u07a2\tW\u0001W\u0001"+
		"W\u0001W\u0001W\u0001W\u0001W\u0003W\u07aa\bW\u0001W\u0001W\u0001W\u0001"+
		"W\u0001W\u0003W\u07b1\bW\u0001W\u0001W\u0001W\u0003W\u07b6\bW\u0001W\u0001"+
		"W\u0001W\u0001W\u0001W\u0003W\u07bd\bW\u0001W\u0001W\u0001W\u0001W\u0001"+
		"W\u0001W\u0001W\u0001W\u0003W\u07c7\bW\u0001W\u0001W\u0001W\u0003W\u07cc"+
		"\bW\u0001W\u0001W\u0001W\u0001W\u0001W\u0003W\u07d3\bW\u0001W\u0001W\u0001"+
		"W\u0001W\u0001W\u0001W\u0001W\u0001W\u0001W\u0001W\u0001W\u0001W\u0001"+
		"W\u0001W\u0001W\u0001W\u0001W\u0001W\u0001W\u0001W\u0001W\u0001W\u0005"+
		"W\u07eb\bW\nW\fW\u07ee\tW\u0001W\u0001W\u0003W\u07f2\bW\u0003W\u07f4\b"+
		"W\u0001W\u0001W\u0001W\u0001W\u0001W\u0003W\u07fb\bW\u0005W\u07fd\bW\n"+
		"W\fW\u0800\tW\u0001X\u0001X\u0001X\u0001X\u0003X\u0806\bX\u0001Y\u0001"+
		"Y\u0003Y\u080a\bY\u0001Z\u0001Z\u0001Z\u0001Z\u0001Z\u0001[\u0001[\u0001"+
		"[\u0001[\u0001[\u0001[\u0001\\\u0001\\\u0001\\\u0001\\\u0003\\\u081b\b"+
		"\\\u0001\\\u0001\\\u0001\\\u0001\\\u0001\\\u0001\\\u0001\\\u0001\\\u0001"+
		"\\\u0001\\\u0001\\\u0005\\\u0828\b\\\n\\\f\\\u082b\t\\\u0001\\\u0001\\"+
		"\u0001\\\u0001\\\u0003\\\u0831\b\\\u0001\\\u0001\\\u0001\\\u0001\\\u0001"+
		"\\\u0001\\\u0001\\\u0003\\\u083a\b\\\u0001\\\u0001\\\u0001\\\u0001\\\u0001"+
		"\\\u0001\\\u0005\\\u0842\b\\\n\\\f\\\u0845\t\\\u0001\\\u0001\\\u0003\\"+
		"\u0849\b\\\u0001\\\u0001\\\u0001\\\u0001\\\u0001\\\u0005\\\u0850\b\\\n"+
		"\\\f\\\u0853\t\\\u0001\\\u0001\\\u0003\\\u0857\b\\\u0001]\u0001]\u0001"+
		"]\u0001]\u0001]\u0001]\u0003]\u085f\b]\u0001^\u0001^\u0001^\u0001^\u0005"+
		"^\u0865\b^\n^\f^\u0868\t^\u0003^\u086a\b^\u0001^\u0001^\u0001^\u0001^"+
		"\u0003^\u0870\b^\u0001^\u0003^\u0873\b^\u0001^\u0001^\u0001^\u0001^\u0001"+
		"^\u0003^\u087a\b^\u0001^\u0001^\u0001^\u0001^\u0005^\u0880\b^\n^\f^\u0883"+
		"\t^\u0003^\u0885\b^\u0001^\u0001^\u0001^\u0001^\u0005^\u088b\b^\n^\f^"+
		"\u088e\t^\u0003^\u0890\b^\u0001_\u0001_\u0001_\u0001_\u0001_\u0001_\u0001"+
		"_\u0001_\u0001_\u0001_\u0001_\u0001_\u0001_\u0001_\u0001_\u0001_\u0001"+
		"_\u0001_\u0001_\u0001_\u0001_\u0001_\u0001_\u0001_\u0003_\u08aa\b_\u0001"+
		"`\u0001`\u0001`\u0001`\u0001`\u0001`\u0001`\u0001`\u0001`\u0003`\u08b5"+
		"\b`\u0001a\u0001a\u0001a\u0003a\u08ba\ba\u0001a\u0001a\u0001a\u0001a\u0001"+
		"a\u0005a\u08c1\ba\na\fa\u08c4\ta\u0001b\u0001b\u0001b\u0001b\u0001b\u0001"+
		"b\u0001b\u0001b\u0005b\u08ce\bb\nb\fb\u08d1\tb\u0001b\u0001b\u0001b\u0001"+
		"b\u0001b\u0001b\u0001b\u0001b\u0001b\u0001b\u0001b\u0001b\u0003b\u08df"+
		"\bb\u0001c\u0001c\u0003c\u08e3\bc\u0001c\u0001c\u0003c\u08e7\bc\u0001"+
		"c\u0001c\u0003c\u08eb\bc\u0001c\u0001c\u0001c\u0001c\u0003c\u08f1\bc\u0001"+
		"c\u0001c\u0003c\u08f5\bc\u0001c\u0001c\u0003c\u08f9\bc\u0001c\u0001c\u0003"+
		"c\u08fd\bc\u0003c\u08ff\bc\u0001d\u0001d\u0001d\u0001d\u0001e\u0001e\u0001"+
		"e\u0001e\u0003e\u0909\be\u0001f\u0001f\u0001f\u0001f\u0001f\u0003f\u0910"+
		"\bf\u0001g\u0001g\u0001g\u0001g\u0001g\u0001g\u0001g\u0003g\u0919\bg\u0001"+
		"h\u0001h\u0001h\u0001h\u0001h\u0003h\u0920\bh\u0001i\u0001i\u0001i\u0001"+
		"i\u0001i\u0003i\u0927\bi\u0001j\u0001j\u0001j\u0005j\u092c\bj\nj\fj\u092f"+
		"\tj\u0001k\u0001k\u0001k\u0001k\u0005k\u0935\bk\nk\fk\u0938\tk\u0001k"+
		"\u0001k\u0001l\u0001l\u0001l\u0001l\u0001l\u0005l\u0941\bl\nl\fl\u0944"+
		"\tl\u0003l\u0946\bl\u0001l\u0001l\u0001m\u0003m\u094b\bm\u0001m\u0001"+
		"m\u0001n\u0001n\u0001n\u0001o\u0001o\u0001o\u0003o\u0955\bo\u0001o\u0001"+
		"o\u0001o\u0001o\u0001o\u0001o\u0001o\u0001o\u0001o\u0001o\u0001o\u0001"+
		"o\u0001o\u0001o\u0003o\u0965\bo\u0001p\u0001p\u0001p\u0001p\u0001p\u0001"+
		"p\u0001p\u0001p\u0001p\u0001p\u0004p\u0971\bp\u000bp\fp\u0972\u0001p\u0003"+
		"p\u0976\bp\u0001p\u0001p\u0001p\u0001p\u0001p\u0004p\u097d\bp\u000bp\f"+
		"p\u097e\u0001p\u0003p\u0982\bp\u0001p\u0001p\u0001p\u0001p\u0001p\u0001"+
		"p\u0001p\u0001p\u0005p\u098c\bp\np\fp\u098f\tp\u0001p\u0003p\u0992\bp"+
		"\u0001p\u0001p\u0001p\u0001p\u0001p\u0001p\u0001p\u0001p\u0001p\u0001"+
		"p\u0001p\u0005p\u099f\bp\np\fp\u09a2\tp\u0001p\u0003p\u09a5\bp\u0001p"+
		"\u0001p\u0001p\u0001p\u0003p\u09ab\bp\u0001p\u0001p\u0001p\u0001p\u0001"+
		"p\u0001p\u0001p\u0001p\u0003p\u09b5\bp\u0001p\u0001p\u0001p\u0001p\u0001"+
		"p\u0001p\u0001p\u0001p\u0001p\u0001p\u0003p\u09c1\bp\u0001p\u0001p\u0001"+
		"p\u0001p\u0001p\u0001p\u0001p\u0003p\u09ca\bp\u0001q\u0001q\u0001q\u0001"+
		"q\u0001q\u0001r\u0001r\u0001r\u0001r\u0001r\u0001s\u0001s\u0001s\u0001"+
		"t\u0001t\u0001t\u0001t\u0005t\u09dd\bt\nt\ft\u09e0\tt\u0001t\u0001t\u0001"+
		"t\u0003t\u09e5\bt\u0001u\u0001u\u0001u\u0004u\u09ea\bu\u000bu\fu\u09eb"+
		"\u0001v\u0001v\u0001v\u0001v\u0001v\u0001v\u0003v\u09f4\bv\u0001w\u0001"+
		"w\u0001w\u0003w\u09f9\bw\u0001x\u0003x\u09fc\bx\u0001x\u0001x\u0001y\u0001"+
		"y\u0001y\u0005y\u0a03\by\ny\fy\u0a06\ty\u0001z\u0001z\u0001z\u0001z\u0001"+
		"z\u0001z\u0001{\u0001{\u0001|\u0001|\u0001|\u0003|\u0a13\b|\u0001}\u0001"+
		"}\u0001}\u0001}\u0001}\u0003}\u0a1a\b}\u0001~\u0001~\u0001~\u0005~\u0a1f"+
		"\b~\n~\f~\u0a22\t~\u0001\u007f\u0001\u007f\u0001\u007f\u0001\u007f\u0001"+
		"\u007f\u0001\u007f\u0003\u007f\u0a2a\b\u007f\u0001\u0080\u0001\u0080\u0001"+
		"\u0080\u0001\u0080\u0001\u0080\u0003\u0080\u0a31\b\u0080\u0001\u0081\u0003"+
		"\u0081\u0a34\b\u0081\u0001\u0081\u0001\u0081\u0003\u0081\u0a38\b\u0081"+
		"\u0001\u0081\u0001\u0081\u0003\u0081\u0a3c\b\u0081\u0001\u0081\u0003\u0081"+
		"\u0a3f\b\u0081\u0001\u0082\u0001\u0082\u0003\u0082\u0a43\b\u0082\u0001"+
		"\u0083\u0001\u0083\u0001\u0083\u0000\u0007*B\u0080\u0084\u0086\u00ae\u00c2"+
		"\u0084\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018"+
		"\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080"+
		"\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098"+
		"\u009a\u009c\u009e\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0"+
		"\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8"+
		"\u00ca\u00cc\u00ce\u00d0\u00d2\u00d4\u00d6\u00d8\u00da\u00dc\u00de\u00e0"+
		"\u00e2\u00e4\u00e6\u00e8\u00ea\u00ec\u00ee\u00f0\u00f2\u00f4\u00f6\u00f8"+
		"\u00fa\u00fc\u00fe\u0100\u0102\u0104\u0106\u0000!\u0002\u0000KKjj\u0001"+
		"\u0000\u00de\u00df\u0002\u0000TT\u009d\u009d\u0002\u0000\u0133\u0133\u0138"+
		"\u0138\u0002\u0000JJ\u0108\u0108\u0002\u0000\f\f::\u0002\u0000TT\u0083"+
		"\u0083\u0002\u0000\u0005\u0005>>\u0002\u0000\u0010\u0010\u00f2\u00f2\u0003"+
		"\u0000\u0012\u0012\u0085\u0085\u00fd\u00fd\u0002\u0000kk\u00e6\u00e6\u0002"+
		"\u0000DDHH\u0002\u0000\u007f\u007f\u00ac\u00ac\u0002\u0000ll\u00b4\u00b4"+
		"\u0002\u0000%%\u0108\u0108\u0001\u0000\u012d\u012e\u0001\u0000\u012f\u0131"+
		"\u0001\u0000\u0112\u0114\u0004\u0000HHPP\u0100\u0100\u010a\u010a\u0002"+
		"\u0000  \u0107\u0107\u0002\u0000SS\u00e0\u00e0\u0001\u0000\u0127\u012c"+
		"\u0003\u0000\u0005\u0005\t\t\u00ed\u00ed\u0002\u0000PP\u0100\u0100\u0005"+
		"\u000022ee\u0099\u009a\u00e4\u00e4\u0125\u0125\u0001\u0000\u009e\u00a1"+
		"\u0002\u0000UU\u00c3\u00c3\u0003\u0000``xx\u00f6\u00f6\u0004\u0000??s"+
		"s\u008f\u008f\u0115\u0115\u0002\u0000\u00af\u00af\u0124\u0124\u0002\u0000"+
		"77rr\u0002\u0000\u00fb\u00fb\u0119\u01196\u0000\u0001\u0005\u0007\u0007"+
		"\t\n\f\u0010\u0012\u0012\u0014\u0016\u0019 \"#\'\'02479:<=?ADFHHKKNNQ"+
		"UWWZ`ccehjkmmpprsuvxx\u007f\u0086\u0088\u0088\u008a\u008a\u008c\u008c"+
		"\u008f\u009a\u009c\u00a3\u00a7\u00ac\u00ae\u00b0\u00b3\u00b3\u00b5\u00c4"+
		"\u00c6\u00cb\u00cd\u00d8\u00da\u00dc\u00de\u00e6\u00e8\u00f2\u00f4\u00f7"+
		"\u00f9\u00fe\u0101\u0103\u0105\u0107\u0109\u010b\u010d\u0110\u0112\u0116"+
		"\u0118\u011a\u011d\u011e\u0120\u0126\u0ba3\u0000\u0108\u0001\u0000\u0000"+
		"\u0000\u0002\u010b\u0001\u0000\u0000\u0000\u0004\u010e\u0001\u0000\u0000"+
		"\u0000\u0006\u0111\u0001\u0000\u0000\u0000\b\u0114\u0001\u0000\u0000\u0000"+
		"\n\u0117\u0001\u0000\u0000\u0000\f\u0131\u0001\u0000\u0000\u0000\u000e"+
		"\u0134\u0001\u0000\u0000\u0000\u0010\u0138\u0001\u0000\u0000\u0000\u0012"+
		"\u0142\u0001\u0000\u0000\u0000\u0014\u0146\u0001\u0000\u0000\u0000\u0016"+
		"\u0154\u0001\u0000\u0000\u0000\u0018\u0156\u0001\u0000\u0000\u0000\u001a"+
		"\u0164\u0001\u0000\u0000\u0000\u001c\u016a\u0001\u0000\u0000\u0000\u001e"+
		"\u016e\u0001\u0000\u0000\u0000 \u0176\u0001\u0000\u0000\u0000\"\u017c"+
		"\u0001\u0000\u0000\u0000$\u017e\u0001\u0000\u0000\u0000&\u01a3\u0001\u0000"+
		"\u0000\u0000(\u01a5\u0001\u0000\u0000\u0000*\u01a7\u0001\u0000\u0000\u0000"+
		",\u01cb\u0001\u0000\u0000\u0000.\u01cd\u0001\u0000\u0000\u00000\u01d5"+
		"\u0001\u0000\u0000\u00002\u0205\u0001\u0000\u0000\u00004\u0237\u0001\u0000"+
		"\u0000\u00006\u0246\u0001\u0000\u0000\u00008\u0248\u0001\u0000\u0000\u0000"+
		":\u024f\u0001\u0000\u0000\u0000<\u026c\u0001\u0000\u0000\u0000>\u0275"+
		"\u0001\u0000\u0000\u0000@\u0286\u0001\u0000\u0000\u0000B\u0288\u0001\u0000"+
		"\u0000\u0000D\u02af\u0001\u0000\u0000\u0000F\u02bf\u0001\u0000\u0000\u0000"+
		"H\u02c1\u0001\u0000\u0000\u0000J\u02ca\u0001\u0000\u0000\u0000L\u02cc"+
		"\u0001\u0000\u0000\u0000N\u02d4\u0001\u0000\u0000\u0000P\u02da\u0001\u0000"+
		"\u0000\u0000R\u02dc\u0001\u0000\u0000\u0000T\u0331\u0001\u0000\u0000\u0000"+
		"V\u0340\u0001\u0000\u0000\u0000X\u034b\u0001\u0000\u0000\u0000Z\u0360"+
		"\u0001\u0000\u0000\u0000\\\u0362\u0001\u0000\u0000\u0000^\u036f\u0001"+
		"\u0000\u0000\u0000`\u0373\u0001\u0000\u0000\u0000b\u037d\u0001\u0000\u0000"+
		"\u0000d\u03cf\u0001\u0000\u0000\u0000f\u041e\u0001\u0000\u0000\u0000h"+
		"\u0439\u0001\u0000\u0000\u0000j\u043b\u0001\u0000\u0000\u0000l\u0442\u0001"+
		"\u0000\u0000\u0000n\u044e\u0001\u0000\u0000\u0000p\u0450\u0001\u0000\u0000"+
		"\u0000r\u046c\u0001\u0000\u0000\u0000t\u0473\u0001\u0000\u0000\u0000v"+
		"\u04bb\u0001\u0000\u0000\u0000x\u04cf\u0001\u0000\u0000\u0000z\u04d1\u0001"+
		"\u0000\u0000\u0000|\u04d5\u0001\u0000\u0000\u0000~\u04e2\u0001\u0000\u0000"+
		"\u0000\u0080\u04eb\u0001\u0000\u0000\u0000\u0082\u0535\u0001\u0000\u0000"+
		"\u0000\u0084\u053b\u0001\u0000\u0000\u0000\u0086\u0713\u0001\u0000\u0000"+
		"\u0000\u0088\u0722\u0001\u0000\u0000\u0000\u008a\u0734\u0001\u0000\u0000"+
		"\u0000\u008c\u0739\u0001\u0000\u0000\u0000\u008e\u073e\u0001\u0000\u0000"+
		"\u0000\u0090\u0742\u0001\u0000\u0000\u0000\u0092\u0748\u0001\u0000\u0000"+
		"\u0000\u0094\u0755\u0001\u0000\u0000\u0000\u0096\u075d\u0001\u0000\u0000"+
		"\u0000\u0098\u076a\u0001\u0000\u0000\u0000\u009a\u076c\u0001\u0000\u0000"+
		"\u0000\u009c\u0772\u0001\u0000\u0000\u0000\u009e\u077a\u0001\u0000\u0000"+
		"\u0000\u00a0\u0782\u0001\u0000\u0000\u0000\u00a2\u0784\u0001\u0000\u0000"+
		"\u0000\u00a4\u0786\u0001\u0000\u0000\u0000\u00a6\u0788\u0001\u0000\u0000"+
		"\u0000\u00a8\u078a\u0001\u0000\u0000\u0000\u00aa\u0794\u0001\u0000\u0000"+
		"\u0000\u00ac\u0796\u0001\u0000\u0000\u0000\u00ae\u07f3\u0001\u0000\u0000"+
		"\u0000\u00b0\u0805\u0001\u0000\u0000\u0000\u00b2\u0809\u0001\u0000\u0000"+
		"\u0000\u00b4\u080b\u0001\u0000\u0000\u0000\u00b6\u0810\u0001\u0000\u0000"+
		"\u0000\u00b8\u0856\u0001\u0000\u0000\u0000\u00ba\u0858\u0001\u0000\u0000"+
		"\u0000\u00bc\u0869\u0001\u0000\u0000\u0000\u00be\u08a9\u0001\u0000\u0000"+
		"\u0000\u00c0\u08b4\u0001\u0000\u0000\u0000\u00c2\u08b6\u0001\u0000\u0000"+
		"\u0000\u00c4\u08de\u0001\u0000\u0000\u0000\u00c6\u08fe\u0001\u0000\u0000"+
		"\u0000\u00c8\u0900\u0001\u0000\u0000\u0000\u00ca\u0908\u0001\u0000\u0000"+
		"\u0000\u00cc\u090f\u0001\u0000\u0000\u0000\u00ce\u0918\u0001\u0000\u0000"+
		"\u0000\u00d0\u091f\u0001\u0000\u0000\u0000\u00d2\u0926\u0001\u0000\u0000"+
		"\u0000\u00d4\u0928\u0001\u0000\u0000\u0000\u00d6\u0930\u0001\u0000\u0000"+
		"\u0000\u00d8\u093b\u0001\u0000\u0000\u0000\u00da\u094a\u0001\u0000\u0000"+
		"\u0000\u00dc\u094e\u0001\u0000\u0000\u0000\u00de\u0964\u0001\u0000\u0000"+
		"\u0000\u00e0\u09c9\u0001\u0000\u0000\u0000\u00e2\u09cb\u0001\u0000\u0000"+
		"\u0000\u00e4\u09d0\u0001\u0000\u0000\u0000\u00e6\u09d5\u0001\u0000\u0000"+
		"\u0000\u00e8\u09d8\u0001\u0000\u0000\u0000\u00ea\u09e9\u0001\u0000\u0000"+
		"\u0000\u00ec\u09f3\u0001\u0000\u0000\u0000\u00ee\u09f8\u0001\u0000\u0000"+
		"\u0000\u00f0\u09fb\u0001\u0000\u0000\u0000\u00f2\u09ff\u0001\u0000\u0000"+
		"\u0000\u00f4\u0a07\u0001\u0000\u0000\u0000\u00f6\u0a0d\u0001\u0000\u0000"+
		"\u0000\u00f8\u0a12\u0001\u0000\u0000\u0000\u00fa\u0a19\u0001\u0000\u0000"+
		"\u0000\u00fc\u0a1b\u0001\u0000\u0000\u0000\u00fe\u0a29\u0001\u0000\u0000"+
		"\u0000\u0100\u0a30\u0001\u0000\u0000\u0000\u0102\u0a3e\u0001\u0000\u0000"+
		"\u0000\u0104\u0a42\u0001\u0000\u0000\u0000\u0106\u0a44\u0001\u0000\u0000"+
		"\u0000\u0108\u0109\u0003\f\u0006\u0000\u0109\u010a\u0005\u0000\u0000\u0001"+
		"\u010a\u0001\u0001\u0000\u0000\u0000\u010b\u010c\u0003~?\u0000\u010c\u010d"+
		"\u0005\u0000\u0000\u0001\u010d\u0003\u0001\u0000\u0000\u0000\u010e\u010f"+
		"\u0003\u00d4j\u0000\u010f\u0110\u0005\u0000\u0000\u0001\u0110\u0005\u0001"+
		"\u0000\u0000\u0000\u0111\u0112\u0003\u00aeW\u0000\u0112\u0113\u0005\u0000"+
		"\u0000\u0001\u0113\u0007\u0001\u0000\u0000\u0000\u0114\u0115\u0003\u00c2"+
		"a\u0000\u0115\u0116\u0005\u0000\u0000\u0001\u0116\t\u0001\u0000\u0000"+
		"\u0000\u0117\u0118\u0003\u00d6k\u0000\u0118\u0119\u0005\u0000\u0000\u0001"+
		"\u0119\u000b\u0001\u0000\u0000\u0000\u011a\u0132\u0003\u000e\u0007\u0000"+
		"\u011b\u011c\u0005\u010f\u0000\u0000\u011c\u0132\u0003\u0100\u0080\u0000"+
		"\u011d\u011e\u0005\u010f\u0000\u0000\u011e\u011f\u0003\u0100\u0080\u0000"+
		"\u011f\u0120\u0005\u0001\u0000\u0000\u0120\u0121\u0003\u0100\u0080\u0000"+
		"\u0121\u0132\u0001\u0000\u0000\u0000\u0122\u0123\u0005\u010e\u0000\u0000"+
		"\u0123\u0124\u0003\u00f2y\u0000\u0124\u0125\u0005\u00ea\u0000\u0000\u0125"+
		"\u012a\u0003\u00c8d\u0000\u0126\u0127\u0005\u0002\u0000\u0000\u0127\u0129"+
		"\u0003\u00c8d\u0000\u0128\u0126\u0001\u0000\u0000\u0000\u0129\u012c\u0001"+
		"\u0000\u0000\u0000\u012a\u0128\u0001\u0000\u0000\u0000\u012a\u012b\u0001"+
		"\u0000\u0000\u0000\u012b\u012f\u0001\u0000\u0000\u0000\u012c\u012a\u0001"+
		"\u0000\u0000\u0000\u012d\u012e\u0005\u011c\u0000\u0000\u012e\u0130\u0003"+
		"\u0080@\u0000\u012f\u012d\u0001\u0000\u0000\u0000\u012f\u0130\u0001\u0000"+
		"\u0000\u0000\u0130\u0132\u0001\u0000\u0000\u0000\u0131\u011a\u0001\u0000"+
		"\u0000\u0000\u0131\u011b\u0001\u0000\u0000\u0000\u0131\u011d\u0001\u0000"+
		"\u0000\u0000\u0131\u0122\u0001\u0000\u0000\u0000\u0132\r\u0001\u0000\u0000"+
		"\u0000\u0133\u0135\u0003\u0010\b\u0000\u0134\u0133\u0001\u0000\u0000\u0000"+
		"\u0134\u0135\u0001\u0000\u0000\u0000\u0135\u0136\u0001\u0000\u0000\u0000"+
		"\u0136\u0137\u0003\u0012\t\u0000\u0137\u000f\u0001\u0000\u0000\u0000\u0138"+
		"\u0139\u0005\u011f\u0000\u0000\u0139\u013e\u0003\u00d6k\u0000\u013a\u013b"+
		"\u0005\u0002\u0000\u0000\u013b\u013d\u0003\u00d6k\u0000\u013c\u013a\u0001"+
		"\u0000\u0000\u0000\u013d\u0140\u0001\u0000\u0000\u0000\u013e\u013c\u0001"+
		"\u0000\u0000\u0000\u013e\u013f\u0001\u0000\u0000\u0000\u013f\u0011\u0001"+
		"\u0000\u0000\u0000\u0140\u013e\u0001\u0000\u0000\u0000\u0141\u0143\u0003"+
		"\u0014\n\u0000\u0142\u0141\u0001\u0000\u0000\u0000\u0142\u0143\u0001\u0000"+
		"\u0000\u0000\u0143\u0144\u0001\u0000\u0000\u0000\u0144\u0145\u0003$\u0012"+
		"\u0000\u0145\u0013\u0001\u0000\u0000\u0000\u0146\u0148\u0005\u011f\u0000"+
		"\u0000\u0147\u0149\u0005\u00cc\u0000\u0000\u0148\u0147\u0001\u0000\u0000"+
		"\u0000\u0148\u0149\u0001\u0000\u0000\u0000\u0149\u014a\u0001\u0000\u0000"+
		"\u0000\u014a\u014f\u0003<\u001e\u0000\u014b\u014c\u0005\u0002\u0000\u0000"+
		"\u014c\u014e\u0003<\u001e\u0000\u014d\u014b\u0001\u0000\u0000\u0000\u014e"+
		"\u0151\u0001\u0000\u0000\u0000\u014f\u014d\u0001\u0000\u0000\u0000\u014f"+
		"\u0150\u0001\u0000\u0000\u0000\u0150\u0015\u0001\u0000\u0000\u0000\u0151"+
		"\u014f\u0001\u0000\u0000\u0000\u0152\u0155\u0003\u0018\f\u0000\u0153\u0155"+
		"\u0003\u001a\r\u0000\u0154\u0152\u0001\u0000\u0000\u0000\u0154\u0153\u0001"+
		"\u0000\u0000\u0000\u0155\u0017\u0001\u0000\u0000\u0000\u0156\u0157\u0003"+
		"\u00f2y\u0000\u0157\u015a\u0003\u00aeW\u0000\u0158\u0159\u0005\u00a5\u0000"+
		"\u0000\u0159\u015b\u0005\u00a6\u0000\u0000\u015a\u0158\u0001\u0000\u0000"+
		"\u0000\u015a\u015b\u0001\u0000\u0000\u0000\u015b\u015e\u0001\u0000\u0000"+
		"\u0000\u015c\u015d\u0005\u001d\u0000\u0000\u015d\u015f\u0003\u009eO\u0000"+
		"\u015e\u015c\u0001\u0000\u0000\u0000\u015e\u015f\u0001\u0000\u0000\u0000"+
		"\u015f\u0162\u0001\u0000\u0000\u0000\u0160\u0161\u0005\u011f\u0000\u0000"+
		"\u0161\u0163\u0003\u001c\u000e\u0000\u0162\u0160\u0001\u0000\u0000\u0000"+
		"\u0162\u0163\u0001\u0000\u0000\u0000\u0163\u0019\u0001\u0000\u0000\u0000"+
		"\u0164\u0165\u0005\u0089\u0000\u0000\u0165\u0168\u0003\u00f2y\u0000\u0166"+
		"\u0167\u0007\u0000\u0000\u0000\u0167\u0169\u0005\u00c7\u0000\u0000\u0168"+
		"\u0166\u0001\u0000\u0000\u0000\u0168\u0169\u0001\u0000\u0000\u0000\u0169"+
		"\u001b\u0001\u0000\u0000\u0000\u016a\u016b\u0005\u0003\u0000\u0000\u016b"+
		"\u016c\u0003\u001e\u000f\u0000\u016c\u016d\u0005\u0004\u0000\u0000\u016d"+
		"\u001d\u0001\u0000\u0000\u0000\u016e\u0173\u0003 \u0010\u0000\u016f\u0170"+
		"\u0005\u0002\u0000\u0000\u0170\u0172\u0003 \u0010\u0000\u0171\u016f\u0001"+
		"\u0000\u0000\u0000\u0172\u0175\u0001\u0000\u0000\u0000\u0173\u0171\u0001"+
		"\u0000\u0000\u0000\u0173\u0174\u0001\u0000\u0000\u0000\u0174\u001f\u0001"+
		"\u0000\u0000\u0000\u0175\u0173\u0001\u0000\u0000\u0000\u0176\u0177\u0003"+
		"\u0100\u0080\u0000\u0177\u0178\u0005\u0127\u0000\u0000\u0178\u0179\u0003"+
		"\"\u0011\u0000\u0179!\u0001\u0000\u0000\u0000\u017a\u017d\u00055\u0000"+
		"\u0000\u017b\u017d\u0003~?\u0000\u017c\u017a\u0001\u0000\u0000\u0000\u017c"+
		"\u017b\u0001\u0000\u0000\u0000\u017d#\u0001\u0000\u0000\u0000\u017e\u0189"+
		"\u0003*\u0015\u0000\u017f\u0180\u0005\u00b2\u0000\u0000\u0180\u0181\u0005"+
		"\u0013\u0000\u0000\u0181\u0186\u0003.\u0017\u0000\u0182\u0183\u0005\u0002"+
		"\u0000\u0000\u0183\u0185\u0003.\u0017\u0000\u0184\u0182\u0001\u0000\u0000"+
		"\u0000\u0185\u0188\u0001\u0000\u0000\u0000\u0186\u0184\u0001\u0000\u0000"+
		"\u0000\u0186\u0187\u0001\u0000\u0000\u0000\u0187\u018a\u0001\u0000\u0000"+
		"\u0000\u0188\u0186\u0001\u0000\u0000\u0000\u0189\u017f\u0001\u0000\u0000"+
		"\u0000\u0189\u018a\u0001\u0000\u0000\u0000\u018a\u0190\u0001\u0000\u0000"+
		"\u0000\u018b\u018c\u0005\u00ab\u0000\u0000\u018c\u018e\u0003(\u0014\u0000"+
		"\u018d\u018f\u0007\u0001\u0000\u0000\u018e\u018d\u0001\u0000\u0000\u0000"+
		"\u018e\u018f\u0001\u0000\u0000\u0000\u018f\u0191\u0001\u0000\u0000\u0000"+
		"\u0190\u018b\u0001\u0000\u0000\u0000\u0190\u0191\u0001\u0000\u0000\u0000"+
		"\u0191\u019f\u0001\u0000\u0000\u0000\u0192\u0193\u0005\u008a\u0000\u0000"+
		"\u0193\u01a0\u0003&\u0013\u0000\u0194\u0195\u0005Q\u0000\u0000\u0195\u0197"+
		"\u0007\u0002\u0000\u0000\u0196\u0198\u0003(\u0014\u0000\u0197\u0196\u0001"+
		"\u0000\u0000\u0000\u0197\u0198\u0001\u0000\u0000\u0000\u0198\u0199\u0001"+
		"\u0000\u0000\u0000\u0199\u019d\u0007\u0001\u0000\u0000\u019a\u019e\u0005"+
		"\u00af\u0000\u0000\u019b\u019c\u0005\u011f\u0000\u0000\u019c\u019e\u0005"+
		"\u00f9\u0000\u0000\u019d\u019a\u0001\u0000\u0000\u0000\u019d\u019b\u0001"+
		"\u0000\u0000\u0000\u019e\u01a0\u0001\u0000\u0000\u0000\u019f\u0192\u0001"+
		"\u0000\u0000\u0000\u019f\u0194\u0001\u0000\u0000\u0000\u019f\u01a0\u0001"+
		"\u0000\u0000\u0000\u01a0%\u0001\u0000\u0000\u0000\u01a1\u01a4\u0005\u0005"+
		"\u0000\u0000\u01a2\u01a4\u0003(\u0014\u0000\u01a3\u01a1\u0001\u0000\u0000"+
		"\u0000\u01a3\u01a2\u0001\u0000\u0000\u0000\u01a4\'\u0001\u0000\u0000\u0000"+
		"\u01a5\u01a6\u0007\u0003\u0000\u0000\u01a6)\u0001\u0000\u0000\u0000\u01a7"+
		"\u01a8\u0006\u0015\uffff\uffff\u0000\u01a8\u01a9\u0003,\u0016\u0000\u01a9"+
		"\u01b8\u0001\u0000\u0000\u0000\u01aa\u01ab\n\u0002\u0000\u0000\u01ab\u01ad"+
		"\u0005o\u0000\u0000\u01ac\u01ae\u0003>\u001f\u0000\u01ad\u01ac\u0001\u0000"+
		"\u0000\u0000\u01ad\u01ae\u0001\u0000\u0000\u0000\u01ae\u01af\u0001\u0000"+
		"\u0000\u0000\u01af\u01b7\u0003*\u0015\u0003\u01b0\u01b1\n\u0001\u0000"+
		"\u0000\u01b1\u01b3\u0007\u0004\u0000\u0000\u01b2\u01b4\u0003>\u001f\u0000"+
		"\u01b3\u01b2\u0001\u0000\u0000\u0000\u01b3\u01b4\u0001\u0000\u0000\u0000"+
		"\u01b4\u01b5\u0001\u0000\u0000\u0000\u01b5\u01b7\u0003*\u0015\u0002\u01b6"+
		"\u01aa\u0001\u0000\u0000\u0000\u01b6\u01b0\u0001\u0000\u0000\u0000\u01b7"+
		"\u01ba\u0001\u0000\u0000\u0000\u01b8\u01b6\u0001\u0000\u0000\u0000\u01b8"+
		"\u01b9\u0001\u0000\u0000\u0000\u01b9+\u0001\u0000\u0000\u0000\u01ba\u01b8"+
		"\u0001\u0000\u0000\u0000\u01bb\u01cc\u00030\u0018\u0000\u01bc\u01bd\u0005"+
		"\u00f3\u0000\u0000\u01bd\u01cc\u0003\u00f2y\u0000\u01be\u01bf\u0005\u0117"+
		"\u0000\u0000\u01bf\u01c4\u0003~?\u0000\u01c0\u01c1\u0005\u0002\u0000\u0000"+
		"\u01c1\u01c3\u0003~?\u0000\u01c2\u01c0\u0001\u0000\u0000\u0000\u01c3\u01c6"+
		"\u0001\u0000\u0000\u0000\u01c4\u01c2\u0001\u0000\u0000\u0000\u01c4\u01c5"+
		"\u0001\u0000\u0000\u0000\u01c5\u01cc\u0001\u0000\u0000\u0000\u01c6\u01c4"+
		"\u0001\u0000\u0000\u0000\u01c7\u01c8\u0005\u0003\u0000\u0000\u01c8\u01c9"+
		"\u0003$\u0012\u0000\u01c9\u01ca\u0005\u0004\u0000\u0000\u01ca\u01cc\u0001"+
		"\u0000\u0000\u0000\u01cb\u01bb\u0001\u0000\u0000\u0000\u01cb\u01bc\u0001"+
		"\u0000\u0000\u0000\u01cb\u01be\u0001\u0000\u0000\u0000\u01cb\u01c7\u0001"+
		"\u0000\u0000\u0000\u01cc-\u0001\u0000\u0000\u0000\u01cd\u01cf\u0003~?"+
		"\u0000\u01ce\u01d0\u0007\u0005\u0000\u0000\u01cf\u01ce\u0001\u0000\u0000"+
		"\u0000\u01cf\u01d0\u0001\u0000\u0000\u0000\u01d0\u01d3\u0001\u0000\u0000"+
		"\u0000\u01d1\u01d2\u0005\u00a8\u0000\u0000\u01d2\u01d4\u0007\u0006\u0000"+
		"\u0000\u01d3\u01d1\u0001\u0000\u0000\u0000\u01d3\u01d4\u0001\u0000\u0000"+
		"\u0000\u01d4/\u0001\u0000\u0000\u0000\u01d5\u01d7\u0005\u00e7\u0000\u0000"+
		"\u01d6\u01d8\u0003>\u001f\u0000\u01d7\u01d6\u0001\u0000\u0000\u0000\u01d7"+
		"\u01d8\u0001\u0000\u0000\u0000\u01d8\u01d9\u0001\u0000\u0000\u0000\u01d9"+
		"\u01de\u0003@ \u0000\u01da\u01db\u0005\u0002\u0000\u0000\u01db\u01dd\u0003"+
		"@ \u0000\u01dc\u01da\u0001\u0000\u0000\u0000\u01dd\u01e0\u0001\u0000\u0000"+
		"\u0000\u01de\u01dc\u0001\u0000\u0000\u0000\u01de\u01df\u0001\u0000\u0000"+
		"\u0000\u01df\u01ea\u0001\u0000\u0000\u0000\u01e0\u01de\u0001\u0000\u0000"+
		"\u0000\u01e1\u01e2\u0005X\u0000\u0000\u01e2\u01e7\u0003B!\u0000\u01e3"+
		"\u01e4\u0005\u0002\u0000\u0000\u01e4\u01e6\u0003B!\u0000\u01e5\u01e3\u0001"+
		"\u0000\u0000\u0000\u01e6\u01e9\u0001\u0000\u0000\u0000\u01e7\u01e5\u0001"+
		"\u0000\u0000\u0000\u01e7\u01e8\u0001\u0000\u0000\u0000\u01e8\u01eb\u0001"+
		"\u0000\u0000\u0000\u01e9\u01e7\u0001\u0000\u0000\u0000\u01ea\u01e1\u0001"+
		"\u0000\u0000\u0000\u01ea\u01eb\u0001\u0000\u0000\u0000\u01eb\u01ee\u0001"+
		"\u0000\u0000\u0000\u01ec\u01ed\u0005\u011c\u0000\u0000\u01ed\u01ef\u0003"+
		"\u0080@\u0000\u01ee\u01ec\u0001\u0000\u0000\u0000\u01ee\u01ef\u0001\u0000"+
		"\u0000\u0000\u01ef\u01f3\u0001\u0000\u0000\u0000\u01f0\u01f1\u0005a\u0000"+
		"\u0000\u01f1\u01f2\u0005\u0013\u0000\u0000\u01f2\u01f4\u00032\u0019\u0000"+
		"\u01f3\u01f0\u0001\u0000\u0000\u0000\u01f3\u01f4\u0001\u0000\u0000\u0000"+
		"\u01f4\u01f7\u0001\u0000\u0000\u0000\u01f5\u01f6\u0005d\u0000\u0000\u01f6"+
		"\u01f8\u0003\u0080@\u0000\u01f7\u01f5\u0001\u0000\u0000\u0000\u01f7\u01f8"+
		"\u0001\u0000\u0000\u0000\u01f8\u0202\u0001\u0000\u0000\u0000\u01f9\u01fa"+
		"\u0005\u011e\u0000\u0000\u01fa\u01ff\u00038\u001c\u0000\u01fb\u01fc\u0005"+
		"\u0002\u0000\u0000\u01fc\u01fe\u00038\u001c\u0000\u01fd\u01fb\u0001\u0000"+
		"\u0000\u0000\u01fe\u0201\u0001\u0000\u0000\u0000\u01ff\u01fd\u0001\u0000"+
		"\u0000\u0000\u01ff\u0200\u0001\u0000\u0000\u0000\u0200\u0203\u0001\u0000"+
		"\u0000\u0000\u0201\u01ff\u0001\u0000\u0000\u0000\u0202\u01f9\u0001\u0000"+
		"\u0000\u0000\u0202\u0203\u0001\u0000\u0000\u0000\u02031\u0001\u0000\u0000"+
		"\u0000\u0204\u0206\u0003>\u001f\u0000\u0205\u0204\u0001\u0000\u0000\u0000"+
		"\u0205\u0206\u0001\u0000\u0000\u0000\u0206\u0207\u0001\u0000\u0000\u0000"+
		"\u0207\u020c\u00034\u001a\u0000\u0208\u0209\u0005\u0002\u0000\u0000\u0209"+
		"\u020b\u00034\u001a\u0000\u020a\u0208\u0001\u0000\u0000\u0000\u020b\u020e"+
		"\u0001\u0000\u0000\u0000\u020c\u020a\u0001\u0000\u0000\u0000\u020c\u020d"+
		"\u0001\u0000\u0000\u0000\u020d3\u0001\u0000\u0000\u0000\u020e\u020c\u0001"+
		"\u0000\u0000\u0000\u020f\u0238\u00036\u001b\u0000\u0210\u0211\u0005\u00dd"+
		"\u0000\u0000\u0211\u021a\u0005\u0003\u0000\u0000\u0212\u0217\u00036\u001b"+
		"\u0000\u0213\u0214\u0005\u0002\u0000\u0000\u0214\u0216\u00036\u001b\u0000"+
		"\u0215\u0213\u0001\u0000\u0000\u0000\u0216\u0219\u0001\u0000\u0000\u0000"+
		"\u0217\u0215\u0001\u0000\u0000\u0000\u0217\u0218\u0001\u0000\u0000\u0000"+
		"\u0218\u021b\u0001\u0000\u0000\u0000\u0219\u0217\u0001\u0000\u0000\u0000"+
		"\u021a\u0212\u0001\u0000\u0000\u0000\u021a\u021b\u0001\u0000\u0000\u0000"+
		"\u021b\u021c\u0001\u0000\u0000\u0000\u021c\u0238\u0005\u0004\u0000\u0000"+
		"\u021d\u021e\u0005&\u0000\u0000\u021e\u0227\u0005\u0003\u0000\u0000\u021f"+
		"\u0224\u00036\u001b\u0000\u0220\u0221\u0005\u0002\u0000\u0000\u0221\u0223"+
		"\u00036\u001b\u0000\u0222\u0220\u0001\u0000\u0000\u0000\u0223\u0226\u0001"+
		"\u0000\u0000\u0000\u0224\u0222\u0001\u0000\u0000\u0000\u0224\u0225\u0001"+
		"\u0000\u0000\u0000\u0225\u0228\u0001\u0000\u0000\u0000\u0226\u0224\u0001"+
		"\u0000\u0000\u0000\u0227\u021f\u0001\u0000\u0000\u0000\u0227\u0228\u0001"+
		"\u0000\u0000\u0000\u0228\u0229\u0001\u0000\u0000\u0000\u0229\u0238\u0005"+
		"\u0004\u0000\u0000\u022a\u022b\u0005b\u0000\u0000\u022b\u022c\u0005\u00eb"+
		"\u0000\u0000\u022c\u022d\u0005\u0003\u0000\u0000\u022d\u0232\u00036\u001b"+
		"\u0000\u022e\u022f\u0005\u0002\u0000\u0000\u022f\u0231\u00036\u001b\u0000"+
		"\u0230\u022e\u0001\u0000\u0000\u0000\u0231\u0234\u0001\u0000\u0000\u0000"+
		"\u0232\u0230\u0001\u0000\u0000\u0000\u0232\u0233\u0001\u0000\u0000\u0000"+
		"\u0233\u0235\u0001\u0000\u0000\u0000\u0234\u0232\u0001\u0000\u0000\u0000"+
		"\u0235\u0236\u0005\u0004\u0000\u0000\u0236\u0238\u0001\u0000\u0000\u0000"+
		"\u0237\u020f\u0001\u0000\u0000\u0000\u0237\u0210\u0001\u0000\u0000\u0000"+
		"\u0237\u021d\u0001\u0000\u0000\u0000\u0237\u022a\u0001\u0000\u0000\u0000"+
		"\u02385\u0001\u0000\u0000\u0000\u0239\u0242\u0005\u0003\u0000\u0000\u023a"+
		"\u023f\u0003~?\u0000\u023b\u023c\u0005\u0002\u0000\u0000\u023c\u023e\u0003"+
		"~?\u0000\u023d\u023b\u0001\u0000\u0000\u0000\u023e\u0241\u0001\u0000\u0000"+
		"\u0000\u023f\u023d\u0001\u0000\u0000\u0000\u023f\u0240\u0001\u0000\u0000"+
		"\u0000\u0240\u0243\u0001\u0000\u0000\u0000\u0241\u023f\u0001\u0000\u0000"+
		"\u0000\u0242\u023a\u0001\u0000\u0000\u0000\u0242\u0243\u0001\u0000\u0000"+
		"\u0000\u0243\u0244\u0001\u0000\u0000\u0000\u0244\u0247\u0005\u0004\u0000"+
		"\u0000\u0245\u0247\u0003~?\u0000\u0246\u0239\u0001\u0000\u0000\u0000\u0246"+
		"\u0245\u0001\u0000\u0000\u0000\u02477\u0001\u0000\u0000\u0000\u0248\u0249"+
		"\u0003\u0100\u0080\u0000\u0249\u024a\u0005\u000b\u0000\u0000\u024a\u024b"+
		"\u0005\u0003\u0000\u0000\u024b\u024c\u0003:\u001d\u0000\u024c\u024d\u0005"+
		"\u0004\u0000\u0000\u024d9\u0001\u0000\u0000\u0000\u024e\u0250\u0003\u0100"+
		"\u0080\u0000\u024f\u024e\u0001\u0000\u0000\u0000\u024f\u0250\u0001\u0000"+
		"\u0000\u0000\u0250\u025b\u0001\u0000\u0000\u0000\u0251\u0252\u0005\u00b8"+
		"\u0000\u0000\u0252\u0253\u0005\u0013\u0000\u0000\u0253\u0258\u0003~?\u0000"+
		"\u0254\u0255\u0005\u0002\u0000\u0000\u0255\u0257\u0003~?\u0000\u0256\u0254"+
		"\u0001\u0000\u0000\u0000\u0257\u025a\u0001\u0000\u0000\u0000\u0258\u0256"+
		"\u0001\u0000\u0000\u0000\u0258\u0259\u0001\u0000\u0000\u0000\u0259\u025c"+
		"\u0001\u0000\u0000\u0000\u025a\u0258\u0001\u0000\u0000\u0000\u025b\u0251"+
		"\u0001\u0000\u0000\u0000\u025b\u025c\u0001\u0000\u0000\u0000\u025c\u0267"+
		"\u0001\u0000\u0000\u0000\u025d\u025e\u0005\u00b2\u0000\u0000\u025e\u025f"+
		"\u0005\u0013\u0000\u0000\u025f\u0264\u0003.\u0017\u0000\u0260\u0261\u0005"+
		"\u0002\u0000\u0000\u0261\u0263\u0003.\u0017\u0000\u0262\u0260\u0001\u0000"+
		"\u0000\u0000\u0263\u0266\u0001\u0000\u0000\u0000\u0264\u0262\u0001\u0000"+
		"\u0000\u0000\u0264\u0265\u0001\u0000\u0000\u0000\u0265\u0268\u0001\u0000"+
		"\u0000\u0000\u0266\u0264\u0001\u0000\u0000\u0000\u0267\u025d\u0001\u0000"+
		"\u0000\u0000\u0267\u0268\u0001\u0000\u0000\u0000\u0268\u026a\u0001\u0000"+
		"\u0000\u0000\u0269\u026b\u0003\u00bc^\u0000\u026a\u0269\u0001\u0000\u0000"+
		"\u0000\u026a\u026b\u0001\u0000\u0000\u0000\u026b;\u0001\u0000\u0000\u0000"+
		"\u026c\u026e\u0003\u0100\u0080\u0000\u026d\u026f\u0003b1\u0000\u026e\u026d"+
		"\u0001\u0000\u0000\u0000\u026e\u026f\u0001\u0000\u0000\u0000\u026f\u0270"+
		"\u0001\u0000\u0000\u0000\u0270\u0271\u0005\u000b\u0000\u0000\u0271\u0272"+
		"\u0005\u0003\u0000\u0000\u0272\u0273\u0003\u0012\t\u0000\u0273\u0274\u0005"+
		"\u0004\u0000\u0000\u0274=\u0001\u0000\u0000\u0000\u0275\u0276\u0007\u0007"+
		"\u0000\u0000\u0276?\u0001\u0000\u0000\u0000\u0277\u027c\u0003~?\u0000"+
		"\u0278\u027a\u0005\u000b\u0000\u0000\u0279\u0278\u0001\u0000\u0000\u0000"+
		"\u0279\u027a\u0001\u0000\u0000\u0000\u027a\u027b\u0001\u0000\u0000\u0000"+
		"\u027b\u027d\u0003\u0100\u0080\u0000\u027c\u0279\u0001\u0000\u0000\u0000"+
		"\u027c\u027d\u0001\u0000\u0000\u0000\u027d\u0287\u0001\u0000\u0000\u0000"+
		"\u027e\u027f\u0003\u0086C\u0000\u027f\u0280\u0005\u0001\u0000\u0000\u0280"+
		"\u0283\u0005\u012f\u0000\u0000\u0281\u0282\u0005\u000b\u0000\u0000\u0282"+
		"\u0284\u0003b1\u0000\u0283\u0281\u0001\u0000\u0000\u0000\u0283\u0284\u0001"+
		"\u0000\u0000\u0000\u0284\u0287\u0001\u0000\u0000\u0000\u0285\u0287\u0005"+
		"\u012f\u0000\u0000\u0286\u0277\u0001\u0000\u0000\u0000\u0286\u027e\u0001"+
		"\u0000\u0000\u0000\u0286\u0285\u0001\u0000\u0000\u0000\u0287A\u0001\u0000"+
		"\u0000\u0000\u0288\u0289\u0006!\uffff\uffff\u0000\u0289\u028a\u0003H$"+
		"\u0000\u028a\u029d\u0001\u0000\u0000\u0000\u028b\u0299\n\u0002\u0000\u0000"+
		"\u028c\u028d\u0005%\u0000\u0000\u028d\u028e\u0005w\u0000\u0000\u028e\u029a"+
		"\u0003H$\u0000\u028f\u0290\u0003D\"\u0000\u0290\u0291\u0005w\u0000\u0000"+
		"\u0291\u0292\u0003B!\u0000\u0292\u0293\u0003F#\u0000\u0293\u029a\u0001"+
		"\u0000\u0000\u0000\u0294\u0295\u0005\u009b\u0000\u0000\u0295\u0296\u0003"+
		"D\"\u0000\u0296\u0297\u0005w\u0000\u0000\u0297\u0298\u0003H$\u0000\u0298"+
		"\u029a\u0001\u0000\u0000\u0000\u0299\u028c\u0001\u0000\u0000\u0000\u0299"+
		"\u028f\u0001\u0000\u0000\u0000\u0299\u0294\u0001\u0000\u0000\u0000\u029a"+
		"\u029c\u0001\u0000\u0000\u0000\u029b\u028b\u0001\u0000\u0000\u0000\u029c"+
		"\u029f\u0001\u0000\u0000\u0000\u029d\u029b\u0001\u0000\u0000\u0000\u029d"+
		"\u029e\u0001\u0000\u0000\u0000\u029eC\u0001\u0000\u0000\u0000\u029f\u029d"+
		"\u0001\u0000\u0000\u0000\u02a0\u02a2\u0005l\u0000\u0000\u02a1\u02a0\u0001"+
		"\u0000\u0000\u0000\u02a1\u02a2\u0001\u0000\u0000\u0000\u02a2\u02b0\u0001"+
		"\u0000\u0000\u0000\u02a3\u02a5\u0005\u0087\u0000\u0000\u02a4\u02a6\u0005"+
		"\u00b4\u0000\u0000\u02a5\u02a4\u0001\u0000\u0000\u0000\u02a5\u02a6\u0001"+
		"\u0000\u0000\u0000\u02a6\u02b0\u0001\u0000\u0000\u0000\u02a7\u02a9\u0005"+
		"\u00d9\u0000\u0000\u02a8\u02aa\u0005\u00b4\u0000\u0000\u02a9\u02a8\u0001"+
		"\u0000\u0000\u0000\u02a9\u02aa\u0001\u0000\u0000\u0000\u02aa\u02b0\u0001"+
		"\u0000\u0000\u0000\u02ab\u02ad\u0005Y\u0000\u0000\u02ac\u02ae\u0005\u00b4"+
		"\u0000\u0000\u02ad\u02ac\u0001\u0000\u0000\u0000\u02ad\u02ae\u0001\u0000"+
		"\u0000\u0000\u02ae\u02b0\u0001\u0000\u0000\u0000\u02af\u02a1\u0001\u0000"+
		"\u0000\u0000\u02af\u02a3\u0001\u0000\u0000\u0000\u02af\u02a7\u0001\u0000"+
		"\u0000\u0000\u02af\u02ab\u0001\u0000\u0000\u0000\u02b0E\u0001\u0000\u0000"+
		"\u0000\u02b1\u02b2\u0005\u00ad\u0000\u0000\u02b2\u02c0\u0003\u0080@\u0000"+
		"\u02b3\u02b4\u0005\u0111\u0000\u0000\u02b4\u02b5\u0005\u0003\u0000\u0000"+
		"\u02b5\u02ba\u0003\u0100\u0080\u0000\u02b6\u02b7\u0005\u0002\u0000\u0000"+
		"\u02b7\u02b9\u0003\u0100\u0080\u0000\u02b8\u02b6\u0001\u0000\u0000\u0000"+
		"\u02b9\u02bc\u0001\u0000\u0000\u0000\u02ba\u02b8\u0001\u0000\u0000\u0000"+
		"\u02ba\u02bb\u0001\u0000\u0000\u0000\u02bb\u02bd\u0001\u0000\u0000\u0000"+
		"\u02bc\u02ba\u0001\u0000\u0000\u0000\u02bd\u02be\u0005\u0004\u0000\u0000"+
		"\u02be\u02c0\u0001\u0000\u0000\u0000\u02bf\u02b1\u0001\u0000\u0000\u0000"+
		"\u02bf\u02b3\u0001\u0000\u0000\u0000\u02c0G\u0001\u0000\u0000\u0000\u02c1"+
		"\u02c8\u0003R)\u0000\u02c2\u02c3\u0005\u00f5\u0000\u0000\u02c3\u02c4\u0003"+
		"J%\u0000\u02c4\u02c5\u0005\u0003\u0000\u0000\u02c5\u02c6\u0003~?\u0000"+
		"\u02c6\u02c7\u0005\u0004\u0000\u0000\u02c7\u02c9\u0001\u0000\u0000\u0000"+
		"\u02c8\u02c2\u0001\u0000\u0000\u0000\u02c8\u02c9\u0001\u0000\u0000\u0000"+
		"\u02c9I\u0001\u0000\u0000\u0000\u02ca\u02cb\u0007\b\u0000\u0000\u02cb"+
		"K\u0001\u0000\u0000\u0000\u02cc\u02cd\u0007\t\u0000\u0000\u02cdM\u0001"+
		"\u0000\u0000\u0000\u02ce\u02d5\u0005H\u0000\u0000\u02cf\u02d1\u0005\u0101"+
		"\u0000\u0000\u02d0\u02d2\u0003\u009eO\u0000\u02d1\u02d0\u0001\u0000\u0000"+
		"\u0000\u02d1\u02d2\u0001\u0000\u0000\u0000\u02d2\u02d3\u0001\u0000\u0000"+
		"\u0000\u02d3\u02d5\u0003P(\u0000\u02d4\u02ce\u0001\u0000\u0000\u0000\u02d4"+
		"\u02cf\u0001\u0000\u0000\u0000\u02d5O\u0001\u0000\u0000\u0000\u02d6\u02d7"+
		"\u0005\u011f\u0000\u0000\u02d7\u02db\u0005\"\u0000\u0000\u02d8\u02d9\u0005"+
		"\u0121\u0000\u0000\u02d9\u02db\u0005\"\u0000\u0000\u02da\u02d6\u0001\u0000"+
		"\u0000\u0000\u02da\u02d8\u0001\u0000\u0000\u0000\u02dbQ\u0001\u0000\u0000"+
		"\u0000\u02dc\u032f\u0003`0\u0000\u02dd\u02de\u0005\u0095\u0000\u0000\u02de"+
		"\u02e9\u0005\u0003\u0000\u0000\u02df\u02e0\u0005\u00b8\u0000\u0000\u02e0"+
		"\u02e1\u0005\u0013\u0000\u0000\u02e1\u02e6\u0003~?\u0000\u02e2\u02e3\u0005"+
		"\u0002\u0000\u0000\u02e3\u02e5\u0003~?\u0000\u02e4\u02e2\u0001\u0000\u0000"+
		"\u0000\u02e5\u02e8\u0001\u0000\u0000\u0000\u02e6\u02e4\u0001\u0000\u0000"+
		"\u0000\u02e6\u02e7\u0001\u0000\u0000\u0000\u02e7\u02ea\u0001\u0000\u0000"+
		"\u0000\u02e8\u02e6\u0001\u0000\u0000\u0000\u02e9\u02df\u0001\u0000\u0000"+
		"\u0000\u02e9\u02ea\u0001\u0000\u0000\u0000\u02ea\u02f5\u0001\u0000\u0000"+
		"\u0000\u02eb\u02ec\u0005\u00b2\u0000\u0000\u02ec\u02ed\u0005\u0013\u0000"+
		"\u0000\u02ed\u02f2\u0003.\u0017\u0000\u02ee\u02ef\u0005\u0002\u0000\u0000"+
		"\u02ef\u02f1\u0003.\u0017\u0000\u02f0\u02ee\u0001\u0000\u0000\u0000\u02f1"+
		"\u02f4\u0001\u0000\u0000\u0000\u02f2\u02f0\u0001\u0000\u0000\u0000\u02f2"+
		"\u02f3\u0001\u0000\u0000\u0000\u02f3\u02f6\u0001\u0000\u0000\u0000\u02f4"+
		"\u02f2\u0001\u0000\u0000\u0000\u02f5\u02eb\u0001\u0000\u0000\u0000\u02f5"+
		"\u02f6\u0001\u0000\u0000\u0000\u02f6\u0300\u0001\u0000\u0000\u0000\u02f7"+
		"\u02f8\u0005\u0097\u0000\u0000\u02f8\u02fd\u0003T*\u0000\u02f9\u02fa\u0005"+
		"\u0002\u0000\u0000\u02fa\u02fc\u0003T*\u0000\u02fb\u02f9\u0001\u0000\u0000"+
		"\u0000\u02fc\u02ff\u0001\u0000\u0000\u0000\u02fd\u02fb\u0001\u0000\u0000"+
		"\u0000\u02fd\u02fe\u0001\u0000\u0000\u0000\u02fe\u0301\u0001\u0000\u0000"+
		"\u0000\u02ff\u02fd\u0001\u0000\u0000\u0000\u0300\u02f7\u0001\u0000\u0000"+
		"\u0000\u0300\u0301\u0001\u0000\u0000\u0000\u0301\u0303\u0001\u0000\u0000"+
		"\u0000\u0302\u0304\u0003V+\u0000\u0303\u0302\u0001\u0000\u0000\u0000\u0303"+
		"\u0304\u0001\u0000\u0000\u0000\u0304\u0308\u0001\u0000\u0000\u0000\u0305"+
		"\u0306\u0005\u0004\u0000\u0000\u0306\u0307\u0005\u0092\u0000\u0000\u0307"+
		"\u0309\u0003Z-\u0000\u0308\u0305\u0001\u0000\u0000\u0000\u0308\u0309\u0001"+
		"\u0000\u0000\u0000\u0309\u030b\u0001\u0000\u0000\u0000\u030a\u030c\u0007"+
		"\n\u0000\u0000\u030b\u030a\u0001\u0000\u0000\u0000\u030b\u030c\u0001\u0000"+
		"\u0000\u0000\u030c\u030d\u0001\u0000\u0000\u0000\u030d\u030e\u0005\u00bd"+
		"\u0000\u0000\u030e\u030f\u0005\u0003\u0000\u0000\u030f\u0310\u0003\u00c2"+
		"a\u0000\u0310\u031a\u0005\u0004\u0000\u0000\u0311\u0312\u0005\u00f0\u0000"+
		"\u0000\u0312\u0317\u0003\\.\u0000\u0313\u0314\u0005\u0002\u0000\u0000"+
		"\u0314\u0316\u0003\\.\u0000\u0315\u0313\u0001\u0000\u0000\u0000\u0316"+
		"\u0319\u0001\u0000\u0000\u0000\u0317\u0315\u0001\u0000\u0000\u0000\u0317"+
		"\u0318\u0001\u0000\u0000\u0000\u0318\u031b\u0001\u0000\u0000\u0000\u0319"+
		"\u0317\u0001\u0000\u0000\u0000\u031a\u0311\u0001\u0000\u0000\u0000\u031a"+
		"\u031b\u0001\u0000\u0000\u0000\u031b\u031c\u0001\u0000\u0000\u0000\u031c"+
		"\u031d\u00056\u0000\u0000\u031d\u0322\u0003^/\u0000\u031e\u031f\u0005"+
		"\u0002\u0000\u0000\u031f\u0321\u0003^/\u0000\u0320\u031e\u0001\u0000\u0000"+
		"\u0000\u0321\u0324\u0001\u0000\u0000\u0000\u0322\u0320\u0001\u0000\u0000"+
		"\u0000\u0322\u0323\u0001\u0000\u0000\u0000\u0323\u0325\u0001\u0000\u0000"+
		"\u0000\u0324\u0322\u0001\u0000\u0000\u0000\u0325\u032d\u0005\u0004\u0000"+
		"\u0000\u0326\u0328\u0005\u000b\u0000\u0000\u0327\u0326\u0001\u0000\u0000"+
		"\u0000\u0327\u0328\u0001\u0000\u0000\u0000\u0328\u0329\u0001\u0000\u0000"+
		"\u0000\u0329\u032b\u0003\u0100\u0080\u0000\u032a\u032c\u0003b1\u0000\u032b"+
		"\u032a\u0001\u0000\u0000\u0000\u032b\u032c\u0001\u0000\u0000\u0000\u032c"+
		"\u032e\u0001\u0000\u0000\u0000\u032d\u0327\u0001\u0000\u0000\u0000\u032d"+
		"\u032e\u0001\u0000\u0000\u0000\u032e\u0330\u0001\u0000\u0000\u0000\u032f"+
		"\u02dd\u0001\u0000\u0000\u0000\u032f\u0330\u0001\u0000\u0000\u0000\u0330"+
		"S\u0001\u0000\u0000\u0000\u0331\u0332\u0003~?\u0000\u0332\u0333\u0005"+
		"\u000b\u0000\u0000\u0333\u0334\u0003\u0100\u0080\u0000\u0334U\u0001\u0000"+
		"\u0000\u0000\u0335\u0336\u0005\u00ae\u0000\u0000\u0336\u0337\u0005\u00de"+
		"\u0000\u0000\u0337\u0338\u0005\u00be\u0000\u0000\u0338\u0341\u0005\u0092"+
		"\u0000\u0000\u0339\u033a\u0005\u0005\u0000\u0000\u033a\u033b\u0005\u00df"+
		"\u0000\u0000\u033b\u033c\u0005\u00be\u0000\u0000\u033c\u033e\u0005\u0092"+
		"\u0000\u0000\u033d\u033f\u0003X,\u0000\u033e\u033d\u0001\u0000\u0000\u0000"+
		"\u033e\u033f\u0001\u0000\u0000\u0000\u033f\u0341\u0001\u0000\u0000\u0000"+
		"\u0340\u0335\u0001\u0000\u0000\u0000\u0340\u0339\u0001\u0000\u0000\u0000"+
		"\u0341W\u0001\u0000\u0000\u0000\u0342\u0343\u0005\u00ec\u0000\u0000\u0343"+
		"\u0344\u0005D\u0000\u0000\u0344\u034c\u0005\u0094\u0000\u0000\u0345\u0346"+
		"\u0005\u00ac\u0000\u0000\u0346\u0347\u0005D\u0000\u0000\u0347\u034c\u0005"+
		"\u0094\u0000\u0000\u0348\u0349\u0005\u011f\u0000\u0000\u0349\u034a\u0005"+
		"\u010b\u0000\u0000\u034a\u034c\u0005\u00df\u0000\u0000\u034b\u0342\u0001"+
		"\u0000\u0000\u0000\u034b\u0345\u0001\u0000\u0000\u0000\u034b\u0348\u0001"+
		"\u0000\u0000\u0000\u034cY\u0001\u0000\u0000\u0000\u034d\u034e\u0005\u0005"+
		"\u0000\u0000\u034e\u034f\u0005\u00fc\u0000\u0000\u034f\u0350\u0005\u009d"+
		"\u0000\u0000\u0350\u0361\u0005\u00de\u0000\u0000\u0351\u0352\u0005\u0005"+
		"\u0000\u0000\u0352\u0353\u0005\u00bb\u0000\u0000\u0353\u0354\u0005\u0083"+
		"\u0000\u0000\u0354\u0361\u0005\u00de\u0000\u0000\u0355\u0356\u0005\u0005"+
		"\u0000\u0000\u0356\u0357\u0005\u00fc\u0000\u0000\u0357\u0358\u0005T\u0000"+
		"\u0000\u0358\u0361\u0003\u0100\u0080\u0000\u0359\u035a\u0005\u0005\u0000"+
		"\u0000\u035a\u035b\u0005\u00fc\u0000\u0000\u035b\u035c\u0005\u0083\u0000"+
		"\u0000\u035c\u0361\u0003\u0100\u0080\u0000\u035d\u035e\u0005\u0005\u0000"+
		"\u0000\u035e\u035f\u0005\u00fc\u0000\u0000\u035f\u0361\u0003\u0100\u0080"+
		"\u0000\u0360\u034d\u0001\u0000\u0000\u0000\u0360\u0351\u0001\u0000\u0000"+
		"\u0000\u0360\u0355\u0001\u0000\u0000\u0000\u0360\u0359\u0001\u0000\u0000"+
		"\u0000\u0360\u035d\u0001\u0000\u0000\u0000\u0361[\u0001\u0000\u0000\u0000"+
		"\u0362\u0363\u0003\u0100\u0080\u0000\u0363\u0364\u0005\u0127\u0000\u0000"+
		"\u0364\u0365\u0005\u0003\u0000\u0000\u0365\u036a\u0003\u0100\u0080\u0000"+
		"\u0366\u0367\u0005\u0002\u0000\u0000\u0367\u0369\u0003\u0100\u0080\u0000"+
		"\u0368\u0366\u0001\u0000\u0000\u0000\u0369\u036c\u0001\u0000\u0000\u0000"+
		"\u036a\u0368\u0001\u0000\u0000\u0000\u036a\u036b\u0001\u0000\u0000\u0000"+
		"\u036b\u036d\u0001\u0000\u0000\u0000\u036c\u036a\u0001\u0000\u0000\u0000"+
		"\u036d\u036e\u0005\u0004\u0000\u0000\u036e]\u0001\u0000\u0000\u0000\u036f"+
		"\u0370\u0003\u0100\u0080\u0000\u0370\u0371\u0005\u000b\u0000\u0000\u0371"+
		"\u0372\u0003~?\u0000\u0372_\u0001\u0000\u0000\u0000\u0373\u037b\u0003"+
		"d2\u0000\u0374\u0376\u0005\u000b\u0000\u0000\u0375\u0374\u0001\u0000\u0000"+
		"\u0000\u0375\u0376\u0001\u0000\u0000\u0000\u0376\u0377\u0001\u0000\u0000"+
		"\u0000\u0377\u0379\u0003\u0100\u0080\u0000\u0378\u037a\u0003b1\u0000\u0379"+
		"\u0378\u0001\u0000\u0000\u0000\u0379\u037a\u0001\u0000\u0000\u0000\u037a"+
		"\u037c\u0001\u0000\u0000\u0000\u037b\u0375\u0001\u0000\u0000\u0000\u037b"+
		"\u037c\u0001\u0000\u0000\u0000\u037ca\u0001\u0000\u0000\u0000\u037d\u037e"+
		"\u0005\u0003\u0000\u0000\u037e\u0383\u0003\u0100\u0080\u0000\u037f\u0380"+
		"\u0005\u0002\u0000\u0000\u0380\u0382\u0003\u0100\u0080\u0000\u0381\u037f"+
		"\u0001\u0000\u0000\u0000\u0382\u0385\u0001\u0000\u0000\u0000\u0383\u0381"+
		"\u0001\u0000\u0000\u0000\u0383\u0384\u0001\u0000\u0000\u0000\u0384\u0386"+
		"\u0001\u0000\u0000\u0000\u0385\u0383\u0001\u0000\u0000\u0000\u0386\u0387"+
		"\u0005\u0004\u0000\u0000\u0387c\u0001\u0000\u0000\u0000\u0388\u038a\u0003"+
		"\u00f2y\u0000\u0389\u038b\u0003\u00f4z\u0000\u038a\u0389\u0001\u0000\u0000"+
		"\u0000\u038a\u038b\u0001\u0000\u0000\u0000\u038b\u03d0\u0001\u0000\u0000"+
		"\u0000\u038c\u038d\u0005\u0003\u0000\u0000\u038d\u038e\u0003\u0012\t\u0000"+
		"\u038e\u038f\u0005\u0004\u0000\u0000\u038f\u03d0\u0001\u0000\u0000\u0000"+
		"\u0390\u0391\u0005\u010c\u0000\u0000\u0391\u0392\u0005\u0003\u0000\u0000"+
		"\u0392\u0397\u0003~?\u0000\u0393\u0394\u0005\u0002\u0000\u0000\u0394\u0396"+
		"\u0003~?\u0000\u0395\u0393\u0001\u0000\u0000\u0000\u0396\u0399\u0001\u0000"+
		"\u0000\u0000\u0397\u0395\u0001\u0000\u0000\u0000\u0397\u0398\u0001\u0000"+
		"\u0000\u0000\u0398\u039a\u0001\u0000\u0000\u0000\u0399\u0397\u0001\u0000"+
		"\u0000\u0000\u039a\u039d\u0005\u0004\u0000\u0000\u039b\u039c\u0005\u011f"+
		"\u0000\u0000\u039c\u039e\u0005\u00b3\u0000\u0000\u039d\u039b\u0001\u0000"+
		"\u0000\u0000\u039d\u039e\u0001\u0000\u0000\u0000\u039e\u03d0\u0001\u0000"+
		"\u0000\u0000\u039f\u03a0\u0005\u0084\u0000\u0000\u03a0\u03a1\u0005\u0003"+
		"\u0000\u0000\u03a1\u03a2\u0003\u0012\t\u0000\u03a2\u03a3\u0005\u0004\u0000"+
		"\u0000\u03a3\u03d0\u0001\u0000\u0000\u0000\u03a4\u03a5\u0005\u00f3\u0000"+
		"\u0000\u03a5\u03a6\u0005\u0003\u0000\u0000\u03a6\u03a7\u0003p8\u0000\u03a7"+
		"\u03a8\u0005\u0004\u0000\u0000\u03a8\u03d0\u0001\u0000\u0000\u0000\u03a9"+
		"\u03aa\u0005\u0003\u0000\u0000\u03aa\u03ab\u0003B!\u0000\u03ab\u03ac\u0005"+
		"\u0004\u0000\u0000\u03ac\u03d0\u0001\u0000\u0000\u0000\u03ad\u03ae\u0005"+
		"}\u0000\u0000\u03ae\u03af\u0005\u0003\u0000\u0000\u03af\u03b0\u0003\u0088"+
		"D\u0000\u03b0\u03b1\u0005\u001c\u0000\u0000\u03b1\u03b2\u0005\u0003\u0000"+
		"\u0000\u03b2\u03b7\u0003f3\u0000\u03b3\u03b4\u0005\u0002\u0000\u0000\u03b4"+
		"\u03b6\u0003f3\u0000\u03b5\u03b3\u0001\u0000\u0000\u0000\u03b6\u03b9\u0001"+
		"\u0000\u0000\u0000\u03b7\u03b5\u0001\u0000\u0000\u0000\u03b7\u03b8\u0001"+
		"\u0000\u0000\u0000\u03b8\u03ba\u0001\u0000\u0000\u0000\u03b9\u03b7\u0001"+
		"\u0000\u0000\u0000\u03ba\u03c6\u0005\u0004\u0000\u0000\u03bb\u03bc\u0005"+
		"\u00c1\u0000\u0000\u03bc\u03bd\u0005\u0003\u0000\u0000\u03bd\u03be\u0003"+
		"h4\u0000\u03be\u03bf\u0005\u0004\u0000\u0000\u03bf\u03c7\u0001\u0000\u0000"+
		"\u0000\u03c0\u03c1\u0005\u00c1\u0000\u0000\u03c1\u03c2\u00055\u0000\u0000"+
		"\u03c2\u03c3\u0005\u0003\u0000\u0000\u03c3\u03c4\u0003n7\u0000\u03c4\u03c5"+
		"\u0005\u0004\u0000\u0000\u03c5\u03c7\u0001\u0000\u0000\u0000\u03c6\u03bb"+
		"\u0001\u0000\u0000\u0000\u03c6\u03c0\u0001\u0000\u0000\u0000\u03c6\u03c7"+
		"\u0001\u0000\u0000\u0000\u03c7\u03cb\u0001\u0000\u0000\u0000\u03c8\u03c9"+
		"\u0007\u000b\u0000\u0000\u03c9\u03ca\u0005\u00ad\u0000\u0000\u03ca\u03cc"+
		"\u0005H\u0000\u0000\u03cb\u03c8\u0001\u0000\u0000\u0000\u03cb\u03cc\u0001"+
		"\u0000\u0000\u0000\u03cc\u03cd\u0001\u0000\u0000\u0000\u03cd\u03ce\u0005"+
		"\u0004\u0000\u0000\u03ce\u03d0\u0001\u0000\u0000\u0000\u03cf\u0388\u0001"+
		"\u0000\u0000\u0000\u03cf\u038c\u0001\u0000\u0000\u0000\u03cf\u0390\u0001"+
		"\u0000\u0000\u0000\u03cf\u039f\u0001\u0000\u0000\u0000\u03cf\u03a4\u0001"+
		"\u0000\u0000\u0000\u03cf\u03a9\u0001\u0000\u0000\u0000\u03cf\u03ad\u0001"+
		"\u0000\u0000\u0000\u03d0e\u0001\u0000\u0000\u0000\u03d1\u03d2\u0003\u0100"+
		"\u0080\u0000\u03d2\u03d3\u0005V\u0000\u0000\u03d3\u03d4\u0005\u00b3\u0000"+
		"\u0000\u03d4\u041f\u0001\u0000\u0000\u0000\u03d5\u03d6\u0003\u0100\u0080"+
		"\u0000\u03d6\u03d9\u0003\u00aeW\u0000\u03d7\u03d8\u0005\u00bc\u0000\u0000"+
		"\u03d8\u03da\u0003\u009eO\u0000\u03d9\u03d7\u0001\u0000\u0000\u0000\u03d9"+
		"\u03da\u0001\u0000\u0000\u0000\u03da\u03df\u0001\u0000\u0000\u0000\u03db"+
		"\u03dc\u0003\u0092I\u0000\u03dc\u03dd\u0005\u00ad\u0000\u0000\u03dd\u03de"+
		"\u0005D\u0000\u0000\u03de\u03e0\u0001\u0000\u0000\u0000\u03df\u03db\u0001"+
		"\u0000\u0000\u0000\u03df\u03e0\u0001\u0000\u0000\u0000\u03e0\u03e5\u0001"+
		"\u0000\u0000\u0000\u03e1\u03e2\u0003\u0092I\u0000\u03e2\u03e3\u0005\u00ad"+
		"\u0000\u0000\u03e3\u03e4\u0005H\u0000\u0000\u03e4\u03e6\u0001\u0000\u0000"+
		"\u0000\u03e5\u03e1\u0001\u0000\u0000\u0000\u03e5\u03e6\u0001\u0000\u0000"+
		"\u0000\u03e6\u041f\u0001\u0000\u0000\u0000\u03e7\u03e8\u0003\u0100\u0080"+
		"\u0000\u03e8\u03e9\u0003\u00aeW\u0000\u03e9\u03ea\u0005W\u0000\u0000\u03ea"+
		"\u03ed\u0003\u008cF\u0000\u03eb\u03ec\u0005\u00bc\u0000\u0000\u03ec\u03ee"+
		"\u0003\u009eO\u0000\u03ed\u03eb\u0001\u0000\u0000\u0000\u03ed\u03ee\u0001"+
		"\u0000\u0000\u0000\u03ee\u03f2\u0001\u0000\u0000\u0000\u03ef\u03f0\u0003"+
		"\u0094J\u0000\u03f0\u03f1\u0005\u0123\u0000\u0000\u03f1\u03f3\u0001\u0000"+
		"\u0000\u0000\u03f2\u03ef\u0001\u0000\u0000\u0000\u03f2\u03f3\u0001\u0000"+
		"\u0000\u0000\u03f3\u03fb\u0001\u0000\u0000\u0000\u03f4\u03f5\u0007\f\u0000"+
		"\u0000\u03f5\u03f9\u0005\u00c9\u0000\u0000\u03f6\u03f7\u0005\u00ad\u0000"+
		"\u0000\u03f7\u03f8\u0005\u00e1\u0000\u0000\u03f8\u03fa\u0005\u00f7\u0000"+
		"\u0000\u03f9\u03f6\u0001\u0000\u0000\u0000\u03f9\u03fa\u0001\u0000\u0000"+
		"\u0000\u03fa\u03fc\u0001\u0000\u0000\u0000\u03fb\u03f4\u0001\u0000\u0000"+
		"\u0000\u03fb\u03fc\u0001\u0000\u0000\u0000\u03fc\u0401\u0001\u0000\u0000"+
		"\u0000\u03fd\u03fe\u0003\u0096K\u0000\u03fe\u03ff\u0005\u00ad\u0000\u0000"+
		"\u03ff\u0400\u0005D\u0000\u0000\u0400\u0402\u0001\u0000\u0000\u0000\u0401"+
		"\u03fd\u0001\u0000\u0000\u0000\u0401\u0402\u0001\u0000\u0000\u0000\u0402"+
		"\u0407\u0001\u0000\u0000\u0000\u0403\u0404\u0003\u0096K\u0000\u0404\u0405"+
		"\u0005\u00ad\u0000\u0000\u0405\u0406\u0005H\u0000\u0000\u0406\u0408\u0001"+
		"\u0000\u0000\u0000\u0407\u0403\u0001\u0000\u0000\u0000\u0407\u0408\u0001"+
		"\u0000\u0000\u0000\u0408\u041f\u0001\u0000\u0000\u0000\u0409\u040b\u0005"+
		"\u009c\u0000\u0000\u040a\u040c\u0005\u00bc\u0000\u0000\u040b\u040a\u0001"+
		"\u0000\u0000\u0000\u040b\u040c\u0001\u0000\u0000\u0000\u040c\u040d\u0001"+
		"\u0000\u0000\u0000\u040d\u0410\u0003\u009eO\u0000\u040e\u040f\u0005\u000b"+
		"\u0000\u0000\u040f\u0411\u0003\u0100\u0080\u0000\u0410\u040e\u0001\u0000"+
		"\u0000\u0000\u0410\u0411\u0001\u0000\u0000\u0000\u0411\u0412\u0001\u0000"+
		"\u0000\u0000\u0412\u0413\u0005\u001c\u0000\u0000\u0413\u0414\u0005\u0003"+
		"\u0000\u0000\u0414\u0419\u0003f3\u0000\u0415\u0416\u0005\u0002\u0000\u0000"+
		"\u0416\u0418\u0003f3\u0000\u0417\u0415\u0001\u0000\u0000\u0000\u0418\u041b"+
		"\u0001\u0000\u0000\u0000\u0419\u0417\u0001\u0000\u0000\u0000\u0419\u041a"+
		"\u0001\u0000\u0000\u0000\u041a\u041c\u0001\u0000\u0000\u0000\u041b\u0419"+
		"\u0001\u0000\u0000\u0000\u041c\u041d\u0005\u0004\u0000\u0000\u041d\u041f"+
		"\u0001\u0000\u0000\u0000\u041e\u03d1\u0001\u0000\u0000\u0000\u041e\u03d5"+
		"\u0001\u0000\u0000\u0000\u041e\u03e7\u0001\u0000\u0000\u0000\u041e\u0409"+
		"\u0001\u0000\u0000\u0000\u041fg\u0001\u0000\u0000\u0000\u0420\u043a\u0003"+
		"j5\u0000\u0421\u0422\u0003j5\u0000\u0422\u0423\u0007\r\u0000\u0000\u0423"+
		"\u0424\u0003l6\u0000\u0424\u043a\u0001\u0000\u0000\u0000\u0425\u0426\u0003"+
		"l6\u0000\u0426\u0427\u0005\u0108\u0000\u0000\u0427\u042c\u0003l6\u0000"+
		"\u0428\u0429\u0005\u0108\u0000\u0000\u0429\u042b\u0003l6\u0000\u042a\u0428"+
		"\u0001\u0000\u0000\u0000\u042b\u042e\u0001\u0000\u0000\u0000\u042c\u042a"+
		"\u0001\u0000\u0000\u0000\u042c\u042d\u0001\u0000\u0000\u0000\u042d\u043a"+
		"\u0001\u0000\u0000\u0000\u042e\u042c\u0001\u0000\u0000\u0000\u042f\u0430"+
		"\u0003l6\u0000\u0430\u0431\u0005%\u0000\u0000\u0431\u0436\u0003l6\u0000"+
		"\u0432\u0433\u0005%\u0000\u0000\u0433\u0435\u0003l6\u0000\u0434\u0432"+
		"\u0001\u0000\u0000\u0000\u0435\u0438\u0001\u0000\u0000\u0000\u0436\u0434"+
		"\u0001\u0000\u0000\u0000\u0436\u0437\u0001\u0000\u0000\u0000\u0437\u043a"+
		"\u0001\u0000\u0000\u0000\u0438\u0436\u0001\u0000\u0000\u0000\u0439\u0420"+
		"\u0001\u0000\u0000\u0000\u0439\u0421\u0001\u0000\u0000\u0000\u0439\u0425"+
		"\u0001\u0000\u0000\u0000\u0439\u042f\u0001\u0000\u0000\u0000\u043ai\u0001"+
		"\u0000\u0000\u0000\u043b\u043c\u0003\u0100\u0080\u0000\u043ck\u0001\u0000"+
		"\u0000\u0000\u043d\u0443\u0003j5\u0000\u043e\u043f\u0005\u0003\u0000\u0000"+
		"\u043f\u0440\u0003h4\u0000\u0440\u0441\u0005\u0004\u0000\u0000\u0441\u0443"+
		"\u0001\u0000\u0000\u0000\u0442\u043d\u0001\u0000\u0000\u0000\u0442\u043e"+
		"\u0001\u0000\u0000\u0000\u0443m\u0001\u0000\u0000\u0000\u0444\u0447\u0007"+
		"\r\u0000\u0000\u0445\u0446\u0005\u0002\u0000\u0000\u0446\u0448\u0007\u000e"+
		"\u0000\u0000\u0447\u0445\u0001\u0000\u0000\u0000\u0447\u0448\u0001\u0000"+
		"\u0000\u0000\u0448\u044f\u0001\u0000\u0000\u0000\u0449\u044c\u0007\u000e"+
		"\u0000\u0000\u044a\u044b\u0005\u0002\u0000\u0000\u044b\u044d\u0007\r\u0000"+
		"\u0000\u044c\u044a\u0001\u0000\u0000\u0000\u044c\u044d\u0001\u0000\u0000"+
		"\u0000\u044d\u044f\u0001\u0000\u0000\u0000\u044e\u0444\u0001\u0000\u0000"+
		"\u0000\u044e\u0449\u0001\u0000\u0000\u0000\u044fo\u0001\u0000\u0000\u0000"+
		"\u0450\u0451\u0003\u00f2y\u0000\u0451\u045a\u0005\u0003\u0000\u0000\u0452"+
		"\u0457\u0003r9\u0000\u0453\u0454\u0005\u0002\u0000\u0000\u0454\u0456\u0003"+
		"r9\u0000\u0455\u0453\u0001\u0000\u0000\u0000\u0456\u0459\u0001\u0000\u0000"+
		"\u0000\u0457\u0455\u0001\u0000\u0000\u0000\u0457\u0458\u0001\u0000\u0000"+
		"\u0000\u0458\u045b\u0001\u0000\u0000\u0000\u0459\u0457\u0001\u0000\u0000"+
		"\u0000\u045a\u0452\u0001\u0000\u0000\u0000\u045a\u045b\u0001\u0000\u0000"+
		"\u0000\u045b\u0465\u0001\u0000\u0000\u0000\u045c\u045d\u0005#\u0000\u0000"+
		"\u045d\u0462\u0003|>\u0000\u045e\u045f\u0005\u0002\u0000\u0000\u045f\u0461"+
		"\u0003|>\u0000\u0460\u045e\u0001\u0000\u0000\u0000\u0461\u0464\u0001\u0000"+
		"\u0000\u0000\u0462\u0460\u0001\u0000\u0000\u0000\u0462\u0463\u0001\u0000"+
		"\u0000\u0000\u0463\u0466\u0001\u0000\u0000\u0000\u0464\u0462\u0001\u0000"+
		"\u0000\u0000\u0465\u045c\u0001\u0000\u0000\u0000\u0465\u0466\u0001\u0000"+
		"\u0000\u0000\u0466\u0467\u0001\u0000\u0000\u0000\u0467\u0468\u0005\u0004"+
		"\u0000\u0000\u0468q\u0001\u0000\u0000\u0000\u0469\u046a\u0003\u0100\u0080"+
		"\u0000\u046a\u046b\u0005\u0006\u0000\u0000\u046b\u046d\u0001\u0000\u0000"+
		"\u0000\u046c\u0469\u0001\u0000\u0000\u0000\u046c\u046d\u0001\u0000\u0000"+
		"\u0000\u046d\u0471\u0001\u0000\u0000\u0000\u046e\u0472\u0003t:\u0000\u046f"+
		"\u0472\u0003x<\u0000\u0470\u0472\u0003~?\u0000\u0471\u046e\u0001\u0000"+
		"\u0000\u0000\u0471\u046f\u0001\u0000\u0000\u0000\u0471\u0470\u0001\u0000"+
		"\u0000\u0000\u0472s\u0001\u0000\u0000\u0000\u0473\u0485\u0003v;\u0000"+
		"\u0474\u0475\u0005\u00b8\u0000\u0000\u0475\u0483\u0005\u0013\u0000\u0000"+
		"\u0476\u047f\u0005\u0003\u0000\u0000\u0477\u047c\u0003~?\u0000\u0478\u0479"+
		"\u0005\u0002\u0000\u0000\u0479\u047b\u0003~?\u0000\u047a\u0478\u0001\u0000"+
		"\u0000\u0000\u047b\u047e\u0001\u0000\u0000\u0000\u047c\u047a\u0001\u0000"+
		"\u0000\u0000\u047c\u047d\u0001\u0000\u0000\u0000\u047d\u0480\u0001\u0000"+
		"\u0000\u0000\u047e\u047c\u0001\u0000\u0000\u0000\u047f\u0477\u0001\u0000"+
		"\u0000\u0000\u047f\u0480\u0001\u0000\u0000\u0000\u0480\u0481\u0001\u0000"+
		"\u0000\u0000\u0481\u0484\u0005\u0004\u0000\u0000\u0482\u0484\u0003~?\u0000"+
		"\u0483\u0476\u0001\u0000\u0000\u0000\u0483\u0482\u0001\u0000\u0000\u0000"+
		"\u0484\u0486\u0001\u0000\u0000\u0000\u0485\u0474\u0001\u0000\u0000\u0000"+
		"\u0485\u0486\u0001\u0000\u0000\u0000\u0486\u048d\u0001\u0000\u0000\u0000"+
		"\u0487\u0488\u0005\u00c8\u0000\u0000\u0488\u0489\u0005\u011b\u0000\u0000"+
		"\u0489\u048e\u0005D\u0000\u0000\u048a\u048b\u0005\u007f\u0000\u0000\u048b"+
		"\u048c\u0005\u011b\u0000\u0000\u048c\u048e\u0005D\u0000\u0000\u048d\u0487"+
		"\u0001\u0000\u0000\u0000\u048d\u048a\u0001\u0000\u0000\u0000\u048d\u048e"+
		"\u0001\u0000\u0000\u0000\u048e\u049f\u0001\u0000\u0000\u0000\u048f\u0490"+
		"\u0005\u00b2\u0000\u0000\u0490\u049d\u0005\u0013\u0000\u0000\u0491\u0492"+
		"\u0005\u0003\u0000\u0000\u0492\u0497\u0003.\u0017\u0000\u0493\u0494\u0005"+
		"\u0002\u0000\u0000\u0494\u0496\u0003.\u0017\u0000\u0495\u0493\u0001\u0000"+
		"\u0000\u0000\u0496\u0499\u0001\u0000\u0000\u0000\u0497\u0495\u0001\u0000"+
		"\u0000\u0000\u0497\u0498\u0001\u0000\u0000\u0000\u0498\u049a\u0001\u0000"+
		"\u0000\u0000\u0499\u0497\u0001\u0000\u0000\u0000\u049a\u049b\u0005\u0004"+
		"\u0000\u0000\u049b\u049e\u0001\u0000\u0000\u0000\u049c\u049e\u0003.\u0017"+
		"\u0000\u049d\u0491\u0001\u0000\u0000\u0000\u049d\u049c\u0001\u0000\u0000"+
		"\u0000\u049e\u04a0\u0001\u0000\u0000\u0000\u049f\u048f\u0001\u0000\u0000"+
		"\u0000\u049f\u04a0\u0001\u0000\u0000\u0000\u04a0u\u0001\u0000\u0000\u0000"+
		"\u04a1\u04a2\u0005\u00f3\u0000\u0000\u04a2\u04a3\u0005\u0003\u0000\u0000"+
		"\u04a3\u04a4\u0003\u00f2y\u0000\u04a4\u04ac\u0005\u0004\u0000\u0000\u04a5"+
		"\u04a7\u0005\u000b\u0000\u0000\u04a6\u04a5\u0001\u0000\u0000\u0000\u04a6"+
		"\u04a7\u0001\u0000\u0000\u0000\u04a7\u04a8\u0001\u0000\u0000\u0000\u04a8"+
		"\u04aa\u0003\u0100\u0080\u0000\u04a9\u04ab\u0003b1\u0000\u04aa\u04a9\u0001"+
		"\u0000\u0000\u0000\u04aa\u04ab\u0001\u0000\u0000\u0000\u04ab\u04ad\u0001"+
		"\u0000\u0000\u0000\u04ac\u04a6\u0001\u0000\u0000\u0000\u04ac\u04ad\u0001"+
		"\u0000\u0000\u0000\u04ad\u04bc\u0001\u0000\u0000\u0000\u04ae\u04af\u0005"+
		"\u00f3\u0000\u0000\u04af\u04b0\u0005\u0003\u0000\u0000\u04b0\u04b1\u0003"+
		"\u0012\t\u0000\u04b1\u04b9\u0005\u0004\u0000\u0000\u04b2\u04b4\u0005\u000b"+
		"\u0000\u0000\u04b3\u04b2\u0001\u0000\u0000\u0000\u04b3\u04b4\u0001\u0000"+
		"\u0000\u0000\u04b4\u04b5\u0001\u0000\u0000\u0000\u04b5\u04b7\u0003\u0100"+
		"\u0080\u0000\u04b6\u04b8\u0003b1\u0000\u04b7\u04b6\u0001\u0000\u0000\u0000"+
		"\u04b7\u04b8\u0001\u0000\u0000\u0000\u04b8\u04ba\u0001\u0000\u0000\u0000"+
		"\u04b9\u04b3\u0001\u0000\u0000\u0000\u04b9\u04ba\u0001\u0000\u0000\u0000"+
		"\u04ba\u04bc\u0001\u0000\u0000\u0000\u04bb\u04a1\u0001\u0000\u0000\u0000"+
		"\u04bb\u04ae\u0001\u0000\u0000\u0000\u04bcw\u0001\u0000\u0000\u0000\u04bd"+
		"\u04be\u0005<\u0000\u0000\u04be\u04bf\u0005\u0003\u0000\u0000\u04bf\u04c4"+
		"\u0003z=\u0000\u04c0\u04c1\u0005\u0002\u0000\u0000\u04c1\u04c3\u0003z"+
		"=\u0000\u04c2\u04c0\u0001\u0000\u0000\u0000\u04c3\u04c6\u0001\u0000\u0000"+
		"\u0000\u04c4\u04c2\u0001\u0000\u0000\u0000\u04c4\u04c5\u0001\u0000\u0000"+
		"\u0000\u04c5\u04c7\u0001\u0000\u0000\u0000\u04c6\u04c4\u0001\u0000\u0000"+
		"\u0000\u04c7\u04c8\u0005\u0004\u0000\u0000\u04c8\u04d0\u0001\u0000\u0000"+
		"\u0000\u04c9\u04ca\u0005\u0018\u0000\u0000\u04ca\u04cb\u0005\u0003\u0000"+
		"\u0000\u04cb\u04cc\u0005\u00a6\u0000\u0000\u04cc\u04cd\u0005\u000b\u0000"+
		"\u0000\u04cd\u04ce\u0005<\u0000\u0000\u04ce\u04d0\u0005\u0004\u0000\u0000"+
		"\u04cf\u04bd\u0001\u0000\u0000\u0000\u04cf\u04c9\u0001\u0000\u0000\u0000"+
		"\u04d0y\u0001\u0000\u0000\u0000\u04d1\u04d3\u0003\u0100\u0080\u0000\u04d2"+
		"\u04d4\u0003\u00aeW\u0000\u04d3\u04d2\u0001\u0000\u0000\u0000\u04d3\u04d4"+
		"\u0001\u0000\u0000\u0000\u04d4{\u0001\u0000\u0000\u0000\u04d5\u04d6\u0005"+
		"\u0003\u0000\u0000\u04d6\u04d7\u0003\u00f2y\u0000\u04d7\u04d8\u0005\u0002"+
		"\u0000\u0000\u04d8\u04dd\u0003\u00f2y\u0000\u04d9\u04da\u0005\u0002\u0000"+
		"\u0000\u04da\u04dc\u0003\u00f2y\u0000\u04db\u04d9\u0001\u0000\u0000\u0000"+
		"\u04dc\u04df\u0001\u0000\u0000\u0000\u04dd\u04db\u0001\u0000\u0000\u0000"+
		"\u04dd\u04de\u0001\u0000\u0000\u0000\u04de\u04e0\u0001\u0000\u0000\u0000"+
		"\u04df\u04dd\u0001\u0000\u0000\u0000\u04e0\u04e1\u0005\u0004\u0000\u0000"+
		"\u04e1}\u0001\u0000\u0000\u0000\u04e2\u04e3\u0003\u0080@\u0000\u04e3\u007f"+
		"\u0001\u0000\u0000\u0000\u04e4\u04e5\u0006@\uffff\uffff\u0000\u04e5\u04e7"+
		"\u0003\u0084B\u0000\u04e6\u04e8\u0003\u0082A\u0000\u04e7\u04e6\u0001\u0000"+
		"\u0000\u0000\u04e7\u04e8\u0001\u0000\u0000\u0000\u04e8\u04ec\u0001\u0000"+
		"\u0000\u0000\u04e9\u04ea\u0005\u00a5\u0000\u0000\u04ea\u04ec\u0003\u0080"+
		"@\u0003\u04eb\u04e4\u0001\u0000\u0000\u0000\u04eb\u04e9\u0001\u0000\u0000"+
		"\u0000\u04ec\u04f5\u0001\u0000\u0000\u0000\u04ed\u04ee\n\u0002\u0000\u0000"+
		"\u04ee\u04ef\u0005\b\u0000\u0000\u04ef\u04f4\u0003\u0080@\u0003\u04f0"+
		"\u04f1\n\u0001\u0000\u0000\u04f1\u04f2\u0005\u00b1\u0000\u0000\u04f2\u04f4"+
		"\u0003\u0080@\u0002\u04f3\u04ed\u0001\u0000\u0000\u0000\u04f3\u04f0\u0001"+
		"\u0000\u0000\u0000\u04f4\u04f7\u0001\u0000\u0000\u0000\u04f5\u04f3\u0001"+
		"\u0000\u0000\u0000\u04f5\u04f6\u0001\u0000\u0000\u0000\u04f6\u0081\u0001"+
		"\u0000\u0000\u0000\u04f7\u04f5\u0001\u0000\u0000\u0000\u04f8\u04f9\u0003"+
		"\u00a2Q\u0000\u04f9\u04fa\u0003\u0084B\u0000\u04fa\u0536\u0001\u0000\u0000"+
		"\u0000\u04fb\u04fc\u0003\u00a2Q\u0000\u04fc\u04fd\u0003\u00a4R\u0000\u04fd"+
		"\u04fe\u0005\u0003\u0000\u0000\u04fe\u04ff\u0003\u0012\t\u0000\u04ff\u0500"+
		"\u0005\u0004\u0000\u0000\u0500\u0536\u0001\u0000\u0000\u0000\u0501\u0503"+
		"\u0005\u00a5\u0000\u0000\u0502\u0501\u0001\u0000\u0000\u0000\u0502\u0503"+
		"\u0001\u0000\u0000\u0000\u0503\u0504\u0001\u0000\u0000\u0000\u0504\u0505"+
		"\u0005\u0011\u0000\u0000\u0505\u0506\u0003\u0084B\u0000\u0506\u0507\u0005"+
		"\b\u0000\u0000\u0507\u0508\u0003\u0084B\u0000\u0508\u0536\u0001\u0000"+
		"\u0000\u0000\u0509\u050b\u0005\u00a5\u0000\u0000\u050a\u0509\u0001\u0000"+
		"\u0000\u0000\u050a\u050b\u0001\u0000\u0000\u0000\u050b\u050c\u0001\u0000"+
		"\u0000\u0000\u050c\u050d\u0005i\u0000\u0000\u050d\u050e\u0005\u0003\u0000"+
		"\u0000\u050e\u0513\u0003~?\u0000\u050f\u0510\u0005\u0002\u0000\u0000\u0510"+
		"\u0512\u0003~?\u0000\u0511\u050f\u0001\u0000\u0000\u0000\u0512\u0515\u0001"+
		"\u0000\u0000\u0000\u0513\u0511\u0001\u0000\u0000\u0000\u0513\u0514\u0001"+
		"\u0000\u0000\u0000\u0514\u0516\u0001\u0000\u0000\u0000\u0515\u0513\u0001"+
		"\u0000\u0000\u0000\u0516\u0517\u0005\u0004\u0000\u0000\u0517\u0536\u0001"+
		"\u0000\u0000\u0000\u0518\u051a\u0005\u00a5\u0000\u0000\u0519\u0518\u0001"+
		"\u0000\u0000\u0000\u0519\u051a\u0001\u0000\u0000\u0000\u051a\u051b\u0001"+
		"\u0000\u0000\u0000\u051b\u051c\u0005i\u0000\u0000\u051c\u051d\u0005\u0003"+
		"\u0000\u0000\u051d\u051e\u0003\u0012\t\u0000\u051e\u051f\u0005\u0004\u0000"+
		"\u0000\u051f\u0536\u0001\u0000\u0000\u0000\u0520\u0522\u0005\u00a5\u0000"+
		"\u0000\u0521\u0520\u0001\u0000\u0000\u0000\u0521\u0522\u0001\u0000\u0000"+
		"\u0000\u0522\u0523\u0001\u0000\u0000\u0000\u0523\u0524\u0005\u0089\u0000"+
		"\u0000\u0524\u0527\u0003\u0084B\u0000\u0525\u0526\u0005I\u0000\u0000\u0526"+
		"\u0528\u0003\u0084B\u0000\u0527\u0525\u0001\u0000\u0000\u0000\u0527\u0528"+
		"\u0001\u0000\u0000\u0000\u0528\u0536\u0001\u0000\u0000\u0000\u0529\u052b"+
		"\u0005t\u0000\u0000\u052a\u052c\u0005\u00a5\u0000\u0000\u052b\u052a\u0001"+
		"\u0000\u0000\u0000\u052b\u052c\u0001\u0000\u0000\u0000\u052c\u052d\u0001"+
		"\u0000\u0000\u0000\u052d\u0536\u0005\u00a6\u0000\u0000\u052e\u0530\u0005"+
		"t\u0000\u0000\u052f\u0531\u0005\u00a5\u0000\u0000\u0530\u052f\u0001\u0000"+
		"\u0000\u0000\u0530\u0531\u0001\u0000\u0000\u0000\u0531\u0532\u0001\u0000"+
		"\u0000\u0000\u0532\u0533\u0005>\u0000\u0000\u0533\u0534\u0005X\u0000\u0000"+
		"\u0534\u0536\u0003\u0084B\u0000\u0535\u04f8\u0001\u0000\u0000\u0000\u0535"+
		"\u04fb\u0001\u0000\u0000\u0000\u0535\u0502\u0001\u0000\u0000\u0000\u0535"+
		"\u050a\u0001\u0000\u0000\u0000\u0535\u0519\u0001\u0000\u0000\u0000\u0535"+
		"\u0521\u0001\u0000\u0000\u0000\u0535\u0529\u0001\u0000\u0000\u0000\u0535"+
		"\u052e\u0001\u0000\u0000\u0000\u0536\u0083\u0001\u0000\u0000\u0000\u0537"+
		"\u0538\u0006B\uffff\uffff\u0000\u0538\u053c\u0003\u0086C\u0000\u0539\u053a"+
		"\u0007\u000f\u0000\u0000\u053a\u053c\u0003\u0084B\u0004\u053b\u0537\u0001"+
		"\u0000\u0000\u0000\u053b\u0539\u0001\u0000\u0000\u0000\u053c\u054b\u0001"+
		"\u0000\u0000\u0000\u053d\u053e\n\u0003\u0000\u0000\u053e\u053f\u0007\u0010"+
		"\u0000\u0000\u053f\u054a\u0003\u0084B\u0004\u0540\u0541\n\u0002\u0000"+
		"\u0000\u0541\u0542\u0007\u000f\u0000\u0000\u0542\u054a\u0003\u0084B\u0003"+
		"\u0543\u0544\n\u0001\u0000\u0000\u0544\u0545\u0005\u0132\u0000\u0000\u0545"+
		"\u054a\u0003\u0084B\u0002\u0546\u0547\n\u0005\u0000\u0000\u0547\u0548"+
		"\u0005\r\u0000\u0000\u0548\u054a\u0003\u00a0P\u0000\u0549\u053d\u0001"+
		"\u0000\u0000\u0000\u0549\u0540\u0001\u0000\u0000\u0000\u0549\u0543\u0001"+
		"\u0000\u0000\u0000\u0549\u0546\u0001\u0000\u0000\u0000\u054a\u054d\u0001"+
		"\u0000\u0000\u0000\u054b\u0549\u0001\u0000\u0000\u0000\u054b\u054c\u0001"+
		"\u0000\u0000\u0000\u054c\u0085\u0001\u0000\u0000\u0000\u054d\u054b\u0001"+
		"\u0000\u0000\u0000\u054e\u054f\u0006C\uffff\uffff\u0000\u054f\u0714\u0005"+
		"\u00a6\u0000\u0000\u0550\u0714\u0003\u00a8T\u0000\u0551\u0552\u0003\u0100"+
		"\u0080\u0000\u0552\u0553\u0003\u009eO\u0000\u0553\u0714\u0001\u0000\u0000"+
		"\u0000\u0554\u0555\u0005A\u0000\u0000\u0555\u0556\u0005\u00c4\u0000\u0000"+
		"\u0556\u0714\u0003\u009eO\u0000\u0557\u0714\u0003\u0102\u0081\u0000\u0558"+
		"\u0714\u0003\u00a6S\u0000\u0559\u0714\u0003\u009eO\u0000\u055a\u0714\u0005"+
		"\u0137\u0000\u0000\u055b\u0714\u0005\u0133\u0000\u0000\u055c\u055d\u0005"+
		"\u00c2\u0000\u0000\u055d\u055e\u0005\u0003\u0000\u0000\u055e\u055f\u0003"+
		"\u0084B\u0000\u055f\u0560\u0005i\u0000\u0000\u0560\u0561\u0003\u0084B"+
		"\u0000\u0561\u0562\u0005\u0004\u0000\u0000\u0562\u0714\u0001\u0000\u0000"+
		"\u0000\u0563\u0564\u0005\u0003\u0000\u0000\u0564\u0567\u0003~?\u0000\u0565"+
		"\u0566\u0005\u0002\u0000\u0000\u0566\u0568\u0003~?\u0000\u0567\u0565\u0001"+
		"\u0000\u0000\u0000\u0568\u0569\u0001\u0000\u0000\u0000\u0569\u0567\u0001"+
		"\u0000\u0000\u0000\u0569\u056a\u0001\u0000\u0000\u0000\u056a\u056b\u0001"+
		"\u0000\u0000\u0000\u056b\u056c\u0005\u0004\u0000\u0000\u056c\u0714\u0001"+
		"\u0000\u0000\u0000\u056d\u056e\u0005\u00de\u0000\u0000\u056e\u056f\u0005"+
		"\u0003\u0000\u0000\u056f\u0574\u0003~?\u0000\u0570\u0571\u0005\u0002\u0000"+
		"\u0000\u0571\u0573\u0003~?\u0000\u0572\u0570\u0001\u0000\u0000\u0000\u0573"+
		"\u0576\u0001\u0000\u0000\u0000\u0574\u0572\u0001\u0000\u0000\u0000\u0574"+
		"\u0575\u0001\u0000\u0000\u0000\u0575\u0577\u0001\u0000\u0000\u0000\u0576"+
		"\u0574\u0001\u0000\u0000\u0000\u0577\u0578\u0005\u0004\u0000\u0000\u0578"+
		"\u0714\u0001\u0000\u0000\u0000\u0579\u057a\u0005\u008b\u0000\u0000\u057a"+
		"\u057c\u0005\u0003\u0000\u0000\u057b\u057d\u0003>\u001f\u0000\u057c\u057b"+
		"\u0001\u0000\u0000\u0000\u057c\u057d\u0001\u0000\u0000\u0000\u057d\u057e"+
		"\u0001\u0000\u0000\u0000\u057e\u0581\u0003~?\u0000\u057f\u0580\u0005\u0002"+
		"\u0000\u0000\u0580\u0582\u0003\u009eO\u0000\u0581\u057f\u0001\u0000\u0000"+
		"\u0000\u0581\u0582\u0001\u0000\u0000\u0000\u0582\u0586\u0001\u0000\u0000"+
		"\u0000\u0583\u0584\u0005\u00ad\u0000\u0000\u0584\u0585\u0005\u00b7\u0000"+
		"\u0000\u0585\u0587\u0003N\'\u0000\u0586\u0583\u0001\u0000\u0000\u0000"+
		"\u0586\u0587\u0001\u0000\u0000\u0000\u0587\u0588\u0001\u0000\u0000\u0000"+
		"\u0588\u0589\u0005\u0004\u0000\u0000\u0589\u058a\u0005\u0120\u0000\u0000"+
		"\u058a\u058b\u0005a\u0000\u0000\u058b\u058c\u0005\u0003\u0000\u0000\u058c"+
		"\u058d\u0005\u00b2\u0000\u0000\u058d\u058e\u0005\u0013\u0000\u0000\u058e"+
		"\u0593\u0003.\u0017\u0000\u058f\u0590\u0005\u0002\u0000\u0000\u0590\u0592"+
		"\u0003.\u0017\u0000\u0591\u058f\u0001\u0000\u0000\u0000\u0592\u0595\u0001"+
		"\u0000\u0000\u0000\u0593\u0591\u0001\u0000\u0000\u0000\u0593\u0594\u0001"+
		"\u0000\u0000\u0000\u0594\u0596\u0001\u0000\u0000\u0000\u0595\u0593\u0001"+
		"\u0000\u0000\u0000\u0596\u0597\u0005\u0004\u0000\u0000\u0597\u0599\u0001"+
		"\u0000\u0000\u0000\u0598\u059a\u0003\u00b6[\u0000\u0599\u0598\u0001\u0000"+
		"\u0000\u0000\u0599\u059a\u0001\u0000\u0000\u0000\u059a\u0714\u0001\u0000"+
		"\u0000\u0000\u059b\u059d\u0003\u009aM\u0000\u059c\u059b\u0001\u0000\u0000"+
		"\u0000\u059c\u059d\u0001\u0000\u0000\u0000\u059d\u059e\u0001\u0000\u0000"+
		"\u0000\u059e\u059f\u0003\u00f2y\u0000\u059f\u05a3\u0005\u0003\u0000\u0000"+
		"\u05a0\u05a1\u0003\u0100\u0080\u0000\u05a1\u05a2\u0005\u0001\u0000\u0000"+
		"\u05a2\u05a4\u0001\u0000\u0000\u0000\u05a3\u05a0\u0001\u0000\u0000\u0000"+
		"\u05a3\u05a4\u0001\u0000\u0000\u0000\u05a4\u05a5\u0001\u0000\u0000\u0000"+
		"\u05a5\u05a6\u0005\u012f\u0000\u0000\u05a6\u05a8\u0005\u0004\u0000\u0000"+
		"\u05a7\u05a9\u0003\u00b6[\u0000\u05a8\u05a7\u0001\u0000\u0000\u0000\u05a8"+
		"\u05a9\u0001\u0000\u0000\u0000\u05a9\u05ab\u0001\u0000\u0000\u0000\u05aa"+
		"\u05ac\u0003\u00ba]\u0000\u05ab\u05aa\u0001\u0000\u0000\u0000\u05ab\u05ac"+
		"\u0001\u0000\u0000\u0000\u05ac\u0714\u0001\u0000\u0000\u0000\u05ad\u05af"+
		"\u0003\u009aM\u0000\u05ae\u05ad\u0001\u0000\u0000\u0000\u05ae\u05af\u0001"+
		"\u0000\u0000\u0000\u05af\u05b0\u0001\u0000\u0000\u0000\u05b0\u05b1\u0003"+
		"\u00f2y\u0000\u05b1\u05bd\u0005\u0003\u0000\u0000\u05b2\u05b4\u0003>\u001f"+
		"\u0000\u05b3\u05b2\u0001\u0000\u0000\u0000\u05b3\u05b4\u0001\u0000\u0000"+
		"\u0000\u05b4\u05b5\u0001\u0000\u0000\u0000\u05b5\u05ba\u0003~?\u0000\u05b6"+
		"\u05b7\u0005\u0002\u0000\u0000\u05b7\u05b9\u0003~?\u0000\u05b8\u05b6\u0001"+
		"\u0000\u0000\u0000\u05b9\u05bc\u0001\u0000\u0000\u0000\u05ba\u05b8\u0001"+
		"\u0000\u0000\u0000\u05ba\u05bb\u0001\u0000\u0000\u0000\u05bb\u05be\u0001"+
		"\u0000\u0000\u0000\u05bc\u05ba\u0001\u0000\u0000\u0000\u05bd\u05b3\u0001"+
		"\u0000\u0000\u0000\u05bd\u05be\u0001\u0000\u0000\u0000\u05be\u05c9\u0001"+
		"\u0000\u0000\u0000\u05bf\u05c0\u0005\u00b2\u0000\u0000\u05c0\u05c1\u0005"+
		"\u0013\u0000\u0000\u05c1\u05c6\u0003.\u0017\u0000\u05c2\u05c3\u0005\u0002"+
		"\u0000\u0000\u05c3\u05c5\u0003.\u0017\u0000\u05c4\u05c2\u0001\u0000\u0000"+
		"\u0000\u05c5\u05c8\u0001\u0000\u0000\u0000\u05c6\u05c4\u0001\u0000\u0000"+
		"\u0000\u05c6\u05c7\u0001\u0000\u0000\u0000\u05c7\u05ca\u0001\u0000\u0000"+
		"\u0000\u05c8\u05c6\u0001\u0000\u0000\u0000\u05c9\u05bf\u0001\u0000\u0000"+
		"\u0000\u05c9\u05ca\u0001\u0000\u0000\u0000\u05ca\u05cb\u0001\u0000\u0000"+
		"\u0000\u05cb\u05cd\u0005\u0004\u0000\u0000\u05cc\u05ce\u0003\u00b6[\u0000"+
		"\u05cd\u05cc\u0001\u0000\u0000\u0000\u05cd\u05ce\u0001\u0000\u0000\u0000"+
		"\u05ce\u05d3\u0001\u0000\u0000\u0000\u05cf\u05d1\u0003\u009cN\u0000\u05d0"+
		"\u05cf\u0001\u0000\u0000\u0000\u05d0\u05d1\u0001\u0000\u0000\u0000\u05d1"+
		"\u05d2\u0001\u0000\u0000\u0000\u05d2\u05d4\u0003\u00ba]\u0000\u05d3\u05d0"+
		"\u0001\u0000\u0000\u0000\u05d3\u05d4\u0001\u0000\u0000\u0000\u05d4\u0714"+
		"\u0001\u0000\u0000\u0000\u05d5\u05d6\u0003\u0100\u0080\u0000\u05d6\u05d7"+
		"\u0003\u00ba]\u0000\u05d7\u0714\u0001\u0000\u0000\u0000\u05d8\u05d9\u0003"+
		"\u0100\u0080\u0000\u05d9\u05da\u0005\u0007\u0000\u0000\u05da\u05db\u0003"+
		"~?\u0000\u05db\u0714\u0001\u0000\u0000\u0000\u05dc\u05e5\u0005\u0003\u0000"+
		"\u0000\u05dd\u05e2\u0003\u0100\u0080\u0000\u05de\u05df\u0005\u0002\u0000"+
		"\u0000\u05df\u05e1\u0003\u0100\u0080\u0000\u05e0\u05de\u0001\u0000\u0000"+
		"\u0000\u05e1\u05e4\u0001\u0000\u0000\u0000\u05e2\u05e0\u0001\u0000\u0000"+
		"\u0000\u05e2\u05e3\u0001\u0000\u0000\u0000\u05e3\u05e6\u0001\u0000\u0000"+
		"\u0000\u05e4\u05e2\u0001\u0000\u0000\u0000\u05e5\u05dd\u0001\u0000\u0000"+
		"\u0000\u05e5\u05e6\u0001\u0000\u0000\u0000\u05e6\u05e7\u0001\u0000\u0000"+
		"\u0000\u05e7\u05e8\u0005\u0004\u0000\u0000\u05e8\u05e9\u0005\u0007\u0000"+
		"\u0000\u05e9\u0714\u0003~?\u0000\u05ea\u05eb\u0005\u0003\u0000\u0000\u05eb"+
		"\u05ec\u0003\u0012\t\u0000\u05ec\u05ed\u0005\u0004\u0000\u0000\u05ed\u0714"+
		"\u0001\u0000\u0000\u0000\u05ee\u05ef\u0005M\u0000\u0000\u05ef\u05f0\u0005"+
		"\u0003\u0000\u0000\u05f0\u05f1\u0003\u0012\t\u0000\u05f1\u05f2\u0005\u0004"+
		"\u0000\u0000\u05f2\u0714\u0001\u0000\u0000\u0000\u05f3\u05f4\u0005\u0017"+
		"\u0000\u0000\u05f4\u05f6\u0003~?\u0000\u05f5\u05f7\u0003\u00b4Z\u0000"+
		"\u05f6\u05f5\u0001\u0000\u0000\u0000\u05f7\u05f8\u0001\u0000\u0000\u0000"+
		"\u05f8\u05f6\u0001\u0000\u0000\u0000\u05f8\u05f9\u0001\u0000\u0000\u0000"+
		"\u05f9\u05fc\u0001\u0000\u0000\u0000\u05fa\u05fb\u0005C\u0000\u0000\u05fb"+
		"\u05fd\u0003~?\u0000\u05fc\u05fa\u0001\u0000\u0000\u0000\u05fc\u05fd\u0001"+
		"\u0000\u0000\u0000\u05fd\u05fe\u0001\u0000\u0000\u0000\u05fe\u05ff\u0005"+
		"G\u0000\u0000\u05ff\u0714\u0001\u0000\u0000\u0000\u0600\u0602\u0005\u0017"+
		"\u0000\u0000\u0601\u0603\u0003\u00b4Z\u0000\u0602\u0601\u0001\u0000\u0000"+
		"\u0000\u0603\u0604\u0001\u0000\u0000\u0000\u0604\u0602\u0001\u0000\u0000"+
		"\u0000\u0604\u0605\u0001\u0000\u0000\u0000\u0605\u0608\u0001\u0000\u0000"+
		"\u0000\u0606\u0607\u0005C\u0000\u0000\u0607\u0609\u0003~?\u0000\u0608"+
		"\u0606\u0001\u0000\u0000\u0000\u0608\u0609\u0001\u0000\u0000\u0000\u0609"+
		"\u060a\u0001\u0000\u0000\u0000\u060a\u060b\u0005G\u0000\u0000\u060b\u0714"+
		"\u0001\u0000\u0000\u0000\u060c\u060d\u0005\u0018\u0000\u0000\u060d\u060e"+
		"\u0005\u0003\u0000\u0000\u060e\u060f\u0003~?\u0000\u060f\u0610\u0005\u000b"+
		"\u0000\u0000\u0610\u0611\u0003\u00aeW\u0000\u0611\u0612\u0005\u0004\u0000"+
		"\u0000\u0612\u0714\u0001\u0000\u0000\u0000\u0613\u0614\u0005\u0102\u0000"+
		"\u0000\u0614\u0615\u0005\u0003\u0000\u0000\u0615\u0616\u0003~?\u0000\u0616"+
		"\u0617\u0005\u000b\u0000\u0000\u0617\u0618\u0003\u00aeW\u0000\u0618\u0619"+
		"\u0005\u0004\u0000\u0000\u0619\u0714\u0001\u0000\u0000\u0000\u061a\u061b"+
		"\u0005\n\u0000\u0000\u061b\u0624\u0005\b\u0000\u0000\u061c\u0621\u0003"+
		"~?\u0000\u061d\u061e\u0005\u0002\u0000\u0000\u061e\u0620\u0003~?\u0000"+
		"\u061f\u061d\u0001\u0000\u0000\u0000\u0620\u0623\u0001\u0000\u0000\u0000"+
		"\u0621\u061f\u0001\u0000\u0000\u0000\u0621\u0622\u0001\u0000\u0000\u0000"+
		"\u0622\u0625\u0001\u0000\u0000\u0000\u0623\u0621\u0001\u0000\u0000\u0000"+
		"\u0624\u061c\u0001\u0000\u0000\u0000\u0624\u0625\u0001\u0000\u0000\u0000"+
		"\u0625\u0626\u0001\u0000\u0000\u0000\u0626\u0714\u0005\t\u0000\u0000\u0627"+
		"\u0714\u0003\u0100\u0080\u0000\u0628\u0714\u0005)\u0000\u0000\u0629\u062d"+
		"\u0005-\u0000\u0000\u062a\u062b\u0005\u0003\u0000\u0000\u062b\u062c\u0005"+
		"\u0138\u0000\u0000\u062c\u062e\u0005\u0004\u0000\u0000\u062d\u062a\u0001"+
		"\u0000\u0000\u0000\u062d\u062e\u0001\u0000\u0000\u0000\u062e\u0714\u0001"+
		"\u0000\u0000\u0000\u062f\u0633\u0005.\u0000\u0000\u0630\u0631\u0005\u0003"+
		"\u0000\u0000\u0631\u0632\u0005\u0138\u0000\u0000\u0632\u0634\u0005\u0004"+
		"\u0000\u0000\u0633\u0630\u0001\u0000\u0000\u0000\u0633\u0634\u0001\u0000"+
		"\u0000\u0000\u0634\u0714\u0001\u0000\u0000\u0000\u0635\u0639\u0005\u008d"+
		"\u0000\u0000\u0636\u0637\u0005\u0003\u0000\u0000\u0637\u0638\u0005\u0138"+
		"\u0000\u0000\u0638\u063a\u0005\u0004\u0000\u0000\u0639\u0636\u0001\u0000"+
		"\u0000\u0000\u0639\u063a\u0001\u0000\u0000\u0000\u063a\u0714\u0001\u0000"+
		"\u0000\u0000\u063b\u063f\u0005\u008e\u0000\u0000\u063c\u063d\u0005\u0003"+
		"\u0000\u0000\u063d\u063e\u0005\u0138\u0000\u0000\u063e\u0640\u0005\u0004"+
		"\u0000\u0000\u063f\u063c\u0001\u0000\u0000\u0000\u063f\u0640\u0001\u0000"+
		"\u0000\u0000\u0640\u0714\u0001\u0000\u0000\u0000\u0641\u0714\u0005/\u0000"+
		"\u0000\u0642\u0714\u0005(\u0000\u0000\u0643\u0714\u0005,\u0000\u0000\u0644"+
		"\u0714\u0005*\u0000\u0000\u0645\u0646\u0005\u00ff\u0000\u0000\u0646\u064e"+
		"\u0005\u0003\u0000\u0000\u0647\u0649\u0003L&\u0000\u0648\u0647\u0001\u0000"+
		"\u0000\u0000\u0648\u0649\u0001\u0000\u0000\u0000\u0649\u064b\u0001\u0000"+
		"\u0000\u0000\u064a\u064c\u0003\u0084B\u0000\u064b\u064a\u0001\u0000\u0000"+
		"\u0000\u064b\u064c\u0001\u0000\u0000\u0000\u064c\u064d\u0001\u0000\u0000"+
		"\u0000\u064d\u064f\u0005X\u0000\u0000\u064e\u0648\u0001\u0000\u0000\u0000"+
		"\u064e\u064f\u0001\u0000\u0000\u0000\u064f\u0650\u0001\u0000\u0000\u0000"+
		"\u0650\u0651\u0003\u0084B\u0000\u0651\u0652\u0005\u0004\u0000\u0000\u0652"+
		"\u0714\u0001\u0000\u0000\u0000\u0653\u0654\u0005\u00ff\u0000\u0000\u0654"+
		"\u0655\u0005\u0003\u0000\u0000\u0655\u0656\u0003\u0084B\u0000\u0656\u0657"+
		"\u0005\u0002\u0000\u0000\u0657\u0658\u0003\u0084B\u0000\u0658\u0659\u0005"+
		"\u0004\u0000\u0000\u0659\u0714\u0001\u0000\u0000\u0000\u065a\u065b\u0005"+
		"\u00f1\u0000\u0000\u065b\u065c\u0005\u0003\u0000\u0000\u065c\u065d\u0003"+
		"\u0084B\u0000\u065d\u065e\u0005X\u0000\u0000\u065e\u0661\u0003\u0084B"+
		"\u0000\u065f\u0660\u0005V\u0000\u0000\u0660\u0662\u0003\u0084B\u0000\u0661"+
		"\u065f\u0001\u0000\u0000\u0000\u0661\u0662\u0001\u0000\u0000\u0000\u0662"+
		"\u0663\u0001\u0000\u0000\u0000\u0663\u0664\u0005\u0004\u0000\u0000\u0664"+
		"\u0714\u0001\u0000\u0000\u0000\u0665\u0666\u0005\u00a4\u0000\u0000\u0666"+
		"\u0667\u0005\u0003\u0000\u0000\u0667\u066a\u0003\u0084B\u0000\u0668\u0669"+
		"\u0005\u0002\u0000\u0000\u0669\u066b\u0003\u00acV\u0000\u066a\u0668\u0001"+
		"\u0000\u0000\u0000\u066a\u066b\u0001\u0000\u0000\u0000\u066b\u066c\u0001"+
		"\u0000\u0000\u0000\u066c\u066d\u0005\u0004\u0000\u0000\u066d\u0714\u0001"+
		"\u0000\u0000\u0000\u066e\u066f\u0005O\u0000\u0000\u066f\u0670\u0005\u0003"+
		"\u0000\u0000\u0670\u0671\u0003\u0100\u0080\u0000\u0671\u0672\u0005X\u0000"+
		"\u0000\u0672\u0673\u0003\u0084B\u0000\u0673\u0674\u0005\u0004\u0000\u0000"+
		"\u0674\u0714\u0001\u0000\u0000\u0000\u0675\u0676\u0005\u0003\u0000\u0000"+
		"\u0676\u0677\u0003~?\u0000\u0677\u0678\u0005\u0004\u0000\u0000\u0678\u0714"+
		"\u0001\u0000\u0000\u0000\u0679\u067a\u0005b\u0000\u0000\u067a\u0683\u0005"+
		"\u0003\u0000\u0000\u067b\u0680\u0003\u00f2y\u0000\u067c\u067d\u0005\u0002"+
		"\u0000\u0000\u067d\u067f\u0003\u00f2y\u0000\u067e\u067c\u0001\u0000\u0000"+
		"\u0000\u067f\u0682\u0001\u0000\u0000\u0000\u0680\u067e\u0001\u0000\u0000"+
		"\u0000\u0680\u0681\u0001\u0000\u0000\u0000\u0681\u0684\u0001\u0000\u0000"+
		"\u0000\u0682\u0680\u0001\u0000\u0000\u0000\u0683\u067b\u0001\u0000\u0000"+
		"\u0000\u0683\u0684\u0001\u0000\u0000\u0000\u0684\u0685\u0001\u0000\u0000"+
		"\u0000\u0685\u0714\u0005\u0004\u0000\u0000\u0686\u0687\u0005z\u0000\u0000"+
		"\u0687\u0688\u0005\u0003\u0000\u0000\u0688\u068d\u0003\u0088D\u0000\u0689"+
		"\u068a\u0003\u0090H\u0000\u068a\u068b\u0005\u00ad\u0000\u0000\u068b\u068c"+
		"\u0005H\u0000\u0000\u068c\u068e\u0001\u0000\u0000\u0000\u068d\u0689\u0001"+
		"\u0000\u0000\u0000\u068d\u068e\u0001\u0000\u0000\u0000\u068e\u068f\u0001"+
		"\u0000\u0000\u0000\u068f\u0690\u0005\u0004\u0000\u0000\u0690\u0714\u0001"+
		"\u0000\u0000\u0000\u0691\u0692\u0005~\u0000\u0000\u0692\u0693\u0005\u0003"+
		"\u0000\u0000\u0693\u0696\u0003\u0088D\u0000\u0694\u0695\u0005\u00d6\u0000"+
		"\u0000\u0695\u0697\u0003\u00aeW\u0000\u0696\u0694\u0001\u0000\u0000\u0000"+
		"\u0696\u0697\u0001\u0000\u0000\u0000\u0697\u069c\u0001\u0000\u0000\u0000"+
		"\u0698\u0699\u0003\u0092I\u0000\u0699\u069a\u0005\u00ad\u0000\u0000\u069a"+
		"\u069b\u0005D\u0000\u0000\u069b\u069d\u0001\u0000\u0000\u0000\u069c\u0698"+
		"\u0001\u0000\u0000\u0000\u069c\u069d\u0001\u0000\u0000\u0000\u069d\u06a2"+
		"\u0001\u0000\u0000\u0000\u069e\u069f\u0003\u0092I\u0000\u069f\u06a0\u0005"+
		"\u00ad\u0000\u0000\u06a0\u06a1\u0005H\u0000\u0000\u06a1\u06a3\u0001\u0000"+
		"\u0000\u0000\u06a2\u069e\u0001\u0000\u0000\u0000\u06a2\u06a3\u0001\u0000"+
		"\u0000\u0000\u06a3\u06a4\u0001\u0000\u0000\u0000\u06a4\u06a5\u0005\u0004"+
		"\u0000\u0000\u06a5\u0714\u0001\u0000\u0000\u0000\u06a6\u06a7\u0005|\u0000"+
		"\u0000\u06a7\u06a8\u0005\u0003\u0000\u0000\u06a8\u06af\u0003\u0088D\u0000"+
		"\u06a9\u06aa\u0005\u00d6\u0000\u0000\u06aa\u06ad\u0003\u00aeW\u0000\u06ab"+
		"\u06ac\u0005W\u0000\u0000\u06ac\u06ae\u0003\u008cF\u0000\u06ad\u06ab\u0001"+
		"\u0000\u0000\u0000\u06ad\u06ae\u0001\u0000\u0000\u0000\u06ae\u06b0\u0001"+
		"\u0000\u0000\u0000\u06af\u06a9\u0001\u0000\u0000\u0000\u06af\u06b0\u0001"+
		"\u0000\u0000\u0000\u06b0\u06b4\u0001\u0000\u0000\u0000\u06b1\u06b2\u0003"+
		"\u0094J\u0000\u06b2\u06b3\u0005\u0123\u0000\u0000\u06b3\u06b5\u0001\u0000"+
		"\u0000\u0000\u06b4\u06b1\u0001\u0000\u0000\u0000\u06b4\u06b5\u0001\u0000"+
		"\u0000\u0000\u06b5\u06bd\u0001\u0000\u0000\u0000\u06b6\u06b7\u0007\f\u0000"+
		"\u0000\u06b7\u06bb\u0005\u00c9\u0000\u0000\u06b8\u06b9\u0005\u00ad\u0000"+
		"\u0000\u06b9\u06ba\u0005\u00e1\u0000\u0000\u06ba\u06bc\u0005\u00f7\u0000"+
		"\u0000\u06bb\u06b8\u0001\u0000\u0000\u0000\u06bb\u06bc\u0001\u0000\u0000"+
		"\u0000\u06bc\u06be\u0001\u0000\u0000\u0000\u06bd\u06b6\u0001\u0000\u0000"+
		"\u0000\u06bd\u06be\u0001\u0000\u0000\u0000\u06be\u06c3\u0001\u0000\u0000"+
		"\u0000\u06bf\u06c0\u0003\u0096K\u0000\u06c0\u06c1\u0005\u00ad\u0000\u0000"+
		"\u06c1\u06c2\u0005D\u0000\u0000\u06c2\u06c4\u0001\u0000\u0000\u0000\u06c3"+
		"\u06bf\u0001\u0000\u0000\u0000\u06c3\u06c4\u0001\u0000\u0000\u0000\u06c4"+
		"\u06c9\u0001\u0000\u0000\u0000\u06c5\u06c6\u0003\u0096K\u0000\u06c6\u06c7"+
		"\u0005\u00ad\u0000\u0000\u06c7\u06c8\u0005H\u0000\u0000\u06c8\u06ca\u0001"+
		"\u0000\u0000\u0000\u06c9\u06c5\u0001\u0000\u0000\u0000\u06c9\u06ca\u0001"+
		"\u0000\u0000\u0000\u06ca\u06cb\u0001\u0000\u0000\u0000\u06cb\u06cc\u0005"+
		"\u0004\u0000\u0000\u06cc\u0714\u0001\u0000\u0000\u0000\u06cd\u06ce\u0005"+
		"{\u0000\u0000\u06ce\u06eb\u0005\u0003\u0000\u0000\u06cf\u06d4\u0003\u0098"+
		"L\u0000\u06d0\u06d1\u0005\u0002\u0000\u0000\u06d1\u06d3\u0003\u0098L\u0000"+
		"\u06d2\u06d0\u0001\u0000\u0000\u0000\u06d3\u06d6\u0001\u0000\u0000\u0000"+
		"\u06d4\u06d2\u0001\u0000\u0000\u0000\u06d4\u06d5\u0001\u0000\u0000\u0000"+
		"\u06d5\u06dd\u0001\u0000\u0000\u0000\u06d6\u06d4\u0001\u0000\u0000\u0000"+
		"\u06d7\u06d8\u0005\u00a6\u0000\u0000\u06d8\u06d9\u0005\u00ad\u0000\u0000"+
		"\u06d9\u06de\u0005\u00a6\u0000\u0000\u06da\u06db\u0005\u0001\u0000\u0000"+
		"\u06db\u06dc\u0005\u00ad\u0000\u0000\u06dc\u06de\u0005\u00a6\u0000\u0000"+
		"\u06dd\u06d7\u0001\u0000\u0000\u0000\u06dd\u06da\u0001\u0000\u0000\u0000"+
		"\u06dd\u06de\u0001\u0000\u0000\u0000\u06de\u06e9\u0001\u0000\u0000\u0000"+
		"\u06df\u06e0\u0005\u011f\u0000\u0000\u06e0\u06e2\u0005\u0109\u0000\u0000"+
		"\u06e1\u06e3\u0005\u0081\u0000\u0000\u06e2\u06e1\u0001\u0000\u0000\u0000"+
		"\u06e2\u06e3\u0001\u0000\u0000\u0000\u06e3\u06ea\u0001\u0000\u0000\u0000"+
		"\u06e4\u06e5\u0005\u0121\u0000\u0000\u06e5\u06e7\u0005\u0109\u0000\u0000"+
		"\u06e6\u06e8\u0005\u0081\u0000\u0000\u06e7\u06e6\u0001\u0000\u0000\u0000"+
		"\u06e7\u06e8\u0001\u0000\u0000\u0000\u06e8\u06ea\u0001\u0000\u0000\u0000"+
		"\u06e9\u06df\u0001\u0000\u0000\u0000\u06e9\u06e4\u0001\u0000\u0000\u0000"+
		"\u06e9\u06ea\u0001\u0000\u0000\u0000\u06ea\u06ec\u0001\u0000\u0000\u0000"+
		"\u06eb\u06cf\u0001\u0000\u0000\u0000\u06eb\u06ec\u0001\u0000\u0000\u0000"+
		"\u06ec\u06f3\u0001\u0000\u0000\u0000\u06ed\u06ee\u0005\u00d6\u0000\u0000"+
		"\u06ee\u06f1\u0003\u00aeW\u0000\u06ef\u06f0\u0005W\u0000\u0000\u06f0\u06f2"+
		"\u0003\u008cF\u0000\u06f1\u06ef\u0001\u0000\u0000\u0000\u06f1\u06f2\u0001"+
		"\u0000\u0000\u0000\u06f2\u06f4\u0001\u0000\u0000\u0000\u06f3\u06ed\u0001"+
		"\u0000\u0000\u0000\u06f3\u06f4\u0001\u0000\u0000\u0000\u06f4\u06f5\u0001"+
		"\u0000\u0000\u0000\u06f5\u0714\u0005\u0004\u0000\u0000\u06f6\u06f7\u0005"+
		"y\u0000\u0000\u06f7\u0708\u0005\u0003\u0000\u0000\u06f8\u06fd\u0003\u008a"+
		"E\u0000\u06f9\u06fa\u0005\u0002\u0000\u0000\u06fa\u06fc\u0003\u008aE\u0000"+
		"\u06fb\u06f9\u0001\u0000\u0000\u0000\u06fc\u06ff\u0001\u0000\u0000\u0000"+
		"\u06fd\u06fb\u0001\u0000\u0000\u0000\u06fd\u06fe\u0001\u0000\u0000\u0000"+
		"\u06fe\u0706\u0001\u0000\u0000\u0000\u06ff\u06fd\u0001\u0000\u0000\u0000"+
		"\u0700\u0701\u0005\u00a6\u0000\u0000\u0701\u0702\u0005\u00ad\u0000\u0000"+
		"\u0702\u0707\u0005\u00a6\u0000\u0000\u0703\u0704\u0005\u0001\u0000\u0000"+
		"\u0704\u0705\u0005\u00ad\u0000\u0000\u0705\u0707\u0005\u00a6\u0000\u0000"+
		"\u0706\u0700\u0001\u0000\u0000\u0000\u0706\u0703\u0001\u0000\u0000\u0000"+
		"\u0706\u0707\u0001\u0000\u0000\u0000\u0707\u0709\u0001\u0000\u0000\u0000"+
		"\u0708\u06f8\u0001\u0000\u0000\u0000\u0708\u0709\u0001\u0000\u0000\u0000"+
		"\u0709\u0710\u0001\u0000\u0000\u0000\u070a\u070b\u0005\u00d6\u0000\u0000"+
		"\u070b\u070e\u0003\u00aeW\u0000\u070c\u070d\u0005W\u0000\u0000\u070d\u070f"+
		"\u0003\u008cF\u0000\u070e\u070c\u0001\u0000\u0000\u0000\u070e\u070f\u0001"+
		"\u0000\u0000\u0000\u070f\u0711\u0001\u0000\u0000\u0000\u0710\u070a\u0001"+
		"\u0000\u0000\u0000\u0710\u0711\u0001\u0000\u0000\u0000\u0711\u0712\u0001"+
		"\u0000\u0000\u0000\u0712\u0714\u0005\u0004\u0000\u0000\u0713\u054e\u0001"+
		"\u0000\u0000\u0000\u0713\u0550\u0001\u0000\u0000\u0000\u0713\u0551\u0001"+
		"\u0000\u0000\u0000\u0713\u0554\u0001\u0000\u0000\u0000\u0713\u0557\u0001"+
		"\u0000\u0000\u0000\u0713\u0558\u0001\u0000\u0000\u0000\u0713\u0559\u0001"+
		"\u0000\u0000\u0000\u0713\u055a\u0001\u0000\u0000\u0000\u0713\u055b\u0001"+
		"\u0000\u0000\u0000\u0713\u055c\u0001\u0000\u0000\u0000\u0713\u0563\u0001"+
		"\u0000\u0000\u0000\u0713\u056d\u0001\u0000\u0000\u0000\u0713\u0579\u0001"+
		"\u0000\u0000\u0000\u0713\u059c\u0001\u0000\u0000\u0000\u0713\u05ae\u0001"+
		"\u0000\u0000\u0000\u0713\u05d5\u0001\u0000\u0000\u0000\u0713\u05d8\u0001"+
		"\u0000\u0000\u0000\u0713\u05dc\u0001\u0000\u0000\u0000\u0713\u05ea\u0001"+
		"\u0000\u0000\u0000\u0713\u05ee\u0001\u0000\u0000\u0000\u0713\u05f3\u0001"+
		"\u0000\u0000\u0000\u0713\u0600\u0001\u0000\u0000\u0000\u0713\u060c\u0001"+
		"\u0000\u0000\u0000\u0713\u0613\u0001\u0000\u0000\u0000\u0713\u061a\u0001"+
		"\u0000\u0000\u0000\u0713\u0627\u0001\u0000\u0000\u0000\u0713\u0628\u0001"+
		"\u0000\u0000\u0000\u0713\u0629\u0001\u0000\u0000\u0000\u0713\u062f\u0001"+
		"\u0000\u0000\u0000\u0713\u0635\u0001\u0000\u0000\u0000\u0713\u063b\u0001"+
		"\u0000\u0000\u0000\u0713\u0641\u0001\u0000\u0000\u0000\u0713\u0642\u0001"+
		"\u0000\u0000\u0000\u0713\u0643\u0001\u0000\u0000\u0000\u0713\u0644\u0001"+
		"\u0000\u0000\u0000\u0713\u0645\u0001\u0000\u0000\u0000\u0713\u0653\u0001"+
		"\u0000\u0000\u0000\u0713\u065a\u0001\u0000\u0000\u0000\u0713\u0665\u0001"+
		"\u0000\u0000\u0000\u0713\u066e\u0001\u0000\u0000\u0000\u0713\u0675\u0001"+
		"\u0000\u0000\u0000\u0713\u0679\u0001\u0000\u0000\u0000\u0713\u0686\u0001"+
		"\u0000\u0000\u0000\u0713\u0691\u0001\u0000\u0000\u0000\u0713\u06a6\u0001"+
		"\u0000\u0000\u0000\u0713\u06cd\u0001\u0000\u0000\u0000\u0713\u06f6\u0001"+
		"\u0000\u0000\u0000\u0714\u071f\u0001\u0000\u0000\u0000\u0715\u0716\n\u0018"+
		"\u0000\u0000\u0716\u0717\u0005\b\u0000\u0000\u0717\u0718\u0003\u0084B"+
		"\u0000\u0718\u0719\u0005\t\u0000\u0000\u0719\u071e\u0001\u0000\u0000\u0000"+
		"\u071a\u071b\n\u0016\u0000\u0000\u071b\u071c\u0005\u0001\u0000\u0000\u071c"+
		"\u071e\u0003\u0100\u0080\u0000\u071d\u0715\u0001\u0000\u0000\u0000\u071d"+
		"\u071a\u0001\u0000\u0000\u0000\u071e\u0721\u0001\u0000\u0000\u0000\u071f"+
		"\u071d\u0001\u0000\u0000\u0000\u071f\u0720\u0001\u0000\u0000\u0000\u0720"+
		"\u0087\u0001\u0000\u0000\u0000\u0721\u071f\u0001\u0000\u0000\u0000\u0722"+
		"\u0723\u0003\u008aE\u0000\u0723\u0724\u0005\u0002\u0000\u0000\u0724\u0727"+
		"\u0003\u009eO\u0000\u0725\u0726\u0005\u000b\u0000\u0000\u0726\u0728\u0003"+
		"\u0100\u0080\u0000\u0727\u0725\u0001\u0000\u0000\u0000\u0727\u0728\u0001"+
		"\u0000\u0000\u0000\u0728\u0732\u0001\u0000\u0000\u0000\u0729\u072a\u0005"+
		"\u00ba\u0000\u0000\u072a\u072f\u0003\u008eG\u0000\u072b\u072c\u0005\u0002"+
		"\u0000\u0000\u072c\u072e\u0003\u008eG\u0000\u072d\u072b\u0001\u0000\u0000"+
		"\u0000\u072e\u0731\u0001\u0000\u0000\u0000\u072f\u072d\u0001\u0000\u0000"+
		"\u0000\u072f\u0730\u0001\u0000\u0000\u0000\u0730\u0733\u0001\u0000\u0000"+
		"\u0000\u0731\u072f\u0001\u0000\u0000\u0000\u0732\u0729\u0001\u0000\u0000"+
		"\u0000\u0732\u0733\u0001\u0000\u0000\u0000\u0733\u0089\u0001\u0000\u0000"+
		"\u0000\u0734\u0737\u0003~?\u0000\u0735\u0736\u0005W\u0000\u0000\u0736"+
		"\u0738\u0003\u008cF\u0000\u0737\u0735\u0001\u0000\u0000\u0000\u0737\u0738"+
		"\u0001\u0000\u0000\u0000\u0738\u008b\u0001\u0000\u0000\u0000\u0739\u073c"+
		"\u0005x\u0000\u0000\u073a\u073b\u0005F\u0000\u0000\u073b\u073d\u0007\u0011"+
		"\u0000\u0000\u073c\u073a\u0001\u0000\u0000\u0000\u073c\u073d\u0001\u0000"+
		"\u0000\u0000\u073d\u008d\u0001\u0000\u0000\u0000\u073e\u073f\u0003\u008a"+
		"E\u0000\u073f\u0740\u0005\u000b\u0000\u0000\u0740\u0741\u0003\u0100\u0080"+
		"\u0000\u0741\u008f\u0001\u0000\u0000\u0000\u0742\u0743\u0007\u0012\u0000"+
		"\u0000\u0743\u0091\u0001\u0000\u0000\u0000\u0744\u0749\u0005H\u0000\u0000"+
		"\u0745\u0749\u0005\u00a6\u0000\u0000\u0746\u0747\u00055\u0000\u0000\u0747"+
		"\u0749\u0003~?\u0000\u0748\u0744\u0001\u0000\u0000\u0000\u0748\u0745\u0001"+
		"\u0000\u0000\u0000\u0748\u0746\u0001\u0000\u0000\u0000\u0749\u0093\u0001"+
		"\u0000\u0000\u0000\u074a\u074c\u0005\u0121\u0000\u0000\u074b\u074d\u0005"+
		"\n\u0000\u0000\u074c\u074b\u0001\u0000\u0000\u0000\u074c\u074d\u0001\u0000"+
		"\u0000\u0000\u074d\u0756\u0001\u0000\u0000\u0000\u074e\u0750\u0005\u011f"+
		"\u0000\u0000\u074f\u0751\u0007\u0013\u0000\u0000\u0750\u074f\u0001\u0000"+
		"\u0000\u0000\u0750\u0751\u0001\u0000\u0000\u0000\u0751\u0753\u0001\u0000"+
		"\u0000\u0000\u0752\u0754\u0005\n\u0000\u0000\u0753\u0752\u0001\u0000\u0000"+
		"\u0000\u0753\u0754\u0001\u0000\u0000\u0000\u0754\u0756\u0001\u0000\u0000"+
		"\u0000\u0755\u074a\u0001\u0000\u0000\u0000\u0755\u074e\u0001\u0000\u0000"+
		"\u0000\u0756\u0095\u0001\u0000\u0000\u0000\u0757\u075e\u0005H\u0000\u0000"+
		"\u0758\u075e\u0005\u00a6\u0000\u0000\u0759\u075a\u0005D\u0000\u0000\u075a"+
		"\u075e\u0005\n\u0000\u0000\u075b\u075c\u0005D\u0000\u0000\u075c\u075e"+
		"\u0005\u00a9\u0000\u0000\u075d\u0757\u0001\u0000\u0000\u0000\u075d\u0758"+
		"\u0001\u0000\u0000\u0000\u075d\u0759\u0001\u0000\u0000\u0000\u075d\u075b"+
		"\u0001\u0000\u0000\u0000\u075e\u0097\u0001\u0000\u0000\u0000\u075f\u0761"+
		"\u0005\u0080\u0000\u0000\u0760\u075f\u0001\u0000\u0000\u0000\u0760\u0761"+
		"\u0001\u0000\u0000\u0000\u0761\u0762\u0001\u0000\u0000\u0000\u0762\u0763"+
		"\u0003~?\u0000\u0763\u0764\u0005\u0116\u0000\u0000\u0764\u0765\u0003\u008a"+
		"E\u0000\u0765\u076b\u0001\u0000\u0000\u0000\u0766\u0767\u0003~?\u0000"+
		"\u0767\u0768\u0005\n\u0000\u0000\u0768\u0769\u0003\u008aE\u0000\u0769"+
		"\u076b\u0001\u0000\u0000\u0000\u076a\u0760\u0001\u0000\u0000\u0000\u076a"+
		"\u0766\u0001\u0000\u0000\u0000\u076b\u0099\u0001\u0000\u0000\u0000\u076c"+
		"\u076d\u0007\u0014\u0000\u0000\u076d\u009b\u0001\u0000\u0000\u0000\u076e"+
		"\u076f\u0005g\u0000\u0000\u076f\u0773\u0005\u00a8\u0000\u0000\u0770\u0771"+
		"\u0005\u00d3\u0000\u0000\u0771\u0773\u0005\u00a8\u0000\u0000\u0772\u076e"+
		"\u0001\u0000\u0000\u0000\u0772\u0770\u0001\u0000\u0000\u0000\u0773\u009d"+
		"\u0001\u0000\u0000\u0000\u0774\u077b\u0005\u0135\u0000\u0000\u0775\u0778"+
		"\u0005\u0136\u0000\u0000\u0776\u0777\u0005\u0104\u0000\u0000\u0777\u0779"+
		"\u0005\u0135\u0000\u0000\u0778\u0776\u0001\u0000\u0000\u0000\u0778\u0779"+
		"\u0001\u0000\u0000\u0000\u0779\u077b\u0001\u0000\u0000\u0000\u077a\u0774"+
		"\u0001\u0000\u0000\u0000\u077a\u0775\u0001\u0000\u0000\u0000\u077b\u009f"+
		"\u0001\u0000\u0000\u0000\u077c\u077d\u0005\u00fa\u0000\u0000\u077d\u077e"+
		"\u0005\u0126\u0000\u0000\u077e\u0783\u0003\u00a8T\u0000\u077f\u0780\u0005"+
		"\u00fa\u0000\u0000\u0780\u0781\u0005\u0126\u0000\u0000\u0781\u0783\u0003"+
		"\u009eO\u0000\u0782\u077c\u0001\u0000\u0000\u0000\u0782\u077f\u0001\u0000"+
		"\u0000\u0000\u0783\u00a1\u0001\u0000\u0000\u0000\u0784\u0785\u0007\u0015"+
		"\u0000\u0000\u0785\u00a3\u0001\u0000\u0000\u0000\u0786\u0787\u0007\u0016"+
		"\u0000\u0000\u0787\u00a5\u0001\u0000\u0000\u0000\u0788\u0789\u0007\u0017"+
		"\u0000\u0000\u0789\u00a7\u0001\u0000\u0000\u0000\u078a\u078c\u0005p\u0000"+
		"\u0000\u078b\u078d\u0007\u000f\u0000\u0000\u078c\u078b\u0001\u0000\u0000"+
		"\u0000\u078c\u078d\u0001\u0000\u0000\u0000\u078d\u078e\u0001\u0000\u0000"+
		"\u0000\u078e\u078f\u0003\u009eO\u0000\u078f\u0792\u0003\u00aaU\u0000\u0790"+
		"\u0791\u0005\u00fc\u0000\u0000\u0791\u0793\u0003\u00aaU\u0000\u0792\u0790"+
		"\u0001\u0000\u0000\u0000\u0792\u0793\u0001\u0000\u0000\u0000\u0793\u00a9"+
		"\u0001\u0000\u0000\u0000\u0794\u0795\u0007\u0018\u0000\u0000\u0795\u00ab"+
		"\u0001\u0000\u0000\u0000\u0796\u0797\u0007\u0019\u0000\u0000\u0797\u00ad"+
		"\u0001\u0000\u0000\u0000\u0798\u0799\u0006W\uffff\uffff\u0000\u0799\u079a"+
		"\u0005\u00de\u0000\u0000\u079a\u079b\u0005\u0003\u0000\u0000\u079b\u07a0"+
		"\u0003\u00b0X\u0000\u079c\u079d\u0005\u0002\u0000\u0000\u079d\u079f\u0003"+
		"\u00b0X\u0000\u079e\u079c\u0001\u0000\u0000\u0000\u079f\u07a2\u0001\u0000"+
		"\u0000\u0000\u07a0\u079e\u0001\u0000\u0000\u0000\u07a0\u07a1\u0001\u0000"+
		"\u0000\u0000\u07a1\u07a3\u0001\u0000\u0000\u0000\u07a2\u07a0\u0001\u0000"+
		"\u0000\u0000\u07a3\u07a4\u0005\u0004\u0000\u0000\u07a4\u07f4\u0001\u0000"+
		"\u0000\u0000\u07a5\u07a6\u0005p\u0000\u0000\u07a6\u07a9\u0003\u00aaU\u0000"+
		"\u07a7\u07a8\u0005\u00fc\u0000\u0000\u07a8\u07aa\u0003\u00aaU\u0000\u07a9"+
		"\u07a7\u0001\u0000\u0000\u0000\u07a9\u07aa\u0001\u0000\u0000\u0000\u07aa"+
		"\u07f4\u0001\u0000\u0000\u0000\u07ab\u07b0\u0005\u00fb\u0000\u0000\u07ac"+
		"\u07ad\u0005\u0003\u0000\u0000\u07ad\u07ae\u0003\u00b2Y\u0000\u07ae\u07af"+
		"\u0005\u0004\u0000\u0000\u07af\u07b1\u0001\u0000\u0000\u0000\u07b0\u07ac"+
		"\u0001\u0000\u0000\u0000\u07b0\u07b1\u0001\u0000\u0000\u0000\u07b1\u07b5"+
		"\u0001\u0000\u0000\u0000\u07b2\u07b3\u0005\u0121\u0000\u0000\u07b3\u07b4"+
		"\u0005\u00fa\u0000\u0000\u07b4\u07b6\u0005\u0126\u0000\u0000\u07b5\u07b2"+
		"\u0001\u0000\u0000\u0000\u07b5\u07b6\u0001\u0000\u0000\u0000\u07b6\u07f4"+
		"\u0001\u0000\u0000\u0000\u07b7\u07bc\u0005\u00fb\u0000\u0000\u07b8\u07b9"+
		"\u0005\u0003\u0000\u0000\u07b9\u07ba\u0003\u00b2Y\u0000\u07ba\u07bb\u0005"+
		"\u0004\u0000\u0000\u07bb\u07bd\u0001\u0000\u0000\u0000\u07bc\u07b8\u0001"+
		"\u0000\u0000\u0000\u07bc\u07bd\u0001\u0000\u0000\u0000\u07bd\u07be\u0001"+
		"\u0000\u0000\u0000\u07be\u07bf\u0005\u011f\u0000\u0000\u07bf\u07c0\u0005"+
		"\u00fa\u0000\u0000\u07c0\u07f4\u0005\u0126\u0000\u0000\u07c1\u07c6\u0005"+
		"\u00fa\u0000\u0000\u07c2\u07c3\u0005\u0003\u0000\u0000\u07c3\u07c4\u0003"+
		"\u00b2Y\u0000\u07c4\u07c5\u0005\u0004\u0000\u0000\u07c5\u07c7\u0001\u0000"+
		"\u0000\u0000\u07c6\u07c2\u0001\u0000\u0000\u0000\u07c6\u07c7\u0001\u0000"+
		"\u0000\u0000\u07c7\u07cb\u0001\u0000\u0000\u0000\u07c8\u07c9\u0005\u0121"+
		"\u0000\u0000\u07c9\u07ca\u0005\u00fa\u0000\u0000\u07ca\u07cc\u0005\u0126"+
		"\u0000\u0000\u07cb\u07c8\u0001\u0000\u0000\u0000\u07cb\u07cc\u0001\u0000"+
		"\u0000\u0000\u07cc\u07f4\u0001\u0000\u0000\u0000\u07cd\u07d2\u0005\u00fa"+
		"\u0000\u0000\u07ce\u07cf\u0005\u0003\u0000\u0000\u07cf\u07d0\u0003\u00b2"+
		"Y\u0000\u07d0\u07d1\u0005\u0004\u0000\u0000\u07d1\u07d3\u0001\u0000\u0000"+
		"\u0000\u07d2\u07ce\u0001\u0000\u0000\u0000\u07d2\u07d3\u0001\u0000\u0000"+
		"\u0000\u07d3\u07d4\u0001\u0000\u0000\u0000\u07d4\u07d5\u0005\u011f\u0000"+
		"\u0000\u07d5\u07d6\u0005\u00fa\u0000\u0000\u07d6\u07f4\u0005\u0126\u0000"+
		"\u0000\u07d7\u07d8\u0005A\u0000\u0000\u07d8\u07f4\u0005\u00c4\u0000\u0000"+
		"\u07d9\u07da\u0005\n\u0000\u0000\u07da\u07db\u0005\u000b\u0000\u0000\u07db"+
		"\u07dc\u0003\u00aeW\u0000\u07dc\u07dd\u0005\f\u0000\u0000\u07dd\u07f4"+
		"\u0001\u0000\u0000\u0000\u07de\u07df\u0005\u0091\u0000\u0000\u07df\u07e0"+
		"\u0005\u000b\u0000\u0000\u07e0\u07e1\u0003\u00aeW\u0000\u07e1\u07e2\u0005"+
		"\u0002\u0000\u0000\u07e2\u07e3\u0003\u00aeW\u0000\u07e3\u07e4\u0005\f"+
		"\u0000\u0000\u07e4\u07f4\u0001\u0000\u0000\u0000\u07e5\u07f1\u0003\u0100"+
		"\u0080\u0000\u07e6\u07e7\u0005\u0003\u0000\u0000\u07e7\u07ec\u0003\u00b2"+
		"Y\u0000\u07e8\u07e9\u0005\u0002\u0000\u0000\u07e9\u07eb\u0003\u00b2Y\u0000"+
		"\u07ea\u07e8\u0001\u0000\u0000\u0000\u07eb\u07ee\u0001\u0000\u0000\u0000"+
		"\u07ec\u07ea\u0001\u0000\u0000\u0000\u07ec\u07ed\u0001\u0000\u0000\u0000"+
		"\u07ed\u07ef\u0001\u0000\u0000\u0000\u07ee\u07ec\u0001\u0000\u0000\u0000"+
		"\u07ef\u07f0\u0005\u0004\u0000\u0000\u07f0\u07f2\u0001\u0000\u0000\u0000"+
		"\u07f1\u07e6\u0001\u0000\u0000\u0000\u07f1\u07f2\u0001\u0000\u0000\u0000"+
		"\u07f2\u07f4\u0001\u0000\u0000\u0000\u07f3\u0798\u0001\u0000\u0000\u0000"+
		"\u07f3\u07a5\u0001\u0000\u0000\u0000\u07f3\u07ab\u0001\u0000\u0000\u0000"+
		"\u07f3\u07b7\u0001\u0000\u0000\u0000\u07f3\u07c1\u0001\u0000\u0000\u0000"+
		"\u07f3\u07cd\u0001\u0000\u0000\u0000\u07f3\u07d7\u0001\u0000\u0000\u0000"+
		"\u07f3\u07d9\u0001\u0000\u0000\u0000\u07f3\u07de\u0001\u0000\u0000\u0000"+
		"\u07f3\u07e5\u0001\u0000\u0000\u0000\u07f4\u07fe\u0001\u0000\u0000\u0000"+
		"\u07f5\u07f6\n\u0002\u0000\u0000\u07f6\u07fa\u0005\n\u0000\u0000\u07f7"+
		"\u07f8\u0005\b\u0000\u0000\u07f8\u07f9\u0005\u0138\u0000\u0000\u07f9\u07fb"+
		"\u0005\t\u0000\u0000\u07fa\u07f7\u0001\u0000\u0000\u0000\u07fa\u07fb\u0001"+
		"\u0000\u0000\u0000\u07fb\u07fd\u0001\u0000\u0000\u0000\u07fc\u07f5\u0001"+
		"\u0000\u0000\u0000\u07fd\u0800\u0001\u0000\u0000\u0000\u07fe\u07fc\u0001"+
		"\u0000\u0000\u0000\u07fe\u07ff\u0001\u0000\u0000\u0000\u07ff\u00af\u0001"+
		"\u0000\u0000\u0000\u0800\u07fe\u0001\u0000\u0000\u0000\u0801\u0806\u0003"+
		"\u00aeW\u0000\u0802\u0803\u0003\u0100\u0080\u0000\u0803\u0804\u0003\u00ae"+
		"W\u0000\u0804\u0806\u0001\u0000\u0000\u0000\u0805\u0801\u0001\u0000\u0000"+
		"\u0000\u0805\u0802\u0001\u0000\u0000\u0000\u0806\u00b1\u0001\u0000\u0000"+
		"\u0000\u0807\u080a\u0005\u0138\u0000\u0000\u0808\u080a\u0003\u00aeW\u0000"+
		"\u0809\u0807\u0001\u0000\u0000\u0000\u0809\u0808\u0001\u0000\u0000\u0000"+
		"\u080a\u00b3\u0001\u0000\u0000\u0000\u080b\u080c\u0005\u011b\u0000\u0000"+
		"\u080c\u080d\u0003~?\u0000\u080d\u080e\u0005\u00f8\u0000\u0000\u080e\u080f"+
		"\u0003~?\u0000\u080f\u00b5\u0001\u0000\u0000\u0000\u0810\u0811\u0005R"+
		"\u0000\u0000\u0811\u0812\u0005\u0003\u0000\u0000\u0812\u0813\u0005\u011c"+
		"\u0000\u0000\u0813\u0814\u0003\u0080@\u0000\u0814\u0815\u0005\u0004\u0000"+
		"\u0000\u0815\u00b7\u0001\u0000\u0000\u0000\u0816\u0817\u0005\u011b\u0000"+
		"\u0000\u0817\u081a\u0005\u0093\u0000\u0000\u0818\u0819\u0005\b\u0000\u0000"+
		"\u0819\u081b\u0003~?\u0000\u081a\u0818\u0001\u0000\u0000\u0000\u081a\u081b"+
		"\u0001\u0000\u0000\u0000\u081b\u081c\u0001\u0000\u0000\u0000\u081c\u081d"+
		"\u0005\u00f8\u0000\u0000\u081d\u081e\u0005\u010e\u0000\u0000\u081e\u081f"+
		"\u0005\u00ea\u0000\u0000\u081f\u0820\u0003\u0100\u0080\u0000\u0820\u0821"+
		"\u0005\u0127\u0000\u0000\u0821\u0829\u0003~?\u0000\u0822\u0823\u0005\u0002"+
		"\u0000\u0000\u0823\u0824\u0003\u0100\u0080\u0000\u0824\u0825\u0005\u0127"+
		"\u0000\u0000\u0825\u0826\u0003~?\u0000\u0826\u0828\u0001\u0000\u0000\u0000"+
		"\u0827\u0822\u0001\u0000\u0000\u0000\u0828\u082b\u0001\u0000\u0000\u0000"+
		"\u0829\u0827\u0001\u0000\u0000\u0000\u0829\u082a\u0001\u0000\u0000\u0000"+
		"\u082a\u0857\u0001\u0000\u0000\u0000\u082b\u0829\u0001\u0000\u0000\u0000"+
		"\u082c\u082d\u0005\u011b\u0000\u0000\u082d\u0830\u0005\u0093\u0000\u0000"+
		"\u082e\u082f\u0005\b\u0000\u0000\u082f\u0831\u0003~?\u0000\u0830\u082e"+
		"\u0001\u0000\u0000\u0000\u0830\u0831\u0001\u0000\u0000\u0000\u0831\u0832"+
		"\u0001\u0000\u0000\u0000\u0832\u0833\u0005\u00f8\u0000\u0000\u0833\u0857"+
		"\u00058\u0000\u0000\u0834\u0835\u0005\u011b\u0000\u0000\u0835\u0836\u0005"+
		"\u00a5\u0000\u0000\u0836\u0839\u0005\u0093\u0000\u0000\u0837\u0838\u0005"+
		"\b\u0000\u0000\u0838\u083a\u0003~?\u0000\u0839\u0837\u0001\u0000\u0000"+
		"\u0000\u0839\u083a\u0001\u0000\u0000\u0000\u083a\u083b\u0001\u0000\u0000"+
		"\u0000\u083b\u083c\u0005\u00f8\u0000\u0000\u083c\u0848\u0005n\u0000\u0000"+
		"\u083d\u083e\u0005\u0003\u0000\u0000\u083e\u0843\u0003\u0100\u0080\u0000"+
		"\u083f\u0840\u0005\u0002\u0000\u0000\u0840\u0842\u0003\u0100\u0080\u0000"+
		"\u0841\u083f\u0001\u0000\u0000\u0000\u0842\u0845\u0001\u0000\u0000\u0000"+
		"\u0843\u0841\u0001\u0000\u0000\u0000\u0843\u0844\u0001\u0000\u0000\u0000"+
		"\u0844\u0846\u0001\u0000\u0000\u0000\u0845\u0843\u0001\u0000\u0000\u0000"+
		"\u0846\u0847\u0005\u0004\u0000\u0000\u0847\u0849\u0001\u0000\u0000\u0000"+
		"\u0848\u083d\u0001\u0000\u0000\u0000\u0848\u0849\u0001\u0000\u0000\u0000"+
		"\u0849\u084a\u0001\u0000\u0000\u0000\u084a\u084b\u0005\u0117\u0000\u0000"+
		"\u084b\u084c\u0005\u0003\u0000\u0000\u084c\u0851\u0003~?\u0000\u084d\u084e"+
		"\u0005\u0002\u0000\u0000\u084e\u0850\u0003~?\u0000\u084f\u084d\u0001\u0000"+
		"\u0000\u0000\u0850\u0853\u0001\u0000\u0000\u0000\u0851\u084f\u0001\u0000"+
		"\u0000\u0000\u0851\u0852\u0001\u0000\u0000\u0000\u0852\u0854\u0001\u0000"+
		"\u0000\u0000\u0853\u0851\u0001\u0000\u0000\u0000\u0854\u0855\u0005\u0004"+
		"\u0000\u0000\u0855\u0857\u0001\u0000\u0000\u0000\u0856\u0816\u0001\u0000"+
		"\u0000\u0000\u0856\u082c\u0001\u0000\u0000\u0000\u0856\u0834\u0001\u0000"+
		"\u0000\u0000\u0857\u00b9\u0001\u0000\u0000\u0000\u0858\u085e\u0005\u00b6"+
		"\u0000\u0000\u0859\u085f\u0003\u0100\u0080\u0000\u085a\u085b\u0005\u0003"+
		"\u0000\u0000\u085b\u085c\u0003:\u001d\u0000\u085c\u085d\u0005\u0004\u0000"+
		"\u0000\u085d\u085f\u0001\u0000\u0000\u0000\u085e\u0859\u0001\u0000\u0000"+
		"\u0000\u085e\u085a\u0001\u0000\u0000\u0000\u085f\u00bb\u0001\u0000\u0000"+
		"\u0000\u0860\u0861\u0005\u0097\u0000\u0000\u0861\u0866\u0003T*\u0000\u0862"+
		"\u0863\u0005\u0002\u0000\u0000\u0863\u0865\u0003T*\u0000\u0864\u0862\u0001"+
		"\u0000\u0000\u0000\u0865\u0868\u0001\u0000\u0000\u0000\u0866\u0864\u0001"+
		"\u0000\u0000\u0000\u0866\u0867\u0001\u0000\u0000\u0000\u0867\u086a\u0001"+
		"\u0000\u0000\u0000\u0868\u0866\u0001\u0000\u0000\u0000\u0869\u0860\u0001"+
		"\u0000\u0000\u0000\u0869\u086a\u0001\u0000\u0000\u0000\u086a\u086b\u0001"+
		"\u0000\u0000\u0000\u086b\u086f\u0003\u00be_\u0000\u086c\u086d\u0005\u0004"+
		"\u0000\u0000\u086d\u086e\u0005\u0092\u0000\u0000\u086e\u0870\u0003Z-\u0000"+
		"\u086f\u086c\u0001\u0000\u0000\u0000\u086f\u0870\u0001\u0000\u0000\u0000"+
		"\u0870\u0872\u0001\u0000\u0000\u0000\u0871\u0873\u0007\n\u0000\u0000\u0872"+
		"\u0871\u0001\u0000\u0000\u0000\u0872\u0873\u0001\u0000\u0000\u0000\u0873"+
		"\u0879\u0001\u0000\u0000\u0000\u0874\u0875\u0005\u00bd\u0000\u0000\u0875"+
		"\u0876\u0005\u0003\u0000\u0000\u0876\u0877\u0003\u00c2a\u0000\u0877\u0878"+
		"\u0005\u0004\u0000\u0000\u0878\u087a\u0001\u0000\u0000\u0000\u0879\u0874"+
		"\u0001\u0000\u0000\u0000\u0879\u087a\u0001\u0000\u0000\u0000\u087a\u0884"+
		"\u0001\u0000\u0000\u0000\u087b\u087c\u0005\u00f0\u0000\u0000\u087c\u0881"+
		"\u0003\\.\u0000\u087d\u087e\u0005\u0002\u0000\u0000\u087e\u0880\u0003"+
		"\\.\u0000\u087f\u087d\u0001\u0000\u0000\u0000\u0880\u0883\u0001\u0000"+
		"\u0000\u0000\u0881\u087f\u0001\u0000\u0000\u0000\u0881\u0882\u0001\u0000"+
		"\u0000\u0000\u0882\u0885\u0001\u0000\u0000\u0000\u0883\u0881\u0001\u0000"+
		"\u0000\u0000\u0884\u087b\u0001\u0000\u0000\u0000\u0884\u0885\u0001\u0000"+
		"\u0000\u0000\u0885\u088f\u0001\u0000\u0000\u0000\u0886\u0887\u00056\u0000"+
		"\u0000\u0887\u088c\u0003^/\u0000\u0888\u0889\u0005\u0002\u0000\u0000\u0889"+
		"\u088b\u0003^/\u0000\u088a\u0888\u0001\u0000\u0000\u0000\u088b\u088e\u0001"+
		"\u0000\u0000\u0000\u088c\u088a\u0001\u0000\u0000\u0000\u088c\u088d\u0001"+
		"\u0000\u0000\u0000\u088d\u0890\u0001\u0000\u0000\u0000\u088e\u088c\u0001"+
		"\u0000\u0000\u0000\u088f\u0886\u0001\u0000\u0000\u0000\u088f\u0890\u0001"+
		"\u0000\u0000\u0000\u0890\u00bd\u0001\u0000\u0000\u0000\u0891\u0892\u0005"+
		"\u00ca\u0000\u0000\u0892\u08aa\u0003\u00c0`\u0000\u0893\u0894\u0005\u00df"+
		"\u0000\u0000\u0894\u08aa\u0003\u00c0`\u0000\u0895\u0896\u0005c\u0000\u0000"+
		"\u0896\u08aa\u0003\u00c0`\u0000\u0897\u0898\u0005\u00ca\u0000\u0000\u0898"+
		"\u0899\u0005\u0011\u0000\u0000\u0899\u089a\u0003\u00c0`\u0000\u089a\u089b"+
		"\u0005\b\u0000\u0000\u089b\u089c\u0003\u00c0`\u0000\u089c\u08aa\u0001"+
		"\u0000\u0000\u0000\u089d\u089e\u0005\u00df\u0000\u0000\u089e\u089f\u0005"+
		"\u0011\u0000\u0000\u089f\u08a0\u0003\u00c0`\u0000\u08a0\u08a1\u0005\b"+
		"\u0000\u0000\u08a1\u08a2\u0003\u00c0`\u0000\u08a2\u08aa\u0001\u0000\u0000"+
		"\u0000\u08a3\u08a4\u0005c\u0000\u0000\u08a4\u08a5\u0005\u0011\u0000\u0000"+
		"\u08a5\u08a6\u0003\u00c0`\u0000\u08a6\u08a7\u0005\b\u0000\u0000\u08a7"+
		"\u08a8\u0003\u00c0`\u0000\u08a8\u08aa\u0001\u0000\u0000\u0000\u08a9\u0891"+
		"\u0001\u0000\u0000\u0000\u08a9\u0893\u0001\u0000\u0000\u0000\u08a9\u0895"+
		"\u0001\u0000\u0000\u0000\u08a9\u0897\u0001\u0000\u0000\u0000\u08a9\u089d"+
		"\u0001\u0000\u0000\u0000\u08a9\u08a3\u0001\u0000\u0000\u0000\u08aa\u00bf"+
		"\u0001\u0000\u0000\u0000\u08ab\u08ac\u0005\u0105\u0000\u0000\u08ac\u08b5"+
		"\u0005\u00c3\u0000\u0000\u08ad\u08ae\u0005\u0105\u0000\u0000\u08ae\u08b5"+
		"\u0005U\u0000\u0000\u08af\u08b0\u0005\'\u0000\u0000\u08b0\u08b5\u0005"+
		"\u00de\u0000\u0000\u08b1\u08b2\u0003~?\u0000\u08b2\u08b3\u0007\u001a\u0000"+
		"\u0000\u08b3\u08b5\u0001\u0000\u0000\u0000\u08b4\u08ab\u0001\u0000\u0000"+
		"\u0000\u08b4\u08ad\u0001\u0000\u0000\u0000\u08b4\u08af\u0001\u0000\u0000"+
		"\u0000\u08b4\u08b1\u0001\u0000\u0000\u0000\u08b5\u00c1\u0001\u0000\u0000"+
		"\u0000\u08b6\u08b7\u0006a\uffff\uffff\u0000\u08b7\u08b9\u0003\u00c4b\u0000"+
		"\u08b8\u08ba\u0003\u00c6c\u0000\u08b9\u08b8\u0001\u0000\u0000\u0000\u08b9"+
		"\u08ba\u0001\u0000\u0000\u0000\u08ba\u08c2\u0001\u0000\u0000\u0000\u08bb"+
		"\u08bc\n\u0002\u0000\u0000\u08bc\u08c1\u0003\u00c2a\u0003\u08bd\u08be"+
		"\n\u0001\u0000\u0000\u08be\u08bf\u0005\r\u0000\u0000\u08bf\u08c1\u0003"+
		"\u00c2a\u0002\u08c0\u08bb\u0001\u0000\u0000\u0000\u08c0\u08bd\u0001\u0000"+
		"\u0000\u0000\u08c1\u08c4\u0001\u0000\u0000\u0000\u08c2\u08c0\u0001\u0000"+
		"\u0000\u0000\u08c2\u08c3\u0001\u0000\u0000\u0000\u08c3\u00c3\u0001\u0000"+
		"\u0000\u0000\u08c4\u08c2\u0001\u0000\u0000\u0000\u08c5\u08df\u0003\u0100"+
		"\u0080\u0000\u08c6\u08c7\u0005\u0003\u0000\u0000\u08c7\u08df\u0005\u0004"+
		"\u0000\u0000\u08c8\u08c9\u0005\u00c0\u0000\u0000\u08c9\u08ca\u0005\u0003"+
		"\u0000\u0000\u08ca\u08cf\u0003\u00c2a\u0000\u08cb\u08cc\u0005\u0002\u0000"+
		"\u0000\u08cc\u08ce\u0003\u00c2a\u0000\u08cd\u08cb\u0001\u0000\u0000\u0000"+
		"\u08ce\u08d1\u0001\u0000\u0000\u0000\u08cf\u08cd\u0001\u0000\u0000\u0000"+
		"\u08cf\u08d0\u0001\u0000\u0000\u0000\u08d0\u08d2\u0001\u0000\u0000\u0000"+
		"\u08d1\u08cf\u0001\u0000\u0000\u0000\u08d2\u08d3\u0005\u0004\u0000\u0000"+
		"\u08d3\u08df\u0001\u0000\u0000\u0000\u08d4\u08d5\u0005\u0003\u0000\u0000"+
		"\u08d5\u08d6\u0003\u00c2a\u0000\u08d6\u08d7\u0005\u0004\u0000\u0000\u08d7"+
		"\u08df\u0001\u0000\u0000\u0000\u08d8\u08df\u0005\u000e\u0000\u0000\u08d9"+
		"\u08df\u0005\u000f\u0000\u0000\u08da\u08db\u0005\u0010\u0000\u0000\u08db"+
		"\u08dc\u0003\u00c2a\u0000\u08dc\u08dd\u0005\u0011\u0000\u0000\u08dd\u08df"+
		"\u0001\u0000\u0000\u0000\u08de\u08c5\u0001\u0000\u0000\u0000\u08de\u08c6"+
		"\u0001\u0000\u0000\u0000\u08de\u08c8\u0001\u0000\u0000\u0000\u08de\u08d4"+
		"\u0001\u0000\u0000\u0000\u08de\u08d8\u0001\u0000\u0000\u0000\u08de\u08d9"+
		"\u0001\u0000\u0000\u0000\u08de\u08da\u0001\u0000\u0000\u0000\u08df\u00c5"+
		"\u0001\u0000\u0000\u0000\u08e0\u08e2\u0005\u012f\u0000\u0000\u08e1\u08e3"+
		"\u0005\u0133\u0000\u0000\u08e2\u08e1\u0001\u0000\u0000\u0000\u08e2\u08e3"+
		"\u0001\u0000\u0000\u0000\u08e3\u08ff\u0001\u0000\u0000\u0000\u08e4\u08e6"+
		"\u0005\u012d\u0000\u0000\u08e5\u08e7\u0005\u0133\u0000\u0000\u08e6\u08e5"+
		"\u0001\u0000\u0000\u0000\u08e6\u08e7\u0001\u0000\u0000\u0000\u08e7\u08ff"+
		"\u0001\u0000\u0000\u0000\u08e8\u08ea\u0005\u0133\u0000\u0000\u08e9\u08eb"+
		"\u0005\u0133\u0000\u0000\u08ea\u08e9\u0001\u0000\u0000\u0000\u08ea\u08eb"+
		"\u0001\u0000\u0000\u0000\u08eb\u08ff\u0001\u0000\u0000\u0000\u08ec\u08ed"+
		"\u0005\u0012\u0000\u0000\u08ed\u08ee\u0005\u0138\u0000\u0000\u08ee\u08f0"+
		"\u0005\u0013\u0000\u0000\u08ef\u08f1\u0005\u0133\u0000\u0000\u08f0\u08ef"+
		"\u0001\u0000\u0000\u0000\u08f0\u08f1\u0001\u0000\u0000\u0000\u08f1\u08ff"+
		"\u0001\u0000\u0000\u0000\u08f2\u08f4\u0005\u0012\u0000\u0000\u08f3\u08f5"+
		"\u0005\u0138\u0000\u0000\u08f4\u08f3\u0001\u0000\u0000\u0000\u08f4\u08f5"+
		"\u0001\u0000\u0000\u0000\u08f5\u08f6\u0001\u0000\u0000\u0000\u08f6\u08f8"+
		"\u0005\u0002\u0000\u0000\u08f7\u08f9\u0005\u0138\u0000\u0000\u08f8\u08f7"+
		"\u0001\u0000\u0000\u0000\u08f8\u08f9\u0001\u0000\u0000\u0000\u08f9\u08fa"+
		"\u0001\u0000\u0000\u0000\u08fa\u08fc\u0005\u0013\u0000\u0000\u08fb\u08fd"+
		"\u0005\u0133\u0000\u0000\u08fc\u08fb\u0001\u0000\u0000\u0000\u08fc\u08fd"+
		"\u0001\u0000\u0000\u0000\u08fd\u08ff\u0001\u0000\u0000\u0000\u08fe\u08e0"+
		"\u0001\u0000\u0000\u0000\u08fe\u08e4\u0001\u0000\u0000\u0000\u08fe\u08e8"+
		"\u0001\u0000\u0000\u0000\u08fe\u08ec\u0001\u0000\u0000\u0000\u08fe\u08f2"+
		"\u0001\u0000\u0000\u0000\u08ff\u00c7\u0001\u0000\u0000\u0000\u0900\u0901"+
		"\u0003\u0100\u0080\u0000\u0901\u0902\u0005\u0127\u0000\u0000\u0902\u0903"+
		"\u0003~?\u0000\u0903\u00c9\u0001\u0000\u0000\u0000\u0904\u0905\u0005W"+
		"\u0000\u0000\u0905\u0909\u0007\u001b\u0000\u0000\u0906\u0907\u0005\u0103"+
		"\u0000\u0000\u0907\u0909\u0007\u001c\u0000\u0000\u0908\u0904\u0001\u0000"+
		"\u0000\u0000\u0908\u0906\u0001\u0000\u0000\u0000\u0909\u00cb\u0001\u0000"+
		"\u0000\u0000\u090a\u090b\u0005u\u0000\u0000\u090b\u090c\u0005\u0088\u0000"+
		"\u0000\u090c\u0910\u0003\u00ceg\u0000\u090d\u090e\u0005\u00cb\u0000\u0000"+
		"\u090e\u0910\u0007\u001d\u0000\u0000\u090f\u090a\u0001\u0000\u0000\u0000"+
		"\u090f\u090d\u0001\u0000\u0000\u0000\u0910\u00cd\u0001\u0000\u0000\u0000"+
		"\u0911\u0912\u0005\u00cb\u0000\u0000\u0912\u0919\u0005\u0106\u0000\u0000"+
		"\u0913\u0914\u0005\u00cb\u0000\u0000\u0914\u0919\u0005\u001f\u0000\u0000"+
		"\u0915\u0916\u0005\u00d0\u0000\u0000\u0916\u0919\u0005\u00cb\u0000\u0000"+
		"\u0917\u0919\u0005\u00e8\u0000\u0000\u0918\u0911\u0001\u0000\u0000\u0000"+
		"\u0918\u0913\u0001\u0000\u0000\u0000\u0918\u0915\u0001\u0000\u0000\u0000"+
		"\u0918\u0917\u0001\u0000\u0000\u0000\u0919\u00cf\u0001\u0000\u0000\u0000"+
		"\u091a\u0920\u0003~?\u0000\u091b\u091c\u0003\u0100\u0080\u0000\u091c\u091d"+
		"\u0005\u0006\u0000\u0000\u091d\u091e\u0003~?\u0000\u091e\u0920";
	private static final String _serializedATNSegment1 =
		"\u0001\u0000\u0000\u0000\u091f\u091a\u0001\u0000\u0000\u0000\u091f\u091b"+
		"\u0001\u0000\u0000\u0000\u0920\u00d1\u0001\u0000\u0000\u0000\u0921\u0922"+
		"\u0003\u0100\u0080\u0000\u0922\u0923\u0005\u0001\u0000\u0000\u0923\u0924"+
		"\u0003\u0100\u0080\u0000\u0924\u0927\u0001\u0000\u0000\u0000\u0925\u0927"+
		"\u0003\u0100\u0080\u0000\u0926\u0921\u0001\u0000\u0000\u0000\u0926\u0925"+
		"\u0001\u0000\u0000\u0000\u0927\u00d3\u0001\u0000\u0000\u0000\u0928\u092d"+
		"\u0003\u00d2i\u0000\u0929\u092a\u0005\u0002\u0000\u0000\u092a\u092c\u0003"+
		"\u00d2i\u0000\u092b\u0929\u0001\u0000\u0000\u0000\u092c\u092f\u0001\u0000"+
		"\u0000\u0000\u092d\u092b\u0001\u0000\u0000\u0000\u092d\u092e\u0001\u0000"+
		"\u0000\u0000\u092e\u00d5\u0001\u0000\u0000\u0000\u092f\u092d\u0001\u0000"+
		"\u0000\u0000\u0930\u0931\u0005Z\u0000\u0000\u0931\u0932\u0003\u00d8l\u0000"+
		"\u0932\u0936\u0003\u00dcn\u0000\u0933\u0935\u0003\u00deo\u0000\u0934\u0933"+
		"\u0001\u0000\u0000\u0000\u0935\u0938\u0001\u0000\u0000\u0000\u0936\u0934"+
		"\u0001\u0000\u0000\u0000\u0936\u0937\u0001\u0000\u0000\u0000\u0937\u0939"+
		"\u0001\u0000\u0000\u0000\u0938\u0936\u0001\u0000\u0000\u0000\u0939\u093a"+
		"\u0003\u00e0p\u0000\u093a\u00d7\u0001\u0000\u0000\u0000\u093b\u093c\u0003"+
		"\u00f2y\u0000\u093c\u0945\u0005\u0003\u0000\u0000\u093d\u0942\u0003\u00da"+
		"m\u0000\u093e\u093f\u0005\u0002\u0000\u0000\u093f\u0941\u0003\u00dam\u0000"+
		"\u0940\u093e\u0001\u0000\u0000\u0000\u0941\u0944\u0001\u0000\u0000\u0000"+
		"\u0942\u0940\u0001\u0000\u0000\u0000\u0942\u0943\u0001\u0000\u0000\u0000"+
		"\u0943\u0946\u0001\u0000\u0000\u0000\u0944\u0942\u0001\u0000\u0000\u0000"+
		"\u0945\u093d\u0001\u0000\u0000\u0000\u0945\u0946\u0001\u0000\u0000\u0000"+
		"\u0946\u0947\u0001\u0000\u0000\u0000\u0947\u0948\u0005\u0004\u0000\u0000"+
		"\u0948\u00d9\u0001\u0000\u0000\u0000\u0949\u094b\u0003\u0100\u0080\u0000"+
		"\u094a\u0949\u0001\u0000\u0000\u0000\u094a\u094b\u0001\u0000\u0000\u0000"+
		"\u094b\u094c\u0001\u0000\u0000\u0000\u094c\u094d\u0003\u00aeW\u0000\u094d"+
		"\u00db\u0001\u0000\u0000\u0000\u094e\u094f\u0005\u00d7\u0000\u0000\u094f"+
		"\u0950\u0003\u00aeW\u0000\u0950\u00dd\u0001\u0000\u0000\u0000\u0951\u0952"+
		"\u0005\u0082\u0000\u0000\u0952\u0965\u0003\u0100\u0080\u0000\u0953\u0955"+
		"\u0005\u00a5\u0000\u0000\u0954\u0953\u0001\u0000\u0000\u0000\u0954\u0955"+
		"\u0001\u0000\u0000\u0000\u0955\u0956\u0001\u0000\u0000\u0000\u0956\u0965"+
		"\u0005=\u0000\u0000\u0957\u0958\u0005\u00d7\u0000\u0000\u0958\u0959\u0005"+
		"\u00a6\u0000\u0000\u0959\u095a\u0005\u00ad\u0000\u0000\u095a\u095b\u0005"+
		"\u00a6\u0000\u0000\u095b\u0965\u0005m\u0000\u0000\u095c\u095d\u0005\u0015"+
		"\u0000\u0000\u095d\u095e\u0005\u00ad\u0000\u0000\u095e\u095f\u0005\u00a6"+
		"\u0000\u0000\u095f\u0965\u0005m\u0000\u0000\u0960\u0961\u0005\u00e5\u0000"+
		"\u0000\u0961\u0965\u0007\u001e\u0000\u0000\u0962\u0963\u0005\u001d\u0000"+
		"\u0000\u0963\u0965\u0003\u009eO\u0000\u0964\u0951\u0001\u0000\u0000\u0000"+
		"\u0964\u0954\u0001\u0000\u0000\u0000\u0964\u0957\u0001\u0000\u0000\u0000"+
		"\u0964\u095c\u0001\u0000\u0000\u0000\u0964\u0960\u0001\u0000\u0000\u0000"+
		"\u0964\u0962\u0001\u0000\u0000\u0000\u0965\u00df\u0001\u0000\u0000\u0000"+
		"\u0966\u0967\u0005\u00d5\u0000\u0000\u0967\u09ca\u0003\u0084B\u0000\u0968"+
		"\u0969\u0005\u00ea\u0000\u0000\u0969\u096a\u0003\u0100\u0080\u0000\u096a"+
		"\u096b\u0005\u0127\u0000\u0000\u096b\u096c\u0003~?\u0000\u096c\u09ca\u0001"+
		"\u0000\u0000\u0000\u096d\u096e\u0005\u0017\u0000\u0000\u096e\u0970\u0003"+
		"~?\u0000\u096f\u0971\u0003\u00e2q\u0000\u0970\u096f\u0001\u0000\u0000"+
		"\u0000\u0971\u0972\u0001\u0000\u0000\u0000\u0972\u0970\u0001\u0000\u0000"+
		"\u0000\u0972\u0973\u0001\u0000\u0000\u0000\u0973\u0975\u0001\u0000\u0000"+
		"\u0000\u0974\u0976\u0003\u00e6s\u0000\u0975\u0974\u0001\u0000\u0000\u0000"+
		"\u0975\u0976\u0001\u0000\u0000\u0000\u0976\u0977\u0001\u0000\u0000\u0000"+
		"\u0977\u0978\u0005G\u0000\u0000\u0978\u0979\u0005\u0017\u0000\u0000\u0979"+
		"\u09ca\u0001\u0000\u0000\u0000\u097a\u097c\u0005\u0017\u0000\u0000\u097b"+
		"\u097d\u0003\u00e2q\u0000\u097c\u097b\u0001\u0000\u0000\u0000\u097d\u097e"+
		"\u0001\u0000\u0000\u0000\u097e\u097c\u0001\u0000\u0000\u0000\u097e\u097f"+
		"\u0001\u0000\u0000\u0000\u097f\u0981\u0001\u0000\u0000\u0000\u0980\u0982"+
		"\u0003\u00e6s\u0000\u0981\u0980\u0001\u0000\u0000\u0000\u0981\u0982\u0001"+
		"\u0000\u0000\u0000\u0982\u0983\u0001\u0000\u0000\u0000\u0983\u0984\u0005"+
		"G\u0000\u0000\u0984\u0985\u0005\u0017\u0000\u0000\u0985\u09ca\u0001\u0000"+
		"\u0000\u0000\u0986\u0987\u0005f\u0000\u0000\u0987\u0988\u0003~?\u0000"+
		"\u0988\u0989\u0005\u00f8\u0000\u0000\u0989\u098d\u0003\u00eau\u0000\u098a"+
		"\u098c\u0003\u00e4r\u0000\u098b\u098a\u0001\u0000\u0000\u0000\u098c\u098f"+
		"\u0001\u0000\u0000\u0000\u098d\u098b\u0001\u0000\u0000\u0000\u098d\u098e"+
		"\u0001\u0000\u0000\u0000\u098e\u0991\u0001\u0000\u0000\u0000\u098f\u098d"+
		"\u0001\u0000\u0000\u0000\u0990\u0992\u0003\u00e6s\u0000\u0991\u0990\u0001"+
		"\u0000\u0000\u0000\u0991\u0992\u0001\u0000\u0000\u0000\u0992\u0993\u0001"+
		"\u0000\u0000\u0000\u0993\u0994\u0005G\u0000\u0000\u0994\u0995\u0005f\u0000"+
		"\u0000\u0995\u09ca\u0001\u0000\u0000\u0000\u0996\u0997\u0005v\u0000\u0000"+
		"\u0997\u09ca\u0003\u0100\u0080\u0000\u0998\u0999\u0005\u0086\u0000\u0000"+
		"\u0999\u09ca\u0003\u0100\u0080\u0000\u099a\u09a0\u0005\u000f\u0000\u0000"+
		"\u099b\u099c\u0003\u00e8t\u0000\u099c\u099d\u0005\u0134\u0000\u0000\u099d"+
		"\u099f\u0001\u0000\u0000\u0000\u099e\u099b\u0001\u0000\u0000\u0000\u099f"+
		"\u09a2\u0001\u0000\u0000\u0000\u09a0\u099e\u0001\u0000\u0000\u0000\u09a0"+
		"\u09a1\u0001\u0000\u0000\u0000\u09a1\u09a4\u0001\u0000\u0000\u0000\u09a2"+
		"\u09a0\u0001\u0000\u0000\u0000\u09a3\u09a5\u0003\u00eau\u0000\u09a4\u09a3"+
		"\u0001\u0000\u0000\u0000\u09a4\u09a5\u0001\u0000\u0000\u0000\u09a5\u09a6"+
		"\u0001\u0000\u0000\u0000\u09a6\u09ca\u0005G\u0000\u0000\u09a7\u09a8\u0003"+
		"\u0100\u0080\u0000\u09a8\u09a9\u0005\n\u0000\u0000\u09a9\u09ab\u0001\u0000"+
		"\u0000\u0000\u09aa\u09a7\u0001\u0000\u0000\u0000\u09aa\u09ab\u0001\u0000"+
		"\u0000\u0000\u09ab\u09ac\u0001\u0000\u0000\u0000\u09ac\u09ad\u0005\u0090"+
		"\u0000\u0000\u09ad\u09ae\u0003\u00eau\u0000\u09ae\u09af\u0005G\u0000\u0000"+
		"\u09af\u09b0\u0005\u0090\u0000\u0000\u09b0\u09ca\u0001\u0000\u0000\u0000"+
		"\u09b1\u09b2\u0003\u0100\u0080\u0000\u09b2\u09b3\u0005\n\u0000\u0000\u09b3"+
		"\u09b5\u0001\u0000\u0000\u0000\u09b4\u09b1\u0001\u0000\u0000\u0000\u09b4"+
		"\u09b5\u0001\u0000\u0000\u0000\u09b5\u09b6\u0001\u0000\u0000\u0000\u09b6"+
		"\u09b7\u0005\u011d\u0000\u0000\u09b7\u09b8\u0003~?\u0000\u09b8\u09b9\u0005"+
		"@\u0000\u0000\u09b9\u09ba\u0003\u00eau\u0000\u09ba\u09bb\u0005G\u0000"+
		"\u0000\u09bb\u09bc\u0005\u011d\u0000\u0000\u09bc\u09ca\u0001\u0000\u0000"+
		"\u0000\u09bd\u09be\u0003\u0100\u0080\u0000\u09be\u09bf\u0005\n\u0000\u0000"+
		"\u09bf\u09c1\u0001\u0000\u0000\u0000\u09c0\u09bd\u0001\u0000\u0000\u0000"+
		"\u09c0\u09c1\u0001\u0000\u0000\u0000\u09c1\u09c2\u0001\u0000\u0000\u0000"+
		"\u09c2\u09c3\u0005\u00cf\u0000\u0000\u09c3\u09c4\u0003\u00eau\u0000\u09c4"+
		"\u09c5\u0005\u010d\u0000\u0000\u09c5\u09c6\u0003~?\u0000\u09c6\u09c7\u0005"+
		"G\u0000\u0000\u09c7\u09c8\u0005\u00cf\u0000\u0000\u09c8\u09ca\u0001\u0000"+
		"\u0000\u0000\u09c9\u0966\u0001\u0000\u0000\u0000\u09c9\u0968\u0001\u0000"+
		"\u0000\u0000\u09c9\u096d\u0001\u0000\u0000\u0000\u09c9\u097a\u0001\u0000"+
		"\u0000\u0000\u09c9\u0986\u0001\u0000\u0000\u0000\u09c9\u0996\u0001\u0000"+
		"\u0000\u0000\u09c9\u0998\u0001\u0000\u0000\u0000\u09c9\u099a\u0001\u0000"+
		"\u0000\u0000\u09c9\u09aa\u0001\u0000\u0000\u0000\u09c9\u09b4\u0001\u0000"+
		"\u0000\u0000\u09c9\u09c0\u0001\u0000\u0000\u0000\u09ca\u00e1\u0001\u0000"+
		"\u0000\u0000\u09cb\u09cc\u0005\u011b\u0000\u0000\u09cc\u09cd\u0003~?\u0000"+
		"\u09cd\u09ce\u0005\u00f8\u0000\u0000\u09ce\u09cf\u0003\u00eau\u0000\u09cf"+
		"\u00e3\u0001\u0000\u0000\u0000\u09d0\u09d1\u0005E\u0000\u0000\u09d1\u09d2"+
		"\u0003~?\u0000\u09d2\u09d3\u0005\u00f8\u0000\u0000\u09d3\u09d4\u0003\u00ea"+
		"u\u0000\u09d4\u00e5\u0001\u0000\u0000\u0000\u09d5\u09d6\u0005C\u0000\u0000"+
		"\u09d6\u09d7\u0003\u00eau\u0000\u09d7\u00e7\u0001\u0000\u0000\u0000\u09d8"+
		"\u09d9\u00054\u0000\u0000\u09d9\u09de\u0003\u0100\u0080\u0000\u09da\u09db"+
		"\u0005\u0002\u0000\u0000\u09db\u09dd\u0003\u0100\u0080\u0000\u09dc\u09da"+
		"\u0001\u0000\u0000\u0000\u09dd\u09e0\u0001\u0000\u0000\u0000\u09de\u09dc"+
		"\u0001\u0000\u0000\u0000\u09de\u09df\u0001\u0000\u0000\u0000\u09df\u09e1"+
		"\u0001\u0000\u0000\u0000\u09e0\u09de\u0001\u0000\u0000\u0000\u09e1\u09e4"+
		"\u0003\u00aeW\u0000\u09e2\u09e3\u00055\u0000\u0000\u09e3\u09e5\u0003\u0084"+
		"B\u0000\u09e4\u09e2\u0001\u0000\u0000\u0000\u09e4\u09e5\u0001\u0000\u0000"+
		"\u0000\u09e5\u00e9\u0001\u0000\u0000\u0000\u09e6\u09e7\u0003\u00e0p\u0000"+
		"\u09e7\u09e8\u0005\u0134\u0000\u0000\u09e8\u09ea\u0001\u0000\u0000\u0000"+
		"\u09e9\u09e6\u0001\u0000\u0000\u0000\u09ea\u09eb\u0001\u0000\u0000\u0000"+
		"\u09eb\u09e9\u0001\u0000\u0000\u0000\u09eb\u09ec\u0001\u0000\u0000\u0000"+
		"\u09ec\u00eb\u0001\u0000\u0000\u0000\u09ed\u09f4\u0005$\u0000\u0000\u09ee"+
		"\u09f4\u0005\u00e7\u0000\u0000\u09ef\u09f4\u00058\u0000\u0000\u09f0\u09f4"+
		"\u0005n\u0000\u0000\u09f1\u09f4\u0005\u010e\u0000\u0000\u09f2\u09f4\u0003"+
		"\u0100\u0080\u0000\u09f3\u09ed\u0001\u0000\u0000\u0000\u09f3\u09ee\u0001"+
		"\u0000\u0000\u0000\u09f3\u09ef\u0001\u0000\u0000\u0000\u09f3\u09f0\u0001"+
		"\u0000\u0000\u0000\u09f3\u09f1\u0001\u0000\u0000\u0000\u09f3\u09f2\u0001"+
		"\u0000\u0000\u0000\u09f4\u00ed\u0001\u0000\u0000\u0000\u09f5\u09f9\u0005"+
		"\u00f3\u0000\u0000\u09f6\u09f9\u0005\u00e2\u0000\u0000\u09f7\u09f9\u0003"+
		"\u0100\u0080\u0000\u09f8\u09f5\u0001\u0000\u0000\u0000\u09f8\u09f6\u0001"+
		"\u0000\u0000\u0000\u09f8\u09f7\u0001\u0000\u0000\u0000\u09f9\u00ef\u0001"+
		"\u0000\u0000\u0000\u09fa\u09fc\u0003\u00eew\u0000\u09fb\u09fa\u0001\u0000"+
		"\u0000\u0000\u09fb\u09fc\u0001\u0000\u0000\u0000\u09fc\u09fd\u0001\u0000"+
		"\u0000\u0000\u09fd\u09fe\u0003\u00f2y\u0000\u09fe\u00f1\u0001\u0000\u0000"+
		"\u0000\u09ff\u0a04\u0003\u0100\u0080\u0000\u0a00\u0a01\u0005\u0001\u0000"+
		"\u0000\u0a01\u0a03\u0003\u0100\u0080\u0000\u0a02\u0a00\u0001\u0000\u0000"+
		"\u0000\u0a03\u0a06\u0001\u0000\u0000\u0000\u0a04\u0a02\u0001\u0000\u0000"+
		"\u0000\u0a04\u0a05\u0001\u0000\u0000\u0000\u0a05\u00f3\u0001\u0000\u0000"+
		"\u0000\u0a06\u0a04\u0001\u0000\u0000\u0000\u0a07\u0a08\u0005V\u0000\u0000"+
		"\u0a08\u0a09\u0003\u00f6{\u0000\u0a09\u0a0a\u0005\u000b\u0000\u0000\u0a0a"+
		"\u0a0b\u0005\u00aa\u0000\u0000\u0a0b\u0a0c\u0003\u0084B\u0000\u0a0c\u00f5"+
		"\u0001\u0000\u0000\u0000\u0a0d\u0a0e\u0007\u001f\u0000\u0000\u0a0e\u00f7"+
		"\u0001\u0000\u0000\u0000\u0a0f\u0a13\u0003\u00fa}\u0000\u0a10\u0a13\u0005"+
		"/\u0000\u0000\u0a11\u0a13\u0005+\u0000\u0000\u0a12\u0a0f\u0001\u0000\u0000"+
		"\u0000\u0a12\u0a10\u0001\u0000\u0000\u0000\u0a12\u0a11\u0001\u0000\u0000"+
		"\u0000\u0a13\u00f9\u0001\u0000\u0000\u0000\u0a14\u0a1a\u0003\u0100\u0080"+
		"\u0000\u0a15\u0a16\u0005\u0110\u0000\u0000\u0a16\u0a1a\u0003\u0100\u0080"+
		"\u0000\u0a17\u0a18\u0005\u00da\u0000\u0000\u0a18\u0a1a\u0003\u0100\u0080"+
		"\u0000\u0a19\u0a14\u0001\u0000\u0000\u0000\u0a19\u0a15\u0001\u0000\u0000"+
		"\u0000\u0a19\u0a17\u0001\u0000\u0000\u0000\u0a1a\u00fb\u0001\u0000\u0000"+
		"\u0000\u0a1b\u0a20\u0003\u0100\u0080\u0000\u0a1c\u0a1d\u0005\u0002\u0000"+
		"\u0000\u0a1d\u0a1f\u0003\u0100\u0080\u0000\u0a1e\u0a1c\u0001\u0000\u0000"+
		"\u0000\u0a1f\u0a22\u0001\u0000\u0000\u0000\u0a20\u0a1e\u0001\u0000\u0000"+
		"\u0000\u0a20\u0a21\u0001\u0000\u0000\u0000\u0a21\u00fd\u0001\u0000\u0000"+
		"\u0000\u0a22\u0a20\u0001\u0000\u0000\u0000\u0a23\u0a2a\u0005$\u0000\u0000"+
		"\u0a24\u0a2a\u0005\u00e7\u0000\u0000\u0a25\u0a2a\u00058\u0000\u0000\u0a26"+
		"\u0a2a\u0005n\u0000\u0000\u0a27\u0a2a\u0005\u010e\u0000\u0000\u0a28\u0a2a"+
		"\u0003\u0100\u0080\u0000\u0a29\u0a23\u0001\u0000\u0000\u0000\u0a29\u0a24"+
		"\u0001\u0000\u0000\u0000\u0a29\u0a25\u0001\u0000\u0000\u0000\u0a29\u0a26"+
		"\u0001\u0000\u0000\u0000\u0a29\u0a27\u0001\u0000\u0000\u0000\u0a29\u0a28"+
		"\u0001\u0000\u0000\u0000\u0a2a\u00ff\u0001\u0000\u0000\u0000\u0a2b\u0a31"+
		"\u0005\u013b\u0000\u0000\u0a2c\u0a31\u0005\u013d\u0000\u0000\u0a2d\u0a31"+
		"\u0003\u0106\u0083\u0000\u0a2e\u0a31\u0005\u013e\u0000\u0000\u0a2f\u0a31"+
		"\u0005\u013c\u0000\u0000\u0a30\u0a2b\u0001\u0000\u0000\u0000\u0a30\u0a2c"+
		"\u0001\u0000\u0000\u0000\u0a30\u0a2d\u0001\u0000\u0000\u0000\u0a30\u0a2e"+
		"\u0001\u0000\u0000\u0000\u0a30\u0a2f\u0001\u0000\u0000\u0000\u0a31\u0101"+
		"\u0001\u0000\u0000\u0000\u0a32\u0a34\u0005\u012e\u0000\u0000\u0a33\u0a32"+
		"\u0001\u0000\u0000\u0000\u0a33\u0a34\u0001\u0000\u0000\u0000\u0a34\u0a35"+
		"\u0001\u0000\u0000\u0000\u0a35\u0a3f\u0005\u0139\u0000\u0000\u0a36\u0a38"+
		"\u0005\u012e\u0000\u0000\u0a37\u0a36\u0001\u0000\u0000\u0000\u0a37\u0a38"+
		"\u0001\u0000\u0000\u0000\u0a38\u0a39\u0001\u0000\u0000\u0000\u0a39\u0a3f"+
		"\u0005\u013a\u0000\u0000\u0a3a\u0a3c\u0005\u012e\u0000\u0000\u0a3b\u0a3a"+
		"\u0001\u0000\u0000\u0000\u0a3b\u0a3c\u0001\u0000\u0000\u0000\u0a3c\u0a3d"+
		"\u0001\u0000\u0000\u0000\u0a3d\u0a3f\u0005\u0138\u0000\u0000\u0a3e\u0a33"+
		"\u0001\u0000\u0000\u0000\u0a3e\u0a37\u0001\u0000\u0000\u0000\u0a3e\u0a3b"+
		"\u0001\u0000\u0000\u0000\u0a3f\u0103\u0001\u0000\u0000\u0000\u0a40\u0a43"+
		"\u0003\u0100\u0080\u0000\u0a41\u0a43\u0003\u009eO\u0000\u0a42\u0a40\u0001"+
		"\u0000\u0000\u0000\u0a42\u0a41\u0001\u0000\u0000\u0000\u0a43\u0105\u0001"+
		"\u0000\u0000\u0000\u0a44\u0a45\u0007 \u0000\u0000\u0a45\u0107\u0001\u0000"+
		"\u0000\u0000\u0156\u012a\u012f\u0131\u0134\u013e\u0142\u0148\u014f\u0154"+
		"\u015a\u015e\u0162\u0168\u0173\u017c\u0186\u0189\u018e\u0190\u0197\u019d"+
		"\u019f\u01a3\u01ad\u01b3\u01b6\u01b8\u01c4\u01cb\u01cf\u01d3\u01d7\u01de"+
		"\u01e7\u01ea\u01ee\u01f3\u01f7\u01ff\u0202\u0205\u020c\u0217\u021a\u0224"+
		"\u0227\u0232\u0237\u023f\u0242\u0246\u024f\u0258\u025b\u0264\u0267\u026a"+
		"\u026e\u0279\u027c\u0283\u0286\u0299\u029d\u02a1\u02a5\u02a9\u02ad\u02af"+
		"\u02ba\u02bf\u02c8\u02d1\u02d4\u02da\u02e6\u02e9\u02f2\u02f5\u02fd\u0300"+
		"\u0303\u0308\u030b\u0317\u031a\u0322\u0327\u032b\u032d\u032f\u033e\u0340"+
		"\u034b\u0360\u036a\u0375\u0379\u037b\u0383\u038a\u0397\u039d\u03b7\u03c6"+
		"\u03cb\u03cf\u03d9\u03df\u03e5\u03ed\u03f2\u03f9\u03fb\u0401\u0407\u040b"+
		"\u0410\u0419\u041e\u042c\u0436\u0439\u0442\u0447\u044c\u044e\u0457\u045a"+
		"\u0462\u0465\u046c\u0471\u047c\u047f\u0483\u0485\u048d\u0497\u049d\u049f"+
		"\u04a6\u04aa\u04ac\u04b3\u04b7\u04b9\u04bb\u04c4\u04cf\u04d3\u04dd\u04e7"+
		"\u04eb\u04f3\u04f5\u0502\u050a\u0513\u0519\u0521\u0527\u052b\u0530\u0535"+
		"\u053b\u0549\u054b\u0569\u0574\u057c\u0581\u0586\u0593\u0599\u059c\u05a3"+
		"\u05a8\u05ab\u05ae\u05b3\u05ba\u05bd\u05c6\u05c9\u05cd\u05d0\u05d3\u05e2"+
		"\u05e5\u05f8\u05fc\u0604\u0608\u0621\u0624\u062d\u0633\u0639\u063f\u0648"+
		"\u064b\u064e\u0661\u066a\u0680\u0683\u068d\u0696\u069c\u06a2\u06ad\u06af"+
		"\u06b4\u06bb\u06bd\u06c3\u06c9\u06d4\u06dd\u06e2\u06e7\u06e9\u06eb\u06f1"+
		"\u06f3\u06fd\u0706\u0708\u070e\u0710\u0713\u071d\u071f\u0727\u072f\u0732"+
		"\u0737\u073c\u0748\u074c\u0750\u0753\u0755\u075d\u0760\u076a\u0772\u0778"+
		"\u077a\u0782\u078c\u0792\u07a0\u07a9\u07b0\u07b5\u07bc\u07c6\u07cb\u07d2"+
		"\u07ec\u07f1\u07f3\u07fa\u07fe\u0805\u0809\u081a\u0829\u0830\u0839\u0843"+
		"\u0848\u0851\u0856\u085e\u0866\u0869\u086f\u0872\u0879\u0881\u0884\u088c"+
		"\u088f\u08a9\u08b4\u08b9\u08c0\u08c2\u08cf\u08de\u08e2\u08e6\u08ea\u08f0"+
		"\u08f4\u08f8\u08fc\u08fe\u0908\u090f\u0918\u091f\u0926\u092d\u0936\u0942"+
		"\u0945\u094a\u0954\u0964\u0972\u0975\u097e\u0981\u098d\u0991\u09a0\u09a4"+
		"\u09aa\u09b4\u09c0\u09c9\u09de\u09e4\u09eb\u09f3\u09f8\u09fb\u0a04\u0a12"+
		"\u0a19\u0a20\u0a29\u0a30\u0a33\u0a37\u0a3b\u0a3e\u0a42";
	public static final String _serializedATN = Utils.join(
		new String[] {
			_serializedATNSegment0,
			_serializedATNSegment1
		},
		""
	);
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}