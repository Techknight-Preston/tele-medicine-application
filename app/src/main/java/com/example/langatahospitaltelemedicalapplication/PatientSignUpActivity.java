package com.example.langatahospitaltelemedicalapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class PatientSignUpActivity extends AppCompatActivity {
    EditText Firstname, Lastname, contactnumber, email, createusername, newpassword, confirmpassword;
    Button Record;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientsignup);

        Firstname = findViewById(R.id.Firstname);
        Lastname = findViewById(R.id.Lastname);
        contactnumber = findViewById(R.id.ContactNumber);
        email = findViewById(R.id.email);
        createusername = findViewById(R.id.CreateUsername);
        newpassword = findViewById(R.id.NewPassword);
        confirmpassword = findViewById(R.id.ConfirmPassword);
        Record = findViewById(R.id.Record);

        dbHelper =new DatabaseHelper(this);

        Record.setOnClickListener( v ->{
            String firstname = Firstname.getText().toString().trim();
            String lastname = Lastname.getText().toString().trim();
            String contact= contactnumber.getText().toString().trim();
            String emailid = email.getText().toString().trim();
            String username = createusername.getText().toString().trim();
            String password = newpassword.getText().toString();
            String confirm = confirmpassword.getText().toString();

            if (firstname.isEmpty() || lastname.isEmpty() || contact.isEmpty() || emailid.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (username.length() < 4 || username. length() >15){
                Toast.makeText(this, "Username must be 4-15 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirm)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else{
                boolean inserted = dbHelper.insertPatient(firstname,lastname,contact,emailid, username,password);

                if (inserted){
                    Toast.makeText(this, "Patient registered successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PatientSignUpActivity.this, PatientLoginActivity.class));
                    finish();
                }
            }


        });
    }
}
