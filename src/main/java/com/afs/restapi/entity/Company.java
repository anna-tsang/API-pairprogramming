package com.afs.restapi.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Company {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String name;
    private List<Employee> employeeList;

    public Company(String id, String name){
        this.id = id;
        this.name = name;
    }
    public Company(String name){
        this.name = name;
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
        return name;
    }

    public void setCompanyName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employeeList;
    }

    public void setEmployees(List<Employee> employees) {
        this.employeeList = employees;
    }
}
