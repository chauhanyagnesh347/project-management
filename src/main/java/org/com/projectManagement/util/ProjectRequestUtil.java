package org.com.projectManagement.util;

import org.com.projectManagement.enums.ProjectStatus;
import org.com.projectManagement.exception.ProjectManagementApplicationException;
import org.com.projectManagement.model.Project;
import org.com.projectManagement.model.ProjectRequest;

import java.util.Arrays;

import static org.com.projectManagement.enums.ProjectStatus.ASSIGNED;
import static org.com.projectManagement.enums.ProjectStatus.IN_PROGRESS;

public class ProjectRequestUtil {

    public static void checkRequestProjectLeadCombination(ProjectRequest projectRequest, Project project, String leadName) {
        if(Arrays.asList(IN_PROGRESS, ASSIGNED).contains(project.getProjectStatus())
                || !projectRequest.getProjectId().equals(project.getProjectId())
                || !project.getLeadName().equals(leadName)) {
            throw new ProjectManagementApplicationException("Found incorrect combination of request, project and lead.");
        }
    }
}
