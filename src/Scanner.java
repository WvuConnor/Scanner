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
	

	/**
	 * BEGIN HELPER METHODS
	 */

	//Check if a character is a digit - done (using Char.isDigit() java method)
	
	//Check if a character is a letter - done (using Char.isLetter() java method)

	//Check if a character is a whitespace - done (using Char.isWhitespace() java method)

	//Check if a character is an operator +, -, ETC - needs written

	//Check if a character is a symbol '(, ), [, ]' - needs written

	//check if a token is an identifier - needs written
		//would check if every character is a letter, thats it


	//check if a token is a keyword - needs written
		//would check if every char is a letter, and if the string is in the keywords array

	
	











	
} //end scanner class

