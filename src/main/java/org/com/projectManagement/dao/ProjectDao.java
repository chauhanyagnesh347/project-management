package org.com.projectManagement.dao;

import org.com.projectManagement.enums.ProjectStatus;
import org.com.projectManagement.exception.DataAccessException;
import org.com.projectManagement.model.Project;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class ProjectDao {

    private final List<Project> projectData = Collections.synchronizedList(new ArrayList<>());
    private final Map<String, Project> projectIdIndex = new ConcurrentHashMap<>();


    public void initData() {
        projectData.clear();
        projectIdIndex.clear();
    }

    public void insertProject(Project project) {
        projectData.add(project);
        projectIdIndex.put(project.getProjectId(), project);
    }

    public Project findProjectByProjectId(String projectId) {
        projectExistanceCheck(projectId);
        return projectIdIndex.get(projectId);
    }

    public void deleteProject(String projectId) {
        projectExistanceCheck(projectId);
        Project project = projectIdIndex.get(projectId);
        projectData.remove(project);
        projectIdIndex.remove(projectId);
    }

    public List<String> findProjectIdByDeveloperName(String developerName) {
        return projectData.stream().filter(proj -> nonNull(proj.getDeveloperName())
                && proj.getDeveloperName().equals(developerName)).map(Project::getProjectId).collect(Collectors.toList());
    }

    public List<Project> getAllProjects() {
        return projectData;
    }

    public List<Project> getProjectsWithStatus(List<ProjectStatus> projectStatusList) {
        return projectData.stream().filter(p -> projectStatusList.contains(p.getProjectStatus())).collect(Collectors.toList());
    }



    public void projectExistanceCheck(String projectId) {
        if(!projectIdIndex.containsKey(projectId)) {
            throw new DataAccessException("Project: " + projectId + " does not exist.");
        }
    }

}
