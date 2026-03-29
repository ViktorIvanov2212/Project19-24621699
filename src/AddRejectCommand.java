public class AddRejectCommand extends AbstractCommand {
    public AddRejectCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "addreject"; }

    @Override
    public String getDescription() { return "Add reject state"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 3, "addreject <id> <state>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            if (tm.addRejectState(args[2])) {
                System.out.println("Reject state " + args[2] + " added");
                fileManager.markChanged();
            } else {
                System.out.println("Failed to add reject state");
            }
        }
        return true;
    }
}
