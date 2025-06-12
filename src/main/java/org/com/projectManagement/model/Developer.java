package org.com.projectManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.com.projectManagement.enums.DeveloperStatus;

@Data
@AllArgsConstructor
public class Developer {

    String developerName;
    DeveloperStatus developerStatus;

/*    public Developer(String developerName, DeveloperStatus developerStatus) {
        this.developerName = developerName;
        this.developerStatus = developerStatus;
    }*/

/*
    public String getDeveloperName() {
        return developerName;
    }

    public DeveloperStatus getDeveloperStatus() {
        return developerStatus;
    }

    public void setDeveloperStatus(DeveloperStatus developerStatus) {
        this.developerStatus = developerStatus;
    }
*/


}
