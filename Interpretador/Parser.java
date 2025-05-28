package Interpretador;

public class Parser {
    private Scanner scan;
    private Token currentToken;

    private StringBuilder output = new StringBuilder();

    public Parser(byte[] input) {
        scan = new Scanner(input);
        nextToken();
    }

    private void nextToken() {
        currentToken = scan.nextToken();
    }

    private void match(TokenType t) {
        if (currentToken.type == t) {
            nextToken();
        } else {
            throw new Error("syntax error at token " + currentToken);
        }
    }

    public void parse() {
        statements();
        if (currentToken.type != TokenType.EOF) {
            throw new Error("syntax error: unexpected tokens after end");
        }
    }

    private void statements() {
        while (currentToken.type != TokenType.EOF) {
            statement();
        }
    }

    private void statement() {
        if (currentToken.type == TokenType.LET) {
            letStatement();
        } else if (currentToken.type == TokenType.PRINT) {
            printStatement();
        } else {
            throw new Error("syntax error: expected 'let' or 'print', found " + currentToken);
        }
    }

    private void letStatement() {
        match(TokenType.LET);
        String id = currentToken.lexeme;
        match(TokenType.IDENT);
        match(TokenType.EQ);
        expr();
        output.append("pop ").append(id).append("\n");
        match(TokenType.SEMICOLON);
    }

    private void printStatement() {
        match(TokenType.PRINT);
        expr();
        output.append("print\n");
        match(TokenType.SEMICOLON);
    }

    private void expr() {
        term();
        oper();
    }

    private void oper() {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            term();
            output.append("add\n");
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            term();
            output.append("sub\n");
            oper();
        }
    }

    private void term() {
        if (currentToken.type == TokenType.NUMBER) {
            output.append("push ").append(currentToken.lexeme).append("\n");
            match(TokenType.NUMBER);
        } else if (currentToken.type == TokenType.IDENT) {
            output.append("push ").append(currentToken.lexeme).append("\n");
            match(TokenType.IDENT);
        } else {
            throw new Error("syntax error: expected number or identifier, found " + currentToken);
        }
    }

    public String output() {
        return output.toString();
    }
}
