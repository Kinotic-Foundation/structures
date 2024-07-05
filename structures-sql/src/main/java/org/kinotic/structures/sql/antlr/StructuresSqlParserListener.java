// Generated from /Users/navid/workspace/git/continuum/structures/structures-sql/src/main/resources/antlr/StructuresSqlParser.g4 by ANTLR 4.13.1
package org.kinotic.structures.sql.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link StructuresSqlParserParser}.
 */
public interface StructuresSqlParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#singleStatement}.
	 * @param ctx the parse tree
	 */
	void enterSingleStatement(StructuresSqlParserParser.SingleStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#singleStatement}.
	 * @param ctx the parse tree
	 */
	void exitSingleStatement(StructuresSqlParserParser.SingleStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#standaloneExpression}.
	 * @param ctx the parse tree
	 */
	void enterStandaloneExpression(StructuresSqlParserParser.StandaloneExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#standaloneExpression}.
	 * @param ctx the parse tree
	 */
	void exitStandaloneExpression(StructuresSqlParserParser.StandaloneExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#standalonePathSpecification}.
	 * @param ctx the parse tree
	 */
	void enterStandalonePathSpecification(StructuresSqlParserParser.StandalonePathSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#standalonePathSpecification}.
	 * @param ctx the parse tree
	 */
	void exitStandalonePathSpecification(StructuresSqlParserParser.StandalonePathSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#standaloneType}.
	 * @param ctx the parse tree
	 */
	void enterStandaloneType(StructuresSqlParserParser.StandaloneTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#standaloneType}.
	 * @param ctx the parse tree
	 */
	void exitStandaloneType(StructuresSqlParserParser.StandaloneTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#standaloneRowPattern}.
	 * @param ctx the parse tree
	 */
	void enterStandaloneRowPattern(StructuresSqlParserParser.StandaloneRowPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#standaloneRowPattern}.
	 * @param ctx the parse tree
	 */
	void exitStandaloneRowPattern(StructuresSqlParserParser.StandaloneRowPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#standaloneFunctionSpecification}.
	 * @param ctx the parse tree
	 */
	void enterStandaloneFunctionSpecification(StructuresSqlParserParser.StandaloneFunctionSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#standaloneFunctionSpecification}.
	 * @param ctx the parse tree
	 */
	void exitStandaloneFunctionSpecification(StructuresSqlParserParser.StandaloneFunctionSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statementDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementDefault(StructuresSqlParserParser.StatementDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementDefault(StructuresSqlParserParser.StatementDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code use}
	 * labeled alternative in {@link StructuresSqlParserParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterUse(StructuresSqlParserParser.UseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code use}
	 * labeled alternative in {@link StructuresSqlParserParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitUse(StructuresSqlParserParser.UseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code update}
	 * labeled alternative in {@link StructuresSqlParserParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterUpdate(StructuresSqlParserParser.UpdateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code update}
	 * labeled alternative in {@link StructuresSqlParserParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitUpdate(StructuresSqlParserParser.UpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#rootQuery}.
	 * @param ctx the parse tree
	 */
	void enterRootQuery(StructuresSqlParserParser.RootQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#rootQuery}.
	 * @param ctx the parse tree
	 */
	void exitRootQuery(StructuresSqlParserParser.RootQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#withFunction}.
	 * @param ctx the parse tree
	 */
	void enterWithFunction(StructuresSqlParserParser.WithFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#withFunction}.
	 * @param ctx the parse tree
	 */
	void exitWithFunction(StructuresSqlParserParser.WithFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(StructuresSqlParserParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(StructuresSqlParserParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#with}.
	 * @param ctx the parse tree
	 */
	void enterWith(StructuresSqlParserParser.WithContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#with}.
	 * @param ctx the parse tree
	 */
	void exitWith(StructuresSqlParserParser.WithContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#tableElement}.
	 * @param ctx the parse tree
	 */
	void enterTableElement(StructuresSqlParserParser.TableElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#tableElement}.
	 * @param ctx the parse tree
	 */
	void exitTableElement(StructuresSqlParserParser.TableElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#columnDefinition}.
	 * @param ctx the parse tree
	 */
	void enterColumnDefinition(StructuresSqlParserParser.ColumnDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#columnDefinition}.
	 * @param ctx the parse tree
	 */
	void exitColumnDefinition(StructuresSqlParserParser.ColumnDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#likeClause}.
	 * @param ctx the parse tree
	 */
	void enterLikeClause(StructuresSqlParserParser.LikeClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#likeClause}.
	 * @param ctx the parse tree
	 */
	void exitLikeClause(StructuresSqlParserParser.LikeClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#properties}.
	 * @param ctx the parse tree
	 */
	void enterProperties(StructuresSqlParserParser.PropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#properties}.
	 * @param ctx the parse tree
	 */
	void exitProperties(StructuresSqlParserParser.PropertiesContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#propertyAssignments}.
	 * @param ctx the parse tree
	 */
	void enterPropertyAssignments(StructuresSqlParserParser.PropertyAssignmentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#propertyAssignments}.
	 * @param ctx the parse tree
	 */
	void exitPropertyAssignments(StructuresSqlParserParser.PropertyAssignmentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(StructuresSqlParserParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(StructuresSqlParserParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code defaultPropertyValue}
	 * labeled alternative in {@link StructuresSqlParserParser#propertyValue}.
	 * @param ctx the parse tree
	 */
	void enterDefaultPropertyValue(StructuresSqlParserParser.DefaultPropertyValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code defaultPropertyValue}
	 * labeled alternative in {@link StructuresSqlParserParser#propertyValue}.
	 * @param ctx the parse tree
	 */
	void exitDefaultPropertyValue(StructuresSqlParserParser.DefaultPropertyValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonDefaultPropertyValue}
	 * labeled alternative in {@link StructuresSqlParserParser#propertyValue}.
	 * @param ctx the parse tree
	 */
	void enterNonDefaultPropertyValue(StructuresSqlParserParser.NonDefaultPropertyValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonDefaultPropertyValue}
	 * labeled alternative in {@link StructuresSqlParserParser#propertyValue}.
	 * @param ctx the parse tree
	 */
	void exitNonDefaultPropertyValue(StructuresSqlParserParser.NonDefaultPropertyValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#queryNoWith}.
	 * @param ctx the parse tree
	 */
	void enterQueryNoWith(StructuresSqlParserParser.QueryNoWithContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#queryNoWith}.
	 * @param ctx the parse tree
	 */
	void exitQueryNoWith(StructuresSqlParserParser.QueryNoWithContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#limitRowCount}.
	 * @param ctx the parse tree
	 */
	void enterLimitRowCount(StructuresSqlParserParser.LimitRowCountContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#limitRowCount}.
	 * @param ctx the parse tree
	 */
	void exitLimitRowCount(StructuresSqlParserParser.LimitRowCountContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#rowCount}.
	 * @param ctx the parse tree
	 */
	void enterRowCount(StructuresSqlParserParser.RowCountContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#rowCount}.
	 * @param ctx the parse tree
	 */
	void exitRowCount(StructuresSqlParserParser.RowCountContext ctx);
	/**
	 * Enter a parse tree produced by the {@code queryTermDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void enterQueryTermDefault(StructuresSqlParserParser.QueryTermDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code queryTermDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void exitQueryTermDefault(StructuresSqlParserParser.QueryTermDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code setOperation}
	 * labeled alternative in {@link StructuresSqlParserParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void enterSetOperation(StructuresSqlParserParser.SetOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code setOperation}
	 * labeled alternative in {@link StructuresSqlParserParser#queryTerm}.
	 * @param ctx the parse tree
	 */
	void exitSetOperation(StructuresSqlParserParser.SetOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code queryPrimaryDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterQueryPrimaryDefault(StructuresSqlParserParser.QueryPrimaryDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code queryPrimaryDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitQueryPrimaryDefault(StructuresSqlParserParser.QueryPrimaryDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code table}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterTable(StructuresSqlParserParser.TableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code table}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitTable(StructuresSqlParserParser.TableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inlineTable}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterInlineTable(StructuresSqlParserParser.InlineTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inlineTable}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitInlineTable(StructuresSqlParserParser.InlineTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subquery}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void enterSubquery(StructuresSqlParserParser.SubqueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subquery}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 */
	void exitSubquery(StructuresSqlParserParser.SubqueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#sortItem}.
	 * @param ctx the parse tree
	 */
	void enterSortItem(StructuresSqlParserParser.SortItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#sortItem}.
	 * @param ctx the parse tree
	 */
	void exitSortItem(StructuresSqlParserParser.SortItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#querySpecification}.
	 * @param ctx the parse tree
	 */
	void enterQuerySpecification(StructuresSqlParserParser.QuerySpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#querySpecification}.
	 * @param ctx the parse tree
	 */
	void exitQuerySpecification(StructuresSqlParserParser.QuerySpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#groupBy}.
	 * @param ctx the parse tree
	 */
	void enterGroupBy(StructuresSqlParserParser.GroupByContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#groupBy}.
	 * @param ctx the parse tree
	 */
	void exitGroupBy(StructuresSqlParserParser.GroupByContext ctx);
	/**
	 * Enter a parse tree produced by the {@code singleGroupingSet}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 */
	void enterSingleGroupingSet(StructuresSqlParserParser.SingleGroupingSetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code singleGroupingSet}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 */
	void exitSingleGroupingSet(StructuresSqlParserParser.SingleGroupingSetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rollup}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 */
	void enterRollup(StructuresSqlParserParser.RollupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rollup}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 */
	void exitRollup(StructuresSqlParserParser.RollupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cube}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 */
	void enterCube(StructuresSqlParserParser.CubeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cube}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 */
	void exitCube(StructuresSqlParserParser.CubeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multipleGroupingSets}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 */
	void enterMultipleGroupingSets(StructuresSqlParserParser.MultipleGroupingSetsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multipleGroupingSets}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 */
	void exitMultipleGroupingSets(StructuresSqlParserParser.MultipleGroupingSetsContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#groupingSet}.
	 * @param ctx the parse tree
	 */
	void enterGroupingSet(StructuresSqlParserParser.GroupingSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#groupingSet}.
	 * @param ctx the parse tree
	 */
	void exitGroupingSet(StructuresSqlParserParser.GroupingSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#windowDefinition}.
	 * @param ctx the parse tree
	 */
	void enterWindowDefinition(StructuresSqlParserParser.WindowDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#windowDefinition}.
	 * @param ctx the parse tree
	 */
	void exitWindowDefinition(StructuresSqlParserParser.WindowDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#windowSpecification}.
	 * @param ctx the parse tree
	 */
	void enterWindowSpecification(StructuresSqlParserParser.WindowSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#windowSpecification}.
	 * @param ctx the parse tree
	 */
	void exitWindowSpecification(StructuresSqlParserParser.WindowSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#namedQuery}.
	 * @param ctx the parse tree
	 */
	void enterNamedQuery(StructuresSqlParserParser.NamedQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#namedQuery}.
	 * @param ctx the parse tree
	 */
	void exitNamedQuery(StructuresSqlParserParser.NamedQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#setQuantifier}.
	 * @param ctx the parse tree
	 */
	void enterSetQuantifier(StructuresSqlParserParser.SetQuantifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#setQuantifier}.
	 * @param ctx the parse tree
	 */
	void exitSetQuantifier(StructuresSqlParserParser.SetQuantifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selectSingle}
	 * labeled alternative in {@link StructuresSqlParserParser#selectItem}.
	 * @param ctx the parse tree
	 */
	void enterSelectSingle(StructuresSqlParserParser.SelectSingleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selectSingle}
	 * labeled alternative in {@link StructuresSqlParserParser#selectItem}.
	 * @param ctx the parse tree
	 */
	void exitSelectSingle(StructuresSqlParserParser.SelectSingleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selectAll}
	 * labeled alternative in {@link StructuresSqlParserParser#selectItem}.
	 * @param ctx the parse tree
	 */
	void enterSelectAll(StructuresSqlParserParser.SelectAllContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selectAll}
	 * labeled alternative in {@link StructuresSqlParserParser#selectItem}.
	 * @param ctx the parse tree
	 */
	void exitSelectAll(StructuresSqlParserParser.SelectAllContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterRelationDefault(StructuresSqlParserParser.RelationDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitRelationDefault(StructuresSqlParserParser.RelationDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code joinRelation}
	 * labeled alternative in {@link StructuresSqlParserParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterJoinRelation(StructuresSqlParserParser.JoinRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code joinRelation}
	 * labeled alternative in {@link StructuresSqlParserParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitJoinRelation(StructuresSqlParserParser.JoinRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#joinType}.
	 * @param ctx the parse tree
	 */
	void enterJoinType(StructuresSqlParserParser.JoinTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#joinType}.
	 * @param ctx the parse tree
	 */
	void exitJoinType(StructuresSqlParserParser.JoinTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#joinCriteria}.
	 * @param ctx the parse tree
	 */
	void enterJoinCriteria(StructuresSqlParserParser.JoinCriteriaContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#joinCriteria}.
	 * @param ctx the parse tree
	 */
	void exitJoinCriteria(StructuresSqlParserParser.JoinCriteriaContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#sampledRelation}.
	 * @param ctx the parse tree
	 */
	void enterSampledRelation(StructuresSqlParserParser.SampledRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#sampledRelation}.
	 * @param ctx the parse tree
	 */
	void exitSampledRelation(StructuresSqlParserParser.SampledRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#sampleType}.
	 * @param ctx the parse tree
	 */
	void enterSampleType(StructuresSqlParserParser.SampleTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#sampleType}.
	 * @param ctx the parse tree
	 */
	void exitSampleType(StructuresSqlParserParser.SampleTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#trimsSpecification}.
	 * @param ctx the parse tree
	 */
	void enterTrimsSpecification(StructuresSqlParserParser.TrimsSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#trimsSpecification}.
	 * @param ctx the parse tree
	 */
	void exitTrimsSpecification(StructuresSqlParserParser.TrimsSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#listAggOverflowBehavior}.
	 * @param ctx the parse tree
	 */
	void enterListAggOverflowBehavior(StructuresSqlParserParser.ListAggOverflowBehaviorContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#listAggOverflowBehavior}.
	 * @param ctx the parse tree
	 */
	void exitListAggOverflowBehavior(StructuresSqlParserParser.ListAggOverflowBehaviorContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#listaggCountIndication}.
	 * @param ctx the parse tree
	 */
	void enterListaggCountIndication(StructuresSqlParserParser.ListaggCountIndicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#listaggCountIndication}.
	 * @param ctx the parse tree
	 */
	void exitListaggCountIndication(StructuresSqlParserParser.ListaggCountIndicationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#patternRecognition}.
	 * @param ctx the parse tree
	 */
	void enterPatternRecognition(StructuresSqlParserParser.PatternRecognitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#patternRecognition}.
	 * @param ctx the parse tree
	 */
	void exitPatternRecognition(StructuresSqlParserParser.PatternRecognitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#measureDefinition}.
	 * @param ctx the parse tree
	 */
	void enterMeasureDefinition(StructuresSqlParserParser.MeasureDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#measureDefinition}.
	 * @param ctx the parse tree
	 */
	void exitMeasureDefinition(StructuresSqlParserParser.MeasureDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#rowsPerMatch}.
	 * @param ctx the parse tree
	 */
	void enterRowsPerMatch(StructuresSqlParserParser.RowsPerMatchContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#rowsPerMatch}.
	 * @param ctx the parse tree
	 */
	void exitRowsPerMatch(StructuresSqlParserParser.RowsPerMatchContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#emptyMatchHandling}.
	 * @param ctx the parse tree
	 */
	void enterEmptyMatchHandling(StructuresSqlParserParser.EmptyMatchHandlingContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#emptyMatchHandling}.
	 * @param ctx the parse tree
	 */
	void exitEmptyMatchHandling(StructuresSqlParserParser.EmptyMatchHandlingContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#skipTo}.
	 * @param ctx the parse tree
	 */
	void enterSkipTo(StructuresSqlParserParser.SkipToContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#skipTo}.
	 * @param ctx the parse tree
	 */
	void exitSkipTo(StructuresSqlParserParser.SkipToContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#subsetDefinition}.
	 * @param ctx the parse tree
	 */
	void enterSubsetDefinition(StructuresSqlParserParser.SubsetDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#subsetDefinition}.
	 * @param ctx the parse tree
	 */
	void exitSubsetDefinition(StructuresSqlParserParser.SubsetDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void enterVariableDefinition(StructuresSqlParserParser.VariableDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void exitVariableDefinition(StructuresSqlParserParser.VariableDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#aliasedRelation}.
	 * @param ctx the parse tree
	 */
	void enterAliasedRelation(StructuresSqlParserParser.AliasedRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#aliasedRelation}.
	 * @param ctx the parse tree
	 */
	void exitAliasedRelation(StructuresSqlParserParser.AliasedRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#columnAliases}.
	 * @param ctx the parse tree
	 */
	void enterColumnAliases(StructuresSqlParserParser.ColumnAliasesContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#columnAliases}.
	 * @param ctx the parse tree
	 */
	void exitColumnAliases(StructuresSqlParserParser.ColumnAliasesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableName}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterTableName(StructuresSqlParserParser.TableNameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableName}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitTableName(StructuresSqlParserParser.TableNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subqueryRelation}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterSubqueryRelation(StructuresSqlParserParser.SubqueryRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subqueryRelation}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitSubqueryRelation(StructuresSqlParserParser.SubqueryRelationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unnest}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterUnnest(StructuresSqlParserParser.UnnestContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unnest}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitUnnest(StructuresSqlParserParser.UnnestContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lateral}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterLateral(StructuresSqlParserParser.LateralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lateral}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitLateral(StructuresSqlParserParser.LateralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableFunctionInvocation}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterTableFunctionInvocation(StructuresSqlParserParser.TableFunctionInvocationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableFunctionInvocation}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitTableFunctionInvocation(StructuresSqlParserParser.TableFunctionInvocationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesizedRelation}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedRelation(StructuresSqlParserParser.ParenthesizedRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesizedRelation}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedRelation(StructuresSqlParserParser.ParenthesizedRelationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jsonTable}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void enterJsonTable(StructuresSqlParserParser.JsonTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jsonTable}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 */
	void exitJsonTable(StructuresSqlParserParser.JsonTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ordinalityColumn}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 */
	void enterOrdinalityColumn(StructuresSqlParserParser.OrdinalityColumnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ordinalityColumn}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 */
	void exitOrdinalityColumn(StructuresSqlParserParser.OrdinalityColumnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueColumn}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 */
	void enterValueColumn(StructuresSqlParserParser.ValueColumnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueColumn}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 */
	void exitValueColumn(StructuresSqlParserParser.ValueColumnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code queryColumn}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 */
	void enterQueryColumn(StructuresSqlParserParser.QueryColumnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code queryColumn}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 */
	void exitQueryColumn(StructuresSqlParserParser.QueryColumnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nestedColumns}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 */
	void enterNestedColumns(StructuresSqlParserParser.NestedColumnsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nestedColumns}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 */
	void exitNestedColumns(StructuresSqlParserParser.NestedColumnsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code leafPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 */
	void enterLeafPlan(StructuresSqlParserParser.LeafPlanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code leafPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 */
	void exitLeafPlan(StructuresSqlParserParser.LeafPlanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code joinPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 */
	void enterJoinPlan(StructuresSqlParserParser.JoinPlanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code joinPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 */
	void exitJoinPlan(StructuresSqlParserParser.JoinPlanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unionPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 */
	void enterUnionPlan(StructuresSqlParserParser.UnionPlanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unionPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 */
	void exitUnionPlan(StructuresSqlParserParser.UnionPlanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code crossPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 */
	void enterCrossPlan(StructuresSqlParserParser.CrossPlanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code crossPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 */
	void exitCrossPlan(StructuresSqlParserParser.CrossPlanContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#jsonTablePathName}.
	 * @param ctx the parse tree
	 */
	void enterJsonTablePathName(StructuresSqlParserParser.JsonTablePathNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#jsonTablePathName}.
	 * @param ctx the parse tree
	 */
	void exitJsonTablePathName(StructuresSqlParserParser.JsonTablePathNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#planPrimary}.
	 * @param ctx the parse tree
	 */
	void enterPlanPrimary(StructuresSqlParserParser.PlanPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#planPrimary}.
	 * @param ctx the parse tree
	 */
	void exitPlanPrimary(StructuresSqlParserParser.PlanPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#jsonTableDefaultPlan}.
	 * @param ctx the parse tree
	 */
	void enterJsonTableDefaultPlan(StructuresSqlParserParser.JsonTableDefaultPlanContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#jsonTableDefaultPlan}.
	 * @param ctx the parse tree
	 */
	void exitJsonTableDefaultPlan(StructuresSqlParserParser.JsonTableDefaultPlanContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#tableFunctionCall}.
	 * @param ctx the parse tree
	 */
	void enterTableFunctionCall(StructuresSqlParserParser.TableFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#tableFunctionCall}.
	 * @param ctx the parse tree
	 */
	void exitTableFunctionCall(StructuresSqlParserParser.TableFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#tableFunctionArgument}.
	 * @param ctx the parse tree
	 */
	void enterTableFunctionArgument(StructuresSqlParserParser.TableFunctionArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#tableFunctionArgument}.
	 * @param ctx the parse tree
	 */
	void exitTableFunctionArgument(StructuresSqlParserParser.TableFunctionArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#tableArgument}.
	 * @param ctx the parse tree
	 */
	void enterTableArgument(StructuresSqlParserParser.TableArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#tableArgument}.
	 * @param ctx the parse tree
	 */
	void exitTableArgument(StructuresSqlParserParser.TableArgumentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableArgumentTable}
	 * labeled alternative in {@link StructuresSqlParserParser#tableArgumentRelation}.
	 * @param ctx the parse tree
	 */
	void enterTableArgumentTable(StructuresSqlParserParser.TableArgumentTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableArgumentTable}
	 * labeled alternative in {@link StructuresSqlParserParser#tableArgumentRelation}.
	 * @param ctx the parse tree
	 */
	void exitTableArgumentTable(StructuresSqlParserParser.TableArgumentTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableArgumentQuery}
	 * labeled alternative in {@link StructuresSqlParserParser#tableArgumentRelation}.
	 * @param ctx the parse tree
	 */
	void enterTableArgumentQuery(StructuresSqlParserParser.TableArgumentQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableArgumentQuery}
	 * labeled alternative in {@link StructuresSqlParserParser#tableArgumentRelation}.
	 * @param ctx the parse tree
	 */
	void exitTableArgumentQuery(StructuresSqlParserParser.TableArgumentQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#descriptorArgument}.
	 * @param ctx the parse tree
	 */
	void enterDescriptorArgument(StructuresSqlParserParser.DescriptorArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#descriptorArgument}.
	 * @param ctx the parse tree
	 */
	void exitDescriptorArgument(StructuresSqlParserParser.DescriptorArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#descriptorField}.
	 * @param ctx the parse tree
	 */
	void enterDescriptorField(StructuresSqlParserParser.DescriptorFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#descriptorField}.
	 * @param ctx the parse tree
	 */
	void exitDescriptorField(StructuresSqlParserParser.DescriptorFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#copartitionTables}.
	 * @param ctx the parse tree
	 */
	void enterCopartitionTables(StructuresSqlParserParser.CopartitionTablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#copartitionTables}.
	 * @param ctx the parse tree
	 */
	void exitCopartitionTables(StructuresSqlParserParser.CopartitionTablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(StructuresSqlParserParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(StructuresSqlParserParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalNot}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalNot(StructuresSqlParserParser.LogicalNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalNot}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalNot(StructuresSqlParserParser.LogicalNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code predicated}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterPredicated(StructuresSqlParserParser.PredicatedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code predicated}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitPredicated(StructuresSqlParserParser.PredicatedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code or}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterOr(StructuresSqlParserParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code or}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitOr(StructuresSqlParserParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code and}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterAnd(StructuresSqlParserParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code and}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitAnd(StructuresSqlParserParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterComparison(StructuresSqlParserParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitComparison(StructuresSqlParserParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code quantifiedComparison}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterQuantifiedComparison(StructuresSqlParserParser.QuantifiedComparisonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code quantifiedComparison}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitQuantifiedComparison(StructuresSqlParserParser.QuantifiedComparisonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code between}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterBetween(StructuresSqlParserParser.BetweenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code between}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitBetween(StructuresSqlParserParser.BetweenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inList}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterInList(StructuresSqlParserParser.InListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inList}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitInList(StructuresSqlParserParser.InListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inSubquery}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterInSubquery(StructuresSqlParserParser.InSubqueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inSubquery}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitInSubquery(StructuresSqlParserParser.InSubqueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code like}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterLike(StructuresSqlParserParser.LikeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code like}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitLike(StructuresSqlParserParser.LikeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullPredicate}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterNullPredicate(StructuresSqlParserParser.NullPredicateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullPredicate}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitNullPredicate(StructuresSqlParserParser.NullPredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code distinctFrom}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterDistinctFrom(StructuresSqlParserParser.DistinctFromContext ctx);
	/**
	 * Exit a parse tree produced by the {@code distinctFrom}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitDistinctFrom(StructuresSqlParserParser.DistinctFromContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueExpressionDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterValueExpressionDefault(StructuresSqlParserParser.ValueExpressionDefaultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueExpressionDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitValueExpressionDefault(StructuresSqlParserParser.ValueExpressionDefaultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code concatenation}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterConcatenation(StructuresSqlParserParser.ConcatenationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code concatenation}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitConcatenation(StructuresSqlParserParser.ConcatenationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithmeticBinary}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticBinary(StructuresSqlParserParser.ArithmeticBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithmeticBinary}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticBinary(StructuresSqlParserParser.ArithmeticBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithmeticUnary}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticUnary(StructuresSqlParserParser.ArithmeticUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithmeticUnary}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticUnary(StructuresSqlParserParser.ArithmeticUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atTimeZone}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterAtTimeZone(StructuresSqlParserParser.AtTimeZoneContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atTimeZone}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitAtTimeZone(StructuresSqlParserParser.AtTimeZoneContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dereference}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterDereference(StructuresSqlParserParser.DereferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dereference}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitDereference(StructuresSqlParserParser.DereferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code typeConstructor}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterTypeConstructor(StructuresSqlParserParser.TypeConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code typeConstructor}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitTypeConstructor(StructuresSqlParserParser.TypeConstructorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jsonValue}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterJsonValue(StructuresSqlParserParser.JsonValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jsonValue}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitJsonValue(StructuresSqlParserParser.JsonValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentDate}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterCurrentDate(StructuresSqlParserParser.CurrentDateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentDate}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitCurrentDate(StructuresSqlParserParser.CurrentDateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code substring}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubstring(StructuresSqlParserParser.SubstringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code substring}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubstring(StructuresSqlParserParser.SubstringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cast}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterCast(StructuresSqlParserParser.CastContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cast}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitCast(StructuresSqlParserParser.CastContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lambda}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambda(StructuresSqlParserParser.LambdaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lambda}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambda(StructuresSqlParserParser.LambdaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesizedExpression}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(StructuresSqlParserParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesizedExpression}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(StructuresSqlParserParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trim}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterTrim(StructuresSqlParserParser.TrimContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trim}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitTrim(StructuresSqlParserParser.TrimContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parameter}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterParameter(StructuresSqlParserParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parameter}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitParameter(StructuresSqlParserParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code normalize}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterNormalize(StructuresSqlParserParser.NormalizeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code normalize}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitNormalize(StructuresSqlParserParser.NormalizeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code localTimestamp}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterLocalTimestamp(StructuresSqlParserParser.LocalTimestampContext ctx);
	/**
	 * Exit a parse tree produced by the {@code localTimestamp}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitLocalTimestamp(StructuresSqlParserParser.LocalTimestampContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jsonObject}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterJsonObject(StructuresSqlParserParser.JsonObjectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jsonObject}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitJsonObject(StructuresSqlParserParser.JsonObjectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intervalLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterIntervalLiteral(StructuresSqlParserParser.IntervalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intervalLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitIntervalLiteral(StructuresSqlParserParser.IntervalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numericLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteral(StructuresSqlParserParser.NumericLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numericLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteral(StructuresSqlParserParser.NumericLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(StructuresSqlParserParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(StructuresSqlParserParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jsonArray}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterJsonArray(StructuresSqlParserParser.JsonArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jsonArray}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitJsonArray(StructuresSqlParserParser.JsonArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleCase}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSimpleCase(StructuresSqlParserParser.SimpleCaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleCase}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSimpleCase(StructuresSqlParserParser.SimpleCaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code columnReference}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterColumnReference(StructuresSqlParserParser.ColumnReferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code columnReference}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitColumnReference(StructuresSqlParserParser.ColumnReferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(StructuresSqlParserParser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(StructuresSqlParserParser.NullLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rowConstructor}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterRowConstructor(StructuresSqlParserParser.RowConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rowConstructor}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitRowConstructor(StructuresSqlParserParser.RowConstructorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subscript}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubscript(StructuresSqlParserParser.SubscriptContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subscript}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubscript(StructuresSqlParserParser.SubscriptContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jsonExists}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterJsonExists(StructuresSqlParserParser.JsonExistsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jsonExists}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitJsonExists(StructuresSqlParserParser.JsonExistsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentPath}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterCurrentPath(StructuresSqlParserParser.CurrentPathContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentPath}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitCurrentPath(StructuresSqlParserParser.CurrentPathContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subqueryExpression}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubqueryExpression(StructuresSqlParserParser.SubqueryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subqueryExpression}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubqueryExpression(StructuresSqlParserParser.SubqueryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryLiteral(StructuresSqlParserParser.BinaryLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryLiteral(StructuresSqlParserParser.BinaryLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentTime}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterCurrentTime(StructuresSqlParserParser.CurrentTimeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentTime}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitCurrentTime(StructuresSqlParserParser.CurrentTimeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code localTime}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterLocalTime(StructuresSqlParserParser.LocalTimeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code localTime}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitLocalTime(StructuresSqlParserParser.LocalTimeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentUser}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterCurrentUser(StructuresSqlParserParser.CurrentUserContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentUser}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitCurrentUser(StructuresSqlParserParser.CurrentUserContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jsonQuery}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterJsonQuery(StructuresSqlParserParser.JsonQueryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jsonQuery}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitJsonQuery(StructuresSqlParserParser.JsonQueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code measure}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterMeasure(StructuresSqlParserParser.MeasureContext ctx);
	/**
	 * Exit a parse tree produced by the {@code measure}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitMeasure(StructuresSqlParserParser.MeasureContext ctx);
	/**
	 * Enter a parse tree produced by the {@code extract}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterExtract(StructuresSqlParserParser.ExtractContext ctx);
	/**
	 * Exit a parse tree produced by the {@code extract}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitExtract(StructuresSqlParserParser.ExtractContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(StructuresSqlParserParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(StructuresSqlParserParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayConstructor}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterArrayConstructor(StructuresSqlParserParser.ArrayConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayConstructor}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitArrayConstructor(StructuresSqlParserParser.ArrayConstructorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCall}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(StructuresSqlParserParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCall}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(StructuresSqlParserParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentTimestamp}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterCurrentTimestamp(StructuresSqlParserParser.CurrentTimestampContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentTimestamp}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitCurrentTimestamp(StructuresSqlParserParser.CurrentTimestampContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentSchema}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterCurrentSchema(StructuresSqlParserParser.CurrentSchemaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentSchema}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitCurrentSchema(StructuresSqlParserParser.CurrentSchemaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exists}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterExists(StructuresSqlParserParser.ExistsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exists}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitExists(StructuresSqlParserParser.ExistsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code position}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPosition(StructuresSqlParserParser.PositionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code position}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPosition(StructuresSqlParserParser.PositionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code listagg}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterListagg(StructuresSqlParserParser.ListaggContext ctx);
	/**
	 * Exit a parse tree produced by the {@code listagg}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitListagg(StructuresSqlParserParser.ListaggContext ctx);
	/**
	 * Enter a parse tree produced by the {@code searchedCase}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSearchedCase(StructuresSqlParserParser.SearchedCaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code searchedCase}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSearchedCase(StructuresSqlParserParser.SearchedCaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentCatalog}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterCurrentCatalog(StructuresSqlParserParser.CurrentCatalogContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentCatalog}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitCurrentCatalog(StructuresSqlParserParser.CurrentCatalogContext ctx);
	/**
	 * Enter a parse tree produced by the {@code groupingOperation}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterGroupingOperation(StructuresSqlParserParser.GroupingOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code groupingOperation}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitGroupingOperation(StructuresSqlParserParser.GroupingOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#jsonPathInvocation}.
	 * @param ctx the parse tree
	 */
	void enterJsonPathInvocation(StructuresSqlParserParser.JsonPathInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#jsonPathInvocation}.
	 * @param ctx the parse tree
	 */
	void exitJsonPathInvocation(StructuresSqlParserParser.JsonPathInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#jsonValueExpression}.
	 * @param ctx the parse tree
	 */
	void enterJsonValueExpression(StructuresSqlParserParser.JsonValueExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#jsonValueExpression}.
	 * @param ctx the parse tree
	 */
	void exitJsonValueExpression(StructuresSqlParserParser.JsonValueExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#jsonRepresentation}.
	 * @param ctx the parse tree
	 */
	void enterJsonRepresentation(StructuresSqlParserParser.JsonRepresentationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#jsonRepresentation}.
	 * @param ctx the parse tree
	 */
	void exitJsonRepresentation(StructuresSqlParserParser.JsonRepresentationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#jsonArgument}.
	 * @param ctx the parse tree
	 */
	void enterJsonArgument(StructuresSqlParserParser.JsonArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#jsonArgument}.
	 * @param ctx the parse tree
	 */
	void exitJsonArgument(StructuresSqlParserParser.JsonArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#jsonExistsErrorBehavior}.
	 * @param ctx the parse tree
	 */
	void enterJsonExistsErrorBehavior(StructuresSqlParserParser.JsonExistsErrorBehaviorContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#jsonExistsErrorBehavior}.
	 * @param ctx the parse tree
	 */
	void exitJsonExistsErrorBehavior(StructuresSqlParserParser.JsonExistsErrorBehaviorContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#jsonValueBehavior}.
	 * @param ctx the parse tree
	 */
	void enterJsonValueBehavior(StructuresSqlParserParser.JsonValueBehaviorContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#jsonValueBehavior}.
	 * @param ctx the parse tree
	 */
	void exitJsonValueBehavior(StructuresSqlParserParser.JsonValueBehaviorContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#jsonQueryWrapperBehavior}.
	 * @param ctx the parse tree
	 */
	void enterJsonQueryWrapperBehavior(StructuresSqlParserParser.JsonQueryWrapperBehaviorContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#jsonQueryWrapperBehavior}.
	 * @param ctx the parse tree
	 */
	void exitJsonQueryWrapperBehavior(StructuresSqlParserParser.JsonQueryWrapperBehaviorContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#jsonQueryBehavior}.
	 * @param ctx the parse tree
	 */
	void enterJsonQueryBehavior(StructuresSqlParserParser.JsonQueryBehaviorContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#jsonQueryBehavior}.
	 * @param ctx the parse tree
	 */
	void exitJsonQueryBehavior(StructuresSqlParserParser.JsonQueryBehaviorContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#jsonObjectMember}.
	 * @param ctx the parse tree
	 */
	void enterJsonObjectMember(StructuresSqlParserParser.JsonObjectMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#jsonObjectMember}.
	 * @param ctx the parse tree
	 */
	void exitJsonObjectMember(StructuresSqlParserParser.JsonObjectMemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#processingMode}.
	 * @param ctx the parse tree
	 */
	void enterProcessingMode(StructuresSqlParserParser.ProcessingModeContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#processingMode}.
	 * @param ctx the parse tree
	 */
	void exitProcessingMode(StructuresSqlParserParser.ProcessingModeContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#nullTreatment}.
	 * @param ctx the parse tree
	 */
	void enterNullTreatment(StructuresSqlParserParser.NullTreatmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#nullTreatment}.
	 * @param ctx the parse tree
	 */
	void exitNullTreatment(StructuresSqlParserParser.NullTreatmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code basicStringLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#string}.
	 * @param ctx the parse tree
	 */
	void enterBasicStringLiteral(StructuresSqlParserParser.BasicStringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code basicStringLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#string}.
	 * @param ctx the parse tree
	 */
	void exitBasicStringLiteral(StructuresSqlParserParser.BasicStringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unicodeStringLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#string}.
	 * @param ctx the parse tree
	 */
	void enterUnicodeStringLiteral(StructuresSqlParserParser.UnicodeStringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unicodeStringLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#string}.
	 * @param ctx the parse tree
	 */
	void exitUnicodeStringLiteral(StructuresSqlParserParser.UnicodeStringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code timeZoneInterval}
	 * labeled alternative in {@link StructuresSqlParserParser#timeZoneSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterTimeZoneInterval(StructuresSqlParserParser.TimeZoneIntervalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code timeZoneInterval}
	 * labeled alternative in {@link StructuresSqlParserParser#timeZoneSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitTimeZoneInterval(StructuresSqlParserParser.TimeZoneIntervalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code timeZoneString}
	 * labeled alternative in {@link StructuresSqlParserParser#timeZoneSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterTimeZoneString(StructuresSqlParserParser.TimeZoneStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code timeZoneString}
	 * labeled alternative in {@link StructuresSqlParserParser#timeZoneSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitTimeZoneString(StructuresSqlParserParser.TimeZoneStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void enterComparisonOperator(StructuresSqlParserParser.ComparisonOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void exitComparisonOperator(StructuresSqlParserParser.ComparisonOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#comparisonQuantifier}.
	 * @param ctx the parse tree
	 */
	void enterComparisonQuantifier(StructuresSqlParserParser.ComparisonQuantifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#comparisonQuantifier}.
	 * @param ctx the parse tree
	 */
	void exitComparisonQuantifier(StructuresSqlParserParser.ComparisonQuantifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void enterBooleanValue(StructuresSqlParserParser.BooleanValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void exitBooleanValue(StructuresSqlParserParser.BooleanValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#interval}.
	 * @param ctx the parse tree
	 */
	void enterInterval(StructuresSqlParserParser.IntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#interval}.
	 * @param ctx the parse tree
	 */
	void exitInterval(StructuresSqlParserParser.IntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#intervalField}.
	 * @param ctx the parse tree
	 */
	void enterIntervalField(StructuresSqlParserParser.IntervalFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#intervalField}.
	 * @param ctx the parse tree
	 */
	void exitIntervalField(StructuresSqlParserParser.IntervalFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#normalForm}.
	 * @param ctx the parse tree
	 */
	void enterNormalForm(StructuresSqlParserParser.NormalFormContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#normalForm}.
	 * @param ctx the parse tree
	 */
	void exitNormalForm(StructuresSqlParserParser.NormalFormContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rowType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void enterRowType(StructuresSqlParserParser.RowTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rowType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void exitRowType(StructuresSqlParserParser.RowTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intervalType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntervalType(StructuresSqlParserParser.IntervalTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intervalType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntervalType(StructuresSqlParserParser.IntervalTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(StructuresSqlParserParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(StructuresSqlParserParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doublePrecisionType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void enterDoublePrecisionType(StructuresSqlParserParser.DoublePrecisionTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doublePrecisionType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void exitDoublePrecisionType(StructuresSqlParserParser.DoublePrecisionTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code legacyArrayType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void enterLegacyArrayType(StructuresSqlParserParser.LegacyArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code legacyArrayType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void exitLegacyArrayType(StructuresSqlParserParser.LegacyArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code genericType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void enterGenericType(StructuresSqlParserParser.GenericTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code genericType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void exitGenericType(StructuresSqlParserParser.GenericTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dateTimeType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void enterDateTimeType(StructuresSqlParserParser.DateTimeTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dateTimeType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void exitDateTimeType(StructuresSqlParserParser.DateTimeTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code legacyMapType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void enterLegacyMapType(StructuresSqlParserParser.LegacyMapTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code legacyMapType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 */
	void exitLegacyMapType(StructuresSqlParserParser.LegacyMapTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#rowField}.
	 * @param ctx the parse tree
	 */
	void enterRowField(StructuresSqlParserParser.RowFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#rowField}.
	 * @param ctx the parse tree
	 */
	void exitRowField(StructuresSqlParserParser.RowFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(StructuresSqlParserParser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(StructuresSqlParserParser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void enterWhenClause(StructuresSqlParserParser.WhenClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void exitWhenClause(StructuresSqlParserParser.WhenClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilter(StructuresSqlParserParser.FilterContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilter(StructuresSqlParserParser.FilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mergeUpdate}
	 * labeled alternative in {@link StructuresSqlParserParser#mergeCase}.
	 * @param ctx the parse tree
	 */
	void enterMergeUpdate(StructuresSqlParserParser.MergeUpdateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mergeUpdate}
	 * labeled alternative in {@link StructuresSqlParserParser#mergeCase}.
	 * @param ctx the parse tree
	 */
	void exitMergeUpdate(StructuresSqlParserParser.MergeUpdateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mergeDelete}
	 * labeled alternative in {@link StructuresSqlParserParser#mergeCase}.
	 * @param ctx the parse tree
	 */
	void enterMergeDelete(StructuresSqlParserParser.MergeDeleteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mergeDelete}
	 * labeled alternative in {@link StructuresSqlParserParser#mergeCase}.
	 * @param ctx the parse tree
	 */
	void exitMergeDelete(StructuresSqlParserParser.MergeDeleteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mergeInsert}
	 * labeled alternative in {@link StructuresSqlParserParser#mergeCase}.
	 * @param ctx the parse tree
	 */
	void enterMergeInsert(StructuresSqlParserParser.MergeInsertContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mergeInsert}
	 * labeled alternative in {@link StructuresSqlParserParser#mergeCase}.
	 * @param ctx the parse tree
	 */
	void exitMergeInsert(StructuresSqlParserParser.MergeInsertContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#over}.
	 * @param ctx the parse tree
	 */
	void enterOver(StructuresSqlParserParser.OverContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#over}.
	 * @param ctx the parse tree
	 */
	void exitOver(StructuresSqlParserParser.OverContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#windowFrame}.
	 * @param ctx the parse tree
	 */
	void enterWindowFrame(StructuresSqlParserParser.WindowFrameContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#windowFrame}.
	 * @param ctx the parse tree
	 */
	void exitWindowFrame(StructuresSqlParserParser.WindowFrameContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#frameExtent}.
	 * @param ctx the parse tree
	 */
	void enterFrameExtent(StructuresSqlParserParser.FrameExtentContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#frameExtent}.
	 * @param ctx the parse tree
	 */
	void exitFrameExtent(StructuresSqlParserParser.FrameExtentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unboundedFrame}
	 * labeled alternative in {@link StructuresSqlParserParser#frameBound}.
	 * @param ctx the parse tree
	 */
	void enterUnboundedFrame(StructuresSqlParserParser.UnboundedFrameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unboundedFrame}
	 * labeled alternative in {@link StructuresSqlParserParser#frameBound}.
	 * @param ctx the parse tree
	 */
	void exitUnboundedFrame(StructuresSqlParserParser.UnboundedFrameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentRowBound}
	 * labeled alternative in {@link StructuresSqlParserParser#frameBound}.
	 * @param ctx the parse tree
	 */
	void enterCurrentRowBound(StructuresSqlParserParser.CurrentRowBoundContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentRowBound}
	 * labeled alternative in {@link StructuresSqlParserParser#frameBound}.
	 * @param ctx the parse tree
	 */
	void exitCurrentRowBound(StructuresSqlParserParser.CurrentRowBoundContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boundedFrame}
	 * labeled alternative in {@link StructuresSqlParserParser#frameBound}.
	 * @param ctx the parse tree
	 */
	void enterBoundedFrame(StructuresSqlParserParser.BoundedFrameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boundedFrame}
	 * labeled alternative in {@link StructuresSqlParserParser#frameBound}.
	 * @param ctx the parse tree
	 */
	void exitBoundedFrame(StructuresSqlParserParser.BoundedFrameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code quantifiedPrimary}
	 * labeled alternative in {@link StructuresSqlParserParser#rowPattern}.
	 * @param ctx the parse tree
	 */
	void enterQuantifiedPrimary(StructuresSqlParserParser.QuantifiedPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code quantifiedPrimary}
	 * labeled alternative in {@link StructuresSqlParserParser#rowPattern}.
	 * @param ctx the parse tree
	 */
	void exitQuantifiedPrimary(StructuresSqlParserParser.QuantifiedPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code patternConcatenation}
	 * labeled alternative in {@link StructuresSqlParserParser#rowPattern}.
	 * @param ctx the parse tree
	 */
	void enterPatternConcatenation(StructuresSqlParserParser.PatternConcatenationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code patternConcatenation}
	 * labeled alternative in {@link StructuresSqlParserParser#rowPattern}.
	 * @param ctx the parse tree
	 */
	void exitPatternConcatenation(StructuresSqlParserParser.PatternConcatenationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code patternAlternation}
	 * labeled alternative in {@link StructuresSqlParserParser#rowPattern}.
	 * @param ctx the parse tree
	 */
	void enterPatternAlternation(StructuresSqlParserParser.PatternAlternationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code patternAlternation}
	 * labeled alternative in {@link StructuresSqlParserParser#rowPattern}.
	 * @param ctx the parse tree
	 */
	void exitPatternAlternation(StructuresSqlParserParser.PatternAlternationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code patternVariable}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void enterPatternVariable(StructuresSqlParserParser.PatternVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code patternVariable}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void exitPatternVariable(StructuresSqlParserParser.PatternVariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyPattern}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void enterEmptyPattern(StructuresSqlParserParser.EmptyPatternContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyPattern}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void exitEmptyPattern(StructuresSqlParserParser.EmptyPatternContext ctx);
	/**
	 * Enter a parse tree produced by the {@code patternPermutation}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void enterPatternPermutation(StructuresSqlParserParser.PatternPermutationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code patternPermutation}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void exitPatternPermutation(StructuresSqlParserParser.PatternPermutationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code groupedPattern}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void enterGroupedPattern(StructuresSqlParserParser.GroupedPatternContext ctx);
	/**
	 * Exit a parse tree produced by the {@code groupedPattern}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void exitGroupedPattern(StructuresSqlParserParser.GroupedPatternContext ctx);
	/**
	 * Enter a parse tree produced by the {@code partitionStartAnchor}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void enterPartitionStartAnchor(StructuresSqlParserParser.PartitionStartAnchorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code partitionStartAnchor}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void exitPartitionStartAnchor(StructuresSqlParserParser.PartitionStartAnchorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code partitionEndAnchor}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void enterPartitionEndAnchor(StructuresSqlParserParser.PartitionEndAnchorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code partitionEndAnchor}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void exitPartitionEndAnchor(StructuresSqlParserParser.PartitionEndAnchorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code excludedPattern}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void enterExcludedPattern(StructuresSqlParserParser.ExcludedPatternContext ctx);
	/**
	 * Exit a parse tree produced by the {@code excludedPattern}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 */
	void exitExcludedPattern(StructuresSqlParserParser.ExcludedPatternContext ctx);
	/**
	 * Enter a parse tree produced by the {@code zeroOrMoreQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 */
	void enterZeroOrMoreQuantifier(StructuresSqlParserParser.ZeroOrMoreQuantifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code zeroOrMoreQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 */
	void exitZeroOrMoreQuantifier(StructuresSqlParserParser.ZeroOrMoreQuantifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code oneOrMoreQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 */
	void enterOneOrMoreQuantifier(StructuresSqlParserParser.OneOrMoreQuantifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code oneOrMoreQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 */
	void exitOneOrMoreQuantifier(StructuresSqlParserParser.OneOrMoreQuantifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code zeroOrOneQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 */
	void enterZeroOrOneQuantifier(StructuresSqlParserParser.ZeroOrOneQuantifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code zeroOrOneQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 */
	void exitZeroOrOneQuantifier(StructuresSqlParserParser.ZeroOrOneQuantifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rangeQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 */
	void enterRangeQuantifier(StructuresSqlParserParser.RangeQuantifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rangeQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 */
	void exitRangeQuantifier(StructuresSqlParserParser.RangeQuantifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#updateAssignment}.
	 * @param ctx the parse tree
	 */
	void enterUpdateAssignment(StructuresSqlParserParser.UpdateAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#updateAssignment}.
	 * @param ctx the parse tree
	 */
	void exitUpdateAssignment(StructuresSqlParserParser.UpdateAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code explainFormat}
	 * labeled alternative in {@link StructuresSqlParserParser#explainOption}.
	 * @param ctx the parse tree
	 */
	void enterExplainFormat(StructuresSqlParserParser.ExplainFormatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code explainFormat}
	 * labeled alternative in {@link StructuresSqlParserParser#explainOption}.
	 * @param ctx the parse tree
	 */
	void exitExplainFormat(StructuresSqlParserParser.ExplainFormatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code explainType}
	 * labeled alternative in {@link StructuresSqlParserParser#explainOption}.
	 * @param ctx the parse tree
	 */
	void enterExplainType(StructuresSqlParserParser.ExplainTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code explainType}
	 * labeled alternative in {@link StructuresSqlParserParser#explainOption}.
	 * @param ctx the parse tree
	 */
	void exitExplainType(StructuresSqlParserParser.ExplainTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code isolationLevel}
	 * labeled alternative in {@link StructuresSqlParserParser#transactionMode}.
	 * @param ctx the parse tree
	 */
	void enterIsolationLevel(StructuresSqlParserParser.IsolationLevelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code isolationLevel}
	 * labeled alternative in {@link StructuresSqlParserParser#transactionMode}.
	 * @param ctx the parse tree
	 */
	void exitIsolationLevel(StructuresSqlParserParser.IsolationLevelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code transactionAccessMode}
	 * labeled alternative in {@link StructuresSqlParserParser#transactionMode}.
	 * @param ctx the parse tree
	 */
	void enterTransactionAccessMode(StructuresSqlParserParser.TransactionAccessModeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code transactionAccessMode}
	 * labeled alternative in {@link StructuresSqlParserParser#transactionMode}.
	 * @param ctx the parse tree
	 */
	void exitTransactionAccessMode(StructuresSqlParserParser.TransactionAccessModeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code readUncommitted}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 */
	void enterReadUncommitted(StructuresSqlParserParser.ReadUncommittedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code readUncommitted}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 */
	void exitReadUncommitted(StructuresSqlParserParser.ReadUncommittedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code readCommitted}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 */
	void enterReadCommitted(StructuresSqlParserParser.ReadCommittedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code readCommitted}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 */
	void exitReadCommitted(StructuresSqlParserParser.ReadCommittedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code repeatableRead}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 */
	void enterRepeatableRead(StructuresSqlParserParser.RepeatableReadContext ctx);
	/**
	 * Exit a parse tree produced by the {@code repeatableRead}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 */
	void exitRepeatableRead(StructuresSqlParserParser.RepeatableReadContext ctx);
	/**
	 * Enter a parse tree produced by the {@code serializable}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 */
	void enterSerializable(StructuresSqlParserParser.SerializableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code serializable}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 */
	void exitSerializable(StructuresSqlParserParser.SerializableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code positionalArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#callArgument}.
	 * @param ctx the parse tree
	 */
	void enterPositionalArgument(StructuresSqlParserParser.PositionalArgumentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code positionalArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#callArgument}.
	 * @param ctx the parse tree
	 */
	void exitPositionalArgument(StructuresSqlParserParser.PositionalArgumentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code namedArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#callArgument}.
	 * @param ctx the parse tree
	 */
	void enterNamedArgument(StructuresSqlParserParser.NamedArgumentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code namedArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#callArgument}.
	 * @param ctx the parse tree
	 */
	void exitNamedArgument(StructuresSqlParserParser.NamedArgumentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code qualifiedArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#pathElement}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedArgument(StructuresSqlParserParser.QualifiedArgumentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code qualifiedArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#pathElement}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedArgument(StructuresSqlParserParser.QualifiedArgumentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unqualifiedArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#pathElement}.
	 * @param ctx the parse tree
	 */
	void enterUnqualifiedArgument(StructuresSqlParserParser.UnqualifiedArgumentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unqualifiedArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#pathElement}.
	 * @param ctx the parse tree
	 */
	void exitUnqualifiedArgument(StructuresSqlParserParser.UnqualifiedArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#pathSpecification}.
	 * @param ctx the parse tree
	 */
	void enterPathSpecification(StructuresSqlParserParser.PathSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#pathSpecification}.
	 * @param ctx the parse tree
	 */
	void exitPathSpecification(StructuresSqlParserParser.PathSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#functionSpecification}.
	 * @param ctx the parse tree
	 */
	void enterFunctionSpecification(StructuresSqlParserParser.FunctionSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#functionSpecification}.
	 * @param ctx the parse tree
	 */
	void exitFunctionSpecification(StructuresSqlParserParser.FunctionSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(StructuresSqlParserParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(StructuresSqlParserParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclaration(StructuresSqlParserParser.ParameterDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclaration(StructuresSqlParserParser.ParameterDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#returnsClause}.
	 * @param ctx the parse tree
	 */
	void enterReturnsClause(StructuresSqlParserParser.ReturnsClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#returnsClause}.
	 * @param ctx the parse tree
	 */
	void exitReturnsClause(StructuresSqlParserParser.ReturnsClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code languageCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void enterLanguageCharacteristic(StructuresSqlParserParser.LanguageCharacteristicContext ctx);
	/**
	 * Exit a parse tree produced by the {@code languageCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void exitLanguageCharacteristic(StructuresSqlParserParser.LanguageCharacteristicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code deterministicCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void enterDeterministicCharacteristic(StructuresSqlParserParser.DeterministicCharacteristicContext ctx);
	/**
	 * Exit a parse tree produced by the {@code deterministicCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void exitDeterministicCharacteristic(StructuresSqlParserParser.DeterministicCharacteristicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnsNullOnNullInputCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void enterReturnsNullOnNullInputCharacteristic(StructuresSqlParserParser.ReturnsNullOnNullInputCharacteristicContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnsNullOnNullInputCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void exitReturnsNullOnNullInputCharacteristic(StructuresSqlParserParser.ReturnsNullOnNullInputCharacteristicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code calledOnNullInputCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void enterCalledOnNullInputCharacteristic(StructuresSqlParserParser.CalledOnNullInputCharacteristicContext ctx);
	/**
	 * Exit a parse tree produced by the {@code calledOnNullInputCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void exitCalledOnNullInputCharacteristic(StructuresSqlParserParser.CalledOnNullInputCharacteristicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code securityCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void enterSecurityCharacteristic(StructuresSqlParserParser.SecurityCharacteristicContext ctx);
	/**
	 * Exit a parse tree produced by the {@code securityCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void exitSecurityCharacteristic(StructuresSqlParserParser.SecurityCharacteristicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code commentCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void enterCommentCharacteristic(StructuresSqlParserParser.CommentCharacteristicContext ctx);
	/**
	 * Exit a parse tree produced by the {@code commentCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 */
	void exitCommentCharacteristic(StructuresSqlParserParser.CommentCharacteristicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(StructuresSqlParserParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(StructuresSqlParserParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(StructuresSqlParserParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(StructuresSqlParserParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleCaseStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterSimpleCaseStatement(StructuresSqlParserParser.SimpleCaseStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleCaseStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitSimpleCaseStatement(StructuresSqlParserParser.SimpleCaseStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code searchedCaseStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterSearchedCaseStatement(StructuresSqlParserParser.SearchedCaseStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code searchedCaseStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitSearchedCaseStatement(StructuresSqlParserParser.SearchedCaseStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(StructuresSqlParserParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(StructuresSqlParserParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code iterateStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterIterateStatement(StructuresSqlParserParser.IterateStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code iterateStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitIterateStatement(StructuresSqlParserParser.IterateStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code leaveStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterLeaveStatement(StructuresSqlParserParser.LeaveStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code leaveStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitLeaveStatement(StructuresSqlParserParser.LeaveStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compoundStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(StructuresSqlParserParser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compoundStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(StructuresSqlParserParser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code loopStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(StructuresSqlParserParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code loopStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(StructuresSqlParserParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(StructuresSqlParserParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(StructuresSqlParserParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code repeatStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterRepeatStatement(StructuresSqlParserParser.RepeatStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code repeatStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitRepeatStatement(StructuresSqlParserParser.RepeatStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#caseStatementWhenClause}.
	 * @param ctx the parse tree
	 */
	void enterCaseStatementWhenClause(StructuresSqlParserParser.CaseStatementWhenClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#caseStatementWhenClause}.
	 * @param ctx the parse tree
	 */
	void exitCaseStatementWhenClause(StructuresSqlParserParser.CaseStatementWhenClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#elseIfClause}.
	 * @param ctx the parse tree
	 */
	void enterElseIfClause(StructuresSqlParserParser.ElseIfClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#elseIfClause}.
	 * @param ctx the parse tree
	 */
	void exitElseIfClause(StructuresSqlParserParser.ElseIfClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void enterElseClause(StructuresSqlParserParser.ElseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void exitElseClause(StructuresSqlParserParser.ElseClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(StructuresSqlParserParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(StructuresSqlParserParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#sqlStatementList}.
	 * @param ctx the parse tree
	 */
	void enterSqlStatementList(StructuresSqlParserParser.SqlStatementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#sqlStatementList}.
	 * @param ctx the parse tree
	 */
	void exitSqlStatementList(StructuresSqlParserParser.SqlStatementListContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#privilege}.
	 * @param ctx the parse tree
	 */
	void enterPrivilege(StructuresSqlParserParser.PrivilegeContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#privilege}.
	 * @param ctx the parse tree
	 */
	void exitPrivilege(StructuresSqlParserParser.PrivilegeContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#entityKind}.
	 * @param ctx the parse tree
	 */
	void enterEntityKind(StructuresSqlParserParser.EntityKindContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#entityKind}.
	 * @param ctx the parse tree
	 */
	void exitEntityKind(StructuresSqlParserParser.EntityKindContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#grantObject}.
	 * @param ctx the parse tree
	 */
	void enterGrantObject(StructuresSqlParserParser.GrantObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#grantObject}.
	 * @param ctx the parse tree
	 */
	void exitGrantObject(StructuresSqlParserParser.GrantObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(StructuresSqlParserParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(StructuresSqlParserParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#queryPeriod}.
	 * @param ctx the parse tree
	 */
	void enterQueryPeriod(StructuresSqlParserParser.QueryPeriodContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#queryPeriod}.
	 * @param ctx the parse tree
	 */
	void exitQueryPeriod(StructuresSqlParserParser.QueryPeriodContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#rangeType}.
	 * @param ctx the parse tree
	 */
	void enterRangeType(StructuresSqlParserParser.RangeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#rangeType}.
	 * @param ctx the parse tree
	 */
	void exitRangeType(StructuresSqlParserParser.RangeTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code specifiedPrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#grantor}.
	 * @param ctx the parse tree
	 */
	void enterSpecifiedPrincipal(StructuresSqlParserParser.SpecifiedPrincipalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code specifiedPrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#grantor}.
	 * @param ctx the parse tree
	 */
	void exitSpecifiedPrincipal(StructuresSqlParserParser.SpecifiedPrincipalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentUserGrantor}
	 * labeled alternative in {@link StructuresSqlParserParser#grantor}.
	 * @param ctx the parse tree
	 */
	void enterCurrentUserGrantor(StructuresSqlParserParser.CurrentUserGrantorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentUserGrantor}
	 * labeled alternative in {@link StructuresSqlParserParser#grantor}.
	 * @param ctx the parse tree
	 */
	void exitCurrentUserGrantor(StructuresSqlParserParser.CurrentUserGrantorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code currentRoleGrantor}
	 * labeled alternative in {@link StructuresSqlParserParser#grantor}.
	 * @param ctx the parse tree
	 */
	void enterCurrentRoleGrantor(StructuresSqlParserParser.CurrentRoleGrantorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code currentRoleGrantor}
	 * labeled alternative in {@link StructuresSqlParserParser#grantor}.
	 * @param ctx the parse tree
	 */
	void exitCurrentRoleGrantor(StructuresSqlParserParser.CurrentRoleGrantorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unspecifiedPrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#principal}.
	 * @param ctx the parse tree
	 */
	void enterUnspecifiedPrincipal(StructuresSqlParserParser.UnspecifiedPrincipalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unspecifiedPrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#principal}.
	 * @param ctx the parse tree
	 */
	void exitUnspecifiedPrincipal(StructuresSqlParserParser.UnspecifiedPrincipalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code userPrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#principal}.
	 * @param ctx the parse tree
	 */
	void enterUserPrincipal(StructuresSqlParserParser.UserPrincipalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code userPrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#principal}.
	 * @param ctx the parse tree
	 */
	void exitUserPrincipal(StructuresSqlParserParser.UserPrincipalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rolePrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#principal}.
	 * @param ctx the parse tree
	 */
	void enterRolePrincipal(StructuresSqlParserParser.RolePrincipalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rolePrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#principal}.
	 * @param ctx the parse tree
	 */
	void exitRolePrincipal(StructuresSqlParserParser.RolePrincipalContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#roles}.
	 * @param ctx the parse tree
	 */
	void enterRoles(StructuresSqlParserParser.RolesContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#roles}.
	 * @param ctx the parse tree
	 */
	void exitRoles(StructuresSqlParserParser.RolesContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#privilegeOrRole}.
	 * @param ctx the parse tree
	 */
	void enterPrivilegeOrRole(StructuresSqlParserParser.PrivilegeOrRoleContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#privilegeOrRole}.
	 * @param ctx the parse tree
	 */
	void exitPrivilegeOrRole(StructuresSqlParserParser.PrivilegeOrRoleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unquotedIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterUnquotedIdentifier(StructuresSqlParserParser.UnquotedIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unquotedIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitUnquotedIdentifier(StructuresSqlParserParser.UnquotedIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code quotedIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterQuotedIdentifier(StructuresSqlParserParser.QuotedIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code quotedIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitQuotedIdentifier(StructuresSqlParserParser.QuotedIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code backQuotedIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterBackQuotedIdentifier(StructuresSqlParserParser.BackQuotedIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code backQuotedIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitBackQuotedIdentifier(StructuresSqlParserParser.BackQuotedIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code digitIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterDigitIdentifier(StructuresSqlParserParser.DigitIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code digitIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitDigitIdentifier(StructuresSqlParserParser.DigitIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code decimalLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#number}.
	 * @param ctx the parse tree
	 */
	void enterDecimalLiteral(StructuresSqlParserParser.DecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code decimalLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#number}.
	 * @param ctx the parse tree
	 */
	void exitDecimalLiteral(StructuresSqlParserParser.DecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doubleLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#number}.
	 * @param ctx the parse tree
	 */
	void enterDoubleLiteral(StructuresSqlParserParser.DoubleLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doubleLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#number}.
	 * @param ctx the parse tree
	 */
	void exitDoubleLiteral(StructuresSqlParserParser.DoubleLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#number}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(StructuresSqlParserParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#number}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(StructuresSqlParserParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierUser}
	 * labeled alternative in {@link StructuresSqlParserParser#authorizationUser}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierUser(StructuresSqlParserParser.IdentifierUserContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierUser}
	 * labeled alternative in {@link StructuresSqlParserParser#authorizationUser}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierUser(StructuresSqlParserParser.IdentifierUserContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringUser}
	 * labeled alternative in {@link StructuresSqlParserParser#authorizationUser}.
	 * @param ctx the parse tree
	 */
	void enterStringUser(StructuresSqlParserParser.StringUserContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringUser}
	 * labeled alternative in {@link StructuresSqlParserParser#authorizationUser}.
	 * @param ctx the parse tree
	 */
	void exitStringUser(StructuresSqlParserParser.StringUserContext ctx);
	/**
	 * Enter a parse tree produced by {@link StructuresSqlParserParser#nonReserved}.
	 * @param ctx the parse tree
	 */
	void enterNonReserved(StructuresSqlParserParser.NonReservedContext ctx);
	/**
	 * Exit a parse tree produced by {@link StructuresSqlParserParser#nonReserved}.
	 * @param ctx the parse tree
	 */
	void exitNonReserved(StructuresSqlParserParser.NonReservedContext ctx);
}