package CommandInterface;

import Mains.FileManager;
import Mains.TuringMachine;

/**
 * Базов клас за командите. Съдържа обща логика за валидация и достъп до мениджъра.
 */
public abstract class AbstractCommand implements Command {
    protected FileManager fileManager;

    public AbstractCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /** Взима машина по ID или хвърля грешка */
    protected TuringMachine getMachineById(String idStr) throws CommandException {
        try {
            int id = Integer.parseInt(idStr);
            TuringMachine tm = fileManager.getMachine(id);
            if (tm == null) throw new CommandException("Machine with ID " + id + " not found");
            return tm;
        } catch (NumberFormatException e) {
            throw new CommandException("Invalid machine ID format: " + idStr);
        }
    }

    /** Валидира минимален брой аргументи */
    protected void validateArgs(String[] args, int minArgs, String usage) throws CommandException {
        if (args.length < minArgs) throw new CommandException("Usage: " + usage);
    }

    /** Парсва max=<n> от аргументите */
    protected int parseMaxSteps(String[] args, int startIndex, int defaultValue) {
        for (int i = startIndex; i < args.length; i++) {
            if (args[i].startsWith("max=")) {
                try { return Integer.parseInt(args[i].substring(4)); } catch (NumberFormatException e) { return defaultValue; }
            }
        }
        return defaultValue;
    }

    /** Проверява дали има отворен файл */
    protected void requireOpenFile() throws CommandException {
        if (fileManager.getCurrentFile() == null) {
            throw new CommandException("No file is currently open. Use 'open <filename>' first.");
        }
    }
}
