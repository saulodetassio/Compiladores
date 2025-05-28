import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos = 0;
    private final int length;

    public Lexer(String input) {
        this.input = input;
        this.length = input.length();
    }

    private char currentChar() {
        return input.charAt(pos);
    }

    private boolean isAtEnd() {
        return pos >= length;
    }

    private void skipWhitespace() {
        while (!isAtEnd() && Character.isWhitespace(currentChar())) {
            pos++;
        }
    }

    private Token number() {
        StringBuilder sb = new StringBuilder();
        while (!isAtEnd() && Character.isDigit(currentChar())) {
            sb.append(currentChar());
            pos++;
        }
        return new Token(TokenType.NUMBER, sb.toString());
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (!isAtEnd()) {
            skipWhitespace();

            if (isAtEnd()) break;

            char ch = currentChar();

            if (Character.isDigit(ch)) {
                tokens.add(number());
            } else if (ch == '+') {
                tokens.add(new Token(TokenType.PLUS, "+"));
                pos++;
            } else if (ch == '-') {
                tokens.add(new Token(TokenType.MINUS, "-"));
                pos++;
            } else {
                throw new RuntimeException("Caractere inválido: " + ch);
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    // Para testes
    public static void main(String[] args) {
        Lexer lexer = new Lexer("45 + 89 - 876");
        List<Token> tokens = lexer.tokenize();
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
