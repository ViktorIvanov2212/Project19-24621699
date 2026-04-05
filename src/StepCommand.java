public class StepCommand extends AbstractCommand {
    public StepCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "step"; }

    @Override
    public String getDescription() { return "Execute one step"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 2, "step <id>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            if (tm.step()) {
                System.out.println("Step executed. State: " + tm.getCurrentState() +
                        ", Head: " + tm.getHeadPosition());
            } else {
                System.out.println("Machine halted. Final state: " + tm.getCurrentState());
            }
        }
        return true;
    }
}
