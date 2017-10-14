package org.zhubao;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.zhubao.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    private ValueOperations<String, String> valueOperations;

    private ListOperations<String, String> listOperations;

    private SetOperations<String, String> setOperations;

    private ValueOperations<String, User> hashOperations;

    private static String USER_PREFIX = "USER_";

    @Before
    public void initContext() {

        valueOperations = stringRedisTemplate.opsForValue();

        listOperations = stringRedisTemplate.opsForList();

        setOperations = stringRedisTemplate.opsForSet();

        hashOperations = redisTemplate.opsForValue();
    }

    @Test
    public void testString() {
        valueOperations.set("string", "jason");
        Assert.assertEquals("jason", valueOperations.get("string"));
    }

    @Test
    public void testList() {
        listOperations.leftPush("list", "a");
        listOperations.leftPush("list", "b");
        listOperations.leftPush("list", "c");
        Assert.assertArrayEquals(Arrays.asList("c", "b", "a").toArray(), listOperations.range("list", 0, 2).toArray());
    }

    @Test
    public void testSet() {
        setOperations.add("set", "jason", "owen", "marco");
        Assert.assertEquals(3, setOperations.size("set").intValue());
    }

    @Test
    public void testHash() {
        User user = new User(10001, "Jason", 28, "zrb0307@gamil.com");
        hashOperations.set(USER_PREFIX + user.getId(), user);
        User queryUser = hashOperations.get(USER_PREFIX + user.getId());
        System.out.println(queryUser);
    }

}
