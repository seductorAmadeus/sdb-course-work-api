package operations;

import daos.RegistrationCodesDAO;
import daos.UserRoleDAO;
import daos.UserStudyingDAO;
import daos.UsersDAO;
import entities.*;
import utils.DataReader;

import java.math.BigDecimal;

public class UsersOperations {
    // TODO: add user not found exception
    public void addNewUser() {
        RegistrationCodesDAO registrationCodesDAO = new RegistrationCodesDAO();
        UsersDAO dao = new UsersDAO();
        UserRoleDAO userRoleDAO = new UserRoleDAO();
        RegistrationCodes registrationCode = null;
        try {
            registrationCode = registrationCodesDAO.findFreeRegistrationCode();
        } catch (NullPointerException exp) {
            System.out.println("Отсутствует свободный инвайт-код в системе. Сгенирируйте новый.");
            return;
        }
        Users user = DataReader.readUser();
        user.setInviteCode(registrationCode);

        // инициализируем все поля, крое user_role_id и user_studying_id
        UserProfile userProfile = DataReader.readUserProfile();

        // получаем и инициализируем поле user_role_id
        UserRole userRole = new UserRole();
        userRoleDAO.generateAllUsersRoles();
        String userRoleStr = DataReader.readUserRole();
        switch (userRoleStr) {
            case "root":
                userRole.setId(userRoleDAO.addRootRole());
                // проверить, существует ли такая сущность с правами, иначе сгенерировать
                userProfile.setUserRoleId(userRole);
                break;
            case "admin":
                userRole.setId(userRoleDAO.addAdminRole());
                userProfile.setUserRoleId(userRole);
                break;
            case "teacher":
                userRole.setId(userRoleDAO.addTeacherRole());
                userProfile.setUserRoleId(userRole);
                break;
            case "stud":
                userRole.setId(userRoleDAO.addStudRole());
                userProfile.setUserRoleId(userRole);
                break;
        }

        // получаем и инициализируем поле user_studying_id
        UserStudyingDAO userStudyingDAO = new UserStudyingDAO();
        UserStudying userStudying = new UserStudying();
        String userGroupStr = DataReader.readUserGroup();
        switch (userGroupStr) {
            case "P3101":
            case "P3100":
            case "P3102":
            case "P3110":
            case "P3111":
                userStudying.setId(userStudyingDAO.addGroupToUser(userGroupStr));
        }

        userProfile.setUserStudyingId(userStudying);
        dao.addUser(user, userProfile);

    }

    public void deleteUser() {
        UsersDAO dao = new UsersDAO();
        BigDecimal userId = DataReader.readUserId();
        dao.deleteUser(userId);
    }
}
