package org.com.projectManagement.service.impl;

import lombok.Synchronized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.projectManagement.dao.ProjectDao;
import org.com.projectManagement.enums.DeveloperStatus;
import org.com.projectManagement.enums.ProjectStatus;
import org.com.projectManagement.model.Developer;
import org.com.projectManagement.model.Project;
import org.com.projectManagement.service.DeveloperService;
import org.com.projectManagement.service.LeadService;
import org.com.projectManagement.service.ProjectRequestService;
import org.com.projectManagement.service.ProjectService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.com.projectManagement.enums.ProjectStatus.ASSIGNED;
import static org.com.projectManagement.enums.ProjectStatus.IN_PROGRESS;
import static org.com.projectManagement.util.ProjectUtils.checkDeveloperProjectMapping;
import static org.com.projectManagement.util.ProjectUtils.checkLeadProjectMapping;

public class ProjectServiceImpl implements ProjectService {


    private static final Logger log = LogManager.getLogger(ProjectServiceImpl.class);
    private ProjectDao projectDao;
    private ProjectRequestService requestService;
    private DeveloperService developerService;
    private LeadService leadService;

    public ProjectServiceImpl(DeveloperService developerService, ProjectRequestService requestService, ProjectDao projectDao, LeadService leadService) {
        this.developerService = developerService;
        this.requestService = requestService;
        this.projectDao = projectDao;
        this.leadService = leadService;
    }

    @Override
    public Project registerProject(String projectName, String leadName, List<String> skillSet, Long timeOut) {
        leadService.leadExists(leadName);
        Project project = new Project(leadName, projectName, skillSet, ProjectStatus.OPEN, TimeUnit.MINUTES.toMillis(timeOut));
        projectDao.insertProject(project);
        log.info("{} project with id {} registered by {}", projectName, project.getProjectId(), leadName);
        return project;
    }

    @Override
    public List<Project> getAvailableProjects() {
        List<Project> projectList = projectDao.getProjectsWithStatus(Arrays.asList(ProjectStatus.OPEN, ProjectStatus.REQUESTED));
        projectList.forEach(p -> log.info(p.toString()));
        return projectList;
    }

    @Override
    public String getProjectDetails(String projectId) {
        Project project = projectDao.findProjectByProjectId(projectId);
        log.info(project.toString());
        return project.toString();
    }

    @Override
    public void cancelProject(String leadName, String projectId) {
        Project project = projectDao.findProjectByProjectId(projectId);
        checkLeadProjectMapping(leadName, project);
        if (Arrays.asList(IN_PROGRESS, ASSIGNED).contains(project.getProjectStatus())) {
            Developer developer = developerService.getDeveloperFor(project.getDeveloperName());
            developer.setDeveloperStatus(DeveloperStatus.AVAILABLE);
        }
        requestService.cleanupRequestForProject(projectId);
        projectDao.deleteProject(projectId);
    }

    @Override
//    @Synchronized
    public void completeProject(String developerName, String projectId) {
        Project project = projectDao.findProjectByProjectId(projectId);
        synchronized(project) {
            checkDeveloperProjectMapping(developerName, project);
            project.setProjectStatus(ProjectStatus.COMPLETED);
        }
        requestService.cleanupRequestForProject(projectId);
        Developer developer = developerService.getDeveloperFor(developerName);
        developer.setDeveloperStatus(DeveloperStatus.AVAILABLE);
    }

    @Override
    public List<String> getDeveloperDetails(String developerName) {
        Developer developer = developerService.getDeveloperFor(developerName);
        List<String> projectId = null;
        if (developer.getDeveloperStatus().equals(DeveloperStatus.AVAILABLE)) {
            log.info("{} has no project assigned", developerName);
            projectId = Collections.emptyList();
        } else {
            projectId = projectDao.findProjectIdByDeveloperName(developerName);
            log.info("{} is working on {}", developerName, projectId);
        }
        return projectId;
    }

    @Override
//    @Synchronized
    public void cleanupInactiveProjects() {
        while (true) {

            List<Project> allProjects = projectDao.getAllProjects();
            synchronized (allProjects) {
                for (int projectId = 0; projectId < allProjects.size(); projectId++) {
                    if (System.currentTimeMillis() > allProjects.get(projectId).getProjectCreationTime() + allProjects.get(projectId).getProjectInactivityTimeOut()
                            && requestService.getAllRequestForProject(allProjects.get(projectId).getProjectId()).isEmpty()) {
                        cancelProject(allProjects.get(projectId).getLeadName(), allProjects.get(projectId).getProjectId());
                    }
                }
            }

            try {
                sleep(TimeUnit.SECONDS.toMillis(60));
            } catch (Exception e) {
                log.error("{}", e);
            }
        }
    }

    @Override
    public void updateTimeout(String projectId, Long timeOut) {
        Project project = projectDao.findProjectByProjectId(projectId);
        project.setProjectInactivityTimeOut(TimeUnit.MINUTES.toMillis(timeOut));
    }


}
