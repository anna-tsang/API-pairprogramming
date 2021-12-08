package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public Company edit(Integer id, Company updatedCompany) {
        Company company = companyRepository.findById(id);
        if(updatedCompany.getCompanyName() != null){
            company.setCompanyName(updatedCompany.getCompanyName());
        }
        if (updatedCompany.getEmployees() != null){
            company.setEmployees(updatedCompany.getEmployees());
        }
        return companyRepository.save(id,company);
    }

    public Company findByCompanyId(Integer id) {
        return companyRepository.findById(id);
    }

    public List<Company> displayCompany(Integer page, Integer pageSize) {
        return companyRepository.displayCompany(page,pageSize);
    }

    public Company create(Company newCompany) {
        return companyRepository.create(newCompany);
    }

    public Company delete(Integer id) {
        return companyRepository.delete(id);
    }

    public List<Employee> getEmployeeListByCompany(Integer companyId){
        return companyRepository.getEmployeeListByCompany(companyId);
    }
}
