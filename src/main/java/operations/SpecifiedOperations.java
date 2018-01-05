package operations;

import daoImpl.PackageFunctions;
import daoImpl.UserSessionDAOImpl;
import entities.UserSession;
import utils.DataReader;

import java.math.BigDecimal;

public class SpecifiedOperations {

    public void getEmailFromSessionId() {
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        PackageFunctions packageFunctions = new PackageFunctions();
        BigDecimal userSessionId = DataReader.readUserSessionId();

        if (userSessionDAO.isExists(UserSession.class, userSessionId)) {
            System.out.println(packageFunctions.getEmailFromFromSessionId(userSessionId));
        } else {
            System.out.println("Session was not created");
        }
    }

    @Deprecated
    public void getSettingsIdForUser() {
        PackageFunctions packageFunctions = new PackageFunctions();
        BigDecimal userSessionId = DataReader.readUserSessionId();
        // TODO: add checking if null
        BigDecimal settingsId = packageFunctions.getSettingsIdForUserSessionId(userSessionId);
        System.out.println(settingsId.toString());
    }
}
