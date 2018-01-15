package operations;

import daoImpl.BcompDAOImpl;
import daoImpl.UserSessionDAOImpl;
import entities.Bcomp;
import entities.UserSession;
import enums.CachePrefixType;
import enums.MenuInputType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class BcompOperations extends DatabaseGenericOperations {

    @Deprecated
    public void create() {
        Bcomp bcomp = new Bcomp();
        BcompDAOImpl bcompDAO = new BcompDAOImpl();
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UserSession userSession;

        BigDecimal userSessionId = DataReader.readId(UserSession.class, "id", MenuInputType.USER_SESSION_ID);
        try {
            // checking that session exists
            userSession = userSessionDAO.get(userSessionId);
            if (userSession == null) {
                throw new NullPointerException();
            }
            bcomp.setUserSession(userSession);
            bcompDAO.create(bcomp);
        } catch (NullPointerException exp) {
            System.out.println("The specified session was not created in the system. Check it out correctly and try again");
        }
    }

    @Deprecated
    public void printAll() {
        BcompDAOImpl bcompDAO = new BcompDAOImpl();
        try {
            List<Bcomp> bcompList = bcompDAO.getList();
            if (bcompList.size() == 0) {
                throw new NullPointerException();
            } else {
                for (Bcomp bcomp : bcompList) {
                    System.out.println(bcomp);
                }
            }
        } catch (NullPointerException exp) {
            System.out.println("Bcomp list is empty. No bcomp has been created");
        }

    }

    public void synchronize() {
        JedisOperations jedisOperations = new JedisOperations();
        BcompDAOImpl bcompDAO = new BcompDAOImpl();

        List<String> keysList = jedisOperations.getAllKeys(CachePrefixType.BCOMP.toString() + "*");
        // удаляем лишние записи в Redis, если таковые отсутствуют в БД
        for (String aKeysList : keysList) {
            String temStrId = aKeysList.substring(aKeysList.lastIndexOf(":") + 1);
            BigDecimal tempId = new BigDecimal(temStrId);
            if (!bcompDAO.isExists(Bcomp.class, tempId)) {
                jedisOperations.delete(aKeysList);
            }
        }

        // добавляем отсутствующие записи в Redis из Oracle-а.
        List<Bcomp> bcomps = bcompDAO.getList();

        for (Bcomp bcomp : bcomps) {
            jedisOperations.set(CachePrefixType.BCOMP.toString() + bcomp.getId(), bcomp.toString());
        }

    }

    public void jPrintAll() {
        JedisOperations jedisOperations = new JedisOperations();
        List<String> records;
        try {
            records = jedisOperations.getAllRecordsMatchingPattern(CachePrefixType.BCOMP + "*");
            if (records.size() == 0) {
                throw new NullPointerException();
            } else {
                for (String record : records) {
                    System.out.println(record);
                }
            }
        } catch (NullPointerException exp) {
            System.out.println("Bcomp list is empty. No bcomp has been created/added in Redis cache");
        }

    }

    public void jCreate() {
        JedisOperations jedisOperations = new JedisOperations();

        Bcomp bcomp = new Bcomp();
        BcompDAOImpl bcompDAO = new BcompDAOImpl();
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UserSession userSession;

        BigDecimal userSessionId = DataReader.readId(UserSession.class, "id", MenuInputType.USER_SESSION_ID);
        try {
            // checking that session exists
            userSession = userSessionDAO.get(userSessionId);
            if (userSession == null) {
                throw new NullPointerException();
            }
            bcomp.setUserSession(userSession);
            BigDecimal newBcompId = bcompDAO.create(bcomp);
            // TODO: add create checking
            // if create in db was successful
            if (newBcompId != null) {
                bcomp.setId(newBcompId);
                jedisOperations.set(CachePrefixType.BCOMP.toString() + bcomp.getId(), bcomp.toString());
            }
        } catch (NullPointerException exp) {
            System.out.println("The specified session was not created in the system. Check it out correctly and try again");
        }
    }

    @Deprecated
    public void update() {
        Bcomp bcomp;
        BcompDAOImpl bcompDAO = new BcompDAOImpl();

        BigDecimal bcompId = DataReader.readId(Bcomp.class, "id", MenuInputType.BCOMP_ID);

        if (bcompDAO.isExists(Bcomp.class, bcompId)) {
            bcomp = bcompDAO.get(bcompId);
            DataReader.initBcomp(bcomp);
            bcompDAO.update(bcomp);
        } else {
            System.out.println("The specified bcomp id was not found in the system. Check it out correctly and try again");
        }
    }

    public void jUpdate() {
        JedisOperations jedisOperations = new JedisOperations();

        Bcomp bcomp;
        BcompDAOImpl bcompDAO = new BcompDAOImpl();

        BigDecimal bcompId = DataReader.readId(Bcomp.class, "id", MenuInputType.BCOMP_ID);

        if (bcompDAO.isExists(Bcomp.class, bcompId)) {
            bcomp = bcompDAO.get(bcompId);
            DataReader.initBcomp(bcomp);
            bcompDAO.update(bcomp);
            // TODO: add exception checking?
            // необходимо ли проверять существует ли, может быть просто добавится новое значение
            jedisOperations.set(CachePrefixType.BCOMP.toString() + bcomp.getId(), bcomp.toString());
        } else {
            System.out.println("The specified bcomp id was not found in the system. Check it out correctly and try again");
        }
    }

    public void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        BcompDAOImpl bcompDAO = new BcompDAOImpl();

        BigDecimal bcompId = DataReader.readId(Bcomp.class, "id", MenuInputType.BCOMP_ID);

        if (bcompDAO.isExists(Bcomp.class, bcompId)) {
            bcompDAO.delete(Bcomp.class, bcompId);
            System.out.println("The entry was successfully deleted from the database");
        } else {
            System.out.println("The entry was not found in the Database.");
        }

        if (jedisOperations.isExists(CachePrefixType.BCOMP.toString(), bcompId)) {
            jedisOperations.delete(CachePrefixType.BCOMP.toString() + bcompId);
            System.out.println("The entry was successfully deleted from the Redis cache");
        } else {
            System.out.println("The entry was not found in the Redis cache.");
        }
    }

    public void jPrint() {
        JedisOperations jedisOperations = new JedisOperations();
        BigDecimal bcompId = DataReader.readId(Bcomp.class, "id", MenuInputType.BCOMP_ID);
        BcompDAOImpl bcompDAO = new BcompDAOImpl();

        if (jedisOperations.isExists(CachePrefixType.BCOMP.toString(), bcompId)) {
            String jBcomp = jedisOperations.get(CachePrefixType.BCOMP.toString() + bcompId);
            System.out.println(jBcomp);
        } else if (bcompDAO.isExists(Bcomp.class, bcompId)) {
            Bcomp bcomp = bcompDAO.get(bcompId);
            System.out.println(bcomp);
            jedisOperations.set(CachePrefixType.BCOMP.toString() + bcomp.getId(), bcomp.toString());
        } else {
            System.out.println("The entry was not found in the Redis cache and in the Oracle database.");
        }
    }

    @Override
    public void print() {

    }

    @Override
    public void delete() {

    }

}
