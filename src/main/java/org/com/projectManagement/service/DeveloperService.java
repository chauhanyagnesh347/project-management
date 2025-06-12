package org.com.projectManagement.service;

import org.com.projectManagement.model.Developer;

public interface DeveloperService {

    void registerDeveloper(String developerName);
    Developer getDeveloperFor(String developerName);
}
