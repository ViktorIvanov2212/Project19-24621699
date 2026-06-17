package CommandInterface.Commands.Configuration;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.Transition;
import Mains.TuringMachine;

public class AddTransCommand extends AbstractCommand {
    public AddTransCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "addtrans"; }
    @Override public String getDescription() { return "Add transition: <id> <from> <read> <to> <write> <move>"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 7, "addtrans <id> <fromState> <read> <toState> <write> <move>");
        requireOpenFile();
        TuringMachine tm = getMachineById(args[1]);

        String from = args[2];
        char read = args[3].charAt(0);
        String to = args[4];
        char write = args[5].charAt(0);
        Transition.MoveDirection dir;
        try { dir = Transition.MoveDirection.valueOf(args[6].toUpperCase()); }
        catch (IllegalArgumentException e) { throw new CommandException("Invalid move direction. Use L, R, or S"); }

        if (!tm.getStates().containsKey(from) || !tm.getStates().containsKey(to)) {
            throw new CommandException("Source or target state does not exist");
        }

        Transition trans = new Transition(from, read, to, write, dir);
        if (!tm.addTransition(trans)) {
            throw new CommandException("Transition conflict: deterministic constraint violated for (" + from + ", " + read + ")");
        }
        return "Transition added: δ(" + from + ", " + read + ") = (" + to + ", " + write + ", " + dir + ")";
    }
}
