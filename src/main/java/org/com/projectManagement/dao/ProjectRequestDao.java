package org.com.projectManagement.dao;

import lombok.Synchronized;
import org.com.projectManagement.exception.DataAccessException;
import org.com.projectManagement.model.ProjectRequest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ProjectRequestDao {

    private final List<ProjectRequest> requestData = Collections.synchronizedList(new ArrayList<>());
    private final Map<String, ProjectRequest> requestIdIndex = new ConcurrentHashMap<>();

    public void initData() {
        requestData.clear();
        requestIdIndex.clear();
    }

    public void insertRequest(ProjectRequest projectRequest) {
        requestData.add(projectRequest);
        requestIdIndex.put(projectRequest.getRequstId(), projectRequest);
    }

    public ProjectRequest findRequestByRequestId(String requestId) {
        requestExistenceCheck(requestId);
        return requestIdIndex.get(requestId);
    }

//    @Synchronized
    public void deleteRequestForProject(String projectId) {
        for (int i=0;i<requestData.size();i++) {
            if (requestData.get(i).getProjectId().equals(projectId)) {
                requestIdIndex.remove(requestData.get(i).getRequstId());
                requestData.remove(requestData.get(i));
                i--;
            }
        }
    }

    public List<String> findRequestForProject(String projectId) {
        return requestData.stream().filter(req -> req.getProjectId().equals(projectId)).map(ProjectRequest::getRequstId).collect(Collectors.toList());
    }


    public void requestExistenceCheck(String requestId) {
        if (!requestIdIndex.containsKey(requestId)) {
            throw new DataAccessException("Request: " + requestId + " does not exist.");
        }
    }


}
