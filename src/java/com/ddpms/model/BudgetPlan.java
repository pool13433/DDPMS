
package com.ddpms.model;

public class BudgetPlan {
    private String budpId;
    
    private String budpName;
    
    private String budpYearBegin;
    
    private String budpYearEnd;

    private String modifiedDate;

    private String modifiedBy;

    public String getBudpId() {
        return budpId;
    }

    public void setBudpId(String budpId) {
        this.budpId = budpId;
    }

    public String getBudpName() {
        return budpName;
    }

    public void setBudpName(String budpName) {
        this.budpName = budpName;
    }

    public String getBudpYearBegin() {
        return budpYearBegin;
    }

    public void setBudpYearBegin(String budpYearBegin) {
        this.budpYearBegin = budpYearBegin;
    }

    public String getBudpYearEnd() {
        return budpYearEnd;
    }

    public void setBudpYearEnd(String budpYearEnd) {
        this.budpYearEnd = budpYearEnd;
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
