package com.ddpms.model;

import java.util.List;


public class Project {
    private String projId;
    
    private String projName;
    
    private String projDetail;
    
    private String projStatus;
    
    private String projStatusDesc;
    
    private String planId;
    
    private String budpId;
    
    private int projBudgApprove;

    private String modifiedDate;

    private String modifiedBy;
    
    private String projRemark;
    
    private String projVerifyDate;
    
    private String projVerifyBy;
    
    private String protId;
    
    private String accountCode;
    
    private List<TaskAssign> taskAssignList;

    private int budgetBalance;
    
    private int budgetActualUse;
    
    private int budgetAll;
    
    private String straId;

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

    public String getProjDetail() {
        return projDetail;
    }

    public void setProjDetail(String projDetail) {
        this.projDetail = projDetail;
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

    public String getProjRemark() {
        return projRemark;
    }

    public void setProjRemark(String projRemark) {
        this.projRemark = projRemark;
    }

    public String getProjVerifyDate() {
        return projVerifyDate;
    }

    public void setProjVerifyDate(String projVerifyDate) {
        this.projVerifyDate = projVerifyDate;
    }

    public String getProjVerifyBy() {
        return projVerifyBy;
    }

    public void setProjVerifyBy(String projVerifyBy) {
        this.projVerifyBy = projVerifyBy;
    }

    public String getProtId() {
        return protId;
    }

    public void setProtId(String protId) {
        this.protId = protId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public int getProjBudgApprove() {
        return projBudgApprove;
    }

    public void setProjBudgApprove(int projBudgApprove) {
        this.projBudgApprove = projBudgApprove;
    }

    public String getProjStatusDesc() {
        return projStatusDesc;
    }

    public void setProjStatusDesc(String projStatusDesc) {
        this.projStatusDesc = projStatusDesc;
    }


    public int getBudgetBalance() {
        return budgetBalance;
    }

    public void setBudgetBalance(int budgetBalance) {
        this.budgetBalance = budgetBalance;
    }

    public int getBudgetActualUse() {
        return budgetActualUse;
    }

    public void setBudgetActualUse(int budgetActualUse) {
        this.budgetActualUse = budgetActualUse;
    }

    public int getBudgetAll() {
        return budgetAll;
    }

    public void setBudgetAll(int budgetAll) {
        this.budgetAll = budgetAll;
    }

    public String getStraId() {
        return straId;
    }

    public void setStraId(String straId) {
        this.straId = straId;
    }
    
}
