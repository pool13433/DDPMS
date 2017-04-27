
package com.ddpms.model;

public class MessageUI {
     private boolean status;
    private String title;
    private String message;
    private String cssClass;

    public MessageUI(boolean status, String title, String message, String cssClass) {
        this.status = status;
        this.title = title;
        this.message = message;
        this.cssClass = cssClass;
    }
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}
