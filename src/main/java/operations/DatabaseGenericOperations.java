package operations;

import enums.CachePrefixType;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

public abstract class DatabaseGenericOperations<T extends Serializable> {
    void printAll() {

    }

    void print() {

    }

    void update() {

    }

    void delete() {

    }

    void create() {

    }

    public void synchronize(Class<?> aClassDAOImpl, Class tClass, CachePrefixType cachePrefixType) {
        JedisOperations jedisOperations = new JedisOperations();

        Method method;
        Object aClassDAOInstance = null;
        try {
            aClassDAOInstance = aClassDAOImpl.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


        List<String> keysList = jedisOperations.getAllKeys(cachePrefixType.toString() + "*");
        // Delete unnecessary entries in Redis, if it's missing in the database
        for (String aKey : keysList) {
            String temStrId = aKey.substring(aKey.lastIndexOf(":") + 1);
            BigDecimal tempId = new BigDecimal(temStrId);
            try {
                method = aClassDAOImpl.getMethod("isExists", tClass.getClass(), BigDecimal.class);
                if (!(boolean) method.invoke(aClassDAOInstance, tClass, tempId)) {
                    jedisOperations.delete(aKey);
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    abstract void jPrintAll();

    abstract void jPrint();

    abstract void jUpdate();

    abstract void jDelete();

    abstract void jCreate();
}
