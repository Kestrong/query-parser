// Generated from D:/workspace/java-project/fcc-query/fcc-query-parser/src/main/resources/antlr4/cql/CQLSearchParser.g4 by ANTLR 4.13.1
package com.xjbg.query.parser.antlr4.cql;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CQLSearchParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CQLSearchParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CQLSearchParser#cql}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCql(CQLSearchParser.CqlContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqExpr(CQLSearchParser.EqExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(CQLSearchParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identityExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentityExpr(CQLSearchParser.IdentityExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lrExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLrExpr(CQLSearchParser.LrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpr(CQLSearchParser.BoolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orItemExpr}
	 * labeled alternative in {@link CQLSearchParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrItemExpr(CQLSearchParser.OrItemExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CQLSearchParser#orItems}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrItems(CQLSearchParser.OrItemsContext ctx);
}