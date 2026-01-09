package com.example.rana_2207094_hall_management_system_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ViewAllStudentsActivity extends AppCompatActivity implements StudentAdapter.OnStudentListener {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private FirebaseManager firebaseManager;

    private Button goBackBtn, deleteStudentBtn, updateStudentBtn;

    private Student selectedStudent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_students);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        goBackBtn = findViewById(R.id.goBackBtn);
        deleteStudentBtn = findViewById(R.id.deleteStudentBtn);
        updateStudentBtn = findViewById(R.id.updateStudentBtn);

        firebaseManager = new FirebaseManager();

        setupButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadActiveStudents();
    }


    private void setupButtons() {

        goBackBtn.setOnClickListener(v -> finish());

        deleteStudentBtn.setOnClickListener(v -> {
            if (selectedStudent == null) {
                Toast.makeText(this, "Please select a student first!", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseManager.deleteStudent(selectedStudent.getRoll(), new DatabaseCallback() {
                @Override
                public void checkResult(boolean isSuccess) {
                    if (isSuccess) {
                        Toast.makeText(ViewAllStudentsActivity.this, "Deleted: " + selectedStudent.getName(), Toast.LENGTH_SHORT).show();

                        loadActiveStudents();
                        selectedStudent = null;
                    } else {
                        Toast.makeText(ViewAllStudentsActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onError(Exception e) {
                    Toast.makeText(ViewAllStudentsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // UPDATE
        updateStudentBtn.setOnClickListener(v -> {
            if (selectedStudent == null) {
                Toast.makeText(this, "Please select a student first!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(ViewAllStudentsActivity.this, AdminUpdateStudentActivity.class);
            intent.putExtra("ROLL_TO_UPDATE", selectedStudent.getRoll());
            startActivity(intent);
        });
    }

    private void loadActiveStudents() {
        firebaseManager.getActiveStudents(new DatabaseCallback() {
            @Override
            public void onStudentListReceived(List<Student> students) {
                if (students != null && !students.isEmpty()) {
                    adapter = new StudentAdapter(ViewAllStudentsActivity.this, students, ViewAllStudentsActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ViewAllStudentsActivity.this, "No active students found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(ViewAllStudentsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStudentClick(int position) {
        selectedStudent = adapter.getSelectedStudent();
    }
}