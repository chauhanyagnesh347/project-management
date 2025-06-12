package org.com.projectManagement.util;

import org.com.projectManagement.exception.ProjectManagementApplicationException;
import org.com.projectManagement.model.Project;

public class ProjectUtils {

    public static void checkLeadProjectMapping(String leadName, Project project) {
        if(!project.getLeadName().equals(leadName)) {
            throw new ProjectManagementApplicationException("Lead: " + leadName + " is not authorized to action on Project: " + project.getProjectId());
        }
    }

    public static void checkDeveloperProjectMapping(String developerName, Project project) {
        if(!project.getDeveloperName().equals(developerName)) {
            throw new ProjectManagementApplicationException("Developer: " + developerName + " is not assigned to Project: " + project.getProjectId());
        }
    }


}
