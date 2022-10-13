package com.assignment.employeemanagementapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String rname;
    private String fname;
    private Object fvalue;
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String rname, String fname, Object fvalue) {
        super(String.format("%s not found with %s: '%s'", rname, fname, fvalue));
        this.rname = rname;
        this.fname = fname;
        this.fvalue = fvalue;
    }

    public String getRname() {
        return rname;
    }

    public String getFname() {
        return fname;
    }

    public Object getFvalue() {
        return fvalue;
    }
}
