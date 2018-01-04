package operations;

import daos.BcompSettingsDAOImpl;
import entities.BcompSettings;
import utils.DataReader;

import java.math.BigDecimal;

public class BcompSettingsOperations {
    public void createBcompSetting() {
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();
        BcompSettings bcompSettings = DataReader.readBcompSettings();
        bcompSettingsDAO.create(bcompSettings);
    }

    public void updateBcompSetting() {
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();

        BigDecimal tempBcompSettingId = DataReader.readBcompSettingsId();
        BcompSettings bcompSettings = DataReader.readBcompSettings();
        bcompSettings.setId(tempBcompSettingId);

        bcompSettingsDAO.update(bcompSettings);
    }
}
