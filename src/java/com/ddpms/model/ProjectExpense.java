
package com.ddpms.model;

public class ProjectExpense {
    private String expId;
    
    private String projId;
    
    private String projName;
    
    private String expDesc;
    
    private String expAmount;
    
    private String expPr;
        
    private String expVoch;
    
    private String receiptDate;
    
    private String expDate;
    private String expDateBegin;
    private String expDateEnd;
    
    private String vender;

    private String modifiedDate;

    private String modifiedBy;

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }
    
    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getExpDesc() {
        return expDesc;
    }

    public void setExpDesc(String expDesc) {
        this.expDesc = expDesc;
    }

    public String getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(String expAmount) {
        this.expAmount = expAmount;
    }

    public String getExpPr() {
        return expPr;
    }

    public void setExpPr(String expPr) {
        this.expPr = expPr;
    }

    public String getExpVoch() {
        return expVoch;
    }

    public void setExpVoch(String expVoch) {
        this.expVoch = expVoch;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
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

    public String getExpDateBegin() {
        return expDateBegin;
    }

    public void setExpDateBegin(String expDateBegin) {
        this.expDateBegin = expDateBegin;
    }

    public String getExpDateEnd() {
        return expDateEnd;
    }

    public void setExpDateEnd(String expDateEnd) {
        this.expDateEnd = expDateEnd;
    }
    
    
}
