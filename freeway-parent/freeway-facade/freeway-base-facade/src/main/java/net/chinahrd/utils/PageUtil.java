package net.chinahrd.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.chinahrd.common.PageData;
import net.chinahrd.common.PageRequest;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * <p>
 *  分页工具类
 * </p>
 *
 * @author bright
 * @since 2019/3/8 10:45
 */
public abstract class PageUtil {

    public static void startPage(PageRequest dataReq) {
        PageHelper.startPage(dataReq.getPageNum(), dataReq.getPageSize(),
                true, false, false);
    }

    public static <T> PageData<T> convert(List<T> list) {
        PageData<T> pageData = new PageData<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        BeanUtils.copyProperties(pageInfo, pageData);
        return pageData;
    }
}
