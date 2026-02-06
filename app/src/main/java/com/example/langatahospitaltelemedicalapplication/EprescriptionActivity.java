package com.example.langatahospitaltelemedicalapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EprescriptionActivity extends AppCompatActivity {

    private AutoCompleteTextView patientAutoComplete, medicationAutoComplete;
    private AutoCompleteTextView unitAutoComplete, frequencyAutoComplete;
    private EditText dosageEditText, durationEditText, instructionsEditText;
    private Button btnGenerate, btnCancel;
    private TextView tvPrescriptionPreview;

    // Sample data
    private final String[] PATIENTS = {"John Doe", "Jane Smith", "Mike Johnson", "Sarah Wilson"};
    private final String[] MEDICATIONS = {"Amoxicillin 500mg", "Paracetamol 500mg", "Ibuprofen 400mg",
            "Metformin 500mg", "Amlodipine 5mg", "Atorvastatin 20mg"};
    private final String[] UNITS = {"mg", "ml", "tablet(s)", "capsule(s)", "drop(s)"};
    private final String[] FREQUENCIES = {"Once daily", "Twice daily", "Three times daily",
            "Four times daily", "Every 6 hours", "Every 8 hours"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eprescription);

        initializeViews();
        setupAutoCompleteAdapters();
        setupClickListeners();
    }

    private void initializeViews() {
        patientAutoComplete = findViewById(R.id.patientAutoComplete);
        medicationAutoComplete = findViewById(R.id.medicationAutoComplete);
        unitAutoComplete = findViewById(R.id.unitAutoComplete);
        frequencyAutoComplete = findViewById(R.id.frequencyAutoComplete);
        dosageEditText = findViewById(R.id.dosageEditText);
        durationEditText = findViewById(R.id.durationEditText);
        instructionsEditText = findViewById(R.id.instructionsEditText);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnCancel = findViewById(R.id.btnCancel);
        tvPrescriptionPreview = findViewById(R.id.tvPrescriptionPreview);
    }

    private void setupAutoCompleteAdapters() {
        ArrayAdapter<String> patientAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, PATIENTS);
        patientAutoComplete.setAdapter(patientAdapter);

        ArrayAdapter<String> medicationAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, MEDICATIONS);
        medicationAutoComplete.setAdapter(medicationAdapter);

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, UNITS);
        unitAutoComplete.setAdapter(unitAdapter);

        ArrayAdapter<String> frequencyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, FREQUENCIES);
        frequencyAutoComplete.setAdapter(frequencyAdapter);
    }

    private void setupClickListeners() {
        btnCancel.setOnClickListener(v -> finish());

        btnGenerate.setOnClickListener(v -> {
            if (validateForm()) {
                generatePrescription();
            }
        });
    }

    private boolean validateForm() {
        if (patientAutoComplete.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please select a patient", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (medicationAutoComplete.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please select a medication", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dosageEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter dosage", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (unitAutoComplete.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please select unit", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (frequencyAutoComplete.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please select frequency", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (durationEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter duration", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void generatePrescription() {
        String patient = patientAutoComplete.getText().toString();
        String medication = medicationAutoComplete.getText().toString();
        String dosage = dosageEditText.getText().toString();
        String unit = unitAutoComplete.getText().toString();
        String frequency = frequencyAutoComplete.getText().toString();
        String duration = durationEditText.getText().toString();
        String instructions = instructionsEditText.getText().toString();

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String prescription = "E-PRESCRIPTION\n" +
                "Date: " + currentDate + "\n\n" +
                "Patient: " + patient + "\n" +
                "Medication: " + medication + "\n" +
                "Dosage: " + dosage + " " + unit + "\n" +
                "Frequency: " + frequency + "\n" +
                "Duration: " + duration + " days\n";

        if (!instructions.isEmpty()) {
            prescription += "Instructions: " + instructions + "\n";
        }

        prescription += "\nDoctor's Signature: _________________\n" +
                "Dr. [Your Name]\n" +
                "Medical License: [Your License Number]";

        // Show preview
        tvPrescriptionPreview.setText(prescription);
        tvPrescriptionPreview.setVisibility(View.VISIBLE);
        findViewById(R.id.tvPreview).setVisibility(View.VISIBLE);
        

        Toast.makeText(this, "Prescription generated successfully!", Toast.LENGTH_SHORT).show();
    }
}