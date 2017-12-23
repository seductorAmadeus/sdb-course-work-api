package utils;

public enum MenuInputType {

    // HibernateOperationsManager
    ENTER_REGISTRATION_CODES("1. Add invite code\n"),
    PRINT_REGISTRATION_CODES("2. Show invite codes\n"),
    UPDATE_REGISTRATION_CODES("3. Refresh invite code status\n"),
    DELETE_REGISTRATION_CODES("4. Delete invite code\n"),
    ENTER_USERS("5. Add user\n"),
    DELETE_USERS("6. Delete user\n"),
    GENERATE_USER_ROLE("7. Generate user roles list\n"),
    ENTER_USER_GROUP("8. Add new user group\n"),
    CREATE_USER_SESSION("9. Sign in\n"),
    CREATE_BCOMP("10. Create new BCOMP\n"),
    CREATE_BCOMP_SETTINGS("11. Create new bcomp settings\n"),
    ASSING_USER_SETTINGS("12. Assign user settings\n"),
    GET_EMAIL_USING_SESSION_ID("13. Get admin email from user's session id\n"),
    // RegistrationCodes
    INVITE_CODE_STATUS("Enter invite code status: "),
    EMAIL("Enter email: "),
    INVITE_CODE("Enter existing invite code: "),
    // Users
    USERNAME("Enter username: "),
    PASSWORD("Enter password: "),
    USER_ID("Enter user id: "),
    STUDYING_STATUS("Enter user status: "),
    FIRST_NAME("Enter user first name: "),
    LAST_NAME("Enter user last name: "),
    GENDER("Enter user gender: "),
    DATE_OF_BIRTH("Enter user date of birth: "),
    //UserStudying
    USER_GROUP("Enter user group: "),
    USER_ROLE("Enter user role: "),
    BCOMP("Enter user session id: "),
    BCOMP_SETTINGS_ID("Enter bcomp settings id: "),
    BCOMP_SETTINGS_TYPE("Enter bcomp settings type: "),
    BCOMP_SETTINGS_VALUE("Enter bcomp settings value: "),
    MEMORY("Enter memory: "),
    RS("Enter rs register: "),
    RA("Enter ra register: "),
    RD("Enter rd register: "),
    RC("Enter rc register: "),
    CC("Enter cc register: "),
    BR("Enter buffer register: "),
    AC("Enter accumulator: "),
    C("Enter bit C: "),
    KR("Enter ")
    // TODO: заполнить полностью это
    ;

    private String text;

    MenuInputType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
