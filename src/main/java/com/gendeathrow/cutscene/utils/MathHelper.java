package com.gendeathrow.cutscene.utils;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathHelper {

	public static int eval(String infixExpression) 
	{
		Stack<String> stack = new Stack<String>();
		String trimmedExp = infixExpression.replaceAll(" ", "");
		for (String c : split(trimmedExp)) 
		{
			if (")".equals(c)) 
			{
				if (!"(".equals(stack.peek())) 
				{
					String result = performOperation(stack.pop(), stack.pop(), stack.pop()) + "";
					stack.pop(); // remove also the open bracket
					stack.push(result);
				}
			} else {
				stack.push(c);
			}
		}
		return Integer.parseInt(stack.pop());
	}

	private static int performOperation(String operand2, String operator, String operand1) 
	{
		int op1 = Integer.parseInt(operand1);
		int op2 = Integer.parseInt(operand2);
		switch (operator.toCharArray()[0]) 
		{
		case '+':
			return op1 + op2;
		case '-':
			return op1 - op2;
		case '*':
			return op1 * op2;
		case '/':
			return op1 / op2;
		default:
			return 0;
		}
	}

	static String[] split(String exp) 
	{
		ArrayList<String> parts = new ArrayList<String>();
		Pattern pat = Pattern.compile("\\d++|\\+|\\-|\\*|/|\\(|\\)");
		Matcher matcher = pat.matcher(exp);
		while (matcher.find()) 
		{
			parts.add(matcher.group());
		}
		return parts.toArray(new String[0]);
	}

}
