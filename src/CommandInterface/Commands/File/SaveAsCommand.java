package CommandInterface.Commands.File;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;

public class SaveAsCommand extends AbstractCommand {
    public SaveAsCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "saveas"; }
    @Override public String getDescription() { return "Save to a new file"; }
    @Override public String execute(String[] args) throws CommandException {
        validateArgs(args, 2, "saveas <filename>");
        return fileManager.saveAs(args[1]) ? "Saved as " + args[1] : "Failed to save";
    }
}
