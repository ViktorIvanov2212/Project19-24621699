package CommandInterface.Commands.Execution;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.TuringMachine;

public class ResetCommand extends AbstractCommand {
    public ResetCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "reset"; }
    @Override public String getDescription() { return "Reset execution"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 2, "reset <id>");
        TuringMachine tm = getMachineById(args[1]);
        tm.reset(); // Изисква метод в TM
        return "Machine " + args[1] + " reset";
    }
}
