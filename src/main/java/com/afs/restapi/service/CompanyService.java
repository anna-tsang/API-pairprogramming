package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoMatchIdFoundException;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.CompanyRepositoryNew;
import com.afs.restapi.repository.EmployeeRepositoryNew;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private CompanyRepositoryNew companyRepositoryNew;
    private EmployeeRepositoryNew employeeRepositoryNew;

    public CompanyService(CompanyRepository companyRepository, CompanyRepositoryNew companyRepositoryNew, EmployeeRepositoryNew employeeRepositoryNew){
        this.companyRepository = companyRepository;
        this.companyRepositoryNew = companyRepositoryNew;
        this.employeeRepositoryNew = employeeRepositoryNew;
    }

    public List<Company> findAllCompanies() {
        return companyRepositoryNew.findAll();
    }

    public Company edit(String id, Company updatedCompany) {
        Company company = findByCompanyId(id);
        if(updatedCompany.getCompanyName() != null){
            company.setCompanyName(updatedCompany.getCompanyName());
        }
        if (updatedCompany.getEmployees() != null){
            company.setEmployees(updatedCompany.getEmployees());
        }
        return companyRepositoryNew.save(company);
    }

    public Company findByCompanyId(String id) {
        return companyRepositoryNew.findById(id).orElseThrow(NoMatchIdFoundException::new);
    }

    public List<Company> displayCompany(Integer page, Integer pageSize) {
        return companyRepositoryNew.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company create(Company newCompany) {
        return companyRepositoryNew.insert(newCompany);
    }

    public void delete(String id) {
        companyRepositoryNew.delete(findByCompanyId(id));
    }

    public List<Employee> getEmployeeListByCompany(String companyId){
        return employeeRepositoryNew.findAllById(companyId);
    }
}
