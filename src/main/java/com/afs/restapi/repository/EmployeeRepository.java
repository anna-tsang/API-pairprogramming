package com.afs.restapi.repository;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoMatchIdFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employeeList = new ArrayList<>();

    public EmployeeRepository(){
        employeeList.add(new Employee ("Anna",20,"F",5000, "1"));
        employeeList.add(new Employee ("Johnson",20,"M",4000, "1"));
        employeeList.add(new Employee ("Apple",20,"F",4000, "1"));
        employeeList.add(new Employee ("April",20,"M",4000, "2"));
        employeeList.add(new Employee ("May",20,"M",4000, "2"));
        employeeList.add(new Employee ("June",20,"M",4000, "3"));
    }

    public List<Employee> findAll() {
        return employeeList;
    }

    public Employee findById(String id) {
        return employeeList.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(NoMatchIdFoundException::new);
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
        //String nextId = employeeList.stream().map(Employee::getId).max().orElse(0) + 1;
        String nextId = String.valueOf(employeeList.size() + 1);
        employee.setId(nextId);
        employeeList.add(employee);
        return employee;
    }

    public Employee save(String id, Employee updatedEmployee) {
        Employee employee = findById(id);
        employeeList.remove(employee);
        employeeList.add(updatedEmployee);
        return employee;
    }

    public Employee delete(String id) {
        Employee employee = findById(id);
        employeeList.remove(employee);
        return employee;
    }

    public void clearAll() {
        employeeList.clear();
    }

    public List<Employee> findEmployeeByCompanyId(String companyId) {
        return employeeList.stream().filter(employee -> employee.getCompanyId().equals(companyId)).collect(Collectors.toList());
    }
}

