package operations;

import daos.UserStudyingDAO;
import utils.DataReader;

public class UserStudyingOperations {

    public void addUserGroup() {
        UserStudyingDAO userStudyingDAO = new UserStudyingDAO();
        String userGroup = DataReader.readUserGroup();
        userStudyingDAO.addNewUserGroup(userGroup);
    }

    public boolean checkGroupExists(String group) {
        UserStudyingDAO userStudyingDAO = new UserStudyingDAO();
        return userStudyingDAO.groupExist(group);
    }
}
