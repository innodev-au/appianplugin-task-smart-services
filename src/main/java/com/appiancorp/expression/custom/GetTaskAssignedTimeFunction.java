package com.appiancorp.expression.custom;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.ServiceLocator;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.process.ProcessExecutionService;
import com.appiancorp.suiteapi.process.TaskDetails;

@com.appiancorp.suiteapi.expression.annotations.AppianScriptingFunctionsCategory
public class GetTaskAssignedTimeFunction {

	private static final Logger LOG = Logger.getLogger(GetTaskAssignedTimeFunction.class);

	@Function
	public Timestamp getTaskAssignedTimestamp(ProcessExecutionService pes, @Parameter Long taskID) {
		Timestamp t = null;
		try {
			TaskDetails td = pes.getTaskDetails(taskID);
			t = td.getAssignedTime();
		} catch (Exception e) {
			LOG.error(e, e);
		}
		return t;
	}

}
