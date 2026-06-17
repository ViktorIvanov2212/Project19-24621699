package CommandInterface.Commands.Machine;

import CommandInterface.AbstractCommand;
import Mains.FileManager;
import Mains.TuringMachine;

import java.util.Map;

public class ListCommand extends AbstractCommand {
    public ListCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "list"; }
    @Override public String getDescription() { return "List loaded machines"; }
    @Override public String execute(String[] args) {
        if (fileManager.getLoadedMachines().isEmpty()) return "No machines loaded";
        StringBuilder sb = new StringBuilder("Loaded machines:\n");
        fileManager.getLoadedMachines().forEach((id, tm) ->
                sb.append("  ID: ").append(id).append(" - ").append(tm.getName()).append("\n"));
        return sb.toString().trim();
    }
}
