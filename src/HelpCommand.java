import java.util.Map;

public class HelpCommand extends AbstractCommand {
    private Map<String, Command> commands;

    public HelpCommand(FileManager fileManager, Map<String, Command> commands) {
        super(fileManager);
        this.commands = commands;
    }

    @Override
    public String getName() { return "help"; }

    @Override
    public String getDescription() { return "Prints this information"; }

    @Override
    public boolean execute(String[] args) {
        System.out.println("The following commands are supported:");
        for (Command cmd : commands.values()) {
            System.out.println(String.format("  %-15s - %s", cmd.getName(), cmd.getDescription()));
        }
        return true;
    }

    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }
}
