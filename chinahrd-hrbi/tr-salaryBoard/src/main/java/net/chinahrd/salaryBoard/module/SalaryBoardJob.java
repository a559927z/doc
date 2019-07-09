package net.chinahrd.salaryBoard.module;

import org.springframework.beans.factory.annotation.Autowired;

import net.chinahrd.core.job.JobRegisterAbstract;
import net.chinahrd.core.timer.model.JobContext;
import net.chinahrd.core.timer.model.TimerConfig;
import net.chinahrd.salaryBoard.mvc.pc.service.SalaryBoardService;

public class SalaryBoardJob extends JobRegisterAbstract {

	@Autowired
	private SalaryBoardService salaryBoService;
	@Override
	public void execute(JobContext arg0) {

		salaryBoService.proFetchPayCollectYear();
	}

	@Override
	public void setTimerConfig(TimerConfig arg0) {
		// TODO Auto-generated method stub

	}

}
