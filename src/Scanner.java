import java.util.map;
import java.util.list;


public class Scanner {

	enum TokenType{
		KEYWORD, OPERATOR, IDENTIFIER, LITERAL, PARENTHESIS, BRACKET, SEMICOLON, EOI;
	}

	//creates an immutable map
	Map<Character, Integer> stateMap = Map.ofEntries(
		new AbstractMap.SimpleEntry<Object, Integer>('(', 0),
		new AbstractMap.SimpleEntry<Object, Integer>(')', 1),
		new AbstractMap.SimpleEntry<Character, Integer>('{', 2),
		new AbstractMap.SimpleEntry<Character, Integer>('}', 3),
		new AbstractMap.SimpleEntry<Character, Integer>('&', 4),
		new AbstractMap.SimpleEntry<Character, Integer>('|', 5),
		new AbstractMap.SimpleEntry<Character, Integer>('!', 6),
		new AbstractMap.SimpleEntry<Character, Integer>('=', 7),
		new AbstractMap.SimpleEntry<Character, Integer>('.', 8),
		new AbstractMap.SimpleEntry<Character, Integer>('+', 9),
		new AbstractMap.SimpleEntry<Character, Integer>('-', 10),
		new AbstractMap.SimpleEntry<Character, Integer>('*', 11),
		new AbstractMap.SimpleEntry<Character, Integer>('/', 12),
		new AbstractMap.SimpleEntry<Character, Integer>('%', 13),
		new AbstractMap.SimpleEntry<Character, Integer>('<', 14),
		new AbstractMap.SimpleEntry<Character, Integer>('>', 15),
		new AbstractMap.SimpleEntry<Character, Integer>("/[0-9]/", 16),
		new AbstractMap.SimpleEntry<Character, Integer>('w', 17),
		new AbstractMap.SimpleEntry<Character, Integer>('h', 18),
		new AbstractMap.SimpleEntry<Character, Integer>('i', 19),
		new AbstractMap.SimpleEntry<Character, Integer>('l', 20),
		new AbstractMap.SimpleEntry<Character, Integer>('e', 21),
		new AbstractMap.SimpleEntry<Character, Integer>('f', 22),
		new AbstractMap.SimpleEntry<Character, Integer>('o', 23),
		new AbstractMap.SimpleEntry<Character, Integer>('r', 24),
		new AbstractMap.SimpleEntry<Character, Integer>('a', 25),
		new AbstractMap.SimpleEntry<Character, Integer>('t', 26),
		new AbstractMap.SimpleEntry<Character, Integer>('n', 27),
		new AbstractMap.SimpleEntry<Character, Integer>('c', 28),
		new AbstractMap.SimpleEntry<Character, Integer>("/[bdgjkmpqsu-vx-zA-Z]/", 29)
	);

	Integer[][] stateTransition = {
		{1, 2, 3, 4, 5, 7, 44, 9, 12, 29, 31, 33, 34, 35, 36, 38, 11, 13, 46, 25, 46, 46, 18, 46, 46, 46, 46, 46, 40, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, 6, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, 8, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, 10, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, 12, null, null, null, null, null, null, null, 11, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 12, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 14, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 15, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 16, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 17, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 21, 46, 46, 19, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 20, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 22, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 23, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 46, 24, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 26, 46, 46, 46, 46, 27, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 46, 28, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, 30, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, 32, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, 37, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, 39, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 41, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 42, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 43, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, 45, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46}
	};

	TokenType[] acceptingState = {null, TokenType.PARENTHESIS, TokenType.PARENTHESIS, TokenType.BRACKET, TokenType.BRACKET, null, TokenType.OPERATOR, null, TokenType.OPERATOR, 
	TokenType.OPERATOR, TokenType.OPERATOR, TokenType.LITERAL, TokenType.LITERAL, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, 
	TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.IDENTIFIER, TokenType.KEYWORD, 
	TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, 
	TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.OPERATOR, 
	TokenType.OPERATOR, TokenType.IDENTIFIER};

	
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
	
	
	
	
	public static void main(String args[]) {
		
		
		
	}//end main
}//end scanner class

