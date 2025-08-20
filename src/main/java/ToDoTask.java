public class ToDoTask extends Task {


    public ToDoTask(String desc) {
        super(desc);
    }

    @Override
    public String toString(){
        return "[T]" + "[" + this.getStatusIcon() + "] " + this.description;
    }
}


