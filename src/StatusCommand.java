public class StatusCommand extends AbstractCommand {
    public StatusCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "status"; }

    @Override
    public String getDescription() { return "Show execution status"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 2, "status <id>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            System.out.println("Current state: " + tm.getCurrentState());
            System.out.println("Head position: " + tm.getHeadPosition());
            System.out.println("Steps executed: " + tm.getStepsExecuted());
            System.out.println("Running: " + tm.isRunning());
        }
        return true;
    }
}
