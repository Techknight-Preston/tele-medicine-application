package com.example.langatahospitaltelemedicalapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FindDoctorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DoctorAdapter adapter;
    private ArrayList<Doctor> doctorList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finddoctor);

        recyclerView = findViewById(R.id.recyclerViewDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);

        loadDoctors();
    }

    private void loadDoctors() {
        doctorList = dbHelper.FindDoctor();
        adapter = new DoctorAdapter(this, doctorList);
        recyclerView.setAdapter(adapter);
    }
}

