package com.example.langatahospitaltelemedicalapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {

    EditText editTextDoctorName, editTextDate, editTextTime, editTextNotes;
    Button buttonBookAppointment;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookappointment);

        editTextDoctorName = findViewById(R.id.editTextDoctorName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        editTextNotes = findViewById(R.id.editTextNotes);
        buttonBookAppointment = findViewById(R.id.buttonBookAppointment);

        dbHelper = new DatabaseHelper(this);


        String doctorFirstName = getIntent().getStringExtra("doctor_first_name");
        String doctorLastName = getIntent().getStringExtra("doctor_last_name");

        if (doctorFirstName != null && doctorLastName != null) {
            editTextDoctorName.setText(doctorFirstName + " " + doctorLastName);
            editTextDoctorName.setEnabled(false);
        }

        editTextDate.setOnClickListener(v -> showDatePicker());
        editTextTime.setOnClickListener(v -> showTimePicker());
        buttonBookAppointment.setOnClickListener(v -> bookAppointment());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            editTextDate.setText(date);
        }, year, month, day);
        dialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d", selectedHour, selectedMinute);
            editTextTime.setText(time);
        }, hour, minute, true);
        dialog.show();
    }

    private void bookAppointment() {
        String doctorName = editTextDoctorName.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();
        String notes = editTextNotes.getText().toString().trim();

        // Validate inputs
        if (doctorName.isEmpty()) {
            Toast.makeText(this, "Please enter doctor name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (date.isEmpty()) {
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (time.isEmpty()) {
            Toast.makeText(this, "Please select time", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int patientId = prefs.getInt("patientId", -1);

        //  CONVERT DOCTOR NAME TO ID
        String[] nameParts = doctorName.split(" ");
        String doctorFirstName = nameParts[0];
        String doctorLastName = nameParts.length > 1 ? nameParts[1] : "";

        int doctorId = dbHelper.getDoctorIdByName(doctorFirstName, doctorLastName);

        if (patientId != -1 && doctorId != -1) {
            boolean inserted = dbHelper.insertAppointment(patientId, doctorId, date, time, notes);

            if (inserted) {
                Toast.makeText(this, "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to book appointment", Toast.LENGTH_SHORT).show();
            }
        } else if (doctorId == -1) {
            Toast.makeText(this, "Doctor not found: " + doctorName, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
        }
    }
}