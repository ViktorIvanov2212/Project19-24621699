package CommandInterface.Commands.Execution;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.ExecutionResult;
import Mains.FileManager;
import Mains.TuringMachine;

public class RunCommand extends AbstractCommand {
    public RunCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "run"; }
    @Override public String getDescription() { return "Run machine"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 2, "run <id> [max=<n>]");
        TuringMachine tm = getMachineById(args[1]);
        ExecutionResult res = tm.run(parseMaxSteps(args, 2, 1000));
        return String.format("Status:%s | Steps:%d | State:%s | Tape:%s | Head:%d",
                res.status(), res.stepsExecuted(), res.finalState().getName(), res.tapeSnapshot(), res.headPosition());
    }
}
