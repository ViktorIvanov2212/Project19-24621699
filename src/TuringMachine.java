import java.util.*;

public class TuringMachine {
    private String name;
    private static int nextId = 1;
    private int id;
    private Set<String> states;
    private String startState;
    private Set<String> acceptStates;
    private Set<String> rejectStates;
    private Map<String, Map<Character, Transition>> transitions;

    private String currentTape;
    private int headPosition;
    private String currentState;
    private boolean isRunning;
    private int stepsExecuted;
    private List<String> executionTrace;

    public TuringMachine(String name) {
        this.id = nextId++;
        this.name = name;
        this.states = new HashSet<>();
        this.acceptStates = new HashSet<>();
        this.rejectStates = new HashSet<>();
        this.transitions = new HashMap<>();
        this.executionTrace = new ArrayList<>();
        reset();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Set<String> getStates() { return states; }
    public String getStartState() { return startState; }
    public Set<String> getAcceptStates() { return acceptStates; }
    public Set<String> getRejectStates() { return rejectStates; }
    public Map<String, Map<Character, Transition>> getTransitions() { return transitions; }

    public boolean addState(String state) {
        if (states.contains(state)) return false;
        states.add(state);
        return true;
    }

    public void setStartState(String state) {
        if (states.contains(state)) {
            this.startState = state;
        }
    }

    public boolean addAcceptState(String state) {
        if (states.contains(state)) {
            acceptStates.add(state);
            return true;
        }
        return false;
    }

    public boolean addRejectState(String state) {
        if (states.contains(state)) {
            rejectStates.add(state);
            return true;
        }
        return false;
    }

    public boolean addTransition(Transition trans) {
        String fromState = trans.getFromState();
        char readSymbol = trans.getReadSymbol();

        transitions.computeIfAbsent(fromState, k -> new HashMap<>());

        if (transitions.get(fromState).containsKey(readSymbol)) {
            return false;
        }

        transitions.get(fromState).put(readSymbol, trans);
        return true;
    }

    public Transition removeTransition(String state, char symbol) {
        if (transitions.containsKey(state)) {
            return transitions.get(state).remove(symbol);
        }
        return null;
    }

    public boolean isDeterministic() {
        for (Map<Character, Transition> stateTrans : transitions.values()) {
            if (stateTrans.size() > 1) {
                return false;
            }
        }
        return true;
    }

    public void reset() {
        this.currentTape = "";
        this.headPosition = 0;
        this.currentState = startState;
        this.isRunning = false;
        this.stepsExecuted = 0;
        this.executionTrace.clear();
    }

    public void init(String input) {
        this.currentTape = input;
        this.headPosition = 0;
        this.currentState = startState;
        this.isRunning = true;
        this.stepsExecuted = 0;
        this.executionTrace.clear();
        addToTrace("INIT: tape=" + currentTape + ", state=" + currentState + ", head=" + headPosition);
    }

    public boolean step() {
        if (!isRunning) return false;

        char currentSymbol = getCurrentSymbol();

        if (!transitions.containsKey(currentState) ||
                !transitions.get(currentState).containsKey(currentSymbol)) {
            isRunning = false;
            addToTrace("HALT: No transition for (" + currentState + ", " + currentSymbol + ")");
            return false;
        }

        Transition trans = transitions.get(currentState).get(currentSymbol);

        writeSymbol(trans.getWriteSymbol());
        moveHead(trans.getDirection());
        currentState = trans.getToState();
        stepsExecuted++;

        addToTrace("STEP " + stepsExecuted + ": tape=" + currentTape +
                ", state=" + currentState + ", head=" + headPosition);

        if (acceptStates.contains(currentState)) {
            isRunning = false;
            addToTrace("ACCEPT");
            return false;
        }

        if (rejectStates.contains(currentState)) {
            isRunning = false;
            addToTrace("REJECT");
            return false;
        }

        return true;
    }

    public boolean run(int maxSteps) {
        int steps = 0;
        while (isRunning && steps < maxSteps) {
            if (!step()) return false;
            steps++;
        }
        return isRunning;
    }

    public boolean accepts(String word, int maxSteps) {
        init(word);
        return !run(maxSteps) && acceptStates.contains(currentState);
    }

    private char getCurrentSymbol() {
        if (headPosition < 0) return '_';
        if (headPosition >= currentTape.length()) return '_';
        return currentTape.charAt(headPosition);
    }

    private void writeSymbol(char symbol) {
        if (headPosition < 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < -headPosition; i++) sb.append('_');
            sb.append(symbol);
            sb.append(currentTape);
            currentTape = sb.toString();
            headPosition = 0;
        } else if (headPosition >= currentTape.length()) {
            StringBuilder sb = new StringBuilder(currentTape);
            for (int i = currentTape.length(); i < headPosition; i++) sb.append('_');
            sb.append(symbol);
            currentTape = sb.toString();
        } else {
            StringBuilder sb = new StringBuilder(currentTape);
            sb.setCharAt(headPosition, symbol);
            currentTape = sb.toString();
        }
    }

    private void moveHead(Transition.MoveDirection direction) {
        switch (direction) {
            case L: headPosition--; break;
            case R: headPosition++; break;
            case S: break;
        }
    }

    private void addToTrace(String message) {
        executionTrace.add(message);
    }

    public String getCurrentTape() { return currentTape; }
    public int getHeadPosition() { return headPosition; }
    public String getCurrentState() { return currentState; }
    public boolean isRunning() { return isRunning; }
    public int getStepsExecuted() { return stepsExecuted; }
    public List<String> getExecutionTrace() { return executionTrace; }

    public String getTapeSubstring(int from, int to) {
        if (from < 0) from = 0;
        if (to > currentTape.length()) to = currentTape.length();
        return currentTape.substring(from, to);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Machine ID: ").append(id).append("\n");
        sb.append("States: ").append(states).append("\n");
        sb.append("Start: ").append(startState).append("\n");
        sb.append("Accept: ").append(acceptStates).append("\n");
        sb.append("Reject: ").append(rejectStates).append("\n");
        sb.append("Transitions:\n");
        for (Map<Character, Transition> stateTrans : transitions.values()) {
            for (Transition t : stateTrans.values()) {
                sb.append("  ").append(t).append("\n");
            }
        }
        return sb.toString();
    }
}
