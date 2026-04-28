public abstract class AbstractCommand implements Command {
    protected FileManager fileManager;

    public AbstractCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    protected TuringMachine getMachineById(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            TuringMachine tm = fileManager.getMachine(id);
            if (tm == null) {
                System.out.println("Machine with ID " + id + " not found");
            }
            return tm;
        } catch (NumberFormatException e) {
            System.out.println("Invalid machine ID: " + idStr);
            return null;
        }
    }

    protected boolean validateArgs(String[] args, int minArgs, String usage) {
        if (args.length < minArgs) {
            System.out.println("Usage: " + usage);
            return false;
        }
        return true;
    }

    protected int parseMaxSteps(String[] args, int startIndex, int defaultValue) {
        for (int i = startIndex; i < args.length; i++) {
            if (args[i].startsWith("max=")) {
                try {
                    return Integer.parseInt(args[i].substring(4));
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    protected boolean requireOpenFile() {
        if (fileManager.getCurrentFile() == null) {
            System.out.println("Error: No file is currently open. Use 'open <filename>' first.");
            return false;
        }
        return true;
    }
}
