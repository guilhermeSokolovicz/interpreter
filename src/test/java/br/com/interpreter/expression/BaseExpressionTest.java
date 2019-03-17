package br.com.interpreter.expression;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.interpreter.function.BaseExpression;

public class BaseExpressionTest {
	@Test
	public void singleExpressionTest() {
		BaseExpression expression = new BaseExpression("@[ ]+\\&\\&[ ]+@");
		expression.findAndReplace("true && false", "F1");
		String result = expression.getResult();
		assertEquals("F1", result);
		assertEquals("true", expression.getParameter(0));
		assertEquals("false", expression.getParameter(1));
	}
	@Test
	public void multipleExpressionTest() {
		BaseExpression expression = new BaseExpression("@[ ]+\\&\\&[ ]+@");
		expression.findAndReplace("true && false && false", "F1");
		String result = expression.getResult();
		assertEquals("F1 && false", result);
		assertEquals("true", expression.getParameter(0));
		assertEquals("false", expression.getParameter(1));
	}
}
