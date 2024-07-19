package com.example.abershop.core;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Employee {
    private SimpleStringProperty name;
    private SimpleIntegerProperty employeeId;
    private SimpleStringProperty gender;
    private SimpleObjectProperty<LocalDate> dateOfBirth;
    private SimpleStringProperty email;
    private SimpleStringProperty pass;
    private SimpleIntegerProperty phoneNumber;
    private SimpleStringProperty role;
    private SimpleStringProperty address;
    private SimpleObjectProperty<LocalDate> startDate;

    public Employee(int employeeId ,String name, String gender , LocalDate dateOfBirth ,
                    int phoneNumber, String role , String address , LocalDate startDate , String email,  String pass  )
    {
        this.name = new SimpleStringProperty(name);
        this.gender = new SimpleStringProperty(gender);
        this.dateOfBirth = new SimpleObjectProperty<>(dateOfBirth);
        this.email = new SimpleStringProperty(email);
        this.pass = new SimpleStringProperty(pass);
        this.phoneNumber = new SimpleIntegerProperty(phoneNumber);
        this.role = new SimpleStringProperty(role);
        this.address = new SimpleStringProperty(address);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.employeeId = new SimpleIntegerProperty(employeeId);
    }

    public int getEmployeeId() {
        return employeeId.get();
    }

    public SimpleIntegerProperty employeeIdProperty() {
        return employeeId;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getGender() {
        return gender.get();
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth.get();
    }

    public SimpleObjectProperty<LocalDate> dateOfBirthProperty() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getPass() {
        return pass.get();
    }

    public SimpleStringProperty passProperty() {
        return pass;
    }

    public int getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleIntegerProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public SimpleObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }
}
