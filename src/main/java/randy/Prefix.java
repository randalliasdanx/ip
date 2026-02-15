package randy;

/**
 * Enum of all valid command keywords that Randy recognises.
 */
public enum Prefix {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, ON, FIND;

    /**
     * Checks if a string matches any valid command keyword (case-insensitive).
     *
     * @param s The string to check.
     * @return true if it matches a known command.
     */
    public static boolean contains(String s) {
        for (Prefix p : Prefix.values()) {
            if (p.name().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}
