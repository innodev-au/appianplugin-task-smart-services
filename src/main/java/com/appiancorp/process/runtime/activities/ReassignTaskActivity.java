package com.appiancorp.process.runtime.activities;

import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.LocalObject;
import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.common.ObjectTypeMapping;
import com.appiancorp.suiteapi.personalization.UserOrGroupDataType;
import com.appiancorp.suiteapi.process.Assignment;
import com.appiancorp.suiteapi.process.Assignment.Assignee;
import com.appiancorp.suiteapi.process.ProcessExecutionService;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;

@PaletteInfo(paletteCategory = "Appian Smart Services", palette = "Task Services") 
public class ReassignTaskActivity extends AppianSmartService {

	private static final Logger LOG = Logger.getLogger(ReassignTaskActivity.class);
	private final ProcessExecutionService _pes;
	private Long taskID;
	private LocalObject[] newAssignees;
	private Boolean errorOccurred=true;
	private String errorText;

	@Override
	public void run() throws SmartServiceException {
		  int numNewAssignees=newAssignees.length;
		    Assignee[]aa=new Assignee[numNewAssignees];
		    
		    for(int i=0;i<numNewAssignees;i++){
		      aa[i]=new Assignee();
		      Integer objType=newAssignees[i].getType();
		      if(objType.equals(ObjectTypeMapping.TYPE_USER)){//user
		        aa[i].setType(new Long(Assignment.ASSIGNEE_TYPE_USERS));
		        aa[i].setValue(newAssignees[i].getStringId());
		        aa[i].setPrivilege(Assignment.PRIVILEGE_REASSIGN_TO_ANY);
		      }
		      else if(objType.equals(ObjectTypeMapping.TYPE_GROUP)){//group
		        aa[i].setType(new Long(Assignment.ASSIGNEE_TYPE_GROUPS));
		        aa[i].setValue(newAssignees[i].getId());
		        aa[i].setPrivilege(Assignment.PRIVILEGE_REASSIGN_TO_ANY);
		      }
		    }
		     errorOccurred = false;
		     errorText = null;

		    try{
		      _pes.reassignTask(taskID, aa);
		    }
		    catch(Exception e){
		      LOG.error(e,e);
		      errorOccurred=true;
		      errorText=e.getMessage();
		    }
	}

	public ReassignTaskActivity(ProcessExecutionService pes_) {
		super();
		this._pes = pes_;
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
	@Name("newAssignees")
	@UserOrGroupDataType
	public void setNewAssignees(LocalObject[] val) {
		this.newAssignees = val;
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
