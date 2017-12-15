package operations;

import daos.RegistrationCodesDAO;
import daos.UsersDAO;
import entities.RegistrationCodes;
import entities.Users;
import utils.DataReader;

public class UsersOperations {
    // TODO: add user not found exception
    public void addNewUser() {
        RegistrationCodesDAO registrationCodesDAO = new RegistrationCodesDAO();
        RegistrationCodes registrationCode = registrationCodesDAO.findFreeRegistrationCode();
        UsersDAO dao = new UsersDAO();
        Users user = DataReader.readUser();
        user.setInviteCode(registrationCode);
        dao.addUser(user);
    }
}
