package com.junyu;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;  
  
/** 
 * base on spring and redisTemplate tool
 * operation about hash begin with h
 * operation about Set  begin with s
 * operation about List begin with l
 */  
public class RedisUtil {  
  
    private RedisTemplate<String, Object> redisTemplate;  
      
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {  
        this.redisTemplate = redisTemplate;  
    }  
    //=============================common============================  
    /** 
     * designate expire time 
     * @param key
     * @param time second
     * @return 
     */  
    public boolean expire(String key,long time){  
        try {  
            if(time>0){  
                redisTemplate.expire(key, time, TimeUnit.SECONDS);  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * get expire time 
     * @param key :can not be null 
     * @return time (second) : return 0 means Never Expires
     */  
    public long getExpire(String key){  
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);  
    }  
      
    /** 
     * determine key exist or not 
     * @param key
     * @return true exist, false not exist 
     */  
    public boolean hasKey(String key){  
        try {  
            return redisTemplate.hasKey(key);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * delete cache
     * @param key : one or more keys at once
     */  
    @SuppressWarnings("unchecked")  
    public void del(String ... key){  
        if(key!=null&&key.length>0){  
            if(key.length==1){  
                redisTemplate.delete(key[0]);  
            }else{  
                redisTemplate.delete(CollectionUtils.arrayToList(key));  
            }  
        }  
    }  
      
    //============================String=============================  
    /** 
     * get cache
     * @param key
     * @return value
     */  
    public Object get(String key){  
        return key==null?null:redisTemplate.opsForValue().get(key);  
    }  
      
    /** 
     * put cache
     * @param key
     * @param value
     * @return true success, false failure 
     */  
    public boolean set(String key,Object value) {  
         try {  
            redisTemplate.opsForValue().set(key, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * put cache and set time
     * @param key
     * @param value
     * @param time(second) if time<=0, there will no time limitation
     * @return true success, false failure 
     */  
    public boolean set(String key,Object value,long time){  
        try {  
            if(time>0){  
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);  
            }else{  
                set(key, value);  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * increment
     * @param key
     * @param by delta
     * @return 
     */  
    public long incr(String key, long delta){    
        if(delta<0){  
            throw new RuntimeException("delta must bigger than 0");  
        }  
        return redisTemplate.opsForValue().increment(key, delta);  
    }  
      
    /** 
     * decrease
     * @param key
     * @param by delta 
     * @return 
     */  
    public long decr(String key, long delta){    
        if(delta<0){  
            throw new RuntimeException("delta must bigger than 0");  
        }  
        return redisTemplate.opsForValue().increment(key, -delta);    
    }    
      
    //================================Map=================================  
    /** 
     * HashGet 
     * @param key (can not be null)
     * @param item (can not be null)
     * @return object
     */  
    public Object hget(String key,String item){  
        return redisTemplate.opsForHash().get(key, item);  
    }  
      
    /** 
     * get all the keys and values related to hashKey
     * @param key
     * @return map
     */  
    public Map<Object,Object> hmget(String key){  
        return redisTemplate.opsForHash().entries(key);  
    }  
      
    /** 
     * HashSet 
     * @param key
     * @param map
     * @return true:success, false:failure
     */  
    public boolean hmset(String key, Map<String,Object> map){    
        try {  
            redisTemplate.opsForHash().putAll(key, map);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * HashSet and set time
     * @param key
     * @param map
     * @param time(second)
     * @return true:success, false:failure
     */  
    public boolean hmset(String key, Map<String,Object> map, long time){    
        try {  
            redisTemplate.opsForHash().putAll(key, map);  
            if(time>0){  
                expire(key, time);  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * put data in a hash table
     * @param key
     * @param item
     * @param value
     * @return true:success, false:failure
     */  
    public boolean hset(String key,String item,Object value) {  
         try {  
            redisTemplate.opsForHash().put(key, item, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * put data in a hash table
     * @param key
     * @param item
     * @param value
     * @param time(second)
     * @return true:success, false:failure
     */  
    public boolean hset(String key,String item,Object value,long time) {  
         try {  
            redisTemplate.opsForHash().put(key, item, value);  
            if(time>0){  
                expire(key, time);  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * delete value from hash table
     * @param key (can not be null)
     * @param item (can not be null)
     */  
    public void hdel(String key, Object... item){    
        redisTemplate.opsForHash().delete(key,item);  
    }   
      
    /** 
     * determine if the value exist in hash table or not
     * @param key (can not be null)
     * @param item (can not be null)
     * @return true:exist, false:not exist 
     */  
    public boolean hHasKey(String key, String item){  
        return redisTemplate.opsForHash().hasKey(key, item);  
    }   
      
    /** 
     * hash increment
     * @param key
     * @param item
     * @param by delta
     * @return 
     */  
    public double hincr(String key, String item,double by){    
        return redisTemplate.opsForHash().increment(key, item, by);  
    }  
      
    /** 
     * hash decrease
     * @param key
     * @param item
     * @param by delta
     * @return 
     */  
    public double hdecr(String key, String item,double by){    
        return redisTemplate.opsForHash().increment(key, item,-by);    
    }    
      
    //============================set=============================  
    /** 
     * get Set according to the key
     * @param key
     * @return 
     */  
    public Set<Object> sGet(String key){  
        try {  
            return redisTemplate.opsForSet().members(key);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
      
    /** 
     * search a value from a set
     * @param key
     * @param value
     * @return true:exist, false:exist
     */  
    public boolean sHasKey(String key,Object value){  
        try {  
            return redisTemplate.opsForSet().isMember(key, value);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * put data in the set
     * @param key
     * @param values
     * @return success number
     */  
    public long sSet(String key, Object...values) {  
        try {  
            return redisTemplate.opsForSet().add(key, values);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  
      
    /** 
     * put data in the set
     * @param key
     * @param time(second)
     * @param values
     * @return success number
     */  
    public long sSetAndTime(String key,long time,Object...values) {  
        try {  
            Long count = redisTemplate.opsForSet().add(key, values);  
            if(time>0) expire(key, time);  
            return count;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  
      
    /** 
     * get set length in cache
     * @param key
     * @return 
     */  
    public long sGetSetSize(String key){  
        try {  
            return redisTemplate.opsForSet().size(key);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  
      
    /** 
     * remove values
     * @param key
     * @param values
     * @return remove number
     */  
    public long setRemove(String key, Object ...values) {  
        try {  
            Long count = redisTemplate.opsForSet().remove(key, values);  
            return count;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  
    //===============================list=================================  
      
    /** 
     * get list in cache
     * @param key
     * @param start
     * @param end
     * @return 
     */  
    public List<Object> lGet(String key,long start, long end){  
        try {  
            return redisTemplate.opsForList().range(key, start, end);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
      
    /** 
     * get list size 
     * @param key
     * @return 
     */  
    public long lGetListSize(String key){  
        try {  
            return redisTemplate.opsForList().size(key);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  
      
    /** 
     * get object in a list by index
     * @param key
     * @param index
     * @return 
     */  
    public Object lGetIndex(String key,long index){  
        try {  
            return redisTemplate.opsForList().index(key, index);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
      
    /** 
     * put Object into cache
     * @param key
     * @param value
     * @return 
     */  
    public boolean lSet(String key, Object value) {  
        try {  
            redisTemplate.opsForList().rightPush(key, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * put Object into cache
     * @param key
     * @param value
     * @param time(second)
     * @return 
     */  
    public boolean lSet(String key, Object value, long time) {  
        try {  
            redisTemplate.opsForList().rightPush(key, value);  
            if (time > 0) expire(key, time);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * put list into cache
     * @param key
     * @param value
     * @return 
     */  
    public boolean lSet(String key, List<Object> value) {  
        try {  
            redisTemplate.opsForList().rightPushAll(key, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * put list into cache
     * @param key
     * @param value
     * @param time(second)
     * @return 
     */  
    public boolean lSet(String key, List<Object> value, long time) {  
        try {  
            redisTemplate.opsForList().rightPushAll(key, value);  
            if (time > 0) expire(key, time);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * According to index, modify data in the list
     * @param key
     * @param index
     * @param value
     * @return 
     */  
    public boolean lUpdateIndex(String key, long index,Object value) {  
        try {  
            redisTemplate.opsForList().set(key, index, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }   
      
    /** 
     * remove multiple values
     * @param key
     * @param count remove number
     * @param value
     * @return the number being removed
     */  
    public long lRemove(String key,long count,Object value) {  
        try {  
            Long remove = redisTemplate.opsForList().remove(key, count, value);  
            return remove;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;
        }
    }

} 
