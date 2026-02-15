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
 * Tasks are stored one per line in their toString() format.
 */
public class Storage {
    private String filepath;

    /**
     * Creates a Storage instance for the given file path.
     *
     * @param filepath Path to the storage file.
     */
    public Storage(String filepath) {
        assert filepath != null && !filepath.isEmpty() : "filepath should not be null or empty";
        this.filepath = filepath;
    }

    /** Creates the parent directory for the storage file if it doesn't exist. */
    public void init() {
        File f = new File(filepath);
        File dir = f.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Loads tasks from the storage file.
     * Returns an empty list if the file doesn't exist.
     * Corrupted lines are skipped instead of crashing.
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

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                // wrap in try-catch so one bad line doesnt kill the whole load
                try {
                    Task task = decode(line);
                    if (task != null) {
                        list.add(task);
                    }
                } catch (Exception e) {
                    // corrupted line, just skip it
                    System.out.println("skipping bad line: " + line);
                }
            }
        }
        return list;
    }

    // figure out what type of task a line represents and recreate it
    private Task decode(String line) {
        assert line != null : "line to decode should not be null";
        Task task = null;

        if (line.startsWith("[T]")) {
            String desc = line.substring(6).trim();
            if (!desc.isEmpty()) {
                task = new ToDo(desc);
            }
        } else if (line.startsWith("[D]")) {
            int byIdx = line.indexOf(" (by:");
            if (byIdx != -1) {
                String desc = line.substring(6, byIdx).trim();
                int closeIdx = line.indexOf(")", byIdx);
                if (closeIdx != -1) {
                    String by = line.substring(line.indexOf("(by:") + 5, closeIdx).trim();
                    task = new Deadline(desc, by);
                }
            }
        } else if (line.startsWith("[E]")) {
            int fromIdx = line.indexOf(" (from:");
            if (fromIdx != -1) {
                String desc = line.substring(6, fromIdx).trim();
                int toIdx = line.indexOf(" to:", fromIdx);
                int closeIdx = (toIdx != -1) ? line.indexOf(")", toIdx) : -1;
                if (toIdx != -1 && closeIdx != -1) {
                    String from = line.substring(line.indexOf("(from:") + 6, toIdx).trim();
                    String to = line.substring(toIdx + 4, closeIdx).trim();
                    task = new Event(desc, from, to);
                }
            }
        }

        // check the done status - 4th char is X if marked done
        if (task != null && line.length() > 4 && line.charAt(4) == 'X') {
            task.setDone();
        }

        return task;
    }

    /**
     * Saves all tasks to the storage file, overwriting any existing content.
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
            System.out.println("oops couldnt save: " + e.getMessage());
        }
    }
}
