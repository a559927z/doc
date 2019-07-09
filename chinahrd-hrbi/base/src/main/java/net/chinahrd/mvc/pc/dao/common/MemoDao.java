package net.chinahrd.mvc.pc.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.entity.dto.pc.common.MemoCreateDto;
import net.chinahrd.entity.dto.pc.common.MemoResultDto;
import net.chinahrd.entity.dto.pc.common.MemoStatusDto;

/**
 * 备忘录Dao接口类
 * Created by wqcai on 15/6/30.
 */
@Repository("memoDao")
public interface MemoDao {

    /**
     * 查询备忘录信息
     * @param quotaId
     * @param organizationId
     * @param empId
     * @return
     */
    List<MemoResultDto> findAllMemo(@Param("customerId")String customerId,@Param("quotaId")String quotaId,@Param("organizationId")String organizationId,@Param("empId")String empId);

    /**
     * 添加备忘录信息
     * @param dto
     */
    void addMemo(@Param("dto") MemoCreateDto dto);

    /**
     * 添加备忘录状态信息
     * @param statusDto
     */
    void addMemoStatus(@Param("dto") MemoStatusDto statusDto);

    /**
     * 删除备忘录信息
     * @param memoId
     */
    void deleteMemo(@Param("customerId")String customerId,@Param("memoId")String memoId);


    List<ItemDto> findMemoTips(@Param("customerId")String customerId);
}
