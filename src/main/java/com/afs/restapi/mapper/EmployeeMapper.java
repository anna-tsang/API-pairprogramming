package com.afs.restapi.mapper;

import com.afs.restapi.dto.EmployeeRequest;
import com.afs.restapi.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();

        // BeanUtils
        BeanUtils.copyProperties(employeeRequest, employee);

        return employee;
    }


}
