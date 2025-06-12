package org.com.projectManagement.model;

public class ProjectRequest {

    private static Integer requestIdSuffix = 0;
    private static final String REQUEST_ID_PREFIX = "request";

    String requstId;
    String projectId;
    String developerName;

    public ProjectRequest(String projectId, String developerName) {
        this.requstId = REQUEST_ID_PREFIX + ++requestIdSuffix;
        this.projectId = projectId;
        this.developerName = developerName;
    }

    public String getRequstId() {
        return requstId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getDeveloperName() {
        return developerName;
    }


}
