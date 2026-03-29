public class CheckDetCommand extends AbstractCommand {
    public CheckDetCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "checkdet"; }

    @Override
    public String getDescription() { return "Check if machine is deterministic"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 2, "checkdet <id>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            if (tm.isDeterministic()) {
                System.out.println("Machine " + args[1] + " is deterministic");
            } else {
                System.out.println("Machine " + args[1] + " is NOT deterministic");
            }
        }
        return true;
    }
}
