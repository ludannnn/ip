package yoda.storage;

import yoda.task.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads and saves tasks to a UTF-8 text file (data/yoda.txt).
 */
public class Storage {
    private final Path file;


    /**
     * Creates a storage that uses the file at data/yoda.txt.
     */
    public Storage() {
        this.file = Paths.get("data", "yoda.txt");
    }


    /**
     * Loads tasks from disk.
     * Creates the file and its parent directory if missing, then returns an empty list.
     *
     * @return a mutable list of tasks; never null
     */
    public ArrayList<Task> load() {
        try {
            if (Files.notExists(file)) {
                Files.createDirectories(file.getParent());
                Files.createFile(file);
                return new ArrayList<>();
            }
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            ArrayList<Task> tasks = new ArrayList<>();
            for (String line : lines) {
                if (line.isBlank()) continue;
                tasks.add(Task.fromSaveLine(line));
            }
            return tasks;
        } catch (IOException e) {
            // first run / corrupted file handling can be improved later
            return new ArrayList<>();
        }
    }

    /**
     * Saves all tasks to disk, overwriting existing contents.
     *
     * @param tasks tasks to write; order is preserved
     */
    public void save(List<Task> tasks) {
        try {
            List<String> lines = new ArrayList<>();
            for (Task t : tasks) lines.add(t.toSaveLine());
            Files.createDirectories(file.getParent());
            Files.write(file, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {

        }
    }
}
