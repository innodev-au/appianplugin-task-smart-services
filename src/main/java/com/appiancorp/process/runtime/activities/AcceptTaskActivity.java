package com.appiancorp.process.runtime.activities;

import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.common.exceptions.PrivilegeException;
import com.appiancorp.suiteapi.process.ProcessExecutionService;
import com.appiancorp.suiteapi.process.exceptions.InvalidActivityException;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.framework.SmartServiceContext;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;

@PaletteInfo(paletteCategory = "Appian Smart Services", palette = "Task Services") 
public class AcceptTaskActivity extends AppianSmartService {

	private static final Logger LOG = Logger.getLogger(AcceptTaskActivity.class);
	private final ProcessExecutionService _pes;
	private final SmartServiceContext _ssc;
	private Long taskID;
	private Boolean errorOccurred=false;
	private String errorText;

	@Override
	public void run() throws SmartServiceException {
		try {
			_pes.acceptTask(taskID);
		} catch (InvalidActivityException e) {
			LOG.error(e,e);
			errorOccurred=true;
			errorText="Invalid Activity ID: "+taskID;
			e.printStackTrace();
		} catch (PrivilegeException e) {
			LOG.error(e,e);
			errorOccurred=true;
			errorText="Privilege Exception on Activity ID "+taskID+" for user "+_ssc.getUsername();
		}

	}

	public AcceptTaskActivity(SmartServiceContext ssc_, ProcessExecutionService pes_) {
		super();
		this._pes = pes_;
		this._ssc=ssc_;
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

	@Name("errorOccurred")
	public Boolean getErrorOccurred() {
		return errorOccurred;
	}

	@Name("errorText")
	public String getErrorText() {
		return errorText;
	}

}
