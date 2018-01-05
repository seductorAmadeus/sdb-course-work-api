package operations;

import daoImpl.BcompDAOImpl;
import daoImpl.UserSessionDAOImpl;
import entities.Bcomp;
import entities.UserSession;
import utils.CachePrefixType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class BcompOperations implements RedisGenericOperations, DatabaseGenericOperations {

    @Deprecated
    public void add() {
        Bcomp bcomp = new Bcomp();
        BcompDAOImpl bcompDAO = new BcompDAOImpl();
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UserSession userSession;

        BigDecimal userSessionId = DataReader.readUserSessionId();
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

    public void jAdd() {
        JedisOperations jedisOperations = new JedisOperations();

        Bcomp bcomp = new Bcomp();
        BcompDAOImpl bcompDAO = new BcompDAOImpl();
        UserSessionDAOImpl userSessionDAO = new UserSessionDAOImpl();
        UserSession userSession;

        BigDecimal userSessionId = DataReader.readUserSessionId();
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

        BigDecimal bcompId = DataReader.readBcompId();

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

        BigDecimal bcompId = DataReader.readBcompId();

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

        BigDecimal bcompId = DataReader.readBcompId();

        // TODO: удалить несовпадение проверок
        if (bcompDAO.isExists(Bcomp.class, bcompId)) {
            bcompDAO.delete(Bcomp.class, bcompId);
            // TODO: add exception checking
            jedisOperations.delete(CachePrefixType.BCOMP.toString() + bcompId);
        } else {
            System.out.println("The specified bcomp id was not found in the Redis cache. Check it out correctly and try again");
        }
    }

    public void jPrint() {
        JedisOperations jedisOperations = new JedisOperations();

        BigDecimal bcompId = DataReader.readBcompId();

        String bcomp = jedisOperations.get(CachePrefixType.BCOMP.toString() + bcompId);

        if (bcomp != null) {
            System.out.println(bcomp);
        } else {
            System.out.println("The specified bcomp id was not found in the Redis cache. Check it out correctly and try again");
        }

    }

    @Override
    public void print() {

    }

    @Override
    public void delete() {

    }

}
