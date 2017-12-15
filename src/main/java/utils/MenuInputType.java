package utils;

public enum MenuInputType {

    // HibernateOperationsManager
    ENTER_REGISTRATION_CODES("1. Добавить инвайт-код \n"),
    PRINT_REGISTRATION_CODES("2. Выдать все инвайт-коды \n"),
    UPDATE_REGISTRATION_CODES("3. Обновить статус инвайт-кода \n"),
    DELETE_REGISTRATION_CODES("4. Удалить инвайт-код \n"),
    // RegistrationCodes
    INVITE_CODE_STATUS("Введите статус инвайт-кода: \n"),
    EMAIL("Введите email: \n"),
    INVITE_CODE("Введите существующий инвайт-код");

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
