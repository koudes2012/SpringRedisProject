package com.junyu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;  

public class TestRedis {  
      
    public static void main(String[] args) throws Exception {  
        @SuppressWarnings("resource")  
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext-redis.xml");  
        RedisUtil redisUtil=(RedisUtil) context.getBean("redisUtil");  
          
        //=====================testString======================  
        redisUtil.set("age", 30);
        // set String test
        System.out.println(redisUtil.get("age"));  
        
		User user = new User("huang", 60, "shanghai", null,null);
		redisUtil.set("user", user);
		// set Object test
		Object object = redisUtil.get("user");
		System.out.println(object);  
    }

}  