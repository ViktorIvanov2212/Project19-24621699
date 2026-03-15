public class SaveCommand extends AbstractCommand {
    public SaveCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "save"; }

    @Override
    public String getDescription() { return "Saves the currently open file"; }

    @Override
    public boolean execute(String[] args) {
        return fileManager.save();
    }
}
