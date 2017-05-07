package com.ddpms.model;

import java.util.List;

public class Task {

    private String taskId;
    private String taskName;
    private String modifiedDate;
    private String modifiedBy;
    
    private List<TaskAssign> taskAssignList;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

   
    
    

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
