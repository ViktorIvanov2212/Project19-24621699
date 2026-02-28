import java.io.*;
import java.util.*;

public class FileManager {
    private File currentFile;
    private TuringMachine currentMachine;
    private Map<Integer, TuringMachine> loadedMachines;
    private boolean hasUnsavedChanges;

    public FileManager() {
        this.loadedMachines = new HashMap<>();
        this.hasUnsavedChanges = false;
    }

    public boolean open(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
                currentFile = file;
                currentMachine = null;
                hasUnsavedChanges = false;
                System.out.println("Successfully created and opened " + filename);
                return true;
            }

            currentFile = file;
            hasUnsavedChanges = false;
            System.out.println("Successfully opened " + filename);
            return true;
        } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
            return false;
        }
    }

    public boolean close() {
        if (hasUnsavedChanges) {
            System.out.println("Warning: You have unsaved changes!");
        }
        currentFile = null;
        currentMachine = null;
        System.out.println("File closed successfully");
        return true;
    }

    public boolean save() {
        if (currentFile == null) {
            System.err.println("No file is currently open");
            return false;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(currentFile))) {
            if (currentMachine != null) {
                writer.println(currentMachine.toString());
            }
            hasUnsavedChanges = false;
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
                if (currentMachine != null) {
                    writer.println(currentMachine.toString());
                }
                currentFile = file;
                hasUnsavedChanges = false;
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
        hasUnsavedChanges = true;
    }

    public TuringMachine getMachine(int id) {
        return loadedMachines.get(id);
    }

    public Map<Integer, TuringMachine> getLoadedMachines() {
        return loadedMachines;
    }

    public void setCurrentMachine(TuringMachine machine) {
        this.currentMachine = machine;
    }

    public TuringMachine getCurrentMachine() {
        return currentMachine;
    }

    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }

    public void markChanged() {
        hasUnsavedChanges = true;
    }
}
