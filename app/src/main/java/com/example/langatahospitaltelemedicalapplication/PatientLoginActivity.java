package com.example.langatahospitaltelemedicalapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class PatientLoginActivity extends AppCompatActivity {
    EditText Username, Password;
    Button Login, Signup;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlogin);

        Username =findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.Login);
        Signup = findViewById(R.id.Signup);

        dbHelper =new DatabaseHelper(this);

        Login.setOnClickListener( v ->{
            String username = Username.getText().toString().trim();
            String password = Password.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "please fill all fields",Toast.LENGTH_SHORT).show();

            } else {
                int patientId = dbHelper.getPatientId(username, password);

                if (patientId !=-1){
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                    SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("patientId", patientId);
                    editor.apply();

                    startActivity(new Intent(PatientLoginActivity.this, PatientHomePageActivity.class));
                    finish();
                }else{
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }


        });
        Signup.setOnClickListener( v ->
                startActivity(new Intent(this, PatientSignUpActivity.class)));
    }
}
