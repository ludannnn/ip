public class Command {
    public enum Type { BYE, LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, UNKNOWN }

    public final Type type;
    public final String desc, by, from, to; // for add-type commands
    public final Integer index;             // 0-based for mark/unmark/delete

    private Command(Type t, String desc, String by, String from, String to, Integer index) {
        this.type = t; this.desc = desc; this.by = by; this.from = from; this.to = to; this.index = index;
    }

    public static Command bye()      { return new Command(Type.BYE, null,null,null,null,null); }
    public static Command list()     { return new Command(Type.LIST,null,null,null,null,null); }
    public static Command unknown()  { return new Command(Type.UNKNOWN,null,null,null,null,null); }

    public static Command todo(String desc)             { return new Command(Type.TODO, desc,null,null,null,null); }
    public static Command deadline(String d, String by) { return new Command(Type.DEADLINE, d, by,null,null,null); }
    public static Command event(String d, String from, String to) {
        return new Command(Type.EVENT, d,null,from,to,null);
    }
    public static Command mark(int idx)   { return new Command(Type.MARK,  null,null,null,null, idx); }
    public static Command unmark(int idx) { return new Command(Type.UNMARK,null,null,null,null, idx); }
    public static Command delete(int idx) { return new Command(Type.DELETE,null,null,null,null, idx); }
}
