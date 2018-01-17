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

    public void synchronize(Class<?> aClassImpl, Class tClass, CachePrefixType cachePrefixType) {
        JedisOperations jedisOperations = new JedisOperations();

        Method method = null;

        List<String> keysList = jedisOperations.getAllKeys(cachePrefixType.toString() + "*");
        // удаляем лишние записи в Redis, если таковые отсутствуют в БД
        for (String aKeysList : keysList) {
            String temStrId = aKeysList.substring(aKeysList.lastIndexOf(":") + 1);
            BigDecimal tempId = new BigDecimal(temStrId);
            try {
                method = aClassImpl.getMethod("isExists", tClass.getClass(), BigDecimal.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                try {
                    if (!(boolean) method.invoke(aClassImpl.newInstance(), tClass, tempId)) {
                        jedisOperations.delete(aKeysList);
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
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
