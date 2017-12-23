package operations;

import daos.RegistrationCodesDAO;
import daos.UserRoleDAO;
import daos.UserStudyingDAO;
import daos.UsersDAO;
import entities.*;
import utils.DataReader;

import java.math.BigDecimal;

public class UsersOperations {

    public void addUser() {
        RegistrationCodesDAO registrationCodesDAO = new RegistrationCodesDAO();
        UsersDAO dao = new UsersDAO();
        UserRoleDAO userRoleDAO = new UserRoleDAO();
        RegistrationCodes registrationCode = null;
        try {
            registrationCode = registrationCodesDAO.findFreeRegistrationCode();
        } catch (NullPointerException exp) {
            System.out.println("No available invite code found. Generate additional codes.");
            return;
        }
        Users user = DataReader.readUser();
        user.setRegCodeId(registrationCode);

        // инициализируем все поля, крое user_role_id и user_studying_id
        UserProfile userProfile = DataReader.readUserProfile();

        // получаем и инициализируем поле user_role_id
        UserRole userRole = new UserRole();
        userRoleDAO.generateAllUsersRoles();
        String userRoleStr = DataReader.readUserRole();
        switch (userRoleStr) {
            case "root":
                userRole.setId(userRoleDAO.addRootRole());
                break;
            case "admin":
                userRole.setId(userRoleDAO.addAdminRole());
                break;
            case "teacher":
                userRole.setId(userRoleDAO.addTeacherRole());
                break;
            case "stud":
                userRole.setId(userRoleDAO.addStudRole());
                break;
        }
        userProfile.setUserRoleId(userRole);

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

    // TODO: Check and refactor this method
    public void deleteUser() {
        UsersDAO dao = new UsersDAO();
        BigDecimal userId = DataReader.readUserId();
        dao.deleteUser(userId);
    }
}
