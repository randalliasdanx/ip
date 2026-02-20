package randy;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles reading and writing tasks to a text file for persistence.
 * Uses try-with-resources for safe I/O and handles corrupted data gracefully.
 */
public class Storage {
    private final String filepath;

    /**
     * Creates a Storage instance for the given file path.
     *
     * @param filepath Path to the storage file.
     */
    public Storage(String filepath) {
        assert filepath != null && !filepath.isEmpty() : "filepath should not be null or empty";
        this.filepath = filepath;
    }

    /**
     * Creates the parent directory for the storage file if it doesn't exist.
     * Handles the case where directory creation fails (e.g., permissions).
     */
    public void init() {
        File f = new File(filepath);
        File dir = f.getParentFile();
        if (dir != null && !dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                System.out.println("warning: could not create data directory: " + dir.getPath());
            }
        }
    }

    /**
     * Loads tasks from the storage file.
     * Returns an empty list if the file doesn't exist or can't be read.
     * Corrupted or malformed lines are skipped instead of crashing.
     *
     * @return An ArrayList of the loaded tasks.
     * @throws IOException If the file cannot be read.
     */
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> list = new ArrayList<>();
        File f = new File(filepath);

        if (!f.exists()) {
            return list;
        }

        if (!f.canRead()) {
            System.out.println("warning: cannot read file: " + filepath);
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                Task task = decode(line, lineNumber);
                if (task != null) {
                    list.add(task);
                }
            }
        }
        return list;
    }

    /**
     * Parses a single line from storage back into a Task.
     * Wraps parsing in try-catch so a corrupted line does not crash loading.
     *
     * @param line       The encoded task line.
     * @param lineNumber The line number for error reporting.
     * @return The decoded Task, or null if the line is corrupted.
     */
    private Task decode(String line, int lineNumber) {
        assert line != null : "line to decode should not be null";
        try {
            return decodeTask(line);
        } catch (StringIndexOutOfBoundsException | IllegalArgumentException e) {
            System.out.println("warning: skipping corrupted line " + lineNumber + ": " + line);
            return null;
        }
    }

    /**
     * Internal decode that may throw on corrupted data.
     */
    private Task decodeTask(String line) {
        Task task = null;

        if (line.startsWith("[T]")) {
            String desc = line.substring(6).trim();
            if (!desc.isEmpty()) {
                task = new ToDo(desc);
            }
        } else if (line.startsWith("[D]")) {
            int byIdx = line.indexOf(" (by:");
            if (byIdx == -1) {
                return null;
            }
            String desc = line.substring(6, byIdx).trim();
            int closeIdx = line.indexOf(")", byIdx);
            if (closeIdx == -1) {
                return null;
            }
            String by = line.substring(line.indexOf("(by:") + 5, closeIdx).trim();
            if (!desc.isEmpty() && !by.isEmpty()) {
                task = new Deadline(desc, by);
            }
        } else if (line.startsWith("[E]")) {
            int fromIdx = line.indexOf(" (from:");
            if (fromIdx == -1) {
                return null;
            }
            String desc = line.substring(6, fromIdx).trim();
            int toIdx = line.indexOf(" to:", fromIdx);
            if (toIdx == -1) {
                return null;
            }
            int closeIdx = line.indexOf(")", toIdx);
            if (closeIdx == -1) {
                return null;
            }
            String from = line.substring(line.indexOf("(from:") + 6, toIdx).trim();
            String to = line.substring(toIdx + 4, closeIdx).trim();
            if (!desc.isEmpty() && !from.isEmpty() && !to.isEmpty()) {
                task = new Event(desc, from, to);
            }
        }

        // check if marked done
        if (task != null && line.length() > 4) {
            if (line.charAt(4) == 'X') {
                task.setDone();
            }
        }

        return task;
    }

    /**
     * Saves all tasks to the storage file, overwriting any existing content.
     * If the file cannot be written, prints an error message.
     *
     * @param tasks The TaskList to save.
     */
    public void writeToFile(TaskList tasks) {
        assert tasks != null : "tasks to save should not be null";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, false))) {
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("oops couldn't save: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("permission denied writing to: " + filepath);
        }
    }
}
