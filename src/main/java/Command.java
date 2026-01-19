public enum Command {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE;

    public static boolean isValid(String inp) {
        for (Command cmd : Command.values()) {
            if (cmd.name().equalsIgnoreCase(inp)) {
                return true;
            }
        }
        return false;
    }
}
