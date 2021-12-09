package com.afs.restapi.controller;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.EmployeeRepository;
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

    @Autowired
    EmployeeRepository employeeRepository;

    //GET "/employees"
    //prepare data
    //send request
    //assertion
    @BeforeEach
    void cleanRepository(){
        companyRepository.clearAll();
        employeeRepository.clearAll();
    }

    @Test
    void should_return_company_list_when_perform_get_given_companies() throws Exception {
        //given
        Company company = new Company("1", "Anna Ltd");
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
        Company company = new Company("1", "Anna Ltd" );
        companyRepository.create(company);
        Company company2 = new Company("2", "Anna Company" );
        Employee employeeJohnson = new Employee("1","Johnson", 20,"F", 99999, "1");
        employeeRepository.create(employeeJohnson);
        companyRepository.create(company2);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(COMPANIES_ENDPOINT + "/{id}", company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect((jsonPath("$.companyName").value("Anna Ltd")))
                .andExpect((jsonPath("$.employees[0].name").value("Johnson")))
                .andExpect((jsonPath("$.employees[0].id").value("1")));
    }

    @Test
    void should_return_employee_list_when_perform_get_given_company_id() throws Exception {
        //given
        Employee employeeAnna = new Employee(null,"Anna", 20,"M", 20, "1");
        employeeRepository.create(employeeAnna);
        Employee employeeJohnson = new Employee(null,"Johnson", 20,"F", 99999, "1");
        employeeRepository.create(employeeJohnson);

        Company company1 = new Company("1", "Anna Ltd");
        companyRepository.create(company1);
        Company company2 = new Company("2", "Anna Company");
        companyRepository.create(company2);
        //when
        //then
        mockMvc.perform((MockMvcRequestBuilders.get(COMPANIES_ENDPOINT + "/{id}/employees", company1.getId())))
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
        Employee employee = new Employee("1","Anna", 20,"M", 20, "1");
        List<Employee> employees = Arrays.asList(employee);
        Company companyA = new Company("1", "Anna Company");
        companyRepository.create(companyA);
        Company companyB = new Company("2", "Bnna Company");
        companyRepository.create(companyB);
        Company companyC = new Company("3", "Cnna Company" );
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
        String newCompany = "{\n" +
                                "    \"companyName\": \"Anna Ltd\"" +
                                "}";
        //when
        //then
        mockMvc.perform((MockMvcRequestBuilders.post(COMPANIES_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCompany)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect((jsonPath("$.companyName").value("Anna Ltd")));
    }

    @Test
    void should_update_company_when_peform_put_given_company_Id_and_updated_Company() throws Exception {
        //given
        Company company = new Company("1", "Anna Ltd");
        String updatedCompany = "{\n" +
                "    \"id\": 1,\n" +
                "    \"companyName\": \"Bnnb Ltd\"\n" +
                "}";

        companyRepository.create(company);

        //when
        mockMvc.perform(MockMvcRequestBuilders.put(COMPANIES_ENDPOINT + "/{id}", company.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCompany))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.companyName").value("Bnnb Ltd"));
    }

    @Test
    void should_delete_company_when_perform_delete_given_company_id() throws Exception {
        //given
        Company company = new Company("1", "Anna Ltd");
        companyRepository.create(company);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete(COMPANIES_ENDPOINT+"/{id}", company.getId()))
                .andExpect(status().isNoContent());
    }
}
