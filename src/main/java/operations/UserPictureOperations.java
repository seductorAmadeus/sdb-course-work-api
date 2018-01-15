package operations;

import daoImpl.UserPictureDAOImpl;
import daoImpl.UserProfileDAOImpl;
import entities.UserPicture;
import entities.UserProfile;
import enums.CachePrefixType;
import enums.MenuInputType;
import utils.DataReader;

import java.math.BigDecimal;
import java.util.List;

public class UserPictureOperations extends DatabaseGenericOperations {

    @Override
    void jPrintAll() {
        JedisOperations jedisOperations = new JedisOperations();
        List<String> records;
        try {
            records = jedisOperations.getAllRecordsMatchingPattern(CachePrefixType.USER_PICTURE + "*");
            if (records.size() == 0) {
                throw new NullPointerException();
            } else {
                for (String record : records) {
                    System.out.println(record);
                }
            }
        } catch (NullPointerException exp) {
            System.out.println("User's pictures list is empty. No user's pictures has been created/added in Redis cache");
        }
    }

    @Override
    void jPrint() {
        JedisOperations jedisOperations = new JedisOperations();
        BigDecimal userPictureId = DataReader.readId(UserPicture.class, "id", MenuInputType.USER_PICTURE_ID);
        UserPictureDAOImpl userPictureDAO = new UserPictureDAOImpl();

        if (jedisOperations.isExists(CachePrefixType.USER_PICTURE.toString(), userPictureId)) {
            String jUserPicture = jedisOperations.get(CachePrefixType.USER_PICTURE.toString() + userPictureId);
            System.out.println(jUserPicture);
        } else if (userPictureDAO.isExists(UserPicture.class, userPictureId)) {
            UserPicture userPicture = userPictureDAO.get(userPictureId);
            System.out.println(userPicture);
            jedisOperations.set(CachePrefixType.USER_PICTURE.toString() + userPicture.getId(), userPicture.toString());
        } else {
            System.out.println("The entry was not found in the Redis cache and in the Oracle database.");
        }
    }

    @Override
    @Deprecated
    void jUpdate() {

    }

    @Override
    void jDelete() {
        JedisOperations jedisOperations = new JedisOperations();
        UserPictureDAOImpl userPictureDAO = new UserPictureDAOImpl();

        BigDecimal userPictureId = DataReader.readId(UserPicture.class, "id", MenuInputType.USER_PICTURE_ID);

        if (userPictureDAO.isExists(UserPicture.class, userPictureId)) {
            userPictureDAO.delete(UserPicture.class, userPictureId);
            System.out.println("The entry was successfully deleted from the database");
        } else {
            System.out.println("The entry was not found in the Database.");
        }

        if (jedisOperations.isExists(CachePrefixType.USER_PICTURE.toString(), userPictureId)) {
            jedisOperations.delete(CachePrefixType.USER_PICTURE.toString() + userPictureId);
            System.out.println("The entry was successfully deleted from the Redis cache");
        } else {
            System.out.println("The entry was not found in the Redis cache.");
        }
    }

    @Override
    void jCreate() {
        JedisOperations jedisOperations = new JedisOperations();

        UserPicture userPicture;
        UserPictureDAOImpl userPictureDAO = new UserPictureDAOImpl();
        UserProfileDAOImpl userProfileDAO = new UserProfileDAOImpl();

        userPicture = DataReader.readUserPicture();
        BigDecimal userProfileId = DataReader.readId(UserProfile.class, "profileId", MenuInputType.USER_PROFILE_ID);
        if (userProfileDAO.isExists(UserProfile.class, userProfileId)) {
            userPicture.setId(userProfileId);
            try {
                BigDecimal userPictureId = userPictureDAO.create(userPicture);

                if (userPictureId != null) {
                    jedisOperations.set(CachePrefixType.USER_PICTURE.toString() + userPicture.getId(), userPicture.toString());
                }
            } catch (NullPointerException exp) {
                System.out.println("Something happened, sorry");
            }
        }
    }

}
