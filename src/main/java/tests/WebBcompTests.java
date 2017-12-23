package tests;

import daos.*;
import entities.*;
import org.junit.Test;
import utils.DataReader;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebBcompTests {

    private static final int TESTS_COUNT = 20;

    public static void main(String[] args) {
        WebBcompTests test = new WebBcompTests();
//        test.dropAllTables();
        test.fillAllTables();
    }

    @Test
    public void testRegexPattern() {
        System.out.println(DataReader.readUserGroup());
    }

    private List<RegistrationCodes> getRegistrationCodesList() {
        List<RegistrationCodes> registrationCodes = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            registrationCodes.add(new RegistrationCodes(i % 2 == 0 ? "available" : "not available", "test" + i + "@mail.ru"));
        }
        return registrationCodes;
    }

    private List<Users> getUsersList(List<RegistrationCodes> registrationCodes, List<UserProfile> userProfile) {
        List<Users> userProfiles = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            userProfiles.add(new Users(registrationCodes.get(i), "login" + i, "password" + i, null, userProfile.get(i)));
        }
        return userProfiles;
    }

    private List<UserProfile> getProfilesList(UserRole userRoleId, UserStudying userStudyingId) {
        Timestamp timestamp = null;
        try {
            String dateOfBirth = "1999-01-01";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(dateOfBirth);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception exp) {
            exp.getMessage();
        }

        List<UserProfile> userProfiles = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            userProfiles.add(new UserProfile(userRoleId, userStudyingId, timestamp, timestamp,
                    "YES", "Martin" + i, "Rayla" + i, "",
                    "M", timestamp, "Y", null, null));
        }
        return userProfiles;
    }


    public List<BcompSettings> getBcompSettings() {
        List<BcompSettings> bcompSettingsList = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            bcompSettingsList.add(new BcompSettings("value" + i, "type" + i));
        }
        return bcompSettingsList;
    }

    private List<Bcomp> getBcompsList(List<UserSession> userSessionList) {
        List<Bcomp> bcompList = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            bcompList.add(new Bcomp(null, userSessionList.get(i), null, null, null, null,
                    null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null,
                    null, null, null));
        }
        return bcompList;
    }

    @Test
    public void dropAllTables() {
        BcompDAO bcompDAO = new BcompDAO();
        bcompDAO.dropAllBcompRecords();

        RegistrationCodesDAO registrationCodesDAO = new RegistrationCodesDAO();
        registrationCodesDAO.dropAllRegistrationCodesRecords();

        UsersDAO usersDAO = new UsersDAO();
        usersDAO.dropAllUsersRecords();

        SessionSettingsDAO sessionSettingsDAO = new SessionSettingsDAO();
        sessionSettingsDAO.dropAllSessionSettingsRecords();


        UserSessionDAO userSessionDAO = new UserSessionDAO();
        userSessionDAO.dropAllUserSessionRecords();

        BcompSettingsDAO bcompSettingsDAO = new BcompSettingsDAO();
        bcompSettingsDAO.dropAllBcompSettingsRecords();



        UserRoleDAO userRoleDAO = new UserRoleDAO();
        userRoleDAO.dropAllUserRoleRecords();




        UserStudyingDAO userStudyingDAO = new UserStudyingDAO();
        userStudyingDAO.dropAllUserStudyingRecords();
    }

    @Test
    public void fillAllTables() {
        UsersDAO usersDAO = new UsersDAO();

        // Заполняем registration_codes
        List<RegistrationCodes> registrationCodesList = getRegistrationCodesList();
        // Добавляем в БД
        RegistrationCodesDAO dao = new RegistrationCodesDAO();
        for (RegistrationCodes registrationCodes : registrationCodesList) {
            dao.addRegistrationCode(registrationCodes);
        }

        /**
         * добавляем пользователей в БД
         */
        // Генерируем общую роль
        UserRoleDAO userRoleDAO = new UserRoleDAO();
        UserRole userRole = new UserRole();
        userRoleDAO.generateAllUsersRoles();
        // set all roles as "admin"
        userRole.setId(userRoleDAO.addAdminRole());

        // Генерируем общий userStudying id
        // инициализировали UserStudyingId
        UserStudyingDAO userStudyingDAO = new UserStudyingDAO();
        UserStudying userStudying = new UserStudying();
        userStudyingDAO.generateAllUsersGroups();
        String userGroupStr = "P3101";
        userStudying.setId(userStudyingDAO.addGroupToUser(userGroupStr));

        List<UserProfile> userProfiles = getProfilesList(userRole, userStudying);
        List<Users> users = getUsersList(registrationCodesList, userProfiles);
        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                usersDAO.addUser(users.get(i), userProfiles.get(i));
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }

        /**
         * создаем сессии в БД на основе последнего пользователя, используя рандомайзер (доделать)
         */
        UserSessionDAO userSessionDAO = new UserSessionDAO();
        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                userSessionDAO.createSession(users.get((int)
                        Math.random() * (users.size() - 1 - users.get(0).getUserId().intValue())).getUserId());
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }

        /**
         * создаем BCOMP-ы
         */
        // получаем список пользовательских сессий
        UserSessionDAO userSessionDAO1 = new UserSessionDAO();
        List<UserSession> userSessionList = userSessionDAO1.listUserSessions();
        // добавляем bcomp's
        List<Bcomp> bcompList = getBcompsList(userSessionList);
        BcompDAO bcompDAO = new BcompDAO();

        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                bcompDAO.createEmptyBcomp(bcompList.get(i));
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }

        /**
         * создаем BCOMP settings
         */
        BcompSettingsDAO bcompSettingsDAO = new BcompSettingsDAO();
        List<BcompSettings> bcompSettingsList = getBcompSettings();

        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                bcompSettingsDAO.addBcompSettings(bcompSettingsList.get(i));
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }

        /**
         * ассоциируем настройки с userSessionId
         */

        List<BcompSettings> newBcompSettingsList = bcompSettingsDAO.getBcompSettingsList();
        List<UserSession> newUserSessionList = userSessionDAO.listUserSessions();

        SessionSettingsDAO sessionSettingsDAO = new SessionSettingsDAO();
        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                sessionSettingsDAO.assignUserSettings(newUserSessionList.get(i), newBcompSettingsList.get(i));
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }

    }
}
