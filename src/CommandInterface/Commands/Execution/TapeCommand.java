package CommandInterface.Commands.Execution;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.TuringMachine;

public class TapeCommand extends AbstractCommand {
    public TapeCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "tape"; }
    @Override public String getDescription() { return "Show tape content"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 2, "tape <id> [from=<a>] [to=<b>]");
        TuringMachine tm = getMachineById(args[1]);
        int from = 0, to = Math.max(tm.getTape().getHeadPosition() + 10, 20);
        for (int i = 2; i < args.length; i++) {
            if (args[i].startsWith("from=")) from = Integer.parseInt(args[i].substring(5));
            else if (args[i].startsWith("to=")) to = Integer.parseInt(args[i].substring(3));
        }
        return "Tape:[" + tm.getTape().getWindow(from, to) + "] Head:" + tm.getTape().getHeadPosition();
    }
}
