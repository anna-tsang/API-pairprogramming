package com.afs.restapi.controller;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    public static final String COMPANIES_ENDPOINT = "/companies";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @AfterEach
    void cleanRepository(){
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_company_list_when_perform_get_given_companies() throws Exception {
        //given
        Company company = new Company(null, "Anna Ltd");
        companyRepository.insert(company);
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
        Company company = new Company(null, "Anna Ltd");
        companyRepository.insert(company);
        Company company2 = new Company(null, "Anna Company");
        companyRepository.insert(company2);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_ENDPOINT + "/{id}", company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect((jsonPath("$.companyName").value("Anna Ltd")));
    }

    @Test
    void should_return_employee_list_when_perform_get_given_company_id() throws Exception {
        //given
        Company company = new Company(null, "Anna Ltd");
        companyRepository.insert(company);
        Company company2 = new Company(null, "Anna Company");
        companyRepository.insert(company2);


        Employee employeeAnna = new Employee(null,"Anna", 20,"M", 20, company.getId());
        employeeRepository.insert(employeeAnna);
        Employee employeeJohnson = new Employee(null,"Johnson", 20,"F", 99999, company.getId());
        employeeRepository.insert(employeeJohnson);

        //when
        //then
        mockMvc.perform((MockMvcRequestBuilders.get(COMPANIES_ENDPOINT + "/{id}/employees", company.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect((jsonPath("$[0].name")).value("Anna"))
                .andExpect((jsonPath("$[0].age").value(20)))
                .andExpect((jsonPath("$[0].gender").value("M")));
    }

    @Test
    void should_return_companies_when_perform_get_given_page_and_pageSize() throws Exception {
        //given
        Company company1 = new Company("1", "ABC");
        Company company2 = new Company("2", "ABC2");
        Company company3 = new Company("3", "ABC3");
        Company company4 = new Company("4", "ABC4");
        Company company5 = new Company("5", "ABC5");

        companyRepository.insert(company1);
        companyRepository.insert(company2);
        companyRepository.insert(company3);
        companyRepository.insert(company4);
        companyRepository.insert(company5);

        Integer page = 0;
        Integer pageSize = 2;

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_ENDPOINT)
                .param("page", String.valueOf(page))
                .param("pageSize", String.valueOf(pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].companyName").value("ABC"))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].companyName").value("ABC2"));

    }

    @Test
    void should_create_company_when_perform_post_given_company() throws Exception {
        // given
        String company = "{\n" +
                "    \"companyName\": \"Anna Ltd\"\n" +
                "}";
        // when
        // then
        mockMvc.perform(post(COMPANIES_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(company))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("Anna Ltd"));
    }

    @Test
    void should_update_company_when_peform_put_given_companyId_and_updatedCompany() throws Exception {
        //given
        Company company = new Company(null, "Anna Ltd");

        companyRepository.insert(company);

        String updatedCompany = "{\n" +
                "    \"id\": \"" + company.getId() + "\",\n" +
                "    \"companyName\": \"ABC Ltd\"\n" +
                "}";

        //when
        mockMvc.perform(MockMvcRequestBuilders.put(COMPANIES_ENDPOINT + "/{id}", company.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCompany))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("ABC Ltd"));
    }

    @Test
    void should_delete_company_when_perform_delete_given_company_id() throws Exception {
        //given
        Company company = new Company(null, "ABC Ltd");
        companyRepository.insert(company);

        int size = companyRepository.findAll().size();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete(COMPANIES_ENDPOINT +"/{id}", company.getId()))
                .andExpect(status().isNoContent());

        assertEquals(--size, companyRepository.findAll().size());
    }
}
