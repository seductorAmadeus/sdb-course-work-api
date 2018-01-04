package operations;

import daos.BcompSettingsDAOImpl;
import daos.SessionSettingsDAO;
import daos.UserSessionDAO;
import entities.BcompSettings;
import entities.UserSession;
import utils.DataReader;

import java.math.BigDecimal;

public class SessionSettingsOperations {

    public void assignUserSettings() {
        UserSessionDAO userSessionDAO = new UserSessionDAO();
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();
        SessionSettingsDAO sessionSettingsDAO = new SessionSettingsDAO();

        UserSession userSession;
        BcompSettings bcompSettings;

        BigDecimal userSessionId = DataReader.readUserSessionId();
        BigDecimal bcompSettingsId = DataReader.readBcompSettingsId();

        try {
            // checking that session exists
            userSession = userSessionDAO.getUserSessionById(userSessionId);
            if (userSession == null) {
                throw new NullPointerException();
            }

            // choose settings
            bcompSettings = bcompSettingsDAO.read(bcompSettingsId);
            if (bcompSettingsId == null) {
                throw new NumberFormatException();
            }

            // create bcomp_user_settings
            sessionSettingsDAO.assignUserSettings(userSession, bcompSettings);
        } catch (NullPointerException exp) {
            System.out.println("The specified session was not created in the system. Check it out correctly and try again");
        } catch (NumberFormatException exp) {
            System.out.println("The specified bcomp setting was not created in the system. Check it out correctly and try again");
        }

    }
}
