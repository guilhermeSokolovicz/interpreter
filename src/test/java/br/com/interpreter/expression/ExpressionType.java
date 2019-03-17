package br.com.interpreter.expression;

public enum ExpressionType {
	MULTIPLY("@[ ]*\\*[ ]*@"),
	DIVIDE("@[ ]*/[ ]*@"),
	ADD("@[ ]*\\+[ ]*@"),
	SUBTRACT("@[ ]*\\-[ ]*@"),		
	GREATER("@[ ]*\\>[ ]*@"),
	LESS("@[ ]*\\<[ ]*@"),
	EQUAL("@[ ]*\\=[ ]*@"),
	NOT_EQUAL("@[ ]*!\\=[ ]*@"),
	GREATER_EQUAL("@[ ]*\\>\\=[ ]*@"),
	LESS_EQUAL("@[ ]*\\<\\=[ ]*@"),
	NOT("!@"),
	AND("@[ ]*\\&\\&[ ]*@"),
	OR("@[ ]*\\|\\|[ ]*@");	
	private static final String PARAM_REGEX = "([a-zA-Z0-9_]+)";
	private String regex;
	ExpressionType(String regex) {
		this.regex = regex.replaceAll("@", PARAM_REGEX);
	}
	
	public String getRegex() {
		return this.regex;
	}
}
