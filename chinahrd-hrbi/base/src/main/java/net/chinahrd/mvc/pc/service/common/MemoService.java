package net.chinahrd.mvc.pc.service.common;

import java.util.List;

import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.entity.dto.pc.common.MemoCreateDto;
import net.chinahrd.entity.dto.pc.common.MemoResultDto;

/**
 * 备忘录Service接口类
 * Created by wqcai on 15/6/30.
 */
public interface MemoService {

    /**
     * 查询备忘录信息
     * @param customerId 客户ID
     * @param quotaId 指标ID
     * @param organizationId 机构ID
     * @param empId 员工ID
     * @return
     */
    List<MemoResultDto> findAllMemo(String customerId,String quotaId,String organizationId,String empId);

    /**
     * 添加备忘录信息
     * @param dto
     */
    void addMemo(MemoCreateDto dto);

    /**
     * 删除备忘录信息
     * @param memoId
     */
    void deleteMemo(String customerId,String memoId);

    /**
     * 更改备忘录为已读状态
     * @param empId
     */
    void updateMemoIsReadStatus(String customerId,String memoId,String empId);

    /**
     * 查询锦囊信息
     * @param customerId
     * @return
     */
    List<ItemDto> findMemoTips(String customerId);
}
