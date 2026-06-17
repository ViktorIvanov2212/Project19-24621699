package CommandInterface.Commands.Machine;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;

public class LoadTMCommand extends AbstractCommand {
    public LoadTMCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "loadtm"; }
    @Override public String getDescription() { return "Load machine from file into memory"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 2, "loadtm <filename>");
        try {
            fileManager.loadMachineFromFile(args[1]);
            return "Successfully loaded machine from " + args[1];
        } catch (Exception e) {
            throw new CommandException("Failed to load machine: " + e.getMessage());
        }
    }
}
