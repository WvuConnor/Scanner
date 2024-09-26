import java.util.ArrayList;
import java.util.List;


public class Scanner {
	enum TokenType{
		KEYWORD, OPERATOR, IDENTIFIER, LITERAL, PARENTHESIS, BRACKET, SEMICOLON, EOI, WHITESPACE, ERROR;
	}
	static class Token {
		TokenType type;
		String value;
		
		//Constructor
		public Token(TokenType type, String value){
			this.type = type;
			this.value = value;
		}
		@Override
		public String toString() {
			String currentToken = "Token(type=" + type + ", value=" + value + ")";
			return currentToken;
		}
	}//End Token Class
	
	//STATES
	private static final int START = 0;
	private static final int LITERAL = 1;
	private static final int IDENTIFIER = 2;
	private static final int OPERATOR = 3;
	private static final int KEYWORD = 4;
	private static final int PARENTHESIS = 5;
	private static final int BRACKET = 6;
	private static final int WHITESPACE = 7;
	private static final int END_OF_INPUT = 8;
	private static final int ERROR = -1;
	
	//keywords
	private static final String keywords[] = {"for", "while", "if"};
	private static final String operators[] = {"+", "++", "==", "--", "&&", "&", "-", "%", "/", "=", "<", ">"};
	
	//State Transition Array
	private static final int STATE_TRANSITION[][]= {
			{LITERAL, IDENTIFIER, OPERATOR, KEYWORD, LITERAL, PARENTHESIS, BRACKET, WHITESPACE, END_OF_INPUT, ERROR}, //START STATE
			{LITERAL, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, END_OF_INPUT, ERROR, }, //State 1: Literal (Number)
			{ERROR, IDENTIFIER, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, END_OF_INPUT, ERROR}, //State 2: IDENTIFIER
			{ERROR, ERROR, OPERATOR, ERROR, ERROR, ERROR, ERROR, ERROR, END_OF_INPUT, ERROR}, //State 3: OPERATOR
			{ERROR, ERROR, ERROR, KEYWORD, ERROR, ERROR, ERROR, ERROR, END_OF_INPUT, ERROR}, //State 4: KEYWORD
			{ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, END_OF_INPUT, ERROR}, //State 5: PARENTHESIS
			{ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, END_OF_INPUT, ERROR}, //State 6: Bracket
			{ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, END_OF_INPUT, ERROR}, //State 7: Whitespace
			{ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR}, //State 8: End_OF_INPUT
			{ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR}, //ERROR STATE	
	};
	public static void main(String args[]) {
		String input = "123";
		List<Token> tokens = scan(input);
		for(Token token : tokens){
			System.out.println(token);
		}
	}//end main

	//Method to scan given input
	//Param input string
	//Return List of tokens
	private static List<Token> scan(String input){
		List<Token> tokens = new ArrayList<>();
		StringBuilder currentToken = new StringBuilder();
		int currentState = START;

		for(char c : input.toCharArray()){
				switch(currentState){
					case START:
							if(Character.isDigit(c)){
								currentState = LITERAL;
								currentToken.append(c);
							}else if(Character.isLetter(c)){
								currentState = IDENTIFIER;
								currentToken.append(c);
							}else if(isOperator(c)){
								currentState = OPERATOR;
								currentToken.append(c);
								tokens.add(new Token(TokenType.OPERATOR, currentToken.toString()));
								currentToken.setLength(0);
								currentState = START;
							}else if(c == '(' || c == ')'){
								currentState = PARENTHESIS;
								currentToken.append(c);
								tokens.add(new Token(TokenType.PARENTHESIS, currentToken.toString()));
								currentToken.setLength(0);
								currentState = START;
							}else if(c == '{' || c == '}'){
								currentState = BRACKET;
								currentToken.append(c);
								tokens.add(new Token(TokennType.BRACKET, currentToken.toString()));
							}
				}
		}
	




		return tokens;
	}

	private static Boolean isKeyword(String input){
		for(String keyword : keywords){
			if(keyword.equals(input)){
				return true;
			}
		}
		return false;
	}
	private static Boolean isOperator(char c){
		for(String op : operators){
			if(op.indexOf(c) != -1){
				return true;
			}
		}
		return false;
	}

}//end scanner class

