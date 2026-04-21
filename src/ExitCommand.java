public class ExitCommand extends AbstractCommand {
    public ExitCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "exit"; }

    @Override
    public String getDescription() { return "Exits the program"; }

    @Override
    public boolean execute(String[] args) {
        System.out.println("Exiting the program...");
        return false;
    }
}
