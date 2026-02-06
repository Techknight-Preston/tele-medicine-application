package com.example.langatahospitaltelemedicalapplication;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ViewInquiriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InquiryAdapter adapter;
    private DatabaseHelper dbHelper;
    private ArrayList<Inquiry> inquiryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inquiries);

        recyclerView = findViewById(R.id.recyclerViewInquiries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        inquiryList = new ArrayList<>();

        loadInquiries();

        adapter = new InquiryAdapter(this, inquiryList, dbHelper);
        recyclerView.setAdapter(adapter);
    }

    private void loadInquiries() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int doctorId = prefs.getInt("doctorId", -1);

        if (doctorId == -1) {
            Toast.makeText(this, "Doctor not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        View emptyState = findViewById(R.id.emptyState);
        if (inquiryList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyState.setVisibility(View.GONE);
        }

        Cursor cursor = dbHelper.getInquiriesForDoctor(doctorId);
        inquiryList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                String priority = cursor.getString(cursor.getColumnIndexOrThrow("priority"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));
                String patientName = cursor.getString(cursor.getColumnIndexOrThrow("patient_name"));
                String patientContact = cursor.getString(cursor.getColumnIndexOrThrow("patient_contact"));

                Inquiry inquiry = new Inquiry(id, message, category, priority, status, createdAt, patientName, patientContact);
                inquiryList.add(inquiry);

            } while (cursor.moveToNext());
            cursor.close();
        }

        if (adapter == null) {
            adapter = new InquiryAdapter(this, inquiryList, dbHelper);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInquiries();
    }
}