package com.afs.restapi.controller;

import com.afs.restapi.entity.Company;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.service.CompanyService;
import com.afs.restapi.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {
    public CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> getCompany(){
        return companyService.findAllCompanies();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id){
        return companyService.findByCompanyId(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeeListByCompany(@PathVariable Integer id){
        return companyService.getEmployeeListByCompany(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Company> displayCompany(@RequestParam Integer page, @RequestParam Integer pageSize){
        return companyService.displayCompany(page,pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company){
        return companyService.create(company);
    }

    @PutMapping("/{id}")
    public Company editCompany(@PathVariable Integer id, @RequestBody Company updatedCompany){
        Company company = companyService.findByCompanyId(id);
        if(updatedCompany.getCompanyName() != null){
            company.setCompanyName(updatedCompany.getCompanyName());
        }
        if(updatedCompany.getEmployees() != null){
            company.setEmployees(updatedCompany.getEmployees());
        }
        return companyService.edit(id,company);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Company deleteCompany(@PathVariable Integer id){
        Company company = companyService.findByCompanyId(id);
        return companyService.delete(id);
    }
}
