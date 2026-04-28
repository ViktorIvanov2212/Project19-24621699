import java.io.*;
import java.util.*;

public class FileManager {
    private File currentFile;
    private Map<Integer, TuringMachine> loadedMachines;

    public FileManager() {
        this.loadedMachines = new HashMap<>();
    }

    /**
     * Отваря файл и зарежда машините от него
     */
    public boolean open(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
                currentFile = file;
                System.out.println("Successfully created and opened " + filename);
                return true;
            }

            currentFile = file;

            // Зареждаме машините от файла само ако не е празен
            if (file.length() > 0) {
                loadMachinesFromFile(file);
            }

            System.out.println("Successfully opened " + filename);
            return true;
        } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Затваря текущия файл
     */
    public boolean close() {
        currentFile = null;
        loadedMachines.clear();
        System.out.println("File closed successfully");
        return true;
    }

    /**
     * Записва всички машини в текущия файл
     */
    public boolean save() {
        if (currentFile == null) {
            System.err.println("No file is currently open");
            return false;
        }

        return saveToFile(currentFile);
    }

    /**
     * Записва всички машини в нов файл
     */
    public boolean saveAs(String filename) {
        try {
            File file = new File(filename);
            currentFile = file;
            return saveToFile(file);
        } catch (Exception e) {
            System.err.println("Error saving file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Запазва машините във файл в прост текстов формат
     */
    private boolean saveToFile(File file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {

            int machineCount = 0;
            for (TuringMachine tm : loadedMachines.values()) {
                if (machineCount > 0) {
                    writer.println();
                    writer.println("---");
                    writer.println();
                }

                // Заглавие на машината
                writer.println("# Machine: " + tm.getName());
                writer.println("# ID: " + tm.getId());
                writer.println();

                // Състояния
                for (String state : tm.getStates()) {
                    writer.println("STATE: " + state);
                }
                writer.println();

                // Начално състояние
                if (tm.getStartState() != null) {
                    writer.println("START: " + tm.getStartState());
                }

                // Приемни състояния
                for (String state : tm.getAcceptStates()) {
                    writer.println("ACCEPT: " + state);
                }

                // Отказни състояния
                for (String state : tm.getRejectStates()) {
                    writer.println("REJECT: " + state);
                }
                writer.println();

                // Преходи
                for (Map.Entry<String, Map<Character, Transition>> entry :
                        tm.getTransitions().entrySet()) {
                    for (Transition trans : entry.getValue().values()) {
                        writer.println("TRANSITION: " +
                                trans.getFromState() + "," +
                                trans.getReadSymbol() + "," +
                                trans.getToState() + "," +
                                trans.getWriteSymbol() + "," +
                                trans.getDirection().name());
                    }
                }

                machineCount++;
            }

            System.out.println("Successfully saved " + file.getName() +
                    " (" + machineCount + " machine(s))");
            return true;

        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Зарежда машините от текстов файл
     */
    private void loadMachinesFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            TuringMachine currentMachine = null;
            String machineName = "Unknown";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Пропускаме празни редове и коментари
                if (line.isEmpty() || line.startsWith("#")) {
                    // Извличаме името от коментара
                    if (line.startsWith("# Machine:")) {
                        machineName = line.substring("# Machine:".length()).trim();
                    }
                    continue;
                }

                // Разделител между машини
                if (line.equals("---")) {
                    currentMachine = null;
                    continue;
                }

                // Парсване на командите
                if (line.startsWith("STATE:")) {
                    String state = line.substring("STATE:".length()).trim();
                    if (currentMachine == null) {
                        // Създаваме нова машина ако още нямаме
                        currentMachine = new TuringMachine(machineName);
                        loadedMachines.put(currentMachine.getId(), currentMachine);
                    }
                    currentMachine.addState(state);

                } else if (line.startsWith("START:")) {
                    String startState = line.substring("START:".length()).trim();
                    if (currentMachine != null) {
                        currentMachine.setStartState(startState);
                    }

                } else if (line.startsWith("ACCEPT:")) {
                    String acceptState = line.substring("ACCEPT:".length()).trim();
                    if (currentMachine != null) {
                        currentMachine.addAcceptState(acceptState);
                    }

                } else if (line.startsWith("REJECT:")) {
                    String rejectState = line.substring("REJECT:".length()).trim();
                    if (currentMachine != null) {
                        currentMachine.addRejectState(rejectState);
                    }

                } else if (line.startsWith("TRANSITION:")) {
                    String transData = line.substring("TRANSITION:".length()).trim();
                    if (currentMachine != null) {
                        parseAndAddTransition(currentMachine, transData);
                    }
                }
            }

            System.out.println("Loaded " + loadedMachines.size() + " machine(s) from file");

        } catch (IOException e) {
            System.err.println("Error loading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Парсва и добавя преход към машината
     * Формат: fromState,readSymbol,toState,writeSymbol,direction
     */
    private void parseAndAddTransition(TuringMachine tm, String data) {
        try {
            String[] parts = data.split(",");
            if (parts.length != 5) {
                System.err.println("Invalid transition format: " + data);
                return;
            }

            String fromState = parts[0].trim();
            char read = parts[1].trim().charAt(0);
            String toState = parts[2].trim();
            char write = parts[3].trim().charAt(0);
            Transition.MoveDirection move = Transition.MoveDirection.valueOf(parts[4].trim().toUpperCase());

            Transition trans = new Transition(fromState, read, toState, write, move);
            tm.addTransition(trans);

        } catch (Exception e) {
            System.err.println("Error parsing transition '" + data + "': " + e.getMessage());
        }
    }

    public void addMachine(TuringMachine machine) {
        loadedMachines.put(machine.getId(), machine);
    }

    public TuringMachine getMachine(int id) {
        return loadedMachines.get(id);
    }

    public Map<Integer, TuringMachine> getLoadedMachines() {
        return loadedMachines;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void markChanged() {
        // Not needed for this implementation
    }
}
