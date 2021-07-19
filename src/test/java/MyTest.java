import com.example.demo.redis.RedisService;
import com.example.demo.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MyTest {

    @Test
    public void test() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        short a = 1;
        System.out.println(list.get(2));

    }
}
