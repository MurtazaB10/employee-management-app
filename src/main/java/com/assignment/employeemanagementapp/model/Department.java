package com.assignment.employeemanagementapp.model;

import javax.persistence.*;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="department_name", nullable = false)
    private String dname;
    @Column(name = "department_location")
    private String dlocation;

    public Department(){}

    public Department(long id, String dname, String dlocation) {
        this.id = id;
        this.dname = dname;
        this.dlocation = dlocation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDlocation() {
        return dlocation;
    }

    public void setDlocation(String dlocation) {
        this.dlocation = dlocation;
    }
}
