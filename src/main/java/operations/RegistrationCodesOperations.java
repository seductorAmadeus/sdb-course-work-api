package operations;

import daoImpl.RegistrationCodesDAOImpl;
import entities.RegistrationCodes;
import enums.CachePrefixType;
import enums.MenuInputType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class RegistrationCodesOperations extends DatabaseGenericOperations {

    public void create() {
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

    public void update() {
        RegistrationCodesDAOImpl dao = new RegistrationCodesDAOImpl();
        String newInviteCodeStatus = DataReader.readString(RegistrationCodes.class, "inviteCodeStatus", MenuInputType.INVITE_CODE_STATUS);
        BigDecimal oldInviteCode = null;
        try {
            oldInviteCode = DataReader.readBigDecimal(RegistrationCodes.class, "inviteCode", MenuInputType.INVITE_CODE);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
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
        BigDecimal registrationCodeId = DataReader.readId(RegistrationCodes.class, "regCodeId", MenuInputType.REGISTRATION_CODE_ID);
        dao.delete(RegistrationCodes.class, registrationCodeId);
    }

    @Override
    public void jPrintAll() {
        JedisOperations jedisOperations = new JedisOperations();
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();
        List<String> records;
        List<RegistrationCodes> registrationCodesList;

        records = jedisOperations.getAllRecordsMatchingPattern(CachePrefixType.REGISTRATION_CODES + "*");
        if (records.size() == 0) {
            System.out.println("Registration codes list is empty. No registration codes has been created/added in the Redis cache");
        } else {
            for (String record : records) {
                System.out.println(record);
            }
        }
        registrationCodesList = registrationCodesDAO.getList();
        if (registrationCodesList.size() != 0) {
            for (RegistrationCodes aRegistrationCodesList : registrationCodesList) {
                if (!jedisOperations.isExists(CachePrefixType.REGISTRATION_CODES.toString(), aRegistrationCodesList.getRegCodeId())) {
                    // set in the Redis cache
                    jedisOperations.set(CachePrefixType.REGISTRATION_CODES.toString() + aRegistrationCodesList.getRegCodeId(), aRegistrationCodesList.toString());
                    // and output the record with the missing key in the Redis cache.
                    System.out.println(aRegistrationCodesList);
                }
            }
        } else {
            System.out.println("Registration codes list is empty. No registration codes has been created in the Oracle DB");
        }
    }

    @Override
    public void jPrint() {
        JedisOperations jedisOperations = new JedisOperations();
        BigDecimal registrationCodeId = DataReader.readId(RegistrationCodes.class, "regCodeId", MenuInputType.REGISTRATION_CODE_ID);
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();

        if (jedisOperations.isExists(CachePrefixType.REGISTRATION_CODES.toString(), registrationCodeId)) {
            String jRegistrationCode = jedisOperations.get(CachePrefixType.REGISTRATION_CODES.toString() + registrationCodeId);
            System.out.println(jRegistrationCode);
        } else if (registrationCodesDAO.isExists(RegistrationCodes.class, registrationCodeId)) {
            RegistrationCodes registrationCodes = registrationCodesDAO.get(registrationCodeId);
            System.out.println(registrationCodes);
            jedisOperations.set(CachePrefixType.REGISTRATION_CODES.toString() + registrationCodes.getRegCodeId(), registrationCodes.toString());
        } else {
            System.out.println("The entry was not found in the Redis cache and in the Oracle database.");
        }
    }

    @Override
    public void jUpdate() {
        JedisOperations jedisOperations = new JedisOperations();

        RegistrationCodes registrationCodes;
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();

        BigDecimal registrationCodeId = DataReader.readId(RegistrationCodes.class, "regCodeId", MenuInputType.REGISTRATION_CODE_ID);

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

    public void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();

        BigDecimal registrationCodeId = DataReader.readId(RegistrationCodes.class, "regCodeId", MenuInputType.REGISTRATION_CODE_ID);

        if (registrationCodesDAO.isExists(RegistrationCodes.class, registrationCodeId)) {
            registrationCodesDAO.delete(RegistrationCodes.class, registrationCodeId);
            System.out.println("The entry was successfully deleted from the database");
        } else {
            System.out.println("The entry was not found in the Database.");
        }

        if (jedisOperations.isExists(CachePrefixType.REGISTRATION_CODES.toString(), registrationCodeId)) {
            jedisOperations.delete(CachePrefixType.REGISTRATION_CODES.toString() + registrationCodeId);
            System.out.println("The entry was successfully deleted from the Redis cache");
        } else {
            System.out.println("The entry was not found in the Redis cache.");
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
