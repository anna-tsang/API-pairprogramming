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

    @BeforeEach
    void cleanRepository(){
        companyRepository.clearAll();
    }

    @Test
    void should_return_company_list_when_perform_get_given_companies() throws Exception {
        //given
        Company company = new Company(1, "Anna Ltd");
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
        Company company = new Company(1, "Anna Ltd");
        companyRepository.create(company);
        Company company2 = new Company(2, "Anna Company");
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
        Company company = new Company(1, "Anna Ltd");
        companyRepository.create(company);
        Company company2 = new Company(2, "Anna Company");
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
    void should_return_companies_when_perform_get_given_page_and_pageSize() throws Exception {
        //given
        Company company1 = new Company(1, "ABC");
        Company company2 = new Company(2, "ABC2");
        Company company3 = new Company(3, "ABC3");
        Company company4 = new Company(4, "ABC4");
        Company company5 = new Company(5, "ABC5");

        companyRepository.create(company1);
        companyRepository.create(company2);
        companyRepository.create(company3);
        companyRepository.create(company4);
        companyRepository.create(company5);

        Integer page = 0;
        Integer pageSize = 2;

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_ENDPOINT)
                .param("page", String.valueOf(page))
                .param("pageSize", String.valueOf(pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].companyName").value("ABC"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].companyName").value("ABC2"));

    }

//    @Test
//    void should_create_company_when_perform_post_given_company() throws Exception {
//        // given
//        String company = "{\n" +
//                "    \"id\": 1,\n" +
//                "    \"companyName\": \"ABC Ltd\",\n" +
//                "    \"employees\"\n" +
//                "}";
//        // when
//        // then
//        mockMvc.perform(post(COMPANIES_ENDPOINT)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(company))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.companyName").value("ABC Ltd"));
//    }

//    @Test
//    void should_update_company_when_peform_put_given_companyId_and_updatedCompany() {
//        //given
//        Company company = new Company();
//        employeeRepository.create(employeeAnna);
//        String updatedEmployee = "{\n" +
//                "        \"name\": \"Anna\",\n" +
//                "        \"age\": 20,\n" +
//                "        \"gender\": \"F\",\n" +
//                "        \"salary\": 2021\n" +
//                "    }";
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.put(EMPLOYEE_ENDPOINT + "/{id}",employeeAnna.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(updatedEmployee))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Anna"))
//                .andExpect(jsonPath("$.gender").value("F"))
//                .andExpect(jsonPath("$.age").value(20))
//                .andExpect(jsonPath("$.salary").value(2021));
//    }


}