public class AddTransCommand extends AbstractCommand {
    public AddTransCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "addtrans"; }

    @Override
    public String getDescription() { return "Add transition"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 7, "addtrans <id> <fromState> <read> <toState> <write> <move>")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            try {
                String fromState = args[2];
                char read = args[3].charAt(0);
                String toState = args[4];
                char write = args[5].charAt(0);
                Transition.MoveDirection move = Transition.MoveDirection.valueOf(args[6].toUpperCase());

                Transition trans = new Transition(fromState, read, toState, write, move);
                if (tm.addTransition(trans)) {
                    System.out.println("Transition added: " + trans);
                    fileManager.markChanged();
                } else {
                    System.out.println("Failed to add transition (might violate determinism)");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move direction. Use L, R, or S");
            }
        }
        return true;
    }
}
