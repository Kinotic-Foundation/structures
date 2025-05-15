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
		CREATE=1, TABLE=2, COMPONENT=3, INDEX=4, TEMPLATE=5, FOR=6, USING=7, ALTER=8, 
		ADD=9, COLUMN=10, REINDEX=11, INTO=12, WITH=13, CONFLICTS=14, ABORT=15, 
		PROCEED=16, MAX_DOCS=17, SLICES=18, AUTO=19, SIZE=20, SOURCE_FIELDS=21, 
		QUERY=22, SCRIPT=23, NUMBER_OF_SHARDS=24, NUMBER_OF_REPLICAS=25, UPDATE=26, 
		DELETE=27, FROM=28, SET=29, WHERE=30, AND=31, OR=32, TEXT=33, KEYWORD=34, 
		INTEGER=35, BOOLEAN=36, DATE=37, LPAREN=38, RPAREN=39, COMMA=40, SEMICOLON=41, 
		EQUALS=42, NOT_EQUALS=43, LESS_THAN=44, GREATER_THAN=45, LESS_THAN_EQUALS=46, 
		GREATER_THAN_EQUALS=47, PLUS=48, MINUS=49, MULTIPLY=50, DIVIDE=51, PARAMETER=52, 
		STRING=53, ID=54, INTEGER_LITERAL=55, BOOLEAN_LITERAL=56, COMMENT=57, 
		WS=58, INSERT=59, VALUES=60;
	public static final int
		RULE_migrations = 0, RULE_statement = 1, RULE_createTableStatement = 2, 
		RULE_createComponentTemplateStatement = 3, RULE_createIndexTemplateStatement = 4, 
		RULE_componentDefinition = 5, RULE_alterTableStatement = 6, RULE_reindexStatement = 7, 
		RULE_reindexOptions = 8, RULE_reindexOption = 9, RULE_updateStatement = 10, 
		RULE_deleteStatement = 11, RULE_insertStatement = 12, RULE_assignment = 13, 
		RULE_expression = 14, RULE_operator = 15, RULE_whereClause = 16, RULE_condition = 17, 
		RULE_comparisonOperator = 18, RULE_columnDefinition = 19, RULE_type = 20, 
		RULE_comment = 21, RULE_tableName = 22, RULE_columnName = 23;
	private static String[] makeRuleNames() {
		return new String[] {
			"migrations", "statement", "createTableStatement", "createComponentTemplateStatement", 
			"createIndexTemplateStatement", "componentDefinition", "alterTableStatement", 
			"reindexStatement", "reindexOptions", "reindexOption", "updateStatement", 
			"deleteStatement", "insertStatement", "assignment", "expression", "operator", 
			"whereClause", "condition", "comparisonOperator", "columnDefinition", 
			"type", "comment", "tableName", "columnName"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'CREATE'", "'TABLE'", "'COMPONENT'", "'INDEX'", "'TEMPLATE'", 
			"'FOR'", "'USING'", "'ALTER'", "'ADD'", "'COLUMN'", "'REINDEX'", "'INTO'", 
			"'WITH'", "'CONFLICTS'", "'ABORT'", "'PROCEED'", "'MAX_DOCS'", "'SLICES'", 
			"'AUTO'", "'SIZE'", "'SOURCE_FIELDS'", "'QUERY'", "'SCRIPT'", "'NUMBER_OF_SHARDS'", 
			"'NUMBER_OF_REPLICAS'", "'UPDATE'", "'DELETE'", "'FROM'", "'SET'", "'WHERE'", 
			"'AND'", "'OR'", "'TEXT'", "'KEYWORD'", "'INTEGER'", "'BOOLEAN'", "'DATE'", 
			"'('", "')'", "','", "';'", "'=='", "'!='", "'<'", "'>'", "'<='", "'>='", 
			"'+'", "'-'", "'*'", "'/'", "'?'", null, null, null, null, null, null, 
			"'INSERT'", "'VALUES'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "CREATE", "TABLE", "COMPONENT", "INDEX", "TEMPLATE", "FOR", "USING", 
			"ALTER", "ADD", "COLUMN", "REINDEX", "INTO", "WITH", "CONFLICTS", "ABORT", 
			"PROCEED", "MAX_DOCS", "SLICES", "AUTO", "SIZE", "SOURCE_FIELDS", "QUERY", 
			"SCRIPT", "NUMBER_OF_SHARDS", "NUMBER_OF_REPLICAS", "UPDATE", "DELETE", 
			"FROM", "SET", "WHERE", "AND", "OR", "TEXT", "KEYWORD", "INTEGER", "BOOLEAN", 
			"DATE", "LPAREN", "RPAREN", "COMMA", "SEMICOLON", "EQUALS", "NOT_EQUALS", 
			"LESS_THAN", "GREATER_THAN", "LESS_THAN_EQUALS", "GREATER_THAN_EQUALS", 
			"PLUS", "MINUS", "MULTIPLY", "DIVIDE", "PARAMETER", "STRING", "ID", "INTEGER_LITERAL", 
			"BOOLEAN_LITERAL", "COMMENT", "WS", "INSERT", "VALUES"
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
			setState(49); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(48);
				statement();
				}
				}
				setState(51); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 720575940580608258L) != 0) );
			setState(53);
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
			setState(64);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(55);
				createTableStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(56);
				createComponentTemplateStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(57);
				createIndexTemplateStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(58);
				alterTableStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(59);
				reindexStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(60);
				updateStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(61);
				deleteStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(62);
				insertStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(63);
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
			setState(66);
			match(CREATE);
			setState(67);
			match(TABLE);
			setState(68);
			match(ID);
			setState(69);
			match(LPAREN);
			setState(70);
			columnDefinition();
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(71);
				match(COMMA);
				setState(72);
				columnDefinition();
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(78);
			match(RPAREN);
			setState(79);
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
		public List<ComponentDefinitionContext> componentDefinition() {
			return getRuleContexts(ComponentDefinitionContext.class);
		}
		public ComponentDefinitionContext componentDefinition(int i) {
			return getRuleContext(ComponentDefinitionContext.class,i);
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
			setState(81);
			match(CREATE);
			setState(82);
			match(COMPONENT);
			setState(83);
			match(TEMPLATE);
			setState(84);
			match(ID);
			setState(85);
			match(LPAREN);
			setState(86);
			componentDefinition();
			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(87);
				match(COMMA);
				setState(88);
				componentDefinition();
				}
				}
				setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(94);
			match(RPAREN);
			setState(95);
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
		public List<ComponentDefinitionContext> componentDefinition() {
			return getRuleContexts(ComponentDefinitionContext.class);
		}
		public ComponentDefinitionContext componentDefinition(int i) {
			return getRuleContext(ComponentDefinitionContext.class,i);
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
			setState(97);
			match(CREATE);
			setState(98);
			match(INDEX);
			setState(99);
			match(TEMPLATE);
			setState(100);
			match(ID);
			setState(101);
			match(FOR);
			setState(102);
			match(STRING);
			setState(103);
			match(USING);
			setState(104);
			match(STRING);
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(105);
				match(WITH);
				setState(106);
				match(LPAREN);
				setState(107);
				componentDefinition();
				setState(112);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(108);
					match(COMMA);
					setState(109);
					componentDefinition();
					}
					}
					setState(114);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(115);
				match(RPAREN);
				}
			}

			setState(119);
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
	public static class ComponentDefinitionContext extends ParserRuleContext {
		public TerminalNode NUMBER_OF_SHARDS() { return getToken(StructuresSQLParser.NUMBER_OF_SHARDS, 0); }
		public TerminalNode EQUALS() { return getToken(StructuresSQLParser.EQUALS, 0); }
		public TerminalNode INTEGER_LITERAL() { return getToken(StructuresSQLParser.INTEGER_LITERAL, 0); }
		public TerminalNode NUMBER_OF_REPLICAS() { return getToken(StructuresSQLParser.NUMBER_OF_REPLICAS, 0); }
		public ColumnDefinitionContext columnDefinition() {
			return getRuleContext(ColumnDefinitionContext.class,0);
		}
		public ComponentDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_componentDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterComponentDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitComponentDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitComponentDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComponentDefinitionContext componentDefinition() throws RecognitionException {
		ComponentDefinitionContext _localctx = new ComponentDefinitionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_componentDefinition);
		try {
			setState(128);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMBER_OF_SHARDS:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				match(NUMBER_OF_SHARDS);
				setState(122);
				match(EQUALS);
				setState(123);
				match(INTEGER_LITERAL);
				}
				break;
			case NUMBER_OF_REPLICAS:
				enterOuterAlt(_localctx, 2);
				{
				setState(124);
				match(NUMBER_OF_REPLICAS);
				setState(125);
				match(EQUALS);
				setState(126);
				match(INTEGER_LITERAL);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(127);
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
			setState(130);
			match(ALTER);
			setState(131);
			match(TABLE);
			setState(132);
			match(ID);
			setState(133);
			match(ADD);
			setState(134);
			match(COLUMN);
			setState(135);
			match(ID);
			setState(136);
			type();
			setState(137);
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
			setState(139);
			match(REINDEX);
			setState(140);
			match(ID);
			setState(141);
			match(INTO);
			setState(142);
			match(ID);
			setState(144);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(143);
				reindexOptions();
				}
			}

			setState(146);
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
			setState(148);
			match(WITH);
			setState(149);
			match(LPAREN);
			setState(150);
			reindexOption();
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(151);
				match(COMMA);
				setState(152);
				reindexOption();
				}
				}
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(158);
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
			setState(181);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONFLICTS:
				enterOuterAlt(_localctx, 1);
				{
				setState(160);
				match(CONFLICTS);
				setState(161);
				match(EQUALS);
				setState(162);
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
				setState(163);
				match(MAX_DOCS);
				setState(164);
				match(EQUALS);
				setState(165);
				match(INTEGER_LITERAL);
				}
				break;
			case SLICES:
				enterOuterAlt(_localctx, 3);
				{
				setState(166);
				match(SLICES);
				setState(167);
				match(EQUALS);
				setState(168);
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
				setState(169);
				match(SIZE);
				setState(170);
				match(EQUALS);
				setState(171);
				match(INTEGER_LITERAL);
				}
				break;
			case SOURCE_FIELDS:
				enterOuterAlt(_localctx, 5);
				{
				setState(172);
				match(SOURCE_FIELDS);
				setState(173);
				match(EQUALS);
				setState(174);
				match(STRING);
				}
				break;
			case QUERY:
				enterOuterAlt(_localctx, 6);
				{
				setState(175);
				match(QUERY);
				setState(176);
				match(EQUALS);
				setState(177);
				match(STRING);
				}
				break;
			case SCRIPT:
				enterOuterAlt(_localctx, 7);
				{
				setState(178);
				match(SCRIPT);
				setState(179);
				match(EQUALS);
				setState(180);
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
			setState(183);
			match(UPDATE);
			setState(184);
			match(ID);
			setState(185);
			match(SET);
			setState(186);
			assignment();
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(187);
				match(COMMA);
				setState(188);
				assignment();
				}
				}
				setState(193);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(194);
			match(WHERE);
			setState(195);
			whereClause(0);
			setState(196);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(DELETE);
			setState(199);
			match(FROM);
			setState(200);
			match(ID);
			setState(201);
			match(WHERE);
			setState(202);
			whereClause(0);
			setState(203);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
			setState(205);
			match(INSERT);
			setState(206);
			match(INTO);
			setState(207);
			tableName();
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(208);
				match(LPAREN);
				setState(209);
				columnName();
				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(210);
					match(COMMA);
					setState(211);
					columnName();
					}
					}
					setState(216);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(217);
				match(RPAREN);
				}
			}

			setState(221);
			match(VALUES);
			setState(222);
			match(LPAREN);
			setState(223);
			expression();
			setState(228);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(224);
				match(COMMA);
				setState(225);
				expression();
				}
				}
				setState(230);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(231);
			match(RPAREN);
			setState(232);
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
		enterRule(_localctx, 26, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
			match(ID);
			setState(235);
			match(EQUALS);
			setState(236);
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
		enterRule(_localctx, 28, RULE_expression);
		try {
			setState(250);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PARAMETER:
				enterOuterAlt(_localctx, 1);
				{
				setState(238);
				match(PARAMETER);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(239);
				match(STRING);
				}
				break;
			case INTEGER_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(240);
				match(INTEGER_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(241);
				match(BOOLEAN_LITERAL);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 5);
				{
				setState(242);
				match(ID);
				setState(243);
				operator();
				setState(244);
				expression();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				setState(246);
				match(LPAREN);
				setState(247);
				expression();
				setState(248);
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
		enterRule(_localctx, 30, RULE_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4226522697170944L) != 0)) ) {
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
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_whereClause, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(255);
				condition();
				}
				break;
			case LPAREN:
				{
				setState(256);
				match(LPAREN);
				setState(257);
				whereClause(0);
				setState(258);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(270);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(268);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
					case 1:
						{
						_localctx = new WhereClauseContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_whereClause);
						setState(262);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(263);
						match(AND);
						setState(264);
						whereClause(3);
						}
						break;
					case 2:
						{
						_localctx = new WhereClauseContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_whereClause);
						setState(265);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(266);
						match(OR);
						setState(267);
						whereClause(2);
						}
						break;
					}
					} 
				}
				setState(272);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
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
		enterRule(_localctx, 34, RULE_condition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			match(ID);
			setState(274);
			comparisonOperator();
			setState(275);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 121597189939003392L) != 0)) ) {
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
		enterRule(_localctx, 36, RULE_comparisonOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 277076930199552L) != 0)) ) {
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
		enterRule(_localctx, 38, RULE_columnDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(279);
			match(ID);
			setState(280);
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
		enterRule(_localctx, 40, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 266287972352L) != 0)) ) {
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
		enterRule(_localctx, 42, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
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
		enterRule(_localctx, 44, RULE_tableName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
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
		enterRule(_localctx, 46, RULE_columnName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 16:
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
		"\u0004\u0001<\u0123\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0001\u0000\u0004\u0000"+
		"2\b\u0000\u000b\u0000\f\u00003\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0003\u0001A\b\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002J\b"+
		"\u0002\n\u0002\f\u0002M\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0005\u0003Z\b\u0003\n\u0003\f\u0003]\t\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004o\b\u0004\n\u0004"+
		"\f\u0004r\t\u0004\u0001\u0004\u0001\u0004\u0003\u0004v\b\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0003\u0005\u0081\b\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0003\u0007\u0091\b\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0005\b\u009a\b\b\n\b\f\b\u009d\t\b\u0001\b\u0001\b"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0003\t\u00b6\b\t\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0005\n\u00be\b\n\n\n\f\n\u00c1\t\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0005\f\u00d5\b\f\n\f\f\f\u00d8\t\f\u0001\f\u0001\f\u0003"+
		"\f\u00dc\b\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0005\f\u00e3\b\f"+
		"\n\f\f\f\u00e6\t\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0003\u000e\u00fb\b\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u0105\b\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0005\u0010\u010d\b\u0010\n\u0010\f\u0010\u0110\t\u0010\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0000\u0001 \u0018"+
		"\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a"+
		"\u001c\u001e \"$&(*,.\u0000\u0006\u0001\u0000\u000f\u0010\u0002\u0000"+
		"\u0013\u001377\u0002\u0000**03\u0002\u00004578\u0001\u0000*/\u0001\u0000"+
		"!%\u012d\u00001\u0001\u0000\u0000\u0000\u0002@\u0001\u0000\u0000\u0000"+
		"\u0004B\u0001\u0000\u0000\u0000\u0006Q\u0001\u0000\u0000\u0000\ba\u0001"+
		"\u0000\u0000\u0000\n\u0080\u0001\u0000\u0000\u0000\f\u0082\u0001\u0000"+
		"\u0000\u0000\u000e\u008b\u0001\u0000\u0000\u0000\u0010\u0094\u0001\u0000"+
		"\u0000\u0000\u0012\u00b5\u0001\u0000\u0000\u0000\u0014\u00b7\u0001\u0000"+
		"\u0000\u0000\u0016\u00c6\u0001\u0000\u0000\u0000\u0018\u00cd\u0001\u0000"+
		"\u0000\u0000\u001a\u00ea\u0001\u0000\u0000\u0000\u001c\u00fa\u0001\u0000"+
		"\u0000\u0000\u001e\u00fc\u0001\u0000\u0000\u0000 \u0104\u0001\u0000\u0000"+
		"\u0000\"\u0111\u0001\u0000\u0000\u0000$\u0115\u0001\u0000\u0000\u0000"+
		"&\u0117\u0001\u0000\u0000\u0000(\u011a\u0001\u0000\u0000\u0000*\u011c"+
		"\u0001\u0000\u0000\u0000,\u011e\u0001\u0000\u0000\u0000.\u0120\u0001\u0000"+
		"\u0000\u000002\u0003\u0002\u0001\u000010\u0001\u0000\u0000\u000023\u0001"+
		"\u0000\u0000\u000031\u0001\u0000\u0000\u000034\u0001\u0000\u0000\u0000"+
		"45\u0001\u0000\u0000\u000056\u0005\u0000\u0000\u00016\u0001\u0001\u0000"+
		"\u0000\u00007A\u0003\u0004\u0002\u00008A\u0003\u0006\u0003\u00009A\u0003"+
		"\b\u0004\u0000:A\u0003\f\u0006\u0000;A\u0003\u000e\u0007\u0000<A\u0003"+
		"\u0014\n\u0000=A\u0003\u0016\u000b\u0000>A\u0003\u0018\f\u0000?A\u0003"+
		"*\u0015\u0000@7\u0001\u0000\u0000\u0000@8\u0001\u0000\u0000\u0000@9\u0001"+
		"\u0000\u0000\u0000@:\u0001\u0000\u0000\u0000@;\u0001\u0000\u0000\u0000"+
		"@<\u0001\u0000\u0000\u0000@=\u0001\u0000\u0000\u0000@>\u0001\u0000\u0000"+
		"\u0000@?\u0001\u0000\u0000\u0000A\u0003\u0001\u0000\u0000\u0000BC\u0005"+
		"\u0001\u0000\u0000CD\u0005\u0002\u0000\u0000DE\u00056\u0000\u0000EF\u0005"+
		"&\u0000\u0000FK\u0003&\u0013\u0000GH\u0005(\u0000\u0000HJ\u0003&\u0013"+
		"\u0000IG\u0001\u0000\u0000\u0000JM\u0001\u0000\u0000\u0000KI\u0001\u0000"+
		"\u0000\u0000KL\u0001\u0000\u0000\u0000LN\u0001\u0000\u0000\u0000MK\u0001"+
		"\u0000\u0000\u0000NO\u0005\'\u0000\u0000OP\u0005)\u0000\u0000P\u0005\u0001"+
		"\u0000\u0000\u0000QR\u0005\u0001\u0000\u0000RS\u0005\u0003\u0000\u0000"+
		"ST\u0005\u0005\u0000\u0000TU\u00056\u0000\u0000UV\u0005&\u0000\u0000V"+
		"[\u0003\n\u0005\u0000WX\u0005(\u0000\u0000XZ\u0003\n\u0005\u0000YW\u0001"+
		"\u0000\u0000\u0000Z]\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000"+
		"[\\\u0001\u0000\u0000\u0000\\^\u0001\u0000\u0000\u0000][\u0001\u0000\u0000"+
		"\u0000^_\u0005\'\u0000\u0000_`\u0005)\u0000\u0000`\u0007\u0001\u0000\u0000"+
		"\u0000ab\u0005\u0001\u0000\u0000bc\u0005\u0004\u0000\u0000cd\u0005\u0005"+
		"\u0000\u0000de\u00056\u0000\u0000ef\u0005\u0006\u0000\u0000fg\u00055\u0000"+
		"\u0000gh\u0005\u0007\u0000\u0000hu\u00055\u0000\u0000ij\u0005\r\u0000"+
		"\u0000jk\u0005&\u0000\u0000kp\u0003\n\u0005\u0000lm\u0005(\u0000\u0000"+
		"mo\u0003\n\u0005\u0000nl\u0001\u0000\u0000\u0000or\u0001\u0000\u0000\u0000"+
		"pn\u0001\u0000\u0000\u0000pq\u0001\u0000\u0000\u0000qs\u0001\u0000\u0000"+
		"\u0000rp\u0001\u0000\u0000\u0000st\u0005\'\u0000\u0000tv\u0001\u0000\u0000"+
		"\u0000ui\u0001\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000vw\u0001\u0000"+
		"\u0000\u0000wx\u0005)\u0000\u0000x\t\u0001\u0000\u0000\u0000yz\u0005\u0018"+
		"\u0000\u0000z{\u0005*\u0000\u0000{\u0081\u00057\u0000\u0000|}\u0005\u0019"+
		"\u0000\u0000}~\u0005*\u0000\u0000~\u0081\u00057\u0000\u0000\u007f\u0081"+
		"\u0003&\u0013\u0000\u0080y\u0001\u0000\u0000\u0000\u0080|\u0001\u0000"+
		"\u0000\u0000\u0080\u007f\u0001\u0000\u0000\u0000\u0081\u000b\u0001\u0000"+
		"\u0000\u0000\u0082\u0083\u0005\b\u0000\u0000\u0083\u0084\u0005\u0002\u0000"+
		"\u0000\u0084\u0085\u00056\u0000\u0000\u0085\u0086\u0005\t\u0000\u0000"+
		"\u0086\u0087\u0005\n\u0000\u0000\u0087\u0088\u00056\u0000\u0000\u0088"+
		"\u0089\u0003(\u0014\u0000\u0089\u008a\u0005)\u0000\u0000\u008a\r\u0001"+
		"\u0000\u0000\u0000\u008b\u008c\u0005\u000b\u0000\u0000\u008c\u008d\u0005"+
		"6\u0000\u0000\u008d\u008e\u0005\f\u0000\u0000\u008e\u0090\u00056\u0000"+
		"\u0000\u008f\u0091\u0003\u0010\b\u0000\u0090\u008f\u0001\u0000\u0000\u0000"+
		"\u0090\u0091\u0001\u0000\u0000\u0000\u0091\u0092\u0001\u0000\u0000\u0000"+
		"\u0092\u0093\u0005)\u0000\u0000\u0093\u000f\u0001\u0000\u0000\u0000\u0094"+
		"\u0095\u0005\r\u0000\u0000\u0095\u0096\u0005&\u0000\u0000\u0096\u009b"+
		"\u0003\u0012\t\u0000\u0097\u0098\u0005(\u0000\u0000\u0098\u009a\u0003"+
		"\u0012\t\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u009a\u009d\u0001\u0000"+
		"\u0000\u0000\u009b\u0099\u0001\u0000\u0000\u0000\u009b\u009c\u0001\u0000"+
		"\u0000\u0000\u009c\u009e\u0001\u0000\u0000\u0000\u009d\u009b\u0001\u0000"+
		"\u0000\u0000\u009e\u009f\u0005\'\u0000\u0000\u009f\u0011\u0001\u0000\u0000"+
		"\u0000\u00a0\u00a1\u0005\u000e\u0000\u0000\u00a1\u00a2\u0005*\u0000\u0000"+
		"\u00a2\u00b6\u0007\u0000\u0000\u0000\u00a3\u00a4\u0005\u0011\u0000\u0000"+
		"\u00a4\u00a5\u0005*\u0000\u0000\u00a5\u00b6\u00057\u0000\u0000\u00a6\u00a7"+
		"\u0005\u0012\u0000\u0000\u00a7\u00a8\u0005*\u0000\u0000\u00a8\u00b6\u0007"+
		"\u0001\u0000\u0000\u00a9\u00aa\u0005\u0014\u0000\u0000\u00aa\u00ab\u0005"+
		"*\u0000\u0000\u00ab\u00b6\u00057\u0000\u0000\u00ac\u00ad\u0005\u0015\u0000"+
		"\u0000\u00ad\u00ae\u0005*\u0000\u0000\u00ae\u00b6\u00055\u0000\u0000\u00af"+
		"\u00b0\u0005\u0016\u0000\u0000\u00b0\u00b1\u0005*\u0000\u0000\u00b1\u00b6"+
		"\u00055\u0000\u0000\u00b2\u00b3\u0005\u0017\u0000\u0000\u00b3\u00b4\u0005"+
		"*\u0000\u0000\u00b4\u00b6\u00055\u0000\u0000\u00b5\u00a0\u0001\u0000\u0000"+
		"\u0000\u00b5\u00a3\u0001\u0000\u0000\u0000\u00b5\u00a6\u0001\u0000\u0000"+
		"\u0000\u00b5\u00a9\u0001\u0000\u0000\u0000\u00b5\u00ac\u0001\u0000\u0000"+
		"\u0000\u00b5\u00af\u0001\u0000\u0000\u0000\u00b5\u00b2\u0001\u0000\u0000"+
		"\u0000\u00b6\u0013\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005\u001a\u0000"+
		"\u0000\u00b8\u00b9\u00056\u0000\u0000\u00b9\u00ba\u0005\u001d\u0000\u0000"+
		"\u00ba\u00bf\u0003\u001a\r\u0000\u00bb\u00bc\u0005(\u0000\u0000\u00bc"+
		"\u00be\u0003\u001a\r\u0000\u00bd\u00bb\u0001\u0000\u0000\u0000\u00be\u00c1"+
		"\u0001\u0000\u0000\u0000\u00bf\u00bd\u0001\u0000\u0000\u0000\u00bf\u00c0"+
		"\u0001\u0000\u0000\u0000\u00c0\u00c2\u0001\u0000\u0000\u0000\u00c1\u00bf"+
		"\u0001\u0000\u0000\u0000\u00c2\u00c3\u0005\u001e\u0000\u0000\u00c3\u00c4"+
		"\u0003 \u0010\u0000\u00c4\u00c5\u0005)\u0000\u0000\u00c5\u0015\u0001\u0000"+
		"\u0000\u0000\u00c6\u00c7\u0005\u001b\u0000\u0000\u00c7\u00c8\u0005\u001c"+
		"\u0000\u0000\u00c8\u00c9\u00056\u0000\u0000\u00c9\u00ca\u0005\u001e\u0000"+
		"\u0000\u00ca\u00cb\u0003 \u0010\u0000\u00cb\u00cc\u0005)\u0000\u0000\u00cc"+
		"\u0017\u0001\u0000\u0000\u0000\u00cd\u00ce\u0005;\u0000\u0000\u00ce\u00cf"+
		"\u0005\f\u0000\u0000\u00cf\u00db\u0003,\u0016\u0000\u00d0\u00d1\u0005"+
		"&\u0000\u0000\u00d1\u00d6\u0003.\u0017\u0000\u00d2\u00d3\u0005(\u0000"+
		"\u0000\u00d3\u00d5\u0003.\u0017\u0000\u00d4\u00d2\u0001\u0000\u0000\u0000"+
		"\u00d5\u00d8\u0001\u0000\u0000\u0000\u00d6\u00d4\u0001\u0000\u0000\u0000"+
		"\u00d6\u00d7\u0001\u0000\u0000\u0000\u00d7\u00d9\u0001\u0000\u0000\u0000"+
		"\u00d8\u00d6\u0001\u0000\u0000\u0000\u00d9\u00da\u0005\'\u0000\u0000\u00da"+
		"\u00dc\u0001\u0000\u0000\u0000\u00db\u00d0\u0001\u0000\u0000\u0000\u00db"+
		"\u00dc\u0001\u0000\u0000\u0000\u00dc\u00dd\u0001\u0000\u0000\u0000\u00dd"+
		"\u00de\u0005<\u0000\u0000\u00de\u00df\u0005&\u0000\u0000\u00df\u00e4\u0003"+
		"\u001c\u000e\u0000\u00e0\u00e1\u0005(\u0000\u0000\u00e1\u00e3\u0003\u001c"+
		"\u000e\u0000\u00e2\u00e0\u0001\u0000\u0000\u0000\u00e3\u00e6\u0001\u0000"+
		"\u0000\u0000\u00e4\u00e2\u0001\u0000\u0000\u0000\u00e4\u00e5\u0001\u0000"+
		"\u0000\u0000\u00e5\u00e7\u0001\u0000\u0000\u0000\u00e6\u00e4\u0001\u0000"+
		"\u0000\u0000\u00e7\u00e8\u0005\'\u0000\u0000\u00e8\u00e9\u0005)\u0000"+
		"\u0000\u00e9\u0019\u0001\u0000\u0000\u0000\u00ea\u00eb\u00056\u0000\u0000"+
		"\u00eb\u00ec\u0005*\u0000\u0000\u00ec\u00ed\u0003\u001c\u000e\u0000\u00ed"+
		"\u001b\u0001\u0000\u0000\u0000\u00ee\u00fb\u00054\u0000\u0000\u00ef\u00fb"+
		"\u00055\u0000\u0000\u00f0\u00fb\u00057\u0000\u0000\u00f1\u00fb\u00058"+
		"\u0000\u0000\u00f2\u00f3\u00056\u0000\u0000\u00f3\u00f4\u0003\u001e\u000f"+
		"\u0000\u00f4\u00f5\u0003\u001c\u000e\u0000\u00f5\u00fb\u0001\u0000\u0000"+
		"\u0000\u00f6\u00f7\u0005&\u0000\u0000\u00f7\u00f8\u0003\u001c\u000e\u0000"+
		"\u00f8\u00f9\u0005\'\u0000\u0000\u00f9\u00fb\u0001\u0000\u0000\u0000\u00fa"+
		"\u00ee\u0001\u0000\u0000\u0000\u00fa\u00ef\u0001\u0000\u0000\u0000\u00fa"+
		"\u00f0\u0001\u0000\u0000\u0000\u00fa\u00f1\u0001\u0000\u0000\u0000\u00fa"+
		"\u00f2\u0001\u0000\u0000\u0000\u00fa\u00f6\u0001\u0000\u0000\u0000\u00fb"+
		"\u001d\u0001\u0000\u0000\u0000\u00fc\u00fd\u0007\u0002\u0000\u0000\u00fd"+
		"\u001f\u0001\u0000\u0000\u0000\u00fe\u00ff\u0006\u0010\uffff\uffff\u0000"+
		"\u00ff\u0105\u0003\"\u0011\u0000\u0100\u0101\u0005&\u0000\u0000\u0101"+
		"\u0102\u0003 \u0010\u0000\u0102\u0103\u0005\'\u0000\u0000\u0103\u0105"+
		"\u0001\u0000\u0000\u0000\u0104\u00fe\u0001\u0000\u0000\u0000\u0104\u0100"+
		"\u0001\u0000\u0000\u0000\u0105\u010e\u0001\u0000\u0000\u0000\u0106\u0107"+
		"\n\u0002\u0000\u0000\u0107\u0108\u0005\u001f\u0000\u0000\u0108\u010d\u0003"+
		" \u0010\u0003\u0109\u010a\n\u0001\u0000\u0000\u010a\u010b\u0005 \u0000"+
		"\u0000\u010b\u010d\u0003 \u0010\u0002\u010c\u0106\u0001\u0000\u0000\u0000"+
		"\u010c\u0109\u0001\u0000\u0000\u0000\u010d\u0110\u0001\u0000\u0000\u0000"+
		"\u010e\u010c\u0001\u0000\u0000\u0000\u010e\u010f\u0001\u0000\u0000\u0000"+
		"\u010f!\u0001\u0000\u0000\u0000\u0110\u010e\u0001\u0000\u0000\u0000\u0111"+
		"\u0112\u00056\u0000\u0000\u0112\u0113\u0003$\u0012\u0000\u0113\u0114\u0007"+
		"\u0003\u0000\u0000\u0114#\u0001\u0000\u0000\u0000\u0115\u0116\u0007\u0004"+
		"\u0000\u0000\u0116%\u0001\u0000\u0000\u0000\u0117\u0118\u00056\u0000\u0000"+
		"\u0118\u0119\u0003(\u0014\u0000\u0119\'\u0001\u0000\u0000\u0000\u011a"+
		"\u011b\u0007\u0005\u0000\u0000\u011b)\u0001\u0000\u0000\u0000\u011c\u011d"+
		"\u00059\u0000\u0000\u011d+\u0001\u0000\u0000\u0000\u011e\u011f\u00056"+
		"\u0000\u0000\u011f-\u0001\u0000\u0000\u0000\u0120\u0121\u00056\u0000\u0000"+
		"\u0121/\u0001\u0000\u0000\u0000\u00123@K[pu\u0080\u0090\u009b\u00b5\u00bf"+
		"\u00d6\u00db\u00e4\u00fa\u0104\u010c\u010e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}