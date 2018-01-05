package utils;

public enum CachePrefixType {
    BCOMP("bcomp:"),
    BCOMP_SETTINGS("bcomp_settings:"),
    REGISTRATION_CODES("registration_codes:"),
    USERS("users:");

    private String text;

    CachePrefixType(String text) {
        this.text = text;
    }

    private String getText() {
        return text;
    }

    @Override
    public String toString() {
        return this.getText();
    }

}
