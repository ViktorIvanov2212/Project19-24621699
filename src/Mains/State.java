package Mains;
/**
 * Обект за състояние на машината. Пази име и тип.
 */
public class State {
    public enum Type { NORMAL, START, ACCEPT, REJECT }
    private final String name;
    private Type type;

    public State(String name) {
        this.name = name;
        this.type = Type.NORMAL;
    }

    public String getName() { return name; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    @Override public String toString() { return name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        return name.equals(((State) o).name);
    }
    @Override public int hashCode() { return name.hashCode(); }
}
