package com.afs.restapi.controller;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    public static final String EMPLOYEE_ENDPOINT = "/employees";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmployeeRepository employeeRepository;

    @AfterEach
    void cleanRepository(){
        employeeRepository.deleteAll();
    }

    @Test
    void should_get_all_employees_when_perform_get_given_employees() throws Exception {
        //given
        Employee employee = new Employee(null,"Anna", 20,"F", 99999, "1");
        employeeRepository.insert(employee);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEE_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].name").value("Anna"))
                .andExpect(jsonPath("$[0].gender").value("F"));
    }

    @Test
    void should_return_employee_when_perform_post_given_employee() throws Exception {
        //given
        String employee = "{\n" +
                "    \"name\": \"Anna\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"F\",\n" +
                "    \"salary\": 5000,\n" +
                "    \"companyId\": 1\n" +
                "}";//copy paste JSON from postman
        //when
        mockMvc.perform(post(EMPLOYEE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.name").value("Anna"))
                .andExpect(jsonPath("$.gender").value("F"));
    }

    @Test
    void should_return_employee_when_perform_get_given_employee_id() throws Exception {
        //given
        Employee employee = new Employee(null,"Anna", 20,"F", 99999, "1");
        employeeRepository.insert(employee);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEE_ENDPOINT+"/{id}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Anna"))
                .andExpect(jsonPath("$.gender").value("F"))
                .andExpect(jsonPath("$.age").value(20));

        //then
    }

    @Test
    void should_return_employees_when_perform_get_given_employee_gender() throws Exception {
        //given
        Employee employeeAnna = new Employee(null,"Anna", 20,"F", 99999, "1");
        employeeRepository.insert(employeeAnna);
        Employee employeeJohnson = new Employee(null,"Johnson", 20,"F", 99999, "1");
        employeeRepository.insert(employeeJohnson);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEE_ENDPOINT).param("gender","F"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Anna"))
                .andExpect(jsonPath("$[0].gender").value("F"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].name").value("Johnson"))
                .andExpect(jsonPath("$[1].gender").value("F"))
                .andExpect(jsonPath("$[1].age").value(20));
    }

    @Test
    void should_return_employees_when_perform_get_given_page_and_pageSize() throws Exception {
        //given
        Employee employeeAnna = new Employee(null,"Anna", 20,"M", 20, "1");
        employeeRepository.insert(employeeAnna);
        Employee employeeJohnson = new Employee(null,"Johnson", 20,"F", 99999, "1");
        employeeRepository.insert(employeeJohnson);
        Employee employeeGloria = new Employee(null,"Gloria", 20,"M", 745, "1");
        employeeRepository.insert(employeeGloria);
        Employee employeeBnna = new Employee(null,"Bnna", 20,"F", 5, "1");
        employeeRepository.insert(employeeBnna);
        Employee employeeCnna = new Employee(null,"Cnna", 20,"F", 99999, "1");
        employeeRepository.insert(employeeCnna);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(EMPLOYEE_ENDPOINT)
                        .param("page","0")
                        .param("pageSize","3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Anna"))
                .andExpect(jsonPath("$[0].gender").value("M"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].name").value("Johnson"))
                .andExpect(jsonPath("$[1].gender").value("F"))
                .andExpect(jsonPath("$[1].age").value(20))
                .andExpect(jsonPath("$[2].id").isString())
                .andExpect(jsonPath("$[2].name").value("Gloria"))
                .andExpect(jsonPath("$[2].gender").value("M"))
                .andExpect(jsonPath("$[2].age").value(20));

    }

    @Test
    void should_return_updated_employee_when_perform_put_given_updated_employee() throws Exception {
        //given
        Employee employeeAnna = new Employee(null,"Anna", 20,"F", 99999, "1");
        employeeRepository.insert(employeeAnna);
        String updatedEmployee = "{\n" +
                "    \"name\": \"Anna\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"F\",\n" +
                "    \"salary\": 2021,\n" +
                "    \"companyId\": 1\n" +
                "}";
        //when
        mockMvc.perform(MockMvcRequestBuilders.put(EMPLOYEE_ENDPOINT + "/{id}",employeeAnna.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedEmployee))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Anna"))
                .andExpect(jsonPath("$.gender").value("F"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    void should_delete_employee_when_perform_delete_given_employee_id() throws Exception {
        //given
        Employee employeeAnna = new Employee(null,"Anna", 20,"F", 99999, "1");
        employeeRepository.insert(employeeAnna);

        //when
        //then
        System.out.println(employeeAnna.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete(EMPLOYEE_ENDPOINT+"/{id}", employeeAnna.getId()))
                .andExpect(status().isNoContent());

        assertEquals(0, employeeRepository.findAll().size());
    }
}
