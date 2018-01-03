package operations;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class JedisOperations {

    private final static String HOST = "127.0.0.1";
    private final static int PORT = 6379;

    private static JedisPool pool;

    public JedisOperations() {
        pool = new JedisPool(HOST, PORT);
    }

    public void set(String key, String value) {
        Jedis jedis = pool.getResource();

        try {
            jedis.set(key, value);
            System.out.println("> Added to cache " + key + " " + jedis.get(key));
        } catch (JedisException e) {
            // if something wrong happened, return it back to the pool
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            // return jedis instance to the pool
            if (null != jedis)
                pool.returnResource(jedis);
        }
    }

    /**
     * Adding single value to cache and setting expiration interval
     *
     * @param key
     * @param value
     * @param expTime - expiration interval (in sec)
     */
    public void setExpire(String key, String value, int expTime) {
        Jedis jedis = pool.getResource();

        try {
            jedis.set(key, value);
            jedis.expire(key, expTime);
            System.out.println("> Added to cache " + key + " " + jedis.get(key));
        } catch (JedisException e) {
            // if something wrong happened, return it back to the pool
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            // return jedis instance to the pool
            if (null != jedis)
                pool.returnResource(jedis);
        }
    }

    /**
     * Getting values from cache by the given key
     *
     * @param key
     * @return value from cache
     */
    public String get(String key) {
        Jedis jedis = pool.getResource();
        String res = null;
        try {
            res = jedis.get(key);
            System.out.println(">> Got from cache: " + res);
        } catch (JedisException e) {
            // if something wrong happened, return it back to the pool
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            // return jedis instance to the pool
            if (null != jedis)
                pool.returnResource(jedis);
        }

        return res;
    }

    public List<String> getAllRecordsMatchingPattern(String keyPattern) {
        Jedis jedis = pool.getResource();
        List<String> records = new ArrayList<>();
        try {
            Set<String> keys = jedis.keys(keyPattern);

            for (String key : keys) {
                String s = jedis.get(key);
                records.add(s);
            }

        } catch (JedisException ex) {
            System.err.println("Oops, sorry " + ex.getMessage());
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis)
                pool.returnResource(jedis);
        }
        return records;
    }

    public void delete(String key) {
        Jedis jedis = pool.getResource();
        try {
            jedis.del(key);
            System.out.println(">> Deleted " + key);
        } catch (JedisException ex) {
            System.err.println("Something happened (Jedis, delete) " + ex.getMessage());
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis)
                pool.returnResource(jedis);
        }
    }
}
