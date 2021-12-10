package com.afs.restapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.EmployeeRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_companies_when_get_companies_given_companies_and_employees() {
        // given
        List<Employee> employees = Arrays.asList(new Employee("1", "Anna", 20, "F", 99999, "1")) ;
        given(employeeRepository.findByCompanyId(any()))
                .willReturn(employees);

        List<Company> companies = Arrays.asList(new Company("1", "ABC Ltd")) ;

        given(companyRepository.findAll())
                .willReturn(companies);

        // when
        List<Company> actual = companyService.getCompanies();

        // then
        assertEquals(companies, actual);
        assertEquals(companies.get(0).getId(), actual.get(0).getId());
        assertEquals(companies.get(0).getCompanyName(), actual.get(0).getCompanyName());
    }

    @Test
    void should_return_company_when_get_company_given_company_id() {
        // given
        Company company = new Company("1", "ABC");

        given(companyRepository.findById(any()))
                .willReturn(java.util.Optional.of(company));

        // when
        Company actual = companyService.getCompanyById(company.getId());

        // then
        assertEquals(company, actual);
        assertEquals(company.getId(), actual.getId());
        assertEquals(company.getCompanyName(), actual.getCompanyName());
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
        given(companyRepository.findAll((Pageable) any()))
                .willReturn(new PageImpl<>(companies, PageRequest.of(page, pageSize), pageSize));

        List<Company> actual = companyService.displayCompany(page, pageSize);

        // when
        // then
        assertEquals(companies, actual);
        assertEquals(companies.get(0).getId(), actual.get(0).getId());
        assertEquals(companies.get(0).getCompanyName(), actual.get(0).getCompanyName());
    }

    @Test
    void should_return_company_when_create_company_given_company() {
        // given
        Company company = new Company("1", "Abc");

        given(companyRepository.insert(company))
                .willReturn(company);

        // when
        // then
        Company actual = companyService.createCompany(company);
        assertEquals(company, actual);
        assertEquals(company.getId(), actual.getId());
        assertEquals(company.getCompanyName(), actual.getCompanyName());
    }

    @Test
    void should_return_company_when_edit_company_given_company_id_and_updated_company() {
        // given
        Company company = new Company("1", "Def");
        Company updatedCompany = new Company("1", "Cde");

        // when
        given(companyRepository.findById(any()))
                .willReturn(java.util.Optional.of(company));

        given(companyRepository.save(any(Company.class)))
                .willReturn(updatedCompany);

        // then
        Company actual = companyService.editCompany("1", updatedCompany);
        assertEquals(updatedCompany, actual);
        assertEquals(updatedCompany.getId(), actual.getId());
        assertEquals(updatedCompany.getCompanyName(), actual.getCompanyName());
    }

    @Test
    void should_return_company_when_delete_company_given_company_id() {
        //given
        Company company = new Company("1", "ABC");
        //when
        willDoNothing().given(companyRepository).deleteById(any());

        //then
        companyService.deleteCompany(company.getId());

        verify(companyRepository).deleteById(company.getId());
    }

}
