import java.util.Objects;

public class Transition {
    private String fromState;
    private char readSymbol;
    private String toState;
    private char writeSymbol;
    private MoveDirection direction;

    public enum MoveDirection {
        L, R, S  // Left, Right, Stay
    }

    public Transition(String fromState, char readSymbol, String toState,
                      char writeSymbol, MoveDirection direction) {
        this.fromState = fromState;
        this.readSymbol = readSymbol;
        this.toState = toState;
        this.writeSymbol = writeSymbol;
        this.direction = direction;
    }

    public String getFromState() { return fromState; }
    public char getReadSymbol() { return readSymbol; }
    public String getToState() { return toState; }
    public char getWriteSymbol() { return writeSymbol; }
    public MoveDirection getDirection() { return direction; }

    @Override
    public String toString() {
        return String.format("δ(%s, %c) = (%s, %c, %s)",
                fromState, readSymbol, toState, writeSymbol, direction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transition)) return false;
        Transition that = (Transition) o;
        return readSymbol == that.readSymbol &&
                fromState.equals(that.fromState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromState, readSymbol);
    }
}
