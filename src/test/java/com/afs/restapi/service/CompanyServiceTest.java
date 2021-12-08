package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository mockCompanyRepository;
    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_all_company_when_perform_get_given_company() {
        //given
        Company company = new Company(1, "Anna Ltd");
        List<Company> companyList = Arrays.asList(new Company(1, "Anna Ltd")) ;

        given(mockCompanyRepository.findAll())
                .willReturn(companyList);
        //when
        List<Company> actual = companyService.findAllCompanies();
        //then
    }
}
