public class NewTMCommand extends AbstractCommand {
    public NewTMCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "newtm"; }

    @Override
    public String getDescription() { return "Create new Turing machine"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 2, "newTM <name>")) return true;
        TuringMachine tm = new TuringMachine(args[1]);
        fileManager.addMachine(tm);
        System.out.println("Created new Turing machine with ID: " + tm.getId() + ", Name: " + tm.getName());
        return true;
    }
}
