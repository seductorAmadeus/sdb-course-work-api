package operations;

import daos.PackageFunctions;
import daos.UserSessionDAO;
import utils.DataReader;

import java.math.BigDecimal;

public class SpecifiedOperations {

    public void getEmailFromSessionId() {
        UserSessionDAO userSessionDAO = new UserSessionDAO();
        PackageFunctions packageFunctions = new PackageFunctions();
        BigDecimal userSessionId = DataReader.readUserSessionId();

        if (userSessionDAO.checkSessionExists(userSessionId)) {
            System.out.println(packageFunctions.getEmailFromFromSessionId(userSessionId));
        } else {
            System.out.println("Session was not created");
        }
    }

    public void getSettingsIdForUser() {
        PackageFunctions packageFunctions = new PackageFunctions();
        BigDecimal userId = DataReader.readUserId();
        // TODO: add checking if null
        BigDecimal settingsId = packageFunctions.getSettingsIdForUserId(userId);
        System.out.println(settingsId.toString());
    }
}
