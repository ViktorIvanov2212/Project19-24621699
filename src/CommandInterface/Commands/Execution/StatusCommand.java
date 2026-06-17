package CommandInterface.Commands.Execution;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.TuringMachine;

public class StatusCommand extends AbstractCommand {
    public StatusCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "status"; }
    @Override public String getDescription() { return "Show execution status"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 2, "status <id>");
        TuringMachine tm = getMachineById(args[1]);
        return String.format("State:%s | Steps:%d | Running:%b | Head:%d",
                tm.getCurrentState().getName(), tm.getSteps(), tm.isRunning(), tm.getTape().getHeadPosition());
    }
}
