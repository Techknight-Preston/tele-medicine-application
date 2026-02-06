package com.example.langatahospitaltelemedicalapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etFullName, etEmail, etContact;
    private Button btnSave, btnLogout;
    private DatabaseHelper dbHelper;
    private int patientId;
    private String originalUsername; // Track original username for validation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_profile);

        // Get patient ID from intent (username is not needed since we'll load it from DB)
        patientId = getIntent().getIntExtra("PATIENT_ID", -1);

        dbHelper = new DatabaseHelper(this);
        initializeViews();
        loadUserProfile();
        setupClickListeners();
    }

    private void initializeViews() {
        etUsername = findViewById(R.id.et_username);
        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etContact = findViewById(R.id.et_contact);
        btnSave = findViewById(R.id.btn_save);
        btnLogout = findViewById(R.id.btn_logout);
    }

    private void setupClickListeners() {
        btnSave.setOnClickListener(v -> saveProfile());
        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void loadUserProfile() {
        Cursor cursor = dbHelper.getPatientById(patientId);

        if (cursor != null && cursor.moveToFirst()) {

            originalUsername = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME));
            etUsername.setText(originalUsername);


            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LAST_NAME));
            String fullName = firstName + " " + lastName;
            etFullName.setText(fullName);

            etEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL)));
            etContact.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTACT)));
            cursor.close();
        } else {
            Toast.makeText(this, "Error loading profile data", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProfile() {
        String username = etUsername.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();


        if (username.isEmpty()) {
            etUsername.setError("Username is required");
            return;
        }

        if (fullName.isEmpty()) {
            etFullName.setError("Full name is required");
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            return;
        }

        if (contact.isEmpty()) {
            etContact.setError("Contact number is required");
            return;
        }

        // Check if username already exists
        if (!username.equals(originalUsername) && dbHelper.doesUsernameExist(username, patientId)) {
            etUsername.setError("Username already exists");
            return;
        }

        // Check if email already exists
        if (dbHelper.doesEmailExist(email, patientId)) {
            etEmail.setError("Email already exists");
            return;
        }

        // Split full name into first and last name
        String[] nameParts = fullName.split(" ", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        // Use the helper method to update profile
        boolean success = dbHelper.updatePatientProfile(patientId, firstName, lastName, email, contact);

        // Update username separately if changed
        if (success && !username.equals(originalUsername)) {
            success = dbHelper.updatePatientUsername(patientId, username);
        }

        if (success) {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            // Update original username if it was changed
            if (!username.equals(originalUsername)) {
                originalUsername = username;
            }
        } else {
            Toast.makeText(this, "Error updating profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void logoutUser() {
        // Clear any session data
        // Navigate back to login activity
        Intent intent = new Intent(ProfileActivity.this, PatientLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}