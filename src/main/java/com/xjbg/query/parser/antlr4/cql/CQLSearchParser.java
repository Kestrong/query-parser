// Generated from D:/workspace/java-project/fcc-query/fcc-query-parser/src/main/resources/antlr4/cql/CQLSearchParser.g4 by ANTLR 4.13.1
package com.xjbg.query.parser.antlr4.cql;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class CQLSearchParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SPACE=1, SPEC_ESSQL_COMMENT=2, COMMENT_INPUT=3, LINE_COMMENT=4, MINUS=5, 
		STAR=6, COLON=7, EQ=8, NE=9, BOOLOR=10, BOOLAND=11, DOT=12, LBRACKET=13, 
		RBRACKET=14, LPAREN=15, RPAREN=16, COMMA=17, SEMI=18, GT=19, GTE=20, LT=21, 
		LTE=22, AFTER=23, AND=24, OR=25, NOT=26, SINGLE_QUOTE=27, DOUBLE_QUOTE=28, 
		REVERSE_QUOTE=29, SLASH=30, BACK_SLASH=31, UNDERLINE=32, CHINESE=33, ID=34, 
		INT=35, FLOAT=36, DOTINTEGER=37, DOTID=38;
	public static final int
		RULE_cql = 0, RULE_expression = 1, RULE_orItems = 2;
	private static String[] makeRuleNames() {
		return new String[] {
			"cql", "expression", "orItems"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'-'", "'*'", null, "'='", "'!='", null, 
			null, "'.'", "'['", "']'", "'('", "')'", null, "';'", "'>'", "'>='", 
			"'<'", "'<='", null, null, null, null, "'''", "'\"'", "'`'", "'/'", "'\\'", 
			"'_'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "SPACE", "SPEC_ESSQL_COMMENT", "COMMENT_INPUT", "LINE_COMMENT", 
			"MINUS", "STAR", "COLON", "EQ", "NE", "BOOLOR", "BOOLAND", "DOT", "LBRACKET", 
			"RBRACKET", "LPAREN", "RPAREN", "COMMA", "SEMI", "GT", "GTE", "LT", "LTE", 
			"AFTER", "AND", "OR", "NOT", "SINGLE_QUOTE", "DOUBLE_QUOTE", "REVERSE_QUOTE", 
			"SLASH", "BACK_SLASH", "UNDERLINE", "CHINESE", "ID", "INT", "FLOAT", 
			"DOTINTEGER", "DOTID"
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
	public String getGrammarFileName() { return "CQLSearchParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CQLSearchParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CqlContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(CQLSearchParser.EOF, 0); }
		public CqlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cql; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).enterCql(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).exitCql(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CQLSearchParserVisitor ) return ((CQLSearchParserVisitor<? extends T>)visitor).visitCql(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CqlContext cql() throws RecognitionException {
		CqlContext _localctx = new CqlContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_cql);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(6);
			expression(0);
			setState(7);
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
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqExprContext extends ExpressionContext {
		public ExpressionContext leftExpr;
		public Token operator;
		public ExpressionContext rightExpr;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode EQ() { return getToken(CQLSearchParser.EQ, 0); }
		public TerminalNode COLON() { return getToken(CQLSearchParser.COLON, 0); }
		public TerminalNode NE() { return getToken(CQLSearchParser.NE, 0); }
		public TerminalNode GT() { return getToken(CQLSearchParser.GT, 0); }
		public TerminalNode GTE() { return getToken(CQLSearchParser.GTE, 0); }
		public TerminalNode LT() { return getToken(CQLSearchParser.LT, 0); }
		public TerminalNode LTE() { return getToken(CQLSearchParser.LTE, 0); }
		public EqExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).enterEqExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).exitEqExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CQLSearchParserVisitor ) return ((CQLSearchParserVisitor<? extends T>)visitor).visitEqExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotExprContext extends ExpressionContext {
		public Token leftExpr;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode NOT() { return getToken(CQLSearchParser.NOT, 0); }
		public NotExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).enterNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).exitNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CQLSearchParserVisitor ) return ((CQLSearchParserVisitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdentityExprContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(CQLSearchParser.ID, 0); }
		public TerminalNode STAR() { return getToken(CQLSearchParser.STAR, 0); }
		public IdentityExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).enterIdentityExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).exitIdentityExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CQLSearchParserVisitor ) return ((CQLSearchParserVisitor<? extends T>)visitor).visitIdentityExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LrExprContext extends ExpressionContext {
		public TerminalNode LPAREN() { return getToken(CQLSearchParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(CQLSearchParser.RPAREN, 0); }
		public LrExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).enterLrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).exitLrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CQLSearchParserVisitor ) return ((CQLSearchParserVisitor<? extends T>)visitor).visitLrExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolExprContext extends ExpressionContext {
		public ExpressionContext leftExpr;
		public Token operator;
		public ExpressionContext rightExpr;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode BOOLAND() { return getToken(CQLSearchParser.BOOLAND, 0); }
		public TerminalNode AND() { return getToken(CQLSearchParser.AND, 0); }
		public TerminalNode BOOLOR() { return getToken(CQLSearchParser.BOOLOR, 0); }
		public TerminalNode OR() { return getToken(CQLSearchParser.OR, 0); }
		public TerminalNode NOT() { return getToken(CQLSearchParser.NOT, 0); }
		public BoolExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).enterBoolExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).exitBoolExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CQLSearchParserVisitor ) return ((CQLSearchParserVisitor<? extends T>)visitor).visitBoolExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrItemExprContext extends ExpressionContext {
		public Token item;
		public TerminalNode LBRACKET() { return getToken(CQLSearchParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(CQLSearchParser.RBRACKET, 0); }
		public TerminalNode ID() { return getToken(CQLSearchParser.ID, 0); }
		public List<OrItemsContext> orItems() {
			return getRuleContexts(OrItemsContext.class);
		}
		public OrItemsContext orItems(int i) {
			return getRuleContext(OrItemsContext.class,i);
		}
		public OrItemExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).enterOrItemExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).exitOrItemExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CQLSearchParserVisitor ) return ((CQLSearchParserVisitor<? extends T>)visitor).visitOrItemExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				_localctx = new LrExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(10);
				match(LPAREN);
				setState(11);
				expression(0);
				setState(12);
				match(RPAREN);
				}
				break;
			case NOT:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(14);
				((NotExprContext)_localctx).leftExpr = match(NOT);
				setState(15);
				expression(4);
				}
				break;
			case LBRACKET:
				{
				_localctx = new OrItemExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(16);
				match(LBRACKET);
				setState(17);
				((OrItemExprContext)_localctx).item = match(ID);
				setState(19); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(18);
					orItems();
					}
					}
					setState(21); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 33686528L) != 0) );
				setState(23);
				match(RBRACKET);
				}
				break;
			case ID:
				{
				_localctx = new IdentityExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(25);
				match(ID);
				}
				break;
			case STAR:
				{
				_localctx = new IdentityExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(26);
				match(STAR);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(37);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(35);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new EqExprContext(new ExpressionContext(_parentctx, _parentState));
						((EqExprContext)_localctx).leftExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(29);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(30);
						((EqExprContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 7865216L) != 0)) ) {
							((EqExprContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(31);
						((EqExprContext)_localctx).rightExpr = expression(7);
						}
						break;
					case 2:
						{
						_localctx = new BoolExprContext(new ExpressionContext(_parentctx, _parentState));
						((BoolExprContext)_localctx).leftExpr = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(32);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(33);
						((BoolExprContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 117443584L) != 0)) ) {
							((BoolExprContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(34);
						((BoolExprContext)_localctx).rightExpr = expression(6);
						}
						break;
					}
					} 
				}
				setState(39);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
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
	public static class OrItemsContext extends ParserRuleContext {
		public Token operator;
		public Token item;
		public TerminalNode ID() { return getToken(CQLSearchParser.ID, 0); }
		public TerminalNode BOOLOR() { return getToken(CQLSearchParser.BOOLOR, 0); }
		public TerminalNode OR() { return getToken(CQLSearchParser.OR, 0); }
		public TerminalNode COMMA() { return getToken(CQLSearchParser.COMMA, 0); }
		public OrItemsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orItems; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).enterOrItems(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CQLSearchParserListener ) ((CQLSearchParserListener)listener).exitOrItems(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CQLSearchParserVisitor ) return ((CQLSearchParserVisitor<? extends T>)visitor).visitOrItems(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrItemsContext orItems() throws RecognitionException {
		OrItemsContext _localctx = new OrItemsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_orItems);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			((OrItemsContext)_localctx).operator = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 33686528L) != 0)) ) {
				((OrItemsContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(41);
			((OrItemsContext)_localctx).item = match(ID);
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
		case 1:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001&,\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002"+
		"\u0007\u0002\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0004\u0001\u0014\b\u0001\u000b\u0001\f\u0001"+
		"\u0015\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u001c"+
		"\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0005\u0001$\b\u0001\n\u0001\f\u0001\'\t\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0000\u0001\u0002\u0003\u0000\u0002\u0004"+
		"\u0000\u0003\u0002\u0000\u0007\t\u0013\u0016\u0002\u0000\n\u000b\u0018"+
		"\u001a\u0003\u0000\n\n\u0011\u0011\u0019\u0019/\u0000\u0006\u0001\u0000"+
		"\u0000\u0000\u0002\u001b\u0001\u0000\u0000\u0000\u0004(\u0001\u0000\u0000"+
		"\u0000\u0006\u0007\u0003\u0002\u0001\u0000\u0007\b\u0005\u0000\u0000\u0001"+
		"\b\u0001\u0001\u0000\u0000\u0000\t\n\u0006\u0001\uffff\uffff\u0000\n\u000b"+
		"\u0005\u000f\u0000\u0000\u000b\f\u0003\u0002\u0001\u0000\f\r\u0005\u0010"+
		"\u0000\u0000\r\u001c\u0001\u0000\u0000\u0000\u000e\u000f\u0005\u001a\u0000"+
		"\u0000\u000f\u001c\u0003\u0002\u0001\u0004\u0010\u0011\u0005\r\u0000\u0000"+
		"\u0011\u0013\u0005\"\u0000\u0000\u0012\u0014\u0003\u0004\u0002\u0000\u0013"+
		"\u0012\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000\u0000\u0000\u0015"+
		"\u0013\u0001\u0000\u0000\u0000\u0015\u0016\u0001\u0000\u0000\u0000\u0016"+
		"\u0017\u0001\u0000\u0000\u0000\u0017\u0018\u0005\u000e\u0000\u0000\u0018"+
		"\u001c\u0001\u0000\u0000\u0000\u0019\u001c\u0005\"\u0000\u0000\u001a\u001c"+
		"\u0005\u0006\u0000\u0000\u001b\t\u0001\u0000\u0000\u0000\u001b\u000e\u0001"+
		"\u0000\u0000\u0000\u001b\u0010\u0001\u0000\u0000\u0000\u001b\u0019\u0001"+
		"\u0000\u0000\u0000\u001b\u001a\u0001\u0000\u0000\u0000\u001c%\u0001\u0000"+
		"\u0000\u0000\u001d\u001e\n\u0006\u0000\u0000\u001e\u001f\u0007\u0000\u0000"+
		"\u0000\u001f$\u0003\u0002\u0001\u0007 !\n\u0005\u0000\u0000!\"\u0007\u0001"+
		"\u0000\u0000\"$\u0003\u0002\u0001\u0006#\u001d\u0001\u0000\u0000\u0000"+
		"# \u0001\u0000\u0000\u0000$\'\u0001\u0000\u0000\u0000%#\u0001\u0000\u0000"+
		"\u0000%&\u0001\u0000\u0000\u0000&\u0003\u0001\u0000\u0000\u0000\'%\u0001"+
		"\u0000\u0000\u0000()\u0007\u0002\u0000\u0000)*\u0005\"\u0000\u0000*\u0005"+
		"\u0001\u0000\u0000\u0000\u0004\u0015\u001b#%";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}