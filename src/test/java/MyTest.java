import com.example.demo.service.GoodsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MyTest {
    @Test
    public void test(){
        GoodsService goodsService = new GoodsService();
        goodsService.listGoodsVo();
    }
}
