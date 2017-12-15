package utils;

public enum MenuInputType {

    // HibernateOperationsManager
    ENTER_REGISTRATION_CODES("1. Добавить регистрационный код\n"),
    PRINT_REGISTRATION_CODES("2. Выдать все регистрационные коды\n"),


    // RegistrationCodes
    INVITE_CODE_STATUS("Введите статус инвайт-кода: \n"),
    EMAIL("Введите email: \n");

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
