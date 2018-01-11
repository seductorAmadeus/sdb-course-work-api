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
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();

        if (jedisOperations.isExists(CachePrefixType.USER_STUDYING.toString(), userStudyingId)) {
            String jUserStudying = jedisOperations.get(CachePrefixType.USER_STUDYING.toString() + userStudyingId);
            System.out.println(jUserStudying);
        } else if (userStudyingDAO.isExists(UserStudying.class, userStudyingId)) {
            UserStudying userStudying = userStudyingDAO.get(userStudyingId);
            System.out.println(userStudying);
            jedisOperations.set(CachePrefixType.USER_STUDYING.toString() + userStudying.getId(), userStudying.toString());
        } else {
            System.out.println("The entry was not found in the Redis cache and in the Oracle database.");
        }
    }

    @Override
    public void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();

        BigDecimal userStudyingId = DataReader.readUserStudyingId();

        if (userStudyingDAO.isExists(UserStudying.class, userStudyingId)) {
            userStudyingDAO.delete(UserStudying.class, userStudyingId);
            System.out.println("The entry was successfully deleted from the database");
        } else {
            System.out.println("The entry was not found in the Database.");
        }

        if (jedisOperations.isExists(CachePrefixType.USER_STUDYING.toString(), userStudyingId)) {
            jedisOperations.delete(CachePrefixType.USER_STUDYING.toString() + userStudyingId);
            System.out.println("The entry was successfully deleted from the Redis cache");
        } else {
            System.out.println("The entry was not found in the Redis cache.");
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

    public void synchronize() {
        JedisOperations jedisOperations = new JedisOperations();
        UserStudyingDAOImpl userStudyingDAO = new UserStudyingDAOImpl();

        List<String> keysList = jedisOperations.getAllKeys(CachePrefixType.USER_STUDYING.toString() + "*");
        // удаляем лишние записи в Redis, если таковые отсутствуют в БД
        for (String aKeysList : keysList) {
            String temStrId = aKeysList.substring(aKeysList.lastIndexOf(":") + 1);
            BigDecimal tempId = new BigDecimal(temStrId);
            if (!userStudyingDAO.isExists(UserStudying.class, tempId)) {
                jedisOperations.delete(aKeysList);
            }
        }

        // добавляем отсутствующие записи в Redis из Oracle-а.
        List<UserStudying> userStudyingList = userStudyingDAO.getList();

        for (UserStudying userStudying : userStudyingList) {
            jedisOperations.set(CachePrefixType.USER_STUDYING.toString() + userStudying.getId(), userStudying.toString());
        }

    }
}
