package CommandInterface.Commands.Execution;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.ExecutionResult;
import Mains.FileManager;
import Mains.TuringMachine;

public class StepCommand extends AbstractCommand {
    public StepCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "step"; }
    @Override public String getDescription() { return "Execute one step"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 2, "step <id>");
        TuringMachine tm = getMachineById(args[1]);
        ExecutionResult.Status status = tm.step();
        return String.format("Step executed. Status:%s | State:%s | Head:%d | Tape:%s",
                status, tm.getCurrentState().getName(), tm.getTape().getHeadPosition(),
                tm.getTape().getWindow(0, Math.max(tm.getTape().getHeadPosition()+10, 10)));
    }
}
