package randy;

import java.util.Arrays;

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
        return Arrays.stream(Prefix.values())
                .anyMatch(p -> p.name().equalsIgnoreCase(s));
    }
}
