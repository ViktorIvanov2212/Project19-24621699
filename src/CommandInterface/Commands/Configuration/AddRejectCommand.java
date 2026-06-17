package CommandInterface.Commands.Configuration;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.State;
import Mains.TuringMachine;

public class AddRejectCommand extends AbstractCommand {
    public AddRejectCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "addreject"; }
    @Override public String getDescription() { return "Mark state as rejecting"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 3, "addreject <id> <stateName>");
        requireOpenFile();
        TuringMachine tm = getMachineById(args[1]);
        State s = tm.getStates().get(args[2]);
        if (s == null) throw new CommandException("State not found");
        s.setType(State.Type.REJECT);
        return "State " + args[2] + " marked as REJECT";
    }
}
