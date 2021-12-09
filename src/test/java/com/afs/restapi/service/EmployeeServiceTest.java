package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        List<Employee> employees = Arrays.asList(new Employee("1", "Anna", 20, "F", 99999, "1"));
        given(employeeRepository.findAll())
                .willReturn(employees);
        //when
        List<Employee> actual = employeeService.findAll();

        //then
        assertEquals(employees, actual);
    }

    @Test
    void should_return_updated_employee_when_edit_employee_given_updated_employee() {
        //given
        Employee employee = new Employee("1","Anna", 20, "F", 9999, "1");
        Employee updatedEmployee = new Employee("1","Anna", 99, "F", 9999, "1");

        given(employeeRepository.findById(any()))
                .willReturn(java.util.Optional.of(employee));

        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());

        given(employeeRepository.save(any(Employee.class)))
                .willReturn(employee);

        //when
        Employee actual = employeeService.edit(employee.getId(), updatedEmployee);
        //then
        verify(employeeRepository).save(employee);
        assertEquals(employee, actual);
    }

    @Test
    void should_return_employee_when_perform_get_given_employee_id() {
        //given
        Employee employee = new Employee("1", "Anna", 20, "M", 100, "1");

        //when
        given(employeeRepository.findById(any()))
                .willReturn(java.util.Optional.of(employee));

        Employee actual = employeeService.findById(employee.getId());

        //then
        assertEquals(employee, actual);
    }

    @Test
    void should_return_employees_when_perform_get_given_employee_gender() {
        //given
        Employee employeeA = new Employee("1", "Anna", 20, "M", 100, "1");
        Employee employeeB = new Employee("2", "Johnson", 20, "F", 10, "1");
        List<Employee> employees = new ArrayList<>();
        employees.add(employeeA);
        employees.add(employeeB);
        List<Employee> employeeMale = Arrays.asList(employeeA);
        //when
        given(employeeRepository.findByGender(any()))
                .willReturn(employeeMale);
        List<Employee> actual = employeeService.findByGender("M");
        //then
        assertEquals(employeeMale, actual);
    }

    @Test
    void should_return_employees_when_perform_get_given_page_and_pageSize() {
        //given
        List<Employee> employees = new ArrayList<>();

        Employee employee = new Employee ("1","Anna",20,"F",5000, "1");
        Employee employee2 = new Employee ("2","Johnson",20,"M",4000, "1");

        employees.add(new Employee ("1","Anna",20,"F",5000, "1"));
        employees.add(new Employee ("2","Johnson",20,"M",4000, "1"));
        employees.add(new Employee ("3","Apple",20,"F",4000, "1"));
        employees.add(new Employee ("4","April",20,"M",4000, "1"));
        employees.add(new Employee ("5","May",20,"M",4000, "1"));
        employees.add(new Employee ("6","June",20,"M",4000, "1"));

        List<Employee> firstPageWith2Employees = new ArrayList<>();

        firstPageWith2Employees.add(employee);
        firstPageWith2Employees.add(employee2);

        Integer page = 0;
        Integer pageSize = 2;

        //when
        given(employeeRepository.findAll((Pageable) any()))
                .willReturn(new PageImpl<>(employees, PageRequest.of(page, pageSize), pageSize));

        List<Employee> actual = employeeService.displayEmployee(page, pageSize);

        //then
        assertEquals(firstPageWith2Employees.get(0).getName(), actual.get(0).getName());
        assertEquals(firstPageWith2Employees.get(0).getAge(), actual.get(0).getAge());
        assertEquals(firstPageWith2Employees.get(0).getGender(), actual.get(0).getGender());
        assertEquals(firstPageWith2Employees.get(0).getSalary(), actual.get(0).getSalary());
        assertEquals(firstPageWith2Employees.get(1).getName(), actual.get(1).getName());
        assertEquals(firstPageWith2Employees.get(1).getAge(), actual.get(1).getAge());
        assertEquals(firstPageWith2Employees.get(1).getGender(), actual.get(1).getGender());
        assertEquals(firstPageWith2Employees.get(1).getSalary(), actual.get(1).getSalary());
    }

    @Test
    void should_return_employee_when_perform_post_given_new_employee() {
        //given
        Employee employee = new Employee("1" ,"Anna", 20, "M", 100, "1");

        //when
        given(employeeRepository.insert(any(Employee.class)))
                .willReturn(employee);

        //then
        Employee newEmployee = new Employee(null ,"Anna", 20, "M", 100, "1");
        Employee actual = employeeService.create(newEmployee);
        assertEquals(employee, actual);
    }

    @Test
    void should_deleted_employee_when_perform_delete_given_employee_id() {
        //given
        String id = "1";
        //when
        willDoNothing().given(employeeRepository).deleteById(id);
        //then
        employeeService.delete(id);
        verify(employeeRepository).deleteById(id);
    }

    @Test
    void should_return_employees_when_perform_get_given_company_id() {
        // given
        Employee employee = new Employee("1","Anna", 20, "F", 9999, "1");
        Employee employee2 = new Employee("2","Anna", 20, "F", 9999, "2");

        List<Employee> employees = Arrays.asList(employee, employee2);
        List<Employee> employeesWithCompanyId1 = Arrays.asList(employee);

        given(employeeRepository.findByCompanyId(any()))
                .willReturn(employeesWithCompanyId1);

        // when
        // then
        List<Employee> actual = employeeService.findByCompanyId(employee.getCompanyId());
        assertEquals(employeesWithCompanyId1, actual);
    }

}
