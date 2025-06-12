package org.com.projectManagement.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.projectManagement.dao.DeveloperDao;
import org.com.projectManagement.dao.LeadDao;
import org.com.projectManagement.dao.ProjectDao;
import org.com.projectManagement.dao.ProjectRequestDao;
import org.com.projectManagement.service.DeveloperService;
import org.com.projectManagement.service.LeadService;
import org.com.projectManagement.service.ProjectRequestService;
import org.com.projectManagement.service.ProjectService;
import org.com.projectManagement.service.impl.DeveloperServiceImpl;
import org.com.projectManagement.service.impl.LeadServiceImpl;
import org.com.projectManagement.service.impl.ProjectRequestServiceImpl;
import org.com.projectManagement.service.impl.ProjectServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ProjectManagementApp {

    private static final Logger log = LogManager.getLogger(ProjectManagementApp.class);

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        DeveloperDao developerDao = new DeveloperDao();
        developerDao.initData();
        LeadDao leadDao = new LeadDao();
        leadDao.initData();
        ProjectDao projectDao = new ProjectDao();
        projectDao.initData();
        ProjectRequestDao projectRequestDao = new ProjectRequestDao();
        projectRequestDao.initData();

        DeveloperService developerService = new DeveloperServiceImpl(developerDao);
        LeadService leadService = new LeadServiceImpl(leadDao);
        ProjectRequestService projectRequestService = new ProjectRequestServiceImpl(projectRequestDao, developerDao, projectDao);
        ProjectService projectService = new ProjectServiceImpl(developerService, projectRequestService, projectDao, leadService);

        Runnable staleProjectCleanupJob = () -> {
            try {
                projectService.cleanupInactiveProjects();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Thread projectCleanupThread = new Thread(staleProjectCleanupJob);
        projectCleanupThread.start();


        while (true) {
            String commandLine = scanner.nextLine();
            String[] parsedCommand = commandLine.split(" ");
            String operation = parsedCommand[0];
            try {
                switch (operation) {
                    case "register_lead":
                        leadService.registerLead(parsedCommand[1]);
                        break;
                    case "register_developer":
                        developerService.registerDeveloper(parsedCommand[1]);
                        break;
                    case "register_project":
                        String[] skills = parsedCommand[3].split(",");
                        projectService.registerProject(parsedCommand[2], parsedCommand[1], Arrays.asList(skills), 1L);
                        break;
                    case "get_available_projects":
                        projectService.getAvailableProjects();
                        break;
                    case "request_project":
                        projectRequestService.requestProject(parsedCommand[1], parsedCommand[2]);
                        break;
                    case "accept_request":
                        projectRequestService.acceptRequest(parsedCommand[1], parsedCommand[2], parsedCommand[3]);
                        break;
                    case "get_developer_details":
                        projectService.getDeveloperDetails(parsedCommand[1]);
                        break;
                    case "get_project_details":
                        projectService.getProjectDetails(parsedCommand[1]);
                        break;
                    case "cancel_project":
                        projectService.cancelProject(parsedCommand[1], parsedCommand[2]);
                        break;
                    case "complete_project":
                        projectService.completeProject(parsedCommand[1], parsedCommand[2]);
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                    default:
                        log.error("Command {} not found", operation);
                        break;
                }
            } catch (Exception e) {
                log.error("Exception occured: {}, {}, {}", e.getClass(), e.getMessage(),e);
                e.printStackTrace();
//                throw new Exception(e);

            }
        }


    }

}
