package yoda;


import yoda.parser.Command;
import yoda.parser.Parser;
import yoda.storage.Storage;
import yoda.task.DeadlineTask;
import yoda.task.EventTask;
import yoda.task.Task;
import yoda.task.TaskList;
import yoda.task.ToDoTask;
import yoda.ui.Ui;

import java.util.ArrayList;
import java.util.Scanner;

public class Yoda {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a Yoda instance
     */
    public Yoda() {
        this.ui = new Ui(new Scanner(System.in));
        this.storage = new Storage();
        ArrayList<Task> loaded = storage.load();
        this.tasks = new TaskList(loaded);
    }

    /**
     * Starts the Yoda task manager.
     * Creates a Yoda instance and runs the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new Yoda().run();
    }

    /**
     * Starts the task manager application.
     * Loads existing tasks, shows welcome message, and enters main loop.
     */
    public void run() {
        ui.showWelcome();
        boolean running = true;

        while (running) {
            String line = ui.readCommand();
            if (line.isEmpty()) continue;

            try {
                Command command = Parser.parse(line);

                switch (command.type) {
                case BYE:
                    ui.showBye();
                    running = false;
                    break;

                case LIST:
                    ui.showList(tasks);
                    break;

                case MARK: {
                    if (command.index < 0 || command.index >= tasks.size()) throw new IndexOutOfBoundsException();
                    tasks.mark(command.index);
                    ui.showMarked(tasks.get(command.index));
                    storage.save(tasks.asList());
                }
                break;

                case UNMARK: {
                    if (command.index < 0 || command.index >= tasks.size()) throw new IndexOutOfBoundsException();
                    tasks.unmark(command.index);
                    ui.showUnmarked(tasks.get(command.index));
                    storage.save(tasks.asList());
                }
                break;

                case TODO: {
                    Task t = new ToDoTask(command.desc);
                    tasks.add(t);
                    ui.showAdded(t, tasks.size());
                    storage.save(tasks.asList());
                }
                break;

                case DEADLINE: {
                    Task t = new DeadlineTask(command.desc, command.by);
                    tasks.add(t);
                    ui.showAdded(t, tasks.size());
                    storage.save(tasks.asList());
                }
                break;

                case EVENT: {
                    Task t = new EventTask(command.desc, command.from, command.to);
                    tasks.add(t);
                    ui.showAdded(t, tasks.size());
                    storage.save(tasks.asList());
                }
                break;

                case DELETE: {
                    if (command.index < 0 || command.index >= tasks.size()) throw new IndexOutOfBoundsException();
                    Task deleted = tasks.remove(command.index);
                    ui.showDeleted(deleted);
                    storage.save(tasks.asList());
                }
                break;

                case UNKNOWN:
                    ui.showUnknown();
                    break;
                }

            } catch (NumberFormatException e) {
                ui.showError("A number, that is not. (e.g., mark 2)");
            } catch (IndexOutOfBoundsException e) {
                ui.showError("Within the list, that task number is not.");
            } catch (IllegalArgumentException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Unexpected error, it is: " + e.getMessage());
            }
        }

        ui.close();
    }

}



