package com.example.langatahospitaltelemedicalapplication;

public class PatientRecord {
    private int recordId;
    private int patientId;
    private String patientName;
    private String diagnosis;
    private String treatment;
    private String medications;
    private String visitDate;
    private String doctorNotes;
    private String bloodPressure;
    private String temperature;
    private String heartRate;

    public PatientRecord(int recordId, int patientId, String patientName, String diagnosis,
                         String treatment, String medications, String visitDate,
                         String doctorNotes, String bloodPressure, String temperature,
                         String heartRate) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.medications = medications;
        this.visitDate = visitDate;
        this.doctorNotes = doctorNotes;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.heartRate = heartRate;
    }


    public int getRecordId() { return recordId; }
    public int getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
    public String getMedications() { return medications; }
    public String getVisitDate() { return visitDate; }
    public String getDoctorNotes() { return doctorNotes; }
    public String getBloodPressure() { return bloodPressure; }
    public String getTemperature() { return temperature; }
    public String getHeartRate() { return heartRate; }
}