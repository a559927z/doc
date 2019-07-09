package net.chinahrd.clientImg.module;

import java.util.List;

import net.chinahrd.core.cache.CacheBlock;
import net.chinahrd.core.cache.CacheBlockConstructor;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.pc.clientImg.ClientImgDto;

/**
 * 
 * @author jxzhang on 2017年1月12日
 * @Verdion 1.0 版本
 */
public class Cache {

	public static CacheBlock<List<ClientImgDto>> queryClientExistRelation = new CacheBlockConstructor<List<ClientImgDto>>(
			"queryClientExistRelation").getDefaultBlock(EisWebContext.getCustomerId());

}
