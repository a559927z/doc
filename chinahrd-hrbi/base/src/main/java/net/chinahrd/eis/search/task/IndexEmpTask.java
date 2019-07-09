package net.chinahrd.eis.search.task;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import net.chinahrd.eis.search.SearchConsts;
import net.chinahrd.eis.search.dao.EmployeeDao;
import net.chinahrd.eis.search.dto.Employee;
import net.chinahrd.eis.search.dto.PastResume;
import net.chinahrd.eis.search.dto.TrainExperience;
import net.chinahrd.utils.CollectionKit;

/**
 * 索引Emp信息 定时任务
 * 
 * @author bright
 * 
 */
public class IndexEmpTask {
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(IndexEmpTask.class);
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private EmployeeDao employeeDao;
	
	private int batchSize = 1000;
	private String taskSwitch;


	public void call() {
		if (!Boolean.valueOf(taskSwitch)) return;
		
		empIndex();
		trainExperienceIndex();
		pastResumeIndex();
	}

	/**
	 * 人员索引
	 */
	private void empIndex(){
		List<Employee> emps = employeeDao.findUnIndexedEmp(batchSize);
		if (emps == null || emps.size() == 0) {
			//log.info("没有需要索引的员工信息，任务结束。");
			return;
		}
		
		//log.info("开始执行 索引员工信息 任务...");
		List<IndexQuery> indexQueries = CollectionKit.newList();
		List<String> ids = CollectionKit.newList();
		for (Employee emp : emps) {
			ids.add(emp.getEmpId());
			
			IndexQuery indexQuery = new IndexQuery();
			indexQuery.setId(emp.getEmpId());
			indexQuery.setObject(emp);
			indexQuery.setIndexName(SearchConsts.EMP_INDEX);
			indexQuery.setType(SearchConsts.EMP_TYPE);
			indexQueries.add(indexQuery);
		}
		
		//先删除之前存在的索引
		DeleteQuery deleteQuery = new DeleteQuery();
		deleteQuery.setQuery(QueryBuilders.idsQuery(SearchConsts.EMP_TYPE).ids(ids));
		deleteQuery.setIndex(SearchConsts.EMP_INDEX);
		deleteQuery.setType(SearchConsts.EMP_TYPE);
		elasticsearchTemplate.delete(deleteQuery, Employee.class);
		elasticsearchTemplate.refresh(SearchConsts.EMP_INDEX);
		
		//增加索引
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(SearchConsts.EMP_INDEX);
		
		employeeDao.updateMark(emps);
		//log.info(" 索引员工信息 任务  结束。");
		empIndex();
	}
	
	/**
	 * 培训经历索引
	 */
	private void trainExperienceIndex(){
		List<TrainExperience> dtos = employeeDao.findUnIndexedTrainExperience(batchSize);
		if (dtos == null || dtos.size() == 0) {
			//log.info("没有需要索引的员工信息，任务结束。");
			return;
		}
		
		//log.info("开始执行 索引员工信息 任务...");
		List<IndexQuery> indexQueries = CollectionKit.newList();
		List<String> ids = CollectionKit.newList();
		for (TrainExperience dto : dtos) {
			ids.add(dto.getTrainExperienceId());
			
			IndexQuery indexQuery = new IndexQuery();
			indexQuery.setId(dto.getTrainExperienceId());
			indexQuery.setObject(dto);
			indexQuery.setIndexName(SearchConsts.EMP_INDEX);
			indexQuery.setType(SearchConsts.EMP_TRAIN);
			indexQueries.add(indexQuery);
		}
		
		//先删除之前存在的索引
		DeleteQuery deleteQuery = new DeleteQuery();
		deleteQuery.setQuery(QueryBuilders.idsQuery(SearchConsts.EMP_TRAIN).ids(ids));
		deleteQuery.setIndex(SearchConsts.EMP_INDEX);
		deleteQuery.setType(SearchConsts.EMP_TRAIN);
		elasticsearchTemplate.delete(deleteQuery, TrainExperience.class);
		elasticsearchTemplate.refresh(SearchConsts.EMP_INDEX);
		
		//增加索引
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(SearchConsts.EMP_INDEX);
		
		employeeDao.updateTrainExperienceMark(dtos);
		trainExperienceIndex();
		//log.info(" 索引员工信息 任务  结束。");
	}
	/**
	 * 过往履历索引
	 */
	private void pastResumeIndex(){
		List<PastResume> dtos = employeeDao.findUnIndexedPastResume(batchSize);
		if (dtos == null || dtos.size() == 0) {
			//log.info("没有需要索引的员工信息，任务结束。");
			return;
		}
		
		//log.info("开始执行 索引员工信息 任务...");
		List<IndexQuery> indexQueries = CollectionKit.newList();
		List<String> ids = CollectionKit.newList();
		for (PastResume dto : dtos) {
			ids.add(dto.getEmpPastResumeId());
			
			IndexQuery indexQuery = new IndexQuery();
			indexQuery.setId(dto.getEmpPastResumeId());
			indexQuery.setObject(dto);
			indexQuery.setIndexName(SearchConsts.EMP_INDEX);
			indexQuery.setType(SearchConsts.EMP_PASTRESUME);
			indexQueries.add(indexQuery);
		}
		
		//先删除之前存在的索引
		DeleteQuery deleteQuery = new DeleteQuery();
		deleteQuery.setQuery(QueryBuilders.idsQuery(SearchConsts.EMP_PASTRESUME).ids(ids));
		deleteQuery.setIndex(SearchConsts.EMP_INDEX);
		deleteQuery.setType(SearchConsts.EMP_PASTRESUME);
		elasticsearchTemplate.delete(deleteQuery, PastResume.class);
		elasticsearchTemplate.refresh(SearchConsts.EMP_INDEX);
		
		//增加索引
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(SearchConsts.EMP_INDEX);
		
		employeeDao.updatePastResumeMark(dtos);
		//log.info(" 索引员工信息 任务  结束。");
		pastResumeIndex();
	}
	
	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public String getTaskSwitch() {
		return taskSwitch;
	}

	public void setTaskSwitch(String taskSwitch) {
		this.taskSwitch = taskSwitch;
	}
	
}
