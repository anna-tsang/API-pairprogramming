package com.afs.restapi.repository;

import com.afs.restapi.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepositoryNew extends MongoRepository<Company, String> {
}
