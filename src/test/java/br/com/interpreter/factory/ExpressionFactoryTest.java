package br.com.interpreter.factory;

import static org.junit.Assert.*;

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
	}
	
	@Test
	public void aGreaterBPlusCTest() {
		ExpressionFactory expressionFactory = new ExpressionFactory();
		Map<String, Object> params = new HashMap<>();
		params.put("A", true);
		params.put("B", false);
		params.put("C", true);
		BaseExpression expression = expressionFactory.build("A > B + C", params);
		assertEquals("A", expression.getParameter(0));
		assertEquals("Exp1", expression.getParameter(1));
		assertTrue(params.containsKey("Exp1"));
	}
}
