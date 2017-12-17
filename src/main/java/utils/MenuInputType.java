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
    GENERATE__USER_GROUP("8. Generate user group list\n"),

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
    USER_ROLE("Enter user role: ");


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
