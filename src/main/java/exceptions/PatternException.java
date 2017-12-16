package exceptions;

import java.util.regex.Pattern;

public class PatternException extends Exception {
    private final static String DETAIL_MESSAGE = "String not found: ";

    public PatternException(String fieldName, Pattern pattern) {
        super(getErrorMessage(fieldName, pattern));
    }

    private static String getErrorMessage(String fieldName, Pattern pattern) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DETAIL_MESSAGE)
                .append(fieldName)
                .append(" must match the regular expression ")
                .append(pattern.toString());
        return stringBuilder.toString();
    }
}
