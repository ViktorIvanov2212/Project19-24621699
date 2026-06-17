package CommandInterface.Commands.HelpAndExit;

import CommandInterface.AbstractCommand;
import Mains.FileManager;

public class ExitCommand extends AbstractCommand {
    public ExitCommand(FileManager fm) { super(fm); }
    @Override public String getName() { return "exit"; }
    @Override public String getDescription() { return "Exit program"; }
    @Override public String execute(String[] args) { return "__EXIT__"; }
}
