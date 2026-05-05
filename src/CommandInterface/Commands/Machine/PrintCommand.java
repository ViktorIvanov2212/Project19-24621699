package CommandInterface.Commands.Machine;

import CommandInterface.AbstractCommand;
import Mains.FileManager;
import Mains.TuringMachine;

public class PrintCommand extends AbstractCommand {
    public PrintCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "print"; }

    @Override
    public String getDescription() { return "Print information about machine"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 2, "print <id>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            System.out.println("=== Machine: " + tm.getName() + " ===");
            System.out.println(tm);
        }
        return true;
    }
}