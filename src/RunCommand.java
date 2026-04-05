public class RunCommand extends AbstractCommand {
    public RunCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "run"; }

    @Override
    public String getDescription() { return "Run machine"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 2, "run <id> [max=<n>]")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            int maxSteps = parseMaxSteps(args, 2, 1000);
            boolean running = tm.run(maxSteps);
            if (running) {
                System.out.println("Machine stopped after " + maxSteps + " steps (limit reached)");
            } else {
                System.out.println("Machine halted after " + tm.getStepsExecuted() + " steps");
            }
            System.out.println("Final state: " + tm.getCurrentState());

            if (tm.getAcceptStates().contains(tm.getCurrentState())) {
                System.out.println("Result: ACCEPT");
            } else if (tm.getRejectStates().contains(tm.getCurrentState())) {
                System.out.println("Result: REJECT");
            } else {
                System.out.println("Result: HALT (no transition)");
            }
        }
        return true;
    }
}
