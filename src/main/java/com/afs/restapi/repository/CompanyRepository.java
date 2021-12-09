package com.afs.restapi.repository;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoMatchIdFoundException;
import com.afs.restapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companyList = new ArrayList<>();

    @Autowired
    public EmployeeRepository employeeRepository;

    public CompanyRepository(){
        List<Employee> employeeListOfCompanyA = new ArrayList<>();
        Employee employeeAnna = new Employee (1,"Anna",20,"F",5000,1);
        Employee employeeJohnson = new Employee (2,"Johnson",20,"M",4000,1);
        Employee employeeApple = new Employee (3,"Apple",20,"F",4000,1);
        companyList.add(new Company(1, "Anna Ltd"));

        List<Employee> employeeListOfCompanyB = new ArrayList<>();
        Employee employeeKandy = new Employee (1,"Kandy",20,"F",5000,2);
        Employee employeeRaymon = new Employee (2,"Raymon",20,"M",4000,2);
        companyList.add(new Company(2, "Bnnb Ltd"));

        List<Employee> employeeListOfCompanyC = new ArrayList<>();
        Employee employeeBnnb = new Employee (1,"Bnnb",20,"F",5000,3);
        Employee employeeCnnc = new Employee (2,"Cnnc",20,"M",4000,3);
        Employee employeeDnnd = new Employee (3,"Dnnd",20,"F",4000,3);
        companyList.add(new Company(3, "Cnnc Ltd"));
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

    public List<Employee> getEmployeeListByCompany(Integer id) {
        List<Employee> employeeList = employeeRepository.findEmployeeByCompanyId(id);
        return employeeList;
        //return companyList.stream().filter(company -> company.getId().equals(id)).findFirst().get().getEmployees();
    }

    public List<Company> displayCompany(Integer page, Integer pageSize) {
        return companyList.stream()
                .skip((long)page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        Integer nextId = companyList.stream().mapToInt(Company::getId).max().orElse(0) + 1;
        company.setId(nextId);

        companyList.add(company);
        return company;
    }

    public Company save(Integer id, Company updatedCompany) {
        Company company = findById(id);
        companyList.remove(company);
        companyList.add(updatedCompany);
        return updatedCompany;
    }

    public Company delete(Integer id) {
        Company company = findById(id);
        companyList.remove(company);
        return null;
    }

    public void clearAll() {
        companyList.clear();
    }
}