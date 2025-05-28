package Interpretador;

public class Main {
    public static void main(String[] args) throws Exception {
        String input = ""
                + "let a = 42 + 5 - 8;\n"
                + "let b = 56 + 8;\n"
                + "print a + b + 6;\n";

        Parser p = new Parser(input.getBytes());
        p.parse();

        Interpretador i = new Interpretador(p.output());
        i.run();
    }
}
