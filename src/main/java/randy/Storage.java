package randy;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    private String path;

    public Storage(String path) {
        this.path = path;
    }

    public void setupDirectory() {
        File outputFile = new File(path);
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> arr = new ArrayList<>();
        File file = new File(path);
        
        if (!file.exists()) {
            return arr;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                Task task = parseLine(line);
                if (task != null) {
                    arr.add(task);
                }
            }
        }
        return arr;
    }

    private Task parseLine(String line) {
        Task task = null;
        
        if (line.startsWith("[T]")) {
            String description = line.substring(6).trim();
            task = new ToDo(description);
        } else if (line.startsWith("[D]")) {
            String description = line.substring(6, line.indexOf(" (by:")).trim();
            String by = line.substring(line.indexOf("(by:") + 5, line.indexOf(")")).trim();
            task = new Deadline(description, by);
        } else if (line.startsWith("[E]")) {
            String description = line.substring(6, line.indexOf(" (from:")).trim();
            String from = line.substring(line.indexOf("(from:") + 6, line.indexOf(" to:")).trim();
            String to = line.substring(line.indexOf("to:") + 3, line.indexOf(")")).trim();
            task = new Event(description, from, to);
        }

        if (task != null) {
            String status = line.substring(4, 5);
            if (status.equals("X")) {
                task.mark();
            }
        }
        
        return task;
    }

    public void save(TaskList arr) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            for (int i = 0; i < arr.size(); i++) {
                bw.write(arr.get(i).toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
