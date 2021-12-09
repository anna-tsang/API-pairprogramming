package com.afs.restapi.controller;

import com.afs.restapi.dto.CompanyRequest;
import com.afs.restapi.dto.CompanyResponse;
import com.afs.restapi.dto.EmployeeResponse;
import com.afs.restapi.entity.Company;
import com.afs.restapi.mapper.CompanyMapper;
import com.afs.restapi.mapper.EmployeeMapper;
import com.afs.restapi.service.CompanyService;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.EmployeeRepository;
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
    public EmployeeService employeeService;
    public CompanyMapper companyMapper;
    public EmployeeMapper employeeMapper;

    public CompanyController(CompanyService companyService, EmployeeService employeeService, CompanyMapper companyMapper, EmployeeMapper employeeMapper){
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<Company> getCompany(){
        List<Company> companies = companyService.getCompanies();

//        companies.forEach(company -> {
//            List<Employee> employees = employeeService.findByCompanyId(company.getId());
//            company.setEmployees(employees);
//        });

//        return companies.stream()
//                .map(company -> companyMapper.toResponse(company, new ArrayList<>()))
//                .collect(Collectors.toList());
        return companyService.getCompanies();
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompanyById(@PathVariable String id){
        Company company = companyService.getCompanyById(id);
        List<Employee> employees = employeeService.findByCompanyId(company.getId());
        return companyMapper.toResponse(company, employees);
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getEmployeeListByCompany(@PathVariable String id){
        return employeeService.findByCompanyId(id).stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Company> displayCompany(@RequestParam Integer page, @RequestParam Integer pageSize){
        return companyService.displayCompany(page,pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company){
        return companyService.createCompany(company);
    }

    @PutMapping("/{id}")
    public Company editCompany(@PathVariable String id, @RequestBody Company updatedCompany){
        Company company = companyService.getCompanyById(id);
        if(updatedCompany.getCompanyName() != null){
            company.setCompanyName(updatedCompany.getCompanyName());
        }
//        if(updatedCompany.getEmployees() != null){
//            company.setEmployees(updatedCompany.getEmployees());
//        }
        return companyService.editCompany(id,company);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable String id){
//        Company company = companyService.getCompanyById(id);
//        return companyService.deleteCompany(id);
        companyService.deleteCompany(id);
    }
}
