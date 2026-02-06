package com.example.langatahospitaltelemedicalapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class SendInquiryActivity extends AppCompatActivity {

    private Spinner spinnerDoctors, spinnerCategory, spinnerPriority;
    private EditText editTextMessage;
    private Button buttonSend;
    private DatabaseHelper dbHelper;
    private Map<String, Integer> doctorIdMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_inquiry);

        spinnerDoctors = findViewById(R.id.spinnerDoctors);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        dbHelper = new DatabaseHelper(this);

        setupSpinners();
        setupSendButton();
    }

    private void setupSpinners() {
        // Categories
        String[] categories = {
                "General Medical Advice",
                "Prescription Refill",
                "Test Results Discussion",
                "Symptom Consultation",
                "Medication Side Effects",
                "Follow-up Question",
                "Emergency Concern"
        };

        // Priorities
        String[] priorities = {"Low", "Medium", "High", "Emergency"};

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, priorities);

        spinnerCategory.setAdapter(categoryAdapter);
        spinnerPriority.setAdapter(priorityAdapter);

        // Load doctors
        loadDoctors();
    }

    private void loadDoctors() {
        java.util.ArrayList<Doctor> doctors = dbHelper.FindDoctor();
        String[] doctorNames = new String[doctors.size()];

        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            String fullName = doctor.getFirstName() + " " + doctor.getLastName();
            doctorNames[i] = fullName;
            doctorIdMap.put(fullName, doctor.getId());
        }

        ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, doctorNames);
        spinnerDoctors.setAdapter(doctorAdapter);
    }

    private void setupSendButton() {
        buttonSend.setOnClickListener(v -> sendInquiry());
    }

    private void sendInquiry() {
        String doctorName = spinnerDoctors.getSelectedItem().toString();
        String category = spinnerCategory.getSelectedItem().toString();
        String priority = spinnerPriority.getSelectedItem().toString();
        String message = editTextMessage.getText().toString().trim();

        if (message.isEmpty()) {
            Toast.makeText(this, "Please enter your message", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer doctorId = doctorIdMap.get(doctorName);
        if (doctorId == null) {
            Toast.makeText(this, "Please select a doctor", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int patientId = prefs.getInt("patientId", -1);

        if (patientId == -1) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = dbHelper.insertInquiry(patientId, doctorId, message, category, priority);

        if (success) {
            Toast.makeText(this, "Inquiry sent successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to send inquiry", Toast.LENGTH_SHORT).show();
        }
    }
}