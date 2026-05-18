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
    private static int nextId = 1;      // Counter за уникални ID-та
    private int id;                      // Уникален идентификатор
    private String name;                 // Име на машината
    private Set<String> states;          // Множество от състояния Q
    private String startState;           // Начално състояние q0
    private Set<String> acceptStates;    // Приемни състояния F
    private Set<String> rejectStates;    // Отказни състояния R

    // Преходи: Map<от_състояние, Map<символ, преход>>
    // Тази структура позволява бързо търсене на преход за O(1)
    private Map<String, Map<Character, Transition>> transitions;

    // Полета за текущото изпълнение
    private String currentTape;          // Съдържание на лентата
    private int headPosition;            // Позиция на главата
    private String currentState;         // Текущо състояние
    private boolean isRunning;           // Дали машината работи
    private int stepsExecuted;           // Брой изпълнени стъпки
    private List<String> executionTrace; // История на изпълнението

    /**
     * Конструктор за създаване на нова машина.
     *
     * Логика:
     * - Автоматично генерираме уникално ID
     * - Инициализираме всички колекции
     * - Извикваме reset() за инициализация на изпълнението
     *
     * @param name Име на машината (четимо за потребителя)
     */
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

    // Getter методи за достъп до информация за машината

    public int getId() { return id; }
    public String getName() { return name; }
    public Set<String> getStates() { return states; }
    public String getStartState() { return startState; }
    public Set<String> getAcceptStates() { return acceptStates; }
    public Set<String> getRejectStates() { return rejectStates; }
    public Map<String, Map<Character, Transition>> getTransitions() {
        return transitions;
    }

    /**
     * Добавя ново състояние към машината.
     *
     * Логика:
     * - Проверяваме дали състоянието вече съществува
     * - Ако не съществува, го добавяме към множеството
     * - Връщаме true/false за успех/неуспех
     *
     * @param state Име на състоянието
     * @return true ако е добавено, false ако вече съществува
     */
    public boolean addState(String state) {
        if (states.contains(state)) return false;
        states.add(state);
        return true;
    }

    /**
     * Задава началното състояние на машината.
     *
     * Логика:
     * - Проверяваме дали състоянието съществува в машината
     * - Ако съществува, го задаваме като начално
     * - Това гарантира, че няма да започнем от несъществуващо състояние
     *
     * @param state Име на състоянието
     */
    public void setStartState(String state) {
        if (states.contains(state)) {
            this.startState = state;
        }
    }

    /**
     * Добавя състояние към приемните състояния.
     *
     * Логика:
     * - Приемните състояния са тези, при които машината спира
     *   и приема входната дума
     * - Проверяваме дали състоянието съществува преди да го добавим
     *
     * @param state Име на състоянието
     * @return true ако е добавено, false ако състоянието не съществува
     */
    public boolean addAcceptState(String state) {
        if (states.contains(state)) {
            acceptStates.add(state);
            return true;
        }
        return false;
    }

    /**
     * Добавя състояние към отказните състояния.
     *
     * Логика:
     * - Отказните състояния са тези, при които машината спира
     *   и отхвърля входната дума
     * - Това е разширение на класическата дефиниция на Тюринг машина
     *
     * @param state Име на състоянието
     * @return true ако е добавено, false ако състоянието не съществува
     */
    public boolean addRejectState(String state) {
        if (states.contains(state)) {
            rejectStates.add(state);
            return true;
        }
        return false;
    }

    /**
     * Добавя нов преход към машината.
     *
     * Логика:
     * - Използваме nested Map структура за бързо търсене
     *   transitions[fromState].get(readSymbol) ни дава прехода
     * - Проверяваме дали вече съществува преход за тази двойка
     *   (състояние, символ) - това гарантира детерминираност
     * - Ако съществува, връщаме false (не добавяме)
     * - Ако не съществува, добавяме прехода и връщаме true
     *
     * @param trans Преходът, който да се добави
     * @return true ако е добавен, false ако вече съществува преход
     */
    public boolean addTransition(Transition trans) {
        String fromState = trans.getFromState();
        char readSymbol = trans.getReadSymbol();

        // computeIfAbsent създава нов HashMap ако не съществува
        transitions.computeIfAbsent(fromState, k -> new HashMap<>());

        // Проверка за детерминираност
        if (transitions.get(fromState).containsKey(readSymbol)) {
            return false; // Вече има преход за тази двойка
        }

        transitions.get(fromState).put(readSymbol, trans);
        return true;
    }

    /**
     * Премахва преход от машината.
     *
     * Логика:
     * - Търсим прехода по състояние и символ
     * - Ако намерим, го премахваме и връщаме
     * - Ако не намерим, връщаме null
     *
     * @param state Състояние
     * @param symbol Символ
     * @return Премахнатия преход или null ако не е намерен
     */
    public Transition removeTransition(String state, char symbol) {
        if (transitions.containsKey(state)) {
            return transitions.get(state).remove(symbol);
        }
        return null;
    }

    /**
     * Проверява дали машината е детерминирана.
     *
     * Логика:
     * - Машина е детерминирана, ако за всяка двойка
     *   (състояние, символ) има най-много един преход
     * - Тъй като addTransition() вече гарантира това,
     *   тук просто връщаме true
     *
     * @return true (винаги, защото addTransition гарантира детерминираност)
     */
    public boolean isDeterministic() {
        for (Map<Character, Transition> stateTrans : transitions.values()) {
            // Вече гарантирано от addTransition()
        }
        return true;
    }

    /**
     * Нулира изпълнението на машината.
     *
     * Логика:
     * - Този метод се използва за подготовка на машината
     *   за ново изпълнение
     * - Нулираме лентата, позицията на главата, състоянието
     * - Изчистваме историята на изпълнението
     * - Важно: НЕ нулираме дефиницията на машината (състояния, преходи)
     */
    public void reset() {
        this.currentTape = "";
        this.headPosition = 0;
        this.currentState = startState;
        this.isRunning = false;
        this.stepsExecuted = 0;
        this.executionTrace.clear();
    }

    /**
     * Инициализира изпълнението с дадена входна дума.
     *
     * Логика:
     * - Записваме входа на лентата
     * - Поставяме главата на позиция 0 (началото)
     * - Задаваме началното състояние
     * - Маркираме машината като работеща (isRunning = true)
     * - Нулираме брояча на стъпките
     * - Добавяме начална конфигурация към историята
     *
     * @param input Входна дума за обработка
     */
    public void init(String input) {
        this.currentTape = input;
        this.headPosition = 0;
        this.currentState = startState;
        this.isRunning = true;
        this.stepsExecuted = 0;
        this.executionTrace.clear();
        addToTrace("INIT: tape=" + currentTape +
                ", state=" + currentState +
                ", head=" + headPosition);
    }

    /**
     * Изпълнява една стъпка от машината.
     *
     * Логика (виж блок схемата в раздел 3.2):
     * 1. Проверяваме дали машината работи
     * 2. Четем текущия символ от лентата
     * 3. Търсим преход за (текущо състояние, символ)
     * 4. Ако няма преход → машината спира (HALT)
     * 5. Ако има преход:
     *    a. Записваме новия символ на лентата
     *    b. Преместваме главата (L/R/S)
     *    c. Преминаваме в новото състояние
     *    d. Увеличаваме брояча на стъпките
     * 6. Проверяваме дали сме в приемно/отказно състояние
     * 7. Връщаме true ако трябва да продължим, false ако спираме
     *
     * @return true ако изпълнението продължава, false ако е спряло
     */
    public boolean step() {
        if (!isRunning) return false;

        // Стъпка 1: Чети текущ символ
        char currentSymbol = getCurrentSymbol();

        // Стъпка 2: Търси преход
        if (!transitions.containsKey(currentState) ||
                !transitions.get(currentState).containsKey(currentSymbol)) {
            // Няма преход → HALT
            isRunning = false;
            addToTrace("HALT: No transition for (" + currentState +
                    ", " + currentSymbol + ")");
            return false;
        }

        // Стъпка 3: Изпълни прехода
        Transition trans = transitions.get(currentState).get(currentSymbol);

        writeSymbol(trans.getWriteSymbol());  // Запиши символ
        moveHead(trans.getDirection());        // Премести главата
        currentState = trans.getToState();     // Промени състоянието
        stepsExecuted++;                       // Увеличи брояча

        // Добави към историята
        addToTrace("STEP " + stepsExecuted + ": tape=" + currentTape +
                ", state=" + currentState +
                ", head=" + headPosition);

        // Стъпка 4: Провери за финални състояния
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

        // Продължи изпълнението
        return true;
    }

    /**
     * Изпълнява машината до край или до достигане на лимит.
     *
     * Логика:
     * - Изпълняваме стъпки в цикъл докато:
     *   1. Машината работи (isRunning == true) И
     *   2. Не сме достигнали лимита на стъпки
     * - Това предпазва от безкрайни цикли
     * - Връщаме true ако сме достигнали лимита, false ако машината спря сама
     *
     * @param maxSteps Максимален брой стъпки за изпълнение
     * @return true ако е достигнат лимита, false ако машината спря
     */
    public boolean run(int maxSteps) {
        int steps = 0;
        while (isRunning && steps < maxSteps) {
            if (!step()) return false;  // Машината спря
            steps++;
        }
        return isRunning;  // true ако сме достигнали лимита
    }

    /**
     * Проверява дали машината приема дадена дума.
     *
     * Логика:
     * - Инициализираме машината с думата
     * - Изпълняваме я до край
     * - Проверяваме дали сме завършили в приемно състояние
     * - Връщаме true ако приема, false ако отхвърля
     *
     * @param word Входна дума
     * @param maxSteps Максимален брой стъпки
     * @return true ако машината приема думата
     */
    public boolean accepts(String word, int maxSteps) {
        init(word);
        return !run(maxSteps) && acceptStates.contains(currentState);
    }

    // ==================== ПОМОЩНИ МЕТОДИ ====================

    /**
     * Чете символа на текущата позиция на главата.
     *
     * Логика:
     * - Ако главата е извън лентата (отрицателна или твърде голяма позиция)
     *   връщаме '_' (blank символ)
     * - Иначе връщаме символа на текущата позиция
     *
     * @return Символът на текущата позиция
     */
    private char getCurrentSymbol() {
        if (headPosition < 0) return '_';
        if (headPosition >= currentTape.length()) return '_';
        return currentTape.charAt(headPosition);
    }

    /**
     * Записва символ на текущата позиция на главата.
     *
     * Логика:
     * - Трябва да обработим три случая:
     *   1. Главата е вляво от лентата (headPosition < 0)
     *      → Разширяваме лентата наляво с '_' и пишем символа
     *   2. Главата е вдясно от лентата (headPosition >= length)
     *      → Разширяваме лентата надясно с '_' и пишем символа
     *   3. Главата е върху лентата
     *      → Просто заменяме символа на текущата позиция
     *
     * @param symbol Символът за запис
     */
    private void writeSymbol(char symbol) {
        if (headPosition < 0) {
            // Разширение наляво
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < -headPosition; i++) sb.append('_');
            sb.append(symbol);
            sb.append(currentTape);
            currentTape = sb.toString();
            headPosition = 0;  // Коригираме позицията
        } else if (headPosition >= currentTape.length()) {
            // Разширение надясно
            StringBuilder sb = new StringBuilder(currentTape);
            for (int i = currentTape.length(); i < headPosition; i++) {
                sb.append('_');
            }
            sb.append(symbol);
            currentTape = sb.toString();
        } else {
            // Замяна на съществуващ символ
            StringBuilder sb = new StringBuilder(currentTape);
            sb.setCharAt(headPosition, symbol);
            currentTape = sb.toString();
        }
    }

    /**
     * Премества главата в посока L, R или S.
     *
     * Логика:
     * - L (Left):  headPosition--
     * - R (Right): headPosition++
     * - S (Stay):  няма промяна
     *
     * @param direction Посока на движение
     */
    private void moveHead(Transition.MoveDirection direction) {
        switch (direction) {
            case L: headPosition--; break;
            case R: headPosition++; break;
            case S: break;  // Остава на място
        }
    }

    /**
     * Добавя съобщение към историята на изпълнението.
     *
     * Логика:
     * - Историята се използва за trace и debug
     * - Всяка конфигурация се записва като низ
     *
     * @param message Съобщение за добавяне
     */
    private void addToTrace(String message) {
        executionTrace.add(message);
    }

    // ==================== GETTER МЕТОДИ ЗА ИЗПЪЛНЕНИЕ ====================

    public String getCurrentTape() { return currentTape; }
    public int getHeadPosition() { return headPosition; }
    public String getCurrentState() { return currentState; }
    public boolean isRunning() { return isRunning; }
    public int getStepsExecuted() { return stepsExecuted; }
    public List<String> getExecutionTrace() { return executionTrace; }

    /**
     * Връща подниз от лентата между две позиции.
     *
     * Логика:
     * - Използва се от командата tape за показване на част от лентата
     * - Коригираме границите ако са извън валидния диапазон
     *
     * @param from Начална позиция (включително)
     * @param to Крайна позиция (изключително)
     * @return Подниз от лентата
     */
    public String getTapeSubstring(int from, int to) {
        if (from < 0) from = 0;
        if (to > currentTape.length()) to = currentTape.length();
        return currentTape.substring(from, to);
    }

    /**
     * Визуализация на цялата машина.
     *
     * Логика:
     * - Показва всички компоненти на машината
     * - Използва се от командата print
     *
     * @return Текстово представяне на машината
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Machine ID: ").append(id).append(", Name: ").append(name).append("\n");
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
