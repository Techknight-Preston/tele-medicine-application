package com.example.langatahospitaltelemedicalapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private Button DoctorLogin;
    private Button PatientLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button DoctorLogin = findViewById(R.id.doctorTab);
        Button PatientLogin = findViewById(R.id.patientTab);

        DoctorLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                Intent intent = new Intent(MainActivity.this, DoctorLoginActivity.class);
                intent.putExtra("userType", "doctor");
                startActivity(intent);

            }

        });


        PatientLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                Intent intent = new Intent(MainActivity.this, PatientLoginActivity.class);
                intent.putExtra("userType","patient");
                startActivity(intent);
            }
        });
    }
}
