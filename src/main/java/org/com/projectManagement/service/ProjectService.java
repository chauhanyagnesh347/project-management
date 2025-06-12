package org.com.projectManagement.service;

import org.com.projectManagement.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Project registerProject(String projectName, String leadName, List<String> skillSet, Long timeOut);
    List<Project> getAvailableProjects();
    String getProjectDetails(String projectId);
    void cancelProject(String leadName, String projectId);
    void completeProject(String developerName, String projectId);
    List<String> getDeveloperDetails(String developerName);
    void cleanupInactiveProjects();
    void updateTimeout(String projectId, Long timeOut);
}
