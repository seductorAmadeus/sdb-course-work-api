package operations;

import daos.RegistrationCodesDAOImpl;
import entities.RegistrationCodes;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class RegistrationCodesOperations {
    public void addNewRegistrationCode() {
        RegistrationCodesDAOImpl dao = new RegistrationCodesDAOImpl();
        RegistrationCodes registrationCodes = DataReader.readRegistrationCode();
        // TODO: Remove print action!
        System.out.println((dao.create(registrationCodes)));
    }

    public void getRegistrationCode() {

    }

    public void printAllRegistrationCodes() {
        RegistrationCodesDAOImpl dao = new RegistrationCodesDAOImpl();
        List<RegistrationCodes> tempList = dao.getList();
        System.out.println("invite_code" + " " + "invite_code_status" + " " + "email");
        for (RegistrationCodes registrationCodes : tempList) {
            System.out.println(registrationCodes.toString());
        }
    }

    public void updateRegistrationCodeStatus() {
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

    public void deleteRegistrationCode() {
        RegistrationCodesDAOImpl dao = new RegistrationCodesDAOImpl();
        BigDecimal inviteCode = DataReader.readInviteCode();
        dao.delete(inviteCode);
    }
}
