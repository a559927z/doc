package net.chinahrd.salesBoard.mvc.pc.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
@Repository("salesBoardDao")
public interface SalesBoardDao {

	/**
	 * 获取销售产品
	 */
	List<SalesBoardDto> querySalesProducts(@Param("customerId") String customerId);

	/**
	 * 获取时间
	 */
	List<SalesBoardDto> queryTimes(@Param("customerId") String customerId, @Param("organId") String organId);

	/**
	 * 查询预警设置信息
	 */
	List<SalesConfigDto> queryWarningInfo(@Param("customerId") String customerId, @Param("empId") String empId);

	/**
	 * 添加预警设置信息
	 */
	boolean addWarningInfo(SalesConfigDto dto);

	/**
	 * 修改预警设置信息
	 */
	boolean updateWarningInfo(SalesConfigDto dto);
	
	/**
	 * 删除预警设置信息
	 */
	boolean deleteWarningInfo(@Param("customerId") String customerId, @Param("empId") String empId);

	/**
	 * 当月销售额及比较
	 */
	List<SalesBoardDto> getCurMonthSalesVal(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("curDate") String curDate, @Param("befDate") String befDate, @Param("subOrganIdList") List<String> subOrganIdList);

	/**
	 * 当月销售异常组织-red
	 */
	List<SalesBoardDto> getCurMonthSalesExctOrganByRed(Map<String, Object> parMap);

	/**
	 * 当月销售异常组织-yellow
	 */
	List<SalesBoardDto> getCurMonthSalesExctOrganByYellow(Map<String, Object> parMap);

	/**
	 * 人员流失异常
	 */
	List<SalesBoardDto> getCurMonthPersonFlow(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("date") String date);

	/**
	 * 业务能力考核
	 */
	List<SalesBoardDto> getCurMonthAbilityCheck(@Param("customerId") String customerId,
			@Param("subOrganIdList") List<String> subOrganIdList);

	/**
	 * 查询年销售额和达标率
	 */
	List<SalesBoardDto> querySalesAndTarget(Map<String, Object> map);

	/**
	 * 查询年销售额和达标率-产品
	 */
	List<SalesBoardDto> querySalesByProduct(Map<String, Object> map);

	/**
	 * 查询团队销售额和达标率
	 */
	int queryTeamSalesAndTargetCount(Map<String, Object> map);
	List<SalesBoardDto> queryTeamSalesAndTarget(Map<String, Object> map);

	/**
	 * 查询个人销售额和达标率
	 */
	int queryPersonSalesAndTargetCount(Map<String, Object> map);
	List<SalesBoardDto> queryPersonSalesAndTarget(Map<String, Object> map);

	/**
	 * 今日销售量/今日销售额/今日利润
	 */
	List<SalesBoardDto> queryTodaySalesInfo(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("date") String date, @Param("subOrganIdList") List<String> subOrganIdList);

	/**
	 * 查询省份信息
	 */
	List<SalesBoardDto> queryProvinceInfo(@Param("customerId") String customerId);

	/**
	 * 今日销售量/今日销售额/今日利润-地图显示数据
	 */
	List<SalesBoardDto> querySalesMapByProvince(@Param("customerId") String customerId, @Param("date") String date);

	/**
	 * 今日销售量/今日销售额/今日利润-地图显示数据
	 */
	List<SalesBoardDto> querySalesMapByCity(@Param("customerId") String customerId,
			@Param("provinceId") String provinceId, @Param("date") String date);

	/**
	 * 查询下级组织
	 */
	List<SalesBoardDto> queryNextOrganInfo(@Param("customerId") String customerId, @Param("organId") String organId);

	/**
	 * 组织销售统计
	 */
	List<SalesBoardDto> queryOrganSalesStatistics(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("date") String date, @Param("subOrganIdList") List<String> subOrganIdList);
	
	/**
	 * 团队排行榜-团队
	 */
	List<SalesBoardDto> queryOrganSalesStatisticsWithTeam(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("date") String date);
	
	/**
	 * 团队排行榜-个人
	 */
	List<SalesBoardDto> queryOrganSalesStatisticsWithEmp(@Param("customerId") String customerId,
			@Param("teamId") String teamId, @Param("date") String date);
	
	/**
	 * 销售排名榜-总数
	 */
	int querySalesRankingCount(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("date") String date);

	/**
	 * 销售排名榜
	 */
	List<SalesBoardDto> querySalesRanking(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("date") String date);

	/**
	 * 查询下级组织机构
	 */
	List<SalesBoardDto> querySubOrganization(@Param("customerId") String customerId, @Param("organId") String organId);

	/**
	 * 查询团队时间
	 */
	List<SalesBoardDto> queryTeamTime(@Param("customerId") String customerId);

	/**
	 * 查询能力趋势
	 */
	List<SalesBoardDto> queryAbilityByEmpid(@Param("customerId") String customerId, @Param("empId") String empId);

	/**
	 * 查询排名趋势
	 */
	List<SalesBoardDto> queryRankByEmpid(@Param("customerId") String customerId, @Param("organId") String organId, @Param("empId") String empId);

	/**
	 * 查询组织销售额
	 */
	List<SalesBoardDto> querySalesByOrgan(Map<String, Object> map);

	/**
	 * 销售排名地图-团队PK
	 */
	int querySalesRankMapTeamPK(@Param("customerId") String customerId,
			@Param("organId") String organId, @Param("yearMonth") String yearMonth);

	/**
	 * 查询能力信息
	 */
	List<SalesBoardDto> queryAbilityInfo(@Param("customerId") String customerId);

	/**
	 * 查询排名信息
	 */
	List<SalesBoardDto> queryRankChartInfo(@Param("customerId") String customerId);

	/**
	 * 销售排名地图-团队总览chart
	 */
	List<SalesMapsSimpleCountDto> queryTeamViewChart(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("date") String date, @Param("subOrganIdList") List<String> subOrganIdList);
	
	/**
	 * 销售排名地图-团队总览全屏chart
	 */
	List<SalesBoardDto> queryTeamViewFullChart(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("date") String date, @Param("rows") Integer rows, @Param("subOrganIdList") List<String> subOrganIdList);

	/**
	 * 销售排名地图-团队PKchart
	 */
	List<SalesMapsPointDto> queryTeamPKChart(SalesBoardQueryDto dto);
	
	/**
	 * 销售排名地图-团队PK获取员工点信息
	 */
	List<SalesMapsPointDto> queryTeamEmpPoint(SalesBoardQueryDto dto);

	/**
	 * 销售排名地图-团队PK 获取配置表信息
	 */
	List<SalesBoardDto> queryMapsConfigInfo(@Param("customerId") String customerId,
			@Param("configKey") String configKey);

	/**
	 * 销售排名地图-view列表
	 */
	int queryMapsGridCount(SalesBoardQueryDto dto);

	/**
	 * 销售排名地图-view列表
	 */
	int queryMapsGridCountByChart(SalesBoardQueryDto dto);

	/**
	 * 销售排名地图-pk列表
	 */
	int queryMapsPKGridCount(Map<String, Object> map);

	/**
	 * 销售排名地图-pk列表
	 */
	int queryMapsPKGridCountByChart(Map<String, Object> map);

	/**
	 * 查询销售人员异动情况
	 */
	List<SalesBoardDto> queryEmpChangeInfo(Map<String, Object> map);

	/**
	 * 查询销售人员异动情况统计
	 */
	int queryEmpChangeInfoCount(Map<String, Object> map);

	/**
	 * 员工对比
	 */
	List<SalesBoardDto> querySalesByEmpid(Map<Object, Object> map);

	/**
	 * 人员变动-销售额
	 */
	LinkedList<SalesBoardDto> queryChangeByMonth(@Param("customerId") String customerId, @Param("organId") String organId,
			@Param("beginTime") String beginTime, @Param("endTime") String endTime,
			@Param("salesType") Integer salesType);

	/**
	 * 销售地图排名-统计员工数量
	 */
	int getTeamSettingEmpCount(Map<String, Object> map);
	
	/**
	 * 查询是否存在销售员工异动
	 */
	List<SalesBoardDto> queryPersonChangeCount(Map<String, Object> map);

	/**
	 * 人员变动-异动情况
	 */
	List<SalesBoardDto> queryChangeInfo(Map<String, Object> pMap);

	/**
	 * 根据id，获取管理者及管理者下属销售人员销售总额、销售利润、回款总额
	 * */
	List<SalesBoardDto> querySalesMoneyAndProfitAndReturnAmount(Map<String, Object> map);
	
	/**
	 * 根据id，获取销售客户销售总额、销售利润、回款总额
	 * */
	List<SalesBoardDto> queryClientSalesMoneyAndProfitAndReturnAmount(Map<String, Object> map);
	
	/**
	 * 根据id，获取管理者及管理者下属销售人员销售额
	 * */
	List<SalesBoardDto> querySalesMoney(@Param("customerId") String customerId, @Param("empId") String empId, @Param("row") Integer row);
	
	/**
	 * 根据id，获取销售客户销售额
	 * */
	List<SalesBoardDto> queryClientSalesMoney(@Param("customerId") String customerId, @Param("empId") String empId, 
			@Param("clientId") String clientId, @Param("row") Integer row);
	
	/**
	 * 根据id，获取管理者及管理者下属销售人员回款额
	 * */
	List<SalesBoardDto> querySalesReturnAmount(@Param("customerId") String customerId, @Param("empId") String empId, @Param("row") Integer row);
	
	/**
	 * 根据id，获取销售客户回款额
	 * */
	List<SalesBoardDto> queryClientReturnAmount(@Param("customerId") String customerId, @Param("empId") String empId, 
			@Param("clientId") String clientId, @Param("row") Integer row);
	
}
