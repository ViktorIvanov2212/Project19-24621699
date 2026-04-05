public class TapeCommand extends AbstractCommand {
    public TapeCommand(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public String getName() { return "tape"; }

    @Override
    public String getDescription() { return "Show tape content"; }

    @Override
    public boolean execute(String[] args) {
        if (!validateArgs(args, 2, "tape <id> [from=<a>] [to=<b>]")) return true;
        TuringMachine tm = getMachineById(args[1]);
        if (tm != null) {
            int from = 0, to = tm.getCurrentTape().length();
            for (int i = 2; i < args.length; i++) {
                if (args[i].startsWith("from=")) {
                    from = Integer.parseInt(args[i].substring(5));
                } else if (args[i].startsWith("to=")) {
                    to = Integer.parseInt(args[i].substring(3));
                }
            }
            String tape = tm.getTapeSubstring(from, to);
            System.out.println("Tape: " + tape);
            System.out.println("Head: " + tm.getHeadPosition());
        }
        return true;
    }
}
