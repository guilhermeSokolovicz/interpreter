package br.com.interpreter.function;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseExpression {
	private ArrayList<Object> params;
	private static final String PARAM_REGEX = "([a-zA-Z0-9_]+)";
	private String regex;
	
	private String result;
	
	public BaseExpression(String regex) {
		params = new ArrayList<>();
		this.regex = regex.replaceAll("@", PARAM_REGEX);
	}

	public Object getParameter(int i) {
		return params.get(i);
	}

	public boolean findAndReplace(String expressionText, String variableName) {	
		params.clear();
		Pattern pattern = Pattern.compile(regex);
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

}
