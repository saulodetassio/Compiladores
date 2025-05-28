public class Main {
    public static void main(String[] args) {
        String input = "289-85+0+69";
        Parser parser = new Parser(input.getBytes());
        parser.parse();
    }
}

