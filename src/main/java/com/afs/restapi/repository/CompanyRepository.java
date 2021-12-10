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
        Employee employeeAnna = new Employee ("Anna",20,"F",5000,"1");
        Employee employeeJohnson = new Employee ("Johnson",20,"M",4000,"1");
        Employee employeeApple = new Employee ("Apple",20,"F",4000,"1");
        companyList.add(new Company( "Anna Ltd"));

        List<Employee> employeeListOfCompanyB = new ArrayList<>();
        Employee employeeKandy = new Employee ("Kandy",20,"F",5000,"2");
        Employee employeeRaymon = new Employee ("Raymon",20,"M",4000,"2");
        companyList.add(new Company( "Bnnb Ltd"));

        List<Employee> employeeListOfCompanyC = new ArrayList<>();
        Employee employeeBnnb = new Employee ("Bnnb",20,"F",5000,"3");
        Employee employeeCnnc = new Employee ("Cnnc",20,"M",4000,"3");
        Employee employeeDnnd = new Employee ("Dnnd",20,"F",4000,"3");
        companyList.add(new Company( "Cnnc Ltd"));
    }
    public List<Company> findAll() {
        companyList.forEach(company -> {
            List<Employee> employeeList = employeeRepository.findEmployeeByCompanyId(company.getId());
            company.setEmployees(employeeList);
        });
        return companyList;
    }

    public Company findById(String id) {
        findAll();
        return companyList.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(NoMatchIdFoundException::new);
    }

    public List<Employee> getEmployeeListByCompany(String id) {
        List<Employee> employeeList = employeeRepository.findEmployeeByCompanyId(id);
        return employeeList;
    }

    public List<Company> displayCompany(int page, int pageSize) {
        return companyList.stream()
                .skip((long)page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        //String nextId = companyList.stream().mapToInt(Company::getId).max().orElse(0) + 1;
        String nextId = String.valueOf((companyList.size() + 1));
        company.setId(nextId);

        companyList.add(company);
        return company;
    }

    public Company save(String id, Company updatedCompany) {
        Company company = findById(id);
        companyList.remove(company);
        companyList.add(updatedCompany);
        return updatedCompany;
    }

    public Company delete(String id) {
        Company company = findById(id);
        companyList.remove(company);
        return null;
    }

    public void clearAll() {
        companyList.clear();
    }
}