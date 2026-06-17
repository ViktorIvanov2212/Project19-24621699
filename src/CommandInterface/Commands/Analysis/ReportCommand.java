package CommandInterface.Commands.Analysis;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.ExecutionResult;
import Mains.FileManager;
import Mains.TuringMachine;

public class ReportCommand extends AbstractCommand {
    public ReportCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "report"; }
    @Override public String getDescription() { return "Full execution report"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 3, "report <id> <word> [max=<n>]");
        TuringMachine tm = getMachineById(args[1]);
        int max = parseMaxSteps(args, 3, 1000);
        tm.init(args[2]);
        ExecutionResult res = tm.run(max);
        return String.format("=== REPORT ===" +
                        "\nInput:%s" +
                        "\nStatus:%s" +
                        "\nSteps:%d" +
                        "\nFinalState:%s" +
                        "\nHead:%d" +
                        "\nTape:%s" +
                        "\n================",
                args[2], res.status(), res.stepsExecuted(), res.finalState().getName(), res.headPosition(), res.tapeSnapshot());
    }
}