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

public class PatientHomePageActivity extends AppCompatActivity {
    ImageView menuBar, searchIcon, backIcon, profileIcon;
    LinearLayout findDoctorCard, inquiryCard, appointmentCard;
    Button specialistsButton;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patienthomepage);

        initializeViews();
        setupNavigationDrawer();
        setupClickListeners();
    }

    private void initializeViews() {
        menuBar = findViewById(R.id.menu_bar);
        searchIcon = findViewById(R.id.search_icon);
        backIcon = findViewById(R.id.back_icon);
        profileIcon = findViewById(R.id.profile2);

        findDoctorCard = findViewById(R.id.find_doctor_card);
        inquiryCard = findViewById(R.id.inquiry_card);
        appointmentCard = findViewById(R.id.appointment_card);
        specialistsButton = findViewById(R.id.Specialists);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        searchBar = findViewById(R.id.search_view);
        searchBar.setVisibility(View.GONE);
    }

    private void setupNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();

            int id = item.getItemId();



                if (id == R.id.nav_find_doctor) {
                    startActivity(new Intent(this, FindDoctorActivity.class));
                } else if (id == R.id.nav_book_appointment) {
                    startActivity(new Intent(this, BookAppointmentActivity.class));
                } else if (id == R.id.nav_send_inquiry) {
                    startActivity(new Intent(this, SendInquiryActivity.class));
                } else if (id == R.id.nav_view_specialists) {
                    startActivity(new Intent(this, SpecialistsActivity.class));
                }

            return true;
        });
    }

    private void setupClickListeners() {
        menuBar.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        searchIcon.setOnClickListener(v -> {
            if (searchBar.getVisibility() == View.GONE) {
                searchBar.setVisibility(View.VISIBLE);
            } else {
                searchBar.setVisibility(View.GONE);
            }
        });

        backIcon.setOnClickListener(v ->
                startActivity(new Intent(this, PatientLoginActivity.class)));

        profileIcon.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        findDoctorCard.setOnClickListener(v ->
                startActivity(new Intent(this, FindDoctorActivity.class)));

        inquiryCard.setOnClickListener(v ->
                startActivity(new Intent(this, SendInquiryActivity.class)));

        appointmentCard.setOnClickListener(v ->
                startActivity(new Intent(this, BookAppointmentActivity.class)));

        specialistsButton.setOnClickListener(v ->
                startActivity(new Intent(this, SpecialistsActivity.class)));
    }
}