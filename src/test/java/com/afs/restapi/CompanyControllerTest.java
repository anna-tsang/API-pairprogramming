package com.afs.restapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
}
