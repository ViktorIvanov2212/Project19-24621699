package CommandInterface.Commands.Configuration;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.State;
import Mains.TuringMachine;

public class AddAcceptCommand extends AbstractCommand {
    public AddAcceptCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "addaccept"; }
    @Override public String getDescription() { return "Mark state as accepting"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 3, "addaccept <id> <stateName>");
        requireOpenFile();
        TuringMachine tm = getMachineById(args[1]);
        State s = tm.getStates().get(args[2]);
        if (s == null) throw new CommandException("State not found");
        s.setType(State.Type.ACCEPT);
        return "State " + args[2] + " marked as ACCEPT";
    }
}
