public class CloseCommand extends AbstractCommand {
    public CloseCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "close"; }

    @Override
    public String getDescription() { return "Closes currently opened file"; }

    @Override
    public boolean execute(String[] args) {
        return fileManager.close();
    }
}
