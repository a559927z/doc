package net.chinahrd.positionCompetency.mvc.pc.service.impl;



import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.search.SearchConsts;
import net.chinahrd.eis.search.dto.Attachment;
import net.chinahrd.eis.search.dto.PastResume;
import net.chinahrd.eis.search.dto.TrainExperience;
import net.chinahrd.eis.search.service.EsQueryService;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.positionCompetency.DimensionDto;
import net.chinahrd.entity.dto.pc.positionCompetency.EmpContrastDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.EmpDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.HighPerfEmpDto;
import net.chinahrd.entity.dto.pc.positionCompetency.PositionCompetencyDto;
import net.chinahrd.entity.dto.pc.positionCompetency.PositionDetailDto;
import net.chinahrd.entity.dto.pc.positionCompetency.PositionEmpCount;
import net.chinahrd.entity.dto.pc.positionCompetency.SequenceAndAblityDto;
import net.chinahrd.positionCompetency.mvc.pc.dao.PositionCompetencyDao;
import net.chinahrd.positionCompetency.mvc.pc.service.PositionCompetencyService;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Sort;

import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


/**
 * 岗位胜任度  server
 * @author htpeng
 *2016年9月5日下午2:00:04
 */
@Service("positionCompetencyService")
public class PositionCompetencyServiceImpl implements PositionCompetencyService {

    @Autowired
	private EsQueryService esQueryService;
	@Autowired
	private PositionCompetencyDao positionCompetencyDao;
	/**
	 * 查询所有周期点
	 */
	public Map<String,List<String>>  queryAllTime(String customerId){
		Map<String,List<String>>  map= CollectionKit.newMap();
		List<String> keyList= positionCompetencyDao.queryAllTime(customerId);
		List<String> valueList=CollectionKit.newList();
		for(String key:keyList){
			valueList.add(key);
		}
		map.put("key", keyList);
		map.put("value", valueList);		
		 return map;
	}
	/**
	 * 岗位
	 */
	@Override
	public PaginationDto<KVItemDto> queryPositionByName(String customerId,
			 String keyName, PaginationDto<KVItemDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String,Object> mapParam=CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("rowBounds", rowBounds);
		mapParam.put("keyName", keyName);
		int count = positionCompetencyDao.queryPositionByNameCount(mapParam);
        List<KVItemDto> dtos = positionCompetencyDao.queryPositionByName(mapParam);
        
        dto.setRecords(count);
        dto.setRows(dtos);
		return dto;
	}
	/*团队平均胜任度
	 * @see net.chinahrd.biz.paper.service.competency.PositionCompetencyService#getTeamAvgCompetency(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public List<PositionCompetencyDto> getTeamAvgCompetency(String customerId,String yearMonth, String organId,
			String positionId, boolean next) {
		List<PositionCompetencyDto> list=CollectionKit.newList();
		if(next){
			list = positionCompetencyDao.queryOrganByTime(customerId, organId,yearMonth);
		}else{
			String[] arr=yearMonth.split(",");
			for(String str:arr){
				if(str.length()==0){
					break;
				}
				PositionCompetencyDto dto=new PositionCompetencyDto();
				dto.setCustomerId(customerId);
				if(null!=positionId&&positionId.trim().length()>0){
					dto.setPositionId(positionId);
				}
				dto.setYearMonth(str);
				dto.setOrganId(organId);
				list.add(0,dto);
			}
		}
		Iterator<PositionCompetencyDto> iterable=list.iterator();
		while(iterable.hasNext()){
			PositionCompetencyDto dto=iterable.next();
			PositionCompetencyDto rate=positionCompetencyDao.queryAvgCompetencyByOrgan(dto);
			if(null==rate){
				iterable.remove();
			}else{
				dto.concat(rate);
			}
		}

		if(next){
			return new Sort<PositionCompetencyDto>().asc( list);
		}
		for(PositionCompetencyDto dto:list){
        	if(null!=dto.getYearMonth()&&dto.getYearMonth().length()>=4){
        		dto.setYearMonth(DateUtil.getYearMonthTohHalf(dto.getYearMonth()));
        	}
        	
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.service.competency.PositionCompetencyService#querySubOrgan(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<PositionCompetencyDto> querySubOrgan(String customerId, String organId) {
		return positionCompetencyDao.queryOrgan(customerId, organId);
	}
	
	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.service.competency.PositionCompetencyService#queryPositionCompetency(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, PositionCompetencyDto> queryPositionCompetency(String customerId,
			String organId,String yearMonth) {
		Map<String,PositionCompetencyDto>  map= CollectionKit.newMap();
		List<PositionCompetencyDto> list= positionCompetencyDao.queryPositionCompetency(customerId,organId,yearMonth);
		if(null!=list&&list.size()>0){
			map.put("max", list.get(list.size()-1));
			map.put("min", list.get(0));	
		}
		return map;
	}
	
	
	/**
	 * 人员表格面板
	 */
	@Override
	public PaginationDto<EmpDetailDto> queryEmpByName(String customerId, String organId,String yearMonth,
    		String keyName,Double start,Double end, PaginationDto<EmpDetailDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String,Object> mapParam=CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("yearMonth", yearMonth);
		mapParam.put("organId", organId);
		mapParam.put("rowBounds", rowBounds);
		if(null!=keyName&&keyName.length()>0){
			mapParam.put("keyName", keyName);
		}
		int count=0;
		if(start==null&&end==null){
			 count = positionCompetencyDao.queryEmpByNameCount(mapParam);
		}else{
			if(start!=null){
				start=start/100;
			}
			if(end!=null){
				end=end/100;
			}
			mapParam.put("start", start);
			mapParam.put("end", end);
			 count = positionCompetencyDao.queryEmpByNameCount2(mapParam);
		}
		   List<EmpDetailDto> list = positionCompetencyDao.queryEmpByName(mapParam);
	       if(list!=null&&list.size()>0){
	    	   List<String> empIds=CollectionKit.newList();
	    	   
	    	   for(EmpDetailDto d:list){
	    		   empIds.add(d.getEmpId());
	    	   }
	    	   mapParam.put("empIds", empIds);
	    	   List<DimensionDto> empDetialList=positionCompetencyDao.queryEmpDetail(mapParam);
	    	   Map<String,List<DimensionDto>> empDetialMap=CollectionKit.newMap();
	    		for(DimensionDto dim:empDetialList){
	    			String key=dim.getEmpId();
	    			List<DimensionDto> dl=empDetialMap.get(key);
	    			if(null==dl){
	    				dl=CollectionKit.newList();
	    				empDetialMap.put(key, dl);
	    			}
	    			dl.add(dim);
	    		}

	    	   for(EmpDetailDto d:list){
	    		   d.setList(empDetialMap.get(d.getEmpId()));
	    	   }
	       }
       dto.setRecords(count);
       dto.setRows(list);
		return dto;
	}
	/**
	 * 岗位面板
	 */
	@Override
	public PaginationDto<PositionDetailDto> queryPositionTable(String customerId, String organId,
			String yearMonth,String keyName, PaginationDto<PositionDetailDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String,Object> mapParam=CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("yearMonth", yearMonth);
		mapParam.put("organId", organId);
		mapParam.put("rowBounds", rowBounds);
		if(null!=keyName&&keyName.length()>0){
			mapParam.put("keyName", keyName);
		}
		long time1=System.currentTimeMillis();
		int count = positionCompetencyDao.queryPositionTableCount(mapParam);
		long time2=System.currentTimeMillis();
//		System.out.println("查询数量："+(time2-time1));
		   List<PositionDetailDto> list = positionCompetencyDao.queryPositionTable(mapParam);
			long time3=System.currentTimeMillis();
//			System.out.println("分页查询："+(time3-time2));
			if(list!=null&&list.size()>0){
	    	   List<String> positionIds=CollectionKit.newList();
	    	   
	    	   for(PositionDetailDto d:list){
	    		   positionIds.add(d.getPositionId());
//	    			mapParam.put("positionId", d.getPositionId());
//	    		   //所有维度
//	    		   d.setList(positionCompetencyDao.queryPositionDimension(mapParam));
//	    		   //胜任度最高最低的人
//	    		   d.setEmpList(positionCompetencyDao.queryPositionEmpHighAndLow(mapParam));
//	    		   //所有人
//	    		   d.setTotalEmp(positionCompetencyDao.queryPositionEmpCount(mapParam));
	    	   }
	    		mapParam.put("positionIds", positionIds);
	    		List<DimensionDto> dimlist=positionCompetencyDao.queryPositionDimension(mapParam);
	    		long time4=System.currentTimeMillis();
//	    		System.out.println("查询维度："+(time4-time3));
	    		List<EmpDetailDto> emplist=positionCompetencyDao.queryPositionEmpHighAndLow(mapParam);
	    		long time5=System.currentTimeMillis();
//	    		System.out.println("查询最高和最低人员："+(time5-time4));
	    		List<PositionEmpCount> countlist=positionCompetencyDao.queryPositionEmpCount(mapParam);
	    		long time6=System.currentTimeMillis();
//	    		System.out.println("查询人员数量："+(time6-time5));
	    		Map<String,List<DimensionDto>>  dimMap=CollectionKit.newMap();
	    		Map<String,List<EmpDetailDto>>  empMap=CollectionKit.newMap();
	    		Map<String,Integer>  countMap=CollectionKit.newMap();
	    		for(DimensionDto dim:dimlist){
	    			String key=dim.getPositionId();
	    			List<DimensionDto> dl=dimMap.get(key);
	    			if(null==dl){
	    				dl=CollectionKit.newList();
	    				dimMap.put(key, dl);
	    			}
	    			dl.add(dim);
	    		}
	    		for(EmpDetailDto emp:emplist){
	    			String key=emp.getPositionId();
	    			List<EmpDetailDto> emlist=empMap.get(key);
	    			if(null==emlist){
	    				emlist=CollectionKit.newList();
	    				empMap.put(key, emlist);
	    			}
	    			emlist.add(emp);
	    		}
	    		for(PositionEmpCount c:countlist){
	    			countMap.put(c.getPositionId(), c.getCount());
	    		}
	    		for(PositionDetailDto d:list){
//		    		   positionIds.add(d.getPositionId());
//		    			mapParam.put("positionId", d.getPositionId());
		    		   //所有维度
//		    		   d.setList(positionCompetencyDao.queryPositionDimension(mapParam));
	    			  d.setList(dimMap.get(d.getPositionId()));   
	    			//胜任度最高最低的人
	    			  d.setEmpList(empMap.get(d.getPositionId()));   
//		    		   d.setEmpList(positionCompetencyDao.queryPositionEmpHighAndLow(mapParam));
		    		   //所有人
	    			  d.setTotalEmp(countMap.get(d.getPositionId()));
//		    		   d.setTotalEmp(positionCompetencyDao.queryPositionEmpCount(mapParam));

		    	   }
//	    		  System.out.println("总时间："+(time6-time1));
	    		
	       }
       dto.setRecords(count);
       dto.setRows(list);
		return dto;
	}
	
	/**
	 * 岗位下所有人的详细信息
	 * @param customerId
	 * @param organId
	 * @param yearMonth
	 * @param keyName
	 * @param dto
	 * @return
	 */
	@Override
	public PaginationDto<EmpDetailDto> queryPositionEmp(String customerId, String organId,
			String yearMonth,String positionId,String keyName, PaginationDto<EmpDetailDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		if(keyName.trim().length()==0){
			keyName=null;
		}
		Map<String,Object> mapParam=CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("yearMonth", yearMonth);
		mapParam.put("organId", organId);
		mapParam.put("positionId", positionId);
		mapParam.put("keyName", keyName);
		mapParam.put("queryEmp", true);
		mapParam.put("rowBounds", rowBounds);
		List<PositionEmpCount> countList = positionCompetencyDao.queryPositionEmpCount(mapParam);
		 int  count=countList.get(0).getCount();   
		List<EmpDetailDto> list = positionCompetencyDao.queryPositionEmp(mapParam);
		if(list!=null&&list.size()>0){
	    	   List<String> empIds=CollectionKit.newList();
	    	   for(EmpDetailDto d:list){
	    		   empIds.add(d.getEmpId());
	    	   }
	    	   mapParam.put("empIds", empIds);
	    	   List<DimensionDto> empDetialList=positionCompetencyDao.queryEmpDetail(mapParam);
	    	   Map<String,List<DimensionDto>> empDetialMap=CollectionKit.newMap();
	    		for(DimensionDto dim:empDetialList){
	    			String key=dim.getEmpId();
	    			List<DimensionDto> dl=empDetialMap.get(key);
	    			if(null==dl){
	    				dl=CollectionKit.newList();
	    				empDetialMap.put(key, dl);
	    			}
	    			dl.add(dim);
	    		}

	    	   for(EmpDetailDto d:list){
	    		   d.setList(empDetialMap.get(d.getEmpId()),true);
	    	   }
//	    	   for(EmpDetailDto d:list){
//	    			mapParam.put("empId", d.getEmpId());
//	    		   d.setList(positionCompetencyDao.queryEmpDetail(mapParam),true);
//	    	   }
	       }
       dto.setRecords(count);
       dto.setRows(list);
		return dto;
	}
	/*序列团队胜任度分析
	 * @see net.chinahrd.biz.paper.service.competency.PositionCompetencyService#getTeamAvgCompetency(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public List<SequenceAndAblityDto> querySequenceCompetency(String customerId,String organId, String yearMonth,String sequenceId
			) {
		List<SequenceAndAblityDto> list=positionCompetencyDao.querySequence(customerId, organId, yearMonth,sequenceId);
			
		return list;
	}
	

	/*职级团队胜任度分析
	 * @see net.chinahrd.biz.paper.service.competency.PositionCompetencyService#getTeamAvgCompetency(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public List<SequenceAndAblityDto> queryAbilityCompetency(String customerId,String organId, String yearMonth,
			String sequenceId) {
		List<SequenceAndAblityDto> list=positionCompetencyDao.queryAbilityBySequence(customerId, organId, yearMonth,sequenceId);
		for(SequenceAndAblityDto dto: list){
			if(dto.getName().endsWith("级")){
				continue;
			}
			dto.setName(dto.getName()+"级");
		}
		return list;
	}
	
	 /**
     * 登录人所有数据权限	by jxzhang
     * @return
     */
    public List<OrganDto> getOrganPermit() {
        return EisWebContext.getCurrentUser().getOrganPermit();
    }
    
    /**
     * 登录人所有数据权限ID集	by jxzhang
     * @return
     */
    public List<String> getOrganPermitId(){
		List<OrganDto> organPermit = getOrganPermit();
    	List<String> rs = CollectionKit.newList();
    	if(null == organPermit){
    		rs.add("");
    		return rs;
        }
    	for (OrganDto organDto : organPermit) {
    		rs.add(organDto.getOrganizationId());
		}
		return  rs;
	}
	  @Override
	    public Map<String,Object> queryPositionImages(String position, Integer yearNum, Integer continueNum, Integer star, String customerId,boolean updatePositon) {
	        int year = Integer.valueOf(DateUtil.getDBTime("yyyy")).intValue();
	        Map<String, Object> queryMap = CollectionKit.newMap();
	        Map<String,Object> map=CollectionKit.newMap();
	        queryMap.put("positionId", position);
	        queryMap.put("continueNum", continueNum);
	        queryMap.put("star", star);
	        queryMap.put("year", year - yearNum);
	        queryMap.put("customerId", customerId);
	        queryMap.put("maxYear", year);
	        queryMap.put("subOrganIdList", getOrganPermitId());
	        
	        List<HighPerfEmpDto> list = positionCompetencyDao.queryHighPerfImagesEmps(queryMap);
	        if (list.size()> 0) {
        	   DecimalFormat df=(DecimalFormat)NumberFormat.getInstance();
        	   df.setRoundingMode(RoundingMode.HALF_UP);  
		       df.applyPattern("##%"); 
		        Map<String,KVItemDto> baseMap=CollectionKit.newMap();
		        double total=list.size();
		        baseMap.put("age",packagYears(list,total,df) );
		        baseMap.put("sex",packagSexPer(list,total,df) );
		        baseMap.put("degree",packagDegree(list,total,df) );
		        baseMap.put("seniority",packagCompanyAge(list,total,df) );
		        map.put("base", baseMap);
	        }
	        if(updatePositon){
	        	List<DimensionDto>  dimensionList = positionCompetencyDao.queryPositionDimensionExpect(queryMap);
	  	        map.put("dimension", dimensionList);
	        }
	        return map;
	    }
	  /* (non-Javadoc)
		 * @see net.chinahrd.biz.paper.service.competency.PositionCompetencyService#getEmpContrastInfo(java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public EmpContrastDetailDto getEmpContrastInfo(String customerId,
				String empId, String yearMonth) {
			// TODO Auto-generated method stub
			
			Map<String,Object> mapParam=CollectionKit.newMap();
			mapParam.put("customerId", customerId);
			mapParam.put("yearMonth", yearMonth);
			mapParam.put("empId",empId);

			EmpContrastDetailDto dto = positionCompetencyDao.queryEmpContrastInfo(mapParam);
			dto.setList(positionCompetencyDao.queryEmpDetail(mapParam));
	      
			return dto;
		}

	    
	    /**
	     * 性别
	     * @param rs
	     * @param total
	     * @return
	     */
	    private KVItemDto packagSexPer(List<HighPerfEmpDto> rs, double total,DecimalFormat df) {
	        if (total == 0) {
	            KVItemDto dto = new KVItemDto();
	            dto.setK("sex");
	            dto.setV(null);
	            return dto;
	        }
	        double manTotal = 0.0, womanTotal = 0.0;
	        double manPer = 0.0, womanPer = 0.0;
	        MultiValueMap<String, HighPerfEmpDto> m = new LinkedMultiValueMap<String, HighPerfEmpDto>();
	        for (HighPerfEmpDto dto : rs) {
	            m.add(dto.getSex(), dto);
	        }
	        for (Entry<String, List<HighPerfEmpDto>> empEntry : m.entrySet()) {
	            String key = empEntry.getKey();
	            //男
	            if (key.equals("m")) {
	                manTotal = empEntry.getValue().size();
	                manPer = ArithUtil.div(manTotal, total, 2);
	            } else {
	                womanTotal = empEntry.getValue().size();
	                womanPer = ArithUtil.div(womanTotal, total, 2);
	            }
	        }
	        KVItemDto dto = new KVItemDto();
	        if (manTotal >= womanTotal) {
	            dto.setK("男士");
	            dto.setV(df.format(manPer));
	        } else if (manTotal < womanTotal) {
	            dto.setK("女士");
	            dto.setV(df.format(womanPer));
	        }
	        return dto;
	    }
	    /**
	     * 出生日期占比
	     *
	     * @param rs
	     * @param total
	     * @return
	     */
	    private KVItemDto packagYears(List<HighPerfEmpDto> rs, double total,DecimalFormat df) {
	        if (total == 0) {
	            KVItemDto dto = new KVItemDto();
	            dto.setK("没有数据");
	            dto.setV(null);
	            return dto;
	        }
	        MultiValueMap<String, HighPerfEmpDto> m = new LinkedMultiValueMap<String, HighPerfEmpDto>();
	        for (HighPerfEmpDto dto : rs) {
	            DateTime dt = new DateTime(dto.getBirthDate());
	            int year = dt.getYear();
	            String years = "";
	            if (year >= 1920 && year < 1930) {
	                years = "20后";
	            } else if (year >= 1930 && year < 1940) {
	                years = "30后";
	            } else if (year >= 1940 && year < 1950) {
	                years = "40后";
	            } else if (year >= 1950 && year < 1960) {
	                years = "50后";
	            } else if (year >= 1960 && year < 1970) {
	                years = "60后";
	            } else if (year >= 1970 && year < 1980) {
	                years = "70后";
	            } else if (year >= 1980 && year < 1990) {
	                years = "80后";
	            } else if (year >= 1990 && year < 2000) {
	                years = "90后";
	            } else if (year >= 2000 && year < 2010) {
	                years = "00后";
	            } else if (year >= 2010 && year < 2020) {
	                years = "10后";
	            } else {
	                years = "新新代";
	            }
	            m.add(years, dto);
	        }
	        int maxSize = 0;
	        String years = "";
	        for (Entry<String, List<HighPerfEmpDto>> empEntry : m.entrySet()) {
	            Integer size = empEntry.getValue().size();
	            // 年份最多人
	            if (maxSize < size) {
	                maxSize = size;
	                years = empEntry.getKey();
	            } 
	        }
	        double maxPer = ArithUtil.div(maxSize, total, 2);
	        KVItemDto dto = new KVItemDto();
	        dto.setK(years);
	        dto.setV(df.format(maxPer));
	        return dto;
	    }
		
		/**
		 *  司龄
		 * @param rs
		 * @param total
		 * @return
		 */
		private KVItemDto packagCompanyAge(List<HighPerfEmpDto> rs,double total,DecimalFormat df){

			MultiValueMap<Integer, HighPerfEmpDto> m = new LinkedMultiValueMap<Integer, HighPerfEmpDto>();
			for (HighPerfEmpDto dto : rs) {
				m.add(dto.getSeniority(), dto);
			}
			String[] xAxisData = {"3月以下","3月-1年","1-3年","3-5年","5年以上"};
			int[] seriesData = {0,0,0,0,0};
			
			for (Entry<Integer, List<HighPerfEmpDto>> empEntry : m.entrySet()) {
				if(empEntry.getKey() instanceof Integer){
					Integer key = empEntry.getKey();
					if(key >= 0 && key < 3){
						int temp = seriesData[0];
						seriesData[0] = temp + empEntry.getValue().size();
					}else if(key >= 3 && key < 12){
						int temp = seriesData[1];
						seriesData[1] = temp + empEntry.getValue().size();
					}else if(key >= 12 && key < 36){
						int temp = seriesData[2];
						seriesData[2] = temp + empEntry.getValue().size();
					}else if(key >= 36 && key < 60){
						int temp = seriesData[3];
						seriesData[3] = temp + empEntry.getValue().size();
					}else{
						int temp = seriesData[4];
						seriesData[4] = temp + empEntry.getValue().size();
					}
				}
			}
			int index=0;
			int max=0;
			for(int i=seriesData.length-1;i>=0;i--){
				if(max<seriesData[i]){
					max=seriesData[i];
					index=i;
				}
			}
			KVItemDto k=new KVItemDto();
			k.setK(xAxisData[index]);
			k.setV((df.format(seriesData[index]/total)));
			return k;
		}
		
		/**
		 *  学历
		 * @param rs
		 * @param total
		 * @return
		 */
		private KVItemDto packagDegree(List<HighPerfEmpDto> rs,double total,DecimalFormat df){
			MultiValueMap<String, HighPerfEmpDto> m = new LinkedMultiValueMap<String, HighPerfEmpDto>();
			for (HighPerfEmpDto dto : rs) {
				m.add(dto.getDegree(), dto);
			}

			String[] xAxisData = {"博士","硕士","本科","大专","中专","高中","初中","小学","其他"};
			int[] seriesData = {0,0,0,0,0,0,0,0,0};
			
			for (Entry<String, List<HighPerfEmpDto>> empEntry : m.entrySet()) {
				if(empEntry.getKey() instanceof String){
					String key = empEntry.getKey();
					if(key.equals("博士")){
						int temp = seriesData[0];
						seriesData[0] = temp + empEntry.getValue().size();
					}else if(key.equals("硕士")){
						int temp = seriesData[1];
						seriesData[1] = temp + empEntry.getValue().size();
					}else if(key.equals("本科")){
						int temp = seriesData[2];
						seriesData[2] = temp + empEntry.getValue().size();
					}else if(key.equals("大专")){
						int temp = seriesData[3];
						seriesData[3] = temp + empEntry.getValue().size();
					}else if(key.equals("中专")){
						int temp = seriesData[4];
						seriesData[4] = temp + empEntry.getValue().size();
					}else if(key.equals("高中")){
						int temp = seriesData[5];
						seriesData[5] = temp + empEntry.getValue().size();
					}else if(key.equals("初中")){
						int temp = seriesData[6];
						seriesData[6] = temp + empEntry.getValue().size();
					}else if(key.equals("小学")){
						int temp = seriesData[7];
						seriesData[7] = temp + empEntry.getValue().size();
					}else{
						int temp = seriesData[8];
						seriesData[8] = temp + empEntry.getValue().size();
					}
				}
			}
			int index=0;
			int max=0;
			for(int i=seriesData.length-1;i>=0;i--){
				if(max<seriesData[i]){
					max=seriesData[i];
					index=i;
				}
			}
			KVItemDto k=new KVItemDto();
			k.setK(xAxisData[index]);
			k.setV((df.format(seriesData[index]/total)));
			return k;
		}
		/* (non-Javadoc)
		 * @see net.chinahrd.biz.paper.service.competency.PositionCompetencyService#getKeyWordInfo(java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public Map<String,Object>  getKeyWordInfo(String customerId, String empId,
				String keyword) {
			// TODO Auto-generated method stub
			Map<String,Object> map=CollectionKit.newMap();
			QueryBuilder queryBuilderPastResumet=buildQueryPastResume(empId,keyword);

			List<PastResume> lisPastResumet = esQueryService.complexSearch(SearchConsts.EMP_INDEX, SearchConsts.EMP_PASTRESUME, queryBuilderPastResumet,
					0,100, null, null, PastResume.class);
			
			QueryBuilder queryBuilderTrainExperience=buildQueryTrain(empId,keyword);
			List<TrainExperience> listTrainExperience = esQueryService.complexSearch(SearchConsts.EMP_INDEX, SearchConsts.EMP_TRAIN, queryBuilderTrainExperience,
					0,100, null, null, TrainExperience.class);
			
			QueryBuilder queryBuilderFile=buildQueryFile(empId,keyword);
			List<Attachment> listFile = esQueryService.complexSearch(SearchConsts.EMP_INDEX, SearchConsts.FILE, queryBuilderFile,
					0,100, null, null, Attachment.class);
			
//			
//			
//			List<KVItemDto> kv=positionCompetencyDao.getKeyWordInfo(customerId,empId,keyword);
			
			int resumeNum=0;
			if(lisPastResumet!=null){
				resumeNum=lisPastResumet.size();
			}
			int trainNum=0;
			if(listTrainExperience!=null){
				trainNum=listTrainExperience.size();
			}
			int fileNum=0;
			if(listFile!=null){
				fileNum=listFile.size();
			}
			map.put("resumeNum",resumeNum);
			map.put("trainNum",trainNum);
			map.put("fileNum", fileNum);
			if(resumeNum>0){
				map.put("resume",lisPastResumet );
			}
			if(trainNum>0){
				map.put("train",listTrainExperience );
			}
			if(fileNum>0){
				map.put("file",listFile );
			}
			return map;
		}
		
		/**
		 * 构建全文检索查询
		 * 
		 * @param talentSearch
		 * @return
		 */
		private QueryBuilder buildQueryPastResume(String empId,String keyword) {
			BoolQueryBuilder boolAllQueryBuilder = QueryBuilders.boolQuery();

			// empId
			boolAllQueryBuilder.must(QueryBuilders.matchQuery("empId", empId));
			
			// 过往履历
			BoolQueryBuilder boolPastQueryBuilder = QueryBuilders.boolQuery();
			boolPastQueryBuilder.should(QueryBuilders.matchQuery("workUnit", keyword));
			boolPastQueryBuilder.should(QueryBuilders.matchQuery("departmentName", keyword));
			boolPastQueryBuilder.should(QueryBuilders.matchQuery("positionName", keyword));
			boolPastQueryBuilder.should(QueryBuilders.matchQuery("bonusPenaltyName", keyword));
			boolPastQueryBuilder.should(QueryBuilders.matchQuery("witnessName", keyword));
			boolPastQueryBuilder.should(QueryBuilders.matchQuery("changeReason", keyword));
			boolPastQueryBuilder.minimumNumberShouldMatch(1);
		
			boolAllQueryBuilder.must(boolPastQueryBuilder);
			return boolAllQueryBuilder;
		}
		/**
		 * 构建全文检索查询
		 * 
		 * @param talentSearch
		 * @return
		 */
		private QueryBuilder buildQueryTrain(String empId,String keyword) {
			BoolQueryBuilder boolAllQueryBuilder = QueryBuilders.boolQuery();

			// empId
			boolAllQueryBuilder.must(QueryBuilders.matchQuery("empId", empId));
			

			// 培训经历
			BoolQueryBuilder booltrainQueryBuilder = QueryBuilders.boolQuery();
			booltrainQueryBuilder.should(QueryBuilders.matchQuery("courseName", keyword));
			booltrainQueryBuilder.should(QueryBuilders.matchQuery("result", keyword));
			booltrainQueryBuilder.should(QueryBuilders.matchQuery("trainUnit", keyword));
			booltrainQueryBuilder.should(QueryBuilders.matchQuery("gainCertificate", keyword));
			booltrainQueryBuilder.should(QueryBuilders.matchQuery("lecturerName", keyword));
			booltrainQueryBuilder.should(QueryBuilders.matchQuery("note", keyword));
			booltrainQueryBuilder.minimumNumberShouldMatch(1);
			
			
			boolAllQueryBuilder.must(booltrainQueryBuilder);
			return boolAllQueryBuilder;
		}
		
		/**
		 * 构建全文检索查询
		 * 
		 * @param talentSearch
		 * @return
		 */
		private QueryBuilder buildQueryFile(String empId,String keyword) {
			BoolQueryBuilder boolAllQueryBuilder = QueryBuilders.boolQuery();

//			// empId
//			boolAllQueryBuilder.must(QueryBuilders.matchQuery("empId", empId));
//			

			// 培训经历
			BoolQueryBuilder booltrainQueryBuilder = QueryBuilders.boolQuery();
			booltrainQueryBuilder.should(QueryBuilders.matchQuery("fileName", keyword));
			booltrainQueryBuilder.should(QueryBuilders.matchQuery("fileContent", keyword));
			booltrainQueryBuilder.minimumNumberShouldMatch(1);
			boolAllQueryBuilder.must(booltrainQueryBuilder);
			return boolAllQueryBuilder;
		}
		
	}
