package randy;

public enum Prefix {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, ON, FIND;

    public static boolean isValid(String inp) {
        for (Prefix cmd : Prefix.values()) {
            if (cmd.name().equalsIgnoreCase(inp)) {
                return true;
            }
        }
        return false;
    }
}
