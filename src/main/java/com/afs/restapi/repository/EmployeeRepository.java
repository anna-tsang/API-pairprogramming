package com.afs.restapi.repository;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.CompanyNotFoundException;
import com.afs.restapi.exception.EmployeeNotFoundException;
import com.afs.restapi.exception.NoMatchIdFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employeeList = new ArrayList<>();

    public EmployeeRepository(){
        employeeList.add(new Employee (1,"Anna",20,"F",5000, 1));
        employeeList.add(new Employee (2,"Johnson",20,"M",4000, 1));
        employeeList.add(new Employee (3,"Apple",20,"F",4000, 1));
        employeeList.add(new Employee (4,"April",20,"M",4000, 1));
        employeeList.add(new Employee (5,"May",20,"M",4000, 1));
        employeeList.add(new Employee (6,"June",20,"M",4000, 1));
    }

    public List<Employee> findAll() {
        return employeeList;
    }

    public Employee findById(int id) {
        return employeeList.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employeeList.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Employee> displayEmployee(Integer page, Integer pageSize) {
        return employeeList.stream()
                .skip((long)page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee create(Employee employee) {
        Integer nextId = employeeList.stream().mapToInt(Employee::getId).max().orElse(0) + 1;
        employee.setId(nextId);
        employeeList.add(employee);
        return employee;
    }

    public Employee save(Integer id, Employee updatedEmployee) {
        Employee employee = findById(id);
        employeeList.remove(employee);
        employeeList.add(updatedEmployee);
        return employee;
    }

    public Employee delete(Integer id) {
        Employee employee = findById(id);
        employeeList.remove(employee);
        return employee;
    }

    public void clearAll() {
        employeeList.clear();
    }
}
