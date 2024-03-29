package net.chinahrd.common.redis;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.util.Assert;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.Charset;

/**
 * Redis的序列换
 * 
 * @author guanjian
 *
 */
public class StringRedisSerializer implements RedisSerializer<Object> {
    
    private final Charset charset;
 
    private final String target = "\"";
 
    private final String replacement = "";
 
    public StringRedisSerializer() {
        this(Charset.forName("UTF8"));
    }
 
    public StringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }
 
    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }
 
    @Override
    public byte[] serialize(Object object) {
        String string = JSON.toJSONString(object);
        if (string == null) {
            return null;
        }
        string = string.replace(target, replacement);
        return string.getBytes(charset);
    }
}