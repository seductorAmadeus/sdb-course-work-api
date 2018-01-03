package utils;

public enum MenuInputType {

    // HibernateOperationsManager
    ADD_REGISTRATION_CODES("1. Add invite code\n"),
    PRINT_REGISTRATION_CODES("2. Print all invite codes\n"),
    UPDATE_REGISTRATION_CODES("3. Update invite code status\n"),
    DELETE_REGISTRATION_CODES("4. Delete invite code\n"),
    ADD_USER("5. Add user\n"),
    PRINT_USERS("6. Print all users (D)\n"),
    UPDATE_USER("7. Update user (D) \n"),
    DELETE_USER("8. Delete user\n"),
    GENERATE_USER_ROLE("9. Generate user roles list\n"),
    ADD_USER_GROUP("10. Add user group\n"),
    CREATE_USER_SESSION("11. Sign in\n"),
    ADD_BCOMP("12. Add new empty BCOMP\n"),
    PRINT_BCOMPS("13. Print all bcomps\n"),
    UPDATE_BCOMP("14. Update BCOMP\n"),
    DELETE_BCOMP("15. Delete bcomp by id\n"),
    CREATE_BCOMP_SETTINGS("16. Create new bcomp settings\n"),
    ASSING_USER_SETTINGS("17. Assign user settings\n"),
    GET_EMAIL_USING_SESSION_ID("18. Get admin email from user's session id\n"),
    GET_SETTINGS_ID_FOR_USER("19. Get settingsId for user\n"),
    UPDATE_USER_PROFILE("20. UPDATE USER_PROFILE\n"),

    // RegistrationCodes
    INVITE_CODE_STATUS("Enter invite code status: "),
    EMAIL("Enter email: "),
    INVITE_CODE("Enter existing invite code: "),
    // Users
    USERNAME("Enter username: "),
    PASSWORD("Enter password: "),
    USER_PROFILE_ID("Enter user profile id: "),
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
    BCOMP_ID("Enter bcomp id: "),
    MEMORY("Enter memory: "),
    RS("Enter rs register: "),
    RA("Enter ra register: "),
    RD("Enter rd register: "),
    RC("Enter rc register: "),
    CC("Enter cc register: "),
    BR("Enter buffer register: "),
    AC("Enter accumulator: "),
    C("Enter bit C: "),
    KR("Enter клавишный register: "),
    BIT("Enter alone bit (from Клавишный register): "),
    INT_REQ_ED_1("Enter interrupt request status of the external device №1: "),
    INT_REQ_ED_2("Enter interrupt request status of the external device №2: "),
    INT_REQ_ED_3("Enter interrupt request status of the external device №3: "),
    RD_ED_1("Enter value of the data register of the external device №1: "),
    RD_ED_2("Enter value of the data register of the external device №2: "),
    RD_ED_3("Enter value of the data register of the external device №3: "),
    MEMORY_MC("Enter value of the micro-command memory: "),
    C_MC("Enter value of the micro-command counter: "),
    R_MC("Enter value of the register of the micro-command: "),
    ASM("Enter assembler code: ");
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
