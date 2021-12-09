package com.afs.restapi.entity;

import java.util.List;

public class Company {
    private String id;
    private String companyName;

    public Company(String id, String companyName){
        this.id = id;
        this.companyName = companyName;
    }

    public Company() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

//    public List<Employee> getEmployees() {
//        return employees;
//    }

//    public void setEmployees(List<Employee> employees) {
//        this.employees = employees;
//    }
}
