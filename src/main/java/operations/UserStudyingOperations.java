package operations;

import daos.UserStudyingDAO;

public class UserStudyingOperations {
    public void generateAllUsersGroups() {
        UserStudyingDAO userStudyingDAO = new UserStudyingDAO();
        userStudyingDAO.generateAllUsersGroups();
    }
}
