public class SetStartCommand extends AbstractCommand {
    public SetStartCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "setstart"; }

    @Override
    public String getDescription() { return "Set start state"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 3, "setstart <id> <state>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            tm.setStartState(args[2]);
            System.out.println("Start state set to " + args[2]);
            fileManager.markChanged();
        }
        return true;
    }
}
