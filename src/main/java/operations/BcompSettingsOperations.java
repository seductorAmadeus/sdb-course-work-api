package operations;

import daoImpl.BcompSettingsDAOImpl;
import entities.BcompSettings;
import enums.CachePrefixType;
import enums.MenuInputType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class BcompSettingsOperations extends DatabaseGenericOperations {

    public void create() {
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();
        BcompSettings bcompSettings = DataReader.readBcompSettings();
        bcompSettingsDAO.create(bcompSettings);
    }

    public void update() {
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();

        BigDecimal tempBcompSettingId = DataReader.readId(BcompSettings.class, "id", MenuInputType.BCOMP_SETTINGS_ID);
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
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();
        List<String> records;
        List<BcompSettings> bcompSettingsList;

        records = jedisOperations.getAllRecordsMatchingPattern(CachePrefixType.BCOMP_SETTINGS + "*");
        if (records.size() == 0) {
            System.out.println("Bcomp settings list is empty. No bcomp setting has been created/added in the Redis cache");
        } else {
            for (String record : records) {
                System.out.println(record);
            }
        }
        bcompSettingsList = bcompSettingsDAO.getList();
        if (bcompSettingsList.size() != 0) {
            for (BcompSettings bcompSettings : bcompSettingsList) {
                if (!jedisOperations.isExists(CachePrefixType.BCOMP_SETTINGS.toString(), bcompSettings.getId())) {
                    // set in the Redis cache
                    jedisOperations.set(CachePrefixType.BCOMP_SETTINGS.toString() + bcompSettings.getId(), bcompSettings.toString());
                    // and output the record with the missing key in the Redis cache.
                    System.out.println(bcompSettings);
                }
            }
        } else {
            System.out.println("Bcomp settings list is empty. No bcomp setting has been created/added in the Oracle DB");
        }
    }

    @Override
    public void jPrint() {
        JedisOperations jedisOperations = new JedisOperations();
        BigDecimal bcompSettingsId = DataReader.readId(BcompSettings.class, "id", MenuInputType.BCOMP_SETTINGS_ID);
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();

        if (jedisOperations.isExists(CachePrefixType.BCOMP_SETTINGS.toString(), bcompSettingsId)) {
            String jBcompSettings = jedisOperations.get(CachePrefixType.BCOMP_SETTINGS.toString() + bcompSettingsId);
            System.out.println(jBcompSettings);
        } else if (bcompSettingsDAO.isExists(BcompSettings.class, bcompSettingsId)) {
            BcompSettings bcompSettings = bcompSettingsDAO.get(bcompSettingsId);
            System.out.println(bcompSettings);
            jedisOperations.set(CachePrefixType.BCOMP_SETTINGS.toString() + bcompSettings.getId(), bcompSettings.toString());
        } else {
            System.out.println("The entry was not found in the Redis cache and in the Oracle database.");
        }
    }

    @Override
    public void jUpdate() {
        JedisOperations jedisOperations = new JedisOperations();

        BcompSettings bcompSettings;
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();

        BigDecimal bcompSettingsId = DataReader.readId(BcompSettings.class, "id", MenuInputType.BCOMP_SETTINGS_ID);

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

        BigDecimal bcompSettingsId = DataReader.readId(BcompSettings.class, "id", MenuInputType.BCOMP_SETTINGS_ID);

        if (bcompSettingsDAO.isExists(BcompSettings.class, bcompSettingsId)) {
            bcompSettingsDAO.delete(BcompSettings.class, bcompSettingsId);
            System.out.println("The entry was successfully deleted from the database");
        } else {
            System.out.println("The entry was not found in the Database.");
        }

        if (jedisOperations.isExists(CachePrefixType.BCOMP_SETTINGS.toString(), bcompSettingsId)) {
            jedisOperations.delete(CachePrefixType.BCOMP_SETTINGS.toString() + bcompSettingsId);
            System.out.println("The entry was successfully deleted from the Redis cache");
        } else {
            System.out.println("The entry was not found in the Redis cache.");
        }
    }

    @Override
    public void jCreate() {
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
