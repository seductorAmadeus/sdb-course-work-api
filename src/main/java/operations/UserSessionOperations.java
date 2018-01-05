package operations;

import daoImpl.UserSessionDAOImpl;
import daoImpl.UsersDAOImpl;
import utils.DataReader;

import java.math.BigDecimal;

public class UserSessionOperations {

    public void createUserSession() {
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UsersDAOImpl usersDAO = new UsersDAOImpl();

        String username = DataReader.readUsername();
        String password = DataReader.readPassword();

        try {
            BigDecimal userId = usersDAO.getUserId(username, password);

            if (userId == null) {
                throw new NullPointerException();
            } else {
                userSessionDAO.create(userId);
            }

        } catch (NullPointerException exp) {
            System.out.println("The specified user is not register in the system. Check it out correctly and try again");
        }
    }
}
