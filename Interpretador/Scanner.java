package Interpretador;

import java.util.HashMap;
import java.util.Map;

public class Scanner {
    private byte[] input;
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

    private char peek() {
        if (current >= input.length) return '\0';
        return (char) input[current];
    }

    private void advance() {
        current++;
    }

    private void skipWhitespace() {
        char ch = peek();
        while (ch == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
            advance();
            ch = peek();
        }
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
               c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private Token number() {
        int start = current;
        while (isDigit(peek())) {
            advance();
        }
        String num = new String(input, start, current - start);
        return new Token(TokenType.NUMBER, num);
    }

    private Token identifier() {
        int start = current;
        while (isAlphaNumeric(peek())) {
            advance();
        }
        String id = new String(input, start, current - start);
        TokenType type = keywords.get(id);
        if (type == null) {
            type = TokenType.IDENT;
        }
        return new Token(type, id);
    }

    public Token nextToken() {
        skipWhitespace();
        char ch = peek();

        if (ch == '\0') {
            return new Token(TokenType.EOF, "");
        }

        if (isDigit(ch)) {
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


