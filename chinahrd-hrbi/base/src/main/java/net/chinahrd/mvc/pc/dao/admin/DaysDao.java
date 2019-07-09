package net.chinahrd.mvc.pc.dao.admin;

import net.chinahrd.entity.dto.pc.admin.DaysDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhiwei 20161130
 */
@Repository("daysDao")
public interface DaysDao {
    /**
     * 查集合
     *
     * @param days
     * @return
     */
    List<DaysDto> queryDays(DaysDto days);

    /**
     * 查某种类型节假日的总天数
     *
     * @param days dto
     * @return
     */
    Integer getCount(DaysDto days);

}
