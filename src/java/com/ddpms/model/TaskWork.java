package com.ddpms.model;

public class TaskWork {

    private String taskwId;
    private String taskwDetail;
    private int taskwManday;
    private int taskaId;
    private String taskName;
    private int projId;
    private String projName;
    private String taskwDate;
    private String modifiedDate;
    private int modifiedBy;        

    public String getTaskwId() {
        return taskwId;
    }

    public void setTaskwId(String taskwId) {
        this.taskwId = taskwId;
    }

    public String getTaskwDetail() {
        return taskwDetail;
    }

    public void setTaskwDetail(String taskwDetail) {
        this.taskwDetail = taskwDetail;
    }

    public int getTaskwManday() {
        return taskwManday;
    }

    public void setTaskwManday(int taskwManday) {
        this.taskwManday = taskwManday;
    }

    public int getTaskaId() {
        return taskaId;
    }

    public void setTaskaId(int taskaId) {
        this.taskaId = taskaId;
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

    public String getTaskwDate() {
        return taskwDate;
    }

    public void setTaskwDate(String taskwDate) {
        this.taskwDate = taskwDate;
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
    
}
