import java.util.*;


public class Scanner {

    enum TokenType {
        KEYWORD, OPERATOR, IDENTIFIER, LITERAL, PARENTHESIS, BRACKET, SEMICOLON, WHITESPACE, EOI;
    }

    static class Token {
        TokenType type;
        String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Token{type=" + type + ", value=" + value + "}";
        }
    }
			//creates an immutable map
	static Map<Character, Integer> stateMap = Map.ofEntries(
		new AbstractMap.SimpleEntry<Character, Integer>('(', 1),
		new AbstractMap.SimpleEntry<Character, Integer>(')', 2),
		new AbstractMap.SimpleEntry<Character, Integer>('{', 3),
		new AbstractMap.SimpleEntry<Character, Integer>('}', 4),
		new AbstractMap.SimpleEntry<Character, Integer>('&', 5),
		new AbstractMap.SimpleEntry<Character, Integer>('|', 6),
		new AbstractMap.SimpleEntry<Character, Integer>('!', 7),
		new AbstractMap.SimpleEntry<Character, Integer>('=', 8),
		new AbstractMap.SimpleEntry<Character, Integer>('.', 9),
		new AbstractMap.SimpleEntry<Character, Integer>('+', 10),
		new AbstractMap.SimpleEntry<Character, Integer>('-', 11),
		new AbstractMap.SimpleEntry<Character, Integer>('*', 12),
		new AbstractMap.SimpleEntry<Character, Integer>('/', 13),
		new AbstractMap.SimpleEntry<Character, Integer>('%', 14),
		new AbstractMap.SimpleEntry<Character, Integer>('<', 15),
		new AbstractMap.SimpleEntry<Character, Integer>('>', 16),
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
		new AbstractMap.SimpleEntry<Character, Integer>('n', 27)
	);



	static Integer[][] stateTransition = {
		{1, 2, 3, 4, 5, 7, 44, 9, 12, 29, 31, 33, 34, 35, 36, 38, 13, null, 25, null, null, 18, null, null, null, null, null, 40},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, 6, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, 8, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, 10, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, 12, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 14, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 15, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 16, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 17, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 21, null, null, 19, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 20, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 22, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 23, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 24, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 26, null, null, null, null, 27, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 28, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, 30, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, 32, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, 37, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, 39, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 41, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 42, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 43, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, 45, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
	};

	static TokenType[] acceptingStates = {null, TokenType.PARENTHESIS, TokenType.PARENTHESIS, TokenType.BRACKET, TokenType.BRACKET, null, TokenType.OPERATOR, null, TokenType.OPERATOR, 
		TokenType.OPERATOR, TokenType.OPERATOR, TokenType.LITERAL, TokenType.LITERAL, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, 
		TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.IDENTIFIER, TokenType.KEYWORD, 
		TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, 
		TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.OPERATOR, 
		TokenType.OPERATOR};

	private static List<Token> scan(String input){
		List<Token> tokens = new ArrayList<>();
		int state = 0; // Start
		StringBuilder currentToken = new StringBuilder();

		for(char c : input.toCharArray()){
				//Skip Whitespace
				if(Character.isWhitespace(c)){
					continue;
				}
				Integer column = stateMap.get(c);
				if(stateTransition[state][column] != null){
					state = stateTransition[state][column];
					currentToken.append(c);
					//check to see if accepting state  to finalize token
					if(acceptingStates[state] != null){
						tokens.add(new Token(acceptingStates[state], currentToken.toString()));
						currentToken.setLength(0);
						state = 0;
					}
				}
		}
		return tokens;
	}
		public static void main(String args[]){
			System.out.println("Enter the input you'd like to tokenize: ");
			String input = System.console().readLine();
			List<Token> tokens = scan(input);
			for(Token token : tokens){
				System.out.println(token);
			}
		}
}
