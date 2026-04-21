public class LoadTMCommand extends AbstractCommand {
    public LoadTMCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "loadtm"; }

    @Override
    public String getDescription() { return "Load machine from file"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 2, "loadtm <filename>")) return true;
        System.out.println("Loading machine from " + args[1] + "...");
        return true;
    }
}
