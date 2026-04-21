import java.util.Scanner;

public class TuringMachineApplication {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        CommandRegistry registry = new CommandRegistry(fileManager);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Turing Machine Simulator ===");
        System.out.println("Type 'help' for available commands");
        System.out.println();

        boolean running = true;
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.trim().isEmpty()) {
                continue;
            }

            running = registry.executeCommand(input);
        }

        scanner.close();
    }
}
