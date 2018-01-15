package operations;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JedisOperations {

    private final static String HOST = "127.0.0.1";
    private final static int PORT = 6379;

    private JedisPool pool;

    public JedisOperations() {
        pool = new JedisPool(HOST, PORT);
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
            System.out.println("> Added to cache " + key + " " + jedis.get(key));
        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
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
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
            jedis.expire(key, expTime);
            System.out.println("> Added to cache " + key + " " + jedis.get(key));
        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
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
        Jedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.get(key);
            System.out.println(">> Got from cache: " + res);
        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
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

    public boolean isExists(String keyPattern, BigDecimal id) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
        } catch (JedisException exp) {
            //
        }
        if (jedis == null) return false;
        return jedis.exists(keyPattern + id);
    }

    public List<String> getAllKeys(String pattern) {
        Jedis jedis = null;
        List<String> keys = new ArrayList<>();
        try {
            jedis = pool.getResource();
            Set<String> tempKeys = jedis.keys(pattern);

            keys.addAll(tempKeys);

        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
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
        return keys;
    }

    public List<BigDecimal> getAllIdsMatchingPattern(String keyPattern) {
        Jedis jedis = null;
        // pattern:id
        List<BigDecimal> id = new ArrayList<>();
        try {
            jedis = pool.getResource();
            Set<String> keys = jedis.keys(keyPattern);

            for (String key : keys) {
                String s = key.substring(key.lastIndexOf(":") + 1);
                // TODO: add exception checking
                id.add(new BigDecimal(s));
            }

        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
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
        return id;
    }

    public List<String> getAllRecordsMatchingPattern(String keyPattern) {
        Jedis jedis = null;
        List<String> records = new ArrayList<>();
        try {
            jedis = pool.getResource();
            Set<String> keys = jedis.keys(keyPattern);

            for (String key : keys) {
                String s = jedis.get(key);
                records.add(s);
            }

        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
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
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
            System.out.println(">> Deleted " + key);
        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
        } catch (JedisException ex) {
            System.err.println("Something happened (Jedis, deleteByBcompSettingsId) " + ex.getMessage());
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
