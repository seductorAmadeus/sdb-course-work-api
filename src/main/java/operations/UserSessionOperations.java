package operations;

import daoImpl.UserSessionDAOImpl;
import daoImpl.UsersDAOImpl;
import entities.UserSession;
import entities.Users;
import enums.CachePrefixType;
import enums.MenuInputType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class UserSessionOperations extends DatabaseGenericOperations {

    public void create() {
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        String username = DataReader.readString(Users.class, "username", MenuInputType.USERNAME);
        String password = DataReader.readString(Users.class, "password", MenuInputType.PASSWORD);

        // create new Hibernate session and get user for adding to user's session

        UserSession userSession = new UserSession();

        try {
            // проверяем, существует ли пользователь
            BigDecimal userId = usersDAO.getUserId(username, password);
            // если существует, то
            if (userId != null) {
                // получаем пользователя по id и устанавливаем в сессию, которую добавляем в БД
                Users users = usersDAO.get(userId);
                userSession.setUserId(users);
                userSessionDAO.create(userSession);
            } else {
                throw new NullPointerException();
            }

        } catch (NullPointerException exp) {
            System.out.println("The specified user is not register in the system. Check it out correctly and try again");
        }
    }

    @Override
    public void printAll() {
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();

        List<UserSession> userSessionList = userSessionDAO.getList();
        if (userSessionList != null) {
            for (UserSession userSession : userSessionList) {
                System.out.println(userSession);
            }
        } else {
            System.out.println("User's session list is empty. Check it out correctly and try again");
        }

    }

    @Override
    public void print() {
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UserSession userSession;
        BigDecimal userSessionId = DataReader.readId(UserSession.class, "id", MenuInputType.USER_SESSION_ID);

        if (userSessionDAO.isExists(UserSession.class, userSessionId)) {
            userSession = userSessionDAO.get(userSessionId);
            System.out.println(userSession);
        } else {
            System.out.println("The specified user's session is not created in the system. Check it out correctly and try again");
        }
    }

    @Override
    public void update() {
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UserSession userSession;

        BigDecimal userSessionId = DataReader.readId(UserSession.class, "id", MenuInputType.USER_SESSION_ID);
        String userSessionStatus = DataReader.readUserSessionStatus();

        if (userSessionDAO.isExists(UserSession.class, userSessionId)) {
            userSession = userSessionDAO.get(userSessionId);
            userSession.setStatus(userSessionStatus);
            userSessionDAO.update(userSession);
            System.out.println("Статус обновлен.");
        } else {
            System.out.println("The specified user's session is not created in the system. Check it out correctly and try again");
        }

    }

    @Override
    public void delete() {
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();

        BigDecimal userSessionId = DataReader.readId(UserSession.class, "id", MenuInputType.USER_SESSION_ID);

        if (userSessionDAO.isExists(UserSession.class, userSessionId)) {
            userSessionDAO.delete(UserSession.class, userSessionId);
        } else {
            System.out.println("The specified user's session is not created in the system. Check it out correctly and try again");
        }

    }

    @Override
    public void jPrintAll() {
        JedisOperations jedisOperations = new JedisOperations();
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        List<String> records;
        List<UserSession> userSessionList;

        records = jedisOperations.getAllRecordsMatchingPattern(CachePrefixType.USER_SESSION + "*");
        if (records.size() == 0) {
            System.out.println("User session list is empty. No user's session has been created/added in the Redis cache");
        } else {
            for (String record : records) {
                System.out.println(record);
            }
        }
        userSessionList = userSessionDAO.getList();
        if (userSessionList.size() != 0) {
            for (UserSession userSession : userSessionList) {
                if (!jedisOperations.isExists(CachePrefixType.USER_SESSION.toString(), userSession.getId())) {
                    // set in the Redis cache
                    jedisOperations.set(CachePrefixType.USER_SESSION.toString() + userSession.getId(), userSession.toString());
                    // and output the record with the missing key in the Redis cache.
                    System.out.println(userSession);
                }
            }
        } else {
            System.out.println("User session list is empty. No user's session has been created/added in the Oracle DB");
        }
    }

    @Override
    public void jPrint() {
        JedisOperations jedisOperations = new JedisOperations();
        BigDecimal userSessionId = DataReader.readId(UserSession.class, "id", MenuInputType.USER_SESSION_ID);
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();

        if (jedisOperations.isExists(CachePrefixType.USER_SESSION.toString(), userSessionId)) {
            String jUserSession = jedisOperations.get(CachePrefixType.USER_SESSION.toString() + userSessionId);
            System.out.println(jUserSession);
        } else if (userSessionDAO.isExists(UserSession.class, userSessionId)) {
            UserSession userSession = userSessionDAO.get(userSessionId);
            System.out.println(userSession);
            jedisOperations.set(CachePrefixType.USER_SESSION.toString() + userSession.getId(), userSession.toString());
        } else {
            System.out.println("The entry was not found in the Redis cache and in the Oracle database.");
        }
    }

    @Override
    public void jUpdate() {
        JedisOperations jedisOperations = new JedisOperations();
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UserSession userSession;

        BigDecimal userSessionId = DataReader.readId(UserSession.class, "id", MenuInputType.USER_SESSION_ID);
        String userSessionStatus = DataReader.readUserSessionStatus();

        if (userSessionDAO.isExists(UserSession.class, userSessionId)) {
            userSession = userSessionDAO.get(userSessionId);
            userSession.setStatus(userSessionStatus);
            userSessionDAO.update(userSession);
            // TODO: add exception checking?
            // необходимо ли проверять существует ли, может быть просто добавится новое значение
            jedisOperations.set(CachePrefixType.USER_SESSION.toString() + userSession.getId(), userSession.toString());
            System.out.println("Статус обновлен.");
        } else {
            System.out.println("The specified user's session is not created in the system. Check it out correctly and try again");
        }
    }

    public void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();

        BigDecimal userSessionId = DataReader.readId(UserSession.class, "id", MenuInputType.USER_SESSION_ID);

        if (userSessionDAO.isExists(UserSession.class, userSessionId)) {
            userSessionDAO.delete(UserSession.class, userSessionId);
            System.out.println("The entry was successfully deleted from the database");
        } else {
            System.out.println("The entry was not found in the Database.");
        }

        if (jedisOperations.isExists(CachePrefixType.USER_SESSION.toString(), userSessionId)) {
            jedisOperations.delete(CachePrefixType.USER_SESSION.toString() + userSessionId);
            System.out.println("The entry was successfully deleted from the Redis cache");
        } else {
            System.out.println("The entry was not found in the Redis cache.");
        }
    }

    @Override
    public void jCreate() {
        JedisOperations jedisOperations = new JedisOperations();

        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        String username = DataReader.readString(Users.class, "username", MenuInputType.USERNAME);
        String password = DataReader.readString(Users.class, "password", MenuInputType.PASSWORD);
        // create new Hibernate session and get user for adding to user's session

        UserSession userSession = new UserSession();

        try {

            // проверяем, существует ли пользователь
            BigDecimal userId = usersDAO.getUserId(username, password);
            // если существует, то
            if (userId != null) {
                // TODO: проверять наличие сессии не только в oracle но и в кеше!
                // получаем пользователя по id и устанавливаем в сессию, которую добавляем в БД
                Users users = usersDAO.get(userId);
                userSession.setUserId(users);
                BigDecimal userSessionId = userSessionDAO.create(userSession);
                userSession.setStatus("active");
                userSession.setId(userSessionId);
                jedisOperations.set(CachePrefixType.USER_SESSION.toString() + userSession.getId(), userSession.toString());
            } else {
                throw new NullPointerException();
            }

        } catch (NullPointerException exp) {
            System.out.println("The specified user's session is not register in the system. Check it out correctly and try again");
        }
    }
}
