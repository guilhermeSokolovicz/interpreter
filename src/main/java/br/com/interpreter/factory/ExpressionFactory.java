package br.com.interpreter.factory;

import java.util.ArrayList;
import java.util.Map;

import br.com.interpreter.function.BaseExpression;

public class ExpressionFactory {
	
	public BaseExpression buildExpression(String expressionText, Map<String, Object> params, String replace, String regex) {
		BaseExpression result = new BaseExpression(regex);
		if(result.findAndReplace(expressionText, replace)) {
			params.put(replace, result);
			return result;
		}
		return null;
	}
	
	public BaseExpression build(String expressionText, Map<String, Object> params) {
		int initialParams = params.size();
		ArrayList<String> regexs = new ArrayList<>();
		regexs.add("@[ ]*\\+[ ]*@");
		regexs.add("@[ ]*\\-[ ]*@");
		regexs.add("@[ ]*\\*[ ]*@");
		regexs.add("@[ ]*/[ ]*@");
		regexs.add("@[ ]*\\>[ ]*@");
		regexs.add("@[ ]*\\<[ ]*@");
		regexs.add("@[ ]*\\=[ ]*@");
		regexs.add("@[ ]*!\\=[ ]*@");
		regexs.add("@[ ]*\\>\\=[ ]*@");
		regexs.add("@[ ]*\\<\\=[ ]*@");
		regexs.add("!@");
		regexs.add("@[ ]*\\&\\&[ ]*@");
		regexs.add("@[ ]*\\|\\|[ ]*@");		
		BaseExpression result = null;
		for(String regex : regexs) {
			result = extractAll(expressionText, params, initialParams, result, regex);
			if(result != null) {
				expressionText = result.getResult();
			}
		}
		return result;		
	}

	private BaseExpression extractAll(String expressionText, Map<String, Object> params, int initialParams,
			BaseExpression result, String regex) {
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

}
