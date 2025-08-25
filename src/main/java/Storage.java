import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path file;

    public Storage() {
        this.file = Paths.get("data", "yoda.txt");
    }

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
