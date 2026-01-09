package com.example.rana_2207094_hall_management_system_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentHomeActivity extends AppCompatActivity {

    private TextView nameLabel;
    private Button myProfileBtn, checkHallDueBtn, viewNoticesBtn,
            reqDiningManagerBtn, reqToRemoveBtn, changePasswordBtn, signOutBtn;

    private FirebaseManager firebaseManager;
    private int loggedInRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        loggedInRoll = getIntent().getIntExtra("LOGGED_IN_ROLL", -1);

        if (loggedInRoll == -1) {
            Toast.makeText(this, "Error loading user session", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        firebaseManager = new FirebaseManager();
        initViews();

        loadUserData();

        setupClickListeners();
    }

    private void initViews() {
        nameLabel = findViewById(R.id.nameLabel);
        myProfileBtn = findViewById(R.id.myProfileBtn);
        checkHallDueBtn = findViewById(R.id.checkHallDueBtn);
        viewNoticesBtn = findViewById(R.id.viewNoticesBtn);
        reqDiningManagerBtn = findViewById(R.id.reqDiningManagerBtn);
        reqToRemoveBtn = findViewById(R.id.reqToRemoveBtn);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);
        signOutBtn = findViewById(R.id.signOutBtn);
    }

    private void loadUserData() {
        firebaseManager.getStudentByRoll(loggedInRoll, new DatabaseCallback() {
            @Override
            public void onStudentReceived(Student student) {
                if (student != null) {
                    nameLabel.setText(student.getName());
                } else {
                    nameLabel.setText("Student Not Found");
                }
            }

            @Override
            public void onError(Exception e) {
                nameLabel.setText("Connection Error");
            }
        });
    }

    private void setupClickListeners() {

        myProfileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StudentHomeActivity.this, StudentProfileActivity.class);
            intent.putExtra("LOGGED_IN_ROLL", loggedInRoll);
            intent.putExtra("IS_ADMIN_VIEW", false);
            startActivity(intent);
        });

        checkHallDueBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StudentHomeActivity.this, StudentHallDuesActivity.class);
            intent.putExtra("LOGGED_IN_ROLL", loggedInRoll);
            startActivity(intent);
        });

        viewNoticesBtn.setOnClickListener(v -> {

             Intent intent = new Intent(StudentHomeActivity.this, StudentViewNoticesActivity.class);
             startActivity(intent);
        });

        reqToRemoveBtn.setOnClickListener(v -> {

             Intent intent = new Intent(StudentHomeActivity.this, StudentReqToRemoveActivity.class);
             intent.putExtra("LOGGED_IN_ROLL", loggedInRoll);
             startActivity(intent);
        });

        signOutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StudentHomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        changePasswordBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StudentHomeActivity.this, StudentChangePasswordActivity.class);
            intent.putExtra("LOGGED_IN_ROLL", loggedInRoll);
            startActivity(intent);
        });

        reqDiningManagerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(StudentHomeActivity.this, StudentDiningManagerRequestActivity.class);
            intent.putExtra("LOGGED_IN_ROLL", loggedInRoll);
            startActivity(intent);
        });
    }
}