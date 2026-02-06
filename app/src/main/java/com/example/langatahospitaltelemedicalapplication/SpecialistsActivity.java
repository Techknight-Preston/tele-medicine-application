package com.example.langatahospitaltelemedicalapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SpecialistsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SpecialistsAdapter specialistsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialists);

        recyclerView = findViewById(R.id.recyclerViewDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<SpecialistsModel> doctorsList = createDoctorsList();

        specialistsAdapter = new SpecialistsAdapter(doctorsList);
        recyclerView.setAdapter(specialistsAdapter);
    }

    private List<SpecialistsModel> createDoctorsList() {
        List<SpecialistsModel> doctors = new ArrayList<>();


        doctors.add(new SpecialistsModel("Dr.Daniel Muchemi", "Cardiologist",
                "MuchemiDan123@langata.com", 15, R.drawable.docicon1));

        doctors.add(new SpecialistsModel("Dr. Ann Muthoni", "Neurologist",
                "Annmuthoni100@lanagata.com", 12, R.drawable.docicon3));

        doctors.add(new SpecialistsModel("Dr. Emilio Omar", "Pediatrician",
                "eomar765@lanagata.com", 10, R.drawable.docicon1));

        doctors.add(new SpecialistsModel("Dr. Julius Omondi", "Orthopedic Surgeon",
                "juliusomondi890@langata.com", 18, R.drawable.docicon1));

        doctors.add(new SpecialistsModel("Dr. Lisa Gitau", "Dermatologist",
                "gitaulisa671@lanagata.com", 8, R.drawable.docicon3));

        doctors.add(new SpecialistsModel("Dr. Robert Wafula", "Oncologist",
                "wafularobert12@langata.com", 20, R.drawable.docicon1));

        doctors.add(new SpecialistsModel("Dr. Maria Chebet", "Gynecologist",
                "mariachebet90@lanagata.com", 14, R.drawable.docicon3));

        doctors.add(new SpecialistsModel("Dr. Nancy Mutiso", "Psychiatrist",
                "mutisonancy300@lanagata.com", 9, R.drawable.docicon3));

        return doctors;
    }
}