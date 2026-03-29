public class AddStateCommand extends AbstractCommand {
    public AddStateCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "addstate"; }

    @Override
    public String getDescription() { return "Add state to machine"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 3, "addstate <id> <state>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            if (tm.addState(args[2])) {
                System.out.println("State " + args[2] + " added to machine " + args[1]);
                fileManager.markChanged();
            } else {
                System.out.println("State " + args[2] + " already exists");
            }
        }
        return true;
    }
}
