package utils;

public enum MenuInputType {

    // HibernateOperationsManager
    ADD_REGISTRATION_CODES("1. Add invite code\n"),
    PRINT_REGISTRATION_CODES("2. Print all invite codes\n"),
    GET_REGISTRATION_CODE_BY_ID("3. Get invite code by id (D)\n"),
    UPDATE_REGISTRATION_CODES("4. Update invite code status\n"),
    DELETE_REGISTRATION_CODES("5. Delete invite code\n"),
    ADD_USER("6. Add user\n"),
    PRINT_USERS("7. Print all users (D)\n"),
    GET_USER_BY_ID("8. Get user by id (D)\n"),
    UPDATE_USER("9. Update user (D)\n"),
    DELETE_USER("10. Delete user\n"),
    GENERATE_USER_ROLE("11. Generate user roles list\n"),
    ADD_USER_GROUP("12. Add user group\n"),
    CREATE_USER_SESSION("13. Sign in\n"),
    ADD_BCOMP("14. Add new empty BCOMP\n"),
    PRINT_BCOMPS("15. Print all bcomps\n"),
    GET_BCOMP_BY_ID("16. Get bcomp by id\n"),
    UPDATE_BCOMP("17. Update bcomp by id\n"),
    DELETE_BCOMP("18. Delete bcomp by id\n"),
    CREATE_BCOMP_SETTINGS("19. Create new bcomp settings\n"),
    PRINT_BCOMP_SETTINGS("20. Print all bcomp settings\n"),
    GET_BCOMP_SETTING_BY_ID("21. Get bcomp setting by id\n"),
    UPDATE_BCOMP_SETTING("22. Update bcomp setting by id\n"),
    DELETE_BCOMP_SETING("23. Delete bcomp setting by id (D)\n"),
    ASSING_USER_SETTINGS("20. Assign user settings\n"),
    GET_EMAIL_USING_SESSION_ID("21. Get admin email from user's session id\n"),
    GET_SETTINGS_ID_FOR_USER("22. Get settingsId for user\n"),
    UPDATE_USER_PROFILE("23. Update user profile\n"),

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
