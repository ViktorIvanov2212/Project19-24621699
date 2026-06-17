package CommandInterface.Commands.File;

import CommandInterface.AbstractCommand;
import CommandInterface.CommandException;
import Mains.FileManager;

public class SaveCommand extends AbstractCommand {
    public SaveCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "save"; }
    @Override public String getDescription() { return "Save machines to current file"; }
    @Override public String execute(String[] args) throws CommandException {
        if (fileManager.getCurrentFile() == null) throw new CommandException("No file is open");
        return fileManager.save() ? "Saved successfully" : "Failed to save";
    }
}
