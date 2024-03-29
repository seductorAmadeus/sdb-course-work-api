package operations;

import daoImpl.RegistrationCodesDAOImpl;
import daoImpl.UserProfileDAOImpl;
import daoImpl.UserRoleDAOImpl;
import daoImpl.UserStudyingDAOImpl;
import entities.*;
import enums.CachePrefixType;
import enums.MenuInputType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class UserProfileOperations extends DatabaseGenericOperations {
    @Override
    public void printAll() {
        UserProfileDAOImpl userProfileDAO = new UserProfileDAOImpl();

        List<UserProfile> userProfileList = userProfileDAO.getList();
        if (userProfileList != null) {
            for (UserProfile userProfile : userProfileList) {
                System.out.println(userProfile);
            }
        } else {
            System.out.println("User's profiles list is empty. Check it out correctly and try again");
        }

    }

    @Override
    public void print() {
        UserProfileDAOImpl userProfileDAO = new UserProfileDAOImpl();
        UserProfile userProfile;
        BigDecimal userProfileId = DataReader.readId(UserProfile.class, "profileId", MenuInputType.USER_PROFILE_ID);

        if (userProfileDAO.isExists(UserProfile.class, userProfileId)) {
            userProfile = userProfileDAO.get(userProfileId);
            System.out.println(userProfile);
        } else {
            System.out.println("The specified user's profile id is not created in the system. Check it out correctly and try again");
        }
    }

    @Override
    public void update() {
        UserProfile userProfile;
        UserProfileDAOImpl userProfileDAO = new UserProfileDAOImpl();
        BigDecimal userProfileId = DataReader.readId(UserProfile.class, "profileId", MenuInputType.USER_PROFILE_ID);

        if (userProfileDAO.isExists(UserProfile.class, userProfileId)) {
            userProfile = userProfileDAO.get(userProfileId);
            DataReader.initUserProfile(userProfile);
            userProfileDAO.update(userProfile);
        } else {
            System.out.println("The specified user profile id was not found in the system. Check it out correctly and try again");
        }
    }

    @Override
    public void delete() {
        UserProfileDAOImpl userProfileDAO = new UserProfileDAOImpl();
        BigDecimal userProfileId = DataReader.readId(UserProfile.class, "profileId", MenuInputType.USER_PROFILE_ID);

        if (userProfileDAO.isExists(UserProfile.class, userProfileId)) {
            userProfileDAO.delete(UserProfile.class, userProfileId);
        } else {
            System.out.println("The specified user's profile id was not found in the Redis cache. Check it out correctly and try again");
        }
    }

    // TODO: change it!
    @Override
    public void create() {
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();
        UserProfileDAOImpl dao = new UserProfileDAOImpl();
        UserRoleDAOImpl userRoleDAO = new UserRoleDAOImpl();
        RegistrationCodes registrationCode;
        try {
            registrationCode = registrationCodesDAO.getAvailableCode();
        } catch (NullPointerException exp) {
            System.out.println("No available invite code found. Generate additional codes.");
            return;
        }
        Users user = DataReader.readUser();
        user.setRegistrationCode(registrationCode);

        // инициализируем все поля, крое user_role_id и user_studying_id
        UserProfile userProfile = DataReader.readUserProfile();

        // получаем и инициализируем поле user_role_id
        UserRole userRole = new UserRole();
        userRoleDAO.generateAllUsersRoles();
        String userRoleStr = DataReader.readUserRole();
        switch (userRoleStr) {
            case "root":
                userRole.setId(userRoleDAO.getRootRoleId());
                break;
            case "admin":
                userRole.setId(userRoleDAO.getAdminRoleId());
                break;
            case "teacher":
                userRole.setId(userRoleDAO.getTeacherRoleId());
                break;
            case "stud":
                userRole.setId(userRoleDAO.getStudRoleId());
                break;
        }
        userProfile.setUserRoleId(userRole);

        // получаем и инициализируем поле user_studying_id
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();
        UserStudying userStudying = new UserStudying();
        String userGroupStr = DataReader.readUserGroup();
        switch (userGroupStr) {
            case "P3101":
            case "P3100":
            case "P3102":
            case "P3110":
            case "P3111":
                userStudying.setId(userStudyingDAO.getIdByUserGroup(userGroupStr));
        }
        userProfile.setUserStudyingId(userStudying);

        userProfile.setUsers(user);
        dao.create(userProfile);
    }

    @Override
    public void jPrintAll() {
        JedisOperations jedisOperations = new JedisOperations();
        UserProfileDAOImpl userProfileDAO = new UserProfileDAOImpl();
        List<String> records;
        List<UserProfile> userProfileList;

        records = jedisOperations.getAllRecordsMatchingPattern(CachePrefixType.USER_PROFILE + "*");
        if (records.size() == 0) {
            System.out.println("User profiles list is empty. No user's profile has been created/added in the Redis cache");
        } else {
            for (String record : records) {
                System.out.println(record);
            }
        }
        userProfileList = userProfileDAO.getList();
        if (userProfileList.size() != 0) {
            for (UserProfile userProfile : userProfileList) {
                if (!jedisOperations.isExists(CachePrefixType.USER_PROFILE.toString(), userProfile.getProfileId())) {
                    // set in the Redis cache
                    jedisOperations.set(CachePrefixType.USER_PROFILE.toString() + userProfile.getProfileId(), userProfile.toString());
                    // and output the record with the missing key in the Redis cache.
                    System.out.println(userProfile);
                }
            }
        } else {
            System.out.println("User profiles list is empty. No user's profile has been created/added in the Oracle DB");
        }
    }

    @Override
    public void jPrint() {
        JedisOperations jedisOperations = new JedisOperations();
        BigDecimal userProfileId = DataReader.readId(UserProfile.class, "profileId", MenuInputType.USER_PROFILE_ID);
        UserProfileDAOImpl userProfileDAO = new UserProfileDAOImpl();

        if (jedisOperations.isExists(CachePrefixType.USER_PROFILE.toString(), userProfileId)) {
            String jUserProfile = jedisOperations.get(CachePrefixType.USER_PROFILE.toString() + userProfileId);
            System.out.println(jUserProfile);
        } else if (userProfileDAO.isExists(UserProfile.class, userProfileId)) {
            UserProfile userProfile = userProfileDAO.get(userProfileId);
            System.out.println(userProfile);
            jedisOperations.set(CachePrefixType.USER_PROFILE.toString() + userProfile.getProfileId(), userProfile.toString());
        } else {
            System.out.println("The entry was not found in the Redis cache and in the Oracle database.");
        }
    }


    @Override
    public void jUpdate() {
        JedisOperations jedisOperations = new JedisOperations();
        UserProfile userProfile;
        UserProfileDAOImpl userProfileDAO = new UserProfileDAOImpl();
        BigDecimal userProfileId = DataReader.readId(UserProfile.class, "profileId", MenuInputType.USER_PROFILE_ID);

        if (userProfileDAO.isExists(UserProfile.class, userProfileId)) {
            userProfile = userProfileDAO.get(userProfileId);
            DataReader.initUserProfile(userProfile);
            userProfileDAO.update(userProfile);
            jedisOperations.set(CachePrefixType.USER_PROFILE.toString() + userProfile.getProfileId(), userProfile.toString());
        } else {
            System.out.println("The specified user profile id was not found in the system. Check it out correctly and try again");
        }
    }

    public void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        UserProfileDAOImpl userProfileDAO = new UserProfileDAOImpl();

        BigDecimal userProfileId = DataReader.readId(UserProfile.class, "profileId", MenuInputType.USER_PROFILE_ID);

        if (userProfileDAO.isExists(UserProfile.class, userProfileId)) {
            userProfileDAO.delete(UserProfile.class, userProfileId);
            System.out.println("The entry was successfully deleted from the database");
        } else {
            System.out.println("The entry was not found in the Database.");
        }

        if (jedisOperations.isExists(CachePrefixType.USER_PROFILE.toString(), userProfileId)) {
            jedisOperations.delete(CachePrefixType.USER_PROFILE.toString() + userProfileId);
            System.out.println("The entry was successfully deleted from the Redis cache");
        } else {
            System.out.println("The entry was not found in the Redis cache.");
        }
    }

    @Override
    public void jCreate() {
        JedisOperations jedisOperations = new JedisOperations();

        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();
        UserProfileDAOImpl dao = new UserProfileDAOImpl();
        UserRoleDAOImpl userRoleDAO = new UserRoleDAOImpl();
        RegistrationCodes registrationCode;
        try {
            registrationCode = registrationCodesDAO.getAvailableCode();
        } catch (NullPointerException exp) {
            System.out.println("No available invite code found. Generate additional codes.");
            return;
        }
        Users user = DataReader.readUser();
        user.setRegistrationCode(registrationCode);

        // инициализируем все поля, крое user_role_id и user_studying_id
        UserProfile userProfile = DataReader.readUserProfile();

        // получаем и инициализируем поле user_role_id
        UserRole userRole = new UserRole();
        userRoleDAO.generateAllUsersRoles();
        String userRoleStr = DataReader.readUserRole();
        switch (userRoleStr) {
            case "root":
                userRole.setId(userRoleDAO.getRootRoleId());
                break;
            case "admin":
                userRole.setId(userRoleDAO.getAdminRoleId());
                break;
            case "teacher":
                userRole.setId(userRoleDAO.getTeacherRoleId());
                break;
            case "stud":
                userRole.setId(userRoleDAO.getStudRoleId());
                break;
        }
        userProfile.setUserRoleId(userRole);

        // получаем и инициализируем поле user_studying_id
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();
        UserStudying userStudying = new UserStudying();
        String userGroupStr = DataReader.readUserGroup();
        switch (userGroupStr) {
            case "P3101":
            case "P3100":
            case "P3102":
            case "P3110":
            case "P3111":
                userStudying.setId(userStudyingDAO.getIdByUserGroup(userGroupStr));
        }
        userProfile.setUserStudyingId(userStudying);

        userProfile.setUsers(user);
        BigDecimal userProfileId = dao.create(userProfile);

        if (userProfileId != null) {
            jedisOperations.set(CachePrefixType.USER_PROFILE.toString() + userProfile.getProfileId(), userProfile.toString());
        }
    }
}
