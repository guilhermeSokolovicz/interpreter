package br.com.interpreter.function;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.interpreter.expression.ExpressionType;

public class BaseExpression {
	private ArrayList<Object> params;	
	private ExpressionType regex;
	
	private String result;
	
	public BaseExpression(ExpressionType regex) {
		params = new ArrayList<>();
		this.regex = regex;
	}

	public Object getParameter(int i) {
		return params.get(i);
	}

	public boolean findAndReplace(String expressionText, String variableName) {	
		params.clear();
		Pattern pattern = Pattern.compile(regex.getRegex());
		Matcher matcher = pattern.matcher(expressionText);
		if(!matcher.find()) {
			return false;
		}
		for(int i = 1; i <= matcher.groupCount(); i++) {
			params.add(matcher.group(i));
		}
		this.result = matcher.replaceFirst(variableName);
		return true;
	}
	
	public String getResult() {
		return this.result;
	}
	
	public Object solve(Map<String, Object> params) {
		switch (this.regex) {
		case ADD:
			return extractNumericParam(params, 0).add(extractNumericParam(params, 1));
		case AND:
			return extractBooleanParam(params, 0) && extractBooleanParam(params, 1);
		case DIVIDE:
			return extractNumericParam(params, 0).divide(extractNumericParam(params, 1));
		case EQUAL:
			return getParameter(0).equals(getParameter(1));
		case GREATER:
			return extractNumericParam(params, 0).compareTo(extractNumericParam(params, 1)) == 1;
		case GREATER_EQUAL:
			return extractNumericParam(params, 0).compareTo(extractNumericParam(params, 1)) != - 1;
		case LESS:
			return extractNumericParam(params, 0).compareTo(extractNumericParam(params, 1)) == - 1;
		case LESS_EQUAL:
			return extractNumericParam(params, 0).compareTo(extractNumericParam(params, 1)) != 1;
		case MULTIPLY:
			return extractNumericParam(params, 0).multiply(extractNumericParam(params, 1));
		case NOT:
			return !extractBooleanParam(params, 0);
		case NOT_EQUAL:
			return !getParameter(0).equals(getParameter(1));
		case OR:
			return extractBooleanParam(params, 0) || extractBooleanParam(params, 1);
		case SUBTRACT:
			return extractNumericParam(params, 0).subtract(extractNumericParam(params, 1));
		default:
			return null;		
		}
	}

	private Boolean extractBooleanParam(Map<String, Object> params, int paramIndex) {
		Boolean param1 = null;
		if(params.containsKey(this.getParameter(paramIndex))) {
			Object param = params.get(this.getParameter(paramIndex));
			if(param instanceof BaseExpression) {
				param = ((BaseExpression) param).solve(params);
			}
			param1 = (Boolean) param;
		} else {
			param1 = Boolean.valueOf((String) this.getParameter(paramIndex));
		}
		return param1;
	}
	
	private BigDecimal extractNumericParam(Map<String, Object> params, int paramIndex) {
		BigDecimal param1 = null;
		if(params.containsKey(this.getParameter(paramIndex))) {
			Object param = params.get(this.getParameter(paramIndex));
			if(param instanceof BaseExpression) {
				param = ((BaseExpression) param).solve(params);
			}
			param1 = (BigDecimal) param;
		} else {
			param1 = new BigDecimal((String) this.getParameter(paramIndex));
		}
		return param1;
	}

}
