package CommandInterface.Commands.Configuration;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.State;
import Mains.TuringMachine;

public class AddStateCommand extends AbstractCommand {
    public AddStateCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "addstate"; }
    @Override public String getDescription() { return "Add a new state to a machine"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 3, "addstate <id> <stateName>");
        requireOpenFile();
        TuringMachine tm = getMachineById(args[1]);
        String stateName = args[2];
        if (tm.getStates().containsKey(stateName)) {
            throw new CommandException("State '" + stateName + "' already exists in machine " + args[1]);
        }
        State newState = new State(stateName);
        tm.addState(newState);
        return "State '" + stateName + "' added to machine " + args[1];
    }
}
