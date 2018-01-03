package operations;

import daos.BcompDAO;
import daos.UserSessionDAO;
import entities.Bcomp;
import entities.UserSession;
import utils.CachePrefixType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class BcompOperations {

    @Deprecated
    public void addEmptyBcomp() {
        Bcomp bcomp = new Bcomp();
        BcompDAO bcompDAO = new BcompDAO();
        UserSessionDAO userSessionDAO = new UserSessionDAO();
        UserSession userSession = null;

        BigDecimal userSessionId = DataReader.readUserSessionId();
        try {
            // checking that session exists
            userSession = userSessionDAO.getUserSessionById(userSessionId);
            if (userSession == null) {
                throw new NullPointerException();
            }
            bcomp.setUserSession(userSession);
            bcompDAO.insert(bcomp);
        } catch (NullPointerException exp) {
            System.out.println("The specified session was not created in the system. Check it out correctly and try again");
        }
    }

    @Deprecated
    public void printAllBcomp() {
        BcompDAO bcompDAO = new BcompDAO();
        try {
            List<Bcomp> bcompList = bcompDAO.getList();
            if (bcompList.size() == 0) throw new NullPointerException();
        } catch (NullPointerException exp) {
            System.out.println("Bcomp list is empty. No bcomp has been created");
        }

    }

    public void jPrintAllBcomp() {
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

    public void jAddEmptyBcomp() {
        JedisOperations jedisOperations = new JedisOperations();

        Bcomp bcomp = new Bcomp();
        BcompDAO bcompDAO = new BcompDAO();
        UserSessionDAO userSessionDAO = new UserSessionDAO();
        UserSession userSession = null;

        BigDecimal userSessionId = DataReader.readUserSessionId();
        try {
            // checking that session exists
            userSession = userSessionDAO.getUserSessionById(userSessionId);
            if (userSession == null) {
                throw new NullPointerException();
            }
            bcomp.setUserSession(userSession);
            BigDecimal newBcompId = bcompDAO.insert(bcomp);
            // TODO: add insert checking
            // if insert in db was successful
            if (newBcompId != null) {
                bcomp.setId(newBcompId);
                jedisOperations.set(CachePrefixType.BCOMP.toString() + bcomp.getId(), bcomp.toString());
            }
        } catch (NullPointerException exp) {
            System.out.println("The specified session was not created in the system. Check it out correctly and try again");
        }
    }

    @Deprecated
    public void updateBcomp() {
        Bcomp bcomp;
        BcompDAO bcompDAO = new BcompDAO();

        BigDecimal bcompId = DataReader.readBcompId();

        if (bcompDAO.checkExistsById(bcompId)) {
            bcomp = bcompDAO.getById(bcompId);
            DataReader.initBcomp(bcomp);
            bcompDAO.update(bcompId, bcomp);
        } else {
            System.out.println("The specified bcomp id was not found in the system. Check it out correctly and try again");
        }
    }

    public void jUpdateBcomp() {
        JedisOperations jedisOperations = new JedisOperations();

        Bcomp bcomp;
        BcompDAO bcompDAO = new BcompDAO();

        BigDecimal bcompId = DataReader.readBcompId();

        if (bcompDAO.checkExistsById(bcompId)) {
            bcomp = bcompDAO.getById(bcompId);
            DataReader.initBcomp(bcomp);
            bcompDAO.update(bcompId, bcomp);
            // TODO: add exception checking?
            // необходимо ли проверять существует ли, может быть просто добавится новое значение
            jedisOperations.set(CachePrefixType.BCOMP.toString() + bcomp.getId(), bcomp.toString());
        } else {
            System.out.println("The specified bcomp id was not found in the system. Check it out correctly and try again");
        }
    }

    public void jDeleteBcomp() {
        JedisOperations jedisOperations = new JedisOperations();
        BcompDAO bcompDAO = new BcompDAO();

        BigDecimal bcompId = DataReader.readBcompId();

        if (bcompDAO.checkExistsById(bcompId)) {
            bcompDAO.deleteBcomp(bcompId);
            // TODO: add exception checking
            jedisOperations.delete(CachePrefixType.BCOMP.toString() + bcompId);
        } else {
            System.out.println("The specified bcomp id was not found in the system. Check it out correctly and try again");
        }
    }
}
