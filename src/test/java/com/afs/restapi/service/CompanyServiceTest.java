package com.afs.restapi.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_companies_when_get_companies_given_companies_and_employees() {
        // given
        List<Employee> employees = Arrays.asList(new Employee(1, "Anna", 20, "F", 99999, 1)) ;
        given(employeeRepository.findByCompanyId(anyInt()))
                .willReturn(employees);

        List<Company> companies = Arrays.asList(new Company(1, "ABC Ltd")) ;

        companies.forEach(company -> company.setEmployees(employees));

        given(companyRepository.findAll())
                .willReturn(companies);

        // when
        List<Company> actual = companyService.getCompanies();

        // then
        assertEquals(companies, actual);
    }

    @Test
    void should_return_company_when_get_company_given_company_id() {
        // given
        Company company = new Company(1, "ABC");

        given(companyRepository.findById(anyInt()))
                .willReturn(company);

        // when
        Company actual = companyService.getCompanyById(company.getId());

        // then
        assertEquals(company, actual);
    }




}
