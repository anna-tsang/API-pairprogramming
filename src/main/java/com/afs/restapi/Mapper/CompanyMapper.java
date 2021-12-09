package com.afs.restapi.Mapper;

import com.afs.restapi.Dto.CompanyRequest;
import com.afs.restapi.Dto.CompanyResponse;
import com.afs.restapi.Dto.EmployeeResponse;
import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {
    private EmployeeMapper employeeMapper;
    private CompanyResponse companyResponse;

    public CompanyMapper (EmployeeMapper employeeMapper){
        this.employeeMapper = employeeMapper;
    }

    public Company toEntity(CompanyRequest companyRequest){
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }

    public CompanyResponse toResponse(Company company, List<Employee> employeeList){
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        companyResponse.setEmployeeList(
                employeeList.stream()
                        .map(employee -> employeeMapper.toResponse(employee))
                        .collect(Collectors.toList())
                );
        return companyResponse;
    }

}
