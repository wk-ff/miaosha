import com.example.demo.redis.RedisService;
import com.example.demo.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MyTest {

    @Test
    public void test(){
        Map<String, Integer> map = new HashMap<>();
        map.put("aaa", 2);
        Map<String, Integer> tmp = new HashMap<>();
        tmp.put("aaa", 1);
        map.putAll(tmp);
        String s = "eeaabb";

        System.out.println(map.get("aaa"));

    }
}
