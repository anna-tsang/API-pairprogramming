package com.afs.restapi.service;

import java.util.List;

import com.afs.restapi.entity.Company;
import com.afs.restapi.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    public CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer id){
        return companyRepository.findById(id);
    }

    public List<Company> displayCompany(Integer page, Integer pageSize){
        return companyRepository.displayCompany(page,pageSize);
    }

    public Company createCompany(Company company){
        return companyRepository.create(company);
    }

    public Company editCompany(Integer id,Company updatedCompany){
        Company company = companyRepository.findById(id);
        if(updatedCompany.getCompanyName() != null){
            company.setCompanyName(updatedCompany.getCompanyName());
        }
        if(updatedCompany.getEmployees() != null){
            company.setEmployees(updatedCompany.getEmployees());
        }
        return companyRepository.save(id,company);
    }

    public Company deleteCompany(Integer id){
        Company company = companyRepository.findById(id);
        return companyRepository.delete(id);
    }
}
