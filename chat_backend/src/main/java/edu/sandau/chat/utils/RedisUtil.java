package edu.sandau.chat.utils;

import edu.sandau.chat.entity.user.User;
import edu.sandau.chat.netty.SocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Transactional
@Component("redisUtil")
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${spring.session.timeout}")
    private String timeout;

    /***
     * 将用户添加进在线用户列表
     * @param user
     */
    public void putUser(User user) {
        redisTemplate.opsForHash().putAll(Prefix.USER + user.getId(), JacksonUtil.toMap(user));
        redisTemplate.expire(Prefix.USER + user.getId(), Duration.ofSeconds(Long.parseLong(timeout)));
    }

    /***
     * 检查用户是否在线
     * @param userId
     * @return
     */
    public Boolean checkUser(Integer userId) {
        if(userId == null) {
            throw new SocketException(SocketException.WRONG_FORMAT);
        }

        return redisTemplate.hasKey(Prefix.USER + userId);
    }

    public interface Prefix {
        /***
         * 在线用户列表
         */
        String USER = "user" + ":";
    }
}
