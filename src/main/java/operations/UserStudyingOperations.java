package operations;

import daoImpl.UserStudyingDAOImpl;
import utils.DataReader;

public class UserStudyingOperations {

    public void addUserGroup() {
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();
        String userGroup = DataReader.readUserGroup();
        userStudyingDAO.addNewUserGroup(userGroup);
    }

    public boolean checkGroupExists(String group) {
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();
        return userStudyingDAO.groupExist(group);
    }
}
