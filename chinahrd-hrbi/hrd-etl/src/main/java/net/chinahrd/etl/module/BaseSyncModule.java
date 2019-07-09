package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.allBase.SourceBudgetEmpNumberETL;
import net.chinahrd.etl.impl.allBase.SourceBudgetPositionNumberETL;
import net.chinahrd.etl.impl.allBase.SourceDeptKpiETL;
import net.chinahrd.etl.impl.allBase.SourceEmpBonusPenaltyETL;
import net.chinahrd.etl.impl.allBase.SourceEmpCertificateInfoETL;
import net.chinahrd.etl.impl.allBase.SourceEmpContactPersonETL;
import net.chinahrd.etl.impl.allBase.SourceEmpEduETL;
import net.chinahrd.etl.impl.allBase.SourceEmpFamilyETL;
import net.chinahrd.etl.impl.allBase.SourceEmpPastResumeETL;
import net.chinahrd.etl.impl.allBase.SourceEmpProfTitleETL;
import net.chinahrd.etl.impl.allBase.SourceFactFteETL;
import net.chinahrd.etl.impl.allBase.SourceOrganizationEmpRelationETL;
import net.chinahrd.etl.impl.allBase.SourceProfessionValueETL;
import net.chinahrd.etl.impl.allBase.SourceVDimEmpETL;
import net.chinahrd.etl.impl.allBase.SourcePopulationRelationEtl;

/**
 * AllDim
 * 
 * @author zhiwei
 * @version 20172017年4月28日
 */
public class BaseSyncModule extends ModuleThread {

	public void execute() {
		new SourceVDimEmpETL().execute();                
		new SourceEmpFamilyETL().execute();              
		new SourceEmpProfTitleETL().execute();           
		new SourceEmpEduETL().execute();                 
		new SourceEmpBonusPenaltyETL().execute();        
		new SourceEmpContactPersonETL().execute();       
		new SourceEmpPastResumeETL().execute();          
		new SourceEmpCertificateInfoETL().execute();     
		new SourcePopulationRelationEtl().execute();     
		new SourceOrganizationEmpRelationETL().execute();
		new SourceBudgetEmpNumberETL().execute();        
		new SourceBudgetPositionNumberETL().execute();   
		new SourceFactFteETL().execute();                
		new SourceDeptKpiETL().execute();                
		new SourceProfessionValueETL().execute();        
	}
		
	
}
