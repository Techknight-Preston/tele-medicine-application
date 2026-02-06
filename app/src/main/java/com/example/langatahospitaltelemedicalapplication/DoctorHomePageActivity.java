package com.example.langatahospitaltelemedicalapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DoctorHomePageActivity extends AppCompatActivity {

    ImageView menuBar, searchIcon, backIcon, profileIcon;
    LinearLayout upcomingappointments, viewpatientrecords, patientrequests;
    Button EprescriptionButton;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorhomepage);

        menuBar = findViewById(R.id.menu_bar2);
        searchIcon = findViewById(R.id.search_icon2);
        backIcon = findViewById(R.id.back_arrow2);
        profileIcon = findViewById(R.id.profile3);

        upcomingappointments = findViewById(R.id.upcoming_appointments);
        viewpatientrecords = findViewById(R.id.view_patient_records);
        patientrequests = findViewById(R.id.Patients_requests);
        EprescriptionButton = findViewById(R.id.E_prescription);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view_id);

        searchBar = findViewById(R.id.search_view_bar);
        searchBar.setVisibility(View.GONE);

        setupNavigationDrawer();
        setupClickListeners();
    }

    private void setupNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();

            int id = item.getItemId();

            if (id == R.id.nav_view_appointments) {
                startActivity(new Intent(this, DoctorAppointmentActivity.class));
            } else if (id == R.id.nav_patient_records) {
                startActivity(new Intent(this, ViewPatientRecordsActivity.class));
            } else if (id == R.id.nav_patient_requests) {
                startActivity(new Intent(this, ViewInquiriesActivity.class));
            } else if (id == R.id.nav_Eprescription) {
                startActivity(new Intent(this, EprescriptionActivity.class));
            } else if (id == R.id.nav_add_patient_records) {
                startActivity(new Intent(this, AddPatientRecordActivity.class));
            }

            return true;
        });
    }

    private void setupClickListeners() {

        menuBar.setOnClickListener(v -> {

            drawerLayout.openDrawer(GravityCompat.START);
        });

        searchIcon.setOnClickListener(v -> {
            if (searchBar.getVisibility() == View.GONE) {
                searchBar.setVisibility(View.VISIBLE);
            } else {
                searchBar.setVisibility(View.GONE);
            }
        });

        backIcon.setOnClickListener(v ->
                startActivity(new Intent(this, DoctorLoginActivity.class))
        );

        profileIcon.setOnClickListener(v ->
                startActivity(new Intent(this, Profile2Activity.class))
        );

        upcomingappointments.setOnClickListener(v ->
                startActivity(new Intent(this, DoctorAppointmentActivity.class))
        );

        viewpatientrecords.setOnClickListener(v ->
                startActivity(new Intent(this, ViewPatientRecordsActivity.class))
        );

        patientrequests.setOnClickListener(v ->
                startActivity(new Intent(this, ViewInquiriesActivity.class))
        );

        EprescriptionButton.setOnClickListener(v ->
                startActivity(new Intent(this, EprescriptionActivity.class))
        );
    }
}