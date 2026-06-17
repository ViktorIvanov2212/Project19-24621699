package CommandInterface.Commands.Analysis;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.ExecutionResult;
import Mains.FileManager;
import Mains.TuringMachine;

public class AcceptsCommand extends AbstractCommand {
    public AcceptsCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "accepts"; }
    @Override public String getDescription() { return "Check if machine accepts word"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 3, "accepts <id> <word> [max=<n>]");
        TuringMachine tm = getMachineById(args[1]);
        int max = parseMaxSteps(args, 3, 1000);
        tm.init(args[2]);
        ExecutionResult res = tm.run(max);
        return "Machine " + (res.status() == ExecutionResult.Status.ACCEPTED ? "ACCEPTS" : "REJECTS/HALTS") +
                " word: " + args[2] + " (Status:" + res.status() + ")";
    }
}
