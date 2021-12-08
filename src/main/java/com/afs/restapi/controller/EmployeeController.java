package com.afs.restapi.controller;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    public EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployee(){
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id){
        return employeeService.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam String gender){
        return employeeService.findByGender(gender);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Employee> displayEmployee(@RequestParam Integer page, @RequestParam Integer pageSize){
        return employeeService.displayEmployee(page,pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.create(employee);
    }

    @PutMapping("/{id}")
    public Employee editEmployee(@PathVariable Integer id, @RequestBody Employee updatedEmployee){
        Employee employee = employeeService.findById(id);
        if(updatedEmployee.getAge() != null){
            employee.setAge(updatedEmployee.getAge());
        }
        if(updatedEmployee.getSalary() != null){
            employee.setSalary(updatedEmployee.getSalary());
        }
        return employeeService.edit(id, employee);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Employee deleteEmployee(@PathVariable Integer id){
        Employee employee = employeeService.findById(id);
        return employeeService.delete(id);
    }
}