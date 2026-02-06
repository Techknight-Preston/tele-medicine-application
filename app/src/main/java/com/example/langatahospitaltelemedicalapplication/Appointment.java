package com.example.langatahospitaltelemedicalapplication;

public class Appointment {
    int id;
    String patientName;

    String date;
    String time;
    String notes;
    String status;

    public Appointment(int Id,String patientName, String date, String time, String notes, String status) {
        this.id = Id;
        this.patientName =patientName;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.status = status;

    }


}
