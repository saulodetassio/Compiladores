public class Parser {
    private byte[] input;
    private int current;

    public Parser(byte[] input) {
        this.input = input;
        this.current = 0;
    }

    public void parse() {
        expr();
        if (peek() != '\0') {
            throw new Error("syntax error: extra characters after expression");
        }
    }

    private char peek() {
        if (current < input.length)
            return (char) input[current];
        return '\0';
    }

    private void match(char c) {
        if (c == peek()) {
            current++;
        } else {
            throw new Error("syntax error: expected '" + c + "', found '" + peek() + "'");
        }
    }

    private void expr() {
        digit();
        oper();
    }

    private void digit() {
        if (Character.isDigit(peek())) {
            System.out.println("push " + peek());
            match(peek());
        } else {
            throw new Error("syntax error: expected digit");
        }
    }

    private void oper() {
        if (peek() == '+') {
            match('+');
            digit();
            System.out.println("add");
            oper();
        } else if (peek() == '-') {
            match('-');
            digit();
            System.out.println("sub");
            oper();
        } else if (peek() == '\0') {
            return;
        } else {
            throw new Error("syntax error: unexpected character '" + peek() + "'");
        }
    }
}