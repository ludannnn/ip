public class DeadlineTask extends Task{
    String deadline;

    public DeadlineTask(String desc, String deadline) {
        super(desc);
        this.deadline = deadline;
    }

    @Override
    public String toString(){
        return "[D]" + "[" + this.getStatusIcon() + "] " + this.description + " (by: " + this.deadline + ")";
    }
}
