public interface Command {
    String getName();
    String getDescription();
    boolean execute(String[] args);
}
