package com.example.langatahospitaltelemedicalapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class DoctorLoginActivity extends AppCompatActivity {
    EditText et_license, Password;
    Button Login, Signup;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlogin);

        dbHelper = new DatabaseHelper(this);

        et_license =findViewById(R.id.LicenseNumber);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.Login);
        Signup = findViewById(R.id.SignUp);

        Login.setOnClickListener( v ->{
            String license = et_license.getText().toString().trim();
            String password = Password.getText().toString();

            int doctorId = dbHelper.getDoctorId(license, password);

            if (doctorId != -1) {
                Toast.makeText(this, "Login successful!",Toast.LENGTH_SHORT).show();

                SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("doctorId", doctorId);
                editor.apply();

                Intent intent = new Intent(DoctorLoginActivity.this, DoctorHomePageActivity.class);
                startActivity(intent);
                finish();

            }
            else{
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }

        });
        Signup.setOnClickListener( v ->
                startActivity(new Intent(this, DoctorSignUpActivity.class)));
    }
}
