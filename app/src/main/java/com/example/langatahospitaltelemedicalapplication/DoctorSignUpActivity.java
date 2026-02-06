package com.example.langatahospitaltelemedicalapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class DoctorSignUpActivity extends AppCompatActivity {
    EditText Firstname, Lastname, LicenseNumber,ContactNumber,Specialization, Email, NewPassword, ConfirmPassword;
    Button Register;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle SavedInstanceState){

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_doctorsignup);

        dbHelper = new DatabaseHelper(this);

        Firstname = findViewById(R.id.Firstname);
        Lastname = findViewById(R.id.Lastname);
        LicenseNumber = findViewById(R.id.LicenseNumber);
        Specialization = findViewById(R.id.Specialization);
        ContactNumber = findViewById(R.id.ContactNumber);
        Email = findViewById(R.id.Email);
        NewPassword = findViewById(R.id.NewPassword);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        Register = findViewById(R.id.Register);

        Register.setOnClickListener(v ->{
            String first = Firstname.getText().toString().trim();
            String last = Lastname.getText().toString().trim();
            String license = LicenseNumber.getText().toString().trim();
            String specialization = Specialization.getText().toString().trim();
            String contact = ContactNumber.getText().toString().trim();
            String email = Email.getText().toString().trim();
            String password = NewPassword.getText().toString();
            String confirm = ConfirmPassword.getText().toString();

            if (first.isEmpty() || last.isEmpty() || license.isEmpty() || specialization.isEmpty() || contact.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()){
                Toast.makeText(this, "All fields are required",Toast.LENGTH_SHORT).show();
                return;
            }
            if (license.length() < 4 || license.length() > 15){
                Toast.makeText(this, "License number must be 4-15 characters",Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirm)){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }


            boolean inserted = dbHelper.insertDoctor(first, last, license,contact,specialization, email, password);
            if (inserted) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Patient registered successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DoctorSignUpActivity.this, DoctorLoginActivity.class));
                    finish();
            } else {
                Toast.makeText(this, "Registration failed - License Number may already exist", Toast.LENGTH_SHORT).show();
            }
        });
    }
}