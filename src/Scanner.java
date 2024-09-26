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
	private static final int SEMICOLON = 9;
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

	// Checks if a character is a digit
	int isDigit(char in){
		if(in >= '0' && in <= '9')
			return 1;
		return ERROR;
	}

	// Checks if a character is a letter
	int isLetter(char in){
		if((in >= 'a' && in <= 'z') || (in >= 'A' && in <= 'Z'))
			return 1;
		return ERROR;
	}

	// Checks if a character is whitespace
	int isWhitespace(char in){
		if(in == ' ' || in == '\t' || in == '\n')
			return 1;
		return ERROR;
	}
	
	// Returns whether a String is an operator, -1 if not, index of operator if it is
	int isOperator(String in){
		for(int i = 0; i < operators.length; i++)
			// If the character is an operator, return the index of the operator
			if(in.equals(operators[i]))
				return i;
		return ERROR;
	}

	// Returns whether a character is a symbol, -1 if not, type if it is
	int isSymbol(char in){
		if(in == '(' || in == ')')
			return PARENTHESIS;
		if(in == '{' || in == '}')
			return BRACKET;
		if(in == ';')
			return SEMICOLON;
		return ERROR;
	}
	
	// Returns whether a character is an identifier, -1 if not, identifier value if it is
	int isIdentifier(String in){
		for(String key : keywords)
			if(in.equals(key))
				return ERROR;
		for(int i = 0; i < in.length(); i++)
			if(isLetter(in.charAt(i)) == 1)
				return ERROR;
		return IDENTIFIER;
	}

	// Returns whether a String is a keyword, -1 if not, keyword value if it is
	int isKeyword(String in){
		for(String key : keywords)
			if(in.equals(key))
				return KEYWORD;
		return ERROR;
	}


	/**
	 * END HELPER METHODS
	 */


	 /*
	  * not worried about states at this point, just tokenizing strings rn
	  */
	  
	// Scans a string and returns a list of tokens
	public Token[] scan(String input){
		// Token[] tokens = new Token[];
		int state = START;
		int tokenIndex = 0;
		
		// Loop through the input string
		for(int i = 0; i < input.length(); i++)
		{
			char curr = input.charAt(i);

			// Check if the character is a digit


			// tokens.add( new Token(TokenType.KEYWORD, isKeyword(input)) );
		}

		return tokens;

} //end scanner class

