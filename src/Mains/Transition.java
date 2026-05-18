package Mains;

import java.util.Objects;

/**
 * Класът Transition представлява един преход в машината на Тюринг.
 *
 * Всеки преход се дефинира от пет елемента:
 * - Начално състояние (fromState)
 * - Символ за четене (readSymbol)
 * - Крайно състояние (toState)
 * - Символ за запис (writeSymbol)
 * - Посока на движение (direction: L, R, S)
 *
 * Класът имплементира equals() и hashCode() за да може да се проверява
 * дали вече съществува преход за дадена двойка (състояние, символ).
 * Това е важно за проверката на детерминираност на машината.
 */
public class Transition {
    private String fromState;           // Състояние, от което тръгваме
    private char readSymbol;            // Символ, който четем
    private String toState;             // Състояние, в което отиваме
    private char writeSymbol;           // Символ, който пишем
    private MoveDirection direction;    // Посока на движение

    /**
     * Enum за посоките на движение на главата.
     * L (Left)  - движение наляво (headPosition--)
     * R (Right) - движение надясно (headPosition++)
     * S (Stay)  - остава на място (headPosition не се променя)
     */
    public enum MoveDirection {
        L, R, S
    }

    /**
     * Конструктор за създаване на нов преход.
     *
     * @param fromState    Начално състояние
     * @param readSymbol   Символ за четене от лентата
     * @param toState      Крайно състояние
     * @param writeSymbol  Символ за запис на лентата
     * @param direction    Посока на движение (L/R/S)
     */
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

    /**
     * Метод за визуализация на прехода във формат δ(q, a) = (q', b, D).
     * Това е стандартният математически запис за функция на преходите.
     *
     * Пример: δ(q0, 0) = (q1, 1, R)
     * Означава: "От състояние q0, при прочитане на 0,
     *            отиваме в q1, записваме 1, движим се надясно"
     */
    @Override
    public String toString() {
        return String.format("δ(%s, %c) = (%s, %c, %s)",
                fromState, readSymbol, toState, writeSymbol, direction);
    }

    /**
     * Override на equals() за проверка дали два прехода са еднакви.
     *
     * Логика: Два прехода се считат за еднакви, ако са от едно и
     * също състояние и четат един и същ символ (независимо къде водят).
     *
     * Това ни позволява да открием конфликти при добавяне на преходи -
     * не може да имаме два различни прехода за една и съща двойка
     * (състояние, символ) в детерминирана машина.
     *
     * @param o Обект за сравнение
     * @return true ако преходите са за една и съща двойка (q, a)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transition)) return false;
        Transition that = (Transition) o;
        return readSymbol == that.readSymbol &&
                fromState.equals(that.fromState);
    }

    /**
     * hashCode() се имплементира винаги заедно с equals().
     * Използваме същите полета, които използваме в equals().
     *
     * @return Хеш код базиран на fromState и readSymbol
     */
    @Override
    public int hashCode() {
        return Objects.hash(fromState, readSymbol);
    }
}
