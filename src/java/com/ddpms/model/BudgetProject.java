package com.ddpms.model;

import java.util.HashMap;
import java.util.Map;

public class BudgetProject {

    private String project;
    private String owner;
    private String planId;
    private String planName;
    private Map<String,Integer> budgetProject = new HashMap<>();

    public Map<String, Integer> getBudgetProject() {
        return budgetProject;
    }

    public void setBudgetProject(Map<String, Integer> budgetProject) {
        this.budgetProject = budgetProject;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
    
}
