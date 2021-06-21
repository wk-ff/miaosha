import com.example.demo.service.GoodsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MyTest {
    @Test
    public void test(){
        String str = String.format("%d:%02d", 11, 21);
        System.out.println(str);
    }
}
