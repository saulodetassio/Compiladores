public class Parser {
    private Scanner scan;
    private Token currentToken;

    public Parser(byte[] input) {
        this.scan = new Scanner(input);
        this.currentToken = scan.nextToken();
    }

    private void nextToken() {
        currentToken = scan.nextToken();
    }

    private void match(TokenType t) {
        if (currentToken.type == t) {
            nextToken();
        } else {
            throw new Error("syntax error: esperado " + t + ", encontrado " + currentToken.type);
        }
    }

    private void number() {
        System.out.println("push " + currentToken.lexeme);
        match(TokenType.NUMBER);
    }

    private void oper() {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            number();
            System.out.println("add");
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            number();
            System.out.println("sub");
            oper();
        }
        // caso contrário: oper -> ε
    }

    private void expr() {
        number();
        oper();
    }

    public void parse() {
        expr();
        if (currentToken.type != TokenType.EOF) {
            throw new Error("syntax error: fim inesperado");
        }
    }
}
