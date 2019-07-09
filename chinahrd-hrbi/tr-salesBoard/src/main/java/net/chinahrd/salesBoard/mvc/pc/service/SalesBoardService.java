package net.chinahrd.salesBoard.mvc.pc.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesBoardDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesBoardQueryDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesConfigDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesMapsPointDto;
import net.chinahrd.entity.dto.pc.salesBoard.SalesMapsSimpleCountDto;


/**
 * 销售看板
 * 
 * @author lma and xwli 2016-08-16
 */
public interface SalesBoardService {
	
	/**
	 * 获取销售产品
	 * 
	 * @param customerId
	 * @return
	 */
	List<SalesBoardDto> querySalesProducts(String customerId);

	/**
	 * 获取时间
	 */
	Map<String, Object> queryTimes(String customerId, String organId);

	/**
	 * 查询预警设置值
	 * 
	 * @return
	 */
	Map<String, Object> queryWarningInfo(String customerId, String empId);

	/**
	 * 修改预警设置值
	 * 
	 * @return
	 */
	void updateWarningInfo(String customerId, String empId, SalesConfigDto dto);

	/**
	 * 当月销售额及比较
	 */
	Map<String, Object> getCurMonthSalesVal(String customerId, String organId);

	/**
	 * 当月销售异常组织
	 */
	Map<String, Object> getCurMonthSalesExctOrgan(String customerId, String organId, String yellowRange,
			String redRange);

	/**
	 * 人员流失异常
	 */
	Map<String, Object> getCurMonthPersonFlow(String customerId, String organId);

	/**
	 * 业务能力考核
	 */
	Map<String, Object> getCurMonthAbilityCheck(String customerId, String organId);

	/**
	 * 查询年销售额
	 */
	Map<String, Object> querySalesAndTarget(String customerId, String organId, int time, String productId, Integer salesType);

	/**
	 * 查询团队销售额和达标率
	 */
	PaginationDto<SalesBoardDto> queryTeamSalesAndTarget(String customerId, String organId, int yearMonth,
			Integer teamStandardRate, Double beginSales, Double endSales, Double beginReturnAmount,
			Double endReturnAmount, PaginationDto<SalesBoardDto> dto);

	/**
	 * 查询个人销售额和达标率
	 */
	PaginationDto<SalesBoardDto> queryPersonSalesAndTarget(String customerId, String organId, int yearMonth, String keyName,
			Integer personStandard, Double beginPersonSales, Double endPersonSales, Double beginPersonAmount,
			Double endPersonAmount, PaginationDto<SalesBoardDto> dto);

	/**
	 * 今日销售量/今日销售额/今日利润
	 */
	Map<String, Object> queryTodaySalesInfo(String customerId, String organId);

	/**
	 * 今日销售量/今日销售额/今日利润-地图显示数据
	 */
	Map<String, Object> querySalesMapByProvince(String customerId, String organId, String provinceId);

	/**
	 * 组织销售统计
	 */
	Map<String, Object> queryOrganSalesStatistics(String customerId, String organId, String date, int flag);

	/**
	 * 销售排名榜
	 */
	Map<String, Object> querySalesRanking(String customerId, String organId, String date);

	/**
	 * 查询下级组织机构
	 */
	List<SalesBoardDto> querySubOrganization(String customerId, String organId);

	/**
	 * 查询团队时间
	 */
	List<SalesBoardDto> queryTeamTime(String customerId);

	/**
	 * 查询能力趋势
	 */
	Map<String, Object> queryAbilityByEmpid(String customerId, String empId);

	/**
	 * 查询排名趋势
	 */
	Map<String, Object> queryRankByEmpid(String customerId, String organId, String empId);

	/**
	 * 查询组织销售额
	 */
	List<SalesBoardDto> querySalesByOrgan(String customerId, String organId, String now, Integer salesType);
	
	/**
	 * 查询销售人员异动情况
	 */
	PaginationDto<SalesBoardDto> queryEmpChangeInfo(PaginationDto<SalesBoardDto> dto, String customerId, String organId,
			String userName, String time);
	
	/**
	 * 人员变动-销售额
	 */
	Map<String, Object> queryChangeByMonth(String customerId, String organId, String time, Integer salesType);

	/**
	 * 销售排名地图-团队PK
	 */
	Map<String, Object> querySalesRankMapTeamPK(String customerId, String organId);
	
	/**
	 * 销售排名地图-团队总览chart
	 */
	List<SalesMapsSimpleCountDto> queryTeamViewChart(String customerId, String organId, String date);
	
	/**
	 * 销售排名地图-团队总览全屏chart
	 */
	List<SalesBoardDto> queryTeamViewFullChart(String customerId, String organId, String date, String rows);
	
	/**
	 * 销售排名地图-团队PKchart
	 */
	List<SalesMapsPointDto> queryTeamPKChart(SalesBoardQueryDto dto);
	
	/**
	 * 销售排名地图-团队PK获取地图员工点信息
	 */
	List<SalesMapsPointDto> queryTeamEmpPoint(SalesBoardQueryDto dto);
	
	/**
	 * 销售排名地图-获取x轴y轴信息
	 */
	Map<String, Object> getMapsBaseInfo(String customerId);
	
	/**
	 * 销售排名地图-列表
	 */
	Map<String, Object> queryMapsGrid(SalesBoardQueryDto dto);
	
	/**
	 * 销售排名地图-员工统计数量
	 */
	int getTeamSettingEmpCount(SalesBoardQueryDto dto);
	
	/**
	 * 员工对比
	 */
	List<Object> querySalesByEmpid(String customerId, String organId, String now, 
			Integer salesType, String[] empArr, String[] changeTypeArr);
	
	/**
	 * 人员变动-异动情况
	 */
	List<SalesBoardDto> queryChangeInfo(String customerId, String organId, 
			String time, Integer flag, String index, Integer salesType);
	
	/**
	 * 根据id，获取管理者及管理者下属销售人员销售总额、销售利润、回款总额
	 * @param customerId 客户id
	 * @param empId 管理者/销售人员id
	 * */
	List<SalesBoardDto> querySalesMoneyAndProfitAndReturnAmount(String customerId, String empId);
	
	/**
	 * 根据id，获取客户购买总额、销售利润、回款总额
	 * @param customerId 客户id
	 * @param empId 销售人员id
	 * @param clientId 销售客户id
	 * */
	List<SalesBoardDto> queryClientSalesMoneyAndProfitAndReturnAmount(String customerId, String empId, String clientId);
	
	/**
	 * 根据id，获取管理者及管理者下属销售人员销售额和环比变化
	 * @param customerId 客户id
	 * @param empId 管理者/销售人员id
	 * @param row 最近几个月
	 * */
	Map<String, Object> querySalesMoneyAndRing(String customerId, String empId, Integer row);
	
	/**
	 * 根据id，获取销售人员/客户销售额和环比变化
	 * @param customerId 客户id
	 * @param empId 管理者/销售人员id
	 * @param clientId 销售客户id
	 * @param row 最近几个月
	 * */
	Map<String, Object> queryClientSalesMoneyAndRing(String customerId, String empId, String clientId, Integer row);
	
	/**
	 * 根据id，获取管理者及管理者下属销售人员销售额和环比变化
	 * @param customerId 客户id
	 * @param empId 管理者/销售人员id
	 * @param row 最近几个月
	 * */
	Map<String, Object> querySalesReturnAmountAndRing(String customerId, String empId, Integer row);
	
	/**
	 * 根据id，获取管理者及管理者下属销售人员销售额和环比变化
	 * @param customerId 客户id
	 * @param empId 管理者/销售人员id
	 * @param clientId 销售客户id
	 * @param row 最近几个月
	 * */
	Map<String, Object> queryClientReturnAmountAndRing(String customerId, String empId, String clientId, Integer row);
}
