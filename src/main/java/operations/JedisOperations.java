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
        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
        } catch (JedisException e) {
            System.err.println("Oops, something was wrong" + e.getMessage());
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

    public void setExpire(String key, String value, int expTime) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
            jedis.expire(key, expTime);
        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
        } catch (JedisException e) {
            System.err.println("Oops, something was wrong" + e.getMessage());
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

    public String get(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.get(key);
        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
        } catch (JedisException e) {
            System.err.println("Oops, something was wrong" + e.getMessage());
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
            System.err.println("Oops, something was wrong" + exp.getMessage());
        } finally {
            // return jedis instance to the pool
            if (null != jedis)
                pool.returnResource(jedis);
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
            System.err.println("Oops, something was wrong" + ex.getMessage());
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
            System.err.println("Oops, something was wrong" + ex.getMessage());
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
            System.err.println("Oops, something was wrong" + ex.getMessage());
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
        } catch (JedisConnectionException exp) {
            System.out.println(exp.getMessage() + ". There is no connection to " + HOST + ":" + PORT);
        } catch (JedisException ex) {
            System.err.println("Oops, something was wrong" + ex.getMessage());
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
