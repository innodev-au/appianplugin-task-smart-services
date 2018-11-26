package com.appiancorp.process.runtime.activities;

import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.process.ProcessExecutionService;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Order;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;

@PaletteInfo(paletteCategory = "Appian Smart Services", palette = "Task Services") 
@Order({"taskID","priority"})
public class SetTaskPriorityActivity extends AppianSmartService {

	private static final Logger LOG = Logger.getLogger(SetTaskPriorityActivity.class);
	private Long taskID;
	private Long priority;
	private Boolean errorOccurred=true;
	private String errorText;
	private ProcessExecutionService _pes;

	@Override
	public void run() throws SmartServiceException {
		try {
			_pes.setPriorityOfTask(taskID, priority);
			errorOccurred = false;
		} catch (Exception pe) {
			LOG.error(pe, pe);
			errorText = pe.getMessage();
		}

	}

	public SetTaskPriorityActivity(ProcessExecutionService pes_) {
		super();
		_pes=pes_;
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

	@Input(required = Required.ALWAYS)
	@Name("priority")
	public void setPriority(Long val) {
		this.priority = val;
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
