package com.afs.restapi.controller;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepositoryNew;
import com.afs.restapi.repository.EmployeeRepositoryNew;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    public static final String COMPANIES_ENDPOINT = "/companies";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CompanyRepositoryNew companyRepositoryNew;

    @Autowired
    EmployeeRepositoryNew employeeRepositoryNew;

    @BeforeEach
    void cleanRepository(){
        companyRepositoryNew.deleteAll();
        employeeRepositoryNew.deleteAll();
    }

    @Test
    void should_return_company_list_when_perform_get_given_companies() throws Exception {
        //given
        Company company = new Company("Anna Ltd");
        companyRepositoryNew.insert(company);
        Company companyB = new Company("Bnna Ltd");
        companyRepositoryNew.insert(companyB);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect((jsonPath("$[0].companyName").value("Anna Ltd")));
    }

    @Test
    void should_return_company_when_perform_get_given_company_id() throws Exception {
        //given
        Company company = new Company( "Anna Ltd" );
        companyRepositoryNew.save(company);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_ENDPOINT + "/{id}", company.getId()))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.name").value("Anna Ltd")));
    }

    @Test
    void should_return_employee_list_when_perform_get_given_company_id() throws Exception {
        //given
        Employee employeeAnna = new Employee("Anna", 20,"M", 20, "1");
        employeeRepositoryNew.insert(employeeAnna);
        Employee employeeJohnson = new Employee("Johnson", 20,"F", 99999, "1");
        employeeRepositoryNew.insert(employeeJohnson);

        Company company1 = new Company( "Anna Ltd");
        companyRepositoryNew.insert(company1);
        Company company2 = new Company( "Anna Company");
        companyRepositoryNew.insert(company2);
        //when
        //then
        mockMvc.perform((MockMvcRequestBuilders.get(COMPANIES_ENDPOINT + "/{id}/employees", company1.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect((jsonPath("$[0].name")).value("Anna"))
                .andExpect((jsonPath("$[0].age").value(20)))
                .andExpect((jsonPath("$[0].gender").value("M")));
    }

    @Test
    void should_given_display_employee_list_when_perform_get_given_page_and_page_size() throws Exception {
        //given
        Employee employee = new Employee("Anna", 20,"M", 20, "1");
        List<Employee> employees = Arrays.asList(employee);
        Company companyA = new Company("Anna Company");
        companyRepositoryNew.insert(companyA);
        Company companyB = new Company("Bnna Company");
        companyRepositoryNew.insert(companyB);
        Company companyC = new Company( "Cnna Company" );
        companyRepositoryNew.insert(companyC);
        //when
        mockMvc.perform((MockMvcRequestBuilders.get(COMPANIES_ENDPOINT)
                        .param("page","1")
                        .param("pageSize","2")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect((jsonPath("$[0].companyName").value("Cnna Company")))
                .andExpect((jsonPath("$[0].employees").value(IsNull.nullValue())));
        //then
    }

    @Test
    void should_return_new_company_when_perform_post_given_new_company() throws Exception {
        //given
        String newCompany = "{\n" +
                                "    \"companyName\": \"Anna Ltd\"" +
                                "}";
        //when
        //then
        mockMvc.perform((MockMvcRequestBuilders.post(COMPANIES_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCompany)))
                .andExpect(status().isCreated())
                .andExpect((jsonPath("$.companyName").value("Anna Ltd")));
    }

    @Test
    void should_update_company_when_peform_put_given_company_Id_and_updated_Company() throws Exception {
        //given
        Company company = new Company("Anna Ltd");
        companyRepositoryNew.insert(company);
        String updatedCompany = "{\n" +
                "    \"id\": 1,\n" +
                "    \"companyName\": \"Bnnb Ltd\"\n" +
                "}";

        //when
        mockMvc.perform(MockMvcRequestBuilders.put(COMPANIES_ENDPOINT + "/{id}", company.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCompany))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("Bnnb Ltd"));
    }

    @Test
    void should_delete_company_when_perform_delete_given_company_id() throws Exception {
        //given
        Company company = new Company( "Anna Ltd");
        companyRepositoryNew.insert(company);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete(COMPANIES_ENDPOINT+"/{id}", company.getId()))
                .andExpect(status().isNoContent());
    }
}
