public class ToDoTask extends Task {


    public ToDoTask(String desc) {
        super(desc);
    }

    @Override
    public String toString(){
        return "[T]" + "[" + this.getStatusIcon() + "] " + this.description;
    }

    @Override
    public String toSaveLine() {
        return "T | " + (isDone ? 1 : 0) + " | " + description;
    }
}


