package net.chinahrd.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.chinahrd.constants.RedisConstants;
import net.chinahrd.utils.JedisUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Title: ${type_name} <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年05月15日 12:28
 * @Verdion 1.0 版本
 * ${tags}
 */

@Controller
@RequestMapping("/redis/")
public class RedisMgrController {

    private JedisUtils jedis = new JedisUtils(RedisConstants.REDIS_HOST, RedisConstants.PROT, RedisConstants.AUTH);

    /**
     * 跳转redis管理页
     *
     * @return
     */
    @RequestMapping(value = "index.do")
    public String redisMgr() {
        return "redisMgr";
    }

    /**
     * 保存
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save.do")
    public String save(String key, String value) {
        return jedis.set(key, value);
    }

    /**
     * 获取值
     *
     * @param queryKey
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get.do")
    public String get(String queryKey) {
        return jedis.get(queryKey);
    }

    /**
     * 删除值
     *
     * @param delKey
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "del.do")
    public String del(String delKey) {
        Long del = jedis.del(delKey);
        return del > 0 ? "成功删除" : "删除失败";
    }


    /**
     * 刷新
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "allKeys.do")
    public List<Map<String, Object>> allKeys() {
        List<Map<String, Object>> rsList = Lists.newArrayList();
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            Map<String, Object> item = Maps.newHashMap();
            item.put("key", key);
            item.put("value", jedis.get(key));
            rsList.add(item);
        }
        return rsList;
    }


}
