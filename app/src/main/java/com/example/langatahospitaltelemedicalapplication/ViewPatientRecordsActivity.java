package com.example.langatahospitaltelemedicalapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class ViewPatientRecordsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PatientRecordAdapter adapter;
    private DatabaseHelper dbHelper;
    private ArrayList<PatientRecord> recordList;
    private TextView tvEmptyState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_records);



        recyclerView = findViewById(R.id.recyclerViewPatientRecords);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dbHelper = new DatabaseHelper(this);
        recordList = new ArrayList<>();




        if (!isDoctorLoggedIn()) {
            showAccessDenied();
            return;
        }

        loadPatientRecords();
    }

    private boolean isDoctorLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int doctorId = prefs.getInt("doctorId", -1);


        return doctorId != -1;
    }

    private void showAccessDenied() {
        Toast.makeText(this, "Access denied. Doctors only.", Toast.LENGTH_LONG).show();
        finish(); // Close the activity
    }

    private void loadPatientRecords() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int doctorId = prefs.getInt("doctorId", -1);

        Cursor cursor = dbHelper.getPatientRecordsForDoctor(doctorId);
        recordList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String diagnosis = cursor.getString(cursor.getColumnIndexOrThrow("diagnosis"));
                String treatment = cursor.getString(cursor.getColumnIndexOrThrow("treatment"));
                String medications = cursor.getString(cursor.getColumnIndexOrThrow("medications"));
                String visitDate = cursor.getString(cursor.getColumnIndexOrThrow("visit_date"));
                String doctorNotes = cursor.getString(cursor.getColumnIndexOrThrow("doctor_notes"));
                String bloodPressure = cursor.getString(cursor.getColumnIndexOrThrow("blood_pressure"));
                String temperature = cursor.getString(cursor.getColumnIndexOrThrow("temperature"));
                String heartRate = cursor.getString(cursor.getColumnIndexOrThrow("heart_rate"));
                String patientName = cursor.getString(cursor.getColumnIndexOrThrow("patient_name"));

                PatientRecord record = new PatientRecord(id, 0, patientName, diagnosis, treatment,
                        medications, visitDate, doctorNotes,
                        bloodPressure, temperature, heartRate);
                recordList.add(record);

            } while (cursor.moveToNext());
            cursor.close();
        }

        // Show empty state if no records
        if (recordList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyState.setVisibility(View.GONE);

            if (adapter == null) {
                adapter = new PatientRecordAdapter(this, recordList);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isDoctorLoggedIn()) {
            loadPatientRecords();
        }
    }
}