package operations;

import daos.RegistrationCodesDAO;
import entities.RegistrationCodes;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class RegistrationCodesOperations {
    public void addNewRegistrationCode() {
        RegistrationCodesDAO dao = new RegistrationCodesDAO();
        RegistrationCodes registrationCodes = DataReader.readRegistrationCode();
        // TODO: Remove print action!
        System.out.println((dao.addRegistrationCode(registrationCodes)));
    }

    public void printAllRegistrationCodes() {
        RegistrationCodesDAO dao = new RegistrationCodesDAO();
        List<RegistrationCodes> tempList = dao.listRegistrationCodes();
        System.out.println("invite_code" + " " + "invite_code_status" + " " + "email");
        for (RegistrationCodes registrationCodes : tempList) {
            System.out.println(registrationCodes.toString());
        }
    }

    public void updateRegistrationCodeStatus() {
        RegistrationCodesDAO dao = new RegistrationCodesDAO();
        String newInviteCodeStatus = DataReader.readNewStatusForRegistrationCode();
        BigDecimal oldInviteCode = DataReader.readInviteCode();
        try {
            dao.updateRegistrationCodeStatus(oldInviteCode, newInviteCodeStatus);
        } catch (NullPointerException exp) {
            System.out.println("Указанный инвайт-код отсутствует в базе. Проверьте его корректность и повторите операцию");
        }
    }
}
