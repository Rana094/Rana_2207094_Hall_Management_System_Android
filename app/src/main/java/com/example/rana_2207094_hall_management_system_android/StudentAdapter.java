package com.example.rana_2207094_hall_management_system_android;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private Context context;
    private OnStudentListener mOnStudentListener;
    private int selectedPosition = -1;

    private boolean isPendingMode;

    public interface OnStudentListener {
        void onStudentClick(int position);
    }

    public StudentAdapter(Context context, List<Student> studentList, OnStudentListener onStudentListener, boolean isPendingMode) {
        this.context = context;
        this.studentList = studentList;
        this.mOnStudentListener = onStudentListener;
        this.isPendingMode = isPendingMode;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view, mOnStudentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);

        holder.nameTxt.setText(student.getName());
        holder.rollTxt.setText("Roll: " + student.getRoll());
        holder.deptTxt.setText("Dept: " + student.getDept());

        if (isPendingMode) {

            holder.dueTxt.setText("CGPA: " + student.getCgpa());
            holder.dueTxt.setTextColor(Color.parseColor("#388E3C"));
        } else {

            holder.dueTxt.setText("Due: 0 Tk");
            holder.dueTxt.setTextColor(Color.parseColor("#D32F2F"));
        }

        if (selectedPosition == position) {
            holder.mainLayout.setBackgroundColor(Color.parseColor("#E0E0E0"));
        } else {
            holder.mainLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
            mOnStudentListener.onStudentClick(selectedPosition);
        });
    }
    public Student getSelectedStudent() {
        if (selectedPosition != -1 && selectedPosition < studentList.size()) {
            return studentList.get(selectedPosition);
        }
        return null;
    }

    public void removeSelected() {
        if (selectedPosition != -1) {
            studentList.remove(selectedPosition);
            notifyItemRemoved(selectedPosition);
            selectedPosition = -1;
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt, rollTxt, deptTxt, dueTxt;
        LinearLayout mainLayout;

        public StudentViewHolder(@NonNull View itemView, OnStudentListener onStudentListener) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            rollTxt = itemView.findViewById(R.id.rollTxt);
            deptTxt = itemView.findViewById(R.id.deptTxt);
            dueTxt = itemView.findViewById(R.id.dueTxt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}