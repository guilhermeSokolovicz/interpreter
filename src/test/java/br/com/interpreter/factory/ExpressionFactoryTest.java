package br.com.interpreter.factory;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.com.interpreter.function.BaseExpression;

public class ExpressionFactoryTest {
	@Test
	public void singleExpressionFactoryTest() {
		ExpressionFactory expressionFactory = new ExpressionFactory();
		Map<String, Object> params = new HashMap<>();
		params.put("A", true);
		params.put("B", false);
		BaseExpression expression = expressionFactory.build("A && B", params);
		assertEquals("A", expression.getParameter(0));
		assertEquals("B", expression.getParameter(1));
		assertFalse((Boolean)expression.solve(params));
	}
	@Test
	public void aAndBOrCTest() {
		ExpressionFactory expressionFactory = new ExpressionFactory();
		Map<String, Object> params = new HashMap<>();
		params.put("A", true);
		params.put("B", false);
		params.put("C", true);
		BaseExpression expression = expressionFactory.build("A && B || C", params);
		assertEquals("Exp1", expression.getParameter(0));
		assertEquals("C", expression.getParameter(1));
		assertTrue(params.containsKey("Exp1"));
		assertTrue((Boolean)expression.solve(params));
	}
	@Test
	public void aAndBAndCTest() {
		ExpressionFactory expressionFactory = new ExpressionFactory();
		Map<String, Object> params = new HashMap<>();
		params.put("A", true);
		params.put("B", false);
		params.put("C", true);
		BaseExpression expression = expressionFactory.build("A && !B && C", params);
		assertEquals("Exp2", expression.getParameter(0));
		assertEquals("C", expression.getParameter(1));
		assertTrue(params.containsKey("Exp2"));
		assertTrue((Boolean)expression.solve(params));
	}
	
	@Test
	public void aGreaterBPlusCTest() {
		ExpressionFactory expressionFactory = new ExpressionFactory();
		Map<String, Object> params = new HashMap<>();
		params.put("A", new BigDecimal(5));
		params.put("B", new BigDecimal(1));
		params.put("C", new BigDecimal(4));
		BaseExpression expression = expressionFactory.build("A > B + C", params);
		assertEquals("A", expression.getParameter(0));
		assertEquals("Exp1", expression.getParameter(1));
		assertTrue(params.containsKey("Exp1"));
		assertFalse((Boolean)expression.solve(params));
	}
	@Test
	public void groupTest() {
		ExpressionFactory expressionFactory = new ExpressionFactory();
		Map<String, Object> params = new HashMap<>();
		params.put("A", new BigDecimal(5));
		params.put("B", new BigDecimal(1));
		params.put("C", new BigDecimal(1));
		BaseExpression expression = expressionFactory.build("A >= (B + C) * 2 + 1", params);
		assertEquals("A", expression.getParameter(0));
		assertEquals("Exp3", expression.getParameter(1));
		assertTrue(params.containsKey("Exp1"));
		assertTrue(params.containsKey("Exp2"));
		assertTrue(params.containsKey("Exp3"));
		assertTrue((Boolean)expression.solve(params));
	}
	@Test
	public void multiGroupTest() {
		ExpressionFactory expressionFactory = new ExpressionFactory();
		Map<String, Object> params = new HashMap<>();
		params.put("A", new BigDecimal(5));
		params.put("B", new BigDecimal(1));
		params.put("C", new BigDecimal(1));
		BaseExpression expression = expressionFactory.build("A > ((B + C) * (2 + 1))", params);
		assertEquals("A", expression.getParameter(0));
		assertEquals("Exp3", expression.getParameter(1));
		assertTrue(params.containsKey("Exp1"));
		assertTrue(params.containsKey("Exp2"));
		assertTrue(params.containsKey("Exp3"));
		assertFalse((Boolean)expression.solve(params));
	}
	
	@Test
	public void multiEqualGroupTest() {
		ExpressionFactory expressionFactory = new ExpressionFactory();
		Map<String, Object> params = new HashMap<>();
		params.put("A", new BigDecimal(5));
		params.put("B", new BigDecimal(1));
		params.put("C", new BigDecimal(1));
		BaseExpression expression = expressionFactory.build("A = ((B + C) * (2 + 1))", params);
		assertEquals("A", expression.getParameter(0));
		assertEquals("Exp3", expression.getParameter(1));
		assertTrue(params.containsKey("Exp1"));
		assertTrue(params.containsKey("Exp2"));
		assertTrue(params.containsKey("Exp3"));
		assertFalse((Boolean)expression.solve(params));
	}
	
	@Test
	public void multiNotEqualGroupTest() {
		ExpressionFactory expressionFactory = new ExpressionFactory();
		Map<String, Object> params = new HashMap<>();
		params.put("A", new BigDecimal(5));
		params.put("B", new BigDecimal(1));
		params.put("C", new BigDecimal(1));
		BaseExpression expression = expressionFactory.build("A != ((B + C) * (2 + 1))", params);
		assertEquals("A", expression.getParameter(0));
		assertEquals("Exp3", expression.getParameter(1));
		assertTrue(params.containsKey("Exp1"));
		assertTrue(params.containsKey("Exp2"));
		assertTrue(params.containsKey("Exp3"));
		assertTrue((Boolean)expression.solve(params));
	}
	
	@Test
	public void numericTest() {
		ExpressionFactory expressionFactory = new ExpressionFactory();
		Map<String, Object> params = new HashMap<>();
		params.put("A", new BigDecimal(5));
		params.put("B", new BigDecimal(1));
		params.put("C", new BigDecimal(3));
		BaseExpression expression = expressionFactory.build("A - (B + C) * (2 + 1) / C", params);
		assertEquals("A", expression.getParameter(0));
		assertEquals("Exp4", expression.getParameter(1));
		assertTrue(params.containsKey("Exp1"));
		assertTrue(params.containsKey("Exp2"));
		assertTrue(params.containsKey("Exp3"));
		assertTrue(params.containsKey("Exp4"));
		assertEquals(new BigDecimal(1), (BigDecimal)expression.solve(params));
	}
}
