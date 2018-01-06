package exceptions;

public class IllegalStringLengthException extends Exception {
    private final static String DETAIL_MESSAGE = "Illegal string length exception: ";

    public IllegalStringLengthException(String fieldName, int min, int max) {
        super(getErrorMessage(fieldName, min, max));
    }

    private static String getErrorMessage(String fieldName, int min, int max) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DETAIL_MESSAGE)
                .append(fieldName)
                .append(" length should be in the interval: ")
                .append("[")
                .append(min).append("; ")
                .append(max)
                .append("]");

        return stringBuilder.toString();
    }
}
