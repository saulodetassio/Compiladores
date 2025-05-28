public class Parser {
    private Scanner scan;
    private char currentToken;

    public Parser(byte[] input) {
        this.scan = new Scanner(input);
        this.currentToken = scan.nextToken();
    }

    private void nextToken() {
        currentToken = scan.nextToken();
    }

    private void match(char t) {
        if (currentToken == t) {
            nextToken();
        } else {
            throw new Error("syntax error: esperado '" + t + "', encontrado '" + currentToken + "'");
        }
    }

    private void digit() {
        if (Character.isDigit(currentToken)) {
            System.out.println("push " + currentToken);
            match(currentToken); // avança após consumir o dígito
        } else {
            throw new Error("syntax error: dígito esperado");
        }
    }

    private void oper() {
        if (currentToken == '+') {
            match('+');
            digit();
            System.out.println("add");
            oper();
        } else if (currentToken == '-') {
            match('-');
            digit();
            System.out.println("sub");
            oper();
        }
        // ε (vazio) está implícito — se não for + nem -, retorna
    }

    private void expr() {
        digit();
        oper();
    }

    public void parse() {
        expr();
    }
}
