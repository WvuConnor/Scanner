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

	// Accepting State Tokens
	Token OPEN_PARENTHESIS new Token(TokenType.OPEN_PARENTHESIS, value:"\(");
	
	//keywords
	private static final String inputs[] = {"(",")","{","}","&","|","=",".","+","-","*","/","%","<",">","0-9","w","h","i","l","e","f","o","r","a","t","n","a-z"};
	private static final int states[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39};
	
	//State Transition Array
	private static final Token STATE_TRANSITION[][]= {
		{new Token(TokenType.PARENTHESIS, "("), new Token(TokenType.PARENTHESIS, ")"), new Token(TokenType.BRACKET, "{"), new Token(TokenType.BRACKET, "}"), new Token(TokenType.OPERATOR, "&"), new Token(TokenType.OPERATOR, "|"), new Token(TokenType.OPERATOR, "="), new Token(TokenType.OPERATOR, "."), new Token(TokenType.OPERATOR, "+"), new Token(TokenType.OPERATOR, "-"), new Token(TokenType.OPERATOR, "*"), new Token(TokenType.OPERATOR, "/"), new Token(TokenType.OPERATOR, "%"), new Token(TokenType.OPERATOR, "<"), new Token(TokenType.OPERATOR, ">"), new Token(TokenType.LITERAL, "0-9"), new Token(TokenType.KEYWORD, "w"), new Token(TokenType.KEYWORD, "h"), new Token(TokenType.KEYWORD, "i"), new Token(TokenType.KEYWORD, "l"), new Token(TokenType.KEYWORD, "e"), new Token(TokenType.KEYWORD, "f"), new Token(TokenType.KEYWORD, "o"), new Token(TokenType.KEYWORD, "r"), new Token(TokenType.KEYWORD, "a"), new Token(TokenType.KEYWORD, "t"), new Token(TokenType.KEYWORD, "n"), new Token(TokenType.IDENTIFIER, "a-z")},
		{},
		{},
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
	  
	

} //end scanner class

