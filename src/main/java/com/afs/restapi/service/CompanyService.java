package com.afs.restapi.service;

import java.util.List;

import com.afs.restapi.entity.Company;
import com.afs.restapi.exception.CompanyNotFoundException;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.CompanyRepositoryNew;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    public CompanyRepositoryNew companyRepositoryNew;

    public CompanyService(CompanyRepositoryNew companyRepositoryNew){
        this.companyRepositoryNew = companyRepositoryNew;
    }

    public List<Company> getCompanies(){
        return companyRepositoryNew.findAll();
    }

    public Company getCompanyById(String id){
        return companyRepositoryNew.findById(id).orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> displayCompany(Integer page, Integer pageSize){
        return companyRepositoryNew.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company createCompany(Company company){
        return companyRepositoryNew.insert(company);
    }

    public Company editCompany(String id, Company updatedCompany){
        Company company = companyRepositoryNew.findById(id).orElseThrow(CompanyNotFoundException::new);

        if(updatedCompany.getCompanyName() != null){
            company.setCompanyName(updatedCompany.getCompanyName());
        }

        return companyRepositoryNew.save(company);
    }

    public void deleteCompany(String id){
        companyRepositoryNew.deleteById(id);
    }
}
