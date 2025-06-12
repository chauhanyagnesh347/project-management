package org.com.projectManagement.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.projectManagement.dao.DeveloperDao;
import org.com.projectManagement.enums.DeveloperStatus;
import org.com.projectManagement.model.Developer;
import org.com.projectManagement.service.DeveloperService;
import org.com.projectManagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class DeveloperServiceImpl implements DeveloperService {

    private static final Logger log = LogManager.getLogger(DeveloperServiceImpl.class);
    private DeveloperDao developerDao;

    @Autowired
    public DeveloperServiceImpl(DeveloperDao developerDao) {
        this.developerDao = developerDao;
    }

    @Override
    public void registerDeveloper(String developerName) {
        Developer developer = new Developer(developerName, DeveloperStatus.AVAILABLE);
        developerDao.insertDeveloper(developer);
        log.info("{} developer registered", developerName);
    }

    public Developer getDeveloperFor(String developerName) {
        return developerDao.findDeveloperByDeveloperName(developerName);
    }

}
