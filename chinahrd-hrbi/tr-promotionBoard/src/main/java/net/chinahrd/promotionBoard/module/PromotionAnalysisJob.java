package net.chinahrd.promotionBoard.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.job.JobRegisterAbstract;
import net.chinahrd.core.timer.model.JobContext;
import net.chinahrd.core.timer.model.TimerConfig;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PerfChangeAnalysisDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionAnalysisDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionElementSchemeDto;
import net.chinahrd.promotionBoard.mvc.pc.service.PromotionBoardService;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.PropertiesUtil;

public class PromotionAnalysisJob extends JobRegisterAbstract {

	@Injection
	private PromotionBoardService promotionBoardService;

	private final String customerId = "1";/*对于单元测试，使用该方法获取配置PropertiesUtil.getProperty("conf/conf.properties", "customer.id");*/
	private List<KVItemDto> conditionProp = CollectionKit.newList();

	@Override
	public void setTimerConfig(TimerConfig tc) {
		String cron = PropertiesUtil.getProperty("conf/timer.properties", "promotionCron");
		tc.setCron(cron);
	}

	@Override
	public void execute(JobContext context) {
		String date = "2015-12-17";// 使用最新一次统计时间进行分析
		// 分析
		List<PromotionAnalysisDto> analysisList = analysisMain(date);
		// 删除所有旧的分析
		promotionBoardService.delEmpAnalysis(null);
		// 分批插入
		List<List<PromotionAnalysisDto>> splitAnalysissList = CollectionKit.splitList3(analysisList, 30000);
		for (List<PromotionAnalysisDto> list : splitAnalysissList) {
			promotionBoardService.batchInsertPA(list);
		}
		// 员工占比统计占比-条件符合占比
		for (KVItemDto kv : conditionProp) {
			System.out.println(kv.getK() + "\t" + kv.getV());
		}
		// 分批更新
		List<List<KVItemDto>> splitConditionProp = CollectionKit.splitList3(conditionProp, 30000);
		for (List<KVItemDto> list : splitConditionProp) {
			promotionBoardService.batchUpdateCP(list, date);
		}
	}

	/**
	 * 入口
	 */
	public List<PromotionAnalysisDto> analysisMain(String date) {
		List<KVItemDto> empSchemeRel = getEmpSchemeRel(date);
		Map<String, PromotionElementSchemeDto> schemeAll = getSchemeAll();
		Map<String, Double> empCompanyAge = getEmpCompanyAge();
		Map<String, Integer> empEval = getEmpEval();
		Map<Integer, String> evalAll = getEvalAll();
		Multimap<String, String> empCertificate = getEmpCertificate();
		Map<String, String> certificateAll = getCertificateAll();
		Multimap<String, String> empCertificateType = getEmpCertificateType();
		Multimap<String, PerfChangeAnalysisDto> empPerf = getEmpPerfMulti();

		List<PromotionAnalysisDto> rs = CollectionKit.newList();

		for (KVItemDto item : empSchemeRel) {

			String empId = item.getK();
			String schemeId = item.getV();
			if (null == empId && null == schemeId) {
				continue;
			}
			String dbCurdate = DateUtil.getDBCurdate();

			// 先删除当前员工最新一次分析的所有记录
			// promotionBoardService.delEmpAnalysis(empId);

			// 方案
			PromotionElementSchemeDto schemeDto = schemeAll.get(schemeId);

			int ca = 0, cne = 0, c = 0, ct = 0, cnp = 0;
			// 司龄
			Integer schemeCompanyAge = schemeDto.getCompanyAge();
			if (null != schemeCompanyAge) {
				Double companyAge = empCompanyAge.get(empId);
				ca = analysisCompanyAge(schemeCompanyAge, companyAge);
				rs.add(new PromotionAnalysisDto(Identities.uuid2(), empId, customerId, schemeCompanyAge.toString(),
						companyAge.toString(), ca, dbCurdate, "工作年限"));
			}

			// 能力评价
			Integer schemeCurtNameEval = schemeDto.getCurtNameEval();
			if (null != schemeCurtNameEval) {
				Integer curtNameEval = empEval.get(empId);
				cne = analysisCurtNameEval(schemeCurtNameEval, curtNameEval);
				rs.add(new PromotionAnalysisDto(Identities.uuid2(), empId, customerId, evalAll.get(schemeCurtNameEval),
						evalAll.get(cne), cne, dbCurdate, "能力评价"));
			}

			// 资格证书
			String schemeCertificateId = schemeDto.getCertificateId();
			if (null != schemeCertificateId) {
				Collection<String> certificateIds = empCertificate.get(empId);
				c = analysisCertificate(certificateIds, schemeCertificateId);
				String cfName = certificateAll.get(schemeCertificateId);
				rs.add(new PromotionAnalysisDto(Identities.uuid2(), empId, customerId, cfName, c == 1 ? "有证书" : "无证书",
						c, dbCurdate, "资格证书"));
			}

			// 此类资格证书
			String schemeCertificateTypeId = schemeDto.getCertificateTypeId();
			if (null != schemeCertificateTypeId) {
				Collection<String> certificateTypeIds = empCertificateType.get(empId);
				ct = analysisCertificateType(certificateTypeIds, schemeCertificateId);
				String cfName = certificateAll.get(schemeCertificateId);
				rs.add(new PromotionAnalysisDto(Identities.uuid2(), empId, customerId, cfName,
						ct == 1 ? "有此类证书" : "无此类证书", ct, dbCurdate, "资格证书类别"));
			}
			String schemeCurtNamePer = schemeDto.getCurtNamePer();
			if (null != schemeCurtNamePer && schemeCurtNamePer.length() == 5) {
				Collection<PerfChangeAnalysisDto> curtNamePers = empPerf.get(empId);
				cnp = analysisCurtNamePer(curtNamePers, schemeCurtNamePer);
				rs.add(new PromotionAnalysisDto(Identities.uuid2(), empId, customerId, schemeCurtNamePer,
						getLastPerName(curtNamePers, empId), cnp, dbCurdate, "绩效等级"));
			}

			int fh = 0;
			if (ca == 1)
				fh++;
			if (cne == 1)
				fh++;
			if (c == 1)
				fh++;
			if (ct == 1)
				fh++;
			if (cnp == 1)
				fh++;

			// 计算百分比 = 符合总数 / 参与项目
			double div = ArithUtil.div(fh, 5, 2) * 100;
			KVItemDto kv = new KVItemDto();
			kv.setK(empId);
			kv.setV(String.valueOf(div));
			conditionProp.add(kv);
		}

		for (PromotionAnalysisDto dto : rs) {
			System.out.println(
					dto.getEmpId() + "\t" + dto.getPersomaStatus() + "\t" + dto.getIsAccord() + "\t" + dto.getNote());
		}
		return rs;
	}

	// 最后一次绩效名称
	private String getLastPerName(Collection<PerfChangeAnalysisDto> curtNamePers, String empId) {
		ArrayList<PerfChangeAnalysisDto> rs = new ArrayList<PerfChangeAnalysisDto>();
		rs.addAll(curtNamePers);
		return rs.get(0).getPerfName();// 这个数据列在数据库按时间倒序排
	}

	// 此类资格证书
	private int analysisCurtNamePer(Collection<PerfChangeAnalysisDto> curtNamePers, String schemeCurtNamePer) {
		String[] split = schemeCurtNamePer.split("_");
		Integer sTimes = Integer.valueOf(split[0]); // 连续次数(周期)
		Integer sCurtNamePer = Integer.valueOf(split[1]); // 绩效要求
		Integer sLastTimes = Integer.valueOf(split[2]); // 达标次数

		int total = 0;
		// 1.绩效次数大于方案里要求次数
		if (curtNamePers.size() >= sTimes) {
			// 2.循环累计曾经满足方案里要求过绩效次数
			for (PerfChangeAnalysisDto dto : curtNamePers) {
				if (dto.getCurtName() > sCurtNamePer) {
					total++;
				}
			}
			// 3.满足方案里的绩效
			return total > sLastTimes ? 1 : 0;
		}
		total = 0;
		// 1.员工小于要求的绩效总数，但也绩效次满足达标次数，也要计算他累计是否得过方案要求绩效总数
		if (curtNamePers.size() <= sTimes && curtNamePers.size() >= sLastTimes) {
			if (curtNamePers.size() >= sTimes) {
				// 2.循环累计曾经满足方案里要求过绩效次数
				for (PerfChangeAnalysisDto dto : curtNamePers) {
					if (dto.getCurtName() > sCurtNamePer) {
						total++;
					}
				}
				// 3.满足方案里的绩效
				return total > sLastTimes ? 1 : 0;
			}
		}
		return 0;
	}

	// 此类资格证书
	private int analysisCertificateType(Collection<String> certificateTypeIds, String schemeCertificateTypeId) {
		int i = 0;
		for (String id : certificateTypeIds) {
			if (id.equals(schemeCertificateTypeId)) {
				i++;
			}
		}
		return i > 0 ? 1 : 0;
	}

	// 资格证书
	private int analysisCertificate(Collection<String> certificateIds, String schemeCertificateId) {
		int i = 0;
		for (String id : certificateIds) {
			if (id.equals(schemeCertificateId)) {
				i++;
			}
		}
		return i > 0 ? 1 : 0;
	}

	// 司龄分析
	private int analysisCompanyAge(Integer schemeCompanyAge, Double companyAge) {
		return schemeCompanyAge <= companyAge ? 1 : 0;
	}

	// 能力评价分析
	private int analysisCurtNameEval(Integer schemeCurtNameEva, Integer curtNameEval) {
		// 数字越高，等级超高
		return curtNameEval <= schemeCurtNameEva ? 1 : 0;
	}

	/**
	 * 员工绩效
	 * 
	 * @return
	 */
	private Multimap<String, PerfChangeAnalysisDto> getEmpPerfMulti() {
		List<PerfChangeAnalysisDto> empPerf = getEmpPerf();
		Multimap<String, PerfChangeAnalysisDto> rs = ArrayListMultimap.create();
		for (PerfChangeAnalysisDto dto : empPerf) {
			rs.put(dto.getEmpId(), dto);
		}
		return rs;
	}

	private List<PerfChangeAnalysisDto> getEmpPerf() {
		return promotionBoardService.getEmpPerf();
	}

	/**
	 * 员工证书类型
	 * 
	 * @return
	 */
	private Multimap<String, String> getEmpCertificateType() {
		List<KVItemDto> empCertificateType = promotionBoardService.getEmpCertificateType();
		Multimap<String, String> rs = ArrayListMultimap.create();
		for (KVItemDto item : empCertificateType) {
			rs.put(item.getK(), item.getV());
		}
		return rs;
	}

	/**
	 * 员工获取证书维
	 * 
	 * @return
	 */
	private Map<String, String> getCertificateAll() {
		List<KVItemDto> certificateAll = promotionBoardService.getCertificateAll();
		Map<String, String> rs = CollectionKit.newMap();
		for (KVItemDto item : certificateAll) {
			rs.put(item.getK(), item.getV());
		}
		return rs;
	}

	/**
	 * 员工获取证书
	 * 
	 * @return
	 */
	private Multimap<String, String> getEmpCertificate() {
		List<KVItemDto> empCertificate = promotionBoardService.getEmpCertificate();
		Multimap<String, String> rs = ArrayListMultimap.create();
		for (KVItemDto item : empCertificate) {
			rs.put(item.getK(), item.getV());
		}
		return rs;
	}

	/**
	 * 能力评价维
	 * 
	 * @return
	 */
	private Map<Integer, String> getEvalAll() {
		List<KVItemDto> evalAll = promotionBoardService.getEvalAll();
		Map<Integer, String> rs = CollectionKit.newMap();
		for (KVItemDto item : evalAll) {
			rs.put(Integer.valueOf(item.getK()), item.getV());
		}
		return rs;
	}

	/**
	 * 员工能力评价
	 * 
	 * @return
	 */
	private Map<String, Integer> getEmpEval() {
		List<KVItemDto> empEval = promotionBoardService.getEmpEval();
		Map<String, Integer> rs = CollectionKit.newMap();
		for (KVItemDto item : empEval) {
			rs.put(item.getK(), Integer.valueOf(item.getV()));
		}
		return rs;
	}

	/**
	 * 员工司龄
	 * 
	 * @return
	 */
	private Map<String, Double> getEmpCompanyAge() {
		List<KVItemDto> empCompanyAge = promotionBoardService.getEmpCompanyAge();
		Map<String, Double> rs = CollectionKit.newMap();
		for (KVItemDto item : empCompanyAge) {
			rs.put(item.getK(), Double.valueOf(item.getV()));
		}
		return rs;
	}

	/**
	 * 方案
	 * 
	 * @return
	 */
	private Map<String, PromotionElementSchemeDto> getSchemeAll() {
		Map<String, PromotionElementSchemeDto> rs = CollectionKit.newMap();
		List<PromotionElementSchemeDto> schemeAll = promotionBoardService.getSchemeAll();
		for (PromotionElementSchemeDto dto : schemeAll) {
			rs.put(dto.getSchemeId(), dto);
		}
		return rs;
	}

	/**
	 * 员工和方案关系
	 * 
	 * @return
	 */
	private List<KVItemDto> getEmpSchemeRel(String date) {
		return promotionBoardService.getEmpSchemeRel(date);
	}

}
