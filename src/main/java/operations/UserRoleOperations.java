package operations;

import daos.UserRoleDAO;

public class UserRoleOperations {
    public void generateAllUsersRoles() {
        UserRoleDAO dao = new UserRoleDAO();
        dao.generateAllUsersRoles();
    }
}
