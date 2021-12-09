package com.afs.restapi.controller;

import com.afs.restapi.Dto.CompanyResponse;
import com.afs.restapi.Dto.EmployeeResponse;
import com.afs.restapi.Mapper.CompanyMapper;
import com.afs.restapi.Mapper.EmployeeMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("companies")
public class CompanyController {
    public CompanyService companyService;
    public EmployeeMapper employeeMapper;
    private CompanyMapper companyMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper){
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @GetMapping
    public List<Company> getCompany(){
        return companyService.findAllCompanies();
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompanyById(@PathVariable String id){
        List<Employee> employeeList = companyService.getEmployeeListByCompany(id);
        return companyMapper.toResponse(companyService.findByCompanyId(id),employeeList);
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getEmployeeListByCompany(@PathVariable String id){
        return companyService.getEmployeeListByCompany(id).stream().map(employee -> employeeMapper.toResponse(employee)).collect(Collectors.toList());
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
    public Company editCompany(@PathVariable String id, @RequestBody Company updatedCompany){
        return companyService.edit(id,updatedCompany);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Company deleteCompany(@PathVariable String id){
        Company company = companyService.findByCompanyId(id);
        return companyService.delete(id);
    }
}
