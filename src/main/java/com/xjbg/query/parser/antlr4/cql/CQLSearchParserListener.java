// Generated from D:/workspace/java-project/fcc-query/fcc-query-parser/src/main/resources/antlr4/cql/CQLSearchParser.g4 by ANTLR 4.13.1
package com.xjbg.query.parser.antlr4.cql;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CQLSearchParser}.
 */
public interface CQLSearchParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CQLSearchParser#cql}.
	 * @param ctx the parse tree
	 */
	void enterCql(CQLSearchParser.CqlContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLSearchParser#cql}.
	 * @param ctx the parse tree
	 */
	void exitCql(CQLSearchParser.CqlContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqExpr(CQLSearchParser.EqExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqExpr(CQLSearchParser.EqExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(CQLSearchParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(CQLSearchParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identityExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentityExpr(CQLSearchParser.IdentityExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identityExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentityExpr(CQLSearchParser.IdentityExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lrExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLrExpr(CQLSearchParser.LrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lrExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLrExpr(CQLSearchParser.LrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpr(CQLSearchParser.BoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpr(CQLSearchParser.BoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orItemExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrItemExpr(CQLSearchParser.OrItemExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orItemExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrItemExpr(CQLSearchParser.OrItemExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CQLSearchParser#orItems}.
	 * @param ctx the parse tree
	 */
	void enterOrItems(CQLSearchParser.OrItemsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLSearchParser#orItems}.
	 * @param ctx the parse tree
	 */
	void exitOrItems(CQLSearchParser.OrItemsContext ctx);
}