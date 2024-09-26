import java.util.ArrayList;

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
		public TokenType toType(int t)
		{
			switch (t){
				case 1: 
				case 2: 
				case 3: 
				case 4: 
				case 5: 
				case 6: 
				case 7: 
				case 8: 
				case 9: 
				default: return TokenType.EOI;
			}
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
	boolean isDigit(char in){
		if(in >= '0' && in <= '9')
			return true;
		return false;
	}

	// Checks if a character is a letter
	boolean isLetter(char in){
		if((in >= 'a' && in <= 'z') || (in >= 'A' && in <= 'Z'))
			return true;
		return false;
	}

	// Checks if a character is whitespace
	boolean isWhitespace(char in){
		if(in == ' ' || in == '\t' || in == '\n')
			return true;
		return false;
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
			if(isLetter(in.charAt(i)))
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
	public ArrayList<Token> scan(String input){
		
		int state = START;
		int tokenIndex = 0;
		var tokens = new ArrayList<Token>();



		// Loop through the input string
		for(int i = 0; i < input.length(); i++)
		{
			
			char c = input.charAt(i);
			Token token;

			if (Character.isWhitespace(c))
			{
				int count = i + 1;
				String whitespace = "" + c;
				while (count != input.length() && Character.isWhitespace(input.charAt(count)))
				{
					whitespace+= input.charAt(count); 
					count++;
					
				}

				token = new Token(TokenType.KEYWORD, whitespace);
				tokens.add(token);

				// push cursor
				i = count - 1;
				continue;
			}
			
				if (isDigit(c))
			{
				
				int count = i + 1;
				String num = "" + c;
				while (count != input.length() && Character.isDigit(input.charAt(count)))
				{
					num+= input.charAt(count); 
					count++;
					
				}

				token = new Token(TokenType.LITERAL, num);
				tokens.add(token);

				// push cursor
				i = count - 1;
				continue;
			}
			
				if (isLetter(c))
			{
				token = new Token(TokenType.LITERAL, String.valueOf(c));
				// single letter

				if (i == input.length() -1)
					{
						tokens.add(token);
						continue;
					}
				if ( i < input.length() - 1 && !isLetter(input.charAt(i+1)))
				{
					
					tokens.add(token);
					continue;
				}


				int count = i + 1;
				String word = "" + c;
				boolean found = false;

				// if next char is also a letter, then it is a keyword
				while ( count != input.length() && isLetter( (input.charAt(count))) && !found)
				{
					word+= input.charAt(count);
					count++;
				
				}
				// take word and get the correct token name
				
				// push cursor up to new position; count is what is looked at next
				i = count - 1;
				TokenType type;
				if (word.equals("if") || word.equals("for") || word.equals("while")))
					type = TokenType.KEYWORD;
					else if (word.equals("int") || word.equals("float") || word.equals("double") || word.equals("string") || word.equals("char"))
					type = TokenType.IDENTIFIER;
					else
					type = TokenType.LITERAL;

					tokens.add(new Token(type, word) );
				continue;
				}


				
				}
			
			
			
			
			// Check if the character is a digit
			// tokens.add( new Token(TokenType.KEYWORD, isKeyword(input)) );
		
	
		return tokens;
	}
} //end scanner class

