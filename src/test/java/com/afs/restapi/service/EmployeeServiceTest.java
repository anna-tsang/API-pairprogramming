package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.EmployeeRepositoryNew;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepositoryNew mockEmployeeRepositoryNew;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        List<Employee> employees = Arrays.asList(new Employee("1", "Anna", 20, "F", 99999, "1")) ;
        given(mockEmployeeRepositoryNew.findAll())
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

        given(mockEmployeeRepositoryNew.findById("1"))
                .willReturn(java.util.Optional.of(employee));

        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());

        given(mockEmployeeRepositoryNew.save(any(Employee.class)))
                .willReturn(employee);

        //when
        Employee actual = employeeService.edit(employee.getId(), updatedEmployee);
        //then
        verify(mockEmployeeRepositoryNew).save(employee);
        assertEquals(employee, actual);
    }

    @Test
    void should_return_employee_when_perform_get_given_employee_id() {
        //given
        Employee employee = new Employee("1", "Anna", 20, "M", 100, "1");

        //when
        given(mockEmployeeRepositoryNew.findById("1"))
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
        given(mockEmployeeRepositoryNew.findAllByGender(any()))
                .willReturn(employeeMale);
        List<Employee> actual = employeeService.findByGender("M");
        //then
        assertEquals(employeeMale, actual);
    }

    @Test
    public void should_return_employees_when_get_given_page_and_page_size() {
        List<Employee> employees = new ArrayList<>();
        Employee firstEmployee = new Employee("1", "jojo", 29, "Male", 1,"1");
        Employee secondEmployee = new Employee("2", "john", 30, "Male", 66666,"1");
        employees.add(firstEmployee);
        employees.add(secondEmployee);
        Pageable pageable = (Pageable) PageRequest.of(1,1);
        given(mockEmployeeRepositoryNew.findAll(pageable))
                .willReturn(new PageImpl<>(employees, PageRequest.of(1, 1), 1));

        List<Employee> actual = employeeService.displayEmployee(1,1);
        assertEquals("jojo",actual.get(0).getName());
        assertEquals(29,actual.get(0).getAge());
        assertEquals("Male",actual.get(0).getGender());
        assertEquals(1,actual.get(0).getSalary());
    }

    @Test
    void should_return_employee_when_perform_post_given_new_employee() {
        //given
        Employee employee = new Employee("1" ,"Anna", 20, "M", 100, "1");

        //when
        given(mockEmployeeRepositoryNew.insert(employee))
                .willReturn(employee);

        //then
        Employee actual = employeeService.create(employee);
        assertEquals(employee, actual);
    }

    @Test
    void should_deleted_employee_when_perform_delete_given_employee_id() {
        //given
        Employee employee = new Employee("1" ,"Anna", 20, "M", 100, "1");
        //when
        given(mockEmployeeRepositoryNew.findById(any()))
                .willReturn(java.util.Optional.of(employee));
        //then
        employeeService.delete(employee.getId());
        verify(mockEmployeeRepositoryNew).delete(employee);
    }
}
