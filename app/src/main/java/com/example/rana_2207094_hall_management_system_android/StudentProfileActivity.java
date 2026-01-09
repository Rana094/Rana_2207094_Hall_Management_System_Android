package com.example.rana_2207094_hall_management_system_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StudentProfileActivity extends AppCompatActivity {

    private EditText rollTxt, nameTxt, emailTxt, addressTxt, departmentTxt, cgpaTxt, birthDateTxt;
    private Button goBackBtn, approveBtn;

    private FirebaseManager firebaseManager;
    private int studentRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        studentRoll = getIntent().getIntExtra("LOGGED_IN_ROLL", -1);
        boolean isAdminView = getIntent().getBooleanExtra("IS_ADMIN_VIEW", false);

        firebaseManager = new FirebaseManager();

        rollTxt = findViewById(R.id.rollTxt);
        nameTxt = findViewById(R.id.nameTxt);
        emailTxt = findViewById(R.id.emailTxt);
        addressTxt = findViewById(R.id.addressTxt);
        departmentTxt = findViewById(R.id.departmentTxt);
        cgpaTxt = findViewById(R.id.cgpaTxt);
        birthDateTxt = findViewById(R.id.birthDateTxt);

        goBackBtn = findViewById(R.id.goBackBtn);
        approveBtn = findViewById(R.id.approveBtn);

        if (studentRoll != -1) {
            loadStudentData();
        } else {
            Toast.makeText(this, "Error: No Roll Number Found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (isAdminView) {

            approveBtn.setVisibility(View.VISIBLE);

            approveBtn.setOnClickListener(v -> performApproval());
        } else {

            approveBtn.setVisibility(View.GONE);
        }
        goBackBtn.setOnClickListener(v -> finish());
    }

    private void performApproval() {
        firebaseManager.approveStudent(studentRoll, new DatabaseCallback() {
            @Override
            public void checkResult(boolean isSuccess) {
                if (isSuccess) {
                    Toast.makeText(StudentProfileActivity.this, "Student Approved Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(StudentProfileActivity.this, "Failed to Approve.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(StudentProfileActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadStudentData() {
        firebaseManager.getStudentByRoll(studentRoll, new DatabaseCallback() {
            @Override
            public void onStudentReceived(Student student) {
                if (student != null) {
                    rollTxt.setText(String.valueOf(student.getRoll()));
                    nameTxt.setText(student.getName());
                    emailTxt.setText(student.getEmail());
                    addressTxt.setText(student.getAddress());
                    departmentTxt.setText(student.getDept());
                    cgpaTxt.setText(student.getCgpa());
                    birthDateTxt.setText(student.getBirthdate());

                } else {
                    Toast.makeText(StudentProfileActivity.this, "Student data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(StudentProfileActivity.this, "Network Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}