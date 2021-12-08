package com.afs.restapi.service;

import java.util.List;

import com.afs.restapi.entity.Company;
import com.afs.restapi.repository.CompanyRepository;
import org.springframework.web.bind.annotation.RequestBody;

public class CompanyService {
    public CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public List<Company> getCompany(){
        return companyRepository.findAll();
    }

    public List<Company> displayCompany(Integer page, Integer pageSize){
        return companyRepository.displayCompany(page,pageSize);
    }

    public Company createCompany(Company company){
        return companyRepository.create(company);
    }

}
