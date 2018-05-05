
import com.win.shiro.redis.utils.RedisClusterUtils;
import com.win.shiro.redis.utils.RedisTemplateUtils;
import com.win.shiro.redis.utils.api.RedisUtilsApi;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import redis.clients.jedis.JedisCluster;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class RedisClusterTest {

    public static void main(String[] args) {
         //System.setProperty("java.class.path","E:/osworkspace/mavenproject1/src/test/java/redis.xml"); 
        // ApplicationContext context = new FileSystemXmlApplicationContext("E:/osworkspace/mavenproject1/src/test/java/redis.xml");  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("redis.xml");
 

        RedisUtilsApi redisClusterUtils = (RedisUtilsApi) context.getBean("redisClusterUtils");
        int num = 1000;
        String key = "yingjun";
        for (int i = 1; i <= num; i++) {
            // 存数据  
            //jedisCluster.set(key+i, "yingjun"+i);  
            //jedisCluster.setex(key+i, 60, "yingjun"+i);  

            // 取数据  
            System.out.println("RedisClusterSpringTest.main()" + key + i + "=" + redisClusterUtils.get(key + i));

            // 删除数据  
            //jedisCluster.del(key+i);   
            //value = jedisCluster.get(key+i);   
            //log.info(key+i + "=" + value);  
        }

  

    }

}
