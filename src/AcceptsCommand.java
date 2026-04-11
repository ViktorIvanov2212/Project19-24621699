public class AcceptsCommand extends AbstractCommand {
    public AcceptsCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "accepts"; }

    @Override
    public String getDescription() { return "Check if machine accepts word"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 3, "accepts <id> <word> [max=<n>]")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            int maxSteps = parseMaxSteps(args, 3, 1000);
            boolean accepts = tm.accepts(args[2], maxSteps);
            System.out.println("Machine " + (accepts ? "ACCEPTS" : "REJECTS") + " the word: " + args[2]);
        }
        return true;
    }
}
