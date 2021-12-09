package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoMatchIdFoundException;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.repository.EmployeeRepositoryNew;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepositoryNew employeeRepositoryNew;

    public EmployeeService(EmployeeRepositoryNew employeeRepositoryNew){
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

    public List<Employee> displayEmployee(int page, int pageSize) {
        return employeeRepositoryNew.findAll(PageRequest.of(page, pageSize)).getContent();
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
