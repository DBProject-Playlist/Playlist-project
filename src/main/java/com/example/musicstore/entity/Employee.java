package com.example.musicstore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "Title")
    private String title;

    @Column(name = "ReportsTo")
    private Long reportsTo;

    @Column(name = "Email")
    private String email;

    public Employee() {}
    public Employee(Long id, String lastName, String firstName, String title, Long reportsTo, String email) {
        this.id = id; 
        this.lastName = lastName; 
        this.firstName = firstName;
        this.title = title; 
        this.reportsTo = reportsTo; 
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Long getReportsTo() { return reportsTo; }
    public void setReportsTo(Long reportsTo) { this.reportsTo = reportsTo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
