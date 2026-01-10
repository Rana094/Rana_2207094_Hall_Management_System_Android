package com.example.rana_2207094_hall_management_system_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public class AdminRemoveRequestsActivity extends AppCompatActivity implements StudentAdapter.OnStudentListener {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private FirebaseManager firebaseManager;
    private Button approveBtn, rejectBtn, goBackBtn;

    private Student selectedStudent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_remove_requests);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        approveBtn = findViewById(R.id.approveBtn);
        rejectBtn = findViewById(R.id.rejectBtn);
        goBackBtn = findViewById(R.id.goBackBtn);

        firebaseManager = new FirebaseManager();

        setupActions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRemovalRequests();
    }

    private void loadRemovalRequests() {
        firebaseManager.getRemovalRequests(new DatabaseCallback() {
            @Override
            public void onStudentListReceived(List<Student> students) {
                if (students != null && !students.isEmpty()) {

                    adapter = new StudentAdapter(AdminRemoveRequestsActivity.this, students, AdminRemoveRequestsActivity.this, false);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(AdminRemoveRequestsActivity.this, "No removal requests found", Toast.LENGTH_SHORT).show();
                    adapter = new StudentAdapter(AdminRemoveRequestsActivity.this, Collections.emptyList(), AdminRemoveRequestsActivity.this, false);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(AdminRemoveRequestsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupActions() {
        goBackBtn.setOnClickListener(v -> finish());

        approveBtn.setOnClickListener(v -> {
            if (selectedStudent == null) {
                Toast.makeText(this, "Please select a student first", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseManager.deleteStudent(selectedStudent.getRoll(), new DatabaseCallback() {
                @Override
                public void checkResult(boolean isSuccess) {
                    if (isSuccess) {
                        Toast.makeText(AdminRemoveRequestsActivity.this, "Student Removed Successfully", Toast.LENGTH_SHORT).show();
                        selectedStudent = null;
                        loadRemovalRequests();
                    } else {
                        Toast.makeText(AdminRemoveRequestsActivity.this, "Failed to remove student", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onError(Exception e) {
                    Toast.makeText(AdminRemoveRequestsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        rejectBtn.setOnClickListener(v -> {
            if (selectedStudent == null) {
                Toast.makeText(this, "Please select a student first", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseManager.rejectRemovalRequest(selectedStudent.getRoll(), new DatabaseCallback() {
                @Override
                public void checkResult(boolean isSuccess) {
                    if (isSuccess) {
                        Toast.makeText(AdminRemoveRequestsActivity.this, "Request Rejected", Toast.LENGTH_SHORT).show();
                        selectedStudent = null;
                        loadRemovalRequests();
                    } else {
                        Toast.makeText(AdminRemoveRequestsActivity.this, "Failed to reject request", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onError(Exception e) {
                    Toast.makeText(AdminRemoveRequestsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onStudentClick(int position) {
        selectedStudent = adapter.getSelectedStudent();
    }
}