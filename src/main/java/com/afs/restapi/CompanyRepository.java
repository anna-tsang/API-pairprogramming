package com.afs.restapi;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private List<Company> companyList = new ArrayList<>();

    public CompanyRepository(){
        List<Employee> employeeListOfCompanyA = new ArrayList<>();
        employeeListOfCompanyA.add(new Employee (1,"Anna",20,"F",5000));
        employeeListOfCompanyA.add(new Employee (2,"Johnson",20,"M",4000));
        employeeListOfCompanyA.add(new Employee (3,"Apple",20,"F",4000));
        companyList.add(new Company(1, "Anna Ltd",  employeeListOfCompanyA));

        List<Employee> employeeListOfCompanyB = new ArrayList<>();
        employeeListOfCompanyB.add(new Employee (1,"Kandy",20,"F",5000));
        employeeListOfCompanyB.add(new Employee (2,"Raymon",20,"M",4000));
        companyList.add(new Company(2, "Bnnb Ltd", employeeListOfCompanyB));

        List<Employee> employeeListOfCompanyC = new ArrayList<>();
        employeeListOfCompanyC.add(new Employee (1,"Bnnb",20,"F",5000));
        employeeListOfCompanyC.add(new Employee (2,"Cnnc",20,"M",4000));
        employeeListOfCompanyC.add(new Employee (3,"Dnnd",20,"F",4000));
        companyList.add(new Company(3, "Cnnc Ltd", employeeListOfCompanyC));
    }
    public List<Company> findAll() {
        return companyList;
    }

    public Company findById(Integer id) {
        return companyList.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(NoMatchIdFoundException::new);
    }
}