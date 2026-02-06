package com.example.langatahospitaltelemedicalapplication;

public class Status {
    int id;
    String date, time, notes, status, doctorName;

    public Status(int id, String date, String time, String notes, String status, String doctorName) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.status = status;
        this.doctorName = doctorName;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getNotes() {
        return notes;
    }

    public String getStatus() {
        return status;
    }

    public String getDoctorName() {
        return doctorName;
    }
}
