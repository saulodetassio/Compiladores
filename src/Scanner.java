public class Scanner {
    private byte[] input;
    private int current;

    public Scanner(byte[] input) {
        this.input = input;
        this.current = 0;
    }

    private char peek() {
        if (current < input.length) {
            return (char) input[current];
        }
        return '\0';
    }

    private void advance() {
        char ch = peek();
        if (ch != '\0') {
            current++;
        }
    }

    private Token number() {
        int start = current;
        while (Character.isDigit(peek())) {
            advance();
        }
        String n = new String(input, start, current - start);
        return new Token(TokenType.NUMBER, n);
    }

    public Token nextToken() {
        char ch = peek();

        if (Character.isWhitespace(ch)) {
            advance();
            return nextToken(); // ignora espaços
        }

        if (ch == '0') {
            advance();
            return new Token(TokenType.NUMBER, "0");
        } else if (Character.isDigit(ch)) {
            return number();
        }

        switch (ch) {
            case '+':
                advance();
                return new Token(TokenType.PLUS, "+");
            case '-':
                advance();
                return new Token(TokenType.MINUS, "-");
            case '\0':
                return new Token(TokenType.EOF, "EOF");
            default:
                throw new Error("lexical error at '" + ch + "'");
        }
    }

    // Para teste rápido
    public static void main(String[] args) {
        String input = "289-85+0+69";
        Scanner scan = new Scanner(input.getBytes());

        Token token;
        do {
            token = scan.nextToken();
            System.out.println(token);
        } while (token.type != TokenType.EOF);
    }
}
