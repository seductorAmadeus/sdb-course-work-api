package enums;

public enum MenuInputType {

    // HibernateOperationsManager
    ADD_REGISTRATION_CODES("1. Add invite code\n"),
    PRINT_REGISTRATION_CODES("2. Print all invite codes\n"),
    GET_REGISTRATION_CODE_BY_ID("3. Get invite code by id\n"),
    UPDATE_REGISTRATION_CODES("4. Update invite code status\n"),
    DELETE_REGISTRATION_CODES("5. Delete invite code\n"),
    ADD_USER("6. Add user\n"),
    PRINT_USERS("7. Print all users\n"),
    GET_USER_BY_ID("8. Get user by id\n"),
    UPDATE_USER("9. Update user\n"),
    DELETE_USER("10. Delete user (and user profile)\n"),
    GENERATE_USER_ROLE("11. Generate user roles list\n"),
    ADD_USER_GROUP("12. Add user group\n"),
    PRINT_USER_GROUP("13. Print all user's group\n"),
    GET_USER_GROUP_BY_ID("14. Get user's group by id\n"),
    DELETE_USER_GROUP("15. Delete user's group by id\n"),
    CREATE_USER_SESSION("16. Sign in\n"),
    PRINT_USER_SESSION("17. Print all user's sessions\n"),
    GET_USER_SESSION_BY_ID("18. Print user session by id\n"),
    UPDATE_USER_SESSION("19. Update user's session by id\n"),
    DELETE_USER_SESSION("20. Delete user's session by id\n"),
    ADD_BCOMP("21. Add new empty bcomp\n"),
    PRINT_BCOMPS("22. Print all bcomps\n"),
    GET_BCOMP_BY_ID("23. Get bcomp by id\n"),
    UPDATE_BCOMP("24. Update bcomp by id\n"),
    DELETE_BCOMP("25. Delete bcomp by id\n"),
    CREATE_BCOMP_SETTINGS("26. Create new bcomp settings\n"),
    PRINT_BCOMP_SETTINGS("27. Print all bcomp settings\n"),
    GET_BCOMP_SETTING_BY_ID("28. Get bcomp setting by id\n"),
    UPDATE_BCOMP_SETTING("29. Update bcomp setting by id\n"),
    DELETE_BCOMP_SETTING("30. Delete bcomp setting by id\n"),
    ASSIGN_USER_SETTINGS("31. Assign user settings\n"),
    GET_EMAIL_USING_SESSION_ID("32. Get admin email from user's session id\n"),
    GET_SETTINGS_ID_FOR_USER("33. Get settingsId for user\n"),
    ADD_USER_PROFILE("34. Add user profile\n"),
    PRINT_USER_PROFILE("35. Print all user's profiles\n"),
    GET_USER_PROFILE_BY_ID("36. Get user profile by id\n"),
    UPDATE_USER_PROFILE("37. Update user profile\n"),
    //    DELETE_USER_PROFILE("35. Delete user profile \n(WARNING! This operation does not affect the user and only deletes the user profile if one exists)\n"),
    DELETE_USER_PROFILE("38. Delete user profile (and user)\n"),
    ADD_USER_PICTURE("39. Create new user picture\n"),
    PRINT_USER_PICTURE("40. Print all user's pictures names\n"),
    GET_USER_PICTURE_BY_ID("41. Print user's picture by id\n"),
    UPDATE_USER_PICTURE("42. Update user's picture by id\n"),
    DELETE_USER_PICTURE("43. Delete user's picture\n"),

    USER_SESSION_ID("Enter user session id: "),
    USER_STUDYING_ID("Enter user studying id: "),
    USER_PICTURE_ID("Enter user picture id: "),
    USER_PICTURE_PICNAME("Enter user's picture picname: "),
    // RegistrationCodes
    INVITE_CODE_STATUS("Enter invite code status: "),
    EMAIL("Enter email: "),
    INVITE_CODE("Enter existing invite code: "),
    // Users
    USERNAME("Enter username: "),
    PASSWORD("Enter password: "),
    USER_SESSION_STATUS("Enter user session status: "),
    USER_PROFILE_ID("Enter user profile id: "),
    REGISTRATION_CODE_ID("Enter invite code id: "),
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
