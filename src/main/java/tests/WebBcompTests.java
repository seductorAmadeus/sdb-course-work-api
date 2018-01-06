package tests;

import daoImpl.*;
import entities.*;
import operations.JedisOperations;
import operations.UsersOperations;
import org.junit.Test;
import utils.CachePrefixType;
import utils.DataReader;
import utils.HibernateUtil;
import utils.RandomInviteCodesGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebBcompTests {

    private static final int TESTS_COUNT = 20;

    public static void main(String[] args) {
        WebBcompTests test = new WebBcompTests();
        test.testSynchronize();
//        test.dropAllTables();
//        test.createRegistrationCodes();
//        test.createUsers();
        HibernateUtil.getSessionFactory().close();
    }

    private void testSynchronize() {
        UsersOperations usersOperations = new UsersOperations();
        usersOperations.synchronize();
    }

    @Test
    public void testRegexPattern() {
        System.out.println(DataReader.readUserGroup());
    }

    private List<RegistrationCodes> getRegistrationCodesList() {
        RandomInviteCodesGenerator randomInviteCodesGenerator = new RandomInviteCodesGenerator();
        List<RegistrationCodes> registrationCodes = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            registrationCodes.add(new RegistrationCodes(randomInviteCodesGenerator.getInviteCode(), "available", "test" + i + "@mail.ru"));
        }
        return registrationCodes;
    }

    private List<RegistrationCodes> getOldRegistrationCodesList() {
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();
        List<RegistrationCodes> registrationCodes = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            registrationCodes.add(registrationCodesDAO.getAvailableCode());
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

    private List<BcompSettings> getBcompSettings() {
        List<BcompSettings> bcompSettingsList = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            bcompSettingsList.add(new BcompSettings("value" + i, "type" + i));
        }
        return bcompSettingsList;
    }

    private List<Bcomp> getBcompsList(List<UserSession> userSessionList) {
        List<Bcomp> bcompList = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            bcompList.add(new Bcomp(userSessionList.get(i), null, null, null, null,
                    null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null,
                    null, null, null));
        }
        return bcompList;
    }

    private void createRegistrationCodes() {
        JedisOperations jedisOperations = new JedisOperations();
        // Заполняем registration_codes
        List<RegistrationCodes> registrationCodesList = getRegistrationCodesList();
        // Добавляем в БД
        RegistrationCodesDAOImpl dao = new RegistrationCodesDAOImpl();
        for (RegistrationCodes registrationCodes : registrationCodesList) {
            BigDecimal regCodeId = dao.create(registrationCodes);
            registrationCodes.setRegCodeId(regCodeId);
            jedisOperations.set(CachePrefixType.REGISTRATION_CODES.toString() + registrationCodes.getRegCodeId(), registrationCodes.toString());
        }
    }

    private void createUsers() {
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        // Генерируем общую роль
        UserRoleDAOImpl userRoleDAO = new UserRoleDAOImpl();
        UserRole userRole = new UserRole();
        userRoleDAO.generateAllUsersRoles();
        // set all roles as "admin"
        userRole.setId(userRoleDAO.addAdminRole());

        // Генерируем общий userStudying id
        // инициализировали UserStudyingId
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();
        UserStudying userStudying = new UserStudying();
        userStudyingDAO.generateAllUsersGroups();
        String userGroupStr = "P3101";
        userStudying.setId(userStudyingDAO.addGroupToUser(userGroupStr));

        List<UserProfile> userProfiles = getProfilesList(userRole, userStudying);
        List<Users> users = getUsersList(getOldRegistrationCodesList(), userProfiles);
        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                users.get(i).setUserProfile(userProfiles.get(i));
                usersDAO.create(users.get(i));
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }
    }

    @Test
    public void fillAllTables() {

        /*
          создаем сессии в БД на основе последнего пользователя, используя рандомайзер (доделать)
         */
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
//                userSessionDAO.create(users.get((int)
//                        Math.random() * (users.size() - 1 - users.get(0).getUserId().intValue())).getUserId());
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }

        /*
          создаем BCOMP-ы
         */
        // получаем список пользовательских сессий
        UserSessionDAOImpl userSessionDAO1 = new UserSessionDAOImpl();
        List<UserSession> userSessionList = userSessionDAO1.getList();
        // добавляем bcomp's
        List<Bcomp> bcompList = getBcompsList(userSessionList);
        BcompDAOImpl bcompDAO = new BcompDAOImpl();

        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                bcompDAO.create(bcompList.get(i));
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }

        /*
          создаем BCOMP settings
         */
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();
        List<BcompSettings> bcompSettingsList = getBcompSettings();

        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                bcompSettingsDAO.create(bcompSettingsList.get(i));
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }

        /*
          ассоциируем настройки с userSessionId
         */

        List<BcompSettings> newBcompSettingsList = bcompSettingsDAO.getList();
        List<UserSession> newUserSessionList = userSessionDAO.getList();
        // TODO: add a check of existence in the table sessionSettings
        SessionSettingsDAOImpl sessionSettingsDAO = new SessionSettingsDAOImpl();
        for (int i = newBcompSettingsList.size(); i > newBcompSettingsList.size() - TESTS_COUNT; i--) {
            try {
                sessionSettingsDAO.assignUserSettings(newUserSessionList.get(i - 1), newBcompSettingsList.get(i - 1));
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }

        HibernateUtil.getSessionFactory().close();
    }
}
