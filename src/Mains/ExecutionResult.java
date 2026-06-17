package Mains;

/**
 * DTO за резултат от изпълнение. Няма логика, само данни.
 */
public class ExecutionResult {
    public enum Status { RUNNING, ACCEPTED, REJECTED, HALTED_NO_TRANSITION, LIMIT_REACHED }

    private final Status status;
    private final int stepsExecuted;
    private final State finalState;
    private final String tapeSnapshot;
    private final int headPosition;

    public ExecutionResult(Status status, int stepsExecuted, State finalState, String tapeSnapshot, int headPosition) {
        this.status = status;
        this.stepsExecuted = stepsExecuted;
        this.finalState = finalState;
        this.tapeSnapshot = tapeSnapshot;
        this.headPosition = headPosition;
    }

    public Status status() { return status; }
    public int stepsExecuted() { return stepsExecuted; }
    public State finalState() { return finalState; }
    public String tapeSnapshot() { return tapeSnapshot; }
    public int headPosition() { return headPosition; }
}
