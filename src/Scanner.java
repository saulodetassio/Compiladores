import java.util.HashMap;
import java.util.Map;

public class Scanner {

    private final byte[] input;
    private int current = 0;

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("let", TokenType.LET);
        keywords.put("print", TokenType.PRINT);
    }

    public Scanner(byte[] input) {
        this.input = input;
    }

    private boolean isAtEnd() {
        return current >= input.length;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return (char) input[current];
    }

    private void advance() {
        current++;
    }

    private void skipWhitespace() {
        while (!isAtEnd()) {
            char ch = peek();
            if (ch == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
                advance();
            } else {
                break;
            }
        }
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || Character.isDigit(c);
    }

    private Token number() {
        int start = current;
        while (Character.isDigit(peek())) {
            advance();
        }
        String n = new String(input, start, current - start);
        return new Token(TokenType.NUMBER, n);
    }

    private Token identifier() {
        int start = current;
        while (isAlphaNumeric(peek())) advance();

        String id = new String(input, start, current - start);
        TokenType type = keywords.getOrDefault(id, TokenType.IDENT);
        return new Token(type, id);
    }

    public Token nextToken() {
        skipWhitespace();

        char ch = peek();

        if (ch == '\0') {
            return new Token(TokenType.EOF, "EOF");
        }

        if (Character.isDigit(ch)) {
            return number();
        }

        if (isAlpha(ch)) {
            return identifier();
        }

        switch (ch) {
            case '+':
                advance();
                return new Token(TokenType.PLUS, "+");
            case '-':
                advance();
                return new Token(TokenType.MINUS, "-");
            case '=':
                advance();
                return new Token(TokenType.EQ, "=");
            case ';':
                advance();
                return new Token(TokenType.SEMICOLON, ";");
            default:
                throw new Error("lexical error at " + ch);
        }
    }
}
