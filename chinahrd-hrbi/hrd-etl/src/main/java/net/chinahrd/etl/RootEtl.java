/**
*net.chinahrd.etl
*/
package net.chinahrd.etl;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import net.chinahrd.etl.module.AccordDismissSyncModule;
import net.chinahrd.etl.module.BaseSyncModule;
import net.chinahrd.etl.module.BenefitSyncModule;
import net.chinahrd.etl.module.DimSyncModule;
import net.chinahrd.etl.module.DismissRiskSyncModule;
import net.chinahrd.etl.module.EmpSatisfactionSyncModule;
import net.chinahrd.etl.module.EmployeePerformanceSyncModule;
import net.chinahrd.etl.module.HumanInventorySyncModule;
import net.chinahrd.etl.module.KeyTalentSyncModule;
import net.chinahrd.etl.module.LaborEfficiencySyncModule;
import net.chinahrd.etl.module.ManpowerCostSyncModule;
import net.chinahrd.etl.module.PositionCompetencySyncModule;
import net.chinahrd.etl.module.PromotionBoardSyncModule;
import net.chinahrd.etl.module.RecruitBoardSyncModule;
import net.chinahrd.etl.module.SalaryBoardSyncModule;
import net.chinahrd.etl.module.SalesBoardSyncModule;
import net.chinahrd.etl.module.SequenceCountSyncModule;
import net.chinahrd.etl.module.TalentMapsModule;
import net.chinahrd.etl.module.TalentProfitLossModule;
import net.chinahrd.etl.module.TalentProfitModule;
import net.chinahrd.etl.module.TeamImgSyncModule;
import net.chinahrd.etl.module.TrainBoardSyncModule;
import net.chinahrd.util.EtlStatusSync;
import net.chinahrd.utils.PropertiesUtil;
import net.chinahrd.utils.db.DatabaseUtil;

/**
 * 数据抽取根节点
 * 
 * @author htpeng 2017年4月11日下午6:03:44
 */
public class RootEtl {
	private static final Logger log = Logger.getLogger(RootEtl.class);

	private StatusTable statusTable = new StatusTable();

	// 写数据连接
	private String propsPath = "conf/default_database.properties";
	private final String URL = PropertiesUtil.getProperty(propsPath, "w-url");
	private final String USER = PropertiesUtil.getProperty(propsPath, "w-user");
	private final String PASSWORD = PropertiesUtil.getProperty(propsPath, "w-password");
	private final String DRIVER = PropertiesUtil.getProperty(propsPath, "w-driver");

	public RootEtl() {
		EtlStatusSync.setMainThreadStatus(false);
//		log.info("开始执行ROOTetl");
	}

	/**
	 * 各指标抽取
	 */
	private void executeEtl() {
		new DimSyncModule().start(); // 串
		new BaseSyncModule().start();// 串

		// 人均效益
		new BenefitSyncModule().start();
		// 主动流失率
		new AccordDismissSyncModule().start();
		// 人才稳定性(人才风险)
		new DismissRiskSyncModule().start();
		// 劳动力效能
		new LaborEfficiencySyncModule().start();
		// 员工绩效
		new EmployeePerformanceSyncModule().start();
		// 员工忠诚度满意度(员工满意度)
		new EmpSatisfactionSyncModule().start();
		// 项目人力盘点
		new HumanInventorySyncModule().start();
		// 团队画像
		new TeamImgSyncModule().start();
		// 岗位胜任度
		new PositionCompetencySyncModule().start();
		// 人力成本
		new ManpowerCostSyncModule().start();
		// 销售看板
		new SalesBoardSyncModule().start();
		// 晋级看板
		new PromotionBoardSyncModule().start();
		// 薪酬看板
		new SalaryBoardSyncModule().start();
		//招聘看板
		new RecruitBoardSyncModule().start();
		// 销售看板
		new SalesBoardSyncModule().start();
		// 职位序列统计
		new SequenceCountSyncModule().start();
		// 人才地图
		new TalentMapsModule().start();
		// 人才损益
		new TalentProfitLossModule().start();
		// 人才剖象
		new TalentProfitModule().start();
		// 培训看板
		new TrainBoardSyncModule().start();
		//关键人才库
		new KeyTalentSyncModule().start();
		EtlStatusSync.setMainThreadStatus(true);

	}
	public static void main(String... arg){
		new RootEtl().start();
		
	}

	/**
	 * 所有ETL抽取入口
	 */
	public void start() {
		DatabaseUtil databaseUtil = null;
		try {
			databaseUtil = new DatabaseUtil(URL, USER, PASSWORD, DRIVER);
			databaseUtil.saveOrUpdate(statusTable.updateAllStart());

			databaseUtil.saveOrUpdate(StatusTable.PSROCESS_STATUS_SQL);

			executeEtl();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != databaseUtil) {
				databaseUtil.close();
			}
		}
	}

}
