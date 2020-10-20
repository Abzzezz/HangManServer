package ga.abzzezz.hangman.util;

public class QuickLog {

    public static void log(final String message, final LogType logType) {
        System.out.println("[".concat(logType.name()).concat("] " + message));
    }


    public enum LogType {
        INFO, WARNING, ERROR
    }
}
