package com.example.langatahospitaltelemedicalapplication;

import android.database.AbstractCursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AppointmentCalendarActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText notesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointmentcalendar);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        notesEditText = findViewById(R.id.notesEditText);
        Button confirmAppointmentBtn = findViewById(R.id.confirmAppointmentBtn);

        String doctorName = getIntent().getStringExtra("doctorId");
        String patientName = "current_patient_id";

        confirmAppointmentBtn.setOnClickListener(v -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            String date = day + "/" + (month + 1) + "/" + year;
            String time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
            String notes = notesEditText.getText().toString();



            Toast.makeText(this, "Appointment booked!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

