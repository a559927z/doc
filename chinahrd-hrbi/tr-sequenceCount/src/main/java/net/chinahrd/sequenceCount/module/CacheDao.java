package net.chinahrd.sequenceCount.module;

import java.util.List;

import net.chinahrd.entity.dto.pc.sequenceCount.SequenceCountDto;

/***
 * 
 * @author zhiwei
 * @time 2017年1月18日下午6:07:39
 * @version 20172017年1月18日
 * </p>
 * 符合mybatis
 */
public interface CacheDao {

	List<SequenceCountDto> querySequenceCount();
}
