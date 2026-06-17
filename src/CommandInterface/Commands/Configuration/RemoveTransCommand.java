package CommandInterface.Commands.Configuration;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.Transition;
import Mains.TuringMachine;

import java.util.Map;

public class RemoveTransCommand extends AbstractCommand {
    public RemoveTransCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "removetrans"; }
    @Override public String getDescription() { return "Remove transition"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 4, "removetrans <id> <state> <symbol>");
        requireOpenFile();
        TuringMachine tm = getMachineById(args[1]);
        char symbol = args[3].charAt(0);
        Map<Character, Transition> map = tm.getTransitions().get(args[2]);
        if (map == null || !map.containsKey(symbol)) throw new CommandException("Transition not found");
        map.remove(symbol);
        return "Transition (" + args[2] + ", " + symbol + ") removed";
    }
}
