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
            return "Token{type='" + type + "', value='" + value + "'}";
        }
    }
	//creates an immutable map
	static Map<Character, Integer> stateMap = Map.ofEntries(
		new AbstractMap.SimpleEntry<>('(', 0),
		new AbstractMap.SimpleEntry<>(')', 1),
		new AbstractMap.SimpleEntry<>('{', 2),
		new AbstractMap.SimpleEntry<>('}', 3),
		new AbstractMap.SimpleEntry<>('&', 4),
		new AbstractMap.SimpleEntry<>('|', 5),
		new AbstractMap.SimpleEntry<>('!', 6),
		new AbstractMap.SimpleEntry<>('=', 7),
		new AbstractMap.SimpleEntry<>('.', 8),
		new AbstractMap.SimpleEntry<>('+', 9),
		new AbstractMap.SimpleEntry<>('-', 10),
		new AbstractMap.SimpleEntry<>('*', 11),
		new AbstractMap.SimpleEntry<>('/', 12),
		new AbstractMap.SimpleEntry<>('%', 13),
		new AbstractMap.SimpleEntry<>('<', 14),
		new AbstractMap.SimpleEntry<>('>', 15),
		new AbstractMap.SimpleEntry<>('0', 16),
		new AbstractMap.SimpleEntry<>('1', 16),
		new AbstractMap.SimpleEntry<>('2', 16),
		new AbstractMap.SimpleEntry<>('3', 16),
		new AbstractMap.SimpleEntry<>('4', 16),
		new AbstractMap.SimpleEntry<>('5', 16),
		new AbstractMap.SimpleEntry<>('6', 16),
		new AbstractMap.SimpleEntry<>('7', 16),
		new AbstractMap.SimpleEntry<>('8', 16),
		new AbstractMap.SimpleEntry<>('9', 16),

		new AbstractMap.SimpleEntry<>('w', 17),
		new AbstractMap.SimpleEntry<>('h', 18),
		new AbstractMap.SimpleEntry<>('i', 19),
		new AbstractMap.SimpleEntry<>('l', 20),
		new AbstractMap.SimpleEntry<>('e', 21),
		new AbstractMap.SimpleEntry<>('f', 22),
		new AbstractMap.SimpleEntry<>('o', 23),
		new AbstractMap.SimpleEntry<>('r', 24),
		new AbstractMap.SimpleEntry<>('a', 25),
		new AbstractMap.SimpleEntry<>('t', 26),
		new AbstractMap.SimpleEntry<>('n', 27),
		new AbstractMap.SimpleEntry<>('c', 28),
		new AbstractMap.SimpleEntry<>('b', 29),
		new AbstractMap.SimpleEntry<>('g', 29),
		new AbstractMap.SimpleEntry<>('j', 29),
		new AbstractMap.SimpleEntry<>('k', 29),
		new AbstractMap.SimpleEntry<>('m', 29),
		new AbstractMap.SimpleEntry<>('p', 29),
		new AbstractMap.SimpleEntry<>('q', 29),
		new AbstractMap.SimpleEntry<>('s', 29),
		new AbstractMap.SimpleEntry<>('u', 29),
		new AbstractMap.SimpleEntry<>('v', 29),
		new AbstractMap.SimpleEntry<>('x', 29),
		new AbstractMap.SimpleEntry<>('y', 29),
		new AbstractMap.SimpleEntry<>('z', 29)
);

	static Integer[][] stateTransition = {
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
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 12, null, null, null, null, null, null, null, null, null, null, null, null, null},
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
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 41, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 42, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 43, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46},
		{null, null, null, null, null, null, null, 45, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
		{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46}
	};

	static TokenType[] acceptingStates = {null, TokenType.PARENTHESIS, TokenType.PARENTHESIS, TokenType.BRACKET, TokenType.BRACKET, null, TokenType.OPERATOR, null, TokenType.OPERATOR, 
		TokenType.OPERATOR, TokenType.OPERATOR, TokenType.LITERAL, TokenType.LITERAL, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, 
		TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.IDENTIFIER, TokenType.KEYWORD, 
		TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, 
		TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.IDENTIFIER, TokenType.KEYWORD, TokenType.OPERATOR, 
		TokenType.OPERATOR, TokenType.IDENTIFIER};

	private static List<Token> scan(String input){
		input += " "; // Add whitespace to end of input to finalize last token
		List<Token> tokens = new ArrayList<>();
		int state = 0; // Start
		int bookmark = 0; // Start of token

		for(int i = 0; i < input.length(); i++){
				char c = input.charAt(i);
				
				if(Character.isWhitespace(c)){
					tokens.add(new Token(acceptingStates[state], input.substring(bookmark, i)));
					state = 0;
					bookmark = i+1;
					continue;
				}

				if(stateTransition[state][stateMap.get(c)] != null){
					//check to see if accepting state  to finalize token
					if(acceptingStates[state] != null && i < input.length() - 1 && stateTransition[state][i+1] != null && acceptingStates[stateTransition[state][i+1]] != acceptingStates[state])
						tokens.add(new Token(acceptingStates[state], input.substring(bookmark, i)));
					else if (input.length() == i)
						tokens.add(new Token(acceptingStates[state], input.substring(bookmark, i)));
				} else {
					if(acceptingStates[state] != null)
						tokens.add(new Token(acceptingStates[state], input.substring(bookmark, i)));
				}
				state = 0;
				bookmark = i;
				state = stateTransition[state][stateMap.get(c)];
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
