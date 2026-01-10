package com.example.rana_2207094_hall_management_system_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StudentReqToRemoveActivity extends AppCompatActivity {

    private Button requestRemoveBtn, goBackBtn;
    private FirebaseManager firebaseManager;
    private int loggedInRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_req_to_remove);

        loggedInRoll = getIntent().getIntExtra("LOGGED_IN_ROLL", -1);

        firebaseManager = new FirebaseManager();

        requestRemoveBtn = findViewById(R.id.requestRemoveBtn);
        goBackBtn = findViewById(R.id.goBackBtn);

        setupActions();
    }

    private void setupActions() {
        goBackBtn.setOnClickListener(v -> finish());

        requestRemoveBtn.setOnClickListener(v -> handleRemovalRequest());
    }

    private void handleRemovalRequest() {
        if (loggedInRoll == -1) {
            Toast.makeText(this, "Session Error", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseManager.getTotalHallDue(loggedInRoll, new DatabaseCallback() {
            @Override
            public void onTotalDueReceived(int amount) {
                if (amount > 0) {

                    Toast.makeText(StudentReqToRemoveActivity.this,
                            "Error: You have " + amount + " Tk due. Clear it first!",
                            Toast.LENGTH_LONG).show();
                } else {

                    submitRequest();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(StudentReqToRemoveActivity.this, "Network Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitRequest() {
        firebaseManager.setRemovalRequested(loggedInRoll, true, new DatabaseCallback() {
            @Override
            public void checkResult(boolean isSuccess) {
                if (isSuccess) {
                    Toast.makeText(StudentReqToRemoveActivity.this, "Request Submitted Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(StudentReqToRemoveActivity.this, "Failed to submit request", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(StudentReqToRemoveActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}