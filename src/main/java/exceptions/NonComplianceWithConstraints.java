package exceptions;

public class NonComplianceWithConstraints extends Exception {

    private final static String DETAIL_MESSAGE = "Unique constraint violation: ";

    public NonComplianceWithConstraints(String fieldName, String... constraints) {
        super(getErrorMessage(fieldName, constraints));
    }

    private static String getErrorMessage(String fieldName, String... constraints) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DETAIL_MESSAGE)
                .append(fieldName)
                .append(" must be ");
        for (String constraint : constraints) {
            stringBuilder.append("\"")
                    .append(constraint)
                    .append("\"")
                    .append(", ");
        }
        // remove last character from stringBuilder
        stringBuilder.setLength(stringBuilder.length() - ", ".length());

        return stringBuilder.toString();
    }
}
