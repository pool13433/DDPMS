
package com.ddpms.model;

public class ProjectShift {
    private String projsId;
    
    private String projId;
    
    private String projsPlan;
    
    private String projsReason;

    private String modifiedDate;

    private String modifiedBy;

    public String getProjsId() {
        return projsId;
    }

    public void setProjsId(String projsId) {
        this.projsId = projsId;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getProjsPlan() {
        return projsPlan;
    }

    public void setProjsPlan(String projsPlan) {
        this.projsPlan = projsPlan;
    }

    public String getProjsReason() {
        return projsReason;
    }

    public void setProjsReason(String projsReason) {
        this.projsReason = projsReason;
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
