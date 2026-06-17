package CommandInterface.Commands.Machine;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.TuringMachine;

public class SaveTMCommand extends AbstractCommand {
    public SaveTMCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "savetm"; }
    @Override public String getDescription() { return "Save specific machine to file"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 3, "savetm <id> <filename>");
        TuringMachine tm = getMachineById(args[1]);
        try {
            fileManager.saveSingleMachine(tm, args[2]);
            return "Machine " + tm.getId() + " saved to " + args[2];
        } catch (Exception e) {
            throw new CommandException("Failed to save machine: " + e.getMessage());
        }
    }
}
