package org.com.projectManagement.model;

import org.com.projectManagement.enums.ProjectStatus;

import java.util.Arrays;
import java.util.List;

import static org.com.projectManagement.enums.ProjectStatus.ASSIGNED;
import static org.com.projectManagement.enums.ProjectStatus.IN_PROGRESS;

public class Project {


    private static Integer projectIdSuffix = 0;
    private static final String PROJECT_ID_SUFFIX = "project";

    String projectName;
    String projectId;
    String leadName;
    List<String> skillSet;
    String developerName;
    ProjectStatus projectStatus;
    Long projectCreationTime;
    Long projectInactivityTimeOut;


    public Project(String leadName, String projectName, List<String> skillSet, ProjectStatus projectStatus, Long projectInactivityTimeOut) {
        this.projectName = projectName;
        this.projectId = PROJECT_ID_SUFFIX + ++projectIdSuffix;
        this.leadName = leadName;
        this.skillSet = skillSet;
        this.projectStatus = projectStatus;
        this.projectCreationTime = System.currentTimeMillis();
        this.projectInactivityTimeOut = projectInactivityTimeOut;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getLeadName() {
        return leadName;
    }

    public List<String> getSkillSet() {
        return skillSet;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Long getProjectInactivityTimeOut() {
        return projectInactivityTimeOut;
    }

    public void setProjectInactivityTimeOut(Long projectInactivityTimeOut) {
        this.projectInactivityTimeOut = projectInactivityTimeOut;
    }

    public Long getProjectCreationTime() {
        return projectCreationTime;
    }

    //TODO: override toString here
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.projectId).append(", ");
        sb.append(this.projectName).append(", ");
        sb.append("[ ");
        this.skillSet.forEach(skill -> sb.append("\"").append(skill).append("\", "));
        sb.append(" ], ");
        sb.append(this.leadName).append(", ");

        if(Arrays.asList(IN_PROGRESS, ASSIGNED).contains(this.projectStatus)) {
            sb.append(this.projectStatus).append(", ");
            sb.append(this.developerName).append(", ");
        } else {
            sb.append(this.projectStatus);
        }
        return sb.toString();
    }
}
