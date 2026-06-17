package CommandInterface;


import CommandInterface.Commands.Analysis.AcceptsCommand;
import CommandInterface.Commands.Analysis.ReportCommand;
import CommandInterface.Commands.Analysis.TraceCommand;
import CommandInterface.Commands.Configuration.*;
import CommandInterface.Commands.Execution.*;
import CommandInterface.Commands.File.CloseCommand;
import CommandInterface.Commands.File.OpenCommand;
import CommandInterface.Commands.File.SaveAsCommand;
import CommandInterface.Commands.File.SaveCommand;
import CommandInterface.Commands.HelpAndExit.ExitCommand;
import CommandInterface.Commands.HelpAndExit.HelpCommand;
import CommandInterface.Commands.Machine.ListCommand;
import CommandInterface.Commands.Machine.NewTMCommand;
import CommandInterface.Commands.Machine.PrintCommand;
import Mains.FileManager;

import java.util.*;

/**
 * Регистър на командите. Намира и изпълнява команди, връща String или хвърля грешка.
 */
public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();
    private final FileManager fileManager;

    public CommandRegistry(FileManager fileManager) {
        this.fileManager = fileManager;
        register(new OpenCommand(fileManager));
        register(new CloseCommand(fileManager));
        register(new SaveCommand(fileManager));
        register(new SaveAsCommand(fileManager));
        register(new ListCommand(fileManager));
        register(new PrintCommand(fileManager));
        register(new NewTMCommand(fileManager));
        register(new AddStateCommand(fileManager));
        register(new SetStartCommand(fileManager));
        register(new AddAcceptCommand(fileManager));
        register(new AddRejectCommand(fileManager));
        register(new AddTransCommand(fileManager));
        register(new RemoveTransCommand(fileManager));
        register(new CheckDetCommand(fileManager));
        register(new InitCommand(fileManager));
        register(new StepCommand(fileManager));
        register(new RunCommand(fileManager));
        register(new StatusCommand(fileManager));
        register(new TapeCommand(fileManager));
        register(new ResetCommand(fileManager));
        register(new AcceptsCommand(fileManager));
        register(new TraceCommand(fileManager));
        register(new ReportCommand(fileManager));
        register(new HelpCommand(fileManager, commands));
        register(new ExitCommand(fileManager));
    }

    private void register(Command cmd) { commands.put(cmd.getName(), cmd); }

    public String executeCommand(String input) throws CommandException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length == 0) return null;
        Command cmd = commands.get(parts[0].toLowerCase());
        if (cmd == null) throw new CommandException("Unknown command: " + parts[0]);
        return cmd.execute(parts);
    }

    public Map<String, Command> getCommands() { return commands; }
}
