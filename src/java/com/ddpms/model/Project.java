
package com.ddpms.model;

import java.util.List;


public class Project {
    private String projId;
    
    private String projName;
    
    private String projDetails;
    
    private String projStatus;
    
    private String planId;
    
    private String budpId;

    private String modifiedDate;

    private String modifiedBy;
    
     private List<TaskAssign> taskAssignList;

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjDetails() {
        return projDetails;
    }

    public void setProjDetails(String projDetails) {
        this.projDetails = projDetails;
    }

    public String getProjStatus() {
        return projStatus;
    }

    public void setProjStatus(String projStatus) {
        this.projStatus = projStatus;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getBudpId() {
        return budpId;
    }

    public void setBudpId(String budpId) {
        this.budpId = budpId;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public List<TaskAssign> getTaskAssignList() {
        return taskAssignList;
    }

    public void setTaskAssignList(List<TaskAssign> taskAssignList) {
        this.taskAssignList = taskAssignList;
    }
    
    
}
