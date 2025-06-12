package org.com.projectManagement.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.projectManagement.dao.LeadDao;
import org.com.projectManagement.model.Lead;
import org.com.projectManagement.service.LeadService;

public class LeadServiceImpl implements LeadService {

    private static final Logger log = LogManager.getLogger(LeadServiceImpl.class);
    private LeadDao leadDao;

    public LeadServiceImpl(LeadDao leadDao) {
        this.leadDao = leadDao;
    }

    @Override
    public void registerLead(String leadName) {
        Lead lead = new Lead(leadName);
        leadDao.insertLead(lead);
        log.info("{} lead registered", leadName);
    }

    public void leadExists(String leadName) {
        leadDao.leadExistanceCheck(leadName);
    }
}
