package br.com.interpreter.factory;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.interpreter.expression.ExpressionType;
import br.com.interpreter.function.BaseExpression;

public class ExpressionFactory {	
	
	public BaseExpression build(String expressionText, Map<String, Object> params) {
		int initialParams = params.size();
		Pattern groupPattern = Pattern.compile("([^a-zA-Z0-9])\\(([^\\(\\)]+)\\)");
		while(true) {
			Matcher matcher = groupPattern.matcher(expressionText);
			if(matcher.find()) {
				BaseExpression expression = extractExpressions(matcher.group(2), params, initialParams);
				params.put(expression.getResult(), expression);
				expressionText = matcher.replaceFirst(matcher.group(1) + expression.getResult());
			} else {
				break;
			}
		}
		return extractExpressions(expressionText, params, initialParams);		
	}

	private BaseExpression extractExpressions(String expressionText, Map<String, Object> params, int initialParams) {	
		BaseExpression result = null;
		for(ExpressionType regex : ExpressionType.values()) {
			result = extractAll(expressionText, params, initialParams, result, regex);
			if(result != null) {
				expressionText = result.getResult();
			}
		}
		return result;
	}

	private BaseExpression extractAll(String expressionText, Map<String, Object> params, int initialParams,
			BaseExpression result, ExpressionType regex) {
		BaseExpression newExpression = null;
		String expressionText1 = expressionText;
		int index = params.size() - initialParams;
		do {
			index++;
			String expressionName = "Exp" + index;
			if(newExpression != null) {
				expressionText1 = newExpression.getResult();
				result = newExpression;
			}
			newExpression = buildExpression(expressionText1, params, expressionName, regex);
		} while(newExpression != null);
		return result;
	}
	
	public BaseExpression buildExpression(String expressionText, Map<String, Object> params, String replace, ExpressionType regex) {
		BaseExpression result = new BaseExpression(regex);
		if(result.findAndReplace(expressionText, replace)) {
			params.put(replace, result);
			return result;
		}
		return null;
	}
}
