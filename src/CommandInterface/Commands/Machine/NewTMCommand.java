package CommandInterface.Commands.Machine;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;
import Mains.TuringMachine;

public class NewTMCommand extends AbstractCommand {
    public NewTMCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "newtm"; }
    @Override public String getDescription() { return "Create new TM"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 2, "newtm <name>");
        requireOpenFile();
        TuringMachine tm = new TuringMachine(args[1]);
        fileManager.addMachine(tm);
        return "Created TM ID:" + tm.getId() + " Name:" + tm.getName();
    }
}
