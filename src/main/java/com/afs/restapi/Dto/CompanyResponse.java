package com.afs.restapi.Dto;

import java.util.List;

public class CompanyResponse {
    private String id;
    private String name;
    private List<EmployeeResponse> employeeList;

    public CompanyResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeResponse> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeResponse> employeeList) {
        this.employeeList = employeeList;
    }
}
