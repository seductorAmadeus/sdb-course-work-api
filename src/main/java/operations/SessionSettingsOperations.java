package operations;

import daoImpl.BcompSettingsDAOImpl;
import daoImpl.SessionSettingsDAOImpl;
import daoImpl.UserSessionDAOImpl;
import entities.BcompSettings;
import entities.UserSession;
import utils.DataReader;

import java.math.BigDecimal;

public class SessionSettingsOperations {

    public void assignUserSettings() {
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();
        SessionSettingsDAOImpl sessionSettingsDAO = new SessionSettingsDAOImpl();

        UserSession userSession;
        BcompSettings bcompSettings;

        BigDecimal userSessionId = DataReader.readUserSessionId();
        BigDecimal bcompSettingsId = DataReader.readBcompSettingsId();

        try {
            // checking that session exists
            userSession = userSessionDAO.get(userSessionId);
            if (userSession == null) {
                throw new NullPointerException();
            }

            // choose settings
            bcompSettings = bcompSettingsDAO.get(bcompSettingsId);
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
