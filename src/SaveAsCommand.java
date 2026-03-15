public class SaveAsCommand extends AbstractCommand {
    public SaveAsCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "saveas"; }

    @Override
    public String getDescription() { return "Saves the currently open file in <filename>"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 2, "saveas <filename>")) return true;
        return fileManager.saveAs(args[1]);
    }
}
