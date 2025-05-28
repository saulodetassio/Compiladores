package Interpretador;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class Interpretador {
    private final List<String[]> commands;
    private final Stack<Integer> stack = new Stack<>();
    private final Map<String, Integer> variables = new HashMap<>();

    public Interpretador(String input) {
        // Usa regex para dividir por qualquer tipo de quebra de linha
        String[] lines = input.split("\\r?\\n");
        
        this.commands = Arrays.stream(lines)
                .map(String::trim)
                .filter(s -> !s.startsWith("//") && !s.isEmpty())
                .map(s -> s.split("\\s+")) // Divide por qualquer espaço em branco
                .collect(Collectors.toList());
    }

    public boolean hasMoreCommands() {
        return !commands.isEmpty();
    }

    public Command nextCommand() {
        if (!hasMoreCommands()) {
            throw new IllegalStateException("No more commands available");
        }
        return new Command(commands.remove(0));
    }

    public void run() {
        while (hasMoreCommands()) {
            Command command = nextCommand();
            try {
                executeCommand(command);
            } catch (Exception e) {
                System.err.println("Error executing command: " + command);
                e.printStackTrace();
                break; // Ou continue, dependendo do comportamento desejado
            }
        }
    }

    private void executeCommand(Command command) {
        switch (command.type) {
            case ADD:
                checkStackSize(2);
                stack.push(stack.pop() + stack.pop());
                break;
                
            case SUB:
                checkStackSize(2);
                int subtrahend = stack.pop();
                int minuend = stack.pop();
                stack.push(minuend - subtrahend);
                break;
                
            case PUSH:
                Integer value = variables.get(command.arg);
                stack.push(value != null ? value : parseNumber(command.arg));
                break;
                
            case POP:
                checkStackSize(1);
                variables.put(command.arg, stack.pop());
                break;
                
            case PRINT:
                checkStackSize(1);
                System.out.println(stack.pop());
                break;
                
            default:
                throw new IllegalArgumentException("Unknown command type: " + command.type);
        }
    }

    private int parseNumber(String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number or undefined variable: " + arg);
        }
    }

    private void checkStackSize(int requiredSize) {
        if (stack.size() < requiredSize) {
            throw new IllegalStateException(
                "Stack underflow. Required " + requiredSize + 
                " elements, but has only " + stack.size());
        }
    }

    // Classe Command deve ser definida em outro arquivo ou como static inner class
    public static class Command {
        public enum Type { ADD, SUB, PUSH, POP, PRINT }
        
        public final Type type;
        public final String arg;
        
        public Command(String[] parts) {
            if (parts == null || parts.length == 0) {
                throw new IllegalArgumentException("Empty command");
            }
            
            try {
                this.type = Type.valueOf(parts[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown command: " + parts[0]);
            }
            
            // Verifica se o comando tem argumento quando necessário
            if ((type == Type.PUSH || type == Type.POP) && parts.length < 2) {
                throw new IllegalArgumentException("Missing argument for command: " + type);
            }
            
            this.arg = (type == Type.PUSH || type == Type.POP) ? parts[1] : null;
        }
        
        @Override
        public String toString() {
            return type + (arg != null ? " " + arg : "");
        }
    }
}