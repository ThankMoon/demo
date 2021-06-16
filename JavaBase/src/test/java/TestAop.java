import aop.CalcService;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * AOP测试controller
 *
 * @author zjh
 * @version v1.0
 * @date 2021/5/23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAop {
    @Resource
    private CalcService calcService;

    @Test
    public void testAop4() {
        System.out.println("Spring版本" + SpringVersion.getVersion() + "SpringBoot版本" + SpringBootVersion.getVersion());
        calcService.div(10, 2);
    }
}
