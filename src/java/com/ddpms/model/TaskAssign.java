package com.ddpms.model;

public class TaskAssign {

    private String taskaId;
    private int taskId;
    private String taskName;
    private int projId;
    private String projName;
    private int taskUserId;
    private String taskUsername;
    private String modifiedDate;
    private String taskaAssignDate;
    private String taskaTargetDate;
    private int modifiedBy;

    public String getTaskaId() {
        return taskaId;
    }

    public void setTaskaId(String taskaId) {
        this.taskaId = taskaId;
    }
        

    

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getProjId() {
        return projId;
    }

    public void setProjId(int projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public int getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(int taskUserId) {
        this.taskUserId = taskUserId;
    }

    

    public String getTaskUsername() {
        return taskUsername;
    }

    public void setTaskUsername(String taskUsername) {
        this.taskUsername = taskUsername;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getTaskaAssignDate() {
        return taskaAssignDate;
    }

    public void setTaskaAssignDate(String taskaAssignDate) {
        this.taskaAssignDate = taskaAssignDate;
    }

    public String getTaskaTargetDate() {
        return taskaTargetDate;
    }

    public void setTaskaTargetDate(String taskaTargetDate) {
        this.taskaTargetDate = taskaTargetDate;
    }

    

}
