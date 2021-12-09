package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository mockEmployeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        List<Employee> employees = Arrays.asList(new Employee("1", "Anna", 20, "F", 99999, "1")) ;
        given(mockEmployeeRepository.findAll())
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

        given(mockEmployeeRepository.findById(any()))
                .willReturn(employee);

        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());

        given(mockEmployeeRepository.save(any(),any(Employee.class)))
                .willReturn(employee);

        //when
        Employee actual = employeeService.edit(employee.getId(), updatedEmployee);
        //then
        verify(mockEmployeeRepository).save(employee.getId(), employee);
        assertEquals(employee, actual);
    }

    @Test
    void should_return_employee_when_perform_get_given_employee_id() {
        //given
        Employee employee = new Employee("1", "Anna", 20, "M", 100, "1");

        //when
        given(mockEmployeeRepository.findById(any()))
                .willReturn(employee);

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
        given(mockEmployeeRepository.findByGender(any()))
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
        given(mockEmployeeRepository.displayEmployee(any(), any()))
                .willReturn(firstPageWith2Employees);

        List<Employee> actual = employeeService.displayEmployee(page, pageSize);
        //then

        assertEquals(firstPageWith2Employees, actual);
    }

    @Test
    void should_return_employee_when_perform_post_given_new_employee() {
        //given
        Employee employee = new Employee("1" ,"Anna", 20, "M", 100, "1");

        //when
        given(mockEmployeeRepository.create(any()))
                .willReturn(employee);

        //then
        Employee newEmployee = new Employee(null ,"Anna", 20, "M", 100, "1");
        Employee actual = employeeService.create(newEmployee);
        assertEquals(employee, actual);
    }

    @Test
    void should_deleted_employee_when_perform_delete_given_employee_id() {
        //given
        Employee employee = new Employee("1" ,"Anna", 20, "M", 100, "1");
        //when
        given(mockEmployeeRepository.delete(any()))
                .willReturn(employee);
        //then
        Employee actual = employeeService.delete(employee.getId());
    }
}
