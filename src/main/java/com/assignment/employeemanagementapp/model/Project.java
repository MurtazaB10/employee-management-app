package com.assignment.employeemanagementapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pid;
    @Column(name = "project_name", nullable = false)
    private String pname;
    @Column(name = "project_description")
    private String pdescription;
    @Column(name = "project_technology", nullable = false)
    private String ptechnology;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dept_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Department dept;

    @JsonIgnore
    @ManyToMany(mappedBy = "assignedProjects")
    private Set<Employee> employeeSet = new HashSet<>();

    public Project(){}

    public Project(long pid, String pname, String pdescription, String ptechnology, Set<Employee> employeeSet) {
        this.pid = pid;
        this.pname = pname;
        this.pdescription = pdescription;
        this.ptechnology = ptechnology;
        this.employeeSet = employeeSet;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }

    public String getPtechnology() {
        return ptechnology;
    }

    public void setPtechnology(String ptechnology) {
        this.ptechnology = ptechnology;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(Set<Employee> employeeSet) {
        this.employeeSet = employeeSet;
    }
}
