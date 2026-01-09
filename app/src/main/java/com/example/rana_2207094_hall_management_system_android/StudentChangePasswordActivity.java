package com.example.rana_2207094_hall_management_system_android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class StudentChangePasswordActivity extends AppCompatActivity {

    private EditText currentPasswordTxt, newPasswordTxt, confirmPasswordTxt;
    private Button changePasswordBtn, goBackBtn;

    private FirebaseManager firebaseManager;
    private int loggedInRoll;
    private Student currentStudent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_change_password);

        loggedInRoll = getIntent().getIntExtra("LOGGED_IN_ROLL", -1);

        firebaseManager = new FirebaseManager();

        currentPasswordTxt = findViewById(R.id.currentPasswordTxt);
        newPasswordTxt = findViewById(R.id.newPasswordTxt);
        confirmPasswordTxt = findViewById(R.id.confirmPasswordTxt);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);
        goBackBtn = findViewById(R.id.goBackBtn);

        loadStudentData();

        setupActions();
    }

    private void loadStudentData() {
        if (loggedInRoll == -1) {
            Toast.makeText(this, "Session Error", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        firebaseManager.getStudentByRoll(loggedInRoll, new DatabaseCallback() {
            @Override
            public void onStudentReceived(Student student) {
                if (student != null) {
                    currentStudent = student;
                } else {
                    Toast.makeText(StudentChangePasswordActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(StudentChangePasswordActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupActions() {
        goBackBtn.setOnClickListener(v -> finish());

        changePasswordBtn.setOnClickListener(v -> attemptChangePassword());
    }

    private void attemptChangePassword() {
        if (currentStudent == null) {
            Toast.makeText(this, "Data not loaded yet, please wait...", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentPass = currentPasswordTxt.getText().toString();
        String newPass = newPasswordTxt.getText().toString();
        String confirmPass = confirmPasswordTxt.getText().toString();

        if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!currentPass.equals(currentStudent.getPassword())) {
            Toast.makeText(this, "Password didn't match with the current password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, "New Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPass.equals(currentPass)) {
            Toast.makeText(this, "New Password is same as prev password", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        updates.put("password", newPass);

        firebaseManager.updateStudentData(loggedInRoll, updates, new DatabaseCallback() {
            @Override
            public void checkResult(boolean isSuccess) {
                if (isSuccess) {
                    Toast.makeText(StudentChangePasswordActivity.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(StudentChangePasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(StudentChangePasswordActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}