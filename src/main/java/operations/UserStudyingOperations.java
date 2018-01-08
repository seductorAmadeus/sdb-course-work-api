package operations;

import daoImpl.UserStudyingDAOImpl;
import entities.UserStudying;
import enums.CachePrefixType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class UserStudyingOperations extends DatabaseGenericOperations {

    public void create() {

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
        JedisOperations jedisOperations = new JedisOperations();
        List<String> records;
        try {
            records = jedisOperations.getAllRecordsMatchingPattern(CachePrefixType.USER_STUDYING + "*");
            if (records.size() == 0) {
                throw new NullPointerException();
            } else {
                for (String record : records) {
                    System.out.println(record);
                }
            }
        } catch (NullPointerException exp) {
            System.out.println("User studying list is empty. No user's studying information has been created/added in Redis cache");
        }
    }

    @Override
    public void jPrint() {
        JedisOperations jedisOperations = new JedisOperations();
        BigDecimal userStudyingId = DataReader.readUserStudyingId();
        String userStudying = jedisOperations.get(CachePrefixType.USER_STUDYING.toString() + userStudyingId);
        if (userStudying != null) {
            System.out.println(userStudying);
        } else {
            System.out.println("The specified user studying was not found in the Redis cache. Check it out correctly and try again");
        }
    }

    @Override
    public void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();

        BigDecimal userStudyingId = DataReader.readUserStudyingId();

        if (userStudyingDAO.isExists(UserStudying.class, userStudyingId)) {
            userStudyingDAO.delete(UserStudying.class, userStudyingId);
            // TODO: add exception checking
            jedisOperations.delete(CachePrefixType.USER_STUDYING.toString() + userStudyingId);
        } else {
            System.out.println("The specified user studying was not found in the Redis cache. Check it out correctly and try again");
        }
    }

    @Override
    public void jCreate() {
        JedisOperations jedisOperations = new JedisOperations();
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();
        String userGroup = DataReader.readUserGroup();
        UserStudying userStudying = new UserStudying();
        userStudying.setUserGroup(userGroup);
        // check this later
        try {
            BigDecimal userStudyingId = userStudyingDAO.create(userStudying);
            if (userStudyingId != null) {
                userStudying.setId(userStudyingId);
                jedisOperations.set(CachePrefixType.USER_STUDYING.toString() + userStudying.getId(), userStudying.toString());
            } else throw new NullPointerException();
        } catch (NullPointerException exp) {
            System.out.println(" The specified user group already exists in the system. Check it out correctly and try again");
        }
    }
}
