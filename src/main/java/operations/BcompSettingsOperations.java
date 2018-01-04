package operations;

import daos.BcompSettingsDAOImpl;
import entities.BcompSettings;
import utils.DataReader;

public class BcompSettingsOperations {
    public void createBcompSettings() {
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();
        BcompSettings bcompSettings = DataReader.readBcompSettings();
        bcompSettingsDAO.create(bcompSettings);
    }
}
