package CommandInterface.Commands.Analysis;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.TuringMachine;

import java.util.List;

public class TraceCommand extends AbstractCommand {
    public TraceCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "trace"; }
    @Override public String getDescription() { return "Show first k configurations"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 4, "trace <id> <word> <k> [max=<n>]");
        TuringMachine tm = getMachineById(args[1]);
        int k = Integer.parseInt(args[3]);
        int max = parseMaxSteps(args, 4, 1000);
        tm.init(args[2]);
        StringBuilder sb = new StringBuilder("Trace for '" + args[2] + "':\n");
        sb.append("INIT: State=").append(tm.getCurrentState().getName())
                .append(" Head=").append(tm.getTape().getHeadPosition())
                .append(" Tape=").append(tm.getTape().getWindow(0, Math.max(tm.getTape().getHeadPosition()+10, 10))).append("\n");
        for (int i = 0; i < k && tm.isRunning(); i++) {
            tm.step();
            sb.append("STEP ").append(tm.getSteps()).append(": State=").append(tm.getCurrentState().getName())
                    .append(" Head=").append(tm.getTape().getHeadPosition())
                    .append(" Tape=").append(tm.getTape().getWindow(0, Math.max(tm.getTape().getHeadPosition()+10, 10))).append("\n");
        }
        return sb.toString().trim();
    }
}
