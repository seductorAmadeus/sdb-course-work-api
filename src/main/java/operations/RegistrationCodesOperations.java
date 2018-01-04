package operations;

import daos.RegistrationCodesDAOImpl;
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
        dao.delete(inviteCode);
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
