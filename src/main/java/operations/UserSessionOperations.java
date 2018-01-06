package operations;

import daoImpl.UserSessionDAOImpl;
import daoImpl.UsersDAOImpl;
import entities.UserSession;
import entities.Users;
import utils.CachePrefixType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class UserSessionOperations implements DatabaseGenericOperations, RedisGenericOperations {

    public void get() {
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        String username = DataReader.readUsername();
        String password = DataReader.readPassword();
        // create new Hibernate session and get user for adding to user's session

        UserSession userSession = new UserSession();

        try {
            // проверяем, существует ли пользователь
            BigDecimal userId = usersDAO.getUserId(username, password);
            // если существует, то
            if (userId != null) {
                // получаем пользователя по id и устанавливаем в сессию, которую добавляем в БД
                Users users = usersDAO.get(userId);
                userSession.setUserID(users);
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
        BigDecimal userSessionId = DataReader.readUserSessionId();

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

        BigDecimal userSessionId = DataReader.readUserSessionId();
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

        BigDecimal userSessionId = DataReader.readUserSessionId();

        if (userSessionDAO.isExists(UserSession.class, userSessionId)) {
            userSessionDAO.delete(UserSession.class, userSessionId);
        } else {
            System.out.println("The specified user's session is not created in the system. Check it out correctly and try again");
        }

    }

    @Override
    public void jPrintAll() {
        JedisOperations jedisOperations = new JedisOperations();
        List<String> records;
        try {
            records = jedisOperations.getAllRecordsMatchingPattern(CachePrefixType.USER_SESSION + "*");
            if (records.size() == 0) {
                throw new NullPointerException();
            } else {
                for (String record : records) {
                    System.out.println(record);
                }
            }
        } catch (NullPointerException exp) {
            System.out.println("User's sessions list is empty. No user's session has been created/added in Redis cache");
        }
    }

    @Override
    public void jPrint() {
        JedisOperations jedisOperations = new JedisOperations();

        BigDecimal userSessionId = DataReader.readUserSessionId();

        String userSession = jedisOperations.get(CachePrefixType.USER_SESSION.toString() + userSessionId);

        if (userSession != null) {
            System.out.println(userSession);
        } else {
            System.out.println("The specified user's session is not created in the Redis cache. Check it out correctly and try again");
        }

    }

    @Override
    public void jUpdate() {
        JedisOperations jedisOperations = new JedisOperations();
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UserSession userSession;

        BigDecimal userSessionId = DataReader.readUserSessionId();
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

    @Override
    public void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();

        BigDecimal userSessionId = DataReader.readUserSessionId();

        // TODO: удалить несовпадение проверок
        if (userSessionDAO.isExists(UserSession.class, userSessionId)) {
            userSessionDAO.delete(UserSession.class, userSessionId);
            // TODO: add exception checking
            // TODO: добавить дополонительную проверку, существует ли такая сущность в кеше
            jedisOperations.delete(CachePrefixType.USER_SESSION.toString() + userSessionId);
        } else {
            System.out.println("The specified user's session was not found in the Redis cache. Check it out correctly and try again");
        }
    }

    @Override
    public void jCreate() {
        JedisOperations jedisOperations = new JedisOperations();

        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        String username = DataReader.readUsername();
        String password = DataReader.readPassword();
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
                userSession.setUserID(users);
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
