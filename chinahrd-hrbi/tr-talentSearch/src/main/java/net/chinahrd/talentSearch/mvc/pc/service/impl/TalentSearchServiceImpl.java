package net.chinahrd.talentSearch.mvc.pc.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.emp.TalentSearch;
import net.chinahrd.entity.enums.AgeIntervalEnum;
import net.chinahrd.talentSearch.mvc.pc.dao.TalentSearchDao;
import net.chinahrd.talentSearch.mvc.pc.service.TalentSearchService;
import net.chinahrd.utils.CollectionKit;
import opennlp.tools.util.StringUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * 人才搜索Service实现类
 * Created by qpzhu on 16/03/02.
 */
@Service("talentSearchService")
public class TalentSearchServiceImpl implements TalentSearchService {

    @Autowired
    private TalentSearchDao talentSearchDao;

    @Override
    public EmpDetailDto findEmpDetail(String empId, String customerId) {
        return talentSearchDao.findEmpDetail(empId, customerId);
    }

    @Override
    public PaginationDto<EmpDto> findEmpAdvancedAll(TalentSearch talentSearch, PaginationDto<EmpDto> pageDto, String sidx, String sord, String customerId) {

        // 通过keyName查询员工时，登录人必须要具备查看所在员工下的数据权限	by jxzhang
        List<String> organPermitIds = getOrganPermitId();
        if (null == organPermitIds || organPermitIds.isEmpty()) {
            return null;
        }
        int count = talentSearchDao.findEmpAdvancedAllCount(talentSearch, customerId, organPermitIds);
        List<EmpDto> dtos = talentSearchDao.findEmpAdvancedAll(organPermitIds, talentSearch, sidx, sord, customerId, pageDto.getOffset(), pageDto.getLimit());

        pageDto.setRecords(count);
        pageDto.setRows(dtos);
        return pageDto;
    }

    //
    private List<String> getPerformanceTime(String performanceTimeOne, String performanceTimeTwo) {
        List<String> list = new ArrayList<String>();
        if (performanceTimeOne != null && performanceTimeTwo != null) {
            int permanceOneTime = Integer.parseInt(performanceTimeOne.substring(0, 4));
            int permanceTwoTime = Integer.parseInt(performanceTimeTwo.substring(0, 4));
            int permanceOneTimeSelect = Integer.parseInt(performanceTimeOne.substring(4, 6));
            int permanceTwoTimeSelect = Integer.parseInt(performanceTimeTwo.substring(4, 6));
            int count = permanceTwoTime - permanceOneTime;
            if (count <= 0) {
                if (permanceOneTimeSelect == 6) {
                    list.add(permanceOneTime + "0" + permanceOneTimeSelect);
                } else {
                    list.add(permanceOneTime + "" + permanceOneTimeSelect);
                }
                if (permanceOneTimeSelect != permanceTwoTimeSelect) {
                    list.add(permanceTwoTime + "" + permanceTwoTimeSelect);
                }
            } else {
                for (int i = permanceOneTime; i <= permanceTwoTime; i++) {
                    if (i == permanceOneTime) {
                        if (permanceOneTimeSelect == 6) {
                            list.add(i + "06");
                            list.add(i + "12");
                        } else {
                            list.add(i + "12");
                        }
                    } else if (i == permanceTwoTime) {
                        if (permanceTwoTimeSelect == 6) {
                            list.add(i + "06");
                        } else {
                            list.add(i + "06");
                            list.add(i + "12");
                        }
                    } else {
                        list.add(i + "06");
                        list.add(i + "12");
                    }
                }
            }
        }
        return list;
    }

    /**
     * 登录人所有数据权限ID集	by jxzhang
     *
     * @return
     */
    private List<String> getOrganPermitId() {
        List<OrganDto> organPermit = EisWebContext.getCurrentUser().getOrganPermit();
        if (null == organPermit) {
            return null;
        }
        List<String> rs = CollectionKit.newList();
        for (OrganDto organDto : organPermit) {
            rs.add(organDto.getOrganizationId());
        }
        return rs;
    }

    /**
     * 根据前端传来的json格式数据处理
     */
    @Override
    public TalentSearch parameterHandling(JSONObject jsonMap) {
        TalentSearch talentSearch = new TalentSearch();
        talentSearch.setKeyName("".equals(jsonMap.getString("keyName")) ? null : jsonMap.getString("keyName"));
        talentSearch.setEmpTypeArray("".equals(jsonMap.getString("empTypeArray")) ? null : jsonMap.getString("empTypeArray").split(","));


        List<AgeIntervalEnum> ageList = null;
        if (!StringUtil.isEmpty(jsonMap.getString("ageArray"))) {
            String[] age = jsonMap.getString("ageArray").split(",");
            ageList = CollectionKit.newList();
            for (String a : age) {
                AgeIntervalEnum ageEnum = AgeIntervalEnum.valueOf(a);
                ageList.add(ageEnum);
            }
        }
        talentSearch.setAgeArray(ageList);

        talentSearch.setSexArray("".equals(jsonMap.getString("sexArray")) ? null : jsonMap.getString("sexArray").split(","));
        talentSearch.setEduArray("".equals(jsonMap.getString("eduArray")) ? null : jsonMap.getString("eduArray").split(","));

        talentSearch.setOrganization("".equals(jsonMap.getString("organizationId")) ? null : jsonMap.getString("organizationId"));
        talentSearch.setMainSequenceArray("".equals(jsonMap.getString("sequenceArray")) ? null : jsonMap.getString("sequenceArray").split(","));
        talentSearch.setSubSequenceArray("".equals(jsonMap.getString("sequenceSubArray")) ? null : jsonMap.getString("sequenceSubArray").split(","));
        talentSearch.setAbilityArray("".equals(jsonMap.getString("abilityIdArray")) ? null : jsonMap.getString("abilityIdArray").split(","));
        talentSearch.setJobTitleArray("".equals(jsonMap.getString("JobTitleArray")) ? null : jsonMap.getString("JobTitleArray").split(","));

        talentSearch.setPerformanceTimeOne("全部".equals(jsonMap.getString("selectTimeOne")) ? null : jsonMap.getString("selectTimeOne"));
        talentSearch.setPerformanceTimeTwo("全部".equals(jsonMap.getString("selectTimeTwo")) ? null : jsonMap.getString("selectTimeTwo"));
        talentSearch.setPerformanceRankArray("".equals(jsonMap.getString("performanceArray")) ? null : jsonMap.getString("performanceArray").split(","));
        //绩效数据特殊处理
        List<String> list = getPerformanceTime(talentSearch.getPerformanceTimeOne(), talentSearch.getPerformanceTimeTwo());
        talentSearch.setPerformanceCondition(list);

        talentSearch.setPastHistory("".equals(jsonMap.getString("pastHistory")) ? null : jsonMap.getString("pastHistory"));
        talentSearch.setProjectExperience("".equals(jsonMap.getString("projectExperience")) ? null : jsonMap.getString("projectExperience"));
        talentSearch.setTrainingExperience("".equals(jsonMap.getString("trainingExperience")) ? null : jsonMap.getString("trainingExperience"));
        return talentSearch;
    }

    /**
     * 数据导出
     *
     * @throws IOException
     */
    @Override
    public void dataResultExport(TalentSearch talentSearch, String customerId, HttpServletResponse response) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("result");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  

        List<String> organPermitIds = getOrganPermitId();
        if (null == organPermitIds || organPermitIds.isEmpty()) {

        }
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
        int count = talentSearchDao.findEmpAdvancedAllCount(talentSearch, customerId, organPermitIds);
        List<EmpDto> list = talentSearchDao.findEmpAdvancedAll(organPermitIds, talentSearch, "", "", customerId, 0, count);
        HSSFCell cell = row.createCell((short) 0);
        String[] strArray = {"序号", "名称", "所属机构", "岗位", "序列", "职位层级"};
        //为EXCEL表头添加数据
        for (int i = 0; i < strArray.length; i++) {
            sheet.setColumnWidth(i, 80 * 80);
            cell.setCellValue(strArray[i]);
            cell.setCellStyle(style);
            cell = row.createCell((short) i + 1);
        }
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow((int) i + 1);
            EmpDto dto = (EmpDto) list.get(i);
            // 第四步，创建单元格，并设置值  
            row.createCell((short) 0).setCellValue(i + 1);
            row.createCell((short) 1).setCellValue(dto.getUserNameCh());
            row.createCell((short) 2).setCellValue(dto.getOrganizationName());
            row.createCell((short) 3).setCellValue(dto.getPositionName());
            row.createCell((short) 4).setCellValue(dto.getSequenceName());
            row.createCell((short) 5).setCellValue(dto.getAbilityName());
            cell = row.createCell((short) 6);
        }

        // 第六步，将文件存到指定位置  
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        // 设置response的Header  
        response.addHeader("Content-Disposition", "attachment;filename="
                + new String("result.xls".getBytes()));
        try {
            OutputStream toClient = response.getOutputStream();
            wb.write(toClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
