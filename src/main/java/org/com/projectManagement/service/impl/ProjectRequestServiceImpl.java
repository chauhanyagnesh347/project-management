package org.com.projectManagement.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.projectManagement.dao.DeveloperDao;
import org.com.projectManagement.dao.ProjectDao;
import org.com.projectManagement.dao.ProjectRequestDao;
import org.com.projectManagement.enums.DeveloperStatus;
import org.com.projectManagement.enums.ProjectStatus;
import org.com.projectManagement.model.Developer;
import org.com.projectManagement.model.Project;
import org.com.projectManagement.model.ProjectRequest;
import org.com.projectManagement.service.ProjectRequestService;

import java.util.List;

import static org.com.projectManagement.util.ProjectRequestUtil.checkRequestProjectLeadCombination;

public class ProjectRequestServiceImpl implements ProjectRequestService {

    private static final Logger log = LogManager.getLogger(ProjectRequestServiceImpl.class);
    private ProjectRequestDao projectRequestDao;
    private DeveloperDao developerDao;
    private ProjectDao projectDao;

    public ProjectRequestServiceImpl(ProjectRequestDao projectRequestDao, DeveloperDao developerDao, ProjectDao projectDao) {
        this.projectRequestDao = projectRequestDao;
        this.developerDao = developerDao;
        this.projectDao = projectDao;
    }

    @Override
    public void requestProject(String developerName, String projectId) {
        projectDao.projectExistanceCheck(projectId);
        developerDao.developerExistanceCheck(developerName);
        ProjectRequest projectRequest = new ProjectRequest(projectId, developerName);
        projectRequestDao.insertRequest(projectRequest);
        log.info("Request with id {} for {} is registered for {}", projectRequest.getRequstId(), projectId, developerName);
    }

    @Override
    public void acceptRequest(String requestId, String projectId, String leadName) {
        ProjectRequest projectRequest = projectRequestDao.findRequestByRequestId(requestId);
        Project project = projectDao.findProjectByProjectId(projectId);
        checkRequestProjectLeadCombination(projectRequest, project, leadName);
        Developer developer = developerDao.findDeveloperByDeveloperName(projectRequest.getDeveloperName());
        developer.setDeveloperStatus(DeveloperStatus.OCCUPIED);
        project.setDeveloperName(developer.getDeveloperName());
        project.setProjectStatus(ProjectStatus.IN_PROGRESS);
        log.info("{} request is accepted to work on {}", developer.getDeveloperName(), projectId);
    }

    @Override
    public List<String> getAllRequestForProject(String projectId) {
        return projectRequestDao.findRequestForProject(projectId);
    }

    @Override
    public void cleanupRequestForProject(String projectId) {
        projectRequestDao.deleteRequestForProject(projectId);
    }
}
