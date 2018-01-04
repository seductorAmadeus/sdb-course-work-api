package operations;

import daos.BcompSettingsDAOImpl;
import entities.BcompSettings;
import utils.CachePrefixType;
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
        JedisOperations jedisOperations = new JedisOperations();

        BigDecimal bcompSettingId = DataReader.readBcompSettingsId();

        String bcompSetting = jedisOperations.get(CachePrefixType.BCOMP_SETTINGS.toString() + bcompSettingId);

        if (bcompSetting != null) {
            System.out.println(bcompSetting);
        } else {
            System.out.println("The specified bcomp setting id was not found in the Redis cache. Check it out correctly and try again");
        }
    }

    @Override
    public void jUpdate() {

    }

    @Override
    public void jDelete() {

    }

    @Override
    public void jAdd() {
        JedisOperations jedisOperations = new JedisOperations();

        BcompSettings bcompSettings;
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();

        bcompSettings = DataReader.readBcompSettings();

        BigDecimal bcompSettingsId;
        try {
            bcompSettingsId = bcompSettingsDAO.create(bcompSettings);
            if (bcompSettingsId != null) {
                bcompSettings.setId(bcompSettingsId);
                jedisOperations.set(CachePrefixType.BCOMP_SETTINGS.toString() + bcompSettings.getId(), bcompSettings.toString());
            }
        } catch (NullPointerException exp) {
            System.out.println("The specified bcomp setting was not created in the system. Check it out correctly and try again");
        }

    }
}
