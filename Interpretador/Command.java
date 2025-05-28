package Interpretador;

public class Command {
    public enum Type {
        ADD, SUB, PUSH, POP, PRINT;
    }

    public Type type;
    public String arg = "";

    public Command(String[] command) {
        type = Type.valueOf(command[0].toUpperCase());
        if (command.length > 1) {
            arg = command[1];
        }
    }

    @Override
    public String toString() {
        return type.name() + " " + arg;
    }
}

