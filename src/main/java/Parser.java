public class Parser {

    public static Command parse(String line) {
        if (line.isBlank()) return Command.unknown();

        String[] parts = line.split("\\s+", 2);
        String cmd = parts[0].toLowerCase();
        String arg = (parts.length > 1) ? parts[1].trim() : "";

        switch (cmd) {
            case "bye":  return Command.bye();
            case "list": return Command.list();

            case "todo": {
                if (arg.isEmpty()) throw new IllegalArgumentException("Todo, what do you need?");
                return Command.todo(arg);
            }

            case "deadline": {
                // <desc> /by <when>
                String[] d = arg.split("\\s*/by\\s*", 2);
                if (d.length < 2 || d[0].isBlank() || d[1].isBlank())
                    throw new IllegalArgumentException("Description and deadline, please tell");
                return Command.deadline(d[0].trim(), d[1].trim());
            }

            case "event": {
                // <desc> /from <start> /to <end>
                String[] e1 = arg.split("\\s*/from\\s*", 2);
                if (e1.length < 2 || e1[0].isBlank())
                    throw new IllegalArgumentException("Description, start and end, please tell");
                String[] e2 = e1[1].split("\\s*/to\\s*", 2);
                if (e2.length < 2 || e2[0].isBlank() || e2[1].isBlank())
                    throw new IllegalArgumentException("Description, start and end, please tell");
                return Command.event(e1[0].trim(), e2[0].trim(), e2[1].trim());
            }

            case "mark": {
                if (arg.isEmpty()) throw new IllegalArgumentException("Mark what item, do you want?");
                int idx = Integer.parseInt(arg) - 1; // 0-based
                return Command.mark(idx);
            }

            case "unmark": {
                if (arg.isEmpty()) throw new IllegalArgumentException("Unmark what item, do you want?");
                int idx = Integer.parseInt(arg) - 1;
                return Command.unmark(idx);
            }

            case "delete": {
                if (arg.isEmpty()) throw new IllegalArgumentException("Delete what item, do you want?");
                int idx = Integer.parseInt(arg) - 1;
                return Command.delete(idx);
            }

            default: return Command.unknown();
        }
    }
}
