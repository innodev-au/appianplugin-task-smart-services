package com.appiancorp.process.runtime.activities;

import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.common.exceptions.InvalidStateException;
import com.appiancorp.suiteapi.common.exceptions.PrivilegeException;
import com.appiancorp.suiteapi.process.ProcessExecutionService;
import com.appiancorp.suiteapi.process.exceptions.InvalidActivityException;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;

@PaletteInfo(paletteCategory = "Appian Smart Services", palette = "Task Services") 
public class PauseTaskActivity extends AppianSmartService {

	private static final Logger LOG = Logger.getLogger(PauseTaskActivity.class);
	private final ProcessExecutionService _pes;
	private Long taskID;

	@Override
	public void run() throws SmartServiceException {
		try {
			_pes.pauseTask(taskID);
		} catch (InvalidActivityException e) {
			LOG.error(e,e);
		} catch (InvalidStateException e) {
			LOG.error(e,e);
		} catch (PrivilegeException e) {
			LOG.error(e,e);
		}

	}

	public PauseTaskActivity(ProcessExecutionService pes_) {
		super();
		this._pes = pes_;
	}

	public void onSave(MessageContainer messages) {
	}

	public void validate(MessageContainer messages) {
	}

	@Input(required = Required.OPTIONAL)
	@Name("taskID")
	public void setTaskID(Long val) {
		this.taskID = val;
	}

}
