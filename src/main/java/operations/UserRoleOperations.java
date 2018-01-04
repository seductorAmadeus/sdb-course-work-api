package operations;

import daos.UserRoleDAOImpl;

public class UserRoleOperations {
    public void generateAllUsersRoles() {
        UserRoleDAOImpl dao = new UserRoleDAOImpl();
        dao.generateAllUsersRoles();
    }
}
