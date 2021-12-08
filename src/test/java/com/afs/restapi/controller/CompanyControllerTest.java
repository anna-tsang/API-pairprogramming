package com.afs.restapi.controller;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
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
    CompanyRepository companyRepository;

    //GET "/employees"
    //prepare data
    //send request
    //assertion
    @BeforeEach
    void cleanRepository(){
        companyRepository.clearAll();
    }

    @Test
    void should_return_company_list_when_perform_get_given_companies() throws Exception {
        //given
        Company company = new Company(1, "Anna Ltd",null);
        companyRepository.create(company);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect((jsonPath("$[0].companyName").value("Anna Ltd")));
    }

    @Test
    void should_return_company_when_perform_get_given_company_id() throws Exception {
        //given
        Company company = new Company(1, "Anna Ltd",null);
        companyRepository.create(company);
        Company company2 = new Company(2, "Anna Company",null);
        companyRepository.create(company2);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_ENDPOINT + "/{id}", company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect((jsonPath("$.companyName").value("Anna Ltd")))
                .andExpect((jsonPath("$.employees").value(IsNull.nullValue())));
    }

    @Test
    void should_return_employee_list_when_perform_get_given_company_id() throws Exception {
        //given
        Employee employee = new Employee(1,"Anna", 20,"M", 20, 1);
        List<Employee> employees = Arrays.asList(employee);
        Company company = new Company(1, "Anna Ltd",employees);
        companyRepository.create(company);
        Company company2 = new Company(2, "Anna Company",null);
        companyRepository.create(company2);

        //when
        //then
        mockMvc.perform((MockMvcRequestBuilders.get(COMPANIES_ENDPOINT + "/{id}/employees", company.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect((jsonPath("$[0].name")).value("Anna"))
                .andExpect((jsonPath("$[0].age").value(20)))
                .andExpect((jsonPath("$[0].gender").value("M")))
                .andExpect((jsonPath("$[0].salary").value(20)));
    }

    @Test
    void should_given_display_employee_list_when_perform_get_given_page_and_page_size() throws Exception {
        //given
        Employee employee = new Employee(1,"Anna", 20,"M", 20, 1);
        List<Employee> employees = Arrays.asList(employee);
        Company companyA = new Company(1, "Anna Company",employees);
        companyRepository.create(companyA);
        Company companyB = new Company(2, "Bnna Company",employees);
        companyRepository.create(companyB);
        Company companyC = new Company(3, "Cnna Company",null);
        companyRepository.create(companyC);
        //when
        mockMvc.perform((MockMvcRequestBuilders.get(COMPANIES_ENDPOINT)
                        .param("page","1")
                        .param("pageSize","2")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect((jsonPath("$[0].companyName").value("Cnna Company")))
                .andExpect((jsonPath("$[0].employees").value(IsNull.nullValue())));
        //then
    }

    @Test
    void should_return_new_company_when_perform_post_given_new_company() throws Exception {
        //given
        Employee employee = new Employee(1,"Anna", 20,"M", 20, 1);
        List<Employee> employees = Arrays.asList(employee);
        Company companyA = new Company(1, "Anna Ltd",employees);
        companyRepository.create(companyA);
        String newCompany = "{\n" +
                "        \"companyName\": \"Anna Ltd\",\n" +
                "        \"employees\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Anna\",\n" +
                "                \"age\": 20,\n" +
                "                \"gender\": \"F\",\n" +
                "                \"salary\": 5000\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Johnson\",\n" +
                "                \"age\": 20,\n" +
                "                \"gender\": \"M\",\n" +
                "                \"salary\": 4000\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"Apple\",\n" +
                "                \"age\": 20,\n" +
                "                \"gender\": \"F\",\n" +
                "                \"salary\": 4000\n" +
                "            }\n" +
                "        ]\n" +
                "    }";
        //when

        //then
        mockMvc.perform((MockMvcRequestBuilders.post(COMPANIES_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCompany)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect((jsonPath("$.companyName").value("Anna Ltd")))
                .andExpect((jsonPath("$.employees").value(employees)));
    }
}
