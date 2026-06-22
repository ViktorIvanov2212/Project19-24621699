package Mains;

import java.util.HashMap;
import java.util.Map;

/**
 * Симулира безкрайна лента чрез HashMap. Ефективно за разрежени данни.
 */
public class Tape {
    private final Map<Integer, Character> cells = new HashMap<>();
    private int headPosition = 0;
    private static final char BLANK = '_';

    public char read() {
        return cells.getOrDefault(headPosition, BLANK);
    }
    /** Задава позицията на главата директно */
    public void setHeadPosition(int position) {
        this.headPosition = position;
    }
    public void write(char symbol) {
        if (symbol == BLANK) cells.remove(headPosition);
        else cells.put(headPosition, symbol);
    }

    public void move(Transition.MoveDirection dir) {
        switch (dir) {
            case L -> headPosition--;
            case R -> headPosition++;
            case S -> {}
        }
    }

    public int getHeadPosition() { return headPosition; }

    /** Връща прозорец от лентата за визуализация */
    public String getWindow(int from, int to) {
        StringBuilder sb = new StringBuilder();
        for (int i = from; i < to; i++) sb.append(cells.getOrDefault(i, BLANK));
        return sb.toString();
    }

    public void reset() {
        cells.clear();
        headPosition = 0;
    }
}
