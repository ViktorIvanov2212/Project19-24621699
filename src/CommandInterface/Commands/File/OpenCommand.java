package CommandInterface.Commands.File;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;

public class OpenCommand extends AbstractCommand {
    public OpenCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "open"; }
    @Override public String getDescription() { return "Open a file and load machines"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 2, "open <filename>");
        if (!fileManager.open(args[1])) throw new CommandException("Failed to open file: " + args[1]);
        return "Successfully opened " + args[1];
    }
}
