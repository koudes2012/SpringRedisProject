package com.junyu;

import redis.clients.jedis.Jedis;
 
public class RedisStringJava {
    public static void main(String[] args) {
        // connect to redis server
        Jedis jedis = new Jedis("192.168.226.202");
        System.out.println("connect success");
        System.out.println("get from redis: "+ jedis.get("foo"));
        // set key and value
        jedis.set("key1", "value1");
        // output the value
        System.out.println("get from redis: "+ jedis.get("key1"));
    }
}
