package operations;

import daoImpl.BcompSettingsDAOImpl;
import entities.BcompSettings;
import enums.CachePrefixType;
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
        BigDecimal bcompSettingsId = DataReader.readBcompSettingsId();
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

    public void synchronize() {
        JedisOperations jedisOperations = new JedisOperations();
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();

        List<String> keysList = jedisOperations.getAllKeys(CachePrefixType.BCOMP_SETTINGS.toString() + "*");
        // удаляем лишние записи в Redis, если таковые отсутствуют в БД
        for (String aKeysList : keysList) {
            String temStrId = aKeysList.substring(aKeysList.lastIndexOf(":") + 1);
            BigDecimal tempId = new BigDecimal(temStrId);
            if (!bcompSettingsDAO.isExists(BcompSettings.class, tempId)) {
                jedisOperations.delete(aKeysList);
            }
        }

        // добавляем отсутствующие записи в Redis из Oracle-а.
        List<BcompSettings> bcompSettings = bcompSettingsDAO.getList();

        for (BcompSettings settings : bcompSettings) {
            jedisOperations.set(CachePrefixType.BCOMP_SETTINGS.toString() + settings.getId(), settings.toString());
        }

    }

    @Override
    public void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        BcompSettingsDAOImpl bcompSettingsDAO = new BcompSettingsDAOImpl();

        BigDecimal bcompSettingsId = DataReader.readBcompSettingsId();

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
