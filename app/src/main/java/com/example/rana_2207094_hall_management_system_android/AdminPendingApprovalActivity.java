package com.example.rana_2207094_hall_management_system_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public class AdminPendingApprovalActivity extends AppCompatActivity implements StudentAdapter.OnStudentListener {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private FirebaseManager firebaseManager;
    private Button viewStudentBtn, goBackBtn;
    private Student selectedStudent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pending_approval);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewStudentBtn = findViewById(R.id.viewStudentBtn);
        goBackBtn = findViewById(R.id.goBackBtn);

        firebaseManager = new FirebaseManager();

        setupActions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPendingStudents();
    }

    private void loadPendingStudents() {
        firebaseManager.getPendingStudents(new DatabaseCallback() {
            @Override
            public void onStudentListReceived(List<Student> students) {
                if (students != null && !students.isEmpty()) {

                    adapter = new StudentAdapter(AdminPendingApprovalActivity.this, students, AdminPendingApprovalActivity.this, true);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(AdminPendingApprovalActivity.this, "No pending approvals", Toast.LENGTH_SHORT).show();

                    adapter = new StudentAdapter(AdminPendingApprovalActivity.this, Collections.emptyList(), AdminPendingApprovalActivity.this, true);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(AdminPendingApprovalActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupActions() {
        goBackBtn.setOnClickListener(v -> finish());

        viewStudentBtn.setOnClickListener(v -> {
            if (selectedStudent == null) {
                Toast.makeText(this, "Select a student first", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(AdminPendingApprovalActivity.this, StudentProfileActivity.class);
            intent.putExtra("LOGGED_IN_ROLL", selectedStudent.getRoll());
            intent.putExtra("IS_ADMIN_VIEW", true);
            startActivity(intent);
        });
    }

    @Override
    public void onStudentClick(int position) {
        selectedStudent = adapter.getSelectedStudent();
    }
}