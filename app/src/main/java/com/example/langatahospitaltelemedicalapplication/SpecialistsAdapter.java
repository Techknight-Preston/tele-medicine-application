package com.example.langatahospitaltelemedicalapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpecialistsAdapter extends RecyclerView.Adapter<SpecialistsAdapter.DoctorViewHolder> {

    private List<SpecialistsModel> doctorsList;

    public SpecialistsAdapter(List<SpecialistsModel> doctorsList) {
        this.doctorsList = doctorsList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_specialists, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        SpecialistsModel doctor = doctorsList.get(position);

        holder.doctorIcon.setImageResource(doctor.getIconResourceId());
        holder.doctorName.setText(doctor.getName());
        holder.doctorSpecialty.setText(doctor.getSpecialty());
        holder.doctorEmail.setText(doctor.getEmail());
        holder.doctorExperience.setText(doctor.getExperienceYears() + " years experience");
    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        ImageView doctorIcon;
        TextView doctorName, doctorSpecialty, doctorEmail, doctorExperience;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorIcon = itemView.findViewById(R.id.doctorIcon);
            doctorName = itemView.findViewById(R.id.doctorName);
            doctorSpecialty = itemView.findViewById(R.id.doctorSpecialty);
            doctorEmail = itemView.findViewById(R.id.doctorEmail);
            doctorExperience = itemView.findViewById(R.id.doctorExperience);
        }
    }
}