package org.com.projectManagement.dao;

import org.com.projectManagement.exception.DataAccessException;
import org.com.projectManagement.model.Lead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeadDao {

    private final List<Lead> leadData = new ArrayList<>();
    private final Map<String, Lead> leadNameIndex = new HashMap<>();

    public void initData() {
        leadData.clear();
        leadNameIndex.clear();
    }

    public void insertLead(Lead lead) {
        duplicateLeadCheck(lead.getLeadName());
        leadData.add(lead);
        leadNameIndex.put(lead.getLeadName(), lead);
    }

    public Lead findLeadByName(String leadName) {
        leadExistanceCheck(leadName);
        return leadNameIndex.get(leadName);
    }

    public void duplicateLeadCheck(String leadName) {
        if(leadNameIndex.containsKey(leadName)) {
            throw new DataAccessException("Duplicate lead: " + leadName);
        }
    }

    public void leadExistanceCheck(String leadName) {
        if(!leadNameIndex.containsKey(leadName)) {
            throw new DataAccessException("Lead: " + leadName + " does not exist.");
        }
    }

}
