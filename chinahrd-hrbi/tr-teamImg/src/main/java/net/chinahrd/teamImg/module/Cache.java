package net.chinahrd.teamImg.module;

import java.util.List;
import java.util.Map;

import net.chinahrd.core.cache.CacheBlock;
import net.chinahrd.core.cache.CacheBlockConstructor;
import net.chinahrd.core.cache.CustomBlock;
import net.chinahrd.core.cache.model.FileCacheConfig;
import net.chinahrd.core.cache.model.FileStorage;
import net.chinahrd.core.cache.model.FileStorageEntity;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.teamImg.mvc.pc.service.TeamImgService;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 定时更新缓存类
 * 
 * @author htpeng
 *
 */
public class Cache {

	 /**
     * 团队画像
     */
    public static CacheBlock<FileStorage<Map<Integer, Object>>> teamImg =
            new CacheBlockConstructor<FileStorage<Map<Integer, Object>>>(new FileCacheConfig("queryTeamImgOrgan")).getCustomBlock(new CustomBlock<FileStorage<Map<Integer, Object>>>() {
                @SuppressWarnings("unchecked")
				@Override
                public FileStorage<Map<Integer, Object>> formatData(Object obj) {
                	WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
                	TeamImgService teamImgService=(TeamImgService) wac.getBean("teamImgService");
					List<String> list=(List)obj;
					FileStorageEntity<Map<Integer, Object>> file=new FileStorageEntity<Map<Integer,Object>>();
//					for(String organId:list){
//						file.setMap(organId,teamImgService.getTeamImgData(organId, EisWebContext.getCustomerId()));
//					}
                	return file;
                }
               
            }, EisWebContext.getCustomerId());

	
}
