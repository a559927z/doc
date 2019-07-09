//package net.chinahrd.positionCompetency.module;
//
//import java.util.List;
//import java.util.Map;
//
//import net.chinahrd.core.cache.CacheBlock;
//import net.chinahrd.core.cache.CacheBlockConstructor;
//import net.chinahrd.core.cache.CacheDefine;
//import net.chinahrd.core.cache.CustomBlock;
//import net.chinahrd.eis.permission.EisWebContext;
//import net.chinahrd.entity.dto.KVItemDto;
//import net.chinahrd.utils.CollectionKit;
//
///**
// * 定时更新缓存类
// * 
// * @author htpeng
// *
// */
//public class Cache implements CacheDefine{
//	
//	private final String customerId = EisWebContext.getCustomerId();
//	
//	/* (non-Javadoc)
//	 * @see net.chinahrd.core.cache.Buffer#getDaoClass()
//	 */
//	private static Cache tb=new Cache();
//	public static Cache getInstance(){
//		return tb;
//	}
//	@Override
//	public Class<CacheDao> getDaoClass() {
//		return CacheDao.class;
//	}
//
//	public  CacheBlock<Integer> AvailabilityDayNum = new CacheBlockConstructor<Integer>(
//			this,"queryAvailabilityDayNum").getDefaultBlock();
//
//	
//	/**
//	 * 关键人才类型
//	 */
//	public CacheBlock<Map<String, String>> KeyTalentType = new CacheBlockConstructor<Map<String, String>>(
//			this,"queryKeyTalentType")
//			.getCustomBlock(new CustomBlock<Map<String, String>>() {
//				@Override
//				public Map<String, String> formatData(Object obj) {
//					Map<String, String> resultMap = CollectionKit.newMap();
//					if (null == obj) {
//						return null;
//					}
//					@SuppressWarnings("unchecked")
//					List<KVItemDto> reList = (List<KVItemDto>) obj;
//					for (KVItemDto kVItemDto : reList) {
//						resultMap.put(kVItemDto.getK(), kVItemDto.getV());
//					}
//					return resultMap;
//				}
//			});
//
//	
//	public CacheBlock<String> queryQuarterLastDay=new CacheBlockConstructor<String>(this,"queryQuarterLastDay").getDefaultBlock(customerId);
//	
//}
