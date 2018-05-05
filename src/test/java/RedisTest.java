
import com.win.shiro.redis.utils.RedisClusterUtils;
import com.win.shiro.redis.utils.RedisTemplateUtils;
import com.win.shiro.redis.utils.api.RedisUtilsApi;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.SerializationUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class RedisTest {

    public static void main(String[] args) {
         //System.setProperty("java.class.path","E:/osworkspace/mavenproject1/src/test/java/redis.xml"); 
        // ApplicationContext context = new FileSystemXmlApplicationContext("E:/osworkspace/mavenproject1/src/test/java/redis.xml");  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("redisTemplateSingle.xml");
 

        RedisUtilsApi redisUtils = (RedisUtilsApi) context.getBean("redisTemplateUtils");
        int num = 1000;
        String key = "yingjun";
        for (int i = 1; i <= num; i++) {
            // 存数据  
            redisUtils.set(key+i, new TestModel(i,"ssss"));  
            //jedisCluster.setex(key+i, 60, "yingjun"+i);  

            // 取数据  
            System.out.println("RedisSpringTest.main()" + key + i + "=" +  redisUtils.get(key + i));

            // 删除数据  
            //jedisCluster.del(key+i);   
            //value = jedisCluster.get(key+i);   
            //log.info(key+i + "=" + value);  
        }

  

    }

}
