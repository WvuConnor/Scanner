public class Scanner {
	enum TokenType{
		KEYWORD, OPERATOR, IDENTIFIER, LITERAL, PARENTHESIS, BRACKET, SEMICOLON, EOI;
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
	} //End Token Class
	
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
	private static final String operators[] = {"+", "++", "==", "--", "&&", "&", "-", "%", "/", "=", "<"};
	
	//State Transition Array
	private static final int STATE_TRANSITION[][]= {
			{LITERAL, IDENTIFIER, OPERATOR, KEYWORD, LITERAL, PARENTHESIS, BRACKET, WHITESPACE, END_OF_INPUT, ERROR}, //START STATE
			{ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR}, //State 1: Number State
			{}, //State 2: IDENTIFIER
			{}, //State 3: OPERATOR
			{}, //State 4: KEYWORD
			{}, //State 5: LITERAL
			{}, //State 6: PARENTHESIS
			
			
	};
	
	
	
} //end scanner class