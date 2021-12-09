package com.afs.restapi.repository;

import java.util.List;

import com.afs.restapi.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositoryNew extends MongoRepository<Employee, String> {
    List<Employee> findByGender (String gender);
    List<Employee> findByCompanyId (String companyId);
}
