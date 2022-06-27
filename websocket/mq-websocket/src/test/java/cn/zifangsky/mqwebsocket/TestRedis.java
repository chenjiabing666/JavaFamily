package cn.zifangsky.mqwebsocket;

import cn.zifangsky.mqwebsocket.common.Constants;
import cn.zifangsky.mqwebsocket.enums.ExpireEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

/**
 * 测试redis的基本用法
 * @author zifangsky
 * @date 2018/7/27
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestRedis {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void testSelect(){
		BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(Constants.REDIS_UNREAD_MSG_PREFIX + "admin");
//        boundValueOperations.rightPush("a");
//        boundValueOperations.rightPush("b");
//        boundValueOperations.rightPush("c");
        boundValueOperations.leftPushAll("m","n");

		boundValueOperations.expire(ExpireEnum.UNREAD_MSG.getTime(),ExpireEnum.UNREAD_MSG.getTimeUnit());

		List<Object> list = boundValueOperations.range(0, -1);
		list.forEach(System.out::print);
		redisTemplate.delete(Constants.REDIS_UNREAD_MSG_PREFIX + "admin");
	}

    @Test
    public void testSet(){
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();

        opsForSet.add(Constants.REDIS_WEBSOCKET_USER_SET,"hahaha","admin");

        //1. 遍历
        Set<Object> set = opsForSet.members(Constants.REDIS_WEBSOCKET_USER_SET);

        //2. 判断是否是set成员
        System.out.println(opsForSet.isMember(Constants.REDIS_WEBSOCKET_USER_SET,"admin"));
        System.out.println(opsForSet.isMember(Constants.REDIS_WEBSOCKET_USER_SET,"zifangsky"));

        //3. 移除某个值
        opsForSet.remove(Constants.REDIS_WEBSOCKET_USER_SET,"admin");

        //4. 再次判断
        System.out.println(opsForSet.isMember(Constants.REDIS_WEBSOCKET_USER_SET,"admin"));
    }

}
