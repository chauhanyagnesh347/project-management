package org.com.projectManagement.model;

import org.com.projectManagement.enums.DeveloperStatus;

public class Developer {

    String developerName;
    DeveloperStatus developerStatus;

    public Developer(String developerName, DeveloperStatus developerStatus) {
        this.developerName = developerName;
        this.developerStatus = developerStatus;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public DeveloperStatus getDeveloperStatus() {
        return developerStatus;
    }

    public void setDeveloperStatus(DeveloperStatus developerStatus) {
        this.developerStatus = developerStatus;
    }


}
