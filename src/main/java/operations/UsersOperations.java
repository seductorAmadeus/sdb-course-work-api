package operations;

import daoImpl.*;
import entities.*;
import utils.CachePrefixType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class UsersOperations implements RedisGenericOperations {

    public void addUser() {
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();
        UsersDAOImpl dao = new UsersDAOImpl();
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
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();
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
        UsersDAOImpl dao = new UsersDAOImpl();
        BigDecimal userId = DataReader.readUserId();
        dao.delete(Users.class, userId);
    }


    public void updateUserProfile() {
        UserProfile userProfile;
        UserProfileDAOImpl userProfileDAO = new UserProfileDAOImpl();
        BigDecimal userProfileId = DataReader.readUserProfileId();

        if (userProfileDAO.isExists(UserProfile.class, userProfileId)) {
            userProfile = userProfileDAO.get(userProfileId);
            DataReader.initUserProfile(userProfile);
            userProfileDAO.update(userProfile);
        } else {
            System.out.println("The specified user profile id was not found in the system. Check it out correctly and try again");
        }
    }

    @Override
    public void jPrintAll() {
        JedisOperations jedisOperations = new JedisOperations();
        List<String> records;
        try {
            records = jedisOperations.getAllRecordsMatchingPattern(CachePrefixType.USERS + "*");
            if (records.size() == 0) {
                throw new NullPointerException();
            } else {
                for (String record : records) {
                    System.out.println(record);
                }
            }
        } catch (NullPointerException exp) {
            System.out.println("Users' list is empty. No bcomp has been created/added in Redis cache");
        }
    }

    @Override
    public void jPrint() {
        JedisOperations jedisOperations = new JedisOperations();

        BigDecimal userId = DataReader.readUserId();

        String users = jedisOperations.get(CachePrefixType.USERS.toString() + userId);

        if (users != null) {
            System.out.println(users);
        } else {
            System.out.println("The specified user id was not found in the Redis cache. Check it out correctly and try again");
        }
    }

    @Override
    public void jUpdate() {
        JedisOperations jedisOperations = new JedisOperations();

        Users user, tempUser;
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        BigDecimal userId = DataReader.readUserId();

        if (usersDAO.isExists(Users.class, userId)) {
            user = usersDAO.get(userId);
            tempUser = DataReader.readUser();
            //TODO: доработать метод иницииации существующей сущности в БД
            //TODO: change this temp action
            // инициируем необходимые поля
            user.setPassword(tempUser.getPassword());
            user.setUsername(tempUser.getUsername());

            usersDAO.update(user);
            // TODO: add exception checking?
            // необходимо ли проверять существует ли, может быть просто добавится новое значение
            jedisOperations.set(CachePrefixType.USERS.toString() + user.getUserId(), user.toString());
        } else {
            System.out.println("The specified user id was not found in the system. Check it out correctly and try again");
        }
    }

    @Override
    public void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        BigDecimal userId = DataReader.readUserId();

        // TODO: удалить несовпадение проверок
        if (usersDAO.isExists(Users.class, userId)) {
            usersDAO.delete(Users.class, userId);
            // TODO: add exception checking
            // TODO: добавить полную проверку зависимостей с другими пользователями
            jedisOperations.delete(CachePrefixType.USERS.toString() + userId);
        } else {
            System.out.println("The specified user id was not found in the Redis cache. Check it out correctly and try again");
        }
    }

    @Override
    public void jAdd() {
        JedisOperations jedisOperations = new JedisOperations();
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();
        UsersDAOImpl dao = new UsersDAOImpl();
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
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();
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

        BigDecimal userId = dao.addUser(user, userProfile);

        if (userId != null) {
            user.setUserId(userId);

            jedisOperations.set(CachePrefixType.USERS.toString() + user.getUserId(), user.toString());
            jedisOperations.set(CachePrefixType.USER_PROFILE.toString() + user.getUserProfile().getProfileId(), userProfile.toString());
        }

    }
}
