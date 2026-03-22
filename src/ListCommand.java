import java.util.Map;

public class ListCommand extends AbstractCommand {
    public ListCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "list"; }

    @Override
    public String getDescription() { return "Lists all loaded machines"; }

    @Override
    public boolean execute(String[] args) {
        Map<Integer, TuringMachine> machines = fileManager.getLoadedMachines();
        if (machines.isEmpty()) {
            System.out.println("No machines loaded");
            return true;
        }
        System.out.println("Loaded machines:");
        for (Map.Entry<Integer, TuringMachine> entry : machines.entrySet()) {
            System.out.println("  ID: " + entry.getKey() + " - " + entry.getValue().getName());
        }
        return true;
    }
}
