package com.afs.restapi.service;

import java.util.List;

import com.afs.restapi.entity.Company;
import com.afs.restapi.exception.CompanyNotFoundException;
import com.afs.restapi.repository.CompanyRepository;
import org.springframework.data.domain.PageRequest;
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

    public Company getCompanyById(String id){
        return companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> displayCompany(Integer page, Integer pageSize){
        return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company createCompany(Company company){
        return companyRepository.insert(company);
    }

    public Company editCompany(String id, Company updatedCompany){
        Company company = companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);

        if(updatedCompany.getCompanyName() != null){
            company.setCompanyName(updatedCompany.getCompanyName());
        }

        return companyRepository.save(company);
    }

    public void deleteCompany(String id){
        companyRepository.deleteById(id);
    }
}
