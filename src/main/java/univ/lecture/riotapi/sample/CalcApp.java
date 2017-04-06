package com.riotgames.sample;

import java.util.Arrays;
import java.util.Stack;

/**
 * Calculator application
 */
public class CalcApp {

	private String[] postfix;
	private Stack<String> oStack;
	private Stack<Double> vStack;
	public static void main(String[] args) {
		final CalcApp app = new CalcApp();
		final StringBuilder outputs = new StringBuilder();
		Arrays.asList(args).forEach(value -> outputs.append(value + " "));
		System.out.print("Addition of values: " + outputs + " = ");
		System.out.println(app.calc(args));
		
	}

	public double calc(String[] tokens) {

		this.token2Postfix(tokens);

		return this.evalPostfix();
	}

	public double evalPostfix() {
		int p = 0;
		String curToken;
		Double popToken1, popToken2, result;
		this.vStack = new Stack<Double>();

		while (p < this.postfix.length && this.postfix[p] != null) {
			curToken = this.postfix[p++];
			if (this.isDigit(curToken)) {
				this.vStack.push(Double.parseDouble(String.valueOf(curToken)));
			} else {
				popToken1 = this.vStack.pop();
				popToken2 = this.vStack.pop();
				Operator operator = Operator.findOperator(curToken);
				this.vStack.push(operator.evaluate(popToken2, popToken1));
			}
		}
		result = this.vStack.pop();
		return result;
	}

	public boolean isDigit(String token) {
		if (token.charAt(0) >= '0' && token.charAt(0) <= '9')
			return true;
		else
			return false;
	}

	public boolean token2Postfix(String[] args) {
		int i = 0;
		int p = 0;
		String[] tokens = args;

		String curToken, poppedToken, topToken;
		this.oStack = new Stack<String>();
		this.postfix = new String[tokens.length];

		while (i < tokens.length) {
			curToken = tokens[i++];
			if (this.isDigit(curToken))
				this.postfix[p++] = curToken;
			else {
				if (curToken.charAt(0) == ')') {
					if (!this.oStack.isEmpty())
						poppedToken = (String) this.oStack.pop();
					else
						return false;

					while (poppedToken.charAt(0) != '(') {
						this.postfix[p++] = poppedToken;
						if (!this.oStack.isEmpty())
							poppedToken = (String) this.oStack.pop();
						else
							return false;
					}
				} else {
					int inComingP = inComingPrecedence(curToken);
					if (!this.oStack.isEmpty()) {
						topToken = (String) this.oStack.peek();
						while (inStackPrecedence(topToken) >= inComingP) {
							poppedToken = (String) this.oStack.pop();
							this.postfix[p++] = poppedToken;
							if (!this.oStack.isEmpty())
								topToken = (String) this.oStack.peek();
							else
								break;
						}
					}
					this.oStack.push(curToken);
				}
			}
		}
		while (!this.oStack.isEmpty()) {
			poppedToken = (String) this.oStack.pop();
			this.postfix[p++] = poppedToken;
		}
		return true;
	}

	private int inComingPrecedence(String givenToken) {
		char tempChar = givenToken.charAt(0);

		switch (tempChar) {
		case '+':
		case '-':
			return 12;
		case '*':
		case '/':
			return 13;
		case '(':
			return 20;
		case ')':
			return 19;
		default:
			return -1;
		}

	}

	private int inStackPrecedence(String givenToken) {

		char tempChar = givenToken.charAt(0);

		switch (tempChar) {
		case '+':
		case '-':
			return 12;
		case '*':
		case '/':
			return 13;
		case '(':
			return 0;
		case ')':
			return 19;
		default:
			return -1;
		}

	}

}
