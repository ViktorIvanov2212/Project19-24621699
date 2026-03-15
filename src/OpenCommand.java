public class OpenCommand extends AbstractCommand {
    public OpenCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "open"; }

    @Override
    public String getDescription() { return "Opens a file"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 2, "open <filename>")) return true;
        return fileManager.open(args[1]);
    }
}
