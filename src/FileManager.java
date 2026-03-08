import java.io.*;
import java.util.*;

public class FileManager {
    private File currentFile;
    private Map<Integer, TuringMachine> loadedMachines;

    public FileManager() {
        this.loadedMachines = new HashMap<>();
    }
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
            System.out.println("Successfully opened " + filename);
            return true;
        } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
            return false;
        }
    }

    public boolean close() {
        currentFile = null;
        System.out.println("File closed successfully");
        return true;
    }

    public boolean save() {
        if (currentFile == null) {
            System.err.println("No file is currently open");
            return false;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(currentFile))) {
            for (TuringMachine tm : loadedMachines.values()) {
                writer.println(tm.toString());
            }
            System.out.println("Successfully saved " + currentFile.getName());
            return true;
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
            return false;
        }
    }

    public boolean saveAs(String filename) {
        try {
            File file = new File(filename);
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (TuringMachine tm : loadedMachines.values()) {
                    writer.println(tm.toString());
                }
                currentFile = file;
                System.out.println("Successfully saved as " + filename);
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
            return false;
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

    public void markChanged() {
    }
}
