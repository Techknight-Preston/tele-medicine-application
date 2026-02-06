package com.example.langatahospitaltelemedicalapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class InquiryAdapter extends RecyclerView.Adapter<InquiryAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Inquiry> inquiryList;
    private final DatabaseHelper dbHelper;

    public InquiryAdapter(Context context, ArrayList<Inquiry> inquiryList, DatabaseHelper dbHelper) {
        this.context = context;
        this.inquiryList = inquiryList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inquiry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inquiry inquiry = inquiryList.get(position);

        // Set inquiry data
        holder.tvPatientName.setText("Patient: " + inquiry.getPatientName());
        holder.tvContact.setText("Contact: " + inquiry.getPatientContact());
        holder.tvCategory.setText("Category: " + inquiry.getCategory());
        holder.tvPriority.setText("Priority: " + inquiry.getPriority());
        holder.tvMessage.setText("Message: " + inquiry.getMessage());
        holder.tvTime.setText("Sent: " + inquiry.getFormattedTime());
        holder.tvStatus.setText("Status: " + inquiry.getStatus());

        // Set status color
        setStatusColor(holder.tvStatus, inquiry.getStatus());

        // Set priority color
        setPriorityColor(holder.tvPriority, inquiry.getPriority());

        // Handle status update buttons
        holder.btnMarkRead.setOnClickListener(v -> updateInquiryStatus(inquiry.getId(), "read", position));
        holder.btnMarkReplied.setOnClickListener(v -> updateInquiryStatus(inquiry.getId(), "replied", position));
        holder.btnMarkResolved.setOnClickListener(v -> updateInquiryStatus(inquiry.getId(), "resolved", position));
    }

    private void setStatusColor(TextView textView, String status) {
        switch (status.toLowerCase()) {
            case "unread":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
                break;
            case "read":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark));
                break;
            case "replied":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
                break;
            case "resolved":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
                break;
            default:
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
                break;
        }
    }

    private void setPriorityColor(TextView textView, String priority) {
        switch (priority.toLowerCase()) {
            case "emergency":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
                break;
            case "high":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark));
                break;
            case "medium":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
                break;
            case "low":
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
                break;
            default:
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
                break;
        }
    }

    private void updateInquiryStatus(int inquiryId, String newStatus, int position) {
        boolean success = dbHelper.updateInquiryStatus(inquiryId, newStatus);
        if (success) {
            // Update local list
            inquiryList.get(position).setStatus(newStatus);
            notifyItemChanged(position);
            Toast.makeText(context, "Status updated to: " + newStatus, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to update status", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return inquiryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName, tvContact, tvCategory, tvPriority, tvMessage, tvTime, tvStatus;
        Button btnMarkRead, btnMarkReplied, btnMarkResolved;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvContact = itemView.findViewById(R.id.tvContact);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvPriority = itemView.findViewById(R.id.tvPriority);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            btnMarkRead = itemView.findViewById(R.id.btnMarkRead);
            btnMarkReplied = itemView.findViewById(R.id.btnMarkReplied);
            btnMarkResolved = itemView.findViewById(R.id.btnMarkResolved);
        }
    }
}