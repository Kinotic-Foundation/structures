// Generated from StructuresSQL.g4 by ANTLR 4.13.2
package org.kinotic.structures.sql.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class StructuresSQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ABORT=1, ADD=2, ALTER=3, AND=4, AUTO=5, COLUMN=6, COMPONENT=7, CONFLICTS=8, 
		CREATE=9, DATE=10, DELETE=11, DOUBLE=12, EXISTS=13, FLOAT=14, FOR=15, 
		FROM=16, IF=17, INDEX=18, INSERT=19, INTO=20, LONG=21, MAX_DOCS=22, NOT=23, 
		NUMBER_OF_REPLICAS=24, NUMBER_OF_SHARDS=25, OR=26, PROCEED=27, QUERY=28, 
		REFRESH=29, REINDEX=30, SCRIPT=31, SET=32, SIZE=33, SLICES=34, SOURCE_FIELDS=35, 
		TABLE=36, TEMPLATE=37, UPDATE=38, USING=39, VALUES=40, WHERE=41, WITH=42, 
		BOOLEAN=43, INTEGER=44, KEYWORD=45, TEXT=46, COMMA=47, DIVIDE=48, EQUALS=49, 
		GREATER_THAN=50, GREATER_THAN_EQUALS=51, LESS_THAN=52, LESS_THAN_EQUALS=53, 
		LPAREN=54, MINUS=55, MULTIPLY=56, NOT_EQUALS=57, PARAMETER=58, PLUS=59, 
		RPAREN=60, SEMICOLON=61, BOOLEAN_LITERAL=62, ID=63, INTEGER_LITERAL=64, 
		STRING=65, COMMENT=66, WS=67;
	public static final int
		RULE_migrations = 0, RULE_statement = 1, RULE_createTableStatement = 2, 
		RULE_createComponentTemplateStatement = 3, RULE_createIndexTemplateStatement = 4, 
		RULE_templatePart = 5, RULE_alterTableStatement = 6, RULE_reindexStatement = 7, 
		RULE_reindexOptions = 8, RULE_reindexOption = 9, RULE_updateStatement = 10, 
		RULE_deleteStatement = 11, RULE_insertStatement = 12, RULE_valueList = 13, 
		RULE_value = 14, RULE_assignment = 15, RULE_expression = 16, RULE_operator = 17, 
		RULE_whereClause = 18, RULE_condition = 19, RULE_comparisonOperator = 20, 
		RULE_tableName = 21, RULE_columnName = 22, RULE_columnDefinition = 23, 
		RULE_type = 24, RULE_comment = 25;
	private static String[] makeRuleNames() {
		return new String[] {
			"migrations", "statement", "createTableStatement", "createComponentTemplateStatement", 
			"createIndexTemplateStatement", "templatePart", "alterTableStatement", 
			"reindexStatement", "reindexOptions", "reindexOption", "updateStatement", 
			"deleteStatement", "insertStatement", "valueList", "value", "assignment", 
			"expression", "operator", "whereClause", "condition", "comparisonOperator", 
			"tableName", "columnName", "columnDefinition", "type", "comment"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'ABORT'", "'ADD'", "'ALTER'", "'AND'", "'AUTO'", "'COLUMN'", "'COMPONENT'", 
			"'CONFLICTS'", "'CREATE'", "'DATE'", "'DELETE'", "'DOUBLE'", "'EXISTS'", 
			"'FLOAT'", "'FOR'", "'FROM'", "'IF'", "'INDEX'", "'INSERT'", "'INTO'", 
			"'LONG'", "'MAX_DOCS'", "'NOT'", "'NUMBER_OF_REPLICAS'", "'NUMBER_OF_SHARDS'", 
			"'OR'", "'PROCEED'", "'QUERY'", "'REFRESH'", "'REINDEX'", "'SCRIPT'", 
			"'SET'", "'SIZE'", "'SLICES'", "'SOURCE_FIELDS'", "'TABLE'", "'TEMPLATE'", 
			"'UPDATE'", "'USING'", "'VALUES'", "'WHERE'", "'WITH'", "'BOOLEAN'", 
			"'INTEGER'", "'KEYWORD'", "'TEXT'", "','", "'/'", "'=='", "'>'", "'>='", 
			"'<'", "'<='", "'('", "'-'", "'*'", "'!='", "'?'", "'+'", "')'", "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ABORT", "ADD", "ALTER", "AND", "AUTO", "COLUMN", "COMPONENT", 
			"CONFLICTS", "CREATE", "DATE", "DELETE", "DOUBLE", "EXISTS", "FLOAT", 
			"FOR", "FROM", "IF", "INDEX", "INSERT", "INTO", "LONG", "MAX_DOCS", "NOT", 
			"NUMBER_OF_REPLICAS", "NUMBER_OF_SHARDS", "OR", "PROCEED", "QUERY", "REFRESH", 
			"REINDEX", "SCRIPT", "SET", "SIZE", "SLICES", "SOURCE_FIELDS", "TABLE", 
			"TEMPLATE", "UPDATE", "USING", "VALUES", "WHERE", "WITH", "BOOLEAN", 
			"INTEGER", "KEYWORD", "TEXT", "COMMA", "DIVIDE", "EQUALS", "GREATER_THAN", 
			"GREATER_THAN_EQUALS", "LESS_THAN", "LESS_THAN_EQUALS", "LPAREN", "MINUS", 
			"MULTIPLY", "NOT_EQUALS", "PARAMETER", "PLUS", "RPAREN", "SEMICOLON", 
			"BOOLEAN_LITERAL", "ID", "INTEGER_LITERAL", "STRING", "COMMENT", "WS"
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
	public String getGrammarFileName() { return "StructuresSQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public StructuresSQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MigrationsContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(StructuresSQLParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MigrationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_migrations; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterMigrations(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitMigrations(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitMigrations(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MigrationsContext migrations() throws RecognitionException {
		MigrationsContext _localctx = new MigrationsContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_migrations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & -9223372002360753855L) != 0)) {
				{
				{
				setState(52);
				statement();
				}
				}
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(58);
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
		public CreateTableStatementContext createTableStatement() {
			return getRuleContext(CreateTableStatementContext.class,0);
		}
		public CreateComponentTemplateStatementContext createComponentTemplateStatement() {
			return getRuleContext(CreateComponentTemplateStatementContext.class,0);
		}
		public CreateIndexTemplateStatementContext createIndexTemplateStatement() {
			return getRuleContext(CreateIndexTemplateStatementContext.class,0);
		}
		public AlterTableStatementContext alterTableStatement() {
			return getRuleContext(AlterTableStatementContext.class,0);
		}
		public ReindexStatementContext reindexStatement() {
			return getRuleContext(ReindexStatementContext.class,0);
		}
		public UpdateStatementContext updateStatement() {
			return getRuleContext(UpdateStatementContext.class,0);
		}
		public DeleteStatementContext deleteStatement() {
			return getRuleContext(DeleteStatementContext.class,0);
		}
		public InsertStatementContext insertStatement() {
			return getRuleContext(InsertStatementContext.class,0);
		}
		public CommentContext comment() {
			return getRuleContext(CommentContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(69);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(60);
				createTableStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(61);
				createComponentTemplateStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(62);
				createIndexTemplateStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(63);
				alterTableStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(64);
				reindexStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(65);
				updateStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(66);
				deleteStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(67);
				insertStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(68);
				comment();
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
	public static class CreateTableStatementContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(StructuresSQLParser.CREATE, 0); }
		public TerminalNode TABLE() { return getToken(StructuresSQLParser.TABLE, 0); }
		public TerminalNode ID() { return getToken(StructuresSQLParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(StructuresSQLParser.LPAREN, 0); }
		public List<ColumnDefinitionContext> columnDefinition() {
			return getRuleContexts(ColumnDefinitionContext.class);
		}
		public ColumnDefinitionContext columnDefinition(int i) {
			return getRuleContext(ColumnDefinitionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(StructuresSQLParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(StructuresSQLParser.SEMICOLON, 0); }
		public TerminalNode IF() { return getToken(StructuresSQLParser.IF, 0); }
		public TerminalNode NOT() { return getToken(StructuresSQLParser.NOT, 0); }
		public TerminalNode EXISTS() { return getToken(StructuresSQLParser.EXISTS, 0); }
		public List<TerminalNode> COMMA() { return getTokens(StructuresSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(StructuresSQLParser.COMMA, i);
		}
		public CreateTableStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTableStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterCreateTableStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitCreateTableStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitCreateTableStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateTableStatementContext createTableStatement() throws RecognitionException {
		CreateTableStatementContext _localctx = new CreateTableStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_createTableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(CREATE);
			setState(72);
			match(TABLE);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IF) {
				{
				setState(73);
				match(IF);
				setState(74);
				match(NOT);
				setState(75);
				match(EXISTS);
				}
			}

			setState(78);
			match(ID);
			setState(79);
			match(LPAREN);
			setState(80);
			columnDefinition();
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(81);
				match(COMMA);
				setState(82);
				columnDefinition();
				}
				}
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(88);
			match(RPAREN);
			setState(89);
			match(SEMICOLON);
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
	public static class CreateComponentTemplateStatementContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(StructuresSQLParser.CREATE, 0); }
		public TerminalNode COMPONENT() { return getToken(StructuresSQLParser.COMPONENT, 0); }
		public TerminalNode TEMPLATE() { return getToken(StructuresSQLParser.TEMPLATE, 0); }
		public TerminalNode ID() { return getToken(StructuresSQLParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(StructuresSQLParser.LPAREN, 0); }
		public List<TemplatePartContext> templatePart() {
			return getRuleContexts(TemplatePartContext.class);
		}
		public TemplatePartContext templatePart(int i) {
			return getRuleContext(TemplatePartContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(StructuresSQLParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(StructuresSQLParser.SEMICOLON, 0); }
		public List<TerminalNode> COMMA() { return getTokens(StructuresSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(StructuresSQLParser.COMMA, i);
		}
		public CreateComponentTemplateStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createComponentTemplateStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterCreateComponentTemplateStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitCreateComponentTemplateStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitCreateComponentTemplateStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateComponentTemplateStatementContext createComponentTemplateStatement() throws RecognitionException {
		CreateComponentTemplateStatementContext _localctx = new CreateComponentTemplateStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_createComponentTemplateStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			match(CREATE);
			setState(92);
			match(COMPONENT);
			setState(93);
			match(TEMPLATE);
			setState(94);
			match(ID);
			setState(95);
			match(LPAREN);
			setState(96);
			templatePart();
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(97);
				match(COMMA);
				setState(98);
				templatePart();
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(104);
			match(RPAREN);
			setState(105);
			match(SEMICOLON);
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
	public static class CreateIndexTemplateStatementContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(StructuresSQLParser.CREATE, 0); }
		public TerminalNode INDEX() { return getToken(StructuresSQLParser.INDEX, 0); }
		public TerminalNode TEMPLATE() { return getToken(StructuresSQLParser.TEMPLATE, 0); }
		public TerminalNode ID() { return getToken(StructuresSQLParser.ID, 0); }
		public TerminalNode FOR() { return getToken(StructuresSQLParser.FOR, 0); }
		public List<TerminalNode> STRING() { return getTokens(StructuresSQLParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(StructuresSQLParser.STRING, i);
		}
		public TerminalNode USING() { return getToken(StructuresSQLParser.USING, 0); }
		public TerminalNode SEMICOLON() { return getToken(StructuresSQLParser.SEMICOLON, 0); }
		public TerminalNode WITH() { return getToken(StructuresSQLParser.WITH, 0); }
		public TerminalNode LPAREN() { return getToken(StructuresSQLParser.LPAREN, 0); }
		public List<TemplatePartContext> templatePart() {
			return getRuleContexts(TemplatePartContext.class);
		}
		public TemplatePartContext templatePart(int i) {
			return getRuleContext(TemplatePartContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(StructuresSQLParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(StructuresSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(StructuresSQLParser.COMMA, i);
		}
		public CreateIndexTemplateStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createIndexTemplateStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterCreateIndexTemplateStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitCreateIndexTemplateStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitCreateIndexTemplateStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateIndexTemplateStatementContext createIndexTemplateStatement() throws RecognitionException {
		CreateIndexTemplateStatementContext _localctx = new CreateIndexTemplateStatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_createIndexTemplateStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(CREATE);
			setState(108);
			match(INDEX);
			setState(109);
			match(TEMPLATE);
			setState(110);
			match(ID);
			setState(111);
			match(FOR);
			setState(112);
			match(STRING);
			setState(113);
			match(USING);
			setState(114);
			match(STRING);
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(115);
				match(WITH);
				setState(116);
				match(LPAREN);
				setState(117);
				templatePart();
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(118);
					match(COMMA);
					setState(119);
					templatePart();
					}
					}
					setState(124);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(125);
				match(RPAREN);
				}
			}

			setState(129);
			match(SEMICOLON);
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
	public static class TemplatePartContext extends ParserRuleContext {
		public TerminalNode NUMBER_OF_SHARDS() { return getToken(StructuresSQLParser.NUMBER_OF_SHARDS, 0); }
		public TerminalNode EQUALS() { return getToken(StructuresSQLParser.EQUALS, 0); }
		public TerminalNode INTEGER_LITERAL() { return getToken(StructuresSQLParser.INTEGER_LITERAL, 0); }
		public TerminalNode NUMBER_OF_REPLICAS() { return getToken(StructuresSQLParser.NUMBER_OF_REPLICAS, 0); }
		public ColumnDefinitionContext columnDefinition() {
			return getRuleContext(ColumnDefinitionContext.class,0);
		}
		public TemplatePartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templatePart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterTemplatePart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitTemplatePart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitTemplatePart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TemplatePartContext templatePart() throws RecognitionException {
		TemplatePartContext _localctx = new TemplatePartContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_templatePart);
		try {
			setState(138);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMBER_OF_SHARDS:
				enterOuterAlt(_localctx, 1);
				{
				setState(131);
				match(NUMBER_OF_SHARDS);
				setState(132);
				match(EQUALS);
				setState(133);
				match(INTEGER_LITERAL);
				}
				break;
			case NUMBER_OF_REPLICAS:
				enterOuterAlt(_localctx, 2);
				{
				setState(134);
				match(NUMBER_OF_REPLICAS);
				setState(135);
				match(EQUALS);
				setState(136);
				match(INTEGER_LITERAL);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(137);
				columnDefinition();
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
	public static class AlterTableStatementContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(StructuresSQLParser.ALTER, 0); }
		public TerminalNode TABLE() { return getToken(StructuresSQLParser.TABLE, 0); }
		public List<TerminalNode> ID() { return getTokens(StructuresSQLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(StructuresSQLParser.ID, i);
		}
		public TerminalNode ADD() { return getToken(StructuresSQLParser.ADD, 0); }
		public TerminalNode COLUMN() { return getToken(StructuresSQLParser.COLUMN, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(StructuresSQLParser.SEMICOLON, 0); }
		public AlterTableStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterTableStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterAlterTableStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitAlterTableStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitAlterTableStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterTableStatementContext alterTableStatement() throws RecognitionException {
		AlterTableStatementContext _localctx = new AlterTableStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_alterTableStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(ALTER);
			setState(141);
			match(TABLE);
			setState(142);
			match(ID);
			setState(143);
			match(ADD);
			setState(144);
			match(COLUMN);
			setState(145);
			match(ID);
			setState(146);
			type();
			setState(147);
			match(SEMICOLON);
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
	public static class ReindexStatementContext extends ParserRuleContext {
		public TerminalNode REINDEX() { return getToken(StructuresSQLParser.REINDEX, 0); }
		public List<TerminalNode> ID() { return getTokens(StructuresSQLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(StructuresSQLParser.ID, i);
		}
		public TerminalNode INTO() { return getToken(StructuresSQLParser.INTO, 0); }
		public TerminalNode SEMICOLON() { return getToken(StructuresSQLParser.SEMICOLON, 0); }
		public ReindexOptionsContext reindexOptions() {
			return getRuleContext(ReindexOptionsContext.class,0);
		}
		public ReindexStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reindexStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterReindexStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitReindexStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitReindexStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReindexStatementContext reindexStatement() throws RecognitionException {
		ReindexStatementContext _localctx = new ReindexStatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_reindexStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			match(REINDEX);
			setState(150);
			match(ID);
			setState(151);
			match(INTO);
			setState(152);
			match(ID);
			setState(154);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(153);
				reindexOptions();
				}
			}

			setState(156);
			match(SEMICOLON);
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
	public static class ReindexOptionsContext extends ParserRuleContext {
		public TerminalNode WITH() { return getToken(StructuresSQLParser.WITH, 0); }
		public TerminalNode LPAREN() { return getToken(StructuresSQLParser.LPAREN, 0); }
		public List<ReindexOptionContext> reindexOption() {
			return getRuleContexts(ReindexOptionContext.class);
		}
		public ReindexOptionContext reindexOption(int i) {
			return getRuleContext(ReindexOptionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(StructuresSQLParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(StructuresSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(StructuresSQLParser.COMMA, i);
		}
		public ReindexOptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reindexOptions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterReindexOptions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitReindexOptions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitReindexOptions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReindexOptionsContext reindexOptions() throws RecognitionException {
		ReindexOptionsContext _localctx = new ReindexOptionsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_reindexOptions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			match(WITH);
			setState(159);
			match(LPAREN);
			setState(160);
			reindexOption();
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(161);
				match(COMMA);
				setState(162);
				reindexOption();
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(168);
			match(RPAREN);
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
	public static class ReindexOptionContext extends ParserRuleContext {
		public TerminalNode CONFLICTS() { return getToken(StructuresSQLParser.CONFLICTS, 0); }
		public TerminalNode EQUALS() { return getToken(StructuresSQLParser.EQUALS, 0); }
		public TerminalNode ABORT() { return getToken(StructuresSQLParser.ABORT, 0); }
		public TerminalNode PROCEED() { return getToken(StructuresSQLParser.PROCEED, 0); }
		public TerminalNode MAX_DOCS() { return getToken(StructuresSQLParser.MAX_DOCS, 0); }
		public TerminalNode INTEGER_LITERAL() { return getToken(StructuresSQLParser.INTEGER_LITERAL, 0); }
		public TerminalNode SLICES() { return getToken(StructuresSQLParser.SLICES, 0); }
		public TerminalNode AUTO() { return getToken(StructuresSQLParser.AUTO, 0); }
		public TerminalNode SIZE() { return getToken(StructuresSQLParser.SIZE, 0); }
		public TerminalNode SOURCE_FIELDS() { return getToken(StructuresSQLParser.SOURCE_FIELDS, 0); }
		public TerminalNode STRING() { return getToken(StructuresSQLParser.STRING, 0); }
		public TerminalNode QUERY() { return getToken(StructuresSQLParser.QUERY, 0); }
		public TerminalNode SCRIPT() { return getToken(StructuresSQLParser.SCRIPT, 0); }
		public ReindexOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reindexOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterReindexOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitReindexOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitReindexOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReindexOptionContext reindexOption() throws RecognitionException {
		ReindexOptionContext _localctx = new ReindexOptionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_reindexOption);
		int _la;
		try {
			setState(191);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONFLICTS:
				enterOuterAlt(_localctx, 1);
				{
				setState(170);
				match(CONFLICTS);
				setState(171);
				match(EQUALS);
				setState(172);
				_la = _input.LA(1);
				if ( !(_la==ABORT || _la==PROCEED) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case MAX_DOCS:
				enterOuterAlt(_localctx, 2);
				{
				setState(173);
				match(MAX_DOCS);
				setState(174);
				match(EQUALS);
				setState(175);
				match(INTEGER_LITERAL);
				}
				break;
			case SLICES:
				enterOuterAlt(_localctx, 3);
				{
				setState(176);
				match(SLICES);
				setState(177);
				match(EQUALS);
				setState(178);
				_la = _input.LA(1);
				if ( !(_la==AUTO || _la==INTEGER_LITERAL) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case SIZE:
				enterOuterAlt(_localctx, 4);
				{
				setState(179);
				match(SIZE);
				setState(180);
				match(EQUALS);
				setState(181);
				match(INTEGER_LITERAL);
				}
				break;
			case SOURCE_FIELDS:
				enterOuterAlt(_localctx, 5);
				{
				setState(182);
				match(SOURCE_FIELDS);
				setState(183);
				match(EQUALS);
				setState(184);
				match(STRING);
				}
				break;
			case QUERY:
				enterOuterAlt(_localctx, 6);
				{
				setState(185);
				match(QUERY);
				setState(186);
				match(EQUALS);
				setState(187);
				match(STRING);
				}
				break;
			case SCRIPT:
				enterOuterAlt(_localctx, 7);
				{
				setState(188);
				match(SCRIPT);
				setState(189);
				match(EQUALS);
				setState(190);
				match(STRING);
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
	public static class UpdateStatementContext extends ParserRuleContext {
		public TerminalNode UPDATE() { return getToken(StructuresSQLParser.UPDATE, 0); }
		public TerminalNode ID() { return getToken(StructuresSQLParser.ID, 0); }
		public TerminalNode SET() { return getToken(StructuresSQLParser.SET, 0); }
		public List<AssignmentContext> assignment() {
			return getRuleContexts(AssignmentContext.class);
		}
		public AssignmentContext assignment(int i) {
			return getRuleContext(AssignmentContext.class,i);
		}
		public TerminalNode WHERE() { return getToken(StructuresSQLParser.WHERE, 0); }
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(StructuresSQLParser.SEMICOLON, 0); }
		public List<TerminalNode> COMMA() { return getTokens(StructuresSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(StructuresSQLParser.COMMA, i);
		}
		public TerminalNode WITH() { return getToken(StructuresSQLParser.WITH, 0); }
		public TerminalNode REFRESH() { return getToken(StructuresSQLParser.REFRESH, 0); }
		public UpdateStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updateStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterUpdateStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitUpdateStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitUpdateStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdateStatementContext updateStatement() throws RecognitionException {
		UpdateStatementContext _localctx = new UpdateStatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_updateStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(UPDATE);
			setState(194);
			match(ID);
			setState(195);
			match(SET);
			setState(196);
			assignment();
			setState(201);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(197);
				match(COMMA);
				setState(198);
				assignment();
				}
				}
				setState(203);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(204);
			match(WHERE);
			setState(205);
			whereClause(0);
			setState(208);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(206);
				match(WITH);
				setState(207);
				match(REFRESH);
				}
			}

			setState(210);
			match(SEMICOLON);
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
	public static class DeleteStatementContext extends ParserRuleContext {
		public TerminalNode DELETE() { return getToken(StructuresSQLParser.DELETE, 0); }
		public TerminalNode FROM() { return getToken(StructuresSQLParser.FROM, 0); }
		public TerminalNode ID() { return getToken(StructuresSQLParser.ID, 0); }
		public TerminalNode WHERE() { return getToken(StructuresSQLParser.WHERE, 0); }
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(StructuresSQLParser.SEMICOLON, 0); }
		public TerminalNode WITH() { return getToken(StructuresSQLParser.WITH, 0); }
		public TerminalNode REFRESH() { return getToken(StructuresSQLParser.REFRESH, 0); }
		public DeleteStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterDeleteStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitDeleteStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitDeleteStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeleteStatementContext deleteStatement() throws RecognitionException {
		DeleteStatementContext _localctx = new DeleteStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_deleteStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			match(DELETE);
			setState(213);
			match(FROM);
			setState(214);
			match(ID);
			setState(215);
			match(WHERE);
			setState(216);
			whereClause(0);
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(217);
				match(WITH);
				setState(218);
				match(REFRESH);
				}
			}

			setState(221);
			match(SEMICOLON);
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
	public static class InsertStatementContext extends ParserRuleContext {
		public TerminalNode INSERT() { return getToken(StructuresSQLParser.INSERT, 0); }
		public TerminalNode INTO() { return getToken(StructuresSQLParser.INTO, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode VALUES() { return getToken(StructuresSQLParser.VALUES, 0); }
		public List<TerminalNode> LPAREN() { return getTokens(StructuresSQLParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(StructuresSQLParser.LPAREN, i);
		}
		public ValueListContext valueList() {
			return getRuleContext(ValueListContext.class,0);
		}
		public List<TerminalNode> RPAREN() { return getTokens(StructuresSQLParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(StructuresSQLParser.RPAREN, i);
		}
		public TerminalNode SEMICOLON() { return getToken(StructuresSQLParser.SEMICOLON, 0); }
		public List<ColumnNameContext> columnName() {
			return getRuleContexts(ColumnNameContext.class);
		}
		public ColumnNameContext columnName(int i) {
			return getRuleContext(ColumnNameContext.class,i);
		}
		public TerminalNode WITH() { return getToken(StructuresSQLParser.WITH, 0); }
		public TerminalNode REFRESH() { return getToken(StructuresSQLParser.REFRESH, 0); }
		public List<TerminalNode> COMMA() { return getTokens(StructuresSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(StructuresSQLParser.COMMA, i);
		}
		public InsertStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterInsertStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitInsertStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitInsertStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertStatementContext insertStatement() throws RecognitionException {
		InsertStatementContext _localctx = new InsertStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_insertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			match(INSERT);
			setState(224);
			match(INTO);
			setState(225);
			tableName();
			setState(237);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(226);
				match(LPAREN);
				setState(227);
				columnName();
				setState(232);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(228);
					match(COMMA);
					setState(229);
					columnName();
					}
					}
					setState(234);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(235);
				match(RPAREN);
				}
			}

			setState(239);
			match(VALUES);
			setState(240);
			match(LPAREN);
			setState(241);
			valueList();
			setState(242);
			match(RPAREN);
			setState(245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(243);
				match(WITH);
				setState(244);
				match(REFRESH);
				}
			}

			setState(247);
			match(SEMICOLON);
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
	public static class ValueListContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(StructuresSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(StructuresSQLParser.COMMA, i);
		}
		public ValueListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterValueList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitValueList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitValueList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueListContext valueList() throws RecognitionException {
		ValueListContext _localctx = new ValueListContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_valueList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			value();
			setState(254);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(250);
				match(COMMA);
				setState(251);
				value();
				}
				}
				setState(256);
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
	public static class ValueContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(StructuresSQLParser.STRING, 0); }
		public TerminalNode INTEGER_LITERAL() { return getToken(StructuresSQLParser.INTEGER_LITERAL, 0); }
		public TerminalNode BOOLEAN_LITERAL() { return getToken(StructuresSQLParser.BOOLEAN_LITERAL, 0); }
		public TerminalNode PARAMETER() { return getToken(StructuresSQLParser.PARAMETER, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			_la = _input.LA(1);
			if ( !(((((_la - 58)) & ~0x3f) == 0 && ((1L << (_la - 58)) & 209L) != 0)) ) {
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
	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(StructuresSQLParser.ID, 0); }
		public TerminalNode EQUALS() { return getToken(StructuresSQLParser.EQUALS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			match(ID);
			setState(260);
			match(EQUALS);
			setState(261);
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
	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode PARAMETER() { return getToken(StructuresSQLParser.PARAMETER, 0); }
		public TerminalNode STRING() { return getToken(StructuresSQLParser.STRING, 0); }
		public TerminalNode INTEGER_LITERAL() { return getToken(StructuresSQLParser.INTEGER_LITERAL, 0); }
		public TerminalNode BOOLEAN_LITERAL() { return getToken(StructuresSQLParser.BOOLEAN_LITERAL, 0); }
		public TerminalNode ID() { return getToken(StructuresSQLParser.ID, 0); }
		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(StructuresSQLParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(StructuresSQLParser.RPAREN, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_expression);
		try {
			setState(275);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PARAMETER:
				enterOuterAlt(_localctx, 1);
				{
				setState(263);
				match(PARAMETER);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(264);
				match(STRING);
				}
				break;
			case INTEGER_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(265);
				match(INTEGER_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(266);
				match(BOOLEAN_LITERAL);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 5);
				{
				setState(267);
				match(ID);
				setState(268);
				operator();
				setState(269);
				expression();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				setState(271);
				match(LPAREN);
				setState(272);
				expression();
				setState(273);
				match(RPAREN);
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
	public static class OperatorContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(StructuresSQLParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(StructuresSQLParser.MINUS, 0); }
		public TerminalNode MULTIPLY() { return getToken(StructuresSQLParser.MULTIPLY, 0); }
		public TerminalNode DIVIDE() { return getToken(StructuresSQLParser.DIVIDE, 0); }
		public TerminalNode EQUALS() { return getToken(StructuresSQLParser.EQUALS, 0); }
		public OperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorContext operator() throws RecognitionException {
		OperatorContext _localctx = new OperatorContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 685391568290447360L) != 0)) ) {
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
	public static class WhereClauseContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(StructuresSQLParser.LPAREN, 0); }
		public List<WhereClauseContext> whereClause() {
			return getRuleContexts(WhereClauseContext.class);
		}
		public WhereClauseContext whereClause(int i) {
			return getRuleContext(WhereClauseContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(StructuresSQLParser.RPAREN, 0); }
		public TerminalNode AND() { return getToken(StructuresSQLParser.AND, 0); }
		public TerminalNode OR() { return getToken(StructuresSQLParser.OR, 0); }
		public WhereClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whereClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterWhereClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitWhereClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitWhereClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhereClauseContext whereClause() throws RecognitionException {
		return whereClause(0);
	}

	private WhereClauseContext whereClause(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		WhereClauseContext _localctx = new WhereClauseContext(_ctx, _parentState);
		WhereClauseContext _prevctx = _localctx;
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_whereClause, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(280);
				condition();
				}
				break;
			case LPAREN:
				{
				setState(281);
				match(LPAREN);
				setState(282);
				whereClause(0);
				setState(283);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(295);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(293);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
					case 1:
						{
						_localctx = new WhereClauseContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_whereClause);
						setState(287);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(288);
						match(AND);
						setState(289);
						whereClause(3);
						}
						break;
					case 2:
						{
						_localctx = new WhereClauseContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_whereClause);
						setState(290);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(291);
						match(OR);
						setState(292);
						whereClause(2);
						}
						break;
					}
					} 
				}
				setState(297);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
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
	public static class ConditionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(StructuresSQLParser.ID, 0); }
		public ComparisonOperatorContext comparisonOperator() {
			return getRuleContext(ComparisonOperatorContext.class,0);
		}
		public TerminalNode PARAMETER() { return getToken(StructuresSQLParser.PARAMETER, 0); }
		public TerminalNode STRING() { return getToken(StructuresSQLParser.STRING, 0); }
		public TerminalNode INTEGER_LITERAL() { return getToken(StructuresSQLParser.INTEGER_LITERAL, 0); }
		public TerminalNode BOOLEAN_LITERAL() { return getToken(StructuresSQLParser.BOOLEAN_LITERAL, 0); }
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_condition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
			match(ID);
			setState(299);
			comparisonOperator();
			setState(300);
			_la = _input.LA(1);
			if ( !(((((_la - 58)) & ~0x3f) == 0 && ((1L << (_la - 58)) & 209L) != 0)) ) {
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
	public static class ComparisonOperatorContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(StructuresSQLParser.EQUALS, 0); }
		public TerminalNode NOT_EQUALS() { return getToken(StructuresSQLParser.NOT_EQUALS, 0); }
		public TerminalNode LESS_THAN() { return getToken(StructuresSQLParser.LESS_THAN, 0); }
		public TerminalNode GREATER_THAN() { return getToken(StructuresSQLParser.GREATER_THAN, 0); }
		public TerminalNode LESS_THAN_EQUALS() { return getToken(StructuresSQLParser.LESS_THAN_EQUALS, 0); }
		public TerminalNode GREATER_THAN_EQUALS() { return getToken(StructuresSQLParser.GREATER_THAN_EQUALS, 0); }
		public ComparisonOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterComparisonOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitComparisonOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitComparisonOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonOperatorContext comparisonOperator() throws RecognitionException {
		ComparisonOperatorContext _localctx = new ComparisonOperatorContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_comparisonOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(302);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 161566636631916544L) != 0)) ) {
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
	public static class TableNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(StructuresSQLParser.ID, 0); }
		public TableNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterTableName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitTableName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitTableName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableNameContext tableName() throws RecognitionException {
		TableNameContext _localctx = new TableNameContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_tableName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			match(ID);
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
	public static class ColumnNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(StructuresSQLParser.ID, 0); }
		public ColumnNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterColumnName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitColumnName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitColumnName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnNameContext columnName() throws RecognitionException {
		ColumnNameContext _localctx = new ColumnNameContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_columnName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306);
			match(ID);
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
		public TerminalNode ID() { return getToken(StructuresSQLParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ColumnDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterColumnDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitColumnDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitColumnDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnDefinitionContext columnDefinition() throws RecognitionException {
		ColumnDefinitionContext _localctx = new ColumnDefinitionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_columnDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			match(ID);
			setState(309);
			type();
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
		public TerminalNode TEXT() { return getToken(StructuresSQLParser.TEXT, 0); }
		public TerminalNode KEYWORD() { return getToken(StructuresSQLParser.KEYWORD, 0); }
		public TerminalNode INTEGER() { return getToken(StructuresSQLParser.INTEGER, 0); }
		public TerminalNode LONG() { return getToken(StructuresSQLParser.LONG, 0); }
		public TerminalNode FLOAT() { return getToken(StructuresSQLParser.FLOAT, 0); }
		public TerminalNode DOUBLE() { return getToken(StructuresSQLParser.DOUBLE, 0); }
		public TerminalNode BOOLEAN() { return getToken(StructuresSQLParser.BOOLEAN, 0); }
		public TerminalNode DATE() { return getToken(StructuresSQLParser.DATE, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(311);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 131941397451776L) != 0)) ) {
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
	public static class CommentContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(StructuresSQLParser.COMMENT, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitComment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			match(COMMENT);
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
		case 18:
			return whereClause_sempred((WhereClauseContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean whereClause_sempred(WhereClauseContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001C\u013c\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0001\u0000\u0005\u00006\b\u0000\n\u0000\f\u0000"+
		"9\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001F\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002M\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0005\u0002T\b\u0002\n\u0002\f\u0002W\t\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003"+
		"d\b\u0003\n\u0003\f\u0003g\t\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0005\u0004y\b\u0004\n\u0004\f\u0004|\t\u0004\u0001\u0004"+
		"\u0001\u0004\u0003\u0004\u0080\b\u0004\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0003\u0005\u008b\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u009b\b\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005"+
		"\b\u00a4\b\b\n\b\f\b\u00a7\t\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0003\t\u00c0\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0005"+
		"\n\u00c8\b\n\n\n\f\n\u00cb\t\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n"+
		"\u00d1\b\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00dc\b\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0005"+
		"\f\u00e7\b\f\n\f\f\f\u00ea\t\f\u0001\f\u0001\f\u0003\f\u00ee\b\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00f6\b\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0005\r\u00fd\b\r\n\r\f\r\u0100\t\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010"+
		"\u0114\b\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u011e\b\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0005\u0012"+
		"\u0126\b\u0012\n\u0012\f\u0012\u0129\t\u0012\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001"+
		"\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0000\u0001$\u001a\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e"+
		" \"$&(*,.02\u0000\u0006\u0002\u0000\u0001\u0001\u001b\u001b\u0002\u0000"+
		"\u0005\u0005@@\u0003\u0000::>>@A\u0003\u00000178;;\u0002\u00001599\u0005"+
		"\u0000\n\n\f\f\u000e\u000e\u0015\u0015+.\u0148\u00007\u0001\u0000\u0000"+
		"\u0000\u0002E\u0001\u0000\u0000\u0000\u0004G\u0001\u0000\u0000\u0000\u0006"+
		"[\u0001\u0000\u0000\u0000\bk\u0001\u0000\u0000\u0000\n\u008a\u0001\u0000"+
		"\u0000\u0000\f\u008c\u0001\u0000\u0000\u0000\u000e\u0095\u0001\u0000\u0000"+
		"\u0000\u0010\u009e\u0001\u0000\u0000\u0000\u0012\u00bf\u0001\u0000\u0000"+
		"\u0000\u0014\u00c1\u0001\u0000\u0000\u0000\u0016\u00d4\u0001\u0000\u0000"+
		"\u0000\u0018\u00df\u0001\u0000\u0000\u0000\u001a\u00f9\u0001\u0000\u0000"+
		"\u0000\u001c\u0101\u0001\u0000\u0000\u0000\u001e\u0103\u0001\u0000\u0000"+
		"\u0000 \u0113\u0001\u0000\u0000\u0000\"\u0115\u0001\u0000\u0000\u0000"+
		"$\u011d\u0001\u0000\u0000\u0000&\u012a\u0001\u0000\u0000\u0000(\u012e"+
		"\u0001\u0000\u0000\u0000*\u0130\u0001\u0000\u0000\u0000,\u0132\u0001\u0000"+
		"\u0000\u0000.\u0134\u0001\u0000\u0000\u00000\u0137\u0001\u0000\u0000\u0000"+
		"2\u0139\u0001\u0000\u0000\u000046\u0003\u0002\u0001\u000054\u0001\u0000"+
		"\u0000\u000069\u0001\u0000\u0000\u000075\u0001\u0000\u0000\u000078\u0001"+
		"\u0000\u0000\u00008:\u0001\u0000\u0000\u000097\u0001\u0000\u0000\u0000"+
		":;\u0005\u0000\u0000\u0001;\u0001\u0001\u0000\u0000\u0000<F\u0003\u0004"+
		"\u0002\u0000=F\u0003\u0006\u0003\u0000>F\u0003\b\u0004\u0000?F\u0003\f"+
		"\u0006\u0000@F\u0003\u000e\u0007\u0000AF\u0003\u0014\n\u0000BF\u0003\u0016"+
		"\u000b\u0000CF\u0003\u0018\f\u0000DF\u00032\u0019\u0000E<\u0001\u0000"+
		"\u0000\u0000E=\u0001\u0000\u0000\u0000E>\u0001\u0000\u0000\u0000E?\u0001"+
		"\u0000\u0000\u0000E@\u0001\u0000\u0000\u0000EA\u0001\u0000\u0000\u0000"+
		"EB\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000ED\u0001\u0000\u0000"+
		"\u0000F\u0003\u0001\u0000\u0000\u0000GH\u0005\t\u0000\u0000HL\u0005$\u0000"+
		"\u0000IJ\u0005\u0011\u0000\u0000JK\u0005\u0017\u0000\u0000KM\u0005\r\u0000"+
		"\u0000LI\u0001\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000MN\u0001\u0000"+
		"\u0000\u0000NO\u0005?\u0000\u0000OP\u00056\u0000\u0000PU\u0003.\u0017"+
		"\u0000QR\u0005/\u0000\u0000RT\u0003.\u0017\u0000SQ\u0001\u0000\u0000\u0000"+
		"TW\u0001\u0000\u0000\u0000US\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000"+
		"\u0000VX\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000XY\u0005<\u0000"+
		"\u0000YZ\u0005=\u0000\u0000Z\u0005\u0001\u0000\u0000\u0000[\\\u0005\t"+
		"\u0000\u0000\\]\u0005\u0007\u0000\u0000]^\u0005%\u0000\u0000^_\u0005?"+
		"\u0000\u0000_`\u00056\u0000\u0000`e\u0003\n\u0005\u0000ab\u0005/\u0000"+
		"\u0000bd\u0003\n\u0005\u0000ca\u0001\u0000\u0000\u0000dg\u0001\u0000\u0000"+
		"\u0000ec\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fh\u0001\u0000"+
		"\u0000\u0000ge\u0001\u0000\u0000\u0000hi\u0005<\u0000\u0000ij\u0005=\u0000"+
		"\u0000j\u0007\u0001\u0000\u0000\u0000kl\u0005\t\u0000\u0000lm\u0005\u0012"+
		"\u0000\u0000mn\u0005%\u0000\u0000no\u0005?\u0000\u0000op\u0005\u000f\u0000"+
		"\u0000pq\u0005A\u0000\u0000qr\u0005\'\u0000\u0000r\u007f\u0005A\u0000"+
		"\u0000st\u0005*\u0000\u0000tu\u00056\u0000\u0000uz\u0003\n\u0005\u0000"+
		"vw\u0005/\u0000\u0000wy\u0003\n\u0005\u0000xv\u0001\u0000\u0000\u0000"+
		"y|\u0001\u0000\u0000\u0000zx\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000"+
		"\u0000{}\u0001\u0000\u0000\u0000|z\u0001\u0000\u0000\u0000}~\u0005<\u0000"+
		"\u0000~\u0080\u0001\u0000\u0000\u0000\u007fs\u0001\u0000\u0000\u0000\u007f"+
		"\u0080\u0001\u0000\u0000\u0000\u0080\u0081\u0001\u0000\u0000\u0000\u0081"+
		"\u0082\u0005=\u0000\u0000\u0082\t\u0001\u0000\u0000\u0000\u0083\u0084"+
		"\u0005\u0019\u0000\u0000\u0084\u0085\u00051\u0000\u0000\u0085\u008b\u0005"+
		"@\u0000\u0000\u0086\u0087\u0005\u0018\u0000\u0000\u0087\u0088\u00051\u0000"+
		"\u0000\u0088\u008b\u0005@\u0000\u0000\u0089\u008b\u0003.\u0017\u0000\u008a"+
		"\u0083\u0001\u0000\u0000\u0000\u008a\u0086\u0001\u0000\u0000\u0000\u008a"+
		"\u0089\u0001\u0000\u0000\u0000\u008b\u000b\u0001\u0000\u0000\u0000\u008c"+
		"\u008d\u0005\u0003\u0000\u0000\u008d\u008e\u0005$\u0000\u0000\u008e\u008f"+
		"\u0005?\u0000\u0000\u008f\u0090\u0005\u0002\u0000\u0000\u0090\u0091\u0005"+
		"\u0006\u0000\u0000\u0091\u0092\u0005?\u0000\u0000\u0092\u0093\u00030\u0018"+
		"\u0000\u0093\u0094\u0005=\u0000\u0000\u0094\r\u0001\u0000\u0000\u0000"+
		"\u0095\u0096\u0005\u001e\u0000\u0000\u0096\u0097\u0005?\u0000\u0000\u0097"+
		"\u0098\u0005\u0014\u0000\u0000\u0098\u009a\u0005?\u0000\u0000\u0099\u009b"+
		"\u0003\u0010\b\u0000\u009a\u0099\u0001\u0000\u0000\u0000\u009a\u009b\u0001"+
		"\u0000\u0000\u0000\u009b\u009c\u0001\u0000\u0000\u0000\u009c\u009d\u0005"+
		"=\u0000\u0000\u009d\u000f\u0001\u0000\u0000\u0000\u009e\u009f\u0005*\u0000"+
		"\u0000\u009f\u00a0\u00056\u0000\u0000\u00a0\u00a5\u0003\u0012\t\u0000"+
		"\u00a1\u00a2\u0005/\u0000\u0000\u00a2\u00a4\u0003\u0012\t\u0000\u00a3"+
		"\u00a1\u0001\u0000\u0000\u0000\u00a4\u00a7\u0001\u0000\u0000\u0000\u00a5"+
		"\u00a3\u0001\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000\u0000\u00a6"+
		"\u00a8\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000\u00a8"+
		"\u00a9\u0005<\u0000\u0000\u00a9\u0011\u0001\u0000\u0000\u0000\u00aa\u00ab"+
		"\u0005\b\u0000\u0000\u00ab\u00ac\u00051\u0000\u0000\u00ac\u00c0\u0007"+
		"\u0000\u0000\u0000\u00ad\u00ae\u0005\u0016\u0000\u0000\u00ae\u00af\u0005"+
		"1\u0000\u0000\u00af\u00c0\u0005@\u0000\u0000\u00b0\u00b1\u0005\"\u0000"+
		"\u0000\u00b1\u00b2\u00051\u0000\u0000\u00b2\u00c0\u0007\u0001\u0000\u0000"+
		"\u00b3\u00b4\u0005!\u0000\u0000\u00b4\u00b5\u00051\u0000\u0000\u00b5\u00c0"+
		"\u0005@\u0000\u0000\u00b6\u00b7\u0005#\u0000\u0000\u00b7\u00b8\u00051"+
		"\u0000\u0000\u00b8\u00c0\u0005A\u0000\u0000\u00b9\u00ba\u0005\u001c\u0000"+
		"\u0000\u00ba\u00bb\u00051\u0000\u0000\u00bb\u00c0\u0005A\u0000\u0000\u00bc"+
		"\u00bd\u0005\u001f\u0000\u0000\u00bd\u00be\u00051\u0000\u0000\u00be\u00c0"+
		"\u0005A\u0000\u0000\u00bf\u00aa\u0001\u0000\u0000\u0000\u00bf\u00ad\u0001"+
		"\u0000\u0000\u0000\u00bf\u00b0\u0001\u0000\u0000\u0000\u00bf\u00b3\u0001"+
		"\u0000\u0000\u0000\u00bf\u00b6\u0001\u0000\u0000\u0000\u00bf\u00b9\u0001"+
		"\u0000\u0000\u0000\u00bf\u00bc\u0001\u0000\u0000\u0000\u00c0\u0013\u0001"+
		"\u0000\u0000\u0000\u00c1\u00c2\u0005&\u0000\u0000\u00c2\u00c3\u0005?\u0000"+
		"\u0000\u00c3\u00c4\u0005 \u0000\u0000\u00c4\u00c9\u0003\u001e\u000f\u0000"+
		"\u00c5\u00c6\u0005/\u0000\u0000\u00c6\u00c8\u0003\u001e\u000f\u0000\u00c7"+
		"\u00c5\u0001\u0000\u0000\u0000\u00c8\u00cb\u0001\u0000\u0000\u0000\u00c9"+
		"\u00c7\u0001\u0000\u0000\u0000\u00c9\u00ca\u0001\u0000\u0000\u0000\u00ca"+
		"\u00cc\u0001\u0000\u0000\u0000\u00cb\u00c9\u0001\u0000\u0000\u0000\u00cc"+
		"\u00cd\u0005)\u0000\u0000\u00cd\u00d0\u0003$\u0012\u0000\u00ce\u00cf\u0005"+
		"*\u0000\u0000\u00cf\u00d1\u0005\u001d\u0000\u0000\u00d0\u00ce\u0001\u0000"+
		"\u0000\u0000\u00d0\u00d1\u0001\u0000\u0000\u0000\u00d1\u00d2\u0001\u0000"+
		"\u0000\u0000\u00d2\u00d3\u0005=\u0000\u0000\u00d3\u0015\u0001\u0000\u0000"+
		"\u0000\u00d4\u00d5\u0005\u000b\u0000\u0000\u00d5\u00d6\u0005\u0010\u0000"+
		"\u0000\u00d6\u00d7\u0005?\u0000\u0000\u00d7\u00d8\u0005)\u0000\u0000\u00d8"+
		"\u00db\u0003$\u0012\u0000\u00d9\u00da\u0005*\u0000\u0000\u00da\u00dc\u0005"+
		"\u001d\u0000\u0000\u00db\u00d9\u0001\u0000\u0000\u0000\u00db\u00dc\u0001"+
		"\u0000\u0000\u0000\u00dc\u00dd\u0001\u0000\u0000\u0000\u00dd\u00de\u0005"+
		"=\u0000\u0000\u00de\u0017\u0001\u0000\u0000\u0000\u00df\u00e0\u0005\u0013"+
		"\u0000\u0000\u00e0\u00e1\u0005\u0014\u0000\u0000\u00e1\u00ed\u0003*\u0015"+
		"\u0000\u00e2\u00e3\u00056\u0000\u0000\u00e3\u00e8\u0003,\u0016\u0000\u00e4"+
		"\u00e5\u0005/\u0000\u0000\u00e5\u00e7\u0003,\u0016\u0000\u00e6\u00e4\u0001"+
		"\u0000\u0000\u0000\u00e7\u00ea\u0001\u0000\u0000\u0000\u00e8\u00e6\u0001"+
		"\u0000\u0000\u0000\u00e8\u00e9\u0001\u0000\u0000\u0000\u00e9\u00eb\u0001"+
		"\u0000\u0000\u0000\u00ea\u00e8\u0001\u0000\u0000\u0000\u00eb\u00ec\u0005"+
		"<\u0000\u0000\u00ec\u00ee\u0001\u0000\u0000\u0000\u00ed\u00e2\u0001\u0000"+
		"\u0000\u0000\u00ed\u00ee\u0001\u0000\u0000\u0000\u00ee\u00ef\u0001\u0000"+
		"\u0000\u0000\u00ef\u00f0\u0005(\u0000\u0000\u00f0\u00f1\u00056\u0000\u0000"+
		"\u00f1\u00f2\u0003\u001a\r\u0000\u00f2\u00f5\u0005<\u0000\u0000\u00f3"+
		"\u00f4\u0005*\u0000\u0000\u00f4\u00f6\u0005\u001d\u0000\u0000\u00f5\u00f3"+
		"\u0001\u0000\u0000\u0000\u00f5\u00f6\u0001\u0000\u0000\u0000\u00f6\u00f7"+
		"\u0001\u0000\u0000\u0000\u00f7\u00f8\u0005=\u0000\u0000\u00f8\u0019\u0001"+
		"\u0000\u0000\u0000\u00f9\u00fe\u0003\u001c\u000e\u0000\u00fa\u00fb\u0005"+
		"/\u0000\u0000\u00fb\u00fd\u0003\u001c\u000e\u0000\u00fc\u00fa\u0001\u0000"+
		"\u0000\u0000\u00fd\u0100\u0001\u0000\u0000\u0000\u00fe\u00fc\u0001\u0000"+
		"\u0000\u0000\u00fe\u00ff\u0001\u0000\u0000\u0000\u00ff\u001b\u0001\u0000"+
		"\u0000\u0000\u0100\u00fe\u0001\u0000\u0000\u0000\u0101\u0102\u0007\u0002"+
		"\u0000\u0000\u0102\u001d\u0001\u0000\u0000\u0000\u0103\u0104\u0005?\u0000"+
		"\u0000\u0104\u0105\u00051\u0000\u0000\u0105\u0106\u0003 \u0010\u0000\u0106"+
		"\u001f\u0001\u0000\u0000\u0000\u0107\u0114\u0005:\u0000\u0000\u0108\u0114"+
		"\u0005A\u0000\u0000\u0109\u0114\u0005@\u0000\u0000\u010a\u0114\u0005>"+
		"\u0000\u0000\u010b\u010c\u0005?\u0000\u0000\u010c\u010d\u0003\"\u0011"+
		"\u0000\u010d\u010e\u0003 \u0010\u0000\u010e\u0114\u0001\u0000\u0000\u0000"+
		"\u010f\u0110\u00056\u0000\u0000\u0110\u0111\u0003 \u0010\u0000\u0111\u0112"+
		"\u0005<\u0000\u0000\u0112\u0114\u0001\u0000\u0000\u0000\u0113\u0107\u0001"+
		"\u0000\u0000\u0000\u0113\u0108\u0001\u0000\u0000\u0000\u0113\u0109\u0001"+
		"\u0000\u0000\u0000\u0113\u010a\u0001\u0000\u0000\u0000\u0113\u010b\u0001"+
		"\u0000\u0000\u0000\u0113\u010f\u0001\u0000\u0000\u0000\u0114!\u0001\u0000"+
		"\u0000\u0000\u0115\u0116\u0007\u0003\u0000\u0000\u0116#\u0001\u0000\u0000"+
		"\u0000\u0117\u0118\u0006\u0012\uffff\uffff\u0000\u0118\u011e\u0003&\u0013"+
		"\u0000\u0119\u011a\u00056\u0000\u0000\u011a\u011b\u0003$\u0012\u0000\u011b"+
		"\u011c\u0005<\u0000\u0000\u011c\u011e\u0001\u0000\u0000\u0000\u011d\u0117"+
		"\u0001\u0000\u0000\u0000\u011d\u0119\u0001\u0000\u0000\u0000\u011e\u0127"+
		"\u0001\u0000\u0000\u0000\u011f\u0120\n\u0002\u0000\u0000\u0120\u0121\u0005"+
		"\u0004\u0000\u0000\u0121\u0126\u0003$\u0012\u0003\u0122\u0123\n\u0001"+
		"\u0000\u0000\u0123\u0124\u0005\u001a\u0000\u0000\u0124\u0126\u0003$\u0012"+
		"\u0002\u0125\u011f\u0001\u0000\u0000\u0000\u0125\u0122\u0001\u0000\u0000"+
		"\u0000\u0126\u0129\u0001\u0000\u0000\u0000\u0127\u0125\u0001\u0000\u0000"+
		"\u0000\u0127\u0128\u0001\u0000\u0000\u0000\u0128%\u0001\u0000\u0000\u0000"+
		"\u0129\u0127\u0001\u0000\u0000\u0000\u012a\u012b\u0005?\u0000\u0000\u012b"+
		"\u012c\u0003(\u0014\u0000\u012c\u012d\u0007\u0002\u0000\u0000\u012d\'"+
		"\u0001\u0000\u0000\u0000\u012e\u012f\u0007\u0004\u0000\u0000\u012f)\u0001"+
		"\u0000\u0000\u0000\u0130\u0131\u0005?\u0000\u0000\u0131+\u0001\u0000\u0000"+
		"\u0000\u0132\u0133\u0005?\u0000\u0000\u0133-\u0001\u0000\u0000\u0000\u0134"+
		"\u0135\u0005?\u0000\u0000\u0135\u0136\u00030\u0018\u0000\u0136/\u0001"+
		"\u0000\u0000\u0000\u0137\u0138\u0007\u0005\u0000\u0000\u01381\u0001\u0000"+
		"\u0000\u0000\u0139\u013a\u0005B\u0000\u0000\u013a3\u0001\u0000\u0000\u0000"+
		"\u00167ELUez\u007f\u008a\u009a\u00a5\u00bf\u00c9\u00d0\u00db\u00e8\u00ed"+
		"\u00f5\u00fe\u0113\u011d\u0125\u0127";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}