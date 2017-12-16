package tests;

import daos.RegistrationCodesDAO;
import daos.UserRoleDAO;
import daos.UserStudyingDAO;
import daos.UsersDAO;
import entities.*;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        // добавляем пользователя в БД
        RegistrationCodesDAO registrationCodesDAO = new RegistrationCodesDAO();
        // инициируем пользователя
        UsersDAO usersDAO = new UsersDAO();
        Users users = new Users();
        RegistrationCodes registrationCode = null;
        try {
            registrationCode = registrationCodesDAO.findFreeRegistrationCode();
        } catch (NullPointerException exp) {
            System.out.println("No available invite code found. Generate additional codes.");
            return;
        }
        users.setUsername("21412412");
        users.setPassword("afawf");
        users.setInviteCode(registrationCode);

        UserProfile userProfile = new UserProfile();
        try {
            String dateOfBirth = "1999-01-01";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(dateOfBirth);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            userProfile.setDateOfBirth(timestamp);
        } catch (Exception exp) {
            exp.getMessage();
        }

        userProfile.setGender("M");
        userProfile.setLastName("Rayla");
        userProfile.setFirstName("Martin");
        userProfile.setStudyingStatus("YES");

        // инициализировали UserRoleId
        UserRole userRole = new UserRole();
        UserRoleDAO userRoleDAO = new UserRoleDAO();
        userRoleDAO.generateAllUsersRoles();
        userRole.setId(userRoleDAO.addTeacherRole());

        userProfile.setUserRoleId(userRole);

        // инициализировали UserStudyingId
        UserStudyingDAO userStudyingDAO = new UserStudyingDAO();
        userStudyingDAO.generateAllUsersGroups();
        UserStudying userStudying = new UserStudying();
        String userGroupStr = "P3101";
        userStudying.setId(userStudyingDAO.addGroupToUser(userGroupStr));

        userProfile.setUserStudyingId(userStudying);

        users.setProfile(userProfile);
        userProfile.setUsers(users);

        usersDAO.addUser(users, userProfile);
    }
}
