import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private Map<String, Command> commands;
    private FileManager fileManager;

    public CommandRegistry(FileManager fileManager) {
        this.fileManager = fileManager;
        this.commands = new HashMap<>();
        registerCommands();
    }

    private void registerCommands() {
        registerCommand(new OpenCommand(fileManager));
        registerCommand(new CloseCommand(fileManager));
        registerCommand(new SaveCommand(fileManager));
        registerCommand(new SaveAsCommand(fileManager));

        registerCommand(new ListCommand(fileManager));
        registerCommand(new PrintCommand(fileManager));
        registerCommand(new NewTMCommand(fileManager));
        registerCommand(new LoadTMCommand(fileManager));
        registerCommand(new SaveTMCommand(fileManager));

        registerCommand(new AddStateCommand(fileManager));
        registerCommand(new SetStartCommand(fileManager));
        registerCommand(new AddAcceptCommand(fileManager));
        registerCommand(new AddRejectCommand(fileManager));

        registerCommand(new AddTransCommand(fileManager));
        registerCommand(new RemoveTransCommand(fileManager));
        registerCommand(new CheckDetCommand(fileManager));

        registerCommand(new InitCommand(fileManager));
        registerCommand(new StepCommand(fileManager));
        registerCommand(new RunCommand(fileManager));
        registerCommand(new StatusCommand(fileManager));
        registerCommand(new TapeCommand(fileManager));
        registerCommand(new ResetCommand(fileManager));
        registerCommand(new AcceptsCommand(fileManager));
        registerCommand(new TraceCommand(fileManager));
        registerCommand(new ReportCommand(fileManager));

        HelpCommand helpCommand = new HelpCommand(fileManager, commands);
        registerCommand(helpCommand);
        registerCommand(new ExitCommand(fileManager));
    }

    private void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public boolean executeCommand(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length == 0 || parts[0].isEmpty()) {
            return true;
        }

        String commandName = parts[0].toLowerCase();
        Command command = commands.get(commandName);

        if (command == null) {
            System.out.println("Unknown command: " + commandName);
            System.out.println("Type 'help' for available commands");
            return true;
        }

        try {
            return command.execute(parts);
        } catch (Exception e) {
            System.err.println("Error executing command: " + e.getMessage());
            e.printStackTrace();
            return true;
        }
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }

    public Map<String, Command> getAllCommands() {
        return new HashMap<>(commands);
    }
}
