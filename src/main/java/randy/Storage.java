package randy;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles reading/writing tasks to disk.
 */
public class Storage {
    private String filepath;

    public Storage(String filepath) {
        this.filepath = filepath;
    }

    // create data folder if needed
    public void init() {
        File f = new File(filepath);
        File dir = f.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
    }

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

    // parse a line from storage file back into a Task
    private Task decode(String line) {
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

        // check if marked done
        if (task != null && line.length() > 4) {
            if (line.charAt(4) == 'X') {
                task.setDone();
            }
        }
        
        return task;
    }

    public void writeToFile(TaskList tasks) {
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
