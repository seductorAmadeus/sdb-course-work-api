package operations;

import daoImpl.UserStudyingDAOImpl;
import entities.UserStudying;
import utils.DataReader;

public class UserStudyingOperations implements DatabaseGenericOperations, RedisGenericOperations{

    public void create() {
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();
        String userGroup = DataReader.readUserGroup();
        UserStudying userStudying = new UserStudying();
        userStudying.setUserGroup(userGroup);
        // TODO: сделать проверку добавления, если уже существует
        System.out.println(userStudyingDAO.create(userStudying));
    }

    @Override
    public void printAll() {

    }

    @Override
    public void print() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

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
    public void jCreate() {

    }
}
