package operations;

import daoImpl.UserSessionDAOImpl;
import daoImpl.UsersDAOImpl;
import entities.UserSession;
import entities.Users;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class UserSessionOperations implements DatabaseGenericOperations {

    public void add() {
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

    }

}
