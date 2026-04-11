import java.util.List;

public class TraceCommand extends AbstractCommand {
    public TraceCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "trace"; }

    @Override
    public String getDescription() { return "Show first k configurations"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 4, "trace <id> <word> <k> [max=<n>]")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            try {
                int k = Integer.parseInt(args[3]);
                int maxSteps = parseMaxSteps(args, 4, 1000);

                tm.init(args[2]);
                List<String> trace = tm.getExecutionTrace();

                System.out.println("First " + k + " configurations:");
                int count = 0;
                while (count < k && (tm.isRunning() || count == 0)) {
                    if (count > 0) tm.step();
                    if (count < trace.size()) {
                        System.out.println(trace.get(count));
                    }
                    count++;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number: " + args[3]);
            }
        }
        return true;
    }
}
