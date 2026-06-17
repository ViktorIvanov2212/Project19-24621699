package Mains;

import CommandInterface.CommandException;
import CommandInterface.CommandRegistry;

import java.util.Scanner;

public class TuringMachineApplication {
    public static void main(String[] args) {
        FileManager fm = new FileManager();
        CommandRegistry reg = new CommandRegistry(fm);
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Turing Machine Simulator ===");
        System.out.println("Type 'help' for commands");

        boolean running = true;
        while (running) {
            System.out.print("> ");
            String input = sc.nextLine();
            if (input.trim().isEmpty()) continue;

            try {
                String result = reg.executeCommand(input);
                if ("__EXIT__".equals(result)) {
                    running = false;
                } else if (result != null) {
                    System.out.println(result);
                }
            } catch (CommandException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Exiting...");
        sc.close();
    }
}
