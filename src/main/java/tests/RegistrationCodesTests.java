package tests;

import daos.RegistrationCodesDAO;
import entities.RegistrationCodes;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.ArrayList;
import java.util.List;

public class RegistrationCodesTests {

    private static final int TEST_COUNT = 50;

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(RegistrationCodesTests.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }

    @Test
    public void testAllOperations() {
        RegistrationCodesDAO dao = new RegistrationCodesDAO();

        // Генерируем значения
        List<RegistrationCodes> list = new ArrayList<>();
        for (int i = 0; i < TEST_COUNT; i++) {
            list.add(new RegistrationCodes(i % 2 == 0 ? "available" : "not available", "test" + i + "@mail.ru"));
        }

        // Добавляем в БД
        for (RegistrationCodes registrationCodes : list) {
            dao.addRegistrationCode(registrationCodes);
        }

        // Получаем значения из БД
        List<RegistrationCodes> printedList = dao.listRegistrationCodes();
        for (RegistrationCodes registrationCodes : printedList) {
            System.out.println(registrationCodes);
        }

        // Обновляем статусы
        for (RegistrationCodes registrationCodes : printedList) {
            dao.updateRegistrationCodeStatus(registrationCodes.getInviteCode(), registrationCodes.getInviteCodeStatus()
                    .equals("available") ? "not available" : "available");
        }


        // Получаем значения из БД
        List<RegistrationCodes> printedList2 = dao.listRegistrationCodes();
        for (RegistrationCodes registrationCodes : printedList2) {
            System.out.println(registrationCodes);
        }

    }
}
