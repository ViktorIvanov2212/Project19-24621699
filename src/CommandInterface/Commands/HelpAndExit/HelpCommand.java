package CommandInterface.Commands.HelpAndExit;

import CommandInterface.AbstractCommand;
import CommandInterface.Command;
import Mains.FileManager;

import java.util.Map;

import java.util.Map;
public class HelpCommand extends AbstractCommand {
    private final Map<String, Command> cmds;
    public HelpCommand(FileManager fm, Map<String, Command> cmds) { super(fm); this.cmds = cmds; }
    @Override public String getName() { return "help"; }
    @Override public String getDescription() { return "Show commands"; }
    @Override public String execute(String[] args) {
        StringBuilder sb = new StringBuilder("Available commands:\n");
        cmds.values().forEach(c -> sb.append(String.format("  %-12s - %s\n", c.getName(), c.getDescription())));
        return sb.toString();
    }
}
