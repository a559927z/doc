package net.chinahrd.clientImg.module;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.chinahrd.entity.dto.pc.clientImg.ClientImgDto;

public interface CacheDao {
	List<ClientImgDto> queryClientExistRelation(@Param("customerId") String customerId);
}
