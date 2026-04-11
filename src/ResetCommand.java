public class ResetCommand extends AbstractCommand {
    public ResetCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "reset"; }

    @Override
    public String getDescription() { return "Reset machine execution"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 2, "reset <id>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            tm.reset();
            System.out.println("Machine " + args[1] + " reset");
        }
        return true;
    }
}
