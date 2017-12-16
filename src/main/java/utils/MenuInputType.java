package utils;

public enum MenuInputType {

    // HibernateOperationsManager
    ENTER_REGISTRATION_CODES("1. Добавить инвайт-код\n"),
    PRINT_REGISTRATION_CODES("2. Выдать все инвайт-коды\n"),
    UPDATE_REGISTRATION_CODES("3. Обновить статус инвайт-кода\n"),
    DELETE_REGISTRATION_CODES("4. Удалить инвайт-код\n"),
    ENTER_USERS("5. Добавить пользователя\n"),
    DELETE_USERS("6. Удалить пользователя\n"),
    GENERATE_USER_ROLE("7. Сгенерировать список ролей пользователя"),
    GENERATE__USER_GROUP("8. Сгенерировать список групп пользователя"),

    // RegistrationCodes
    INVITE_CODE_STATUS("Введите статус инвайт-кода: "),
    EMAIL("Введите email: "),
    INVITE_CODE("Введите существующий инвайт-код: "),
    // Users
    USERNAME("Введите username: "),
    PASSWORD("Введите password: "),
    USER_ID("Введите id пользователя: "),
    STUDYING_STATUS("Введите статус пользователя: "),
    FIRST_NAME("Введите имя пользователя: "),
    LAST_NAME("Введите фамилию пользователя: "),
    GENDER("Введите пол пользователя: "),
    DATE_OF_BIRTH("Введите дату рождения пользователя"),
    //UserStudying
    USER_GROUP("Введите группу пользователя"),
    USER_ROLE("Введите роль пользователя");


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
