package operations;

import daoImpl.RegistrationCodesDAOImpl;
import entities.RegistrationCodes;
import utils.CachePrefixType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class RegistrationCodesOperations implements RedisGenericOperations, DatabaseGenericOperations {

    public void add() {
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

    }

    @Override
    public void jDelete() {

    }

    @Override
    public void jAdd() {
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
