package com.example.langatahospitaltelemedicalapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PatientRecordAdapter extends RecyclerView.Adapter<PatientRecordAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PatientRecord> recordList;

    public PatientRecordAdapter(Context context, ArrayList<PatientRecord> recordList) {
        this.context = context;
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_patient_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PatientRecord record = recordList.get(position);

        holder.tvPatientName.setText("Patient: " + record.getPatientName());
        holder.tvVisitDate.setText(record.getVisitDate());
        holder.tvBloodPressure.setText("BP: " + record.getBloodPressure());
        holder.tvTemperature.setText("Temp: " + record.getTemperature());
        holder.tvHeartRate.setText("HR: " + record.getHeartRate() + " bpm");
        holder.tvDiagnosis.setText("Diagnosis: " + record.getDiagnosis());
        holder.tvTreatment.setText("Treatment: " + record.getTreatment());

        holder.btnViewDetails.setOnClickListener(v -> {
            // Open detailed view (doctors can see full details)
            showRecordDetails(record);
        });
    }

    private void showRecordDetails(PatientRecord record) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Medical Record Details");
        builder.setMessage(
                "Patient: " + record.getPatientName() + "\n\n" +
                        "Date: " + record.getVisitDate() + "\n\n" +
                        "Diagnosis: " + record.getDiagnosis() + "\n\n" +
                        "Treatment: " + record.getTreatment() + "\n\n" +
                        "Medications: " + record.getMedications() + "\n\n" +
                        "Vitals: " + record.getBloodPressure() + " | " +
                        record.getTemperature() + " | " +
                        record.getHeartRate() + " bpm\n\n" +
                        "Doctor Notes: " + record.getDoctorNotes()
        );
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName, tvVisitDate, tvBloodPressure, tvTemperature, tvHeartRate;
        TextView tvDiagnosis, tvTreatment;
        Button btnViewDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvVisitDate = itemView.findViewById(R.id.tvVisitDate);
            tvBloodPressure = itemView.findViewById(R.id.tvBloodPressure);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            tvHeartRate = itemView.findViewById(R.id.tvHeartRate);
            tvDiagnosis = itemView.findViewById(R.id.tvDiagnosis);
            tvTreatment = itemView.findViewById(R.id.tvTreatment);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}
