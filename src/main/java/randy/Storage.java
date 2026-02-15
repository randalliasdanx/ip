package randy;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles reading and writing tasks to persistent storage.
 * 
 * This class manages serialization and deserialization of tasks to/from disk.
 * Tasks are stored in a text file with a specific format where each line
 * represents a task and includes its type, completion status, and content.
 */
public class Storage {
    private String filepath;

    /**
     * Creates a Storage manager for the given file path.
     * 
     * @param filepath the path to the storage file
     */
    public Storage(String filepath) {
        assert filepath != null && !filepath.isEmpty() : "filepath should not be null or empty";
        this.filepath = filepath;
    }

    /**
     * Initializes the storage by creating the data directory if it doesn't exist.
     * This should be called before any load or write operations.
     */
    public void init() {
        File f = new File(filepath);
        File dir = f.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Loads all tasks from the storage file.
     * If the file doesn't exist, returns an empty list.
     * Invalid lines in the file are skipped.
     * 
     * @return a list of loaded tasks
     * @throws IOException if an error occurs while reading the file
     */
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> list = new ArrayList<>();
        File f = new File(filepath);
        
        if (!f.exists()) {
            return list;
        }

        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            Task task = decode(line);
            if (task != null) {
                list.add(task);
            }
        }
        reader.close();
        return list;
    }

    /**
     * Parses a single line from storage back into a Task object.
     * Handles ToDo, Deadline, and Event task formats.
     * 
     * Line format: [TYPE][STATUS] description (additional info)
     * - TYPE: T (ToDo), D (Deadline), E (Event)
     * - STATUS: X (done) or space (not done)
     * 
     * @param line the encoded task line
     * @return the decoded Task, or null if format is invalid
     */
    private Task decode(String line) {
        assert line != null : "line to decode should not be null";
        Task task = null;
        
        if (line.startsWith("[T]")) {
            String desc = line.substring(6).trim();
            task = new ToDo(desc);
        } else if (line.startsWith("[D]")) {
            String desc = line.substring(6, line.indexOf(" (by:")).trim();
            String by = line.substring(line.indexOf("(by:") + 5, line.indexOf(")")).trim();
            task = new Deadline(desc, by);
        } else if (line.startsWith("[E]")) {
            String desc = line.substring(6, line.indexOf(" (from:")).trim();
            String from = line.substring(line.indexOf("(from:") + 6, line.indexOf(" to:")).trim();
            String to = line.substring(line.indexOf("to:") + 3, line.indexOf(")")).trim();
            task = new Event(desc, from, to);
        }

        // check if marked done - character at index 4 is the status icon
        if (task != null && line.length() > 4) {
            if (line.charAt(4) == 'X') {
                task.setDone();
            }
        }
        
        return task;
    }

    /**
     * Writes all tasks to the storage file.
     * Overwrites the file completely. If an error occurs, prints an error message
     * but doesn't throw an exception, allowing the application to continue.
     * 
     * @param tasks the TaskList to save
     */
    public void writeToFile(TaskList tasks) {
        assert tasks != null : "tasks to save should not be null";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, false));
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("oops couldn't save: " + e.getMessage());
        }
    }
}
