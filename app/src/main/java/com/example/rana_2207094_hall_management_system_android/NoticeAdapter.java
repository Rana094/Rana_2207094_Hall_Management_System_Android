package com.example.rana_2207094_hall_management_system_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private List<Notice> noticeList;

    public NoticeAdapter(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        Notice notice = noticeList.get(position);

        holder.titleTxt.setText(notice.getTitle());
        holder.messageTxt.setText(notice.getMessage());

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
        String dateString = sdf.format(new Date(notice.getDate()));
        holder.dateTxt.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, dateTxt, messageTxt;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            dateTxt = itemView.findViewById(R.id.dateTxt);
            messageTxt = itemView.findViewById(R.id.messageTxt);
        }
    }
}