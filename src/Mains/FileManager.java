package Mains;

import java.io.*;
import java.util.*;

public class FileManager {
    private File currentFile;
    private final Map<Integer, TuringMachine> loadedMachines = new HashMap<>();

    /**
     * Отваря файл и зарежда машините от него.
     * Ако файлът не съществува, се създава нов.
     *
     * параметър filename Име на файла
     * return true при успех, false при грешка
     */
    public boolean open(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) file.createNewFile();
            currentFile = file;
            loadedMachines.clear();
            if (file.length() > 0) loadFromFile(file);
            return true;
        } catch (IOException e) { return false; }
    }

    /**
     * Затваря текущия файл
     */
    public boolean close() {
        currentFile = null;
        loadedMachines.clear();
        return true;
    }

    /**
     * Записва всички машини в текущия файл
     */
    public boolean save() {
        return currentFile != null && saveToFile(currentFile);
    }

    /**
     * Записва всички машини в нов файл
     */
    public boolean saveAs(String filename) {
        try {
            currentFile = new File(filename);
            return saveToFile(currentFile);
        } catch (Exception e) { return false; }
    }

    /**
     * Запазва всички машини в текущия файл в текстов формат.
     *
     * параметър: file Файл за запис
     * return true при успех, false при грешка
     */
    private boolean saveToFile(File file) {
        try (PrintWriter w = new PrintWriter(new FileWriter(file))) {
            int idx = 0;
            for (TuringMachine tm : loadedMachines.values()) {
                if (idx++ > 0) w.println("\n---");
                w.println("# Machine: " + tm.getName());
                w.println("# ID: " + tm.getId());
                for (State s : tm.getStates().values()) w.println("STATE: " + s.getName());
                w.println("START: " + tm.getStates().values().stream().filter(s -> s.getType() == State.Type.START).findFirst().map(State::getName).orElse(""));
                tm.getStates().values().stream().filter(s -> s.getType() == State.Type.ACCEPT).forEach(s -> w.println("ACCEPT: " + s.getName()));
                tm.getStates().values().stream().filter(s -> s.getType() == State.Type.REJECT).forEach(s -> w.println("REJECT: " + s.getName()));
                tm.getTransitions().values().forEach(map -> map.values().forEach(t ->
                        w.println("TRANSITION: " + t.getFromState() + "," + t.getReadSymbol() + "," + t.getToState() + "," + t.getWriteSymbol() + "," + t.getDirection())));
            }
            return true;
        } catch (IOException e) { return false; }
    }

    /**
     * Зарежда машините от текстов файл.
     * Парсва команди като STATE, START, ACCEPT, TRANSITION.
     *
     * параметър file: Файл за четене
     */
    private void loadFromFile(File file) {
        try (BufferedReader r = new BufferedReader(new FileReader(file))) {
            String line; TuringMachine cur = null; String name = "Unknown";
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    if (line.startsWith("# Machine:")) name = line.substring(10).trim();
                    continue;
                }
                if (line.equals("---")) { cur = null; continue; }
                if (cur == null) { cur = new TuringMachine(name); loadedMachines.put(cur.getId(), cur); }

                if (line.startsWith("STATE:")) cur.addState(new State(line.substring(6).trim()));
                else if (line.startsWith("START:")) { String s = line.substring(6).trim(); State st = cur.getStates().get(s); if (st != null) st.setType(State.Type.START); }
                else if (line.startsWith("ACCEPT:")) { String s = line.substring(7).trim(); State st = cur.getStates().get(s); if (st != null) st.setType(State.Type.ACCEPT); }
                else if (line.startsWith("REJECT:")) { String s = line.substring(7).trim(); State st = cur.getStates().get(s); if (st != null) st.setType(State.Type.REJECT); }
                else if (line.startsWith("TRANSITION:")) parseTransition(cur, line.substring(11).trim());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Парсва и добавя преход към машината.
     * Очакван формат: "fromState,readSymbol,toState,writeSymbol,direction"
     *
     * параметър tm Машина, към която да се добави преходът
     * параметър data Низ с данни за прехода
     */
    private void parseTransition(TuringMachine tm, String data) {
        try {
            String[] p = data.split(",");
            if (p.length == 5) tm.addTransition(new Transition(p[0].trim(), p[1].trim().charAt(0), p[2].trim(), p[3].trim().charAt(0), Transition.MoveDirection.valueOf(p[4].trim().toUpperCase())));
        } catch (Exception ignored) {}
    }
    public void loadMachineFromFile(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists()) throw new IOException("File not found");
        // Използваме същата логика за парсване, но добавяме към loadedMachines
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line; TuringMachine cur = null; String name = "LoadedMachine";
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    if (line.startsWith("# Machine:")) name = line.substring(10).trim();
                    continue;
                }
                if (line.equals("---")) break; // Спираме при първата машина
                if (cur == null) { cur = new TuringMachine(name); loadedMachines.put(cur.getId(), cur); }

                if (line.startsWith("STATE:")) cur.addState(new State(line.substring(6).trim()));
                else if (line.startsWith("START:")) { String s = line.substring(6).trim(); State st = cur.getStates().get(s); if (st != null) st.setType(State.Type.START); }
                else if (line.startsWith("ACCEPT:")) { String s = line.substring(7).trim(); State st = cur.getStates().get(s); if (st != null) st.setType(State.Type.ACCEPT); }
                else if (line.startsWith("REJECT:")) { String s = line.substring(7).trim(); State st = cur.getStates().get(s); if (st != null) st.setType(State.Type.REJECT); }
                else if (line.startsWith("TRANSITION:")) {
                    try {
                        String[] p = line.substring(11).trim().split(",");
                        if (p.length == 5) cur.addTransition(new Transition(p[0].trim(), p[1].trim().charAt(0), p[2].trim(), p[3].trim().charAt(0), Transition.MoveDirection.valueOf(p[4].trim().toUpperCase())));
                    } catch (Exception ignored) {}
                }
            }
        }
    }

    /** Запазва само една конкретна машина в нов файл */
    public void saveSingleMachine(TuringMachine tm, String filename) throws IOException {
        try (PrintWriter w = new PrintWriter(new FileWriter(filename))) {
            w.println("# Machine: " + tm.getName());
            w.println("# ID: " + tm.getId());
            for (State s : tm.getStates().values()) w.println("STATE: " + s.getName());
            tm.getStates().values().stream().filter(s -> s.getType() == State.Type.START).findFirst().ifPresent(s -> w.println("START: " + s.getName()));
            tm.getStates().values().stream().filter(s -> s.getType() == State.Type.ACCEPT).forEach(s -> w.println("ACCEPT: " + s.getName()));
            tm.getStates().values().stream().filter(s -> s.getType() == State.Type.REJECT).forEach(s -> w.println("REJECT: " + s.getName()));
            tm.getTransitions().values().forEach(map -> map.values().forEach(t ->
                    w.println("TRANSITION: " + t.getFromState() + "," + t.getReadSymbol() + "," + t.getToState() + "," + t.getWriteSymbol() + "," + t.getDirection())));
        }
    }

    public void addMachine(TuringMachine m) { loadedMachines.put(m.getId(), m); }
    public TuringMachine getMachine(int id) { return loadedMachines.get(id); }
    public Map<Integer, TuringMachine> getLoadedMachines() { return loadedMachines; }
    public File getCurrentFile() { return currentFile; }
}
