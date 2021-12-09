package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoMatchIdFoundException;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.repository.EmployeeRepositoryNew;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeRepositoryNew employeeRepositoryNew;

    public EmployeeService(EmployeeRepository employeeRepository,EmployeeRepositoryNew employeeRepositoryNew){
        this.employeeRepository = employeeRepository;
        this.employeeRepositoryNew = employeeRepositoryNew;
    }

    public List<Employee> findAll() {
        return employeeRepositoryNew.findAll();
    }

    public Employee edit(String id, Employee updatedEmployee) {
        Employee employee = findById(id);
        if(updatedEmployee.getAge() != null){
            employee.setAge(updatedEmployee.getAge());
        }
        if (updatedEmployee.getSalary() != null){
            employee.setSalary(updatedEmployee.getSalary());
        }
        return employeeRepositoryNew.save(employee);
    }

    public Employee findById(String id) {
        return employeeRepositoryNew.findById(id).orElseThrow(NoMatchIdFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepositoryNew.findAllByGender(gender);
    }

    public List<Employee> displayEmployee(Integer page, Integer pageSize) {
        return employeeRepository.displayEmployee(page,pageSize);
    }

    public Employee create(Employee newEmployee) {
        return employeeRepositoryNew.insert(newEmployee);
    }

    public void delete(String id) {
        employeeRepositoryNew.delete(findById(id));
    }

//    public List<Employee> findEmployeeByCompanyId(String companyId){
//        return employeeRepositoryNew.findEmployeeByCompanyId(companyId);
//    }

}
