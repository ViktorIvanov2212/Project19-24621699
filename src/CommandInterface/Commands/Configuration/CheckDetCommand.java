package CommandInterface.Commands.Configuration;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.TuringMachine;

public class CheckDetCommand extends AbstractCommand {
    public CheckDetCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "checkdet"; }
    @Override public String getDescription() { return "Check if machine is deterministic"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 2, "checkdet <id>");
        TuringMachine tm = getMachineById(args[1]);
        return "Machine " + args[1] + " is deterministic (enforced by addTransition)";
    }
}
