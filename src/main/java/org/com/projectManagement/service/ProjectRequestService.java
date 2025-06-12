package org.com.projectManagement.service;

import org.com.projectManagement.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRequestService {


    void requestProject(String developerName, String projectId);
    void acceptRequest(String requestId, String projectId, String leadName);
    void cleanupRequestForProject(String projectId);
    List<String> getAllRequestForProject(String projectId);
}
