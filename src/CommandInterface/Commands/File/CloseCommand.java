package CommandInterface.Commands.File;

import CommandInterface.AbstractCommand;
import Mains.FileManager;

public class CloseCommand extends AbstractCommand {
    public CloseCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "close"; }
    @Override public String getDescription() { return "Close current file"; }
    @Override public String execute(String[] args) {
        fileManager.close();
        return "File closed successfully";
    }
}
