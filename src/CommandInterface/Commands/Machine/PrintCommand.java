package CommandInterface.Commands.Machine;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.TuringMachine;

public class PrintCommand extends AbstractCommand {
    public PrintCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "print"; }
    @Override public String getDescription() { return "Print machine details"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 2, "print <id>");
        TuringMachine tm = getMachineById(args[1]);
        StringBuilder sb = new StringBuilder("Machine: ").append(tm.getName()).append(" (ID:").append(tm.getId()).append(")\n");
        sb.append("States: ");
        tm.getStates().values().forEach(s -> sb.append(s.getName()).append("(").append(s.getType()).append(") "));
        sb.append("\nTransitions:\n");
        tm.getTransitions().forEach((k, v) -> v.values().forEach(t -> sb.append("  ").append(t).append("\n")));
        return sb.toString().trim();
    }
}