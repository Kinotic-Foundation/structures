// Generated from StructuresSQL.g4 by ANTLR 4.13.2
package org.kinotic.structures.internal.sql.parser;
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
		MIGRATION=1, CREATE=2, TABLE=3, COMPONENT=4, INDEX=5, TEMPLATE=6, FOR=7, 
		USING=8, ALTER=9, ADD=10, COLUMN=11, REINDEX=12, INTO=13, WITH=14, CONFLICTS=15, 
		ABORT=16, PROCEED=17, MAX_DOCS=18, SLICES=19, AUTO=20, SIZE=21, SOURCE_FIELDS=22, 
		QUERY=23, SCRIPT=24, NUMBER_OF_SHARDS=25, NUMBER_OF_REPLICAS=26, UPDATE=27, 
		DELETE=28, FROM=29, SET=30, WHERE=31, AND=32, OR=33, TEXT=34, KEYWORD=35, 
		INTEGER=36, BOOLEAN=37, DATE=38, LPAREN=39, RPAREN=40, COMMA=41, SEMICOLON=42, 
		EQUALS=43, NOT_EQUALS=44, LESS_THAN=45, GREATER_THAN=46, LESS_THAN_EQUALS=47, 
		GREATER_THAN_EQUALS=48, PLUS=49, MINUS=50, MULTIPLY=51, DIVIDE=52, PARAMETER=53, 
		STRING=54, ID=55, INTEGER_LITERAL=56, BOOLEAN_LITERAL=57, COMMENT=58, 
		WS=59;
	public static final int
		RULE_migrations = 0, RULE_migrationStatement = 1, RULE_statement = 2, 
		RULE_createTableStatement = 3, RULE_createComponentTemplateStatement = 4, 
		RULE_createIndexTemplateStatement = 5, RULE_componentDefinition = 6, RULE_alterTableStatement = 7, 
		RULE_reindexStatement = 8, RULE_reindexOptions = 9, RULE_reindexOption = 10, 
		RULE_updateStatement = 11, RULE_deleteStatement = 12, RULE_assignment = 13, 
		RULE_expression = 14, RULE_operator = 15, RULE_whereClause = 16, RULE_condition = 17, 
		RULE_comparisonOperator = 18, RULE_columnDefinition = 19, RULE_type = 20, 
		RULE_comment = 21;
	private static String[] makeRuleNames() {
		return new String[] {
			"migrations", "migrationStatement", "statement", "createTableStatement", 
			"createComponentTemplateStatement", "createIndexTemplateStatement", "componentDefinition", 
			"alterTableStatement", "reindexStatement", "reindexOptions", "reindexOption", 
			"updateStatement", "deleteStatement", "assignment", "expression", "operator", 
			"whereClause", "condition", "comparisonOperator", "columnDefinition", 
			"type", "comment"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'MIGRATION'", "'CREATE'", "'TABLE'", "'COMPONENT'", "'INDEX'", 
			"'TEMPLATE'", "'FOR'", "'USING'", "'ALTER'", "'ADD'", "'COLUMN'", "'REINDEX'", 
			"'INTO'", "'WITH'", "'CONFLICTS'", "'ABORT'", "'PROCEED'", "'MAX_DOCS'", 
			"'SLICES'", "'AUTO'", "'SIZE'", "'SOURCE_FIELDS'", "'QUERY'", "'SCRIPT'", 
			"'NUMBER_OF_SHARDS'", "'NUMBER_OF_REPLICAS'", "'UPDATE'", "'DELETE'", 
			"'FROM'", "'SET'", "'WHERE'", "'AND'", "'OR'", "'TEXT'", "'KEYWORD'", 
			"'INTEGER'", "'BOOLEAN'", "'DATE'", "'('", "')'", "','", "';'", "'=='", 
			"'!='", "'<'", "'>'", "'<='", "'>='", "'+'", "'-'", "'*'", "'/'", "'?'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "MIGRATION", "CREATE", "TABLE", "COMPONENT", "INDEX", "TEMPLATE", 
			"FOR", "USING", "ALTER", "ADD", "COLUMN", "REINDEX", "INTO", "WITH", 
			"CONFLICTS", "ABORT", "PROCEED", "MAX_DOCS", "SLICES", "AUTO", "SIZE", 
			"SOURCE_FIELDS", "QUERY", "SCRIPT", "NUMBER_OF_SHARDS", "NUMBER_OF_REPLICAS", 
			"UPDATE", "DELETE", "FROM", "SET", "WHERE", "AND", "OR", "TEXT", "KEYWORD", 
			"INTEGER", "BOOLEAN", "DATE", "LPAREN", "RPAREN", "COMMA", "SEMICOLON", 
			"EQUALS", "NOT_EQUALS", "LESS_THAN", "GREATER_THAN", "LESS_THAN_EQUALS", 
			"GREATER_THAN_EQUALS", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "PARAMETER", 
			"STRING", "ID", "INTEGER_LITERAL", "BOOLEAN_LITERAL", "COMMENT", "WS"
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
		public List<MigrationStatementContext> migrationStatement() {
			return getRuleContexts(MigrationStatementContext.class);
		}
		public MigrationStatementContext migrationStatement(int i) {
			return getRuleContext(MigrationStatementContext.class,i);
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
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MIGRATION) {
				{
				{
				setState(44);
				migrationStatement();
				}
				}
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(50);
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
	public static class MigrationStatementContext extends ParserRuleContext {
		public TerminalNode MIGRATION() { return getToken(StructuresSQLParser.MIGRATION, 0); }
		public TerminalNode STRING() { return getToken(StructuresSQLParser.STRING, 0); }
		public TerminalNode SEMICOLON() { return getToken(StructuresSQLParser.SEMICOLON, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MigrationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_migrationStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).enterMigrationStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof StructuresSQLListener ) ((StructuresSQLListener)listener).exitMigrationStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof StructuresSQLVisitor ) return ((StructuresSQLVisitor<? extends T>)visitor).visitMigrationStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MigrationStatementContext migrationStatement() throws RecognitionException {
		MigrationStatementContext _localctx = new MigrationStatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_migrationStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(MIGRATION);
			setState(53);
			match(STRING);
			setState(54);
			match(SEMICOLON);
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288230376554369540L) != 0)) {
				{
				{
				setState(55);
				statement();
				}
				}
				setState(60);
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
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(69);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(61);
				createTableStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
				createComponentTemplateStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(63);
				createIndexTemplateStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(64);
				alterTableStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(65);
				reindexStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(66);
				updateStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(67);
				deleteStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
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
		enterRule(_localctx, 6, RULE_createTableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(CREATE);
			setState(72);
			match(TABLE);
			setState(73);
			match(ID);
			setState(74);
			match(LPAREN);
			setState(75);
			columnDefinition();
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(76);
				match(COMMA);
				setState(77);
				columnDefinition();
				}
				}
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(83);
			match(RPAREN);
			setState(84);
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
		enterRule(_localctx, 8, RULE_createComponentTemplateStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(CREATE);
			setState(87);
			match(COMPONENT);
			setState(88);
			match(TEMPLATE);
			setState(89);
			match(ID);
			setState(90);
			match(LPAREN);
			setState(91);
			componentDefinition();
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(92);
				match(COMMA);
				setState(93);
				componentDefinition();
				}
				}
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(99);
			match(RPAREN);
			setState(100);
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
		enterRule(_localctx, 10, RULE_createIndexTemplateStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(CREATE);
			setState(103);
			match(INDEX);
			setState(104);
			match(TEMPLATE);
			setState(105);
			match(ID);
			setState(106);
			match(FOR);
			setState(107);
			match(STRING);
			setState(108);
			match(USING);
			setState(109);
			match(STRING);
			setState(122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(110);
				match(WITH);
				setState(111);
				match(LPAREN);
				setState(112);
				componentDefinition();
				setState(117);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(113);
					match(COMMA);
					setState(114);
					componentDefinition();
					}
					}
					setState(119);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(120);
				match(RPAREN);
				}
			}

			setState(124);
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
		enterRule(_localctx, 12, RULE_componentDefinition);
		try {
			setState(133);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMBER_OF_SHARDS:
				enterOuterAlt(_localctx, 1);
				{
				setState(126);
				match(NUMBER_OF_SHARDS);
				setState(127);
				match(EQUALS);
				setState(128);
				match(INTEGER_LITERAL);
				}
				break;
			case NUMBER_OF_REPLICAS:
				enterOuterAlt(_localctx, 2);
				{
				setState(129);
				match(NUMBER_OF_REPLICAS);
				setState(130);
				match(EQUALS);
				setState(131);
				match(INTEGER_LITERAL);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(132);
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
		enterRule(_localctx, 14, RULE_alterTableStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			match(ALTER);
			setState(136);
			match(TABLE);
			setState(137);
			match(ID);
			setState(138);
			match(ADD);
			setState(139);
			match(COLUMN);
			setState(140);
			match(ID);
			setState(141);
			type();
			setState(142);
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
		enterRule(_localctx, 16, RULE_reindexStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(REINDEX);
			setState(145);
			match(ID);
			setState(146);
			match(INTO);
			setState(147);
			match(ID);
			setState(149);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(148);
				reindexOptions();
				}
			}

			setState(151);
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
		enterRule(_localctx, 18, RULE_reindexOptions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			match(WITH);
			setState(154);
			match(LPAREN);
			setState(155);
			reindexOption();
			setState(160);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(156);
				match(COMMA);
				setState(157);
				reindexOption();
				}
				}
				setState(162);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(163);
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
		enterRule(_localctx, 20, RULE_reindexOption);
		int _la;
		try {
			setState(186);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONFLICTS:
				enterOuterAlt(_localctx, 1);
				{
				setState(165);
				match(CONFLICTS);
				setState(166);
				match(EQUALS);
				setState(167);
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
				setState(168);
				match(MAX_DOCS);
				setState(169);
				match(EQUALS);
				setState(170);
				match(INTEGER_LITERAL);
				}
				break;
			case SLICES:
				enterOuterAlt(_localctx, 3);
				{
				setState(171);
				match(SLICES);
				setState(172);
				match(EQUALS);
				setState(173);
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
				setState(174);
				match(SIZE);
				setState(175);
				match(EQUALS);
				setState(176);
				match(INTEGER_LITERAL);
				}
				break;
			case SOURCE_FIELDS:
				enterOuterAlt(_localctx, 5);
				{
				setState(177);
				match(SOURCE_FIELDS);
				setState(178);
				match(EQUALS);
				setState(179);
				match(STRING);
				}
				break;
			case QUERY:
				enterOuterAlt(_localctx, 6);
				{
				setState(180);
				match(QUERY);
				setState(181);
				match(EQUALS);
				setState(182);
				match(STRING);
				}
				break;
			case SCRIPT:
				enterOuterAlt(_localctx, 7);
				{
				setState(183);
				match(SCRIPT);
				setState(184);
				match(EQUALS);
				setState(185);
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
		enterRule(_localctx, 22, RULE_updateStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(UPDATE);
			setState(189);
			match(ID);
			setState(190);
			match(SET);
			setState(191);
			assignment();
			setState(196);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(192);
				match(COMMA);
				setState(193);
				assignment();
				}
				}
				setState(198);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(199);
			match(WHERE);
			setState(200);
			whereClause(0);
			setState(201);
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
		enterRule(_localctx, 24, RULE_deleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(DELETE);
			setState(204);
			match(FROM);
			setState(205);
			match(ID);
			setState(206);
			match(WHERE);
			setState(207);
			whereClause(0);
			setState(208);
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
			setState(210);
			match(ID);
			setState(211);
			match(EQUALS);
			setState(212);
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
			setState(226);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PARAMETER:
				enterOuterAlt(_localctx, 1);
				{
				setState(214);
				match(PARAMETER);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(215);
				match(STRING);
				}
				break;
			case INTEGER_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(216);
				match(INTEGER_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(217);
				match(BOOLEAN_LITERAL);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 5);
				{
				setState(218);
				match(ID);
				setState(219);
				operator();
				setState(220);
				expression();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				setState(222);
				match(LPAREN);
				setState(223);
				expression();
				setState(224);
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
			setState(228);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8453045394341888L) != 0)) ) {
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
			setState(236);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(231);
				condition();
				}
				break;
			case LPAREN:
				{
				setState(232);
				match(LPAREN);
				setState(233);
				whereClause(0);
				setState(234);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(246);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(244);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
					case 1:
						{
						_localctx = new WhereClauseContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_whereClause);
						setState(238);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(239);
						match(AND);
						setState(240);
						whereClause(3);
						}
						break;
					case 2:
						{
						_localctx = new WhereClauseContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_whereClause);
						setState(241);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(242);
						match(OR);
						setState(243);
						whereClause(2);
						}
						break;
					}
					} 
				}
				setState(248);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
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
			setState(249);
			match(ID);
			setState(250);
			comparisonOperator();
			setState(251);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 243194379878006784L) != 0)) ) {
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
			setState(253);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 554153860399104L) != 0)) ) {
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
			setState(255);
			match(ID);
			setState(256);
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
			setState(258);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 532575944704L) != 0)) ) {
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
			setState(260);
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
		"\u0004\u0001;\u0107\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0001\u0000\u0005\u0000.\b\u0000\n\u0000\f\u00001\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001"+
		"9\b\u0001\n\u0001\f\u0001<\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"+
		"F\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0005\u0003O\b\u0003\n\u0003\f\u0003R\t\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004"+
		"_\b\u0004\n\u0004\f\u0004b\t\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0005\u0005t\b\u0005\n\u0005\f\u0005w\t\u0005\u0001\u0005"+
		"\u0001\u0005\u0003\u0005{\b\u0005\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0003\u0006\u0086\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0003\b\u0096\b\b\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0005\t\u009f\b\t\n\t\f\t\u00a2\t\t\u0001\t"+
		"\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00bb\b\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u00c3"+
		"\b\u000b\n\u000b\f\u000b\u00c6\t\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0003\u000e\u00e3\b\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0003\u0010\u00ed\b\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0005\u0010\u00f5\b\u0010\n\u0010\f\u0010\u00f8"+
		"\t\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001"+
		"\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0000\u0001 \u0016\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*\u0000"+
		"\u0006\u0001\u0000\u0010\u0011\u0002\u0000\u0014\u001488\u0002\u0000+"+
		"+14\u0002\u00005689\u0001\u0000+0\u0001\u0000\"&\u0110\u0000/\u0001\u0000"+
		"\u0000\u0000\u00024\u0001\u0000\u0000\u0000\u0004E\u0001\u0000\u0000\u0000"+
		"\u0006G\u0001\u0000\u0000\u0000\bV\u0001\u0000\u0000\u0000\nf\u0001\u0000"+
		"\u0000\u0000\f\u0085\u0001\u0000\u0000\u0000\u000e\u0087\u0001\u0000\u0000"+
		"\u0000\u0010\u0090\u0001\u0000\u0000\u0000\u0012\u0099\u0001\u0000\u0000"+
		"\u0000\u0014\u00ba\u0001\u0000\u0000\u0000\u0016\u00bc\u0001\u0000\u0000"+
		"\u0000\u0018\u00cb\u0001\u0000\u0000\u0000\u001a\u00d2\u0001\u0000\u0000"+
		"\u0000\u001c\u00e2\u0001\u0000\u0000\u0000\u001e\u00e4\u0001\u0000\u0000"+
		"\u0000 \u00ec\u0001\u0000\u0000\u0000\"\u00f9\u0001\u0000\u0000\u0000"+
		"$\u00fd\u0001\u0000\u0000\u0000&\u00ff\u0001\u0000\u0000\u0000(\u0102"+
		"\u0001\u0000\u0000\u0000*\u0104\u0001\u0000\u0000\u0000,.\u0003\u0002"+
		"\u0001\u0000-,\u0001\u0000\u0000\u0000.1\u0001\u0000\u0000\u0000/-\u0001"+
		"\u0000\u0000\u0000/0\u0001\u0000\u0000\u000002\u0001\u0000\u0000\u0000"+
		"1/\u0001\u0000\u0000\u000023\u0005\u0000\u0000\u00013\u0001\u0001\u0000"+
		"\u0000\u000045\u0005\u0001\u0000\u000056\u00056\u0000\u00006:\u0005*\u0000"+
		"\u000079\u0003\u0004\u0002\u000087\u0001\u0000\u0000\u00009<\u0001\u0000"+
		"\u0000\u0000:8\u0001\u0000\u0000\u0000:;\u0001\u0000\u0000\u0000;\u0003"+
		"\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000=F\u0003\u0006\u0003"+
		"\u0000>F\u0003\b\u0004\u0000?F\u0003\n\u0005\u0000@F\u0003\u000e\u0007"+
		"\u0000AF\u0003\u0010\b\u0000BF\u0003\u0016\u000b\u0000CF\u0003\u0018\f"+
		"\u0000DF\u0003*\u0015\u0000E=\u0001\u0000\u0000\u0000E>\u0001\u0000\u0000"+
		"\u0000E?\u0001\u0000\u0000\u0000E@\u0001\u0000\u0000\u0000EA\u0001\u0000"+
		"\u0000\u0000EB\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000ED\u0001"+
		"\u0000\u0000\u0000F\u0005\u0001\u0000\u0000\u0000GH\u0005\u0002\u0000"+
		"\u0000HI\u0005\u0003\u0000\u0000IJ\u00057\u0000\u0000JK\u0005\'\u0000"+
		"\u0000KP\u0003&\u0013\u0000LM\u0005)\u0000\u0000MO\u0003&\u0013\u0000"+
		"NL\u0001\u0000\u0000\u0000OR\u0001\u0000\u0000\u0000PN\u0001\u0000\u0000"+
		"\u0000PQ\u0001\u0000\u0000\u0000QS\u0001\u0000\u0000\u0000RP\u0001\u0000"+
		"\u0000\u0000ST\u0005(\u0000\u0000TU\u0005*\u0000\u0000U\u0007\u0001\u0000"+
		"\u0000\u0000VW\u0005\u0002\u0000\u0000WX\u0005\u0004\u0000\u0000XY\u0005"+
		"\u0006\u0000\u0000YZ\u00057\u0000\u0000Z[\u0005\'\u0000\u0000[`\u0003"+
		"\f\u0006\u0000\\]\u0005)\u0000\u0000]_\u0003\f\u0006\u0000^\\\u0001\u0000"+
		"\u0000\u0000_b\u0001\u0000\u0000\u0000`^\u0001\u0000\u0000\u0000`a\u0001"+
		"\u0000\u0000\u0000ac\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000"+
		"cd\u0005(\u0000\u0000de\u0005*\u0000\u0000e\t\u0001\u0000\u0000\u0000"+
		"fg\u0005\u0002\u0000\u0000gh\u0005\u0005\u0000\u0000hi\u0005\u0006\u0000"+
		"\u0000ij\u00057\u0000\u0000jk\u0005\u0007\u0000\u0000kl\u00056\u0000\u0000"+
		"lm\u0005\b\u0000\u0000mz\u00056\u0000\u0000no\u0005\u000e\u0000\u0000"+
		"op\u0005\'\u0000\u0000pu\u0003\f\u0006\u0000qr\u0005)\u0000\u0000rt\u0003"+
		"\f\u0006\u0000sq\u0001\u0000\u0000\u0000tw\u0001\u0000\u0000\u0000us\u0001"+
		"\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000vx\u0001\u0000\u0000\u0000"+
		"wu\u0001\u0000\u0000\u0000xy\u0005(\u0000\u0000y{\u0001\u0000\u0000\u0000"+
		"zn\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{|\u0001\u0000\u0000"+
		"\u0000|}\u0005*\u0000\u0000}\u000b\u0001\u0000\u0000\u0000~\u007f\u0005"+
		"\u0019\u0000\u0000\u007f\u0080\u0005+\u0000\u0000\u0080\u0086\u00058\u0000"+
		"\u0000\u0081\u0082\u0005\u001a\u0000\u0000\u0082\u0083\u0005+\u0000\u0000"+
		"\u0083\u0086\u00058\u0000\u0000\u0084\u0086\u0003&\u0013\u0000\u0085~"+
		"\u0001\u0000\u0000\u0000\u0085\u0081\u0001\u0000\u0000\u0000\u0085\u0084"+
		"\u0001\u0000\u0000\u0000\u0086\r\u0001\u0000\u0000\u0000\u0087\u0088\u0005"+
		"\t\u0000\u0000\u0088\u0089\u0005\u0003\u0000\u0000\u0089\u008a\u00057"+
		"\u0000\u0000\u008a\u008b\u0005\n\u0000\u0000\u008b\u008c\u0005\u000b\u0000"+
		"\u0000\u008c\u008d\u00057\u0000\u0000\u008d\u008e\u0003(\u0014\u0000\u008e"+
		"\u008f\u0005*\u0000\u0000\u008f\u000f\u0001\u0000\u0000\u0000\u0090\u0091"+
		"\u0005\f\u0000\u0000\u0091\u0092\u00057\u0000\u0000\u0092\u0093\u0005"+
		"\r\u0000\u0000\u0093\u0095\u00057\u0000\u0000\u0094\u0096\u0003\u0012"+
		"\t\u0000\u0095\u0094\u0001\u0000\u0000\u0000\u0095\u0096\u0001\u0000\u0000"+
		"\u0000\u0096\u0097\u0001\u0000\u0000\u0000\u0097\u0098\u0005*\u0000\u0000"+
		"\u0098\u0011\u0001\u0000\u0000\u0000\u0099\u009a\u0005\u000e\u0000\u0000"+
		"\u009a\u009b\u0005\'\u0000\u0000\u009b\u00a0\u0003\u0014\n\u0000\u009c"+
		"\u009d\u0005)\u0000\u0000\u009d\u009f\u0003\u0014\n\u0000\u009e\u009c"+
		"\u0001\u0000\u0000\u0000\u009f\u00a2\u0001\u0000\u0000\u0000\u00a0\u009e"+
		"\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000\u0000\u0000\u00a1\u00a3"+
		"\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a3\u00a4"+
		"\u0005(\u0000\u0000\u00a4\u0013\u0001\u0000\u0000\u0000\u00a5\u00a6\u0005"+
		"\u000f\u0000\u0000\u00a6\u00a7\u0005+\u0000\u0000\u00a7\u00bb\u0007\u0000"+
		"\u0000\u0000\u00a8\u00a9\u0005\u0012\u0000\u0000\u00a9\u00aa\u0005+\u0000"+
		"\u0000\u00aa\u00bb\u00058\u0000\u0000\u00ab\u00ac\u0005\u0013\u0000\u0000"+
		"\u00ac\u00ad\u0005+\u0000\u0000\u00ad\u00bb\u0007\u0001\u0000\u0000\u00ae"+
		"\u00af\u0005\u0015\u0000\u0000\u00af\u00b0\u0005+\u0000\u0000\u00b0\u00bb"+
		"\u00058\u0000\u0000\u00b1\u00b2\u0005\u0016\u0000\u0000\u00b2\u00b3\u0005"+
		"+\u0000\u0000\u00b3\u00bb\u00056\u0000\u0000\u00b4\u00b5\u0005\u0017\u0000"+
		"\u0000\u00b5\u00b6\u0005+\u0000\u0000\u00b6\u00bb\u00056\u0000\u0000\u00b7"+
		"\u00b8\u0005\u0018\u0000\u0000\u00b8\u00b9\u0005+\u0000\u0000\u00b9\u00bb"+
		"\u00056\u0000\u0000\u00ba\u00a5\u0001\u0000\u0000\u0000\u00ba\u00a8\u0001"+
		"\u0000\u0000\u0000\u00ba\u00ab\u0001\u0000\u0000\u0000\u00ba\u00ae\u0001"+
		"\u0000\u0000\u0000\u00ba\u00b1\u0001\u0000\u0000\u0000\u00ba\u00b4\u0001"+
		"\u0000\u0000\u0000\u00ba\u00b7\u0001\u0000\u0000\u0000\u00bb\u0015\u0001"+
		"\u0000\u0000\u0000\u00bc\u00bd\u0005\u001b\u0000\u0000\u00bd\u00be\u0005"+
		"7\u0000\u0000\u00be\u00bf\u0005\u001e\u0000\u0000\u00bf\u00c4\u0003\u001a"+
		"\r\u0000\u00c0\u00c1\u0005)\u0000\u0000\u00c1\u00c3\u0003\u001a\r\u0000"+
		"\u00c2\u00c0\u0001\u0000\u0000\u0000\u00c3\u00c6\u0001\u0000\u0000\u0000"+
		"\u00c4\u00c2\u0001\u0000\u0000\u0000\u00c4\u00c5\u0001\u0000\u0000\u0000"+
		"\u00c5\u00c7\u0001\u0000\u0000\u0000\u00c6\u00c4\u0001\u0000\u0000\u0000"+
		"\u00c7\u00c8\u0005\u001f\u0000\u0000\u00c8\u00c9\u0003 \u0010\u0000\u00c9"+
		"\u00ca\u0005*\u0000\u0000\u00ca\u0017\u0001\u0000\u0000\u0000\u00cb\u00cc"+
		"\u0005\u001c\u0000\u0000\u00cc\u00cd\u0005\u001d\u0000\u0000\u00cd\u00ce"+
		"\u00057\u0000\u0000\u00ce\u00cf\u0005\u001f\u0000\u0000\u00cf\u00d0\u0003"+
		" \u0010\u0000\u00d0\u00d1\u0005*\u0000\u0000\u00d1\u0019\u0001\u0000\u0000"+
		"\u0000\u00d2\u00d3\u00057\u0000\u0000\u00d3\u00d4\u0005+\u0000\u0000\u00d4"+
		"\u00d5\u0003\u001c\u000e\u0000\u00d5\u001b\u0001\u0000\u0000\u0000\u00d6"+
		"\u00e3\u00055\u0000\u0000\u00d7\u00e3\u00056\u0000\u0000\u00d8\u00e3\u0005"+
		"8\u0000\u0000\u00d9\u00e3\u00059\u0000\u0000\u00da\u00db\u00057\u0000"+
		"\u0000\u00db\u00dc\u0003\u001e\u000f\u0000\u00dc\u00dd\u0003\u001c\u000e"+
		"\u0000\u00dd\u00e3\u0001\u0000\u0000\u0000\u00de\u00df\u0005\'\u0000\u0000"+
		"\u00df\u00e0\u0003\u001c\u000e\u0000\u00e0\u00e1\u0005(\u0000\u0000\u00e1"+
		"\u00e3\u0001\u0000\u0000\u0000\u00e2\u00d6\u0001\u0000\u0000\u0000\u00e2"+
		"\u00d7\u0001\u0000\u0000\u0000\u00e2\u00d8\u0001\u0000\u0000\u0000\u00e2"+
		"\u00d9\u0001\u0000\u0000\u0000\u00e2\u00da\u0001\u0000\u0000\u0000\u00e2"+
		"\u00de\u0001\u0000\u0000\u0000\u00e3\u001d\u0001\u0000\u0000\u0000\u00e4"+
		"\u00e5\u0007\u0002\u0000\u0000\u00e5\u001f\u0001\u0000\u0000\u0000\u00e6"+
		"\u00e7\u0006\u0010\uffff\uffff\u0000\u00e7\u00ed\u0003\"\u0011\u0000\u00e8"+
		"\u00e9\u0005\'\u0000\u0000\u00e9\u00ea\u0003 \u0010\u0000\u00ea\u00eb"+
		"\u0005(\u0000\u0000\u00eb\u00ed\u0001\u0000\u0000\u0000\u00ec\u00e6\u0001"+
		"\u0000\u0000\u0000\u00ec\u00e8\u0001\u0000\u0000\u0000\u00ed\u00f6\u0001"+
		"\u0000\u0000\u0000\u00ee\u00ef\n\u0002\u0000\u0000\u00ef\u00f0\u0005 "+
		"\u0000\u0000\u00f0\u00f5\u0003 \u0010\u0003\u00f1\u00f2\n\u0001\u0000"+
		"\u0000\u00f2\u00f3\u0005!\u0000\u0000\u00f3\u00f5\u0003 \u0010\u0002\u00f4"+
		"\u00ee\u0001\u0000\u0000\u0000\u00f4\u00f1\u0001\u0000\u0000\u0000\u00f5"+
		"\u00f8\u0001\u0000\u0000\u0000\u00f6\u00f4\u0001\u0000\u0000\u0000\u00f6"+
		"\u00f7\u0001\u0000\u0000\u0000\u00f7!\u0001\u0000\u0000\u0000\u00f8\u00f6"+
		"\u0001\u0000\u0000\u0000\u00f9\u00fa\u00057\u0000\u0000\u00fa\u00fb\u0003"+
		"$\u0012\u0000\u00fb\u00fc\u0007\u0003\u0000\u0000\u00fc#\u0001\u0000\u0000"+
		"\u0000\u00fd\u00fe\u0007\u0004\u0000\u0000\u00fe%\u0001\u0000\u0000\u0000"+
		"\u00ff\u0100\u00057\u0000\u0000\u0100\u0101\u0003(\u0014\u0000\u0101\'"+
		"\u0001\u0000\u0000\u0000\u0102\u0103\u0007\u0005\u0000\u0000\u0103)\u0001"+
		"\u0000\u0000\u0000\u0104\u0105\u0005:\u0000\u0000\u0105+\u0001\u0000\u0000"+
		"\u0000\u0010/:EP`uz\u0085\u0095\u00a0\u00ba\u00c4\u00e2\u00ec\u00f4\u00f6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}