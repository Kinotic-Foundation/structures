// Generated from /Users/navid/workspace/git/continuum/structures/structures-sql/src/main/resources/antlr/StructuresSqlParser.g4 by ANTLR 4.13.1
package org.kinotic.structures.sql.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link StructuresSqlParserParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface StructuresSqlParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#singleStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleStatement(StructuresSqlParserParser.SingleStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#standaloneExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStandaloneExpression(StructuresSqlParserParser.StandaloneExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#standalonePathSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStandalonePathSpecification(StructuresSqlParserParser.StandalonePathSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#standaloneType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStandaloneType(StructuresSqlParserParser.StandaloneTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#standaloneRowPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStandaloneRowPattern(StructuresSqlParserParser.StandaloneRowPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#standaloneFunctionSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStandaloneFunctionSpecification(StructuresSqlParserParser.StandaloneFunctionSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementDefault(StructuresSqlParserParser.StatementDefaultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code use}
	 * labeled alternative in {@link StructuresSqlParserParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUse(StructuresSqlParserParser.UseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code update}
	 * labeled alternative in {@link StructuresSqlParserParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdate(StructuresSqlParserParser.UpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#rootQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRootQuery(StructuresSqlParserParser.RootQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#withFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithFunction(StructuresSqlParserParser.WithFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(StructuresSqlParserParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#with}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWith(StructuresSqlParserParser.WithContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#tableElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableElement(StructuresSqlParserParser.TableElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#columnDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnDefinition(StructuresSqlParserParser.ColumnDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#likeClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLikeClause(StructuresSqlParserParser.LikeClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#properties}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperties(StructuresSqlParserParser.PropertiesContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#propertyAssignments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyAssignments(StructuresSqlParserParser.PropertyAssignmentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(StructuresSqlParserParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code defaultPropertyValue}
	 * labeled alternative in {@link StructuresSqlParserParser#propertyValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultPropertyValue(StructuresSqlParserParser.DefaultPropertyValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nonDefaultPropertyValue}
	 * labeled alternative in {@link StructuresSqlParserParser#propertyValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonDefaultPropertyValue(StructuresSqlParserParser.NonDefaultPropertyValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#queryNoWith}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryNoWith(StructuresSqlParserParser.QueryNoWithContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#limitRowCount}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitRowCount(StructuresSqlParserParser.LimitRowCountContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#rowCount}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowCount(StructuresSqlParserParser.RowCountContext ctx);
	/**
	 * Visit a parse tree produced by the {@code queryTermDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#queryTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryTermDefault(StructuresSqlParserParser.QueryTermDefaultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code setOperation}
	 * labeled alternative in {@link StructuresSqlParserParser#queryTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetOperation(StructuresSqlParserParser.SetOperationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code queryPrimaryDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryPrimaryDefault(StructuresSqlParserParser.QueryPrimaryDefaultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code table}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable(StructuresSqlParserParser.TableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inlineTable}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineTable(StructuresSqlParserParser.InlineTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subquery}
	 * labeled alternative in {@link StructuresSqlParserParser#queryPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubquery(StructuresSqlParserParser.SubqueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#sortItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSortItem(StructuresSqlParserParser.SortItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#querySpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuerySpecification(StructuresSqlParserParser.QuerySpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#groupBy}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupBy(StructuresSqlParserParser.GroupByContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleGroupingSet}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleGroupingSet(StructuresSqlParserParser.SingleGroupingSetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rollup}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRollup(StructuresSqlParserParser.RollupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code cube}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCube(StructuresSqlParserParser.CubeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multipleGroupingSets}
	 * labeled alternative in {@link StructuresSqlParserParser#groupingElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultipleGroupingSets(StructuresSqlParserParser.MultipleGroupingSetsContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#groupingSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupingSet(StructuresSqlParserParser.GroupingSetContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#windowDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowDefinition(StructuresSqlParserParser.WindowDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#windowSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowSpecification(StructuresSqlParserParser.WindowSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#namedQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedQuery(StructuresSqlParserParser.NamedQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#setQuantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetQuantifier(StructuresSqlParserParser.SetQuantifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selectSingle}
	 * labeled alternative in {@link StructuresSqlParserParser#selectItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectSingle(StructuresSqlParserParser.SelectSingleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selectAll}
	 * labeled alternative in {@link StructuresSqlParserParser#selectItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectAll(StructuresSqlParserParser.SelectAllContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relationDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#relation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationDefault(StructuresSqlParserParser.RelationDefaultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code joinRelation}
	 * labeled alternative in {@link StructuresSqlParserParser#relation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinRelation(StructuresSqlParserParser.JoinRelationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#joinType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinType(StructuresSqlParserParser.JoinTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#joinCriteria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinCriteria(StructuresSqlParserParser.JoinCriteriaContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#sampledRelation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSampledRelation(StructuresSqlParserParser.SampledRelationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#sampleType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSampleType(StructuresSqlParserParser.SampleTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#trimsSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrimsSpecification(StructuresSqlParserParser.TrimsSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#listAggOverflowBehavior}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListAggOverflowBehavior(StructuresSqlParserParser.ListAggOverflowBehaviorContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#listaggCountIndication}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListaggCountIndication(StructuresSqlParserParser.ListaggCountIndicationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#patternRecognition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatternRecognition(StructuresSqlParserParser.PatternRecognitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#measureDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeasureDefinition(StructuresSqlParserParser.MeasureDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#rowsPerMatch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowsPerMatch(StructuresSqlParserParser.RowsPerMatchContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#emptyMatchHandling}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyMatchHandling(StructuresSqlParserParser.EmptyMatchHandlingContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#skipTo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSkipTo(StructuresSqlParserParser.SkipToContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#subsetDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubsetDefinition(StructuresSqlParserParser.SubsetDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#variableDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDefinition(StructuresSqlParserParser.VariableDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#aliasedRelation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAliasedRelation(StructuresSqlParserParser.AliasedRelationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#columnAliases}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnAliases(StructuresSqlParserParser.ColumnAliasesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableName}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableName(StructuresSqlParserParser.TableNameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subqueryRelation}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubqueryRelation(StructuresSqlParserParser.SubqueryRelationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unnest}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnnest(StructuresSqlParserParser.UnnestContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lateral}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLateral(StructuresSqlParserParser.LateralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableFunctionInvocation}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableFunctionInvocation(StructuresSqlParserParser.TableFunctionInvocationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenthesizedRelation}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedRelation(StructuresSqlParserParser.ParenthesizedRelationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jsonTable}
	 * labeled alternative in {@link StructuresSqlParserParser#relationPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonTable(StructuresSqlParserParser.JsonTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ordinalityColumn}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinalityColumn(StructuresSqlParserParser.OrdinalityColumnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valueColumn}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueColumn(StructuresSqlParserParser.ValueColumnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code queryColumn}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryColumn(StructuresSqlParserParser.QueryColumnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nestedColumns}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableColumn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedColumns(StructuresSqlParserParser.NestedColumnsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code leafPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeafPlan(StructuresSqlParserParser.LeafPlanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code joinPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinPlan(StructuresSqlParserParser.JoinPlanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unionPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnionPlan(StructuresSqlParserParser.UnionPlanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code crossPlan}
	 * labeled alternative in {@link StructuresSqlParserParser#jsonTableSpecificPlan}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCrossPlan(StructuresSqlParserParser.CrossPlanContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#jsonTablePathName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonTablePathName(StructuresSqlParserParser.JsonTablePathNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#planPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlanPrimary(StructuresSqlParserParser.PlanPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#jsonTableDefaultPlan}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonTableDefaultPlan(StructuresSqlParserParser.JsonTableDefaultPlanContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#tableFunctionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableFunctionCall(StructuresSqlParserParser.TableFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#tableFunctionArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableFunctionArgument(StructuresSqlParserParser.TableFunctionArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#tableArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableArgument(StructuresSqlParserParser.TableArgumentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableArgumentTable}
	 * labeled alternative in {@link StructuresSqlParserParser#tableArgumentRelation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableArgumentTable(StructuresSqlParserParser.TableArgumentTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableArgumentQuery}
	 * labeled alternative in {@link StructuresSqlParserParser#tableArgumentRelation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableArgumentQuery(StructuresSqlParserParser.TableArgumentQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#descriptorArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescriptorArgument(StructuresSqlParserParser.DescriptorArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#descriptorField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescriptorField(StructuresSqlParserParser.DescriptorFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#copartitionTables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCopartitionTables(StructuresSqlParserParser.CopartitionTablesContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(StructuresSqlParserParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalNot}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalNot(StructuresSqlParserParser.LogicalNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code predicated}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicated(StructuresSqlParserParser.PredicatedContext ctx);
	/**
	 * Visit a parse tree produced by the {@code or}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(StructuresSqlParserParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code and}
	 * labeled alternative in {@link StructuresSqlParserParser#booleanExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(StructuresSqlParserParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(StructuresSqlParserParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code quantifiedComparison}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuantifiedComparison(StructuresSqlParserParser.QuantifiedComparisonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code between}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBetween(StructuresSqlParserParser.BetweenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inList}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInList(StructuresSqlParserParser.InListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inSubquery}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInSubquery(StructuresSqlParserParser.InSubqueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code like}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLike(StructuresSqlParserParser.LikeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullPredicate}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullPredicate(StructuresSqlParserParser.NullPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code distinctFrom}
	 * labeled alternative in {@link StructuresSqlParserParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDistinctFrom(StructuresSqlParserParser.DistinctFromContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valueExpressionDefault}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueExpressionDefault(StructuresSqlParserParser.ValueExpressionDefaultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code concatenation}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConcatenation(StructuresSqlParserParser.ConcatenationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithmeticBinary}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticBinary(StructuresSqlParserParser.ArithmeticBinaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithmeticUnary}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticUnary(StructuresSqlParserParser.ArithmeticUnaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atTimeZone}
	 * labeled alternative in {@link StructuresSqlParserParser#valueExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtTimeZone(StructuresSqlParserParser.AtTimeZoneContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dereference}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDereference(StructuresSqlParserParser.DereferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code typeConstructor}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeConstructor(StructuresSqlParserParser.TypeConstructorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jsonValue}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonValue(StructuresSqlParserParser.JsonValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code currentDate}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentDate(StructuresSqlParserParser.CurrentDateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code substring}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubstring(StructuresSqlParserParser.SubstringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code cast}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCast(StructuresSqlParserParser.CastContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lambda}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambda(StructuresSqlParserParser.LambdaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenthesizedExpression}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedExpression(StructuresSqlParserParser.ParenthesizedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trim}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrim(StructuresSqlParserParser.TrimContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parameter}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(StructuresSqlParserParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code normalize}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalize(StructuresSqlParserParser.NormalizeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code localTimestamp}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTimestamp(StructuresSqlParserParser.LocalTimestampContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jsonObject}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonObject(StructuresSqlParserParser.JsonObjectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intervalLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalLiteral(StructuresSqlParserParser.IntervalLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numericLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteral(StructuresSqlParserParser.NumericLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code booleanLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(StructuresSqlParserParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jsonArray}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonArray(StructuresSqlParserParser.JsonArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleCase}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleCase(StructuresSqlParserParser.SimpleCaseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code columnReference}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnReference(StructuresSqlParserParser.ColumnReferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLiteral(StructuresSqlParserParser.NullLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rowConstructor}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowConstructor(StructuresSqlParserParser.RowConstructorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subscript}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscript(StructuresSqlParserParser.SubscriptContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jsonExists}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonExists(StructuresSqlParserParser.JsonExistsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code currentPath}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentPath(StructuresSqlParserParser.CurrentPathContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subqueryExpression}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubqueryExpression(StructuresSqlParserParser.SubqueryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryLiteral(StructuresSqlParserParser.BinaryLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code currentTime}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentTime(StructuresSqlParserParser.CurrentTimeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code localTime}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalTime(StructuresSqlParserParser.LocalTimeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code currentUser}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentUser(StructuresSqlParserParser.CurrentUserContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jsonQuery}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonQuery(StructuresSqlParserParser.JsonQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code measure}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeasure(StructuresSqlParserParser.MeasureContext ctx);
	/**
	 * Visit a parse tree produced by the {@code extract}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtract(StructuresSqlParserParser.ExtractContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(StructuresSqlParserParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayConstructor}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayConstructor(StructuresSqlParserParser.ArrayConstructorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionCall}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(StructuresSqlParserParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code currentTimestamp}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentTimestamp(StructuresSqlParserParser.CurrentTimestampContext ctx);
	/**
	 * Visit a parse tree produced by the {@code currentSchema}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentSchema(StructuresSqlParserParser.CurrentSchemaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exists}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExists(StructuresSqlParserParser.ExistsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code position}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPosition(StructuresSqlParserParser.PositionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code listagg}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListagg(StructuresSqlParserParser.ListaggContext ctx);
	/**
	 * Visit a parse tree produced by the {@code searchedCase}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSearchedCase(StructuresSqlParserParser.SearchedCaseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code currentCatalog}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentCatalog(StructuresSqlParserParser.CurrentCatalogContext ctx);
	/**
	 * Visit a parse tree produced by the {@code groupingOperation}
	 * labeled alternative in {@link StructuresSqlParserParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupingOperation(StructuresSqlParserParser.GroupingOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#jsonPathInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonPathInvocation(StructuresSqlParserParser.JsonPathInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#jsonValueExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonValueExpression(StructuresSqlParserParser.JsonValueExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#jsonRepresentation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonRepresentation(StructuresSqlParserParser.JsonRepresentationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#jsonArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonArgument(StructuresSqlParserParser.JsonArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#jsonExistsErrorBehavior}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonExistsErrorBehavior(StructuresSqlParserParser.JsonExistsErrorBehaviorContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#jsonValueBehavior}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonValueBehavior(StructuresSqlParserParser.JsonValueBehaviorContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#jsonQueryWrapperBehavior}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonQueryWrapperBehavior(StructuresSqlParserParser.JsonQueryWrapperBehaviorContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#jsonQueryBehavior}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonQueryBehavior(StructuresSqlParserParser.JsonQueryBehaviorContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#jsonObjectMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonObjectMember(StructuresSqlParserParser.JsonObjectMemberContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#processingMode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcessingMode(StructuresSqlParserParser.ProcessingModeContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#nullTreatment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullTreatment(StructuresSqlParserParser.NullTreatmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code basicStringLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicStringLiteral(StructuresSqlParserParser.BasicStringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unicodeStringLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnicodeStringLiteral(StructuresSqlParserParser.UnicodeStringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code timeZoneInterval}
	 * labeled alternative in {@link StructuresSqlParserParser#timeZoneSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeZoneInterval(StructuresSqlParserParser.TimeZoneIntervalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code timeZoneString}
	 * labeled alternative in {@link StructuresSqlParserParser#timeZoneSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeZoneString(StructuresSqlParserParser.TimeZoneStringContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#comparisonOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonOperator(StructuresSqlParserParser.ComparisonOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#comparisonQuantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonQuantifier(StructuresSqlParserParser.ComparisonQuantifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#booleanValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanValue(StructuresSqlParserParser.BooleanValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#interval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterval(StructuresSqlParserParser.IntervalContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#intervalField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalField(StructuresSqlParserParser.IntervalFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#normalForm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalForm(StructuresSqlParserParser.NormalFormContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rowType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowType(StructuresSqlParserParser.RowTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intervalType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalType(StructuresSqlParserParser.IntervalTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(StructuresSqlParserParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doublePrecisionType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoublePrecisionType(StructuresSqlParserParser.DoublePrecisionTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code legacyArrayType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLegacyArrayType(StructuresSqlParserParser.LegacyArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code genericType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenericType(StructuresSqlParserParser.GenericTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dateTimeType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateTimeType(StructuresSqlParserParser.DateTimeTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code legacyMapType}
	 * labeled alternative in {@link StructuresSqlParserParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLegacyMapType(StructuresSqlParserParser.LegacyMapTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#rowField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowField(StructuresSqlParserParser.RowFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#typeParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameter(StructuresSqlParserParser.TypeParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#whenClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenClause(StructuresSqlParserParser.WhenClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#filter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilter(StructuresSqlParserParser.FilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mergeUpdate}
	 * labeled alternative in {@link StructuresSqlParserParser#mergeCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMergeUpdate(StructuresSqlParserParser.MergeUpdateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mergeDelete}
	 * labeled alternative in {@link StructuresSqlParserParser#mergeCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMergeDelete(StructuresSqlParserParser.MergeDeleteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mergeInsert}
	 * labeled alternative in {@link StructuresSqlParserParser#mergeCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMergeInsert(StructuresSqlParserParser.MergeInsertContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#over}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOver(StructuresSqlParserParser.OverContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#windowFrame}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowFrame(StructuresSqlParserParser.WindowFrameContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#frameExtent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFrameExtent(StructuresSqlParserParser.FrameExtentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unboundedFrame}
	 * labeled alternative in {@link StructuresSqlParserParser#frameBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnboundedFrame(StructuresSqlParserParser.UnboundedFrameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code currentRowBound}
	 * labeled alternative in {@link StructuresSqlParserParser#frameBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentRowBound(StructuresSqlParserParser.CurrentRowBoundContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boundedFrame}
	 * labeled alternative in {@link StructuresSqlParserParser#frameBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoundedFrame(StructuresSqlParserParser.BoundedFrameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code quantifiedPrimary}
	 * labeled alternative in {@link StructuresSqlParserParser#rowPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuantifiedPrimary(StructuresSqlParserParser.QuantifiedPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code patternConcatenation}
	 * labeled alternative in {@link StructuresSqlParserParser#rowPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatternConcatenation(StructuresSqlParserParser.PatternConcatenationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code patternAlternation}
	 * labeled alternative in {@link StructuresSqlParserParser#rowPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatternAlternation(StructuresSqlParserParser.PatternAlternationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code patternVariable}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatternVariable(StructuresSqlParserParser.PatternVariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyPattern}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyPattern(StructuresSqlParserParser.EmptyPatternContext ctx);
	/**
	 * Visit a parse tree produced by the {@code patternPermutation}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatternPermutation(StructuresSqlParserParser.PatternPermutationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code groupedPattern}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupedPattern(StructuresSqlParserParser.GroupedPatternContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionStartAnchor}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionStartAnchor(StructuresSqlParserParser.PartitionStartAnchorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionEndAnchor}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionEndAnchor(StructuresSqlParserParser.PartitionEndAnchorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code excludedPattern}
	 * labeled alternative in {@link StructuresSqlParserParser#patternPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExcludedPattern(StructuresSqlParserParser.ExcludedPatternContext ctx);
	/**
	 * Visit a parse tree produced by the {@code zeroOrMoreQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitZeroOrMoreQuantifier(StructuresSqlParserParser.ZeroOrMoreQuantifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code oneOrMoreQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOneOrMoreQuantifier(StructuresSqlParserParser.OneOrMoreQuantifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code zeroOrOneQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitZeroOrOneQuantifier(StructuresSqlParserParser.ZeroOrOneQuantifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rangeQuantifier}
	 * labeled alternative in {@link StructuresSqlParserParser#patternQuantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeQuantifier(StructuresSqlParserParser.RangeQuantifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#updateAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdateAssignment(StructuresSqlParserParser.UpdateAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code explainFormat}
	 * labeled alternative in {@link StructuresSqlParserParser#explainOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplainFormat(StructuresSqlParserParser.ExplainFormatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code explainType}
	 * labeled alternative in {@link StructuresSqlParserParser#explainOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplainType(StructuresSqlParserParser.ExplainTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isolationLevel}
	 * labeled alternative in {@link StructuresSqlParserParser#transactionMode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsolationLevel(StructuresSqlParserParser.IsolationLevelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code transactionAccessMode}
	 * labeled alternative in {@link StructuresSqlParserParser#transactionMode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransactionAccessMode(StructuresSqlParserParser.TransactionAccessModeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code readUncommitted}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadUncommitted(StructuresSqlParserParser.ReadUncommittedContext ctx);
	/**
	 * Visit a parse tree produced by the {@code readCommitted}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadCommitted(StructuresSqlParserParser.ReadCommittedContext ctx);
	/**
	 * Visit a parse tree produced by the {@code repeatableRead}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeatableRead(StructuresSqlParserParser.RepeatableReadContext ctx);
	/**
	 * Visit a parse tree produced by the {@code serializable}
	 * labeled alternative in {@link StructuresSqlParserParser#levelOfIsolation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSerializable(StructuresSqlParserParser.SerializableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code positionalArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#callArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPositionalArgument(StructuresSqlParserParser.PositionalArgumentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code namedArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#callArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedArgument(StructuresSqlParserParser.NamedArgumentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code qualifiedArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#pathElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedArgument(StructuresSqlParserParser.QualifiedArgumentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unqualifiedArgument}
	 * labeled alternative in {@link StructuresSqlParserParser#pathElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnqualifiedArgument(StructuresSqlParserParser.UnqualifiedArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#pathSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathSpecification(StructuresSqlParserParser.PathSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#functionSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionSpecification(StructuresSqlParserParser.FunctionSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(StructuresSqlParserParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclaration(StructuresSqlParserParser.ParameterDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#returnsClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnsClause(StructuresSqlParserParser.ReturnsClauseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code languageCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLanguageCharacteristic(StructuresSqlParserParser.LanguageCharacteristicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code deterministicCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeterministicCharacteristic(StructuresSqlParserParser.DeterministicCharacteristicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnsNullOnNullInputCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnsNullOnNullInputCharacteristic(StructuresSqlParserParser.ReturnsNullOnNullInputCharacteristicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code calledOnNullInputCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalledOnNullInputCharacteristic(StructuresSqlParserParser.CalledOnNullInputCharacteristicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code securityCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSecurityCharacteristic(StructuresSqlParserParser.SecurityCharacteristicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commentCharacteristic}
	 * labeled alternative in {@link StructuresSqlParserParser#routineCharacteristic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommentCharacteristic(StructuresSqlParserParser.CommentCharacteristicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(StructuresSqlParserParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(StructuresSqlParserParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleCaseStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleCaseStatement(StructuresSqlParserParser.SimpleCaseStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code searchedCaseStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSearchedCaseStatement(StructuresSqlParserParser.SearchedCaseStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(StructuresSqlParserParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code iterateStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterateStatement(StructuresSqlParserParser.IterateStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code leaveStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeaveStatement(StructuresSqlParserParser.LeaveStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compoundStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStatement(StructuresSqlParserParser.CompoundStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loopStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(StructuresSqlParserParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(StructuresSqlParserParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code repeatStatement}
	 * labeled alternative in {@link StructuresSqlParserParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeatStatement(StructuresSqlParserParser.RepeatStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#caseStatementWhenClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseStatementWhenClause(StructuresSqlParserParser.CaseStatementWhenClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#elseIfClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseIfClause(StructuresSqlParserParser.ElseIfClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#elseClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseClause(StructuresSqlParserParser.ElseClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(StructuresSqlParserParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#sqlStatementList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSqlStatementList(StructuresSqlParserParser.SqlStatementListContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#privilege}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrivilege(StructuresSqlParserParser.PrivilegeContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#entityKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntityKind(StructuresSqlParserParser.EntityKindContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#grantObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrantObject(StructuresSqlParserParser.GrantObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(StructuresSqlParserParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#queryPeriod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryPeriod(StructuresSqlParserParser.QueryPeriodContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#rangeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeType(StructuresSqlParserParser.RangeTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code specifiedPrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#grantor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecifiedPrincipal(StructuresSqlParserParser.SpecifiedPrincipalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code currentUserGrantor}
	 * labeled alternative in {@link StructuresSqlParserParser#grantor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentUserGrantor(StructuresSqlParserParser.CurrentUserGrantorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code currentRoleGrantor}
	 * labeled alternative in {@link StructuresSqlParserParser#grantor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentRoleGrantor(StructuresSqlParserParser.CurrentRoleGrantorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unspecifiedPrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#principal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnspecifiedPrincipal(StructuresSqlParserParser.UnspecifiedPrincipalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code userPrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#principal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUserPrincipal(StructuresSqlParserParser.UserPrincipalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rolePrincipal}
	 * labeled alternative in {@link StructuresSqlParserParser#principal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRolePrincipal(StructuresSqlParserParser.RolePrincipalContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#roles}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoles(StructuresSqlParserParser.RolesContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#privilegeOrRole}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrivilegeOrRole(StructuresSqlParserParser.PrivilegeOrRoleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unquotedIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnquotedIdentifier(StructuresSqlParserParser.UnquotedIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code quotedIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuotedIdentifier(StructuresSqlParserParser.QuotedIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code backQuotedIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBackQuotedIdentifier(StructuresSqlParserParser.BackQuotedIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code digitIdentifier}
	 * labeled alternative in {@link StructuresSqlParserParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDigitIdentifier(StructuresSqlParserParser.DigitIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code decimalLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimalLiteral(StructuresSqlParserParser.DecimalLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doubleLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleLiteral(StructuresSqlParserParser.DoubleLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link StructuresSqlParserParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(StructuresSqlParserParser.IntegerLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identifierUser}
	 * labeled alternative in {@link StructuresSqlParserParser#authorizationUser}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierUser(StructuresSqlParserParser.IdentifierUserContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringUser}
	 * labeled alternative in {@link StructuresSqlParserParser#authorizationUser}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringUser(StructuresSqlParserParser.StringUserContext ctx);
	/**
	 * Visit a parse tree produced by {@link StructuresSqlParserParser#nonReserved}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonReserved(StructuresSqlParserParser.NonReservedContext ctx);
}