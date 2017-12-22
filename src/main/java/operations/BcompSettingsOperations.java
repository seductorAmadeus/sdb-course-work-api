package operations;

import daos.BcompSettingsDAO;
import entities.BcompSettings;
import utils.DataReader;

public class BcompSettingsOperations {
    public void createBcompSettings() {
        BcompSettingsDAO bcompSettingsDAO = new BcompSettingsDAO();
        BcompSettings bcompSettings = DataReader.readBcompSettings();
        bcompSettingsDAO.addBcompSettings(bcompSettings);
    }
}
