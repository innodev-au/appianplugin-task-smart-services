package com.appiancorp.expression.custom;

import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.Constants;
import com.appiancorp.suiteapi.common.ResultPage;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.process.ProcessExecutionService;
import com.appiancorp.suiteapi.process.TaskSummary;

@com.appiancorp.suiteapi.expression.annotations.AppianScriptingFunctionsCategory
public class getActiveTasksForProcessFunction {

	private static final Logger LOG = Logger.getLogger(getActiveTasksForProcessFunction.class);

	@Function
	public Long[] getActiveTaskIDsForProcess(ProcessExecutionService pes, @Parameter Long processID,
			@Parameter(required = false) Boolean includeSubs) {
		if (includeSubs == null) {
			includeSubs = false;
		}
		Long[] idArray = new Long[0];
		try {
			ResultPage rp1 = pes.getTasksWithStatusForProcess(processID,
					TaskSummary.TASK_STATUS_ASSIGNED, includeSubs, 0, Constants.COUNT_ALL,
					TaskSummary.SORT_BY_ASSIGNED_TIME, Constants.SORT_ORDER_ASCENDING);
			ResultPage rp2 = pes.getTasksWithStatusForProcess(processID,
					TaskSummary.TASK_STATUS_ACCEPTED, includeSubs, 0, Constants.COUNT_ALL,
					TaskSummary.SORT_BY_ASSIGNED_TIME, Constants.SORT_ORDER_ASCENDING);
			idArray = new Long[rp1.getNumResults() + rp2.getNumResults()];
			TaskSummary[] ts1 = (TaskSummary[]) rp1.getResults();
			TaskSummary[] ts2 = (TaskSummary[]) rp2.getResults();

			for (int i = 0; i < ts1.length; i++) {
				idArray[i] = ts1[i].getId();
			}
			for (int i = 0; i < ts2.length; i++) {
				idArray[i + ts1.length] = ts2[i].getId();
			}
		} catch (Exception e) {
			LOG.error(e, e);
		}
		return idArray;
	}

}
