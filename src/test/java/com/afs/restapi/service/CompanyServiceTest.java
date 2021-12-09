package com.afs.restapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.CompanyRepositoryNew;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.repository.EmployeeRepositoryNew;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    CompanyRepositoryNew companyRepositoryNew;
    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_companies_when_get_companies_given_companies_and_employees() {
        // given
        List<Employee> employees = Arrays.asList(new Employee("1", "Anna", 20, "F", 99999, "1")) ;
        given(employeeRepository.findByCompanyId(any()))
                .willReturn(employees);

        List<Company> companies = Arrays.asList(new Company("1", "ABC Ltd")) ;

//        companies.forEach(company -> company.setEmployees(employees));

        given(companyRepositoryNew.findAll())
                .willReturn(companies);

        // when
        List<Company> actual = companyService.getCompanies();

        // then
        assertEquals(companies, actual);
    }

    @Test
    void should_return_company_when_get_company_given_company_id() {
        // given
        Company company = new Company("1", "ABC");

        given(companyRepositoryNew.findById(any()))
                .willReturn(java.util.Optional.of(company));

        // when
        Company actual = companyService.getCompanyById(company.getId());

        // then
        assertEquals(company, actual);
    }

    @Test
    void should_return_companies_when_display_company_given_page_and_pageSize() {
        // given
        List<Company> companies = new ArrayList<>();

        companies.add(new Company("1", "ABC"));
        companies.add(new Company("2", "DEF"));

        Integer page = 0;
        Integer pageSize = 2;

        //when
        given(companyRepositoryNew.findAll((Pageable) any()))
                .willReturn(new PageImpl<>(companies, PageRequest.of(page, pageSize), pageSize));

        List<Company> actual = companyService.displayCompany(page, pageSize);

        // when
        // then
        assertEquals(companies.get(0).getId(), actual.get(0).getId());
        assertEquals(companies.get(0).getCompanyName(), actual.get(0).getCompanyName());
    }

    @Test
    void should_return_company_when_create_company_given_company() {
        // given
        Company company = new Company("1", "Abc");

        given(companyRepositoryNew.insert(company))
                .willReturn(company);

        // when
        // then
        Company actual = companyService.createCompany(company);
        assertEquals(company, actual);
    }

    @Test
    void should_return_company_when_edit_company_given_company_id_and_updated_company() {
        // given
        Company company = new Company("1", "Def");
        Company updatedCompany = new Company("1", "Cde");

        // when
        given(companyRepositoryNew.findById(any()))
                .willReturn(java.util.Optional.of(company));

        given(companyRepositoryNew.save(any(Company.class)))
                .willReturn(updatedCompany);

        // then
        Company actual = companyService.editCompany("1", updatedCompany);
        assertEquals(updatedCompany, actual);
    }

    @Test
    void should_return_company_when_delete_company_given_company_id() {
        //given
        Company company = new Company("1", "ABC");
        //when
        willDoNothing().given(companyRepositoryNew).deleteById(any());

        //then
        companyService.deleteCompany(company.getId());
//        Company actual = companyService.deleteCompany(company.getId());
        verify(companyRepositoryNew).deleteById(company.getId());
//        assertEquals(company, actual);
    }

}
