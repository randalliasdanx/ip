package randy;

/**
 * Valid command keywords.
 */
public enum Prefix {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, ON, FIND, REMIND;

    public static boolean contains(String s) {
        for (Prefix p : Prefix.values()) {
            if (p.name().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}
