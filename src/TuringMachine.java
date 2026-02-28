import java.util.*;
public class TuringMachine {
    private static int nextId = 1;
    private int id;
    private String name;
    private Set<String> states;
    private Set<Character> inputAlphabet;
    private Set<Character> tapeAlphabet;
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
        this.inputAlphabet = new HashSet<>();
        this.tapeAlphabet = new HashSet<>();
        this.tapeAlphabet.add('_');
        this.acceptStates = new HashSet<>();
        this.rejectStates = new HashSet<>();
        this.transitions = new HashMap<>();
        this.executionTrace = new ArrayList<>();
        //
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Set<String> getStates() { return states; }
    public String getStartState() { return startState; }
    public Set<String> getAcceptStates() { return acceptStates; }
    public Set<String> getRejectStates() { return rejectStates; }
    public Map<String, Map<Character, Transition>> getTransitions() { return transitions; }
}
