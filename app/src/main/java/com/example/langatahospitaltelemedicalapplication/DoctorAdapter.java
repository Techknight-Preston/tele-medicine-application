package com.example.langatahospitaltelemedicalapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
    private final Context context;
    private final ArrayList<Doctor> doctorList;

    public DoctorAdapter(Context context, ArrayList<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);

        holder.tvDoctorName.setText(doctor.getFullName());
        holder.tvSpecialization.setText(doctor.getSpecialization());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookAppointmentActivity.class);
            intent.putExtra("doctor_first_name", doctor.getFirstName());
            intent.putExtra("doctor_last_name", doctor.getLastName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return doctorList.size(); }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView tvDoctorName, tvSpecialization;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
            tvSpecialization = itemView.findViewById(R.id.tvSpecialization);

        }
    }
}