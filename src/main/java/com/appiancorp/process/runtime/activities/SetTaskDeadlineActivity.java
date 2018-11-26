package com.appiancorp.process.runtime.activities;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.common.exceptions.PrivilegeException;
import com.appiancorp.suiteapi.process.ProcessExecutionService;
import com.appiancorp.suiteapi.process.exceptions.InvalidActivityException;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Order;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;

@PaletteInfo(paletteCategory = "Appian Smart Services", palette = "Task Services")
@Order({"taskID","deadline"})
public class SetTaskDeadlineActivity extends AppianSmartService {

	private static final Logger LOG = Logger.getLogger(SetTaskDeadlineActivity.class);
	private Long taskID;
	private Timestamp deadline;
	private Boolean errorOccurred = true;
	private String errorText;
	private final ProcessExecutionService _pes;

	@Override
	public void run() throws SmartServiceException {
		try {
			_pes.setDeadlineForTask(taskID, deadline);
			errorOccurred = false;
		} catch (PrivilegeException pe) {
			LOG.error(pe, pe);
			errorText = pe.getMessage();
		} catch (InvalidActivityException e) {
			LOG.error(e, e);
			errorText = e.getMessage();
		}
	}

	public SetTaskDeadlineActivity(ProcessExecutionService pes) {
		super();
		this._pes = pes;
	}

	public void onSave(MessageContainer messages) {
	}

	public void validate(MessageContainer messages) {
	}

	@Input(required = Required.ALWAYS)
	@Name("taskID")
	public void setTaskID(Long val) {
		this.taskID = val;
	}

	@Input(required = Required.OPTIONAL)
	@Name("deadline")
	public void setDeadline(Timestamp val) {
		this.deadline = val;
	}

	@Name("errorOccurred")
	public Boolean getErrorOccurred() {
		return errorOccurred;
	}

	@Name("errorText")
	public String getErrorText() {
		return errorText;
	}

}
