package com.ddpms.model;

public class TaskAssign {

    private int taskaId;
    private int taskId;
    private String taskName;
    private int projId;
    private String projName;
    private String taskUserId;
    private String taskUsername;
    private String modifiedDate;
    private String modifiedBy;
        

    public int getTaskaId() {
        return taskaId;
    }

    public void setTaskaId(int taskaId) {
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

    public String getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(String taskUserId) {
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

}
