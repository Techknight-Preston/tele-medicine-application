package com.example.langatahospitaltelemedicalapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class Profile2Activity extends AppCompatActivity {

    private TextInputEditText etLicense, etFullName, etEmail, etContact;
    private Button btnSave, btnLogout;
    private ImageView profileIcon;
    private DatabaseHelper dbHelper;
    private int doctorId;
    private String originalLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        // Get doctor ID from intent
        doctorId = getIntent().getIntExtra("DOCTOR_ID", -1);

        dbHelper = new DatabaseHelper(this);
        initializeViews();
        loadDoctorProfile();
        setupClickListeners();
    }

    private void initializeViews() {
        profileIcon = findViewById(R.id.profile_icon);
        etLicense = findViewById(R.id.et_license);
        etFullName = findViewById(R.id.et_full_name2);
        etEmail = findViewById(R.id.et_email2);
        etContact = findViewById(R.id.et_contact2);
        btnSave = findViewById(R.id.btn_save2);
        btnLogout = findViewById(R.id.btn_logout2);
    }

    private void setupClickListeners() {
        btnSave.setOnClickListener(v -> saveProfile());
        btnLogout.setOnClickListener(v -> logoutUser());

        profileIcon.setOnClickListener(v -> {
            Toast.makeText(this, "Profile photo feature coming soon!", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadDoctorProfile() {
        Cursor cursor = dbHelper.getDoctorById(doctorId);

        if (cursor != null && cursor.moveToFirst()) {
            // Get original license for validation
            originalLicense = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LICENSE));
            etLicense.setText(originalLicense);

            // Combine first and last name into full name
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LAST_NAME));
            String fullName = firstName + " " + lastName;
            etFullName.setText(fullName);

            etEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL)));
            // Note: Doctors table doesn't have contact field in your current schema
            cursor.close();
        } else {
            Toast.makeText(this, "Error loading profile data", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProfile() {
        String license = etLicense.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();

        // Basic validation
        if (license.isEmpty()) {
            etLicense.setError("License number is required");
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

        // Split full name into first and last name
        String[] nameParts = fullName.split(" ", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        // Check if license already exists (if changed)
        if (!license.equals(originalLicense) && dbHelper.doesLicenseExist(license, doctorId)) {
            etLicense.setError("License number already exists");
            return;
        }

        // Check if email already exists
        if (dbHelper.doesDoctorEmailExist(email, doctorId)) {
            etEmail.setError("Email already exists");
            return;
        }

        // Use the helper method to update doctor profile
        boolean success = dbHelper.updateDoctorProfile(doctorId, firstName, lastName, license, email, contact);

        if (success) {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            if (!license.equals(originalLicense)) {
                originalLicense = license;
            }
        } else {
            Toast.makeText(this, "Error updating profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void logoutUser() {
        // Navigate back to login activity
        Intent intent = new Intent(Profile2Activity.this, DoctorLoginActivity.class);
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