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
    private static final int[][] stateTransitionTable = {
        //   [letter] [digit] [operator] [paren] [bracket] [whitespace] [EOF]
        {1, 2, 3, 4, 5, 0, -1}, //State 0 = Start 
        {1, 1, -1, -1, -1, -1, -1}, // State 1 = Identifier
        {-1, 2, -1, -1, -1, -1, -1}, // State 2 = Number
        {-1, -1, -1, -1, -1, -1, -1}, // State 3 = Operator
        {-1, -1, -1, -1, -1, -1, -1}, // State 4 = Parenthesis
        {-1, -1, -1, -1, -1, -1, -1}, // State 5 = Bracket
    };

    // Dictionary for keywords and operators
    private static final Map<String, TokenType> dictionary = new HashMap<>();
    static {
        // Keywords
        dictionary.put("if", TokenType.KEYWORD);
        dictionary.put("while", TokenType.KEYWORD);
        dictionary.put("for", TokenType.KEYWORD);
        // Operators
        dictionary.put("<", TokenType.OPERATOR);
        dictionary.put("<=", TokenType.OPERATOR);
        dictionary.put(">", TokenType.OPERATOR);
        dictionary.put(">=", TokenType.OPERATOR);
        dictionary.put("=", TokenType.OPERATOR);
        dictionary.put("==", TokenType.OPERATOR);
        dictionary.put("+", TokenType.OPERATOR);
        dictionary.put("-", TokenType.OPERATOR);
				dictionary.put("!", TokenType.OPERATOR);
				dictionary.put("&&", TokenType.OPERATOR);
				dictionary.put("||", TokenType.OPERATOR);
				//Semicolon/Brackets/Parenthesis
				dictionary.put("{", TokenType.BRACKET);
				dictionary.put("}", TokenType.BRACKET);
				dictionary.put("(", TokenType.PARENTHESIS);
				dictionary.put(")", TokenType.PARENTHESIS);
				dictionary.put(";", TokenType.SEMICOLON);

				//Whitespace
				dictionary.put(" ", TokenType.WHITESPACE);
    }

    public static List<Token> scan(String input) {
        List<Token> tokens = new ArrayList<>();
        int state = 0;
        StringBuilder tokenValue = new StringBuilder();
        char[] chars = input.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            int column = getColumn(c);

            if (column == -1) {
                throw new IllegalArgumentException("Invalid input character: " + c);
            }
            if (c == '<' || c == '>' || c == '!') {
                if (i + 1 < chars.length && chars[i + 1] == '=') {
                    tokenValue.append(c).append('=');
                    i++;
                    tokens.add(new Token(TokenType.OPERATOR, tokenValue.toString()));
                    tokenValue.setLength(0);
                    state = 0; 
                    continue;
                }
            }
						if (c == '&' || c == '|') {
							if (i + 1 < chars.length && chars[i + 1] == c) {
									tokenValue.append(c).append(c); 
									i++; 
									tokens.add(new Token(TokenType.OPERATOR, tokenValue.toString()));
									tokenValue.setLength(0);
									state = 0;
									continue;
							}
					}
            if (c == '(' || c == ')') {
                if (tokenValue.length() > 0) {
                    tokens.add(classifyToken(tokenValue.toString()));
                    tokenValue.setLength(0);
                }
                tokens.add(new Token(TokenType.PARENTHESIS, Character.toString(c)));
                state = 0; 
                continue;
            }
						if(c == '{' || c == '}'){
							if(tokenValue.length() > 0){
								tokens.add(classifyToken(tokenValue.toString()));
								tokenValue.setLength(0);
							}
							tokens.add(new Token(TokenType.BRACKET, Character.toString(c)));
							state = 0;
							continue;
						}
						if (Character.isWhitespace(c)) {
							if(tokenValue.length() > 0){
									tokens.add(classifyToken(tokenValue.toString()));
									tokenValue.setLength(0);
							}
							tokens.add(new Token(TokenType.WHITESPACE, " "));
							state = 0; 
							continue;
						}
						if(c == ';'){
							if(tokenValue.length() > 0){
								tokens.add(classifyToken(tokenValue.toString()));
								tokenValue.setLength(0);
							}
							tokens.add(new Token(TokenType.SEMICOLON, ";"));
							state = 0;
							continue;
						}

            //find next state depending on column
            int nextState = stateTransitionTable[state][column];

            if (nextState == -1) {
                if (tokenValue.length() > 0) {
                    tokens.add(classifyToken(tokenValue.toString()));
                    tokenValue.setLength(0);
                }
                state = 0;
            } else {
                tokenValue.append(c);
                state = nextState;
            }
        }

        if (tokenValue.length() > 0) {
            tokens.add(classifyToken(tokenValue.toString()));
        }

				tokens.add(new Token(TokenType.EOI, "End of Input"));

        return tokens;
    }

    private static Token classifyToken(String tokenValue) {
        if (dictionary.containsKey(tokenValue)) {
            return new Token(dictionary.get(tokenValue), tokenValue);
        } else if (Character.isDigit(tokenValue.charAt(0))) {
            return new Token(TokenType.LITERAL, tokenValue);
        } else {
            return new Token(TokenType.IDENTIFIER, tokenValue);
        }
    }

    private static int getColumn(char c) {
        if (Character.isLetter(c)) {
            return 0; //Column 0 (Letter)
        } else if (Character.isDigit(c)) {
            return 1; //Column 1 (Number)
        } else if ("+-*/<>=!|&".indexOf(c) >= 0) {
            return 2; // Column 2 (Operators)
        } else if (c == '(' || c == ')') {
            return 3; //Column 3 (Parenthesis)
        } else if (c == '{' || c == '}') {
            return 4; //Column 4 (Bracket)
        } else if (Character.isWhitespace(c)) {
            return 5; //Column 5 (Whitespace)
        } else if (c == ';') {
            return 6; //Column 6 (Semicolon)
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
				System.out.println("Enter the input you'd like to tokenize: ");
				String input = System.console().readLine();
        List<Token> tokens = scan(input);
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
