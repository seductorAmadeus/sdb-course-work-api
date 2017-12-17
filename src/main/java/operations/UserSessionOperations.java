package operations;

import daos.UserSessionDAO;
import daos.UsersDAO;
import utils.DataReader;

import java.math.BigDecimal;

public class UserSessionOperations {

    public void createUserSession() {
        UserSessionDAO userSessionDAO = new UserSessionDAO();
        UsersDAO usersDAO = new UsersDAO();

        String username = DataReader.readUsername();
        String password = DataReader.readPassword();

        try {
            BigDecimal userId = usersDAO.getUserId(username, password);

            if (userId == null) {
                throw new NullPointerException();
            } else {
                userSessionDAO.createSession(userId);
            }

        } catch (NullPointerException exp) {
            System.out.println("The specified user is not register in the system. Check it out correctly and try again");
        }
    }
}
