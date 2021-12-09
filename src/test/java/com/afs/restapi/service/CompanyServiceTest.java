package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.CompanyRepositoryNew;
import com.afs.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {

    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyRepositoryNew mockCompanyRepositoryNew;

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
//
//    @Test
//    void should_return_company_when_perform_get_given_company_id() {
//        //given
//        Company company1 = new Company("1", "Anna Ltd");
//        Company company2 = new Company("2", "Bnna Ltd");
//        List<Company> companyList = new ArrayList<>();
//        companyList.add(company1);
//        companyList.add(company2);
//        //when
//        given(mockCompanyRepository.findById(any()))
//                .willReturn(company1);
//        Company actual = companyService.findByCompanyId("1");
//        //then
//        assertEquals(company1, actual);
//    }
//
//    @Test
//    void should_return_employee_list_when_perform_put_given_company_id() {
//        //given
//        Company company1 = new Company("1", "Anna Ltd");
//        Company company2 = new Company("2", "Bnna Ltd");
//        List<Employee> employeeList = mockCompanyRepository.getEmployeeListByCompany("1");
//
//        given(mockCompanyRepository.getEmployeeListByCompany(any()))
//                .willReturn(employeeList);
//        //when
//
//        //then
//        List<Employee> actual = companyService.getEmployeeListByCompany("1");
//        assertEquals(employeeList, actual);
//    }
//
//
//    @Test
//    void should_return_companies_when_display_company_given_page_and_pageSize() {
//        // given
//        List<Company> companyList = new ArrayList<>();
//
//        companyList.add(new Company("1", "Anna Co"));
//        companyList.add(new Company("2", "Bnnb Co"));
//
//        given(mockCompanyRepository.displayCompany(0, 2))
//                .willReturn(companyList);
//        // when
//        // then
//        List<Company> actual = companyService.displayCompany(0, 2);
//        assertEquals(companyList, actual);
//    }
//
//    @Test
//    void should_return_company_when_create_company_given_company() {
//        // given
//        Company company = new Company("1", "Abc");
//
//        given(mockCompanyRepository.create(company))
//                .willReturn(company);
//
//        // when
//        // then
//        Company actual = companyService.create(company);
//        assertEquals(company, actual);
//    }
//
//    @Test
//    void should_return_company_when_edit_company_given_company_id_and_updated_company() {
//        // given
//        Company company = new Company("1", "Anna Ltd");
//        Company updatedCompany = new Company("1", "Bnnb Ltd");
//
//        // when
//        given(mockCompanyRepository.findById(any()))
//                .willReturn(company);
//
//        given(mockCompanyRepository.save(any(), any(Company.class)))
//                .willReturn(updatedCompany);
//
//        // then
//        Company actual = companyService.edit("1", updatedCompany);
//        assertEquals(updatedCompany, actual);
//    }
//
//    @Test
//    void should_return_company_when_delete_company_given_company_id() {
//        //given
//        Company company = new Company("1", "Anna Ltd");
//        //when
//        given(mockCompanyRepository.delete(any()))
//                .willReturn(company);
//        //then
//        Company actual = companyService.delete(company.getId());
//        assertEquals(company, actual);
//    }


}
