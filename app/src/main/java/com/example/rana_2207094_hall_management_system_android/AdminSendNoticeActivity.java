package com.example.rana_2207094_hall_management_system_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminSendNoticeActivity extends AppCompatActivity {

    private EditText titleField, messageField;
    private Button sendNoticeBtn, goBackBtn;

    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_send_notice);

        firebaseManager = new FirebaseManager();

        titleField = findViewById(R.id.titleField);
        messageField = findViewById(R.id.messageField);
        sendNoticeBtn = findViewById(R.id.sendNoticeBtn);
        goBackBtn = findViewById(R.id.goBackBtn);

        setupActions();
    }

    private void setupActions() {

        goBackBtn.setOnClickListener(v -> finish());

        sendNoticeBtn.setOnClickListener(v -> {
            String title = titleField.getText().toString().trim();
            String message = messageField.getText().toString().trim();

            if (title.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "All fields required!", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseManager.sendNotice(title, message, new DatabaseCallback() {
                @Override
                public void checkResult(boolean isSuccess) {
                    if (isSuccess) {
                        Toast.makeText(AdminSendNoticeActivity.this, "Notice Sent Successfully", Toast.LENGTH_SHORT).show();

                        titleField.setText("");
                        messageField.setText("");
                    } else {
                        Toast.makeText(AdminSendNoticeActivity.this, "Failed to send notice", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(AdminSendNoticeActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}