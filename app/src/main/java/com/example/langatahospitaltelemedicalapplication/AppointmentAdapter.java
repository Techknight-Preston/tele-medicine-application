package com.example.langatahospitaltelemedicalapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    private final Context context;
    private final ArrayList<Appointment> appointmentList;
    private final DatabaseHelper dbHelper;

    public AppointmentAdapter(Context context, ArrayList<Appointment> appointmentList, DatabaseHelper dbHelper) {
        this.context = context;
        this.appointmentList = appointmentList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appt = appointmentList.get(position);

        holder.tvPatientName.setText(appt.patientName);
        holder.tvDate.setText(appt.date);
        holder.tvTime.setText(appt.time);
        holder.tvNotes.setText(appt.notes);
        holder.tvStatus.setText("Status: " + appt.status);


        holder.btnAccept.setOnClickListener(v -> {
         if(dbHelper.updateAppointmentStatus(appt.id, "Accepted")) {
             appt.status = "Accepted";
             notifyItemChanged(position);
             Toast.makeText(context, "Appointment Accepted", Toast.LENGTH_SHORT).show();
         }
        });


        holder.btnDecline.setOnClickListener(v -> {
           if(dbHelper.updateAppointmentStatus(appt.id, "Declined")) {
               appt.status = "Declined";
               notifyItemChanged(position);
               Toast.makeText(context, "Appointment Declined", Toast.LENGTH_SHORT).show();
           }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName, tvDate, tvTime, tvNotes, tvStatus;
        Button btnAccept, btnDecline;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnDecline = itemView.findViewById(R.id.btnDecline);
        }
    }
}