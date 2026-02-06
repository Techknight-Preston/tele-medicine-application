package com.example.langatahospitaltelemedicalapplication;

public class SpecialistsModel {
    private final String name;
    private final String specialty;
    private final String email;
    private final int experienceYears;
    private final int iconResourceId;

    public SpecialistsModel(String name, String specialty, String email, int experienceYears, int iconResourceId) {
        this.name = name;
        this.specialty = specialty;
        this.email = email;
        this.experienceYears = experienceYears;
        this.iconResourceId = iconResourceId;
    }

    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public String getEmail() { return email; }
    public int getExperienceYears() { return experienceYears; }
    public int getIconResourceId() { return iconResourceId; }
}