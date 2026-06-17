package CommandInterface;

// Command.java
public interface Command {
    String getName();
    String getDescription();
    // Връща резултат за принтиране или хвърля грешка
    String execute(String[] args) throws CommandException;
}
