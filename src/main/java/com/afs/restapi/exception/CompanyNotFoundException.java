package com.afs.restapi.exception;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException() {
        super("Company Not Found");
    }
}
