package CommandInterface.Commands.Configuration;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.State;
import Mains.TuringMachine;

public class SetStartCommand extends AbstractCommand {
    public SetStartCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "setstart"; }
    @Override public String getDescription() { return "Set start state"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 3, "setstart <id> <stateName>");
        requireOpenFile();
        TuringMachine tm = getMachineById(args[1]);
        State s = tm.getStates().get(args[2]);
        if (s == null) throw new CommandException("State " + args[2] + " not found");
        s.setType(State.Type.START);
        tm.setStartState(args[2]); // Изисква метод в TM (виж patch по-долу)
        return "Start state set to " + args[2];
    }
}
