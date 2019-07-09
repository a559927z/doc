package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.sequenceCount.SourceJobRelationETL;

/**
 * 职位序列
 * @author zhiwei
 * @time 2017年5月17日下午2:24:39
 * @version 20172017年5月17日
 */
public class SequenceCountSyncModule extends ModuleThread {

	private SourceJobRelationETL jobRelationETL = new SourceJobRelationETL();
	@Override
	protected void execute() {
		jobRelationETL.execute();
	}
	public static void main(String[] d) {
		new SequenceCountSyncModule().start();
	}

}
