package com.example.langatahospitaltelemedicalapplication;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorAppointmentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AppointmentAdapter adapter;
    ArrayList<Appointment> appointmentList;
    DatabaseHelper dbHelper;
    int doctorId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewappointments);

        recyclerView = findViewById(R.id.recyclerViewAppointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        doctorId = prefs.getInt("doctorId", -1);
        Toast.makeText(this, "Doctor ID loaded: " +doctorId, Toast.LENGTH_SHORT).show();

        loadAppointments();
    }

    private void loadAppointments() {
        appointmentList = new ArrayList<>();
        Cursor cursor = dbHelper.getAppointmentsForDoctor(doctorId);

        if (cursor != null) {

            int idIndex = cursor.getColumnIndex("id");
            int patientNameIndex = cursor.getColumnIndex("patient_name");
            int dateIndex = cursor.getColumnIndex("date");
            int timeIndex = cursor.getColumnIndex("time");
            int notesIndex = cursor.getColumnIndex("notes");
            int statusIndex = cursor.getColumnIndex("appointment_status");


            if (idIndex != -1 && patientNameIndex != -1 && dateIndex != -1 &&
                    timeIndex != -1 && notesIndex != -1 && statusIndex != -1) {

                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(idIndex);
                        String patientName = cursor.getString(patientNameIndex);
                        String date = cursor.getString(dateIndex);
                        String time = cursor.getString(timeIndex);
                        String notes = cursor.getString(notesIndex);
                        String status = cursor.getString(statusIndex);

                        appointmentList.add(new Appointment(id, patientName, date, time, notes, status));
                    } while (cursor.moveToNext());
                }
            } else {

                android.util.Log.e("DoctorAppointment", "Missing required database columns");
            }
            cursor.close();
        }

        adapter = new AppointmentAdapter(this, appointmentList, dbHelper);
        recyclerView.setAdapter(adapter);
    }
}