package operations;

import daoImpl.UserSessionDAOImpl;
import daoImpl.UsersDAOImpl;
import entities.UserSession;
import entities.Users;
import utils.DataReader;

import java.math.BigDecimal;

public class UserSessionOperations implements DatabaseGenericOperations{

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

    }

    @Override
    public void print() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

}
