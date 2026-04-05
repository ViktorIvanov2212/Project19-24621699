public class InitCommand extends AbstractCommand {
    public InitCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "init"; }

    @Override
    public String getDescription() { return "Initialize machine with input"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 3, "init <id> <input>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            tm.init(args[2]);
            System.out.println("Machine " + args[1] + " initialized with input: " + args[2]);
        }
        return true;
    }
}
