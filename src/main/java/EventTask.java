public class EventTask extends Task {
    String start;
    String end;

    public EventTask(String desc, String start, String end) {
        super(desc);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString(){
        return "[E]" + "[" + this.getStatusIcon() + "] " + this.description + " (from: " + this.start + " to: " + this.end + ")";
    }


    @Override
    public String toSaveLine() {
        return "E | " + (isDone ? 1 : 0) + " | " + description + " | " + start + " | " + end;
    }
}
