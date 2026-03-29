public class AddAcceptCommand extends AbstractCommand {
    public AddAcceptCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "addaccept"; }

    @Override
    public String getDescription() { return "Add accept state"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 3, "addaccept <id> <state>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            if (tm.addAcceptState(args[2])) {
                System.out.println("Accept state " + args[2] + " added");
                fileManager.markChanged();
            } else {
                System.out.println("Failed to add accept state");
            }
        }
        return true;
    }
}
