package br.com.interpreter.expression;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import br.com.interpreter.function.BaseExpression;

public class BaseExpressionTest {
	@Test
	public void singleExpressionTest() {
		BaseExpression expression = new BaseExpression(ExpressionType.AND);
		expression.findAndReplace("true && false", "F1");
		String result = expression.getResult();
		assertEquals("F1", result);
		assertEquals("true", expression.getParameter(0));
		assertEquals("false", expression.getParameter(1));
	}
	@Test
	public void multipleExpressionTest() {
		BaseExpression expression = new BaseExpression(ExpressionType.AND);
		expression.findAndReplace("true && false && false", "F1");
		String result = expression.getResult();
		assertEquals("F1 && false", result);
		assertEquals("true", expression.getParameter(0));
		assertEquals("false", expression.getParameter(1));
	}
	
	@Test
	public void solveAndExpressionTest() {
		BaseExpression expression = new BaseExpression(ExpressionType.AND);
		expression.findAndReplace("A && B", "F1");
		HashMap<String, Object> params = new HashMap<>();
		params.put("A", true);
		params.put("B", false);
		assertEquals(false, expression.solve(params));
	}
	
	@Test
	public void solveOrExpressionTest() {
		BaseExpression expression = new BaseExpression(ExpressionType.OR);
		expression.findAndReplace("A || B", "F1");
		HashMap<String, Object> params = new HashMap<>();
		params.put("A", true);
		params.put("B", false);
		assertEquals(true, expression.solve(params));
	}
	
	@Test
	public void solveNotExpressionTest() {
		BaseExpression expression = new BaseExpression(ExpressionType.NOT);
		expression.findAndReplace("!A", "F1");
		HashMap<String, Object> params = new HashMap<>();
		params.put("A", true);
		assertEquals(false, expression.solve(params));
	}
	
	@Test
	public void solveMultipleExpressionTest() {
		BaseExpression expression = new BaseExpression(ExpressionType.AND);
		expression.findAndReplace("F1 && C", "F2");
		BaseExpression expressionF1 = new BaseExpression(ExpressionType.OR);
		expressionF1.findAndReplace("A || B", "F1");
		HashMap<String, Object> params = new HashMap<>();
		params.put("A", false);
		params.put("B", true);
		params.put("C", true);
		params.put("F1", expressionF1);
		assertEquals(true, expression.solve(params));
	}
}
