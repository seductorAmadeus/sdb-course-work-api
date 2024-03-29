package enums;

public enum CachePrefixType {
    BCOMP("bcomp:"),
    BCOMP_SETTINGS("bcomp_settings:"),
    REGISTRATION_CODES("registration_codes:"),
    USERS("users:"),
    USER_PROFILE("user_profile:"),
    USER_SESSION("user_session:"),
    USER_ROLE("user_role:"),
    USER_STUDYING("user_studying:"),
    USER_PICTURE("user_picture:");

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
