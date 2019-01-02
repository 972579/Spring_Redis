package com.redis.test;

import com.redis.bean.User;
import com.redis.service.UserService;
import com.redis.util.SerializeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;


@ContextConfiguration({ "classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class Test1 {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    private RedisConnection connection;


    @Test
    public void init(){
        //可直接操作redis 的队列、集合、列表等类型(方法名与redis命令对应)
        connection = redisTemplate.getRequiredConnectionFactory().getConnection();

        //使用序列化工具类序列化对象
        connection.setNX("测试".getBytes(), SerializeUtil.serialize(new User("测试","名称",1)));

        Set<byte[]> keys = connection.keys("*".getBytes());
        for (byte[] key: keys) {
            System.out.println(SerializeUtil.unSerialize(connection.get(key)));
        }

        System.out.println("-------------------------------");
        System.out.println(SerializeUtil.unSerialize(connection.get("测试".getBytes())));
    }


    @Test
    public void save(){
        User user = new User("测试","abc",122);
        userService.save(user.getId(),user);
    }

    @Test
    public void get(){
        User user = userService.get("测试");
        System.out.println(user);
    }


    @Test
    public void update(){
        User user = userService.get("测试");
        user.setAge(55);
        System.out.println(userService.update(user.getId(),user));
    }

    @Test
    public void delete(){
        userService.delete("测试");
    }


}
