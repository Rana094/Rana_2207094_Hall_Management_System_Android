package com.example.rana_2207094_hall_management_system_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private Button sendNoticeBtn;
    private Button viewAllStudentBtn;
    private Button pendingApprovalBtn;
    private Button diningManagerApprovalBtn;
    private Button calculateHallDueBtn;
    private Button removeStudentBtn;
    private Button signOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        sendNoticeBtn = findViewById(R.id.sendNoticeBtn);
        viewAllStudentBtn = findViewById(R.id.viewAllStudentBtn);
        pendingApprovalBtn = findViewById(R.id.pendingApprovalBtn);
        diningManagerApprovalBtn = findViewById(R.id.diningManagerApprovalBtn);
        calculateHallDueBtn = findViewById(R.id.calculateHallDueBtn);
        removeStudentBtn = findViewById(R.id.removeStudentBtn);
        signOutBtn = findViewById(R.id.signOutBtn);

        setupActions();
    }

    private void setupActions() {

        sendNoticeBtn.setOnClickListener(v -> {

             Intent intent = new Intent(AdminHomeActivity.this, AdminSendNoticeActivity.class);
             startActivity(intent);
            Toast.makeText(this, "Go to Send Notice Page", Toast.LENGTH_SHORT).show();
        });

        viewAllStudentBtn.setOnClickListener(v -> {

             Intent intent = new Intent(AdminHomeActivity.this, ViewAllStudentsActivity.class);
             startActivity(intent);
            Toast.makeText(this, "Go to View All Students", Toast.LENGTH_SHORT).show();
        });

        pendingApprovalBtn.setOnClickListener(v -> {

             Intent intent = new Intent(AdminHomeActivity.this, AdminPendingApprovalActivity.class);
             startActivity(intent);
            Toast.makeText(this, "Go to Pending Approvals", Toast.LENGTH_SHORT).show();
        });

        diningManagerApprovalBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AdminDiningManagerApprovalActivity.class);
            startActivity(intent);
        });

        calculateHallDueBtn.setOnClickListener(v -> {

             Intent intent = new Intent(AdminHomeActivity.this, AdminCalculateHallDueActivity.class);
             startActivity(intent);
            Toast.makeText(this, "Go to Calculate Due", Toast.LENGTH_SHORT).show();
        });

        removeStudentBtn.setOnClickListener(v -> {

             Intent intent = new Intent(AdminHomeActivity.this, AdminRemoveRequestsActivity.class);
             startActivity(intent);
            Toast.makeText(this, "Go to Remove Requests", Toast.LENGTH_SHORT).show();
        });

        signOutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}