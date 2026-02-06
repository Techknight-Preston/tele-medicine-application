package com.example.langatahospitaltelemedicalapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddPatientRecordActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private Spinner spinnerPatients;
    private EditText etDiagnosis, etTreatment, etMedications, etDoctorNotes;
    private EditText etBloodPressure, etTemperature, etHeartRate;
    private Button btnSaveRecord;
    private ArrayList<Integer> patientIds;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_record);

        dbHelper = new DatabaseHelper(this);
        initializeViews();
        loadPatients();

        btnSaveRecord.setOnClickListener(v -> savePatientRecord());

    }

//Initialize View
    private void initializeViews() {
        spinnerPatients = findViewById(R.id.spinnerPatients);
        etDiagnosis = findViewById(R.id.etDiagnosis);
        etTreatment = findViewById(R.id.etTreatment);
        etMedications = findViewById(R.id.etMedications);
        etDoctorNotes = findViewById(R.id.etDoctorNotes);
        etBloodPressure = findViewById(R.id.etBloodPressure);
        etTemperature = findViewById(R.id.etTemperature);
        etHeartRate = findViewById(R.id.etHeartRate);
        btnSaveRecord = findViewById(R.id.btnSaveRecord);


        patientIds = new ArrayList<>();
    }
       //load patients
    private void loadPatients() {
        int doctorId = getDoctorId();
        if (doctorId == -1) {
            Toast.makeText(this, "Doctor not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Cursor cursor = dbHelper.getPatientsForDoctor(doctorId);

        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<String> patientNames = new ArrayList<>();
            patientIds.clear();

            do {
                // Get patient ID and name from cursor
                int patientId = cursor.getInt(cursor.getColumnIndexOrThrow("patient_id"));
                String patientName = cursor.getString(cursor.getColumnIndexOrThrow("patient_name"));

                patientIds.add(patientId);
                patientNames.add(patientName);

            } while (cursor.moveToNext());
            cursor.close();


            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, patientNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPatients.setAdapter(adapter);

        } else {
            Toast.makeText(this, "No patients found for this doctor", Toast.LENGTH_SHORT).show();
        }
    }

//Save PatientRecords
    private void savePatientRecord() {
        int patientId = getSelectedPatientId();
        int doctorId = getDoctorId();

        if (patientId == -1) {
            Toast.makeText(this, "Please select a patient", Toast.LENGTH_SHORT).show();
            return;
        }

        String diagnosis = etDiagnosis.getText().toString().trim();
        String treatment = etTreatment.getText().toString().trim();
        String medications = etMedications.getText().toString().trim();
        String doctorNotes = etDoctorNotes.getText().toString().trim();
        String bloodPressure = etBloodPressure.getText().toString().trim();
        String temperature = etTemperature.getText().toString().trim();
        String heartRate = etHeartRate.getText().toString().trim();

        if (validateInput(diagnosis, treatment)) {
            boolean success = dbHelper.insertPatientRecord(
                    patientId, doctorId, diagnosis, treatment, medications,
                    doctorNotes, bloodPressure, temperature, heartRate
            );

            if (success) {
                Toast.makeText(this, "Patient record saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save record", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //Selected Patient Id
    private int getSelectedPatientId() {
        int selectedPosition = spinnerPatients.getSelectedItemPosition();
        if (selectedPosition >= 0 && selectedPosition < patientIds.size()) {
            return patientIds.get(selectedPosition);
        }
        return -1;
    }

    private int getDoctorId() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return prefs.getInt("doctorId", -1);
    }

    private boolean validateInput(String diagnosis, String treatment) {
        boolean isValid = true;

        if (diagnosis.isEmpty()) {
            etDiagnosis.setError("Diagnosis is required");
            isValid = false;
        }
        if (treatment.isEmpty()) {
            etTreatment.setError("Treatment is required");
            isValid = false;
        }
        return isValid;
    }


}
