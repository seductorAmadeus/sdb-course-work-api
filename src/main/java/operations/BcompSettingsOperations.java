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
        JedisOperations jedisOperations = new JedisOperations();
        List<String> records;
        try {
            records = jedisOperations.getAllRecordsMatchingPattern(CachePrefixType.BCOMP_SETTINGS + "*");
            if (records.size() == 0) {
                throw new NullPointerException();
            } else {
                for (String record : records) {
                    System.out.println(record);
                }
            }
        } catch (NullPointerException exp) {
            System.out.println("Bcomp settings list is empty. No bcomp has been created/added in Redis cache");
        }

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
        JedisOperations jedisOperations = new JedisOperations();

        BcompSettings bcompSettings;
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();

        BigDecimal bcompSettingsId = DataReader.readBcompSettingsId();

        if (bcompSettingsDAO.isExists(BcompSettings.class, bcompSettingsId)) {
            bcompSettings = DataReader.readBcompSettings();
            bcompSettings.setId(bcompSettingsId);
            bcompSettingsDAO.update(bcompSettings);
            // TODO: add exception checking?
            // необходимо ли проверять существует ли, может быть просто добавится новое значение
            jedisOperations.set(CachePrefixType.BCOMP_SETTINGS.toString() + bcompSettings.getId(), bcompSettings.toString());
        } else {
            System.out.println("The specified bcomp setting id was not found in the system. Check it out correctly and try again");
        }
    }

    @Override
    public void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();

        BigDecimal bcompSettingId = DataReader.readBcompSettingsId();

        if (bcompSettingsDAO.isExists(BcompSettings.class, bcompSettingId)) {
            // firstly, we must to deleteByBcompSettingsId session setting that linked
            bcompSettingsDAO.delete(BcompSettings.class, bcompSettingId);
        } else {
            System.out.println("The specified bcomp setting id was not found in the oracle DB. Check it out correctly and try again");
        }
        // check if exists in cache
        jedisOperations.delete(CachePrefixType.BCOMP_SETTINGS.toString() + bcompSettingId);
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
