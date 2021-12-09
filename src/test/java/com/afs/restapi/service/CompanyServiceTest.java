package com.afs.restapi.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {

    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyRepositoryNew mockCompanyRepositoryNew;

    @Mock
    EmployeeRepositoryNew mockEmployeeRepositoryNew;

    @Test
    void should_return_all_company_when_perform_get_given_company() {
        //given
        List<Company> companyList = Arrays.asList(new Company("1", "Anna Ltd")) ;

        given(mockCompanyRepositoryNew.findAll())
                .willReturn(companyList);
        //when
        List<Company> actual = companyService.findAllCompanies();
        //then
        assertEquals(companyList, actual);
    }

    @Test
    void should_return_company_when_perform_get_given_company_id() {
        //given
        Company company1 = new Company("1", "Anna Ltd");
        Company company2 = new Company("2", "Bnna Ltd");
        List<Company> companyList = new ArrayList<>();
        companyList.add(company1);
        companyList.add(company2);
        //when
        given(mockCompanyRepositoryNew.findById(any()))
                .willReturn(java.util.Optional.of(company1));
        Company actual = companyService.findByCompanyId("1");
        //then
        assertEquals(company1, actual);
    }

    @Test
    void should_return_employee_list_when_perform_put_given_company_id() {
        //given
        List<Employee> employees = Stream.of(new Employee("1","Anna",3,"female",2,"1"))
                .collect(Collectors.toList());
        Company company = new Company("1","company");
        given(mockEmployeeRepositoryNew.findAllByCompanyId("1"))
                .willReturn(employees);

        //when

        //then
        List<Employee> actual = companyService.getEmployeeListByCompany("1");
        assertEquals(employees, actual);
    }


    @Test
    void should_return_companies_when_display_company_given_page_and_pageSize() {
        // given
        List<Company> companyList = new ArrayList<>();

        companyList.add(new Company("1", "Anna Co"));
        companyList.add(new Company("2", "Bnnb Co"));

        Pageable pageable = (Pageable) PageRequest.of(1,1);
        given(mockCompanyRepositoryNew.findAll(pageable))
                .willReturn(new PageImpl<>(companyList, PageRequest.of(1, 1), 1));
        // when
        // then
        List<Company> actual = companyService.displayCompany(1, 1);
        assertEquals(companyList, actual);
    }

    @Test
    void should_return_company_when_create_company_given_company() {
        // given
        Company company = new Company("1", "Abc");

        given(mockCompanyRepositoryNew.insert(company))
                .willReturn(company);

        // when
        // then
        Company actual = companyService.create(company);
        assertEquals(company, actual);
    }

    @Test
    void should_return_company_when_edit_company_given_company_id_and_updated_company() {
        // given
        Company company = new Company("1", "Anna Ltd");
        Company updatedCompany = new Company("1", "Bnnb Ltd");

        // when
        given(mockCompanyRepositoryNew.findById(any()))
                .willReturn(java.util.Optional.of(company));

        given(mockCompanyRepositoryNew.save(any(Company.class)))
                .willReturn(updatedCompany);

        // then
        Company actual = companyService.edit("1", updatedCompany);
        assertEquals(updatedCompany, actual);
    }

    @Test
    void should_return_company_when_delete_company_given_company_id() {
        //given
        Company company = new Company("1", "Anna Ltd");
        //when
        given(mockCompanyRepositoryNew.findById(any()))
                .willReturn(java.util.Optional.of(company));
        //then
        companyService.delete(company.getId());
        verify(mockCompanyRepositoryNew).delete(company);
    }


}
