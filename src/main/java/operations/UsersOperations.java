package operations;

import daoImpl.RegistrationCodesDAOImpl;
import daoImpl.UserRoleDAOImpl;
import daoImpl.UserStudyingDAOImpl;
import daoImpl.UsersDAOImpl;
import entities.*;
import enums.CachePrefixType;
import enums.MenuInputType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class UsersOperations extends DatabaseGenericOperations {

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

        user.setUserProfile(userProfile);
        dao.create(user);

    }

    @Override
    void printAll() {

    }

    @Override
    void print() {

    }

    @Override
    void update() {

    }

    @Override
    void delete() {

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
        BigDecimal userId = DataReader.readId(Users.class, "userId", MenuInputType.USER_ID);
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        if (jedisOperations.isExists(CachePrefixType.USERS.toString(), userId)) {
            String jUser = jedisOperations.get(CachePrefixType.USERS.toString() + userId);
            System.out.println(jUser);
        } else if (usersDAO.isExists(Users.class, userId)) {
            Users users = usersDAO.get(userId);
            System.out.println(users);
            jedisOperations.set(CachePrefixType.USERS.toString() + users.getUserId(), users.toString());
        } else {
            System.out.println("The entry was not found in the Redis cache and in the Oracle database.");
        }
    }

    @Override
    public void jUpdate() {
        JedisOperations jedisOperations = new JedisOperations();

        Users user, tempUser;
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        BigDecimal userId = DataReader.readId(Users.class, "userId", MenuInputType.USER_ID);

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

    public void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        BigDecimal userId = DataReader.readId(Users.class, "userId", MenuInputType.USER_ID);

        if (usersDAO.isExists(Users.class, userId)) {
            usersDAO.delete(Users.class, userId);
            System.out.println("The entry was successfully deleted from the database");
        } else {
            System.out.println("The entry was not found in the Database.");
        }

        if (jedisOperations.isExists(CachePrefixType.USERS.toString(), userId)) {
            jedisOperations.delete(CachePrefixType.USERS.toString() + userId);
            System.out.println("The entry was successfully deleted from the Redis cache");
        } else {
            System.out.println("The entry was not found in the Redis cache.");
        }
    }

    public void synchronize() {
        JedisOperations jedisOperations = new JedisOperations();
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        List<String> keysList = jedisOperations.getAllKeys(CachePrefixType.USERS.toString() + "*");
        // удаляем лишние записи в Redis, если таковые отсутствуют в БД
        for (String aKeysList : keysList) {
            String temStrId = aKeysList.substring(aKeysList.lastIndexOf(":") + 1);
            BigDecimal tempId = new BigDecimal(temStrId);
            if (!usersDAO.isExists(Users.class, tempId)) {
                jedisOperations.delete(aKeysList);
            }
        }

        // добавляем отсутствующие записи в Redis из Oracle-а.
        List<Users> users = usersDAO.getList();

        for (Users user : users) {
            jedisOperations.set(CachePrefixType.USERS.toString() + user.getUserId(), user.toString());
        }

    }

    @Override
    public void jCreate() {
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

        user.setUserProfile(userProfile);

        BigDecimal userId = dao.create(user);

        if (userId != null) {
            user.setUserId(userId);

            jedisOperations.set(CachePrefixType.USERS.toString() + user.getUserId(), user.toString());
            jedisOperations.set(CachePrefixType.USER_PROFILE.toString() + user.getUserProfile().getProfileId(), userProfile.toString());
        }

    }
}
