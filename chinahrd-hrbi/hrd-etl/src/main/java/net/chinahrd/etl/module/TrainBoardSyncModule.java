package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.trainBoard.SourceCourseRecordETL;
import net.chinahrd.etl.impl.trainBoard.SourceEmpTrainExperienceETL;
import net.chinahrd.etl.impl.trainBoard.SourceLecturerCourseDesignETL;
import net.chinahrd.etl.impl.trainBoard.SourceLecturerCourseSpeakETL;
import net.chinahrd.etl.impl.trainBoard.SourceLecturerETL;
import net.chinahrd.etl.impl.trainBoard.SourceTrainOutlayETL;
import net.chinahrd.etl.impl.trainBoard.SourceTrainPlanETL;
import net.chinahrd.etl.impl.trainBoard.SourceTrainSatfacETL;
import net.chinahrd.etl.impl.trainBoard.SourceTrainValueETL;

public class TrainBoardSyncModule extends ModuleThread {

	@Override
	protected void execute() {
		new SourceTrainOutlayETL().execute();
		new SourceTrainValueETL().execute();
		new SourceTrainPlanETL().execute();
		new SourceEmpTrainExperienceETL().execute();
		new SourceTrainSatfacETL().execute();
		new SourceCourseRecordETL().execute();
		new SourceLecturerETL().execute();
		new SourceLecturerCourseDesignETL().execute();
		new SourceLecturerCourseSpeakETL().execute();
	}
	public static void main(String... arg){
		new TrainBoardSyncModule().start();
	}

}
