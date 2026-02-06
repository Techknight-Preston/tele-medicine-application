package com.example.langatahospitaltelemedicalapplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Inquiry {
    private final int id;
    private int patientId;
    private int doctorId;
    private final String message;
    private final String category;
    private final String priority;
    private String status;
    private final String createdAt;
    private final String patientName;
    private final String patientContact;

    public Inquiry(int id, String message, String category, String priority,
                   String status, String createdAt, String patientName, String patientContact) {
        this.id = id;
        this.message = message;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
        this.patientName = patientName;
        this.patientContact = patientContact;
    }


    public int getId() { return id; }
    public String getMessage() { return message; }
    public String getCategory() { return category; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public String getCreatedAt() { return createdAt; }
    public String getPatientName() { return patientName; }
    public String getPatientContact() { return patientContact; }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormattedTime() {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault());
            Date date = inputFormat.parse(createdAt);
            return outputFormat.format(date);
        } catch (Exception e) {
            return createdAt;
        }
    }
}
