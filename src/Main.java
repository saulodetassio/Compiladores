public class Main {
    public static void main(String[] args) {
        String input = "45  + 89   -       876";
        Parser parser = new Parser(input.getBytes());
        parser.parse();
    }
}

