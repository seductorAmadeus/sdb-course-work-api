package tests;

import daoImpl.*;
import entities.*;
import enums.CachePrefixType;
import operations.JedisOperations;
import operations.UsersOperations;
import utils.HibernateUtil;
import utils.RandomInviteCodesGenerator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class WebBcompTests {

    private static final int TESTS_COUNT = 20;

    public static void main(String[] args) {
        WebBcompTests webBcompTests = new WebBcompTests();
        webBcompTests.createAllTables(webBcompTests);
        //test.testConstraintsViolation();
        //test.testSynchronize();
        //test.dropAllTables();
    }

    public void createAllTables(WebBcompTests webBcompTests) {
        webBcompTests.createRegistrationCodes();
        webBcompTests.createUsers();
        webBcompTests.createUserPictures();
        webBcompTests.createUserSession();
        webBcompTests.createBcomps();
        webBcompTests.createBcompSettings();
        webBcompTests.createSessionSettings();
        HibernateUtil.getSessionFactory().close();
    }

    private void testSynchronize() {
        UsersOperations usersOperations = new UsersOperations();
        usersOperations.synchronize();
    }

    private void testConstraintsViolation() {
        RegistrationCodes registrationCodes = new RegistrationCodes(new BigDecimal(23), "availaeble", "maaf@mail.ru");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<RegistrationCodes>> errors = validator.validateProperty(registrationCodes, "inviteCodeStatus");

        if (!errors.isEmpty()) {
            for (ConstraintViolation<RegistrationCodes> constraintViolation : errors) {

                System.out.println(constraintViolation.getPropertyPath() + " -> " +
                        constraintViolation.getMessage());

            }
        }
    }

    private List<RegistrationCodes> getRegistrationCodesList() {
        RandomInviteCodesGenerator randomInviteCodesGenerator = new RandomInviteCodesGenerator();
        List<RegistrationCodes> registrationCodes = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            registrationCodes.add(new RegistrationCodes(randomInviteCodesGenerator.getInviteCode(), "available", "test" + i + "@mail.ru"));
        }
        return registrationCodes;
    }

    private List<RegistrationCodes> getAvailableCodesList() {
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
            userProfiles.add(new Users(registrationCodes.get(i), "loginlogin" + i, "passwordpassword" + i, null, userProfile.get(i)));
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
                    "YES", "Martin" + i, "Rayla" + i, null,
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

    private List<UserPicture> getUserPicturesList() {
        List<UserPicture> userPictureList = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            userPictureList.add(new UserPicture("picturename" + i, null, null));
        }
        return userPictureList;
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
        JedisOperations jedisOperations = new JedisOperations();
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        List<UserProfile> userProfiles = getProfilesList(getUserRole(), getUserStudying());
        List<Users> users = getUsersList(getAvailableCodesList(), userProfiles);
        // TODO: изменить цикл на пробег по пользователям
        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                users.get(i).setUserProfile(userProfiles.get(i));
                BigDecimal userId = usersDAO.create(users.get(i));
                users.get(i).setUserId(userId);
                jedisOperations.set(CachePrefixType.USERS.toString() + users.get(i).getUserId(), users.get(i).toString());
                jedisOperations.set(CachePrefixType.USER_PROFILE.toString() + users.get(i).getUserProfile().getProfileId(), users.get(i).getUserProfile().toString());
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }
    }

    private void createUserSession() {
        JedisOperations jedisOperations = new JedisOperations();
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UsersDAOImpl usersDAO = new UsersDAOImpl();
        List<Users> users = usersDAO.getList();

        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                UserSession userSession = new UserSession();
                userSession.setUserId(users.get((int) Math.random() * (users.size() - 1 - users.get(0).getUserId().intValue())));
                BigDecimal userSessionId = userSessionDAO.create(userSession);
                userSession.setId(userSessionId);
                jedisOperations.set(CachePrefixType.USER_SESSION.toString() + userSession.getId(), userSession.toString());
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }
    }

    private void createBcomps() {
        JedisOperations jedisOperations = new JedisOperations();
        UserSessionDAOImpl userSessionDAO1 = new UserSessionDAOImpl();
        List<UserSession> userSessionList = userSessionDAO1.getList();
        List<Bcomp> bcompList = getBcompsList(userSessionList);
        BcompDAOImpl bcompDAO = new BcompDAOImpl();

        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                BigDecimal bcompId = bcompDAO.create(bcompList.get(i));
                bcompList.get(i).setId(bcompId);
                jedisOperations.set(CachePrefixType.BCOMP.toString() + bcompList.get(i).getId(), bcompList.get(i).toString());
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }
    }

    private void createBcompSettings() {
        JedisOperations jedisOperations = new JedisOperations();
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();
        List<BcompSettings> bcompSettingsList = getBcompSettings();

        for (int i = 0; i < TESTS_COUNT; i++) {
            try {
                BigDecimal bcompSettingsId = bcompSettingsDAO.create(bcompSettingsList.get(i));
                bcompSettingsList.get(i).setId(bcompSettingsId);
                jedisOperations.set(CachePrefixType.BCOMP_SETTINGS.toString() + bcompSettingsList.get(i).getId(), bcompSettingsList.get(i).toString());
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }
    }

    private void createSessionSettings() {
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();

        List<BcompSettings> newBcompSettingsList = bcompSettingsDAO.getList();
        List<UserSession> newUserSessionList = userSessionDAO.getList();
        SessionSettingsDAOImpl sessionSettingsDAO = new SessionSettingsDAOImpl();
        for (int i = newBcompSettingsList.size(); i > newBcompSettingsList.size() - TESTS_COUNT; i--) {
            try {
                sessionSettingsDAO.assignUserSettings(newUserSessionList.get(i - 1), newBcompSettingsList.get(i - 1));
            } catch (NullPointerException exp) {
                exp.getMessage();
            }
        }
    }

    private void createUserPictures() {
        JedisOperations jedisOperations = new JedisOperations();

        UserProfileDAOImpl userProfileDAO = new UserProfileDAOImpl();
        UserPictureDAOImpl userPictureDAO = new UserPictureDAOImpl();
        List<UserProfile> userProfileList = userProfileDAO.getList();

        for (UserProfile userProfile : userProfileList) {

            if (!userPictureDAO.isExists(UserPicture.class, userProfile.getProfileId())) {
                UserPicture userPicture = new UserPicture();
                userPicture.setId(userProfile.getProfileId());
                userPicture.setPictureName("pictureName" + userPicture.getId());
                userPictureDAO.create(userPicture);
                jedisOperations.set(CachePrefixType.USER_PICTURE.toString() + userPicture.getId(), userPicture.toString());
            }
        }
    }

    private UserRole getUserRole() {
        JedisOperations jedisOperations = new JedisOperations();

        UserRoleDAOImpl userRoleDAO = new UserRoleDAOImpl();
        UserRole userRole = new UserRole();
        userRoleDAO.generateAllUsersRoles();
        // set all roles as "admin"
        userRole.setId(userRoleDAO.getAdminRoleId());

        List<UserRole> userRoles = userRoleDAO.getList();
        for (UserRole temp : userRoles) {
            jedisOperations.set(CachePrefixType.USER_ROLE.toString() + temp.getId(), temp.toString());
        }
        return userRole;
    }

    private UserStudying getUserStudying() {
        JedisOperations jedisOperations = new JedisOperations();
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();
        UserStudying userStudying = new UserStudying();
        userStudyingDAO.generateAllUsersGroups();
        String userGroupStr = "P3101";
        userStudying.setId(userStudyingDAO.getIdByUserGroup(userGroupStr));

        List<UserStudying> userStudyings = userStudyingDAO.getList();
        for (UserStudying temp : userStudyings) {
            jedisOperations.set(CachePrefixType.USER_STUDYING.toString() + temp.getId(), temp.toString());
        }
        return userStudying;
    }
}
