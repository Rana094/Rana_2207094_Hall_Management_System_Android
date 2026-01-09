package com.example.rana_2207094_hall_management_system_android;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HallBillAdapter extends RecyclerView.Adapter<HallBillAdapter.BillViewHolder> {

    private List<HallBill> billList;

    private OnBillListener mListener;
    private int selectedPosition = -1;

    public interface OnBillListener {
        void onBillClick(int position);
    }

    public HallBillAdapter(List<HallBill> billList) {
        this.billList = billList;
        this.mListener = null;
    }

    public HallBillAdapter(List<HallBill> billList, OnBillListener listener) {
        this.billList = billList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hall_bill, parent, false);
        return new BillViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        HallBill bill = billList.get(position);
        holder.monthTxt.setText(bill.getMonth());
        holder.amountTxt.setText(bill.getAmount() + " Tk");

        if (mListener != null) {
            if (selectedPosition == position) {
                holder.mainLayout.setBackgroundColor(Color.parseColor("#BBDEFB")); // Blue Highlight
            } else {
                holder.mainLayout.setBackgroundColor(Color.parseColor("#FFFFFF")); // White
            }

            holder.itemView.setOnClickListener(v -> {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
                mListener.onBillClick(selectedPosition);
            });
        }
    }
    public HallBill getSelectedBill() {
        if (selectedPosition != -1 && selectedPosition < billList.size()) {
            return billList.get(selectedPosition);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView monthTxt, amountTxt;
        LinearLayout mainLayout;

        public BillViewHolder(@NonNull View itemView, OnBillListener listener) {
            super(itemView);
            monthTxt = itemView.findViewById(R.id.monthTxt);
            amountTxt = itemView.findViewById(R.id.amountTxt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}