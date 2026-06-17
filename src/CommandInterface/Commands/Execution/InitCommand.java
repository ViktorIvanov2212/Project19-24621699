package CommandInterface.Commands.Execution;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.State;
import Mains.TuringMachine;

public class InitCommand extends AbstractCommand {
    public InitCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "init"; }
    @Override public String getDescription() { return "Initialize machine with input word"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 3, "init <id> <input>");
        TuringMachine tm = getMachineById(args[1]);
        if (tm.getStates().isEmpty()) throw new CommandException("Machine has no states defined");
        if (tm.getStates().values().stream().noneMatch(s -> s.getType() == State.Type.START)) {
            throw new CommandException("No start state defined. Use 'setstart' first.");
        }
        tm.init(args[2]);
        return "Machine " + args[1] + " initialized with input: '" + args[2] + "'";
    }
}
