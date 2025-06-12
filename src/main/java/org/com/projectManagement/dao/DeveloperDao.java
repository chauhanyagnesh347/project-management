package org.com.projectManagement.dao;

import org.com.projectManagement.exception.DataAccessException;
import org.com.projectManagement.model.Developer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DeveloperDao {

    private final List<Developer> developerData = new ArrayList<>();
    private final Map<String, Developer> developerNameIndex = new HashMap<>();

    public void initData() {
        developerData.clear();
        developerNameIndex.clear();
    }


    public void insertDeveloper(Developer developer) {
        duplicateDeveloperCheck(developer.getDeveloperName());
        developerData.add(developer);
        developerNameIndex.put(developer.getDeveloperName(), developer);
    }

    public Developer findDeveloperByDeveloperName(String developerName) {
        developerExistanceCheck(developerName);
        return developerNameIndex.get(developerName);
    }

    public void duplicateDeveloperCheck(String developerName) {
        if(developerNameIndex.containsKey(developerName)) {
            throw new DataAccessException("Duplicate developer: " + developerName);
        }
    }

    public void developerExistanceCheck(String developerName) {
        if(!developerNameIndex.containsKey(developerName)) {
            throw new DataAccessException("Developer: " + developerName + " does not exist.");
        }
    }

}
