package operations;

import daoImpl.RegistrationCodesDAOImpl;
import entities.RegistrationCodes;
import utils.CachePrefixType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class RegistrationCodesOperations implements RedisGenericOperations, DatabaseGenericOperations {

    public void get() {
        RegistrationCodesDAOImpl dao = new RegistrationCodesDAOImpl();
        RegistrationCodes registrationCodes = DataReader.readRegistrationCode();
        // TODO: Remove print action!
        System.out.println((dao.create(registrationCodes)));
    }

    public void printAll() {
        RegistrationCodesDAOImpl dao = new RegistrationCodesDAOImpl();
        List<RegistrationCodes> tempList = dao.getList();
        System.out.println("invite_code" + " " + "invite_code_status" + " " + "email");
        for (RegistrationCodes registrationCodes : tempList) {
            System.out.println(registrationCodes.toString());
        }
    }

    @Override
    public void print() {

    }

    public void synchronize() {
        JedisOperations jedisOperations = new JedisOperations();
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();

        List<String> keysList = jedisOperations.getAllKeys(CachePrefixType.REGISTRATION_CODES.toString() + "*");
        // удаляем лишние записи в Redis, если таковые отсутствуют в БД
        for (String aKeysList : keysList) {
            String temStrId = aKeysList.substring(aKeysList.lastIndexOf(":") + 1);
            BigDecimal tempId = new BigDecimal(temStrId);
            if (!registrationCodesDAO.isExists(RegistrationCodes.class, tempId)) {
                jedisOperations.delete(aKeysList);
            }
        }

        // добавляем отсутствующие записи в Redis из Oracle-а.
        List<RegistrationCodes> registrationCodes = registrationCodesDAO.getList();

        for (RegistrationCodes codes : registrationCodes) {
            jedisOperations.set(CachePrefixType.REGISTRATION_CODES.toString() + codes.getRegCodeId(), codes.toString());
        }
    }

    public void update() {
        RegistrationCodesDAOImpl dao = new RegistrationCodesDAOImpl();
        String newInviteCodeStatus = DataReader.readNewStatusForRegistrationCode();
        BigDecimal oldInviteCode = DataReader.readInviteCode();
        RegistrationCodes tempRegistrationCode = new RegistrationCodes();
        tempRegistrationCode.setInviteCode(oldInviteCode);
        tempRegistrationCode.setInviteCodeStatus(newInviteCodeStatus);
        try {
            dao.update(tempRegistrationCode);
        } catch (NullPointerException exp) {
            System.out.println("Invite code not found. Check input and try again");
        }
    }

    public void delete() {
        RegistrationCodesDAOImpl dao = new RegistrationCodesDAOImpl();
        BigDecimal inviteCode = DataReader.readInviteCode();
        dao.delete(RegistrationCodes.class, inviteCode);
    }

    @Override
    public void jPrintAll() {
        JedisOperations jedisOperations = new JedisOperations();
        List<String> records;
        try {
            records = jedisOperations.getAllRecordsMatchingPattern(CachePrefixType.REGISTRATION_CODES + "*");
            if (records.size() == 0) {
                throw new NullPointerException();
            } else {
                for (String record : records) {
                    System.out.println(record);
                }
            }
        } catch (NullPointerException exp) {
            System.out.println("Registration codes list is empty. No bcomp has been created/added in Redis cache");
        }
    }

    @Override
    public void jPrint() {
        JedisOperations jedisOperations = new JedisOperations();
        BigDecimal registrationCodeId = DataReader.readRegistrationCodeId();
        String registrationCode = jedisOperations.get(CachePrefixType.REGISTRATION_CODES.toString() + registrationCodeId);
        if (registrationCode != null) {
            System.out.println(registrationCode);
        } else {
            System.out.println("The specified registration code was not found in the Redis cache. Check it out correctly and try again");
        }
    }

    @Override
    public void jUpdate() {
        JedisOperations jedisOperations = new JedisOperations();

        RegistrationCodes registrationCodes;
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();

        BigDecimal registrationCodeId = DataReader.readRegistrationCodeId();

        if (registrationCodesDAO.isExists(RegistrationCodes.class, registrationCodeId)) {
            registrationCodes = DataReader.readRegistrationCode();
            registrationCodes.setRegCodeId(registrationCodeId);
            registrationCodesDAO.update(registrationCodes);
            // TODO: add exception checking?
            // необходимо ли проверять существует ли, может быть просто добавится новое значение
            jedisOperations.set(CachePrefixType.REGISTRATION_CODES.toString() + registrationCodes.getRegCodeId(), registrationCodes.toString());
        } else {
            System.out.println("The specified registration code id was not found in the system. Check it out correctly and try again");
        }
    }

    @Override
    public void jDelete() {

        JedisOperations jedisOperations = new JedisOperations();
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();

        BigDecimal registrationCodeId = DataReader.readRegistrationCodeId();

        // TODO: удалить несовпадение проверок
        if (registrationCodesDAO.isExists(RegistrationCodes.class, registrationCodeId)) {
            registrationCodesDAO.delete(RegistrationCodes.class, registrationCodeId);
            // TODO: add exception checking
            jedisOperations.delete(CachePrefixType.REGISTRATION_CODES.toString() + registrationCodeId);
        } else {
            System.out.println("The specified registration code id was not found in the Redis cache. Check it out correctly and try again");
        }

    }

    @Override
    public void jCreate() {
        JedisOperations jedisOperations = new JedisOperations();

        RegistrationCodes registrationCodes;
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();

        registrationCodes = DataReader.readRegistrationCode();

        try {
            BigDecimal registrationCodeId = registrationCodesDAO.create(registrationCodes);

            if (registrationCodeId != null) {
                registrationCodes.setRegCodeId(registrationCodeId);
                jedisOperations.set(CachePrefixType.REGISTRATION_CODES.toString() + registrationCodes.getRegCodeId(), registrationCodes.toString());
            }
        } catch (NullPointerException exp) {
            System.out.println("Something happened, sorry");
        }
    }
}
