package enums;

public enum LimitType {
    BCOMP_ID("bcomp_id", 23, 23);

    private int min;
    private int max;
    private String fieldName;

    LimitType(String fieldName, int min, int max) {
        this.fieldName = fieldName;
        this.min = min;
        this.max = max;
    }

    public int getMinLength() {
        return min;
    }

    public int getMaxLength() {
        return max;
    }

    public String getFieldName() {
        return fieldName;
    }
}
