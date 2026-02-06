package com.example.langatahospitaltelemedicalapplication;

public class Doctor {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String specialization;


    public Doctor(int id, String firstName, String lastName, String specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;

    }


    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getSpecialization() { return specialization; }

    public String getFullName() { return firstName + " " + lastName; }
}