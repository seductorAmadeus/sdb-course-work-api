package operations;

import daoImpl.UserRoleDAOImpl;

public class UserRoleOperations {
    public void generateAllUsersRoles() {
        UserRoleDAOImpl dao = new UserRoleDAOImpl();
        dao.generateAllUsersRoles();
    }
}
