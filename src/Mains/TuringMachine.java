package Mains;

import java.util.*;

/**
 * Класът TuringMachine представлява основния модел на машина на Тюринг.
 *
 * Този клас съдържа всички компоненти на една машина:
 * 1. Състояния (states) - крайно множество Q
 * 2. Преходи (transitions) - функция δ
 * 3. Начално, приемни и отказни състояния
 * 4. Текущо изпълнение (лента, глава, състояние)
 *
 * Използваме статичен counter (nextId) за автоматично генериране
 * на уникални идентификатори за всяка нова машина.
 *
 * Разделяме дефиницията на машината (състояния, преходи) от
 * изпълнението (лента, глава) - това позволява една машина
 * да се изпълнява многократно с различни входове.
 */

public class TuringMachine {
    private static int nextId = 1;
    private final int id;
    private final String name;
    private final Map<String, State> states = new HashMap<>();
    private final Map<String, Map<Character, Transition>> transitions = new HashMap<>();
    private String startStateName = null;

    private State currentState;
    private final Tape tape = new Tape();
    private boolean running = false;
    private int steps = 0;

    public TuringMachine(String name) {
        this.id = nextId++;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Map<String, State> getStates() { return Collections.unmodifiableMap(states); }
    public Map<String, Map<Character, Transition>> getTransitions() { return Collections.unmodifiableMap(transitions); }
    public State getCurrentState() { return currentState; }
    public Tape getTape() { return tape; }
    public int getSteps() { return steps; }
    public boolean isRunning() { return running; }

    public void addState(State state) {
        states.put(state.getName(), state);
        if (state.getType() == State.Type.START) startStateName = state.getName();
    }

    public boolean addTransition(Transition trans) {
        String from = trans.getFromState();
        char read = trans.getReadSymbol();
        transitions.computeIfAbsent(from, k -> new HashMap<>());
        if (transitions.get(from).containsKey(read)) return false;
        transitions.get(from).put(read, trans);
        return true;
    }

    public void init(String input) {
        tape.reset();
        steps = 0;
        running = true;

        // Записва входа символ по символ
        for (int i = 0; i < input.length(); i++) {
            tape.write(input.charAt(i));
            tape.move(Transition.MoveDirection.R);
        }

        // Връща главата на позиция 0
        tape.setHeadPosition(0); // ✅ Правилно!

        currentState = states.get(startStateName);
    }

    /** Изпълнява една стъпка */
    public ExecutionResult.Status step() {
        if (!running) return ExecutionResult.Status.HALTED_NO_TRANSITION;
        char symbol = tape.read();
        String cur = currentState.getName();
        if (!transitions.containsKey(cur) || !transitions.get(cur).containsKey(symbol)) {
            running = false;
            return ExecutionResult.Status.HALTED_NO_TRANSITION;
        }
        Transition t = transitions.get(cur).get(symbol);
        tape.write(t.getWriteSymbol());
        tape.move(t.getDirection());
        currentState = states.get(t.getToState());
        steps++;
        if (currentState.getType() == State.Type.ACCEPT) { running = false; return ExecutionResult.Status.ACCEPTED; }
        if (currentState.getType() == State.Type.REJECT) { running = false; return ExecutionResult.Status.REJECTED; }
        return ExecutionResult.Status.RUNNING;
    }

    /** Автоматично изпълнение до край или лимит */
    public ExecutionResult run(int maxSteps) {
        ExecutionResult.Status status = ExecutionResult.Status.RUNNING;
        while (running && steps < maxSteps) status = step();
        if (steps >= maxSteps && running) status = ExecutionResult.Status.LIMIT_REACHED;
        int viewFrom = Math.max(0, tape.getHeadPosition() - 5);
        int viewTo = tape.getHeadPosition() + 10;
        return new ExecutionResult(status, steps, currentState, tape.getWindow(viewFrom, viewTo), tape.getHeadPosition());
    }
    public void setStartState(String stateName) {
        this.startStateName = stateName;
        if (states.containsKey(stateName)) currentState = states.get(stateName);
    }

    public void reset() {
        tape.reset();
        steps = 0;
        running = false;
        currentState = states.get(startStateName);
    }

    public Transition removeTransition(String state, char symbol) {
        Map<Character, Transition> map = transitions.get(state);
        return (map != null) ? map.remove(symbol) : null;
    }
}
