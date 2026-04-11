public class ReportCommand extends AbstractCommand {
    public ReportCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "report"; }

    @Override
    public String getDescription() { return "Show execution report"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 3, "report <id> <word> [max=<n>]")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            int maxSteps = parseMaxSteps(args, 3, 1000);

            tm.init(args[2]);
            tm.run(maxSteps);

            System.out.println("=== EXECUTION REPORT ===");
            System.out.println("Input: " + args[2]);
            System.out.println("Steps executed: " + tm.getStepsExecuted());
            System.out.println("Final state: " + tm.getCurrentState());
            System.out.println("Final tape: " + tm.getCurrentTape());
            System.out.println("Head position: " + tm.getHeadPosition());

            if (tm.getAcceptStates().contains(tm.getCurrentState())) {
                System.out.println("Result: ACCEPT");
            } else if (tm.getRejectStates().contains(tm.getCurrentState())) {
                System.out.println("Result: REJECT");
            } else {
                System.out.println("Result: HALT");
            }
            System.out.println("========================");
        }
        return true;
    }
}