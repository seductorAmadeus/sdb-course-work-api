package operations;

import daos.BcompSettingsDAOImpl;
import entities.BcompSettings;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class BcompSettingsOperations implements DatabaseGenericOperations, RedisGenericOperations {

    public void add() {
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();
        BcompSettings bcompSettings = DataReader.readBcompSettings();
        bcompSettingsDAO.create(bcompSettings);
    }

    public void update() {
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();

        BigDecimal tempBcompSettingId = DataReader.readBcompSettingsId();
        BcompSettings bcompSettings = DataReader.readBcompSettings();
        bcompSettings.setId(tempBcompSettingId);

        bcompSettingsDAO.update(bcompSettings);
    }

    @Override
    public void delete() {

    }

    public void printAll() {
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();
        try {
            List<BcompSettings> bcompSettingsList = bcompSettingsDAO.getList();
            if (bcompSettingsList.size() == 0) {
                throw new NullPointerException();
            } else {
                for (BcompSettings bcompSettings : bcompSettingsList) {
                    System.out.println(bcompSettings);
                }
            }
        } catch (NullPointerException exp) {
            System.out.println("Bcomp settings list is empty. No bcomp setting has been created");
        }
    }

    @Override
    public void print() {

    }

    @Override
    public void jPrintAll() {

    }

    @Override
    public void jPrint() {

    }

    @Override
    public void jUpdate() {

    }

    @Override
    public void jDelete() {

    }

    @Override
    public void jAdd() {

    }
}
