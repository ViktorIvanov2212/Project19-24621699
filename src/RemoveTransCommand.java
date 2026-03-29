public class RemoveTransCommand extends AbstractCommand {
    public RemoveTransCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "removetrans"; }

    @Override
    public String getDescription() { return "Remove transition"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 4, "removetrans <id> <state> <symbol>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            Transition removed = tm.removeTransition(args[2], args[3].charAt(0));
            if (removed != null) {
                System.out.println("Transition removed: " + removed);
                fileManager.markChanged();
            } else {
                System.out.println("No such transition found");
            }
        }
        return true;
    }
}
