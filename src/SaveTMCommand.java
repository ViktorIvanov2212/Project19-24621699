public class SaveTMCommand extends AbstractCommand {
    public SaveTMCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "savetm"; }

    @Override
    public String getDescription() { return "Save machine to file"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 3, "savetm <id> <filename>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            System.out.println("Saving machine " + args[1] + " (" + tm.getName() + ") to " + args[2]);
        }
        return true;
    }
}
