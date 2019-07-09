package net.chinahrd.eis.search.task;

import java.io.File;
import java.io.FileInputStream;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import net.chinahrd.eis.search.SearchConsts;
import net.chinahrd.eis.search.dto.Attachment;
import net.chinahrd.utils.Identities;

/**
 * 索引Emp信息 定时任务
 * 
 * @author bright
 * 
 */
public class IndexAttachmentTask {
	
	private static Logger log = LoggerFactory.getLogger(IndexAttachmentTask.class);
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;


	public void call() {
		
		saveFile(new File("F:/Desktop/docs"));
		//log.info(" 索引员工信息 任务  结束。");
	}

	

	private boolean saveFile(File file) {
		if(file == null || !file.exists() || !file.canRead()) return false;
        if(file.isDirectory()){
            // 文件夹
            File[] files = file.listFiles();
            for(File f : files){
            	saveFile(f);
            }
        }else{
            // 文件
        	Attachment doc = null;
            try{
                doc = new Attachment();
                Tika tika = new Tika();
                Metadata metadata = new Metadata();
                metadata.add(Metadata.CONTENT_ENCODING, "utf-8");  
                
                doc.setId(Identities.uuid2());
                doc.setFileContent(tika.parseToString(new FileInputStream(file),metadata));
                doc.setFileName(file.getName());
                doc.setFilePath(file.getAbsolutePath());
                
                IndexQuery query = new IndexQuery();
                query.setId(doc.getId());
                query.setObject(doc);
                query.setIndexName(SearchConsts.EMP_INDEX);
                query.setType(SearchConsts.FILE);
                elasticsearchTemplate.index(query);
                
                log.info("正在加载文件索引："+file.getName()+" "+file.getAbsolutePath());
            }catch(Exception e){
                log.info("索引创建失败："+e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return true;
	}
	
}
