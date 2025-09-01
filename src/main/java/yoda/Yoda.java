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
    private boolean shouldExit = false;

    /**
     * Creates a Yoda instance
     */
    public Yoda() {
        this.storage = new Storage();
        ArrayList<Task> loaded = storage.load();
        this.tasks = new TaskList(loaded);
    }

    public boolean shouldExit() {
        return this.shouldExit;
    }

    private static String line() { return "_".repeat(40); }

    public String getResponse(String input) {
        String in = input == null ? "" : input.trim();
        if (in.isEmpty()) return "";

        try {
            Command command = Parser.parse(in);

            switch (command.type) {
                case BYE:
                    shouldExit = true;
                    return "Farewell, I bid you.";

                case LIST: {
                    StringBuilder sb = new StringBuilder();
                    sb.append(line()).append('\n');
                    sb.append("The tasks in your list, here are:\n");
                    for (int i = 0; i < tasks.size(); i++) {
                        sb.append(i + 1).append(". ").append(tasks.get(i)).append('\n');
                    }
                    sb.append(line());
                    return sb.toString();
                }

                case MARK: {
                    int idx = command.index;
                    if (idx < 0 || idx >= tasks.size()) throw new IndexOutOfBoundsException();
                    tasks.mark(idx);
                    storage.save(tasks.asList());
                    return line()+"\nMarked this task as done, I have:\n"+tasks.get(idx)+"\n"+line();
                }

                case UNMARK: {
                    int idx = command.index;
                    if (idx < 0 || idx >= tasks.size()) throw new IndexOutOfBoundsException();
                    tasks.unmark(idx);
                    storage.save(tasks.asList());
                    return line()+"\nnot done yet, is this task:\n"+tasks.get(idx)+"\n"+line();
                }

                case TODO: {
                    Task t = new ToDoTask(command.desc);
                    tasks.add(t);
                    storage.save(tasks.asList());
                    return line()+"\nAdded this task, I have:\n    "+t+"\n"+
                            tasks.size()+" tasks in the list, Now you have.\n"+line();
                }

                case DEADLINE: {
                    Task t = new DeadlineTask(command.desc, command.by);
                    tasks.add(t);
                    storage.save(tasks.asList());
                    return line()+"\nAdded this task, I have:\n    "+t+"\n"+
                            tasks.size()+" tasks in the list, Now you have.\n"+line();
                }

                case EVENT: {
                    Task t = new EventTask(command.desc, command.from, command.to);
                    tasks.add(t);
                    storage.save(tasks.asList());
                    return line()+"\nAdded this task, I have:\n    "+t+"\n"+
                            tasks.size()+" tasks in the list, Now you have.\n"+line();
                }

                case DELETE: {
                    int idx = command.index;
                    if (idx < 0 || idx >= tasks.size()) throw new IndexOutOfBoundsException();
                    Task deleted = tasks.remove(idx);
                    storage.save(tasks.asList());
                    return line()+"\nDeleted this task, I have:\n"+deleted+"\n"+line();
                }

                case FIND: {
                    TaskList matches = tasks.find(command.desc);
                    StringBuilder sb = new StringBuilder();
                    sb.append(line()).append('\n');
                    sb.append("Here are the matching tasks in your list:\n");
                    for (int i = 0; i < matches.size(); i++) {
                        sb.append(i + 1).append(". ").append(matches.get(i)).append('\n');
                    }
                    sb.append(line());
                    return sb.toString();
                }

                default:
                    return line()+"\nWhat you are trying to say, I do not understand.\n"+line();
                }

            } catch (NumberFormatException e) {
                return line()+"\nA number, that is not. (e.g., mark 2)\n"+line();
            } catch (IndexOutOfBoundsException e) {
                return line()+"\nWithin the list, that task number is not.\n"+line();
            } catch (IllegalArgumentException e) {
                return line()+"\n"+e.getMessage()+"\n"+line();
            } catch (Exception e) {
                return line()+"\nUnexpected error, it is: " + e.getMessage() + "\n"+line();
            }
        }
}



